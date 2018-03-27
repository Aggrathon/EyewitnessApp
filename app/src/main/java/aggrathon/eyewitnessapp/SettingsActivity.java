package aggrathon.eyewitnessapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import aggrathon.eyewitnessapp.utils.MessageDialog;
import aggrathon.eyewitnessapp.utils.StorageManager;

public class SettingsActivity extends AppCompatActivity {

	public static final String PREFERENCE_NAME = "Preferences";
	public static final String LINEUP_VARIATION = "LINEUP_VARIATION";
	public static final String LINEUP_TARGET = "LINEUP_TARGET";
	public static final String LINEUP_NORMALISATION = "LINEUP_NORMALISATION";
	public static final String LINEUP_STATS_SEQUENTIAL = "LINEUP_STATS_SEQUENTIAL";
	public static final String LINEUP_STATS_SIMULTANEOUS = "LINEUP_STATS_SIMULTANEOUS";
	public static final String LINEUP_STATS_TARGET_ABSENT = "LINEUP_STATS_TARGET_ABSENT";
	public static final String LINEUP_STATS_TARGET_PRESENT = "LINEUP_STATS_TARGET_PRESENT";
	public static final String LINEUP_COUNT = "LINEUP_COUNT";
	public static final String IMAGE_COUNT = "IMAGE_COUNT";
	public static final String TEST_NUM_COUNTER = "TEST_NUM_COUNTER";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String LOG_FOLDER_LOCATION = "LOG_FOLDER_LOCATION";
	public static final String IMAGE_FOLDER_LOCATION = "IMAGE_FOLDER_LOCATION";

	SeekBar lineupVariation;
	SeekBar lineupTarget;
	Switch lineupNormalisation;
	EditText deviceID;
	TextView testID;
	Spinner logFolderSpinner;
	Spinner imageFolderSpinner;
	SeekBar lineupCountBar;
	SeekBar imageCountBar;
	TextView lineupCountText;
	TextView imageCountText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		lineupVariation = (SeekBar)findViewById(R.id.prefLineupVariationsSeekBar);
		lineupTarget = (SeekBar)findViewById(R.id.prefTargetInLineupSeekBar);
		lineupNormalisation = (Switch)findViewById(R.id.prefLineupNormalizeSwitch);
		deviceID = (EditText)findViewById(R.id.deviceIdText);
		testID = (TextView)findViewById(R.id.testIdText);
		logFolderSpinner = (Spinner)findViewById(R.id.spinnerLogFolder);
		imageFolderSpinner = (Spinner)findViewById(R.id.spinnerImageFolder);

		final SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		lineupVariation.setProgress(prefs.getInt(LINEUP_VARIATION, 100));
		lineupTarget.setProgress(prefs.getInt(LINEUP_TARGET, 100));
		lineupNormalisation.setChecked(prefs.getBoolean(LINEUP_NORMALISATION, true));
		deviceID.setText(prefs.getString(DEVICE_ID, ""));
		testID.setText(testID.getText()+" "+prefs.getInt(TEST_NUM_COUNTER, 1));

		File root = Environment.getExternalStorageDirectory();
		final ArrayList<String> folders = new ArrayList<>();
		folders.add(".");
		for (File f : root.listFiles())
			if(f.isDirectory())
				folders.add(f.getName());
		ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, folders);
		logFolderSpinner.setAdapter(adapter);
		logFolderSpinner.setSelection(folders.indexOf(prefs.getString(LOG_FOLDER_LOCATION, Environment.DIRECTORY_DOCUMENTS)));
		final Activity act = this;
		logFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				StorageManager.moveLogDirectoryLocation(act, folders.get(i));
				prefs.edit().putString(LOG_FOLDER_LOCATION, folders.get(i)).commit();
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) { }
		});
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, folders);
		imageFolderSpinner.setAdapter(adapter);
		imageFolderSpinner.setSelection(folders.indexOf(prefs.getString(IMAGE_FOLDER_LOCATION, Environment.DIRECTORY_DOCUMENTS)));
		imageFolderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				StorageManager.moveImageDirectoryLocation(act, folders.get(i));
				prefs.edit().putString(IMAGE_FOLDER_LOCATION, folders.get(i)).commit();
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) { }
		});


		lineupCountBar = (SeekBar)findViewById(R.id.prefLineupCountSeekBar);
		lineupCountText = (TextView)findViewById(R.id.prefLineupCountText);
		lineupCountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				lineupCountText.setText(Integer.toString(i+1));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		int tmp = prefs.getInt(LINEUP_COUNT, 4);
		lineupCountBar.setProgress(tmp-1);
		lineupCountBar.setMax(9);
		lineupCountText.setText(Integer.toString(tmp));

		imageCountBar = (SeekBar)findViewById(R.id.prefImageCountSeekBar);
		imageCountText = (TextView)findViewById(R.id.prefImageCountText);
		imageCountBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				imageCountText.setText(Integer.toString(i+2));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		tmp = prefs.getInt(IMAGE_COUNT, 8);
		imageCountBar.setProgress(tmp-2);
		imageCountBar.setMax(8);
		imageCountText.setText(Integer.toString(tmp));
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
		editor.putInt(LINEUP_COUNT, lineupCountBar.getProgress()+1);
		editor.putInt(IMAGE_COUNT, imageCountBar.getProgress()+2);
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
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(StorageManager.LOG_DIRECTORY));
			String[] types = new String[] {"files/*", "resource/folder"};
			PackageManager manager = getPackageManager();
			for (String s: types) {
				intent.setType(s);
				if (manager.queryIntentActivities(intent, 0).size() > 0) {
					startActivity(intent);
					return;
				}
			}
			Toast.makeText(this, "No filebrowser found", Toast.LENGTH_SHORT).show();
			StorageManager.showCombinedLog(deviceID.getText().toString(), this);
		}
		catch (Exception e) {
			Toast.makeText(this, "Couldn't show the log", Toast.LENGTH_SHORT).show();
			StorageManager.showCombinedLog(deviceID.getText().toString(), this);
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

		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle(R.string.notification_delete_logs);
		b.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				StorageManager.deleteAllLogs();
			}
		});
		b.setNegativeButton(R.string.no, null);
		b.show();
	}
}
