package aggrathon.eyewitnessapp.data;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import aggrathon.eyewitnessapp.MainActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.StorageManager;

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
				data.get(data.size() - 1).selectedImage = img.equals("_Correct")? "correct" : img;
			}
			else {
				data.get(data.size() - 1).selectedImage = "missing";
			}
		}
	}

	public void save(Activity activity) {
		StorageManager.createLogfile(activity, toCsv(), CSV_HEADERS);
	}

	public static final String CSV_HEADERS = TextUtils.join("\",\"",new String[] {
			"\"Time", "Language", "Test Number", "Language", "Nationality", "Home Country", "Age",
			"Height", "Sex", "Previous Participant", "Iteration", "Lineup Number", "Lineup Presentation",
			"Target in Lineup", "Image Order", "Selected Image", "Confidence", "Target Height",
			"Target Weight", "Target Sex", "Target Distance", "Recognised Target", "Recognised Other\""
	});
	public String[] toCsv() {
		String[] csvs = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			ExperimentIteration d = data.get(i);
			csvs[i] =
				'"'+d.time+ "\","+
				personalInformation.testNumber +",\""+
				personalInformation.language +"\",\""+
				personalInformation.nationality.replace("\"","") +"\",\""+
				personalInformation.country.replace("\"","") + "\"," +
				personalInformation.age + ","+
				personalInformation.height +",\""+
				personalInformation.sex +",\"" +
				personalInformation.previousParticipations +"\"," +
				i + ",\"" +
				lineup.toString() + "\",\"" +
				targetPresent + "\"," +
				d.lineupNumber +",\"" +
				d.imageOrder + "\",\"" +
				d.selectedImage + "\"," +
				d.confidence + "," +
				d.targetHeight + "," +
				d.targetWeight + ",\"" +
				d.targetSex + "\"," +
				d.distance + ",\"" +
				d.recognisedTarget + "\",\"" +
				d.recognisedOther + "\"";
		}
		return csvs;
	}

	public String getResultString() {
		int correct = 0;
		for (ExperimentIteration d : data) {
			if (d.selectedImage.equals("correct"))
				correct++;
		}

		if (personalInformation.language.equals("swe") || true) {
			return "Du identifierade "+correct+" korrekta\n\nTack för ditt deltagande som ögonvittne!";
		}
		return "Du identifierade "+correct+" korrekta\n\nTack för ditt deltagande som ögonvittne!";
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
