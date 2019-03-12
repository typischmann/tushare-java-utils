package com.tushare.constant.stock.basic;

public enum  ListStatus {

    L("L","上市"),
    D("D","退市"),
    P("P","暂停上市");

    private String value;

    private String description;

    public static final String keyName = "list_status";

    private ListStatus(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
