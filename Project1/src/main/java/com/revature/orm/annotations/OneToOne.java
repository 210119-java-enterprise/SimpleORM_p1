package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface OneToOne {
}

/*
*
*
    Use @PrimaryKeyJoinColumn for associated entities sharing the same primary key.
    Use @JoinColumn & @OneToOne mappedBy attribute when foreign key is held by one of the entities.
    Use @JoinTable and mappedBy entities linked through an association table.
    Persist two entities with shared key using @MapsId

For entities Company and CompanyDetail sharing the same primary key, we can associate them using @OneToOne and @PrimaryKeyJoinColumn as shown in the example below.

Notice that the id property of CompanyDetail is NOT annotated with @GeneratedValue. It will be populated by id value of Company.
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
24
25
26

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

@Entity
@Table(name = "companyDetail")
public class CompanyDetail implements Serializable {

  @Id
  @Column(name = "id")
  private int id;

  ...
}
For entities Contact and ContactDetail linked through a foriegn key, we can use @OneToOne and @JoinColumn annotations. In example below, the id genereated for Contact will be mapped to 'contact_id' column of ContactDetail table. Please note the usage of @MapsId for the same.
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
24
25
26
27
28
29
30
31

@Entity
@Table(name = "contactDetail")
public class ContactDetail implements Serializable {

  @Id
  @Column(name = "id")
  @GeneratedValue
  private int id;

  @OneToOne
  @MapsId
  @JoinColumn(name = "contactId")
  private Contact contact;

  ...
}

@Entity
@Table(name = "contact")
public class Contact implements Serializable {

  @Id
  @Column(name = "ID")
  @GeneratedValue
  private Integer id;

  @OneToOne(mappedBy = "contact", cascade = CascadeType.ALL)
  private ContactDetail contactDetail;

  ....
}
Also note that the relationship between Company and CompanyDetail is uni-directional. On the other hand, the relationship between Contact and Contact Detail is bi-directional and that can be achieved using 'mappedBy' attribute.

The rationale to have one relationship as uni-directional and other as bi-directional in this tutorial is to illustrate both concepts and their usage. You can opt for uni-directional or bi-directional relationships to suit your needs.
*
*
* */
