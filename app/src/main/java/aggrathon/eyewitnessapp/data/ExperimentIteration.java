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
	}
}
