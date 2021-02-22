package com.revature.orm.util;

import com.revature.orm.annotations.Id;

import java.lang.reflect.Field;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class IdField {

    private Field field;

    /**
     *
     *
     * @return
     * */
    public IdField(Field field) {
        if (field.getAnnotation(Id.class) == null) {
            throw new IllegalStateException("Cannot create IdField object! Provided field, " + getName() + " is not annotated with @Id");
        }
        this.field = field;
    }

    /**
     *
     *
     * @return
     * */
    public String getName() {
        return field.getName();
    }

    /**
     *
     *
     * @return
     * */
    public Class<?> getType() {
        return field.getType();
    }

    /**
     *
     *
     * @return
     * */
    public String getColumnName() {
        return field.getAnnotation(Id.class).columnName();
    }
}
