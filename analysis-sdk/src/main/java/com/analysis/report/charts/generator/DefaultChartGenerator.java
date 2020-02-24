package com.analysis.report.charts.generator;

import com.analysis.core.api.TushareDataFrameService;
import com.analysis.core.impl.TushareDataFrameServiceImpl;
import com.analysis.report.charts.IndexVsBondsChart;
import com.analysis.report.charts.IndexVsShiborChart;
import com.tushare.constant.index.IndexDaily;
import com.tushare.constant.index.IndexDailyBasic;
import com.tushare.constant.interest.IntrestRateFields;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.jfree.chart.JFreeChart;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

public class DefaultChartGenerator {

    private final static String tushare_token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";


    public static JFreeChart generateUqerIndexBasicVsShiborChart(
            String tsCode, String filePath, Date startDate, Date endDate, String indexBasicField,
            String shiborField, Boolean withIndex) throws TushareException, ParseException, IOException {
        TushareDataFrameService tushareDataFrameService = new TushareDataFrameServiceImpl(tushare_token);


        //get index basic data
        List<String> fields = new ArrayList<>();
        fields.add(IndexDailyBasic.TRADE_DATE);
        fields.add(indexBasicField);
        DataFrame indexBasicDf = DataFrame.readCsv(filePath);
        indexBasicDf.convert(String.class, String.class);
        //get shibor data
        DataFrame shiborDf = tushareDataFrameService.queryShibor(startDate, endDate);

        IndexVsShiborChart chart = new IndexVsShiborChart(indexBasicDf, shiborDf);
        List<String> shiborFields = new ArrayList<>();
        //shiborFields.add(IntrestRateFields.ON);
        //shiborFields.add(IntrestRateFields.ONE_WEEK);
        shiborFields.add(shiborField);
        if(withIndex){
            //get index data
            DataFrame indexDf=null;
            if(tsCode.startsWith("000922")||tsCode.startsWith("399922")) {
                indexDf = process000922(startDate, endDate);
            }else{
                indexDf = tushareDataFrameService.queryIndexDaily(tsCode, startDate, endDate);
            }
            return chart.generateChartVsIndexDaily(indexBasicField, shiborFields, indexDf);
        }else{
            return chart.generateChart(indexBasicField, shiborFields);
        }
    }


    public static JFreeChart generateIndexBasicVsShiborChart(
            String tsCode, Date startDate, Date endDate, String indexBasicField,
            String shiborField, Boolean withIndex) throws TushareException, ParseException {
        TushareDataFrameService tushareDataFrameService = new TushareDataFrameServiceImpl(tushare_token);


        //get index basic data
        List<String> fields = new ArrayList<>();
        fields.add(IndexDailyBasic.TRADE_DATE);
        fields.add(indexBasicField);
        DataFrame indexBasicDf = tushareDataFrameService.queryIndexDailybasic(tsCode,
                null,startDate, endDate, fields);
        //get shibor data
        DataFrame shiborDf = tushareDataFrameService.queryShibor(startDate, endDate);

        IndexVsShiborChart chart = new IndexVsShiborChart(indexBasicDf, shiborDf);
        List<String> shiborFields = new ArrayList<>();
        //shiborFields.add(IntrestRateFields.ON);
        //shiborFields.add(IntrestRateFields.ONE_WEEK);
        shiborFields.add(shiborField);
        if(withIndex){
            //get index data
            DataFrame indexDf=tushareDataFrameService.queryIndexDaily(tsCode, startDate, endDate);
            return chart.generateChartVsIndexDaily(indexBasicField, shiborFields, indexDf);
        }else{
            return chart.generateChart(indexBasicField, shiborFields);
        }
    }

    public static JFreeChart generateIndexBasicVsBondChart(
            String tsCode, Date startDate, Date endDate,
            String indexBasicField, String bondFilePath,
            Boolean withIndex) throws TushareException, ParseException, IOException {
        TushareDataFrameService tushareDataFrameService = new TushareDataFrameServiceImpl(tushare_token);


        //get index basic data
        List<String> fields = new ArrayList<>();
        fields.add(IndexDailyBasic.TRADE_DATE);
        fields.add(indexBasicField);
        DataFrame indexBasicDf = tushareDataFrameService.queryIndexDailybasic(tsCode,
                null,startDate, endDate, fields);

        IndexVsBondsChart chart = new IndexVsBondsChart(indexBasicDf, bondFilePath);
        if(withIndex){
            //get index data
            DataFrame indexDf=tushareDataFrameService.queryIndexDaily(tsCode, startDate, endDate);
            return chart.generateChartVsIndexDaily(indexBasicField, indexDf);
        }else{
            return chart.generateChart(indexBasicField);
        }
    }

    private static DataFrame process000922(Date startDate, Date endDate) throws TushareException {
        TushareDataFrameService tushareDataFrameService = new TushareDataFrameServiceImpl(tushare_token);
        DataFrame shDf=tushareDataFrameService.queryIndexDaily("000922.SH", startDate, endDate);
        DataFrame csiDf=tushareDataFrameService.queryIndexDaily("000922.CSI", startDate, endDate);
        Integer length = csiDf.length();
        for(int i = 0 ; i<length ;i++){
            shDf.append(csiDf.row(i));
        }
        /*DataFrame mergedDf = shDf.joinOn(csiDf, DataFrame.JoinType.OUTER, IndexDaily.TRADE_DATE);

        mergedDf = mergedDf.drop(IndexDaily.TRADE_DATE+"_right");
        mergedDf.rename(IndexDaily.TRADE_DATE+"_left",IndexDaily.TRADE_DATE);
        mergedDf = mergedDf.drop(IndexDaily.CLOSE+"_right");
        mergedDf.rename(IndexDaily.CLOSE+"_left",IndexDaily.CLOSE);*/
        return shDf.unique(IndexDaily.TRADE_DATE);
    }
}
