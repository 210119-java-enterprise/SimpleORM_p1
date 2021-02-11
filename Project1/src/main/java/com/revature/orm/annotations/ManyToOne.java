package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
}

/*
*
*
    Use @JoinColumn when foreign key is held by one of the entities.
    Use @JoinTable for entities linked through an association table.

The two examples below illustrate many-to-one relationships. Contact to Company and Company to CompanyStatus. Many contacts can belong to a company. Similary many companies can share the same status (Lead, Prospect, Customer) - there will be many companies that are currently leads.
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
12
13
14
15
16
17
18
19
20
21
22
23

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

  @ManyToOne
  @JoinColumn(name = "companyId")
  private Company company;

  ...

 }

@Entity
@Table(name = "company")
public class Company implements Serializable {

  @ManyToOne
  @JoinColumn(name = "statusId")
  private CompanyStatus status;

  ...

 }
*
* */
