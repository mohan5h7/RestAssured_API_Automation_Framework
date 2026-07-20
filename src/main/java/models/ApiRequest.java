package models;

import enums.HttpMethod;

import java.util.HashMap;
import java.util.Map;

public class ApiRequest {

	private HttpMethod method;

	private String baseUrl;

	private String endpoint;

	private Map<String, String> headers = new HashMap<>();

	private Map<String, String> cookies = new HashMap<>();

	private Map<String, Object> queryParams = new HashMap<>();

	private Map<String, Object> pathParams = new HashMap<>();

	private Map<String, Object> formParams = new HashMap<>();

	private Object body;

	public ApiRequest() {

	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {

		if (headers != null) {
			this.headers = headers;
		}

	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {

		if (cookies != null) {
			this.cookies = cookies;
		}

	}

	public Map<String, Object> getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {

		if (queryParams != null) {
			this.queryParams = queryParams;
		}

	}

	public Map<String, Object> getPathParams() {
		return pathParams;
	}

	public void setPathParams(Map<String, Object> pathParams) {

		if (pathParams != null) {
			this.pathParams = pathParams;
		}

	}

	public Map<String, Object> getFormParams() {
		return formParams;
	}

	public void setFormParams(Map<String, Object> formParams) {

		if (formParams != null) {
			this.formParams = formParams;
		}

	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "ApiRequest{" + "method=" + method + ", baseUrl='" + baseUrl + '\'' + ", endpoint='" + endpoint + '\''
				+ ", headers=" + headers + ", cookies=" + cookies + ", queryParams=" + queryParams + ", pathParams="
				+ pathParams + ", formParams=" + formParams + ", body=" + body + '}';
	}

}