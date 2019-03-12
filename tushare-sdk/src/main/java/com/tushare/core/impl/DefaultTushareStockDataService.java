package com.tushare.core.impl;

import com.alibaba.fastjson.JSONArray;
import com.tushare.bean.ApiRequest;
import com.tushare.bean.ApiResponse;
import com.tushare.constant.TushareApiName;
import com.tushare.constant.finance.CompType;
import com.tushare.constant.finance.DividendFields;
import com.tushare.constant.finance.IncomeFields;
import com.tushare.constant.finance.ReportType;
import com.tushare.constant.market.*;
import com.tushare.constant.reference.ConceptFields;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
import com.tushare.constant.stock.hsconst.HsType;
import com.tushare.constant.stock.namechange.NameChangeFields;
import com.tushare.core.api.TushareStockDataService;
import com.tushare.exception.TushareException;
import com.tushare.constant.TushareConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTushareStockDataService extends AbstractTushareDataService implements TushareStockDataService {

    public DefaultTushareStockDataService(String token){
        this.token = token;
        this.url = TushareConstants.API_URL;
    }

    public DefaultTushareStockDataService(String token, String url ){
        this.token = token;
        this.url = url;
    }

    @Override
    public ApiResponse stockBasic(IsHS isHS, ListStatus listStatus, ExchangeId exchangeId) throws TushareException {
        return stockBasic(isHS, listStatus, exchangeId, null);
    }

    @Override
    public ApiResponse stockBasic(IsHS isHS, ListStatus listStatus, ExchangeId exchangeId, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();
        if(isHS != null){
            params.put(IsHS.keyName, isHS.getValue());
        }

        if(listStatus != null){
            params.put(ListStatus.keyName, listStatus.getValue());
        }

        if(exchangeId != null){
            params.put(ExchangeId.keyName, exchangeId.getValue());
        }

        ApiResponse apiResponse = query(TushareApiName.STOCK_BASIC, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen) throws TushareException {
        return tradeCalender(exchangeId, startDate, endDate, isOpen, null);
    }

    @Override
    public ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();
        if(exchangeId != null){
            params.put(ExchangeId.keyName, exchangeId.getValue());
        }

        if(startDate != null){
            params.put("start_date", new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put("end_date", new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        if(isOpen != null){
            params.put("is_open", isOpen ? "1" : "0");
        }

        ApiResponse apiResponse = query(TushareApiName.TRADE_CALENDER, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse stockCompany() throws TushareException{
        return stockCompany(null);
    }

    @Override
    public ApiResponse stockCompany(List<String> fields) throws TushareException{

        ApiResponse apiResponse = query(TushareApiName.STOCK_COMPANY, null, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse nameChange(String tsCode, Date startDate, Date endDate) throws TushareException {
        return nameChange(tsCode, startDate, endDate, null);
    }

    @Override
    public ApiResponse nameChange(String tsCode, Date startDate, Date endDate, List<String> fields) throws TushareException {

        Map<String, String> params = new HashMap<>();
        if(tsCode != null){
            params.put(NameChangeFields.TS_CODE, tsCode);
        }

        if(startDate != null){
            params.put(NameChangeFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put(NameChangeFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }


        ApiResponse apiResponse = query(TushareApiName.NAME_CHANGE, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse hsConst(HsType hsType, Boolean isNew) throws TushareException {
        return hsConst(hsType, isNew, null);
    }

    /**
     * {@inheritDoc}
     * @param hsType
     * @param isNew
     * @param fields
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse hsConst(HsType hsType, Boolean isNew, List<String> fields) throws TushareException {ApiRequest apiRequest = new ApiRequest();

        Map<String, String> params = new HashMap<>();
        if(hsType != null){
            params.put(HsType.keyName, hsType.getValue());
        }

        if(isNew != null){
            params.put("is_new", isNew ? "1" : "0");
        }

        ApiResponse apiResponse = query(TushareApiName.HS_CONST, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse daily(String tsCode, Date tradeDate) throws TushareException {
        return daily(tsCode, tradeDate, (List<String>) null);
    }

    @Override
    public ApiResponse daily(String tsCode, Date tradeDate, List<String> fields) throws TushareException {

        Map<String, String> params = new HashMap<>();
        if(tsCode != null){
            params.put(ProBarFields.TS_CODE, tsCode);
        }

        if(tradeDate != null){
            params.put(ProBarFields.TRADE_DATE, new SimpleDateFormat("yyyyMMdd").format(tradeDate));
        }

        ApiResponse apiResponse = query(TushareApiName.DAILY, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse daily(String tsCode, Date startDate, Date endDate) throws TushareException {
        return daily(tsCode, startDate, endDate, null);
    }

    @Override
    public ApiResponse daily(String tsCode, Date startDate, Date endDate, List<String> fields) throws TushareException {Map<String, String> params = new HashMap<>();
        if(tsCode != null){
            params.put(ProBarFields.TS_CODE, tsCode);
        }

        if(startDate != null){
            params.put(ProBarFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(startDate != null){
            params.put(ProBarFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        ApiResponse apiResponse = query(TushareApiName.DAILY, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse proBar(String tsCode, Date startDate, Date endDate, AssetType assetType, AdjType adjType, FreqType freqType, List<Integer> ma) throws TushareException {
        return proBar(tsCode, startDate, endDate, assetType, adjType, freqType, ma, null);
    }

    @Override
    public ApiResponse proBar(String tsCode, Date startDate, Date endDate, AssetType assetType, AdjType adjType, FreqType freqType, List<Integer> ma, List<String> fields) throws TushareException {



        Map<String, String> params = new HashMap<>();
        if(tsCode != null){
            params.put(NameChangeFields.TS_CODE, tsCode);
        }

        if(startDate != null){
            params.put(NameChangeFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put(NameChangeFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        if(assetType != null){
            params.put(AssetType.keyName, assetType.getValue());
        }

        if(adjType != null){
            params.put(AdjType.keyName, adjType.getValue());
        }

        if(freqType != null){
            params.put(FreqType.keyName, freqType.getValue());
        }

        if(ma != null){
            params.put("ma", JSONArray.toJSONString(ma));
        }


        ApiResponse apiResponse = query(TushareApiName.PRO_BAR, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse adjFactor(String tsCode, Date tradeDate, Date startDate, Date endDate) throws TushareException {
        return adjFactor(tsCode, tradeDate, startDate, endDate, null);
    }

    @Override
    public ApiResponse adjFactor(String tsCode, Date tradeDate, Date startDate, Date endDate, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();

        if(tsCode != null){
            params.put(AdjFactorFields.TS_CODE, tsCode);
        }


        if(tradeDate != null){
            params.put(AdjFactorFields.TRADE_DATE, new SimpleDateFormat("yyyyMMdd").format(tradeDate));
        }

        if(startDate != null){
            params.put(AdjFactorFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put(AdjFactorFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        ApiResponse apiResponse = query(TushareApiName.ADJ_FACTOR, params, fields);

        return apiResponse;
    }

    /**
     * {@inheritDoc}
     * @param tsCode
     * @param suspendDate
     * @param resumeDate
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse suspend(String tsCode, Date suspendDate, Date resumeDate) throws TushareException {
        return suspend(tsCode, suspendDate, resumeDate, null);
    }

    /**
     * {@inheritDoc}
     * @param tsCode
     * @param suspendDate
     * @param resumeDate
     * @param fields
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse suspend(String tsCode, Date suspendDate, Date resumeDate, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();

        if(tsCode != null){
            params.put(SuspendFields.TS_CODE, tsCode);
        }


        if(suspendDate != null){
            params.put(SuspendFields.SUSPEND_DATE, new SimpleDateFormat("yyyyMMdd").format(suspendDate));
        }

        if(resumeDate != null){
            params.put(SuspendFields.RESUME_DATE, new SimpleDateFormat("yyyyMMdd").format(resumeDate));
        }

        ApiResponse apiResponse = query(TushareApiName.SUSPEND, params, fields);

        return apiResponse;
    }

    /**
     * {@inheritDoc}
     * @param tsCode
     * @param tradeDate
     * @param startDate
     * @param endDate
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse dailyBasic(String tsCode, Date tradeDate, Date startDate, Date endDate) throws TushareException {
        return dailyBasic(tsCode, tradeDate, startDate, endDate, null);
    }

    /**
     * {@inheritDoc}
     * @param tsCode
     * @param tradeDate
     * @param startDate
     * @param endDate
     * @param fields
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse dailyBasic(String tsCode, Date tradeDate, Date startDate, Date endDate, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();

        if(tsCode != null){
            params.put(DailyBasicFields.TS_CODE, tsCode);
        }


        if(tradeDate != null){
            params.put(DailyBasicFields.TRADE_DATE, new SimpleDateFormat("yyyyMMdd").format(tradeDate));
        }

        if(startDate != null){
            params.put(DailyBasicFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put(DailyBasicFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        ApiResponse apiResponse = query(TushareApiName.DAILY_BASIC, params, fields);

        return apiResponse;
    }

    /**
     * {@inheritDoc}
     * @param tsCode
     * @param annDate
     * @param recordDate
     * @param exDate
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse dividend(String tsCode, Date annDate, Date recordDate, Date exDate) throws TushareException {
        return dividend(tsCode, annDate, recordDate, exDate, null);
    }


    /**
     * {@inheritDoc}
     * @param tsCode
     * @param annDate
     * @param recordDate
     * @param exDate
     * @param fields
     * @return
     * @throws TushareException
     */
    @Override
    public ApiResponse dividend(String tsCode, Date annDate, Date recordDate, Date exDate, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();

        if(tsCode != null){
            params.put(DividendFields.TS_CODE, tsCode);
        }


        if(annDate != null){
            params.put(DividendFields.ANN_DATE, new SimpleDateFormat("yyyyMMdd").format(annDate));
        }

        if(recordDate != null){
            params.put(DividendFields.RECORD_DATE, new SimpleDateFormat("yyyyMMdd").format(recordDate));
        }

        if(exDate != null){
            params.put(DividendFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(exDate));
        }

        ApiResponse apiResponse = query(TushareApiName.DIVIDEND, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse income(String tsCode, Date annDate, Date startDate, Date endDate, Date period, ReportType reportType, CompType compType) throws TushareException {
        return income(tsCode,annDate,startDate,endDate,period,reportType,compType, null);
    }

    @Override
    public ApiResponse income(String tsCode, Date annDate, Date startDate, Date endDate, Date period, ReportType reportType, CompType compType, List<String> fields) throws TushareException {
        Map<String, String> params = new HashMap<>();

        if(tsCode != null){
            params.put(IncomeFields.TS_CODE, tsCode);
        }


        if(annDate != null){
            params.put(IncomeFields.ANN_DATE, new SimpleDateFormat("yyyyMMdd").format(annDate));
        }

        if(startDate != null){
            params.put(IncomeFields.START_DATE, new SimpleDateFormat("yyyyMMdd").format(startDate));
        }

        if(endDate != null){
            params.put(IncomeFields.END_DATE, new SimpleDateFormat("yyyyMMdd").format(endDate));
        }

        if(period != null){
            params.put(IncomeFields.PERIOD, new SimpleDateFormat("yyyyMMdd").format(period));
        }

        if(reportType != null){
            params.put(ReportType.keyName, reportType.getValue());
        }

        if(compType != null){
            params.put(CompType.keyName, compType.getValue());
        }

        ApiResponse apiResponse = query(TushareApiName.INCOME, params, fields);

        return apiResponse;
    }

    @Override
    public ApiResponse concept() throws TushareException {
        ApiResponse apiResponse = query(TushareApiName.CONCEPT, null, null);
        return apiResponse;
    }

    @Override
    public ApiResponse conceptDetail(String id) throws TushareException {

        Map<String, String> params = new HashMap<>();

        if(id != null){
            params.put(ConceptFields.ID, id);
        }

        ApiResponse apiResponse = query(TushareApiName.CONCEPT_DETAIL,params,null);
        return null;
    }
}
