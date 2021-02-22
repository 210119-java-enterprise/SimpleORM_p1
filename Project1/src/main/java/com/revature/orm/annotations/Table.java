package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
/**
 *
 *
 * An annotation marking the table name for an entity
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public @interface Table {
    /**
     * A required method that denotes the name of the table/entity
     * */
    String tableName();

}
