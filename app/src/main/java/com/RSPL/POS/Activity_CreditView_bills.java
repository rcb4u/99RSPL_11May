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

import java.text.DecimalFormat;
import java.util.ArrayList;

import Adapter.BillDetails_Adapter;
import Pojo.Sales;
import Pojo.Settlecustmodel;

public class Activity_CreditView_bills extends Activity {
    Adapter.CreditViewBill_Adapter billDetails_adapter;
    ListView viewbilllistview;
    TextView billnodetail,billdate,billgrandtotal;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity__credit_view_bills);
        viewbilllistview=(ListView)findViewById(R.id.viewbilllistView);
        billnodetail=(TextView)findViewById(R.id.billnodetails);
        billdate=(TextView)findViewById(R.id.billdate);
        billgrandtotal=(TextView)findViewById(R.id.billgrandtotal);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);


        Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        String Billno = extras.getString("billno");
        String Billdate = extras.getString("billdate");
        String Grandtotal = extras.getString("billamount");





        try {
            String userTypedCaption = Value.toString();
            Log.e("!!!!!!!!!!!", "" + Value.toString());

            if (userTypedCaption.equals("")) {
                return;
            }
            DBhelper mydb = new DBhelper(Activity_CreditView_bills.this);
            ArrayList<Sales> creditcustalldata = mydb.getAllsettleCreditCustomerbillitmDetalis(userTypedCaption);
            if (creditcustalldata != null) {
                if (billDetails_adapter == null) {
                    billDetails_adapter = new Adapter.CreditViewBill_Adapter(Activity_CreditView_bills.this, android.R.layout.simple_expandable_list_item_1, creditcustalldata);
                    viewbilllistview.setAdapter(billDetails_adapter);

                }
                billDetails_adapter.notifyDataSetChanged();
                billnodetail.setText(Billno);
                billdate.setText(Billdate);
                setCreditSummaryRow();
               // billgrandtotal.setText(Grandtotal);
                // billnodetail.setText();
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

    public void setCreditSummaryRow() {

        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = billDetails_adapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        billgrandtotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + billgrandtotal);



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
                Intent i = new Intent(Activity_CreditView_bills.this, Activity_SettleCreditCustomer.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_CreditView_bills.this);
                showIncentive.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}