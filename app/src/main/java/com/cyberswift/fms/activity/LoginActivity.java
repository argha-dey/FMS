package com.cyberswift.fms.activity;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.cyberswift.fms.Db.DBConstants;
import com.cyberswift.fms.Db.OtherSupplyDB;
import com.cyberswift.fms.Db.OtherTownDB;
import com.cyberswift.fms.Db.SupplyDB;
import com.cyberswift.fms.Db.TownDB;
import com.cyberswift.fms.R;
import com.cyberswift.fms.model.OtherSupply;
import com.cyberswift.fms.model.UserCredentials;
import com.cyberswift.fms.network.ServerResponseCallback;
import com.cyberswift.fms.model.UserClass;
import com.cyberswift.fms.network.VolleyTaskManager;
import com.cyberswift.fms.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity implements ServerResponseCallback, DBConstants {
    private TelephonyManager telephonyManager;
    private Context mContext;
    private EditText et_userName, et_passWord;
    boolean remember = false;
    boolean isLoginService = false;
    private VolleyTaskManager volleyTaskManager;
    String deviceId = null;
    public SharedPreferences.Editor loginPrefsEditor;
    public SharedPreferences loginPreferences;
    private Boolean saveLogin;
    public CheckBox checkBox;
    private UserClass user = new UserClass();
    private UserCredentials userCredentials = new UserCredentials();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        initView();

    }

    /**
     * Initialize the UI Components
     */
    public void initView() {
        et_passWord = (EditText) findViewById(R.id.et_passWord);
        et_userName = (EditText) findViewById(R.id.et_userName);
        checkBox = (CheckBox) findViewById(R.id.remember_me_check);
        if (Util.fetchUserClass(LoginActivity.this) != null) {

            user = Util.fetchUserClass(LoginActivity.this);
        }
        Util.getTimeFromNetwork(LoginActivity.this);


        if (user.getIsRemember()) {

            et_userName.setText(user.getUserName());

            et_passWord.setText(user.getPassword());

            checkBox.setChecked(user.getIsRemember());
        }


        volleyTaskManager = new VolleyTaskManager(mContext);
    }


    public void onLoginClick(View v) {

        if (et_userName.getText().toString().trim().isEmpty()) {

            Util.showMessageWithOk(mContext, "Please enter a username.");

        } else if (et_passWord.getText().toString().trim().isEmpty()) {

            Util.showMessageWithOk(mContext, "Please enter a password.");

        } else if (et_passWord.getText().toString().trim().length() < 4) {

            Util.showMessageWithOk(mContext, "Please enter the right password.");
        } else {

            if (Util.isInternetPresent(mContext))
                loginWebServiceCalling();
            else
                Util.showMessageWithOk(mContext, "Please check your internet connection.");
        }

    }


    public void loginWebServiceCalling() {
        HashMap<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("USER_NAME", et_userName.getText().toString());
        paramsMap.put("PASSWORD", et_passWord.getText().toString());
        paramsMap.put("DEVICE_ID", deviceId);
        paramsMap.put("REGISTRATION_ID", "test");
        isLoginService = true;
        volleyTaskManager.doLogin(paramsMap);

    }

    @Override
    public void onSuccess(JSONObject resultJsonObject) {
        //Log.d("JsonObject", "" + resultJsonObject);


        if (isLoginService) {

            String status = resultJsonObject.optString("IS_STATUS");
            Log.d("IS_STATUS", status);
            if (status.equals("1")) {


                user.setUserName(resultJsonObject.optString("USER_NAME"));
                user.setPassword(resultJsonObject.optString("PASSWORD"));
                user.setIsRemember(checkBox.isChecked());

// create json funtion calling here..
                taskListData(resultJsonObject);
                supplytownData(resultJsonObject);
                otherTown(resultJsonObject);

                String USER_NAME = resultJsonObject.optString("USER_NAME");
                String PASSWORD = resultJsonObject.optString("PASSWORD");
                String USER_TYPE = resultJsonObject.optString("USER_TYPE");
                String USER_ID = resultJsonObject.optString("USER_ID");

                Log.d("USER_NAME", USER_NAME);
                Log.d("PASSWORD", PASSWORD);
                Log.d("USER_TYPE", USER_TYPE);


                Util.saveUserClass(LoginActivity.this, user);


                openHomeActivity();

                Toast.makeText(mContext, "login Successful...", Toast.LENGTH_SHORT).show();


            } else {
                Util.showMessageWithOk(mContext, " login fail..Please try again!!");

            }
        }
    }


    @Override
    public void onError() {


    }

    public void openHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void taskListData(JSONObject resultJsonObject) {


        JSONArray taskList = resultJsonObject.optJSONArray("taskList");
        System.out.println("<----TASK LIST DATA PRINT HERE---->");
        for (int i = 0; i < taskList.length(); i++) {
            JSONObject taskObj = taskList.optJSONObject(i);

        }


    }

    private void supplytownData(JSONObject resultJsonObject) {


        JSONArray supplyTown = resultJsonObject.optJSONArray("supplyTown");
        System.out.println("<----SYPPLY TOWN LIST  DATA PRINT HERE---->");

        for (int i = 0; i < supplyTown.length(); i++) {

            JSONObject supplyObj = supplyTown.optJSONObject(i);
            JSONObject town = supplyObj.optJSONObject("town");
            ContentValues townCV = new ContentValues();
            townCV.put(DBConstants.TOWN_NAME, town.optString("TOWN_NAME"));
            townCV.put(DBConstants.TOWN_ID, town.optString("TOWN_ID"));


            new TownDB().saveTowns(this, townCV);


            JSONArray sList = supplyObj.optJSONArray("sList");
            for (int j = 0; j < sList.length(); j++) {
                JSONObject sListobj = sList.optJSONObject(j);

                ContentValues SupplyCV = new ContentValues();
                SupplyCV.put(DBConstants.TOWN_ID, town.optString("TOWN_ID"));
                SupplyCV.put(DBConstants.TOWN_NAME, town.optString("TOWN_NAME"));
                SupplyCV.put(DBConstants.SUPPLY_NAME, sListobj.optString("SUPPLY_NAME"));
                SupplyCV.put(DBConstants.SUPPLY_ID, sListobj.optString("SUPPLY_ID"));

                new SupplyDB().saveSupplyList(this, SupplyCV);

            }

        }


    }

    private void otherTown(JSONObject resultJsonObject) {

        JSONArray OtherTownSupply = resultJsonObject.optJSONArray("OthersupplyTown");


        for (int i = 0; i < OtherTownSupply.length(); i++) {

            JSONObject supplyObj = OtherTownSupply.optJSONObject(i);
            JSONObject town = supplyObj.optJSONObject("town");
            ContentValues OtherTownCV = new ContentValues();
            OtherTownCV.put(DBConstants.OTHER_TOWN_ID, town.optString("TOWN_ID"));
            OtherTownCV.put(DBConstants.OTHER_TOWN_NAME, town.optString("TOWN_NAME"));
            new OtherTownDB().saveOtherTowns(this, OtherTownCV);

            JSONArray sList = supplyObj.optJSONArray("sList");
            for (int j = 0; j < sList.length(); j++) {
                JSONObject sListobj = sList.optJSONObject(j);
                ContentValues OtherSupplyCV = new ContentValues();

                OtherSupplyCV.put(DBConstants.OTHER_TOWN_ID, town.optString("TOWN_ID"));
                OtherSupplyCV.put(DBConstants.OTHER_SUPPLY_ID, sListobj.optString("SUPPLY_ID"));
                OtherSupplyCV.put(DBConstants.OTHER_SUPPLY_NAME, sListobj.optString("SUPPLY_NAME"));
                new OtherSupplyDB().saveOtherSupply(this, OtherSupplyCV);


            }


        }
    }

}
