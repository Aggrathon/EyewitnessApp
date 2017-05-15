package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import aggrathon.eyewitnessapp.data.ExperimentData;

public class BackgroundInformation extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_background_information);
	}

	public void onStartButton(View view) {
		Log.d("Data",ExperimentData.getInstance().toString());
		startActivity(new Intent(this, NumberActivity.class));
	}
}
