package com.tushare.core.impl;

import com.tushare.bean.ApiRequest;
import com.tushare.bean.ApiResponse;
import com.tushare.core.client.TushareRequestClient;
import com.tushare.exception.TushareException;

import java.util.List;
import java.util.Map;

public abstract class AbstractTushareDataService {
    protected String token;

    protected String url;

    protected TushareRequestClient tushareRequestClient = new TushareRequestClient();

    public TushareRequestClient getTushareRequestClient() {
        return tushareRequestClient;
    }

    public void setTushareRequestClient(TushareRequestClient tushareRequestClient) {
        this.tushareRequestClient = tushareRequestClient;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ApiResponse query(String apiName, Map<String, String> params, List<String> fields) throws TushareException {
        ApiRequest request = new ApiRequest();
        request.setApiName(apiName);
        request.setParams(params);
        request.setFields(fields);
        request.setToken(this.token);
        return tushareRequestClient.send(request,this.url);
    }
}
