package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import aggrathon.eyewitnessapp.AImmersiveActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class AgeActivity extends AImmersiveActivity {

	final static int MIN_AGE = 5;

	SeekBar ageBar;
	TextView ageText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_age);

		ageBar = (SeekBar) findViewById(R.id.ageBar);
		ageText = (TextView)findViewById(R.id.ageText);

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

	public void onNext(View v) {
		ExperimentData.getInstance().personalInformation.age = ageBar.getProgress()+MIN_AGE;
		startActivity(new Intent(this, ExperimentInformationActivity.class));
		finish();
	}

}
