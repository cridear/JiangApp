package com.ows.mylaucher;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class HanAppAdapter extends BaseAdapter implements OnClickListener
{
	private ArrayList<String> appInfos;
	private Context context;
	private Callback callback;
	
	public HanAppAdapter(Context context,ArrayList<String> appInfos,Callback callback)
	{
		this.appInfos = appInfos;
		this.context = context;
		this.callback = callback;
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
		View view = LayoutInflater.from(context).inflate(R.layout.hanapp_item, null);
		TextView textView =  (TextView) view.findViewById(R.id.hori);
		ImageView imageView = (ImageView) view.findViewById(R.id.movie);
		textView.setText(rd(appInfos.get(arg0).replaceAll(Pattern.compile("[^/]*$").split(appInfos.get(arg0))[0],"").replace(".mp4","")));
		textView.setOnClickListener(this);
		textView.setTag(arg0);
		imageView.setOnClickListener(this);
		imageView.setTag(arg0);
		return view;
	}

	@Override
	public void onClick(View v)
	{
		callback.click(v);		
	}
	
	
	
	
	
	private String rd(String str){		
		 String pat="\\d+";
		 Pattern p=Pattern.compile(pat);
		 Matcher m=p.matcher(str);
		 return m.replaceAll("");
		}	

}
