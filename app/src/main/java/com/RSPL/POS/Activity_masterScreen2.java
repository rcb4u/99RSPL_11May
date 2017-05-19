package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Pojo.Decimal;

/**
 * Created by w7 on 1/27/2016.
 */
public class Activity_masterScreen2 extends Activity implements View.OnClickListener{
    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
DBhelper mydb;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_master_screen2);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb=new DBhelper(Activity_masterScreen2.this);
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



        ImageButton strimage = (ImageButton) findViewById(R.id.master_storemanagement_button);
        ImageButton prodimage = (ImageButton) findViewById(R.id.master_productmanagement_button);
       // ImageButton taximage = (ImageButton) findViewById(R.id.master_taxmanagememt_button);
        ImageButton vendorimage = (ImageButton) findViewById(R.id.master_vendormanagement_button);
        ImageButton schemaimage = (ImageButton) findViewById(R.id.master_schemamanagement_button);
        ImageButton customerimage = (ImageButton) findViewById(R.id.master_custmanagement_button);
        ImageButton localprodimage = (ImageButton) findViewById(R.id.master_localproduct_button);
        ImageButton localvendorimage = (ImageButton) findViewById(R.id.master_local_vendor_button);
//        ImageButton localprodimagecpg = (ImageButton) findViewById(R.id.master_localproductcpg_button);
//        ImageButton prodcpg = (ImageButton) findViewById(R.id.master_productcpg_button);

        ImageButton doctor= (ImageButton) findViewById(R.id.master__doctor_button);

        strimage.setOnClickListener(this);
        prodimage.setOnClickListener(this);
       // taximage.setOnClickListener(this);
        vendorimage.setOnClickListener(this);
        schemaimage.setOnClickListener(this);
        customerimage.setOnClickListener(this);
        localprodimage.setOnClickListener(this);
        localvendorimage.setOnClickListener(this);
//        localprodimagecpg.setOnClickListener(this);
//        prodcpg.setOnClickListener(this);
        doctor.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
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

    public void Masterscreen2Dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("              MASTER DATA MANAGEMENT    ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "User select the “Master Data Management” option and navigates to the following options:\n" +
                "\n" +
                "*Store Management\n" +
                "*Product(Pharma)\n" +
                "*Local Product\n" +
                "*Capturing Doctor\n" +
                "*Distributors\n" +
                "*Scheme Management For Companies\n" +
                "*Customer Management\n" +
                "*Local Vendors");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {
            case R.id.showHome:
                         Intent i = new Intent(Activity_masterScreen2.this, Activity_masterScreen1.class);
                         startActivity(i);
                         return true;

            case R.id.action_settings:
                Intent i1 = new Intent(Activity_masterScreen2.this, Activity_masterScreen1.class);
                startActivity(i1);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_masterScreen2.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                Masterscreen2Dialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_masterScreen2.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_settinginv:
                Intent in=new Intent(Activity_masterScreen2.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_masterScreen2.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent r=new Intent(Activity_masterScreen2.this,Activity_masterScreen1.class);
                startActivity(r);
                return true;

            case R.id.action_logout:
                Logout();

                return true;



        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(Activity_masterScreen2.this ,login.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.master_storemanagement_button:
                Intent intent = new Intent(this, ActivityStore.class);
                startActivity(intent);
                break;
            case R.id.master_productmanagement_button:
                Intent intent4 = new Intent(this,ActivityProduct.class);
                startActivity(intent4);
                break;
//            case R.id.master_taxmanagememt_button:
//                Intent intent5 = new Intent(this, ActivityTax.class);
//                startActivity(intent5);
//                break;
            case R.id.master_vendormanagement_button:
                Intent intent6 = new Intent(this, ActivityVendor.class);
                startActivity(intent6);
                break;

            case R.id.master_schemamanagement_button:
                /*Intent intent7 = new Intent(this,ActivityScheme.class);
                startActivity(intent7);*/
                clearbuttondialog();
                break;

            case R.id.master_custmanagement_button:
                Intent intent8 = new Intent(this, ActivityCustomer.class);
                startActivity(intent8);
                break;

            case R.id.master_local_vendor_button:
                Intent intent9 = new Intent(this,ActivitylocalVendor .class);
                startActivity(intent9);
                break;

            case R.id.master_localproduct_button:
                Intent intent10 = new Intent(this, ActivityLocalProduct.class);
                startActivity(intent10);
                break;

//            case R.id.master_localproductcpg_button:
//                Intent intent11 = new Intent(this, Activitylocalproductcpg.class);
//                startActivity(intent11);
//                break;
//
//            case R.id.master_productcpg_button:
//                Intent intent12 = new Intent(this, Activityproductcpg.class);
//                startActivity(intent12);
//                break;

            case R.id.master__doctor_button:
                Intent intent13 = new Intent(this, Activity_Doctor.class);
                startActivity(intent13);
                break;

        }
    }
    public  void clearbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                              ");
        alertDialog.setMessage("Under Progress");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


            /*    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }
}
