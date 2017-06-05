package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class ExperimentInformationActivity extends AppCompatActivity {

	CheckBox conditionCheck;
	Button nextButton;
	EditText testNumber;
	RadioButton prevPartButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_experiment_information);

		nextButton = (Button) findViewById(R.id.nextButton);
		conditionCheck = (CheckBox) findViewById(R.id.checkBox);
		testNumber = (EditText) findViewById(R.id.testNumberField);
		prevPartButton = (RadioButton) findViewById(R.id.yesButton);
		nextButton.setVisibility(View.INVISIBLE);
		conditionCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				nextButton.setVisibility(b? View.VISIBLE : View.INVISIBLE);
			}
		});
	}

	public void onNextButton(View v) {
		if (testNumber.getText().length() < 1) {
			Toast.makeText(this, R.string.fillTestNumberNotification, Toast.LENGTH_SHORT).show();
		}
		else {
			try {
				ExperimentData.getInstance().personalInformation.testId = Integer.parseInt(testNumber.getText().toString());
				ExperimentData.getInstance().personalInformation.previousParticipations = prevPartButton.isChecked();

				startActivity(new Intent(this, BackgroundInformationActivity.class));
			}
			catch (NumberFormatException e) {
				Toast.makeText(this, R.string.notificationTestNumberNotANumber, Toast.LENGTH_SHORT).show();
			}
		}
	}

}
