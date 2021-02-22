package com.revature.orm.util;

import com.revature.orm.annotations.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class Metamodel<T>{


    private Class<T> clazz;

    /**
     *
     *
     * @return
     * */
    protected Metamodel(){
        super();

    }

    /**
     *
     *
     * @return
     * */
    protected Metamodel(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     *
     *
     * @return
     * */
    protected TableClass getTableName() {

        Table tableClass = clazz.getAnnotation(Table.class);
        if(tableClass != null) {
            return new TableClass(clazz);
        }
        return null;
    }
    /**
     *
     *
     * @return
     * */
    protected EntityClass getEntityName() {
        Entity entity = clazz.getAnnotation(Entity.class);
        if(entity != null) {
            return new EntityClass(clazz);
        }
        return null;
    }
    /**
     *
     *
     * @return
     * */
    protected IdField getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                return new IdField(field);
            }
        }

        return null;
    }

    /**
     *
     *
     * @return
     * */
    protected ArrayList<ColumnField> getColumns() {
        ArrayList<ColumnField> columnFieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if(column != null) {
                columnFieldList.add(new ColumnField(field));
            }
        }

        if (columnFieldList.isEmpty()) {

            return null;
        }
        return columnFieldList;
    }
    /**
     *
     *
     * @return
     * */
    protected Constructor<T> getNoArgsConstructor() {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            return constructor;
        }catch (Exception e) {
            return null;
        }

    }
    /**
     *
     *
     * @return
     * */
    protected ArrayList<Method> getSetters(IdField idField, ArrayList<ColumnField> columnFieldArrayList) {
        ArrayList<Method> methodArrayList = new ArrayList<>();
        int numOfSetterStillNeeded = 1 + columnFieldArrayList.size();
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {

                if (!method.getName().toLowerCase(Locale.ROOT).equals("set" + idField.getName().toLowerCase(Locale.ROOT))) {
                        for(ColumnField columnField : columnFieldArrayList) {
                            if (method.getName().toLowerCase(Locale.ROOT).equals("set" + columnField.getName().toLowerCase(Locale.ROOT))) {
                                methodArrayList.add(method);
                                numOfSetterStillNeeded-=1;
                                break;
                            }
                        }
                }
                else {
                        methodArrayList.add(method);
                        numOfSetterStillNeeded-=1;
                }
        }

        if (numOfSetterStillNeeded != 0) {
            ArrayList<Method> emptyArrayList = new ArrayList<>();
            return emptyArrayList;
        }

        return methodArrayList;
    }
    /**
     *
     *
     * @return
     * */
    protected ArrayList<Method> getGetters(IdField idField, ArrayList<ColumnField> columnFieldArrayList) {
        ArrayList<Method> methodArrayList = new ArrayList<>();
        int numOfGettersStillNeeded = 1 + columnFieldArrayList.size();

        Method[] methods = clazz.getDeclaredMethods();
        for(Method method : methods) {




            if (!method.getName().toLowerCase(Locale.ROOT).equals("get" + idField.getName().toLowerCase(Locale.ROOT))) {
                for(ColumnField columnField : columnFieldArrayList) {
                    if (method.getName().toLowerCase(Locale.ROOT).equals("get" + columnField.getName().toLowerCase(Locale.ROOT))) {
                        methodArrayList.add(method);
                        numOfGettersStillNeeded-=1;
                        break;
                    }
                }
            }
            else {
                methodArrayList.add(method);
                numOfGettersStillNeeded-=1;
            }
        }

        if (numOfGettersStillNeeded != 0) {
            ArrayList<Method> emptyArrayList = new ArrayList<>();
            return emptyArrayList;
        }

        return methodArrayList;
    }
    /**
     *
     *
     * @return
     * */
    protected boolean checkForNoColumnAnnotation() {

        if(clazz.isAnnotationPresent(NoColumns.class)) {
            return true;
        }
        return false;
    }
}
