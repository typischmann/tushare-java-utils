package com.tushare.core.impl;

import com.tushare.bean.ApiRequest;
import com.tushare.bean.ApiResponse;
import com.tushare.constant.TushareApiName;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
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
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiName(TushareApiName.STOCK_BASIC);
        apiRequest.setToken(this.token);
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

        apiRequest.setParams(params);
        if(fields != null){
            apiRequest.setFields(fields);
        }
        ApiResponse apiResponse = tushareRequestClient.send(apiRequest, this.url);

        return apiResponse;
    }

    @Override
    public ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen) throws TushareException {
        return tradeCalender(exchangeId, startDate, endDate, isOpen, null);
    }

    @Override
    public ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen, List<String> fields) throws TushareException {

        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiName(TushareApiName.TRADE_CALENDER);
        apiRequest.setToken(this.token);
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

        apiRequest.setParams(params);
        if(fields != null){
            apiRequest.setFields(fields);
        }
        ApiResponse apiResponse = tushareRequestClient.send(apiRequest, this.url);

        return apiResponse;
    }

    @Override
    public ApiResponse stockCompany() throws TushareException{
        return stockCompany(null);
    }

    @Override
    public ApiResponse stockCompany(List<String> fields) throws TushareException{
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiName(TushareApiName.STOCK_COMPANY);
        apiRequest.setToken(this.token);

        if(fields != null){
            apiRequest.setFields(fields);
        }
        ApiResponse apiResponse = tushareRequestClient.send(apiRequest, this.url);

        return apiResponse;
    }

    @Override
    public ApiResponse nameChange(String tsCode, Date startDate, Date endDate) throws TushareException {
        return nameChange(tsCode, startDate, endDate, null);
    }

    @Override
    public ApiResponse nameChange(String tsCode, Date startDate, Date endDate, List<String> fields) throws TushareException {
        ApiRequest apiRequest = new ApiRequest();
        apiRequest.setApiName(TushareApiName.NAME_CHANGE);
        apiRequest.setToken(this.token);
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


        apiRequest.setParams(params);
        if(fields != null){
            apiRequest.setFields(fields);
        }
        ApiResponse apiResponse = tushareRequestClient.send(apiRequest, this.url);

        return apiResponse;
    }
}
