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


public class ActivityMainMaintainence extends Activity implements View.OnClickListener {
    ActionBar actionBar;
    DBhelper mydb;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_main_maintainence);
        mydb = new DBhelper(ActivityMainMaintainence.this);

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
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        ImageButton imageButton = (ImageButton) findViewById(R.id.maintainimage);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.statusimage);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
    }

    public  void SystemMaintainancehelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  SYSTEM MAINTENANCE        ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "User select System Maintenance to be able to raise a ticket when the user faces the issue and either report/monitor the issue status.\n" +
                "\n" +
                "Options:\n" +
                "\n" +
                "*System Maintenance Report an Issue\n" +
                "*System Maintainance check the status of an issue.\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
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
                Intent i=new Intent(ActivityMainMaintainence.this,Activity_masterScreen1.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityMainMaintainence.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                SystemMaintainancehelpdialog();
                return true;
         /*   case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityMainMaintainence.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityMainMaintainence.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityMainMaintainence.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityMainMaintainence.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityMainMaintainence.this ,login.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.maintainimage:
                Intent intent = new Intent(this, ActivityMaintain.class);
                startActivity(intent);
                break;
            case R.id.statusimage:
                Intent intent1 = new Intent(this, maintainenecereport.class);
                startActivity(intent1);
        }
    }
}