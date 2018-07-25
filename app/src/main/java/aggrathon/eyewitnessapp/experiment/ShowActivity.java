package aggrathon.eyewitnessapp.experiment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import aggrathon.eyewitnessapp.ACancelCheckActivity;
import aggrathon.eyewitnessapp.R;
import aggrathon.eyewitnessapp.SettingsActivity;
import aggrathon.eyewitnessapp.data.ExperimentData;
import aggrathon.eyewitnessapp.data.ExperimentIteration;
import aggrathon.eyewitnessapp.utils.StorageManager;

public class ShowActivity extends ACancelCheckActivity {

	TextView title;
	TextView message;
	ImageView image;
	VideoView video;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		title = (TextView)findViewById(R.id.title);
		message = (TextView)findViewById(R.id.showLive);
		image = (ImageView)findViewById(R.id.showImage);
		video = (VideoView)findViewById(R.id.showVideo);
		switch (ExperimentData.getInstance().show) {
			case live: setLive(); break;
			case image: setImage(); break;
			case video: setVideo(); break;
		}
	}

	public void setLive() {
		title.setText(R.string.wait);
		message.setVisibility(View.VISIBLE);
		image.setVisibility(View.INVISIBLE);
		video.setVisibility(View.INVISIBLE);
		ExperimentIteration exp = ExperimentData.getInstance().getLatestData();
		exp.showDistance = -1;
		exp.show = "live";
	}

	public void setVideo() {
		title.setText(R.string.show_look);
		message.setVisibility(View.INVISIBLE);
		image.setVisibility(View.INVISIBLE);
		video.setVisibility(View.VISIBLE);
		ExperimentIteration iter = ExperimentData.getInstance().getLatestData();
		SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		int min = prefs.getInt(SettingsActivity.SHOW_RANGE_MIN, 0);
		int max = prefs.getInt(SettingsActivity.SHOW_RANGE_MAX, 1000);;
		ArrayList<File> files = new ArrayList<>();
		for (File f : StorageManager.getImageList(iter.lineupLabel)) {
			String name = f.getName();
			String lower = name.toLowerCase();
			if (!lower.contains(ExperimentData.SHOW_TAG))
				continue;
			if (!lower.contains(".avi") && !lower.contains(".mp4") && !lower.contains(".3gp") && !lower.contains(".mpv"))
				continue;
			int dist = getDistance(lower);
			if (dist != -1 && (dist < min || dist > max))
				continue;
			files.add(f);
		}
		if (files.size() == 0) {
			Log.d("Video Read", "Could not find any video to show");
			Toast.makeText(this, "Could not find any video to show", Toast.LENGTH_LONG).show();
			return;
		}
		File imagefile = files.get(new Random().nextInt(files.size()));
		iter.showDistance = getDistance(imagefile.getName().toLowerCase());
		iter.show = imagefile.getName();
		video.setVideoPath(imagefile.getPath());
		video.start();
		video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {
				NextActivity();
			}
		});
	}

	public void setImage() {
		title.setText(R.string.show_look);
		message.setVisibility(View.INVISIBLE);
		image.setVisibility(View.VISIBLE);
		video.setVisibility(View.INVISIBLE);
		ExperimentIteration iter = ExperimentData.getInstance().getLatestData();
		SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		int min = prefs.getInt(SettingsActivity.SHOW_RANGE_MIN, 0);
		int max = prefs.getInt(SettingsActivity.SHOW_RANGE_MAX, 1000);;
		ArrayList<File> files = new ArrayList<>();
		for (File f : StorageManager.getImageList(iter.lineupLabel)) {
			String name = f.getName();
			String lower = name.toLowerCase();
			if (!lower.contains(ExperimentData.SHOW_TAG))
				continue;
			if (!lower.contains(".png") && !lower.contains(".jpg") && !lower.contains(".jpeg") && !lower.contains(".gif"))
				continue;
			int dist = getDistance(lower);
			if (dist != -1 && (dist < min || dist > max))
				continue;
			files.add(f);
		}
		if (files.size() == 0) {
			Log.d("Image Read", "Could not find any image to show");
			Toast.makeText(this, "Could not find any image to show", Toast.LENGTH_LONG).show();
			return;
		}
		File imagefile = files.get(new Random().nextInt(files.size()));
		iter.showDistance = getDistance(imagefile.getName().toLowerCase());
		iter.show = imagefile.getName();
		Bitmap bmp = BitmapFactory.decodeFile(imagefile.getPath());
		if (bmp == null) {
			Log.d("Image Read", "Could not read bitmap from "+imagefile.getPath());
			Toast.makeText(this, "Could not read bitmap from "+imagefile.getPath(), Toast.LENGTH_LONG).show();
		}
		else
			image.setImageBitmap(bmp);
	}

	private int getDistance(String filename) {
		if (filename.contains("_")) {
			int li = filename.lastIndexOf('_')+1;
			int lo = filename.indexOf('.', li);
			try {
				return Integer.parseInt(filename.substring(li, lo));
			} catch (NumberFormatException nfe) {}
		}
		return -1;
	}



	public void onNextButton(View v) {
		NextActivity();
	}

	public void NextActivity() {
		startActivity(new Intent(this, TargetQuestionsActivity.class));
		finish();
	}
}
