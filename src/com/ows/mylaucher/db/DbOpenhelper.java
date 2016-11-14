package com.ows.mylaucher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**  
 * 通过继承SqliteOpenHelper来创建一个数据库  
 * @author Administrator  
 *  
 */  
public class DbOpenhelper extends SQLiteOpenHelper  
{  
    private static String DATABASENAME = "conts.db";  
    private static int DATABASEVERSION = 2;  
      
  
    /**  
     * (Context context, String name, CursorFactory factory,int version)  
     * @param context 上下文对象  
     * @param name 数据库名称 secb.db  
     * @param factory  游标工厂  
     * @param version 数据库版本  
     */  
    public DbOpenhelper(Context context)  
    {  
        super(context, DATABASENAME, null, DATABASEVERSION);  
    }  
      
    /**数据库第一次被使用时创建数据库  
     * @param db 操作数据库的  
     */  
    public void onCreate(SQLiteDatabase db)  
    {  
        //执行有更新行为的sql语句  
    	
    	
        db.execSQL("CREATE Table person (personid integer primary key autoincrement, name varchar(20), number varchar(20),group integer,rank integer)");  
    }  
  
    /**数据库版本发生改变时才会被调用,数据库在升级时才会被调用;  
     * @param db 操作数据库  
     * @param oldVersion 旧版本  
     * @param newVersion 新版本  
     */  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  
    {  
        db.execSQL("drop table if exists person");  
        onCreate(db);  
    }  
  
}  