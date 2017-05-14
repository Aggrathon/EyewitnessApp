package aggrathon.eyewitnessapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;

public class NumberActivity extends ACancelCheckActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!ExperimentData.isInstanced()) {
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		setContentView(R.layout.activity_number);

		GridView grid = (GridView)findViewById(R.id.gridView);
		ArrayList<Integer> arr = new ArrayList<>();
		arr.add(R.drawable.one);
		arr.add(R.drawable.two);
		arr.add(R.drawable.three);
		arr.add(R.drawable.four);
		final Activity act = this;
		ImageGridAdapter gridAdapter = new ImageGridAdapter(this, arr, new ImageGridAdapter.OnSelectionListener() {
			@Override
			public void callback(int i) {
				ExperimentData.getInstance().log.append("Number ").append(i).append(" selected\n");
				startActivity(new Intent(act, LineupActivity.class));
			}
		});
		grid.setAdapter(gridAdapter);
	}

	public void OnEndButton(View v) {
		onBackPressed();
	}
}
