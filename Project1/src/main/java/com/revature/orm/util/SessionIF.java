package com.revature.orm.util;

import java.io.Serializable;
import java.sql.Connection;

public interface SessionIF {

    Transaction beginTransaction();
    void clear();
    Connection close();
    void delete(Object object);
    void delete(String entityName, Object object);
    Connection disconnect();
    boolean isOpen();
    Serializable save(Object object);
    Serializable save(String entityName, Object object);
    String createSQLQuery(String queryString);
    void create(Object object);
    void read();
    void readAll(String table);
    void update(Object object);




}
