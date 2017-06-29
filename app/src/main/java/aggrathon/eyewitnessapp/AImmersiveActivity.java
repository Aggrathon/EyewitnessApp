package aggrathon.eyewitnessapp;

import android.app.ActionBar;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.utils.StorageManager;


public abstract class AImmersiveActivity extends AppCompatActivity {

	private TextView title;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setup();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		setup();
	}

	protected void setup() {
		//Centered Action bar
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			CharSequence titleText = actionBar.getTitle();
			getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
			getSupportActionBar().setCustomView(R.layout.toolbar_center_title);
			title = (TextView) findViewById(R.id.toolbar_title);
			setTitle(titleText);
		}
		//Background Image
		View root = findViewById(android.R.id.content);
		if (ExperimentData.adultTheme()) {
			root.setBackgroundResource( R.drawable.background_abstract);
		}
		else {
			root.setBackgroundResource( R.drawable.landscape_bitmap);
		}
		//Sticky Immersion
		SetImmersiveListener();
		SetImmersion();
	}

	@Override
	public void setTitle(CharSequence titleText) {
		 if(title != null) title.setText(titleText);
	}

	@Override
	public void setTitle(int titleId) {
		if(title != null) title.setText(titleId);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SetImmersiveListener();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == StorageManager.STORAGE_PERMISSION_REQUEST) {
			StorageManager.handleStoragePermissionCallback(this, grantResults[0] == PackageManager.PERMISSION_GRANTED);
		}
	}

	public void SetImmersiveListener() {
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					SetImmersion();
				}
			}
		});
	}

	public void SetImmersion() {
		getWindow().getDecorView().setSystemUiVisibility(
			View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_IMMERSIVE
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus)
			SetImmersion();
	}
}
