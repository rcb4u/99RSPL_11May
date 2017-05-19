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


public class ReportsActivity extends Activity implements View.OnClickListener {

    ActionBar actionBar;
    Button submit;
    Intent intent;
    String CurrentDate,TicketId;
    SimpleDateFormat ticketId;
    public TextView Reports;
    private EditText editMasterData,editMedia,editPurchasing,editInventory,editVendorPayment,editVendorReturn,editSales,editSalesReturn,editFinancial,editOthers;
    private RadioGroup radioScreenGroup;
    private RadioButton radioButtonMasterData,radioButtonMedia,radioButtonPurchasing,radioButtonInventory,radioButtonVendorPayment,radioButtonVendorReturn,radioButtonSales,radioButtonSalesReturn,radioButtonFinancial,radioButtonOthers;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_status);

        submit = (Button) findViewById(R.id.Submit);
        Reports = (TextView) findViewById(R.id.reports);

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

        radioButtonMasterData=(RadioButton)findViewById(R.id.radioButton1) ;
        radioButtonMedia=(RadioButton)findViewById(R.id.radioButton2) ;
        radioButtonPurchasing=(RadioButton)findViewById(R.id.radioButton3) ;
        radioButtonInventory=(RadioButton)findViewById(R.id.radioButton4) ;
        radioButtonVendorPayment=(RadioButton)findViewById(R.id.radioButton5) ;
        radioButtonVendorReturn=(RadioButton)findViewById(R.id.radioButton6) ;
        radioButtonSales=(RadioButton)findViewById(R.id.radioButton7) ;
        radioButtonSalesReturn=(RadioButton)findViewById(R.id.radioButton8) ;
        radioButtonFinancial=(RadioButton)findViewById(R.id.radioButton9) ;
        radioButtonOthers=(RadioButton)findViewById(R.id.radioButton10) ;

        editMasterData=(EditText)findViewById(R.id.editText1);
        editMedia=(EditText)findViewById(R.id.editText2);
        editPurchasing=(EditText)findViewById(R.id.editText3);
        editInventory=(EditText)findViewById(R.id.editText4);
        editVendorPayment=(EditText)findViewById(R.id.editText5);
        editVendorReturn=(EditText)findViewById(R.id.editText6);
        editSales=(EditText)findViewById(R.id.editText7);
        editSalesReturn=(EditText)findViewById(R.id.editText8);
        editFinancial=(EditText)findViewById(R.id.editText9);
        editOthers=(EditText)findViewById(R.id.editText10);


        radioScreenGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton1)
                {
                    editMasterData.setVisibility(View.VISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton2)
                {
                    editMedia.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton3)
                {
                    editPurchasing.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton4)
                {
                    editInventory.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton5)
                {
                    editVendorPayment.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    editVendorReturn.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    editSales.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton8)
                {
                    editSalesReturn.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton9)
                {
                    editFinancial.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editOthers.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton10)
                {
                    editOthers.setVisibility(View.VISIBLE);
                    editMasterData.setVisibility(View.INVISIBLE);
                    editMedia.setVisibility(View.INVISIBLE);
                    editPurchasing.setVisibility(View.INVISIBLE);
                    editInventory.setVisibility(View.INVISIBLE);
                    editVendorPayment.setVisibility(View.INVISIBLE);
                    editVendorReturn.setVisibility(View.INVISIBLE);
                    editSales.setVisibility(View.INVISIBLE);
                    editSalesReturn.setVisibility(View.INVISIBLE);
                    editFinancial.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (radioButtonMasterData.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editMasterData.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonMasterData.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportMasterDataIssues(editMasterData.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportMasterData();
                editMasterData.setText("");
            }
        }

        else if (radioButtonMedia.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editMedia.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonMedia.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportMediaIssues(editMedia.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportMedia();
                editMedia.setText("");
            }
        }

        else if (radioButtonPurchasing.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editPurchasing.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonPurchasing.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportPurchasingIssues(editPurchasing.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportPurchasing();
                editPurchasing.setText("");
            }
        }

        else if (radioButtonInventory.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editInventory.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonInventory.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportInventoryIssues(editInventory.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportInventory();
                editInventory.setText("");
            }
        }

        else if (radioButtonVendorPayment.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editVendorPayment.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonVendorPayment.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportVendorPaymentIssues(editVendorPayment.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportVendorPayment();
                editVendorPayment.setText("");
            }
        }

        else if (radioButtonVendorReturn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editVendorReturn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonVendorReturn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportVendorReturnIssues(editVendorReturn.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportVendorReturn();
                editVendorReturn.setText("");
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
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonSales.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportSalesIssues(editSales.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportSales();
                editSales.setText("");
            }
        }

        else if (radioButtonSalesReturn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editSalesReturn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonSalesReturn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportSalesReturnIssues(editSalesReturn.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportSalesReturn();
                editSalesReturn.setText("");
            }
        }

        else if (radioButtonFinancial.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editFinancial.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonFinancial.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportFinancialIssues(editFinancial.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportFinancial();
                editFinancial.setText("");
            }
        }

        else if (radioButtonOthers.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editOthers.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String ReportsScreen = Reports.getText().toString();
                String SubScreen = radioButtonOthers.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertReportOtherIssues(editOthers.getText().toString(),CurrentDate.toString(),ReportsScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdateReportOther();
                editOthers.setText("");
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

            Intent i=new Intent(ReportsActivity.this, ActivityStoreScreenFeedback.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(ReportsActivity.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }


    public void UpdateReportMasterData() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  MasterData =editMasterData.getText().toString().trim();

        class UpdateReportMasterData extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"01");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Master Data Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,MasterData);
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
        UpdateReportMasterData updateStore = new UpdateReportMasterData();
        updateStore.execute();
    }

    public void UpdateReportMedia() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Media =editMedia.getText().toString().trim();

        class UpdateReportMedia extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"02");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Media Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Media);
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
        UpdateReportMedia updateStore = new UpdateReportMedia();
        updateStore.execute();
    }

    public void UpdateReportPurchasing() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Purchase =editPurchasing.getText().toString().trim();

        class UpdateReportPurchasing extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"03");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Purchasing Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Purchase);
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
        UpdateReportPurchasing updateStore = new UpdateReportPurchasing();
        updateStore.execute();
    }

    public void UpdateReportInventory() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Inventory =editInventory.getText().toString().trim();

        class UpdateReportInventory extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"04");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Inventory Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Inventory);
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
        UpdateReportInventory updateStore = new UpdateReportInventory();
        updateStore.execute();
    }

    public void UpdateReportVendorPayment() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  VendorPayment =editVendorPayment.getText().toString().trim();

        class UpdateReportVendorPayment extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"05");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Vendor Payment Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,VendorPayment);
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
        UpdateReportVendorPayment updateStore = new UpdateReportVendorPayment();
        updateStore.execute();
    }

    public void UpdateReportVendorReturn() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String VendorReturn =editVendorReturn.getText().toString().trim();

        class UpdateReportVendorReturn extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"06");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Vendor Return Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,VendorReturn);
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
        UpdateReportVendorReturn updateStore = new UpdateReportVendorReturn();
        updateStore.execute();
    }

    public void UpdateReportSales() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Sales =editSales.getText().toString().trim();

        class UpdateReportSales extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"07");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Sales Report");
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
        UpdateReportSales updateStore = new UpdateReportSales();
        updateStore.execute();
    }

    public void UpdateReportSalesReturn() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  SalesReturn =editSalesReturn.getText().toString().trim();

        class UpdateReportSalesReturn extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"08");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Sales Return Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,SalesReturn);
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
        UpdateReportSalesReturn updateStore = new UpdateReportSalesReturn();
        updateStore.execute();
    }

    public void UpdateReportFinancial() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Financial =editFinancial.getText().toString().trim();

        class UpdateReportFinancial extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"09");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Financial Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Financial);
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
        UpdateReportFinancial updateStore = new UpdateReportFinancial();
        updateStore.execute();
    }

    public void UpdateReportOther() {

        final DBhelper mydb = new DBhelper(ReportsActivity.this);
        final String  Other =editOthers.getText().toString().trim();

        class UpdateReportOther extends AsyncTask<Void, Void, String> {
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
                    PersistenceManager.saveStoreId(ReportsActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(ReportsActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"00");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Reports");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Others Report");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,Other);
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
        UpdateReportOther updateStore = new UpdateReportOther();
        updateStore.execute();
    }



}
