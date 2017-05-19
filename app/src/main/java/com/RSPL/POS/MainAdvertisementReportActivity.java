package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.StoreMainAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.StoreMainModel;


public class MainAdvertisementReportActivity extends Activity {
    public Spinner spinner;
    private ListView listView;
    ArrayList<StoreMainModel> arraylist;
   StoreMainModel storeMainModel;
    StoreMainAdapter storeMainAdapter;
    DBhelper helper;
    ActionBar actionBar;
    Button emailbutton;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper=new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getReadableDatabase();


        emailbutton=(Button)findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });


        final ArrayList arrayList = helper.getStoreMainReportData();
        Log.e("%%%%%%%%%%%%%%%%%%%", arrayList.toString());
        listView = (ListView) findViewById(R.id.lv_storemain);
        Log.e("***********Lt1*******", listView.toString());

        storeMainAdapter = new StoreMainAdapter(this, arrayList);
        listView.setAdapter(storeMainAdapter);
        Log.e("***********lt2*******", listView.toString());

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
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

            Intent i=new Intent(MainAdvertisementReportActivity.this,MediaReportActivity.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(MainAdvertisementReportActivity.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    public void insertEmaildata() {
        try {

            DBhelper dBhelper = new DBhelper(this);

            dBhelper.insertemailmainadvertisement(storeMainAdapter.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }



    public void Updatemainaddlistsreport() {


        for (StoreMainModel purchase : storeMainAdapter.getList()) {

            final String reportmainId = String.valueOf(purchase.getAD_MAIN_ID());
            final String reportADDESC = String.valueOf(purchase.getAD_DESC());
            final String reportADSLB1 = String.valueOf(purchase.getAD_CST_SLB1());
            final String reportADSLB2 = String.valueOf(purchase.getAD_CST_SLB2());
            final String reportADSLB3 = String.valueOf(purchase.getAD_CST_SLB3());
          //  final String reportStartdate= purchase.getAD_START_DATE();
            //final String reportenddate = String.valueOf(purchase.getAD_END_DATE());
            final String reportposuser = String.valueOf(purchase.getUserNm());

            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
            try{
                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.Retail_report_mainid, reportmainId);
                    hashMap.put(Config.Retail_report_addesc, reportADDESC);
                    hashMap.put(Config.Retail_report_Slb1, reportADSLB1);
                    hashMap.put(Config.Retail_report_Slb2, reportADSLB2);
                    hashMap.put(Config.Retail_report_Slb3, reportADSLB3);
                  //  hashMap.put(Config.Retail_report_startdate,reportStartdate);
                  //  hashMap.put(Config.Retail_report_enddate,reportenddate);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);




                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_ad_store_main_mail.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;

                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // loading = ProgressDialog.show(activity_inventory.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //     loading.dismiss();
                    //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();
                }

            }
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }

    }
    public  void emailbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {

                    insertEmaildata();
                    insertEmaildata();
                    Updatemainaddlistsreport();
                    Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();
                    finish();

                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }



}
