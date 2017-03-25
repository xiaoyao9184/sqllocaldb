package com.xy.sqllocaldb.win;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.xy.sqllocaldb.LocalDBUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBNativeApiTest {

    public interface Kernel32 extends Library {
        // FREQUENCY is expressed in hertz and ranges from 37 to 32767
        // DURATION is expressed in milliseconds
        public boolean Beep(int FREQUENCY, int DURATION);
        public void Sleep(int DURATION);
    }

    @Test
    public void testLoadKernel32(){
        Kernel32 lib = Native.loadLibrary("kernel32", Kernel32.class);
        lib.Beep(698, 500);
        lib.Sleep(500);
        lib.Beep(698, 500);
        assert true;
    }

    @Test
    public void testLocalDbApi(){
        try {
            String path = LocalDBUtil.getLocalDbApiPath();
            SqlLocalDBNativeApi sqlLocalDBNativeApi = Native.loadLibrary(path, SqlLocalDBNativeApi.class);
            Assert.assertNotNull(sqlLocalDBNativeApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert true;
    }

}
