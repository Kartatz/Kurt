package com.amanoteam.kurt.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.amanoteam.kurt.R;
import com.amanoteam.kurt.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;
import android.webkit.WebView;
import com.amanoteam.kurt.models.KurtViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import androidx.fragment.app.Fragment;
import com.amanoteam.kurt.fragments.HomeFragment;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import com.google.android.material.appbar.AppBarLayout;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.View;
import com.google.android.material.behavior.HideViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
	
	private NavController navController = null;
	private KurtViewModel viewModel = null;
	
	OnBackPressedDispatcher onBackPressedDispatcher = null;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		final CoordinatorLayout.LayoutParams bottomNavigationLayout = (CoordinatorLayout.LayoutParams) binding.bottomNavigation.getLayoutParams();
		final HideViewOnScrollBehavior behavior = (HideViewOnScrollBehavior) bottomNavigationLayout.getBehavior();
		
		final MaterialToolbar toolbar = findViewById(R.id.main_toolbar);
		setSupportActionBar(toolbar);
		
		final AppBarLayout appbar = findViewById(R.id.main_appbar);

		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener((final SharedPreferences settings, final String key) -> {
			/*
			if (key.equals("appTheme")) {
				final String appTheme = settings.getString("appTheme", "follow_system");
				PackageUtils.setAppTheme(appTheme);
			}
			*/
		});
		
		viewModel = new ViewModelProvider(this).get(KurtViewModel.class);
		
		final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		final AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_browser, R.id.navigation_console, R.id.navigation_network)
			.build();
		navController = Navigation.findNavController(this, R.id.fragment_container_view);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
		
		navController.addOnDestinationChangedListener((final NavController controller, final NavDestination destination, final Bundle arguments) -> {
			appbar.setExpanded(true, true);
			behavior.slideIn(binding.bottomNavigation, true);
			final View view = getCurrentFocus();
			
			if (view == null) {
				return;
			}
			
			final IBinder windowToken = view.getWindowToken();
			inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
		});

		final Window window = getWindow();
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		getSupportFragmentManager().findFragmentById(R.id.navigation_browser);
		
		onBackPressedDispatcher = getOnBackPressedDispatcher();
		
		onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
			@Override
			 public void handleOnBackPressed() {
				final WebView webView = viewModel.getWebView();
				
				Fragment f = (androidx.fragment.app.Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
				
				if (/*f instanceof HomeFragment && */webView.canGoBack()) {
					webView.goBack();
				} else {
					setEnabled(false);
					onBackPressedDispatcher.onBackPressed();
					setEnabled(true);
				}
			}
		});
	}
	
	@Override
	public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
/*
	@Override
	public void onBackPressed() {
		final WebView webView = viewModel.getWebView();
		
		Fragment f = (androidx.fragment.app.Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
		
		if (f instanceof HomeFragment && webView.canGoBack()) {
			webView.goBack();
		} else {
			super.onBackPressed();
		}
	}
	
*/
}
