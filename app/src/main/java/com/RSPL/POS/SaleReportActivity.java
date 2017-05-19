package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import Pojo.Decimal;

public class SaleReportActivity extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    DBhelper db;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_sale);
        db = new DBhelper(SaleReportActivity.this);

        Decimal valuetextsize = db.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();

        if(backroundcolour.matches("Orange")){
            colorchange="#ff9033";
        }
        if(backroundcolour.matches("Orange Dark")){
            colorchange="#EE782D";
            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
        }
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));
        ImageButton SalesBtn=(ImageButton)findViewById(R.id.SaleReportSales);
        ImageButton SalesRetrnBtn=(ImageButton)findViewById(R.id.SaleReportSalesReturn);

        SalesBtn.setOnClickListener(this);
        SalesRetrnBtn.setOnClickListener(this);

    }

    public  void help_sales_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("            SALES REPORT  ");
        alertDialog.setMessage("Objective:\n" +
                        "\t\n" +
                        "Following are the two options below:\n" +
                        "\n" +
                        "\t * Sales Report \n" +
                        "\t * Sales Return Report\n" +

                        " \n"
        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.SaleReportSales:
                Intent intent = new Intent(this, ReportTabActivitySalesReport.class);
                startActivity(intent);
                break;
            case R.id.SaleReportSalesReturn:
                Intent in = new Intent(this, ReportTabActivitySalesReturnReport.class);
                startActivity(in);
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
                Intent i = new Intent(SaleReportActivity.this, ActivityLoyalityCust.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(SaleReportActivity.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_sales_dialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(SaleReportActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_settinginv:
                Intent in=new Intent(SaleReportActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(SaleReportActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:

                Intent intent = new Intent(SaleReportActivity.this,Activity_masterScreen1.class);
                startActivity(intent);

                return true;



            case R.id.action_logout:
                Logout();

                return true;


        }


            return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(SaleReportActivity.this ,login.class);
        startActivity(intent);
    }


}

