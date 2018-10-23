package com.tushare.core.impl;

import com.tushare.core.client.TushareRequestClient;

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
}
