package com.tushare.constant.finance;

public enum CompType {

    COMMON("1","一般工商业"),
    BANK("2","银行"),
    INSURANCE("3","保险"),
    SECUERITY("4", "证券");

    private String value;

    private String description;

    public static final String keyName = "comp_type";

    private CompType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
