package aggrathon.eyewitnessapp.light.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.io.File;

import aggrathon.eyewitnessapp.light.AMessageActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.data.ExperimentData;
import aggrathon.eyewitnessapp.light.experiment.LineupActivity;
import aggrathon.eyewitnessapp.light.utils.StorageManager;

public class InformationActivity extends AMessageActivity {
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ExperimentData.checkInstanced(null)) {
			final Activity act = this;
			File file = new File(new File(StorageManager.IMAGE_DIRECTORY).getParentFile().getPath() + File.separator + "instructions.txt");
			String instr = StorageManager.readTextFile(file);
			if (instr == null || instr.length() == 0) {
				startActivity(new Intent(act, ExperimentInformationActivity.class));
				finish();
				return;
			}
			setMessage(R.string.information, instr, new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, ExperimentInformationActivity.class));
					finish();
				}
			});
		}
	}
}
