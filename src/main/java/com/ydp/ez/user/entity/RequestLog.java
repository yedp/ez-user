package com.ydp.ez.user.entity;

import com.ydp.ez.user.common.util.StringUtils;
import lombok.Data;

import java.util.Date;

@Data
public class RequestLog {
    private Long id;

    private String trackId;

    private String requestIp;

    private String requestUri;

    private String params;

    private String result;

    private Long operatorId;

    private String subSystem;

    private Date requestTime;

    private Date returnTime;

    private Long timeSpend;

    private Date addTime;

    private String tableName;

    public void setTrackId(String trackId) {
        this.trackId = StringUtils.lengthEnsure(trackId, 50);
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = StringUtils.lengthEnsure(requestUri, 80);
    }

    public void setParams(String params) {
        this.params = StringUtils.lengthEnsure(params, 4000);
    }

    public void setResult(String result) {
        this.result = StringUtils.lengthEnsure(result, 4000);
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = StringUtils.lengthEnsure(subSystem, 50);
    }
}