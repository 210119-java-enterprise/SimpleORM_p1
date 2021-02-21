package com.revature.orm.util;

import java.util.ArrayList;
import java.util.List;

public interface MetamodelIF<T> {

     Model<T> checkForCorrectness(Class<T> clazz);

     IdField getIdField();

     ArrayList<ColumnField> getColumnFieldList();

     TableClass getTableClass();

     EntityClass getEntityClass();
}
