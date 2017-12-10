package aggrathon.eyewitnessapp.light.data;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentIteration {

	public static final String SELECTED_NO_TARGET = "no image chosen";

	public Date time;
	public boolean targetPresent;
	public String lineupNumber;
	public ArrayList<String> imageOrder;
	public float[] imageTimes;
	public int selectedImage;
	public int confidence;
	public boolean recognisedTarget;
	public boolean recognisedOther;
	public float lineupTime;


	public ExperimentIteration(String folder, boolean targetPresent, ArrayList<String> imageOrder) {
		time = new Date();
		selectedImage = -1;
		lineupNumber = folder;
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
				"\n\tNumber: "+lineupNumber+
				"\n\tLineup time: "+lineupTime+
				"\n\tImage Order: "+order+
				"\n\tTarget Present: "+targetPresent+
				"\n\tSelected: "+getSelectedImage()+
				"\n\tConfidence: "+confidence+
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
}
