package com.revature.orm.exceptions;

public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException(){super("Value passed to parameter was invalid");}
    public InvalidParametersException(String message){super(message);}
}
