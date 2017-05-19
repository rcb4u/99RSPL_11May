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


public class MasterDataActivity extends Activity implements View.OnClickListener{

    ActionBar actionBar;
    Button submit;
    Intent intent;
    String CurrentDate,TicketId;
    SimpleDateFormat ticketId;
    public TextView MasterData;
    private EditText editStoreMgmnt,editPrdctPhrma,editLoclPrdctPhrma,editCaptrngDctr,editDsbtr,editSchmeMgmnt,editCustmrMgmnt,editLocalVendr;
    private RadioGroup radioScreenGroup;
    private RadioButton radioButtonStoreMgmnt,radioButtonPrdctPhrma,radioButtonLoclPrdctPhrma,radioButtonCaptrngDctr,radioButtonDsbtr,radioButtonSchmeMgmnt,radioButtonCustmrMgmnt,radioButtonLocalVendr;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterdata_status);

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

        submit = (Button) findViewById(R.id.Submit);
        MasterData = (TextView) findViewById(R.id.masterdata);

        radioScreenGroup=(RadioGroup)findViewById(R.id.radioGroup);
        radioButtonStoreMgmnt=(RadioButton)findViewById(R.id.radioButton1) ;
        radioButtonPrdctPhrma=(RadioButton)findViewById(R.id.radioButton2) ;
        radioButtonLoclPrdctPhrma=(RadioButton)findViewById(R.id.radioButton3) ;
        radioButtonCaptrngDctr=(RadioButton)findViewById(R.id.radioButton4) ;
        radioButtonDsbtr=(RadioButton)findViewById(R.id.radioButton5) ;
        radioButtonSchmeMgmnt=(RadioButton)findViewById(R.id.radioButton6) ;
        radioButtonCustmrMgmnt=(RadioButton)findViewById(R.id.radioButton7) ;
        radioButtonLocalVendr=(RadioButton)findViewById(R.id.radioButton8);

        editStoreMgmnt=(EditText)findViewById(R.id.editText1);
        editPrdctPhrma=(EditText)findViewById(R.id.editText2);
        editLoclPrdctPhrma=(EditText)findViewById(R.id.editText3);
        editCaptrngDctr=(EditText)findViewById(R.id.editText4);
        editDsbtr=(EditText)findViewById(R.id.editText5);
        editSchmeMgmnt=(EditText)findViewById(R.id.editText6);
        editCustmrMgmnt=(EditText)findViewById(R.id.editText7);
        editLocalVendr=(EditText)findViewById(R.id.editText8);

        radioScreenGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton1)
                {
                    editStoreMgmnt.setVisibility(View.VISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton2)
                {
                    editPrdctPhrma.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton3)
                {
                    editLoclPrdctPhrma.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton4)
                {
                    editCaptrngDctr.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton5)
                {
                    editDsbtr.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    editSchmeMgmnt.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    editCustmrMgmnt.setVisibility(View.VISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                    editLocalVendr.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton8)
                {
                    editLocalVendr.setVisibility(View.VISIBLE);
                    editCustmrMgmnt.setVisibility(View.INVISIBLE);
                    editStoreMgmnt.setVisibility(View.INVISIBLE);
                    editPrdctPhrma.setVisibility(View.INVISIBLE);
                    editLoclPrdctPhrma.setVisibility(View.INVISIBLE);
                    editCaptrngDctr.setVisibility(View.INVISIBLE);
                    editDsbtr.setVisibility(View.INVISIBLE);
                    editSchmeMgmnt.setVisibility(View.INVISIBLE);
                }
            }
        });

        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (radioButtonStoreMgmnt.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editStoreMgmnt.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonStoreMgmnt.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataStoreMgmntIssues(editStoreMgmnt.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataStrMgmnt();
                editStoreMgmnt.setText("");
            }
        }

       else if (radioButtonPrdctPhrma.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editPrdctPhrma.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonPrdctPhrma.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataPrdctPhrmaIssues(editPrdctPhrma.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataPrdctPhrma();
                editPrdctPhrma.setText("");
            }
        }

        else if (radioButtonLoclPrdctPhrma.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editLoclPrdctPhrma.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonLoclPrdctPhrma.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataLocalPrdctPhrmaIssues(editLoclPrdctPhrma.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataLocalPrdctPhrma();
                editLoclPrdctPhrma.setText("");
            }
        }

        else if (radioButtonCaptrngDctr.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editCaptrngDctr.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonCaptrngDctr.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataCaptrngDctrIssues(editCaptrngDctr.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataCapturngDctr();
                editCaptrngDctr.setText("");
            }
        }

        else if (radioButtonDsbtr.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editDsbtr.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonDsbtr.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataDistributorIssues(editDsbtr.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataDistrbtr();
                editDsbtr.setText("");
            }
        }

        else if (radioButtonSchmeMgmnt.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editSchmeMgmnt.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonSchmeMgmnt.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataSchemeMgmntIssues(editSchmeMgmnt.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataSchemeManagmnt();
                editSchmeMgmnt.setText("");
            }
        }

        else if (radioButtonCustmrMgmnt.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editCustmrMgmnt.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonCustmrMgmnt.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataCustomerMgmntIssues(editCustmrMgmnt.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataCustmrMngmnt();
                editCustmrMgmnt.setText("");
            }
        }

        else if (radioButtonLocalVendr.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editLocalVendr.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String MasterScreen = MasterData.getText().toString();
                String SubScreen = radioButtonLocalVendr.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertMasterDataLocalVendrIssues(editLocalVendr.getText().toString(),CurrentDate.toString(),MasterScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateMasterDataLocalVendr();
                editLocalVendr.setText("");
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

            Intent i=new Intent(MasterDataActivity.this, ActivityStoreScreenFeedback.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(MasterDataActivity.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    public void UpdateMasterDataStrMgmnt() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  StoreManagement =editStoreMgmnt.getText().toString().trim();

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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                 final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"8");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Store Management");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,StoreManagement);
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

    public void UpdateMasterDataPrdctPhrma() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  ProductPharma =editPrdctPhrma.getText().toString().trim();

        class UpdateMasterDataPrdctPhrma extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"8");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Product Pharma");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,ProductPharma);
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
        UpdateMasterDataPrdctPhrma updateStore = new UpdateMasterDataPrdctPhrma();
        updateStore.execute();
    }

    public void UpdateMasterDataLocalPrdctPhrma() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String LocalProductPharma =editLoclPrdctPhrma.getText().toString().trim();

        class UpdateMasterDataLocalPrdctPhrma extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"16");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Local Product Pharma");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,LocalProductPharma);
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
        UpdateMasterDataLocalPrdctPhrma updateStore = new UpdateMasterDataLocalPrdctPhrma();
        updateStore.execute();
    }

    public void UpdateMasterDataCapturngDctr() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  CaptrngDoctr =editCaptrngDctr.getText().toString().trim();

        class UpdateMasterDataCapturngDctr extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"15");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Capturing Doctor");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,CaptrngDoctr);
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
        UpdateMasterDataCapturngDctr updateStore = new UpdateMasterDataCapturngDctr();
        updateStore.execute();
    }

    public void UpdateMasterDataDistrbtr() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  Distrbutr =editDsbtr.getText().toString().trim();

        class UpdateMasterDataDistrbtr extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"14");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Distributor");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Distrbutr);
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
        UpdateMasterDataDistrbtr updateStore = new UpdateMasterDataDistrbtr();
        updateStore.execute();
    }

    public void UpdateMasterDataSchemeManagmnt() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  SchemeMngmnt =editSchmeMgmnt.getText().toString().trim();

        class UpdateMasterDataSchemeManagmnt extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"13");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Scheme Management For Companies");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,SchemeMngmnt);
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
        UpdateMasterDataSchemeManagmnt updateStore = new UpdateMasterDataSchemeManagmnt();
        updateStore.execute();
    }

    public void UpdateMasterDataCustmrMngmnt() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  CustmrMngmnt =editCustmrMgmnt.getText().toString().trim();

        class UpdateMasterDataCustmrMngmnt extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"12");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Customer Management");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,CustmrMngmnt);
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
        UpdateMasterDataCustmrMngmnt updateStore = new UpdateMasterDataCustmrMngmnt();
        updateStore.execute();
    }

    public void UpdateMasterDataLocalVendr() {

        final DBhelper mydb = new DBhelper(MasterDataActivity.this);
        final String  LocalVendr =editLocalVendr.getText().toString().trim();

        class UpdateMasterDataLocalVendr extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(MasterDataActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(MasterDataActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"11");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Master Data Management");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Local Vendor");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,LocalVendr);
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
        UpdateMasterDataLocalVendr updateStore = new UpdateMasterDataLocalVendr();
        updateStore.execute();
    }



}
