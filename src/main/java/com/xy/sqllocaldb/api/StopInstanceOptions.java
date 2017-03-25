package com.xy.sqllocaldb.api;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public enum StopInstanceOptions {
    /**
     * Shut down using the SHUTDOWN Transact-SQL command
     */
    None,

    /**
     * Shut down immediately using the kill process operating system command.
     */
    KillProcess,

    /**
     * Shut down using the WITH NOWAIT option Transact-SQL command.
     */
    NoWait,
}
