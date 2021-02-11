package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OneToMany {
}

/*
*
*
    Use mappedBy attribute for bi-directional associations with ManyToOne being the owner.
    OneToMany being the owner or unidirectional with foreign key - try to avoid such associations but can be achieved with @JoinColumn
    @JoinTable for Unidirectional with association table

Please see the many-to-one relationship between Contact and Company above. Company to Contact will be a one-to-many relationship. The owner of this relationship is Contact and hence we will use 'mappedBy' attribute in Company to make it bi-directional relationship.
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

  @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
  @OrderBy("firstName asc")
  private Set contacts;

  ...

 }
Again, for this tutorial, we have kept Company to CompanyStatus relationship as uni-directional.
*
*
* */
