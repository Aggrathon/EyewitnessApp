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
	private int layoutResourceId;
	private ArrayList data = new ArrayList();

	public ImageGridAdapter(Context context, int layoutResourceId, ArrayList data) {
		super(context, layoutResourceId, data);
		this.context = context;
		this.layoutResourceId = layoutResourceId;
		this.data = data;
	}

	@NonNull
	@Override
	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			convertView = inflater.inflate(layoutResourceId, parent, false);
			convertView.setTag(convertView.findViewById(R.id.image_button));
		}
		final ImageButton imgB = (ImageButton)convertView.getTag();
		final int dataInt = (Integer) data.get(position);
		imgB.setImageResource(R.mipmap.icon);
		imgB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				context.startActivity(new Intent(context, QuestionsActivity.class));
			}
		});
		Log.d("Grid View", ""+dataInt);
		return convertView;
	}
}
