package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.Store;


public class ActivityStore extends Activity  {
    ActionBar actionBar;
    TextView tvStoreid,storename ,adress,countery,contactname,emailtextview,mobileno,altmobileno,footersize,mediaid;


    EditText Storename,Email,Mobile,Alter_Mobile,footer,Zip,StoreContactName,City,Address1;
    TextView Country,Storeid;
    DBhelper mydb;

    String store_id;
    TextWatcher mobilep;
    String Store_Id_To_Update,Footer_Update;
    private PersistenceManager persistancemanager;
    Activity context = this;

    String iteam;
    TextView user2;


    //POS USER ADD
    public static String username = null;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    private TextWatcher pTextWatcher;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");

        //POS USER ADD
        username =  login.b.getString("Pos_User");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_store);
        mydb = new DBhelper(ActivityStore.this);

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
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));
///////////////////////////////////////text view///////////////////////////////////////////////////////////
        mediaid = (TextView) findViewById(R.id.media);


        tvStoreid = (TextView) findViewById(R.id.textView3);
        tvStoreid.setTextSize(Float.parseFloat(textsize));

        storename = (TextView) findViewById(R.id.textView4);
        storename.setTextSize(Float.parseFloat(textsize));

        adress = (TextView) findViewById(R.id.textView11);
        adress.setTextSize(Float.parseFloat(textsize));

        countery = (TextView) findViewById(R.id.textView100);
        countery.setTextSize(Float.parseFloat(textsize));

        contactname = (TextView) findViewById(R.id.textView19);
        contactname.setTextSize(Float.parseFloat(textsize));

        emailtextview = (TextView) findViewById(R.id.textView5);
        emailtextview.setTextSize(Float.parseFloat(textsize));

        mobileno = (TextView) findViewById(R.id.textView6);
        mobileno.setTextSize(Float.parseFloat(textsize));

        altmobileno = (TextView) findViewById(R.id.textVieealtmobileno);
        altmobileno.setTextSize(Float.parseFloat(textsize));

        footersize = (TextView) findViewById(R.id.textViewrelevant);
        footersize.setTextSize(Float.parseFloat(textsize));


