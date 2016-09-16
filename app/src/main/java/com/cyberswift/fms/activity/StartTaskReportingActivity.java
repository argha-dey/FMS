package com.cyberswift.fms.activity;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cyberswift.fms.Db.OtherSupplyDB;
import com.cyberswift.fms.Db.OtherTownDB;
import com.cyberswift.fms.Db.SupplyDB;
import com.cyberswift.fms.Db.TownDB;
import com.cyberswift.fms.R;
import com.cyberswift.fms.dropdown.DropDownViewForXML;
import com.cyberswift.fms.model.OtherSupply;
import com.cyberswift.fms.model.OtherTown;
import com.cyberswift.fms.model.Supply;
import com.cyberswift.fms.model.Town;
import com.cyberswift.fms.util.Util;

import java.util.ArrayList;

public class StartTaskReportingActivity extends AppCompatActivity {

    private Button cancle, done;
    private EditText assingBy;
    private ArrayList<Town> townList = new ArrayList<>();
    private ArrayList<Supply> supplyList = new ArrayList<>();
    private ArrayList<OtherTown> otherTownList = new ArrayList<>();
    private ArrayList<OtherSupply> otherSupplyList = new ArrayList<>();
    private Context mContext;
    public boolean flag = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_task);
        mContext = StartTaskReportingActivity.this;
        initView();
        showTownAndSupply();
    }

    private void showTownAndSupply() {


        {
            townList = new TownDB().getTown(mContext);
            if (townList.size() > 0) {


                final DropDownViewForXML dropDown_town = (DropDownViewForXML)findViewById(R.id.dropdown_town);

                final DropDownViewForXML dropDown_supply = (DropDownViewForXML)findViewById(R.id.dropdown_supply);
                populateTownDropdown(dropDown_town);

                final DropDownViewForXML dropDown_otherSupply = (DropDownViewForXML)findViewById(R.id.dropDown_otherSupply);
                final RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.rl_new_town);


                dropDown_town.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        if (dropDown_town.getText().toString().trim().equalsIgnoreCase("OtherTown"))

                        {
                            populateOtherTowns(dropDown_supply);
                            flag = true;
                        } else {

                            populateSupplyDropdown(dropDown_supply, position);
                            relativeLayout.setVisibility(View.GONE);
                            flag = false;
                        }
                        assingBy.setVisibility(View.GONE);
                    }
                });


                dropDown_supply.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                        if (flag == true) {
                            populateOtherSupply(dropDown_otherSupply, position);
                            relativeLayout.setVisibility(View.VISIBLE);
                            assingBy.setVisibility(View.VISIBLE);
                        }


                    }
                });
                Button btn_done = (Button)findViewById(R.id.btn_done);
                btn_done.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    if (dropDown_town.getText().toString().trim().equalsIgnoreCase("") || dropDown_supply.getText().toString().trim().equalsIgnoreCase(""))
                                                        Toast.makeText(getApplicationContext(), "Please Select... ", Toast.LENGTH_SHORT).show();


                                                    else if ((dropDown_town.getText().toString() == "OtherTown") && (dropDown_supply.getText().toString().trim().equalsIgnoreCase("") || dropDown_otherSupply.getText().toString().trim().equalsIgnoreCase("") || assingBy.getText().toString().trim().equalsIgnoreCase(""))) {
                                                        Toast.makeText(getApplicationContext(), "Please Select... ", Toast.LENGTH_SHORT).show();
                                                    } else {


                                                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                                                        alertDialogBuilder.setMessage("Are you sure?");

                                                        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface arg0, int arg1) {
                                                                Toast.makeText(getApplicationContext(), "You clicked yes button", Toast.LENGTH_LONG).show();
                                                            }
                                                        });

                                                        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                            @Override
                                                           public void onClick(DialogInterface dialog, int which) {


                                                            }
                                                        });
                                                        alertDialogBuilder.show();
                                                    }

                                                }
                                            }

                );


                assingBy = (EditText) findViewById(R.id.tv_assigned_by);


            } else {
                Util.showMessageWithOk(mContext, "You are not assigned to any Town.");
            }
        }
    }


    private void populateTownDropdown(DropDownViewForXML dropDown_town) {
        dropDown_town.setText(" Select ");
        if (townList.size() > 0) {
            dropDown_town.setEnabled(true);
            String[] townArray = new String[townList.size()];

            for (int i = 0; i < townList.size(); i++) {
                townArray[i] = townList.get(i).getTownName();

            }
            dropDown_town.setItems(townArray);
            dropDown_town.setText("");
            dropDown_town.setHint(" select ");
        } else {
            dropDown_town.setEnabled(false);
        }
    }

    private void populateSupplyDropdown(DropDownViewForXML dropDown_supply, int position) {
        // supplyList.clear();
        supplyList = new SupplyDB().getSupply(mContext, townList.get(position).getTownId());
        if (supplyList.size() > 0) {
            dropDown_supply.setEnabled(true);
            String[] supplyArray = new String[supplyList.size()];

            for (int i = 0; i < supplyList.size(); i++) {
                supplyArray[i] = supplyList.get(i).getSupplyName();

            }
            dropDown_supply.setItems(supplyArray);
            dropDown_supply.setText("");
            dropDown_supply.setHint(" select ");
        } else {
            dropDown_supply.setEnabled(false);
        }

    }


    /**
     * populate the other_towns on supply dropdown.
     */
    private void populateOtherTowns(DropDownViewForXML dropDown_supply) {

        otherTownList.clear();
        otherTownList = new OtherTownDB().getOtherTown(mContext);
        if (otherTownList.size() > 0) {
            dropDown_supply.setEnabled(true);
            String[] otherTownArray = new String[otherTownList.size()];

            for (int i = 0; i < otherTownList.size(); i++) {
                otherTownArray[i] = otherTownList.get(i).getOtherTownName();

            }


            dropDown_supply.setItems(otherTownArray);
            dropDown_supply.setText("");
            dropDown_supply.setHint(" select ");

        } else {
            dropDown_supply.setEnabled(false);
        }
    }

    /**
     * *populate the other_Supply on dropdownList
     */

    private void populateOtherSupply(DropDownViewForXML dropDown_OtherSupply, int pos) {

        dropDown_OtherSupply.setText(" Select ");
        otherSupplyList.clear();
        otherSupplyList = new OtherSupplyDB().getOtherSupply(mContext, otherTownList.get(pos).getOtherTownId());
        if (otherSupplyList.size() > 0) {
            dropDown_OtherSupply.setEnabled(true);
            String[] otherSupplyArray = new String[otherSupplyList.size()];

            for (int i = 0; i < otherSupplyList.size(); i++) {
                otherSupplyArray[i] = otherSupplyList.get(i).getOtherSupplyName();

            }

            dropDown_OtherSupply.setItems(otherSupplyArray);
            dropDown_OtherSupply.setText("");
            dropDown_OtherSupply.setHint(" select ");
        } else {
            dropDown_OtherSupply.setEnabled(false);
        }

    }

    private void initView() {

        cancle = (Button) findViewById(R.id.btn_cancel);
        done = (Button) findViewById(R.id.btn_done);

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,TaskReportingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
