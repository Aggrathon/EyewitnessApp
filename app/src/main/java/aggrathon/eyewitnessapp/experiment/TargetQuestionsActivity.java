package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;

public class TargetQuestionsActivity extends ACancelCheckActivity {

	SeekBar weightBar;
	SeekBar heightBar;
	SeekBar distBar;
	RadioButton manRadio;
	Spinner ageSpinner;

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

		final TextView distText = (TextView)findViewById(R.id.distText);
		distBar = ((SeekBar)findViewById(R.id.distBar));
		distBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				distText.setText(i+" m");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});

		manRadio = (RadioButton)findViewById(R.id.sexMan);

		ageSpinner = (Spinner)findViewById(R.id.ageSpinner);
		ArrayList<Integer> list = new ArrayList<>(100);
		for (int i = 5; i < 100; i++)
			list.add(i);
		ArrayAdapter<Integer> adap1 = new ArrayAdapter(this, R.layout.spinner_text_element, list);
		adap1.setDropDownViewResource(R.layout.spinner_text_dropdown);
		ageSpinner.setAdapter(adap1);
		ageSpinner.setSelection(20);
	}

	public void onNextButton(View v) {
		ExperimentIteration data = ExperimentData.getInstance().getLatestData();
		data.targetWeight = weightBar.getProgress();
		data.targetHeight = heightBar.getProgress();
		data.distance = distBar.getProgress();
		data.targetSex = manRadio.isChecked() ? "man" : "woman";
		data.age = (Integer) ageSpinner.getSelectedItem();
		startActivity(new Intent(this, LineupActivity.class));
	}
}
