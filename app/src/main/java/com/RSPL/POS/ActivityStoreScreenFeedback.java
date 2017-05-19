package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class ActivityStoreScreenFeedback extends Activity {

    Spinner allscreensSpinnerValue;
    String allscreensName[],allScreenValue;
    ArrayAdapter<String> adapterallScreen;
    ActionBar actionBar;
    Button submit;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_status);
        allscreensSpinnerValue = (Spinner) findViewById(R.id.allscreens);
        submit = (Button) findViewById(R.id.Submit);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        allscreensName = getResources().getStringArray(R.array.cr_status_for_allscreen);
        adapterallScreen = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allscreensName);
        adapterallScreen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        allscreensSpinnerValue.setAdapter(adapterallScreen);

        //Adding setOnItemSelectedListener method on spinner.
        allscreensSpinnerValue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                allScreenValue = (String) allscreensSpinnerValue.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch(allScreenValue){

                    case "Master Data Management":
                        intent = new Intent(ActivityStoreScreenFeedback.this, MasterDataActivity.class);
                        startActivity(intent);
                        break;
                    case "System Settings":
                        intent = new Intent(ActivityStoreScreenFeedback.this, SystemSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case "Purchasing":
                        intent = new Intent(ActivityStoreScreenFeedback.this, PurchasingActivity.class);
                        startActivity(intent);
                        break;
                    case "Sales":
                        intent = new Intent(ActivityStoreScreenFeedback.this, SalesActivity.class);
                        startActivity(intent);
                        break;
                    case "Reports":
                        intent = new Intent(ActivityStoreScreenFeedback.this, ReportsActivity.class);
                        startActivity(intent);
                        break;
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
    public boolean onTouchEvent(MotionEvent event) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
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
        if (id == R.id.action_settings) {

            Intent i=new Intent(ActivityStoreScreenFeedback.this, Activity_masterScreen1.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(ActivityStoreScreenFeedback.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }
}
