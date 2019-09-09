package com.parindo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

public class DBController extends SQLiteOpenHelper {


    public DBController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,"SQLite.db", factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists UserData");
        db.execSQL("Create table UserData(NombreCompleto text,NumeroCedula text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public void InsertUserData(String NombreCompleto, String Numerocedula){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NombreCompleto",NombreCompleto);
        contentValues.put("NumeroCedula",Numerocedula);
        this.getWritableDatabase().insertOrThrow("UserData","",contentValues);
    }

    public String[] SelectUserData(){
        Cursor cursor= getReadableDatabase().rawQuery("Select * from UserData",null);

        while (cursor.moveToNext()){
            return new String[]{cursor.getString(0),cursor.getString(1)};
            //return cursor.getString(0);
        }
        return null;
    }



   /////////////////////////////////////////////////////////

}
