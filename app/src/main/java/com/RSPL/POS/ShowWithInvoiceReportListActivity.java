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

import Adapter.ReportWithInvoiceListAdapter;
import Pojo.SalesReturnReportModel;


public class ShowWithInvoiceReportListActivity extends Activity {

    ListView listView;
    ReportWithInvoiceListAdapter salelistAdapter;
    ArrayList<SalesReturnReportModel> alldata;
    DBhelper helper;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_withinvoice_list);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        helper = new DBhelper(this);


        Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        alldata=helper.getSalesReturnWithInvoiceDetailReport(Value);


        listView = (ListView) findViewById(R.id.lv_WithInvoiceReport);
        Log.e("***********Lt1*******", listView.toString());

        salelistAdapter=new ReportWithInvoiceListAdapter(this,alldata,android.R.layout.simple_expandable_list_item_1,null);
        listView.setAdapter(salelistAdapter);


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

            Intent i=new Intent(ShowWithInvoiceReportListActivity.this, ReportTabActivitySalesReturnReport.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.action_settinginfo){
            ShowIncentive showIncentive = new ShowIncentive(ShowWithInvoiceReportListActivity.this);
            showIncentive.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
