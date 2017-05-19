
        package com.RSPL.POS;

        import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
        import android.support.v4.view.MenuItemCompat;
        import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.USBPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

import Adapter.SalesReturnAdapter;
import Adapter.SalesReturnProductAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
        import Pojo.Registeremployeesmodel;
        import Pojo.Salesreturndetail;
import Pojo.Visibility;
import Pojo.displaypojo;
public class ActivitySalesreturn extends Activity
{

    public static String username = null;
    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;


    ArrayList<Salesreturndetail> Returnlist;
    SalesReturnAdapter ReturnListAdapter;
    AlertDialog dialog;
    DBhelper mydb;
    ListView listView;
    TextView invoice ;
    Button clear,submit;
    Spinner reason;
    private TextView Grandtotal,discount;
    String item,transactionid;
    ActionBar actionBar;
    EditText LastInvoiceno;
    private TextWatcher  ProductNameTextWatcher;
    AutoCompleteTextView ProductName;
    SalesReturnProductAdapter productNmAdaptor;
    ArrayList<Salesreturndetail> ProductNameArrayList;
    Bundle syncDataBundle = null;
    String Totalitems;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String retail_salesreturn_op = "sh /sdcard/sales_return.sh";
    String superuser ="su";
    PendingIntent mPermissionIntent;
    UsbManager mUsbManager;
    UsbDevice device;
    UsbController usbController;

    protected static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";

    String MRPisShown,FooterShown,Tele2Shown;
    int   paybycashbillcopy,creditbillcopy,ReturnBillCopy;
    private static final int PRINT_COLUMN = 40;
    private static final int PRINT_LEFT_MARGIN = 2;
    private String eol = "\n";
    private static final int BILL_DATE_COLUMN = 14;
    String TOTAL_ITEMS = "Total Items:";
    String BILL_TOTAL = "Bill Total:";
    String BILL_DISC = "Discount:";
    String NETPAYAMOUNT = "Net Amount:";
    String RECEIVEAMOUNT = "Recieved Amount:";
    String BALANCEAMOUNT = "Balance to be Paid:";
    String CREDIT_BILL_TOTAL = "Total Credit Amount:";
    String TOTALSAVINGS = "Total Savings:";
    String DUEAMOUNT = "Due Amount:";

    public static USBPrinter UPrinter = USBPrinter.INSTANCE;
    public static SharedPreferences mSp;
    TextPaint tp = new TextPaint();
    TextPaint tp32 = new TextPaint();
    TextPaint tp36 = new TextPaint();
    Typeface tff;


    String iteam;
    TextView user2;


    TextView tvproductname,tvexp,tvbatch,tvsprice,tvqty,tvdisc,tvuom,tvtotal,tvreason,tvdiscount,tvgrand;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_salesreturn);

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();
        device = deviceList.get("deviceName");
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext()){
            device = deviceIterator.next();
            //  mUsbManager.requestPermission(device,mPermissionIntent);
        }
        registerReceiver(mUsbReceiver, filter);

        mydb = new DBhelper(ActivitySalesreturn.this);
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        ////////////////jimmmyyy//////////////////////////
        tvproductname = (TextView) findViewById(R.id.product6);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvexp = (TextView) findViewById(R.id.product7);
        tvexp.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) findViewById(R.id.product8);
        tvbatch.setTextSize(Float.parseFloat(textsize));

        tvsprice = (TextView) findViewById(R.id.product9);
        tvsprice.setTextSize(Float.parseFloat(textsize));

        tvdisc = (TextView) findViewById(R.id.product14);
        tvdisc.setTextSize(Float.parseFloat(textsize));

        tvqty = (TextView) findViewById(R.id.product9se);
        tvqty.setTextSize(Float.parseFloat(textsize));

        tvuom = (TextView) findViewById(R.id.product10);
        tvuom.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) findViewById(R.id.totalitems);
        tvtotal.setTextSize(Float.parseFloat(textsize));

        tvreason = (TextView) findViewById(R.id.tv_reason);
        tvreason.setTextSize(Float.parseFloat(textsize));

        tvgrand = (TextView) findViewById(R.id.grandtotal);
        tvgrand.setTextSize(Float.parseFloat(textsize));


        tvdiscount = (TextView) findViewById(R.id.totalitems);
        tvdiscount.setTextSize(Float.parseFloat(textsize));



        listView = (ListView)findViewById(R.id.lv_PurchaseProduct);
        invoice = (TextView)findViewById(R.id.invoiceno);
        Grandtotal=(TextView)findViewById(R.id.GrandTotal);
        Grandtotal.setTextSize(Float.parseFloat(textsize));


        reason = (Spinner)findViewById(R.id.reasonreturn);
        clear = (Button)findViewById(R.id.clear);
        submit = (Button)findViewById(R.id.salesreturn_submit);
        discount = (TextView) findViewById(R.id.discountsalereturn);
        discount.setTextSize(Float.parseFloat(textsize));

