package com.RSPL.POS;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import Pojo.Decimal;
import ReportTabFragments.TabInventoryReportPagerAdapter;

public class ReportTabActivityInventory extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabInventoryReportPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"Daily Inventory","1 Month", "3 Month", "6 Month", "Custom Report","Stock"};

      DBhelper mydb;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_inventory_report);

        mydb = new DBhelper(ReportTabActivityInventory.this);

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
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        viewPager = (ViewPager) findViewById(R.id.pagerinventry);
        actionBar = getActionBar();
        mAdapter = new TabInventoryReportPagerAdapter(getSupportFragmentManager());

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

    public  void help_inventory_reports_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                 INVENTORY REPORT    ");
        alertDialog.setMessage("Objective:\n" +
                        "\t\n" +
                        "The inventory reports consists of providing 4 reports:\n" +
                        "\n" +
                        "\t 1. Stock report - To provide information on what stocks are available at the store. \n" +
                        "\t 2. Inventory ageing - within 1 month: Stocks that are going to expire within 1 month of store.\n" +
                        "\t 3. Stock Ageing - less than 3 months - Stocks that are going to expire within 3 months \n" +
                        "\t 4. Stock ageing - 6 months - Stocks that are going to expire within 6 months.\n"
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
                Intent i=new Intent(ReportTabActivityInventory.this,PurchasingReportActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ReportTabActivityInventory.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_inventory_reports_dialog();
                return true;
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ReportTabActivityInventory.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(ReportTabActivityInventory.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportTabActivityInventory.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent intent=new Intent(ReportTabActivityInventory.this,Activity_masterScreen1.class);
                startActivity(intent);
                return true;

            case R.id.action_logout:
                Logout();

                return true;





        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(ReportTabActivityInventory.this ,login.class);
        startActivity(intent);
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
