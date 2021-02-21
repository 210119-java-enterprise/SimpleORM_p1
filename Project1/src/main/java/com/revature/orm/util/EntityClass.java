package com.revature.orm.util;

import com.revature.orm.annotations.Entity;

public class EntityClass {

    private Class clazz;

    public EntityClass(Class clazz) {
        if(clazz.getAnnotation(Entity.class) == null) {
            throw new IllegalStateException("Cannot create EntityClass object! Provided class, " + getName() + " is not annotated with @Entity");
        }
        this.clazz = clazz;
    }

    public String getName() {
        return clazz.getName();
    }

    public Class<?> getType() {
        return clazz.getComponentType();
    }

}
