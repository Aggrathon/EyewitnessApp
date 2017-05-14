package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

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
		startActivity(new Intent(this, BackgroundInformation.class));
	}

	public void onLangEnglish(View view) {
		startActivity(new Intent(this, BackgroundInformation.class));
	}

	public void onLangFinnish(View view) {
		startActivity(new Intent(this, BackgroundInformation.class));
	}
}
