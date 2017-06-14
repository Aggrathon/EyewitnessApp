package aggrathon.eyewitnessapp.data;

import java.util.Date;

public class ExperimentIteration {

	public Date time;
	public int lineupNumber;
	public String imageOrder;
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
		imageOrder = "";
		selectedImage = "";
		targetSex = "";
	}

	public String toString(int index) {
		return "Experiment Iteration "+index+":"+
				"\n\tTime: "+time+
				"\n\tTutorial: "+tutorial+
				"\n\tNumber: "+lineupNumber+
				"\n\tImage Order: "+imageOrder+
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
}
