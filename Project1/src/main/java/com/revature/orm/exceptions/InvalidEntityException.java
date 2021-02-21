package com.revature.orm.exceptions;

public class InvalidEntityException extends RuntimeException{

    public InvalidEntityException(){super("Invalid Entity. There is something wrong with your Entity");}
    public InvalidEntityException(String message){super(message);}

}
