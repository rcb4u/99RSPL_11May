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
import android.view.KeyEvent;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Adapter.CustomerAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Customer;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;

/**
 * Created by nishant on 1/27/2016.
 */
public class ActivityCustomer extends Activity

{
    EditText CUSTOMERNAME;
    EditText CUSTOMERMOBILE;
    EditText CUSTOMEREMAIL;
    EditText CUSTOMERADDRESS;
    LinearLayout hidden;
    LinearLayout hidden2;
    Button addbtn, update;
    ActionBar actionBar;

    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    ArrayList<Customer> customerlist;
    Spinner Credithidd, Customercredit;

    DBhelper mydb = new DBhelper(ActivityCustomer.this);
    String custid;
    String iteam;
    TextView user2;

    ////////////////////////////////jimmmy for size//////////////////////

    TextView seatchtv, searchCustomermobile, searchCustomeremail,searchcustomername, searchCustomeradresss,searchcreditcustomer;

    TextView createCustomermobile, createCustomeremail,createcustomername, createCustomeradresss,createcreditcustomer;
    String backroundcolour,colorchange;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////



    CustomerAdapter adapter;
    String ActiveType[];
    ArrayAdapter adapterActiveType;
    String SpinValue;


    TextView Customername, Customermobile, Customeremail, Customeradressshown;




    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    public static String username = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String cust_mobile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.test_customer);

        username = login.b.getString("Pos_User");


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




//////////////////////////////create//////////////////////////////////////////////////////

        createCustomermobile = (TextView) findViewById(R.id.textView1);
        createCustomermobile.setTextSize(Float.parseFloat(textsize));

        createcustomername = (TextView) findViewById(R.id.textView2);
        createcustomername.setTextSize(Float.parseFloat(textsize));
        createCustomeremail = (TextView) findViewById(R.id.textViewCREDIT1);
        createCustomeremail.setTextSize(Float.parseFloat(textsize));
        createCustomeradresss = (TextView) findViewById(R.id.textVi2);
        createCustomeradresss.setTextSize(Float.parseFloat(textsize));
        createcreditcustomer = (TextView) findViewById(R.id.textViewemail);
        createcreditcustomer.setTextSize(Float.parseFloat(textsize));



        CUSTOMERNAME = (EditText) findViewById(R.id.Cust_name);
        CUSTOMERNAME.setTextSize(Float.parseFloat(textsize));
        CUSTOMERMOBILE = (EditText) findViewById(R.id.Cust_mobileno);
        CUSTOMERMOBILE.setTextSize(Float.parseFloat(textsize));
        CUSTOMEREMAIL = (EditText) findViewById(R.id.Cust_email);
        CUSTOMEREMAIL.setTextSize(Float.parseFloat(textsize));
        CUSTOMERADDRESS = (EditText) findViewById(R.id.Cust_address_create);
        CUSTOMERADDRESS.setTextSize(Float.parseFloat(textsize));

