package com.revature.orm.util;

import java.sql.Connection;

public class SessionFactory implements SessionFactoryIF {

    private ConnectionPool connectionPool = null;
    private MetamodelIF metamodelIF = null;
    //private SessionIF sessionIF;

    private SessionFactory(ConnectionPool connectionPool, MetamodelIF metamodelIF) {
        super();
        this.connectionPool = connectionPool;
        this.metamodelIF = metamodelIF;

    }

    // Will probably put more into this. For now, this just associates a connection pool with this session factory that I can easily grab later
    public static SessionFactory create(ConnectionPool connectionPool, MetamodelIF metamodelIF) {
        return new SessionFactory(connectionPool, metamodelIF);
    }

    // Destroy this SessionFactory and release all resources (caches, connection pools, etc).
    @Override
    public void close() {

        connectionPool = null;
    }
    //ClassMetadata getClassMetadata(Class entityClass);

    // Obtains the current session
    Session getCurrentSession() {

    }

    // Is this factory already closed?
    boolean isClosed() {

        return connectionPool == null;

    }

    // Official definition from hibernate jboss
    // JDBC connection(s will be obtained from the configured ConnectionProvider as need to perform requested work.
    // Obviously, I am just going to grab from the ConnectionPool a connection stored and set everything up after that.
    // obtain a connection and create a session
    // For now, I won't have a discriminator, but I might try to find a way to have one later.
    public SessionIF openSession() {

        SessionIF sessionIF = Session.create(connectionPool.getConnection(), metamodelIF);

        return sessionIF;

    }

    // Open a session with your own connection. Think about not providing this, since this can get overcomplicated and I would rather them deal with the connection pool that was
    // created prior.
    public SessionIF openSession(Connection connection) {

    }
}
