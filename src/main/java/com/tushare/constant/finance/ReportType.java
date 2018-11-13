package com.tushare.constant.finance;

public enum ReportType {
    TYPE_1("1","合并报表,上市公司最新报表（默认）"),
    TYPE_2("2","单季合并,单一季度的合并报表"),
    TYPE_3("3","调整合并报表,本年度公布上年同期的财务报表数据，报告期为上年度"),
    TYPE_4("4", "数据发生变更,将原数据进行保留，即调整前的原数据"),
    TYPE_5("5","调整前合并报表,该公司母公司的财务报表数据"),
    TYPE_6("6","母公司报表,该公司母公司的财务报表数据"),
    TYPE_7("7","母公司单季表,母公司的单季度表"),
    TYPE_8("8","母公司调整单季表,母公司调整后的单季表"),
    TYPE_9("9","母公司调整表,该公司母公司的本年度公布上年同期的财务报表数据"),
    TYPE_10("10","母公司调整前报表,母公司调整之前的原始财务报表数据"),
    TYPE_11("11","调整前合并报表,调整之前合并报表原数据"),
    TYPE_12("12","母公司调整前报表,母公司报表发生变更前保留的原数据");

    private String value;

    private String description;

    public static final String keyName = "report_type";

    private ReportType(String value, String description){
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }
}
