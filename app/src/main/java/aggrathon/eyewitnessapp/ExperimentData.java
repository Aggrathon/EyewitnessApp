package aggrathon.eyewitnessapp;


public class ExperimentData {

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

	public static void createInstance() {
		instance = new ExperimentData();
	}

	public enum LineupVariant {
		sequential,
		simultaneous
	}


	public LineupVariant lineup;
	public boolean targetPresent;

	private ExperimentData() {
		lineup = LineupVariant.simultaneous;
		targetPresent = true;
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

		return sb.toString();
	}
}
