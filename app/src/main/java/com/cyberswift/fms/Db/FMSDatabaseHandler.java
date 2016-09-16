package com.cyberswift.fms.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class FMSDatabaseHandler extends SQLiteOpenHelper implements DBConstants {

    private static FMSDatabaseHandler mDatabase;
    private Context mcContext;
    private static final String TAG = "FMSDatabaseHandler";

    public FMSDatabaseHandler(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
        this.mcContext = context;
    }

    public static final FMSDatabaseHandler getInstance(Context context) {
        if (mDatabase == null) {
            mDatabase = new FMSDatabaseHandler(context);
            mDatabase.getWritableDatabase();
        }
        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate Database");
        // create table
        String[] createStatements = getCreateTableStatements();
        int total = createStatements.length;
        for (int i = 0; i < total; i++) {
            Log.i(TAG, "executing create" + createStatements[i]);
            Log.i("Database", "Database created");
            sqLiteDatabase.execSQL(createStatements[i]);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private String[] getCreateTableStatements() {

        String[] create = new String[4];

        // TOWN table -> _id , townID, townName
        String TOWN_TABLE_ST = CREATE_TABLE_BASE + TOWN_TABLE + START_COLUMN + _ID + INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + TOWN_ID + TEXT + COMMA + TOWN_NAME + TEXT + COMMA + UNIQUE + START_COLUMN + TOWN_ID + FINISH_COLUMN
                + ON_CONFLICT_REPLACE + FINISH_COLUMN;

        create[0] = TOWN_TABLE_ST;

        String SUPPLY_LIST_TABLE_ST = CREATE_TABLE_BASE + SUPPLY_TABLE + START_COLUMN + _ID + INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + TOWN_ID + TEXT + COMMA + TOWN_NAME + TEXT + COMMA + SUPPLY_ID + TEXT + COMMA + SUPPLY_NAME + TEXT + COMMA + UNIQUE + START_COLUMN + SUPPLY_ID + FINISH_COLUMN
                + ON_CONFLICT_REPLACE + FINISH_COLUMN;

        create[1] = SUPPLY_LIST_TABLE_ST;

        String OTHER_SUPPLY_TOWN_ST = CREATE_TABLE_BASE + OTHER_TOWN_TABLE + START_COLUMN + _ID + INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + OTHER_TOWN_ID + TEXT + COMMA + OTHER_TOWN_NAME + TEXT + COMMA + UNIQUE + START_COLUMN + OTHER_TOWN_ID + FINISH_COLUMN
                + ON_CONFLICT_REPLACE + FINISH_COLUMN;

        create[2] = OTHER_SUPPLY_TOWN_ST;

        String OTHER_SUPPPLY_LIST_TABLE_ST = CREATE_TABLE_BASE + OTHER_SUPPLY_TABLE + START_COLUMN + _ID + INTEGER + PRIMARY_KEY + AUTO_INCREMENT + COMMA
                + OTHER_TOWN_ID + TEXT + COMMA + OTHER_SUPPLY_ID + TEXT + COMMA + OTHER_SUPPLY_NAME + TEXT + COMMA + UNIQUE + START_COLUMN + OTHER_SUPPLY_ID + FINISH_COLUMN
                + ON_CONFLICT_REPLACE + FINISH_COLUMN;

        create[3] = OTHER_SUPPPLY_LIST_TABLE_ST;
        return create;
    }
}
