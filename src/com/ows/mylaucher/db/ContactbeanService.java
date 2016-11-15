package com.ows.mylaucher.db;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ows.mylaucher.Contactbean;
import com.ows.mylaucher.MainActivity;

/**  
 * ��Person�����sql����(��ɾ�Ĳ�)  
 *   
 * @author Administrator  
 *   
 */  
public class ContactbeanService  
{  
  
    private DbOpenhelper dbOpenHelper;  
  
    public ContactbeanService(Context context)  
    {  
        dbOpenHelper = new DbOpenhelper(context);  
    }  
  
    /**  
     * ���Person  
     *   
     * @param person  
     */  
    public void addPerson(Contactbean person)  
    {  
        // �Զ���д�����ķ���  
        // ��������Ƕ��ε���������ݿⷽ��,���ǵ��õ���ͬһ�����ݿ����,������ķ������������ݵ��ö������õ�ͬһ������  
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
        db.execSQL("insert into Person(name,number,group,rank) values(?,?,?,?)", new Object[]  
        { person.name, person.number,person.group,person.rank});  
    }  
  
    /**  
     * �޸�Person  
     *   
     * @param person  
     */  
    public void modifyPerson(Contactbean person,Contactbean person2)  
    {  
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
        db.execSQL("update Person set name=?  set number=?  set group=?  set rank=? where group=? where rank=?", new Object[]  
        { person.name,person.number,person.group,person.rank, person2.group,person2.rank});  
    }  
  
    /**  
     * ɾ��Person  
     *   
     * @param person  
     */  
    public void deletePerson(Integer group,Integer rank)  
    {  
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
        db.execSQL("delete from Person where group=? rank=?", new Object[]  
        { group.toString(),rank.toString() });  
    }  
  
    /**  
     * ����person��Id��ѯPerson����  
     *   
     * @param id  
     *            Person��ID  
     * @return Person  
     */  
    public Contactbean findPerson(Integer id)  
    {  
        // ֻ�Զ��Ĳ����ķ���  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        // Cursor�α��λ��,Ĭ����0,�����ڲ���ʱһ��Ҫ��cursor.moveToFirst()һ��,��λ����һ����¼  
        // Cursor cursor =  
        // db.rawQuery("select * from person Where personid=?",new  
        // String[]{id.toString()});  
        Cursor cursor = db.query("Person", new String[]  
        { "personid", "name", "amount" }, "personid=?", new String[]  
        { id.toString() }, null, null, null);  
        if (cursor.moveToFirst())  
        {  
            int personId = cursor.getInt(cursor.getColumnIndex("personid"));  
            String name = cursor.getString(cursor.getColumnIndex("name"));  
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));  
            return new MainActivity().getContactbean(); 
        }  
        return null;  
    }  
  
    /**  
     * ����Person����ļ���  
     *   
     * @return List<Person>  
     */  
    public List<Contactbean> findPersonList(Integer start, Integer length)  
    {  
        List<Contactbean> persons = new ArrayList<Contactbean>();  
        // ֻ�Զ��Ĳ����ķ���  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select * from Person limit ?,?",  
                new String[]  
                { start.toString(), length.toString() });  
        cursor = db.query("Person", null, null, null, null, null, null, start  
                + "," + length);  
        while (cursor.moveToNext())  
        {  
            int personId = cursor.getInt(cursor.getColumnIndex("personid"));  
            String name = cursor.getString(cursor.getColumnIndex("name"));  
            int amount = cursor.getInt(cursor.getColumnIndex("amount"));  
            persons.add( new MainActivity().getContactbean());  
        }  
        return persons;  
    }  
  
    /**  
     * ����Person�ļ�¼�ܸ���  
     *   
     * @return  
     */  
    public Long getCount()  
    {  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select count(0) from Person ", null);  
        // ����ض���һ����¼.���в����ж�,ֱ���Ƶ���һ��.  
        cursor.moveToFirst();  
        // ����ֻ��һ���ֶ�ʱ�� ����  
        return cursor.getLong(0);  
    }  
  
    /**  
     * ����һ������  
     *   
     * @return  
     */  
    public String getTransaction()  
    {  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        String success = "";  
        db.beginTransaction();  
        try  
        {  
            db.execSQL("update person set amount = amount+10 where personId=?",  
                    new Object[]  
                    { 1 });  
            db.execSQL("update person set amount = amount-10 where personId=?",  
                    new Object[]  
                    { 2 });  
            success = "success";  
        } catch (Exception e)  
        {  
            success = "input";  
        } finally  
        {  
            // db.setTransactionSuccessful();//���������־Ϊ�ɹ�,����������ʱ�ͻ��ύ����  
            db.endTransaction();  
        }  
        return success;  
    }  
}  