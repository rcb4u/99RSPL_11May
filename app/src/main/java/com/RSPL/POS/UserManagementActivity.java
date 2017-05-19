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


public class UserManagementActivity extends Activity {

    TextView storeid;
    EditText firstname,lastname,password,confirmpassword;
    Spinner username, active;
    Button cancel,update;

    DBhelper mydb;
    String Update,Update2;
    String iteam;
    TextView user2;
    String token;
  /////////////////////////////jimmy()size//////////////////////////////////////////////

    TextView tvstoreid,tvusername,tvfirstname,tvlastname,tvpassword,tvconfirmpassword,tvactive;



    static TextView res;
    ActionBar actionBar;
    public static String username1 = null;


    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    ArrayList<Registeremployeesmodel> arrayList;

    String ActiveType[];

    // Adapter
    ArrayAdapter<String> adapteractiveType;
    String SpinValue;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        username1 =  login.b.getString("Pos_User");


        mydb = new DBhelper(UserManagementActivity.this);
        Decimal valuetextsize =
                mydb.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();
     token = SharedPrefManager.getInstance(UserManagementActivity.this).getDeviceToken();


         //getDeviceToken()     Log.e("ttoken",token);




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
        actionBar = getActionBar();
        //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


////////////////////////////////////jimmmy///////////////////////////////////////////




        ArrayList<String> updatelist3 = mydb.getdataemp();
        String Store_Id_Search = updatelist3.get(0);
        final Bundle databundle = new Bundle();
        databundle.putString("Store_Id", Store_Id_Search);
        Intent i = getIntent();
        i.putExtras(databundle);
        //////////////////////////////////////jimmy../////////////////////////////////////////////

        tvstoreid=(TextView)findViewById(R.id.textViewstoreid);
        tvstoreid.setTextSize(Float.parseFloat(textsize));

        tvusername=(TextView)findViewById(R.id.textViewsno);
        tvusername.setTextSize(Float.parseFloat(textsize));
        tvfirstname=(TextView)findViewById(R.id.textviewcardtype);
        tvfirstname.setTextSize(Float.parseFloat(textsize));
        tvlastname=(TextView)findViewById(R.id.textview);
        tvlastname.setTextSize(Float.parseFloat(textsize));
        tvpassword=(TextView)findViewById(R.id.textviewpertonpoint);
        tvpassword.setTextSize(Float.parseFloat(textsize));
        tvconfirmpassword=(TextView)findViewById(R.id.textviewbasevalue);
        tvconfirmpassword.setTextSize(Float.parseFloat(textsize));
        tvactive=(TextView)findViewById(R.id.textvieactive);
        tvactive.setTextSize(Float.parseFloat(textsize));



        storeid=(TextView)findViewById(R.id.editstoreid);
        storeid.setTextSize(Float.parseFloat(textsize));
        firstname=(EditText)findViewById(R.id.editfirstname);
        firstname.setTextSize(Float.parseFloat(textsize));
        lastname=(EditText)findViewById(R.id.editlastname);
        lastname.setTextSize(Float.parseFloat(textsize));
        password=(EditText)findViewById(R.id.editpassword);
        password.setTextSize(Float.parseFloat(textsize));
        confirmpassword=(EditText)findViewById(R.id.editconfrmpassword);
        confirmpassword.setTextSize(Float.parseFloat(textsize));

        username=(Spinner)findViewById(R.id.username);
        active=(Spinner)findViewById(R.id.editactive);




        cancel=(Button)findViewById(R.id.buttoncancl);
        update=(Button)findViewById(R.id.buttonupdate);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapteractiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
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
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String Value = extras.getString("Store_Id");
            Cursor rs = mydb.getdatastore(Value);

            rs.moveToFirst();

