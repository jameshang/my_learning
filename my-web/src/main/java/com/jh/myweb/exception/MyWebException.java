package com.jh.myweb.exception;

public class MyWebException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -7517555159801488896L;

    private MyWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public static MyWebException create(String message, Throwable cause) {
        return new MyWebException(message, cause);
    }

}
