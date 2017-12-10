package aggrathon.eyewitnessapp.light.experiment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import aggrathon.eyewitnessapp.light.ACancelCheckActivity;
import aggrathon.eyewitnessapp.light.R;
import aggrathon.eyewitnessapp.light.SettingsActivity;
import aggrathon.eyewitnessapp.light.data.ExperimentData;
import aggrathon.eyewitnessapp.light.data.ExperimentIteration;
import aggrathon.eyewitnessapp.light.utils.StorageManager;

public class LineupActivity extends ACancelCheckActivity {

	private ImageView imageView;
	private int imageIndex = 0;
	private long startTime;
	private long startTime2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		ExperimentData data = ExperimentData.getInstance();

		switch (data.lineup) {
			case sequential:
				setContentView(R.layout.activity_lineup2);

				imageView = (ImageView) findViewById(R.id.imageView);
				imageIndex = 0;
				imageView.setImageBitmap(data.images.get(imageIndex));
				startTime2 = System.currentTimeMillis();
				break;

			case simultaneous:
				setContentView(R.layout.activity_lineup);

				setupButton(R.id.imageButton0, 0, data);
				setupButton(R.id.imageButton1, 1, data);
				setupButton(R.id.imageButton2, 2, data);
				setupButton(R.id.imageButton3, 3, data);
				setupButton(R.id.imageButton4, 4, data);
				setupButton(R.id.imageButton5, 5, data);
				setupButton(R.id.imageButton6, 6, data);
				setupButton(R.id.imageButton7, 7, data);

				SharedPreferences prefs = this.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
				int pres = prefs.getInt(SettingsActivity.LINEUP_TARGET, 50);
				if (pres == 100) {
					findViewById(R.id.targetAbsentButton).setVisibility(View.GONE);
				}
				else {
					findViewById(R.id.targetAbsentButton).setVisibility(View.VISIBLE);
				}

				break;
		}
		startTime = System.currentTimeMillis();
		File f = new File(StorageManager.IMAGE_DIRECTORY+ File.separator + ExperimentData.getInstance().getLatestData().lineupNumber + File.separator + "instructions.txt");
		String instr = StorageManager.readTextFile(f);
		if (instr != null && instr.length() > 0) {
			TextView title = (TextView) findViewById(R.id.title);
			title.setText(instr);
		}
	}

	private void setupButton(int viewId, int index, ExperimentData data) {
		ImageButton imageButton = (ImageButton)findViewById(viewId);
		imageButton.setImageBitmap(data.images.get(index));
		final int i = index;
		imageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				imageIndex = i;
				onSelectImageButton(null);
			}
		});
	}

	public void onTargetMissingButton(View v) {
		ExperimentData data = ExperimentData.getInstance();
		ExperimentIteration iter = data.getLatestData();
		iter.selectImage(-1);
		iter.setLineupTime(startTime, System.currentTimeMillis());
		if (data.lineup == ExperimentData.LineupVariant.sequential) {
			iter.setImageTime(imageIndex, startTime2, System.currentTimeMillis());
		}
		NextActivity();
	}

	public void onNextImageButton(View v) {
		ExperimentData data = ExperimentData.getInstance();
		if(imageView != null && data.images != null) {
			if (data.lineup == ExperimentData.LineupVariant.sequential) {
				data.getLatestData().setImageTime(imageIndex, startTime2, System.currentTimeMillis());
				startTime2 = System.currentTimeMillis();
			}
			imageIndex++;
			if (imageIndex == data.images.size())
				onTargetMissingButton(null);
			else
				imageView.setImageBitmap(data.images.get(imageIndex));
		}
	}

	public void onSelectImageButton(View v) {
		ExperimentData data =  ExperimentData.getInstance();
		ExperimentIteration iter = data.getLatestData();
		iter.selectImage(imageIndex);
		iter.setLineupTime(startTime, System.currentTimeMillis());
		if (data.lineup == ExperimentData.LineupVariant.sequential) {
			iter.setImageTime(imageIndex, startTime2, System.currentTimeMillis());
		}
		NextActivity();
	}

	public void NextActivity() {
		startActivity(new Intent(this, QuestionsActivity.class));
		finish();
	}
}
