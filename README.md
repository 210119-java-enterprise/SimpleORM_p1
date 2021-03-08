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
   
(include git clone command)
(include all environment setup steps)

> Be sure to include BOTH Windows and Unix command  
> Be sure to mention if the commands only work on a specific platform (eg. AWS, GCP)

- All the `code` required to get started
- Images of what it should look like

## Usage

> Here, you instruct other people on how to use your project after they’ve installed it. This would also be a good place to include screenshots of your project in action.

## Contributors

> Here list the people who have contributed to this project. (ignore this section, if its a solo project)

## License

This project uses the following license: [<license_name>](<link>).
