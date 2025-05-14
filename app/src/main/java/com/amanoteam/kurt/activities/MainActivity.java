package com.amanoteam.kurt.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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

public class MainActivity extends AppCompatActivity {
	
	private NavController navController = null;
	private KurtViewModel viewModel = null;
	
	OnBackPressedDispatcher onBackPressedDispatcher = null;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		final MaterialToolbar toolbar = findViewById(R.id.main_toolbar);
		setSupportActionBar(toolbar);

		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		preferences.registerOnSharedPreferenceChangeListener((final SharedPreferences settings, final String key) -> {
			/*
			if (key.equals("appTheme")) {
				final String appTheme = settings.getString("appTheme", "follow_system");
				PackageUtils.setAppTheme(appTheme);
			}
			*/
		});

		final AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_browser, R.id.navigation_console, R.id.navigation_network)
			.build();
		navController = Navigation.findNavController(this, R.id.fragment_container_view);
		
		
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
		
		//navController.addOnDestinationChangedListener((final NavController controller, final NavDestination destination, final Bundle arguments) -> PackageUtils.hideKeyboard(MainActivity.this));

		final Window window = getWindow();
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		viewModel = new ViewModelProvider(this).get(KurtViewModel.class);
		
		navController.navigate(R.id.navigation_browser);
		navController.navigate(R.id.navigation_network);
		navController.navigate(R.id.navigation_console);
		
		
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
