/**
 * DatabaseHelper.java is a database helper call which creates database table and implements insertion and update operations.
 *
 * @author Supada Hegde
 * @version 1.0
 */

package com.jhonson.supada.deviceinfo;

import android.database.DatabaseUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "devices.db";
    private long devicelist;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table devicelist " +
                        "(id integer primary key, device text, os text, manufacturer text, lastcheckout_date text, lastcheckout_by text, ischecked_out text)"
        );
        Log.d("TABLE", "created");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS devicelist");
        onCreate(db);

    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, "devicelist");
        return numRows;
    }

    public boolean insertDevice(String id, String device, String os,
                                String manufacturer, String lastcheckout_date, String lastcheckout_by, String ischecked_out) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("device", device);
        contentValues.put("os", os);
        contentValues.put("manufacturer", manufacturer);
        contentValues.put("lastcheckout_date", lastcheckout_date);
        contentValues.put("lastcheckout_by", lastcheckout_by);
        contentValues.put("ischecked_out", ischecked_out);
        long result = devicelist;
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Device getDevice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from devicelist where id=" + id + "", null);
        res.moveToFirst();
        Device d = new Device();
        d.setId(res.getShort(res.getColumnIndex("id")));
        d.setDevice(res.getString(res.getColumnIndex("device")));
        d.setOs(res.getString(res.getColumnIndex("os")));
        d.setManufacturer(res.getString(res.getColumnIndex("manufacturer")));
        d.setLastCheckedOutDate(res.getString(res.getColumnIndex("lastcheckout_date")));
        d.setLastCheckedOutBy(res.getString(res.getColumnIndex("lastcheckout_by")));
        d.setIsCheckedOut(res.getString(res.getColumnIndex("ischecked_out")));

        if (d.getLastCheckedOutBy() == null) {
            d.setLastCheckedOutBy("");
        }
        if (d.getLastCheckedOutDate() == null) {
            d.setLastCheckedOutDate("");
        }
        if (d.getOs() == null) {
            d.setOs("");
        }
        if (!res.isClosed()) {
            res.close();
        }
        return d;
    }

    public boolean updateDevice(String id, String lastcheckeout_date, String lastcheckout_by, String ischecked_out) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lastcheckout_date", lastcheckeout_date);
        contentValues.put("lastcheckout_by", lastcheckout_by);
        contentValues.put("ischecked_out", ischecked_out);
        long res = db.update("devicelist", contentValues, "id = ? ", new String[]{id});
        Log.d("UPDATE STATUS", String.valueOf(res));
        if (res == -1) {
            Log.d("UPDATE", "FAILED");
            return false;
        }
        return true;
    }

    public Integer deleteDevice(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("devicelist",
                "id = ? ",
                new String[]{id});
    }

    public Integer deleteDevices() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("devicelist", null, null);
    }

    public ArrayList<Device> getAllDevices() {
        ArrayList<Device> retDevice = new ArrayList<Device>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from devicelist", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Device d = new Device();
            d.setId(res.getShort(res.getColumnIndex("id")));
            d.setDevice(res.getString(res.getColumnIndex("device")));
            d.setOs(res.getString(res.getColumnIndex("os")));
            d.setManufacturer(res.getString(res.getColumnIndex("manufacturer")));
            d.setLastCheckedOutDate(res.getString(res.getColumnIndex("lastcheckout_date")));
            d.setLastCheckedOutBy(res.getString(res.getColumnIndex("lastcheckout_by")));
            d.setIsCheckedOut(res.getString(res.getColumnIndex("ischecked_out")));

            if (d.getLastCheckedOutBy() == null) {
                d.setLastCheckedOutBy("");
            }
            if (d.getLastCheckedOutDate() == null) {
                d.setLastCheckedOutDate("");
            }
            if (d.getOs() == null) {
                d.setOs("");
            }
            retDevice.add(d);
            res.moveToNext();
        }
        return retDevice;
    }


}



