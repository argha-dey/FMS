

package com.cyberswift.fms.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cyberswift.fms.model.OtherTown;
import com.cyberswift.fms.model.Supply;

import java.util.ArrayList;

public class OtherTownDB implements DBConstants {

    private static OtherTownDB obj = null;

    public synchronized static OtherTownDB obj() {

        if (obj == null)
            obj = new OtherTownDB();
        return obj;

    }

    public Boolean saveOtherTowns(Context context, ContentValues cv) {

        System.out.println(" ----------  ADD OTHER  TOWN LIST INTO  OTHER_TOWN_LIST TABLE  --------- ");
        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {
            mdb.insert(OTHER_TOWN_TABLE, null, cv);
            mdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mdb.endTransaction();
            return true;
        }

    }

    public ArrayList<OtherTown> getOtherTown(Context context) {

        ArrayList<OtherTown> otherTownList = new ArrayList<>();
        String[] columns = {_ID, OTHER_TOWN_ID, OTHER_TOWN_NAME};

        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getReadableDatabase();
        Cursor cur = mdb.query(OTHER_TOWN_TABLE, columns, null, null, null, null, null);

        if (!isDatabaseEmpty(cur)) {
            try {
                if (cur.moveToFirst()) {
                    do {
                        OtherTown otherTown = new OtherTown();

                        otherTown.setOtherTownId(cur.getString(cur.getColumnIndex(OTHER_TOWN_ID)));
                        otherTown.setOtherTownName(cur.getString(cur.getColumnIndex(OTHER_TOWN_NAME)));

                        otherTownList.add(otherTown);
                    } while (cur.moveToNext());
                }
                cur.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return otherTownList;
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

