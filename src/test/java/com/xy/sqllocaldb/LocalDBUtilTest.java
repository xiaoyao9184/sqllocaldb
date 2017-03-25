package com.xy.sqllocaldb;

import com.github.sarxos.winreg.RegistryException;
import org.junit.Test;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class LocalDBUtilTest {

    @Test
    public void testCheck64Window(){
        assert LocalDBUtil.check64Window();
    }

    @Test
    public void testGetLocalDbApiPath() throws RegistryException {
        assert LocalDBUtil.getLocalDbApiPath() != null;
    }
}