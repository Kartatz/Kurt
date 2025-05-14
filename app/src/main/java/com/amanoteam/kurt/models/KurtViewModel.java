package com.amanoteam.kurt.models;

import androidx.lifecycle.ViewModel;
import android.webkit.WebView;

public class KurtViewModel extends ViewModel {
	
	private WebView webView = null;
	
	public WebView getWebView() {
		return webView;
	}
	
	public void setWebView(final WebView view) {
		webView = view;
	}
	
}
