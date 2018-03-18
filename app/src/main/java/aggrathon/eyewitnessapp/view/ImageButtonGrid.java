package aggrathon.eyewitnessapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Space;

import java.util.ArrayList;

import aggrathon.eyewitnessapp.R;

public class ImageButtonGrid extends GridLayout {
	/**
	 * This class implements a grid of Image Buttons.
	 * It is based on the GridLayout.
	 * You must call SetImages in order to show images.
	 */

	public  interface OnCLick {
		public void OnClick(int i);
	}

	public ImageButtonGrid(Context context) {
		super(context);
	}
	public ImageButtonGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ImageButtonGrid(Context context, AttributeSet attrs, int defStyleAttr) { super(context, attrs, defStyleAttr); }
	public ImageButtonGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) { super(context, attrs, defStyleAttr, defStyleRes); }

	public void SetImages(Context context, int[] images, final OnCLick onCLick) {
		removeAllViews();
		LayoutInflater inf = LayoutInflater.from(context);
		//addView(new Space(context));
		for (int i = 0; i < images.length; i++) {
			ImageButton ib = (ImageButton)inf.inflate(R.layout.layout_image_button, null);
			final int i_ = i;
			ib.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onCLick.OnClick(i_);
				}
			});
			ib.setImageResource(images[i]);
			addView(ib);
			//LayoutParams params = (LayoutParams) ib.getLayoutParams();
			//params.setMargins(16, 16, 16, 16);
			//params.setGravity(Gravity.CENTER);
			//ib.setLayoutParams(params);
			//addView(new Space(context));
		}
		if (images.length < 4) {
			setRowCount(1);
			setColumnCount(images.length);
		}
		else {
			setRowCount(2);
			setColumnCount((images.length+1)/2);
		}
		requestLayout();
	}

	public void SetImages(Context context, ArrayList<Bitmap> images, final OnCLick onCLick) {
		for (int i = 0; i < images.size(); i++) {
			ImageButton ib = (ImageButton)inflate(context, R.layout.layout_image_button, this);
			final int i_ = i;
			ib.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onCLick.OnClick(i_);
				}
			});
			ib.setImageBitmap(images.get(i));
			addView(ib);
		}
		if (images.size() < 4) {
			setRowCount(1);
			setColumnCount(images.size()*2);
		}
		else {
			setRowCount(2);
			setColumnCount((images.size()+1)/2);
		}
		requestLayout();
	}
}
