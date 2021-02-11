package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OrderBy {
}


/*
*
*
* Sort your data using @OrderBy annotation. In example below, it will sort all contacts in a company by their firstname in ascending order.
1
2

@OrderBy("firstName asc")
private Set contacts;
*
* */
