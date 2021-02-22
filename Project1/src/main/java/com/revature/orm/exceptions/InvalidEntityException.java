package com.revature.orm.exceptions;
/**
 *
 *
 *
 * @author Daniel Skwarcha
 * @version %I% %G%
 * */
public class InvalidEntityException extends RuntimeException{

    /**
     *
     *
     * @return
     * */
    public InvalidEntityException(){super("Invalid Entity. There is something wrong with your Entity");}
    /**
     *
     *
     * @return
     * */
    public InvalidEntityException(String message){super(message);}

}
