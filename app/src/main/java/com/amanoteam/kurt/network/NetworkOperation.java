package com.amanoteam.kurt.network;

import java.util.Map;

public class NetworkOperation {
	
	private String url = null;
	private Map<String, String> headers = null;
	
	public final void setUrl(final String value) {
		this.url = value;
	}
	
	public final void setHeaders(final Map<String, String> value) {
		this.headers = value;
	}
	
	public final String getUrl() {
		return this.url;
	}
	
	public final Map<String, String> getHeaders() {
		return this.headers;
	}
	
	
}