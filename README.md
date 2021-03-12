# SimpleORM_p1
## Configuration (POM File)
## Configuration (application.properties file)
## Annotations
## Initialize
This is an ORM that wraps the JDBC and allows you to implement CRUD functionality.

Setup
1. Include dependency within your POM file:
        <dependency>
            <groupId>org.example</groupId>
            <artifactId>Project1</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
2. Include an application.properties file either within your resources folder by default or somewhere else within your project. A url, admin-ur, and admin-pw should be set. The url refers to the URL of your database. The admin-ur is the username of your database. The admin-pw is the password of your database.
Model
1. Your model will contain Annotations required to use the ORM. The minimum Annotations required are @Entity over your model and @Id for your primary key. A no-args constructor is required as well as a getter and setter for your primary key.
2. Here is an example model:




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
* Fix delete method on session so the object is properly deleted within the session cache. Will not have to close the session and reopen it in order to see the updated list.
* Fix the update method on session so the object is properly updated within the session cache. Will not have to close the session and reopen it in order to see the updated list.
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
(include all environment setup steps)
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
* @Column marks a field as a column of your table. Provide column name your are referencing within this annotation.
* @Entity marks a class as an Entity.
* @Id marks a field as a primary key. Only 1 is allowed per class.
* @No Columns

> Here, you instruct other people on how to use your project after they’ve installed it. This would also be a good place to include screenshots of your project in action.

## Contributors

> Here list the people who have contributed to this project. (ignore this section, if its a solo project)

## License

This project uses the following license: [<license_name>](<link>).
