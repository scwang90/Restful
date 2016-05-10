package com.scwang.restful.http;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.scwang.restful.api.ErrorMessage;
import com.scwang.restful.api.HttpMethod;
import com.scwang.restful.api.RequestHandler;
import com.scwang.restful.api.Response;
import com.scwang.restful.exception.HttpException;
import com.scwang.restful.config.Config;
import com.scwang.restful.config.Loader;
import com.scwang.restful.exception.ServerCodeException;
import com.scwang.restful.exception.ServerException;
import com.scwang.restful.util.JacksonUtil;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 多实例请求对象
 */
public class MultiRequestHandler extends RequestHandler {

    private static final String HTTP = "http://";

    protected Config config;

    public MultiRequestHandler() {
        // singleton
        config = new Config();//Loader.load("config.properties");
    }

    public MultiRequestHandler(Config config) {
        this.config = config;
    }

    public MultiRequestHandler(String properties) {
        // singleton
        config = Loader.load(properties);
    }

    public static MultiRequestHandler getInstance() {
        return new MultiRequestHandler();
    }

    public static MultiRequestHandler getInstance(String properties) {
        return new MultiRequestHandler(properties);
    }

    public static MultiRequestHandler getInstance(Config config) {
        return new MultiRequestHandler(config);
    }

    @Override
    public Response doUpload(String path, Map<String, String> headers, Map<String, Object> params , String... files) throws Exception {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "******";

        URL url = new URL(buildUri(path,params));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setUseCaches(false);
        http.setRequestMethod("POST");
        http.setRequestProperty("Cookie", getCookie());
        http.setRequestProperty("Connection", "Keep-Alive");
        http.setRequestProperty("Charset", config.charset);
        http.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                http.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        DataOutputStream dos = new DataOutputStream(http.getOutputStream());

        for (String file : files) {
            String filename = file.substring(file.lastIndexOf("/") + 1);
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"FileName\"; filename=\"" + filename + "\"" + end);
            dos.writeBytes(end);

            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[8192]; // 8k
            for (int count; (count = fis.read(bytes)) != -1; ) {
                dos.write(bytes, 0, count);
            }
            fis.close();
            dos.writeBytes(end);
        }

        dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
        dos.flush();
        dos.close();

        return parser(getResponse(http));
    }

    /**
     * Make the request to Xively API and return the response string
     *
     * @param method  http request methods
     * @param path    restful app path
     * @param body    objects to be parsed as body for api call
     * @param params  key-value of params for api call
     * @return response string
     * @throws HttpException
     */
    public synchronized Response doRequest(HttpMethod method, String path, Map<String, String> headers,Object body, Map<String, Object> params) throws Exception {

        URL url = new URL(buildUri(path,params));
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setUseCaches(false);
        http.setRequestMethod(method.name());
        http.setRequestProperty("Charset", config.charset);
        http.setRequestProperty("Cookie", getCookie());
        http.setReadTimeout(config.socketTimeout);
        http.setConnectTimeout(config.connectionTimeout);
        //http.setRequestProperty("Connection", "Keep-Alive");
        //http.setRequestProperty("Content-TaskType", "multipart/form-data;boundary=" + boundary);

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                http.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        if (HttpMethod.POST.equals(method)) {
            http.setDoOutput(true);
            if (body != null) {
                byte[] entity = getEntity(body).getBytes(config.charset);
                http.setRequestProperty("Content-Type", config.requestMediaType.contentType);
                http.setRequestProperty("Content-Length", String.valueOf(entity.length));
                OutputStream outStream = http.getOutputStream();
                outStream.write(entity);
                outStream.flush();
                outStream.close();
            } else {
                http.setRequestProperty("Content-Type", config.requestMediaType.contentType);
                http.setRequestProperty("Content-Length", String.valueOf(0));
                OutputStream outStream = http.getOutputStream();
                outStream.close();
            }
        }

        return parser(getResponse(http));
    }

    private String buildUri(String path, Map<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        if (!path.startsWith(HTTP)) {
            String host = config.ip;
            builder.append(HTTP);
            builder.append(config.ip);
            if (config.port != null && config.port.length() > 0) {
                host += ":" + config.port;
                builder.append(':');
                builder.append(config.port);
            }
            builder.append('/');
            builder.append(config.version);
            builder.append(path);
        } else {
            builder.append(path);
        }
        boolean hasparam = path.indexOf('?') >= 0,isfirst = true;
        if (params != null && !params.isEmpty()) {
            for (Entry<String, Object> param : params.entrySet()) {
                if (isfirst && !hasparam) {
                    builder.append('?');
                    isfirst = false;
                } else {
                    builder.append('&');
                }
                builder.append(param.getKey());
                builder.append('=');
                builder.append(String.valueOf(param.getValue()));
            }
        }
        return builder.toString();
    }

    private String getEntity(Object body) throws JsonProcessingException {
        if (body instanceof JSONObject || body instanceof String) {
            return body.toString();
        } else if (body != null) {
            return JacksonUtil.toJson(body);
        } else {
            return "";
        }
    }

    private Response getResponse(HttpURLConnection http) throws IOException {
        InputStream is = http.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, config.charset);
        BufferedReader br = new BufferedReader(isr);
        StringBuffer buffer = new StringBuffer();
        for (String line; (line = br.readLine()) != null; ) {
            buffer.append(line);
        }
        is.close();

        Response response = new Response(http.getResponseCode());
        response.setBody(buffer.toString());
        Map<String, String> reheaders = new HashMap<>();
        for (Entry<String, List<String>> header : http.getHeaderFields().entrySet()) {
            if (header.getValue() != null && header.getValue().size() > 0) {
                reheaders.put(header.getKey(), header.getValue().get(0));
            }
        }
        if (cookies != null) {
            updateCookie(reheaders.get("Set-Cookie"));
        }
        response.setHeaders(reheaders);
        return response;
    }

    private Response parser(Response response) {
        int statusCode = response.getStatusCode();
        if (statusCode != config.successcode) {
            throw new HttpException("HTTP " + statusCode, statusCode, response.getBody());
        }
        if (!config.jsonframework || config.status==null || config.status_ok==null
                || config.result == null || config.message == null) {
            return response;
        }
        try {
            JSONObject object = new JSONObject(response.getBody());
            if (config.status_ok.equals("" + object.get(config.status))) {
                if (object.has(config.result)) {
                    response.setBody(object.get(config.result).toString());
                } else {
                    response.setBody("");
                }
            } else {
                String errormessage = object.get(config.message).toString();
                try {
                    ErrorMessage message = JacksonUtil.toObject(errormessage, config.ErrorMessageClass);
                    throw new ServerCodeException(message);
                } catch (ServerCodeException e) {
                    throw e;
                } catch (Throwable e) {
                    throw new ServerException(errormessage);
                }
            }
        } catch (ServerException e) {
            throw e;
        } catch (Throwable e) {
            throw new ServerException(response.getBody(), e);
        }
        return response;
    }
}
