package com.analysis.core.api;

import com.tushare.exception.TushareException;
import joinery.DataFrame;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TushareDataFrameService {

    DataFrame queryShibor(Date startDate, Date endDate, List<String> fields) throws ParseException, TushareException;
}
