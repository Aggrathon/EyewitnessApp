package aggrathon.eyewitnessapp.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.experiment.LineupActivity;
import aggrathon.eyewitnessapp.experiment.NumberActivity;
import aggrathon.eyewitnessapp.experiment.QuestionsActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class TutorialActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.checkInstanced(this))
			return;
		setContentView(R.layout.activity_tutorial);
	}

	public void onNextButton(View v) {
		startActivity(new Intent(this, TutorialNumbers.class));
	}


	public static class TutorialNumbers extends NumberActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			findViewById(R.id.endButton).setVisibility(View.GONE);
		}

		@Override
		public void OnNumberOne(View v) {
			ExperimentData.getInstance().LoadImages("Test", this);
			startActivity(new Intent(this, TutorialLineup.class));
		}

		@Override
		public void OnNumberTwo(View v) {
			ExperimentData.getInstance().LoadImages("Test", this);
			startActivity(new Intent(this, TutorialLineup.class));
		}

		@Override
		public void OnNumberThree(View v) {
			ExperimentData.getInstance().LoadImages("Test", this);
			startActivity(new Intent(this, TutorialLineup.class));
		}

		@Override
		public void OnNumberFour(View v) {
			ExperimentData.getInstance().LoadImages("Test", this);
			startActivity(new Intent(this, TutorialLineup.class));
		}
	}


	public static class TutorialLineup extends LineupActivity {

		@Override
		public void onTargetMissingButton(View v) {
			startActivity(new Intent(this, TutorialQuestions.class));
		}

		@Override
		public void onSelectImageButton(View v) {
			startActivity(new Intent(this, TutorialQuestions.class));
		}
	}


	public static class TutorialQuestions extends QuestionsActivity {
		@Override
		public void onNextButton(View v) {
			startActivity(new Intent(this, TutorialEnd.class));
		}
	}

	public static class TutorialEnd extends TutorialActivity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			((TextView)findViewById(R.id.tutorialText)).setText(R.string.tutorialInstructions2);
		}

		@Override
		public void onNextButton(View v) {
			startActivity(new Intent(this, NumberActivity.class));
		}
	}
}
