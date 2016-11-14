package com.ows.mylaucher;

import android.os.Environment;
import android.util.Log;

public final class MyApp {

	private static String packageNameValue = "";
	private static final String CHARSET_GBK = "GB2312";
	private static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+"/0123/Conts.db";
	// private static final String DATABASE_NAME="/mnt/sdcard/Conts.db";
	private static final String DATABASE_TABLE1 ="Contact";
	private static final String DATABASE_TABLE2 ="Contact_Data";
	private static final String DATABASE_CREATE1=
			 "create table book ("
			+ "id integer primary key autoincrement, "
			+ "author text, "
			+ "price real, "
			+ "pages integer, "
			+ "name text)";
			
			/*"create table"+" "+DATABASE_TABLE1+
			"(personid integer primary key autoincrement," +			
			"Name varchar(20),"+
			"Number varchar(20),"+
			"Group integer,"+
			"Rank integer"+");";*/
	private static final String DATABASE_CREATE2="create table"+" "+DATABASE_TABLE2+
			"(ID integer," +
			"Number_Phone varchar(20)," +
			"Type varchar(20)"+");";
	public static String getCharSet(){
		return CHARSET_GBK;
	}
	
	
	public static String getDATABASE_NAME(){
		String retDataBaseName = "";
		retDataBaseName = DATABASE_NAME.replace("%s", packageNameValue);
		//retDataBaseName = DATABASE_NAME;
		Log.v("xy","packageName:"+packageNameValue);
		Log.v("xy","all database path:"+retDataBaseName);
		return retDataBaseName;
	}
	public static String getDATABASE_TABLE1(){
		return DATABASE_TABLE1;
	}
	public static String getDATABASE_TABLE2(){
		return DATABASE_TABLE2;
	}
	public static String getDATABASE_CREATE1(){
		return DATABASE_CREATE1;
	}
	public static String getDATABASE_CREATE2(){
		return DATABASE_CREATE2;
	}
	public static void setPackageName(String packageName) {
		packageNameValue = packageName;
	}
}
