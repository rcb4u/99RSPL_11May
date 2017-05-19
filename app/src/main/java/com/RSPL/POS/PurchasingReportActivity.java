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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import Pojo.Decimal;


public class PurchasingReportActivity extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    String backroundcolour,colorchange;
DBhelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_report);
        helper = new DBhelper(PurchasingReportActivity.this);

        Decimal valuetextsize = helper.getStoreprice();
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

        // ImageButton PurchaseBtn=(ImageButton)findViewById(R.id.PurchaseReportPurchase);
        ImageButton InventoryBtn=(ImageButton)findViewById(R.id.PurchaseReportInvntry);
        ImageButton VendorPaymntBtn=(ImageButton)findViewById(R.id.PurchaseReportVendPaymnt);
        ImageButton VendorReturnBtn=(ImageButton)findViewById(R.id.PurchaseReportVendRetrn);

       // PurchaseBtn.setOnClickListener(this);
        InventoryBtn.setOnClickListener(this);
        VendorPaymntBtn.setOnClickListener(this);
        VendorReturnBtn.setOnClickListener(this);

    }

    public  void help_purchasereports_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("               PURCHASING REPORT ");
        alertDialog.setMessage("Objective:\n" +
                        "\t\n" +
                        "Following are the option available on this  page:\n" +
                        "\n" +
                        "\t * Purchase Report \n" +
                        "\t * Inventory Report\n" +
                        "\t * Vendor Payment\n" +
                        "\t * Vendor Return Report\n"
        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });


        alertDialog.show();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

           /* case R.id.PurchaseReportPurchase:
                Intent intent = new Intent(this, ReportTabActivityPurchasing.class);
                startActivity(intent);
                break;*/
            case R.id.PurchaseReportInvntry:
                Intent in = new Intent(this, ReportTabActivityInventory.class);
                startActivity(in);
                break;
            case R.id.PurchaseReportVendPaymnt:
                Intent intent3 = new Intent(this, VendorPaymentReportActivity.class);
                startActivity(intent3);
                break;
            case R.id.PurchaseReportVendRetrn:
                Intent in4 = new Intent(this, ReportVendorReturnActivity.class);
                startActivity(in4);
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
                Intent i = new Intent(PurchasingReportActivity.this, ActivityLoyalityCust.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(PurchasingReportActivity.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                help_purchasereports_dialog();
                return true;

            case R.id.action_Masterscn1:
                Intent intent=new Intent(PurchasingReportActivity.this,Activity_masterScreen1.class);
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
        Intent intent = new Intent(PurchasingReportActivity.this ,login.class);
        startActivity(intent);
    }


}

