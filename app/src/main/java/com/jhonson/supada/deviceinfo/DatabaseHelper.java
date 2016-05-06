/**
 * DatabaseHelper.java is a database helper call which creates database table and implements insertion and update operations.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME= "device.db";
    public static final String TABLE_NAME= "device_table";
    public static final String COL_1="ID";
    public static final String COL_2="DEVICE";
    public static final String COL_3="OS";
    public static final String COL_4="MANUFACTURER";
    public static final String COL_5="LASTCHEKOUT_DATE";
    public static final String COL_6="LASTCHECKOUT_BY";
    public static final String COL_7="ISCHECKEDOUT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String COL_ID = "ID";
        final String COL_DEVICE = "DEVICE";
        final String COL_OS = "OS";
        final String COL_MANUFACTURER = "MANUFACTURER";
        final String COL_LASTCHEKOUT_DATE = "LASTCHEKOUT_DATE";
        final String COL_LASTCHECKOUT_BY = "LASTCHECKOUT_BY";
        final String COL_ISCHECKEDOUT = "ISCHECKEDOUT";
        final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + TABLE_NAME +
                        " USING fts3 (" +
                        COL_ID + ", " +
                        COL_DEVICE + ", " +
                        COL_OS + ", " +
                        COL_MANUFACTURER + ", " +
                        COL_LASTCHEKOUT_DATE + ", " +
                        COL_LASTCHECKOUT_BY + ", " +
                        COL_ISCHECKEDOUT +
                        ")";
        db.execSQL(FTS_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+ TABLE_NAME);
        onCreate(db);

    }
    public boolean insertData(String id, String device, String os,
                              String manufacturer, String lastcheckoutDate, String lastcheckoutBy, String isCheckedOut) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, device);
        contentValues.put(COL_3, os);
        contentValues.put(COL_4, manufacturer);
        contentValues.put(COL_5, lastcheckoutDate);
        contentValues.put(COL_6, lastcheckoutBy);
        contentValues.put(COL_7, isCheckedOut);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else
            return true;
    }
    public Device getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        //List<Device> retDevice = Arrays.asList(new Device[6]);
        if (res != null) {
            res.moveToFirst();
            int i = 0;
            Device d = new Device();
            while(!res.isAfterLast()) {

                d.setId(Integer.parseInt(res.getString(0)));
                d.setDevice(res.getString(1));
                d.setOs(res.getString(2));
                d.setManufacturer(res.getString(3));
                d.setLastCheckedOutDate(res.getString(4));
                d.setLastCheckedOutBy(res.getString(5));
                d.setIsCheckedOut(false);
            }
            return d;
        } else{
            return null;
        }
    }
        }



