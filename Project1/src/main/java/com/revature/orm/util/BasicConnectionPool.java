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
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class BasicConnectionPool implements ConnectionPool{


    private List<Connection> connectionPool = null;
    private List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE =  10;

    /**
     *
     *
     * @return
     * */
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     *
     * @return
     * */
    public BasicConnectionPool()
    {
        super();

    }

    /**
     *
     *
     * @return
     * */
    private BasicConnectionPool(List<Connection> connectionPool) {

        this.connectionPool = connectionPool;
    }


    /**
     *
     *
     * @return
     * */
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

    /**
     *
     *
     * @return
     * */
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



    /**
     *
     *
     * @return
     * */
    @Override
    public Connection getConnection() {
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    /**
     *
     *
     * @return
     * */
    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    /**
     *
     *
     * @return
     * */
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

    /**
     *
     *
     * @return
     * */
    private static Connection createConnection(Properties props) throws SQLException {
        return DriverManager.getConnection(props.getProperty("url"), props.getProperty("admin-usr"), props.getProperty("admin-pw"));
    }

    /**
     *
     *
     * @return
     * */
    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }



}
