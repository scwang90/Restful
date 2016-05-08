// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.scwang.restful.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scwang.restful.exception.ServerException;
import com.scwang.restful.util.JacksonUtil;
import org.json.JSONObject;

/**
 * 返回值
 * @author 树朾
 */
public class Response {
	
	public static final String HEADER_NEW_OBJ_URI = "Location";

	private int statusCode;
	private Map<String, String> headers;
	private String body;

	public Response() {
	}
	
	public Response(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public String getHeaders(String key) {
		return headers.get(key);
	}

	/**
	 * Shallow cope the collection of headers
	 * @param headers
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = new HashMap<>();
		this.headers.putAll(headers);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public <T> T getBodyAsObject(Class<T> returnType) throws IOException {
		return JacksonUtil.toObject(body, returnType);
	}

	public <T> List<T> getBodyAsObjects(Class<T> returnType)
			throws Exception {
		return JacksonUtil.toObjects(body, returnType);
	}

	/**
	 * @return the id of object as indicated in the headers, e.g. for Feed and
	 *         Trigger; null if no such header is found.
	 */
	public String getIdFromResponse() {
		String feedUrlStr = getHeaders(HEADER_NEW_OBJ_URI);
		String[] tokens = feedUrlStr.split("/");
		return tokens[tokens.length - 1];
	}

	public JSONObject getJSONObject() throws Exception {
		try {
			return new JSONObject(body);
		} catch (Throwable e) {
			throw new ServerException("服务器返回格式异常："+body,e);
		}
	}
}
