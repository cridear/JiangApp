package com.ows.mylaucher.db;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ows.mylaucher.Contactbean;
import com.ows.mylaucher.MainActivity;

/**  
 * 对Person对象的sql操作(增删改查)  
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
     * 添加Person  
     *   
     * @param person  
     */  
    public void addPerson(Contactbean person)  
    {  
        // 对读和写操作的方法  
        // 如果当我们二次调用这个数据库方法,他们调用的是同一个数据库对象,在这里的方法创建的数据调用对象是用的同一个对象  
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();  
        db.execSQL("insert into Person(name,number,group,rank) values(?,?,?,?)", new Object[]  
        { person.name, person.number,person.group,person.rank});  
    }  
  
    /**  
     * 修改Person  
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
     * 删除Person  
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
     * 根据person的Id查询Person对象  
     *   
     * @param id  
     *            Person的ID  
     * @return Person  
     */  
    public Contactbean findPerson(Integer id)  
    {  
        // 只对读的操作的方法  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        // Cursor游标的位置,默认是0,所有在操作时一定要先cursor.moveToFirst()一下,定位到第一条记录  
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
     * 返回Person对象的集合  
     *   
     * @return List<Person>  
     */  
    public List<Contactbean> findPersonList(Integer start, Integer length)  
    {  
        List<Contactbean> persons = new ArrayList<Contactbean>();  
        // 只对读的操作的方法  
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
     * 返回Person的记录总个数  
     *   
     * @return  
     */  
    public Long getCount()  
    {  
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();  
        Cursor cursor = db.rawQuery("select count(0) from Person ", null);  
        // 这里必定有一条记录.所有不用判断,直接移到第一条.  
        cursor.moveToFirst();  
        // 这里只有一个字段时候 返回  
        return cursor.getLong(0);  
    }  
  
    /**  
     * 操作一个事务  
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
            // db.setTransactionSuccessful();//设置事务标志为成功,当结束事务时就会提交事务  
            db.endTransaction();  
        }  
        return success;  
    }  
}  