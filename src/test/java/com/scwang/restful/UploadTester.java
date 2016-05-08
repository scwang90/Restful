package com.scwang.restful;

import com.scwang.restful.api.HttpMethod;
import com.scwang.restful.config.Config;
import com.scwang.restful.http.MultiRequestHandler;
import org.junit.Test;

/**
 * 上传测试
 * Created by SCWANG on 2016/5/6.
 */
public class UploadTester {
    /**
     * 配置加载
     */
    protected static Config config = new Config() {{
        this.jsonframework = true;
        this.message = "ErrorMsg";
        this.status = "ResultCode";
        this.status_ok = "0";
        this.result = "Data";
        this.message = "msg";
        this.status = "success";
        this.status_ok = "true";
        this.result = "data";
        this.socketTimeout = 20000;
        this.connectionTimeout = 20000;
        this.ip = "192.168.1.207";
        this.port = "8080";
        this.charset = "utf-8";
        this.version = "api/v1";
        this.requestMediaType = AcceptedMediaType.form;
        this.responseMediaType = AcceptedMediaType.json;
    }};

    protected MultiRequestHandler handler = MultiRequestHandler.getInstance(config);

    @Test
    public void upload() throws Exception {
        handler.doRequest(HttpMethod.POST, "");
    }

}
