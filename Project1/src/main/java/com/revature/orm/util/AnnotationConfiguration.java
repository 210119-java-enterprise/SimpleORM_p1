package com.revature.orm.util;

import com.revature.orm.annotations.Entity;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Set;

public class AnnotationConfiguration {

    // Use the mappings and properties specified in an application resource named hibernate.cfg.xml (Obviously change this to a file you would use. Probably look for a configuration
    // That would include a username, password, and path to a database. Where is that properties file? I don't want to go any complicated than that because Hibernate deals with file
    // type that I never heard of and don't have time to learn. Remember, make it simple. By default, this will be inside of src/main/resources/application.properties. Else
    // use the Overloaded method
    //private AnnotationConfiguration annotationConfiguration;
    private ConnectionPool connectionPool = null;
    private MetamodelIF metamodelIF = null;
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
        // MAKE SURE THE DEVELOPERS KNOW THAT THEY NEED TO INCLUDE ANY PACKAGES THAT THE CLASS IS CONTAINED IN THAT OCCUR AFTER THE JAVA FILE.
        // I am going to try to use the forPackage method that is part of ClasspathHelper, but I am having trouble figuring out what it accepts. It says that is searches for the package name as a resource, using ClassLoader.getResrouces(String)
        // For example, forPackage(org.reflections) effectively returns URLs from the classpath containing packages starting with org.reflections. Remember, it does not return null, so just check if the collection is empty
        if(packageName == null || packageName.trim().equals(""))
        {
            // I just want to make sure they know that they should make sure to include a value. I want to use my own exceptions to make sure they know what they did wrong and how to fix it.
            throw RuntimeException;
        }
        classPath = packageName;


        Collection<URL> URLsContainingPackagesStartingWithTheClassPath = ClasspathHelper.forPackage(classPath);
        if (URLsContainingPackagesStartingWithTheClassPath.isEmpty())
        {
            // I want to make sure that the packagename actually exists. If this collection is empty, then there are no URLs from the classpath containing packages starting with the packageName
            throw RuntimeException
        }


        return this;

    }
    // This is the class you are looking through for any and all annotations. Make sure that the annotations are actually correct!
    // Read a mapping from the class annotation metadata. Accepts a persistentClass- the mapped class. Returns the annotationconfiguration object
    public AnnotationConfiguration addAnnotatedClass(Class clazz) {
        // Check if the class exists! Figure out how to do that!!!
        // Check that the correct annotations exist!!! This is where this gets a little tough. Also, I have to figure out if you have to use this thing called
        // JSR 175   https://jcp.org/en/jsr/detail?id=175 or figure out some other way to check that the class is correctly annotated maybe using an idea
        // from the Metamodel class

        //If I understand this correctly, I already checked that the package exists. That is good. Now I am checking subtypes exist (maybe) and annotations exist (yes). The example
        // on Baeldung and the example on their github are somewhat different and I am still trying to figure out how to do this properly.
        Reflections reflections = new Reflections(classPath);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class);
        if (!annotated.contains(clazz.getClass()))
        {
            // The class is not an Entity, so I can't do anything with this. At least for now. All I care about is that I get passed a class that is also a table. Remember, very simple for now.
            throw RuntimeException
        }
        // So we found out that the class does have the Entity annotation. Now you have to check everything else in the class. This is where it gets hard.
        metamodelIF = metamodelIF.checkForCorrectness(clazz);

        if (metamodelIF == null){
            // The annotations in this file are not correct.
            throw RuntimeException
        }
        // Concatanate the class path
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
        SessionFactoryIF sessionFactoryIF = SessionFactory.create(connectionPool, metamodelIF);

        return sessionFactoryIF;

    }
}
