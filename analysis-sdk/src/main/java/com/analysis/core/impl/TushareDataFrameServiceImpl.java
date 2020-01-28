package com.analysis.core.impl;

import com.analysis.core.api.TushareDataFrameService;
import com.tushare.bean.ApiResponse;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import joinery.DataFrame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TushareDataFrameServiceImpl extends DefaultTushareStockDataService
        implements TushareDataFrameService {

    public TushareDataFrameServiceImpl(String token) {
        super(token);
    }

    @Override
    public DataFrame queryShibor(Date startDate, Date endDate, List<String> fields) throws ParseException, TushareException {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        Integer startYear = startCalendar.get(Calendar.YEAR);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        Integer endYear = endCalendar.get(Calendar.YEAR);

        Date start = startDate;
        Date end = endDate;
        ApiResponse shiborResponse = null;
        while(startYear+3<endYear){
            startYear+=3;


        }

        ApiResponse shiborResponse1 = this.shibor(new SimpleDateFormat("yyyyMMdd").parse("20090101"),
                new SimpleDateFormat("yyyyMMdd").parse("20141231"));
        ApiResponse shiborResponse2 = this.shibor(new SimpleDateFormat("yyyyMMdd").parse("20150101"),
                new SimpleDateFormat("yyyyMMdd").parse("20200124"));
        shiborResponse1.getItems().addAll(0,shiborResponse2.getItems());
        DataFrame shiborDf = shiborResponse1.getDataFrame();

        return null;
    }
}
