package com.tushare.exception;

public class TushareException extends Exception {

    private static final long serialVersionUID = -1822725338748049495L;

    public TushareException() {

        super();

    }

    public TushareException(String string) {

        super(string);

    }

    public TushareException(String string, Throwable throwable) {

        super(string, throwable);

    }

    public TushareException(Throwable throwable) {

        super(throwable);

    }
}
