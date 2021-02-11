package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {

    public String columnName();
}


/*
*
*Annotate the id column using @Id.
1
2
3
4
5
6
7
8
9
10

@Entity
@Table(name = "company")
public class Company implements Serializable {

  @Id
  @Column(name = "id")
  private int id;

...
}
*
* */
