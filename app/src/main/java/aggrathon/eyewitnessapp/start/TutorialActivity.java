package aggrathon.eyewitnessapp.start;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import aggrathon.eyewitnessapp.AMessageActivity;
import aggrathon.eyewitnessapp.experiment.LineupActivity;
import aggrathon.eyewitnessapp.experiment.NumberActivity;
import aggrathon.eyewitnessapp.experiment.QuestionsActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.experiment.TargetQuestionsActivity;
import aggrathon.eyewitnessapp.view.ImageButtonGrid;

public class TutorialActivity extends AMessageActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(ExperimentData.checkInstanced(null)) {
			final Activity act = this;
			setMessage(R.string.title_tutorial, R.string.text_tutorial_instructions1, new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					startActivity(new Intent(act, TutorialNumbers.class));
					finish();
				}
			});
		}
	}

	public static class TutorialNumbers extends NumberActivity {

		@SuppressLint("MissingSuperCall")
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState, new ImageButtonGrid.OnCLick() {
				@Override
				public void OnClick(int i) {
					NextActivity(i);
				}
			});
			findViewById(R.id.endButton).setVisibility(View.GONE);
		}

		@Override
		public void NextActivity(int number) {
			ExperimentData.getInstance().startExperimentIteration(this, number, "Test").tutorial = true;
			startActivity(new Intent(this, TutorialWait.class));
			finish();
		}
	}

	public static class TutorialWait extends AMessageActivity {
		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			final Activity act = this;
			setMessage(R.string.title_tutorial, R.string.text_wait_instructions, new View.OnClickListener() {
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
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setTitle(R.string.title_tutorial);
		}

		@Override
		public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
			super.onCreate(savedInstanceState, persistentState);
			setTitle(R.string.title_tutorial);
		}

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
		public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
			super.onCreate(savedInstanceState, persistentState);
			setTitle(R.string.title_tutorial);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setTitle(R.string.title_tutorial);
		}

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
				final Activity act = this;
				setMessage(R.string.title_tutorial, R.string.text_tutorial_instructions2, new View.OnClickListener() {
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
