package com.amanoteam.kurt.models;

import android.arch.lifecycle.ViewModel;
import android.webkit.WebView;

public class KurtViewModel extends ViewModel {
	
	private WebView webView = null;
	
	public WebView getWebView() {
		return webView;
	}
	
	public WebView setWebView(final WebView view) {
		webView = view;
	}
	
}
