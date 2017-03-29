package com.scwang.restful;

import com.restful.api.HttpMethod;
import com.restful.api.RequestHandler;
import com.restful.api.Response;
import com.restful.config.Config;
import com.restful.http.MultiRequestHandler;
import com.restful.util.JacksonUtil;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.junit.Test;
import sun.nio.cs.ext.GBK;
import sun.security.util.BigInt;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by SCWANG on 2017/3/22.
 */
public class PoetryTester {
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
    public void test() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        MultiRequestHandler.DEBUD  = true;
        Response response = null;
        handler.enableCookie(true);
        Map<String, Object> params = new LinkedHashMap<>();

        String key = "唯爱紫夕";
        key = getUrlUnicode(key);
        StringBuffer buffer = new StringBuffer("http://cts.388g.com/fasong.php");
        buffer.append("?num=").append(5);
        buffer.append("&type=").append(1);
        buffer.append("&yayuntype=").append(1);
        buffer.append("&w=").append(key);
        response = handler.doRequest(HttpMethod.GET, buffer.toString(),params);
//        response = handler.doRequest(HttpMethod.POST, "?$c=Common&$m=UserLogin$OPEN","username=18200000000&password=000000");
        System.out.println(response.getOrgbody());
    }


    private String getUrlUnicode(String key) {
        byte[] bytes = key.getBytes(Charset.forName("Unicode"));
        char[] hexes = "0123456789ABCDEF".toCharArray();
        int index = (bytes.length == key.length() / 2) ? 0 : 2;

//		key = new BigInteger(1,bytes).toString(16).substring(4).toUpperCase();
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (int i = (bytes.length == key.length() / 2) ? 0 : 2; i < bytes.length ; i+=2) {
            builder.append("%u");
            builder.append(hexes[(bytes[i] >> 4) & 0xF]);
            builder.append(hexes[(bytes[i]) & 0xF]);
            builder.append(hexes[(bytes[i+1] >> 4) & 0xF]);
            builder.append(hexes[(bytes[i+1]) & 0xF]);
        }
//		for (int i = 0,index = 0; i < builder.length(); i++) {
//			if (index++ % 4 == 0) {
//				builder.insert(i, "%u");
//				i += 2;
//			}
//		}//%u4E3A%u4F60%u5199%u8BD7
        return builder.toString();
    }

    @Test
    public void testCookies() throws Exception {
        //http://192.168.1.207:8090/api/android.ashx?$c=User&$m=Login$OPEN
        MultiRequestHandler.DEBUD  = true;
        Response response = null;
        handler.enableCookie(true);
        Map<String, Object> params = new LinkedHashMap<>();
//        StringBuffer buffer = new StringBuffer("http://cts.388g.com/fasong.php?num=5&type=1&yayuntype=1&w=%25u2F55%25u3172%25u2B7D%25u1559");
//        StringBuffer buffer = new StringBuffer("http://cts.388g.com/fasong.php?num=5&type=1&yayuntype=1&w=%u552F%u7231%u7D2B%u5915");
          StringBuffer buffer = new StringBuffer("http://cts.388g.com/fasong.php?num=5&type=1&yayuntype=1&w=%u2F55%u3172%u2B7D%u1559");

        response = handler.doRequest(HttpMethod.GET, buffer.toString(),params);
        System.out.println(response.getOrgbody());
    }

    @Test
    public void testOkhttp() throws IOException {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url("http://cts.388g.com/fasong.php?num=5&type=1&yayuntype=1&w=%u552F%u7231%u7D2B%u5915")
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);
        okhttp3.Response response = call.execute();
        System.out.println(response.body().string());
    }

    @Test
    public void testQiNiu() throws Exception {

        MultiRequestHandler.DEBUD  = true;
        Map<String, Object> params = new LinkedHashMap<>();
        String url = "http://7xsws2.com1.z0.glb.clouddn.com/backs%5Clist";
        Response response = handler.doRequest(HttpMethod.GET, url, params);
        System.out.println(response.getOrgbody());

    }
}
