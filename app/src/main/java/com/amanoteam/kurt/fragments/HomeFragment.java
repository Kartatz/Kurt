package com.amanoteam.kurt.fragments;

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


public class HomeFragment extends Fragment {
	
	private WebView webView = null;
	
	private KurtViewModel viewModel = null;
	private HomeFragmentBinding binding = null;
	
	private String cssSelector = null;
	private String xPath = null;
	private String content = null;
	private String contentParent = null;
	
	static private final String JAVASCRIPT_INTERFACE = "internalBinding";
	
	class JavaScriptInterface {
		public void setXPath(final String value) {
			xPath = value;
		}
		
		public void setSelector(final String value) {
			cssSelector = value;
		}
		
		public void setContent(final String value) {
			content = value;
		}
		
		public void setContentParent(final String value) {
			contentParent = value;
		}
	}
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final FragmentActivity activity = getActivity();
		
		viewModel = (
			new ViewModelProvider(activity)
				.get(KurtViewModel.class)
		);
		
		if (viewModel.homeFragment == null) {
			binding = HomeFragmentBinding.inflate(inflater, container, false);
		}
		
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
		
		webView = (WebView) fragmentView.findViewById(R.id.webview);
		final WebSettings webSettings = webView.getSettings();
		
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		
		webView.addJavascriptInterface(new JavaScriptInterface(), JAVASCRIPT_INTERFACE);
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient());
		webView.setVisibility(View.VISIBLE);
		
		webView.loadUrl("https://wikipedia.org");
		
		final SwipeRefreshLayout swipeRefresh = fragmentView.findViewById(R.id.swipe_to_refresh);
		
		swipeRefresh.setOnRefreshListener(() -> {
			webView.clearCache(true);
			webView.reload();
			swipeRefresh.setRefreshing(false);
		});
		
		final Toast toast = Toast.makeText(context, "bruh", Toast.LENGTH_SHORT);
		toast.show();
		viewModel.setWebView(webView);
		//viewModel.homeFragment = binding;
	}
	
}
