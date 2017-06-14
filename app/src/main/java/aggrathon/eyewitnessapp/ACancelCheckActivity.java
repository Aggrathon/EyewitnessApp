package aggrathon.eyewitnessapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public abstract class ACancelCheckActivity extends AImmersiveActivity {

	@Override
	public void onBackPressed() {
		DialogFragment df = new EndExperimentDialog();
		df.show(getSupportFragmentManager(), "End Experiment");
	}

	public static class EndExperimentDialog extends DialogFragment {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Activity act = getActivity();
			// Use the Builder class for convenient dialog construction
			AlertDialog.Builder builder = new AlertDialog.Builder(act);
			builder.setMessage(R.string.end_experiment)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							act.startActivity(new Intent(act, ResultActivity.class));
						}
					})
					.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) { }
					});
			return builder.create();
		}
	}
}
