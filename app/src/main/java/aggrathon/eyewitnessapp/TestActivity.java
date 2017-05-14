package aggrathon.eyewitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

	private GridView grid;
	private ImageGridAdapter gridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!ExperimentData.isInstanced()) {
			Log.d("Test", "No Data");
			startActivity(new Intent(this, MainActivity.class));
			return;
		}
		setContentView(R.layout.activity_test);

		grid = (GridView)findViewById(R.id.gridView);
		ArrayList<Integer> arr = new ArrayList();
		for (int i = 0; i < 8; i++)
			arr.add(i);
		gridAdapter = new ImageGridAdapter(this, R.layout.image_selector_button, arr);
		grid.setAdapter(gridAdapter);
	}

}
