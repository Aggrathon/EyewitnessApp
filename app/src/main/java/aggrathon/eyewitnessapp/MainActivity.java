package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.start.AgeActivity;
import aggrathon.eyewitnessapp.start.TutorialActivity;
import aggrathon.eyewitnessapp.utils.StorageManager;

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
		SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		StorageManager.setDirectoryLocation(
				prefs.getString(SettingsActivity.LOG_FOLDER_LOCATION, Environment.DIRECTORY_DOCUMENTS),
				prefs.getString(SettingsActivity.IMAGE_FOLDER_LOCATION, Environment.DIRECTORY_DOCUMENTS)
		);
		StorageManager.checkForStoragePermissions(this);
	}

	public void onLangSwedish(View view) {
		ExperimentData.createInstance(this, "swe");
		setLocale("sv");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangEnglish(View view) {
		ExperimentData.createInstance(this, "eng");
		setLocale("en");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangFinnish(View view) {
		ExperimentData.createInstance(this, "fin");
		setLocale("fi");
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
			startActivity(new Intent(this, TutorialActivity.class));
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void setLocale(String lang) {
		Locale myLocale = new Locale(lang);
		Resources res = getResources();
		Configuration conf = res.getConfiguration();
		conf.setLocale(myLocale);
		res.updateConfiguration(conf, res.getDisplayMetrics());
	}
}
