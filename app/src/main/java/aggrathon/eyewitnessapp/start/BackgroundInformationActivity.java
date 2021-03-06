package aggrathon.eyewitnessapp.start;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.experiment.NumberActivity;

public class BackgroundInformationActivity extends ACancelCheckActivity {

	RadioButton manRadio;
	RadioButton womanRadio;
	RadioButton otherRadio;
	SeekBar lengthBar;
	TextView lengthText;
	Spinner natSpinner;
	RadioButton glassesYesRadio;
	RadioButton glassesNoRadio;
	Spinner glassesSpinner;

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
		glassesSpinner = (Spinner)findViewById(R.id.spinnerGlasses);
		glassesNoRadio = (RadioButton) findViewById(R.id.glassesNo);
		glassesYesRadio = (RadioButton) findViewById(R.id.glassesYes);

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
		lengthBar.setProgress(0);
		lengthText.setText(lengthBar.getProgress()+" cm");

		ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this, R.array.countries, R.layout.spinner_text_element);
		adap.setDropDownViewResource(R.layout.spinner_text_dropdown);
		natSpinner.setAdapter(adap);

		adap = ArrayAdapter.createFromResource(this, R.array.glassesOptions, R.layout.spinner_text_element);
		adap.setDropDownViewResource(R.layout.spinner_text_dropdown);
		glassesSpinner.setAdapter(adap);
		glassesSpinner.setSelection(3);
	}

	public void onStartButton(View view) {
		if (natSpinner.getSelectedItem() == null || glassesSpinner.getSelectedItem() == null) {
			Toast.makeText(this, R.string.notification_fill_all, Toast.LENGTH_SHORT).show();
		}
		else {
			ExperimentData data = ExperimentData.getInstance();
			data.personalInformation.nationality = getSelectedCountry();
			data.personalInformation.height = lengthBar.getProgress();
			data.personalInformation.sex = manRadio.isChecked() ? "man" : womanRadio.isChecked() ? "woman" : "other";
			data.personalInformation.glassesCurrent = glassesYesRadio.isChecked();
			data.personalInformation.glassesUsually = getGlasses();
			if(getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0).getBoolean(SettingsActivity.EYE_TEST, true))
				startActivity(new Intent(this, VisualAcuityActivity.class));
			else if(getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0).getBoolean(SettingsActivity.TUTORIAL, true))
				startActivity(new Intent(this, TutorialActivity.class));
			else
				startActivity(new Intent(this, NumberActivity.class));
			finish();
		}
	}

	private String getSelectedCountry() {
		Configuration conf = getResources().getConfiguration();
		conf = new Configuration(conf);
		conf.setLocale(new Locale("en"));
		Context localizedContext = createConfigurationContext(conf);
		return localizedContext.getResources().getStringArray(R.array.countries)[natSpinner.getSelectedItemPosition()];
	}

	private String getGlasses() {
		switch (glassesSpinner.getSelectedItemPosition()) {
			case 0: return "far";
			case 1: return "close";
			case 2: return "other";
			case 3: return "no";
			default: return "";
		}
	}
}
