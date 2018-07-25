package aggrathon.eyewitnessapp.data;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import aggrathon.eyewitnessapp.MainActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.utils.CsvGenerator;
import aggrathon.eyewitnessapp.utils.StorageManager;

public class ExperimentData {

	public static final String CORRECT_TAG = "correct";
	public static final String SHOW_TAG = "show";
	public static final String EXTRA_TAG = "extra";
	public static final String MISSING_TAG = "missing";
	public static final int NUM_IMAGES = 10;

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

	public enum ShowVariant { live, video, image }

	//Experiment Variables
	public LineupVariant lineup;
	private ArrayList<String> imageLabels;
	public int numImages;
	public int timeLimit;
	public ShowVariant show;
	Random rnd;

	//Information
	public PersonalInformation personalInformation;
	public ArrayList<ExperimentIteration> data;

	private ExperimentData(Activity activity, String language) {
		rnd = new Random();
		SharedPreferences prefs = activity.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		boolean normalise = prefs.getBoolean(SettingsActivity.LINEUP_NORMALISATION, true);

		if(getSettings(
				prefs,
				SettingsActivity.LINEUP_VARIATION,
				normalise,
				SettingsActivity.LINEUP_STATS_SEQUENTIAL,
				SettingsActivity.LINEUP_STATS_SIMULTANEOUS,
				rnd))
			lineup = LineupVariant.simultaneous;
		else
			lineup = LineupVariant.sequential;

		imageLabels = new ArrayList<>();

		int testNum = prefs.getInt(SettingsActivity.TEST_NUM_COUNTER, 1);
		String id = prefs.getString(SettingsActivity.DEVICE_ID, "")+testNum;
		while (StorageManager.checkLogFile(id))
			id = "" + (++testNum);
		personalInformation = new PersonalInformation(testNum, language);
		prefs.edit().putInt(SettingsActivity.TEST_NUM_COUNTER, testNum+1).commit();
		data = new ArrayList<>();
		timeLimit = prefs.getInt(SettingsActivity.TIME_LIMIT, 0);

		int sl = prefs.getInt(SettingsActivity.SHOW_LIVE, 10);
		int sv = prefs.getInt(SettingsActivity.SHOW_VIDEO, 0);
		int si = prefs.getInt(SettingsActivity.SHOW_IMAGE, 0);
		int sum = sl + sv + si;
		int n = rnd.nextInt(sum);
		if (n <= sl)
			show = ShowVariant.live;
		else if (n - sl < sv)
			show = ShowVariant.video;
		else
			show = ShowVariant.image;
	}

	private ExperimentData() {
		lineup = LineupVariant.sequential;
		imageLabels = new ArrayList<>();

		personalInformation = new PersonalInformation(0, "non");
		data = new ArrayList<>();
		timeLimit = 0;
	}

	private boolean getSettings(SharedPreferences prefs, String setting, boolean normalised, String statA, String statB, Random rnd) {
		float target = (float)prefs.getInt(setting, 50)/100f;
		if (target<0.04f) {
			return false;
		}
		else if (target>0.96f) {
			return true;
		}
		else if (normalised) {
			return normalise(target, prefs.getInt(statA, 1), prefs.getInt(statB, 1), rnd);
		}
		else {
			return rnd.nextFloat() < target;
		}
	}

	public static boolean normalise(float target, float a, float b, Random rnd) {
		return rnd.nextFloat() <  2 * target  - b / (a + b);
	}

	public ArrayList<Bitmap> LoadImages(Activity act) {
		SharedPreferences prefs = act.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		ExperimentIteration iter = getLatestData();
		numImages = prefs.getInt(SettingsActivity.IMAGE_COUNT, 8);
		ArrayList<Bitmap> images = new ArrayList<>();
		imageLabels = new ArrayList<>();
		File[] fileList = StorageManager.getImageList(iter.lineupLabel); // Get the list of files
		//Read the bitmaps
		if (fileList != null) for (int i = 0; i < fileList.length; i++) {
			File f = fileList[i];
			String name = f.getName();
			String lower = name.toLowerCase();
			if (lower.contains(SHOW_TAG))
				continue;
			if (!lower.contains(".png") && !lower.contains(".jpg") && !lower.contains(".jpeg") && !lower.contains(".gif"))
				continue;
			if (!iter.targetPresent && lower.contains(CORRECT_TAG)) {
				continue;
			}
			Bitmap bmp = BitmapFactory.decodeFile(f.getPath());
			if (bmp != null) {
				images.add(bmp);
				imageLabels.add(name);
			}
			else {
				Log.d("Image Read", "Could not read bitmap from "+f.getPath());
			}
		}
		//Check if enough images
		if(images.size() < numImages) {
			Toast.makeText(act, R.string.notification_images_missing, Toast.LENGTH_LONG).show();
			for (int i = images.size(); i < numImages; i++) {
				images.add(BitmapFactory.decodeResource(act.getResources(), R.drawable.placeholder_image));
				imageLabels.add(MISSING_TAG);
			}
		}
		//If more images than wanted remove the extra image
		if (images.size() > numImages) {
			for (int i = 0; i < images.size(); i++) {
				if (imageLabels.get(i).toLowerCase().contains(EXTRA_TAG)) {
					images.remove(i).recycle();
					imageLabels.remove(i);
					break;
				}
			}
		}
		if (rnd == null)
			rnd = new Random();
		//If still more images than wanted remove random images
		while (images.size() > numImages) {
			int i = rnd.nextInt(images.size());
			if (iter.targetPresent && imageLabels.get(i).toLowerCase().contains(CORRECT_TAG))
				continue;
			images.remove(i).recycle();
			imageLabels.remove(i);
		}
		//Shuffle
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
		iter.setImages(imageLabels);
		return images;
	}

