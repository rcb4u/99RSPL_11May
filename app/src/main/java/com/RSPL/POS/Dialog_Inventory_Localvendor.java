package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Adapter.Dialoginventory_adapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.localvendor;


public class Dialog_Inventory_Localvendor extends Activity {

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
    Dialoginventory_adapter adapter;
    String ActiveType[],SpinType[];
    String item;
    String SpinValue,InvSpinValue;

    CustomAlphaNumericKeyboard mCustomKeyboard;
    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;
    DialogKeyboard dialogKeyboard;
    mDialogKeybaord mDialogKeybaord;

    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    // Adapter
    ArrayAdapter<String> adapterActiveType;
    ArrayAdapter<String>InventoyActiveType;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    String retail_localvendor_op = "sh /sdcard/local_vendor.sh";
    String superuser ="su";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__inventory__localvendor);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        username =  login.b.getString("Pos_User");

        localvendorid = (TextView)findViewById(R.id.localvend_vendorid);
        localvendorname= (EditText)findViewById(R.id.localvend_name);
        localvendorcontactname =(EditText)findViewById(R.id.localvend_contactname);
        localvendoraddress = (EditText)findViewById(R.id.localvend_address1);
        localvendorcity =(EditText)findViewById(R.id.localvend_city);
        localvendorcountry =(TextView)findViewById(R.id.localvend_countrydesc);
        localvendorzip =(EditText)findViewById(R.id.localvend_zip);
        localvendortele =(EditText)findViewById(R.id.localvend_telephone);
        localvendormobile =(EditText)findViewById(R.id.localvend_mobilenumber);
        localvendorinventory =(Spinner)findViewById(R.id.localvend_inventory);
        localvendoractive = (Spinner)findViewById(R.id.localvend_active);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        // final AutoCompleteTextView autoCompleteTextView;
       /* actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);*/



        dialogKeyboard = new DialogKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_local_vendor, R.xml.alphanumerickeyboard);

        dialogKeyboard.registerEditText(R.id.localvend_name);






        dialogKeyboard= new DialogKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_local_vendor, R.xml.alphanumerickeyboard );

        dialogKeyboard.registerEditText(R.id.localvend_contactname);




        dialogKeyboard= new DialogKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_local_vendor, R.xml.alphanumerickeyboard );

        dialogKeyboard.registerEditText(R.id.localvend_address1);



        dialogKeyboard= new DialogKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_local_vendor, R.xml.alphanumerickeyboard );

        dialogKeyboard.registerEditText(R.id.localvend_city);



        customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_number1, R.xml.fractional_keyboard);

        customFractionalKeyboard.registerEditText(R.id.localvend_zip);



        localvendortele.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_number2, R.xml.fractional_keyboard);

                customFractionalKeyboard.registerEditText(R.id.localvend_telephone);

                return false;
            }
        });


        customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Inventory_Localvendor.this, R.id.keyboard_number3, R.xml.fractional_keyboard);

        customFractionalKeyboard.registerEditText(R.id.localvend_mobilenumber);



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

            autoCompleteTextView.setDropDownHeight(300);



            mDialogKeybaord= new mDialogKeybaord(Dialog_Inventory_Localvendor.this, R.id.keyboard_local_vendor_auto, R.xml.alphanumerickeyboard );

            mDialogKeybaord.registerEditText(R.id.myautocomplete);

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
                            adapter = new Dialoginventory_adapter(Dialog_Inventory_Localvendor.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                            adapter.setLocalVendorList(localvendorlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(1);
                        } else {

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

                    final DBhelper  mydb=new DBhelper(Dialog_Inventory_Localvendor.this);

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
                            localvendoraddress.getText().toString(), localvendorcountry.getText().toString(), localvendorcity.getText().toString(), localvendormobile.getText().toString(), SpinValue, InvSpinValue, localvendorzip.getText().toString(), localvendortele.getText().toString(),username);
                    mydb.updatelocalVendorinpurchase(localVendor_Id_To_Update, localvendorname.getText().toString(), localvendoraddress.getText().toString(), localvendorcountry.getText().toString(), localvendorcity.getText().toString(), localvendorzip.getText().toString(), InvSpinValue, SpinValue,username);

                    Toast.makeText(getApplicationContext(), "Local Vendor Updated", Toast.LENGTH_SHORT).show();
                    updateLocalVendor();
                    // UpdateShell();
                    Intent intent = new Intent(getApplicationContext(), activity_inventorywithpo.class);
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
                adapterActiveType = new ArrayAdapter<String>(Dialog_Inventory_Localvendor.this,android.R.layout.simple_spinner_item,ActiveType);
                adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                InventoyActiveType = new ArrayAdapter<String>(Dialog_Inventory_Localvendor.this,android.R.layout.simple_spinner_item,ActiveType);
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
                Intent intent = new Intent(getApplicationContext(), activity_inventorywithpo.class);
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
                Intent i = new Intent(Dialog_Inventory_Localvendor.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                LocalVendorhelpDialog();



           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Dialog_Inventory_Localvendor.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(Dialog_Inventory_Localvendor.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Dialog_Inventory_Localvendor.this,ActivitySalesbill.class);
                startActivity(s);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public String toString() {
        return item;
    }






    public void updateLocalVendor() {

        final DBhelper  mydb=new DBhelper(Dialog_Inventory_Localvendor.this);

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
        PersistenceManager.saveStoreId(Dialog_Inventory_Localvendor.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String  store_id= PersistenceManager.getStoreId(Dialog_Inventory_Localvendor.this);

        class UpdateVendor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

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
                    hashMap.put(Config.Retail_Pos_user,username);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_VENDOR,hashMap);

                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updateflaglocalvendor(localVendor_Id_To_Update);


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;            }
        }

        UpdateVendor updateVendor = new UpdateVendor();
        updateVendor.execute();
    }



    public void UpdateShell() {


        class UpdateShell extends AsyncTask<Void, Void, String> {
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
                try {
                    executerlocalvendor(superuser);
                    String s = executerlocalvendor(retail_localvendor_op);
                    Log.e("@@@Script Called", s);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }
        }

        UpdateShell updateShell= new UpdateShell();
        updateShell.execute();

    }


    public String executerlocalvendor(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/local_vendor.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

}
