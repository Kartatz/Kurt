package com.amanoteam.kurt.network;

import java.util.Map;

import com.amanoteam.kurt.network.NetworkOperation;

public final class HTTPResponse implements NetworkOperation {
	
	private int statusCode = 0;
	
	public final void setStatusCode(final int value) {
		this.statusCode = value;
	}
	
	public final int getStatusCode() {
		return this.statusCode;
	}
	
}