package com.example.sanukumar.billsplit;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sudeepvig on 7/11/2018.
 */

public class InternalDatabase extends SQLiteOpenHelper {


    public InternalDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, "BillSplit", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table expenses(id integer not null unique,articlename text,date date,friendsContributing text)");
        sqLiteDatabase.execSQL("create table friends(id integer not null unique,phonenumber text,payReceive text,emailID text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists expenses");
        sqLiteDatabase.execSQL("drop table if exists friends");

    }

    public boolean InsertExpenses(int id,String articlename,String date,String friendsContributing)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",id);
        contentValues.put("articlename",articlename);
        contentValues.put("date",date);
        contentValues.put("friendsContributing",friendsContributing);

        long r=db.insert("expenses",null,contentValues);
        if(r==-1)
        {
            return false;

        }else
            {
            return true;
        }
    }

    public boolean UpdateExpenses(int id,String articlename,String date,String friendsContributing)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("articlename",articlename);
        values.put("date",date);
        values.put("friendsContributing",friendsContributing);
        values.put("id",id);
        String id1=Integer.toString(id);

        int i=db.update("expenses",values,"id="+id1,null);

        System.out.println(i);
        if(i<=0)
        {
            return false;

        }
        else
        {
            return true;
        }
    }

    public boolean deleteExpenses(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        long res=db.delete("expenses","id="+id,null);

        if(res<=0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


    public boolean InsertFriendExpense(int id,String phonenumber,String PayReceive,String emailID)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",id);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("payReceive",PayReceive);
        contentValues.put("emailID",emailID);

        long r=db.insert("friends",null,contentValues);
        if(r==-1)
        {
            return false;

        }else
        {
            return true;
        }
    }

    public boolean UpdateFriendExpense(int id,String phonenumber,String PayReceive,String emailID)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",id);
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("payReceive",PayReceive);
        contentValues.put("emailID",emailID);

        String id1=Integer.toString(id);

        int i=db.update("friends",contentValues,"id="+id1,null);

        System.out.println(i);
        if(i<=0)
        {
            return false;

        }
        else
        {
            return true;
        }

    }

    public boolean deleteFriendExpense(int id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        long res=db.delete("friends","id="+id,null);

        if(res<=0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }


}
