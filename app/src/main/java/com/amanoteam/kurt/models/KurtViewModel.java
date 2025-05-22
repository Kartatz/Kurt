package com.amanoteam.kurt.models;

import androidx.lifecycle.ViewModel;
import android.webkit.WebView;
import com.amanoteam.kurt.databinding.HomeFragmentBinding;
import com.amanoteam.kurt.databinding.ConsoleFragmentBinding;
import com.amanoteam.kurt.databinding.NetworkFragmentBinding;
import java.util.ArrayList;
import java.util.List;
import com.amanoteam.kurt.network.HTTPRequest;
import com.amanoteam.kurt.network.HTTPResponse;

public class KurtViewModel extends ViewModel {
	
	public HomeFragmentBinding homeFragment = null;
	public ConsoleFragmentBinding consoleFragment = null;
	public NetworkFragmentBinding networkFragment = null;
	
	public List<HTTPRequest> requests = new ArrayList<HTTPRequest>();
	public List<HTTPResponse> responses = new ArrayList<HTTPResponse>();
	
	public WebView webView = null;
	
	public List<String> networkLogs = new ArrayList<String>();
	
	public WebView getWebView() {
		return webView;
	}
	
	public void setWebView(final WebView view) {
		webView = view;
	}
	
}
