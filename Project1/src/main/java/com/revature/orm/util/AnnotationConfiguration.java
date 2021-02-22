package com.revature.orm.util;

import com.revature.orm.annotations.Entity;
import com.revature.orm.exceptions.InvalidClassPathException;
import com.revature.orm.exceptions.InvalidConnectionException;
import com.revature.orm.exceptions.InvalidEntityException;
import com.revature.orm.exceptions.InvalidParametersException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
/**
 * A class that contains fields and methods needed to set up
 * the SessionFactoryIF, ConnectionPool, MetaModelIF, and classPath
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class AnnotationConfiguration {
    private ConnectionPool connectionPool = null;
    private MetamodelIF metamodelIF = null;
    private String classPath = null;
    /**
     * A no-args default constructor that called super()
     * */
    public AnnotationConfiguration() {
        super();
    }

    /**
     * A method used to set up the connectionPool
     * by setting the connectionPool field to
     * a static create method in BasicConnectionPool
     * @return current AnnotationConfiguration
     * */
    public AnnotationConfiguration configure(){
        try {
            connectionPool = BasicConnectionPool.create();
            return this;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new InvalidConnectionException();
        }

    }
    /**
     *
     * A method used to set up the connectionPool by setting
     * the connectionPool field to a static create method
     * in BasicConnectionPool. This method accepts a String
     * that tells the program where the properties file is
     * located in the project
     * @param String propertiesPath
     * @return current AnnotationConfiguration
     * */
    public AnnotationConfiguration configure(String propertiesPath) {
        try {
            connectionPool = BasicConnectionPool.create(propertiesPath);
            return this;
        }catch(SQLException e) {
            e.printStackTrace();
            throw new InvalidConnectionException();
        }


    }
    /**
     *
     * A method used to add a package path to the class path
     * while also checking that the package actual exists
     * @param String packageName
     * @return current AnnotationConfiguration
     * */
    public AnnotationConfiguration addPackage(String packageName){

        if(packageName == null || packageName.trim().equals(""))
        {
            throw new InvalidParametersException("Invalid package name. The package name passed was either null or empty");
        }
        classPath = packageName;
        Collection<URL> URLsContainingPackagesStartingWithTheClassPath = ClasspathHelper.forPackage(classPath);
        if (URLsContainingPackagesStartingWithTheClassPath.isEmpty())
        {
            throw new InvalidClassPathException("Invalid package name. The package name passed '" + packageName + "' cannot be found or doesn't exist.");
        }

        return this;
    }
    /**
     *
     *
     * @return
     * */
    public AnnotationConfiguration addAnnotatedClass(Class clazz) {
        // Check if the class exists! Figure out how to do that!!!
        // Check that the correct annotations exist!!! This is where this gets a little tough. Also, I have to figure out if you have to use this thing called
        // JSR 175   https://jcp.org/en/jsr/detail?id=175 or figure out some other way to check that the class is correctly annotated maybe using an idea
        // from the Metamodel class

        //If I understand this correctly, I already checked that the package exists. That is good. Now I am checking subtypes exist (maybe) and annotations exist (yes). The example
        // on Baeldung and the example on their github are somewhat different and I am still trying to figure out how to do this properly.

        if(clazz == null) {
            throw new InvalidParametersException("Invalid class name. The class name passed was null.");
        }
        Reflections reflections = new Reflections(classPath);

        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Entity.class); //Bug with version 0.9.12, switching to version 0.9.11

        if (!Arrays.toString(Arrays.stream(annotated.toArray()).toArray()).contains("class " + clazz.getName()))
        {
            // The class is not an Entity, so I can't do anything with this. At least for now. All I care about is that I get passed a class that is also a table. Remember, very simple for now.
            throw new InvalidEntityException("Invalid class. The class either doesn't exist or the class you are passing was not given an @Entity annotation.");
            //throw new InvalidClassPathException("Invalid class name. The class name passed '" + clazz.toString() + "' cannot be found or doesn't exist.");
        }
        // So we found out that the class does have the Entity annotation. Now you have to check everything else in the class. This is where it gets hard.
        metamodelIF = new Model();
        metamodelIF = metamodelIF.checkForCorrectness(clazz);

        if (metamodelIF == null){
            // The annotations in this file are not correct.
            throw new InvalidEntityException();
        }
        // Concatanate the class path
        classPath = classPath + clazz;
        return this;
    }

    /**
     *
     *
     * @return
     * */
    public SessionFactoryIF buildSessionFactory() {

        if (connectionPool == null)
        {
            // throw an exception
            throw new InvalidConnectionException("Invalid connection. You did not set up a connection for this session factory.");
        }
        else if (classPath == null)
        {
            // throw an exception
            throw new InvalidClassPathException("Invalid classpath. You did not set up a class path.");
        }
        SessionFactoryIF sessionFactoryIF = SessionFactory.create(connectionPool, metamodelIF);

        return sessionFactoryIF;

    }
}
