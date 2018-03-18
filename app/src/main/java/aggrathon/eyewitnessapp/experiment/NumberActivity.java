package aggrathon.eyewitnessapp.experiment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.utils.StorageManager;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.view.ImageButtonGrid;

public class NumberActivity extends ACancelCheckActivity {

	ImageButtonGrid grid;

	protected void onCreate(Bundle savedInstanceState, ImageButtonGrid.OnCLick onClick) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_number);
		StorageManager.checkForStoragePermissions(this);

		grid = (ImageButtonGrid)findViewById(R.id.numberGrid);
		SharedPreferences prefs = this.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		int num = prefs.getInt(SettingsActivity.LINEUP_COUNT, 4);
		if (num == 1) {
			NextActivity(0);
			return;
		}
		int[] imgs = new int[num];
		if (1 <= num)
			imgs[0] = R.drawable.one;
		if (2 <= num)
			imgs[1] = R.drawable.two;
		if (3 <= num)
			imgs[2] = R.drawable.three;
		if (4 <= num)
			imgs[3] = R.drawable.four;
		if (5 <= num)
			imgs[4] = R.drawable.five;
		if (6 <= num)
			imgs[5] = R.drawable.six;
		if (7 <= num)
			imgs[6] = R.drawable.seven;
		if (8 <= num)
			imgs[7] = R.drawable.eight;
		if (9 <= num)
			imgs[8] = R.drawable.nine;
		if (10 <= num)
			imgs[9] = R.drawable.ten;
		grid.SetImages(imgs, onClick);

	}

	@SuppressLint("MissingSuperCall")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		onCreate(savedInstanceState, new ImageButtonGrid.OnCLick() {
			@Override
			public void OnClick(int i) {
				NextActivity(i);
			}
		});
	}

	public void OnEndButton(View v) {
		onBackPressed();
	}

	public void NextActivity(int number) {
		ExperimentData.getInstance().startExperimentIteration(this, number+1, Integer.toString(number));
		startActivity(new Intent(this, WaitActivity.class));
		finish();
	}
}
