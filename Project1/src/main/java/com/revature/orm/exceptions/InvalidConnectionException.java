package com.revature.orm.exceptions;

public class InvalidConnectionException extends RuntimeException {

    public InvalidConnectionException(){super("Connection to database failed");}
    public InvalidConnectionException(String message){super(message);}
}
