package com.revature.orm.util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public interface SessionIF {


    void close();
    void delete(Object object);
    void save(Object object);
    void update(Object object);
    Object get(Object id);
    List<Object> getAll();






}
