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
import ReportTabFragments.TabSalesReturnReportPagerAdapter;


public class ReportTabActivitySalesReturnReport extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabSalesReturnReportPagerAdapter mAdapter;
    private ActionBar actionBar;
    private String[] tabs = {"With Invoice", "Without Invoice"};
    DBhelper db;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_activity_sales_return_report);

        db = new DBhelper(ReportTabActivitySalesReturnReport.this);

        Decimal valuetextsize = db.getStoreprice();
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
        RelativeLayout layout=(RelativeLayout) findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


        viewPager = (ViewPager) findViewById(R.id.pagersalesreturn);
        actionBar = getActionBar();
        mAdapter = new TabSalesReturnReportPagerAdapter(getSupportFragmentManager());

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }

    public  void help_salesreturn_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("          SALES RETRUN REPORTS  ");
        alertDialog.setMessage("Objective:\n" +
                        "\t\n" +
                        "The report shows the Products returned within a store with a bill.\n" +
                        "The report shows the products returned within a store without a bill. \n" +
                        " \n"
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i=new Intent(ReportTabActivitySalesReturnReport.this,SaleReportActivity.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ReportTabActivitySalesReturnReport.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_salesreturn_dialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ReportTabActivitySalesReturnReport.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_settinginv:
                Intent in=new Intent(ReportTabActivitySalesReturnReport.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportTabActivitySalesReturnReport.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:

                Intent intent = new Intent(ReportTabActivitySalesReturnReport.this,Activity_masterScreen1.class);
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
        Intent intent = new Intent(ReportTabActivitySalesReturnReport.this ,login.class);
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
