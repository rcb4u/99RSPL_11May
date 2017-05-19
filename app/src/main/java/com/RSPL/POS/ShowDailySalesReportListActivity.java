package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Adapter.ReportDailySaleListAdapter;
import Pojo.Decimal;
import Pojo.SaleReportModel;


public class ShowDailySalesReportListActivity extends Activity {

    ListView listView;
    ReportDailySaleListAdapter salelistAdapter;
    ArrayList<SaleReportModel> alldata;
    DBhelper helper;
    ActionBar actionBar;
    TextView tvproduct,tvbatch,tvexpiry,tvmrp,tvqty,tvcardtype,tvbankname;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dailyreportsale_list);

        actionBar = getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        helper = new DBhelper(this);

        Decimal valuetextsize = helper.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();




        if(backroundcolour.matches("Orange")){

            colorchange="#ff9033";
            //  String    d="#bdc3c7";//silver


            //f39c12
            //EF643C
            //EE782D
        }
        if(backroundcolour.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

            //    d=Color.BLUE;//FBB917

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
            //C3C3C3 grey//E1E1E1

            ////FBB917

        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));




        tvproduct = (TextView)findViewById(R.id.tvName);

        tvproduct.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView)findViewById(R.id.tvBatch);
        tvproduct.setTextSize(Float.parseFloat(textsize));
        tvexpiry = (TextView)findViewById(R.id.tvExp);
        tvproduct.setTextSize(Float.parseFloat(textsize));
        tvmrp = (TextView)findViewById(R.id.tvMrp);
        tvproduct.setTextSize(Float.parseFloat(textsize));
        tvqty = (TextView)findViewById(R.id.tvQty);
        tvproduct.setTextSize(Float.parseFloat(textsize));
        tvcardtype = (TextView)findViewById(R.id.tvCardType);
        tvproduct.setTextSize(Float.parseFloat(textsize));
        tvbankname = (TextView)findViewById(R.id.tvBankNm);
        tvproduct.setTextSize(Float.parseFloat(textsize));




        Bundle extras = getIntent().getExtras();
        String Value = extras.getString("id");
        alldata=helper.getDailySalesBillDetailReport(Value);


        listView = (ListView) findViewById(R.id.lv_DailySalesReport);
        Log.e("***********Lt1*******", listView.toString());

        salelistAdapter=new ReportDailySaleListAdapter(this,alldata,android.R.layout.simple_expandable_list_item_1,null);
        listView.setAdapter(salelistAdapter);


    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
        }
        else
            super.onBackPressed();
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

            onBackPressed();
            return true;
        }

        if(id==R.id.action_settinginfo){
            ShowIncentive showIncentive = new ShowIncentive(ShowDailySalesReportListActivity.this);
            showIncentive.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}