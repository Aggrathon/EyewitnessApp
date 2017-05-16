package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import aggrathon.eyewitnessapp.data.ExperimentData;

public class ResultActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_result);

		ExperimentData data = ExperimentData.getInstance();
		((TextView)findViewById(R.id.resultText)).setText(data.getResultString());
		data.save(this);
		ExperimentData.clearInstance();
	}

	public void endExperiment(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}
}
