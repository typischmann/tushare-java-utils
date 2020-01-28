package com.analysis.core;

import com.analysis.core.api.RankingService;
import com.analysis.core.impl.RankingServiceImpl;
import com.tushare.bean.ApiResponse;
import com.tushare.constant.market.DailyBasicFields;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.core.impl.DefaultTushareStockDataService;
import com.tushare.exception.TushareException;
import joinery.DataFrame;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RankingServiceTest {

    public static final String token = "456b1cb6d086872e59ffd4432c0ef6bc31fa1e6450a3d8b89a1d667d";

    @Test
    public void peRankingTest() throws ParseException, TushareException {
        DefaultTushareStockDataService tushareStockDataService = new DefaultTushareStockDataService(token);
        List<String> fields = new ArrayList<>();
        fields.add(DailyBasicFields.TRADE_DATE);
        fields.add(DailyBasicFields.PE);
        fields.add(DailyBasicFields.PE_TTM);
        fields.add(DailyBasicFields.PB);

        ApiResponse result = tushareStockDataService.dailyBasic("000513.SZ",
                null,
                new SimpleDateFormat("yyyyMMdd").parse("20090101"),
                new SimpleDateFormat("yyyyMMdd").parse("20191128"),
                fields
                );
        DataFrame df = result.getDataFrame();
        df.convert();
        Double targetValue = (Double) df.get(0,1);
        RankingService rankingService = new RankingServiceImpl();

        Integer number = rankingService.getGreaterAndEqualThanNumber(df,targetValue, 1);
        System.out.println("The result of getGreaterAndEqualThanNumber is : "+number);

        Double percent = rankingService.getGreaterAndEqualThanPercent(df,targetValue, 1);
        System.out.println("The result of getGreaterAndEqualThanPercent is : "+percent);

    }
}
