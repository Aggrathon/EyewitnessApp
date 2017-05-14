package aggrathon.eyewitnessapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import java.util.ArrayList;


public class ImageGridAdapter extends ArrayAdapter {

	private Context context;
	private ArrayList data = new ArrayList();
	private OnSelectionListener callback;

	public ImageGridAdapter(Context context, ArrayList data, OnSelectionListener callback) {
		super(context, R.layout.image_selector_button, data);
		this.context = context;
		this.data = data;
		this.callback = callback;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(R.layout.image_selector_button, parent, false);
			convertView.setTag(convertView.findViewById(R.id.image_button));
		}
		ImageButton imgB = (ImageButton)convertView.getTag();
		imgB.setImageResource((int)data.get(position));
		final int pos = position;
		imgB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				callback.callback(pos);
			}
		});
		return convertView;
	}

	public interface OnSelectionListener {
		void callback(int i);
	}
}
