package com.xy.sqllocaldb.win;

import com.github.sarxos.winreg.RegistryException;
import com.sun.jna.Native;
import com.xy.sqllocaldb.api.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBWrapperTest {


    private LocalDBWrapper sqlLocalDBWrapperApi;

    @Before
    public void init() throws RegistryException {
        String path = LocalDBRegistryUtil.getLocalDbApiPath();
        SqlLocalDBNativeApi sqlLocalDBNativeApi = Native.loadLibrary(path, SqlLocalDBNativeApi.class);

        sqlLocalDBWrapperApi = new LocalDBWrapper(sqlLocalDBNativeApi);
    }

    @Test
    public void testFormatMessage(){
        try {
            String msg = sqlLocalDBWrapperApi.formatMessage(SqlLocalDBNativeDef.LOCALDB_ERROR_INSTANCE_STOP_FAILED);
            Assert.assertNotNull(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testGetVersions(){
        try {
            List<String> versions = sqlLocalDBWrapperApi.getVersions();
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
            List<String> versions = sqlLocalDBWrapperApi.getVersions();
            String last = versions.get(versions.size() - 1);
            SqlLocalDBVersionInfo info = sqlLocalDBWrapperApi.getVersion(last);
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
            List<String> instances = sqlLocalDBWrapperApi.getInstances();
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
            List<String> instances = sqlLocalDBWrapperApi.getInstances();
            Assert.assertNotEquals(instances.size(),0);

            String first = instances.get(0);
            SqlLocalDBInstanceInfo info = sqlLocalDBWrapperApi.getInstance(first);
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
            List<String> versions = sqlLocalDBWrapperApi.getVersions();
            Assert.assertNotEquals(versions.size(),0);
            String version = versions.get(0);
            String name = UUID.randomUUID().toString();

            assert sqlLocalDBWrapperApi.createInstance(version,name);
            SqlLocalDBInstanceInfo info = sqlLocalDBWrapperApi.getInstance(name);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isExists());

            assert sqlLocalDBWrapperApi.deleteInstance(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testDeleteInstance(){
        try {
            List<String> versions = sqlLocalDBWrapperApi.getVersions();
            Assert.assertNotEquals(versions.size(),0);
            String version = versions.get(0);
            String name = UUID.randomUUID().toString();

            assert sqlLocalDBWrapperApi.createInstance(version,name);
            SqlLocalDBInstanceInfo info = sqlLocalDBWrapperApi.getInstance(name);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isExists());

            assert sqlLocalDBWrapperApi.deleteInstance(name);
            info = sqlLocalDBWrapperApi.getInstance(name);
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
            List<String> instances = sqlLocalDBWrapperApi.getInstances();
            Assert.assertNotEquals(instances.size(),0);

            String first = instances.get(0);
            String pipe = sqlLocalDBWrapperApi.startInstance(first);
            Assert.assertNotNull(pipe);

            SqlLocalDBInstanceInfo info = sqlLocalDBWrapperApi.getInstance(first);
            Assert.assertNotNull(info);
            Assert.assertTrue(info.isRunning());

            sqlLocalDBWrapperApi.stopInstance(first,StopInstanceOptions.None,30);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStopInstance(){
        try {
            List<String> instances = sqlLocalDBWrapperApi.getInstances();
            String first = instances.get(0);
            assert sqlLocalDBWrapperApi.stopInstance(first, StopInstanceOptions.None, 30);

            SqlLocalDBInstanceInfo info = sqlLocalDBWrapperApi.getInstance(first);
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
            assert sqlLocalDBWrapperApi.startTracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

    @Test
    public void testStopTracing(){
        try {
            assert sqlLocalDBWrapperApi.stopTracing();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

}
