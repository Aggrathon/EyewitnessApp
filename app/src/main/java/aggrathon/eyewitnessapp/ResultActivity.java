package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import aggrathon.eyewitnessapp.data.ExperimentData;

public class ResultActivity extends AImmersiveActivity {

	ExperimentData data;
	TextView resultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_result);

		data = ExperimentData.getInstance();
		resultText = (TextView)findViewById(R.id.resultText);
		resultText.setText(data.getResultString());
		data.save(this);
		ExperimentData.clearInstance();
	}

	public void endExperiment(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == 75) {
			resultText.setText(data.toString());
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		endExperiment(null);
	}
}
