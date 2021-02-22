package com.revature.orm.exceptions;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidParametersException extends RuntimeException {

    /**
     *
     *
     * @return
     * */
    public InvalidParametersException(){super("Value passed to parameter was invalid");}
    /**
     *
     *
     * @return
     * */
    public InvalidParametersException(String message){super(message);}
}
