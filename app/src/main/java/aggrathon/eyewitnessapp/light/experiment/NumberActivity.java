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
		NextActivity(1);
	}

	public void OnNumberTwo(View v) {
		NextActivity(2);
	}

	public void OnNumberThree(View v) {
		NextActivity(3);
	}

	public void OnNumberFour(View v) {
		NextActivity(4);
	}

	public void NextActivity(int number) {
		ExperimentData.getInstance().startExperimentIteration(this, number, Integer.toString(number));
		startActivity(new Intent(this, WaitActivity.class));
		finish();
	}
}
