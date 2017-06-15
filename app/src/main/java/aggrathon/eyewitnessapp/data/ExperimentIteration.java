package aggrathon.eyewitnessapp.data;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentIteration {

	public Date time;
	public int lineupNumber;
	public ArrayList<String> imageOrder;
	public String selectedImage;
	public String targetSex;
	public int targetHeight;
	public int targetWeight;
	public int confidence;
	public int distance;
	public int age;
	public boolean recognisedTarget;
	public boolean recognisedOther;
	public boolean tutorial;


	public ExperimentIteration() {
		time = new Date();
		selectedImage = "";
		targetSex = "";
	}

	public String toString(int index) {
		String order = "";
		if(imageOrder != null)
			for (String s : imageOrder)
				order += s+" ";
		return "Experiment Iteration "+index+":"+
				"\n\tTime: "+time+
				"\n\tTutorial: "+tutorial+
				"\n\tNumber: "+lineupNumber+
				"\n\tImage Order: "+order+
				"\n\tSelected: "+selectedImage+
				"\n\tTarget sex: "+targetSex+
				"\n\tTarget Height: "+targetHeight+
				"\n\tTarget Weight: "+targetWeight+
				"\n\tTarget Age: "+age+
				"\n\tConfidence: "+confidence+
				"\n\tDistance To Target: "+distance+
				"\n\tRecognised Target: "+recognisedTarget+
				"\n\tRecognised Other: "+recognisedOther+"\n";
	}

	public boolean selectionIsCorrect(ExperimentData data) {
		return selectedImage.toLowerCase().contains(ExperimentData.CORRECT_TAG) || (selectedImage.equals(ExperimentData.MISSING_TAG) && !data.targetPresent);
	}
}
