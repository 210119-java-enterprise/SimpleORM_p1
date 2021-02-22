package com.revature.orm.util;

import java.sql.Connection;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public interface ConnectionPool {

    /**
     *
     *
     * @return
     * */
    Connection getConnection();
    /**
     *
     *
     * @return
     * */
    boolean releaseConnection(Connection connection);
    /**
     *
     *
     * @return
     * */
    boolean destroyAllConnections();

}
