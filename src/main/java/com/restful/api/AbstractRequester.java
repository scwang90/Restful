package com.restful.api;

import com.restful.annotation.HttpDelete;
import com.restful.annotation.HttpGet;
import com.restful.annotation.HttpPost;
import com.restful.annotation.HttpPut;
import com.restful.exception.RestfulException;
import com.restful.http.MultiRequestHandler;
import com.restful.util.StackTraceUtil;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HTTP请求
 * Created by SCWANG on 2016/6/12.
 */
public class AbstractRequester {

    public static class HttpRequest {
        public HttpMethod method;
        public String path;

        public HttpRequest(HttpMethod method, String path) {
            this.path = path;
            this.method = method;
        }
    }

    protected static RequestHandler handler = new MultiRequestHandler();


    public static Response doRequest() throws Exception {
        HttpRequest request = getHttpRequest();
        return handler.doRequest(request.method, request.path, null, null, null);
    }

    public static Response doRequestBody(Object body) throws Exception {
        HttpRequest request = getHttpRequest();
        return handler.doRequest(request.method, request.path, null, body, null);
    }

    public static Response doRequestParam(Object... param) throws Exception {
        HttpRequest request = getHttpRequest();
        return handler.doRequest(request.method, request.path, null, null, paramPackage(param));
    }

    public static Response doRequestBodyParam(Object body, Object... param) throws Exception {
        HttpRequest request = getHttpRequest();
        return handler.doRequest(request.method, request.path, null, body, paramPackage(param));
    }

    protected static Map<String, Object> paramPackage(Object... args) {
        Map<String, Object> params = new LinkedHashMap<>();
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length / 2; i++) {
                if (args[2 * i] instanceof String) {
                    Object arg = args[2 * i + 1];
                    params.put((String) args[2 * i], arg);
                }
            }
        }
        return params;
    }

    /**
     * 统一获取API注解
     */
    protected static HttpRequest getHttpRequest() throws Exception {
        return getHttpRequest(2);
    }

    /**
     * 统一获取API注解
     */
    protected static HttpRequest getHttpRequest(int level) throws Exception {
        HttpGet get = StackTraceUtil.getCurrentMethodAnnotation(HttpGet.class, 1 + level);
        HttpPost post = StackTraceUtil.getCurrentMethodAnnotation(HttpPost.class, 1 + level);
        HttpDelete delete = StackTraceUtil.getCurrentMethodClassAnnotation(HttpDelete.class, 1 + level);
        HttpPut put = StackTraceUtil.getCurrentMethodAnnotation(HttpPut.class, 1 + level);

        if (get != null) {
            return new HttpRequest(get.method(),get.value());
        } else if (post != null) {
            return new HttpRequest(post.method(),post.value());
        } else if (delete != null) {
            return new HttpRequest(delete.method(),delete.value());
        } else if (put != null) {
            return new HttpRequest(put.method(), put.value());
        } else {
            throw new RestfulException("访问API没有设置HttpRequest");
        }
    }

}
