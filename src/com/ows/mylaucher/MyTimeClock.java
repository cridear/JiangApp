package com.ows.mylaucher;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.DigitalClock;
import android.widget.TextClock;

public class MyTimeClock extends TextClock
{

	public MyTimeClock(Context context)
	{
		super(context);
	}

	@Override
	@ExportedProperty
	public CharSequence getFormat12Hour()
	{
		// TODO Auto-generated method stub
		return super.getFormat12Hour();
	}

	@Override
	@ExportedProperty
	public CharSequence getFormat24Hour()
	{
		// TODO Auto-generated method stub
		return super.getFormat24Hour();
	}

	@Override
	public String getTimeZone()
	{
		// TODO Auto-generated method stub
		return super.getTimeZone();
	}

	@Override
	public boolean is24HourModeEnabled()
	{
		// TODO Auto-generated method stub
		return super.is24HourModeEnabled();
	}

	@Override
	protected void onAttachedToWindow()
	{
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	@Override
	protected void onDetachedFromWindow()
	{
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	@Override
	public void setFormat12Hour(CharSequence format)
	{
		// TODO Auto-generated method stub
		super.setFormat12Hour(format);
	}

	@Override
	public void setFormat24Hour(CharSequence format)
	{
		// TODO Auto-generated method stub
		super.setFormat24Hour(format);
	}

	@Override
	public void setTimeZone(String timeZone)
	{
		// TODO Auto-generated method stub
		super.setTimeZone(timeZone);
	}

	

}
