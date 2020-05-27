package com.example.projectgdsync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SmartTechnology.db";
    public static final String TABLE_NAME = "userinfo";
    public static final String COL_1 = "ID";
    public static final String COL_2 ="Name";
    public static final String COL_3 ="Address";
    public static final String COL_4 ="A/P";
    public static final String COL_5 ="Village";
    public static final String COL_6 ="City";
    public static final String COL_7 ="Taluka";
    public static final String COL_8 ="District";
    public static final String COL_9 ="State";
    public static final String COL_10 ="Jurisdiction";
    public static final String COL_11 ="EmailID";
    public static final String COL_12 ="Phone1";
    public static final String COL_13 ="Phone2";
    public static final String COL_14 ="Phone3";
    SQLiteDatabase dbnew;

    public NewDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        dbnew = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase dbnew) {
        //db.execSQL("drop table if exists "+TABLE_NAME);
        dbnew.execSQL("create table " +TABLE_NAME+ "(ID VARCHAR(100) PRIMARY KEY,Name VARCHAR(100),Address Varchar(500),AP VARCHAR(100),Village VARCHAR(100),City VARCHAR(100),Taluka VARCHAR(100),District VARCHAR(100),State VARCHAR(100),Jurisdiction VARCHAR(100),EmailID VARCHAR(100),Phone1 VARCHAR(100),Phone2 VARCHAR(100),Phone3 VARCHAR(100))");




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        dbnew.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ID, String Name,String Address, String AP, String Village, String City, String Taluka, String District, String State, String Jurisdiction, String EmailID, String Phone1, String Phone2,String Phone3)//, int price, String date, int value)
    {
        dbnew = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,ID);
        contentValues.put(COL_2,Name);
        contentValues.put(COL_3,Address);
        contentValues.put(COL_4,AP);
        contentValues.put(COL_5,Village);
        contentValues.put(COL_6,City);
        contentValues.put(COL_7,Taluka);
        contentValues.put(COL_8,District);
        contentValues.put(COL_9,State);
        contentValues.put(COL_10,Jurisdiction);
        contentValues.put(COL_11,EmailID);
        contentValues.put(COL_12,Phone1);
        contentValues.put(COL_13,Phone2);
        contentValues.put(COL_14,Phone3);

        long result = dbnew.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }




    public boolean checkUserLogin(String username){
        SQLiteDatabase dbnew=this.getWritableDatabase();
        String Query = "select * from userinfo where Name='"+ username;
        Cursor cursor = null;

        try {
            cursor = dbnew.rawQuery(Query, null);//raw query always holds rawQuery(String Query,select args)
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(cursor!=null && cursor.getCount()>0){

            return true;
        }
        else{

            return false;
        }

    }
}
//    public  Cursor getAllData()
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
//        return res;
//    }

//    public boolean updateData(int ID,String name,String product, String quantity, String price, String date)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1,ID);
//        contentValues.put(COL_2,name);
//        contentValues.put(COL_3,product);
//        contentValues.put(COL_4,quantity);
//        contentValues.put(COL_5,price);
//        contentValues.put(COL_6,date);
//        //db.update(TABLE_NAME,contentValues,"ID = ?", new int[] { Integer.parseInt(ID)  });
//        db.execSQL(" UPDATE " + TABLE_NAME + " SET name = '" +name+ "',product = '"+product+"',quantity = '"+quantity+"',price = '"+price+"',date = '"+date+"'  where ID =" +ID+ ";" );
//        return  true;
//
//    }

//    public Cursor getListContents(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return data;
//    }
//
//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
//    }


