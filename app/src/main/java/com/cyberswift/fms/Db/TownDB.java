package com.cyberswift.fms.Db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyberswift.fms.model.Supply;
import com.cyberswift.fms.model.Town;

import org.w3c.dom.Node;

import java.util.ArrayList;

public class TownDB implements DBConstants {

    private static TownDB obj = null;

    public synchronized static TownDB obj() {

        if (obj == null)
            obj = new TownDB();
        return obj;

    }

    public Boolean saveTowns(Context context, ContentValues cv) {

        System.out.println(" ----------  ADD TOWNS INTO TOWN TABLE  --------- ");
        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {

            mdb.insert(TOWN_TABLE, null, cv);
            mdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mdb.endTransaction();
            return true;
        }

    }

    public ArrayList<Town> getTown(Context context) {

        ArrayList<Town> TownArray = new ArrayList<>();
        String[] columns = {_ID, TOWN_ID, TOWN_NAME};

        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getReadableDatabase();
        Cursor cur = mdb.query(TOWN_TABLE, columns, null, null, null, null, null);

        if (!isDatabaseEmpty(cur)) {
            try {
                if (cur.moveToFirst()) {
                    do {
                        Town town = new Town();

                        town.setTownId(cur.getString(cur.getColumnIndex(TOWN_ID)));
                        town.setTownName(cur.getString(cur.getColumnIndex(TOWN_NAME)));


                        TownArray.add(town);
                    } while (cur.moveToNext());
                    Town town = new Town();
                    town.setTownName("OtherTown");
                    town.setTownId("101");
                    TownArray.add(town);
                }

                cur.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TownArray;
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