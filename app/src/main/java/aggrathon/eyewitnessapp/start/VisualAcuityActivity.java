package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class VisualAcuityActivity extends ACancelCheckActivity {

	public enum State {
		setup,
		instructions1,
		left,
		instructions2,
		right,
		finnish
	}

	State state;
	ImageView imageView;
	ImageView directionView;
	TextView textView;
	Button nextButton;
	int visualStage = 1;
	Random rnd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_visual_acuity);
		textView = (TextView)findViewById(R.id.textView);
		imageView = (ImageView)findViewById(R.id.imageView);
		nextButton = (Button)findViewById(R.id.nextButton);
		directionView = (ImageView)findViewById(R.id.directionView);
		imageView.setImageResource(R.drawable.landolt_c);
		state = State.setup;
		visualStage = 1;
		rnd = new Random();
		onNext(null);
	}

	public void onNext(View v) {
		switch (state) {
			case setup:
				textView.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				directionView.setVisibility(View.INVISIBLE);
				textView.setText(R.string.visualAcuityInstructions1);
				setTitle(R.string.visualAcuityActivity);
				state = State.instructions1;
				break;
			case instructions1:
				textView.setVisibility(View.INVISIBLE);
				imageView.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.INVISIBLE);
				directionView.setVisibility(View.VISIBLE);
				setTitle(R.string.visualAcuityTitle2);
				state = State.left;
				onNextImage();
				break;
			case left:
				textView.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				directionView.setVisibility(View.INVISIBLE);
				textView.setText(R.string.visualAcuityInstructions2);
				setTitle(R.string.visualAcuityActivity);
				state = State.instructions2;
				break;
			case instructions2:
				textView.setVisibility(View.INVISIBLE);
				imageView.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.INVISIBLE);
				directionView.setVisibility(View.VISIBLE);
				setTitle(R.string.visualAcuityTitle2);
				state = State.right;
				onNextImage();
				break;
			case right:
				textView.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				textView.setText(R.string.visualAcuityInstructions3);
				setTitle(R.string.visualAcuityActivity);
				state = State.finnish;
				break;
			case finnish:
				startActivity(new Intent(this, TutorialActivity.class));
				break;
		}
	}

	private void onNextImage() {
		imageView.setRotation(rnd.nextInt(8)*45);
		float scale = (float) Math.pow(0.7, visualStage);
		imageView.setScaleX(scale);
		imageView.setScaleY(scale);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (state == State.left && event.getAction() == MotionEvent.ACTION_UP) {
			visualStage++;
			Log.d("VisualAcuity", "Stage: "+visualStage);
			onNextImage();
		}
		else if (state == State.right && event.getAction() == MotionEvent.ACTION_UP) {
			visualStage++;
			onNextImage();
		}
		return super.onTouchEvent(event);
	}
}
