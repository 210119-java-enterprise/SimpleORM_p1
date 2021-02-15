package com.revature.orm.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BasicConnectionPool implements ConnectionPool{

    private String url;
    private String user;
    private String password;
    private List<Connection> connectionPool = null;
    private List<Connection> usedConnections = new ArrayList<>();
    private Properties props = new Properties();
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
//        try {
//            props.load(new FileReader("src/main/resources/application.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    // I don't want them to be able to access this because the create method will deal with setting everything up
    private BasicConnectionPool(String url, String user, String password, List<Connection> connectionPool) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.connectionPool = connectionPool;
    }


    // This is VERY temporary. Just to check that I can get a connection. Will accept no parameters later on and just have the other no-args constructor deal with setting up the
    // url, username, and password when we first create that object. The main goal is to make it so there is no passing back and forth between classes the url, username, and password
    public static BasicConnectionPool create(String url, String user, String password) throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, pool);
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
            url = null;
            user = null;
            password = null;
            connectionPool = null;
            usedConnections.clear();
            props.clear();
            return true;
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }



}
