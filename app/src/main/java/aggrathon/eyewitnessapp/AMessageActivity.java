package aggrathon.eyewitnessapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import aggrathon.eyewitnessapp.data.ExperimentData;

public abstract class AMessageActivity extends ACancelCheckActivity {

	protected TextView messageText;
	protected Button nextButton;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_message);

		messageText = (TextView)findViewById(R.id.messageText);
		nextButton = (Button)findViewById(R.id.nextButton);
	}
}
