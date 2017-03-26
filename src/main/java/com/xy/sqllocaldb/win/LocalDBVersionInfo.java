package com.xy.sqllocaldb.win;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.xy.sqllocaldb.api.SqlLocalDBVersionInfo;

import java.util.Arrays;
import java.util.List;

/**
 *

 typedef struct _LocalDBVersionInfo
 {
 // Contains the size of the LocalDBVersionInfo struct
 DWORD  cbLocalDBVersionInfoSize;

 // Holds the version name
 TLocalDBVersion wszVersion;

 // TRUE if the instance files exist on disk, FALSE otherwise
 BOOL   bExists;

 // Holds the LocalDB version for the instance in the format: major.minor.build.revision
 DWORD  dwMajor;
 DWORD  dwMinor;
 DWORD  dwBuild;
 DWORD  dwRevision;
 } LocalDBVersionInfo;

 *
 *
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBVersionInfo extends Structure implements SqlLocalDBVersionInfo {
    public int cbLocalDBVersionInfoSize;
    public char[] wszVersion = new char[SqlLocalDBNativeDef.MAX_LOCALDB_VERSION_LENGTH+1];
    public boolean bExists;
    public int dwMajor;
    public int dwMinor;
    public int dwBuild;
    public int dwRevision;

    protected List<String> getFieldOrder() {
        return Arrays.asList("cbLocalDBVersionInfoSize", "wszVersion", "bExists", "dwMajor", "dwMinor", "dwBuild", "dwRevision");
    }

    public LocalDBVersionInfo() {
        super();
    }
    public LocalDBVersionInfo(int cbLocalDBVersionInfoSize, char wszVersion[], boolean bExists, int dwMajor, int dwMinor, int dwBuild, int dwRevision) {
        super();
        this.cbLocalDBVersionInfoSize = cbLocalDBVersionInfoSize;
        if ((wszVersion.length != this.wszVersion.length))
            throw new IllegalArgumentException("Wrong array size !");
        this.wszVersion = wszVersion;
        this.bExists = bExists;
        this.dwMajor = dwMajor;
        this.dwMinor = dwMinor;
        this.dwBuild = dwBuild;
        this.dwRevision = dwRevision;
    }
    public LocalDBVersionInfo(Pointer peer) {
        super(peer);
    }

    public static class ByReference extends LocalDBVersionInfo implements Structure.ByReference {}
    public static class ByValue extends LocalDBVersionInfo implements Structure.ByValue {}



    @Override
    public boolean isExists() {
        return bExists;
    }

    @Override
    public String getVersionName() {
        return new String(wszVersion).trim();
    }

    @Override
    public int[] getVersion(){
        int[] v = new int[4];
        v[0] = dwMajor;
        v[1] = dwMinor;
        v[2] = dwBuild;
        v[3] = dwRevision;
        return v;
    }
}
