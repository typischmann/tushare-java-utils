package com.tushare.core.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tushare.bean.ApiRequest;
import com.tushare.bean.ApiResponse;
import com.tushare.exception.TushareException;
import com.tushare.constant.TushareConstants;
import com.tushare.util.WebHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;


public abstract class AbstractApiRequestClient implements ApiRequestClient{

    protected Logger logger = LogManager.getLogger(getClass());

    protected  abstract void validateRequest(ApiRequest request);

    @Override
    public ApiResponse send(ApiRequest request) throws TushareException {
        return send(request, TushareConstants.API_URL);
    }



    @Override
    public ApiResponse send(ApiRequest request, String url) throws TushareException {
        logger.info("Begining to validate request");
        validateRequest(request);
        logger.info("Validation was passed :-)");
        String message = JSONObject.toJSONString(request);
        String result = WebHttpClient.postRequest(message, url, "application/json");
        JSONObject jsonObject = JSONObject.parseObject(result);
        logger.info("Begining to parse response");
        ApiResponse response = new ApiResponse();
        response.setFields(request.getFields());
        response.setCode(jsonObject.getString(TushareConstants.CODE));
        response.setCode(jsonObject.getString(TushareConstants.MSG));

        if(StringUtils.isNotEmpty(jsonObject.getString(TushareConstants.DATA))){
            JSONObject data = jsonObject.getJSONObject(TushareConstants.DATA);
            JSONArray fields = data.getJSONArray(TushareConstants.FIELDS);
            JSONArray items = data.getJSONArray(TushareConstants.ITEMS);
            if(fields != null){
                response.setFields(fields.toJavaList(String.class));
            }

            if(items != null){
                response.setItems(items.toJavaList(String[].class));
            }

        }else{
            response.setItems(null);
        }

        return response;
    }

}
