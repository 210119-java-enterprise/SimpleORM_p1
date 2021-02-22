package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
/**
 *
 *
 * An annotaiton marking a field as an Id/Primary Key
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public @interface Id {
    /**
     * A required method that denotes the name of the id
     * */
    String columnName();
}

