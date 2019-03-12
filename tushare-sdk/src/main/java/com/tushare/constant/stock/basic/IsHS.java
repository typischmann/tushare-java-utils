package com.tushare.constant.stock.basic;

public enum IsHS {

    N("N","不是沪深港通标的"),
    H("H","沪股通"),
    S("S","深股通");

    private String value;

    private String description;

    public static final String keyName = "is_hs";

    private IsHS(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
