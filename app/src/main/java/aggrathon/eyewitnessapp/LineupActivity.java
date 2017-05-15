package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import aggrathon.eyewitnessapp.data.ExperimentData;

public class LineupActivity extends ACancelCheckActivity {

	private ImageView imageView;
	private int imageIndex = 0;

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
				if(data.targetPresent)
					setupButton(R.id.imageButton7, 7, data);
				else
					findViewById(R.id.imageButton7).setVisibility(View.GONE);
				findViewById(R.id.targetAbsentButton).setVisibility(data.targetPresent? View.GONE: View.VISIBLE);
				break;
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
		ExperimentData.getInstance().log.append("Lineup image missing selected\n");
		startActivity(new Intent(this, QuestionsActivity.class));
	}

	public void onNextImageButton(View v) {
		ExperimentData data = ExperimentData.getInstance();
		if(imageView != null && data.images != null) {
			imageIndex++;
			if (imageIndex == data.images.size())
				onTargetMissingButton(null);
			else
				imageView.setImageBitmap(data.images.get(imageIndex));
		}
	}

	public void onSelectImageButton(View v) {
		ExperimentData.getInstance().log.append("Lineup image ").append(imageIndex).append(" selected\n");
		startActivity(new Intent(this, QuestionsActivity.class));
	}
}
