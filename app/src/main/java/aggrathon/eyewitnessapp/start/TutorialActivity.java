package aggrathon.eyewitnessapp.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import aggrathon.eyewitnessapp.AMessageActivity;
import aggrathon.eyewitnessapp.experiment.LineupActivity;
import aggrathon.eyewitnessapp.experiment.NumberActivity;
import aggrathon.eyewitnessapp.experiment.QuestionsActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;

public class TutorialActivity extends AMessageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ExperimentData.checkInstanced(null)) {
			titleText.setText(R.string.title_activity_tutorial);
			messageText.setText(R.string.tutorialInstructions1);
			final Activity act = this;
			nextButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, TutorialNumbers.class));
				}
			});
		}
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

	public static class TutorialEnd extends AMessageActivity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			if(ExperimentData.checkInstanced(null)) {
				titleText.setText(R.string.title_activity_tutorial);
				messageText.setText(R.string.tutorialInstructions2);
				final Activity act = this;
				nextButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(act, NumberActivity.class));
					}
				});
			}
		}
	}
}
