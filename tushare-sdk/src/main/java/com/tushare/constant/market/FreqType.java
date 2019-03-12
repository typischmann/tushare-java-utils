package com.tushare.constant.market;

public enum FreqType {

    MIN_1("1","1分钟"),
    MIN_5("5","5分钟"),
    MIN_15("15","15分钟"),
    MIN_30("30","30分钟"),
    MIN_60("60","60分钟"),
    DAY("D", "天");


    private String value;

    private String description;

    public static final String keyName = "freq";

    private FreqType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
