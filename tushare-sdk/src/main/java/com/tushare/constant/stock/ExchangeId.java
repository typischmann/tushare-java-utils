package com.tushare.constant.stock;

public enum ExchangeId {
    SSE("SSE","上交所"),
    SZSE("SZSE","深交所"),
    HKEX("HKEX","港交所");

    private String value;

    private String description;

    public static final String keyName = "exchange_id";

    private ExchangeId(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
