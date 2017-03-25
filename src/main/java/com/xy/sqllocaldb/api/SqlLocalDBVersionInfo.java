package com.xy.sqllocaldb.api;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public interface SqlLocalDBVersionInfo {

    /**
     * Gets a value indicating whether the instance files exist on disk.
     * @return
     */
    boolean isExists();

    /**
     * Gets the version name.
     * @return
     */
    String getVersionName();

    /**
     * Gets the version.
     * @return
     */
    int[] getVersion();
}
