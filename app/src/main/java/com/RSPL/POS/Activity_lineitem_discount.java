package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

//import Adapter.line_fullproductadapter;
//import Adapter.line_productId_Name_adapter;
//import Adapter.LineItemDiscount_DropDown;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.line_item_Product_Model;

public class Activity_lineitem_discount extends Activity {


    DBhelper mydb;



    ArrayList<line_item_Product_Model> arrayList;

    //line_productId_Name_adapter adapter;
    //line_fullproductadapter lineproductadapter;

    Button submit,clrbtn;
    Spinner mLineIteamSpinner,active;
    EditText  Item_Disc,Item_Id;
    TextView Item_Name;
    ActionBar actionBar;
    ArrayAdapter<String> adapteractiveType;
    //BillLevelAdapter adapter;
    String ActiveType[];


    String SpinValue,store_id;

    public static String username = null;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";


    String retail_lineitem_op = "sh /sdcard/line_item_discount.sh";
    String superuser ="su";

    CustomAlphabatKeyboard customAlphabatKeyboard;
    CustomFractionalKeyboard customFractionalKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_lineitem_discount);

        username =  login.b.getString("Pos_User");



        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        submit = (Button) findViewById(R.id.submit_button);
        clrbtn = (Button) findViewById(R.id.buttonclear);

        Item_Name=(TextView) findViewById(R.id.Line_ITEM_Name);
        Item_Disc=(EditText) findViewById(R.id.Line_Item_Disc);
        Item_Id=(EditText) findViewById(R.id.Line_Item_ID);
        active = (Spinner) findViewById(R.id.editactive);
        mLineIteamSpinner=(Spinner)findViewById(R.id.Line_Item_Spinner);



        //*********************** Custom Keyboard************************************************************//
/*

        Item_Name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                customAlphabatKeyboard = new CustomAlphabatKeyboard(Activity_lineitem_discount.this, R.id.keyboard_line_iteam, R.xml.alphanumerickeyboard);

                customAlphabatKeyboard.registerEditText(R.id.Line_ITEM_Name);



                return false;
            }
        });

*/


        Item_Disc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                customFractionalKeyboard= new CustomFractionalKeyboard(Activity_lineitem_discount.this, R.id.keyboard_line_iteam_number, R.xml.fractional_keyboard );

                customFractionalKeyboard.registerEditText(R.id.Line_Item_Disc);

                return false;
            }
        });







        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf





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



        mydb = new DBhelper(Activity_lineitem_discount.this);

        ArrayList<line_item_Product_Model> reasonReturn = mydb.getAllLineItems();

        ArrayAdapter<line_item_Product_Model> stringArrayAdapter =

                new ArrayAdapter<line_item_Product_Model>(Activity_lineitem_discount.this, android.R.layout.simple_spinner_dropdown_item, reasonReturn);
        mLineIteamSpinner.setAdapter(stringArrayAdapter);

        mLineIteamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mydb = new DBhelper(Activity_lineitem_discount.this);
                line_item_Product_Model value = (line_item_Product_Model) parent.getItemAtPosition(position);

                // ArrayList<VendorRejectModel> alldata = mydb.getReasonforRagister(value);

                Item_Name.setText(value.getProductLinename());
                Item_Disc.setText(value.getDiscountLineitem());
                Item_Id.setText(value.getProductLineId());
                SpinValue=value.getLineActive();
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

/*


        Item_Disc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                customFractionalKeyboard= new CustomFractionalKeyboard(Activity_lineitem_discount.this, R.id.keyboard_line_iteam_number, R.xml.fractional_keyboard );

                customFractionalKeyboard.registerEditText(R.id.Line_Item_Disc);

                return false;
            }
        });

*/







        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                try {
                    if (Item_Name.getText().toString().matches(""))
                    {
                        Item_Name.setError("Please enter name");
                        return;
                    }
                    if (Item_Disc.getText().toString().matches(""))
                    {
                        Item_Disc.setError("Please enter discount");
                        return;
                    }


                    mydb.linediscountitem (Item_Id.getText().toString(),Item_Name.getText().toString(),Item_Disc.getText().toString(),SpinValue,username);
                    //  Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent5 = new Intent(Activity_lineitem_discount.this,ActivityLoyality.class);
                    startActivity(intent5);
                    Updatelineitem();
                    UpdateShell();


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } }

        });


        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                v.startAnimation(Buttonok);
                try{
                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);

                }catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

            }
        });









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
        if (id == R.id.action_settings) {

            Intent i=new Intent(Activity_lineitem_discount.this,ActivityLoyality.class);
            startActivity(i);
            return true;
        }

        if(id==R.id.action_settinginfo) {
            ShowIncentive showIncentive = new ShowIncentive(Activity_lineitem_discount.this);
            showIncentive.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void Updatelineitem() {


        final String lineitemname =  Item_Name.getText().toString();
        final String lineitemid =Item_Id.getText().toString();
        final String lineitemdisc =  Item_Disc.getText().toString();
        final String lineitemspinvalue =  SpinValue.toString();



        PersistenceManager.saveStoreId(Activity_lineitem_discount.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(Activity_lineitem_discount.this);



        class Updatecustomerloyalty extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Lineitem_store_id,store_id);
                    hashMap.put(Config.Line_Item_Name,lineitemname);
                    hashMap.put(Config.Line_Item_Id,lineitemid);
                    hashMap.put(Config.Line_Item_Disc,lineitemdisc);


                    hashMap.put(Config.Retail_Pos_user,username);


                    //   hashMap.put(com.mycompany.apps.Config.Line_Item_spinvalue,lineitemspinvalue);


                    JSONParserSync rh = new JSONParserSync();
                   // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/line_item_discount.jsp", hashMap);
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LINE_ITEM_DISCOUNT, hashMap);
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
                // Toast.makeText(Activity_lineitem_discount.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updatecustomerloyalty updatepointrange = new Updatecustomerloyalty();
        updatepointrange.execute();
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
                    executerloyalityaccural(superuser);
                    String s = executerloyalityaccural(retail_lineitem_op);
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


    public String executerloyalityaccural(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/line_item_discount.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }



}