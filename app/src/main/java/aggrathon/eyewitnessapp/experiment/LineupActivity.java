package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;
import aggrathon.eyewitnessapp.view.ImageButtonGrid;

public class LineupActivity extends ACancelCheckActivity {

	private ImageView imageView;
	private int imageIndex = 0;
	private long startTime;
	private long startTime2;
	ImageButtonGrid grid;

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
				grid = (ImageButtonGrid) findViewById(R.id.imageGrid);
				grid.SetImages(data.images, new ImageButtonGrid.OnCLick() {
					@Override
					public void OnClick(int i) {
						imageIndex = i;
						onSelectImageButton(null);
					}
				});
				// Here the light version removes the option for target not included if that's impossible
				break;
		}
		startTime = System.currentTimeMillis();
		// Here the light version reads instruction text from a file
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
