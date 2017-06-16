package aggrathon.eyewitnessapp.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import aggrathon.eyewitnessapp.AMessageActivity;
import aggrathon.eyewitnessapp.experiment.LineupActivity;
import aggrathon.eyewitnessapp.experiment.NumberActivity;
import aggrathon.eyewitnessapp.experiment.QuestionsActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.experiment.TargetQuestionsActivity;
import aggrathon.eyewitnessapp.experiment.WaitActivity;

public class TutorialActivity extends AMessageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ExperimentData.checkInstanced(null)) {
			messageText.setText(R.string.text_tutorial_instructions1);
			final Activity act = this;
			nextButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, TutorialNumbers.class));
					finish();
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
		public void NextActivity() {
			ExperimentData.getInstance().LoadImages("Test", this);
			ExperimentData.getInstance().getLatestData().tutorial = true;
			startActivity(new Intent(this, TutorialWait.class));
			finish();
		}
	}

	public static class TutorialWait extends WaitActivity {
		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			final Activity act = this;
			nextButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, TutorialTargetQuestions.class));
					finish();
				}
			});
		}
	}

	public static class TutorialTargetQuestions extends TargetQuestionsActivity {
		@Override
		public void NextActivity() {
			startActivity(new Intent(this, TutorialLineup.class));
			finish();
		}
	}

	public static class TutorialLineup extends LineupActivity {

		@Override
		public void NextActivity() {
			startActivity(new Intent(this, TutorialQuestions.class));
			finish();
		}
	}

	public static class TutorialQuestions extends QuestionsActivity {
		@Override
		public void NextActivity() {
			startActivity(new Intent(this, TutorialEnd.class));
			finish();
		}
	}

	public static class TutorialEnd extends AMessageActivity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			if(ExperimentData.checkInstanced(null)) {
				messageText.setText(R.string.text_tutorial_instructions2);
				final Activity act = this;
				nextButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(act, NumberActivity.class));
						finish();
					}
				});
			}
		}
	}
}
