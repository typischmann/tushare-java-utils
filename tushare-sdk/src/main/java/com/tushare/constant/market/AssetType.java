package com.tushare.constant.market;

public enum AssetType {

    E("E","股票"),
    I("I","沪深指数"),
    F("F","期货"),
    O("O","期权"),
    C("C","数字货币");

    private String value;

    private String description;

    public static final String keyName = "asset";

    private AssetType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