/////////////////////////////////////////////////search////////////////////////////////////////////////

        seatchtv = (TextView) findViewById(R.id.textView0);
        seatchtv.setTextSize(Float.parseFloat(textsize));
        searchCustomermobile = (TextView) findViewById(R.id.textViewhidden);
        searchCustomermobile.setTextSize(Float.parseFloat(textsize));
        searchCustomeremail = (TextView) findViewById(R.id.textViewhidden2);
        searchCustomeremail.setTextSize(Float.parseFloat(textsize));
        searchcustomername = (TextView) findViewById(R.id.textViewhidden2);
        searchcustomername.setTextSize(Float.parseFloat(textsize));
        searchCustomeradresss = (TextView) findViewById(R.id.textViewema);
        searchCustomeradresss.setTextSize(Float.parseFloat(textsize));
        searchcreditcustomer = (TextView) findViewById(R.id.textViewCREDIT);
        searchcreditcustomer.setTextSize(Float.parseFloat(textsize));




        Customername = (TextView) findViewById(R.id.Cust_name2);
        Customername.setTextSize(Float.parseFloat(textsize));
        Customermobile = (TextView) findViewById(R.id.Cust_mobileno1);
        Customermobile.setTextSize(Float.parseFloat(textsize));
        Customeremail = (TextView) findViewById(R.id.Cust_email2);
        Customeremail.setTextSize(Float.parseFloat(textsize));
        Customeradressshown = (TextView) findViewById(R.id.Cust_adress_shown);
        Customeradressshown.setTextSize(Float.parseFloat(textsize));


        Customercredit = (Spinner) findViewById(R.id.Cust_Credit2);
        Credithidd = (Spinner) findViewById(R.id.Cust_Credithidden);


        final EditText Custhidd = (EditText) findViewById(R.id.Cust_mobilenohidd);
        LinearLayout show = (LinearLayout) findViewById(R.id.Hiddenlayout1);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        // final AutoCompleteTextView autoCompleteTextView;
        mydb = new DBhelper(this);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.myautocomplete);


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
                customerlist = mydb.getAllCustomers(userTypedString);

                if (customerlist != null) {
                    if (adapter == null) {
                        adapter = new CustomerAdapter(ActivityCustomer.this, android.R.layout.simple_dropdown_item_1line, customerlist);
                        adapter.setCustomerList(customerlist);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {

                        adapter.setCustomerList(customerlist);
                        adapter.notifyDataSetChanged();
//
                    }
                }
            }
        };
        CUSTOMEREMAIL.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (CUSTOMEREMAIL.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0) {
                    //  CUSTOMEREMAIL .setText("valid email");

                } else {
                    // CUSTOMEREMAIL.setError("invalid email");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(ActivityCustomer.this);

                Customer value = (Customer) parent.getItemAtPosition(pos);
                Customername.setText(value.getCustomername());
                Customermobile.setText(value.getCustomermobileno());
                Customeremail.setText(value.getCustomeremail());
                Customeradressshown.setText(value.getCustomeradress());

                SpinValue = value.getCustomercredit();
                if (SpinValue.equals("Y")) {
                    Customercredit.setSelection(0);
                }
                if (SpinValue.equals("N")) {
                    Customercredit.setSelection(1);
                }
                Custhidd.requestFocus();
                autoCompleteTextView.setText("");


            }
        });


        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Customercredit.setAdapter(adapterActiveType);
        Customercredit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Customercredit.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Credithidd.setAdapter(adapterActiveType);
        Credithidd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Credithidd.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
                CUSTOMERNAME.setFocusableInTouchMode(true);
                CUSTOMERMOBILE.setFocusableInTouchMode(true);
                CUSTOMEREMAIL.setFocusableInTouchMode(true);
                CUSTOMERADDRESS.setFocusableInTouchMode(true);
                custid = getSystemCurrentTime();


                DBhelper db = new DBhelper(ActivityCustomer.this);
                PersistenceManager.saveStoreId(ActivityCustomer.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
                final String store_id = PersistenceManager.getStoreId(ActivityCustomer.this);

                if (CUSTOMEREMAIL.getText().toString().matches("")) {

                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(CUSTOMEREMAIL.getText().toString()).matches()) {
                    //Validation for Invalid Email Address
                    //  Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    //   CUSTOMEREMAIL.setError("Invalid Email");
                    return;
                }


                if (CUSTOMERMOBILE.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(ActivityCustomer.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;


                }

                if (CUSTOMERMOBILE.getText().toString().length() > 10 || CUSTOMERMOBILE.getText().toString().length() != 10) {
                    CUSTOMERMOBILE.setError("Invalid Number");
                    return;
                }


                if (SpinValue == "Y") {
                    Customercredit.setBackgroundColor(Integer.parseInt("#FF9033"));

                }


                if (db.CheckIsDataAlreadyInDBorNot(CUSTOMERMOBILE.getText().toString()))


                {
                    Toast toast1 = Toast.makeText(ActivityCustomer.this, "MOBILE NO ALREADY REGISTERED", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                } else if (db.insertCustomer(new Customer(CUSTOMERMOBILE.getText().toString(),
                        CUSTOMERNAME.getText().toString(), CUSTOMEREMAIL.getText().toString(), SpinValue, CUSTOMERADDRESS.getText().toString()), username, custid, store_id))

                {
                    Toast toast = Toast.makeText(ActivityCustomer.this, "CUSTOMER ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    updateCustomer();
                    //             UpdateShell();

                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);


                }

            }
        });


        update = (Button) findViewById(R.id.Cust_update_button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Customername.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(ActivityCustomer.this, "Please select the customer name", Toast.LENGTH_SHORT);
                    toast.show();
                    return;


                }
                PersistenceManager.saveStoreId(ActivityCustomer.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                final String store_id = PersistenceManager.getStoreId(ActivityCustomer.this);
                String name = Customername.getText().toString();
                String mobileno = Customermobile.getText().toString();
                String email = Customeremail.getText().toString();


                mydb.updateCustomer(name, email, mobileno, SpinValue, user2.getText().toString(), store_id);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
                updateCustomerActive();


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
                CUSTOMEREMAIL.setText("");
                CUSTOMERNAME.setText("");
                CUSTOMERMOBILE.setText("");
                Customeremail.setText("");
                Customermobile.setText("");
                CUSTOMERADDRESS.setText("");
                Customername.setText("");
                autoCompleteTextView.setText("");
                Customeradressshown.setText("");

            }
        });
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
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



        mydb=new DBhelper(ActivityCustomer.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityCustomer.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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



    public void Customerhelpdialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("    CUSTOMER MANAGEMENT    ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "User selects the Customer Management to either create a customer or select a known customer. The customer can be accessed using the sales order management.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Search - The user enters the customer name to retrieve an existing customer. \n" +
                "Fields Description:\n" +
                "\n" +
                "Mobile number: Mobile no of a particular customer.\n" +
                "Customer Name: Name of the customer.\n" +
                "Email: Email of a particular customer.\n" +
                "Credit Customer: Credit term means that the customer can pay at a later date. Customer is credit customer if 'Y' or customer in non-credit customer if 'N'.\n" +
                "\n" +
                "Functions:\n" +
                " There are 3 options below:\n" +
                "A. CREATE - To create new customer record.(see fig a)\n" +
                "B. CLEAR-    Clear the record from the window and make it available for re-entry. \n" +
                "C. EXIT - Go back to the menu option.\n" +
                "\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
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


        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i = new Intent(ActivityCustomer.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityCustomer.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                Customerhelpdialog();
                return true;
//
//            case R.id.action_settingpurchase:
//                Intent p=new Intent(ActivityCustomer.this,PurchaseActivity.class);
//                startActivity(p);
//                return true;

            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityCustomer.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in = new Intent(ActivityCustomer.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(ActivityCustomer.this, ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityCustomer.this ,login.class);
        startActivity(intent);
    }

    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }

    public void updateCustomer() {


        final String cust_mobile2 = CUSTOMERMOBILE.getText().toString().trim();
        final String cust_name = CUSTOMERNAME.getText().toString().trim();
        final String cust_email = CUSTOMEREMAIL.getText().toString();
        final String id = custid.toString();
        final String adress = CUSTOMERADDRESS.getText().toString();
        final String CreditCust=SpinValue.toString();
        final String pos_user = user2.getText().toString();


        PersistenceManager.saveStoreId(ActivityCustomer.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(ActivityCustomer.this);
        class UpdateCustomerinsert extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityCustomer.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                //  Toast.makeText(ActivityCustomer.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.RETAIL_CUST_STORE_ID, store_id);
                    hashMap.put(Config.RETAIL_CUST_NAME, cust_name);
                    hashMap.put(Config.RETAIL_CUST_EMAIL, cust_email);
                    hashMap.put(Config.RETAIL_CUST_MOBILE_NO, cust_mobile2);
                    hashMap.put(Config.RETAIL_CUST_ID, id);
                    hashMap.put(Config.RETAIL_CUST_ADRESS, adress);
                    hashMap.put(Config.RETAIL_CUST_ACTIVE, CreditCust);
                    hashMap.put(Config.Retail_Pos_user, pos_user);



                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.INSERT_CUSTOMER, hashMap);
//                    Toast.makeText(ActivityCustomer.this, s.toString(), Toast.LENGTH_LONG).show();

                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());

                        mydb.updateflagcustomer(cust_mobile);


//                         return s.getString(TAG_MESSAGE);
                    } else {

                        // return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        UpdateCustomerinsert updatedr = new UpdateCustomerinsert();
        updatedr.execute();
    }


    public void updateCustomerActive() {

        final DBhelper mydb = new DBhelper(ActivityCustomer.this);

        cust_mobile = CUSTOMERMOBILE.getText().toString().trim();
        final String cust_name = CUSTOMERNAME.getText().toString().trim();
        final String cust_active = SpinValue.toString();


        PersistenceManager.saveStoreId(ActivityCustomer.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(ActivityCustomer.this);
        class UpdateCustomer extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityCustomer.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                //  Toast.makeText(ActivityCustomer.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.RETAIL_CUST_STORE_ID, store_id);
                    hashMap.put(Config.RETAIL_CUST_NAME, cust_name);
                    hashMap.put(Config.RETAIL_CUST_ACTIVE, cust_active);
                    hashMap.put(Config.RETAIL_CUST_MOBILE_NO, cust_mobile);
                    hashMap.put(Config.RETAIL_CUST_ID, custid);
                    hashMap.put(Config.Retail_Pos_user, username);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_CUSTOMER, hashMap);
                    //  Toast.makeText(ActivityCustomer.this, s, Toast.LENGTH_LONG).show();

                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());

                        mydb.updateflagcustomer(cust_mobile);


//                         return s.getString(TAG_MESSAGE);
                    } else {

                        // return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        UpdateCustomer updatedr = new UpdateCustomer();
        updatedr.execute();
    }



}
