package com.revature.orm.util;

import com.revature.orm.exceptions.InvalidEntityException;

import java.util.ArrayList;
import java.util.List;

public class Model<T> extends Metamodel<T> implements MetamodelIF<T>{
    private IdField idField = null;
    private ArrayList<ColumnField> columnFieldList = new ArrayList<>();
    private TableClass tableClass = null;
    private EntityClass entityClass = null;



    public Model(Class<T> clazz){

    }
    private Model(IdField idField, ArrayList<ColumnField> columnFieldList)
    {
        this.idField = idField;
        this.columnFieldList = columnFieldList;
    }
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
        //checkPrimaryKey(metamodel);
        //checkForColumns(metamodel);
        //metamodel.getPrimaryKey().getcol().;
        //metamodel.getColumns();
        return new Model<T>(this.idField, this.columnFieldList);
    }

    private void checkForTableName(Metamodel<T> metamodel) {

        tableClass = metamodel.getTableName();
        entityClass = metamodel.getEntityName();

    }

    private boolean checkPrimaryKey( Metamodel<T> metamodel) {

        idField = metamodel.getPrimaryKey();
        if (idField != null) {
            return true;
        }
        else {
            return false;
        }

    }

    private boolean checkPrimaryKeyGetterAndSetter(Metamodel<T> metamodel) {
        if (metamodel.getPrimaryKeyGetterAndSetter()) {
                return true;
        }
        else {
            return false;
        }
    }

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

    public IdField getIdField() {
        return idField;
    }

    public ArrayList<ColumnField> getColumnFieldList() {

        return columnFieldList;
    }

    public TableClass getTableClass() {
        return tableClass;
    }

    public EntityClass getEntityClass() {
        return entityClass;
    }

}
