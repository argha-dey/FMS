


package com.cyberswift.fms.Db;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;

        import com.cyberswift.fms.model.OtherSupply;

        import java.util.ArrayList;

public class OtherSupplyDB implements DBConstants {

    private static OtherSupplyDB obj = null;

    public synchronized static OtherSupplyDB obj() {

        if (obj == null)
            obj = new OtherSupplyDB();
        return obj;

    }

    public Boolean saveOtherSupply(Context context, ContentValues cv) {

        System.out.println(" ----------  ADD OTHER SUPPLY LIST INTO  OTHER_SUPPLY_LIST TABLE  --------- ");
        SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getWritableDatabase();
        mdb.beginTransaction();
        try {
            mdb.insert(OTHER_SUPPLY_TABLE, null, cv);
            mdb.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            mdb.endTransaction();
            return true;
        }

    }

public ArrayList<OtherSupply> getOtherSupply(Context context,String ID){
    ArrayList<OtherSupply> OtherSupplyList= new ArrayList<OtherSupply>();
    String[] columns = {_ID, OTHER_SUPPLY_ID, OTHER_SUPPLY_NAME,OTHER_TOWN_ID};

    SQLiteDatabase mdb = FMSDatabaseHandler.getInstance(context).getReadableDatabase();
    Cursor cur = mdb.query(OTHER_SUPPLY_TABLE, columns, OTHER_TOWN_ID + "=?", new String[]{ID}, null, null, null);

    if (!isDatabaseEmpty(cur)) {
        try {
            if (cur.moveToFirst()) {
                do {
                    OtherSupply otherSupply = new OtherSupply();

                    otherSupply.setOtherSupplyId(cur.getString(cur.getColumnIndex(OTHER_SUPPLY_ID)));
                    otherSupply.setOtherSupplyName(cur.getString(cur.getColumnIndex(OTHER_SUPPLY_NAME)));

                    OtherSupplyList.add(otherSupply);
                } while (cur.moveToNext());
            }
            cur.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return OtherSupplyList;
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

