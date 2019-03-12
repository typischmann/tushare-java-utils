package com.tushare.constant.market;

public enum AdjType {

    QFQ("qfq","前复权"),
    HFQ("hfq","后复权");

    private String value;

    private String description;

    public static final String keyName = "adj";

    private AdjType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
