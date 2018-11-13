package com.tushare.core.api;

import com.tushare.bean.ApiResponse;
import com.tushare.constant.finance.CompType;
import com.tushare.constant.finance.ReportType;
import com.tushare.constant.market.AdjType;
import com.tushare.constant.market.AssetType;
import com.tushare.constant.market.FreqType;
import com.tushare.constant.stock.ExchangeId;
import com.tushare.constant.stock.basic.IsHS;
import com.tushare.constant.stock.basic.ListStatus;
import com.tushare.constant.stock.hsconst.HsType;
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
     * @param tsCode
     * @param startDate
     * @param endDate
     * @return
     * @throws TushareException
     */
    ApiResponse nameChange(String tsCode, Date startDate, Date endDate)throws TushareException;

    /**
     *
     * @param tsCode
     * @param startDate
     * @param endDate
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse nameChange(String tsCode, Date startDate, Date endDate, List<String> fields)throws TushareException;

    /**
     *
     * @param hsType
     * @param isNew
     * @return
     * @throws TushareException
     */
    ApiResponse hsConst(HsType hsType, Boolean isNew)throws TushareException;

    /**
     *
     * @param hsType
     * @param isNew
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse hsConst(HsType hsType, Boolean isNew, List<String> fields)throws TushareException;

    ApiResponse daily(String tsCode, Date tradeDate) throws TushareException;

    ApiResponse daily(String tsCode, Date tradeDate, List<String> fields) throws TushareException;

    ApiResponse daily(String tsCode, Date startDate, Date endDate) throws TushareException;

    ApiResponse daily(String tsCode, Date startDate, Date endDate, List<String> fields) throws TushareException;

    ApiResponse proBar(String tsCode, Date startDate, Date endDate, AssetType assetType, AdjType adjType, FreqType freqType, List<Integer> ma) throws TushareException;

    ApiResponse proBar(String tsCode, Date startDate, Date endDate, AssetType assetType, AdjType adjType, FreqType freqType, List<Integer> ma, List<String> fields) throws TushareException;

    ApiResponse adjFactor(String tsCode, Date tradeDate, Date startDate, Date endDate) throws TushareException;

    ApiResponse adjFactor(String tsCode, Date tradeDate, Date startDate, Date endDate, List<String> fields) throws TushareException;

    ApiResponse suspend(String tsCode, Date suspendDate, Date resumeDate) throws TushareException;

    ApiResponse suspend(String tsCode, Date suspendDate, Date resumeDate, List<String> fields) throws TushareException;

    /**
     *
     * @param tsCode
     * @param tradeDate
     * @param startDate
     * @param endDate
     * @return
     * @throws TushareException
     */
    ApiResponse dailyBasic(String tsCode, Date tradeDate, Date startDate, Date endDate) throws TushareException;

    /**
     *
     * @param tsCode
     * @param tradeDate
     * @param startDate
     * @param endDate
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse dailyBasic(String tsCode, Date tradeDate, Date startDate, Date endDate, List<String> fields) throws TushareException;


    /**
     *
     * @param tsCode
     * @param annDate
     * @param recordDate
     * @param exDate
     * @return
     * @throws TushareException
     */
    ApiResponse dividend(String tsCode, Date annDate, Date recordDate, Date exDate) throws TushareException;


    /**
     *
     * @param tsCode
     * @param annDate
     * @param recordDate
     * @param exDate
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse dividend(String tsCode, Date annDate, Date recordDate, Date exDate, List<String> fields) throws TushareException;

    /**
     *
     * @param tsCode
     * @param annDate
     * @param startDate
     * @param endDate
     * @param period
     * @param reportType
     * @param compType
     * @return
     * @throws TushareException
     */
    ApiResponse income(String tsCode, Date annDate, Date startDate, Date endDate, Date period, ReportType reportType, CompType compType) throws TushareException;

    /**
     *
     * @param tsCode
     * @param annDate
     * @param startDate
     * @param endDate
     * @param period
     * @param reportType
     * @param compType
     * @param fields
     * @return
     * @throws TushareException
     */
    ApiResponse income(String tsCode, Date annDate, Date startDate, Date endDate, Date period, ReportType reportType, CompType compType, List<String> fields) throws TushareException;
}
