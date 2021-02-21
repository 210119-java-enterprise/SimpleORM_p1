package com.revature.orm.util;
import com.revature.orm.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


public class TableClass{
    private Class clazz;

    public TableClass(Class clazz) {
        if(clazz.getAnnotation(Table.class) == null) {
            throw new IllegalStateException("Cannot create TableClass object! Provided class, " + getName() + " is not annotated with @Table");
        }
        this.clazz = clazz;
    }

    public String getName() {
        return clazz.getName();
    }

    public Class<?> getType() {
        return clazz.getComponentType();
    }

    public String getTableName() {
        Table table = (Table) clazz.getAnnotation(Table.class);
        return table.tableName();
    }
}
