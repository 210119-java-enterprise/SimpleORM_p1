package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Version {
}

/*
*
* Control versioning or concurrency using @Version annotation.
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

  @Version
  @Column(name = "version")
  private Date version;

...
}
*
* */
