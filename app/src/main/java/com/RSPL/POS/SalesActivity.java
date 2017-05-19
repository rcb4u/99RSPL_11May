package com.RSPL.POS;

import android.app.ActionBar;
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
import android.text.format.DateFormat;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;


public class SalesActivity extends Activity implements View.OnClickListener {

    ActionBar actionBar;
    Button submit;
    Intent intent;
    String CurrentDate,TicketId;
    SimpleDateFormat ticketId;
    public TextView Sales;
    private EditText editDayOpening,editDayClosing,editSalesReturnWithInvoice,editSalesReturnWithoutInvoice,editSales;
    private RadioGroup radioScreenGroup;
    private RadioButton radioButtonDayOpening,radioButtonDayClosing,radioButtonSalesReturnWithInvoice,radioButtonSalesReturnWithoutInvoice,radioButtonSales;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_status);

        submit = (Button) findViewById(R.id.Submit);
        Sales = (TextView) findViewById(R.id.sales);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
        CurrentDate = s.toString();
        Log.e("current date :", CurrentDate);

        submit.setOnClickListener(this);

        radioScreenGroup=(RadioGroup)findViewById(R.id.radioGroup);

        radioButtonDayOpening=(RadioButton)findViewById(R.id.radioButton1) ;
        radioButtonDayClosing=(RadioButton)findViewById(R.id.radioButton2) ;
        radioButtonSalesReturnWithInvoice=(RadioButton)findViewById(R.id.radioButton3) ;
        radioButtonSalesReturnWithoutInvoice=(RadioButton)findViewById(R.id.radioButton4) ;
        radioButtonSales=(RadioButton)findViewById(R.id.radioButton5) ;

        editDayOpening=(EditText)findViewById(R.id.editText1);
        editDayClosing=(EditText)findViewById(R.id.editText2);
        editSalesReturnWithInvoice=(EditText)findViewById(R.id.editText3);
        editSalesReturnWithoutInvoice=(EditText)findViewById(R.id.editText4);
        editSales=(EditText)findViewById(R.id.editText5);

        radioScreenGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton1)
                {
                    editDayOpening.setVisibility(View.VISIBLE);
                    editDayClosing.setVisibility(View.INVISIBLE);
                    editSalesReturnWithInvoice.setVisibility(View.INVISIBLE);
                    editSalesReturnWithoutInvoice.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);

                }
                else if(checkedId==R.id.radioButton2)
                {
                    editDayClosing.setVisibility(View.VISIBLE);
                    editDayOpening.setVisibility(View.INVISIBLE);
                    editSalesReturnWithInvoice.setVisibility(View.INVISIBLE);
                    editSalesReturnWithoutInvoice.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);

                }
                else if(checkedId==R.id.radioButton3)
                {
                    editSalesReturnWithInvoice.setVisibility(View.VISIBLE);
                    editDayOpening.setVisibility(View.INVISIBLE);
                    editDayClosing.setVisibility(View.INVISIBLE);
                    editSalesReturnWithoutInvoice.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);

                }
                else if(checkedId==R.id.radioButton4)
                {
                    editSalesReturnWithoutInvoice.setVisibility(View.VISIBLE);
                    editDayOpening.setVisibility(View.INVISIBLE);
                    editDayClosing.setVisibility(View.INVISIBLE);
                    editSalesReturnWithInvoice.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);

                }
                else if(checkedId==R.id.radioButton5)
                {
                    editSales.setVisibility(View.VISIBLE);
                    editDayOpening.setVisibility(View.INVISIBLE);
                    editDayClosing.setVisibility(View.INVISIBLE);
                    editSalesReturnWithInvoice.setVisibility(View.INVISIBLE);
                    editSalesReturnWithoutInvoice.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (radioButtonDayOpening.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editDayOpening.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SalesScreen = Sales.getText().toString();
                String SubScreen = radioButtonDayOpening.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSalesDayOpenIssues(editDayOpening.getText().toString(),CurrentDate.toString(),SalesScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSalesDayOpening();
                editDayOpening.setText("");
            }
        }

        else if (radioButtonDayClosing.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editDayClosing.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SalesScreen = Sales.getText().toString();
                String SubScreen = radioButtonDayOpening.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSalesDayCloseIssues(editDayClosing.getText().toString(),CurrentDate.toString(),SalesScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSalesDayClosing();
                editDayClosing.setText("");
            }
        }

        else if (radioButtonSalesReturnWithInvoice.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editSalesReturnWithInvoice.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SalesScreen = Sales.getText().toString();
                String SubScreen = radioButtonSalesReturnWithInvoice.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSalesReturnWithInvoiceIssues(editSalesReturnWithInvoice.getText().toString(),CurrentDate.toString(),SalesScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSalesWithInvoice();
                editSalesReturnWithInvoice.setText("");
            }
        }

        else if (radioButtonSalesReturnWithoutInvoice.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editSalesReturnWithoutInvoice.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SalesScreen = Sales.getText().toString();
                String SubScreen = radioButtonSalesReturnWithoutInvoice.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSalesReturnWithoutInvoiceIssues(editSalesReturnWithoutInvoice.getText().toString(),CurrentDate.toString(),SalesScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSalesWithoutInvoice();
                editSalesReturnWithoutInvoice.setText("");
            }
        }

        else if (radioButtonSales.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editSales.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SalesScreen = Sales.getText().toString();
                String SubScreen = radioButtonSales.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSalesIssues(editSales.getText().toString(),CurrentDate.toString(),SalesScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSales();
                editSales.setText("");
            }
        }

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
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
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
        if (id == R.id.action_settings) {

            Intent i=new Intent(SalesActivity.this, ActivityStoreScreenFeedback.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void UpdateSalesDayOpening() {

        final DBhelper mydb = new DBhelper(SalesActivity.this);
        final String  DayOpen =editDayOpening.getText().toString().trim();

        class UpdateSalesDayOpening extends AsyncTask<Void, Void, String> {
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
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(SalesActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SalesActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"54");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Day Opening");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,DayOpen);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateSalesDayOpening updateStore = new UpdateSalesDayOpening();
        updateStore.execute();
    }

    public void UpdateSalesDayClosing() {

        final DBhelper mydb = new DBhelper(SalesActivity.this);
        final String  DayClose =editDayClosing.getText().toString().trim();

        class UpdateSalesDayClosing extends AsyncTask<Void, Void, String> {
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
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(SalesActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SalesActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"53");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Day Closing");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,DayClose);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateSalesDayClosing updateStore = new UpdateSalesDayClosing();
        updateStore.execute();
    }

    public void UpdateSalesWithInvoice() {

        final DBhelper mydb = new DBhelper(SalesActivity.this);
        final String WithInvoice =editSalesReturnWithInvoice.getText().toString().trim();

        class UpdateSalesWithInvoice extends AsyncTask<Void, Void, String> {
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
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(SalesActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SalesActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"52");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Sales Return With Invoice");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithInvoice);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateSalesWithInvoice updateStore = new UpdateSalesWithInvoice();
        updateStore.execute();
    }

    public void UpdateSalesWithoutInvoice() {

        final DBhelper mydb = new DBhelper(SalesActivity.this);
        final String  WithoutInvoice =editSalesReturnWithoutInvoice.getText().toString().trim();

        class UpdateSalesWithoutInvoice extends AsyncTask<Void, Void, String> {
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
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(SalesActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SalesActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"51");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Sales Return Without Invoice");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithoutInvoice);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateSalesWithoutInvoice updateStore = new UpdateSalesWithoutInvoice();
        updateStore.execute();
    }

    public void UpdateSales() {

        final DBhelper mydb = new DBhelper(SalesActivity.this);
        final String  Sales =editSales.getText().toString().trim();

        class UpdateSales extends AsyncTask<Void, Void, String> {
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
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(SalesActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SalesActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"50");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Sales");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Sales);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateSales updateStore = new UpdateSales();
        updateStore.execute();
    }

}
