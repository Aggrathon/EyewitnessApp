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
import android.util.Log;
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
	public static final String TEST_NUM_COUNTER = "TEST_NUM_COUNTER";
	public static final String DEVICE_ID = "DEVICE_ID";
	public static final String FOLDER_LOCATION = "FOLDER_LOCATION";

	private static final int FOLDER_REQUEST = 12342;

	SeekBar lineupVariation;
	SeekBar lineupTarget;
	Switch lineupNormalisation;
	EditText deviceID;
	TextView testID;
	Spinner folderSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		lineupVariation = (SeekBar)findViewById(R.id.prefLineupVariationsSeekBar);
		lineupTarget = (SeekBar)findViewById(R.id.prefTargetInLineupSeekBar);
		lineupNormalisation = (Switch)findViewById(R.id.prefLineupNormalizeSwitch);
		deviceID = (EditText)findViewById(R.id.deviceIdText);
		testID = (TextView)findViewById(R.id.testIdText);
		folderSpinner = (Spinner)findViewById(R.id.spinnerFolder);

		final SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		lineupVariation.setProgress(prefs.getInt(LINEUP_VARIATION, lineupVariation.getProgress()));
		lineupTarget.setProgress(prefs.getInt(LINEUP_TARGET, lineupTarget.getProgress()));
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
		//adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
		folderSpinner.setAdapter(adapter);
		folderSpinner.setSelection(folders.indexOf(prefs.getString(FOLDER_LOCATION, Environment.DIRECTORY_DOCUMENTS)));
		final Activity act = this;
		folderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				StorageManager.moveDirectoryLocation(act, folders.get(i));
				prefs.edit().putString(FOLDER_LOCATION, folders.get(i)).commit();
			}
			@Override
			public void onNothingSelected(AdapterView<?> adapterView) { }
		});
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
		try {
			if (f.isFile()) {
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
			} else {
				Toast.makeText(this, R.string.notification_no_log, Toast.LENGTH_SHORT).show();
			}
		}
		catch (Exception e) {
			Toast.makeText(this, "Couldn't show the log", Toast.LENGTH_SHORT).show();
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

	public void onChangeFolder(View v) {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("file/*");
		startActivityForResult(intent, FOLDER_REQUEST);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == FOLDER_REQUEST && data != null) {
			String folder = data.getDataString();
			Log.d("settings", folder);
			if (folder.endsWith(StorageManager.DIRECTORY_NAME))
				folder = folder.substring(0, folder.length()-StorageManager.DIRECTORY_NAME.length());
			else if (folder.endsWith(StorageManager.DIRECTORY_NAME+"/"))
				folder = folder.substring(0, folder.length()-StorageManager.DIRECTORY_NAME.length()-1);
			String oldFolder = getSharedPreferences(PREFERENCE_NAME, 0).getString(FOLDER_LOCATION, StorageManager.PATH);
		}
		else
			super.onActivityResult(requestCode, resultCode, data);
	}
}
