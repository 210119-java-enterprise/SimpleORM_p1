package com.revature.orm.util;

import java.sql.SQLException;

public class AnnotationConfiguration {

    // Use the mappings and properties specified in an application resource named hibernate.cfg.xml (Obviously change this to a file you would use. Probably look for a configuration
    // That would include a username, password, and path to a database. Where is that properties file? I don't want to go any complicated than that because Hibernate deals with file
    // type that I never heard of and don't have time to learn. Remember, make it simple. By default, this will be inside of src/main/resources/application.properties. Else
    // use the Overloaded method
    //private AnnotationConfiguration annotationConfiguration;
    private ConnectionPool connectionPool = null;
   //private SessionFactoryIF sessionFactoryIF = null;

    // I need to make a new variable that will, by default, be in src/main/java and look for the class there. If I need to go deeper into packages, then I will change the string
    private String classPath = null;
    private AnnotationConfiguration (ConnectionPool connectionPool) {
        super();
        this.connectionPool = connectionPool;
        //annotationConfiguration = new AnnotationConfiguration();
    }
    // DELETE AFTER YOU SUCCESSFULLY GET THIS TO WORK. WILL BE RELYING ON PROPERTIES FILE
    public AnnotationConfiguration configure(String url, String username, String password) {
        //BasicConnectionPool basicConnectionPool = new BasicConnectionPool();
        try {
            connectionPool = BasicConnectionPool.create(url, username, password);
            return new AnnotationConfiguration(connectionPool);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//    public AnnotationConfiguration configure(){
//        try {
//            connectionPool = BasicConnectionPool.create();
//            return new AnnotationConfiguration(connectionPool);
//        }catch(SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public AnnotationConfiguration configure(String propertiesPath) {
//        try {
//            connectionPool = BasicConnectionPool.create(propertiesPath);
//            return new AnnotationConfiguration(connectionPool);
//        }catch(SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }
    // Will probably need this because the developer will organize their classes into packages. Read package level metadata, accepts the package name as a string and returns
    // the annotationconfiguration object
    public AnnotationConfiguration addPackage(String packageName){

        // I am concatenating two strings, the original classpath and the package that the class will be located in
        // I think I should check to make sure this packageName actually exists and return if it doesn't. Figure out how to do that!!!
        classPath = "src/main/java/" + packageName;
        return this;

    }
    // This is the class you are looking through for any and all annotations. Make sure that the annotations are actually correct!
    // Read a mapping from the class annotation metadata. Accepts a persistentClass- the mapped class. Returns the annotationconfiguration object
    public AnnotationConfiguration addAnnotatedClass(Class clazz) {
        // Check if the class exists! Figure out how to do that!!!
        // Check that the correct annotations exist!!! This is where this gets a little tough. Also, I have to figure out if you have to use this thing called
        // JSR 175   https://jcp.org/en/jsr/detail?id=175 or figure out some other way to check that the class is correctly annotated maybe using an idea
        // from the Metamodel class
        classPath = classPath + clazz;
        return this;

    }

    // Instantiate a new SessionFactory, using the properties and mappings in this configuration. The SessionFactory will be immutable, so changes made to the Configuration after building
    // the SessionFactory will not affect it. Basically make sure that everything is configured before calling this and any changes made to the Configuration object after building
    // the SessionFactory will not affect it
    public SessionFactoryIF buildSessionFactory() {

        if (connectionPool == null)
        {
            // throw an exception
            System.out.println("Your connection is null");
            return null;
        }
        else if (classPath == null)
        {
            // throw an exception
            System.out.println("Your classpath is null");
            return null;
        }
        SessionFactoryIF sessionFactoryIF = SessionFactory.create(connectionPool);

        return sessionFactoryIF;

    }
}
