package com.xy.sqllocaldb;

import com.sun.jna.Native;
import com.xy.sqllocaldb.api.SqlLocalDBApi;
import com.xy.sqllocaldb.win.LocalDBRegistryUtil;
import com.xy.sqllocaldb.win.LocalDBWrapper;
import com.xy.sqllocaldb.win.SqlLocalDBNativeApi;

import java.util.List;

/**
 * Created by xiaoyao9184 on 2017/3/26.
 */
public class SqlLocalDBManager {

    private SqlLocalDBConfig sqlLocalDBConfig;
    private SqlLocalDBApi sqlLocalDBApi;

    public SqlLocalDBManager(SqlLocalDBConfig sqlLocalDBConfig) throws Exception {
        this.sqlLocalDBConfig = sqlLocalDBConfig;
        this.init();
    }


    public SqlLocalDBConfig getSqlLocalDBConfig() {
        return sqlLocalDBConfig;
    }

    public void setSqlLocalDBConfig(SqlLocalDBConfig sqlLocalDBConfig) {
        this.sqlLocalDBConfig = sqlLocalDBConfig;
    }

    public SqlLocalDBApi getSqlLocalDBApi() {
        return sqlLocalDBApi;
    }

    public void setSqlLocalDBApi(SqlLocalDBApi sqlLocalDBApi) {
        this.sqlLocalDBApi = sqlLocalDBApi;
    }



    private void init() throws Exception {
        if(sqlLocalDBConfig.getLibPath() == null){
            List<LocalDBRegistryUtil.RegLocalDbInstalledVersion> list = LocalDBRegistryUtil.getRegLocalDbInstalledVersions();
            if(list.isEmpty()){
                throw new Exception("Cant find any library in Registry!");
            }

            if(sqlLocalDBConfig.getLibVersion() != null){
               list.stream()
                        .filter((item)-> sqlLocalDBConfig.getLibVersion().equalsIgnoreCase(item.getVersion()))
                        .findFirst()
                        .ifPresent(installed -> sqlLocalDBConfig.setLibPath(installed.getInstanceAPIPath()));
            }

            if(sqlLocalDBConfig.getLibPath() == null &&
                    sqlLocalDBConfig.isUseLastVersion()){
                LocalDBRegistryUtil.RegLocalDbInstalledVersion last = list.get(list.size() - 1);
                sqlLocalDBConfig.setLibVersion(last.getVersion());
                sqlLocalDBConfig.setLibPath(last.getInstanceAPIPath());
            }
        }

        SqlLocalDBNativeApi sqlLocalDBNativeApi = Native.loadLibrary(sqlLocalDBConfig.getLibPath(), SqlLocalDBNativeApi.class);
        sqlLocalDBApi = new LocalDBWrapper(sqlLocalDBNativeApi);
    }


    /**
     * Create
     * @return
     * @throws Exception
     */
    public static SqlLocalDBManager createManager() throws Exception {
        return new SqlLocalDBManager(new SqlLocalDBConfig());
    }
}
