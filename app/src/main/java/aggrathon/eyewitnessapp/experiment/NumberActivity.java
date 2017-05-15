package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;

public class NumberActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_number);
	}

	public void OnEndButton(View v) {
		onBackPressed();
	}

	public void OnNumberOne(View v) {
		ExperimentData.getInstance().LoadImages("1", this);
		ExperimentData.getInstance().data.add(new ExperimentIteration());
		startActivity(new Intent(this, WaitActivity.class));
	}

	public void OnNumberTwo(View v) {
		ExperimentData.getInstance().LoadImages("2", this);
		ExperimentData.getInstance().data.add(new ExperimentIteration());
		startActivity(new Intent(this, WaitActivity.class));
	}

	public void OnNumberThree(View v) {
		ExperimentData.getInstance().LoadImages("3", this);
		ExperimentData.getInstance().data.add(new ExperimentIteration());
		startActivity(new Intent(this, WaitActivity.class));
	}

	public void OnNumberFour(View v) {
		ExperimentData.getInstance().LoadImages("4", this);
		ExperimentData.getInstance().data.add(new ExperimentIteration());
		startActivity(new Intent(this, WaitActivity.class));
	}
}
