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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.VendorAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.Vendor;


public class ActivityVendor extends Activity {
    public static String username = null;
/////jimmy/////////////////////////////////////////////////////////////////////////////////////////////////
    TextView tvVendorsearch,tvdstrid,tvdstrname,tvcontactname,tvadress,tvlandline,tvemail,tvcity,tvzip,tvinventory,tvmobileno,tvcountery,tvactive;
///////////////////////////////////////////////////////////////////

    TextView ColumnVendorname,Email,ColumnVendorcontactname,ColumnVendoraddress,ColumnVendorcity,ColumnVendorcountry,ColumnVendorzip,ColumnVendortelephone,ColumnVendormobile,ColumnVendorinventory, ColumnVendorId;
    Spinner ColumnVendoractive;
    AutoCompleteTextView autoCompleteTextView;
    String Vend_Id_To_Update,SpinValue;



    ActionBar actionBar;
    DBhelper mydb;
    TextWatcher mTextWatcher;
    ArrayList<Vendor> vendorlist;
    VendorAdapter adapter;
    String ActiveType[];
    ArrayAdapter<String> adapterActiveType;

    String iteam;
    TextView user2;




    String dstrId;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    String retail_distributor_op = "sh /sdcard/distributors.sh";
    String superuser ="su";
    String backroundcolour,colorchange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_vendor);
        username =  login.b.getString("Pos_User");


        actionBar = getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(ActivityVendor.this);


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


