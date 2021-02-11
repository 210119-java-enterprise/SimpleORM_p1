package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JoinTable {
}

/*
*
*Use @JoinTable and mappedBy for entities linked through an association table.
 * */
