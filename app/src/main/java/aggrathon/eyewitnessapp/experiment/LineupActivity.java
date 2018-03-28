package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
	private Handler handler;
	private int timeLimit;
	private TextView timeLimitText;

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
				imageView = null;
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
		timeLimit = data.timeLimit;
		timeLimitText = (TextView)findViewById(R.id.timeLimitText);
		if (timeLimit > 0) {
			handler = new Handler();
			countDown();
		}
		else {
			timeLimitText.setVisibility(View.GONE);
		}
	}

	private void countDown() {
		if (timeLimit <= 0) {
			ExperimentData data = ExperimentData.getInstance();
			if (data.lineup == ExperimentData.LineupVariant.simultaneous || imageIndex+1 == data.images.size())
				onTargetMissingButton(-2);
			else
				onNextImageButton(null);
		}
		else if (timeLimit%10 >= 5) {
			timeLimitText.setText(" "+Integer.toString(timeLimit/10+1)+" s ");
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					countDown();
				}
			}, (timeLimit%10)*100);
			timeLimit -= timeLimit%10;
		}
		else {
			timeLimitText.setText(" "+Integer.toString(timeLimit/10)+" s ");
			int nt = (timeLimit/10-1)*10;
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					countDown();
				}
			}, (timeLimit-nt)*100);
			timeLimit = nt;
		}
	}

	public void onTargetMissingButton(View v) {
		onTargetMissingButton(-1);
	}

	public void onTargetMissingButton(int selected) {
		ExperimentData data = ExperimentData.getInstance();
		ExperimentIteration iter = data.getLatestData();
		iter.selectImage(selected);
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
				ExperimentIteration iter = data.getLatestData();
				iter.setImageTime(imageIndex, startTime2, System.currentTimeMillis());
				startTime2 = System.currentTimeMillis();
				timeLimit = data.timeLimit;
				if (timeLimit > 0)
					countDown();
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
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
		startActivity(new Intent(this, QuestionsActivity.class));
		finish();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (handler != null) {
			handler.removeCallbacksAndMessages(null);
			handler = null;
		}
	}
}
