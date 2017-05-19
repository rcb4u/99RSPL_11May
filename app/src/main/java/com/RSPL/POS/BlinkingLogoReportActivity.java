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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Adapter.BlinkingLogoReportAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.BlinkingLogoReportModel;

public class BlinkingLogoReportActivity extends Activity {

    ListView listview;
    BlinkingLogoReportAdapter BlinkingListAdapter;
    ArrayList<BlinkingLogoReportModel> arrayBlinkingList;

    public Spinner FromMonth;
    public Spinner ToMonth;
    public Spinner FromYear;
    public Spinner ToYear;
    public Spinner FromDate;
    public Spinner ToDate;

    String FromYearValue;
    String ToYearValue;
    String FromMonthValue;
    String ToMonthValue;
    String FromDateValue;
    String ToDateValue;

    String fromString;
    String toString ;
    Button Submit,emailbutton;
    private ListView monthList;
    ActionBar actionBar;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinkinglogo_report);
        final DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getReadableDatabase();

        emailbutton=(Button)findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();


            }
        });

        Submit=(Button)findViewById(R.id.Submit);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        FromDate = (Spinner) findViewById(R.id.BlinkngFromDate);
        FromMonth = (Spinner) findViewById(R.id.BlinkngFromMonth);
        FromYear = (Spinner) findViewById(R.id.BlinkngFromYear);
        ToDate=(Spinner)findViewById(R.id.BlinkngToDate);
        ToMonth=(Spinner)findViewById(R.id.BlinkngToMonth);
        ToYear=(Spinner)findViewById(R.id.BlinkngToYear);

        listview = (ListView) findViewById(R.id.lv_BlinkingLogoReport);
        Log.e("***********Lt1*******", listview.toString());

        monthList = (ListView) findViewById(R.id.lv_BlinkingLogoReport);
        Log.e("***********Lt1*******", monthList.toString());

        final String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, month);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayList<String> dates = new ArrayList<String>();
        int Date = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1;i<=31;i++){
            dates.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        FromMonth.setAdapter(arrayAdapter);
        ToMonth.setAdapter(arrayAdapter);
        FromYear.setAdapter(adapter);
        ToYear.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates);
        FromDate.setAdapter(adapter1);
        ToDate.setAdapter(adapter1);

        FromMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromMonthValue = FromMonth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToMonthValue = ToMonth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FromYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromYearValue = FromYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToYearValue = ToYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FromDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromDateValue = FromDate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDateValue = ToDate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String month_from = FromMonthValue;
                String year_from = FromYearValue;
                String date_from = FromDateValue;
                String month_to = ToMonthValue;
                String year_to = ToYearValue;
                String date_to = ToDateValue;

                fromString = String.format("%s-%02d-%s " + "00:00:00", year_from, getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ", fromString);
                toString = String.format("%s-%02d-%s " + "23:59:59", year_to, getIntFromMonthName(month_to), date_to);
                Log.e("Value To date ", toString);

                arrayBlinkingList = helper.BlinkingLogoReport(fromString, toString);
                BlinkingListAdapter = new BlinkingLogoReportAdapter(arrayBlinkingList, BlinkingLogoReportActivity.this);
                monthList.setAdapter(BlinkingListAdapter);
                BlinkingListAdapter.notifyDataSetChanged();

            }
        });
    }

    /**
     *
     * @param monthName Month name either in full ("January") or short "Jan"
     *                  case insensitive
     * @return Returns integer corresponding to month name (1 for January...)
     *          Returns -1 if month name not recognized
     */

    private int getIntFromMonthName( String monthName ) {
        String[] monthNames = new String[] { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };

        int returnVal = -1;

        for( int i=0; i < monthNames.length; i++ ) {
            if( monthNames[i].toLowerCase().contains(monthName.toLowerCase())) {
                returnVal = i+1;
                break;
            }
        }

        return returnVal;
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

            Intent i=new Intent(BlinkingLogoReportActivity.this,MediaReportActivity.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(BlinkingLogoReportActivity.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    public void insertEmaildata() {
        try {
            DBhelper dBhelper = new DBhelper(this);

            dBhelper.insertemailBlinkinglogo(BlinkingListAdapter.getList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }



    public void Updateblinkinglistsreport() {


        for (BlinkingLogoReportModel purchase : BlinkingListAdapter.getList()) {

            final String reportmainId = String.valueOf(purchase.getAD_MAIN_ID());
            final String reportADDESC = String.valueOf(purchase.getAD_DESC());
            final String reportADSLB1 = String.valueOf(purchase.getAD_CST_SLB1());
            final String reportADSLB2 = String.valueOf(purchase.getAD_CST_SLB2());
            final String reportADSLB3 = String.valueOf(purchase.getAD_CST_SLB3());
            final String reportStartdate= String.valueOf(purchase.getAD_START_DATE());
            final String reportenddate = String.valueOf(purchase.getAD_END_DATE());
            final String reportposuser = String.valueOf(purchase.getUser_Nm());

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
                    hashMap.put(Config.Retail_report_startdate,reportStartdate);
                    hashMap.put(Config.Retail_report_enddate,reportenddate);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);




                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/development/E_mail/retail_ad_blinkinglogo_mail.php", hashMap);
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
                    Updateblinkinglistsreport();
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