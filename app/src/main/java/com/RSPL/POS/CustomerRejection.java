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

import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.CustomerRejectModel;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;


public class CustomerRejection extends Activity {

    TextView storeid;
    Spinner mreasone;
    EditText mVID;
    EditText editVReason;


    Spinner active;
    Button clear;
    Button update;
    DBhelper mydb;
    String Update;
    String Update2;
    static TextView res;
    String positions,store_id;
    String iteam;
    TextView user2;

    ActionBar actionBar;
    //POS USER ADD
    public static String username = null;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";




    ArrayList<CustomerRejectModel> arrayList;

    // Data Source

    String ActiveType[];

    // Adapter
    ArrayAdapter<String> adapteractiveType;
    String SpinValue;
    Bundle syncDataBundle = null;


    TextView tvreason,tvreasonofrejection,tvactive;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_rejection);
        //POS USER ADD
        username =  login.b.getString("Pos_User");





        mydb = new DBhelper(this);

        active = (Spinner) findViewById(R.id.editCactive);


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


        /////////////////////////////jimmmy.///////////////////////////////

        tvreason = (TextView) findViewById(R.id.textViewsno);
        tvreason.setTextSize(Float.parseFloat(textsize));

        tvreasonofrejection = (TextView) findViewById(R.id.textviewcardtype);
        tvreasonofrejection.setTextSize(Float.parseFloat(textsize));
        tvactive = (TextView) findViewById(R.id.textvieactive);
        tvactive.setTextSize(Float.parseFloat(textsize));

        editVReason = (EditText) findViewById(R.id.editCReason);
        editVReason.setTextSize(Float.parseFloat(textsize));



        update = (Button) findViewById(R.id.buttonCupdate);
        clear = (Button) findViewById(R.id.buttonCclear);
        mreasone = (Spinner) findViewById(R.id.customer_reason);
        mVID = (EditText) findViewById(R.id.vendor_id);

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);




        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapteractiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapteractiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        active.setAdapter(adapteractiveType);
        active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = active.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       /* Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("Store_Id");
            Cursor rs = mydb.getdatastore(Value);

            rs.moveToFirst();

            String editstoreid = rs.getString(rs.getColumnIndex(DBhelper.STORE_ID_STORE));
            storeid.setText(editstoreid);

        }*/

        mydb = new DBhelper(CustomerRejection.this);
        ArrayList<CustomerRejectModel> reasonReturn = mydb.getCRejectReason();

        ArrayAdapter<CustomerRejectModel> stringArrayAdapter =

                new ArrayAdapter<CustomerRejectModel>(CustomerRejection.this, android.R.layout.simple_spinner_dropdown_item, reasonReturn);
        mreasone.setAdapter(stringArrayAdapter);

        mreasone.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mydb = new DBhelper(CustomerRejection.this);
                CustomerRejectModel value = (CustomerRejectModel) parent.getItemAtPosition(position);
                // String Value = parent.getSelectedItem().toString();
                //  ArrayList<CustomerRejectModel> alldata = mydb.getCustomerReasonforRagister(Value);

                editVReason.setText(value.getmVReason());
                mVID.setText(value.getmVId());
                SpinValue=value.getActive();
                if (SpinValue.equals("Y"))
                {
                    active.setSelection(0);
                }
                if (SpinValue.equals("N"))
                {
                    active.setSelection(1);
                }

               /* // active.setText(alldata.get(0).getActive());
                SpinValue = alldata.get(0).getActive();
                if (SpinValue.equals("Y")) {
                    active.setSelection(0);
                }
                if (SpinValue.equals("N")) {
                    active.setSelection(1);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                try{
                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);*/
            }
        });


        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                try{


                    if (editVReason.getText().toString().matches(""))
                    {
                        editVReason.setError("Please enter reason");
                        return;
                    }

                    // Update = Value;



                    mydb.updatemCustomerRejectReason(mVID.getText().toString(), editVReason.getText().toString(), SpinValue,user2.getText().toString());
                    Updatecustomerjection();


                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);

                    // Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();




                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    public static String sha256(String base) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            res.setText("SHA-256 hash generated is: " + hexString.toString().toUpperCase());
            Log.d("hhh", hexString.toString());
            return hexString.toString();


        } catch (Exception ex) {
            throw new RuntimeException(ex);
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



        mydb=new DBhelper(CustomerRejection.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(CustomerRejection.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:

                Intent i=new Intent(CustomerRejection.this,ActivityLoyality.class);
                startActivity(i);

                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(CustomerRejection.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                help_Customer_Reason_for_Rejection();


                return true;
            case R.id.action_Masterscn1:
                Intent p = new Intent(CustomerRejection.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(CustomerRejection.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_settinginv:
                Intent in=new Intent(CustomerRejection.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(CustomerRejection.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(CustomerRejection.this ,login.class);
        startActivity(intent);
    }


    public void Updatecustomerjection() {



        final String customerrejectionid =  mVID.getText().toString();
        final String customerrejectionreason =editVReason.getText().toString();
        final String customerrejectionspinvalue =  SpinValue.toString();
        final String pos_user = user2.getText().toString();


        PersistenceManager.saveStoreId(CustomerRejection.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(CustomerRejection.this);



        class Updatecustomerrejection extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
try{
                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put(Config.Customer_storeid,store_id);
                hashMap.put(Config.Customer_id,customerrejectionid);
                hashMap.put(Config.Customer_reason,customerrejectionreason);
                hashMap.put(Config.Customer_spinvalue,customerrejectionspinvalue);
                hashMap.put(Config.Retail_Pos_user,pos_user);





                JSONParserSync rh = new JSONParserSync();
    JSONObject s = rh.sendPostRequest(Config.CUST_REJECT, hashMap);
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
               // Toast.makeText(CustomerRejection.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updatecustomerrejection updatepointrange = new Updatecustomerrejection();
        updatepointrange.execute();
    }






    public void help_Customer_Reason_for_Rejection() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("               CUSTOMER REASON FOR REJECTION");
        alertDialog.setMessage(" \n" +
                        "Objective:\n" +
                        "\t\n" +
                        "There are 10 resons provided why customer can reject or return a product. The store personnel can update the same.\n" +

                        "\n"
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


