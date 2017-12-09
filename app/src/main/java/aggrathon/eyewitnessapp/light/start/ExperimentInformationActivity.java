package aggrathon.eyewitnessapp.light.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import aggrathon.eyewitnessapp.light.AImmersiveActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.data.ExperimentData;

public class ExperimentInformationActivity extends AImmersiveActivity {

	CheckBox conditionCheck;
	Button nextButton;
	RadioButton prevPartButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_experiment_information);

		nextButton = (Button) findViewById(R.id.nextButton);
		conditionCheck = (CheckBox) findViewById(R.id.checkBox);
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
		ExperimentData.getInstance().personalInformation.previousParticipations = prevPartButton.isChecked();
		startActivity(new Intent(this, BackgroundInformationActivity.class));
		finish();
	}

}
