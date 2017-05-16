package aggrathon.eyewitnessapp;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class StorageManager {


	public static final int STORAGE_PERMISSION_REQUEST = 95;
	public static final String DIRECTORY_NAME = "Eyewitness";
	public static final String PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + DIRECTORY_NAME;
	public static final String LOG_DIRECTORY = PATH + File.separator + "logs";
	public static final String COMBINED_LOG = LOG_DIRECTORY + File.separator + "combinedlog.csv";

	public static void createFolders(Activity activity) {
		File logDir = new File(LOG_DIRECTORY);
		File imgDir1 = new File(PATH + File.separator + "images" + File.separator + "1");
		File imgDir2 = new File(PATH + File.separator + "images" + File.separator + "2");
		File imgDir3 = new File(PATH + File.separator + "images" + File.separator + "3");
		File imgDir4 = new File(PATH + File.separator + "images" + File.separator + "4");
		File imgDirTest = new File(PATH + File.separator + "images" + File.separator + "Test");
		logDir.mkdirs();
		imgDir1.mkdirs();
		imgDir2.mkdirs();
		imgDir3.mkdirs();
		imgDir4.mkdirs();
		imgDirTest.mkdirs();
		if(logDir.isDirectory() && imgDir1.isDirectory() && imgDir2.isDirectory() &&
				imgDir3.isDirectory() && imgDir4.isDirectory() && imgDirTest.isDirectory()) {
		}
		else {
			Log.e("Storage", "Could not access storage to create directories");
			showErrorToast(activity);
		}
	}

	private static void showErrorToast(Activity activity) {
		Toast.makeText(activity, R.string.notificationStorageError, Toast.LENGTH_LONG);
	}

	public static void createLogfile(Activity activity, String[] logs, String labels) { createLogfile(activity, logs, labels, true); }

	public static void createLogfile(Activity activity, String[] logs, String labels, boolean alsoCombined) {
		createFolders(activity);
		String fileName = "log_"+(System.currentTimeMillis()-1000000000000l)+".csv";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(LOG_DIRECTORY + File.separator + fileName));
			writer.write(labels);
			writer.newLine();
			for (String s : logs) {
				writer.write(s);
				writer.newLine();
			}
			writer.close();
			if (alsoCombined) {
				boolean writeLabels = !new File(COMBINED_LOG).isFile();
				writer = new BufferedWriter(new FileWriter(COMBINED_LOG, true));
				if (writeLabels) {
					writer.write(labels);
					writer.newLine();
				}
				for (String s : logs) {
					writer.append(s);
					writer.newLine();
				}
				writer.close();
			}
		}
		catch (IOException e) {
			Log.e("Storage", "Could not write logfiles ("+e.getLocalizedMessage()+")");
			showErrorToast(activity);
		}
	}

	public static void checkForStoragePermissions(Activity activity) {
		if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
			ActivityCompat.requestPermissions(activity, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST);
		else
			createFolders(activity);
	}

	public static void handleStoragePermissionCallback(Activity activity, boolean result) {
		if (result)
			StorageManager.createFolders(activity);
		else
			new StorageRequiredDialog().show(activity.getFragmentManager(), "Storage Required");
	}

	public static class StorageRequiredDialog extends DialogFragment{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Activity act = getActivity();
			AlertDialog.Builder builder = new AlertDialog.Builder(act);
			builder.setMessage(R.string.messageStorageRequired);
			builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							checkForStoragePermissions(act);
						}
					});
			return builder.create();
		}
	}
}
