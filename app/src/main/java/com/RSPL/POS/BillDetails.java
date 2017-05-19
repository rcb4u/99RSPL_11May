package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.BillDetails_Adapter;
import Pojo.Settlecustmodel;

public class BillDetails extends  Activity {
    ListView billlistView;
    TextView outstanding;
    ActionBar actionBar;

    DBhelper db;
    BillDetails_Adapter billDetails_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        billlistView = (ListView) findViewById(R.id.billlistView);
        Log.e("***********Lt1*******", billlistView.toString());

        outstanding = (TextView) findViewById(R.id.billoutstandingblance);



        Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        String Value1=extras.getString("outstandblance");

        try {
            String userTypedCaption = Value.toString();
            Log.e("!!!!!!!!!!!", "" + Value.toString());

            if (userTypedCaption.equals("")) {
                return;
            }
            DBhelper mydb = new DBhelper(BillDetails.this);
            ArrayList<Settlecustmodel> creditcustalldata = mydb.getAllsettleCreditCustomerDetalis(userTypedCaption);
            if (creditcustalldata != null) {
                if (billDetails_adapter == null) {
                    billDetails_adapter = new BillDetails_Adapter(BillDetails.this, android.R.layout.simple_expandable_list_item_1, creditcustalldata);
                    billlistView.setAdapter(billDetails_adapter);
                    outstanding.setText(Value1);

                }
                billDetails_adapter.notifyDataSetChanged();
       /* Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        creditcustalldata=db.getAllsettleCreditCustomerDetalis(Value);



        billDetails_adapter=new BillDetails_Adapter(this,android.R.layout.simple_expandable_list_item_1,creditcustalldata);
        billlistView.setAdapter(billDetails_adapter);*/
            }
        }catch (Exception e){
            e.printStackTrace();
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_settle_creditcustomer, menu);

        // inflater.inflate(R.menu.activity_main_actions, menu);
        // inflater.inflate(R.menu.menu_activity_salesbill,menu);
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i = new Intent(BillDetails.this, ActivitySalesbill.class);
                startActivity(i);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetCreditviewbillDetails(String mobileno,String billnoinv,String Billdate,String billamount){

        Log.e("DailySales", mobileno);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", mobileno);
        dataBundle.putString("billno", billnoinv);
        dataBundle.putString("billdate", Billdate);
        dataBundle.putString("billamount", billamount);

        //dataBundle.putString("grandtotal", Grandtotal);


        Intent intent = new Intent(BillDetails.this, Activity_CreditView_bills.class);
        intent.putExtras(dataBundle);
        intent.putExtras(dataBundle);
        intent.putExtras(dataBundle);
        intent.putExtras(dataBundle);
       // intent.putExtras(dataBundle);


        startActivity(intent);
    }
}