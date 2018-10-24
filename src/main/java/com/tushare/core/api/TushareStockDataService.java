package com.tushare.core.api;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
import com.tushare.exception.TushareException;

import java.util.Date;
import java.util.List;

public interface TushareStockDataService {
    /**
     *
     * @param isHS
     * @param listStatus
     * @param exchangeId
     * @return
     */
    ApiResponse stockBasic(IsHS isHS, ListStatus listStatus, ExchangeId exchangeId) throws TushareException;


    /**
     *
     * @param isHS
     * @param listStatus
     * @param exchangeId
     * @param fields
     * @return
     */
    ApiResponse stockBasic(IsHS isHS, ListStatus listStatus, ExchangeId exchangeId, List<String> fields) throws TushareException;


    /**
     *
     * @param exchangeId
     * @param startDate
     * @param endDate
     * @param isOpen
     * @return
     * @throws TushareException
     */
    ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen) throws TushareException;


    /**
     *
     * @param exchangeId
     * @param startDate
     * @param endDate
     * @param isOpen
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse tradeCalender(ExchangeId exchangeId, Date startDate, Date endDate, Boolean isOpen, List<String> fields) throws TushareException;

    /**
     *
     * @return
     */
    ApiResponse stockCompany()throws TushareException;

    /**
     *
     * @param fields
     * @return
     */
    ApiResponse stockCompany(List<String> fields)throws TushareException;

    /**
     *
     * @return
     */
    ApiResponse nameChange(String tsCode, Date startDate, Date endDate)throws TushareException;

    /**
     *
     * @param fields
     * @return
     */
    ApiResponse nameChange(String tsCode, Date startDate, Date endDate, List<String> fields)throws TushareException;
}
