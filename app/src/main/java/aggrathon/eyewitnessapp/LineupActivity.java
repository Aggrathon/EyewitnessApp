package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Collections;

public class LineupActivity extends ACancelCheckActivity {

	ArrayList<Integer> imageArray;
	private ImageSwitcher imageSwitcher;
	private int imageIndex = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.isInstanced()) {
			Log.d("Test", "No Data");
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		ExperimentData data = ExperimentData.getInstance();
		imageArray = new ArrayList<>(data.images);
		Collections.shuffle(imageArray);
		data.log.append("Lineup image order: ");
		for (int img : data.images) {
			data.log.append(imageArray.indexOf(img)).append(" ");
		}
		data.log.append('\n');

		switch (data.lineup) {
			case sequential:
				setContentView(R.layout.activity_lineup2);

				imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
				imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
					@Override
					public View makeView() {
						ImageView view = new ImageView(getApplicationContext());
						view.setScaleType(ImageView.ScaleType.FIT_CENTER);
						view.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
						return view;
					}
				});
				imageSwitcher.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						onNextImageButton(null);
					}
				});
				imageIndex = 0;
				imageSwitcher.setImageResource(imageArray.get(0));
				break;

			case simultaneous:
				setContentView(R.layout.activity_lineup);

				setupButton(R.id.imageButton0, 0);
				setupButton(R.id.imageButton1, 1);
				setupButton(R.id.imageButton2, 2);
				setupButton(R.id.imageButton3, 3);
				setupButton(R.id.imageButton4, 4);
				setupButton(R.id.imageButton5, 5);
				setupButton(R.id.imageButton6, 6);
				setupButton(R.id.imageButton7, 7);
				findViewById(R.id.targetAbsentButton).setVisibility(data.targetPresent? View.GONE: View.VISIBLE);
				break;
		}
	}

	private void setupButton(int viewId, int index) {
		ImageButton imageButton = (ImageButton)findViewById(viewId);
		imageButton.setImageResource(imageArray.get(index));
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
		ExperimentData.getInstance().log.append("Lineup image missing selected\n");
		startActivity(new Intent(this, QuestionsActivity.class));
	}

	public void onNextImageButton(View v) {
		if(imageSwitcher != null && imageArray != null) {
			imageIndex++;
			if (imageIndex == imageArray.size())
				onTargetMissingButton(null);
			else
				imageSwitcher.setImageResource(imageArray.get(imageIndex));
		}
	}

	public void onSelectImageButton(View v) {
		ExperimentData.getInstance().log.append("Lineup image ").append(imageIndex).append(" selected\n");
		startActivity(new Intent(this, QuestionsActivity.class));
	}
}
