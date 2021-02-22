package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidConnectionException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BasicConnectionPool implements ConnectionPool{


    private List<Connection> connectionPool = null;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE =  10;

    // Will probably need to change this to allow for different databases to get in. Just include this for now. I think it makes sure you have the postgresql dependency prior.
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public BasicConnectionPool()
    {
        super();

    }

    // I don't want them to be able to access this because the create method will deal with setting everything up
    private BasicConnectionPool(List<Connection> connectionPool) {

        this.connectionPool = connectionPool;
    }


    // This is VERY temporary. Just to check that I can get a connection. Will accept no parameters later on and just have the other no-args constructor deal with setting up the
    // url, username, and password when we first create that object. The main goal is to make it so there is no passing back and forth between classes the url, username, and password
    public static BasicConnectionPool create() throws SQLException {
         Properties props = new Properties();
        try {
            props.load(new FileReader("src/main/resources/application.properties"));

            List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.add(createConnection(props));
            }
            return new BasicConnectionPool(pool);
        }catch(IOException io) {
            io.printStackTrace();
            throw new InvalidConnectionException("Something went wrong while initializing the connection. Remember that you should have your application.properties file in src/main/resources/application.properties");
        }

    }

    public static BasicConnectionPool create(String propertiesPath) throws SQLException {
        Properties props = new Properties();
        try {
            props.load(new FileReader(propertiesPath));

            List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
            for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
                pool.add(createConnection(props));
            }
            return new BasicConnectionPool(pool);
        }catch(IOException io) {
            io.printStackTrace();
            throw new InvalidConnectionException("Something went wrong while initializing the connection. Is your path to your properties file correct?");
        }
    }



    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    @Override
    public boolean destroyAllConnections() {

        try {
            connectionPool = null;
            usedConnections.clear();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    private static Connection createConnection(Properties props) throws SQLException {
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("admin-usr"), props.getProperty("admin-pw"));
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }



}
