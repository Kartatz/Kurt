package com.amanoteam.kurt.network;

import java.util.Map;

import com.amanoteam.kurt.network.NetworkOperation;

public final class HTTPRequest implements NetworkOperation {
	
	private String method = null;
	
	public final void setMethod(final String value) {
		this.method = value;
	}
	
	public final String getMethod() {
		return this.method;
	}
	
}