package com.amanoteam.kurt.models;

import androidx.lifecycle.ViewModel;
import android.webkit.WebView;
import com.amanoteam.kurt.databinding.HomeFragmentBinding;
import com.amanoteam.kurt.databinding.ConsoleFragmentBinding;
import com.amanoteam.kurt.databinding.NetworkFragmentBinding;

public class KurtViewModel extends ViewModel {
	
	public HomeFragmentBinding homeFragment = null;
	public ConsoleFragmentBinding consoleFragment = null;
	public NetworkFragmentBinding networkFragment = null;
	
	private WebView webView = null;
	
	public WebView getWebView() {
		return webView;
	}
	
	public void setWebView(final WebView view) {
		webView = view;
	}
	
}
