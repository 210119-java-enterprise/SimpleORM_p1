package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    public String columnName();
}


/*
*
* Specify the column mapping using @Column annotation.
1
2
3
4
5
6
7
8
9

@Entity
@Table(name = "company")
public class Company implements Serializable {

  @Column(name = "name")
  private String name;

...
}
*
* */