////////////////////////////////////////////////////////////////////////////////////////////////
        tff = Typeface.createFromAsset(ActivitySalesreturn.this.getAssets(), "Fonts/DroidSansMono.ttf");
        ///////////////////////////////////////////////////////////////////////////////////////////////
        mydb=new DBhelper(this);
        Visibility value = mydb.getStorevisibility();
        MRPisShown=value.getMrpvisibility();
        FooterShown=value.getFootervisi();
        Tele2Shown=value.getTele2();
        paybycashbillcopy=Integer.parseInt(value.getBillcopy());
        creditbillcopy=Integer.parseInt(value.getCreditcopy());
        ReturnBillCopy=Integer.parseInt(value.getReturncopy());
////////////////////////////////////////////////////////////////////////////////////////////////
        displaypojo billDisplayModel = mydb.getStoredisplaydetail();

        BILL_TOTAL= billDisplayModel.getTotalbillvalue();
        BILL_DISC=billDisplayModel.getDiscount();
        NETPAYAMOUNT=billDisplayModel.getNetbillpayable();
        RECEIVEAMOUNT=billDisplayModel.getAmountreceived();
        BALANCEAMOUNT=billDisplayModel.getAmountpaidback();

////////////////////////////////////////////////////////////////////////////////////////////////
        username =  login.b.getString("Pos_User");

        ProductName = (AutoCompleteTextView) findViewById(R.id.autoProductName);
        //  ProductName.setThreshold(3);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        SelectPurchaseAlertDialog();
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearbuttondialog();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
        RemoteVideoPresentation.showBill();
        showBill();

        final DBhelper mydb=new DBhelper(ActivitySalesreturn.this);
        ArrayList<Salesreturndetail> reasonReturn= mydb.getReasonReturn();
        ArrayAdapter<Salesreturndetail > stringArrayAdapter =
                new ArrayAdapter<Salesreturndetail>(ActivitySalesreturn.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
        reason.setAdapter(stringArrayAdapter);
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int pos = 0;

                    for (Salesreturndetail item : ReturnListAdapter.getList()) {
                        if (item.getSaleqty() <= 0) {
                            //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }
                    if (ReturnListAdapter.getList().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No Item To Return", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mydb.insertsalesreturn(invoice.getText().toString(), ReturnListAdapter.getList(), item.toString(),Grandtotal.getText().toString(),user2.getText().toString());
                    mydb.insertdataIntosendMailforSalesReturn(invoice.getText().toString(),user2.getText().toString());
                    mydb.insertsalereturndataforproductdetail(invoice.getText().toString(), ReturnListAdapter.getList(),user2.getText().toString());
                    mydb.updateqtyforinvoice(ReturnListAdapter.getList());
                    mydb .updateflagforreturn(invoice.getText().toString());

                    UpdateSalesReturnwithinvoice();
                    UpdateShell();


//////////////////////////////////////Printer testing ///////////////////////////////////////
                    try { ArrayList<String> store_name = mydb.getAllStores();
                        String  store = (store_name.get(1).toString());
                        String  storeAddress = store_name.get(2).toString();
                        String  City = store_name.get(3).toString();
                        String  Storenumber = store_name.get(4).toString();
                        String  AlternateNo = store_name.get(5).toString();
                        String  Footer=store_name.get(6).toString();
                        String formattedDate;
                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));
                        tp.setTypeface(tff);
                        if (UsPrinter.getState()==0) {
                            UsPrinter.showDeviceList(ActivitySalesreturn.this);
                        }
                        for (int i=1;i<=ReturnBillCopy;i++) {
                            DecimalFormat f = new DecimalFormat("##.00");
                            StringBuilder strbuilder = new StringBuilder();
                            strbuilder.append(store + "\n");
                            strbuilder.append(storeAddress + "\n");
                            strbuilder.append(City + "\n");
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                            strbuilder.setLength(0);
                            if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {
                                strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");

                            } else {
                                strbuilder.append("Tel:" + Storenumber);
                                strbuilder.append(ActivitySalesreturn.this.eol);
                            }
                            /*UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);*/
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                            strbuilder.setLength(0);
                            formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                            int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(LastInvoiceno.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                            strbuilder.append("Bill No:");
                            strbuilder.append(LastInvoiceno.getText().toString());
                            strbuilder.append(getSpacer(billDtSpace));
                            strbuilder.append(formattedDate);
                            strbuilder.append(ActivitySalesreturn.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesreturn.this.eol);
                            if (MRPisShown.matches("Y")) {
                                strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                                strbuilder.append(ActivitySalesreturn.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesreturn.this.eol);
                                for (Salesreturndetail prod : ReturnListAdapter.getList()) {
                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getSaleproductname());
                                    strbuilder.append(ActivitySalesreturn.this.eol);
                                    String printMrp =prod.getSalemrp();
                                    int spaces = 9 - printMrp.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printSPrice = String.valueOf(f.format(prod.getSalesellingprice()));
                                    spaces = 8 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getSaleqty());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printtotal = String.valueOf(f.format(prod.getSaletotal()));
                                    spaces = 7 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                    strbuilder.append(ActivitySalesreturn.this.eol);
                                    strbuilder.append(ActivitySalesreturn.this.eol);


                                }
                            } else {
                                strbuilder.append("   S.Price" + "       " + "Qty" + "       " + "Total");
                                strbuilder.append(ActivitySalesreturn.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesreturn.this.eol);
                                for (Salesreturndetail prod : ReturnListAdapter.getList()) {
                                    strbuilder.append(prod.getSaleproductname());
                                    strbuilder.append(ActivitySalesreturn.this.eol);
                                    String printSPrice = String.valueOf(f.format(prod.getSalesellingprice()));
                                    int spaces = 10 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }

                                    String printqty = String.valueOf(prod.getSaleqty());
                                    spaces = 7 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printtotal = String.valueOf(f.format(prod.getSaletotal()));
                                    spaces = 12 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                    strbuilder.append(ActivitySalesreturn.this.eol);
                                    // strbuilder.append(ActivitySalesbill.this.eol);
                                }
                            }
                            strbuilder.append(getDividerSales());

                            String getFooterpart = FooterValueFullBill();
                            strbuilder.append(getFooterpart);
                            UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                            strbuilder.setLength(0);

                            if (FooterShown.matches("Y")) {
                                int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                                strbuilder.append(Footer);
                                strbuilder.append(ActivitySalesreturn.this.eol);
                            }
                            UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            UPrinter.print();
                            UPrinter.FeedLine();
                            // UPrinter.FeedLine();
                            UPrinter.FullCut();

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(),invoice.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);

                } catch (NullPointerException ex){
                    ex.printStackTrace();
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });
    }

    public void writeData(ArrayList<Salesreturndetail> sales)
    {



        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method","AddSalesReturn");

            jsonObject.put("ArrayList",sales);
            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public void setCustomerName(String customerName){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Method","CustomerAdd");
            jsonObject.put("Name",customerName);
            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void deleteData(int sales)
    {

        int i = 0;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method","Delete");
            jsonObject.put("PositionDelete",sales);

            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void updateQuantity(int position,int quantity)
    {

        int i = 0;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method","Quantity");
            jsonObject.put("QuantityUpdate",quantity);
            jsonObject.put("Position",position);

            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
            Log.d("JSONObject", String.valueOf(jsonObject));
        } catch (JSONException e) {

        }


    }
    public void fullScreen(){

        Log.d("FullScreen","Entering full Screen mode");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method","FullScreen");

            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
        } catch (JSONException e) {
        }
    }
    public void showBill(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method","ShowBill");
            UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
            UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
        } catch (JSONException e) {
        }
    }
    public void startReading(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method","StartReading");
        } catch (JSONException e) {
        }
    }

    private class TalkThread extends Thread
    {
        private AtomicBoolean working;

        TalkThread()
        {
            working = new AtomicBoolean(true);
        }

        @Override
        public void run() {
            try {

                    /*    UsPrinter.writeDataToSerialPort(hello);
                        UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
                        Log.i("NGX","Write Data");*/
                Thread.currentThread().sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stopThread()
        {
            working.set(false);
            Log.i("NGX","Data write stopped");
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        // unregisterReceiver(mUsbReceiver);
        fullScreen();
    }

    public void SelectPurchaseAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();

        final View alertLayout = inflater.inflate(R.layout.alert_dialog_salesreturn, null);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button submit = (Button) alertLayout.findViewById(R.id.invoice_submit_button);
        final Button NewPurchase = (Button) alertLayout.findViewById(R.id.Noinvoice);
        final Button OldPurchase = (Button) alertLayout.findViewById(R.id.Invoiceno) ;
        LastInvoiceno = (EditText) alertLayout.findViewById(R.id.EnterInvoiceno);
        final Button cancel=(Button)alertLayout.findViewById(R.id.Cancel);
        LastInvoiceno.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.VISIBLE);


        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("SELECT INVOICE OPTION");
        dialog = alert.create();
        OldPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LastInvoiceno.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.VISIBLE);
            }
        });

        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(LastInvoiceno.getWindowToken(), 0);
                return true;
            }

        });

        NewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_Salesreturn_withoutinvoiceno.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String userTypedInvoiceno = LastInvoiceno.getText().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + LastInvoiceno.toString());
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }

                    final DBhelper mydb = new DBhelper(ActivitySalesreturn.this);
