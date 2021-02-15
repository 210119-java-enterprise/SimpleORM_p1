package com.revature.orm.util;

import java.sql.Connection;

public interface SessionFactoryIF {

    void close();
    //ClassMetadata getClassMetadata(Class entityClass);
    Session getCurrentSession();
    boolean isClosed();
    SessionIF openSession();
    SessionIF openSession(Connection connection);


}
