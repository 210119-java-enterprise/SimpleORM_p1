package com.revature.orm.util;

public class Model<T> extends Metamodel<T>{

    public Metamodel<T> checkForCorrectness(Class<T> clazz) {
        Metamodel<T> metamodel = new Metamodel<>(clazz);
        if(!checkPrimaryKey(metamodel)) {
            return null;
        }
        //checkPrimaryKey(metamodel);
        //checkForColumns(metamodel);
        //metamodel.getPrimaryKey().getcol().;
        //metamodel.getColumns();
        return metamodel;
    }

    private boolean checkPrimaryKey(Metamodel<T> metamodel) {

        if (metamodel.getPrimaryKey()) {
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
        if (metamodel.getColumns()) {
            return true;
        }
        else {
            return false;
        }
    }




}
