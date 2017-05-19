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
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import Pojo.Decimal;

public class ActivitySales extends Activity implements View.OnClickListener{
ActionBar actionBar;
    String backroundcolour,colorchange;
DBhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sales);

        actionBar=getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(ActivitySales.this);
        Decimal valuetextsize = mydb.getStoreprice();
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


////////////////////////////////////jimmmy///////////////////////////////////////////
        ImageButton dayimage = (ImageButton) findViewById(R.id.dayopenimage);
        ImageButton tenderimage = (ImageButton) findViewById(R.id.Tenderimage);
        ImageButton salesreturnimage = (ImageButton) findViewById(R.id.salesreturnpage);
        ImageButton salesimage = (ImageButton) findViewById(R.id.salesimage);
        dayimage.setOnClickListener(this);
        tenderimage.setOnClickListener(this);
        salesreturnimage.setOnClickListener(this);
        salesimage.setOnClickListener(this);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


    }

    public  void SalesManagementhelpDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                   SALES ORDER MANAGEMENT     ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "User selects Sales Order Management to do the following options.\n" +
                "\n" +
                "Options:\n" +
                "\n" +
                "Day Opening:\n" +
                "Day Closing:\n" +
                "Sales Returns:\n" +
                "Sales:\n" +
                "\n  DAY OPENING\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "User select “Day Opening” to do the following options.\n" +
                "\n" +
                "Field Descriptions:\n" +
                "\n" +
                "POS Date:\n" +
                "Start Date:\n" +
                "\n" +
                "Input descriptions:\n" +
                "\n" +
                "Start Cash: The Cash on Hand when the user is opening a store.\n" +
                "The “Day Opening” is an optional step. \n" +
                "\n" +
                "Options:\n" +
                "\n" +
                "OPEN CASH: Declare the cash at the store opening.\n" +
                "EXIT:\n" +
                "\n" +
                "\n" +
                "DAY CLOSING:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "User select “Day Closing” to declare the cash at the store closing. This is an optional step.\n" +
                "\n" +
                "Field Descriptions: \n" +
                "\n" +
                "Text boxes to enter the number of notes of each denominations to declare the day close\n" +
                "\n" +
                "Options:\n" +
                "CLOSE CASH:\n" +
                "EXIT:");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }


    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.dayopenimage:
                Intent intent = new Intent(this, ActivityDayOpen.class);
                startActivity(intent);
                break;
            case R.id.Tenderimage:
                Intent intent1 = new Intent(this, ActivityTender.class);
                startActivity(intent1);
                break;
            case R.id.salesreturnpage:
                Intent intent2 = new Intent(this,ActivitySalesreturn .class);
                startActivity(intent2);
                break;
            case R.id.salesimage:
                Intent intent3 = new Intent(this, ActivitySalesbill.class);
                startActivity(intent3);
                break;

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);
        item.setVisible(false);

        final MenuItem item2 = menu.findItem(R.id.TextView);
        item2.setVisible(false);
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

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(ActivitySales.this,Activity_masterScreen1.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivitySales.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                SalesManagementhelpDialog();
                return true;
/*

            case R.id.action_settingpurchase:
                Intent p=new Intent(ActivitySales.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/

            case R.id.action_Masterscn1:
                Intent r=new Intent(ActivitySales.this,Activity_masterScreen1.class);
                startActivity(r);
                return true;
            case R.id.action_settinginv:
                Intent in=new Intent(ActivitySales.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivitySales.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;



        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(ActivitySales.this ,login.class);
        startActivity(intent);
    }

}