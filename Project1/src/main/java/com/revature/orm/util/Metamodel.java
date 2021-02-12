package com.revature.orm.util;

import com.revature.orm.annotations.Column;
import com.revature.orm.annotations.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Metamodel<T> {


    private Class<T> clazz;

    public static <T> Metamodel<T> of(Class<T> clazz) {
        return new Metamodel<>(clazz);
    }

    public Metamodel(Class<T> clazz) {
        this.clazz = clazz;
    }

    public List<TableClass> getTables() {
        List<TableClass> tableClassList = new ArrayList<>();
        Class[] clazzes =
    }

    public IdField getPrimaryKey() {

        Field[] fields = clazz.getDeclaredFields();
        for(Field field: fields) {
            Id primaryKey = field.getAnnotation(Id.class);
            if (primaryKey != null) {
                return new IdField(field);
            }
        }

        throw new RuntimeException("Did not find a field annotated with @Id in: " + clazz.getName());
    }

    public List<ColumnField> getColumns() {
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

        return columnFieldList;
    }
}
