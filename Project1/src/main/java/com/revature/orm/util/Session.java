package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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


    // Completely clear the session. Does this mean there is more associated with the session then just the connection?
    @Override
    public void clear() {
        connectionPool.releaseConnection(connection);
        connection = null;
        metamodelIF = null;
        entityLinkedList = null;

    }
    // A little confused by this one. It says to End the session by releasing the JDBC connection and cleaning up. Why do I return the connection associated with this session then?
    @Override
    public Connection close() {
        connectionPool.releaseConnection(connection);
        return connection;

    }
    // Remove a persistent instance from the datastore. I don't know how well this will work, since I only get an object and I don't know how I am going to delete a specific
    // row from the data or if this is just used to delete an entire table data or the table itself?
    @Override
    public void delete(Object object) {
        if(object == null) {
            return;
        }

        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String sql = "DELETE FROM " + tableName + "WHERE ? = ?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, metamodelIF.getIdField().getColumnName());

            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
                    pstmt.setObject(2, method.invoke(object));
                    break;
                }
            }
            int rowsAffected = pstmt.executeUpdate();
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
        String numQuestionMarks = new String(new char[numColumns]).replace("\0","?,");
        numQuestionMarks = numQuestionMarks.substring(0,numQuestionMarks.length()-1); // Have to do this in order to get rid of last , in the string
        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String sql = "INSERT INTO " + tableName + "(" + numQuestionMarks +") " + "VALUES (" + numQuestionMarks + ")";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, metamodelIF.getIdField().getColumnName());


            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                pstmt.setString(i, columnField1.getColumnName());
            }

            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getSetterMethods().get(i);
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
                    pstmt.setObject(numColumns + 1, method.invoke(object));
                    break;
                }
            }

            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField) metamodelIF.getColumnFieldList().get(i - 2);
                for (int j = 0; j < metamodelIF.getGetterMethods().size(); j++) {
                    Method method = (Method) metamodelIF.getGetterMethods().get(j);
                    if (method.getName().equals("get" + columnField1.getName())) {
                        pstmt.setObject(numColumns + 2 + (i - 2), method.invoke(object));
                        break;
                    }
                }
            }

            int rowsInserted = pstmt.executeUpdate();
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
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
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
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
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
                        if (method.getName().equals("get" + columnField1.getName())) {
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
                if (method.getName().equals("get" + metamodelIF.getIdField().getName())) {
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
                    if (!method.getName().equals("set" + metamodelIF.getIdField().getName())) {
                        for(int j = 0; j< metamodelIF.getColumnFieldList().size(); j++) {
                            ColumnField columnField = (ColumnField) metamodelIF.getColumnFieldList().get(j);
                            if (method.getName().equals("set" + columnField.getName())) {
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