///////////////////////Edit text////////////////////////////////////////////////////////////////



        Storeid = (TextView) findViewById(R.id.Store_Storeid);

        Storeid.setTextSize(Float.parseFloat(textsize));

        Storename = (EditText) findViewById(R.id.Store_Name);
        Storename.setTextSize(Float.parseFloat(textsize));



        Email = (EditText) findViewById(R.id.Store_Email);
        Email.setTextSize(Float.parseFloat(textsize));


        Mobile = (EditText) findViewById(R.id.Store_Mobile);
        Mobile.setTextSize(Float.parseFloat(textsize));


        footer = (EditText) findViewById(R.id.Store_footer);
        footer.setTextSize(Float.parseFloat(textsize));


        Alter_Mobile=(EditText) findViewById(R.id.Store_Mobile_Alter);
        Alter_Mobile.setTextSize(Float.parseFloat(textsize));


        Zip = (EditText) findViewById(R.id.Store_Zip);
        Zip.setTextSize(Float.parseFloat(textsize));


        StoreContactName = (EditText) findViewById(R.id.Store_Contact);
        StoreContactName.setTextSize(Float.parseFloat(textsize));


        City = (EditText) findViewById(R.id.Store_city);
        City.setTextSize(Float.parseFloat(textsize));


        Address1 = (EditText) findViewById(R.id.Store_Address1);
        Address1.setTextSize(Float.parseFloat(textsize));


        Country = (TextView) findViewById(R.id.Store_Country);
        Country.setTextSize(Float.parseFloat(textsize));


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        persistancemanager = new PersistenceManager();
        actionBar = getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);



        final DBhelper mydb = new DBhelper(ActivityStore.this);
        Store value = mydb.getStoreDetails();
        Storeid.setText(value.getStoreId());
        Storename.setText(value.getStoreName());
        Email.setText(value.getStoreemail());
        Mobile.setText(value.getStoreTele());
        footer.setText(value.getFooter());
        Alter_Mobile.setText(value.getAlterMobileNo());

        Zip.setText(value.getStorezip());
        City.setText(value.getStorecity());
        Country.setText(value.getStorecountry());
        Address1.setText(value.getStoreAddress());
        StoreContactName.setText(value.getStorecontactname());
        mediaid.setText(value.getMediaid());



        PersistenceManager.saveStoreId(this, value.getStoreId());

        Button Exit = (Button) findViewById(R.id.Store_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });
        mobilep = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }


            @Override
            public void afterTextChanged(Editable editable) {
                String monbile = Mobile.getText().toString();
                String Fotr=  footer.getText().toString();


            }
        };



        Button Update = (Button) findViewById(R.id.Store_update_button);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                view.startAnimation(Buttonok);



                Mobile = (EditText) findViewById(R.id.Store_Mobile);
                footer = (EditText) findViewById(R.id.Store_footer);
                Storeid = (TextView) findViewById(R.id.Store_Storeid);
                String Value = Storeid.getText().toString();
                String Value2 = footer.getText().toString();

                String mStoreName=Storename.getText().toString();
                String mEmail=Email.getText().toString();
                String mZip=Zip.getText().toString();
                String mStoreContactName = StoreContactName.getText().toString();
                String mCity = City.getText().toString();
                String mAddress1 = Address1.getText().toString();
                String mCountry = Country.getText().toString();


                Store_Id_To_Update = Value;
                Footer_Update=Value2;

                if (Mobile.getText().toString().length() >10||Mobile.getText().toString().length()!=10)
                {
                    // Toast.makeText(getApplicationContext(), "INVALID NUMBER", Toast.LENGTH_SHORT).show();
                    Mobile.setError("Please enter 10 digit mobile number");
                    return;
                }
                mydb.updateStore(Store_Id_To_Update, Mobile.getText().toString(),user2.getText().toString(),footer.getText().toString(),Alter_Mobile.getText().toString(),mStoreName,mEmail,mZip,mStoreContactName,mCity,mAddress1,mCountry);

                Toast.makeText(getApplicationContext(), "Store Updated", Toast.LENGTH_SHORT).show();
                UpdateStore();
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);

            }
        });


    }


    public  void Storedialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                           STORE MANAGEMENT");
        alertDialog.setMessage("Objective: \n" +
                "\n" +
                "The objective of the task is the following:\n" +
                "\n" +
                "Update of Phone number for a store.\n" +
                "Input Description:\n" +
                "Not Applicable\n" +
                "Fields Description:\n" +
                "\n" +
                "The following are the outputs displayed under the store management.\n" +
                "Store Id:  A unique identifier for the store.\n" +
                "Store Name: Name of the store\n" +
                "Address: The location where store is established.\n" +
                "Contact Name: Name of person to be contacted if the store has raised an issue for failure of the system using the System Maintenance --> System Maintenance Report an Issue and then select the Master data support option under the same.\n" +
                "E-Mail - Registered email address of the store. This is used to send e-Mail for all the reports to the Store Contact person. This is used for printing of reports.\n" +
                "Mobile - Mobile number of the Store to be updated.\n" +
                "Rules :\n" +
                "\n" +
                "The phone number is numeric and 10 digits are allowed.\n" +
                "Functions :\n" +

                "There are 2 options listed below:\n" +
                "UPDATE:  Update the record.\n" +
                "CANCEL: Go back to menu option.");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
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



        mydb=new DBhelper(ActivityStore.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityStore.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i = new Intent(ActivityStore.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityStore.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                Storedialog();
                return true;
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityStore.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;


            case R.id.action_settinginv:
                Intent in=new Intent(ActivityStore.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityStore.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            //noinspection SimplifiableIfStatement


            case R.id.action_logout:
                Logout();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(ActivityStore.this ,login.class);
        startActivity(intent);
    }





    public void UpdateStore() {

        final DBhelper mydb = new DBhelper(ActivityStore.this);

        final String  mobile_no =Mobile.getText().toString().trim();

        final String storefooter=       footer.getText().toString().trim();
        final String alternatemobileno=       Alter_Mobile.getText().toString().trim();

        final String pos_user = user2.getText().toString();

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
                    hashMap.put(Config.RETAIL_STORE_MOBILE_NUMBER,mobile_no);
                    hashMap.put(Config.RETAIL_STORE_FOOTER,storefooter);
                    hashMap.put(Config.RETAIL_STORE_ALTERMOBILENO,alternatemobileno);



                    hashMap.put(Config.RETAIL_STORE_STORE_ID,store_id);
                    hashMap.put(Config.Retail_Pos_user,pos_user);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_STORE,hashMap);
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