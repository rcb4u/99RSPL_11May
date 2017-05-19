package com.RSPL.POS;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.USBPrinter;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Pojo.InventoryStockadjustmentmodel;
import Pojo.Store;
import Pojo.Visibility;

public class ReportDayOpenCash extends Activity{

    TextView DayOpenCash,DayTotalSales,DayTotalSalesReturn,DayTotalofPurchase,DayTotalVendorReturn,DayOfVendoPayment,DayTotalCreditAmount,TotalFinancialLoss;
    Context context;
    public static String opencash = null;
    ActionBar actionBar;
    DBhelper helper;
    public static String username = null;
    ArrayList<String> alldata;

    //------------------------For printer------------------------------

    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;
    public USBPrinter UsbPrinter = USBPrinter.INSTANCE;
    TextPaint tp = new TextPaint();
    TextPaint tp32 = new TextPaint();
    TextPaint tp36 = new TextPaint();
    Typeface tff;
    private String eol = "\n";
    public Double value,value1,value2,value3,value4,value5,value6,value7;
    private static final int PRINT_LEFT_MARGIN = 2;
    String extraspace="  ";

    Button print;
    DBhelper db = new DBhelper(this);

    ArrayList<String> store_name;
    int paybycashbillcopy;

    String store, storeAddress, City, Storenumber, Str, AlternateNo, Footer, storeid,formattedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dayopen_report);
        helper = new DBhelper(this);

        username =  login.b.getString("Pos_User");

        actionBar=getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        tff = Typeface.createFromAsset(ReportDayOpenCash.this.getAssets(), "Fonts/DroidSansMono.ttf");


        TextView  Store_ID=(TextView) findViewById(R.id.Store_Storeidd) ;
        TextView Store_Name=(TextView) findViewById(R.id.store_name) ;
        TextView Store_Address =(TextView)findViewById(R.id.store_address) ;
        TextView Store_City=(TextView) findViewById(R.id.store_city) ;
        TextView Store_Country =(TextView)findViewById(R.id.store_country) ;

        DayOpenCash = (TextView)findViewById(R.id.dayStartCash);
        DayTotalSales = (TextView)findViewById(R.id.dayTotalSales);
        DayTotalSalesReturn=(TextView)findViewById(R.id.dayTotalSalesReturn);
        DayTotalofPurchase =(TextView)findViewById(R.id.dayTotalPurchasing);
        DayTotalVendorReturn = (TextView)findViewById(R.id.dayTotalVendorReturn);
        TotalFinancialLoss=(TextView)findViewById(R.id.TotalFinancialLoss);
        DayOfVendoPayment=(TextView)findViewById(R.id.dayTotalPayment);
        DayTotalCreditAmount=(TextView)findViewById(R.id.dayTotalCredit);
        TotalFinancialLoss=(TextView)findViewById(R.id.TotalFinancialLoss);
        print = (Button)findViewById(R.id.financial_print);
        ////////////////////////////////////////////////////////////////////////////////////////////

        Visibility visible = db.getStorevisibility();
        paybycashbillcopy = Integer.parseInt(visible.getBillcopy());
        ////////////////////////////////////////////////////////////////////////////////////////////
        String result = "";
        // get column value

        helper = new DBhelper(ReportDayOpenCash.this);
        Store values = helper.getStoreDetails();

        Store_ID.setText(values.getStoreId());
        Store_Name.setText(values.getStoreName());
        Store_Address.setText(values.getStoreAddress());
        Store_City.setText(values.getStorecity());
        Store_Country.setText(values.getStorecountry());


        Cursor day_cash = helper.getDayCashReport();
        if (day_cash.moveToNext())
            result = String.valueOf(day_cash.getDouble(day_cash.getColumnIndex("DayCashTotal")));
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        value = Double.valueOf(result);
        DayOpenCash.setText(decimalFormat.format(value));

        Cursor grand_total = helper.getDailyTotalSalesReport();
        if (grand_total.moveToNext())
            result = String.valueOf(grand_total.getDouble(grand_total.getColumnIndex("SalesTotal")));
        value1 = Double.valueOf(result);
        DayTotalSales.setText(decimalFormat.format(value1));

        Cursor GrandTotalForSalesReturn=helper.getDailySalesReturnTotal();
        if (GrandTotalForSalesReturn.moveToNext())
            result = String.valueOf(GrandTotalForSalesReturn.getDouble(GrandTotalForSalesReturn.getColumnIndex("SalesReturnTotal")));
        value2 = Double.valueOf(result);
        DayTotalSalesReturn.setText(decimalFormat.format(value2));


        Cursor GrandtotalPurhasing=helper.getDailyPurchaseTotal();
        if (GrandtotalPurhasing.moveToNext())
            result = String.valueOf(GrandtotalPurhasing.getDouble(GrandtotalPurhasing.getColumnIndex("PurchasingTotal")));
        value3 = Double.valueOf(result);
        DayTotalofPurchase.setText(decimalFormat.format(value3));

        Cursor GrandtotalVendorPayment=helper.getDailyVendorpaymentTotal();
        if (GrandtotalVendorPayment.moveToNext())
            result = String.valueOf(GrandtotalVendorPayment.getDouble(GrandtotalVendorPayment.getColumnIndex("PaymentTotal")));
        value4 = Double.valueOf(result);
        DayOfVendoPayment.setText(decimalFormat.format(value4));

        Cursor vendorreturn_total = helper.getDailyTotalVendorReturnReport();
        if (vendorreturn_total.moveToNext())
            result = String.valueOf(vendorreturn_total.getDouble(vendorreturn_total.getColumnIndex("VendorReturnTotal")));
        value5 = Double.valueOf(result);
        DayTotalVendorReturn.setText(decimalFormat.format(value5));

        Cursor GrandTotalCreditAmount=helper.getDailyCreditTotal();
        if (GrandTotalCreditAmount.moveToNext())
            result = String.valueOf(GrandTotalCreditAmount.getDouble(GrandTotalCreditAmount.getColumnIndex("CreditTotal")));
        value6 = Double.valueOf(result);
        DayTotalCreditAmount.setText(decimalFormat.format(value6));

        Cursor financial_loss = helper.getFinancialLossReport();
        if (financial_loss.moveToNext())
            result = String.valueOf(financial_loss.getDouble(financial_loss.getColumnIndex("TotalFinancialLoss")));
        value7 = Double.valueOf(result);
        TotalFinancialLoss.setText(decimalFormat.format(value7));
        Log.e("TotalLoss",TotalFinancialLoss.toString());


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                printtest();
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
        }catch (Exception Ex){}
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

                Intent i=new Intent(ReportDayOpenCash.this, ActivityLoyalityCust.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                help_dayopenclose_reports_dialog();
                return true;
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ReportDayOpenCash.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ReportDayOpenCash.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in=new Intent(ReportDayOpenCash.this,activity_inventorywithpo.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportDayOpenCash.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ReportDayOpenCash.this ,login.class);
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



    public void printtest()
    {
                tp.setColor(Color.BLUE);
                tp.setTextSize(26);
                tp32.setTextSize(40);
                tp36.setTextSize(34);
                tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));
                tp.setTypeface(tff);

                store_name=db.getAllStores();
                storeid=store_name.get(0).toString();
                store=store_name.get(1).toString();
                storeAddress=store_name.get(2).toString();
                City=store_name.get(3).toString();
                AlternateNo = store_name.get(5).toString();
                Footer = store_name.get(6).toString();
                Storenumber = store_name.get(4).toString();

                DecimalFormat f = new DecimalFormat("##.00");
                for(int i = 1;i<=paybycashbillcopy;i++)
                {
                    StringBuilder strbuilder = new StringBuilder();
                    strbuilder.append(store + "\n");
                    strbuilder.append(storeAddress + "\n");
                    strbuilder.append(City);
                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                    strbuilder.setLength(0);

                    strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");
                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                    strbuilder.setLength(0);
                    formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                    strbuilder.append(formattedDate);

                    UsPrinter.addText(strbuilder.toString(),Layout.Alignment.ALIGN_OPPOSITE,tp);
                    strbuilder.setLength(0);

                    strbuilder.append(getDividerReport());
                    UsPrinter.addText(strbuilder.toString(),Layout.Alignment.ALIGN_CENTER,tp);
                    strbuilder.setLength(0);

                    strbuilder.append("Financial Report");
                    UsPrinter.addText(strbuilder.toString(),Layout.Alignment.ALIGN_CENTER,tp32);
                    strbuilder.setLength(0);
                    strbuilder.append(getDividerReport());


                    int space=3;

                    strbuilder.append("Day Start Cash       : " +getSpacer(space) + String.valueOf(f.format(value)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Total Sales          : " + getSpacer(space) + String.valueOf(f.format(value1)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Total Sales Return   : "+ getSpacer(space) + String.valueOf(f.format(value2)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Total Vendor Payment : "+ getSpacer(space) + String.valueOf(f.format(value4)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Total Purchasing     : "+ getSpacer(space) + String.valueOf(f.format(value3)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Total Credit         : "+ getSpacer(space) + String.valueOf(f.format(value6)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Vendor Return        : "+  getSpacer(space) +String.valueOf(f.format(value5)));
                    strbuilder.append(ReportDayOpenCash.this.eol);

                    strbuilder.append("Financial Loss       : "+ getSpacer(space) + String.valueOf(f.format(value7)));
                    strbuilder.append(ReportDayOpenCash.this.eol);


                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                    strbuilder.setLength(0);

                    strbuilder.append(getDividerReport());
                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                    strbuilder.setLength(0);

                    UsPrinter.print();
                    UsPrinter.FeedLine();
                    // UPrinter.FeedLine();
                    UsPrinter.FullCut();
            }


    }

    private String getDividerReport() {
        //this for ls printer
        return "-----------------------------------  ";
    }
    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }
}


