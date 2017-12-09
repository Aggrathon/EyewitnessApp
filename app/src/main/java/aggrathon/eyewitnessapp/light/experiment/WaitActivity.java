package aggrathon.eyewitnessapp.light.experiment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import aggrathon.eyewitnessapp.light.AMessageActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.data.ExperimentData;

public class WaitActivity extends AMessageActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ExperimentData.checkInstanced(null)) {
			final Activity act = this;
			setMessage(R.string.wait, R.string.text_wait_instructions, new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, LineupActivity.class));
					finish();
				}
			});
		}
	}
}
