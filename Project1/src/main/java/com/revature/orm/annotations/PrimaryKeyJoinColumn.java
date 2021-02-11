package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKeyJoinColumn {
}

/*
*
* @PrimaryKeyJoinColumn annotation is used for associated entities sharing the same primary key. See OneToOne section for details.
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

@Entity
@Table(name = "company")
public class Company implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue
  private int id;

  @OneToOne(cascade = CascadeType.MERGE)
  @PrimaryKeyJoinColumn
  private CompanyDetail companyDetail;

  ...
}
*
* */
