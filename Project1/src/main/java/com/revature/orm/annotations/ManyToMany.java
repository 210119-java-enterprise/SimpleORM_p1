package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToMany {
}

/*
*
*
    Use @JoinTable for entities linked through an association table.
    Use mappedBy attribute for bi-directional association.

*
* */