///////////////////////////////////////////////////////jimmy(text size)////////////////////////////////////////////



        tvVendorsearch = (TextView) findViewById(R.id.textView1);
        tvVendorsearch.setTextSize(Float.parseFloat(textsize));
        tvdstrid = (TextView) findViewById(R.id.textViewid);
        tvdstrid.setTextSize(Float.parseFloat(textsize));
        tvdstrname = (TextView) findViewById(R.id.textView3);
        tvdstrname.setTextSize(Float.parseFloat(textsize));
        tvcontactname = (TextView) findViewById(R.id.textView4);
        tvcontactname.setTextSize(Float.parseFloat(textsize));
        tvadress = (TextView) findViewById(R.id.textView5);
        tvadress.setTextSize(Float.parseFloat(textsize));
        tvlandline = (TextView) findViewById(R.id.textView6);
        tvlandline.setTextSize(Float.parseFloat(textsize));
        tvemail = (TextView) findViewById(R.id.EmailText);
        tvemail.setTextSize(Float.parseFloat(textsize));
        tvcity = (TextView) findViewById(R.id.textView7);
        tvcity.setTextSize(Float.parseFloat(textsize));
        tvzip = (TextView) findViewById(R.id.textView8);
        tvzip.setTextSize(Float.parseFloat(textsize));
        tvinventory = (TextView) findViewById(R.id.textView9);
        tvinventory.setTextSize(Float.parseFloat(textsize));
        tvmobileno = (TextView) findViewById(R.id.textView10);
        tvmobileno.setTextSize(Float.parseFloat(textsize));
        tvcontactname = (TextView) findViewById(R.id.textView12);
        tvcontactname.setTextSize(Float.parseFloat(textsize));
        tvactive = (TextView) findViewById(R.id.textViewactive);
        tvactive.setTextSize(Float.parseFloat(textsize));




        ColumnVendorname = (TextView) findViewById(R.id.Vendor_Name);
        ColumnVendorname.setTextSize(Float.parseFloat(textsize));
        ColumnVendorcontactname = (TextView) findViewById(R.id.Vendor_Contact_Name);
        ColumnVendorcontactname.setTextSize(Float.parseFloat(textsize));
        ColumnVendoraddress = (TextView) findViewById(R.id.Vendor_Address);
        ColumnVendoraddress.setTextSize(Float.parseFloat(textsize));
        ColumnVendorcity = (TextView) findViewById(R.id.Vendor_City);
        ColumnVendorcity.setTextSize(Float.parseFloat(textsize));
        ColumnVendorcountry = (TextView) findViewById(R.id.Vendor_Country);
        ColumnVendorcountry.setTextSize(Float.parseFloat(textsize));
        ColumnVendorzip = (TextView) findViewById(R.id.Vendor_Zip);
        ColumnVendorzip.setTextSize(Float.parseFloat(textsize));
        ColumnVendortelephone = (TextView) findViewById(R.id.Vendor_Landline);
        ColumnVendortelephone.setTextSize(Float.parseFloat(textsize));
        ColumnVendormobile = (TextView) findViewById(R.id.Vendor_Mobile);
        ColumnVendormobile.setTextSize(Float.parseFloat(textsize));
        ColumnVendorinventory = (TextView) findViewById(R.id.Vendor_Inventory);
        ColumnVendorinventory.setTextSize(Float.parseFloat(textsize));
        ColumnVendoractive = (Spinner) findViewById(R.id.Vendor_Active);

        ColumnVendorId = (TextView) findViewById(R.id.Vendor_Id);
        ColumnVendorId.setTextSize(Float.parseFloat(textsize));
        Email=(TextView)findViewById(R.id.EmailProd);
        Email.setTextSize(Float.parseFloat(textsize));


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ColumnVendoractive.setAdapter(adapterActiveType);
        ColumnVendoractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = ColumnVendoractive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        try {
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
                    vendorlist = mydb.getAllVendor(userTypedString);

                    if (vendorlist != null) {
                        if (adapter == null) {
                            adapter = new VendorAdapter(ActivityVendor.this, android.R.layout.simple_dropdown_item_1line, vendorlist);
                            adapter.setVendorList(vendorlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(1);
                        } else {

                            adapter.setVendorList(vendorlist);
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

                    final DBhelper mydb = new DBhelper(ActivityVendor.this);

                    Vendor value = (Vendor) parent.getItemAtPosition(pos);
                    ColumnVendorId.setText(value.getVendorId());
                    ColumnVendorname.setText(value.getVendorname());
                    ColumnVendoraddress.setText(value.getAddress());
                    ColumnVendorcountry.setText(value.getCountry());
                    ColumnVendorcontactname.setText(value.getVendorContact());
                    ColumnVendorcity.setText(value.getCity());
                    ColumnVendorzip.setText(value.getZip());
                    ColumnVendormobile.setText(value.getMobile());
                    ColumnVendortelephone.setText(value.getTelephone());
                    ColumnVendorinventory.setText(value.getVendorInventory());
                    Email.setText(value.getEmail());

                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        ColumnVendoractive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        ColumnVendoractive.setSelection(1);
                    }
                    ColumnVendoractive.requestFocus();
                    autoCompleteTextView.setText("");


                }
            });

            Button Clear = (Button) findViewById(R.id.vendor_clear_button);
            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(Buttonok);
                    ColumnVendorinventory.setText("");
                    ColumnVendorname.setText("");
                    ColumnVendoraddress.setText("");
                    ColumnVendorcity.setText("");
                    ColumnVendortelephone.setText("");
                    ColumnVendorzip.setText("");
                    ColumnVendorcontactname.setText("");
                    ColumnVendorcountry.setText("");
                    ColumnVendormobile.setText("");
                    ColumnVendoractive.setAdapter(null);
                    ActiveType = getResources().getStringArray(R.array.active_Status);
                    adapterActiveType = new ArrayAdapter<String>(ActivityVendor.this,android.R.layout.simple_spinner_item,ActiveType);
                    adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ColumnVendoractive.setAdapter(adapterActiveType);
                    ColumnVendorId.setText("");
                    autoCompleteTextView.setText("");
                }
            });

            Button Exit = (Button) findViewById(R.id.vendor_exit_button);
            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);
                }
            });

            Button Update = (Button) findViewById(R.id.vendor_update_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);
                    /*ColumnVendorId = (TextView) findViewById(R.id.Vendor_Id);
                    ColumnVendoractive = (EditText) findViewById(R.id.Vendor_Active);*/
                    String value = ColumnVendorId.getText().toString();
                    Vend_Id_To_Update = value;
                    if (ColumnVendorname.getText().toString().matches("") || ColumnVendorId.getText().toString().matches(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please select Distributor Name", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(mydb.updateVendor(Vend_Id_To_Update,SpinValue,user2.getText().toString())&&
                            mydb.updateVendorinpurchase(Vend_Id_To_Update,SpinValue,user2.getText().toString()));
                    {
try {

    Toast.makeText(getApplicationContext(), "Distributor Updated", Toast.LENGTH_SHORT).show();
    UpdateVendor();
    //  UpdateShell();
    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
    startActivity(intent);

}catch (Exception e){}

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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



        mydb=new DBhelper(ActivityVendor.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityVendor.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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


        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(ActivityVendor.this,Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityVendor.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                Disrtibuterhelpdialog();
                return true;
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityVendor.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;


         /*   case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityVendor.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityVendor.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityVendor.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityVendor.this ,login.class);
        startActivity(intent);
    }

    public  void Disrtibuterhelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                DISTRIBUTORS      ");
        alertDialog.setMessage("\n" +
                "The distribuor is centralised and delivers the products to the store. The retailer at the time of installation or later can select distrbutors that are acive for him. This would reduce the flow of the data in the purchasing. All distributors are inventory related and hence are available during direct payments.\n" +
                "\n" +
                "Objectives:\n" +
                "\n" +
                "The user selects the distributors option to make either the distributor active or inactive for the store.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Search - The user enters the distributor’s name.\n" +
                "Fields Description:\n" +
                "\n" +
                "Distributor Id\n" +
                "Distributors Name: Name of the distributor.\n" +
                "Contact Name: Contact name of the disrtributor .\n" +
                "Address: Address of the disrtibutor.\n" +
                "Landline: Landline number of the disributors.\n" +
                "Email: Email id of the distributor.\n" +
                "City: City of distributor.\n" +
                "Zip: Pin code of distributor’s city.\n" +
                "Inventory: Inventory detail of the customer.\n" +
                "Mobile no: Mobile number of the distributor.\n" +
                "Country: Living country of distributor.\n" +
                "Active: Distributor is active 'Y' or in-Active 'N'.\n" +
                "\n" +
                "Functions: \n" +
                "There are 3 options below:\n" +
                " A. UPDATE - To update the record.\n" +
                " B. CLEAR- Clear the record from the window and make it available for re-entry.\n" +
                " C. EXIT - Go back to the menu option.\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }



    public void UpdateVendor() {

        final DBhelper mydb = new DBhelper(ActivityVendor.this);

        final String  active =ColumnVendoractive.getSelectedItem().toString();
        PersistenceManager.saveStoreId(ActivityVendor.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String   store_id= PersistenceManager.getStoreId(ActivityVendor.this);
        final String pos_user = user2.getText().toString();
        dstrId = ColumnVendorId.getText().toString();
        class UpdateVendor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityVendor.this, "UPDATING...", "Wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();
                // Toast.makeText(ActivityVendor.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.RETAIL_STR_DSTR_STORE_ID,store_id);
                    hashMap.put(Config.RETAIL_STR_DSTR_ACTIVE,active);
                    hashMap.put(Config.RETAIL_STR_DSTR_ID,dstrId);
                    hashMap.put(Config.RETAIL_VEND_DSTR_ID,dstrId);



                    hashMap.put(Config.Retail_Pos_user,pos_user);



                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_VENDOR,hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updatedistributorflag(dstrId);



                        //   return s.getString(TAG_MESSAGE);
                    } else {

                        //  return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;            }
        }
        UpdateVendor updatevendor = new UpdateVendor();
        updatevendor.execute();
    }



}
