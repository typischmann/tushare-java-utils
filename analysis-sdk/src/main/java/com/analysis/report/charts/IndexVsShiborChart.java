package com.analysis.report.charts;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.index.IndexDaily;
import com.tushare.constant.index.IndexDailyBasic;
import com.tushare.constant.interest.IntrestRateFields;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class IndexVsShiborChart {

    private DataFrame joinedDf;



    public IndexVsShiborChart(DataFrame indexDailyBasicDf, DataFrame shiborDf){
        shiborDf.rename(IntrestRateFields.DATE, IndexDailyBasic.TRADE_DATE);
        this.joinedDf = indexDailyBasicDf.joinOn(shiborDf, DataFrame.JoinType.INNER,IndexDailyBasic.TRADE_DATE);
        this.joinedDf = this.joinedDf.drop(IndexDailyBasic.TRADE_DATE+"_right");
        this.joinedDf.rename(IndexDailyBasic.TRADE_DATE+"_left",IndexDailyBasic.TRADE_DATE);
        this.joinedDf.convert();
    }

    public JFreeChart generateChart(String indexBasicField, List<String> shiborFields) throws ParseException {
        if(StringUtils.isEmpty(indexBasicField)||shiborFields==null || shiborFields.size()==0){
            return null;
        }

        TimeSeriesCollection vsSeriesCollection = new TimeSeriesCollection();
        TimeSeriesCollection shiborSeriesCollection = new TimeSeriesCollection();

        //对比曲线
        for(String shiborField : shiborFields){
            DataFrame retainDf = this.joinedDf.retain(IndexDailyBasic.TRADE_DATE,
                    indexBasicField, shiborField);
            retainDf.add(shiborField+"_"+indexBasicField+"_ratio", new DataFrame.Function<List<Object>, Double>() {
                @Override
                public Double apply(List<Object> o) {
                    BigDecimal peTtm = BigDecimal.valueOf((Double) o.get(1)).setScale(4);
                    BigDecimal shiborRate = BigDecimal.valueOf((Double) o.get(2)).setScale(4);
                    return Double.valueOf(peTtm.multiply(shiborRate)
                            .divide(BigDecimal.valueOf(100L)).toString());
                }
            });
            TimeSeries timeSeries = new TimeSeries(indexBasicField+"-"+shiborField);
            Integer length = retainDf.length();
            for(int i =0 ; i<length; i++){
                String result = String.valueOf(retainDf.get(i,0));
                timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                        (Double) retainDf.get(i, 3));
            }
            vsSeriesCollection.addSeries(timeSeries);

            timeSeries = new TimeSeries(shiborField);
            for(int i =0 ; i<length; i++){
                String result = String.valueOf(retainDf.get(i,0));
                timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                        (Double) retainDf.get(i, 2));
            }
            shiborSeriesCollection.addSeries(timeSeries);
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart("IndexVsShiborChart", "TRADE DATE",
                "Y Value", vsSeriesCollection);

        //设置日期格式为yyyy-MM-dd，顺便规避中文方框问题
        XYPlot plot = chart.getXYPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        //shibor曲线
        NumberAxis shiborAxis = new NumberAxis("Shibor Axis");
        plot.setRangeAxis(1, shiborAxis);
        plot.setDataset(1, shiborSeriesCollection);
        plot.mapDatasetToRangeAxis(1, 1);
        // -- 修改第shibor曲线显示效果
        XYLineAndShapeRenderer shiborRenderer =  new XYLineAndShapeRenderer();
        //设置观点形状是否可见
        shiborRenderer.setDefaultShapesVisible(false);
        shiborRenderer.setDrawOutlines(false);
        //设置鼠标悬停以及日期格式
        shiborRenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                new SimpleDateFormat("yyyy-MM-dd"), NumberFormat.getNumberInstance()));
        plot.setRenderer(1, shiborRenderer);

        //指数指标曲线
        DataFrame retainDf = this.joinedDf.retain(IndexDailyBasic.TRADE_DATE, indexBasicField);
        TimeSeries timeSeries = new TimeSeries(indexBasicField);
        TimeSeriesCollection indexBasicSeriesCollection = new TimeSeriesCollection();
        Integer length = retainDf.length();
        for(int i =0 ; i<length; i++){
            String result = String.valueOf(retainDf.get(i,0));
            timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                    (Double) retainDf.get(i, 1));
        }
        indexBasicSeriesCollection.addSeries(timeSeries);
        NumberAxis indexBasicAxis = new NumberAxis(indexBasicField);
        plot.setRangeAxis(2, indexBasicAxis);
        plot.setDataset(2, indexBasicSeriesCollection);
        plot.mapDatasetToRangeAxis(2, 2);
        // 曲线显示效果
        XYLineAndShapeRenderer indexBasicRenderer =  new XYLineAndShapeRenderer();
        //设置观点形状是否可见
        indexBasicRenderer.setDefaultShapesVisible(false);
        indexBasicRenderer.setDrawOutlines(false);
        //设置鼠标悬停以及日期格式
        indexBasicRenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                new SimpleDateFormat("yyyy-MM-dd"), NumberFormat.getNumberInstance()));
        plot.setRenderer(2, indexBasicRenderer);

        return chart;
    }

    public JFreeChart generateChartVsIndexDaily(String indexField,
                                                List<String> shiborFields,DataFrame indexDailyDf) throws ParseException {
        DataFrame retainDf = indexDailyDf.retain(IndexDaily.TRADE_DATE, IndexDaily.CLOSE);
        Integer length = retainDf.length();
        retainDf.convert();

        TimeSeriesCollection indexSeriesCollection = new TimeSeriesCollection();
        TimeSeries timeSeries = new TimeSeries("Close");
        for(int i =0 ; i<length; i++){
            String result = String.valueOf(retainDf.get(i,0));
            timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                    (Double) retainDf.get(i, 1));
        }
        indexSeriesCollection.addSeries(timeSeries);


        JFreeChart chart = this.generateChart(indexField, shiborFields);
        XYPlot plot = chart.getXYPlot();
        NumberAxis indexAxis = new NumberAxis("Index Axis");
        plot.setRangeAxis(3, indexAxis);
        plot.setDataset(3, indexSeriesCollection);
        plot.mapDatasetToRangeAxis(3, 3);
        // 修改曲线显示效果
        XYLineAndShapeRenderer indexRenderer =  new XYLineAndShapeRenderer();
        //设置观点形状是否可见
        indexRenderer.setDefaultShapesVisible(false);
        indexRenderer.setDrawOutlines(false);
        //设置鼠标悬停以及日期格式
        indexRenderer.setDefaultToolTipGenerator(new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                new SimpleDateFormat("yyyy-MM-dd"), NumberFormat.getNumberInstance()));
        plot.setRenderer(3, indexRenderer);

        return chart;
    }

    public static void main(String[] agrs) throws ParseException, TushareException {
        String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);

        //get index data
        ApiResponse indexResponse=tushareStockDataService.indexDaily("000016.SH",
                new SimpleDateFormat("yyyyMMdd").parse("20090601"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"));
        DataFrame indexDf = indexResponse.getDataFrame();


        //get index basic data
        List<String> fields = new ArrayList<>();
        fields.add(IndexDailyBasic.TRADE_DATE);
        fields.add(IndexDailyBasic.PE);
        fields.add(IndexDailyBasic.PE_TTM);
        fields.add(IndexDailyBasic.PB);
        ApiResponse indexBasicResponse = tushareStockDataService.indexDailybasic("000016.SH",
                null,
                new SimpleDateFormat("yyyyMMdd").parse("20090601"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"),
                fields);
        DataFrame indexBasicDf = indexBasicResponse.getDataFrame();

        //get shibor data
        ApiResponse shiborResponse1 = tushareStockDataService.shibor(new SimpleDateFormat("yyyyMMdd").parse("20090101"),
                new SimpleDateFormat("yyyyMMdd").parse("20141231"));
        ApiResponse shiborResponse2 = tushareStockDataService.shibor(new SimpleDateFormat("yyyyMMdd").parse("20150101"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"));
        shiborResponse1.getItems().addAll(0,shiborResponse2.getItems());
        DataFrame shiborDf = shiborResponse1.getDataFrame();

        IndexVsShiborChart chart = new IndexVsShiborChart(indexBasicDf, shiborDf);
        List<String> shiborFields = new ArrayList<>();
        //shiborFields.add(IntrestRateFields.ON);
        //shiborFields.add(IntrestRateFields.ONE_WEEK);
        shiborFields.add(IntrestRateFields.SIX_MONTH);
        //shiborFields.add(IntrestRateFields.ONE_YEAR);
        //ChartFrame frame = new ChartFrame("上证50指数指标-Shibor利率标轴",
        //        chart.generateChart(IndexDailyBasic.PE_TTM, shiborFields));
        ChartFrame frame = new ChartFrame("上证50指数指标-Shibor利率标轴",
                chart.generateChartVsIndexDaily(IndexDailyBasic.PE_TTM, shiborFields, indexDf));
        frame.pack();
        frame.setVisible(true);
    }
}
