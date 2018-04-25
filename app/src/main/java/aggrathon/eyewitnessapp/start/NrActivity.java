package aggrathon.eyewitnessapp.start;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import aggrathon.eyewitnessapp.AImmersiveActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class NrActivity extends AImmersiveActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_nr);
	}

	public void onSpecify(View v) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.manual_id_number);
		final EditText text = new EditText(this);
		text.setSingleLine(true);
		builder.setView(text);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				ExperimentData.getInstance().personalInformation.personalId = text.getText().toString();
				onNext(null);
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialogInterface.cancel();
			}
		});
		builder.show();
	}

	public void onNext(View v) {
		startActivity(new Intent(this, AgeActivity.class));
		finish();
	}

}
