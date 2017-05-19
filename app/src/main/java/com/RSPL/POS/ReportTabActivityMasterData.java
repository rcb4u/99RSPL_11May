package com.RSPL.POS;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import Pojo.Decimal;
import ReportTabFragments.TabMasterDataReportPagerAdapter;


public class ReportTabActivityMasterData extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabMasterDataReportPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Distributor", "Vendor", "Product Pharma", "Local Product Pharma"};

    String backroundcolour,colorchange;
DBhelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_masterdata_report);
        mydb = new DBhelper(ReportTabActivityMasterData.this);

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
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        RelativeLayout layout=(RelativeLayout) findViewById(R.id.main_layout);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pagermasterdata);
        actionBar = getActionBar();
        mAdapter = new TabMasterDataReportPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }


    public void help_masterdata_reports_dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                           MASTER DATA REPORT PAGE");
        alertDialog.setMessage(
                "\n" +
                        "Objective:\n" +
                        "\t\n" +
                        "\t 1.The distributor report displays the list of distributors which are active, inactive in the system. The distributors are one which delivers the products to the store.\n" +
                        "\t 2.The vendor report displays the list of local vendors. These local vendors either delivers products or are for administrative purposes.\n" +
                        "\t 3.The report displays products are centralized andt gives an idea of MRP, SP, Purchase price and whether they are active/inactive at the stores.\n" +
                        "\t 4.The products are centralized and the report gives an idea of MRP, SP, Purchase price and whether they are active/inactive at the stores.\n"
        );
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                // Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                // startActivity(intent);
            }
        });


        alertDialog.show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i=new Intent(ReportTabActivityMasterData.this,ActivityLoyalityCust.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                help_masterdata_reports_dialog();
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ReportTabActivityMasterData.this);
                showIncentive.show();
                return true;
       /*     case R.id.action_settingpurchase:
                Intent p=new Intent(ReportTabActivityMasterData.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ReportTabActivityMasterData.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ReportTabActivityMasterData.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportTabActivityMasterData.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ReportTabActivityMasterData.this ,login.class);
        startActivity(intent);
    }



}


