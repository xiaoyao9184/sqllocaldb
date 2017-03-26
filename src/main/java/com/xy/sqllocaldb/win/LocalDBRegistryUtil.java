package com.xy.sqllocaldb.win;

import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBRegistryUtil {

    public static boolean check64Window(){
        boolean is64bit;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").contains("64"));
        }
        return is64bit;
    }

    @Deprecated
    public static String getLocalDbApiPath() throws RegistryException {
        WindowsRegistry reg = WindowsRegistry.getInstance();
        String branch = "SOFTWARE\\Microsoft\\Microsoft SQL Server Local DB\\Installed Versions";
        List<String> keys = reg.readStringSubKeys(HKey.HKLM, branch);
        branch = branch + "\\" + keys.get(keys.size()-1);
        return reg.readString(HKey.HKLM,branch,"InstanceAPIPath");
    }

    public static List<RegLocalDbInstalledVersion> getRegLocalDbInstalledVersions(boolean isWOW64) {
        WindowsRegistry reg = WindowsRegistry.getInstance();
        String branch = "SOFTWARE\\Microsoft\\" +
                (isWOW64 ? "Wow6432Node\\" : "") +
                "Microsoft SQL Server Local DB\\Installed Versions";
        try {
            List<String> keys = reg.readStringSubKeys(HKey.HKLM, branch);
            return keys
                    .stream()
                    .sorted()
                    .map((key)-> {
                        try {
                            String versionBranch = branch + "\\" + key;
                            String apiPath = reg.readString(HKey.HKLM,versionBranch,"InstanceAPIPath");
                            RegLocalDbInstalledVersion regV = new RegLocalDbInstalledVersion();
                            regV.setVersion(key);
                            regV.setWow64(isWOW64);
                            regV.setInstanceAPIPath(apiPath);
                            return regV;
                        } catch (RegistryException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
        } catch (RegistryException e) {
            return Collections.emptyList();
        }
    }

    public static List<RegLocalDbInstalledVersion> getRegLocalDbInstalledVersions() {
        List<RegLocalDbInstalledVersion> list = getRegLocalDbInstalledVersions(false);

        if(check64Window()){
            list.addAll(0,getRegLocalDbInstalledVersions(true));
        }
        return list;
    }

    /**
     * LocalDbInstalledVersion Model for Windows Registry
     */
    public static class RegLocalDbInstalledVersion {
        private boolean wow64;
        private String version;
        private String instanceAPIPath;

        public boolean isWow64() {
            return wow64;
        }

        public void setWow64(boolean wow64) {
            this.wow64 = wow64;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getInstanceAPIPath() {
            return instanceAPIPath;
        }

        public void setInstanceAPIPath(String instanceAPIPath) {
            this.instanceAPIPath = instanceAPIPath;
        }
    }
}
