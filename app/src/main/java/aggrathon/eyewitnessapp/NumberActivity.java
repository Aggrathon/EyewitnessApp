package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.data.ExperimentData;

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
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberTwo(View v) {
		ExperimentData.getInstance().LoadImages("2", this);
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberThree(View v) {
		ExperimentData.getInstance().LoadImages("3", this);
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberFour(View v) {
		ExperimentData.getInstance().LoadImages("4", this);
		startActivity(new Intent(this, LineupActivity.class));
	}
}
