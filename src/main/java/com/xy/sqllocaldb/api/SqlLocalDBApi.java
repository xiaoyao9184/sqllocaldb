package com.xy.sqllocaldb.api;

import java.util.List;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public interface SqlLocalDBApi {

    String formatMessage(int msgCode) throws SqlLocalDBException;

    List<String> getVersions() throws SqlLocalDBException;

    SqlLocalDBVersionInfo getVersion(String name) throws SqlLocalDBException;

    List<String> getInstances() throws SqlLocalDBException;

    SqlLocalDBInstanceInfo getInstance(String name) throws SqlLocalDBException;

    boolean createInstance(String version, String name) throws SqlLocalDBException;

    boolean deleteInstance(String name) throws SqlLocalDBException;

    boolean shareInstance(String privateName, String sharedName) throws SqlLocalDBException;

    boolean unShareInstance(String sharedName) throws SqlLocalDBException;

    String startInstance(String name) throws SqlLocalDBException;

    boolean stopInstance(String name, StopInstanceOptions options, long timeout) throws SqlLocalDBException;

    boolean startTracing() throws SqlLocalDBException;

    boolean stopTracing() throws SqlLocalDBException;
}
