package com.ows.mylaucher;

import java.util.ArrayList;

import com.ows.mylaucher.Myadapter.Callback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppAdapter extends BaseAdapter implements OnClickListener
{
	private ArrayList<AppInfo> appInfos;
	private Context context;
	private AppCallback appCallback;
	 public interface AppCallback {		 
		 public void appclick(View v);
		 }
	
	public AppAdapter(Context context,ArrayList<AppInfo> appInfos)
	{
		this.appInfos = appInfos;
		this.context = context;
	}
	
	public AppAdapter(Context context,ArrayList<AppInfo> appInfos,AppCallback appCallback)
	{
		this.appInfos = appInfos;
		this.context = context;
		this.appCallback = appCallback;
	}
	
	

	@Override
	public int getCount()
	{
		return appInfos.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.app_item, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.img);
		TextView textView = (TextView) view.findViewById(R.id.txt);
		imageView.setImageDrawable(appInfos.get(arg0).appIcon);
		textView.setText(appInfos.get(arg0).appName);
		imageView.setOnClickListener(this);
		imageView.setTag(arg0);
		textView.setOnClickListener(this);
		textView.setTag(arg0);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (appCallback!=null) {
			appCallback.appclick(v);	
		}
		

		
	}
	
	
	
	
	
	

}
