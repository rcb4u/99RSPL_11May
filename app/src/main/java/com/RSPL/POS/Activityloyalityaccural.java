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
import android.view.KeyEvent;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Config.Config;
import JSON.JSONParserSync;

public class Activityloyalityaccural extends Activity {
    public static String username = null;

    TextView storeid;
    TextView sno;
    TextView cardtype;
    TextView pertonpointt;
    String Update;
    CustomFractionalKeyboard customFractionalKeyboard;


    Button update;
    EditText basevalue;
    DBhelper mydb;
    ActionBar actionBar;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    String retail_loyalty_ahead_op = "sh /sdcard/loyalty_ahead.sh";
    String superuser ="su";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activityloyalityaccural);
        username =  login.b.getString("Pos_User");

        mydb = new DBhelper(this);
        ArrayList<String> updatelist2 = mydb.getdata2();
        try
        {
        String Store_Id_Search = updatelist2.get(0);
        Bundle databundle = new Bundle();
        databundle.putString("Store_Id", Store_Id_Search);
        Intent i = getIntent();
        i.putExtras(databundle);
        //  startActivity(i);
        storeid=(TextView)findViewById(R.id.editstoreid);
        sno=(TextView)findViewById(R.id.editsno);
        cardtype=(TextView)findViewById(R.id.editcardtype);
        pertonpointt=(TextView)findViewById(R.id.editpertonpoint);
        basevalue=(EditText)findViewById(R.id.editbasevalue);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);




            basevalue.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {


                    customFractionalKeyboard= new CustomFractionalKeyboard(Activityloyalityaccural.this, R.id.keyboard_loyality_number, R.xml.fractional_keyboard );

                    customFractionalKeyboard.registerEditText(R.id.editbasevalue);

                    return false;
                }
            });



            update=(Button)findViewById(R.id.buttonupdate);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            String Value = extras.getString("Store_Id");
            Cursor rs = mydb.getdatarule(Value);

            rs.moveToFirst();

            String editstoreid = rs.getString(rs.getColumnIndex(DBhelper.STORE_ID_RULE));
            storeid.setText(editstoreid);
            String editsno = rs.getString(rs.getColumnIndex(DBhelper.SNO));
            sno.setText(editsno);
            String editcardtype = rs.getString(rs.getColumnIndex(DBhelper.CARD_TYPE));
            cardtype.setText(editcardtype);
            String editpertonpoint=rs.getString(rs.getColumnIndex(DBhelper.PER_TON_POINTS));
            pertonpointt.setText(editpertonpoint);
            String basevalues=rs.getString(rs.getColumnIndex(DBhelper.BASE_VALUE));
            basevalue.setText(basevalues);


        }


        update.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                basevalue= (EditText) findViewById(R.id.editbasevalue);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
//
                    String Value = extras.getString("Store_Id");
                    Update = Value;


                    mydb.updateContact2(Update, basevalue.getText().toString(),username);
                    Toast.makeText(getApplicationContext(), "LOYALITY UPDATED", Toast.LENGTH_SHORT).show();
                    Updatebasevalue();
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
                Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
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

            Intent i=new Intent(Activityloyalityaccural.this,ActivityLoyality.class);
            startActivity(i);
            return true;


        }

        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(Activityloyalityaccural.this);
        showIncentive.show();
        return true;}

        return super.onOptionsItemSelected(item);
    }



    public void Updatebasevalue() {



        final String loyaltyidSno =  sno.getText().toString();
        final String loyaltystoreid =storeid.getText().toString();
        final String loyaltycardtype =  cardtype.getText().toString();
        final String loyaltybasevalue =basevalue.getText().toString();
        final String loyaltypertonpoint =pertonpointt.getText().toString();





        class Updateloyaltyahead extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
try{
                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put(Config.loyalty_store_id,loyaltystoreid);
                hashMap.put(Config.loyalty_sno,loyaltyidSno);
                hashMap.put(Config.loyalty_cardtype,loyaltycardtype);
                hashMap.put(Config.loyalty_basevalue,loyaltybasevalue);
                hashMap.put(Config.loyalty_pertonpoint,loyaltypertonpoint);
                hashMap.put(Config.Retail_Pos_user,username);



                JSONParserSync rh = new JSONParserSync();
               JSONObject s = rh.sendPostRequest(Config.UPDATE_LOYALTY_HEAD, hashMap);
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
              //  Toast.makeText(Activityloyalityaccural.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updateloyaltyahead updatepointrange = new Updateloyaltyahead();
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
                    String s = executerloyalityaccural(retail_loyalty_ahead_op);
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
            outputStream.writeBytes("sh /sdcard/loyalty_ahead.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }



}
