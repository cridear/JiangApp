package com.ows.mylaucher;

import android.widget.Switch;

public  class MyLunarDateFormat
{

	public MyLunarDateFormat()
	{
	}
	public static String Month2汉(int month){
		if (month==1)
		{
			return "正月";
		}
		else if (month==11)
		{
			return "十一月";
		}
		else if(month ==12) {
			return "十二月";
		}{
			return int2汉(month)+"月";
		}		

		
				
	}
	public static String Day2汉(int day){
		 if (day<=10)
		{
			 return "初"+int2汉(day);			
		}
		 else {
			return int2汉(day/10).replace("一","")+"十"+int2汉(day%10);
		}		
				
	}
public static String int2汉(int rsc){
	switch (rsc)
	{
	case 1:
		return "一";
	case 2:
		return "二";
	case 3:
		return "三";
	case 4:
		return "四";
	case 5:
		return "五";
	case 6:
		return "六";
	case 7:
		return "七";
	case 8:
		return "八";
	case 9:
		return "九";
	case 10:
		return "十";
		

	default:
		break;
	}
	
	return "";
	
}


}
