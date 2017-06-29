package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

	static final int MAX_VISUAL_STAGE = 32;
	static final float TARGET_ACCURACY = 0.5625f;
	static final float TARGET_WINDOW = 0.2f;
	static final float IMAGE_STARTING_SIZE = 0.65f;
	static final float IMAGE_SCALING = 0.75f;
	static final int NUM_IMAGES = 18;

	public enum State {
		instructions1,
		left,
		instructions2,
		right,
		instructions3,
		next
	}


	ImageView imageView;
	ImageView directionView;
	TextView textView;
	View textPanel;
	Button nextButton;
	Random rnd;
	ImageView leftEyeImage;
	ImageView rightEyeImage;

	State nextState;
	int visualStage = 0;
	int numImagesShown = 0;
	int[] numCorrect;
	int[] numWrong;
	int rotation;
	boolean startLeft;
	boolean hasBoth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.checkInstanced(this)) {
			return;
		}
		setContentView(R.layout.activity_visual_acuity);
		textView = (TextView)findViewById(R.id.textView);
		textPanel = findViewById(R.id.textPanel);
		imageView = (ImageView)findViewById(R.id.imageView);
		nextButton = (Button)findViewById(R.id.nextButton);
		directionView = (ImageView)findViewById(R.id.directionView);
		imageView.setImageResource(R.drawable.landolt_c);
		leftEyeImage = (ImageView)findViewById(R.id.leftEyeImage);
		rightEyeImage = (ImageView)findViewById(R.id.rightEyeImage);
		visualStage = 0;
		rnd = new Random();
		numCorrect = new int[MAX_VISUAL_STAGE];
		numWrong = new int[MAX_VISUAL_STAGE];
		nextState = State.instructions1;
		onNext(null);
	}

	public void onNext(View v) {
		switch (nextState) {
			case instructions1:
				textPanel.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				directionView.setVisibility(View.INVISIBLE);
				setTitle(R.string.title_visual_acuity);
				startLeft = rnd.nextBoolean();
				hasBoth = false;
				if(startLeft) {
					Resources sys = getResources();
					textView.setText(sys.getString(R.string.text_visual_acuity_instructions1)+"\n\n"+sys.getString(R.string.text_close_eye_left));
					leftEyeImage.setImageResource(R.drawable.hand);
					rightEyeImage.setImageResource(R.drawable.eye);
					rightEyeImage.setRotationY(0);
					nextState = State.left;
				}
				else {
					Resources sys = getResources();
					textView.setText(sys.getString(R.string.text_visual_acuity_instructions1)+"\n\n"+sys.getString(R.string.text_close_eye_right));
					leftEyeImage.setImageResource(R.drawable.eye);
					rightEyeImage.setImageResource(R.drawable.hand);
					rightEyeImage.setRotationY(180);
					nextState = State.right;
				}
				break;
			case left:
				textPanel.setVisibility(View.INVISIBLE);
				imageView.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.INVISIBLE);
				directionView.setVisibility(View.VISIBLE);
				setTitle(R.string.title_visual_acuity2);
				nextState = hasBoth? State.instructions3 : State.instructions2;
				resetTest();
				break;
			case instructions2:
				textPanel.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				directionView.setVisibility(View.INVISIBLE);
				setTitle(R.string.title_visual_acuity);
				if (startLeft) {
					Resources sys = getResources();
					textView.setText(sys.getText(R.string.text_visual_acuity_instructions2)+"\n\n"+sys.getText(R.string.text_close_eye_right));
					hasBoth = true;
					leftEyeImage.setImageResource(R.drawable.eye);
					rightEyeImage.setImageResource(R.drawable.hand);
					rightEyeImage.setRotationY(180);
					nextState = State.right;
					ExperimentData.getInstance().personalInformation.visualAcuityLeft = changeResultScale(calculateOptimalStage());
				}
				else {
					Resources sys = getResources();
					textView.setText(sys.getText(R.string.text_visual_acuity_instructions2)+"\n\n"+sys.getText(R.string.text_close_eye_left));
					hasBoth = true;
					leftEyeImage.setImageResource(R.drawable.hand);
					rightEyeImage.setImageResource(R.drawable.eye);
					rightEyeImage.setRotationY(0);
					nextState = State.left;
					ExperimentData.getInstance().personalInformation.visualAcuityRight = changeResultScale(calculateOptimalStage());
				}
				break;
			case right:
				textPanel.setVisibility(View.INVISIBLE);
				imageView.setVisibility(View.VISIBLE);
				nextButton.setVisibility(View.INVISIBLE);
				directionView.setVisibility(View.VISIBLE);
				setTitle(R.string.title_visual_acuity2);
				nextState = hasBoth? State.instructions3 : State.instructions2;
				resetTest();
				break;
			case instructions3:
				textPanel.setVisibility(View.VISIBLE);
				imageView.setVisibility(View.INVISIBLE);
				nextButton.setVisibility(View.VISIBLE);
				directionView.setVisibility(View.INVISIBLE);
				textView.setText(R.string.text_visual_acuity_instructions3);
				setTitle(R.string.title_visual_acuity);
				nextState = State.next;
				if (startLeft)
					ExperimentData.getInstance().personalInformation.visualAcuityRight = changeResultScale(calculateOptimalStage());
				else
					ExperimentData.getInstance().personalInformation.visualAcuityLeft = changeResultScale(calculateOptimalStage());
				break;
			case next:
				startActivity(new Intent(this, TutorialActivity.class));
				finish();
				break;
		}
	}

	private void resetTest() {
		numImagesShown = -1;
		visualStage = 0;
		for (int i = 0; i < numCorrect.length; i++) {
			numCorrect[i] = 0;
		}
		for (int i = 0; i < numWrong.length; i++) {
			numWrong[i] = 0;
		}
		numCorrect[0]--;
		onNextImage(true);
	}

	private float getImageScale(float stage) {
		return IMAGE_STARTING_SIZE * (float) Math.pow(IMAGE_SCALING, visualStage);
	}

	private void onNextImage(boolean correct) {
		int origStage = visualStage;
		if(numImagesShown != 0 && numImagesShown%6 == 0)
			visualStage = Math.max(0, visualStage-4);

		if (correct)
			numCorrect[visualStage]++;
		else
			numWrong[visualStage]++;

		if(numImagesShown != 0 && numImagesShown%6 == 0)
			visualStage = origStage;
		else {
			int fastFind = 0;
			for (int c : numWrong)
				fastFind += c;
			float accuracy = (float) numCorrect[visualStage] / (float) (numCorrect[visualStage] + numWrong[visualStage]);
			if (fastFind == 0 || (fastFind == 1 && !correct)) { //fast find
				if (accuracy > TARGET_ACCURACY + TARGET_WINDOW)
					visualStage += 2;
				else if (accuracy < TARGET_ACCURACY - TARGET_WINDOW)
					visualStage--;
			} else {
				if (accuracy > TARGET_ACCURACY + TARGET_WINDOW/2)
					visualStage++;
				else if (accuracy < TARGET_ACCURACY - TARGET_WINDOW)
					visualStage--;
			}
			visualStage = visualStage < 0 ? 0 : visualStage >= MAX_VISUAL_STAGE ? MAX_VISUAL_STAGE - 1 : visualStage;
		}

		numImagesShown++;
		if(numImagesShown > NUM_IMAGES) {
			onNext(null);
			return;
		}

		if(numImagesShown != 0 && numImagesShown%6 == 0) {
			origStage = visualStage;
			visualStage = Math.max(0, visualStage - 4);
		}

		rotation = rnd.nextInt(8)*45;
		imageView.setRotation(rotation);
		float scale = getImageScale(visualStage);
		imageView.setScaleX(scale);
		imageView.setScaleY(scale);

		if(numImagesShown != 0 && numImagesShown%6 == 0)
			visualStage = origStage;
	}

	private float calculateOptimalStage() {
		int level = 0;
		float accuracy = 0;
		for (int i = 0; i < MAX_VISUAL_STAGE; i++) {
			float tmpAcc = (float)numCorrect[i] / (float)(numCorrect[i]+numWrong[i]);
			if (tmpAcc > TARGET_ACCURACY-TARGET_WINDOW) {
				level = i;
				accuracy = tmpAcc;
			}
		}
		if (Math.abs(accuracy-TARGET_ACCURACY) < TARGET_WINDOW*0.5f)
			return level;
		else if (accuracy < TARGET_ACCURACY) {
			if (level == 0)
				return 0;
			float prevAcc = (float)numCorrect[level-1] / (float)(numCorrect[level-1]+numWrong[level-1]);
			if (prevAcc > TARGET_ACCURACY+TARGET_WINDOW)
				return (float)level-0.25f;
			else
				return (float)level-0.5f;
		}
		else {
			if(level == MAX_VISUAL_STAGE-1)
				return MAX_VISUAL_STAGE;
			if (accuracy > TARGET_ACCURACY+TARGET_WINDOW)
				return (float)level +0.5f;
			else
				return (float)level +0.25f;
		}
	}

	private float changeResultScale(float stage) {
		float scale = getImageScale(stage)/IMAGE_STARTING_SIZE;
		return (float)Math.pow(1-scale, 4);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if ((nextState == State.instructions2 || nextState == State.instructions3) && event.getAction() == MotionEvent.ACTION_UP) {
			MotionEvent.PointerCoords pos = new MotionEvent.PointerCoords();
			event.getPointerCoords(0, pos);
			View parent = directionView.getRootView();
			float dx = (float) (parent.getLeft() + parent.getRight())/2 - pos.x;
			float dy = (float) (parent.getTop() + parent.getBottom())/2 - pos.y;
			int touchRot = (int)(-Math.toDegrees(Math.atan2(dx,dy))+270)%360;
			onNextImage(Math.abs(rotation - touchRot) < 22.5 || Math.abs(rotation + 360 - touchRot) < 22.5);
			return true;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if(keyCode == 75) {
			nextState = State.next;
			onNext(null);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
}
