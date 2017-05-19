package com.RSPL.POS;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Visibility;

public class Activity_bill_visibility extends Activity {
    TextView Storeid;
    Spinner mrpvisible,tele1,footervisiblity,billcopy,credit,returnbillcopy,itemvisibilty,margin,freeqty,invbillprint,PrintBill,otp;
    String ActiveType[],Activebillcopy[] ;
    String SpinValue,SpinValue2,SpinValue3,store_id,Store_Id_To_Update,SpinValue4,SpinValue5,SpinValue6,SpinValue7,SpinValue8,SpinValue9,SpinValue10,SpinValue11;
    ArrayAdapter<String> visibiltyActiveType,billActivetype;
    android.app.ActionBar actionBar;
    DBhelper mydb;
    String iteam;
    TextView user2;


    //////////////jimmy     size////////////////////////////


    TextView tvstoreid,tvmrpvisible,tvtele1,tvfootervisiblity,tvbillcopy,tvcredit,tvreturnbillcopy,tvmargin,tvfreeqty,tvinvbillprint,tvPrintBill,otpgenerate;


    String backroundcolour,colorchange;


    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_bill_visibility);


        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(Activity_bill_visibility.this);
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


////////////////////////////////////jimmmy///////////////////////////////////////////
        tvstoreid = (TextView) findViewById(R.id.store_id);
        tvstoreid.setTextSize(Float.parseFloat(textsize));

        tvmrpvisible = (TextView) findViewById(R.id.textView5);
        tvmrpvisible.setTextSize(Float.parseFloat(textsize));
        tvtele1 = (TextView) findViewById(R.id.textView9);
        tvtele1.setTextSize(Float.parseFloat(textsize));
        tvfootervisiblity = (TextView) findViewById(R.id.textView1);
        tvfootervisiblity.setTextSize(Float.parseFloat(textsize));
        tvbillcopy = (TextView) findViewById(R.id.billcopy);
        tvbillcopy.setTextSize(Float.parseFloat(textsize));
        tvcredit = (TextView) findViewById(R.id.creditcardbillcopy);
        tvcredit.setTextSize(Float.parseFloat(textsize));
        tvreturnbillcopy = (TextView) findViewById(R.id.returnbillcopy);
        tvreturnbillcopy.setTextSize(Float.parseFloat(textsize));
        tvmargin = (TextView) findViewById(R.id.returnmargin);
        tvmargin.setTextSize(Float.parseFloat(textsize));
        tvfreeqty = (TextView) findViewById(R.id.freeqtyvisiblr);
        tvfreeqty.setTextSize(Float.parseFloat(textsize));
        tvinvbillprint = (TextView) findViewById(R.id.inv);
        tvinvbillprint.setTextSize(Float.parseFloat(textsize));
        tvPrintBill = (TextView) findViewById(R.id.saleprint);
        tvPrintBill.setTextSize(Float.parseFloat(textsize));

        otpgenerate = (TextView) findViewById(R.id.otp);
        otpgenerate.setTextSize(Float.parseFloat(textsize));



        Storeid = (TextView) findViewById(R.id.store);
        Storeid.setTextSize(Float.parseFloat(textsize));

        mrpvisible = (Spinner)findViewById(R.id.mrpvisible);
        tele1 = (Spinner)findViewById(R.id.tele2);
        footervisiblity = (Spinner) findViewById(R.id.footer);
        billcopy = (Spinner)findViewById(R.id.bill1);
        credit = (Spinner)findViewById(R.id.bill2);
        returnbillcopy = (Spinner) findViewById(R.id.bill3);
        margin = (Spinner) findViewById(R.id.marginvisible);
        freeqty = (Spinner) findViewById(R.id.freeqtyvisible);
        invbillprint = (Spinner) findViewById(R.id.invbillprintvisible);
        PrintBill=(Spinner)findViewById(R.id.PrintBill);
        otp=(Spinner)findViewById(R.id.otpgenerate);


        //  itemvisibilty = (Spinner) findViewById(R.id.itemvisibilty);



        final DBhelper mydb = new DBhelper(Activity_bill_visibility.this);
        Visibility value = mydb.getStorevisibility();
        Storeid.setText(value.getStoreid());

        SpinValue=value.getMrpvisibility();
        SpinValue2=value.getTele2();
        SpinValue3=value.getFootervisi();
        SpinValue4=value.getBillcopy();
        SpinValue5=value.getCreditcopy();
        SpinValue6=value.getReturncopy();
        SpinValue7=value.getItemvisibilty();
        SpinValue8=value.getMarginvisiblty();
        SpinValue9=value.getFreequantity();
        SpinValue10=value.getInvbillprints();
        SpinValue11=value.getOtp();







        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mrpvisible.setAdapter(visibiltyActiveType);
        if (SpinValue.equals("Y"))
        {
            mrpvisible.setSelection(0);
        }
        if (SpinValue.equals("N"))
        {
            mrpvisible.setSelection(1);


        }


        mrpvisible.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = mrpvisible.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tele1.setAdapter(visibiltyActiveType);

        if (SpinValue2.equals("Y"))
        {
            tele1.setSelection(0);
        }
        if (SpinValue2.equals("N"))
        {
            tele1.setSelection(1);

        }

        tele1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue2 = tele1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        footervisiblity.setAdapter(visibiltyActiveType);
        if (SpinValue3.equals("Y"))
        {
            footervisiblity.setSelection(0);
        }
        if (SpinValue3.equals("N"))
        {
            footervisiblity.setSelection(1);

        }
        footervisiblity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue3 = footervisiblity.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Activebillcopy = getResources().getStringArray(R.array.active_bill_copy);
        billActivetype = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Activebillcopy);
        billActivetype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        billcopy.setAdapter(billActivetype);
        if (SpinValue4.equals("1"))
        {
            billcopy.setSelection(0);
        }
        if (SpinValue4.equals("2"))
        {
            billcopy.setSelection(1);

        }
        billcopy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue4 = billcopy.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        Activebillcopy = getResources().getStringArray(R.array.active_bill_copy);
        billActivetype = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Activebillcopy);
        billActivetype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        credit.setAdapter(billActivetype);
        if (SpinValue5.equals("1"))
        {
            credit.setSelection(0);

        }
        if (SpinValue5.equals("2"))
        {
            credit.setSelection(1);
        }
        credit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue5 = credit.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Activebillcopy = getResources().getStringArray(R.array.active_bill_copy);
        billActivetype = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Activebillcopy);
        billActivetype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        returnbillcopy.setAdapter(billActivetype);
        if (SpinValue6.equals("1"))
        {
            returnbillcopy.setSelection(0);
        }
        if (SpinValue6.equals("2"))
        {
            returnbillcopy.setSelection(1);

        }
        returnbillcopy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue6 = returnbillcopy.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


          ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        PrintBill.setAdapter(visibiltyActiveType);
        if (SpinValue7.equals("Y"))
        {
            PrintBill.setSelection(0);
        }
        if (SpinValue7.equals("N"))
        {
            PrintBill.setSelection(1);

        }


        PrintBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue7 = PrintBill.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        margin.setAdapter(visibiltyActiveType);
        if (SpinValue8.equals("Y"))
        {
            margin.setSelection(0);
        }
        if (SpinValue8.equals("N"))
        {
            margin.setSelection(1);

        }


        margin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue8 = margin.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        freeqty.setAdapter(visibiltyActiveType);
        if (SpinValue9.equals("Y"))
        {
            freeqty.setSelection(0);
        }
        if (SpinValue9.equals("N"))
        {
            freeqty.setSelection(1);

        }


        freeqty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue9 = freeqty.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        invbillprint.setAdapter(visibiltyActiveType);
        if (SpinValue10.equals("Y"))
        {
            invbillprint.setSelection(0);
        }
        if (SpinValue10.equals("N"))
        {
            invbillprint.setSelection(1);

        }


        invbillprint.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue10 = invbillprint.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        otp.setAdapter(visibiltyActiveType);
        if (SpinValue11.equals("Y"))
        {
            otp.setSelection(0);
        }
        if (SpinValue11.equals("N"))
        {
            otp.setSelection(1);

        }


        otp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue11 = otp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        Button Exit = (Button) findViewById(R.id.Store_exit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);
            }
        });


        Button Update = (Button) findViewById(R.id.Store_update);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Value = Storeid.getText().toString();

                Store_Id_To_Update = Value;

              mydb.updatevisibility(Store_Id_To_Update, SpinValue,SpinValue2,SpinValue3,SpinValue4,SpinValue5,SpinValue6,SpinValue7,SpinValue8,SpinValue9,SpinValue10,SpinValue11);
               // mydb.updatevisibility(Store_Id_To_Update, SpinValue,SpinValue2,SpinValue3,SpinValue4,SpinValue5,SpinValue6,SpinValue7,SpinValue8);

                Toast.makeText(getApplicationContext(), "Bill Detail Updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);

                startActivity(intent);
                Updatedisplay();

                //call shell script for update record into slave machine
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
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:
                Intent i = new Intent(Activity_bill_visibility.this, ActivityLoyality.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_bill_visibility.this);
                showIncentive.show();
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_bill_visibility.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/

            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_bill_visibility.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in=new Intent(Activity_bill_visibility.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_bill_visibility.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(Activity_bill_visibility.this ,login.class);
        startActivity(intent);
    }

    public void Updatedisplay() {

        final DBhelper mydb = new DBhelper(Activity_bill_visibility.this);

        PersistenceManager.saveStoreId(this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id= PersistenceManager.getStoreId(this);
        class Updatedecimal extends AsyncTask<Void, Void, String> {
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

                    hashMap.put(Config.RETAIL_STORE_STORE_ID,store_id);
                    hashMap.put(Config.RETAIL_STORE_MRP,SpinValue);
                    hashMap.put(Config.RETAIL_STORE_ALTERMOBILENO2,SpinValue2);
                    hashMap.put(Config.RETAIL_STORE_FOOTER,SpinValue3);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_BILL_VISIBILITY,hashMap);
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
        Updatedecimal updateStore = new Updatedecimal();
        updateStore.execute();
    }



}
