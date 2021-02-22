package com.revature.orm.util;

import java.sql.Connection;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public interface SessionFactoryIF {
    /**
     *
     *
     * @return
     * */
    void close();
    /**
     *
     *
     * @return
     * */
    boolean isClosed();
    /**
     *
     *
     * @return
     * */
    SessionIF openSession();



}
