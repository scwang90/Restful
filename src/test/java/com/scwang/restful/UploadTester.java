package com.scwang.restful;

import com.restful.api.HttpMethod;
import com.restful.api.RequestHandler;
import com.restful.api.Response;
import com.restful.config.Config;
import com.restful.http.MultiRequestHandler;
import com.restful.util.JacksonUtil;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
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

//        this.ip = "112.74.67.17";
//        this.port = "10000";

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
    public void uploadDiYinBian() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        MultiRequestHandler.DEBUD  = true;
        Response response = null;
        handler.enableCookie(true);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Signature", "");
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("UserNo","18200000000");
        user.put("Password","000000");
        params.put("Param", user);
        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN",params);
//        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN","username=18200000000&password=000000");
        System.out.println(response.getBody());
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("TaskNo", "177ccc61-ae7f-4e22-bd51-196bb5a79a79");//20160411
        param.put("TestNo", "#20-1");//1
        param.put("ItemCode", "115");//25
        param.put("DevieMakerCode", "DM20160001");//DM20160001
        param.put("DevieNo", "DevieNo");
        params.put("Param", param);
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("params", JacksonUtil.toJson(params));
//        headers.put("Token", "YX00000087.4FFCFCE85D");
        response = handler.doUpload("?$c=Common&$m=UploadFile", headers, null, "target/1.PRT");
        System.out.println(response.getBody());
    }

    @Test
    public void uploadChaoShengBo() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        MultiRequestHandler.DEBUD  = true;
        Response response = null;
        handler.enableCookie(true);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Signature", "");
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("UserNo","18200000000");
        user.put("Password","000000");
        params.put("Param", user);
        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN",params);
        System.out.println(response.getBody());
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("TaskNo", "177ccc61-ae7f-4e22-bd51-196bb5a79a79");//20160411
        param.put("TestNo", "1");//1
        param.put("ItemCode", "116");//25
        param.put("DevieMakerCode", "DM20160001");//DM20160001
        param.put("DevieNo", "DevieNo");
        params.put("Param", param);
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("params", JacksonUtil.toJson(params));
//        headers.put("Token", "YX00000087.4FFCFCE85D");
        response = handler.doUpload("?$c=Common&$m=UploadFile", headers, null, "target/1.CHZ");
        System.out.println(response.getBody());
    }

    @Test
    public void uploadJingZaiHe() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        MultiRequestHandler.DEBUD  = true;
        Response response = null;
        handler.enableCookie(true);
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("Signature", "");
        Map<String, Object> user = new LinkedHashMap<>();
        user.put("UserNo","18200000000");
        user.put("Password","000000");
        params.put("Param", user);
        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN",params);
        System.out.println(response.getBody());
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("TaskNo", "177ccc61-ae7f-4e22-bd51-196bb5a79a79");//20160411
        param.put("TestNo", "1");//1
        param.put("ItemCode", "113");//25
        param.put("DevieMakerCode", "DM20160001");//DM20160001
        param.put("DevieNo", "DevieNo");
        params.put("Param", param);
        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("params", JacksonUtil.toJson(params));
//        headers.put("Token", "YX00000087.4FFCFCE85D");
        response = handler.doUpload("?$c=Common&$m=UploadFile", headers, null, "target/Pile20160719.org");
        System.out.println(response.getBody());
    }

    @Test
    public void testTiem(){
        Date date = new Date(1468390739513l);

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR,2016);
        instance.set(Calendar.MONTH,7);
        instance.set(Calendar.DAY_OF_MONTH,13);
        instance.set(Calendar.HOUR,14);
        instance.set(Calendar.MINUTE,18);
        instance.set(Calendar.SECOND,58);
        instance.set(Calendar.MILLISECOND,713);
        System.out.println(instance.getTime().getTime());
//        instance.setTime(date);
//        System.out.println(date + " : " + instance.get(Calendar.MILLISECOND));
    }

    @Test
    public void testUrl(){
        String[] urls = new String[]{"http://www.baidu.com/","http://www.baidu.com/baike:80/","http://www.baidu.com","www.baidu.com:8080",};
        for (String url : urls) {
            System.out.println(url.replaceAll("http://|/$",""));
        }
    }

}
