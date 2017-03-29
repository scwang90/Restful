// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.restful.api;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public abstract class RequestHandler {

	private static RequestHandler instance;

	protected Map<String, String> cookies = null;

	public RequestHandler enableCookie(boolean enable) {
		if (enable && cookies == null) {
			this.cookies = new LinkedHashMap<>();
		} else if (!enable && cookies != null) {
			this.cookies = null;
		}
		return this;
	}

	public RequestHandler enableCookie(Map<String, String> cookies) {
		this.cookies = cookies;
		return this;
	}

	public Response doRequest(HttpMethod method, String path) throws Exception {
		return doRequest(method, path, null,null,null);
	}

	public Response doRequest(HttpMethod method, String path, Object body) throws Exception {
		return doRequest(method, path, null, body,null);
	}

	public Response doRequest(HttpMethod method, String path, Map<String, String> headers) throws Exception {
		return doRequest(method, path, headers, null,null);
	}

	public Response doRequest(HttpMethod method, String path, Map<String, String> headers, Object body) throws Exception {
		return doRequest(method, path, headers, body,null);
	}

	public Response doRequest(HttpMethod method, String path, Map<String, String> headers, Map<String, Object> params) throws Exception {
		return doRequest(method, path,headers,null, params);
	}

	public Response doUpload(String path, String... files) throws Exception {
		return doUpload(path, null, null, files);
	}

	public Response doUpload(String path, Map<String, Object> params, String... files) throws Exception {
		return doUpload(path, null, params, files);
	}

	public abstract Response doUpload(String path, Map<String, String> headers, Map<String, Object> params, String... files) throws Exception;

	public abstract Response doUpload(String path, Map<String, String> headers, Map<String, Object> params, String file, long start, long len) throws Exception;

	public abstract Response doRequest(HttpMethod method, String path, Map<String, String> headers, Object body, Map<String, Object> params) throws Exception ;

	protected String getCookie() {
		if (cookies != null && !cookies.isEmpty()) {
			StringBuilder cookie = new StringBuilder();
			for (Map.Entry<String, String> entry : cookies.entrySet()) {
				cookie.append(";");
				cookie.append(entry.getValue());
			}
			return cookie.substring(1);
		} else {
			return "";
		}
	}

	protected boolean updateCookie(String set_cookie) {
		if (cookies != null && set_cookie != null && set_cookie.length() > 0) {
			String[] setcookies = set_cookie.split(";");
			for (String setcookie : setcookies) {
				int index = setcookie.indexOf('=');
				if (index > 0) {
					String key = setcookie.substring(0, index);
					cookies.put(key, setcookie);
				}
			}
			return true;
		}
		return false;
	}
}
