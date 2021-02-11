package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {

    /*
    *
    *
    * Annotate all your entity beans with @Entity.

1
2
3
4

@Entity
public class Company implements Serializable {
...
}
    *
    * */
}
