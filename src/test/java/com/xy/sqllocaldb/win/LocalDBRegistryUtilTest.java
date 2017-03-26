package com.xy.sqllocaldb.win;

import com.github.sarxos.winreg.RegistryException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBRegistryUtilTest {

    @Test
    public void testCheck64Window(){
        assert LocalDBRegistryUtil.check64Window();
    }

    @Test
    public void testGetLocalDbApiPath() throws RegistryException {
        assert LocalDBRegistryUtil.getLocalDbApiPath() != null;
    }

    @Test
    public void testGetRegLocalDbInstalledVersions() throws RegistryException {
        List<LocalDBRegistryUtil.RegLocalDbInstalledVersion> list = LocalDBRegistryUtil.getRegLocalDbInstalledVersions();
        Assert.assertNotNull(list);
    }
}
