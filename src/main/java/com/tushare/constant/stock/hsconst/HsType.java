package com.tushare.constant.stock.hsconst;

public enum HsType {

    SH("SH","沪股通"),
    SZ("SZ","深股通");

    private String value;

    private String description;

    public static final String keyName = "hs_type";

    private HsType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
