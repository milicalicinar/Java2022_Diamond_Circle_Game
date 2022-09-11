package org.unibl.etf.exceptions;

public class ReadConfigException extends Exception{

    public ReadConfigException(String message){
        super(message);
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
