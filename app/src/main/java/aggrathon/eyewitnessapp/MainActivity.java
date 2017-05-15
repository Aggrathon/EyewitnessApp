package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.start.AgeActivity;

public class MainActivity extends AppCompatActivity {

	ImageButton langSwedish;
	ImageButton langFinnish;
	ImageButton langEnglish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		langSwedish = (ImageButton)findViewById(R.id.sweButton);
		langEnglish = (ImageButton)findViewById(R.id.finButton);
		langFinnish = (ImageButton)findViewById(R.id.engButton);

		ExperimentData.clearInstance();	//RESET DATA
	}

	public void onLangSwedish(View view) {
		ExperimentData.createInstance("swe");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangEnglish(View view) {
		ExperimentData.createInstance("eng");
		startActivity(new Intent(this, AgeActivity.class));
	}

	public void onLangFinnish(View view) {
		ExperimentData.createInstance("fin");
		startActivity(new Intent(this, AgeActivity.class));
	}
}
