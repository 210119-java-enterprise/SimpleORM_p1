package com.revature.orm.util;

import java.sql.Connection;

public interface SessionFactoryIF {

    void close();
    //ClassMetadata getClassMetadata(Class entityClass);
    Session getCurrentSession();
    boolean isClosed();
    Session openSession();
    Session openSession(Connection connection);

}
