package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;



import org.json.JSONObject;

import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;

public class Activity_Bill_Level extends Activity  {


    ActionBar actionBar;


   DBhelper mydb;

    public Activity c;
    public Dialog d;
    public Button customer_ok, btnCancel;
    public EditText editMobileNo, editcustName;
    String enteredMobNumber,enteredUserName;
    TextView pleaseEnterDetails;

    private String storeMediaId;
    String adPlay;
    public int maintain = 0;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity__bill__level);
        mydb = new DBhelper(Activity_Bill_Level.this);



        customer_ok = (Button) findViewById(R.id.button_submit);
        btnCancel = (Button) findViewById(R.id.id_btn_cancel);

        editMobileNo = (EditText) findViewById(R.id.edit_customermobile);
        editcustName = (EditText) findViewById(R.id.edit_customername);

        customer_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adplay="1228348278346782835";

                String storemedia="1412332122";

                String message="HI Welcome to 99 retail street";

                enteredMobNumber=editMobileNo.getText().toString();
                enteredUserName=editcustName.getText().toString();

              //  mydb.insertUserResponse(adplay,storemedia,enteredMobNumber,message,enteredUserName);

                sendMessage();



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Adding a listener to button


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

            Intent i = new Intent(Activity_Bill_Level.this, ActivityLoyality.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.action_settinginfo) {
            ShowIncentive showIncentive = new ShowIncentive(Activity_Bill_Level.this);
            showIncentive.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendMessage() {



        class UpdateProduct extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityProduct.this, "UPDATING...", "Wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //  loading.dismiss();
                // Toast.makeText(ActivityProduct.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    String Media_SMS="Hi "+ enteredUserName +" Welcome To 99 Retail Solution Pvt. Ltd. " +
                            ",Thanks for showing interest in Mobitel.";
                    String Mobile_No="91"+enteredMobNumber;

                    //hashMap.put(Config.RETAIL_MEDIACLICK_SMS,Media_SMS);
                   // hashMap.put(Config.RETAIL_MEDIACLICK_MOBILENUMBER,Mobile_No);

                    Log.e("!!!UserName",enteredUserName);
                    Log.e("!!!User Mobile Number",enteredMobNumber);
                    Log.e("!!!UserName",enteredUserName);
                    Log.e("!!!User Mobile Number",Mobile_No);
                    Log.e("!!!User Mobile Number",Media_SMS);



                    JSONParserSync rh = new JSONParserSync();

                    //this is for media development............

                    JSONObject s = rh.sendPostRequest(" https://schedular.eu-gb.mybluemix.net/Media_Add.jsp",hashMap);


                    //this is for media production.........

                    // JSONObject s = rh.sendPostRequest("http://uploaddev.eu-gb.mybluemix.net/Media/Retail_Video_Data.jsp",hashMap);

                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully SMS Sent.!", s.toString());



                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;            }
        }
        UpdateProduct updateproduct = new UpdateProduct();
        updateproduct.execute();
    }



}

