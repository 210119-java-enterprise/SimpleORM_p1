package com.revature.orm.util;
import com.revature.orm.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class TableClass{
    private Class clazz;
    /**
     *
     *
     * @return
     * */
    public TableClass(Class clazz) {
        if(clazz.getAnnotation(Table.class) == null) {
            throw new IllegalStateException("Cannot create TableClass object! Provided class, " + getName() + " is not annotated with @Table");
        }
        this.clazz = clazz;
    }
    /**
     *
     *
     * @return
     * */
    public String getName() {
        return clazz.getName();
    }
    /**
     *
     *
     * @return
     * */
    public Class<?> getType() {
        return clazz.getComponentType();
    }
    /**
     *
     *
     * @return
     * */
    public String getTableName() {
        Table table = (Table) clazz.getAnnotation(Table.class);
        return table.tableName();
    }
}
