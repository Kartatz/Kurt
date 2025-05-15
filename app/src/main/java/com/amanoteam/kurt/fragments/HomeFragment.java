package com.amanoteam.kurt.fragments;


import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.lang.StringBuilder;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import android.app.UiModeManager;
import android.text.TextUtils;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.WindowInsets;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.ValueCallback;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;

import androidx.work.WorkRequest;
import androidx.work.WorkManager;
import androidx.work.OneTimeWorkRequest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import androidx.appcompat.widget.AppCompatImageButton;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.button.MaterialButton;
import android.view.Menu;
import androidx.core.view.MenuProvider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import androidx.lifecycle.Lifecycle;
import android.view.ViewParent;
import android.widget.CheckBox;
import androidx.appcompat.widget.AppCompatButton;
import android.content.DialogInterface;
import android.widget.ScrollView;
import android.content.res.AssetManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.lang.StringBuilder;
import com.amanoteam.kurt.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import android.webkit.WebView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.amanoteam.kurt.databinding.HomeFragmentBinding;
import android.webkit.ConsoleMessage;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.ValueCallback;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.amanoteam.kurt.models.KurtViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import android.widget.Toast;
import java.io.InputStream;
import java.io.BufferedReader;
import java.lang.StringBuilder;
import java.lang.StringBuilder;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import android.net.Uri;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import android.os.Looper;
import android.os.Handler;

public class HomeFragment extends Fragment {
	
	private LinearProgressIndicator progress = null;
	private WebView webView = null;
	
	private KurtViewModel viewModel = null;
	private HomeFragmentBinding binding = null;
	
	private String cssSelector = null;
	private String xPath = null;
	private String content = null;
	private String contentParent = null;
	
	static private final Looper looper = Looper.getMainLooper();
	
	static private final String JAVASCRIPT_INTERFACE = "internalBinding";
	
	class JavaScriptInterface {
		
		@JavascriptInterface
		public void setXPath(final String value) {
			xPath = value;
		}
		
		@JavascriptInterface
		public void setSelector(final String value) {
			cssSelector = value;
		}
		
		@JavascriptInterface
		public void setContent(final String value) {
			content = value;
			final Toast toast = Toast.makeText(webView.getContext(), "bruh", Toast.LENGTH_SHORT);
			toast.show();
		}
		
		@JavascriptInterface
		public void setContentParent(final String value) {
			contentParent = value;
		}
	}
	
	private final WebViewClient webViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(final WebView webView, final String url, final Bitmap favicon) {
			//setTitle(R.string.page_loading);
			
			new Handler(looper).post(() -> {
				if (progress.isVisible()) {
					return;
				}
				
				progress.setVisibility(View.VISIBLE);
			});
			
			super.onPageStarted(webView, url, favicon);
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
			final FragmentActivity activity = getActivity();
			
			final Uri uri = request.getUrl();
			final String scheme = uri.getScheme();
			
			if (scheme.equals("http") || scheme.equals("https")) {
				return false;
			}
			
			final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			
			activity.startActivity(intent);
			
			return true;
		}
				
		@Override
		public void onPageFinished(final WebView webView, final String url) {
			// setTitle(webView.getTitle());
			
			super.onPageFinished(webView, url);
			
			final String expression = readFile("kurt.js");
			webView.evaluateJavascript(expression, null);
			
			new Handler(looper).post(() -> {
				progress.setVisibility(View.GONE);
			});
		}
		
	};
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final FragmentActivity activity = getActivity();
		
		viewModel = (
			new ViewModelProvider(activity)
				.get(KurtViewModel.class)
		);
		
		binding = (
			(viewModel.homeFragment == null) ?
			HomeFragmentBinding.inflate(inflater, container, false) :
			viewModel.homeFragment
		);
		
		return binding.getRoot();
	}

	@Override
	public void onViewCreated(final View fragmentView, final Bundle savedInstanceState) {
		if (viewModel.homeFragment != null) {
			return;
		}
		
		viewModel.homeFragment = binding;
		
		final FragmentActivity activity = getActivity();
		final Context context = activity.getApplicationContext();
		
		final LayoutInflater layoutInflater = activity.getLayoutInflater();
		
		progress = fragmentView.findViewById(R.id.progress);
		
		webView = (WebView) fragmentView.findViewById(R.id.webview);
		final WebSettings webSettings = webView.getSettings();
		
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setUseWideViewPort(true);
		
		webView.addJavascriptInterface(new JavaScriptInterface(), JAVASCRIPT_INTERFACE);
		webView.setWebViewClient(webViewClient);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebContentsDebuggingEnabled(true);
		
		webView.setVisibility(View.VISIBLE);
		progress.setVisibility(View.VISIBLE);
		webView.loadUrl("https://wikipedia.org");
		
		final SwipeRefreshLayout swipeRefresh = fragmentView.findViewById(R.id.swipe_to_refresh);
		
		swipeRefresh.setOnRefreshListener(() -> {
			progress.setVisibility(View.VISIBLE);
			webView.clearCache(true);
			webView.reload();
			swipeRefresh.setRefreshing(false);
		});
		
		viewModel.setWebView(webView);
		//viewModel.homeFragment = binding;
	}
	
	public String readFile(final String name) {
		final FragmentActivity activity = getActivity();
		final AssetManager assetManager = activity.getAssets();
		
		String string = null;
		final StringBuilder stringBuilder = new StringBuilder();
		
		try {
			final InputStream inputStream = assetManager.open(name);
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8.name()));
			
			while ((string = bufferedReader.readLine()) != null) {
				stringBuilder.append(string);
			}
			
			inputStream.close();
		} catch (IOException e) {}
		
		string = stringBuilder.toString();
		
		return string;
	}
	
}
