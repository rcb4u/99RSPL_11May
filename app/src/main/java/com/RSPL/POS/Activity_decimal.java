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

public class Activity_decimal extends Activity {

    Spinner Mrp;
    Spinner pprice;
    TextView Storeid;
    Spinner sprice,holdpo,holdinv,holdsales,roundoff,purchaseholdno,inventoryholdno,salesholdno,backroundcolours;
    ArrayAdapter<String> mrpActiveType;
    String ActiveType[] ;
    String ActiveTypehold[] ;
    String RoundoffActive[];
    String SpinValue,SpinValue2,SpinValue3,SpinValue4,SpinValue5,SpinValue6,store_id,SpinValue7,SpinValue8,SpinValue9,SpinValue10;
    ArrayAdapter<String> holdActiveType;
    ArrayAdapter<String> roundoffActiveType;

    android.app.ActionBar actionBar;
    String colorchange;
    TextView TV_mrpdec, tvppricedec, tvspricedec,
            tvtextsize, tvholdinv,tv_Storeid,tvholdsale,tvroundoff,tvinvhold,tvbackround;



    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    // String store_id;
    public static Bundle b = new Bundle();
    String usermrp,Store_Id_To_Update,Value2,value3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_decimal);
        actionBar=getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Storeid = (TextView) findViewById(R.id.store);

        Mrp = (Spinner)findViewById(R.id.mrpdecimal);
        pprice = (Spinner)findViewById(R.id.ppricedecimal);
        sprice = (Spinner) findViewById(R.id.spricedecimal);
        holdpo = (Spinner) findViewById(R.id.holdpo);// for text size
        holdinv = (Spinner) findViewById(R.id.holdinv);
        holdsales = (Spinner) findViewById(R.id.hold_sales);
        roundoff=(Spinner)findViewById(R.id.roundoff);

       // purchaseholdno = (Spinner) findViewById(R.id.purchaseholdno);
        inventoryholdno = (Spinner) findViewById(R.id.inventoryholdno);
        backroundcolours = (Spinner) findViewById(R.id.backroundcolor);






        final DBhelper mydb = new DBhelper(Activity_decimal.this);
        Decimal value = mydb.getStoreprice();
        Storeid.setText(value.getStoreid());

        SpinValue=value.getDecimalmrp();
        SpinValue2=value.getDecimalpprice();
        SpinValue3=value.getDecimalsprice();
        SpinValue4=value.getHoldpo();//for text size
        SpinValue5=value.getHoldinv();
        SpinValue6=value.getHoldsales();
        SpinValue7=value.getRoundofff();
       // SpinValue8=value.getPurchaseholdno();///use for backroundcolour
        SpinValue9=value.getInventoryholdno();
        SpinValue10=value.getColorbackround();////


        if(SpinValue10.matches("Orange")){

            colorchange="#ff9033";
            //  String    d="#bdc3c7";//silver

        }
        if(SpinValue10.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(SpinValue10.matches("Orange Lite")){

            colorchange="#FFA500";

            //    d=Color.BLUE;//FBB917

        }
        if(SpinValue10.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(SpinValue10.matches("Grey")) {

            colorchange = "#E1E1E1";
            //C3C3C3 grey//E1E1E1

            ////FBB917

        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        tv_Storeid = (TextView) findViewById(R.id.store_id);
        tv_Storeid.setTextSize(Float.parseFloat(SpinValue4));

        TV_mrpdec = (TextView) findViewById(R.id.textView5);
        TV_mrpdec.setTextSize(Float.parseFloat(SpinValue4));

        tvppricedec = (TextView) findViewById(R.id.textView9);
        tvppricedec.setTextSize(Float.parseFloat(SpinValue4));

        tvspricedec = (TextView) findViewById(R.id.textView1);
        tvspricedec.setTextSize(Float.parseFloat(SpinValue4));

        tvtextsize = (TextView) findViewById(R.id.textVie);
        tvtextsize.setTextSize(Float.parseFloat(SpinValue4));

        tvholdinv = (TextView) findViewById(R.id.textViewinv);
        tvholdinv.setTextSize(Float.parseFloat(SpinValue4));

        tvholdsale = (TextView) findViewById(R.id.textViewsales);
        tvholdsale.setTextSize(Float.parseFloat(SpinValue4));

        tvroundoff = (TextView) findViewById(R.id.textroundoff);
        tvroundoff.setTextSize(Float.parseFloat(SpinValue4));

        tvinvhold = (TextView) findViewById(R.id.textinvhold);
        tvinvhold.setTextSize(Float.parseFloat(SpinValue4));

        tvbackround = (TextView) findViewById(R.id.textbackcolor);
        tvbackround.setTextSize(Float.parseFloat(SpinValue4));


        Storeid.setTextSize(Float.parseFloat(SpinValue4));







        ActiveType = getResources().getStringArray(R.array.active_mrp_decimal);
        mrpActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        mrpActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Mrp.setAdapter(mrpActiveType);
        if (SpinValue.equals("2"))
        {
            Mrp.setSelection(0);
        }
        if (SpinValue.equals("3"))
        {
            Mrp.setSelection(1);

        }
        if (SpinValue.equals("4"))
        {
            Mrp.setSelection(2);

        }
        Mrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Mrp.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActiveType = getResources().getStringArray(R.array.active_mrp_decimal);
        mrpActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        mrpActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        pprice.setAdapter(mrpActiveType);

        if (SpinValue2.equals("2"))
        {
            pprice.setSelection(0);
        }
        if (SpinValue2.equals("3"))
        {
            pprice.setSelection(1);

        }
        if (SpinValue2.equals("4"))
        {
            pprice.setSelection(2);

        }
        pprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue2 = pprice.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ActiveType = getResources().getStringArray(R.array.active_mrp_decimal);
        mrpActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
        mrpActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sprice.setAdapter(mrpActiveType);
        if (SpinValue3.equals("2"))
        {
            sprice.setSelection(0);
        }
        if (SpinValue3.equals("3"))
        {
            sprice.setSelection(1);

        }
        if (SpinValue3.equals("4"))
        {
            sprice.setSelection(2);

        }

        sprice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue3 = sprice.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

///////////////////////////////////////////for text size/////////////////////////////////////////////
        ActiveTypehold = getResources().getStringArray(R.array.active_textsize);
        holdActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveTypehold);
        holdActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holdpo.setAdapter(holdActiveType);
        if (SpinValue4.equals("20"))
        {
            holdpo.setSelection(0);
        }
        if (SpinValue4.equals("21"))
        {
            holdpo.setSelection(1);

        }
        if (SpinValue4.equals("22"))
        {
            holdpo.setSelection(2);

        }
        if (SpinValue4.equals("23"))
        {
            holdpo.setSelection(3);

        }



        holdpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue4 = holdpo.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });



        ActiveTypehold = getResources().getStringArray(R.array.active_hold_decimal);
        holdActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveTypehold);
        holdActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holdinv.setAdapter(holdActiveType);
        if (SpinValue5.equals("1"))
        {
            holdinv.setSelection(0);
        }
        if (SpinValue5.equals("2"))
        {
            holdinv.setSelection(1);

        }
        if (SpinValue5.equals("3"))
        {
            holdinv.setSelection(2);

        }
        if (SpinValue5.equals("4"))
        {
            holdinv.setSelection(3);

        }
        if (SpinValue5.equals("5"))
        {
            holdinv.setSelection(4);

        }

        holdinv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue5 = holdinv.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ActiveTypehold = getResources().getStringArray(R.array.active_hold_decimal);
        holdActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveTypehold);
        holdActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        holdsales.setAdapter(holdActiveType);
        if (SpinValue6.equals("1"))
        {
            holdsales.setSelection(0);
        }
        if (SpinValue6.equals("2"))
        {
            holdsales.setSelection(1);

        }
        if (SpinValue6.equals("3"))
        {
            holdsales.setSelection(2);

        }
        if (SpinValue6.equals("4"))
        {
            holdsales.setSelection(3);

        }
        if (SpinValue6.equals("5"))
        {
            holdsales.setSelection(4);

        }
        holdsales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue6 = holdsales.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        RoundoffActive = getResources().getStringArray(R.array.active_Status);
        roundoffActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,RoundoffActive);
        roundoffActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        roundoff.setAdapter(roundoffActiveType);
        if (SpinValue7.equals("Y"))
        {
            roundoff.setSelection(0);
        }
        if (SpinValue7.equals("N"))
        {
            roundoff.setSelection(1);

        }
        roundoff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue7 = roundoff.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        ActiveTypehold = getResources().getStringArray(R.array.active_backroundcolour);
        holdActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveTypehold);
        holdActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        backroundcolours.setAdapter(holdActiveType);
        if (SpinValue10.equals("Orange"))
        {
            backroundcolours.setSelection(0);
        }
        if (SpinValue10.equals("Orange Dark"))
        {
            backroundcolours.setSelection(1);

        }
        if (SpinValue10.equals("Orange Lite"))
        {
            backroundcolours.setSelection(2);

        }
        if (SpinValue10.equals("Blue"))
        {
            backroundcolours.setSelection(3);

        }
        if (SpinValue10.equals("Grey"))
        {
            backroundcolours.setSelection(4);

        }



        backroundcolours.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue10 = backroundcolours.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        ActiveTypehold = getResources().getStringArray(R.array.active_hold_no);
        holdActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveTypehold);
        holdActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        inventoryholdno.setAdapter(holdActiveType);
        if (SpinValue9.equals("1"))
        {
            inventoryholdno.setSelection(0);
        }
        if (SpinValue9.equals("2"))
        {
            inventoryholdno.setSelection(1);

        }
        if (SpinValue9.equals("3"))
        {
            inventoryholdno.setSelection(2);

        }
        if (SpinValue9.equals("4"))
        {
            inventoryholdno.setSelection(3);

        }
        if (SpinValue9.equals("5"))
        {
            inventoryholdno.setSelection(4);

        }
        if (SpinValue9.equals("6"))
        {
            inventoryholdno.setSelection(5);

        }
        if (SpinValue9.equals("7"))
        {
            inventoryholdno.setSelection(6);

        }
        if (SpinValue9.equals("8"))
        {
            inventoryholdno.setSelection(7);

        }
        if (SpinValue9.equals("9"))
        {
            inventoryholdno.setSelection(8);

        }
        if (SpinValue9.equals("10"))
        {
            inventoryholdno.setSelection(9);

        } if (SpinValue9.equals("11"))
        {
            inventoryholdno.setSelection(10);

        } if (SpinValue9.equals("12"))
        {
            inventoryholdno.setSelection(11);

        } if (SpinValue9.equals("13"))
        {
            inventoryholdno.setSelection(12);

        } if (SpinValue9.equals("14"))
        {
            inventoryholdno.setSelection(13);

        } if (SpinValue9.equals("15"))
        {
            inventoryholdno.setSelection(14);

        } if (SpinValue9.equals("16"))
        {
            inventoryholdno.setSelection(15);

        } if (SpinValue9.equals("17"))
        {
            inventoryholdno.setSelection(16);

        } if (SpinValue9.equals("18"))
        {
            inventoryholdno.setSelection(17);

        } if (SpinValue9.equals("19"))
        {
            inventoryholdno.setSelection(18);

        } if (SpinValue9.equals("20"))
        {
            inventoryholdno.setSelection(19);

        }
        inventoryholdno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue9 = inventoryholdno.getSelectedItem().toString();
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

                mydb.updatedecimal(Store_Id_To_Update, SpinValue,SpinValue2,SpinValue3,SpinValue4,SpinValue5,SpinValue6,SpinValue7,SpinValue10,SpinValue9);

              /*  com.RSPL.POS.Activity_decimal.b.putInt("MRP_Decimal", Integer.parseInt(SpinValue));
                com.RSPL.POS.Activity_decimal.b.putInt("P_Price_Decimal",Integer.parseInt(SpinValue2));
                com.RSPL.POS.Activity_decimal.b.putInt("S_Price_Decimal",Integer.parseInt(SpinValue3));
*/
                Updatedecimal();
                Toast.makeText(getApplicationContext(), "Store Updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);

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
            case R.id.action_settings:


                Intent i = new Intent(Activity_decimal.this, ActivityLoyality.class);
                startActivity(i);
                return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_decimal.this);
                showIncentive.show();
                return true;
//
//            case R.id.action_settingpurchase:
//                Intent p=new Intent(Activity_decimal.this,PurchaseActivity.class);
//                startActivity(p);
//                return true;
//
            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_decimal.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(Activity_decimal.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_decimal.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(Activity_decimal.this ,login.class);
        startActivity(intent);
    }

    public void Updatedecimal() {

        final DBhelper mydb = new DBhelper(Activity_decimal.this);

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

                    hashMap.put(Config.RETAIL_STORE_MRP_DECIMAL,SpinValue);
                    hashMap.put(Config.RETAIL_STORE_PPRICE,SpinValue2);
                    hashMap.put(Config.RETAIL_STORE_SPRICE,SpinValue3);
                    hashMap.put(Config.RETAIL_STORE_HOLDPO,SpinValue4);
                    hashMap.put(Config.RETAIL_STORE_HOLDINV,SpinValue5);
                    hashMap.put(Config.RETAIL_STORE_HOLDSALES,SpinValue6);





                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_DECIMAL,hashMap);
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