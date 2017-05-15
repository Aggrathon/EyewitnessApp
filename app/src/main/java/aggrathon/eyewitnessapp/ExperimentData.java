package aggrathon.eyewitnessapp;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ExperimentData {


	//region static instance

	private static ExperimentData instance;

	public static ExperimentData getInstance() {
		return instance;
	}

	public static boolean isInstanced() {
		return (instance != null);
	}

	public static void clearInstance() {
		instance = null;
	}

	public static void clearInstance(boolean save)
	{
		if (instance != null && save)
			instance.save();
		instance = null;
	}

	public static void createInstance() {
		instance = new ExperimentData();
	}

	public enum LineupVariant {
		sequential,
		simultaneous
	}

	//endregion


	public LineupVariant lineup;
	public boolean targetPresent;
	public ArrayList<Bitmap> images;
	public ArrayList<String> imageLabels;
	public StringBuilder log;

	private ExperimentData() {
		Random rnd = new Random();
		LineupVariant[] vars = LineupVariant.values();

		lineup = vars[rnd.nextInt(vars.length)];
		//lineup = vars[1];
		targetPresent = rnd.nextBoolean();

		images = new ArrayList<>();
		imageLabels = new ArrayList<>();
		log = new StringBuilder();
	}

	public void LoadImages(String id, Activity act) {
		images.clear();
		imageLabels.clear();
		imageLabels.addAll(Arrays.asList(new String[] {"A", "B", "C", "D", "E", "F", "G", "_Correct"}));
		if (!targetPresent)
			imageLabels.remove(imageLabels.size()-1);
		try {
			for (int i = 0; i < imageLabels.size(); i++) {
				images.add(BitmapFactory.decodeStream(act.getAssets().open(id+"/"+id+imageLabels.get(i)+".jpg")));
			}
		}
		catch (IOException e) {
			Log.e("Load Images", "Could not load images for "+id);
			Toast.makeText(act, "Could not read all images", Toast.LENGTH_LONG).show();
			images.clear();
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_horizontal));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_horizontal));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_horizontal));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_horizontal));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_vertical));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_vertical));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_vertical));
			images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_vertical));
		}
		//Shuffle
		Random rnd = new Random();
		log.append("Image Sequence:");
		for (int i = 0; i < images.size(); i++) {
			Bitmap a = images.get(i);
			String as = imageLabels.get(i);
			int r = rnd.nextInt(images.size()-i)+i;
			Bitmap b = images.get(r);
			String bs = imageLabels.get(r);
			images.set(i, b);
			images.set(r, a);
			imageLabels.set(r, as);
			imageLabels.set(i, bs);
			log.append(" ").append(bs);
		}
		log.append('\n');
	}

	public void save() {

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		//Lineup
		sb.append("Lineup:\n\t");
		switch (lineup) {
			case sequential:
				sb.append("Sequential and the target was ");
				break;
			case simultaneous:
				sb.append("Simultaneous and the target was ");
		}
		if(targetPresent)
			sb.append("present.\n");
		else
			sb.append("absent.\n");

		sb.append('\n');
		sb.append(log);

		return sb.toString();
	}
}
