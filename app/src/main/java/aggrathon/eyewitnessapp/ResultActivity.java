package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.isInstanced()) {
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		setContentView(R.layout.activity_result);

		((TextView)findViewById(R.id.resultText)).setText(ExperimentData.getInstance().toString());
	}

	public void endExperiment(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}
}
