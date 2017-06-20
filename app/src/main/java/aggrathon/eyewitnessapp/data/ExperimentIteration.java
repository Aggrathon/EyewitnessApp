package aggrathon.eyewitnessapp.data;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentIteration {

	public Date time;
	public boolean targetPresent;
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


	public ExperimentIteration(int number, boolean targetPresent, ArrayList<String> imageOrder) {
		time = new Date();
		selectedImage = "";
		targetSex = "";
		lineupNumber = number;
		this.targetPresent = targetPresent;
		this.imageOrder = imageOrder;
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
				"\n\tTarget Present: "+targetPresent+
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

	public boolean selectionIsCorrect() {
		return selectedImage.toLowerCase().contains(ExperimentData.CORRECT_TAG) || (selectedImage.equals(ExperimentData.MISSING_TAG) && !targetPresent);
	}
}
