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

	public static final String BLURRED_TAG = "blurred";

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
		setVisual (ExperimentData.getInstance().show);
	}

	public void setVisual(ExperimentData.ShowVariant variant) {
		message.setVisibility(View.GONE);
		image.setVisibility(View.GONE);
		video.setVisibility(View.GONE);
		ExperimentIteration iter = ExperimentData.getInstance().getLatestData();
		switch (variant) {
			case live:
				title.setText(R.string.wait);
				message.setVisibility(View.VISIBLE);
				iter.showDistance = -1;
				iter.show = "live";
				return;
			case video:
				title.setText(R.string.show_look);
				video.setVisibility(View.VISIBLE);
				break;
			default:
				title.setText(R.string.show_look);
				image.setVisibility(View.VISIBLE);
				break;
		}
		SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFERENCE_NAME, 0);
		int min = prefs.getInt(SettingsActivity.SHOW_RANGE_MIN, 0);
		int max = prefs.getInt(SettingsActivity.SHOW_RANGE_MAX, 1000);
		ArrayList<File> files = new ArrayList<>();
		for (File f : StorageManager.getImageList(iter.lineupLabel)) {
			String name = f.getName();
			String lower = name.toLowerCase();
			if (!lower.contains(ExperimentData.SHOW_TAG))
				continue;
			if (variant == ExperimentData.ShowVariant.video) {
				if (!lower.contains(".avi") && !lower.contains(".mp4") && !lower.contains(".3gp") && !lower.contains(".mpv"))
					continue;
			} else {
				if (!lower.contains(".png") && !lower.contains(".jpg") && !lower.contains(".jpeg") && !lower.contains(".gif"))
					continue;
				if (variant == ExperimentData.ShowVariant.image) {
					if (lower.contains(BLURRED_TAG))
						continue;
				}
				else {
					if (!lower.contains(BLURRED_TAG))
						continue;
				}
			}
			int dist = getDistance(lower);
			if (dist != -1 && (dist < min || dist > max))
				continue;
			files.add(f);
		}
		if (files.size() == 0) {
			switch (variant) {
				case video:
					Log.d("Video Read", "Could not find any video to show");
					Toast.makeText(this, "Could not find any video to show", Toast.LENGTH_LONG).show();
					return;
				case image:
					Log.d("Image Read", "Could not find any image to show");
					Toast.makeText(this, "Could not find any image to show", Toast.LENGTH_LONG).show();
					return;
				case blurred:
					Log.d("Image Read", "Could not find any blurred image to show");
					Toast.makeText(this, "Could not find any blurred image to show", Toast.LENGTH_LONG).show();
					return;
			}
		}
		File imageFile = files.get(new Random().nextInt(files.size()));
		iter.showDistance = getDistance(imageFile.getName().toLowerCase());
		iter.show = imageFile.getName();
		switch (variant) {
			case video:
				video.setVideoPath(imageFile.getPath());
				video.start();
				video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mediaPlayer) {
						NextActivity();
					}
				});
				break;
			default:
				Bitmap bmp = BitmapFactory.decodeFile(imageFile.getPath());
				if (bmp == null) {
					Log.d("Image Read", "Could not read bitmap from "+imageFile.getPath());
					Toast.makeText(this, "Could not read bitmap from "+imageFile.getPath(), Toast.LENGTH_LONG).show();
				}
				else
					image.setImageBitmap(bmp);
				break;
		}
	}

	private int getDistance(String filename) {
		if (filename.contains("_")) {
			int li = filename.lastIndexOf('_')+1;
			int lo = filename.indexOf('.', li);
			if(li < lo) try {
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

	public void onPrevButton(View v) {
		ExperimentData.getInstance().removeExperimentIteration();
		startActivity(new Intent(this, NumberActivity.class));
		finish();
	}
}
