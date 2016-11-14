package com.ows.mylaucher;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ows.mylaucher.MainActivity.MyLinkedList;

public class Myadapter extends BaseAdapter implements OnClickListener{
	private MyLinkedList al;
	private Context context;
	private Callback mCallback;
	
	 public interface Callback {		 
		 public void click(View v);
		 }
	
	
	public Myadapter(MyLinkedList al,Context context,Callback callback) {
		this.al = al;
		this.context = context;
		this.mCallback = callback;
	}

	@Override
	public int getCount() {
		return al.size();
	}

	@Override
	public Object getItem(int position) {
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.item_main, null);
		RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.rl);
		TextView nametxt = (TextView) view.findViewById(R.id.name);
		TextView numbertxt = (TextView) view.findViewById(R.id.number);
		ImageView voiceImageView = (ImageView) view.findViewById(R.id.voice);
		
		nametxt.setText(al.get(position).name);
		numbertxt.setText(al.get(position).number);
		nametxt.setOnClickListener(this);
		nametxt.setTag(position);
		numbertxt.setOnClickListener(this);
		numbertxt.setTag(position);
		voiceImageView.setOnClickListener(this);
		voiceImageView.setTag(position);
		//view.setBackgroundColor());
		relativeLayout.setBackgroundColor(getColor(al.get(position).group));
		
		
		return view;
	}
	
@Override
public void onClick(View v) {
	 mCallback.click(v);
}

private int getColor(int id) {
	switch (id) {
	case 0:		
		return Color.parseColor("#FF8C84");		
	case 1:
		return Color.parseColor("#EFEFEF");
	case 2:	
		return Color.parseColor("#C6D6AD");
	default:
		return Color.parseColor("#F0F0F0");
	}
	
 
 

}	
}
