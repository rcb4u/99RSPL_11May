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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.DoctorAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.DoctorPojo;
import Pojo.Registeremployeesmodel;

public class Activity_Doctor extends Activity {

    String dr_name,dr_active;
    EditText DOCTORADDRESS;
    EditText DOCTORNAME;
    LinearLayout hidden;

    Spinner DOCTORSPECIAL;
    LinearLayout hidden2;
    Button addbtn,update;
    ActionBar actionBar;

    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    ArrayList<DoctorPojo> doctorlist;
    TextView Doctorname, Doctorspeclz,Doctid,doctoridhide;
    String iteam;
    TextView user2;


    ////////////////////jimmy//////////////////////////
    TextView tvDoctorname, tvDoctorspeclz,tvsearch,tvactive;
    TextView createDoctorname, createDoctorspeclz,createadress,createactive;

///////////////////////////////////////////////////////////////////////////////////////////////

    DoctorAdapter adapter;
    Bundle syncDataBundle = null;
    String item;
    Spinner Active,DocActive;
    String ActiveType[];
    String SpinValue;
    String dr_id;
    ArrayAdapter adapterActiveType;
    public static String username = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper mydb = new DBhelper(Activity_Doctor.this);
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity__doctor);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar = getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
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


////////////////////////////////////////////////////////show doctor/////////////////////////////////////////////////////////
        tvDoctorname = (TextView) findViewById(R.id.textView0);
        tvDoctorname.setTextSize(Float.parseFloat(textsize));

        tvDoctorspeclz = (TextView) findViewById(R.id.textViewhidden);
        tvDoctorspeclz.setTextSize(Float.parseFloat(textsize));

        tvsearch = (TextView) findViewById(R.id.textViewhidden2);
        tvsearch.setTextSize(Float.parseFloat(textsize));

        tvactive = (TextView) findViewById(R.id.textViewCREDIT);
        tvactive.setTextSize(Float.parseFloat(textsize));




        autoCompleteTextView = (CustomAuto) findViewById(R.id.myautocomplete);
        Doctorname = (TextView) findViewById(R.id.doct_show_name);
        Doctorname.setTextSize(Float.parseFloat(textsize));
        Doctorspeclz = (TextView) findViewById(R.id.doct_show_special);
        Doctorspeclz.setTextSize(Float.parseFloat(textsize));
        Active = (Spinner) findViewById(R.id.doc_active1);





////////////////////////////////////////////////////////////////create doctor/////////////////////////////////

        createDoctorname = (TextView) findViewById(R.id.textView1);
        createDoctorname.setTextSize(Float.parseFloat(textsize));
        createadress = (TextView) findViewById(R.id.textView2);
        createadress.setTextSize(Float.parseFloat(textsize));
        createDoctorspeclz = (TextView) findViewById(R.id.textViewemail);
        createDoctorspeclz.setTextSize(Float.parseFloat(textsize));
        createactive = (TextView) findViewById(R.id.textactive);
        createactive.setTextSize(Float.parseFloat(textsize));





        DOCTORADDRESS = (EditText) findViewById(R.id.Doct_addr);
        DOCTORADDRESS.setTextSize(Float.parseFloat(textsize));
        DOCTORNAME = (EditText) findViewById(R.id.Doct_name);
        DOCTORNAME.setTextSize(Float.parseFloat(textsize));
        DOCTORSPECIAL = (Spinner) findViewById(R.id.doctor_special);
        DocActive=(Spinner)findViewById(R.id.doc_active);


