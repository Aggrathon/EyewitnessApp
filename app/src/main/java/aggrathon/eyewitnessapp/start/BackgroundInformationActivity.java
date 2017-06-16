package aggrathon.eyewitnessapp.start;

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

public class BackgroundInformationActivity extends ACancelCheckActivity {

	RadioButton manRadio;
	RadioButton womanRadio;
	RadioButton otherRadio;
	SeekBar lengthBar;
	TextView lengthText;
	Spinner natSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_background_information);

		manRadio = (RadioButton) findViewById(R.id.sexMan);
		womanRadio = (RadioButton) findViewById(R.id.sexWoman);
		otherRadio = (RadioButton) findViewById(R.id.sexOther);
		lengthBar = (SeekBar) findViewById(R.id.selfLengthBar);
		lengthText = (TextView) findViewById(R.id.selfLengthText);
		natSpinner = (Spinner)findViewById(R.id.spinnerNationality);

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

		ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this, R.array.countries, R.layout.spinner_text_element);
		adap.setDropDownViewResource(R.layout.spinner_text_dropdown);
		natSpinner.setAdapter(adap);
	}

	public void onStartButton(View view) {
		if (natSpinner.getSelectedItem() == null) {
			Toast.makeText(this, R.string.notification_fill_all, Toast.LENGTH_SHORT).show();
		}
		else {
			ExperimentData data = ExperimentData.getInstance();
			data.personalInformation.nationality = natSpinner.getSelectedItem().toString();
			data.personalInformation.height = lengthBar.getProgress();
			data.personalInformation.sex = manRadio.isChecked() ? "man" : womanRadio.isChecked() ? "woman" : "other";
			startActivity(new Intent(this, VisualAcuityActivity.class));
			finish();
		}
	}
}
