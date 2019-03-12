package com.tushare.core.client;

import com.tushare.bean.ApiRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

public class TushareRequestClient extends AbstractApiRequestClient {

    @Override
    protected void validateRequest(ApiRequest request) {
        Assert.assertTrue("方法名不为空", StringUtils.isNotEmpty(request.getApiName()));
        Assert.assertTrue("Token不为空", StringUtils.isNotEmpty(request.getToken()));
    }

}
