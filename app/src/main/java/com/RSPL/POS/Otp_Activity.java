package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import Pojo.Decimal;

public class Otp_Activity extends Activity {

    ActionBar actionBar;
    EditText password;
    DBhelper db;
    String backroundcolour, colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);

        db = new DBhelper(Otp_Activity.this);


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

                if (password.getText().toString().equals("4387")) {
                    Intent intent = new Intent(Otp_Activity.this, Activity_bill_visibility.class);
                    startActivity(intent);
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

                Intent i = new Intent(Otp_Activity.this, ActivityLoyality.class);
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
                Intent p = new Intent(Otp_Activity.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in = new Intent(Otp_Activity.this, activity_inventorywithpo.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(Otp_Activity.this, ActivitySalesbill.class);
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
        Intent intent = new Intent(Otp_Activity.this ,login.class);
        startActivity(intent);
    }
}
