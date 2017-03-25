package com.xy.sqllocaldb.win;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public interface SqlLocalDBNativeApi extends Library {


    /**
     * #define MAX_LOCALDB_VERSION_LENGTH 43
     * typedef WCHAR TLocalDBVersion[MAX_LOCALDB_VERSION_LENGTH + 1];
     * typedef TLocalDBVersion* PTLocalDBVersion;
     * HRESULT LocalDBGetVersions(
     * PTLocalDBVersion pVersion,
     * LPDWORD lpdwNumberOfVersions);
     * @param pVersion
     * @param lpdwNumberOfVersions
     * @return
     */
    int LocalDBGetVersions(Pointer pVersion, IntByReference lpdwNumberOfVersions);

    /**
     * HRESULT LocalDBGetVersionInfo(
     PCWSTR wszVersionName,
     PLocalDBVersionInfo pVersionInfo,
     DWORD dwVersionInfoSize);
     */
    int LocalDBGetVersionInfo(WString wszVersion, LocalDBVersionInfo pVersionInfo, int cbVersionInfo);

    /**
     #define MAX_LOCALDB_INSTANCE_NAME_LENGTH 128
     typedef WCHAR TLocalDBInstanceName[MAX_LOCALDB_INSTANCE_NAME_LENGTH + 1];
     typedef TLocalDBInstanceName* PTLocalDBInstanceName;
     HRESULT LocalDBGetInstances(
     PTLocalDBInstanceName pInstanceNames,
     LPDWORD lpdwNumberOfInstances
     );
     */
    int LocalDBGetInstances(Pointer pInstanceNames, IntByReference lpdwNumberOfInstances);

    /**
     HRESULT LocalDBGetInstanceInfo(
     PCWSTR wszInstanceName,
     PLocalDBInstanceInfo pInstanceInfo,
     DWORD dwInstanceInfoSize
     );
     */
    int LocalDBGetInstanceInfo(WString wszInstanceName, LocalDBInstanceInfo pInstanceInfo, int dwInstanceInfoSize);

    /**
     HRESULT LocalDBCreateInstance(
     PCWSTR wszVersion,
     PCWSTR pInstanceName,
     DWORD dwFlags
     );
     */
    int LocalDBCreateInstance(WString wszVersion, WString pInstanceName, int dwFlags);

    /**
     HRESULT LocalDBDeleteInstance(
     PCWSTR pInstanceName,
     DWORD dwFlags
     );
     */
    int LocalDBDeleteInstance(WString pInstanceName, int dwFlags);

    /**
     HRESULT LocalDBFormatMessage(
     HRESULT hrLocalDB,
     DWORD dwFlags,
     DWORD dwLanguageId,
     LPWSTR wszMessage,
     LPDWORD lpcchMessage
     );
     */
    int LocalDBFormatMessage(int hrLocalDB, int dwFlags, int dwLanguageId, Pointer wszMessage, IntByReference lpcchMessage);

    /**
     HRESULT LocalDBShareInstance(
     PSID pOwnerSID,
     PCWSTR pInstancePrivateName,
     PCWSTR pInstanceSharedName,
     DWORD dwFlags
     );
     */
    int LocalDBShareInstance(Pointer pOwnerSID, WString pInstancePrivateName, WString pInstanceSharedName, int dwFlags);

    /**
     HRESULT LocalDBUnShareInstance(
     PCWSTR pInstanceSharedName,
     DWORD dwFlags
     );
     */
    int LocalDBUnShareInstance(WString pInstanceSharedName, int dwFlags);

    /**
     HRESULT LocalDBStartInstance(
     PCWSTR pInstanceName,
     DWORD dwFlags,
     LPWSTR wszSqlConnection,
     LPDWORD lpcchSqlConnection
     );
     */
    int LocalDBStartInstance(WString pInstanceName, int dwFlags, Pointer wszSqlConnection, IntByReference lpcchSqlConnection);

    /**
     HRESULT LocalDBStopInstance(
     PCWSTR pInstanceName,
     DWORD dwFlags,
     ULONG ulTimeout
     );
     */
    int LocalDBStopInstance(WString pInstanceName, int dwFlags, long ulTimeout);

    /**
     * HRESULT LocalDBStartTracing();
     */
    int LocalDBStartTracing();

    /**
     * HRESULT LocalDBStopTracing();
     */
    int LocalDBStopTracing();
}
