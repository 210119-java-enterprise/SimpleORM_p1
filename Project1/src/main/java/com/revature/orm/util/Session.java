package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Session implements SessionIF{

    private Connection connection;
    private MetamodelIF metamodelIF;
    private Transaction transaction;

    private Session() {
        super();
    }
    private Session(Connection connection, MetamodelIF metamodelIF){
        this.connection = connection;
        this.metamodelIF = metamodelIF;
        transaction = null;

        // You have the meta model. You just need to check if the primary key and columns are correct. You can do this my getting the id field and getting each individual column. Remember to check the id's type and each column's type and that
        // it matches with the table's types.
        if(!checkForEntityCorrectnessWtihdDatabaseTable()) {
            throw new InvalidEntityException("Your Entity does not match with your Database table or your Database Table does not exist");
        }
    }

    private boolean checkForEntityCorrectnessWtihdDatabaseTable()
    {

            // The only way I can think of checking this is by selecting each column name from the tablename or entity name(if there is no table name) and if they messed up somewhere, then that means something is wrong with the entity
            // and the database table. They are NOT the same
            int numColumns = 1;
            if (metamodelIF.getColumnFieldList() != null) {
                numColumns += metamodelIF.getColumnFieldList().size();
            }
            String numQuestionMarks = new String(new char[numColumns]).replace("\0","?,");
            numQuestionMarks = numQuestionMarks.substring(0,numQuestionMarks.length()-1); // Have to do this in order to get rid of last , in the string
            String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
            String sql = "Select " + numQuestionMarks + "From " + tableName;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, metamodelIF.getIdField().getColumnName());


            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                pstmt.setString(i, columnField1.getColumnName());
            }
            ResultSet resultSet = pstmt.executeQuery();
            
            // Ask Wezley about this. This is so weird. The IDE is not recognizing that ColumnField objects are in the ArrayList while at the same time recognizing that the ArrayList stores ColumnFieldObjects
//            for(Object columnField : metamodelIF.getColumnFieldList()) {
//                    ColumnField columnField1 = (ColumnField)columnField;
//                    pstmt.setString(i, columnField1.getColumnName()
//
//            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }

    }

    public static Session create(Connection connection, MetamodelIF metamodelIF){

        return new Session(connection, metamodelIF);

    }
    // Begin a unit of work and return the assocated Transaction object. If a new underlying transaction is required, begin the transaction. Otherwise continue the new work in the
    // context of the existing underlying transaction. The class of the returned Transaction object is determined by the property hibernate.transaction_factory (Ignore that part)
    @Override
    public Transaction beginTransaction() {
        if(transaction == null) {

            transaction = new JDBCTransaction(this);
        }

        return transaction;

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
metamodelIF.getEntityClass().
    }


}
