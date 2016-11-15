package com.ows.mylaucher.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {
	private static final String DBFILENAME = "person.db";
	private static int db_version = 2;

	public PersonSQLiteOpenHelper(Context context) {
		super(context, DBFILENAME, null, db_version);
	}

	/**
	 * �����ݿ��һ�δ���ʱ����
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table person(id integer primary key autoincrement,name varchar(20),number varchar(20))";
		db.execSQL(sql);
	}

	/**
	 * �����ݿ�İ汾�ŷ������ӵ�ʱ�����
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("���ݿ���ģ�");
		String sql = "alter table person add account varchar(20)";
		db.execSQL(sql);
	}

}
