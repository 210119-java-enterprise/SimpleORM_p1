package com.revature.orm.exceptions;
/**
 * An exception class that is thrown when anything that has
 * to do with the Entity is invalid
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidEntityException extends RuntimeException{

    /**
     * A no-args default constructor that passes a generic message
     * */
    public InvalidEntityException(){super("Invalid Entity. There is something wrong with your Entity");}
    /**
     * A constructor that passes a string message
     * @param String message
     * */
    public InvalidEntityException(String message){super(message);}

}
