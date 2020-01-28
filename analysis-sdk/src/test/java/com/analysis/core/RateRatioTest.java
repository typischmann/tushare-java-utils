package com.analysis.core;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.index.IndexDailyBasic;
import com.tushare.constant.interest.IntrestRateFields;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RateRatioTest {

    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Test
    public void shiborVsIndexPeTtmTest() throws ParseException, TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        List<String> fields = new ArrayList<>();
        fields.add(IndexDailyBasic.TRADE_DATE);
        fields.add(IndexDailyBasic.PE);
        fields.add(IndexDailyBasic.PE_TTM);
        fields.add(IndexDailyBasic.PB);
        ApiResponse indexResponse = tushareStockDataService.indexDailybasic("000016.SH",
                null,
                new SimpleDateFormat("yyyyMMdd").parse("20180101"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"),
                fields);

        DataFrame indexDf = indexResponse.getDataFrame();
        //indexDf.convert();


        ApiResponse shiborResponse1 = tushareStockDataService.shibor(new SimpleDateFormat("yyyyMMdd").parse("20090101"),
                new SimpleDateFormat("yyyyMMdd").parse("20121231"));
        ApiResponse shiborResponse2 = tushareStockDataService.shibor(new SimpleDateFormat("yyyyMMdd").parse("20150101"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"));
        shiborResponse1.getItems().addAll(0,shiborResponse2.getItems());
        DataFrame shiborDf = shiborResponse1.getDataFrame();
        //shiborDf.convert();
        shiborDf.rename(IntrestRateFields.DATE, IndexDailyBasic.TRADE_DATE);


        DataFrame joinedDf = indexDf.joinOn(shiborDf, DataFrame.JoinType.INNER,IndexDailyBasic.TRADE_DATE);
        joinedDf = joinedDf.drop(IndexDailyBasic.TRADE_DATE+"_right");
        joinedDf.rename(IndexDailyBasic.TRADE_DATE+"_left",IndexDailyBasic.TRADE_DATE);
        DataFrame oneYearPeTtmDf = joinedDf.retain(IndexDailyBasic.TRADE_DATE, IndexDailyBasic.PE_TTM, IntrestRateFields.ONE_YEAR);
        oneYearPeTtmDf.add("one_year_pe_ttm_ratio", new DataFrame.Function<List<Object>, Double>() {
            @Override
            public Double apply(List<Object> o) {
                Double peTtm = (Double) o.get(1);
                Double oneYear = (Double) o.get(2);
                return peTtm*oneYear/100;
            }
        });
        Double median = (Double)oneYearPeTtmDf.median().get(0,3);
        Double mean = (Double)oneYearPeTtmDf.mean().get(0,3);
        oneYearPeTtmDf.toString();

    }
}
