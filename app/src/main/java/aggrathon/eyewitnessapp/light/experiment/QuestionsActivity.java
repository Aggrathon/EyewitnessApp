package aggrathon.eyewitnessapp.light.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import aggrathon.eyewitnessapp.light.ACancelCheckActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.data.ExperimentData;
import aggrathon.eyewitnessapp.light.data.ExperimentIteration;

public class QuestionsActivity extends ACancelCheckActivity {

	SeekBar sureBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_questions);

		final TextView sureText = (TextView)findViewById(R.id.sureText);
		sureBar = ((SeekBar)findViewById(R.id.sureBar));
		sureBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				sureText.setText(i+" %");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		sureBar.setProgress(0);
		sureText.setText(0+" %");
	}

	public void onNextButton(View v) {
		ExperimentIteration data = ExperimentData.getInstance().getLatestData();
		data.confidence = sureBar.getProgress();
		data.recognisedTarget = ((RadioButton)findViewById(R.id.yesAnswerTarget)).isChecked();
		data.recognisedOther = ((RadioButton)findViewById(R.id.yesAnswerOther)).isChecked();
		NextActivity();
	}

	public void NextActivity() {
		ExperimentData data = ExperimentData.getInstance();
		if (data.hasExperimentationsLeft())
			startActivity(new Intent(this, WaitActivity.class));
		else {
			data.save(this);
			ExperimentData.clearInstance();
		}
		finish();
	}

}
