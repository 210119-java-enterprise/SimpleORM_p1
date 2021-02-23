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
3. 
