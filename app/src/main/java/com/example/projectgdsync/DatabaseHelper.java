package com.example.projectgdsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Sample1.db";
    public static final String TABLE_NAME = "firsttable";
    public static final String COL_1 = "ID";
    public static final String COL_2 ="Name";
    public static final String COL_3 ="Product";
    public static final String COL_4 ="Quantity";
    public static final String COL_5 ="Price";
    public static final String COL_6 ="Date";

    SQLiteDatabase db;

//    public static final String COL_3 ="Price";
//    public static final String COL_4 ="updationDate";
//    public static final String COL_5 ="Value";

//    public static final String DATABASE_NAME = "Smart.db";
//    public static final String TABLE_NAME = "Information";
//    public static final String COL_1 = "ID";
//    public static final String COL_2 ="Name";
//    public static final String COL_3 ="Product";
//    public static final String COL_4 ="Quantity";
//    public static final String COL_5 ="Price";
//    public static final String COL_6 ="OrderDate";

    //public static final String COL_7 ="Time";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("drop table if exists "+TABLE_NAME);
        db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY,Name VARCHAR(20),Product Varchar(200),Quantity Integer, Price Integer, Date TIMESTAMP default CURRENT_TIMESTAMP)");

//                "Price INTEGER," +
//                "updationDate Date," +
//                "value INTEGER)");

        //db.execSQL("create table " +TABLE_NAME+ "(ID INTEGER PRIMARY KEY,Name VARCHAR(50),Quantity INTEGER,Price INTEGER,OrderDate DATE );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(int ID, String name,String product, String quantity, String price, String Date)//, int price, String date, int value)
    {
        db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,product);
        contentValues.put(COL_4,quantity);
        contentValues.put(COL_5,price);
        contentValues.put(COL_6,Date);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

//    public  Cursor getAllData()
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
//        return res;
//    }

    public boolean updateData(int ID,String name,String product, String quantity, String price, String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,product);
        contentValues.put(COL_4,quantity);
        contentValues.put(COL_5,price);
        contentValues.put(COL_6,date);
        //db.update(TABLE_NAME,contentValues,"ID = ?", new int[] { Integer.parseInt(ID)  });
        db.execSQL(" UPDATE " + TABLE_NAME + " SET name = '" +name+ "',product = '"+product+"',quantity = '"+quantity+"',price = '"+price+"',date = '"+date+"'  where ID =" +ID+ ";" );
        return  true;

    }

    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

}
