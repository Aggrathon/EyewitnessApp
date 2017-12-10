package aggrathon.eyewitnessapp.light.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.light.ACancelCheckActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.utils.StorageManager;
import aggrathon.eyewitnessapp.light.data.ExperimentData;

public class NumberActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_number);
		StorageManager.checkForStoragePermissions(this);
	}

	public void OnEndButton(View v) {
		onBackPressed();
	}

	public void OnNumberOne(View v) {
		NextActivity("A");
	}

	public void OnNumberTwo(View v) {
		NextActivity("B");
	}

	public void OnNumberThree(View v) {
		NextActivity("");
	}

	public void OnNumberFour(View v) {
		NextActivity("");
	}

	public void NextActivity(String label) {
		ExperimentData.getInstance().setupExperimentIterations(label);
		ExperimentData.getInstance().startExperimentIteration(this);
		startActivity(new Intent(this, LineupActivity.class));
		finish();
	}
}