            String editstoreid = rs.getString(rs.getColumnIndex(DBhelper.STORE_ID_STORE));
            storeid.setText(editstoreid);

        }

         mydb=new DBhelper(UserManagementActivity.this);
        ArrayList<Registeremployeesmodel> reasonReturn= mydb.getusername();
        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(UserManagementActivity.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
        username.setAdapter(stringArrayAdapter);

        username.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Registeremployeesmodel value = (Registeremployeesmodel) parent.getItemAtPosition(position);
                String Value = parent.getSelectedItem().toString();
                ArrayList<Registeremployeesmodel> alldata = mydb.getdatafoeregister(Value);
                firstname.setText(alldata.get(0).getFirstname());
                lastname.setText(alldata.get(0).getLastname());
                password.setText(alldata.get(0).getPassword());
                confirmpassword.setText(alldata.get(0).getConfirmpassword());
               // active.setText(alldata.get(0).getActive());
                SpinValue=alldata.get(0).getActive();
                if (SpinValue.equals("Y"))
                {
                    active.setSelection(0);
                }
                if (SpinValue.equals("N"))
                {
                    active.setSelection(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       cancel.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);



                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);
            }
       });





        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                try {

                    if (firstname.getText().toString().matches("")) {
                        firstname.setError("Please enter first name");
                        return;
                    }

                    if (lastname.getText().toString().matches("")) {
                        lastname.setError("Please enter last name");
                        return;
                    }

                    if (password.getText().toString().matches("")) {
                        password.setError("Please enter password");
                        return;
                    }

                    if (confirmpassword.getText().toString().matches("")) {
                        confirmpassword.setError("Please confirm password");
                        return;
                    }

                    if (confirmpassword.getText().toString().matches("") && password.getText().toString().matches("")) {
                        confirmpassword.setError("Please fill password");
                    }


                    Bundle extras = getIntent().getExtras();
                    if (extras != null) {
                        username = (Spinner) findViewById(R.id.username);

                        String Value = extras.getString("Store_Id");
                        String Value2 = username.getSelectedItem().toString();
                        Update = Value;
                        Update2 = Value2;
                        String usrnames = firstname.getText().toString().concat(lastname.getText().toString());



                        if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                            confirmpassword.setError("Passwords do not match");
                            return;

                        }
                        mydb.updateemployees(Update, Update2, firstname.getText().toString(), lastname.getText().toString(), password.getText().toString(), confirmpassword.getText().toString(), SpinValue,user2.getText().toString(),usrnames);
                        Updateusermanagement();


                        Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "updated", Toast.LENGTH_SHORT).show();


                    }



   } catch (NullPointerException e) {
       e.printStackTrace();
   } catch (Exception e) {
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



        mydb=new DBhelper(UserManagementActivity.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(UserManagementActivity.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

                    user2.setText(username1);
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
            case R.id.action_settings:

                Intent i=new Intent(UserManagementActivity.this,ActivityLoyality.class);
                startActivity(i);

                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(UserManagementActivity.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_user_management();
                return true;

            case R.id.action_Masterscn1:
                Intent p = new Intent(UserManagementActivity.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;


            case R.id.action_settinginv:
                Intent in=new Intent(UserManagementActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(UserManagementActivity.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(UserManagementActivity.this ,login.class);
        startActivity(intent);
    }
    public void Updateusermanagement() {



        final String usermanagementusername =  Update2.toString();
        final String usermanagementstoreid =Update.toString();
        final String usermanagementfirstname =  firstname.getText().toString();
        final String usermanagementlastname =lastname.getText().toString();
        final String usermanagementpassword =password.getText().toString();
        final String pos_user = user2.getText().toString();

        final String usermanagementconfirmpassword =confirmpassword.getText().toString();


        final String usermanagementactive =SpinValue.toString();



        class Updateuser extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put(Config.user_management_store_id,usermanagementstoreid);
                hashMap.put(Config.user_management_username,usermanagementusername);
                hashMap.put(Config.user_management_firstname,usermanagementfirstname);
                hashMap.put(Config.user_management_lastnamee,usermanagementlastname);
                hashMap.put(Config.user_management_password,usermanagementpassword);
                hashMap.put(Config.user_management_confrmpassword,usermanagementconfirmpassword);
                hashMap.put(Config.user_management_active,usermanagementactive);
                hashMap.put(Config.Retail_Pos_user,pos_user);



                JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.USER_MANAGEMENT, hashMap);
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
              //  Toast.makeText(UserManagementActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updateuser updatename = new Updateuser();
        updatename.execute();
    }








    public void help_user_management() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("               USER MANAGEMENT");
        alertDialog.setMessage(" \n" +
                        "Objective:\n" +
                        "\t\n" +
                        "There are 10 users provided in the system. The users are for store personnel and all transactions are being tracked at store level which transaction is done by whom.\n" +

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


