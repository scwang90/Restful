// Copyright (c) 2003-2013, LogMeIn, Inc. All rights reserved.
// This is part of Xively4J library, it is under the BSD 3-Clause license.
package com.restful.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.List;

/**
 * Helper class for parsing to and from json for API calls
 * @author s0pau
 */
public class GsonUtil {
	
	/**
	 * Get the list of model objects and create json as expected by the API.
	 * @return json string suitable for Xively API consumption
	 */
	public static  String toJson(Object model) throws JsonProcessingException {
		return JacksonUtil.toJson(model);
	}

	public static <T> List<T> toObjects(String body, Class<T> clazz) throws Exception {
		return JacksonUtil.toObjects(body, clazz);
	}

	public static <T> T toObject(String body,Class<T> clazz) throws IOException {
		return JacksonUtil.toObject(body, clazz);
	}
}
