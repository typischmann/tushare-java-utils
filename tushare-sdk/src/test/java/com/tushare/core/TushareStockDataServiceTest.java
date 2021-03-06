package com.tushare.core;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.finance.DividendFields;
import com.tushare.constant.market.AssetType;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
import com.tushare.constant.stock.calender.TradeCalenderFields;
import com.tushare.constant.stock.hsconst.HsType;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class TushareStockDataServiceTest {

    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Test
    public void stockBasicTest() throws TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.stockBasic(IsHS.H, ListStatus.L, ExchangeId.SSE);
        DataFrame dataFrame = result.getDataFrame();
        //dataFrame.resetIndex();

        System.out.println("DataFrame begin:");
        StringBuilder fields = new StringBuilder();
        for(String field : (Set<String>)dataFrame.columns()){
            fields.append(field).append("   |   ");
        }
        System.out.println(fields.toString());

        ListIterator<List<String>> listIterator = dataFrame.iterrows();
        while (listIterator.hasNext()){
            StringBuilder line = new StringBuilder();
            List<String> items = listIterator.next();
            for(String item : items){
                line.append(item).append("  |  ");
            }
            System.out.println(line.toString());
        }

        System.out.println("DataFrame end");
        for(Object type : dataFrame.types()){
            System.out.println(type.toString());
        }

        dataFrame.convert();

        System.out.println("after type conversion");
        for(Object type : dataFrame.types()){
            System.out.println(type.toString());
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

    @Test
    public void stockCompanyTest() throws TushareException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.stockCompany();
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
    public void nameChangeTest() throws TushareException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.nameChange("600848.SH",null,null);
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
    public void hsConstTest() throws  TushareException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.hsConst(HsType.SH, null);
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
    public void proBarTest() throws TushareException, ParseException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.proBar("600848.SH",
                new SimpleDateFormat("yyyyMMdd").parse("20180101"),
                new SimpleDateFormat("yyyyMMdd").parse("20181011"),AssetType.I,null, null, Arrays.asList(5, 20, 50));

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
    public void dailyTest() throws ParseException, TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.daily("000001.SZ",
                new SimpleDateFormat("yyyyMMdd").parse("20180701"),
                new SimpleDateFormat("yyyyMMdd").parse("20180718"));

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
    public void dailyTradeDateTest() throws ParseException, TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.daily("000001.SZ",
                new SimpleDateFormat("yyyyMMdd").parse("20180702"),
                (List<String>) null);

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
    public void adjFactorTest() throws TushareException, ParseException {

        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.adjFactor("000001.SZ",null, null, null);

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
    public void suspendTest() throws TushareException, ParseException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.suspend("600848.SH",null, null);

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
    public void dailyBasicTest() throws TushareException, ParseException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.dailyBasic(null, new SimpleDateFormat("yyyyMMdd").parse("20180726"), null, null);

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
    public void dividendTest() throws TushareException, ParseException{

        List<String> fieldArray = Arrays.asList(DividendFields.TS_CODE, DividendFields.DIV_PROC, DividendFields.STK_DIV,
                DividendFields.RECORD_DATE, DividendFields.EX_DATE);

        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse result = tushareStockDataService.dividend("600848.SH", null, null, null, fieldArray);

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
    public void conceptTest() throws TushareException{
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        ApiResponse response = tushareStockDataService.concept();

        StringBuilder fields = new StringBuilder();
        for(String item : response.getFields()){
            fields.append(item).append("   |   ");
        }
        System.out.println(fields.toString());

        for(String[] items  : response.getItems()) {
            StringBuilder line = new StringBuilder();
            for(String item : items){
                line.append(item).append("  |  ");
            }
            System.out.println(line.toString());
        }
    }

}
