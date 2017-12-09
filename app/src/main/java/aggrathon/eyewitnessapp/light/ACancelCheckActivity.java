package aggrathon.eyewitnessapp.light;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


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
							act.startActivity(new Intent(act, MainActivity.class));
							act.finish();
						}
					})
					.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) { }
					});
			return builder.create();
		}
	}
}
