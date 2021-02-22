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
 * An annotaiton marking an Entity with no columns/ only a primary key/id
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public @interface NoColumns {
}
