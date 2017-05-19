package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SalesReturnReportActivity extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sales_return);

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Button WithInvoiceBtn=(Button)findViewById(R.id.WithInvoiceReport);
        Button WithoutInvoiceBtn=(Button)findViewById(R.id.WithoutInvoiceReport);

        WithInvoiceBtn.setOnClickListener(this);
        WithoutInvoiceBtn.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.WithInvoiceReport:
                Intent intent1 = new Intent(this, ReportTabActivitySalesReturnReport.class);
                startActivity(intent1);
                break;
            case R.id.WithoutInvoiceReport:
                Intent intent2 = new Intent(this, ReportTabActivitySalesReturnReport.class);
                startActivity(intent2);

                break;

        }
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
        switch (item.getItemId()) {


            case R.id.action_settings:

            Intent i=new Intent(SalesReturnReportActivity.this,SaleReportActivity.class);
            startActivity(i);
            return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(SalesReturnReportActivity.this);
                showIncentive.show();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(SalesReturnReportActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_settinginv:
                Intent in=new Intent(SalesReturnReportActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(SalesReturnReportActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}


