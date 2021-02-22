package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class Session implements SessionIF{

    private Connection connection;
    private ConnectionPool connectionPool;
    private MetamodelIF metamodelIF;
    private LinkedList<Object> entityLinkedList;

    private Session() {
        super();
    }
    private Session(ConnectionPool connectionPool, Connection connection, MetamodelIF metamodelIF){
        this.connectionPool = connectionPool;
        this.connection = connection;
        this.metamodelIF = metamodelIF;

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


            String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
            String querySQL = "SELECT ";
        try {
            Statement statement = connection.createStatement();
            querySQL = querySQL + metamodelIF.getIdField().getColumnName();

            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                querySQL = querySQL + "," + columnField1.getColumnName();
            }
            querySQL = querySQL + " FROM " + tableName;

            ResultSet resultSet = statement.executeQuery(querySQL);

            entityLinkedList = mapResultSet(resultSet);
            return true;
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

    public static Session create(ConnectionPool connectionPool, Connection connection, MetamodelIF metamodelIF){

        return new Session(connectionPool, connection, metamodelIF);

    }



    // A little confused by this one. It says to End the session by releasing the JDBC connection and cleaning up. Why do I return the connection associated with this session then?
    @Override
    public void close() {
        connectionPool.releaseConnection(connection);
        connection = null;
        metamodelIF = null;
        entityLinkedList = null;

    }
    // Remove a persistent instance from the datastore. I don't know how well this will work, since I only get an object and I don't know how I am going to delete a specific
    // row from the data or if this is just used to delete an entire table data or the table itself?
    @Override
    public void delete(Object object) {
        if(object == null) {
            return;
        }

        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String querySQL = "DELETE FROM " + tableName + " WHERE ";

        try {
            Statement statement = connection.createStatement();

            querySQL = querySQL + metamodelIF.getIdField().getColumnName() + " = ";


            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getGetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    querySQL = querySQL + method.invoke(object);
                    break;
                }
            }
            System.out.println(querySQL);
            int rowsAffected = statement.executeUpdate(querySQL);
            if(rowsAffected == 0) {
                throw new RuntimeException("For some reason the data was not deleted from the row");
            }
            entityLinkedList.remove(object);

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }

    }
    // Persist the given transient instance, first assigning a generated identifier
    @Override
    public void save(Object object) {
        if (object == null) {
            return;
        }

        int numColumns = 1;
        if (metamodelIF.getColumnFieldList() != null) {
            numColumns += metamodelIF.getColumnFieldList().size();
        }
        System.out.println(numColumns);
        String numQuestionMarks = new String(new char[numColumns]).replace("\0","?,");
        numQuestionMarks = numQuestionMarks.substring(0,numQuestionMarks.length()-1); // Have to do this in order to get rid of last , in the string
        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String querySQL = "INSERT INTO " + tableName + "(";
        String sql = "INSERT INTO " + tableName + "(" + numQuestionMarks +") " + "VALUES (" + numQuestionMarks + ")";
        try {
            Statement statement = connection.createStatement();
            PreparedStatement pstmt = connection.prepareStatement(sql);
            querySQL = querySQL + metamodelIF.getIdField().getColumnName();
            pstmt.setString(1, metamodelIF.getIdField().getColumnName());


            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                querySQL = querySQL + "," + columnField1.getColumnName();
                pstmt.setString(i, columnField1.getColumnName());
            }
            querySQL = querySQL + ") VALUES (";
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getGetterMethods().get(i);
                System.out.println(method.getName().toLowerCase(Locale.ROOT));
                System.out.println(metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT));
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    System.out.println("I made it!!! ");
                    querySQL = querySQL +  method.invoke(object);
                    pstmt.setObject(numColumns + 1, method.invoke(object));
                    break;
                }
            }

            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField) metamodelIF.getColumnFieldList().get(i - 2);
                for (int j = 0; j < metamodelIF.getGetterMethods().size(); j++) {
                    Method method = (Method) metamodelIF.getGetterMethods().get(j);
                    if (method.getName().toLowerCase(Locale.ROOT).equals("get" + columnField1.getName().toLowerCase(Locale.ROOT))) {
                        System.out.println(columnField1.getType());
                        if(columnField1.getType().toString().equals("class java.lang.String") || columnField1.getType().toString().equals("class java.lang.Character"))
                        {
                            querySQL = querySQL + ",'" + method.invoke(object) + "'";
                        }
                        else {
                            querySQL = querySQL + "," + method.invoke(object);
                        }

                        pstmt.setObject(numColumns + 2 + (i - 2), method.invoke(object));
                        break;
                    }
                }
            }
            querySQL = querySQL + ")";
            System.out.println(querySQL);
            System.out.println(pstmt.toString());
            int rowsInserted = statement.executeUpdate(querySQL);

            //int rowsInserted = pstmt.executeUpdate();
            if(rowsInserted == 0) {
                throw new RuntimeException("For some reason the data was not saved to the table");
            }
            entityLinkedList.add(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }
    }
    @Override
    public Object get(Object id) {
        try {
            Method method = null;
            if(id == null)
            {
                return null;
            }
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    break;
                }
            }

            for (int i = 0; i < entityLinkedList.size(); i++) {
                Object o = entityLinkedList.get(i);
                if(method.invoke(o).equals(id))
                {
                    return o;
                }

            }
            return null;

        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Was not able to invoke the method");
        }
    }

    @Override
    public List<Object> getAll() {
        return entityLinkedList;
    }

    // Update any column that ISN'T the key
    @Override
    public void update(Object object) {

        if (object == null) {
            return;
        }

        int numColumns = 1;
        if (metamodelIF.getColumnFieldList() != null) {
            numColumns += metamodelIF.getColumnFieldList().size();
        }
        String numQuestionMarks = new String(new char[numColumns]).replace("\0","? = ?,");
        numQuestionMarks = numQuestionMarks.substring(0,numQuestionMarks.length()-1); // Have to do this in order to get rid of last , in the string
        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String sql = "UPDATE " + tableName + " SET " + numQuestionMarks +" WHERE ? = ?";
        try {


            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, metamodelIF.getIdField().getColumnName());

            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    pstmt.setObject(2, method.invoke(object));
                    break;
                }
            }

            int currentParameterIndex = 3;
            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                pstmt.setString(currentParameterIndex, columnField1.getColumnName());
                currentParameterIndex+=1;
                    for (int j = 0; j < metamodelIF.getGetterMethods().size(); j++) {
                        Method method = (Method) metamodelIF.getGetterMethods().get(j);
                        if (method.getName().toLowerCase(Locale.ROOT).equals("get" + columnField1.getName().toLowerCase(Locale.ROOT))) {
                            pstmt.setObject(currentParameterIndex, method.invoke(object));
                            currentParameterIndex+=1;
                            break;
                        }
                    }
            }

            pstmt.setString(currentParameterIndex, metamodelIF.getIdField().getColumnName());
            currentParameterIndex+=1;
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    pstmt.setObject(currentParameterIndex, method.invoke(object));
                    break;
                }
            }





            int rowsUpdated = pstmt.executeUpdate();
            if(rowsUpdated == 0) {
                throw new RuntimeException("For some reason the data was not updated to the table");
            }


            Method method = null;
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
                    break;
                }
            }

            Object id = method.invoke(object);
            for (int i = 0; i < entityLinkedList.size(); i++) {
                Object o = entityLinkedList.get(i);
                if(method.invoke(o).equals(id))
                {
                    entityLinkedList.remove(i);
                    entityLinkedList.add(object);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }

    }

    // Method to return a LinkedList of Objects
    private LinkedList<Object> mapResultSet(ResultSet rs) throws SQLException {
        try {
            LinkedList<Object> objects = new LinkedList<>();
            //Class<?> clazz = Class.forName(metamodelIF.getEntityClass().getName());
            Constructor<?> constructor = metamodelIF.getConstructor();
            while(rs.next()) {
                Object object = constructor.newInstance();
                for(int i = 0; i < metamodelIF.getSetterMethods().size(); i++) {
                    Method method = (Method)metamodelIF.getSetterMethods().get(i);
                    if (!method.getName().toLowerCase(Locale.ROOT).equals("set" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                        for(int j = 0; j< metamodelIF.getColumnFieldList().size(); j++) {
                            ColumnField columnField = (ColumnField) metamodelIF.getColumnFieldList().get(j);
                            if (method.getName().toLowerCase(Locale.ROOT).equals("set" + columnField.getName().toLowerCase(Locale.ROOT))) {
                                method.invoke(object, rs.getObject(columnField.getColumnName()));
                                break;
                            }
                        }
                    }
                    else {
                        method.invoke(object, rs.getObject(metamodelIF.getIdField().getColumnName()));
                    }
                }
                objects.add(object);
            }
            return objects;

        }catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Issue creating a new instance of the class");
        }

    }

}
