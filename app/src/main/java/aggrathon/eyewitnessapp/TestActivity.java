package aggrathon.eyewitnessapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

	private GridView grid;
	private ImageGridAdapter gridAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		grid = (GridView)findViewById(R.id.gridView);
		ArrayList<Integer> arr = new ArrayList();
		for (int i = 0; i < 8; i++)
			arr.add(i);
		gridAdapter = new ImageGridAdapter(this, R.layout.image_selector_button, arr);
		grid.setAdapter(gridAdapter);
	}

}
