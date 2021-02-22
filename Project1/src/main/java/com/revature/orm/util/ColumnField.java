package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import org.reflections.Reflections;

import java.lang.reflect.Field;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class ColumnField {

    private Field field;


    /**
     *
     *
     * @return
     * */
    public ColumnField(Field field) {
        if (field.getAnnotation(Column.class) == null) {
            throw new IllegalStateException ("Cannot create ColumnField object! Provided field, " + getName() + "is not annotated with @Column");
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
        return field.getAnnotation(Column.class).columnName();
    }
}
