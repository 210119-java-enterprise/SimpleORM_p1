package com.revature.orm.exceptions;
/**
 * An exception class that is thrown when there is an issue
 * with the connection
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidConnectionException extends RuntimeException {
    /**
     *  A default no-args constructor that passes a generic message
     * */
    public InvalidConnectionException(){super("Connection to database failed");}
    /**
     * A constructor that passes a string message
     * @param String message
     * */
    public InvalidConnectionException(String message){super(message);}
}
