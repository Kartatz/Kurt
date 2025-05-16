package com.amanoteam.kurt.activities;

import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.WindowCompat;
import androidx.appcompat.app.AppCompatDelegate;
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
import android.os.Build;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
import android.view.View;
import com.google.android.material.behavior.HideViewOnScrollBehavior;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.View;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.graphics.Insets;

public class MainActivity extends AppCompatActivity {
	
	private NavController navController = null;
	private KurtViewModel viewModel = null;
	
	OnBackPressedDispatcher onBackPressedDispatcher = null;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		
		final BottomNavigationView bottomNavigationView = binding.bottomNavigation;
		
		final CoordinatorLayout.LayoutParams bottomNavigationLayout = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
		
		@SuppressWarnings("unchecked")
		final HideViewOnScrollBehavior<BottomNavigationView> bottomNavigationBehavior = (HideViewOnScrollBehavior<BottomNavigationView>) bottomNavigationLayout.getBehavior();
		
		final AppBarLayout appBar = findViewById(R.id.main_appbar);
		
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
		AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
		final AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_browser, R.id.navigation_console, R.id.navigation_network)
			.build();
		navController = Navigation.findNavController(this, R.id.fragment_container_view);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
		
		navController.addOnDestinationChangedListener((final NavController controller, final NavDestination destination, final Bundle arguments) -> {
			appbar.setExpanded(true, true);
			bottomNavigationBehavior.slideIn(bottomNavigationView, true);
			
			final View view = getCurrentFocus();
			
			if (view == null) {
				return;
			}
			
			final IBinder windowToken = view.getWindowToken();
			inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
		});

		final Window window = getWindow();
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
			final WindowManager.LayoutParams params = window.getAttributes();
			params.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_ALWAYS;
			window.setAttributes(params);
		}
		
		WindowCompat.setDecorFitsSystemWindows(window, false);
		
		ViewCompat.setOnApplyWindowInsetsListener(appBar, new OnApplyWindowInsetsListener() {
		    @Override
		    public WindowInsetsCompat onApplyWindowInsets(final View view, final WindowInsetsCompat insets) {
				final Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
				
				appBar.setPaddingRelative(
					systemBars.left,
					systemBars.top,
					systemBars.right,
					appBar.getPaddingBottom()
		        );
				
		        return insets;
		    }
		});
		
		onBackPressedDispatcher = getOnBackPressedDispatcher();
		
		onBackPressedDispatcher.addCallback(this, new OnBackPressedCallback(true) {
			@Override
			 public void handleOnBackPressed() {
				final WebView webView = viewModel.getWebView();
				final Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
				
				if (fragment instanceof HomeFragment && webView.canGoBack()) {
					webView.goBack();
					return;
				}
				
				setEnabled(false);
				onBackPressedDispatcher.onBackPressed();
				setEnabled(true);
			}
		});
	}
	
	@Override
	public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

}
