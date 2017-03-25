package com.xy.sqllocaldb.api;

import java.util.Date;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public interface SqlLocalDBInstanceInfo {

    /**
     * Gets the name of the instance.
     * @return
     */
    String getName();

    /**
     * Gets a value indicating whether the instance's files exist on disk.
     * @return
     */
    boolean isExists();

    /**
     * Gets a value indicating whether the Registry configuration is corrupt.
     * @return
     */
    boolean isConfigurationCorrupted();

    /**
     * Gets a value indicating whether the instance is currently running.
     * @return
     */
    boolean isRunning();

    /**
     * Gets the LocalDB version for the instance.
     * @return
     */
    int[] getVersion();

    /**
     * Gets the UTC date and time the instance was last started.
     * @return
     */
    Date getLastStartDate();

    /**
     * Gets the named pipe that should be used to communicate with the instance.
     * @return
     */
    String getConnectionName();

    /**
     * Gets a value indicating whether the instance is shared.
     * @return
     */
    boolean isShared();

    /**
     * Gets the shared name of the LocalDB instance if the instance is shared.
     * @return
     */
    String getSharedInstanceName();

    /**
     * Gets the SID of the LocalDB instance owner if the instance is shared.
     * @return
     */
    String getOwnerSID();

    /**
     * Gets a value indicating whether the instance is automatic.
     * @return
     */
    boolean isAutomatic();
}