	public ExperimentIteration startExperimentIteration(Activity act, int number) {
		return startExperimentIteration(act, number, null);
	}
	public ExperimentIteration startExperimentIteration(Activity act, int number, String label) {
		SharedPreferences prefs = act.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		boolean normalise = prefs.getBoolean(SettingsActivity.LINEUP_NORMALISATION, true);
		boolean targetPresent = getSettings(
				prefs,
				SettingsActivity.LINEUP_TARGET,
				normalise,
				SettingsActivity.LINEUP_STATS_TARGET_ABSENT,
				SettingsActivity.LINEUP_STATS_TARGET_PRESENT,
				rnd);
		if (label == null) {
			label = Integer.toString(number);
		}
		ExperimentIteration iter = new ExperimentIteration(number, targetPresent, label);
		data.add(iter);
		return iter;
	}

	public ExperimentIteration getLatestData() {
		if (data == null || data.size() < 1) {
			if (imageLabels == null) {
				imageLabels = new ArrayList<>();
				for (int i = 0; i < NUM_IMAGES; i++)
					imageLabels.add(MISSING_TAG);
			}
			return new ExperimentIteration(0, true, "0");
		}
		else
			return data.get(data.size()-1);
	}

	public void save(Activity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		SharedPreferences.Editor editor = prefs.edit();
		int abs = 0;
		int pres = 0;
		for (ExperimentIteration d: data) {
			if (d.tutorial)
				continue;
			if (d.targetPresent)
				pres++;
			else
				abs++;
		}
		editor.putInt(SettingsActivity.LINEUP_STATS_TARGET_PRESENT, prefs.getInt(SettingsActivity.LINEUP_STATS_TARGET_PRESENT, 0) + pres);
		editor.putInt(SettingsActivity.LINEUP_STATS_TARGET_ABSENT, prefs.getInt(SettingsActivity.LINEUP_STATS_TARGET_ABSENT, 0) + abs);
		switch (lineup) {
			case sequential:
				editor.putInt(SettingsActivity.LINEUP_STATS_SEQUENTIAL, prefs.getInt(SettingsActivity.LINEUP_STATS_SEQUENTIAL, 0) + (abs+pres));
				break;
			case simultaneous:
				editor.putInt(SettingsActivity.LINEUP_STATS_SIMULTANEOUS, prefs.getInt(SettingsActivity.LINEUP_STATS_SIMULTANEOUS, 0) + (abs+pres));
				break;
		}
		editor.commit();
		saveCsv(activity, prefs);
	}

	public static float getScreenSize() {
		DisplayMetrics dm = new DisplayMetrics();
		double width = (double)dm.widthPixels / (double)dm.xdpi;
		double height = (double)dm.heightPixels / (double)dm.ydpi;
		double size = Math.sqrt(width*width+ height*height);
		return (float)size;
	}

