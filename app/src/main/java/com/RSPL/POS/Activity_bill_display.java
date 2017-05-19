package com.RSPL.POS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Store;
import Pojo.displaypojo;

public class Activity_bill_display extends Activity {



EditText TOTAL_BILL_VALUE, DISCOUNT, NET_BILL_PAYABLE,
        AMOUNT_RECEIVED, AMOUNT_PAID_BACK;
    TextView Storeid,footer;
    String Store_Id_To_Update,store_id;
    android.app.ActionBar actionBar;



    TextWatcher NET;
DBhelper mydb;


    TextView TV_TOTAL_BILL_VALUE, TV_DISCOUNT, TV_NET_BILL_PAYABLE,
            TV_AMOUNT_RECEIVED, TV_AMOUNT_PAID_BACK,tv_Storeid,tv_footer;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    String backroundcolour,colorchange;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_bill_display);






        //      Log.e("data test",dddddd);


        actionBar=getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        mydb = new DBhelper(Activity_bill_display.this);
        Decimal valuetextsize = mydb.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
         backroundcolour=  valuetextsize.getColorbackround();




        if(backroundcolour.matches("Orange")){

              colorchange="#ff9033";
          //  String    d="#bdc3c7";//silver


            //f39c12
            //EF643C
            //EE782D
        }
        if(backroundcolour.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

            //    d=Color.BLUE;//FBB917

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
            //C3C3C3 grey//E1E1E1

            ////FBB917

        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        /////////////////////////jimmy/////////////////////////////////

        tv_Storeid = (TextView) findViewById(R.id.store_id);
        tv_Storeid.setTextSize(Float.parseFloat(textsize));

        TV_TOTAL_BILL_VALUE = (TextView) findViewById(R.id.textView5);
        TV_TOTAL_BILL_VALUE.setTextSize(Float.parseFloat(textsize));
        TV_DISCOUNT = (TextView) findViewById(R.id.textView9);
        TV_DISCOUNT.setTextSize(Float.parseFloat(textsize));
        TV_NET_BILL_PAYABLE = (TextView) findViewById(R.id.textView1);
        TV_NET_BILL_PAYABLE.setTextSize(Float.parseFloat(textsize));
        TV_AMOUNT_RECEIVED = (TextView) findViewById(R.id.textVie);
        TV_AMOUNT_RECEIVED.setTextSize(Float.parseFloat(textsize));
        TV_AMOUNT_PAID_BACK = (TextView) findViewById(R.id.textViewinv);
        TV_AMOUNT_PAID_BACK.setTextSize(Float.parseFloat(textsize));
        tv_footer = (TextView) findViewById(R.id.textViewsales);
        tv_footer.setTextSize(Float.parseFloat(textsize));






        Storeid = (TextView) findViewById(R.id.displaystore);
        Storeid.setTextSize(Float.parseFloat(textsize));

        TOTAL_BILL_VALUE = (EditText) findViewById(R.id.displaytotalbillvalue);
        TOTAL_BILL_VALUE.setTextSize(Float.parseFloat(textsize));
        DISCOUNT = (EditText)findViewById(R.id.displaydiscount);
        DISCOUNT.setTextSize(Float.parseFloat(textsize));
        NET_BILL_PAYABLE = (EditText) findViewById(R.id.displaynetbillpayable);
        NET_BILL_PAYABLE.setTextSize(Float.parseFloat(textsize));
        AMOUNT_RECEIVED = (EditText) findViewById(R.id.displayamountreceived);
        AMOUNT_RECEIVED.setTextSize(Float.parseFloat(textsize));
        AMOUNT_PAID_BACK = (EditText) findViewById(R.id.displayamountpaidback);
        AMOUNT_PAID_BACK.setTextSize(Float.parseFloat(textsize));
        footer = (TextView) findViewById(R.id.displayfooter);
        footer.setTextSize(Float.parseFloat(textsize));






        final DBhelper mydb = new DBhelper(Activity_bill_display.this);
        displaypojo value = mydb.getStoredisplaydetail();

        Storeid.setText(value.getStoreid());
        TOTAL_BILL_VALUE.setText(value.getTotalbillvalue());
        DISCOUNT.setText(value.getDiscount());
        NET_BILL_PAYABLE.setText(value.getNetbillpayable());
        AMOUNT_RECEIVED.setText(value.getAmountreceived());
        AMOUNT_PAID_BACK.setText(value.getAmountpaidback());
        footer.setText(value.getFooter());


        Button Exit = (Button) findViewById(R.id.Store_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);

            }
        });


        NET = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {

                String Fotr=  footer.getText().toString();


            }
        };



        Button Update = (Button) findViewById(R.id.Store_update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Value = Storeid.getText().toString();

                Store_Id_To_Update = Value;

                mydb.updatedisplay(Store_Id_To_Update, TOTAL_BILL_VALUE.getText().toString(),DISCOUNT.getText().toString(),NET_BILL_PAYABLE.getText().toString(),AMOUNT_RECEIVED.getText().toString(),AMOUNT_PAID_BACK.getText().toString(),footer.getText().toString());

                Toast.makeText(getApplicationContext(), "Bill Detail Updated", Toast.LENGTH_SHORT).show();
                UpdateStore();
                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);

                //call shell script for update record into slave machine

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }

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

            Intent i = new Intent(Activity_bill_display.this, ActivityLoyality.class);
            startActivity(i);
            return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_bill_display.this);
                showIncentive.show();
                return true;

            /*case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_bill_display.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(Activity_bill_display.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_bill_display.this,ActivitySalesbill.class);
                startActivity(s);
                return true;


            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_bill_display.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_logout:
                Logout();

                return true;



        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(Activity_bill_display.this ,login.class);
        startActivity(intent);
    }


    public void UpdateStore() {

        final DBhelper mydb = new DBhelper(Activity_bill_display.this);

        final String  totalbillvalue =TOTAL_BILL_VALUE.getText().toString().trim();

        final String discount=       DISCOUNT.getText().toString().trim();
        final String netpayable=       NET_BILL_PAYABLE.getText().toString().trim();

        final String amountreceived=       AMOUNT_RECEIVED.getText().toString().trim();
        final String amountpayback=       AMOUNT_PAID_BACK.getText().toString().trim();
        final String Footer=       footer.getText().toString().trim();







        PersistenceManager.saveStoreId(this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id= PersistenceManager.getStoreId(this);

        class UpdateStore extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.RETAIL_STORE_TOTALBILLVALUE,totalbillvalue);
                    hashMap.put(Config.RETAIL_STORE_DISCOUNT,discount);
                    hashMap.put(Config.RETAIL_STORE_NETPAYABLE,netpayable);
                    hashMap.put(Config.RETAIL_STORE_AMOUNTRECEIVED,amountreceived);
                    hashMap.put(Config.RETAIL_STORE_AMOUNTPAIDBACK,amountpayback);
                    hashMap.put(Config.RETAIL_STORE_FOOTER,Footer);

                    hashMap.put(Config.RETAIL_STORE_STORE_ID,store_id);



                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_BILL_DISPLAY,hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updatemobileno(store_id);

                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        // return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateStore updateStore = new UpdateStore();
        updateStore.execute();
    }






}