////////////////////////////////////hide////////////////////////////////////////////////////////////
        Doctid = (EditText) findViewById(R.id.doctid);
        doctoridhide=(TextView)findViewById(R.id.doct_id);



        final EditText Custhidd = (EditText) findViewById(R.id.Cust_mobilenohidd);
        LinearLayout show = (LinearLayout) findViewById(R.id.Hiddenlayout1);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        Doctid.setText(result);

        username = login.b.getString("Pos_User");

        mydb = new DBhelper(this);
        autoCompleteTextView.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                doctorlist = mydb.getAllDoctors(userTypedString);

                if (doctorlist != null) {
                    if (adapter == null) {
                        adapter = new DoctorAdapter(Activity_Doctor.this, android.R.layout.simple_dropdown_item_1line, doctorlist);
                        adapter.setDoctorList(doctorlist);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        adapter.setDoctorList(doctorlist);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(Activity_Doctor.this);

                DoctorPojo value = (DoctorPojo) parent.getItemAtPosition(pos);
                Doctorname.setText(value.getDoctorName());
                Doctorspeclz.setText(value.getDoctorSpeciality());
                doctoridhide.setText(value.getDoctid());
                SpinValue = value.getActive();
                if (SpinValue.equals("Y")) {
                    Active.setSelection(0);
                }
                if (SpinValue.equals("N")) {
                    Active.setSelection(1);
                }
                Custhidd.requestFocus();
                autoCompleteTextView.setText("");


            }
        });
        final DBhelper mydb = new DBhelper(Activity_Doctor.this);
        ArrayList<String> doctorspecial = mydb.getdoctorspecialication();
        ArrayAdapter<String> stringArrayAdapter =
                new ArrayAdapter<String>(Activity_Doctor.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
        DOCTORSPECIAL.setAdapter(stringArrayAdapter);
        DOCTORSPECIAL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        addbtn = (Button) findViewById(R.id.Cust_ok_button);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.startAnimation(Buttonok);
                hidden2 = (LinearLayout) findViewById(R.id.Hiddenlayout1);
                hidden2.setVisibility(View.INVISIBLE);
                hidden = (LinearLayout) findViewById(R.id.Hiddenlayout);
                hidden.setVisibility(View.VISIBLE);
                LinearLayout hidden3 = (LinearLayout) findViewById(R.id.autolayout);
                hidden3.setVisibility(View.INVISIBLE);
                DOCTORADDRESS = (EditText) findViewById(R.id.Doct_addr);
                DOCTORNAME = (EditText) findViewById(R.id.Doct_name);
                DOCTORSPECIAL = (Spinner) findViewById(R.id.doctor_special);
                DOCTORADDRESS.setFocusableInTouchMode(true);
                DOCTORNAME.setFocusableInTouchMode(true);
                DOCTORSPECIAL.setFocusableInTouchMode(true);


                DBhelper db = new DBhelper(Activity_Doctor.this);

                if (DOCTORNAME.getText().toString().matches("") || item.toString().matches("")) {
                    Toast toast = Toast.makeText(Activity_Doctor.this, "Please fill the filled", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                } else {
                    db.insertDoctor(new DoctorPojo(Doctid.getText().toString(), DOCTORNAME.getText().toString(),
                            item.toString(),DOCTORADDRESS.getText().toString(), SpinValue), username);
                    Toast toast = Toast.makeText(Activity_Doctor.this, "Doctor Added", Toast.LENGTH_SHORT);
                    toast.show();
                    insertDoctor();
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);

                }

            }
        });
        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        DocActive.setAdapter(adapterActiveType);
        DocActive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = DocActive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update=(Button)findViewById(R.id.doc_update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Doctorname.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(Activity_Doctor.this, "Please select the doctor name", Toast.LENGTH_SHORT);



                    toast.show();
                    return;


                }

                String name=Doctorname.getText().toString();
                String docspecialization=Doctorspeclz.getText().toString();
                dr_id = doctoridhide.getText().toString();
                mydb.updateDoctordata(dr_id,name,docspecialization,SpinValue ,user2.getText().toString());
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
                updateDoctorActive();

            }

        });




        Button Exit = (Button) findViewById(R.id.Cust_Exit_button);

        Exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });
        Button Clear = (Button) findViewById(R.id.Cust_clear_button);
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                try{
                    DOCTORSPECIAL.setAdapter(null);
                    final DBhelper mydb = new DBhelper(Activity_Doctor.this);
                    ArrayList<String> doctorspecial = mydb.getdoctorspecialication();
                    ArrayAdapter<String> stringArrayAdapter =
                            new ArrayAdapter<String>(Activity_Doctor.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
                    DOCTORSPECIAL.setAdapter(stringArrayAdapter);
                    DOCTORADDRESS.setText("");
                    //   DOCTORNAME.setText("");
                    //   DoctorSpecialition.setText("");
                    Doctorname.setText("");
                    autoCompleteTextView.setText("");
                    Doctorname.setText("");
                    DOCTORNAME.setText("");
                    Doctorspeclz.setText("");

                }catch (Exception e)
                {
                    e.printStackTrace();
                }
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



        mydb=new DBhelper(Activity_Doctor.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(Activity_Doctor.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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




    public  void Doctorhelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  CAPTURING DOCTOR");
        alertDialog.setMessage("Objective:\n" +
                "     \n" +
                "The user can  create or search the Doctors in parrticular locality and make doctor active or inactive based on the uses in that store.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Search - The user can enters the doctor name.\n" +
                "Fields Description:\n" +
                "\n" +
                "Doctor Name:  Name of the doctor.\n" +
                "Specialization: Doctor’s specialization.\n" +
                "Address: Address of the Doctor.\n" +
                "Active: Doctor is active 'Y' or in-Active 'N'.\n" +
                "Functions: \n" +
                "There are 3 options below:\n" +
                "         A. CREATE - To create new doctor record(see figure a).\n" +
                "         B. CLEAR- Clear the record from the window and make it available for re-entry.\n" +
                "         C. EXIT - Go back to the menu option.\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



                dialog.dismiss();
            }
        });


        alertDialog.show();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try{

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
                Intent i = new Intent(Activity_Doctor.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_Doctor.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                Doctorhelpdialog();
                return true;
/*

            case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_Doctor.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_Doctor.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(Activity_Doctor.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_Doctor.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(Activity_Doctor.this ,login.class);
        startActivity(intent);
    }


    public void insertDoctor() {


        final String dr_name = DOCTORNAME.getText().toString().trim();
        final String dr_address = DOCTORADDRESS.getText().toString().trim();
        final String dr_speciality = DOCTORSPECIAL.getSelectedItem().toString();
        final String pos_user = user2.getText().toString();
        final  String dr_id = Doctid.getText().toString();
       final  String dr_active= SpinValue.toString();

        PersistenceManager.saveStoreId(Activity_Doctor.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(Activity_Doctor.this);

        class UpdateDoctor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(Activity_Doctor.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(Activity_Doctor.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.DOCTOR_DISCRIPTION_STORE_ID, store_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ID, dr_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_NAME, dr_name);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ADDRESS, dr_address);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_SPECIALITY, dr_speciality);
                    hashMap.put(Config.Retail_Pos_user, pos_user);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ACTIVE, dr_active);



                    JSONParserSync rh = new JSONParserSync();
                   // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/Dr.Discription_Update.jsp", hashMap);
                    JSONObject s = rh.sendPostRequest(Config.INSERT_DOCTOR, hashMap);

                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updatedoctorsflag(dr_id);
                        Toast.makeText(getApplication(),"Doctor Updated",Toast.LENGTH_LONG).show();

                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        //  return s.getString(TAG_MESSAGE);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateDoctor updatedr = new UpdateDoctor();
        updatedr.execute();


    }



    public void updateDoctorActive() {


        // dr_name = DOCTORNAME.getText().toString().trim();

      final String  dr_id = doctoridhide.getText().toString();
        final String dr_active= SpinValue.toString();


        PersistenceManager.saveStoreId(Activity_Doctor.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(Activity_Doctor.this);

        class UpdateDoctoractive extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(Activity_Doctor.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(Activity_Doctor.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.DOCTOR_DISCRIPTION_STORE_ID, store_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ID, dr_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ACTIVE, dr_active);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_DOCTOR, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updatedoctorsflag(dr_id);



                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        //  return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateDoctoractive updatedr = new UpdateDoctoractive();
        updatedr.execute();


    }


}





//        private boolean isValidEmail(String email) {
//            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//
//            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//            Matcher matcher = pattern.matcher(email);
//            return matcher.matches();
//        }