//                    if (mydb.CheckIsInvoiceReturn(invoice.getText().toString()))
//                    {
//                        Toast.makeText(getApplicationContext(),"Invoice Alredy return", Toast.LENGTH_SHORT).show();
//                        return;
//                    }


                    Returnlist = mydb.getSalesReturn(userTypedInvoiceno);

                    if (Returnlist.isEmpty())
                    {
                        Toast.makeText(ActivitySalesreturn.this, "Already Return Or Not Found", Toast.LENGTH_SHORT).show();
                        return;

                    }
                    Log.d("!@#!@#!@#!@#", "Product arraylist size is " + Returnlist.size());

                    if (Returnlist != null && Returnlist.size() > 0) {
                        if (ReturnListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            ReturnListAdapter = new SalesReturnAdapter(ActivitySalesreturn.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Salesreturndetail>());
                            listView.setAdapter(ReturnListAdapter);
                            String res = LastInvoiceno.getText().toString();
                            invoice.setText(res);
                        }
                        for (Salesreturndetail prod : Returnlist) {
                            ReturnListAdapter.addProductToList(prod);

                        }
                        RemoteVideoPresentation.AddSalesReturnProducttoListWithPo(Returnlist);

                        ReturnListAdapter.notifyDataSetChanged();
                        grandtotal();
                        grandDiscount();
                        dialog.dismiss();
                    }

                }catch (NullPointerException ex) {
                    ex.printStackTrace();

                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldPurchase.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                startActivity(intent);

            }
        });

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


    }
    protected final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                        }
                    }
                    else {
                        Log.d("Test99", "permission denied for device " + device);
                    }
                }
            }
        }
    };

    protected final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbController.USB_CONNECTED:
                    Toast.makeText(getApplicationContext(),"Permission Granted Successfully",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }

    public void grandtotal()
    {
        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        Grandtotal.setText(GrandVal);
        int Getitem = ReturnListAdapter.getCount();
        int Allitem = Getitem;
        //String GETITEM = Integer.toString(Allitem);
        Totalitems=Integer.toString(Allitem);


    }


    public  void clearbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        //     alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {
                    ReturnListAdapter.clearAllRows();
                    invoice.setText("");
                    Grandtotal.setText("");
                    discount.setText(".0");

                    RemoteVideoPresentation.clearTotal();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public String toString() {
        return item;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);


        final MenuItem item2 = menu.findItem(R.id.TextView);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        user2 = (TextView) MenuItemCompat.getActionView(item2);


        // user2.setText(username);



        mydb=new DBhelper(ActivitySalesreturn.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivitySalesreturn.this, android.R.layout.simple_spinner_dropdown_item,user_name);

        spinner.setAdapter(stringArrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.d("slected item   :  ", iteam);

                if (!user2.getText().toString().trim().isEmpty()) {
                    iteam = spinner.getSelectedItem().toString();


//                    user2.setText("");
                    user2.setText(iteam );
                }

                else {

                    user2.setText(username);
                }



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        return true;


    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
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
        switch (item.getItemId()) {
            case R.id.action_settings:


                Intent i=new Intent(ActivitySalesreturn.this,Activity_masterScreen1.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivitySalesreturn.this);
                showIncentive.show();
                return true;

            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ActivitySalesreturn.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/ case R.id.action_Masterscn1:
                Intent p = new Intent(ActivitySalesreturn.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;


            case R.id.action_settinginv:
                Intent in=new Intent(ActivitySalesreturn.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivitySalesreturn.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivitySalesreturn.this ,login.class);
        startActivity(intent);
    }
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public void grandDiscount()
    {
        int pos = 0;
        for (Salesreturndetail item : ReturnListAdapter.getList()) {
            String Dissalereturn = item.getDiscount();
            discount.setText(Dissalereturn);
            pos++;
        }
    }


    public void UpdateSalesReturnwithinvoice() {




        for (Salesreturndetail purchase : ReturnListAdapter.getList()) {

            final DBhelper mydb = new DBhelper(ActivitySalesreturn.this);
            PersistenceManager.saveStoreId(ActivitySalesreturn.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
            final String save_sales_store_id = PersistenceManager.getStoreId(ActivitySalesreturn.this);



            Long Value = System.currentTimeMillis() + 1000;
            final String id = String.valueOf(Value);
            transactionid = invoice.getText().toString();
            final String reasonofreturn = item.toString();
            final String grandtotal = String.valueOf(purchase.getGrandTotal());
            final String prodname = purchase.getSaleproductname();
            final String batchno = purchase.getSalebatchno();
            final String expirydate = purchase.getSaleexpiry();
            final String sellingprice = String.valueOf(purchase.getSalesellingprice());
            final String mrp = purchase.getSalemrp();
            final String uom = purchase.getSaleuom();
            final String qty = String.valueOf(purchase.getSaleqty());
            final String total = String.valueOf(purchase.getSaletotal());
            final String pos_user = user2.getText().toString();

//            PersistenceManager.saveStoreId(ActivitySalesreturn.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));

            class Updatesalesreturn extends AsyncTask<Void, Void, String> {

                ProgressDialog loading;
                int success;


                @Override
                protected String doInBackground(Void... params) {
                    try{
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_ID, id);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_TRI_ID, transactionid);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_STORE_ID, save_sales_store_id);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_GRAND_TOTAL, grandtotal);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_REASON_OF_RETURN, reasonofreturn);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_PROD_NAME, prodname);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_BATCH_NO, batchno);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_EXP_DATE, expirydate);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_SELLING_PRICE, sellingprice);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_QUANTITY, qty);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_MRP, mrp);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_UOM, uom);
                        hashMap.put(Config. STR_SALES_MASTER_RETURN_TOTAL,total);
                        hashMap.put(Config.Retail_Pos_user,pos_user);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_DATE,getDate());
                        hashMap.put(Config.SAVE_LIST_PREFIX_NM,"SalesReturn-");
                        hashMap.put(Config.SAVE_LIST_PO_No,transactionid);




                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.SALES_MASTER_RETURN, hashMap);
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());
                            mydb.updatesalesflagdetailreturn(transactionid);
                            mydb.updateflagsavesalesMasterreturn(transactionid);
                            mydb.updateflagSavePdfDetailForsalereturn(transactionid);



                            return s.getString(TAG_MESSAGE);
                        } else {

                            return s.getString(TAG_MESSAGE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;                }
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //   loading = ProgressDialog.show(ActivitySalesreturn.this, "UPDATING.SERVER....", "Wait...", false, false);
                }
                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //  loading.dismiss();
                    // Toast.makeText(ActivitySalesreturn.this, s, Toast.LENGTH_LONG).show();
                }
            }

            Updatesalesreturn returnsale = new Updatesalesreturn();
            returnsale.execute();
        }
    }


    public void UpdateShell() {


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
                    executerInventory(superuser);
                    String s = executerInventory(retail_salesreturn_op);
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


    public String executerInventory(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/sales_return.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }
    public String HeaderValueFullBill(){
        ArrayList store_name = mydb.getAllStores();
        String  store = (store_name.get(1).toString());
        String  storeAddress = store_name.get(2).toString();
        String  City = store_name.get(3).toString();
        String  Storenumber = store_name.get(4).toString();
        String  AlternateNo = store_name.get(5).toString();
        String formattedDate;
        StringBuilder strbuilder=new StringBuilder();
        int spaceSide = 3 + (40 - store.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(ActivitySalesreturn.this.eol);
        strbuilder.append(getSpacer(spaceSide) + store + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesreturn.this.eol);
        spaceSide = 3 + (40 - storeAddress.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(getSpacer(spaceSide) + storeAddress + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesreturn.this.eol);
        spaceSide = 3 + (40 - City.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(getSpacer(spaceSide) + City + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesreturn.this.eol);
        spaceSide = 3 + (40 - Storenumber.length()) / PRINT_LEFT_MARGIN;
        if (!TextUtils.isEmpty(AlternateNo)&&Tele2Shown.matches("Y")) {
            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN * 3) + "  Tel:" + Storenumber + "," + AlternateNo + getSpacer(spaceSide));
            strbuilder.append(ActivitySalesreturn.this.eol);

        } else {
            strbuilder.append(getSpacer(spaceSide) + "Tel:" + Storenumber + getSpacer(spaceSide));
            strbuilder.append(ActivitySalesreturn.this.eol);
        }
        formattedDate = new SimpleDateFormat("EEE,dd MMM yy hh:mma").format(Calendar.getInstance().getTime());
        int billDtSpace = 3 + 40 - ((String.valueOf(LastInvoiceno.getText().toString()).length() + BILL_DATE_COLUMN) + formattedDate.length());
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:  ");
        strbuilder.append(LastInvoiceno.getText().toString());
        strbuilder.append(getSpacer(billDtSpace));
        strbuilder.append(formattedDate);
        strbuilder.append(ActivitySalesreturn.this.eol);
        strbuilder.append(getDividerSales());
        strbuilder.append(ActivitySalesreturn.this.eol);

        return String.valueOf(strbuilder);
    }

    public String FooterValueFullBill()
    {
        StringBuilder strbuilder=new StringBuilder();
        ArrayList store_name = mydb.getAllStores();

        DecimalFormat f = new DecimalFormat("##.00");
        String Roundoffvalue = String.valueOf(f.format(Double.parseDouble(Grandtotal.getText().toString())));
        int spaces = 10 - String.valueOf(Totalitems).length();
        int amtspaces = 9 - Roundoffvalue.length();
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + TOTAL_ITEMS+Totalitems+getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue+ getSpacer(PRINT_LEFT_MARGIN));
        strbuilder.append(ActivitySalesreturn.this.eol);

        return String.valueOf(strbuilder);
    }

    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }
    private String getDividerSales() {
        return "----------------------------------  ";
    }

}

