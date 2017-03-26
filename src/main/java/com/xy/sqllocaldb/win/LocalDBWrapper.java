package com.xy.sqllocaldb.win;

import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import com.xy.sqllocaldb.api.*;
import com.xy.sqllocaldb.win.LocalDBInstanceInfo;
import com.xy.sqllocaldb.win.LocalDBVersionInfo;
import com.xy.sqllocaldb.win.SqlLocalDBNativeDef;
import com.xy.sqllocaldb.win.SqlLocalDBNativeApi;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBWrapper implements SqlLocalDBApi {

    // The maximum length of an SQL LocalDBWrapper instance name, in bytes.
    public static final int MaxVersionLength = (SqlLocalDBNativeDef.MAX_LOCALDB_VERSION_LENGTH + 1) * 2;

    // The maximum length of an SQL LocalDBWrapper version string, in bytes.
    public static final int MaxInstanceNameLength = (SqlLocalDBNativeDef.MAX_LOCALDB_INSTANCE_NAME_LENGTH + 1) * 2;

    // The value to pass to functions which have a reserved parameter for future use.
    public static final int ReservedValue = 0;



    private SqlLocalDBNativeApi sqlLocalDBNativeApi;

    public LocalDBWrapper(SqlLocalDBNativeApi sqlLocalDBNativeApi){
        this.sqlLocalDBNativeApi = sqlLocalDBNativeApi;
    }

    private SqlLocalDBException createSqlLocalDB(int msgCode){
        try{
            String msg = this.formatMessage(msgCode);
            return new SqlLocalDBException(msg,msgCode);
        }catch (Exception e){
            return new SqlLocalDBException(e);
        }
    }

    @Override
    public String formatMessage(int msgCode) throws SqlLocalDBException {
        int count = SqlLocalDBNativeDef.LOCALDB_MAX_SQLCONNECTION_BUFFER_SIZE + 1;
        IntByReference stringLength = new IntByReference();
        stringLength.setValue(count);
        Pointer p = new Memory(count);

        int result = sqlLocalDBNativeApi.LocalDBFormatMessage(msgCode,
                SqlLocalDBNativeDef.LOCALDB_TRUNCATE_ERR_MESSAGE, ReservedValue,
                p,stringLength);
        if(result != 0){
            throw new RuntimeException("" + result);
        }
        return p.getWideString(0);
    }

    @Override
    public List<String> getVersions() throws SqlLocalDBException {
        IntByReference stringLength = new IntByReference();
        sqlLocalDBNativeApi.LocalDBGetVersions(Pointer.NULL,stringLength);
        int count = stringLength.getValue();
        Pointer p = new Memory(count * MaxVersionLength);
        int result = sqlLocalDBNativeApi.LocalDBGetVersions(p,stringLength);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return IntStream
                .range(0,count)
                .mapToObj((i) -> p.getWideString(i * MaxVersionLength))
                .collect(Collectors.toList());
    }

    @Override
    public SqlLocalDBVersionInfo getVersion(String version) throws SqlLocalDBException {
        WString v = new WString(version);
        LocalDBVersionInfo versionInfo = new LocalDBVersionInfo();
        int result = sqlLocalDBNativeApi.LocalDBGetVersionInfo(v,versionInfo,versionInfo.size());
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return versionInfo;
    }

    @Override
    public List<String> getInstances() throws SqlLocalDBException {
        IntByReference stringLength = new IntByReference();
        sqlLocalDBNativeApi.LocalDBGetInstances(Pointer.NULL,stringLength);
        int count = stringLength.getValue();
        Pointer p = new Memory(count * MaxInstanceNameLength);
        int result = sqlLocalDBNativeApi.LocalDBGetInstances(p,stringLength);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return IntStream
                .range(0,count)
                .mapToObj((i) -> p.getWideString(i * MaxInstanceNameLength))
                .collect(Collectors.toList());
    }

    @Override
    public SqlLocalDBInstanceInfo getInstance(String name) throws SqlLocalDBException {
        WString n = new WString(name);
        LocalDBInstanceInfo instanceInfo = new LocalDBInstanceInfo();
        int result = sqlLocalDBNativeApi.LocalDBGetInstanceInfo(n,instanceInfo,instanceInfo.size());
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return instanceInfo;
    }

    @Override
    public boolean createInstance(String version, String name) throws SqlLocalDBException {
        WString v = new WString(version);
        WString n = new WString(name);
        int result = sqlLocalDBNativeApi.LocalDBCreateInstance(v,n, ReservedValue);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public boolean deleteInstance(String name) throws SqlLocalDBException {
        WString n = new WString(name);
        int result = sqlLocalDBNativeApi.LocalDBDeleteInstance(n, ReservedValue);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public String startInstance(String name) throws SqlLocalDBException {
        WString n = new WString(name);
        int count = SqlLocalDBNativeDef.LOCALDB_MAX_SQLCONNECTION_BUFFER_SIZE + 1;
        IntByReference stringLength = new IntByReference();
        stringLength.setValue(count);
        Pointer p = new Memory(count);
        int result = sqlLocalDBNativeApi.LocalDBStartInstance(
                n, ReservedValue,
                p,stringLength);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return p.getWideString(0);
    }

    @Override
    public boolean stopInstance(String name, StopInstanceOptions options, long timeout) throws SqlLocalDBException {
        WString n = new WString(name);
        int result = sqlLocalDBNativeApi.LocalDBStopInstance(n,options.ordinal(),timeout);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public boolean shareInstance(String privateName, String sharedName) throws SqlLocalDBException {
        WString pInstancePrivateName = new WString(privateName);
        WString pInstanceSharedName = new WString(sharedName);

        //todo
        Pointer p = new Memory(MaxVersionLength);
        int result = sqlLocalDBNativeApi.LocalDBShareInstance(p,
                pInstancePrivateName,pInstanceSharedName, ReservedValue);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public boolean unShareInstance(String sharedName) throws SqlLocalDBException {
        WString pInstanceSharedName = new WString(sharedName);
        int result = sqlLocalDBNativeApi.LocalDBUnShareInstance(pInstanceSharedName, ReservedValue);
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public boolean startTracing() throws SqlLocalDBException {
        int result = sqlLocalDBNativeApi.LocalDBStartTracing();
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

    @Override
    public boolean stopTracing() throws SqlLocalDBException {
        int result = sqlLocalDBNativeApi.LocalDBStopTracing();
        if(result != 0){
            throw createSqlLocalDB(result);
        }
        return true;
    }

}
