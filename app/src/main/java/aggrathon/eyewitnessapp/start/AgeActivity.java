package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class AgeActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_age);
	}

	public void onLessThanTenButton(View v) {
		startActivity(new Intent(this, ExperimentInformationActivity.class));
		ExperimentData.getInstance().personalInformation.age = 10;
	}

	public void onMoreThanTenButton(View v) {
		startActivity(new Intent(this, ExperimentInformationActivity.class));
		ExperimentData.getInstance().personalInformation.age = 20;
	}

}
