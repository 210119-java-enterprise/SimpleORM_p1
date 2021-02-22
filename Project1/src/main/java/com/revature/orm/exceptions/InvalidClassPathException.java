package com.revature.orm.exceptions;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidClassPathException extends RuntimeException{
    /**
     *
     *
     * @return
     * */
    public InvalidClassPathException(){super("Invalid class path.");}
    /**
     *
     *
     * @return
     * */
    public InvalidClassPathException(String message){super(message);}

}
