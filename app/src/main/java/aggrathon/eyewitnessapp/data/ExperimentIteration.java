package aggrathon.eyewitnessapp.data;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentIteration {

	public static final String SELECTED_NO_TARGET = "no image chosen";

	public Date time;
	public boolean targetPresent;
	public int lineupNumber;
	private ArrayList<String> imageOrder;
	private float[] imageTimes;
	public int selectedImage;
	public String targetSex;
	public int targetHeight;
	public int targetWeight;
	public int confidence;
	public int distance;
	public int age;
	public boolean recognisedTarget;
	public boolean recognisedOther;
	public boolean tutorial;
	public float lineupTime;


	public ExperimentIteration(int number, boolean targetPresent, ArrayList<String> imageOrder) {
		time = new Date();
		selectedImage = -1;
		targetSex = "";
		lineupNumber = number;
		this.targetPresent = targetPresent;
		this.imageOrder = imageOrder;
		this.imageTimes = new float[imageOrder.size()];
		selectedImage = -1;
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
				"\n\tLineup time: "+lineupTime+
				"\n\tImage Order: "+order+
				"\n\tTarget Present: "+targetPresent+
				"\n\tSelected: "+getSelectedImage()+
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
		if (selectedImage < 0 || selectedImage >= imageOrder.size())
			return !targetPresent;
		else
			return imageOrder.get(selectedImage).toLowerCase().contains(ExperimentData.CORRECT_TAG);
	}

	public String getSelectedImage() {
		if (selectedImage < 0 || selectedImage >= imageOrder.size())
			return SELECTED_NO_TARGET;
		else
			return imageOrder.get(selectedImage);
	}

	public void selectImage(int index) {
		if(index >= 0 && index < imageOrder.size())
			selectedImage = index;
		else
			selectedImage = -1;
	}

	public void setLineupTime(long start, long end) {
		lineupTime = (float)((double)(end-start)*0.001);
	}

	public void setImageTime(int index, long start, long end) {
		if (index >= 0 && index < imageTimes.length)
			imageTimes[index] = (float)((double)(end-start)*0.001);
	}

	public String getImageOrder(int index) {
		if (index < 0 ||index >= imageOrder.size()) {
			return "";
		}
		else {
			return imageOrder.get(index);
		}
	}

	public float getImageTime(int index) {
		if (index < 0 || index >= imageTimes.length) {
			return 0.0f;
		}
		else {
			return imageTimes[index];
		}
	}
}
