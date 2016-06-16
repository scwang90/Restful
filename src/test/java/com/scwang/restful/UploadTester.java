package com.scwang.restful;

import com.restful.api.HttpMethod;
import com.restful.api.RequestHandler;
import com.restful.api.Response;
import com.restful.config.Config;
import com.restful.http.MultiRequestHandler;
import org.junit.Test;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

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

        this.message = "msg";
        this.status = "success";
        this.status_ok = "true";
        this.result = "data";

        this.message = "ErrorMsg";
        this.status = "ResultCode";
        this.status_ok = "0";
        this.result = "Data";

        this.socketTimeout = 200000;
        this.connectionTimeout = 200000;
        this.ip = "192.168.1.207";
        this.port = "8090";
        this.charset = "utf-8";
        this.version = "api/android.ashx";
        this.requestMediaType = AcceptedMediaType.form;
        this.responseMediaType = AcceptedMediaType.json;
    }};

    protected RequestHandler handler = MultiRequestHandler.getInstance(config);

    @Test
    public void test() {
        System.out.println(new File("12").getAbsolutePath());
    }

    @Test
    public void upload() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        Response response = null;
        handler.enableCookie(true);
        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN","username=18200000000&password=000000");
        System.out.println(response.getBody());
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("TaskNo", "20160411");
        param.put("TestNo", "1");
        param.put("ItemCode", "25");
        param.put("DevieMakerCode", "DM20160001");
        param.put("DevieNo", "DevieNo");
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Signature", "");
        params.put("Param", param);
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("params", JacksonUtil.toJson(params));
        headers.put("Token", "YX00000087.4FFCFCE85D");
        response = handler.doUpload("?$c=Upload&$m=UploadFile", headers, null, "target/1.PRT");
        System.out.println(response.getBody());
    }

}
