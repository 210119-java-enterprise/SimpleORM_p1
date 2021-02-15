package com.revature.orm.util;

import java.sql.Connection;

public interface ConnectionPool {

    Connection getConnection();
    boolean releaseConnection(Connection connection);
    boolean destroyAllConnections();
    //String getUrl();
    //String getUser();
    //String getPassword();
}
