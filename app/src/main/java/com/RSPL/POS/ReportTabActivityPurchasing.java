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

import ReportTabFragments.TabPurchasingReportPagerAdapter;


public class ReportTabActivityPurchasing extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabPurchasingReportPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Daily Purchasing","Last 1 Month", "Last 3 Month", "Custom Report"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_purchasing_report);

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        viewPager = (ViewPager) findViewById(R.id.pagerpurchasing);
        actionBar = getActionBar();
        mAdapter = new TabPurchasingReportPagerAdapter(getSupportFragmentManager());

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

    public  void help_purchase_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  PURCHASE REPORT ");
        alertDialog.setMessage("\t\n" +
                        "The purchase reports consists of products ordered at a store from the vendor or the distributor.:\n" +
                        "\n"

        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                dialog.dismiss();
                /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
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

        switch (item.getItemId()) {


            case R.id.action_settings:

                Intent i=new Intent(ReportTabActivityPurchasing.this,PurchasingReportActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ReportTabActivityPurchasing.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_purchase_dialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ReportTabActivityPurchasing.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(ReportTabActivityPurchasing.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportTabActivityPurchasing.this,ActivitySalesbill.class);
                startActivity(s);
                return true;



        }

        return super.onOptionsItemSelected(item);
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
}
