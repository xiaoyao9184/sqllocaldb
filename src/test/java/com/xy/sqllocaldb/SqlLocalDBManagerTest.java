package com.xy.sqllocaldb;

import com.github.sarxos.winreg.RegistryException;
import com.sun.jna.Native;
import com.xy.sqllocaldb.api.*;
import com.xy.sqllocaldb.win.LocalDBInstanceInfo;
import com.xy.sqllocaldb.win.SqlLocalDBNativeDef;
import com.xy.sqllocaldb.win.SqlLocalDBNativeApi;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBManagerTest {


    private LocalDBManager sqlLocalDBManagerApi;

    @Before
    public void init() throws RegistryException {
        String path = LocalDBUtil.getLocalDbApiPath();
        SqlLocalDBNativeApi sqlLocalDBNativeApi = Native.loadLibrary(path, SqlLocalDBNativeApi.class);

        sqlLocalDBManagerApi = new LocalDBManager(sqlLocalDBNativeApi);
    }

    @Test
    public void testFormatMessage(){
        try {
            String msg = sqlLocalDBManagerApi.formatMessage(SqlLocalDBNativeDef.LOCALDB_ERROR_INSTANCE_STOP_FAILED);
            Assert.assertNotNull(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testGetVersions(){
        try {
            List<String> versions = sqlLocalDBManagerApi.getVersions();
            Assert.assertNotNull(versions);
            Assert.assertNotEquals(versions.size(),-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testGetVersion(){
        try {
            List<String> versions = sqlLocalDBManagerApi.getVersions();
            String last = versions.get(versions.size() - 1);
            SqlLocalDBVersionInfo info = sqlLocalDBManagerApi.getVersion(last);
            Assert.assertNotNull(info);
            int[] v = info.getVersion();
            Assert.assertNotNull(v);
            Assert.assertEquals(v.length,4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testGetInstances(){
        try {
            List<String> instances = sqlLocalDBManagerApi.getInstances();
            Assert.assertNotNull(instances);
            Assert.assertNotEquals(instances.size(),-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testGetInstance(){
        try {
            List<String> instances = sqlLocalDBManagerApi.getInstances();
            Assert.assertNotEquals(instances.size(),0);

            String first = instances.get(0);
            SqlLocalDBInstanceInfo info = sqlLocalDBManagerApi.getInstance(first);
            String name = info.getName();
            Assert.assertNotNull(info);
            Assert.assertNotNull(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testCreateInstance(){
        try {
            List<String> versions = sqlLocalDBManagerApi.getVersions();
            Assert.assertNotEquals(versions.size(),0);
            String version = versions.get(0);
            String name = UUID.randomUUID().toString();

            assert sqlLocalDBManagerApi.createInstance(version,name);
            SqlLocalDBInstanceInfo info = sqlLocalDBManagerApi.getInstance(name);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isExists());

            assert sqlLocalDBManagerApi.deleteInstance(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testDeleteInstance(){
        try {
            List<String> versions = sqlLocalDBManagerApi.getVersions();
            Assert.assertNotEquals(versions.size(),0);
            String version = versions.get(0);
            String name = UUID.randomUUID().toString();

            assert sqlLocalDBManagerApi.createInstance(version,name);
            SqlLocalDBInstanceInfo info = sqlLocalDBManagerApi.getInstance(name);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isExists());

            assert sqlLocalDBManagerApi.deleteInstance(name);
            info = sqlLocalDBManagerApi.getInstance(name);
            Assert.assertNotNull(info);
            Assert.assertFalse(info.isExists());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStartInstance(){
        try {
            List<String> instances = sqlLocalDBManagerApi.getInstances();
            Assert.assertNotEquals(instances.size(),0);

            String first = instances.get(0);
            String pipe = sqlLocalDBManagerApi.startInstance(first);
            Assert.assertNotNull(pipe);

            SqlLocalDBInstanceInfo info = sqlLocalDBManagerApi.getInstance(first);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isRunning());

            sqlLocalDBManagerApi.stopInstance(first,StopInstanceOptions.None,30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStopInstance(){
        try {
            List<String> instances = sqlLocalDBManagerApi.getInstances();
            String first = instances.get(0);
            assert sqlLocalDBManagerApi.stopInstance(first, StopInstanceOptions.None, 30);

            SqlLocalDBInstanceInfo info = sqlLocalDBManagerApi.getInstance(first);
            Assert.assertNotNull(info);
            Assert.assertFalse(info.isRunning());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStartTracing(){
        try {
            assert sqlLocalDBManagerApi.startTracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStopTracing(){
        try {
            assert sqlLocalDBManagerApi.stopTracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

}
