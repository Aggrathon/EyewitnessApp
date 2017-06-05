package aggrathon.eyewitnessapp.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ExperimentIteration {

	public String time;
	public int lineupNumber;
	public String imageOrder;
	public String selectedImage;
	public String targetSex;
	public int targetHeight;
	public int targetWeight;
	public int confidence;
	public int distance;
	public boolean recognisedTarget;
	public boolean recognisedOther;


	public ExperimentIteration() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getDefault());
		time = sdf.format(new Date());
		imageOrder = "";
		selectedImage = "";
		targetSex = "";
	}

	public String toString(int index) {
		return "Experiment Iteration "+index+":"+
				"\n\tTime: "+time+
				"\n\tNumber: "+lineupNumber+
				"\n\tImage Order: "+imageOrder+
				"\n\tSelected: "+selectedImage+
				"\n\tTarget sex: "+targetSex+
				"\n\tTarget Height: "+targetHeight+
				"\n\tTarget Weight: "+targetWeight+
				"\n\tConfidence: "+confidence+
				"\n\tDistance To Target: "+distance+
				"\n\tRecognised Target: "+recognisedTarget+
				"\n\tRecognised Other: "+recognisedOther+"\n";
	}

	public static String getCsvHeader() {
		return "\"Lineup Number\",\"Image Order\",\"Selected Image\",\"Confidence\",\"Target Height\","+
				"\"Target Weight\",\"Target Sex\",\"Target Distance\",\"Recognised Target\",\"Recognised Other\"";
	}

	public String getCsvData() {
		return
			lineupNumber +",\"" +
			imageOrder + "\",\"" +
			selectedImage + "\"," +
			confidence + "," +
			targetHeight + "," +
			targetWeight + ",\"" +
			targetSex + "\"," +
			distance + ",\"" +
			recognisedTarget + "\",\"" +
			recognisedOther + "\"";
	}
}
