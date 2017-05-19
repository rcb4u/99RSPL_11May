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

public class ActivityLoyality extends Activity implements View.OnClickListener {
    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
      DBhelper mydb;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_loyality);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        mydb = new DBhelper(ActivityLoyality.this);
        Decimal valuetextsize = mydb.getStoreprice();
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

        actionBar = getActionBar();
         actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


        //   ImageButton imageButton = (ImageButton) findViewById(R.id.loyal1);
     //   ImageButton imageButton2 = (ImageButton) findViewById(R.id.loyal2);
       // ImageButton imageButton3 = (ImageButton) findViewById(R.id.loyal3);
       ImageButton imageButton4 = (ImageButton) findViewById(R.id.loyal4);
        ImageButton imageButton5 = (ImageButton) findViewById(R.id.loyal5);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.loyal6);
        ImageButton Button7 = ( ImageButton) findViewById(R.id.loyal7);
        ImageButton imageButton8 = ( ImageButton) findViewById(R.id.loyal8);
        ImageButton imageButton9 = ( ImageButton) findViewById(R.id.loyal9);
        ImageButton imageButton10 = (ImageButton) findViewById(R.id.loyal10);
       // Button Button11 = (Button) findViewById(R.id.loyal11);
        ImageButton Button10 = (ImageButton) findViewById(R.id.decimal);
      /*  imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
       // imageButton3.setOnClickListener(this);
        */
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        Button7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);
        imageButton10.setOnClickListener(this);
        Button10.setOnClickListener(this);
      //  Button11.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loyal1:


                clearbuttondialog();
                break;
            case R.id.loyal2:
              /*  Intent intent1 = new Intent(this, Activityloyalityaccural.class);
                startActivity(intent1);*/
                clearbuttondialog();
                break;
         /*   case R.id.loyal3:
                Intent intent2 = new Intent(this, Activityreport.class);
                startActivity(intent2);
                break;*/
            case R.id.loyal4:
                Intent intent3 = new Intent(this, Activity_Media.class);
                startActivity(intent3);
               // clearbuttondialog();
                break;
            case R.id.loyal5:
                Intent intent4 = new Intent(this,UserManagementActivity.class);
                startActivity(intent4);
                break;
            case R.id.loyal6:
                Intent intent5 = new Intent(this,Activity_Top_Product.class);
                startActivity(intent5);
                break;
            case R.id.loyal7:
                Intent intent6 = new Intent(this, Otp_Activity.class);
                startActivity(intent6);
              //  clearbuttondialog();
                break;
            case R.id.loyal8:
                Intent intent7 = new Intent(this, Activity_bill_display.class);
                startActivity(intent7);
              //  clearbuttondialog();
                break;
            case R.id.loyal9:
                Intent intent8 = new Intent(this,CustomerRejection.class);
                startActivity(intent8);
                break;
            case R.id.loyal10:
                Intent intent9 = new Intent(this,VendorRejection.class);
                startActivity(intent9);
                break;
           /* case R.id.loyal11:
                Intent intent11 = new Intent(this,ActivityStoreScreenFeedback.class);
                startActivity(intent11);
                break;*/
            case R.id.decimal:
                Intent intent10 = new Intent(this,Activity_decimal.class);
                startActivity(intent10);
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

            Intent i=new Intent(ActivityLoyality.this,Activity_masterScreen1.class);
            startActivity(i);

            return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityLoyality.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                system_settings();

            case R.id.action_Masterscn1:
                Intent intent = new Intent(ActivityLoyality.this,Activity_masterScreen1.class);
                startActivity(intent);

                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityLoyality.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityLoyality.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityLoyality.this ,login.class);
        startActivity(intent);
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




    public  void system_settings()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                     SYSTEM SETTINGS");
        alertDialog.setMessage("\n" +
                        "Objective:\n" +
                        "\t\n" +
                        "Following are the option available on this  page:\n" +
                        "\n" +
                        "\t * Customer Loyalty \n" +
                        "\t * User Management\n" +
                        "\t * Loyalty Ahead\n" +
                        "\t * Bill Level Discount\n" +
                        "\t * Line Item Discount\n" +
                        "\t * Ticket Generation\n" +
                        "\t * Top 15 Products\n" +
                        "\t * Vendor Reason for Rejection\n"+
                        "\t * Customer Reason for Rejection\n"
        );
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }


}

