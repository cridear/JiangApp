package com.ows.mylaucher;

import android.widget.Switch;

public  class MyLunarDateFormat
{

	public MyLunarDateFormat()
	{
	}
	public static String Month2��(int month){
		if (month==1)
		{
			return "����";
		}
		else if (month==11)
		{
			return "ʮһ��";
		}
		else if(month ==12) {
			return "ʮ����";
		}{
			return int2��(month)+"��";
		}		

		
				
	}
	public static String Day2��(int day){
		 if (day<=10)
		{
			 return "��"+int2��(day);			
		}
		 else {
			return int2��(day/10).replace("һ","")+"ʮ"+int2��(day%10);
		}		
				
	}
public static String int2��(int rsc){
	switch (rsc)
	{
	case 1:
		return "һ";
	case 2:
		return "��";
	case 3:
		return "��";
	case 4:
		return "��";
	case 5:
		return "��";
	case 6:
		return "��";
	case 7:
		return "��";
	case 8:
		return "��";
	case 9:
		return "��";
	case 10:
		return "ʮ";
		

	default:
		break;
	}
	
	return "";
	
}


}
