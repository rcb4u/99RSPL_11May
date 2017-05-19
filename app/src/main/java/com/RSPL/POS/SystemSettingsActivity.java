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


public class SystemSettingsActivity extends Activity implements View.OnClickListener {

    ActionBar actionBar;
    Button submit;
    Intent intent;
    String CurrentDate,TicketId;
    SimpleDateFormat ticketId;
    public TextView SystemSetting;
    private EditText editUserMgmnt,editBillPara1,editBillPara2,editTopProdct,editVendrReasn,editCustmrReasn,editStorePara;
    private RadioGroup radioScreenGroup;
    private RadioButton radioButtonUserMgmnt,radioButtonBillPara1,radioButtonBillPara2,radioButtonTopProdct,radioButtonVendrReasn,radioButtonCustmrReasn,radioButtonStorePara;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemsettng_status);

        submit = (Button) findViewById(R.id.Submit);
        SystemSetting = (TextView) findViewById(R.id.systemsettng);

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

        radioScreenGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioButtonUserMgmnt=(RadioButton)findViewById(R.id.radioButton1) ;
        radioButtonBillPara1=(RadioButton)findViewById(R.id.radioButton2) ;
        radioButtonBillPara2=(RadioButton)findViewById(R.id.radioButton3) ;
        radioButtonTopProdct=(RadioButton)findViewById(R.id.radioButton4) ;
        radioButtonVendrReasn=(RadioButton)findViewById(R.id.radioButton5) ;
        radioButtonCustmrReasn=(RadioButton)findViewById(R.id.radioButton6) ;
        radioButtonStorePara=(RadioButton)findViewById(R.id.radioButton7) ;

        editUserMgmnt=(EditText)findViewById(R.id.editText1);
        editBillPara1=(EditText)findViewById(R.id.editText2);
        editBillPara2=(EditText)findViewById(R.id.editText3);
        editTopProdct=(EditText)findViewById(R.id.editText4);
        editVendrReasn=(EditText)findViewById(R.id.editText5);
        editCustmrReasn=(EditText)findViewById(R.id.editText6);
        editStorePara=(EditText)findViewById(R.id.editText7);

        radioScreenGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton1)
                {
                    editUserMgmnt.setVisibility(View.VISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton2)
                {
                    editBillPara1.setVisibility(View.VISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton3)
                {
                    editBillPara2.setVisibility(View.VISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton4)
                {
                    editTopProdct.setVisibility(View.VISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton5)
                {
                    editVendrReasn.setVisibility(View.VISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    editCustmrReasn.setVisibility(View.VISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                    editStorePara.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    editStorePara.setVisibility(View.VISIBLE);
                    editCustmrReasn.setVisibility(View.INVISIBLE);
                    editTopProdct.setVisibility(View.INVISIBLE);
                    editBillPara2.setVisibility(View.INVISIBLE);
                    editBillPara1.setVisibility(View.INVISIBLE);
                    editUserMgmnt.setVisibility(View.INVISIBLE);
                    editVendrReasn.setVisibility(View.INVISIBLE);
                }
            }
        });

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (radioButtonUserMgmnt.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editUserMgmnt.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonUserMgmnt.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingUserMgmntIssues(editUserMgmnt.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingUserMgmnt();
                editUserMgmnt.setText("");
            }
        }

        else if (radioButtonBillPara1.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editBillPara1.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonBillPara1.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingBillPara1Issues(editBillPara1.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingBillPara1();
                editBillPara1.setText("");
            }
        }

        else if (radioButtonBillPara2.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editBillPara2.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonBillPara2.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingBillPara2Issues(editBillPara2.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingBillPara2();
                editBillPara2.setText("");
            }
        }

        else if (radioButtonTopProdct.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editTopProdct.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonTopProdct.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingTopProdctIssues(editTopProdct.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingTopProduct();
                editTopProdct.setText("");
            }
        }

        else if (radioButtonVendrReasn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editVendrReasn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonVendrReasn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingVendrReasnRejctnIssues(editVendrReasn.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingVendrReasnRejctn();
                editVendrReasn.setText("");
            }
        }

        else if (radioButtonCustmrReasn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editCustmrReasn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonCustmrReasn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingCustmrReasnRejctnIssues(editCustmrReasn.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingCustmrReasnRejctn();
                editCustmrReasn.setText("");
            }
        }

        else if (radioButtonStorePara.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editStorePara.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String SystemSettingScreen = SystemSetting.getText().toString();
                String SubScreen = radioButtonStorePara.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertSystemSettingStoreParaIssues(editStorePara.getText().toString(),CurrentDate.toString(),SystemSettingScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateSystemSettingStorePara();
                editStorePara.setText("");
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

            Intent i=new Intent(SystemSettingsActivity.this, ActivityStoreScreenFeedback.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void UpdateSystemSettingUserMgmnt() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String  UserManagement =editUserMgmnt.getText().toString().trim();

        class UpdateMasterDataStrMgmnt extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"17");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Settings");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"User Management");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,UserManagement);
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
        UpdateMasterDataStrMgmnt updateStore = new UpdateMasterDataStrMgmnt();
        updateStore.execute();
    }

    public void UpdateSystemSettingBillPara1() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String BillPara1 =editBillPara1.getText().toString().trim();

        class UpdateSystemSettingBillPara1 extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"18");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Bill Parameter 1");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,BillPara1);
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
        UpdateSystemSettingBillPara1 updateStore = new UpdateSystemSettingBillPara1();
        updateStore.execute();
    }

    public void UpdateSystemSettingBillPara2() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String BillPara2 =editBillPara2.getText().toString().trim();

        class UpdateSystemSettingBillPara2 extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"19");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Bill Parameter 2");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,BillPara2);
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
        UpdateSystemSettingBillPara2 updateStore = new UpdateSystemSettingBillPara2();
        updateStore.execute();
    }

    public void UpdateSystemSettingTopProduct() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String  TopProduct =editTopProdct.getText().toString().trim();

        class UpdateSystemSettingTopProduct extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"20");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Top 15 Product");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,TopProduct);
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
        UpdateSystemSettingTopProduct updateStore = new UpdateSystemSettingTopProduct();
        updateStore.execute();
    }

    public void UpdateSystemSettingVendrReasnRejctn() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String  VendrReasnRejctn =editVendrReasn.getText().toString().trim();

        class UpdateSystemSettingVendrReasnRejctn extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"21");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Vendor Reason For Rejection");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,VendrReasnRejctn);
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
        UpdateSystemSettingVendrReasnRejctn updateStore = new UpdateSystemSettingVendrReasnRejctn();
        updateStore.execute();
    }

    public void UpdateSystemSettingCustmrReasnRejctn() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String  CustomrReason =editCustmrReasn.getText().toString().trim();

        class UpdateSystemSettingCustmrReasnRejctn extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"22");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Customer Reason For Rejection");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,CustomrReason);
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
        UpdateSystemSettingCustmrReasnRejctn updateStore = new UpdateSystemSettingCustmrReasnRejctn();
        updateStore.execute();
    }

    public void UpdateSystemSettingStorePara() {

        final DBhelper mydb = new DBhelper(SystemSettingsActivity.this);
        final String  StorePara =editStorePara.getText().toString().trim();

        class UpdateSystemSettingStorePara extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(SystemSettingsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(SystemSettingsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"23");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"System Setting");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Store Parameter");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,StorePara);
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
        UpdateSystemSettingStorePara updateStore = new UpdateSystemSettingStorePara();
        updateStore.execute();
    }
}
