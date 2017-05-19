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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

import Pojo.Decimal;

public class ActivitySystemValidate extends Activity {

    ActionBar actionBar;
    EditText password;
    DBhelper db;
    String backroundcolour, colorchange;
    String hide = "su -c  am startservice -n com.android.systemui/.SystemUIService";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_validate);

        db = new DBhelper(ActivitySystemValidate.this);


        Decimal valuetextsize = db.getStoreprice();
        String textsize = valuetextsize.getHoldpo();
        backroundcolour = valuetextsize.getColorbackround();


        if (backroundcolour.matches("Orange")) {

            colorchange = "#ff9033";
        }
        if (backroundcolour.matches("Orange Dark")) {

            colorchange = "#EE782D";

            //    d=Color.BLUE;

        }
        if (backroundcolour.matches("Orange Lite")) {

            colorchange = "#FFA500";

        }
        if (backroundcolour.matches("Blue")) {

            colorchange = "#357EC7";

        }
        if (backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
        }
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        Button login = (Button) findViewById(R.id.loginbutton);

        password = (EditText) findViewById(R.id.validatepassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (password.getText().toString().equals("system")) {
                    Intent intent = new Intent(ActivitySystemValidate.this, Activity_masterScreen1.class);
                    startActivity(intent);
                    hideoff();
                } else {
                    Toast.makeText(getBaseContext(), "Wrong password", Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
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
        int id = item.getItemId();


        switch (item.getItemId()) {

            case R.id.action_settings:

                Intent i = new Intent(ActivitySystemValidate.this, ActivityMaintain.class);
                startActivity(i);
                return true;

           /* case R.id.action_settinghelp:
                help_dayopenclose_reports_dialog();
                return true;*/
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ReportDayOpenCash.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivitySystemValidate.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in = new Intent(ActivitySystemValidate.this, activity_inventorywithpo.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(ActivitySystemValidate.this, ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivitySystemValidate.this ,login.class);
        startActivity(intent);
    }

    public void help_dayopenclose_reports_dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                         FINACIAL REPORT");
        alertDialog.setMessage("Objective:\n" +
                "\t\n" +
                "The day end report to declare what all transactions have happened within the day. This includes all transaction summary within a day.\n" +

                "\n"
        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        alertDialog.show();

    }


    public void hideoff() {


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

                    String s = executerloyalityaccural(hide);
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
            outputStream.writeBytes("su -c  am startservice -n com.android.systemui/.SystemUIService");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

}
