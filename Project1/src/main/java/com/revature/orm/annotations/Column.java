package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)

/**
* An annotation marking a field as a table column
* @author Daniel Skwarcha
* @version %I% %G%
* */
public @interface Column {

    /**
    * A required method that denotes the name of the column
    * */
    String columnName();
}



