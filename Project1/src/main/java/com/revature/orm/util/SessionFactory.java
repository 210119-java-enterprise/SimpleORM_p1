package com.revature.orm.util;

import java.sql.Connection;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class SessionFactory implements SessionFactoryIF {

    private ConnectionPool connectionPool = null;
    private MetamodelIF metamodelIF = null;
    /**
     *
     *
     * @return
     * */
    private SessionFactory(ConnectionPool connectionPool, MetamodelIF metamodelIF) {
        super();
        this.connectionPool = connectionPool;
        this.metamodelIF = metamodelIF;

    }

    /**
     *
     *
     * @return
     * */
    public static SessionFactory create(ConnectionPool connectionPool, MetamodelIF metamodelIF) {
        return new SessionFactory(connectionPool, metamodelIF);
    }

    /**
     *
     *
     * @return
     * */
    @Override
    public void close() {

        connectionPool = null;
        metamodelIF = null;
    }


    /**
     *
     *
     * @return
     * */
    @Override
    public boolean isClosed() {

        return connectionPool == null && metamodelIF == null;

    }

    /**
     *
     *
     * @return
     * */
    public SessionIF openSession() {

        SessionIF sessionIF = Session.create(connectionPool, connectionPool.getConnection(), metamodelIF);

        return sessionIF;

    }

}
