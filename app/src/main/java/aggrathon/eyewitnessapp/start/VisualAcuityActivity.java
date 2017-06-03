package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class VisualAcuityActivity extends ACancelCheckActivity {

	public enum State {
		instructions1,
		left,
		instructions2,
		right
	}

	State state;
	ImageView imageView;
	TextView textView;
	int visualStage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_visual_acuity);
		textView = (TextView)findViewById(R.id.textView);
		imageView = (ImageView)findViewById(R.id.imageView);
		state = State.instructions1;
		visualStage = 0;

		imageView.setImageResource(R.drawable.landolt_c);
		imageView.setRotation(45);
		imageView.setScaleX(1.0f);
		imageView.setScaleY(1.0f);
	}

	public void onNext(View v) {
		switch (state) {
			case instructions1:
				visualStage++;
				float scale = (float) Math.pow(0.7, visualStage);
				imageView.setScaleX(scale);
				imageView.setScaleY(scale);
				break;
			case left:
				break;
			case instructions2:
				break;
			case right:
				startActivity(new Intent(this, TutorialActivity.class));
				break;
		}
	}
}
