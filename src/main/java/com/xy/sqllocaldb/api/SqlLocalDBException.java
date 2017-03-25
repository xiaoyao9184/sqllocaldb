package com.xy.sqllocaldb.api;

import java.sql.SQLException;

/**
 * Created by xiaoyao9184 on 2017/3/25.
 */
public class SqlLocalDBException extends SQLException {

    public SqlLocalDBException(Exception e) {
        super(e);
    }

    public SqlLocalDBException(String msg, int errorCode){
        super(msg,null,errorCode);
    }

}
