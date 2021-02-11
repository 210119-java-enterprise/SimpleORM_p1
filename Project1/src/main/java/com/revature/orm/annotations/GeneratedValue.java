package com.revature.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// I need to check for the Id annotation. I don't think there is a way to do that with @Target. Might have to do that with business logic and the
// Field.isAnnotationPresent method that will check if a specific annotation is present. If I detect this annotation, then I have to do the
// Field.isAnnotationPresent method to detect if the Id annotation exists, else give them an error if it doesn't exist.
// isAnnotation present method.
@Target({ElementType.FIELD, Elem})
@Retention(RetentionPolicy.RUNTIME)
public @interface GeneratedValue {
}


/*
*
*Let database generate (auto-increment) the id column.
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
11

@Entity
@Table(name = "company")
public class Company implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue
  private int id;

...
}
*
* */
