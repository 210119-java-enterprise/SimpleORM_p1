package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Table {

    /*
    *
    * Specify the database table this Entity maps to using the name attribute of @Table annotation. In the example below, the data will be stored in 'company' table in the database.

1
2
3
4
5

@Entity
@Table(name = "company")
public class Company implements Serializable {
...
}
    *
    *
    * */
}
