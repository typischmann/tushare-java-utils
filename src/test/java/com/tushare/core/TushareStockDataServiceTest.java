package com.tushare.core;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
import com.tushare.constant.stock.calender.TradeCalenderFields;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TushareStockDataServiceTest {

    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Test
    public void stockBasicTest() throws TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.stockBasic(IsHS.H, ListStatus.L, ExchangeId.SSE);
        StringBuilder fields = new StringBuilder();
        for(String item : result.getFields()){
            fields.append(item).append("   |   ");
        }
        System.out.println(fields.toString());

        for(String[] items  : result.getItems()) {
            StringBuilder line = new StringBuilder();
            for(String item : items){
                line.append(item).append("  |  ");
            }
            System.out.println(line.toString());
        }

    }

    @Test
    public void tradeCalenderTest() throws TushareException, ParseException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.tradeCalender(ExchangeId.SSE,
                new SimpleDateFormat("yyyyMMdd").parse("20180101"),
                new SimpleDateFormat("yyyyMMdd").parse("20181231"),null, Arrays.asList(TradeCalenderFields.EXCHANGE_ID,
                        TradeCalenderFields.CAL_DATE, TradeCalenderFields.IS_OPEN, TradeCalenderFields.PRETRADE_DATE));
        StringBuilder fields = new StringBuilder();
        for(String item : result.getFields()){
            fields.append(item).append("   |   ");
        }
        System.out.println(fields.toString());

        for(String[] items  : result.getItems()) {
            StringBuilder line = new StringBuilder();
            for(String item : items){
                line.append(item).append("  |  ");
            }
            System.out.println(line.toString());
        }
    }
}
