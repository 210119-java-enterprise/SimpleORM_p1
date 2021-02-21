package com.revature.orm.exceptions;

public class InvalidClassPathException extends RuntimeException{

    public InvalidClassPathException(){super("Invalid class path.");}
    public InvalidClassPathException(String message){super(message);}

}
