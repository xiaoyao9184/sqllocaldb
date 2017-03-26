package com.xy.sqllocaldb.win;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBNativeDef {

    // The maximum size of SQL Server LocalDB connection string.
    public static final int LOCALDB_MAX_SQLCONNECTION_BUFFER_SIZE = 260;

    // The maximum size of SQL Server LocalDB instance names.
    public static final int MAX_LOCALDB_INSTANCE_NAME_LENGTH = 128;

    // The maximum size of an SQL Server LocalDB version string.
    public static final int MAX_LOCALDB_VERSION_LENGTH = 43;

    // The maximum length of a SID string.
    public static final int MAX_STRING_SID_LENGTH = 186;

    // Specifies that error messages that are too long should be truncated.
    public static final int LOCALDB_TRUNCATE_ERR_MESSAGE = 1;






    public static final int LOCALDB_ERROR_INSTANCE_STOP_FAILED = 0x89c5010f;

}
