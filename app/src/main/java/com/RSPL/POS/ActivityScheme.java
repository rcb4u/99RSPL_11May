package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class ActivityScheme extends Activity implements View.OnClickListener {
    ActionBar actionBar;
    ImageButton companylist;
    ImageButton mfglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_scheme);
        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        companylist=(ImageButton)findViewById(R.id.companylist_button);
        mfglist=(ImageButton)findViewById(R.id.mfglistbutton);

        companylist.setOnClickListener(this);
        mfglist.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.companylist_button:

                Intent intent = new Intent(this, Activity_Comp_Btl.class);
                startActivity(intent);
                break;
            case R.id.mfglistbutton:
                Intent intent1 = new Intent(this, Activity_Mfg_btl.class);
                startActivity(intent1);
                break;


        }
    }


    public  void Schemahelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("       SCHEME MANAGEMENT FOR THE COMPANIES  \n");
        alertDialog.setMessage("Schems are BTL’s (Below The Line) run by manufacturers or various companies for a store. These schemes are centralised for example a store  if it sells 1000 LKR of a manufacturer product would be entitled for 5% discount. \n" +
                "A company can run their BTL’s being a primary distributor across all manufacturers.These are centrally created and available to retailers for viewing. \n" +
                "The performance of these BTL’s can be seen under report section “Others”." +
                "\n" +
                "\n Scheme Management for company\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "Input description:\n" +
                "\n" +
                "Comapny Name\n" +
                "Target Amount\n" +
                "BTL Desc\n" +
                "Target value\n" +
                "Start Date\n" +
                "\n" +
                "End DateScheme Management for Manufacturer\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "\n" +
                "Input description:\n" +
                "\n" +
                "MFG Name\n" +
                "Target Amount\n" +
                "BTL Desc\n" +
                "Target value\n" +
                "Start Date\n" +
                "End Date\n" +
                "\n" +
                "\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



                dialog.dismiss();
            }
        });


        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen2, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(ActivityScheme.this,Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityScheme.this);
                showIncentive.show();
                return true;

            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityScheme.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinghelp:
                Schemahelpdialog();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
