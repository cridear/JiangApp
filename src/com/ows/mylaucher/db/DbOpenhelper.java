package com.ows.mylaucher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**  
 * ͨ���̳�SqliteOpenHelper������һ�����ݿ�  
 * @author Administrator  
 *  
 */  
public class DbOpenhelper extends SQLiteOpenHelper  
{  
    private static String DATABASENAME = "conts.db";  
    private static int DATABASEVERSION = 2;  
      
  
    /**  
     * (Context context, String name, CursorFactory factory,int version)  
     * @param context �����Ķ���  
     * @param name ���ݿ����� secb.db  
     * @param factory  �α깤��  
     * @param version ���ݿ�汾  
     */  
    public DbOpenhelper(Context context)  
    {  
        super(context, DATABASENAME, null, DATABASEVERSION);  
    }  
      
    /**���ݿ��һ�α�ʹ��ʱ�������ݿ�  
     * @param db �������ݿ��  
     */  
    public void onCreate(SQLiteDatabase db)  
    {  
        //ִ���и�����Ϊ��sql���  
    	
    	
        db.execSQL("CREATE Table person (personid integer primary key autoincrement, name varchar(20), number varchar(20),group integer,rank integer)");  
    }  
  
    /**���ݿ�汾�����ı�ʱ�Żᱻ����,���ݿ�������ʱ�Żᱻ����;  
     * @param db �������ݿ�  
     * @param oldVersion �ɰ汾  
     * @param newVersion �°汾  
     */  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)  
    {  
        db.execSQL("drop table if exists person");  
        onCreate(db);  
    }  
  
}  