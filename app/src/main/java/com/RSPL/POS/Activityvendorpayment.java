package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import Pojo.Decimal;


public class Activityvendorpayment extends Activity implements View.OnClickListener {
    ActionBar actionBar;
    String backroundcolour,colorchange;
 DBhelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityvendorpayment);

        actionBar=getActionBar();
    //    actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(Activityvendorpayment.this);

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


        ImageButton purchase = (ImageButton) findViewById(R.id.directpayment);
        Button inventory = (Button) findViewById(R.id.indirectpayment);

        purchase.setOnClickListener(this);
        inventory.setOnClickListener(this);


    }

    public  void VendorPaymenthelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("              VENDOR PAYMENT       ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "There are 2 types of payments retailer can do – " +
                "\n" +
                " *Direct payments and indirect payments.\n" +
                "\n" +
                "*Vendor payments are inventory related.\n" +
                "*Indirect payments are non-inventory related.\n" +
                "\n" +"");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.directpayment:
                Intent intent = new Intent(this,VendorPaymentActivity.class);
                startActivity(intent);
                break;
            case R.id.indirectpayment:
                Intent intent1 = new Intent(this,VendorIndirectPaymentActivity.class);
                startActivity(intent1);
                break;




        }
    }

    public void Logout()
    {
        Intent intent = new Intent(Activityvendorpayment.this ,login.class);
        startActivity(intent);
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
    public boolean onTouchEvent(MotionEvent event) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){}
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
            Intent i=new Intent(Activityvendorpayment.this,Activitypurchase.class);
            startActivity(i);
            return true;

            case R.id.action_settinghelp:
                VendorPaymenthelpdialog();
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activityvendorpayment.this);
                showIncentive.show();
                return true;
            case R.id.action_Masterscn1:
                Intent p = new Intent(Activityvendorpayment.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
//            case R.id.action_settingpurchase:
//                Intent p=new Intent(Activityvendorpayment.this,PurchaseActivity.class);
//                startActivity(p);
//                return true;
//

            case R.id.action_settinginv:
                Intent in=new Intent(Activityvendorpayment.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activityvendorpayment.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;



        }

        return super.onOptionsItemSelected(item);
    }


}