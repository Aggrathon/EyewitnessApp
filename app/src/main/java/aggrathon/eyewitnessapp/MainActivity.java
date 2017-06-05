package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.start.AgeActivity;
import aggrathon.eyewitnessapp.start.BackgroundInformationActivity;
import aggrathon.eyewitnessapp.start.VisualAcuityActivity;

public class MainActivity extends AppCompatActivity {

	ImageButton langSwedish;
	ImageButton langFinnish;
	ImageButton langEnglish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		langSwedish = (ImageButton)findViewById(R.id.sweButton);
		langEnglish = (ImageButton)findViewById(R.id.finButton);
		langFinnish = (ImageButton)findViewById(R.id.engButton);

		ExperimentData.clearInstance();	//RESET DATA

		StorageManager.checkForStoragePermissions(this);
		Log.d("Main",""+System.currentTimeMillis());

		//Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(tb);
	}

	public void onLangSwedish(View view) {
		ExperimentData.createInstance(this, "swe");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangEnglish(View view) {
		ExperimentData.createInstance(this, "eng");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangFinnish(View view) {
		ExperimentData.createInstance(this, "fin");
		startActivity(new Intent(this, AgeActivity.class));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == StorageManager.STORAGE_PERMISSION_REQUEST) {
			StorageManager.handleStoragePermissionCallback(this, grantResults[0] == PackageManager.PERMISSION_GRANTED);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return true;
		}
		else
			return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == 75) {
			ExperimentData.createInstance(this, "test");
			startActivity(new Intent(this, BackgroundInformationActivity.class));
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
