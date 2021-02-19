package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Metamodel<T> implements MetamodelIF<T>{


    private Class<T> clazz;
    private List<ColumnField> columnFieldList;
    private IdField idField;


//    public static <T> Metamodel<T> of(Class<T> clazz) {
//        return new Metamodel<>(clazz);
//    }

    protected Metamodel(){
        super();

    }

    protected Metamodel(Class<T> clazz) {
        this.clazz = clazz;
    }

//    public List<TableClass> getTables() {
//        List<TableClass> tableClassList = new ArrayList<>();
//        Class[] clazzes =
//    }

/*
*
*
* */
    protected boolean getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                this.idField  = new IdField(field);
                return true;
            }
        }

        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }
    protected boolean getPrimaryKeyGetterAndSetter() {

        return true;
    }

    protected boolean getColumns() {
        List<ColumnField> columnFieldList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if(column != null) {
                columnFieldList.add(new ColumnField(field));
            }
        }

        if (columnFieldList.isEmpty()) {
            throw new RuntimeException("No columns found in: " + clazz.getName());
        }
        this.columnFieldList = columnFieldList;
        return true;
    }
}
