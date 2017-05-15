package aggrathon.eyewitnessapp.data;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import aggrathon.eyewitnessapp.MainActivity;
import aggrathon.eyewitnessapp.R;

public class ExperimentData {


	//region static instance

	private static ExperimentData instance;

	public static ExperimentData getInstance() {
		return instance;
	}

	public static boolean checkInstanced(Activity act) {
		if (instance != null)
			return true;
		if (act != null)
			act.startActivity(new Intent(act, MainActivity.class));
		return false;
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

	public static void createInstance(String language) {
		instance = new ExperimentData(language);
	}

	public enum LineupVariant {
		sequential,
		simultaneous
	}

	//endregion

	//Experiment Variables
	public LineupVariant lineup;
	public boolean targetPresent;
	public ArrayList<Bitmap> images;
	private ArrayList<String> imageLabels;
	public StringBuilder log;

	//Information
	public PersonalInformation personalInformation;
	public ArrayList<ExperimentIteration> data;

	private ExperimentData(String language) {
		Random rnd = new Random();
		LineupVariant[] vars = LineupVariant.values();
		lineup = vars[rnd.nextInt(vars.length)];
		//lineup = vars[1];
		targetPresent = rnd.nextBoolean();

		images = new ArrayList<>();
		imageLabels = new ArrayList<>();
		log = new StringBuilder();

		personalInformation = new PersonalInformation();
		personalInformation.language = language;
		data = new ArrayList<>();
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
			if(targetPresent)
				images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.test_vertical));
		}
		//Shuffle
		Random rnd = new Random();
		log.append("Image Sequence:");
		for (int i = 0; i < imageLabels.size(); i++) {
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

	public ExperimentIteration getLatestData() {
		if (data.size() < 1)
			return new ExperimentIteration();
		else
			return data.get(data.size()-1);
	}

	public void save() {

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(personalInformation.toString());
		sb.append("\n");

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

		sb.append("\nLog:\n\n");
		sb.append(log);

		return sb.toString();
	}
}
