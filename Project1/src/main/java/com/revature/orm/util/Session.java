package com.revature.orm.util;

import java.io.Serializable;
import java.sql.Connection;

public class Session implements SessionIF{

    private Connection connection;

    private Session(Connection connection){
        this.connection = connection;

    }

    public static Session create(Connection connection){

        return new Session(connection);

    }
    // Begin a unit of work and return the assocated Transaction object. If a new underlying transaction is required, begin the transaction. Otherwise continue the new work in the
    // context of the existing underlying transaction. The class of the returned Transaction object is determined by the property hibernate.transaction_factory (Ignore that part)
    @Override
    public Transaction beginTransaction() {

    }
    // Get the Transaction instance associated with this session. The class of the returned Transaction object is determined by the property hibernate.transaction_factory
    @Override
    public Transaction getTransaction() {

    }
    // Completely clear the session. Does this mean there is more associated with the session then just the connection?
    @Override
    public void clear() {
        connection = null;
    }
    // A little confused by this one. It says to End the session by releasing the JDBC connection and cleaning up. Why do I return the connection associated with this session then?
    @Override
    public Connection close() {


    }
    // Remove a persistent instance from the datastore. I don't know how well this will work, since I only get an object and I don't know how I am going to delete a specific
    // row from the data or if this is just used to delete an entire table data or the table itself?
    @Override
    public void delete(Object object) {

    }
    // Remove a persistent instance from the datastore. Same issue with delete. I do understand you can name the entity a different name from the table.
    @Override
    public void delete(String entityName, Object object) {

    }
    // Disconnect the Session from the current JDBC connection
    @Override
    public Connection disconnect() {

    }
    // Check if the session is still open
    @Override
    public boolean isOpen() {

    }
    // Persist the given transient instance, first assigning a generated identifier
    @Override
    public Serializable save(Object object) {

    }
    // Persist the given transient instance, first assigning a generated identifier
    @Override
    public Serializable save(String entityName, Object object) {

    }
    // Create a new instance of SQLQuery for the given SQL guery string. For me, I am just going to make a prepared statement from this String
    @Override
    public String createSQLQuery(String queryString) {

    }
    @Override
    public Object get(Class clazz, Serializable id) {

    }
    @Override
    public Object get(String entityName, Serializable id) {

    }

//    //
//    @Override
//    public void create(Object object) {
//
//    }
//    @Override
//    public void read() {
//
//    }
//    @Override
//    public void readAll(String table) {
//
//    }
    @Override
    public void update(Object object) {

    }
}
