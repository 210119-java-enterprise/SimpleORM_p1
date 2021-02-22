package com.revature.orm.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public interface MetamodelIF<T> {

     Model<T> checkForCorrectness(Class<T> clazz);

     IdField getIdField();

     ArrayList<ColumnField> getColumnFieldList();

     TableClass getTableClass();

     EntityClass getEntityClass();

     Constructor<T> getConstructor();

     public ArrayList<Method> getGetterMethods();

     public ArrayList<Method> getSetterMethods();

}
