package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import Adapter.ReportPurchaseList1MonthAdapter;
import Pojo.PurchaseProductReportModel;


public class ShowPurchaseReport1MonthListActivity extends Activity {

    ListView listView;
    ReportPurchaseList1MonthAdapter productlistAdapter;
    ArrayList<PurchaseProductReportModel> alldata;
    DBhelper helper;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_purchaselist);
        helper = new DBhelper(this);

        Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        alldata=helper.getall1MonthPurchasedata(Value);

        listView = (ListView) findViewById(R.id.lv_PurchaseProduct);
        Log.e("***********Lt1*******", listView.toString());

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        productlistAdapter=new ReportPurchaseList1MonthAdapter(this,alldata,android.R.layout.simple_expandable_list_item_1,null);
        listView.setAdapter(productlistAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

           /* Intent i=new Intent(ShowPurchaseReport1MonthListActivity.this, ReportTabActivityPurchasing.class);
            startActivity(i);*/
            Intent intent = new Intent(ShowPurchaseReport1MonthListActivity.this,ReportTabActivityPurchasing.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

            return true;
        }

        if(id==R.id.action_settinginfo){
            ShowIncentive showIncentive = new ShowIncentive(ShowPurchaseReport1MonthListActivity.this);
            showIncentive.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
