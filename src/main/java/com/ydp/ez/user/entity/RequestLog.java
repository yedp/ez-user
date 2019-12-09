package com.ydp.ez.user.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public class RequestLog {
    private String tableName;

    private long id;

    /**
     * 50.
     */
    private String trackId;
    /**
     * 200
     */
    private String requestUrl;
    /**
     * 2000
     */
    private String requestParams;
    /**
     * 4000
     */
    private String requestResult;
    /**
     * 32
     */
    private String requestUser;
    /**
     * 50
     */
    private String requestId;
    private Date requestTime;
    private Date   returnTime;
    private Date   addTime;
    private String serverType;
    private long   timeSpend;


    /**
     * 获取requestUrl.
     *
     * @param requestUrl 要设置的requestUrl
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = checkLength(requestUrl, 200);
    }
    public void setRequestParams(String requestParams) {
        this.requestParams = checkLength(requestParams, 4000);
    }
    public void setRequestResult(String requestResult) {
        this.requestResult = checkLength(requestResult, 4000);
    }
    public void setRequestUser(String requestUser) {
        this.requestUser = checkLength(requestUser, 32);
    }
    public void setRequestId(String requestId) {
        this.requestId = checkLength(requestId, 50);
    }

    /**
     * 检查长度.
     *
     * @param requestResult 请求结果.
     * @param len           长度.
     * @return 检查结果.
     */
    private String checkLength(String requestResult, int len) {
        if (StringUtils.isBlank(requestResult)) {
            return requestResult;
        }
        if (requestResult.length() <= len) {
            return requestResult;
        } else {
            return requestResult.substring(0, len);
        }
    }
}