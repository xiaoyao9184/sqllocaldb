package com.xy.sqllocaldb;

import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;

import java.util.List;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBUtil {

    public static boolean check64Window(){
        boolean is64bit = false;
        if (System.getProperty("os.name").contains("Windows")) {
            is64bit = (System.getenv("ProgramFiles(x86)") != null);
        } else {
            is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
        }
        return is64bit;
    }

    public static String getLocalDbApiPath() throws RegistryException {
        WindowsRegistry reg = WindowsRegistry.getInstance();
        String branch = "SOFTWARE\\Microsoft\\Microsoft SQL Server Local DB\\Installed Versions";
        List<String> keys = reg.readStringSubKeys(HKey.HKLM, branch);
        branch = branch + "\\" + keys.get(keys.size()-1);
        return reg.readString(HKey.HKLM,branch,"InstanceAPIPath");
    }
}
