# Project 1 - Custom Object Relational Mapping Framework

## Project Description

A custom object relational mapping (ORM) framework that allows for a simplified and SQL-free interaction with a relational data source. This custom ORM was inspired by Hibernate for its detailed documentation and outstanding use of design patterns.

## Technologies Used

* Java - version 8.0
* Apache Maven - version 3.6.3
* PostGreSQL - version 12.4
* Git SCM (on GitHub) - version 2.30.1

## Features

List of features ready and TODOs for future development
* Connection Pooling
* A public API that can be added as a dependency in other projects
* Programmatic configuration of entities
* Programmatic persistence of entities (basic CRUD support)
* Lightweight session creation
* Session-based caching to minimize calls to the database

To-do list:
* Allow for more than 1 Entity class per SessionFactoryIF
* Implement foriegn key references
* Add transactions to the ORM. Would like to give the developer the chance to not immediately update the database records and wait until they commit those records.

## Getting Started

This project is a simple ORM. That means you are able to communicate between your Java project and your postgreSQL database. Note that the postgresql
dependency used in this ORM is 42.2.12 which works with PostgreSQL 8.2 and higher using the version 3.0 of the protocol.

1. git clone https://github.com/210119-java-enterprise/SimpleORM_p1.git
	Builds the project described by your Maven POM file and installs the resulting artifact (JAR) into your local Maven repository
2. cd into SimpleORM_p1
3. cd into Project1
4. mvn install (This is to build the project described by the local Maven POM file 
   within the Project1 directory and installs the resulting artifact (JAR) into your local Maven repository)
5. You can now use the dependency.

1. Inside your pom.xml file, include this dependency.
        <dependency>
            <groupId>org.example</groupId>
            <artifactId>Project1</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
2. Create an application.properties file. It is recommended you put this inside your resources folder, but you can put this anywhere within your project, you
   will just have to specify where it is located later in the setup if you don't put it in the resurces folder.
3. Inside your application.properties file, specify 3 properties, url, admin-usr, and admin-pw.
4. Set url equal to your jdbc:postgresql database
5. Set admin-usr equal to your username for your jdbc:postgresql database
6. Set admin-pw equal to your password for your jdbc:postgresql database
7. Your application.properties should look like the following, with data for each property filled in:
![image](https://user-images.githubusercontent.com/77693248/110888300-e5bf1d00-82b9-11eb-8ad3-c82cddf97f96.png)

## Usage

1. Create all tables prior to starting this project. This ORM requires all tables in the database are already created as well as having a primary key in each
   table.
2. Create a Java class representing a table in your database.
3. Use the appropriate annotations in your class to correctly mark your class as well as your fields.
* @Column marks a field as a column of your table. Provide column name your are referencing within this annotation. REQUIRED unless using @NoColumns
* @Entity marks a class as an Entity. REQUIRED
* @Id marks a field as a primary key. Only 1 is allowed per class. REQUIRED
* @NoColumns marks an Entity as having no other columns except the primary key.
* @Table marks an Entity's table name. REQUIRED.
4. Create a no argument constructor
5. Create getters and setters for your field(s). They must start with get or set respectively and have the field name. Case does not matter.
6. Example of a class with a primary key and columns

![image](https://user-images.githubusercontent.com/77693248/110892206-9d0b6200-82c1-11eb-883d-143b7ebc2dd7.png)

7. Example of a class with a primary key and no columns:

![image](https://user-images.githubusercontent.com/77693248/110892282-c4fac580-82c1-11eb-88ba-58a394f26899.png)

8. Set the SessionFactoryIF fieldName = new AnnotationConfiguration().configure().addPackage("your entity class directory path").addAnnotatedClass(YourEntityClass.class).buildSessionFactory();
9. 1 SessionFactoryIF per Entity Class
10. Example of SessionFactoryIF setup:

![image](https://user-images.githubusercontent.com/77693248/110892692-83b6e580-82c2-11eb-86fb-7c852f6a6e0f.png)

NOTE: Entity directory starts at src/main/java. If your entity class is located within that directory, you do not need the addPackage method.

11. Example of SessionFactoryIF setup without addPackage method:

![image](https://user-images.githubusercontent.com/77693248/110893181-7cdca280-82c3-11eb-8775-df253577dcdf.png)

12. Create a SessionIF object and open a session.

![image](https://user-images.githubusercontent.com/77693248/110893433-f8d6ea80-82c3-11eb-8df6-f16687974768.png)

13. Methods you can use from that SessionIF object:
* close(), closes a session and releases all data stored locally. Need to create a new instance of a session with SessionFactoryIF object's openSession() method.
* save(EntityObject), allows you to insert a new instance of your Entity Class into your table. Please include a key, since this ORM does not auto generate one.
* getAll(), allows you to get all rows/instances of the entity
* get(PrimaryKeyValue), gets the entity instance with that primary key value
* delete(EntityObject), deletes the entity object.
* update(EntityObject), updates the value of the entity object. Uses primary key to update the object, so cannot update the primary key.

14. Remember to close the session when you are finished with it and close the SessionFactory when you are finished with it. Closing the SessionFactory releases the connection and metamodel.

15. Example Driver that implements SimpleORM:

![image](https://user-images.githubusercontent.com/77693248/110896843-638b2480-82ca-11eb-9408-14403e30666e.png)

## License

Unknown.
This project uses the following license: [<license_name>](<link>).
