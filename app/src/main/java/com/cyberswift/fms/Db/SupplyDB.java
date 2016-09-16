package com.cyberswift.fms.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyberswift.fms.model.Supply;

import java.util.ArrayList;


public class SupplyDB implements DBConstants {

    private static SupplyDB obj = null;

    public synchronized static SupplyDB obj() {

        if (obj == null)
            obj = new SupplyDB();
        return obj;

    }

    public Boolean saveSupplyList(Context context, ContentValues cv) {

        System.out.println(" ----------  ADD SUPPLY LIST  INTO SUPPLY_LIST TABLE  --------- ");
        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {
            mdb.insert(SUPPLY_TABLE, null, cv);
            mdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mdb.endTransaction();
            return true;
        }

    }

    public ArrayList<Supply> getSupply(Context context, String townId) {

        ArrayList<Supply> supplyArray = new ArrayList<Supply>();
        String[] columns = {_ID, SUPPLY_ID, SUPPLY_NAME, TOWN_ID};

        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getReadableDatabase();
        Cursor cur = mdb.query(SUPPLY_TABLE, columns, TOWN_ID + "=?", new String[]{townId}, null, null, null);

        if (!isDatabaseEmpty(cur)) {
            try {
                if (cur.moveToFirst()) {
                    do {
                        Supply supply = new Supply();

                        supply.setSupplyId(cur.getString(cur.getColumnIndex(SUPPLY_ID)));
                        supply.setSupplyName(cur.getString(cur.getColumnIndex(SUPPLY_NAME)));

                        supplyArray.add(supply);
                    } while (cur.moveToNext());
                }
                cur.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return supplyArray;
    }

    private Boolean isDatabaseEmpty(Cursor mCursor) {

        if (mCursor.moveToFirst()) {
            // NOT EMPTY
            return false;

        } else {
            // IS EMPTY
            return true;
        }

    }


}