package aggrathon.eyewitnessapp.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Space;

import java.util.ArrayList;

import aggrathon.eyewitnessapp.R;

public class ImageButtonGrid extends LinearLayout {
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
		init();
	}
	public ImageButtonGrid(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public ImageButtonGrid(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	public ImageButtonGrid(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}


	private void init() {
		inflate(getContext(),R.layout.layout_image_button_grid,this);
		setOrientation(VERTICAL);
	}

	public void SetImages(int[] images, final OnCLick onCLick) {
		ArrayList<ImageButton> buttons = getButtons(images.length);
		for (int i = 0; i < images.length; i++) {
			buttons.get(i).setImageResource(images[i]);
			final int i_ = i;
			buttons.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onCLick.OnClick(i_);
				}
			});
		}
	}

	public void SetImages(ArrayList<Bitmap> images, final OnCLick onCLick) {
		ArrayList<ImageButton> buttons = getButtons(images.size());
		for (int i = 0; i < images.size(); i++) {
			buttons.get(i).setImageBitmap(images.get(i));
			final int i_ = i;
			buttons.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					onCLick.OnClick(i_);
				}
			});
		}
	}

	ArrayList<ImageButton> getButtons(int num) {
		ArrayList<ImageButton> list = new ArrayList<>();
		list.add((ImageButton)findViewById(R.id.imageButton0));
		list.add((ImageButton)findViewById(R.id.imageButton1));
		list.add((ImageButton)findViewById(R.id.imageButton2));
		list.add((ImageButton)findViewById(R.id.imageButton3));
		list.add((ImageButton)findViewById(R.id.imageButton4));
		list.add((ImageButton)findViewById(R.id.imageButton5));
		list.add((ImageButton)findViewById(R.id.imageButton6));
		list.add((ImageButton)findViewById(R.id.imageButton7));
		list.add((ImageButton)findViewById(R.id.imageButton8));
		list.add((ImageButton)findViewById(R.id.imageButton9));
		if (num < 4) {
			for (int i = 0; i < num; i++)
				list.get(i).setVisibility(VISIBLE);
			for (int i = num; i < 10; i++)
				list.get(i).setVisibility(GONE);
		}
		else {
			int width = (num + 1)/2;
			for (int i = 0; i < width; i++) {
				ImageButton tmp = list.get(width+i);
				list.set(width+i, list.get(5+i));
				list.set(5+i, tmp);
			}
			for (int i = 0; i < num; i++)
				list.get(i).setVisibility(VISIBLE);
			for (int i = num; i < 10; i++)
				list.get(i).setVisibility(GONE);
			if (num%2 == 1)
				list.get(num).setVisibility(INVISIBLE);
		}
		return list;
	}
}
