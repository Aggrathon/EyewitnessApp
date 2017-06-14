package aggrathon.eyewitnessapp.data;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import aggrathon.eyewitnessapp.MainActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.StorageManager;

public class ExperimentData {

	public static final String CORRECT_TAG = "correct";
	public static final String MISSING_TAG = "missing";

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

	public static boolean isInstanced() {
		return instance != null;
	}

	public static boolean adultTheme() {
		if (instance == null)
			return false;
		if (instance.personalInformation == null)
			return false;
		return  instance.personalInformation.age > 10;
	}

	public static void clearInstance() {
		instance = null;
	}

	public static void createInstance(Activity activity, String language) {
		instance = new ExperimentData(activity, language);
	}

	public static void createSimpleInstance() {
		instance = new ExperimentData();
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

	//Information
	public PersonalInformation personalInformation;
	public ArrayList<ExperimentIteration> data;

	private ExperimentData(Activity activity, String language) {
		Random rnd = new Random();
		SharedPreferences prefs = activity.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		boolean normalise = prefs.getBoolean(SettingsActivity.LINEUP_NORMALISATION, true);

		float variation = getSettings(prefs, SettingsActivity.LINEUP_VARIATION, normalise, SettingsActivity.LINEUP_STATS_SEQUENTIAL, SettingsActivity.LINEUP_STATS_SIMULTANEOUS);
		if(rnd.nextFloat() > variation)
			lineup = LineupVariant.sequential;
		else
			lineup = LineupVariant.simultaneous;
		float presence = getSettings(prefs, SettingsActivity.LINEUP_TARGET, normalise, SettingsActivity.LINEUP_STATS_TARGET_ABSENT, SettingsActivity.LINEUP_STATS_TARGET_PRESENT);
		if (rnd.nextFloat() > presence)
			targetPresent = false;
		else
			targetPresent = true;

		images = new ArrayList<>();
		imageLabels = new ArrayList<>();

		int testNum = prefs.getInt(SettingsActivity.TEST_NUM_COUNTER, 1);
		String id = prefs.getString(SettingsActivity.DEVICE_ID, "")+testNum;
		while (StorageManager.checkLogFile(id))
			id = "" + (++testNum);
		personalInformation = new PersonalInformation(id, language);
		prefs.edit().putInt(SettingsActivity.TEST_NUM_COUNTER, testNum+1).commit();
		data = new ArrayList<>();
	}

	private ExperimentData() {
		lineup = LineupVariant.sequential;
		targetPresent = true;
		images = new ArrayList<>();
		imageLabels = new ArrayList<>();

		personalInformation = new PersonalInformation("", "non");
		data = new ArrayList<>();
	}

	private float getSettings(SharedPreferences prefs, String setting, boolean normalised, String statA, String statB) {
		float target = (float)prefs.getInt(setting, 50)/100f;
		if (target<0.04f) {
			return -1;
		}
		else if (target>0.96f) {
			return 2;
		}
		else if (normalised) {
			float a = prefs.getInt(statA, 1);
			float b = prefs.getInt(statB, 1);
			float stat = b / (a+b);
			return  2* target - stat;
		}
		else {
			return target;
		}
	}

	public void LoadImages(String id, Activity act) {
		images.clear();
		imageLabels.clear();
		for (File f: StorageManager.getImageList(id)) {
			String name = f.getName();
			if (!targetPresent && name.toLowerCase().contains(CORRECT_TAG)) {
				continue;
			}
			Bitmap bmp = BitmapFactory.decodeFile(f.getPath());
			if (bmp != null) {
				images.add(bmp);
				imageLabels.add(f.getName());
			}
			else {
				Log.d("Image Read", "Could not read bitmap from "+f.getPath());
			}
			if(images.size() == 8)
				break;
		}
		if(images.size() != 8) {
			Toast.makeText(act, R.string.notification_images_missing, Toast.LENGTH_LONG).show();
			for (int i = images.size(); i < 8; i++) {
				images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.placeholder_image));
				imageLabels.add(MISSING_TAG);
			}
		}
		//Shuffle
		Random rnd = new Random();
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
		}
	}

	public ExperimentIteration startExperimentIteration() {
		ExperimentIteration iter = new ExperimentIteration();
		data.add(iter);
		String comb = "";
		for (String s :imageLabels) {
			comb += s+" ";
		}
		iter.imageOrder = comb.substring(0, comb.length()-1);
		return iter;
	}

	public ExperimentIteration getLatestData() {
		if (data == null || data.size() < 1)
			return new ExperimentIteration();
		else
			return data.get(data.size()-1);
	}

	public void selectImage(int index) {
		if (data != null && data.size() > 0) {
			if (index > -1 && index < imageLabels.size()) {
				String img = imageLabels.get(index);
				data.get(data.size() - 1).selectedImage = img;
			}
			else {
				data.get(data.size() - 1).selectedImage = MISSING_TAG;
			}
		}
	}

	public void save(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		if (targetPresent)
			editor.putInt(SettingsActivity.LINEUP_STATS_TARGET_PRESENT, prefs.getInt(SettingsActivity.LINEUP_STATS_TARGET_PRESENT, 0)+1);
		else
			editor.putInt(SettingsActivity.LINEUP_STATS_TARGET_ABSENT, prefs.getInt(SettingsActivity.LINEUP_STATS_TARGET_ABSENT, 0)+1);
		switch (lineup) {
			case sequential:
				editor.putInt(SettingsActivity.LINEUP_STATS_SEQUENTIAL, prefs.getInt(SettingsActivity.LINEUP_STATS_SEQUENTIAL, 0)+1);
				break;
			case simultaneous:
				editor.putInt(SettingsActivity.LINEUP_STATS_SIMULTANEOUS, prefs.getInt(SettingsActivity.LINEUP_STATS_SIMULTANEOUS, 0)+1);
				break;
		}
		editor.commit();
		if(data != null || data.size() > 0)
			StorageManager.createLogfile(activity, toCsv(), CSV_HEADERS, personalInformation.testId);
	}

	public static final String CSV_HEADERS = "\"Time\","+PersonalInformation.getCsvHeaders()+",\"Iteration\",\"Lineup Presentation\",\"Target in Lineup\","+ExperimentIteration.getCsvHeader();

	public String[] toCsv() {
		String[] csvs = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			ExperimentIteration d = data.get(i);
			csvs[i] =
				'"'+d.time+ "\","+
				personalInformation.getCsvData() +"," +
				i + ",\"" +
				lineup.toString() + "\",\"" +
				targetPresent + "\"," +
				d.getCsvData();
		}
		return csvs;
	}

	public String getResultString() {
		int correct = 0;
		for (ExperimentIteration d : data) {
			if ((!targetPresent && d.selectedImage.equals(MISSING_TAG)) || d.selectedImage.toLowerCase().contains(CORRECT_TAG))
				correct++;
		}

		if (personalInformation.language.equals("swe") || true) {
			return "Du identifierade "+correct+" korrekt\n\nTack för ditt deltagande som ögonvittne!";
		}
		return "Du identifierade "+correct+" korrekt\n\nTack för ditt deltagande som ögonvittne!";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(personalInformation.toString());
		sb.append("\n");

		//Lineup
		sb.append("Lineup: ");
		switch (lineup) {
			case sequential:
				sb.append("Sequential and the target was ");
				break;
			case simultaneous:
				sb.append("Simultaneous and the target was ");
		}
		if(targetPresent)
			sb.append("present.\n\n");
		else
			sb.append("absent.\n\n");

		for (int i = 0; i < data.size(); i++) {
			sb.append(data.get(i).toString(i));
		}
		return sb.toString();
	}
}
