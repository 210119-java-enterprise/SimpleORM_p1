package com.revature.orm.util;

public interface MetamodelIF<T> {

    public Metamodel<T> checkForCorrectness(Class<T> clazz);
}
