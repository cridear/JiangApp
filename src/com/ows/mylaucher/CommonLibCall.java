package com.ows.mylaucher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.text.TextUtils;
import android.util.Log;

public class CommonLibCall {

	public static String numberType[]={"家庭","手机","工作","工作传真","家庭传真","寻呼�?","其他","回拨号码"};
	/**
	 * �?查是否存在当前数据库
	 */
	public static boolean checkDataBase(String path){

	    SQLiteDatabase checkDB = null;
	    try{

	        String myPath = path;
	        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);

	    }catch(SQLiteException e){
         Log.v("ccy", "不存在当前数据库");
	        //database does't exist yet.
	    }
	    if(checkDB != null){
	        checkDB.close();
	    }
	    return checkDB != null ? true : false;
	}
	/**
	 * 取得字符串第�?个字母并变为大写�?
	 * 不是字母则为#
	 */
	public static String FirstA(String s){
		String str="";
		char c=s.charAt(0);
		if(c>='A'&&c<='Z'){
			str=str+c;
		}else if (c>='a'&&c<='z') {
			c-=32;
			str=str+c;
		}else {
			str="#";
		} 					
		return str;
	}
	/**
	 * 把字符串中小写字母变成大写字母，
	 */
	public static String ShowA(String s){
		String str="";
		for(int i=0;i<s.length();i++)
		{
			if(!(" ".equals(s.charAt(i)))){
			    char c=s.charAt(i);	
			    
				if(c>='a'&&c<='z')
				{
					c-=32;
					str=str+c;
				}else{
					str=str+c;
				}
			}				
		}
		return str;
	}
	/**
	 * 把一个字符串按空格分�?
	 * 取每组的首字母变为大写字�?
	 */
	public static String kongGe(String s){
		String[] array=s.split(" ");
		String str = "";
		for(int i=0;i<array.length;i++){
//		       Log.v("ccy","array[i]:"+array[i] + "end");
		       if(!(" ".equals(array[i]) || "".equals(array[i]) || array[i] == null)){
			       char c=array[i].charAt(0);
			       if(c>='A'&&c<='Z'){
			    	   str = str + "" + c;
			       }else if(c>='a'&&c<='z'){
			    	   c-=32;
			    	   str = str + "" + c;
			       }
		       }else {
//				Log.v("ccy", "array[i]:"+array[i]+"**");
			   }		       
		}
		return str;			
	}
	/**
	 * 按路径查询数据库表里
	 * 每一列的名字
	 */
	public static void listColumnNames(String path,Context context)
	{
		Uri contactUri =Uri.parse(path);
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor =resolver.query(contactUri, null,null, null,null);
		int columnNumber = cursor.getColumnCount();
		for(int i = 0; i <columnNumber; i++)
		{
		String temp =cursor.getColumnName(i);
		Log.v("ccy","" + i + "\t" + temp);
		}
		cursor.close();
	}
	/**
	 * 通过在�?�讯录表里查询Num
	 * 返回联系人姓�?
	 */
	public static String selectName(String select, String[] args,Context context){
	    String[] PHONES_PROJECTION = new String[] {
		       Phone.DISPLAY_NAME, Phone.NUMBER, Phone.TYPE,Phone.DATA3
		       };
	    String contactName=null;
	    String phoneNumber=null;
		Uri uri=Phone.CONTENT_URI;
		ContentResolver resolver = context.getContentResolver();
		Cursor phoneCursor = resolver.query(uri,
				PHONES_PROJECTION, select, args, null);
		if(phoneCursor.getCount()==0){
			Log.v("ccy", "陌生人电�?,返回电话�?");
			return args[0];
		}else{
			if (phoneCursor!= null) {
			    while (phoneCursor.moveToNext()) {
				
				phoneNumber = phoneCursor.getString(1);
				//当手机号码为空的或�?�为空字�? 跳过当前循环
				if (TextUtils.isEmpty(phoneNumber))
				    continue;		
				//得到联系人名�?
				contactName=phoneCursor.getString(0);				
			    }
			    phoneCursor.close();
			  }
			return contactName;
		}		
	}
	/**
	 * 验证电话号码的正确�??
	 */
	public static boolean checkPhone(String phone){
		boolean isNum = true;
		String[] a=new String[]{"1","2","3","4","5","6","7","8","9","0","*","#","+"};
		List<String> tempList = Arrays.asList(a);
		for(int i=0;i<phone.length();i++){				   				   
			 String p =String.valueOf(phone.charAt(i));
			 
			 if(!tempList.contains(p)) {
				 isNum=false;
				 return isNum;
			 }
		}						
		return isNum;
	}
	/**
	 * 把type从数字转换成汉字
	 */
	public static String changeType(String type,String type2){
		String s=null;
		if(type!=null){
			int i=Integer.parseInt(type);				
			if(i > 0){
				if ((i - 1) < numberType.length) {
					s=numberType[i-1];
				}
		    }else{
		    	s=type2;
		    }	
			return s;
		}else{
			return "";
		}			
	}
	/**
	 * 文件转换成byte[]
	 */
	public static byte[] convertTo(File file){
		Log.d("ccy", "根据路径查找数据库，写入输出流�?�返回Byte数组");
		byte[] b = null;
	    try {
	        InputStream in = new FileInputStream(file);  
	        b = new byte[(int)file.length()];     //创建合�?�文件大小的数组  
	        in.read(b);    //读取文件中的内容到b[]数组
			in.close();
	    } catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return b;
	}
}
