package com.cyberswift.fms.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cyberswift.fms.Db.OtherSupplyDB;
import com.cyberswift.fms.Db.OtherTownDB;
import com.cyberswift.fms.Db.SupplyDB;
import com.cyberswift.fms.Db.TownDB;

import com.cyberswift.fms.R;
import com.cyberswift.fms.callback.AlertDialogCallBack;
import com.cyberswift.fms.dropdown.DropDownViewForXML;
import com.cyberswift.fms.model.OtherSupply;
import com.cyberswift.fms.model.OtherTown;
import com.cyberswift.fms.model.Supply;
import com.cyberswift.fms.model.Town;
import com.cyberswift.fms.model.UserClass;
import com.cyberswift.fms.util.Util;

import java.util.ArrayList;

public class TaskReportingActivity extends AppCompatActivity {


    private ImageView btn_start, logout;
    private Context mContext;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_task);
        mContext = TaskReportingActivity.this;

        initView();
    }

    private void initView() {

        btn_start = (ImageView) findViewById(R.id.btn_start);
        logout = (ImageView) findViewById(R.id.iv_lgout_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, StartTaskReportingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onMenuClick(View v) {

        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void LogoutClicked(View mView) {
        Util.showCallBackMessageWithOkCancel(TaskReportingActivity.this,
                "Do you want to log out?", new AlertDialogCallBack() {
                    @Override
                    public void onSubmit() {
                        UserClass user = Util.fetchUserClass(TaskReportingActivity.this);
                        user.setIsRemember(true);
                        Util.saveUserClass(TaskReportingActivity.this, user);
                        Intent intent = new Intent(TaskReportingActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }
}