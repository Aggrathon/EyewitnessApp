package aggrathon.eyewitnessapp.light.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import aggrathon.eyewitnessapp.light.R;


public class MessageDialog extends DialogFragment {

	@StringRes int messageID;
	String messageString;
	DialogInterface.OnClickListener callback;

	public void show(FragmentManager manager, String tag, @StringRes int message, DialogInterface.OnClickListener callback) {
		this.messageID = message;
		this.messageString = null;
		this.callback = callback;
		super.show(manager, tag);
	}

	public void show(FragmentManager manager, String tag, @StringRes int message) {
		show(manager, tag, message, null);
	}

	public void show(FragmentManager manager, String tag, String message, DialogInterface.OnClickListener callback) {
		this.messageString = message;
		this.callback = callback;
		super.show(manager, tag);
	}

	public void show(FragmentManager manager, String tag, String message) {
		show(manager, tag, message, null);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Activity act = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		if (messageString == null || messageString.equals(""))
			builder.setMessage(messageID);
		else {
			builder.setMessage(messageString);
		}
		builder.setPositiveButton(R.string.ok, callback);
		return builder.create();
	}
}
