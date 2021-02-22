package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import javax.xml.transform.Result;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class Session implements SessionIF{

    private Connection connection;
    private ConnectionPool connectionPool;
    private MetamodelIF metamodelIF;
    private LinkedList<Object> entityLinkedList;
    /**
     *
     *
     * @return
     * */
    private Session() {
        super();
    }
    /**
     *
     *
     * @return
     * */
    private Session(ConnectionPool connectionPool, Connection connection, MetamodelIF metamodelIF){
        this.connectionPool = connectionPool;
        this.connection = connection;
        this.metamodelIF = metamodelIF;

        /**
         *
         *
         * @return
         * */
        if(!checkForEntityCorrectnessWtihdDatabaseTable()) {
            throw new InvalidEntityException("Your Entity does not match with your Database table or your Database Table does not exist");
        }
    }
    /**
     *
     *
     * @return
     * */
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
    /**
     *
     *
     * @return
     * */
    public static Session create(ConnectionPool connectionPool, Connection connection, MetamodelIF metamodelIF){

        return new Session(connectionPool, connection, metamodelIF);

    }



    /**
     *
     *
     * @return
     * */
    @Override
    public void close() {
        connectionPool.releaseConnection(connection);
        connection = null;
        metamodelIF = null;
        entityLinkedList = null;

    }
    /**
     *
     *
     * @return
     * */
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
                    if(metamodelIF.getIdField().getType().toString().equals("class java.lang.String") || metamodelIF.getIdField().getType().toString().equals("class java.lang.Character")) {
                        querySQL = querySQL + "'" + method.invoke(object) + "'";
                    }
                    else {
                        querySQL = querySQL + method.invoke(object);
                    }
                    break;
                }
            }

            int rowsAffected = statement.executeUpdate(querySQL);
            if(rowsAffected == 0) {
                throw new RuntimeException("For some reason the data was not deleted from the row");
            }

            Method method = null;
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                method = (Method) metamodelIF.getGetterMethods().get(i);
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
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }

    }
    /**
     *
     *
     * @return
     * */
    @Override
    public void save(Object object) {
        if (object == null) {
            return;
        }

        int numColumns = 1;
        if (metamodelIF.getColumnFieldList() != null) {
            numColumns += metamodelIF.getColumnFieldList().size();
        }
        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String querySQL = "INSERT INTO " + tableName + "(";
        try {
            Statement statement = connection.createStatement();
            querySQL = querySQL + metamodelIF.getIdField().getColumnName();


            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                querySQL = querySQL + "," + columnField1.getColumnName();
            }
            querySQL = querySQL + ") VALUES (";
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getGetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    if(metamodelIF.getIdField().getType().toString().equals("class java.lang.String") || metamodelIF.getIdField().getType().toString().equals("class java.lang.Character")) {
                        querySQL = querySQL + "'" + method.invoke(object) + "'";
                    }
                    else {
                        querySQL = querySQL +  method.invoke(object);
                    }
                    break;
                }
            }

            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField) metamodelIF.getColumnFieldList().get(i - 2);
                for (int j = 0; j < metamodelIF.getGetterMethods().size(); j++) {
                    Method method = (Method) metamodelIF.getGetterMethods().get(j);
                    if (method.getName().toLowerCase(Locale.ROOT).equals("get" + columnField1.getName().toLowerCase(Locale.ROOT))) {
                        if(columnField1.getType().toString().equals("class java.lang.String") || columnField1.getType().toString().equals("class java.lang.Character"))
                        {
                            querySQL = querySQL + ",'" + method.invoke(object) + "'";
                        }
                        else {
                            querySQL = querySQL + "," + method.invoke(object);
                        }

                        break;
                    }
                }
            }
            querySQL = querySQL + ")";

            int rowsInserted = statement.executeUpdate(querySQL);

            if(rowsInserted == 0) {
                throw new RuntimeException("For some reason the data was not saved to the table");
            }
            entityLinkedList.add(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidEntityException("Your Entity does not match your table.");
        }
    }
    /**
     *
     *
     * @return
     * */
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
    /**
     *
     *
     * @return
     * */
    @Override
    public List<Object> getAll() {
        return entityLinkedList;
    }

    /**
     *
     *
     * @return
     * */
    @Override
    public void update(Object object) {

        if (object == null) {
            return;
        }

        int numColumns = 1;
        if (metamodelIF.getColumnFieldList() != null) {
            numColumns += metamodelIF.getColumnFieldList().size();
        }
        String tableName = metamodelIF.getTableClass() != null ? metamodelIF.getTableClass().getTableName() : metamodelIF.getEntityClass().getName();
        String querySQL = "UPDATE " + tableName + " SET ";
        try {

            Statement statement = connection.createStatement();
            querySQL = querySQL + metamodelIF.getIdField().getColumnName();

            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getGetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    if(metamodelIF.getIdField().getType().toString().equals("class java.lang.String") || metamodelIF.getIdField().getType().toString().equals("class java.lang.Character")) {
                        querySQL = querySQL + " = " + "'" + method.invoke(object) + "'";
                    }
                    else
                    {
                        querySQL = querySQL + " = " + method.invoke(object);
                    }
                    break;
                }
            }

            int currentParameterIndex = 3;
            for (int i = 2; i <= numColumns; i++) {
                ColumnField columnField1 = (ColumnField)metamodelIF.getColumnFieldList().get(i-2);
                querySQL = querySQL + ", " + columnField1.getColumnName();
                currentParameterIndex+=1;
                    for (int j = 0; j < metamodelIF.getGetterMethods().size(); j++) {
                        Method method = (Method) metamodelIF.getGetterMethods().get(j);
                        if (method.getName().toLowerCase(Locale.ROOT).equals("get" + columnField1.getName().toLowerCase(Locale.ROOT))) {
                            if(columnField1.getType().toString().equals("class java.lang.String") || columnField1.getType().toString().equals("class java.lang.Character")) {
                                querySQL = querySQL + " = " + "'" + method.invoke(object) + "'";
                            }
                            else
                            {
                                querySQL = querySQL + " = " + method.invoke(object);
                            }
                            currentParameterIndex+=1;
                            break;
                        }
                    }
            }

            querySQL = querySQL + " WHERE " + metamodelIF.getIdField().getColumnName();
            currentParameterIndex+=1;
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                Method method = (Method) metamodelIF.getGetterMethods().get(i);
                if (method.getName().toLowerCase(Locale.ROOT).equals("get" + metamodelIF.getIdField().getName().toLowerCase(Locale.ROOT))) {
                    if(metamodelIF.getIdField().getType().toString().equals("class java.lang.String") || metamodelIF.getIdField().getType().toString().equals("class java.lang.Character")) {
                        querySQL = querySQL + " = " + "'" + method.invoke(object) + "'";
                    }
                    else
                    {
                        querySQL = querySQL + " = " + method.invoke(object);
                    }
                    break;
                }
            }



            int rowsUpdated = statement.executeUpdate(querySQL);
            //int rowsUpdated = pstmt.executeUpdate();
            if(rowsUpdated == 0) {
                throw new RuntimeException("For some reason the data was not updated to the table");
            }


            Method method = null;
            for(int i = 0; i < metamodelIF.getGetterMethods().size(); i++) {
                method = (Method) metamodelIF.getGetterMethods().get(i);
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

    /**
     *
     *
     * @return
     * */
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
