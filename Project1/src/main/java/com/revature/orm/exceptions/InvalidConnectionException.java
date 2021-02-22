package com.revature.orm.exceptions;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidConnectionException extends RuntimeException {
    /**
     *
     *
     * @return
     * */
    public InvalidConnectionException(){super("Connection to database failed");}
    /**
     *
     *
     * @return
     * */
    public InvalidConnectionException(String message){super(message);}
}
