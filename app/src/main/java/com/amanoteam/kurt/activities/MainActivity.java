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

import com.amanoteam.kurt.models.KurtViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
	
	private NavController navController = null;
	private ItemViewModel viewModel = null;
	
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
	}
	
	@Override
	public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

}
