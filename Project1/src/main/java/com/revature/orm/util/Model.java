package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class Model<T> extends Metamodel<T> implements MetamodelIF<T>{
    private IdField idField = null;
    private ArrayList<ColumnField> columnFieldList = new ArrayList<>();
    private TableClass tableClass = null;
    private EntityClass entityClass = null;
    private Constructor<T> constructor = null;
    private ArrayList<Method> getterMethods = new ArrayList<>();
    private ArrayList<Method> setterMethods = new ArrayList<>();


    /**
     *
     *
     * @return
     * */
    public Model(){
        super();

    }
    /**
     *
     *
     * @return
     * */
    private Model(IdField idField, ArrayList<ColumnField> columnFieldList, TableClass tableClass, EntityClass entityClass, Constructor<T> constructor, ArrayList<Method> getterMethods, ArrayList<Method> setterMethods)
    {
        this.idField = idField;
        this.columnFieldList = columnFieldList;
        this.tableClass = tableClass;
        this.entityClass = entityClass;
        this.constructor = constructor;
        this.getterMethods = getterMethods;
        this.setterMethods = setterMethods;
    }
    /**
     *
     *
     * @return
     * */
    public Model<T> checkForCorrectness(Class<T> clazz) {
        Metamodel<T> metamodel = new Metamodel<>(clazz);
        //Model<T> model = new Model<>(clazz);
        checkForTableName(metamodel);
        if(!checkPrimaryKey(metamodel)) {
            throw new InvalidEntityException("Invalid Entity. You do not have an @Id annotation that denotes your primary key.");
        }
        if (!checkForColumns(metamodel))
        {
            throw new InvalidEntityException("Invalid Entity. You do not have at least one @Column annotation. If your Entity only contains a primary key, add an @NoColumns to your Entity.");
        }
        if(!checkForNoArgsConstructor(metamodel)) {
            throw new InvalidEntityException("Invalid Entity. You do not have a no args constructor in your Entity.");
        }
        if(!checkForGetters(metamodel, idField, columnFieldList)) {
            throw new InvalidEntityException("Invalid Entity. You are missing a Getter for a field that you either annotated with @Id or @Column.");
        }
        if(!checkForSetters(metamodel, idField, columnFieldList)) {
            throw new InvalidEntityException("Invalid Entity. You are missing a Setter for a field that you either annotated with @Id or @Column");
        }

        return new Model<T>(this.idField, this.columnFieldList, this.tableClass, this.entityClass, this.constructor, this.getterMethods, this.setterMethods);
    }
    /**
     *
     *
     * @return
     * */
    private void checkForTableName(Metamodel<T> metamodel) {

        tableClass = metamodel.getTableName();
        entityClass = metamodel.getEntityName();

    }
    /**
     *
     *
     * @return
     * */
    private boolean checkPrimaryKey( Metamodel<T> metamodel) {

        idField = metamodel.getPrimaryKey();
        if (idField != null) {
            return true;
        }
        else {
            return false;
        }

    }

    /**
     *
     *
     * @return
     * */
    private boolean checkForColumns(Metamodel<T> metamodel) {
        columnFieldList = metamodel.getColumns();
        if (columnFieldList.size() != 0 ) {
            return true;
        }
        else {

            if (metamodel.checkForNoColumnAnnotation())
            {
                return true;
            }
            return false;
        }
    }
    /**
     *
     *
     * @return
     * */
    private boolean checkForNoArgsConstructor(Metamodel<T> metamodel) {
        constructor = metamodel.getNoArgsConstructor();
        if(constructor != null) {
            return true;
        }
        return false;
    }
    /**
     *
     *
     * @return
     * */
    private boolean checkForSetters(Metamodel<T> metamodel, IdField idField, ArrayList<ColumnField> columnFieldList) {
        setterMethods = metamodel.getSetters(idField, columnFieldList);
        if(setterMethods.size() != 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     *
     *
     * @return
     * */
    private boolean checkForGetters(Metamodel<T> metamodel, IdField idField, ArrayList<ColumnField> columnFieldList) {
        getterMethods = metamodel.getGetters(idField, columnFieldList);
        if(getterMethods.size() != 0) {
            return true;
        }
        else {
            return false;
        }
    }
    /**
     *
     *
     * @return
     * */
    public IdField getIdField() {
        return idField;
    }
    /**
     *
     *
     * @return
     * */
    public ArrayList<ColumnField> getColumnFieldList() {

        return columnFieldList;
    }
    /**
     *
     *
     * @return
     * */
    public TableClass getTableClass() {
        return tableClass;
    }
    /**
     *
     *
     * @return
     * */
    public EntityClass getEntityClass() {
        return entityClass;
    }
    /**
     *
     *
     * @return
     * */
    public Constructor<T> getConstructor() {
        return constructor;
    }
    /**
     *
     *
     * @return
     * */
    public ArrayList<Method> getGetterMethods() {
        return getterMethods;
    }
    /**
     *
     *
     * @return
     * */
    public ArrayList<Method> getSetterMethods() {
        return setterMethods;
    }

}
