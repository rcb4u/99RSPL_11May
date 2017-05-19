package com.RSPL.POS;



import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Store;


public class Activity_Media extends FragmentActivity implements View.OnClickListener{


    Button  Sbtbtn, Canclbtn;
    EditText EditStart, EditEnd, AdText,timeselect,selectendtime;
    String TickerDescAD,store_id;
    String Store_Id, AdMainId, Status;
    SimpleDateFormat admainid;
    DBhelper helper;
    String mediaid;

    //POS USER ADD
    public static String username = null;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";



    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));


    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH)+1;
    int Day = c.get(Calendar.DAY_OF_MONTH);

    public static int startDay,startMonth,startYear;
    StartDateDialogFragment startDatepicker;
    EndDateDialogFragment endDatepicker;

    boolean startDatePicked=false;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;


    String retail_ticker_op = "sh /sdcard/ticker_generation.sh";
    String superuser ="su";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity__media);
        helper = new DBhelper(Activity_Media.this);

        //POS USER ADD
        username =  login.b.getString("Pos_User");
        Store value = helper.getStoreDetails();


        mediaid=value.getMediaid();



        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        EditStart = (EditText) findViewById(R.id.txtStartDate);
        EditStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startDatepicker = new StartDateDialogFragment();

                startDatepicker.show(getSupportFragmentManager(), "showDate");
            }
        });


        EditEnd = (EditText) findViewById(R.id.txtEndDate);
        timeselect = (EditText) findViewById(R.id.timepicker);
        selectendtime = (EditText) findViewById(R.id.endtime);




        timeselect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                final int sec = mcurrentTime.get(Calendar.SECOND);

                // converting hour to tow digit if its between 0 to 9. (e.g. 7 to 07)

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Activity_Media.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String sHour = "00";
                        String sminute = "00";


                        if(selectedHour < 10)
                            sHour = "0"+selectedHour;
                        else
                            sHour = String.valueOf(selectedHour);

                        if(selectedMinute < 10)
                            sminute = "0"+selectedMinute;
                        else
                            sminute = String.valueOf(selectedMinute);




                        timeselect.setText( sHour + ":" + sminute +":" +sec);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        selectendtime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
               final int sec = mcurrentTime.get(Calendar.SECOND);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Activity_Media.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        String sHour = "00";
                        String sminute = "00";


                        if(selectedHour < 10)
                            sHour = "0"+selectedHour;
                        else
                            sHour = String.valueOf(selectedHour);

                        if(selectedMinute < 10)
                            sminute = "0"+selectedMinute;
                        else
                            sminute = String.valueOf(selectedMinute);




                        selectendtime.setText( sHour + ":" + sminute +":" +sec);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        EditEnd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (startDatePicked) {
                    endDatepicker = new EndDateDialogFragment();
                    endDatepicker.show(getSupportFragmentManager(), "showDate");
                } else {
                    Toast.makeText(getApplicationContext(), "Enter Start Date First !!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AdText = (EditText) findViewById(R.id.adedit);
        AdText.requestFocus();
        //AdText.requestFocus();




        Sbtbtn = (Button) findViewById(R.id.sbtbutton);
        Canclbtn = (Button) findViewById(R.id.canclbutton);


        Sbtbtn.setOnClickListener(this);
        Canclbtn.setOnClickListener(this);


        Status = "Created";
        final Calendar calendar = Calendar.getInstance();
        admainid = new SimpleDateFormat("yyyyMMDDHHmmss");
        AdMainId = admainid.format(calendar.getTime());
        Log.e("############", AdMainId);


        TickerDescAD = "Ad-" + AdMainId;
        Log.e("###########", TickerDescAD);

    }


    public class StartDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            validateStartDate(year, monthOfYear + 1, dayOfMonth);

        }

    }



    public class EndDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            validateEndDate(year, monthOfYear + 1, dayOfMonth);

        }

    }



    public void validateStartDate(int year,int month,int day) {
        Date Tdate=null,edate=null;

        String enddate=year+"-"+month+"-"+day;
        String todaysdate=Year+"-"+Month+"-"+Day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            edate = sdf.parse(enddate);
            Tdate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(Tdate);
        cal2.setTime(edate);
        if(cal2.before(cal1)){
            startDatepicker.dismiss();
            Toast.makeText(getApplicationContext(),"Invalid date !!",Toast.LENGTH_SHORT).show();
            startDatepicker = new StartDateDialogFragment();
            startDatepicker.show(getSupportFragmentManager(), "showDate");

        }
        else{
            startDatePicked=true;
            EditStart.setText(year + "-" + month + "-" + day);
            startDay=day;
            startMonth=month;
            startYear=year;
        }

        Log.e("########", "----------->" + Tdate);
        Log.e("########", "----------->" + edate);
    }


    public void validateEndDate(int year,int month,int day){
        Date sdate=null,edate=null;

        String enddate=year+"-"+month+"-"+day;
        String startdate=startYear+"-"+startMonth+"-"+startDay;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            edate = sdf.parse(enddate);
            sdate = sdf.parse(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(sdate);
        cal2.setTime(edate);
        if(cal2.before(cal1)){
            endDatepicker.dismiss();
            Toast.makeText(getApplicationContext(),"Invalid date !!",Toast.LENGTH_SHORT).show();
            endDatepicker = new EndDateDialogFragment();

            endDatepicker.show(getSupportFragmentManager(), "showDate");
        }
        else{
            EditEnd.setText(year + "-" + month + "-" + day);
        }
    }



    @Override
    public void onClick(View v) {
        AdText = (EditText) findViewById(R.id.adedit);

        AdText.setKeyListener(DigitsKeyListener.getInstance("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz  "));


        if (v == Sbtbtn) {


            if ( ( AdText.getText().toString().equals("")) || EditEnd.getText().toString().equals("") || EditStart.getText().toString().equals("") )
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;

            }

            else {
                DBhelper helper = new DBhelper(getApplicationContext());

                UpdateActivitymedia();
                //POS USER ADD
                helper.InsertAdTicker(AdMainId.toString(), TickerDescAD.toString(), AdText.getText().toString(), EditStart.getText().toString().concat(" ").concat(timeselect.getText().toString()), EditEnd.getText().toString().concat(" ").concat(selectendtime.getText().toString()), Status.toString(),username);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);

                AdText.setText("");
                EditStart.setText("");
                EditEnd.setText("");


                final Calendar calendar = Calendar.getInstance();
                admainid = new SimpleDateFormat("yyyyMMDDHHmmss");
                AdMainId = admainid.format(calendar.getTime());
                Log.e("############", AdMainId);
                TickerDescAD = "Ad-" + AdMainId;
                Log.e("###########", TickerDescAD);

            }

        }
        if (v ==Canclbtn ) {

            finish();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen2, menu);


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


            Intent i = new Intent(Activity_Media.this, ActivityLoyality.class);
            startActivity(i);
            return true;

        }

        if(id==R.id.action_settinginfo) {
            ShowIncentive showIncentive = new ShowIncentive(Activity_Media.this);
            showIncentive.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void UpdateActivitymedia() {



        final String TicketdescAD =  TickerDescAD.toString();
        final String Ticketmainid =AdMainId.toString();
        final String Ticketadtext =AdText.getText().toString();
        final String Ticketstart =  EditStart.getText().toString().concat(" ").concat(timeselect.getText().toString());
        final String Ticketend =EditEnd.getText().toString().concat(" ").concat(selectendtime.getText().toString());
        final String Ticketstatus =Status.toString();
        final  String media_id= mediaid;


          helper=new DBhelper(this);

        PersistenceManager.saveStoreId(Activity_Media.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(Activity_Media.this);


        class Updateticketgeneration extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
try{
                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put(Config.Ticket_store_id,store_id);
                hashMap.put(Config.Ticket_media_id,media_id);
                hashMap.put(Config.Ticket_descad,TicketdescAD);
                hashMap.put(Config.Ticket_mainid,Ticketmainid);
                hashMap.put(Config.Ticket_adtext,Ticketadtext);
                hashMap.put(Config.Ticket_start,Ticketstart);
                hashMap.put(Config.Ticket_end,Ticketend);
                hashMap.put(Config.Ticket_status,Ticketstatus);
                hashMap.put(Config.Retail_Pos_user,username);






                JSONParserSync rh = new JSONParserSync();
              // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/ticket_generation.jsp", hashMap);
                JSONObject s = rh.sendPostRequest(Config.UPDATE_TICKET_GENERATION, hashMap);
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
                //  loading = ProgressDialog.show(ActivityTender.this, "UPDATING...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //     loading.dismiss();
              //  Toast.makeText(Activity_Media.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updateticketgeneration updatemedia = new Updateticketgeneration();
        updatemedia.execute();
    }








}