package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BackgroundInformation extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_background_information);
	}

	public void onStartButton(View view) {
		ExperimentData.createInstance();
		Log.d("Data",ExperimentData.getInstance().toString());
		startActivity(new Intent(this, NumberActivity.class));
	}
}
