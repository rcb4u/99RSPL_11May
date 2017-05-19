package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;


public class ActivityDayOpen extends Activity  {
    ActionBar actionBar;
    TextView BusinessDate;
   TextView Startdate;
    EditText StartCash,Store,transdate;
    DBhelper mydb;
    Button btn;
    String store_id;
    Bundle syncDataBundle = null;
    public static String username = null;
    public static Bundle b = new Bundle();
    TextView tvposdate,tvstatrdate,tvstartcash;
    String backroundcolour,colorchange;
    String iteam;
    TextView user2;




    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String retail_dayopen_op = "sh /sdcard/day_open.sh";
    String superuser ="su";

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_day_open);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        username =  login.b.getString("Pos_User");


        actionBar=getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(ActivityDayOpen.this);
        Decimal valuetextsize = mydb.getStoreprice();
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));




        tvposdate=(TextView)findViewById(R.id.textView1);
        tvposdate.setTextSize(Float.parseFloat(textsize));

        tvstatrdate=(TextView)findViewById(R.id.textView2);

        tvstatrdate.setTextSize(Float.parseFloat(textsize));

        tvstartcash=(TextView)findViewById(R.id.textView3);
        tvstartcash.setTextSize(Float.parseFloat(textsize));





        BusinessDate=(TextView)findViewById(R.id.posdate);
        BusinessDate.setTextSize(Float.parseFloat(textsize));


        Startdate=(TextView)findViewById(R.id.startdate);
        Startdate.setTextSize(Float.parseFloat(textsize));

        StartCash=(EditText)findViewById(R.id.startcash);
        StartCash.setTextSize(Float.parseFloat(textsize));

        Store = (EditText)findViewById(R.id.storeid);
        transdate = (EditText)findViewById(R.id.transdate);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);






            Long value = System.currentTimeMillis();
            String result = Long.toString(value);
            transdate.setText(result);
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
        BusinessDate.setText(dateFormat.format(date));
        Startdate.setText(dateFormat.format(date));

        btn=(Button)findViewById(R.id.opencash_button);
        final DBhelper mydb = new DBhelper(this);
        Cursor res = mydb.getStoreDay();
        if(res.moveToFirst())
        {
            do{
                String store = res.getString(res.getColumnIndex(DBhelper.STORE_ID));
                Store.setText(store);

            }while (res.moveToNext());
        }


            btn.setOnClickListener( new View.OnClickListener(){
                @Override
            public void onClick(View v) {
                BusinessDate=(TextView)findViewById(R.id.posdate);
                Startdate=(TextView)findViewById(R.id.startdate);
                StartCash=(EditText)findViewById(R.id.startcash);
                    Store = (EditText)findViewById(R.id.storeid);
                DBhelper db = new DBhelper(ActivityDayOpen.this);
                    if(StartCash.getText().toString().matches(""))
                    {
                        StartCash.setError("Please enter the cash");
                        return;

                    }

                    if(db. CheckDateAlreadyInDBorNot(StartCash.getText().toString()))


                    {
                        Toast toast1 = Toast.makeText(ActivityDayOpen.this, "PLEASE CLOSE THE DAY FIRST",Toast.LENGTH_SHORT);
                        toast1.show();
                        return;
                    }
                    if(db.CheckCashInHandAlreadyInDBorNot(StartCash.getText().toString()))
                    {

                    }

                        if  (db.insertDayopen(transdate.getText().toString(),Store.getText().toString(),StartCash.getText().toString(),user2.getText().toString()));
                Toast toast1 = Toast.makeText(ActivityDayOpen.this, "DAY IS OPENED", Toast.LENGTH_SHORT);
                toast1.show();
                    UpdateDayOpen();

                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);

                    ActivityDayOpen.b.putString("Start_Cash",StartCash.getText().toString());


                }
        });



        Button Exit = (Button)findViewById(R.id.day_close_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);
            }
        });




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);


        final MenuItem item2 = menu.findItem(R.id.TextView);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        user2 = (TextView) MenuItemCompat.getActionView(item2);


        // user2.setText(username);



        mydb=new DBhelper(ActivityDayOpen.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityDayOpen.this, android.R.layout.simple_spinner_dropdown_item,user_name);

        spinner.setAdapter(stringArrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.d("slected item   :  ", iteam);

                if (!user2.getText().toString().trim().isEmpty()) {
                    iteam = spinner.getSelectedItem().toString();


//                    user2.setText("");
                    user2.setText(iteam );
                }

                else {

                    user2.setText(username);
                }



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        return true;


    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(ActivityDayOpen.this,ActivitySales.class);
                startActivity(i);
                return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityDayOpen.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
               help_DayOpen();
                return true;
         /*   case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityDayOpen.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityDayOpen.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityDayOpen.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityDayOpen.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(ActivityDayOpen.this ,login.class);
        startActivity(intent);
    }

    public void UpdateDayOpen() {



        final String dayopenstartdate = Startdate.getText().toString();
        final String dayopenbuisnessdate = BusinessDate.getText().toString();
        final String dayopenstartcash = StartCash.getText().toString();
        final String dayopentransdate = transdate.getText().toString();
        final String pos_user = user2.getText().toString();
      //  final String doctorid = Doc

        mydb = new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityDayOpen.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityDayOpen.this);

        class UpdateDayopen extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.Retail_store_id, store_id);
                hashMap.put(Config.Retail_dayopen_buisnessdate,dayopenbuisnessdate);
                hashMap.put(Config.Retail_dayopen_startcash,dayopenstartcash);
                hashMap.put(Config.Retail_dayopen_startdate,dayopenstartdate);
                hashMap.put(Config.Retail_dayopen_Tri_id,dayopentransdate);
                hashMap.put(Config.Retail_dayopen_flag, "1");
                hashMap.put(Config.Retail_Pos_user, pos_user);



                JSONParserSync rh = new JSONParserSync();
                  //  JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/Day_Open_Json_Upload.jsp", hashMap);
                    JSONObject s = rh.sendPostRequest(Config.DAY_OPEN, hashMap);
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
                //  loading = ProgressDialog.show(ActivityDayOpen.this, "UPDATING...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                Toast.makeText(ActivityDayOpen.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateDayopen updatedayopen = new UpdateDayopen();
        updatedayopen.execute();
    }






    public void help_DayOpen() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("               DAY OPENING");
        alertDialog.setMessage(" \n" +
                        "Objective:\n" +
                        "\n" +
                        "User select “Day Opening” to do the following options.\n" +
                        "\n" +
                        "Field Descriptions:\n" +
                        "\n" +
                        "POS Date:\n" +
                        "Start Date:\n" +
                        "\n" +
                        "Input descriptions:\n" +
                        "\n" +
                        "Start Cash: The Cash on Hand when the user is opening a store.\n" +
                        "The “Day Opening” is an optional step. \n" +
                        "\n" +
                        "Options:\n" +
                        "\n" +
                        "OPEN CASH: Declare the cash at the store opening.\n" +
                        "EXIT:\n"
        );
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }
}

