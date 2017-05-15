package aggrathon.eyewitnessapp;


import java.util.ArrayList;
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
	public ArrayList<Integer> images;
	public StringBuilder log;

	private ExperimentData() {
		Random rnd = new Random();
		LineupVariant[] vars = LineupVariant.values();

		lineup = vars[rnd.nextInt(vars.length)];
		//lineup = vars[0];
		targetPresent = rnd.nextBoolean();

		images = new ArrayList<>();
		images.add(R.drawable.flag_of_the_united_kingdom);
		images.add(R.drawable.flag_of_finland);
		images.add(R.drawable.flag_of_sweden);
		images.add(R.drawable.one);
		images.add(R.drawable.two);
		images.add(R.drawable.three);
		images.add(R.drawable.four);
		images.add(R.mipmap.icon);

		log = new StringBuilder();
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
