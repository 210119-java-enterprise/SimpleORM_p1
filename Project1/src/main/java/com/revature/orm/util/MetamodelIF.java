package com.revature.orm.util;

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
public interface MetamodelIF<T> {
     /**
      *
      *
      * @return
      * */
     Model<T> checkForCorrectness(Class<T> clazz);
     /**
      *
      *
      * @return
      * */
     IdField getIdField();
     /**
      *
      *
      * @return
      * */
     ArrayList<ColumnField> getColumnFieldList();
     /**
      *
      *
      * @return
      * */
     TableClass getTableClass();
     /**
      *
      *
      * @return
      * */
     EntityClass getEntityClass();
     /**
      *
      *
      * @return
      * */
     Constructor<T> getConstructor();
     /**
      *
      *
      * @return
      * */
     ArrayList<Method> getGetterMethods();
     /**
      *
      *
      * @return
      * */
     ArrayList<Method> getSetterMethods();

}
