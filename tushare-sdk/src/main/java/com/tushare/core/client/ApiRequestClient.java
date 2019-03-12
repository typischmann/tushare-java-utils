package com.tushare.core.client;

import com.tushare.bean.ApiRequest;
import com.tushare.bean.ApiResponse;
import com.tushare.exception.TushareException;

public interface ApiRequestClient<RESPONSE extends ApiResponse, REQUEST extends ApiRequest> {

    RESPONSE send(REQUEST request) throws TushareException;

    RESPONSE send(REQUEST request, String url) throws TushareException;

}
