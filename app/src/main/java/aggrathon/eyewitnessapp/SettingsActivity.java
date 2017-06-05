package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

	public static final String PREFERENCE_NAME = "Preferences";
	public static final String LINEUP_VARIATION = "LINEUP_VARIATION";
	public static final String LINEUP_TARGET = "LINEUP_TARGET";
	public static final String LINEUP_NORMALISATION = "LINEUP_NORMALISATION";
	public static final String LINEUP_STATS_SEQUENTIAL = "LINEUP_STATS_SEQUENTIAL";
	public static final String LINEUP_STATS_SIMULTANEOUS = "LINEUP_STATS_SIMULTANEOUS";
	public static final String LINEUP_STATS_TARGET_ABSENT = "LINEUP_STATS_TARGET_ABSENT";
	public static final String LINEUP_STATS_TARGET_PRESENT = "LINEUP_STATS_TARGET_PRESENT";
	public static final String TEST_NUM_COUNTER = "TEST_NUM_COUNTER";
	public static final String DEVICE_ID = "DEVICE_ID";

	SeekBar lineupVariation;
	SeekBar lineupTarget;
	Switch lineupNormalisation;
	EditText deviceID;
	TextView testID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		lineupVariation = (SeekBar)findViewById(R.id.prefLineupVariationsSeekBar);
		lineupTarget = (SeekBar)findViewById(R.id.prefTargetInLineupSeekBar);
		lineupNormalisation = (Switch)findViewById(R.id.prefLineupNormalizeSwitch);
		deviceID = (EditText)findViewById(R.id.deviceIdText);
		testID = (TextView)findViewById(R.id.testIdText);

		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		lineupVariation.setProgress(prefs.getInt(LINEUP_VARIATION, lineupVariation.getProgress()));
		lineupTarget.setProgress(prefs.getInt(LINEUP_TARGET, lineupTarget.getProgress()));
		lineupNormalisation.setChecked(prefs.getBoolean(LINEUP_NORMALISATION, true));
		deviceID.setText(prefs.getString(DEVICE_ID, ""));
		testID.setText(testID.getText()+" "+prefs.getInt(TEST_NUM_COUNTER, 1));
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(LINEUP_VARIATION, lineupVariation.getProgress());
		editor.putInt(LINEUP_TARGET, lineupTarget.getProgress());
		editor.putBoolean(LINEUP_NORMALISATION, lineupNormalisation.isChecked());
		editor.putString(DEVICE_ID, deviceID.getText().toString());
		editor.commit();
	}

	public void onResetStatsButton(View v) {
		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(LINEUP_STATS_SEQUENTIAL, 0);
		editor.putInt(LINEUP_STATS_SIMULTANEOUS, 0);
		editor.putInt(LINEUP_STATS_TARGET_ABSENT, 0);
		editor.putInt(LINEUP_STATS_TARGET_PRESENT, 0);
		editor.commit();
	}

	public void onImagesButton(View v) {
		new MessageDialog().show(getSupportFragmentManager(), "Image Instructions", R.string.text_settings_image_instructions);
	}

	public void onLogButton(View v) {
		File f = new File(StorageManager.COMBINED_LOG);
		if(f.isFile()) {
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(f), "text/plain");
			PackageManager manager = getPackageManager();
			if (manager.queryIntentActivities(intent, 0).size() > 0) {
				startActivityForResult(intent, 10);
			} else {
				String log = StorageManager.readTextFile(f);
				if (f == null)
					Toast.makeText(this, R.string.notification_no_log, Toast.LENGTH_SHORT).show();
				else
					new MessageDialog().show(getSupportFragmentManager(), "Master Log", log);
			}
		}
		else {
			Toast.makeText(this, R.string.notification_no_log, Toast.LENGTH_SHORT).show();
		}
	}

	public void onResetID(View v) {
		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(TEST_NUM_COUNTER, 1);
		editor.commit();
		String testIdText = testID.getText().toString();
		testIdText = testIdText.substring(0, testIdText.lastIndexOf(' ')+1) +"1";
		testID.setText(testIdText);
	}
}
