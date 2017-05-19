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

import Adapter.localvendoradapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.localvendor;


public class ActivitylocalVendor extends Activity {

    EditText localvendorname,localvendorcontactname,localvendoraddress,
            localvendorcity, localvendorzip,localvendortele,localvendormobile;
    TextView localvendorcountry, localvendorid;
    ActionBar actionBar;

    String localVendor_Id_To_Update;
    TextWatcher mTextWatcher;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    public static String username = null;

    AutoCompleteTextView autoCompleteTextView;
  Spinner localvendoractive, localvendorinventory;

    ArrayList<localvendor>localvendorlist;
    DBhelper mydb;
    localvendoradapter adapter;
    String ActiveType[],SpinType[];
    String item;
    String SpinValue,InvSpinValue;
    String iteam;
    TextView user2;


//////////////////////jimmy////////////(for size)/////////////////////////////////////////////////

    String backroundcolour,colorchange;

    TextView     tvsearch ,tvlocalvendorid, tvlocalvendorname,tvlocalvendorcontactname,tvlocalvendoraddress,tvlocalvendorcountry,
            tvlocalvendorcity,tvlocalvendormobile ,tvinventory,tvlocalvendorzip,tvactive,tvlocalvendortele;


    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    // Adapter
    ArrayAdapter<String> adapterActiveType;
    ArrayAdapter<String>InventoyActiveType;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activitylocal_vendor);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        username =  login.b.getString("Pos_User");

        mydb = new DBhelper(ActivitylocalVendor.this);

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
        actionBar=getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        ///////////////////////////////jimmy(for size)///////////////////////


        tvsearch = (TextView)findViewById(R.id.local_nametextView);
        tvsearch.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorid = (TextView)findViewById(R.id.store_nametextView);
        tvlocalvendorid.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorname = (TextView)findViewById(R.id.store_mobiletextView);
        tvlocalvendorname.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorcontactname = (TextView)findViewById(R.id.counterid_textView);
        tvlocalvendorcontactname.setTextSize(Float.parseFloat(textsize));
        tvlocalvendoraddress = (TextView)findViewById(R.id.emailid_textView);
        tvlocalvendoraddress.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorcountry = (TextView)findViewById(R.id.city_textView);
        tvlocalvendorcountry.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorcity = (TextView)findViewById(R.id.telephone_textView);
        tvlocalvendorcity.setTextSize(Float.parseFloat(textsize));
        tvlocalvendormobile = (TextView)findViewById(R.id.dstrnum_textView);
        tvlocalvendormobile.setTextSize(Float.parseFloat(textsize));
        tvinventory = (TextView)findViewById(R.id.vendinv_textView);
        tvinventory.setTextSize(Float.parseFloat(textsize));
        tvlocalvendorzip = (TextView)findViewById(R.id.active_textView);
        tvlocalvendorzip.setTextSize(Float.parseFloat(textsize));
        tvactive = (TextView)findViewById(R.id.active_textViewv);
        tvactive.setTextSize(Float.parseFloat(textsize));
        tvlocalvendortele = (TextView)findViewById(R.id.statecountry_textView);
        tvlocalvendortele.setTextSize(Float.parseFloat(textsize));

        //////////////////////////////////////////////////////////////////////////////////////////////////////

        localvendorid = (TextView)findViewById(R.id.localvend_vendorid);
        localvendorid.setTextSize(Float.parseFloat(textsize));

        localvendorname= (EditText)findViewById(R.id.localvend_name);
        localvendorname.setTextSize(Float.parseFloat(textsize));
        localvendorcontactname =(EditText)findViewById(R.id.localvend_contactname);
        localvendorcontactname.setTextSize(Float.parseFloat(textsize));
        localvendoraddress = (EditText)findViewById(R.id.localvend_address1);
        localvendoraddress.setTextSize(Float.parseFloat(textsize));
        localvendorcity =(EditText)findViewById(R.id.localvend_city);
        localvendorcity.setTextSize(Float.parseFloat(textsize));
        localvendorcountry =(TextView)findViewById(R.id.localvend_countrydesc);
        localvendorcountry.setTextSize(Float.parseFloat(textsize));
        localvendorzip =(EditText)findViewById(R.id.localvend_zip);
        localvendorzip.setTextSize(Float.parseFloat(textsize));
       localvendortele =(EditText)findViewById(R.id.localvend_telephone);
        localvendortele.setTextSize(Float.parseFloat(textsize));
        localvendormobile =(EditText)findViewById(R.id.localvend_mobilenumber);
        localvendormobile.setTextSize(Float.parseFloat(textsize));




        localvendorinventory =(Spinner)findViewById(R.id.localvend_inventory);
        localvendoractive = (Spinner)findViewById(R.id.localvend_active);

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);





        SpinType = getResources().getStringArray(R.array.active_Statusinventory);
        InventoyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SpinType);
        InventoyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localvendorinventory.setAdapter(InventoyActiveType);
        localvendorinventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                InvSpinValue = localvendorinventory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        localvendoractive.setAdapter(adapterActiveType);
        localvendoractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = localvendoractive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try
        {
            mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);



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
                    if( autoCompleteTextView.isPerformingCompletion()) {
                        Log.d( "Debuging", "Performing completion " );
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    localvendorlist = mydb.getAlllocalVendor(userTypedString);

                    if (localvendorlist != null) {
                        if (adapter == null) {
                            adapter = new localvendoradapter(ActivitylocalVendor.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                            adapter.setLocalVendorList(localvendorlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(1);
                        }
                        else
                        {
                            adapter.setLocalVendorList(localvendorlist);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            };
            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d( "Debuging", "On click called " );

                    final DBhelper  mydb=new DBhelper(ActivitylocalVendor.this);

                    localvendor value = (localvendor) parent.getItemAtPosition(pos);
                    localvendorid.setText(value.getLocalvendorid());
                    localvendorname.setText(value.getLocalvendorname());
                    localvendorname.requestFocus();
                    localvendoraddress.setText(value.getLocalvendoraddress());
                    localvendortele.setText(value.getLocalvendortele());
                    localvendorzip.setText(value.getLocalvendorzip());
                    localvendorcontactname.setText(value.getLocalvendorcontactname());
                    localvendormobile.setText(value.getLocalvendormobile());
                    localvendorcity.setText(value.getLocalvendorcity());
                    localvendorcountry.setText(value.getLocalvendorcountry());
                    //localvendorinventory.setText();
                    InvSpinValue=value.getLocalvendorinventory();
                    if (  InvSpinValue.equals("N"))
                    {
                        localvendorinventory.setSelection(0);
                    }
                    if (  InvSpinValue.equals("Y"))
                    {
                        localvendorinventory.setSelection(1);
                    }
                    SpinValue=value.getLocalactive();
                    if (SpinValue.equals("Y"))
                    {
                        localvendoractive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        localvendoractive.setSelection(1);
                    }
                    autoCompleteTextView.setText("");

                }
            });
            Button Update = (Button)findViewById(R.id.localvendor_edit_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);

                    String value = localvendorid.getText().toString();
                    localVendor_Id_To_Update = value;
                    if (localvendorid.getText().toString().matches(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please select Local Vendor", Toast.LENGTH_SHORT).show();

                        return;
                    }
                    if (localvendorname.getText().toString().matches(""))
                    {
                        localvendorname.setError("Please select Vendor Name");
                        return;
                    }
                    if (localvendorcontactname.getText().toString().matches(""))
                    {
                        localvendorcontactname.setError("Please select Vendor Contact Name");
                        return;
                    }

                    mydb.updatelocalVendor(localVendor_Id_To_Update, localvendorname.getText().toString(), localvendorcontactname.getText().toString(),
                            localvendoraddress.getText().toString(), localvendorcountry.getText().toString(), localvendorcity.getText().toString(), localvendormobile.getText().toString(), SpinValue, InvSpinValue, localvendorzip.getText().toString(), localvendortele.getText().toString(),user2.getText().toString());
                    mydb.updatelocalVendorinpurchase(localVendor_Id_To_Update, localvendorname.getText().toString(), localvendoraddress.getText().toString(), localvendorcountry.getText().toString(), localvendorcity.getText().toString(), localvendorzip.getText().toString(), InvSpinValue, SpinValue,user2.getText().toString());

                        Toast.makeText(getApplicationContext(), "Local Vendor Updated", Toast.LENGTH_SHORT).show();
                    updateLocalVendor();
                   // UpdateShell();
                        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                        startActivity(intent);



                }
            });


        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button Clear = (Button) findViewById(R.id.localvendor_clear_button);
        Clear .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                localvendoraddress.setText("");
                localvendorid.setText("");
                localvendorcity.setText("");
                localvendorname.setText("");
                localvendorcontactname.setText("");
                localvendormobile.setText("");
                localvendorcountry.setText("");
                localvendortele.setText("");
                localvendorzip.setText("");
                localvendorinventory.setAdapter(null);
                autoCompleteTextView.setText("");
                localvendoractive.setAdapter(null);
                ActiveType = getResources().getStringArray(R.array.active_Status);
                adapterActiveType = new ArrayAdapter<String>(ActivitylocalVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                InventoyActiveType = new ArrayAdapter<String>(ActivitylocalVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                InventoyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                localvendoractive.setAdapter(adapterActiveType);
                localvendorinventory.setAdapter(InventoyActiveType);
            }
        });

        Button Exit = (Button)findViewById(R.id.localvendor_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                startActivity(intent);
            }
        });

   }


    public  void LocalVendorhelpDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("      LOCAL VENDORS   ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "Users select local vendors option to be able to update the local vendors which are either delivering products or are non-product specifics. The system provides 100 local vendors and the user can request for new local vendors using the System Maintenance \uF0E0 Report a Issue \uF0E0 Master Data.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Search - The user enters the vendor name.\n" +
                "Field Description:\n" +
                "Vendor Id: Id of vendor.\n" +
                "Vendor Name: Name of the vendor.\n" +
                "Vendor Contact Name: Name of the vendor.\n" +
                "Address: Address of vendor.\n" +
                "Country: Country of vendor.\n" +
                "City: city of vendor.\n" +
                "Mobile no.: Mobile number of vendor.\n" +
                "Inventory:\n" +
                "Zip: Pin code of Location of vendor.\n" +
                "Active:Vendor is active 'Y' or in-Active 'N'.\n" +
                "Telephone: Landline number of the vendor.\n" +
                "\n" +
                "Functions:\n" +
                " There are 3 options below:\n" +
                "A. UPDATE - To update the records.\n" +
                "B. CLEAR-    Clear the record from the window and make it available for re-entry. \n" +
                "C. EXIT - Go back to the menu option.\n");
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



        mydb=new DBhelper(ActivitylocalVendor.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivitylocalVendor.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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
            case R.id.action_settings:
                Intent i = new Intent(ActivitylocalVendor.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivitylocalVendor.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                LocalVendorhelpDialog();
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivitylocalVendor.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ActivitylocalVendor.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(ActivitylocalVendor.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivitylocalVendor.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivitylocalVendor.this ,login.class);
        startActivity(intent);
    }



    @Override
    public String toString() {
        return item;
    }






    public void updateLocalVendor() {

        final DBhelper  mydb=new DBhelper(ActivitylocalVendor.this);

        final String  local_vendorname =localvendorname.getText().toString().trim();
        final String   local_vendoraddress= localvendoraddress.getText().toString().trim();
        final String   local_vendortele= localvendortele.getText().toString().trim();
        final String   local_vendorzip=localvendorzip.getText().toString().trim();
        final String  local_vendorcontactname=localvendorcontactname.getText().toString().trim();
        final String  local_vendormobile=localvendormobile.getText().toString().trim();
        final String  local_vendorcity=localvendorcity.getText().toString().trim();
        final String  local_vendorcountry=localvendorcountry.getText().toString().trim();
        final String  local_vendorinventory= localvendorinventory.getSelectedItem().toString();
        final String  local_vendoractive= localvendoractive.getSelectedItem().toString();
        final  String pos_user = user2.getText().toString();
        PersistenceManager.saveStoreId(ActivitylocalVendor.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String  store_id= PersistenceManager.getStoreId(ActivitylocalVendor.this);

        class UpdateVendor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int Success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // loading = ProgressDialog.show(ActivitylocalVendor.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
               // loading.dismiss();
               // Toast.makeText(ActivitylocalVendor.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try{
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.LOCAL_VENDOR_STORE_ID,store_id);
                hashMap.put(Config.LOCAL_VENDOR_ID,localVendor_Id_To_Update);
                    hashMap.put(Config.RETAIL_VEND_DSTR_ID,localVendor_Id_To_Update);

                    hashMap.put(Config.LOCAL_VENDOR_NAME,local_vendorname);
                hashMap.put(Config.LOCAL_VENDOR_CONTACT,local_vendorcontactname);
                hashMap.put(Config.LOCAL_VENDOR_ADDRESS,local_vendoraddress);
                hashMap.put(Config.LOCAL_VENDOR_CITY,local_vendorcity);
                hashMap.put(Config.LOCAL_VENDOR_TELE,local_vendortele);
                hashMap.put(Config.LOCAL_VENDOR_ZIP,local_vendorzip);
                hashMap.put(Config.LOCAL_VENDOR_ACTIVE,local_vendoractive);
                hashMap.put(Config.LOCAL_VENDOR_COUNTRY,local_vendorcountry);
                hashMap.put(Config.LOCAL_VENDOR_MOBILE, local_vendormobile);
                hashMap.put(Config.LOCAL_VENDOR_INVENTORY, local_vendorinventory);
                hashMap.put(Config.Retail_Pos_user,pos_user);


                JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_VENDOR,hashMap);

                Log.d("Login attempt", s.toString());

                // Success tag for json
                Success = s.getInt(TAG_SUCCESS);
                if (Success == 1) {
                    Log.d("Successfully Login!", s.toString());
                    mydb.updateflaglocalvendor(localVendor_Id_To_Update);
                   // return s.getString(TAG_MESSAGE);
                } else {

                   // return s.getString(TAG_MESSAGE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;            }
        }

        UpdateVendor updateVendor = new UpdateVendor();
        updateVendor.execute();
    }

}
