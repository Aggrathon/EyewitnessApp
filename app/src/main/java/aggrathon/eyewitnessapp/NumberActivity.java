package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NumberActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.isInstanced()) {
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		setContentView(R.layout.activity_number);
	}

	public void OnEndButton(View v) {
		onBackPressed();
	}

	public void OnNumberOne(View v) {
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberTwo(View v) {
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberThree(View v) {
		startActivity(new Intent(this, LineupActivity.class));
	}

	public void OnNumberFour(View v) {
		startActivity(new Intent(this, LineupActivity.class));
	}
}
