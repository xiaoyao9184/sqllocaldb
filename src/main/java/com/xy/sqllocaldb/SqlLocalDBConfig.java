package com.xy.sqllocaldb;

/**
 * Created by xiaoyao9184 on 2017/3/26.
 */
public class SqlLocalDBConfig {

    private String libPath;
    private String libVersion;
    private boolean useLastVersion = true;

    public boolean isUseLastVersion() {
        return useLastVersion;
    }

    public void setUseLastVersion(boolean useLastVersion) {
        this.useLastVersion = useLastVersion;
    }

    public String getLibPath() {
        return libPath;
    }

    public void setLibPath(String libPath) {
        this.libPath = libPath;
    }

    public String getLibVersion() {
        return libVersion;
    }

    public void setLibVersion(String libVersion) {
        this.libVersion = libVersion;
    }
}
