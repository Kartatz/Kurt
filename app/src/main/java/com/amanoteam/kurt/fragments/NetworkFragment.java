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
import com.amanoteam.kurt.databinding.NetworkFragmentBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.amanoteam.kurt.models.KurtViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import android.widget.Toast;

public class NetworkFragment extends Fragment {
	
	private KurtViewModel viewModel = null;
	
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		final FragmentActivity activity = getActivity();
		
		viewModel = (
			new ViewModelProvider(activity)
				.get(KurtViewModel.class)
		);
		
		if (viewModel.networkFragment == null) {
			viewModel.networkFragment = NetworkFragmentBinding.inflate(inflater, container, false);
		}
		
		return viewModel.networkFragment.getRoot();
	}

	@Override
	public void onViewCreated(final View fragmentView, final Bundle savedInstanceState) {
		final FragmentActivity activity = getActivity();
		final Context context = activity.getApplicationContext();
		
		if (viewModel.networkFragment != null) {
			return;
		}
		
		final Toast toast = Toast.makeText(context, "bruh", Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
