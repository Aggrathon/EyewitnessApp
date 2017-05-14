package aggrathon.eyewitnessapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


public abstract class ACancelCheckActivity extends AppCompatActivity {

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
			builder.setMessage(R.string.endExperiment)
					.setPositiveButton(R.string.yesAnswer, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							ExperimentData.clearInstance(true);
							act.startActivity(new Intent(act, MainActivity.class));
						}
					})
					.setNegativeButton(R.string.noAnswer, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) { }
					});
			return builder.create();
		}
	}
}
