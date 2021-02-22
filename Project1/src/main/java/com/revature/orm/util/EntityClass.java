package com.revature.orm.util;

import com.revature.orm.annotations.Entity;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class EntityClass {

    private Class clazz;

    /**
     *
     *
     * @return
     * */
    public EntityClass(Class clazz) {
        if(clazz.getAnnotation(Entity.class) == null) {
            throw new IllegalStateException("Cannot create EntityClass object! Provided class, " + getName() + " is not annotated with @Entity");
        }
        this.clazz = clazz;
    }

    /**
     *
     *
     * @return
     * */
    public String getName() {
        return clazz.getName();
    }

    /**
     *
     *
     * @return
     * */
    public Class<?> getType() {
        return clazz.getComponentType();
    }

}
