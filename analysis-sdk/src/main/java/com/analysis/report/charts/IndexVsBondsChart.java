package com.analysis.report.charts;

import com.analysis.report.charts.generator.DefaultChartGenerator;
import com.tushare.constant.index.IndexDaily;
import com.tushare.constant.index.IndexDailyBasic;
import com.tushare.constant.interest.IntrestRateFields;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class IndexVsBondsChart {

    private DataFrame joinedDf;



    /**
     *
     * @param indexDailyBasicDf
     * @param filePath die csv Daten wurden von
     *                 https://cn.investing.com/rates-bonds/china-10-year-bond-yield-historical-data
     *                 heruntergeladen und reformattiert.
     * @throws IOException
     */
    public IndexVsBondsChart(DataFrame indexDailyBasicDf, String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        DataFrame bondDf = DataFrame.readCsv(inputStream);
        bondDf.convert(String.class, String.class, String.class, String.class, String.class);
        bondDf.add(IndexDailyBasic.TRADE_DATE, bondDf.col(0));
        bondDf.retain(IndexDailyBasic.TRADE_DATE, IndexDaily.CLOSE);
        this.joinedDf = indexDailyBasicDf.joinOn(bondDf, DataFrame.JoinType.INNER,IndexDailyBasic.TRADE_DATE);
        this.joinedDf = this.joinedDf.drop(IndexDailyBasic.TRADE_DATE+"_right");
        this.joinedDf.rename(IndexDailyBasic.TRADE_DATE+"_left",IndexDailyBasic.TRADE_DATE);
        this.joinedDf.convert();
    }

    public JFreeChart generateChart(String indexBasicField) throws ParseException {
        if(StringUtils.isEmpty(indexBasicField)){
            return null;
        }

        TimeSeriesCollection vsSeriesCollection = new TimeSeriesCollection();
        TimeSeriesCollection shiborSeriesCollection = new TimeSeriesCollection();

        //对比曲线
        DataFrame retainDf = this.joinedDf.retain(IndexDailyBasic.TRADE_DATE,
                indexBasicField, "close");
        retainDf.add("bond_" + indexBasicField + "_ratio", new DataFrame.Function<List<Object>, Double>() {
            @Override
            public Double apply(List<Object> o) {
                BigDecimal peTtm = BigDecimal.valueOf((Double) o.get(1)).setScale(4);
                BigDecimal shiborRate = BigDecimal.valueOf((Double) o.get(2)).setScale(4);
                return Double.valueOf(peTtm.multiply(shiborRate)
                        .divide(BigDecimal.valueOf(100L)).toString());
            }
        });
        TimeSeries timeSeries = new TimeSeries(indexBasicField + "-bond");
        Integer length = retainDf.length();
        for (int i = 0; i < length; i++) {
            String result = String.valueOf(retainDf.get(i, 0));
            timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                    (Double) retainDf.get(i, 3));
        }
        vsSeriesCollection.addSeries(timeSeries);

        timeSeries = new TimeSeries("bond");
        for (int i = 0; i < length; i++) {
            String result = String.valueOf(retainDf.get(i, 0));
            timeSeries.add(new Day(new SimpleDateFormat("yyyyMMdd").parse(result)),
                    (Double) retainDf.get(i, 2));
        }
        shiborSeriesCollection.addSeries(timeSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart("IndexVsBondChart", "TRADE DATE",
                "Y Value", vsSeriesCollection);

        //设置日期格式为yyyy-MM-dd，顺便规避中文方框问题
        XYPlot plot = chart.getXYPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));

        //shibor曲线
        NumberAxis shiborAxis = new NumberAxis("Bond Axis");
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
        retainDf = this.joinedDf.retain(IndexDailyBasic.TRADE_DATE, indexBasicField);
        timeSeries = new TimeSeries(indexBasicField);
        TimeSeriesCollection indexBasicSeriesCollection = new TimeSeriesCollection();
        length = retainDf.length();
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

    public JFreeChart generateChartVsIndexDaily(String indexField,DataFrame indexDailyDf) throws ParseException {
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


        JFreeChart chart = this.generateChart(indexField);
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

    public static void main(String[] agrs) throws ParseException, TushareException, IOException {

        ChartFrame frame = new ChartFrame("上证50指数指标-10年期国债利率标轴",
                DefaultChartGenerator.generateIndexBasicVsBondChart("000300.SH",
                        new SimpleDateFormat("yyyyMMdd").parse("20070101"),
                        new SimpleDateFormat("yyyyMMdd").parse("20200204"),
                        IndexDailyBasic.PE_TTM, "C:\\work\\data\\bond\\10y.csv", true));
        frame.pack();
        frame.setVisible(true);
    }
}
