package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import Adapter.MaintenanceAdapter;
import Pojo.Decimal;
import Pojo.ShowModel;

/****************Show the data maintenance *************/
public class maintainenecereport extends Activity {
    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView obj;
    DBhelper helper;
    ShowModel showModel;
    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    String backroundcolour,colorchange;
    TextView tvid,discription ,tvdate,status,member;



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);


        helper = new DBhelper(maintainenecereport.this);
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout3);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


        tvid = (TextView) findViewById(R.id.tvTicket_Id);
        tvid.setTextSize(Float.parseFloat(textsize));

        discription = (TextView) findViewById(R.id.tvSupport_Ticket_Status);
        discription.setTextSize(Float.parseFloat(textsize));

        tvdate = (TextView) findViewById(R.id.tvdate);
        tvdate.setTextSize(Float.parseFloat(textsize));

        status = (TextView) findViewById(R.id.tvSubject_Desc);
        status.setTextSize(Float.parseFloat(textsize));

        member = (TextView) findViewById(R.id.tvTeamMember);
        member.setTextSize(Float.parseFloat(textsize));




        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper=new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getReadableDatabase();
        final ArrayList arrayList = helper.getAlldata();
        Collections.reverse(arrayList);
        Log.e("%%%%%%%%%%%%%%%%%%%", arrayList.toString());
        obj = (ListView) findViewById(R.id.listView);
        Log.e("***********Lt1*******", obj.toString());
        MaintenanceAdapter maintenanceAdapter = new MaintenanceAdapter(this, arrayList);
        obj.setAdapter(maintenanceAdapter);
        Log.e("***********lt2*******", obj.toString());


    }


    public  void SystemMaintainancehelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle(" SYSTEM MAINTAINANCE CHECK THE STATUS OF AN ISSUE ");
        alertDialog.setMessage(" System Maintenance: Check the Status of Issue\n" +
                "\n" +
                "The user can check the status of the issue raised.\n" +
                "\n" +
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

            case R.id.action_settings:

                Intent i=new Intent(maintainenecereport.this,ActivityMainMaintainence.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(maintainenecereport.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                SystemMaintainancehelpdialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(maintainenecereport.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/

            case R.id.action_settinginv:
                Intent in=new Intent(maintainenecereport.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(maintainenecereport.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent r=new Intent(maintainenecereport.this,Activity_masterScreen1.class);
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
        Intent intent = new Intent(maintainenecereport.this ,login.class);
        startActivity(intent);
    }









}
