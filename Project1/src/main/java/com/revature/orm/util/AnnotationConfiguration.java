package com.revature.orm.util;

import java.sql.SQLException;

public class AnnotationConfiguration {

    // Use the mappings and properties specified in an application resource named hibernate.cfg.xml (Obviously change this to a file you would use. Probably look for a configuration
    // That would include a username, password, and path to a database. Where is that properties file? I don't want to go any complicated than that because Hibernate deals with file
    // type that I never heard of and don't have time to learn. Remember, make it simple. By default, this will be inside of src/main/resources/application.properties. Else
    // use the Overloaded method
    //private AnnotationConfiguration annotationConfiguration;
    private ConnectionPool connectionPool = null;
    private SessionFactoryIF sessionFactoryIF = null;

    // I need to make a new variable that will, by default, be in src/main/java and look for the class there. If I need to go deeper into packages, then I will change the string
    private String classPath = "src/main/java/";
    public AnnotationConfiguration () {
        super();
        //annotationConfiguration = new AnnotationConfiguration();
    }
    // DELETE AFTER YOU SUCCESSFULLY GET THIS TO WORK. WILL BE RELYING ON PROPERTIES FILE
    public void configure(String url, String username, String password) {
        //BasicConnectionPool basicConnectionPool = new BasicConnectionPool();
        try {
            connectionPool = BasicConnectionPool.create(url, username, password);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }
    public void configure(){
    }
    public void configure(String propertiesPath) {

    }
    // Will probably need this because the developer will organize their classes into packages. Read package level metadata, accepts the package name as a string and returns
    // the annotationconfiguration object
    public void addPackage(String packageName){

        // I am concatenating two strings, the original classpath and the package that the class will be located in
        classPath = classPath + packageName;

    }
    // This is the class you are looking through for any and all annotations. Make sure that the annotations are actually correct!
    // Read a mapping from the class annotation metadata. Accepts a persistentClass- the mapped class. Returns the annotationconfiguration object
    public void addAnnotatedClass(Class clazz) {
        classPath = classPath + clazz;


    }

    // Instantiate a new SessionFactory, using the properties and mappings in this configuration. The SessionFactory will be immutable, so changes made to the Configuration after building
    // the SessionFactory will not affect it. Basically make sure that everything is configured before calling this AND (I don't know if they mean that any changes made to the configuration
    // file will not change the SessionFactory or if they mean that any changes made to the AnnotationConfiguration object will not affect the SessionFactory).
    public SessionFactoryIF buildSessionFactory() {

        if (connectionPool == null)
        {
            // throw an exception
            System.out.println("Your connection is null");
            return null;
        }
        sessionFactoryIF = SessionFactory.create(connectionPool);

        return sessionFactoryIF;

    }
}
