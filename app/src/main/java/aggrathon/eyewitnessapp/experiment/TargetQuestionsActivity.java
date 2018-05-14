package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;

public class TargetQuestionsActivity extends ACancelCheckActivity {

	final static int MIN_AGE = 5;

	SeekBar weightBar;
	SeekBar heightBar;
	SeekBar distBar;
	RadioButton manRadio;
	RadioButton womanRadio;
	SeekBar ageBar;

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
		weightBar.setProgress(0);
		weightText.setText(0+" kg");

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
		heightBar.setProgress(0);
		heightText.setText(0+" cm");

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
		distBar.setProgress(0);
		distText.setText(0+" m");

		manRadio = (RadioButton)findViewById(R.id.sexMan);
		womanRadio = (RadioButton)findViewById(R.id.sexWoman);

		ageBar = (SeekBar) findViewById(R.id.ageBar);
		final TextView ageText = (TextView)findViewById(R.id.ageText);
		ageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				ageText.setText(Integer.toString(i+MIN_AGE));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		ageBar.setProgress(0);
		ageText.setText(Integer.toString(MIN_AGE));
	}

	public void onNextButton(View v) {
		if (!manRadio.isChecked() && !womanRadio.isChecked()) {
			Toast.makeText(this, R.string.notification_fill_all, Toast.LENGTH_SHORT).show();
			return;
		}
		ExperimentIteration data = ExperimentData.getInstance().getLatestData();
		data.targetWeight = weightBar.getProgress();
		data.targetHeight = heightBar.getProgress();
		data.distance = distBar.getProgress();
		data.targetSex = manRadio.isChecked() ? "man" : "woman";
		data.age = ageBar.getProgress()+MIN_AGE;
		NextActivity();
	}

	public void NextActivity() {
		startActivity(new Intent(this, LineupActivity.class));
		finish();
	}
}
