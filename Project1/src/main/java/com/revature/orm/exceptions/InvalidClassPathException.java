package com.revature.orm.exceptions;
/**
 * An exception class that is thrown when there is an issue with the
 * class path that the user put in
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidClassPathException extends RuntimeException{
    /**
     * A no-args default constructor that passes a generic message
     * */
    public InvalidClassPathException(){super("Invalid class path.");}
    /**
     * A constructor that accepts a message and passes that message
     * @param String message
     * */
    public InvalidClassPathException(String message){super(message);}

}
