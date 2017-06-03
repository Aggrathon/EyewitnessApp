package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class BackgroundInformationActivity extends ACancelCheckActivity {

	EditText ageText;
	EditText natText;
	RadioButton manRadio;
	RadioButton womanRadio;
	RadioButton otherRadio;
	SeekBar lengthBar;
	TextView lengthText;
	EditText livedText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_background_information);

		ageText = (EditText) findViewById(R.id.ageField);
		natText = (EditText) findViewById(R.id.natField);
		manRadio = (RadioButton) findViewById(R.id.sexMan);
		womanRadio = (RadioButton) findViewById(R.id.sexWoman);
		otherRadio = (RadioButton) findViewById(R.id.sexOther);
		lengthBar = (SeekBar) findViewById(R.id.selfLengthBar);
		lengthText = (TextView) findViewById(R.id.selfLengthText);
		livedText = (EditText) findViewById(R.id.selfLivedLandField);

		lengthBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				lengthText.setText(i+" cm");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		lengthText.setText(lengthBar.getProgress()+" cm");
	}

	public void onStartButton(View view) {
		if (ageText.getText().length() < 1 || natText.getText().length() < 1 || livedText.getText().length() < 1) {
			Toast.makeText(this, R.string.notificationFillAllValues, Toast.LENGTH_SHORT).show();
		}
		else {
			try {
				ExperimentData data = ExperimentData.getInstance();
				data.personalInformation.age = Integer.parseInt(ageText.getText().toString());
				data.personalInformation.nationality = natText.getText().toString();
				data.personalInformation.height = lengthBar.getProgress();
				data.personalInformation.country = livedText.getText().toString();
				data.personalInformation.sex = manRadio.isChecked() ? "man" : womanRadio.isChecked() ? "woman" : "other";
				startActivity(new Intent(this, VisualAcuityActivity.class));
			}
			catch (NumberFormatException e) {
				Toast.makeText(this, R.string.notificationFillAllValues, Toast.LENGTH_SHORT).show();
			}
		}
	}
}
