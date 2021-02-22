package com.revature.orm.util;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public interface SessionIF {

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
    void delete(Object object);
    /**
     *
     *
     * @return
     * */
    void save(Object object);
    /**
     *
     *
     * @return
     * */
    void update(Object object);
    /**
     *
     *
     * @return
     * */
    Object get(Object id);
    /**
     *
     *
     * @return
     * */
    List<Object> getAll();






}
