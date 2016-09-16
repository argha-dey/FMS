package com.cyberswift.fms.Db;

import android.os.Environment;

public interface DBConstants {

    /**
     * Database Name
     */


   public static String DB_NAME = "FMS.db";

    /*for external storage*/

   // public static final String DB_NAME = Environment.getExternalStorageDirectory() + "/FMS.db";


    /**
     * Database version
     */
    public static final int DB_VERSION = 1;

    public static final String _ID = "_id";

    final String CREATE_TABLE_BASE = "CREATE TABLE IF NOT EXISTS ";

    final String ON = " ON ";

    final String PRIMARY_KEY = " PRIMARY KEY";

    final String INTEGER = " Integer";

    final String TEXT = " TEXT";

    final String DATE_TIME = " DATETIME";

    final String BLOB = " BLOB";

    final String AUTO_INCREMENT = " AUTOINCREMENT";

    final String UNIQUE = "UNIQUE";

    final String START_COLUMN = " ( ";

    final String FINISH_COLUMN = " ) ";

    final String COMMA = ",";

    final String ON_CONFLICT_REPLACE = "ON CONFLICT REPLACE";

    // TOWN Table.....
    public static final String TOWN_TABLE = " townTable";

    public static final String TOWN_ID = "townID";

    public static final String TOWN_NAME = "townName";

    //SUPPLY_LIST TABLE....

    public static final String SUPPLY_TABLE = " supplyTable";

    public static final String SUPPLY_ID = "supplyID";

    public static final String SUPPLY_NAME = "supplyName";


    // OTHER SUPPLY TOWN LIST TABLE....

    public static final String OTHER_TOWN_TABLE=" otherTownTable";

    public static final String OTHER_TOWN_ID="otherTownID";

    public static final String OTHER_TOWN_NAME="otherTownName";

    //OTHER SUPPLY LIST TABLE .....

     public static final String OTHER_SUPPLY_TABLE = " otherSupplyTable";

    public static final String OTHER_SUPPLY_ID= "otherSupplyId";

    public  static final String OTHER_SUPPLY_NAME="otherSupplyName";

/* TOWN AND SUPPLY  COMBO TABLE LIST...

    public static final String TOWN_AND_SUPPLY_COMBO_TABLE = " TOWN_AND_SUPPLY_COMBO_TABLE";

    public static final String COMBO_TOWN_ID = "townID";

    public static final String COMBO_TOWN_NAME = "townName";

    public static final String COMBO_SUPPLY_ID = "supplyID";

    public static final String COMBO_SUPPLY_NAME = "supplyName";
    */

}
