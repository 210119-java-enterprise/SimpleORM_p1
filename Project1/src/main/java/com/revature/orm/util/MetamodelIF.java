package com.revature.orm.util;

public interface MetamodelIF<T> {

    public Model<T> checkForCorrectness(Class<T> clazz);
}
