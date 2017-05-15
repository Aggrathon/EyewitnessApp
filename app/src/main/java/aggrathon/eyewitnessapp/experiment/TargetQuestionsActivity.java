package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;

public class TargetQuestionsActivity extends AppCompatActivity {

	SeekBar weightBar;
	SeekBar heightBar;
	RadioButton manRadio;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_target_questions);

		final TextView weightText = (TextView)findViewById(R.id.weightText);
		weightBar = ((SeekBar)findViewById(R.id.weightBar));
		weightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				weightText.setText(i+" kg");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		final TextView heightText = (TextView)findViewById(R.id.heightText);
		heightBar = ((SeekBar)findViewById(R.id.heightBar));
		heightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				heightText.setText(i+" cm");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		manRadio = (RadioButton)findViewById(R.id.sexMan);
	}

	public void onNextButton(View v) {
		ExperimentIteration data = ExperimentData.getInstance().getLatestData();
		data.targetWeight = weightBar.getProgress();
		data.targetHeight = heightBar.getProgress();
		data.targetSex = manRadio.isChecked() ? "man" : "woman";
		startActivity(new Intent(this, LineupActivity.class));
	}
}
