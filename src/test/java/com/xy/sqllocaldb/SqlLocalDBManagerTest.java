package com.xy.sqllocaldb;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBManagerTest {

    @Test
    public void testCreate() throws Exception {
        SqlLocalDBManager manager = SqlLocalDBManager.createManager();
        Assert.assertNotNull(manager.getSqlLocalDBConfig());
        Assert.assertTrue(manager.getSqlLocalDBConfig().isUseLastVersion());
        Assert.assertNotNull(manager.getSqlLocalDBApi());
        Assert.assertNotNull(manager.getSqlLocalDBApi().getVersions());
    }

}