	private void saveCsv(Activity activity, SharedPreferences prefs) {
		if(data != null || data.size() > 0) {
			CsvGenerator csv = new CsvGenerator();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
			sd.setTimeZone(TimeZone.getDefault());
			st.setTimeZone(TimeZone.getDefault());
			for (ExperimentIteration d : data) {
				csv.beginRow();
				csv.addString("Date", sd.format(d.time));
				csv.addString("Time", st.format(d.time));
				csv.addString("Tablet_ID", prefs.getString(SettingsActivity.DEVICE_ID, ""));
				csv.addFloat("Sceen_Size", getScreenSize());
				csv.addInt("Pt_ID", personalInformation.testId);
				csv.addString("Manual_ID", personalInformation.personalId);
				csv.addString("Pt_language", personalInformation.language);
				csv.addString("Pt_nationality", personalInformation.nationality);
				csv.addInt("Pt_age", personalInformation.age);
				csv.addInt("Pt_height", personalInformation.height);
				csv.addString("Pt_gender", personalInformation.sex);
				csv.addBooleanAsInt("Pt_participated_before", personalInformation.previousParticipations);
				csv.addFloat("Pt_left_eye", personalInformation.visualAcuityLeft);
				csv.addFloat("Pt_right_eye", personalInformation.visualAcuityRight);
				csv.addFloat("Pt_average_eye", personalInformation.visualAcuityRight*0.5f + personalInformation.visualAcuityLeft*0.5f);
				csv.addFloat("Pt_min_eye", personalInformation.visualAcuityRight < personalInformation.visualAcuityLeft? personalInformation.visualAcuityRight : personalInformation.visualAcuityLeft);
				csv.addFloat("Pt_max_eye", personalInformation.visualAcuityRight > personalInformation.visualAcuityLeft? personalInformation.visualAcuityRight : personalInformation.visualAcuityLeft);
				csv.addString("Experiment_type", d.tutorial? "testrunda" : "huvudexperiment");
				csv.addString("Show_type", (show == ShowVariant.live? "live" : (show == ShowVariant.video? "video" : "image")));
				csv.addString("Show", d.show);
				csv.addInt("Show_distance", d.showDistance);
				csv.addString("Lineup_type", lineup.toString());
				csv.addBooleanAsInt("Target_present", d.targetPresent);
				csv.addInt("Lineup_number", d.lineupNumber);
				csv.addInt("Lineup_size", numImages);
				csv.addFloat("Lineup_time_limit", (float)timeLimit*0.1f);
				switch (lineup) {
					case sequential:
						for (int i = 0; i < NUM_IMAGES; i++) {
							csv.addString("Seq_image" + (i + 1), d.getImageOrder(i));
							csv.addBooleanAsInt("Seq_image" + (i + 1)+"_choice", d.selectedImage == i);
							csv.addFloat("Seq_image" + (i + 1)+"_time", d.getImageTime(i));
						}
						for (int i = 0; i < NUM_IMAGES; i++)
							csv.addEmpty("Sim_row"+(i*2/NUM_IMAGES+1)+"_image"+(i%(NUM_IMAGES/2)+1));
						break;
					case simultaneous:
						for (int i = 0; i < NUM_IMAGES; i++) {
							csv.addEmpty("Seq_image" + (i + 1));
							csv.addEmpty("Seq_image" + (i + 1)+"_choice");
							csv.addEmpty("Seq_image" + (i + 1)+"_time");
						}
						for (int i = 0; i < NUM_IMAGES; i++)
							csv.addString("Sim_row"+(i*2/NUM_IMAGES+1)+"_image"+(i%(NUM_IMAGES/2)+1), d.getImageOrder(i));
						break;
				}
				csv.addFloat("Simultaneous_choice_time", d.lineupTime);
				csv.addBooleanAsInt("rejection", d.selectedImage < 0);
				csv.addFloat("Lineup_total_time", d.lineupTime);
				csv.addString("Selected_image", d.getSelectedImage());
				csv.addBooleanAsInt("Identification", d.selectionIsCorrect());
				if(d.targetPresent) {
					csv.addBooleanAsInt("Target_present_identification", d.selectionIsCorrect());
					csv.addString("Target_absent_identification", "N/A");
				}
				else {
					csv.addString("Target_present_identification", "N/A");
					csv.addBooleanAsInt("Target_absent_identification", d.selectedImage < 0);
				}
				csv.addInt("Confidence", d.confidence);
				csv.addInt("Target_height", d.targetHeight);
				csv.addInt("Target_weight", d.targetWeight);
				csv.addString("Target_gender", d.targetSex);
				csv.addInt("Target_age", d.age);
				csv.addInt("Target_distance", d.distance);
				csv.addBooleanAsInt("Recognise_chosen", d.recognisedTarget);
				csv.addBooleanAsInt("Recognise_other", d.recognisedOther);
			}
			if(csv.hasContent())
				StorageManager.createLogfile(activity, csv.getValues(), csv.getHeader(), prefs.getString(SettingsActivity.DEVICE_ID, ""), Integer.toString(personalInformation.testId));
		}
	}

	public int[] getResult() {
		int correct = 0;
		int missings = 0;
		int correct_miss = 0;
		for (ExperimentIteration d : data) {
			if(d.tutorial)
				continue;
			if (d.targetPresent) {
				if (d.selectionIsCorrect())
					correct++;
			}
			else {
				missings++;
				if (d.selectionIsCorrect())
					correct_miss++;
			}
		}
		return new int[]{correct, missings, correct_miss};
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
				sb.append("Sequential");
				break;
			case simultaneous:
				sb.append("Simultaneous");
		}
		sb.append("\n\n");

		for (int i = 0; i < data.size(); i++) {
			sb.append(data.get(i).toString(i));
		}
		return sb.toString();
	}
}
