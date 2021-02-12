package com.revature.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface JoinColumn {
}
/*
*
* Represents an object that is acting as a foreign key to another table
* Remember to require OneToOne, ManyToOne, maybe more
* Still confused by this
* http://www.techferry.com/articles/hibernate-jpa-annotations.html#OneToOne
* For entities Contact and ContactDetail linked through a foriegn key, we can use @OneToOne and @JoinColumn annotations. In example below, the id genereated for Contact will be mapped to 'contact_id' column of ContactDetail table. Please note the usage of @MapsId for the same.
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
*
* */
/*
* Use @JoinColumn annotation for one-to-one or many-to-one associations when foreign key is held by one of the entities.
* We can use @OneToOne or @ManyToOne mappedBy attribute for bi-directional relations.
* Also see OneToOne and ManyToOne sections for more details.
1
2
3

@ManyToOne
@JoinColumn(name = "statusId")
private CompanyStatus status;
*
* */
