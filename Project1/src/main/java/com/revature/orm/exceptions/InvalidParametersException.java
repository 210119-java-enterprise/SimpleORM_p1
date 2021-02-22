package com.revature.orm.exceptions;
/**
 *
 *
 * An exception class that is thrown when there is an issue with
 * the parameters that were passed into the method
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidParametersException extends RuntimeException {

    /**
     * A no-args default constructor that passes a generic message
     * */
    public InvalidParametersException(){super("Value passed to parameter was invalid");}
    /**
     * A constructor that passes a stirng message
     * @param String message
     * */
    public InvalidParametersException(String message){super(message);}
}
