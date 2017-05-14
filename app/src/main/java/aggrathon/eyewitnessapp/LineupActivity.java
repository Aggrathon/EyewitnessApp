package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.GridView;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import java.util.ArrayList;
import java.util.Collections;

public class LineupActivity extends AppCompatActivity {

	private GridView grid;
	private ImageGridAdapter gridAdapter;

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
		Collections.shuffle(data.images);

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
				imageIndex = 1;
				onPrevImageButton(null);
				break;

			case simultaneous:
				setContentView(R.layout.activity_lineup);

				grid = (GridView)findViewById(R.id.gridView);
				ArrayList<Integer> arr = new ArrayList();
				for (int i = 0; i < 8; i++)
					arr.add(i);
				gridAdapter = new ImageGridAdapter(this, R.layout.image_selector_button, arr);
				grid.setAdapter(gridAdapter);
				break;
		}

		findViewById(R.id.targetAbsentButton).setVisibility(data.targetPresent? View.GONE: View.VISIBLE);
	}

	public void onTargetMissingButton(View v) {
		startActivity(new Intent(this, QuestionsActivity.class));
	}

	public void onNextImageButton(View v) {
		if(imageSwitcher != null) {
			ExperimentData data = ExperimentData.getInstance();
			imageIndex = (imageIndex+1)%data.images.size();
			imageSwitcher.setImageResource(data.images.get(imageIndex));
		}
	}

	public void onPrevImageButton(View v) {
		if(imageSwitcher != null) {
			ExperimentData data = ExperimentData.getInstance();
			imageIndex = (imageIndex-1+data.images.size())%data.images.size();
			imageSwitcher.setImageResource(data.images.get(imageIndex));
		}
	}

	public void onSelectImageButton(View v) {
		startActivity(new Intent(this, QuestionsActivity.class));
	}
}
