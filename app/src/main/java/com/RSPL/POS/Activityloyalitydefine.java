package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;

public class Activityloyalitydefine extends Activity {

    TextView storeid;
    TextView Id;
    TextView cardtype;
    Button update;
    EditText pointsrange;
    DBhelper mydb;
    String Update;
    public static String username = null;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    String retail_customer_loyalty_op = "sh /sdcard/customer_loyalty.sh";
    String superuser ="su";
    CustomFractionalKeyboard customFractionalKeyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activityloyalitydefine);
        ActionBar actionBar;

        mydb = new DBhelper(this);

        username =  login.b.getString("Pos_User");

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf


        ArrayList<String> updatelist1 = mydb.getdata1();
        try {
            String Store_Id_Search = updatelist1.get(0);
            Bundle databundle = new Bundle();
            databundle.putString("Store_Id", Store_Id_Search);
            Intent i = getIntent();
            i.putExtras(databundle);
            actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setIcon(R.drawable.w);

            //  startActivity(i);
            storeid = (TextView) findViewById(R.id.editstoreid);
            Id = (TextView) findViewById(R.id.editid);
            cardtype = (TextView) findViewById(R.id.editcardtype);


            pointsrange.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    customFractionalKeyboard= new CustomFractionalKeyboard(Activityloyalitydefine.this, R.id.keyboard_loyalitydefine_number, R.xml.fractional_keyboard );

                    customFractionalKeyboard.registerEditText(R.id.editpointrange);

                    return false;
                }
            });



            pointsrange = (EditText) findViewById(R.id.editpointrange);
            update = (Button) findViewById(R.id.buttonupdate);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                String Value = extras.getString("Store_Id");
                Cursor rs = mydb.getdata(Value);

                rs.moveToFirst();

                String editstoreid = rs.getString(rs.getColumnIndex(DBhelper.STORE_ID_CARD));
                storeid.setText(editstoreid);
                String editid = rs.getString(rs.getColumnIndex(DBhelper.ID));
                Id.setText(editid);
                String editcardtype = rs.getString(rs.getColumnIndex(DBhelper.CARD_TYPE));
                cardtype.setText(editcardtype);
                String pointrange = rs.getString(rs.getColumnIndex(DBhelper.POINTS_RANGE));
                pointsrange.setText(pointrange);


            }
/*

            pointsrange.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    customFractionalKeyboard= new CustomFractionalKeyboard(Activityloyalitydefine.this, R.id.keyboard_loyalitydefine_number, R.xml.fractional_keyboard );

                    customFractionalKeyboard.registerEditText(R.id.editpointrange);

                    return false;
                }
            });
*/


            update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                pointsrange = (EditText) findViewById(R.id.editpointrange);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
//
                    String Value = extras.getString("Store_Id");
                    Update = Value;


                    mydb.updateContact(Update, pointsrange.getText().toString(),username);
                    Toast.makeText(getApplicationContext(), "LOYALITY UPDATED", Toast.LENGTH_SHORT).show();
                    Updatepointrange();
                    UpdateShell();
                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);


                }

            }
        });
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        catch (IndexOutOfBoundsException  e){
            e.printStackTrace();
        }
        catch (NullPointerException  e) {
            e.printStackTrace();
        }
        Button Exit = (Button) findViewById(R.id.buttonexit);
        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(),ActivityLoyality.class);
                startActivity(intent);
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

            Intent i=new Intent(Activityloyalitydefine.this,ActivityLoyality.class);
            startActivity(i);
            return true;
        }
if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(Activityloyalitydefine.this);
        showIncentive.show();
        return true;}

        return super.onOptionsItemSelected(item);
    }

    public void Updatepointrange() {



        final String customerloyaltyid =  Id.getText().toString();
        final String customerloyaltystoreid =storeid.getText().toString();
        final String customerloyaltycardtype =  cardtype.getText().toString();
        final String customerloyaltypointrange =pointsrange.getText().toString();





        class Updatecustomerloyalty extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
try{
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(Config.Customer_loyalty_store_id,customerloyaltystoreid);
                hashMap.put(Config.Customer_loyalty_id,customerloyaltyid);
                hashMap.put(Config.Customer_loyalty_cardtype,customerloyaltycardtype);
                hashMap.put(Config.Customer_loyalty_pointrange,customerloyaltypointrange);
                hashMap.put(Config.Retail_Pos_user,username);


                JSONParserSync rh = new JSONParserSync();
                JSONObject s = rh.sendPostRequest(Config.UPDATE_RETAIL_CUST_LOYALTY, hashMap);
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
              //  Toast.makeText(Activityloyalitydefine.this, s, Toast.LENGTH_LONG).show();
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
                    executerInventory(superuser);
                    String s = executerInventory(retail_customer_loyalty_op);
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
            outputStream.writeBytes("sh /sdcard/customer_loyalty.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

}
