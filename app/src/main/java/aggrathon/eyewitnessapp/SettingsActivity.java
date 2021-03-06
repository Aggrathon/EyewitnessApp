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
	public static final String MANUAL_ID = "MANUAL_ID";
	public static final String LOG_FOLDER_LOCATION = "LOG_FOLDER_LOCATION";
	public static final String IMAGE_FOLDER_LOCATION = "IMAGE_FOLDER_LOCATION";
	public static final String TIME_LIMIT = "TIME_LIMIT";
	public static final String EYE_TEST = "EYE_TEST";
	public static final String TUTORIAL = "TUTORIAL";
	public static final String SHOW_LIVE = "SHOW_LIVE";
	public static final String SHOW_IMAGE = "SHOW_IMAGE";
	public static final String SHOW_VIDEO = "SHOW_VIDEO";
	public static final String SHOW_BLURRED = "SHOW_BLURRED";
	public static final String SHOW_RANGE_MIN = "SHOW_RANGE_MIN";
	public static final String SHOW_RANGE_MAX = "SHOW_RANGE_MAX";

	SeekBar lineupVariation;
	SeekBar lineupTarget;
	TextView lineupVariationText;
	TextView lineupTargetText;
	TextView lineupStatsText;
	Switch lineupNormalisation;
	EditText deviceID;
	TextView testID;
	Spinner logFolderSpinner;
	Spinner imageFolderSpinner;
	SeekBar lineupCountBar;
	SeekBar imageCountBar;
	TextView lineupCountText;
	TextView imageCountText;
	TextView timeLimitText;
	SeekBar timeLimitBar;
	Switch switchManualID;
	Switch switchEyeTest;
	Switch switchTutorial;
	EditText showRangeMin;
	EditText showRangeMax;
	SeekBar showLiveBar;
	SeekBar showImageBar;
	SeekBar showVideoBar;
	SeekBar showBlurredBar;
	TextView showLiveText;
	TextView showImageText;
	TextView showVideoText;
	TextView showBlurredText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		lineupNormalisation = (Switch)findViewById(R.id.prefLineupNormalizeSwitch);
		deviceID = (EditText)findViewById(R.id.deviceIdText);
		testID = (TextView)findViewById(R.id.testIdText);
		logFolderSpinner = (Spinner)findViewById(R.id.spinnerLogFolder);
		imageFolderSpinner = (Spinner)findViewById(R.id.spinnerImageFolder);

		final SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		lineupNormalisation.setChecked(prefs.getBoolean(LINEUP_NORMALISATION, true));
		deviceID.setText(prefs.getString(DEVICE_ID, ""));
		testID.setText(testID.getText()+" "+prefs.getInt(TEST_NUM_COUNTER, 1));

		lineupVariation = (SeekBar)findViewById(R.id.prefLineupVariationsSeekBar);
		lineupVariationText = (TextView)findViewById(R.id.prefLineupVariationsText);
		int tmp = prefs.getInt(LINEUP_VARIATION, 50);
		lineupVariation.setProgress(tmp/5);
		lineupVariationText.setText(Integer.toString(tmp)+ (tmp==100? "%" :" %"));
		lineupVariation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				lineupVariationText.setText(Integer.toString(i*5)+ (i==20? "%" :" %"));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		lineupTarget = (SeekBar)findViewById(R.id.prefTargetInLineupSeekBar);
		lineupTargetText = (TextView)findViewById(R.id.prefTargetInLineupText);
		tmp = prefs.getInt(LINEUP_TARGET, 50);
		lineupTarget.setProgress(tmp/5);
		lineupTargetText.setText(Integer.toString(tmp)+ (tmp==100? "%" :" %"));
		lineupTarget.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				lineupTargetText.setText(Integer.toString(i*5)+ (i==20? "%" :" %"));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		lineupStatsText = (TextView)findViewById(R.id.prefStatsText);
		int a1 = prefs.getInt(LINEUP_STATS_SEQUENTIAL, 1);
		int b1 = prefs.getInt(LINEUP_STATS_SIMULTANEOUS, 1);
		int r1 = (int)((float)b1 * 100f / (float)(a1+b1));
		int a2 = prefs.getInt(LINEUP_STATS_TARGET_ABSENT, 1);
		int b2 = prefs.getInt(LINEUP_STATS_TARGET_PRESENT, 1);
		int r2 = (int)((float)b2 * 100f / (float)(a2+b2));
		lineupStatsText.setText("Stats:\n\tSimultaneous: "+r1+" %\n\tPresent: "+r2+" %");

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
		tmp = prefs.getInt(LINEUP_COUNT, 4);
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

		timeLimitText = (TextView)findViewById(R.id.textTimeLimitText);
		timeLimitBar = (SeekBar)findViewById(R.id.prefTimeLimitSeekBar);
		timeLimitBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				if (i == 0)
					timeLimitText.setText("∞");
				else
					timeLimitText.setText(Integer.toString(i/10)+","+Integer.toString(i%10)+"s");
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		tmp = prefs.getInt(TIME_LIMIT, 0);
		timeLimitBar.setProgress(tmp);
		if (tmp == 0)
			timeLimitText.setText("∞");
		else
			timeLimitText.setText(Integer.toString(tmp/10)+","+Integer.toString(tmp%10)+"s");

		switchManualID = (Switch) findViewById(R.id.switchManualId);
		switchManualID.setChecked(prefs.getBoolean(MANUAL_ID, false));

		switchEyeTest = (Switch) findViewById(R.id.switchEyeTest);
		switchEyeTest.setChecked(prefs.getBoolean(EYE_TEST, true));

		switchTutorial = (Switch) findViewById(R.id.switchTutorial);
		switchTutorial.setChecked(prefs.getBoolean(TUTORIAL, true));

		showRangeMax = (EditText)findViewById(R.id.showRangeMax);
		showRangeMax.setText(Integer.toString(prefs.getInt(SHOW_RANGE_MAX, 1000)));
		showRangeMin = (EditText)findViewById(R.id.showRangeMin);
		showRangeMin.setText(Integer.toString(prefs.getInt(SHOW_RANGE_MIN, 0)));
		showLiveBar = (SeekBar)findViewById(R.id.showLiveBar);
		showImageBar = (SeekBar)findViewById(R.id.showImageBar);
		showVideoBar = (SeekBar)findViewById(R.id.showVideoBar);
		showBlurredBar = (SeekBar)findViewById(R.id.showBlurredBar);
		showLiveText = (TextView) findViewById(R.id.showLiveValue);
		showVideoText = (TextView) findViewById(R.id.showVideoValue);
		showImageText = (TextView) findViewById(R.id.showImageValue);
		showBlurredText = (TextView) findViewById(R.id.showBlurredValue);
		showLiveBar.setProgress(prefs.getInt(SHOW_LIVE, 10));
		showVideoBar.setProgress(prefs.getInt(SHOW_VIDEO, 0));
		showImageBar.setProgress(prefs.getInt(SHOW_IMAGE, 0));
		showBlurredBar.setProgress(prefs.getInt(SHOW_BLURRED, 0));
		SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				SetLivePerc();
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		};
		showLiveBar.setOnSeekBarChangeListener(listener);
		showVideoBar.setOnSeekBarChangeListener(listener);
		showImageBar.setOnSeekBarChangeListener(listener);
		showBlurredBar.setOnSeekBarChangeListener(listener);
		SetLivePerc();
	}

	private void SetLivePerc() {
		int lp = showLiveBar.getProgress();
		int vp = showVideoBar.getProgress();
		int ip = showImageBar.getProgress();
		int bp = showBlurredBar.getProgress();
		int sum = lp + vp + ip + bp;
		showLiveText.setText((lp * 100 / sum ) + "%");
		showVideoText.setText((vp * 100 / sum ) + "%");
		showImageText.setText((ip * 100 / sum ) + "%");
		showBlurredText.setText((bp * 100 / sum ) + "%");
	}

	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(LINEUP_VARIATION, lineupVariation.getProgress()*5);
		editor.putInt(LINEUP_TARGET, lineupTarget.getProgress()*5);
		editor.putBoolean(LINEUP_NORMALISATION, lineupNormalisation.isChecked());
		editor.putString(DEVICE_ID, deviceID.getText().toString());
		editor.putInt(LINEUP_COUNT, lineupCountBar.getProgress()+1);
		editor.putInt(IMAGE_COUNT, imageCountBar.getProgress()+2);
		editor.putInt(TIME_LIMIT, timeLimitBar.getProgress());
		editor.putBoolean(MANUAL_ID, switchManualID.isChecked());
		editor.putBoolean(EYE_TEST, switchEyeTest.isChecked());
		editor.putBoolean(TUTORIAL, switchTutorial.isChecked());
		editor.putInt(SHOW_IMAGE, showImageBar.getProgress());
		editor.putInt(SHOW_LIVE, showLiveBar.getProgress());
		editor.putInt(SHOW_VIDEO, showVideoBar.getProgress());
		editor.putInt(SHOW_BLURRED, showBlurredBar.getProgress());
		editor.putInt(SHOW_RANGE_MIN, Integer.parseInt(showRangeMin.getText().toString()));
		editor.putInt(SHOW_RANGE_MAX, Integer.parseInt(showRangeMax.getText().toString()));
		editor.commit();
	}

	public void onResetStatsButton(View v) {
		SharedPreferences prefs = getSharedPreferences(PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putInt(LINEUP_STATS_SEQUENTIAL, 1);
		editor.putInt(LINEUP_STATS_SIMULTANEOUS, 1);
		editor.putInt(LINEUP_STATS_TARGET_ABSENT, 1);
		editor.putInt(LINEUP_STATS_TARGET_PRESENT, 1);
		editor.commit();
	}

	public void onImagesButton(View v) {
		startActivity(new Intent(this, FileInstructionsActivity.class));
		finish();
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
