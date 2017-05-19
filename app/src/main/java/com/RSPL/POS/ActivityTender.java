package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;


public class ActivityTender extends Activity implements TextWatcher {

    ActionBar actionBar;
    TextView Posdate;
    TextView Systemdate;
    Button btn;
    EditText Text1,Text2,Text3,Text4,Text5,Text6,Text7,Text8,Text9,Text10,Text11;
    TextView ResultField5000,ResultField2000,ResultField1000,ResultField500,ResultField100,ResultField50,ResultField20,ResultField10,ResultField5,ResultField2,ResultField1;
    TextView TotalResult;
    EditText posdate;
    String iteam;
    TextView user2;
    //POS USER ADD
    public static String username = null;



    String Trans_Id_Update;
    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    DBhelper  mydb=new DBhelper(this);
    String retail_dayclose_op = "sh /sdcard/day_close.sh";
    String superuser ="su";


    TextView tv5000,tv2000,tv1000,tv500,tv100,tv50,tv20,tv10,tv5,tv2,tv1,tvsystemdate,tvposdate,tvtotal;

    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_tender);
        //POS USER ADD
        username =  login.b.getString("Pos_User");


        actionBar = getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        mydb = new DBhelper(ActivityTender.this);
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





        tv1 = (TextView) findViewById(R.id.textView10);
        tv1.setTextSize(Float.parseFloat(textsize));

        tv2 = (TextView) findViewById(R.id.textView9);
        tv2.setTextSize(Float.parseFloat(textsize));

        tv5 = (TextView) findViewById(R.id.textView8);
        tv5.setTextSize(Float.parseFloat(textsize));

        tv10 = (TextView) findViewById(R.id.textView7);
        tv10.setTextSize(Float.parseFloat(textsize));

        tv20 = (TextView) findViewById(R.id.textView20);
        tv20.setTextSize(Float.parseFloat(textsize));

        tv50 = (TextView) findViewById(R.id.textView5);
        tv50.setTextSize(Float.parseFloat(textsize));

        tv100 = (TextView) findViewById(R.id.textView4);
        tv100.setTextSize(Float.parseFloat(textsize));

        tv500 = (TextView) findViewById(R.id.textView3);
        tv500.setTextSize(Float.parseFloat(textsize));

        tv1000 = (TextView) findViewById(R.id.textView1000);
        tv1000.setTextSize(Float.parseFloat(textsize));



        tv2000 = (TextView) findViewById(R.id.textView2000);
        tv2000.setTextSize(Float.parseFloat(textsize));

        tv5000 = (TextView) findViewById(R.id.textView5000);
        tv5000.setTextSize(Float.parseFloat(textsize));

        tvsystemdate = (TextView) findViewById(R.id.textView1);
        tvsystemdate.setTextSize(Float.parseFloat(textsize));

        tvposdate = (TextView) findViewById(R.id.textViewpo);
        tvposdate.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) findViewById(R.id.textView11);
        tvtotal.setTextSize(Float.parseFloat(textsize));







        ResultField5000 = (TextView)findViewById(R.id.editTextres5000);
        ResultField5000.setTextSize(Float.parseFloat(textsize));
        ResultField2000 = (TextView)findViewById(R.id.editTextres2000);
        ResultField2000.setTextSize(Float.parseFloat(textsize));
        ResultField1000 = (TextView)findViewById(R.id.editTextres1000);
        ResultField1000.setTextSize(Float.parseFloat(textsize));
        ResultField500=(TextView)findViewById(R.id.editTextres500);
        ResultField500.setTextSize(Float.parseFloat(textsize));
        ResultField100=(TextView)findViewById(R.id.editTextres100);
        ResultField100.setTextSize(Float.parseFloat(textsize));
        ResultField50=(TextView)findViewById(R.id.editTextres50);
        ResultField50.setTextSize(Float.parseFloat(textsize));
        ResultField20 = (TextView)findViewById(R.id.editTextres20);
        ResultField20.setTextSize(Float.parseFloat(textsize));
        ResultField10=(TextView)findViewById(R.id.editTextres10);
        ResultField10.setTextSize(Float.parseFloat(textsize));
        ResultField5=(TextView)findViewById(R.id.editTextres5);
        ResultField5.setTextSize(Float.parseFloat(textsize));
        ResultField2=(TextView)findViewById(R.id.editTextres2);
        ResultField2.setTextSize(Float.parseFloat(textsize));
        ResultField1=(TextView)findViewById(R.id.editTextres1);
        ResultField1.setTextSize(Float.parseFloat(textsize));
        posdate = (EditText)findViewById(R.id.posdate);
        posdate.setTextSize(Float.parseFloat(textsize));
        Systemdate = (TextView)findViewById(R.id.systemdate);
        Systemdate.setTextSize(Float.parseFloat(textsize));
        Posdate = (TextView)findViewById(R.id.posdateno);
        Posdate.setTextSize(Float.parseFloat(textsize));
        TotalResult=(TextView)findViewById(R.id.Totalresult);
        TotalResult.setTextSize(Float.parseFloat(textsize));
        final Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd ", Locale.getDefault());
        Systemdate.setText(dateFormat.format(date));
        Posdate.setText(dateFormat.format(date));



        // editTextres1 = (EditText) findViewById(R.id.editText1000);
        Text1 = (EditText)findViewById(R.id.editText1000);
        Text1.addTextChangedListener(this);

        Text2=(EditText)findViewById(R.id.editText500);
        Text2.addTextChangedListener(this);

        Text3=(EditText)findViewById(R.id.editText100);
        Text3.addTextChangedListener(this);

        Text4=(EditText)findViewById(R.id.editText50);
        Text4.addTextChangedListener(this);

        Text5=(EditText)findViewById(R.id.editText10);
        Text5.addTextChangedListener(this);

        Text6=(EditText)findViewById(R.id.editText5);
        Text6.addTextChangedListener(this);

        Text7=(EditText)findViewById(R.id.editText2);
        Text7.addTextChangedListener(this);

        Text8=(EditText)findViewById(R.id.editText1);
        Text8.addTextChangedListener(this);

        Text9=(EditText)findViewById(R.id.editText5000);
        Text9.addTextChangedListener(this);

        Text10=(EditText)findViewById(R.id.editText2000);
        Text10.addTextChangedListener(this);

        Text11=(EditText)findViewById(R.id.editText20);
        Text11.addTextChangedListener(this);








        final DBhelper  mydb=new DBhelper(this);
        Cursor cs = mydb.gettender();
        if(cs.moveToFirst())
        {
            do {
                String localprodstore = cs.getString(cs.getColumnIndex(DBhelper.TRANSACTIONID));
                posdate.setText(localprodstore);
            }while (cs.moveToNext());
        }

        Button closeday = (Button)findViewById(R.id.tender_closeday_button);
        closeday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                posdate = (EditText) findViewById(R.id.posdate);
                TotalResult = (TextView) findViewById(R.id.Totalresult);
                String value = posdate.getText().toString();
                Trans_Id_Update = value;
                if (TotalResult.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please fill the Field", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mydb.CheckTenderDateAlreadyInDBorNot(value))

                {
                    Toast.makeText(getApplicationContext(), "DAY IS ALREADY CLOSED", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( mydb.updatedayclose(Trans_Id_Update, TotalResult.getText().toString(),user2.getText().toString()))
                    Toast.makeText(getApplicationContext(), "DAY CLOSE", Toast.LENGTH_SHORT).show();
                updatetender();
                UpdateShell();
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);


            }
        });



        Button Exit = (Button) findViewById(R.id.tender_exit_button);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);
            }
        });

    }






    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        calcResult();

    }


    private void calcResult() throws NumberFormatException {
        Editable editableText1 = Text1.getText();
        Editable editableText2=Text2.getText();
        Editable editableText3=Text3.getText();
        Editable editableText4=Text4.getText();
        Editable editableText5=Text5.getText();
        Editable editableText6=Text6.getText();
        Editable editableText7=Text7.getText();
        Editable editableText8=Text8.getText();
        Editable editableText9=Text9.getText();
        Editable editableText10=Text10.getText();
        Editable editableText11=Text11.getText();




        // editableText2 = Text2.getText();

        double value5000 = 5000.0,value2000 = 2000.0, value1000 = 1000.0,value500=500.0,value100=100.0,value50=50.0,value20 = 20.0,value10=10.0,value05=5.0,value02=2.0,
                value01=1.0,
                value1 = 0.0,value2=0.0,value3=0.0,value4=0.0,value5=0.0,value6=0.0,value7=0.0,value8=0.0,value9=0.0,values10=0.0,value11=0.0,
                result1,result2,result3,result4,result5,result6,result7,result8,result9,result10,result11;

        double total;

        if (editableText1 != null && editableText1.length() >= 1)
            value1 = Double.parseDouble(editableText1.toString());

        if (editableText2 != null && editableText2.length() >= 1)
            value2 = Double.parseDouble(editableText2.toString());

        if (editableText3 != null && editableText3.length() >= 1)
            value3 = Double.parseDouble(editableText3.toString());

        if (editableText4!= null && editableText4.length() >= 1)
            value4 = Double.parseDouble(editableText4.toString());

        if (editableText5 != null && editableText5.length() >= 1)
            value5 = Double.parseDouble(editableText5.toString());

        if (editableText6!= null && editableText6.length() >= 1)
            value6= Double.parseDouble(editableText6.toString());

        if (editableText7!= null && editableText7.length() >= 1)
            value7 = Double.parseDouble(editableText7.toString());

        if (editableText8!= null && editableText8.length() >= 1)
            value8= Double.parseDouble(editableText8.toString());

        if (editableText9!= null && editableText9.length() >= 1)
            value9= Double.parseDouble(editableText9.toString());

        if (editableText10!= null && editableText10.length() >= 1)
            values10= Double.parseDouble(editableText10.toString());

        if (editableText11!= null && editableText11.length() >= 1)
            value11= Double.parseDouble(editableText11.toString());


        DecimalFormat f= new DecimalFormat("##.0");

        // Whatever your magic formula is

        result1 = value1000 * value1;
        ResultField1000.setText((Double.toString(result1)));

        result2=value500*value2;
        ResultField500.setText((Double.toString(result2)));

        result3=value100*value3;
        ResultField100.setText((Double.toString(result3)));

        result4=value50*value4;
        ResultField50.setText((Double.toString(result4)));

        result5=value10*value5;
        ResultField10.setText((Double.toString(result5)));

        result6=value05*value6;
        ResultField5.setText((Double.toString(result6)));

        result7=value02*value7;
        ResultField2.setText((Double.toString(result7)));

        result8=value01*value8;
        ResultField1.setText((Double.toString(result8)));

        result9 = value5000 * value9;
        ResultField5000.setText((Double.toString(result9)));

        result10 = value2000 * values10;
        ResultField2000.setText((Double.toString(result10)));

        result11 = value20 * value11;
        ResultField20.setText((Double.toString(result11)));




        total=result1+result2+result3+result4+result5+result6+result7+result8+result9+result10+result11;
        TotalResult.setText((Double.toString(total)));


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



        mydb=new DBhelper(ActivityTender.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityTender.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

        } catch (NullPointerException e) {
            e.printStackTrace();
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

            Intent i=new Intent(ActivityTender.this,ActivitySales.class);
            startActivity(i);
            return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityTender.this);
                showIncentive.show();
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityTender.this,PurchaseActivity.class);
                startActivity(p);
                return true;

*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityTender.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in=new Intent(ActivityTender.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityTender.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityTender.this ,login.class);
        startActivity(intent);
    }



    public  void updatetender()
    {

        final String dayclosetotalresult = TotalResult.getText().toString();
        final String  daycloseposdate=posdate.getText().toString();
        final String pos_user = user2.getText().toString();




        class Updatedayclose extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.day_close_cb_cash,dayclosetotalresult);
                    hashMap.put(Config.day_close_flag,"0");
                    hashMap.put(Config.day_close_tri_id,daycloseposdate);
                    hashMap.put(Config.Retail_Pos_user,pos_user);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Day_Close_Json_Upload.jsp", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        mydb.updatesalesflagdayopenclose(daycloseposdate);

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
                // Toast.makeText(ActivityTender.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updatedayclose updatedayclose = new Updatedayclose();
        updatedayclose.execute();
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
                    executerInventory(superuser);
                    String s = executerInventory(retail_dayclose_op);
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


    public String executerInventory(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/day_close.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }


}
