package com.xy.sqllocaldb.win;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinBase;
import com.xy.sqllocaldb.api.SqlLocalDBInstanceInfo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *

 typedef struct _LocalDBInstanceInfo
 {
 // Contains the size of the LocalDBInstanceInfo struct
 DWORD  cbLocalDBInstanceInfoSize;

 // Holds the instance name
 TLocalDBInstanceNamewszInstanceName;

 // TRUE if the instance files exist on disk, FALSE otherwise
 BOOL   bExists;

 // TRUE if the instance configuration registry is corrupted, FALSE otherwise
 BOOLbConfigurationCorrupted;

 // TRUE if the instance is running at the moment, FALSE otherwise
 BOOL   bIsRunning;

 // Holds the LocalDBManager version for the instance in the format: major.minor.build.revision
 DWORD  dwMajor;
 DWORD  dwMinor;
 DWORD  dwBuild;
 DWORD  dwRevision;

 // Holds the date and time when the instance was started for the last time
 FILETIME ftLastStartUTC;

 // Holds the name of the TDS named pipe to connect to the instance
 WCHARwszConnection;

 // TRUE if the instance is shared, FALSE otherwise
 BOOLbIsShared;

 // Holds the shared name for the instance (if the instance is shared)
 TLocalDBInstanceNamewszSharedInstanceName;

 // Holds the SID of the instance owner (if the instance is shared)
 WCHARwszOwnerSID;

 // TRUE if the instance is Automatic, FALSE otherwise
 BOOLbIsAutomatic;
 } LocalDBInstanceInfo;

 *
 *
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBInstanceInfo extends Structure implements SqlLocalDBInstanceInfo {

    public int cbLocalDBInstanceInfoSize;
    public char[] wszInstanceName = new char[SqlLocalDBNativeDef.MAX_LOCALDB_INSTANCE_NAME_LENGTH+1];
    public boolean bExists;
    public boolean bConfigurationCorrupted;
    public boolean bIsRunning;
    public int dwMajor;
    public int dwMinor;
    public int dwBuild;
    public int dwRevision;
    public WinBase.FILETIME ftLastStartUTC;
    public char[] wszConnection = new char[SqlLocalDBNativeDef.LOCALDB_MAX_SQLCONNECTION_BUFFER_SIZE+1];
    public boolean bIsShared;
    public char[] wszSharedInstanceName = new char[SqlLocalDBNativeDef.MAX_LOCALDB_INSTANCE_NAME_LENGTH+1];
    public char[] wszOwnerSID = new char[SqlLocalDBNativeDef.MAX_STRING_SID_LENGTH+1];
    public boolean bIsAutomatic;
    protected List<String> getFieldOrder() {
        return Arrays.asList("cbLocalDBInstanceInfoSize", "wszInstanceName", "bExists",
                "bConfigurationCorrupted","bIsRunning",
                "dwMajor", "dwMinor", "dwBuild", "dwRevision",
                "ftLastStartUTC",
                "wszConnection","bIsShared",
                "wszSharedInstanceName", "wszOwnerSID",
                "bIsAutomatic");
    }
    public LocalDBInstanceInfo() {
        super();
    }
    public LocalDBInstanceInfo(int cbLocalDBInstanceInfoSize, char wszInstanceName[], boolean bExists,
                               boolean bConfigurationCorrupted, boolean bIsRunning,
                               int dwMajor, int dwMinor, int dwBuild, int dwRevision,
                               WinBase.FILETIME ftLastStartUTC,
                               char wszConnection[],boolean bIsShared,
                               char[] wszSharedInstanceName,char[] wszOwnerSID,
                               boolean bIsAutomatic) {
        super();
        this.cbLocalDBInstanceInfoSize = cbLocalDBInstanceInfoSize;
        if ((wszInstanceName.length != this.wszInstanceName.length))
            throw new IllegalArgumentException("Wrong array size !");
        this.wszInstanceName = wszInstanceName;
        this.bExists = bExists;
        this.bConfigurationCorrupted = bConfigurationCorrupted;
        this.bIsRunning = bIsRunning;
        this.dwMajor = dwMajor;
        this.dwMinor = dwMinor;
        this.dwBuild = dwBuild;
        this.dwRevision = dwRevision;
        this.ftLastStartUTC = ftLastStartUTC;
        if ((wszConnection.length != this.wszConnection.length))
            throw new IllegalArgumentException("Wrong array size !");
        this.wszConnection = wszConnection;
        this.bIsShared = bIsShared;
        if ((wszSharedInstanceName.length != this.wszSharedInstanceName.length))
            throw new IllegalArgumentException("Wrong array size !");
        this.wszSharedInstanceName = wszSharedInstanceName;
        if ((wszOwnerSID.length != this.wszOwnerSID.length))
            throw new IllegalArgumentException("Wrong array size !");
        this.wszOwnerSID = wszOwnerSID;
        this.bIsAutomatic = bIsAutomatic;
    }
    public LocalDBInstanceInfo(Pointer peer) {
        super(peer);
    }

    public static class ByReference extends LocalDBInstanceInfo implements Structure.ByReference {}
    public static class ByValue extends LocalDBInstanceInfo implements Structure.ByValue {}



    @Override
    public String getName(){
        return new String(wszInstanceName).trim();
    }

    @Override
    public boolean isExists(){
        return bExists;
    }

    @Override
    public boolean isConfigurationCorrupted(){
        return bConfigurationCorrupted;
    }

    @Override
    public boolean isRunning(){
        return bIsRunning;
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

    @Override
    public Date getLastStartDate(){
        return ftLastStartUTC.toDate();
    }

    @Override
    public String getConnectionName(){
        return new String(wszConnection).trim();
    }

    @Override
    public boolean isShared(){
        return bIsShared;
    }

    @Override
    public String getSharedInstanceName(){
        return new String(wszSharedInstanceName).trim();
    }

    @Override
    public String getOwnerSID(){
        return new String(wszOwnerSID).trim();
    }

    @Override
    public boolean isAutomatic(){
        return bIsAutomatic;
    }

}
