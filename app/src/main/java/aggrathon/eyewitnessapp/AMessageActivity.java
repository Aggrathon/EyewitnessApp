package aggrathon.eyewitnessapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import aggrathon.eyewitnessapp.data.ExperimentData;

public abstract class AMessageActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_message);
	}

	public void setMessage(int titleText, int messageText, View.OnClickListener action) {
		((TextView)findViewById(R.id.title)).setText(titleText);
		((TextView)findViewById(R.id.messageText)).setText(messageText);
		(findViewById(R.id.nextButton)).setOnClickListener(action);
	}
}
