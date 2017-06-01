package com.RSPL.POS;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.serialport.api.SerialPortHelper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.USBPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;


import Adapter.CreditNoteAdapter;
import Adapter.CustomerSalesAdapter;
import Adapter.Saledoctorsadapter;
import Adapter.SalesAdapter;
import Adapter.SalesProductNmAdaptor;
import Adapter.SearchproductAdapter;
import Adapter.TabsPagerAdapter;
import Adapter.localsalesproductadapterdialog;
import Config.Config;
import Fragments.FastMovingFragment;
import JSON.JSONParserSync;
import Pojo.CreditNote;
import Pojo.Customer;
import Pojo.Decimal;
import Pojo.DoctorPojo;
import Pojo.Inventoryproductmodel;
import Pojo.LocalProduct;
import Pojo.Registeremployeesmodel;
import Pojo.Sales;
import Pojo.Visibility;
import Pojo.displaypojo;

public class ActivitySalesbill extends FragmentActivity implements View.OnClickListener,
        ActionBar.TabListener {
    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;
    public static SerialPortHelper UssPrinter = login.mSerialPortHelper;
    public USBPrinter UsbPrinter = USBPrinter.INSTANCE;
    private TalkThread talkThread;
    private String mConnectedDeviceName = "";
    public static final String title_connecting = "connecting...";
    public static final String title_connected_to = "connected: ";
    public static final String title_not_connected = "not connected";
    String linevaluediscount;
    String iteam;
    TextView user2;

    @SuppressLint("HandlerLeak")
    private final Handler Handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USBPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case USBPrinter.STATE_CONNECTED:
                            Log.d("Connected to", title_connected_to);
                            break;
                    /*case USBPrinter.STATE_CONNECTING:
                  tvStatus.setText(title_connecting);
                  break;
               case USBPrinter.STATE_LISTEN:*/
                        case USBPrinter.STATE_NONE:
                            Log.d("Not Connected", "No Success");
                            break;

                        //Bellow status message should be shown in different textview.
                        case USBPrinter.ACTION_SERIALPORT_CONNECTED:
                            Log.d("Connected", "Success Serial Port");
                            break;
                        case USBPrinter.ACTION_SERIALPORT_DISCONNECTED:


                            Log.d("DisConnected", "Serial Port Disconnected");
                            break;
                        case USBPrinter.ACTION_SERIALPORT_NOT_SUPPORTED:
                            Log.d("Invalid", "Not Supported");
                    }
                    break;
                case USBPrinter.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(
                            USBPrinter.DEVICE_NAME);
                    break;
                case USBPrinter.MESSAGE_STATUS:
                    Log.d("Status", msg.getData().getString(
                            USBPrinter.STATUS_TEXT));
                    break;

//for weighing scale
         /*case UsbSerialService.MESSAGE_FROM_SERIAL_PORT:
            String data = msg.getData().getString(UsbSerialService.WEIGHT_TEXT);
            //UnicodeFragment.showWeight.setText(data);
            TalkEachOtherFragment.txtReadData.append(data);

            break;*/
                default:
                    break;
            }
        }
    };

    ///////////////////////////////////////////////printer code  ngx/////////////////////////////////////////


    public static SharedPreferences mSp;
    TextPaint tp = new TextPaint();
    TextPaint tp32 = new TextPaint();
    TextPaint tp36 = new TextPaint();
    Typeface tff;
    private TextView tvStatus;
    private Button Creditclose;
    Float ValueBeforeDiscount;

    ///////////////////////////////////////////////////////////////////reprint//////////////////////////


    Button Reprint_button;
    EditText reprint_bill_dialog_text;
    ArrayList<Sales> get_Transcation_List;
    //!!!!!!!!*************************SearchProductDialogVariable*****************************************
    TextView SearchProductNameDialog, searchuom;
    AutoCompleteTextView searchproductname;
    EditText searchbatch, searchexpdate, searchmrp, searchsprice, searchqty;
    Button searchsubmit, searchexit;

    AlertDialog dialog2;
    String stest, Qtyval;
    TextWatcher netextw, mTextWatcher, OverAllDiscount;
    boolean batchcheck;
    ArrayList<Sales> arrayList;
    ArrayList<LocalProduct> localprodaddarraylist;
    AlertDialog.Builder searchalert;
    DBhelper db = new DBhelper(this);
//!!!!!!!!!!!!!!!!!!!!!!!!!************** END*******************************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // !!!!!!!!!!!!!!!!!!!!!!!!! UPLOAD SERVER CODE VARIABLE``!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String grand_total, sale_date, sale_time, saleimeino, save_sales_prod_nm, save_sales_expiry_date, save_sales_s_price,
            save_sales_qty, save_sales_mrp, save_sales_batch_no, store_ID, save_sales_uom, save_sales_total, save_sales_store_id;
    float stockQuantity, newStockQuantity, newstockQuant, newstock;
    int saleQuantity;
    // *******************************END********************************************

    //!!!!!!!!!!!!!!!!!!!!!!!!!! CREDIT CUSTOMER BUTTON VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    String Valuecredit, Customername, Grandtotal, MobileNo;

    Button Creditcustomer;
    ArrayList<String> store_name;
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    // !!!!!!!!!!!!!!!!!!!!!!!!!PRINTER VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    String store, storeAddress, City, Storenumber, Str, AlternateNo, Footer, amountdiscount;
    StringBuilder strbuilder = new StringBuilder();

    //!!!!!!!!!!!!!!!!!!!!!! END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    String GrnIDForLocal;
//!!!!!!********************** ADD PRODUCT DIALOG!!!!!!!!!!!!!!!!!!!!!!!!!!!

    Button Doctorupdate, docclear, Doctorexit;
    EditText doctname, doctaddress;
    Spinner specialization, Active;
    AlertDialog doctordialog;
    ArrayList<String> doctorspecial;
    ArrayAdapter<String> stringArrayAdapter;
//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! HOLD BILL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    ArrayList<String> holdbilltransaction;
    ArrayAdapter<String> holdbilladapter;
    String holdspinneritem, Valuetrans;

// !!!!!!!!!!!!!!!!!!!!!! END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    // !!!!!!!!!!!!!!!!!!!!!! INVOICE NO METHOD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    Long Value;
    String result, invoicevalue, str;
    ArrayList<String> invoicebillno;
    ArrayList<String> imei;

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!!!!!!


//!!!!!!!!!!!!!!!!!!!!!! LOCAL PRODUCT DIALOG!!!!!!!!!!!!!!!!!!!!!!!!

    float selling, purchase, mrplocal;

// !!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! CREDIT NOTE DIALOG!!!!!!!!!!!!!!!!!!!!!!!!!

    AlertDialog.Builder alert;
    Button cleardialog, addtopay;
    TextView creditvalue, credittransid;
    AutoCompleteTextView CreditBillNo;
    AlertDialog dialog;
    Float Creditresult, CreditNotevalue, CredNotebillvalue;

//!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!

//!!!!!!!!!!!!!!!!!!!!!!!!!!ADDITION PRODUCT DIALOG!!!!!!!!!!!!!!!!!!!!!!!!

    AutoCompleteTextView searchauto;
    Button addnewcustomer, update, ClearDoctor, exit, exitcust;
    LinearLayout hidden, hidden2, hidden3, buttonlayout;
    TextView CustomerMobile, CUSTOMERNAME, CUSTOMERMOBILE, CUSTOMEREMAIL, CUSTOMERADRESS, CUSTOMERCREDIT;
    Spinner Credithidd;

//!!!!!!!!!!!!!!!!!!!!!!!!!!!! END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    //*******************************************addd local product ********************************************************
    localsalesproductadapterdialog localadapter;
    ArrayList<LocalProduct> localproductlist;
    String ActiveTypesaleproduct[];
    ArrayAdapter<String> adapterActiveTypesaleproduct;
    String SpinValuesale, localProd_Id_To_Update, SpinValue2, SpinValue3, SpinValue4, localVendor_Id_To_Update;
    AutoCompleteTextView localproductname;
    EditText productname, localbarcode, localhmrp, localsprice, localpprice, localQauntity, localuom, localdiscount, localsearchexpdate, LocalBatchno;
    TextView productid, localmargin;
    Spinner localactive;

    ImageButton addlocalprod;

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!END!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    ///!!!!!!!!!!!!!!!Paycredit/debit card!!!!!!!!!!!!!!!!!!!!!!!!!!!///
    EditText cardno, cardholdername, cardno2, cardno3, cardno4;
    TextView cardgrandtotal;
    Button cardpay;
    RadioButton Creditcard, debitcard;
    String cardtype, BankNameSelected;

    ImageButton mastercard, amexcard;
    Spinner cardexpirytdate, cardexpirytdateyear, cardbankname;
    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!PAY BY CASH AND HOT KEY ALERT DIALOG
    Double PaySelling, PayPurchase;
    TextView discountlinelevel, HotGrandtotal, OverAllDisCount, OverallTotalDiscount;
    Button close, HotAlertSubmit;
    EditText discount;
    EditText discountamount;
    Float PayResult;

    LinearLayout container;
    SalesAdapter adapter;
    RecyclerView RecycleView;
    Calendar c = Calendar.getInstance();

    String item, itemdoctor, lineitemdiscount, DoctorSales;
    String CreditBill, custid;
    ScrollView scrollView1;
    ArrayList<Sales> holdinvoicelist;
    AutoCompleteTextView ProductName, doctor;
    SalesProductNmAdaptor productNmAdaptor;
    Saledoctorsadapter doctoradapterdropdown;
    ArrayList<DoctorPojo> doctorlist;

    ArrayList<Sales> ProductNameArrayList;
    CustomerSalesAdapter customerSalesAdapter;
    ArrayList<Customer> customerlist;
    EditText reciveamt;

    ImageButton addnewproduct, addnewdoct, search;

    Button submitBillDiscount;
    String save_sales_con_mul_qty;
    //HoldBillAdapter holdBillAdapter;
    TextView creditnoteretrieve, TotalGrandAmountPopup;
    CreditNoteAdapter creditNoteAdapter;
    ArrayList<CreditNote> CreditNotelist;
    String Store_Id;
    TextView Credit;
    String x_imei;
    TextView Totalsavings;
    Bundle syncDataBundle = null;
    Button clear, hold;
    float Getval;
    float Getline;
    float linediscamount;
    Button cashbt, chequebt, cardbt, creditnote, walletpayment, lostsale;
    TelephonyManager tel;
    TextView Sellinprice;
    Spinner holdspinner;
    TextView Mrp, mobileno;
    EditText Quantity;
    String name;
    ArrayList<Sales> Searchnamelist;
    SearchproductAdapter searchadapter;
    String ActiveType[], CreditActiveType[];
    String SpinValue, CreditSpinvalue;
    ArrayAdapter adapterActiveType, creditadapterActiveType;
    Sales resval;
    LocalProduct valuelocal;
    String finalgrandtotal;
    Calendar myCalendar = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH) + 1;
    int Day = c.get(Calendar.DAY_OF_MONTH);
    TextView expectedchang;
    String switchoffmachine = "su -c svc power shutdown";
    String off = "su";
    UsbManager mUsbManager;
    private static int Billnumber;
    PendingIntent mPermissionIntent;
    UsbDevice device;
    UsbController usbController;
    private TextWatcher salesTextWatcher, DiscountTextWatcher, DiscountAmountTextWatcher;
    private TextWatcher ProductNameTextWatcher;
    private TextWatcher cTextWatcher;
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private TextView GrandTotal, Transid, Billno, PosCust, imeibill;
    private TextView Totalitems, Finalgrandtotaldisp;
    private Button paybycash, paybycard,McashWallet;
    private int mTempPositionBeforeCalenderDialog = -1;
    TextView grandtotalafterdiscount, linediscount, LineDiscountAmount;
    String mrp;
    String pprice;
    String sprice, roundofff;
    ArrayAdapter<String> visibiltyActiveType;
    Spinner printbilladptr;
    EditText emailids;

    /*  <<<<<<<Updated upstream
      =======*/
    String MRPisShown, FooterShown, Tele2Shown, PrintBill;
    int paybycashbillcopy, creditbillcopy, ReturnBillCopy;
    private static final int PRINT_COLUMN = 40;
    private static final int PRINT_LEFT_MARGIN = 2;
    private String eol = "\n";
    private static final int BILL_DATE_COLUMN = 14;
    String TOTAL_ITEMS = "Total Items:";
    String BILL_TOTAL = "Bill Total:";
    String BILL_DISC = "Discount:";
    String LINE_BILL_DISCOUNT = "Line Disc  ";
    String NETPAYAMOUNT = "Net Amount:";
    String RECEIVEAMOUNT = "Recieved Amount:";
    String BALANCEAMOUNT = "Balance to be Paid:";
    String CREDIT_BILL_TOTAL = " Credit Amount:";
    String TOTALSAVINGS = "Total Savings:";
    String DUEAMOUNT = "Due Amount:";


    // >>>>>>>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    //Stashed changes
    // Tab titles

    private String[] tabs = {"Fast Moving"};
    private boolean firsttime = true;

    private DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub
                    validateDate(year, month + 1, day);
                }
            };

    private DatePickerDialog.OnDateSetListener date1 = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub
                    localvalidateDate(year, month + 1, day);
                }
            };


    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    public static String username = null;
    public static Bundle Bundle = new Bundle();
    //////////////////////////////////////printer code////////////////////////////////////////////////
    /////////////////////////////oldbill code///////////////////////(jimmy)

    Spinner oldspinner;
    ArrayAdapter<String> oldbilladapter;
    String oldspinneritem;
    ArrayList<Sales> oldinvoicelist;
    //////////////////////////////jimmmy (textsize)////////////////////////////////////////////////
    TextView tvproductname, tvexp, tvmrp, tvsprice, tvqty, tvdisc, tvstock, tvuom, tvtotal, tvdoctorname, tvtotalitem, tvgrand;

    String backroundcolour, colorchange;

////////////////////////////////////MCASH BUTTON //////////////////////////////////////////////////////////////////
    Button CashIn,PayForgoods,CashOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_salesbill);
  /*  BluetoothChatService bluetoothChatService;
      int i=login.setboolean();
       if(i==0)
       {  login.Pairdevice();

       }*/

        store_name = db.getAllStores();
        store = (store_name.get(1).toString());

        showBill(store);


        tff = Typeface.createFromAsset(ActivitySalesbill.this.getAssets(), "Fonts/DroidSansMono.ttf");
//************************************************decimal******************************************************

        Decimal value2 = db.getStoreprice();
        mrp = value2.getDecimalmrp();
        pprice = value2.getDecimalpprice();
        sprice = value2.getDecimalsprice();
        roundofff = value2.getRoundofff();
        String textsize = value2.getHoldpo();
        backroundcolour = value2.getColorbackround();
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

        //change
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


        username = login.b.getString("Pos_User");

        //  String us = userManagementActivity.getText(0).toString();


        //////////////////////////////printer code///////////////////////////////////////
/*

        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

        Log.d("Device", String.valueOf(deviceList));
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
*/

      /*  mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        while (deviceIterator.hasNext()) {
            device = deviceIterator.next();
            mUsbManager.requestPermission(device, mPermissionIntent);

        }
        registerReceiver(mUsbReceiver, filter);*/
        try {

            TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
            Billno = (TextView) findViewById(R.id.sales_billno);
            imeibill = (TextView) findViewById(R.id.sales_imeibill);
            TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tel.getDeviceId();
            Log.e("imei is :", device_id);
            Billno.setText("012345678912345");

            //////////////////////////jimmmyyy///////////////////////////

            tvproductname = (TextView) findViewById(R.id.productname);
            tvproductname.setTextSize(Float.parseFloat(textsize));

            tvexp = (TextView) findViewById(R.id.expirydate);
            tvexp.setTextSize(Float.parseFloat(textsize));

            tvmrp = (TextView) findViewById(R.id.Mrp);
            tvmrp.setTextSize(Float.parseFloat(textsize));

            tvsprice = (TextView) findViewById(R.id.sellingprice);
            tvsprice.setTextSize(Float.parseFloat(textsize));

            tvdisc = (TextView) findViewById(R.id.discount);
            tvdisc.setTextSize(Float.parseFloat(textsize));

            tvqty = (TextView) findViewById(R.id.Quantity);
            tvqty.setTextSize(Float.parseFloat(textsize));

            tvstock = (TextView) findViewById(R.id.stock);
            tvstock.setTextSize(Float.parseFloat(textsize));

            tvuom = (TextView) findViewById(R.id.uom);
            tvuom.setTextSize(Float.parseFloat(textsize));

            tvtotal = (TextView) findViewById(R.id.total);
            tvtotal.setTextSize(Float.parseFloat(textsize));

            tvdoctorname = (TextView) findViewById(R.id.tv_reason);
            tvdoctorname.setTextSize(Float.parseFloat(textsize));

            tvtotalitem = (TextView) findViewById(R.id.totalitems);
            tvtotalitem.setTextSize(Float.parseFloat(textsize));

            tvgrand = (TextView) findViewById(R.id.grandtotal);
            tvgrand.setTextSize(Float.parseFloat(textsize));


            RecycleView = (RecyclerView) findViewById(R.id.listView);
            LinearLayoutManager llm = new LinearLayoutManager(ActivitySalesbill.this);
            RecycleView.setLayoutManager(llm);
            RecycleView.setHasFixedSize(true);
            RecycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            RecycleView.setItemAnimator(new DefaultItemAnimator());
            Totalitems = (TextView) findViewById(R.id.totalitem_textt);
            Totalitems.setTextSize(Float.parseFloat(textsize));

            GrandTotal = (TextView) findViewById(R.id.grandtotal_textt);
            GrandTotal.setTextSize(Float.parseFloat(textsize));
            PosCust = (TextView) findViewById(R.id.sale_customer);
            paybycash = (Button) findViewById(R.id.paycash);
            //  paybycard = (Button) findViewById(R.id.paycard);

            //reciveamt=(EditText)findViewById(R.id.reciveamount);
            expectedchang = (TextView) findViewById(R.id.expectedchange);
            Transid = (TextView) findViewById(R.id.sales_invoiceno);
            Transid.setText(String.valueOf(getBillnumber()));


            ProductName = (CustomAuto) findViewById(R.id.autoProductName);
            creditnoteretrieve = (TextView) findViewById(R.id.creditnote);
            Sellinprice = (TextView) findViewById(R.id.sprice);
            Credit = (TextView) findViewById(R.id.sales_credit);
            Mrp = (TextView) findViewById(R.id.mrp);
            Quantity = (EditText) findViewById(R.id.quantity);
            hold = (Button) findViewById(R.id.hold);
            Totalsavings = (TextView) findViewById(R.id.totalsavings_textt);
            container = (LinearLayout) findViewById(R.id.container);
            creditnote = (Button) findViewById(R.id.creditnotesbutton);
            lostsale = (Button) findViewById(R.id.lostsalebutton);


            doctor = (CustomAuto) findViewById(R.id.doctorname);
            holdspinner = (Spinner) findViewById(R.id.holdsalebill);
            clear = (Button) findViewById(R.id.clear);
            scrollView1 = (ScrollView) findViewById(R.id.scrollView);
            Finalgrandtotaldisp = (TextView) findViewById(R.id.finaldisplineitemdiscount);
            addnewdoct = (ImageButton) findViewById(R.id.addnewdoct_button);
            addnewdoct.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

            addnewproduct = (ImageButton) findViewById(R.id.addnewproduct_button);
            addnewproduct.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

            Creditcustomer = (Button) findViewById(R.id.credit);
            search = (ImageButton) findViewById(R.id.search_button);
            search.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

            credittransid = (TextView) findViewById(R.id.credit_invoiceno);
            mobileno = (TextView) findViewById(R.id.mobileno);
            LineDiscountAmount = (TextView) findViewById(R.id.totalsavings_discamt);
            linediscount = (TextView) findViewById(R.id.totallineitemdiscount);
            addlocalprod = (ImageButton) findViewById(R.id.addlocalsaleproduct);
            addlocalprod.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


            Reprint_button = (Button) findViewById(R.id.Reprint_button);
            TextView mrpforsale = (TextView) findViewById(R.id.Mrp);
            oldspinner = (Spinner) findViewById(R.id.oldbillnumbers);


            //  cardbt=(Button)findViewById(R.id.cardbutton);
            // walletpayment=(Button)findViewById(R.id.mcashesbutton);


            invoiceno();
            RemoteVideoPresentation.showBill();
////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////

            Visibility value = db.getStorevisibility();
            MRPisShown = value.getMrpvisibility();
            FooterShown = value.getFootervisi();
            Tele2Shown = value.getTele2();
            PrintBill = value.getItemvisibilty();//bill print yes or not
            paybycashbillcopy = Integer.parseInt(value.getBillcopy());
            creditbillcopy = Integer.parseInt(value.getCreditcopy());

////////////////////////////////////////////////////////////////////////////////////////////////
            displaypojo billDisplayModel = db.getStoredisplaydetail();

            BILL_TOTAL = billDisplayModel.getTotalbillvalue();
            BILL_DISC = billDisplayModel.getDiscount();
            NETPAYAMOUNT = billDisplayModel.getNetbillpayable();
            RECEIVEAMOUNT = billDisplayModel.getAmountreceived();
            BALANCEAMOUNT = billDisplayModel.getAmountpaidback();

////////////////////////////////////////////////////////////////////////////////////////////////
            final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
            Long Dr_Id = System.currentTimeMillis();
            DoctorSales = Long.toString(Dr_Id);

            clear.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.startAnimation(Buttonok);
                            try {
                                clearbuttondialog();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

            addnewproduct.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchnewproductDialod();
                        }
                    });


            addnewdoct.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Adddoctordialog();
                        }
                    });


            Reprint_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Reprintdialog();
                }
            });
            if (MRPisShown.matches("N")) {
                mrpforsale.setVisibility(View.GONE);
            } else {

                mrpforsale.setVisibility(View.VISIBLE);

            }


            Creditcustomer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        if (PosCust.getText().toString().matches("") || Credit.getText().toString().matches("N") || Credit.getText().toString().matches("null")) {
                            Toast toast = Toast.makeText(ActivitySalesbill.this, "Please Select Credit Customer", Toast.LENGTH_SHORT);
                            toast.show();
                            return;
                        }
                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial", Typeface.BOLD));
                        tp.setTypeface(tff);

                        Valuecredit = imeibill.getText().toString();
                        Customername = PosCust.getText().toString();
                        Grandtotal = GrandTotal.getText().toString();
                        MobileNo = mobileno.getText().toString();
                        db.insercredticustomer(Valuecredit, Customername, MobileNo, Grandtotal, user2.getText().toString());
                        db.updateStockQty(adapter.getList());
                        db.insercredticustomeritemlist(adapter.getList(), imeibill.getText().toString(), Grandtotal, MobileNo);
                        //////////////////////////////////////Printer testing ///////////////////////////////////////
                        store_name = db.getAllStores();
                        store = (store_name.get(1).toString());
                        //   String Store_ID = (store_name.get(0).toString());
                        storeAddress = store_name.get(2).toString();
                        City = store_name.get(3).toString();
                        Storenumber = store_name.get(4).toString();
                        AlternateNo = store_name.get(5).toString();
                        Footer = store_name.get(6).toString();

                        String formattedDate;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E,y-MM-dd hh:mm:ss a");

                        for (int i = 1; i <= creditbillcopy; i++) {
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
                                strbuilder.append(ActivitySalesbill.this.eol);
                            }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);

                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Credit Customer:");
                            strbuilder.append(PosCust.getText().toString());
                            strbuilder.append(ActivitySalesbill.this.eol);


                            formattedDate = new SimpleDateFormat("EEE,dd MMMyy hh:mma").format(Calendar.getInstance().getTime());
                            int val = imeibill.getText().toString().length();
                            int creditbill = Integer.parseInt(String.valueOf(val));
                            int billDtSpace = 3 + 40 - (val + BILL_DATE_COLUMN) + formattedDate.length();
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:");
                            strbuilder.append(imeibill.getText().toString());
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(formattedDate);
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);
                            if (MRPisShown.matches("Y")) {
                                strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : adapter.getList()) {
                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    String printMrp = String.valueOf(f.format(prod.getMrp()));
                                    int spaces = 9 - printMrp.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    spaces = 8 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printtotal = String.valueOf(f.format(prod.getTotal()));
                                    spaces = 7 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);


                                }
                            } else {
                                strbuilder.append("   S.Price" + "       " + "Qty" + "       " + "Total");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : adapter.getList()) {
                                    strbuilder.append(prod.getProductName());
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    int spaces = 10 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }

                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 7 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printtotal = String.valueOf(f.format(prod.getTotal()));
                                    spaces = 12 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    // strbuilder.append(ActivitySalesbill.this.eol);
                                }
                            }
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);


                            String Roundoffvalue = String.valueOf(f.format(Double.parseDouble(GrandTotal.getText().toString())));
                            int spaces = 10 - String.valueOf(Totalitems.getText().toString()).length();
                            int amtspaces = getSpacer(15).length() - CREDIT_BILL_TOTAL.length();
                            strbuilder.append(TOTAL_ITEMS + Totalitems.getText().toString() + CREDIT_BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(ActivitySalesbill.this.eol);


                            int Disamtspaces = getSpacer(24).length() - TOTALSAVINGS.length();
                            int netvalspce = 8 - (Totalsavings.getText().toString()).length();
                            strbuilder.append(getSpacer(Disamtspaces) + TOTALSAVINGS + getSpacer(netvalspce) + Totalsavings.getText().toString() + getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(ActivitySalesbill.this.eol);

                            Disamtspaces = getSpacer(24).length() - RECEIVEAMOUNT.length();
                            netvalspce = 8 - (Totalsavings.getText().toString()).length();
                            strbuilder.append(getSpacer(Disamtspaces) + RECEIVEAMOUNT + getSpacer(netvalspce) + "0.00" + getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                            strbuilder.setLength(0);
                            if (FooterShown.matches("Y")) {
                                strbuilder.append(Footer);
                                strbuilder.append(ActivitySalesbill.this.eol);
                            }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            UsPrinter.print();
                            UsPrinter.FeedLine();
                            UsPrinter.FullCut();
                            Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                            startActivity(intent);
                        }
                        invoiceno();
                        PosCust.setText("");
                        adapter.clearAllRows();
                        RemoteVideoPresentation.fullScreenVideo();
                        GrandTotal.setText(".0");
                        Totalitems.setText("0");
                        Totalsavings.setText(".0");
                        reciveamt.setText(".0");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SearchCustomerDialog();
                }
            });
            Log.v("&&&&&&&&&&", "AutoCompletetextview for Productname initilised");
            ProductName.setThreshold(1);
////////////////////***************Search Through ProductName********************///////////////

            ProductNameTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    Log.i("&&&&&&&&", "After text changed called and text value is" + s.toString());
                    if (ProductName.isPerformingCompletion()) {
                        Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                        return;
                    }
                    String userTypedString = ProductName.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    String x = "X-";
                    if (userTypedString.startsWith("0") || userTypedString.startsWith("1") || userTypedString.startsWith("3") || userTypedString.startsWith("4") || userTypedString.startsWith("5") || userTypedString.startsWith("6") || userTypedString.startsWith("7") || userTypedString.startsWith("8") || userTypedString.startsWith("9")) {

                        userTypedString = x.concat(ProductName.getText().toString().trim());
                    }
                    if (userTypedString.length() < 1) {
                        return;
                    }


                    if (userTypedString.startsWith(BARCODE_STRING_PREFIX)) {
                        if (ProductNameArrayList != null) {
                            ProductNameArrayList.clear();
                        }
                        //this is a barcode generated string
                        if (userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                            //ignore all strings of length < 13
                            return;
                        }
                        ProductNameArrayList = db.getProductNamedata(userTypedString.substring(1));
                        //dropdownProductArrayList = helper.getProductdata(userTypedString);
                        if (ProductNameArrayList.size() == 1) {
                            //we have found the product
                            AddSalesProducttoList(ProductNameArrayList.get(0));
                            return;
                        } else if (ProductNameArrayList.size() < 1) {
                            //no product found
                            //   Toast.makeText(ActivitySalesbill.this, "No Product found", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        ProductNameArrayList = db.getProductNamedata(userTypedString);
                        if (ProductNameArrayList.size() < 1) {
                            ProductNameArrayList.clear();
                            //no product found
                            // Toast.makeText(ActivitySalesbill.this, "No Product found", Toast.LENGTH_SHORT).show();
                            // return;
                        }
                    }
                    if (ProductNameArrayList != null) {
                        if (productNmAdaptor == null) {
                            productNmAdaptor = new SalesProductNmAdaptor(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
                            productNmAdaptor.setList(ProductNameArrayList);
                            ProductName.setAdapter(productNmAdaptor);
                            ProductName.setThreshold(1);
                        } else {

                            productNmAdaptor.setList(ProductNameArrayList);
                            productNmAdaptor.notifyDataSetChanged();

                        }
                    }
                }
            };
            ProductName.addTextChangedListener(ProductNameTextWatcher);
            ProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                    // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                    Sales result = (Sales) parent.getItemAtPosition(position);
                    AddSalesProducttoList(result);
                    writeData(result);
                    RemoteVideoPresentation.AddSalesProducttoList(result);
                }
            });


            creditnote.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CreditnotesAlertDialog();
                        }
                    });


            lostsale.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), Activity_Lost_Sale.class);
                            startActivity(intent);

                        }
                    });


            addlocalprod.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchlocalproductDialod();
                        }
                    });
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ///////////////////////////////doctor autocomplete (jimmy)
            doctor.setThreshold(1);
            mTextWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.d("Debuging", "After text changed called ");
                    if (doctor.isPerformingCompletion()) {
                        Log.d("Debuging", "Performing completion ");
                        return;
                    }
                    String userTypedString = doctor.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    doctorlist = db.getAllDoctors(userTypedString);

                    if (doctorlist != null) {
                        if (doctoradapterdropdown == null) {
                            doctoradapterdropdown = new Saledoctorsadapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, doctorlist);
                            doctoradapterdropdown.setDoctorList(doctorlist);
                            doctor.setAdapter(doctoradapterdropdown);
                            doctor.setThreshold(1);
                        } else {
                            doctoradapterdropdown.setDoctorList(doctorlist);
                            doctoradapterdropdown.notifyDataSetChanged();
                        }
                    }
                }
            };
            doctor.addTextChangedListener(mTextWatcher);
            doctor.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d("Debuging", "On click called ");

                    final DBhelper mydb = new DBhelper(ActivitySalesbill.this);

                    DoctorPojo value = (DoctorPojo) parent.getItemAtPosition(pos);
                    doctor.setText(value.getDoctorName());


                    //   item = adapterView.getItemAtPosition(position).toString();

                }
            });
            paybycash.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //mUsbManager.requestPermission(device,mPermissionIntent);
                            try {

                                if (adapter.getList().isEmpty()) {
                                    return;
                                }
                                int pos = 0;
                                for (Sales item : adapter.getList()) {
                                    if (item.getQuantity() <= 0) {
                                        // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                        return;


                                    } else if (MRPisShown.matches("N")) {

                                    } else if (MRPisShown.matches("Y")) {

                                        if (item.getMrp() < item.getSPrice()) {

                                            Toast.makeText(getApplicationContext(), "Mrp never less then Selling price", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }

                                calculategrangtotalafterdiscount();
                                HotkeyAlertDialog();
                                if (UsPrinter.getState() == 0) {
                                    UsPrinter.showDeviceList(ActivitySalesbill.this);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    });






          /*  ArrayList<String> oldbillnumber = new ArrayList<String>();
            oldbillnumber = db.getoldnumberbill();
            Collections.sort(oldbillnumber);

            Integer highest = Integer.parseInt(oldbillnumber.get(0));
            if (oldbillnumber.size() > 0) {

                int highestIndex = 0;

                for (int s = 1; s < oldbillnumber.size(); s++){
                    Integer curValue = Integer.parseInt(oldbillnumber.get(s));
                    if (curValue > highest) {
                        highest = curValue;
                        highestIndex = s;
                    }
                }

                System.out.println("highest fitness = " + highest + " indoexOf = " + highestIndex);


            }
*/



          /*  maxoldbillnumber = db.getoldnumberbillmax();

            maxoldbillnumber.add(0,"OLD BILLS");*/
          /*  final ArrayList<String> gg=new ArrayList<String>();


            gg.add(0,"OLD BILLS");
            gg.add(String.valueOf(highest));
            oldbilladapter =
                    new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item,gg);
            oldspinner.setAdapter(oldbilladapter);
            oldspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    oldspinneritem = String.valueOf(oldspinner.getSelectedItem().toString());

                    if (oldspinneritem.toString().equals("OLD BILLS")) {
                        if (adapter != null) {
                            //  adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }

                    Log.e("vvv",oldspinneritem);

                    oldinvoicelist = db.getalloldinvoicedata(oldspinneritem);

                    if (oldinvoicelist != null && oldinvoicelist.size() > 0) {
                        if (adapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, oldinvoicelist);
                            RecycleView.setAdapter(adapter);
                        }

//                           else {
                        adapter.setsalearrayList(oldinvoicelist);
                        adapter.notifyDataSetChanged();
                        setSummaryRow();

                    }

                    db.Deleteolddatefromsalesdetail(oldspinneritem);
                    deleterecords();


                }


                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


*/


            holdbilltransaction = db.getTransidofholdbill();
            holdbilltransaction.add(0, "Hold Bills");
            holdbilladapter =
                    new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, holdbilltransaction);
            holdspinner.setAdapter(holdbilladapter);
            holdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    holdspinneritem = holdspinner.getSelectedItem().toString();
                    if (holdspinneritem.toString().equals("Hold Bills")) {
                        if (adapter != null) {
                            //  adapter.clear();
                            adapter.notifyDataSetChanged();
                        }
                    }
                    holdbilltransaction = db.getTransidofholdbill();
                    for (String str : holdbilltransaction) {
                        if (str.equals(holdspinneritem)) {
                            holdinvoicelist = db.getallholdinvoicedata(holdspinneritem);
                            if (holdinvoicelist != null && holdinvoicelist.size() > 0) {
                                if (adapter == null) {
                                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, holdinvoicelist);
                                    RecycleView.setAdapter(adapter);
                                }

//                           else {
                                adapter.setsalearrayList(holdinvoicelist);
                                adapter.notifyDataSetChanged();

                            }
                            setSummaryRow();
                            db.updateholdflag(holdspinneritem.toString());
                            db.updateStockQtyforhold(adapter.getList());
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            hold.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Valuetrans = imeibill.getText().toString();
                        int pos = 0;
                        for (Sales item : adapter.getList()) {
                            if (item.getQuantity() <= 0) {
                                // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            pos++;
                        }
                        db.tempsavesalesListdetail(Valuetrans, adapter.getList(), user2.getText().toString());

                        invoiceno();
                        GrandTotal.setText("");
                        Totalitems.setText("");
                        adapter.clearAllRows();
                        Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                        startActivity(intent);
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
            });

            // Initilization
            viewPager = (ViewPager) findViewById(R.id.pager);
            actionBar = getActionBar();
            mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void AddSalesProducttoList(Sales result) {
        if (adapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Sales>());
            RecycleView.setAdapter(adapter);
            //   ScrollviewSales.getListViewSize(listView);
        }
        int pos = adapter.addProductToList(result);
        Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
        adapter.notifyDataSetChanged();
        ProductName.setText("");
        setSummaryRow();
        ProductNameArrayList.clear();

        RecycleView.smoothScrollToPosition(pos);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.addtolist_button:
//                Intent intent = new Intent(this, Additems.class);
//                startActivity(intent);
//                break;
//            case R.id.search_button:
//                Intent intent1 = new Intent(this,ActivityCustomerSales.class);
//                startActivity(intent1);
//                break;
        }
    }

    private void SearchlocalproductDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localproduct_dialog, null);
        searchalert = new AlertDialog.Builder(ActivitySalesbill.this);
        localproductname = (CustomAuto) searchalertLayout.findViewById(R.id.searchlocalproductname);
        productname = (EditText) searchalertLayout.findViewById(R.id.localproductname);
        productid = (TextView) searchalertLayout.findViewById(R.id.localProductid);
        localbarcode = (EditText) searchalertLayout.findViewById(R.id.localbarcode);
        localhmrp = (EditText) searchalertLayout.findViewById(R.id.localmrp);
        localhmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, Integer.parseInt(mrp))});

        localsprice = (EditText) searchalertLayout.findViewById(R.id.localsprice);
        localsprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, Integer.parseInt(sprice))});

        localpprice = (EditText) searchalertLayout.findViewById(R.id.localpprice);
        localpprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, Integer.parseInt(pprice))});

        localactive = (Spinner) searchalertLayout.findViewById(R.id.localactive);
        localmargin = (TextView) searchalertLayout.findViewById(R.id.localmargin);
        localuom = (EditText) searchalertLayout.findViewById(R.id.localuom);
        localdiscount = (EditText) searchalertLayout.findViewById(R.id.localdiscount);
        localdiscount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});

        localsearchexpdate = (EditText) searchalertLayout.findViewById(R.id.localsearchexpdate);
        LocalBatchno = (EditText) searchalertLayout.findViewById(R.id.localbatchno);
        localQauntity = (EditText) searchalertLayout.findViewById(R.id.localQauntity);
        searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
        searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);


        dialog2 = searchalert.create();
        dialog2.setView(searchalertLayout);
        dialog2.setTitle("ADD LOCAL PRODUCT TO PRODUCT");
        dialog2.show();
        dialog2.setCanceledOnTouchOutside(false);

        ActiveTypesaleproduct = getResources().getStringArray(R.array.active_Status);
        adapterActiveTypesaleproduct = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveTypesaleproduct);
        adapterActiveTypesaleproduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localactive.setAdapter(adapterActiveTypesaleproduct);
        localactive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValuesale = localactive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchalertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(localproductname.getWindowToken(), 0);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });


        localproductname.setThreshold(1);
        TextWatcher xTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (localproductname.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = localproductname.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }

                localproductlist = db.getAllLocalProductforpurchase(userTypedString);

                if (localproductlist != null) {
                    if (localadapter == null) {
                        localadapter = new localsalesproductadapterdialog(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
                        localadapter.setLocalProductList(localproductlist);
                        localproductname.setAdapter(localadapter);
                        localproductname.setThreshold(1);
                    } else {
                        localproductname.setAdapter(localadapter);
                        localadapter.setLocalProductList(localproductlist);
                        localadapter.notifyDataSetChanged();
//
                    }
                }
            }
        };
        localproductname.addTextChangedListener(xTextWatcher);
        localproductname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int pos, long id) {

                Log.d("Debuging", "On click called ");

                valuelocal = (LocalProduct) parent.getItemAtPosition(pos);
                productid.setText(valuelocal.getProductid());
                productname.setText(valuelocal.getProductname());
                productname.requestFocus();
                localsprice.setText(valuelocal.getSellingPrice());
                localpprice.setText(valuelocal.getPurchasePrice());
                localbarcode.setText(valuelocal.getBarcode());
                localhmrp.setText(valuelocal.getMRP());
                localdiscount.setText(valuelocal.getDiscount());
                localuom.setText(valuelocal.getUom());
                LocalBatchno.setText(valuelocal.getBatchNo());
                localQauntity.setText(valuelocal.getQuantity());
                SpinValuesale = valuelocal.getActive();
                if (SpinValuesale.equals("Y")) {
                    localactive.setSelection(0);
                }
                if (SpinValuesale.equals("N")) {
                    localactive.setSelection(1);
                }
                localmargin.setText(valuelocal.getMargin());
                localproductname.setText("");

                localsearchexpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTempPositionBeforeCalenderDialog = pos;
                        new DatePickerDialog(ActivitySalesbill.this, date1, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        //  Toast.makeText(ActivitySalesbill.this,date.toString(),Toast.LENGTH_SHORT).show();
                        localsearchexpdate.setText(valuelocal.getExpDate());
                    }

                });


            }
        });
        searchsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = productid.getText().toString();
                localProd_Id_To_Update = value;
                if (productid.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (localsprice.getText().toString().matches("")) {
                    localsprice.setError("Please fill Selling Price");
                    return;
                }
                if (localpprice.getText().toString().matches("")) {
                    localpprice.setError("Please fill Purchase Price");
                    return;
                }
                if (productname.getText().toString().matches("")) {
                    productname.setError("Please fill Product name ");
                    return;
                }
                if (localhmrp.getText().toString().matches("")) {
                    localhmrp.setError("Please fill Mrp ");
                    return;
                }
                if (localQauntity.getText().toString().matches("")) {
                    localhmrp.setError("Please fill Quantity ");
                    return;
                }
                try {

                    selling = Float.parseFloat(localsprice.getText().toString());
                    purchase = Float.parseFloat(localpprice.getText().toString());
                    mrplocal = Float.parseFloat(localhmrp.getText().toString());
                    if (selling > mrplocal) {
                        localsprice.setError("Invalid Selling Price");
                        return;

                    }
                    if (purchase > selling) {
                        localpprice.setError("Invalid PurchasePrice");
                        return;
                    }
                    if (purchase > mrplocal) {
                        localpprice.setError("Invalid purchase Price");
                        return;
                    }
                    localsearchexpdate.setText(valuelocal.getExpDate());
                    db.updatelocalProduct(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValuesale, localmargin.getText().toString(), user2.getText().toString(), localdiscount.getText().toString(), localuom.getText().toString());

                    db.updatelocalProductCom(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValuesale, user2.getText().toString(), localdiscount.getText().toString(), localuom.getText().toString());
                    updateLocalProduct();
                    localprodaddarraylist = new ArrayList();
                    valuelocal.setProductid(valuelocal.getProductid());
                    valuelocal.setProductname(productname.getText().toString());
                    valuelocal.setSellingPrice(localsprice.getText().toString());
                    valuelocal.setPurchasePrice(localpprice.getText().toString());
                    valuelocal.setBarcode(valuelocal.getBarcode());
                    valuelocal.setMRP(localhmrp.getText().toString());
                    valuelocal.setDiscount(localdiscount.getText().toString());
                    valuelocal.setUom(localuom.getText().toString());
                    valuelocal.setBatchNo(LocalBatchno.getText().toString());
                    valuelocal.setQuantity(localQauntity.getText().toString());
                    valuelocal.setExpDate(localsearchexpdate.getText().toString());

                    localprodaddarraylist.add(valuelocal);

                    for (LocalProduct Val : localprodaddarraylist) {
                        if (Val.getExpDate().trim().equals("select date")) {
                            Toast.makeText(ActivitySalesbill.this, "Please fill the mandatory", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (Val.getBatchNo().toString().trim().equals("Batch") || Val.getBatchNo() == null || Val.getBatchNo().trim().equals("")) {

                            Val.setBatchNo(getSystemCurrentTime());
                        }
                    }
//                    setSummaryRow();
                    Long valueop = System.currentTimeMillis();
                    GrnIDForLocal = Long.toString(valueop);
                    db.DirectlyAddProductInSalesForLocalprod(localprodaddarraylist, GrnIDForLocal.toString(), user2.getText().toString());
                    UpdateIventoryByLocalProduct();
                    dialog2.dismiss();

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }


        });

        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();

            }
        });


    }

    public void localvalidateDate(int year, int month, int day) {
        Date Todaydate = null, edate = null;
        String enddate = year + "/" + month + "/" + day;

        Log.e("########", "----------->" + enddate);

        String todaysdate = Year + "/" + Month + "/" + Day;
        Log.e("########", "----------->" + todaysdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (edate.before(Todaydate)) {

            Toast.makeText(this, "Invalid date !!", Toast.LENGTH_SHORT).show();

            return;


        } else {

            Log.e("########", "----------->" + edate);


            if (mTempPositionBeforeCalenderDialog != -1) {
                int Mymonth = month;
                valuelocal.setExpDate(year + "/" + Mymonth + "/" + day);
                localsearchexpdate.setText(valuelocal.getExpDate());
                mTempPositionBeforeCalenderDialog = -1;


            }
        }
    }

///**************************CREDITNOTE ALERT DIALOG *******************************************************************//

    public void invoiceno() {
        Value = System.currentTimeMillis();
        result = Long.toString(Value);
        invoicevalue = Billno.getText().toString();
        invoicebillno = db.getimeino();
        for (String str : invoicebillno) {
            if (str.equals(invoicevalue)) {
                imei = db.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei = imei.toString();
                String x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                Log.e("X1_imei is :", x1);
                imeibill.setText(x1);
            } else {
                continue;
            }
        }
    }


    private void CreditnotesAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.creditnote_dialog, null);
        alert = new AlertDialog.Builder(ActivitySalesbill.this);
        cleardialog = (Button) alertLayout.findViewById(R.id.clear_dialog_button);
        addtopay = (Button) alertLayout.findViewById(R.id.addtopayment_button);
        creditvalue = (TextView) alertLayout.findViewById(R.id.creditamount_tv);
        CreditBillNo = (CustomAuto) alertLayout.findViewById(R.id.invoiceno);
        dialog = alert.create();
        dialog.setView(alertLayout);
        dialog.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        dialog.show();
        CreditBillNo.setThreshold(1);
        cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                try {
                    if (CreditBillNo.isPerformingCompletion()) {
                        Log.d("Debuging", "Performing completion ");
                        return;
                    }
                    String userTypedInvoiceno = CreditBillNo.getText().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + CreditBillNo.toString());
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }
                    CreditNotelist = db.getcreditno(userTypedInvoiceno);
                    Log.d("!@#!@#!@#!@#", "creditnote arraylist size is " + CreditNotelist.size());
                    if (CreditNotelist != null && CreditNotelist.size() > 0) {
                        if (creditNoteAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            creditNoteAdapter = new CreditNoteAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, CreditNotelist);
                            creditNoteAdapter.setCreditNotelist(CreditNotelist);
                            CreditBillNo.setAdapter(creditNoteAdapter);
                            CreditBillNo.setThreshold(1);
                        } else {
                            creditNoteAdapter.setCreditNotelist(CreditNotelist);
                            creditNoteAdapter.notifyDataSetChanged();
                            CreditBillNo.setAdapter(creditNoteAdapter);
                            CreditBillNo.setThreshold(1);
                        }
                    }
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        };

        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(CreditBillNo.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });
        CreditBillNo.removeTextChangedListener(cTextWatcher);
        CreditBillNo.addTextChangedListener(cTextWatcher);
        CreditBillNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                try {
                    Log.d("Debuging", "On click called ");
                    CreditBill = parent.getItemAtPosition(pos).toString();
                    CreditNote value = (CreditNote) parent.getItemAtPosition(pos);
                    creditvalue.setText(value.getCreditnotevalue());
                    CreditBillNo.setText("");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        addtopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    String crt = creditvalue.getText().toString();
                    creditnoteretrieve.setText(crt);
                    db.updatecreditflags(CreditBill);
                    dialog.dismiss();
                    calculatecreditnote();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cleardialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    //**************************************SEARCH CUSTOMER DIALOG**********************************************************************************//

    public void calculatecreditnote() {
        try {

            CreditNotevalue = Float.parseFloat(creditnoteretrieve.getText().toString());
            CredNotebillvalue = Float.parseFloat(GrandTotal.getText().toString());
            Creditresult = CredNotebillvalue - CreditNotevalue;
            GrandTotal.setText(result.toString());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    //**************************************ADDITION DOCTOR DIALOG************************************************//

    private void SearchCustomerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.search_customer_dialog, null);
        alert = new AlertDialog.Builder(ActivitySalesbill.this);
        searchauto = (CustomAuto) alertLayout.findViewById(R.id.myautocomplete);
        addnewcustomer = (Button) alertLayout.findViewById(R.id.Cust_ok_button);
        hidden = (LinearLayout) alertLayout.findViewById(R.id.Hiddenlayout);
        CustomerMobile = (TextView) alertLayout.findViewById(R.id.Cust_mobileno1);
        update = (Button) alertLayout.findViewById(R.id.Cust_update_button);
        ClearDoctor = (Button) alertLayout.findViewById(R.id.Cust_clear_button);
        exit = (Button) alertLayout.findViewById(R.id.Cust_Exit_button);
        exitcust = (Button) alertLayout.findViewById(R.id.exit);
        hidden2 = (LinearLayout) alertLayout.findViewById(R.id.Hiddenlayout1);
        hidden3 = (LinearLayout) alertLayout.findViewById(R.id.autolayout);
        buttonlayout = (LinearLayout) alertLayout.findViewById(R.id.buttons_layout);
        CUSTOMERNAME = (TextView) alertLayout.findViewById(R.id.Cust_name);
        CUSTOMERMOBILE = (TextView) alertLayout.findViewById(R.id.Cust_mobileno);
        CUSTOMEREMAIL = (TextView) alertLayout.findViewById(R.id.Cust_email);
        CUSTOMERADRESS = (TextView) alertLayout.findViewById(R.id.Cust_adress_sales_create);
        CUSTOMERCREDIT = (TextView) alertLayout.findViewById(R.id.Cust_credit);
        Credithidd = (Spinner) alertLayout.findViewById(R.id.Cust_Credithidden);
        //final EditText Custhidd = (EditText)findViewById(R.id.Cust_mobilenohidd);


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        dialog = alert.create();

        buttonlayout.setVisibility(View.INVISIBLE);
        dialog.setView(alertLayout);
        dialog.setTitle("ADD CUSTOMER");
        dialog.setCanceledOnTouchOutside(false);
        alert.setCancelable(false);
        dialog.show();

        searchauto.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (searchauto.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = searchauto.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                customerlist = db.getAllCustomers(userTypedString);
                if (customerlist != null) {
                    if (customerSalesAdapter == null) {
                        customerSalesAdapter = new CustomerSalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, customerlist);
                        customerSalesAdapter.setCustomerList(customerlist);
                        searchauto.setAdapter(customerSalesAdapter);
                        searchauto.setThreshold(1);
                    } else {
                        customerSalesAdapter.setCustomerList(customerlist);
                        customerSalesAdapter.notifyDataSetChanged();
                        searchauto.setAdapter(customerSalesAdapter);
                        searchauto.setThreshold(1);
                    }
                }
            }
        };

        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchauto.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });

        searchauto.removeTextChangedListener(mTextWatcher);
        searchauto.addTextChangedListener(mTextWatcher);
        searchauto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Log.d("Debuging", "On click called ");

                Customer value = (Customer) parent.getItemAtPosition(pos);

                CUSTOMERNAME.setText(value.getCustomername());
                String res = CUSTOMERNAME.getText().toString();

                CUSTOMERMOBILE.setText(value.getCustomermobileno());
                String res1 = CUSTOMERMOBILE.getText().toString();
                CUSTOMERCREDIT.setText(value.getCustomercredit());
                String res2 = CUSTOMERCREDIT.getText().toString();

                Credit.setText(res2);
                mobileno.setText(res1);
                PosCust.setText(res);
                setCustomerName(res);
                RemoteVideoPresentation.getCustomername(res);

                searchauto.setText("");
                customerlist.remove(pos);
                customerSalesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                CUSTOMEREMAIL.setText("");
                CUSTOMERNAME.setText("");
                CUSTOMERMOBILE.setText("");
                CUSTOMERADRESS.setText("");
            }
        });
        exitcust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        CUSTOMEREMAIL.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (CUSTOMEREMAIL.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0) {
                    //  CUSTOMEREMAIL .setText("valid email");

                } else {
                    CUSTOMEREMAIL.setError("invalid email");
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        CreditActiveType = getResources().getStringArray(R.array.active_Status);
        creditadapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CreditActiveType);
        creditadapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Credithidd.setAdapter(creditadapterActiveType);
        Credithidd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CreditSpinvalue = Credithidd.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custid = getSystemCurrentTime();
                String res = CUSTOMERNAME.getText().toString();
                String mob = CUSTOMERMOBILE.getText().toString();
                mobileno.setText(CUSTOMERMOBILE.getText().toString());
                PosCust.setText(res);
                PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
                String save_sales_store_ids = PersistenceManager.getStoreId(ActivitySalesbill.this);


                if (CUSTOMEREMAIL.getText().toString().matches("")) {

                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(CUSTOMEREMAIL.getText().toString()).matches()) {
                    //Validation for Invalid Email Address
                    //  Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                    //   CUSTOMEREMAIL.setError("Invalid Email");
                    return;
                }

                if (CUSTOMERMOBILE.getText().toString().matches("")) {
                    Toast toast = Toast.makeText(ActivitySalesbill.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if (db.CheckIsDataAlreadyInDBorNot(mob)) {
                    Toast toast1 = Toast.makeText(ActivitySalesbill.this, "MOBILE NO ALREADY REGISTERED", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;

                }
                if (CUSTOMERMOBILE.getText().toString().length() > 10 || CUSTOMERMOBILE.getText().toString().length() != 10) {
                    CUSTOMERMOBILE.setError("Invalid Number");
                    return;


                } else if (db.insertCustomer(new Customer(mob,
                        CUSTOMERNAME.getText().toString(), CUSTOMEREMAIL.getText().toString(), CreditSpinvalue, CUSTOMERADRESS.getText().toString()), user2.getText().toString(), custid, save_sales_store_ids))

                {

                    Toast toast = Toast.makeText(ActivitySalesbill.this, "CUSTOMER ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    dialog.dismiss();
                }
            }
        });
        addnewcustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                hidden2.setVisibility(View.GONE);
                hidden.setVisibility(View.VISIBLE);
                hidden3.setVisibility(View.INVISIBLE);
                buttonlayout.setVisibility(View.VISIBLE);
            }
        });

    }

//*********************************************ADDPRODUCT DIALOG***********************************************

    private void Adddoctordialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View DoctorLayout = inflater.inflate(R.layout.doctoradditiondiaglog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(ActivitySalesbill.this);

        Doctorupdate = (Button) DoctorLayout.findViewById(R.id.doct_ok_button);
        docclear = (Button) DoctorLayout.findViewById(R.id.doct_clear_button);
        Doctorexit = (Button) DoctorLayout.findViewById(R.id.doct_Exit_button);
        doctname = (EditText) DoctorLayout.findViewById(R.id.doctor_name);
        doctaddress = (EditText) DoctorLayout.findViewById(R.id.doctor_address);
        specialization = (Spinner) DoctorLayout.findViewById(R.id.doctor_special);
        Active = (Spinner) DoctorLayout.findViewById(R.id.doc_active);
        doctordialog = searchalert.create();
        doctordialog.setView(DoctorLayout);
        doctordialog.setTitle("ADD DOCTOR DETAILS");
        doctordialog.show();
        doctordialog.setCanceledOnTouchOutside(false);


        doctorspecial = db.getdoctorspecialication();
        stringArrayAdapter =
                new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
        specialization.setAdapter(stringArrayAdapter);
        specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                itemdoctor = adapterView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });

        DoctorLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(doctname.getWindowToken(), 0);

                } catch (NullPointerException e) {
                }
                return true;


            }

        });

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Doctorexit.setOnClickListener(

                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doctordialog.dismiss();
                    }
                });


        Doctorupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (doctname.getText().toString().matches("") || doctname.getText().toString().matches("[a-z]+")) {
                    doctname.setError("Please Fill Capital Letters");
                    Toast toast = Toast.makeText(ActivitySalesbill.this, "PLEASE FILL THE FIELD", Toast.LENGTH_SHORT);
                    toast.show();
                    return;

                } else {
                    db.insertDoctor(new DoctorPojo(DoctorSales, doctname.getText().toString(),
                            itemdoctor.toString(), doctname.getText().toString(), SpinValue), user2.getText().toString());
                    Toast toast = Toast.makeText(ActivitySalesbill.this, "DOCTOR ADDED", Toast.LENGTH_SHORT);
                    toast.show();
                    insertDoctor();
                    doctordialog.dismiss();

                }


            }


        });


        docclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<String> doctorspecial = db.getdoctorspecialication();
                stringArrayAdapter =
                        new ArrayAdapter<String>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, doctorspecial);
                specialization.setAdapter(stringArrayAdapter);
                doctname.setText(" ");
            }
        });
    }

    public void validateDate(int year, int month, int day) {
        Date Todaydate = null, edate = null;
        String enddate = year + "/" + month + "/" + day;

        Log.e("########", "----------->" + enddate);

        String todaysdate = Year + "/" + Month + "/" + Day;
        Log.e("########", "----------->" + todaysdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (edate.before(Todaydate)) {

            Toast.makeText(this, "Invalid date !!", Toast.LENGTH_SHORT).show();

            return;


        } else {

            Log.e("########", "----------->" + edate);


            if (mTempPositionBeforeCalenderDialog != -1) {
                int Mymonth = month;
                resval.setExpiry(year + "/" + Mymonth + "/" + day);
                resval.setExpDate(year + "/" + Mymonth + "/" + day);
                searchexpdate.setText(resval.getExpDate());
                //    localsearchexpdate.setText(resval.getExpDate());
                mTempPositionBeforeCalenderDialog = -1;


            }
        }
    }


    private void SearchnewproductDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_newproduct_dialog, null);
        searchalert = new AlertDialog.Builder(ActivitySalesbill.this);
        searchproductname = (CustomAuto) searchalertLayout.findViewById(R.id.searchproductname);
        SearchProductNameDialog = (TextView) searchalertLayout.findViewById(R.id.setproductname);
        searchbatch = (EditText) searchalertLayout.findViewById(R.id.searchbatchno);
        searchexpdate = (EditText) searchalertLayout.findViewById(R.id.searchexpdate);


        TextView mrpdialog = (TextView) searchalertLayout.findViewById(R.id.mrpfordialog);
        if (MRPisShown.matches("N")) {
            mrpdialog.setVisibility(View.GONE);
        } else {
            mrpdialog.setVisibility(View.VISIBLE);

        }

        searchmrp = (EditText) searchalertLayout.findViewById(R.id.searchmrp);
        searchmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(mrp))});
        if (MRPisShown.matches("N")) {
            searchmrp.setVisibility(View.GONE);


        } else {

            searchmrp.setVisibility(View.VISIBLE);


        }


        searchsprice = (EditText) searchalertLayout.findViewById(R.id.searchsprice);
        searchsprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(sprice))});

        searchqty = (EditText) searchalertLayout.findViewById(R.id.searchqty);
        searchuom = (TextView) searchalertLayout.findViewById(R.id.searchuom);
        //final TextView searchtotal = (TextView) searchalertLayout.findViewById(R.id.searchtotal);
        searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
        searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);


        dialog2 = searchalert.create();
        dialog2.setView(searchalertLayout);
        dialog2.setTitle("ADD NEW PRODUCT");
        dialog2.show();
        dialog2.setCanceledOnTouchOutside(false);

        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Log.d("Debuging", "After text changed called ");
                if (searchproductname.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = searchproductname.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                Searchnamelist = db.getProductdataforsearch(userTypedString);

                if (Searchnamelist.size() < 1) {
                    //no product found
                    return;
                }
                if (searchadapter == null) {
                    searchadapter = new SearchproductAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, Searchnamelist);
                    searchadapter.setList(Searchnamelist);
                    searchproductname.setAdapter(searchadapter);
                    searchproductname.setThreshold(1);

                } else {
                    searchproductname.setAdapter(searchadapter);
                    searchadapter.setList(Searchnamelist);
                    searchadapter.notifyDataSetChanged();

                }
            }
        };

        searchalertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(searchproductname.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });

        searchproductname.addTextChangedListener(mTextWatcher);
        searchproductname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                resval = (Sales) parent.getItemAtPosition(position);

                SearchProductNameDialog.setText(resval.getProductName());
                searchbatch.setText(resval.getBatchNo());
                //  searchexpdate.setText(resval.getExpiry());
                if (MRPisShown.matches("N")) {
                    searchmrp.setText(String.format("%.2f", resval.getSearchsprice()));
                } else {
                    searchmrp.setText(String.format("%.2f", resval.getSearchmrp()));
                }
                searchqty.setText(String.format("%d", resval.getQuantity()));
                searchsprice.setText(String.format("%.2f", resval.getSearchsprice()));
                searchuom.setText(resval.getUom());
                searchproductname.setText("");
                searchexpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTempPositionBeforeCalenderDialog = position;
                        new DatePickerDialog(ActivitySalesbill.this, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        //  Toast.makeText(ActivitySalesbill.this,date.toString(),Toast.LENGTH_SHORT).show();
                        searchexpdate.setText(resval.getExpDate());
                    }

                });

            }

        });

        stest = searchbatch.getText().toString();
        Log.e("Rahul Test", " Demo" + stest.toString());
        netextw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (searchbatch.getText().toString().trim().equals("")) {
                        return;
                    }
                    resval.setProductName(resval.getProductName());
                    resval.setBatchNo(searchbatch.getText().toString());
                    resval.setQuantity(Integer.parseInt(searchqty.getText().toString()));
                    resval.setMrp(Float.parseFloat(searchmrp.getText().toString()));
                    resval.setSPrice(Float.parseFloat(searchsprice.getText().toString()));
                    searchexpdate.setText(resval.getExpDate());


                    batchcheck = false;
                    batchcheck = db.ForDirecltyAddProductCheckbatchnoAlreadyInDBorNot(resval.getBatchNo());
                    if (batchcheck == false) {
                        resval.setBatchNo(searchbatch.getText().toString());
                        resval.setQuantity(resval.getQuantity());
                        resval.setStockquant(resval.getConversionfacter() * resval.getQuantity());
                    } else {
                        Qtyval = db.ForDirecltyAddProductgetparticularbatchqty(resval.getBatchNo(), resval.getProductId());

                        resval.setQuantity(resval.getQuantity());
                        resval.setStockquant(Float.parseFloat(Qtyval));
                        resval.setSPrice(resval.getSearchsprice());
                        resval.setTotal(resval.getTotal());
                        resval.setTotal(resval.getSPrice() * resval.getQuantity());

                    }

                    // check from here
                } catch (Exception e) {

                }
            }
        };
        searchbatch.addTextChangedListener(netextw);
        searchbatch.setTag(netextw);
        searchqty.addTextChangedListener(netextw);
        searchqty.setTag(netextw);
        searchmrp.addTextChangedListener(netextw);
        searchmrp.setTag(netextw);
        searchsprice.addTextChangedListener(netextw);
        searchsprice.setTag(netextw);
        searchsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    arrayList = new ArrayList();
                    batchcheck = db.ForDirecltyAddProductCheckbatchnoAlreadyInDBorNot(resval.getBatchNo());
                    if (batchcheck == false) {
                        resval.setBatchNo(searchbatch.getText().toString());
                        resval.setQuantity(resval.getQuantity());
                        resval.setStockquant(resval.getConversionfacter() * resval.getQuantity());
                    } else {
                        Qtyval = db.ForDirecltyAddProductgetparticularbatchqty(resval.getBatchNo(), resval.getProductId());
                        resval.setQuantity(resval.getQuantity());
                        resval.setStockquant(Float.parseFloat(Qtyval));
                        resval.setSPrice(resval.getSearchsprice());
                        resval.setTotal(resval.getTotal());
                        resval.setTotal(resval.getSPrice() * resval.getQuantity());

                    }
                    arrayList.add(resval);
                    for (Sales Val : arrayList) {
                        if (Val.getExpDate().trim().equals("select date")) {
                            Toast.makeText(ActivitySalesbill.this, "Please fill the mandatory", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (Val.getBatchNo().toString().trim().equals("Batch") || Val.getBatchNo() == null || Val.getBatchNo().trim().equals("")) {

                            Val.setBatchNo(getSystemCurrentTime());
                        }


                    }
                    if (arrayList != null) {
                        if (adapter == null) {
                            adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Sales>());
                            RecycleView.setAdapter(adapter);
                        }

                        adapter.addProductToList(resval);
                        RemoteVideoPresentation.AddSalesProducttoList(resval);
                        RecycleView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                    setSummaryRow();
                    dialog2.dismiss();

                    db.DirectlyAddProductInSales(arrayList, Billno.getText().toString());
                    //  updateSaveSalesList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();

            }
        });


    }

    public void SaleshelpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                       SALES    ");
        alertDialog.setMessage("User selects the Sales option to do the following activities.\n" +
                "\n" +
                "Add a new Customer – Customer can be addded using Master Data \uF0E0 Customers\n" +
                "Add new Doctor – Doctors can be added using Master Data \uF0E0 Doctors\n" +
                "Create a POS receipt for billing – The user can have the following options:\n" +
                "     Select the Customer\n" +
                "     Select the Doctor\n" +
                "\n" +
                "Scan or enter the product manually. The system allow the manual entry by selecting a batch.\n" +
                "The user can enter the product code by selecting the Top " +
                "   15 products configured in the system.\n" +
                "The following are the menu options  the user can select" +
                " to carry out the activities.\n" +
                "\n" +
                " Pay Cash – Submit the bill for cash payments. \n" +
                "Hold – Hold a bill\n" +
                "Credit – Credit the customer for the bill\n" +
                "Clear – Clears the line items\n" +
                "Settle credit customer  - A unique function to settle the customer credited at a later point of time. \n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });

        alertDialog.show();

    }


    public void setSummaryRow() {
        if (roundofff.toString().matches("Y")) {
            DecimalFormat f2 = new DecimalFormat("##");
            DecimalFormat f3 = new DecimalFormat("##.00");
            Getval = adapter.getGrandTotal();
            Getline = adapter.getGrandTotalineitem();
            linediscamount = adapter.gettotalline();
            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f2.format(Getval).concat(".00");
            linevaluediscount = f3.format(linediscamount);
            Log.d("line level discount", "" + linevaluediscount);
            String Grandlinelevel = f3.format(Getline);
            linediscount.setText(Grandlinelevel);
            LineDiscountAmount.setText(linevaluediscount);
            GrandTotal.setText(GrandVal);
            Log.d("@@@@@@@@@@", "" + GrandTotal);
            // reciveamt.setText("");
            // expectedchang.setText("");
            int Getitem = adapter.getItemCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);

        } else {

            DecimalFormat f = new DecimalFormat("##.00");


            Getval = adapter.getGrandTotal();
            Getline = adapter.getGrandTotalineitem();
            linediscamount = adapter.gettotalline();

            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f.format(Getval);
            GrandTotal.setText(GrandVal);
            String linevaluediscount = f.format(linediscamount);
            String Grandlinelevel = f.format(Getline);
            linediscount.setText(Grandlinelevel);
            LineDiscountAmount.setText(linevaluediscount);
            Log.d("@@@@@@@@@@", "" + GrandTotal);
            // reciveamt.setText("");
            // expectedchang.setText("");
            int Getitem = adapter.getItemCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);

        }
    }

    public void fragmentdata() {
/* String shilpa="Hello";
 textView.setText(shilpa);*/
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        FastMovingFragment fragobj = new FastMovingFragment();
        String value = (String) FastMovingFragment.myBundle.get("id_User");
        try {
            String userTypedCaption = value.toString();
            Log.e("!!!!!!!!!!!", "" + value.toString());
            if (userTypedCaption.equals("")) {
                return;
            }
            ArrayList<Sales> fragmentdatalist = db.getAllTopProductDetails(userTypedCaption);
            if (fragmentdatalist != null) {
                if (adapter == null) {
                    adapter = new SalesAdapter(ActivitySalesbill.this, android.R.layout.simple_dropdown_item_1line, fragmentdatalist);
                    //adapter.setsalearrayList(fragmentdatalist);
                    RecycleView.setAdapter(adapter);
                }
                adapter.addProductToList(fragmentdatalist.get(0));
                writeData(fragmentdatalist.get(0));
                // RemoteVideoPresentation.AddSalesProducttoList(fragmentdatalist.get(0));
                //adapter.setsalearrayList(fragmentdatalist);
                adapter.notifyDataSetChanged();
                setSummaryRow();
            }
        } catch (Exception npe) {
            npe.printStackTrace();
        }
    }

    public void HotkeyAlertDialog() {

        username = login.b.getString("Pos_User");

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.keyboard_popup_salesbill, null);
        alert = new AlertDialog.Builder(this);
        HotAlertSubmit = (Button) alertLayout.findViewById(R.id.paycash);
        paybycard = (Button) alertLayout.findViewById(R.id.paybycard);
        OverAllDisCount = (TextView) alertLayout.findViewById(R.id.OverAlldiscountamount);
        OverallTotalDiscount = (TextView) alertLayout.findViewById(R.id.OverAlldiscountamounttotal);
        TotalGrandAmountPopup = (TextView) alertLayout.findViewById(R.id.TotalDiscountEdit);

        HotGrandtotal = (TextView) alertLayout.findViewById(R.id.grandtot_ed);
        reciveamt = (EditText) alertLayout.findViewById(R.id.reciveamountdialog);
        reciveamt.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
        expectedchang = (TextView) alertLayout.findViewById(R.id.expectedchange);
        discountlinelevel = (TextView) alertLayout.findViewById(R.id.Lineleveldiscount);
        close = (Button) alertLayout.findViewById(R.id.close);
        discount = (EditText) alertLayout.findViewById(R.id.discountspinner);
        discountamount = (EditText) alertLayout.findViewById(R.id.discountamount);
        discount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});
        grandtotalafterdiscount = (TextView) alertLayout.findViewById(R.id.finalamttopaid);
        submitBillDiscount = (Button) alertLayout.findViewById(R.id.discountclose_button);
        finalgrandtotal = Finalgrandtotaldisp.getText().toString();
        HotGrandtotal.setText(finalgrandtotal);
        lineitemdiscount = linediscount.getText().toString();
        printbilladptr = (Spinner) alertLayout.findViewById(R.id.PrintBillsale);

        emailids = (EditText) alertLayout.findViewById(R.id.emailid);


        discountlinelevel.setText(lineitemdiscount);
        OverAllDisCount.setText(String.valueOf(linediscamount));
        TotalGrandAmountPopup.setText(GrandTotal.getText().toString());
        alert.setView(alertLayout);
        final AlertDialog dialog = alert.create();
        dialog.setTitle("PAYMENT OPTIONS");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        DiscountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f = new DecimalFormat("##.00");


                try {
                    if (discount.getText().toString().matches("")) {
                        discountamount.setText("");
                        return;
                    }

                    discountamount.setText(String.valueOf(f.format(Float.parseFloat(finalgrandtotal) * (Float.parseFloat(discount.getText().toString()) / 100))));
                    grandtotalafterdiscount.setText(String.valueOf(f.format(Float.parseFloat(finalgrandtotal) - (Float.parseFloat(finalgrandtotal) * Float.parseFloat(discount.getText().toString()) / 100) + (Float.parseFloat(lineitemdiscount)))));

                    //*********************For media screen gnand total ************************************
                    String strTotal = grandtotalafterdiscount.getText().toString();
                    //updateGrandTotalAfterDiscount(strTotal);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(reciveamt.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });

        discount.removeTextChangedListener(DiscountTextWatcher);
        discount.addTextChangedListener(DiscountTextWatcher);


        DiscountAmountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f = new DecimalFormat("##.00");


                try {

                    if (discountamount.getText().toString().matches("")) {
                        discount.setText(".0");

                        return;
                    }
                    if (discountamount.getText().toString().matches("")) {
                        grandtotalafterdiscount.setText(finalgrandtotal);


                    }
                    if (HotGrandtotal.getText().toString().matches("0.0"))

                    {
                        grandtotalafterdiscount.setText(lineitemdiscount);
                        return;

                    }

                    grandtotalafterdiscount.setText(String.valueOf(f.format(Float.parseFloat(finalgrandtotal) - (Float.parseFloat(discountamount.getText().toString())) + (Float.parseFloat(lineitemdiscount)))));
                    //float OverAllDiscount=(Float.parseFloat(discountamount.getText().toString()))+(Float.parseFloat(lineitemdiscount))+Float.parseFloat(LineDiscountAmount.getText().toString());


                    OverallTotalDiscount.setText(String.valueOf(Float.parseFloat(OverAllDisCount.getText().toString()) + Float.parseFloat(discountamount.getText().toString())));

                    //*********************For media screen overall discount ************************************
                    String strDis = OverallTotalDiscount.getText().toString();
                    //updateDiscountByPercentage(strDis);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        };

        discount.setText("");
        discountamount.removeTextChangedListener(DiscountAmountTextWatcher);
        discountamount.addTextChangedListener(DiscountAmountTextWatcher);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        HotAlertSubmit.setVisibility(View.VISIBLE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("");

        if (adapter.getList().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
            return;
        }
        if ((reciveamt.getTag() != null) && (reciveamt.getTag() instanceof TextWatcher)) {
            reciveamt.removeTextChangedListener((TextWatcher) reciveamt.getTag());
        }

        salesTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f = new DecimalFormat("##.00");
                try {
                    DecimalFormat f1 = new DecimalFormat("#");

                    if (reciveamt.getText().toString() == null || reciveamt.getText().toString().isEmpty()) {
                        expectedchang.setText("");
                        return;
                    }
                    if (discount.getText().toString().isEmpty() && discountamount.getText().toString().isEmpty()) {
                        grandtotalafterdiscount.setText(finalgrandtotal);
                        OverallTotalDiscount.setText(String.valueOf(OverAllDisCount.getText().toString()));
                        discountamount.setText(".00");
                        expectedchang.setText(".00");

                    } else {
                        expectedchang.setText(String.valueOf(f.format(Float.parseFloat(grandtotalafterdiscount.getText().toString()) - Float.parseFloat(reciveamt.getText().toString()))));
                    }

                    Log.d("$$$$$$$$$$$", "" + expectedchang);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        reciveamt.removeTextChangedListener(salesTextWatcher);
        reciveamt.addTextChangedListener(salesTextWatcher);
        reciveamt.setTag(salesTextWatcher);

        ActiveType = getResources().getStringArray(R.array.active_Status);
        visibiltyActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        visibiltyActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        printbilladptr.setAdapter(visibiltyActiveType);
        if (PrintBill.equals("Y")) {
            printbilladptr.setSelection(0);
        }
        if (PrintBill.equals("N")) {
            printbilladptr.setSelection(1);
        }
        printbilladptr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PrintBill = printbilladptr.getSelectedItem().toString();
                // PrintBill=PrintBill;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.show();
        HotAlertSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reciveamt.getText().toString().matches("") || grandtotalafterdiscount.getText().toString().matches("")) {
                    return;
                }
                PaySelling = Double.parseDouble(reciveamt.getText().toString());
                PayPurchase = Double.parseDouble(grandtotalafterdiscount.getText().toString());

                if (PaySelling < PayPurchase) {
                    Toast.makeText(getApplicationContext(), "Please enter Amount Received > = Sales Value", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (adapter.getList().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog.dismiss();

                printTest();

                Log.e("print", PrintBill);

//                Log.e("mail",emailids.getText().toString());

                updateSaveSalesListMaster();
                updateSaveSalesList();


            }
        });


        paybycard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Creditdebitcarddialog();

            }
        });
        //  dialog.setCanceledOnTouchOutside(false);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        //  inflater.inflate(R.menu.menu_master_screen2, menu);
        inflater.inflate(R.menu.activity_main_actions, menu);
        // inflater.inflate(R.menu.menu_activity_salesbill,menu);
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        MenuItem item = menu.findItem(R.id.spinner_user);
        final MenuItem item2 = menu.findItem(R.id.TextView);
        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);
        user2 = (TextView) MenuItemCompat.getActionView(item2);
        // user2.setText(username);
        db = new DBhelper(ActivitySalesbill.this);
        ArrayList<Registeremployeesmodel> user_name = db.getusername();
        ArrayAdapter<Registeremployeesmodel> stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivitySalesbill.this, android.R.layout.simple_spinner_dropdown_item, user_name);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.d("slected item   :  ", iteam);
                if (!user2.getText().toString().trim().isEmpty()) {
                    iteam = spinner.getSelectedItem().toString();
//                    user2.setText("");
                    user2.setText(iteam);
                } else {
                    user2.setText(username);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return super.onCreateOptionsMenu(menu);

    }


    @Override

    public boolean onTouchEvent(MotionEvent event) {

        try {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
                    INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        } catch (NullPointerException ex) {
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


        switch (item.getItemId()) {
            case R.id.settlecustomer:
                Intent credit = new Intent(ActivitySalesbill.this, Activity_SettleCreditCustomer.class);
                startActivity(credit);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivitySalesbill.this);
                showIncentive.show();
                return true;
            case R.id.action_settings:
                Intent i = new Intent(ActivitySalesbill.this, ActivitySales.class);
                startActivity(i);
                return true;


            case R.id.action_settinghel:
                if (UsPrinter.getState() == 0) {
                    try {
                        // Toast.makeText(login.this,"Please connect with printer ",Toast.LENGTH_SHORT).show();
                        UsPrinter.showDeviceList(ActivitySalesbill.this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    return false;

                }
            case R.id.action_settinghelp:
                SaleshelpDialog();
                return true;

            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivitySalesbill.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in = new Intent(ActivitySalesbill.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_logout:
                // machineoff();
                Logout();

                return true;

           /* case R.id.action_username:
              System.out.print(username);
*/


            default:
                return super.onOptionsItemSelected(item);

        }


    }


    public void Logout() {
        Intent intent = new Intent(ActivitySalesbill.this, login.class);
        startActivity(intent);
    }

    public String HeaderValueFullBill() {
        store_name = db.getAllStores();
        store = (store_name.get(1).toString());
        storeAddress = store_name.get(2).toString();
        City = store_name.get(3).toString();
        Storenumber = store_name.get(4).toString();
        AlternateNo = store_name.get(5).toString();
        String formattedDate;
        StringBuilder strbuilder = new StringBuilder();
        int spaceSide = 3 + (40 - store.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(ActivitySalesbill.this.eol);
        strbuilder.append(getSpacer(spaceSide) + store + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesbill.this.eol);
        spaceSide = 3 + (40 - storeAddress.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(getSpacer(spaceSide) + storeAddress + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesbill.this.eol);
        spaceSide = 3 + (40 - City.length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(getSpacer(spaceSide) + City + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesbill.this.eol);
        spaceSide = 3 + (40 - Storenumber.length()) / PRINT_LEFT_MARGIN;
        if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {

            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN * 3) + "  Tel:" + Storenumber + "," + AlternateNo + getSpacer(spaceSide));
            strbuilder.append(ActivitySalesbill.this.eol);

        } else {

            strbuilder.append(getSpacer(spaceSide) + "Tel:" + Storenumber + getSpacer(spaceSide));
            strbuilder.append(ActivitySalesbill.this.eol);

        }
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Customer Name:");
        spaceSide = 3 + (40 - PosCust.getText().toString().length()) / PRINT_LEFT_MARGIN;
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + PosCust.getText().toString() + getSpacer(spaceSide));
        strbuilder.append(ActivitySalesbill.this.eol);

        formattedDate = new SimpleDateFormat("EEE,dd MMM yy hh:mma").format(Calendar.getInstance().getTime());
        int billDtSpace = 3 + 40 - ((String.valueOf(Integer.parseInt(Transid.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:  ");
        strbuilder.append(Transid.getText().toString());
        strbuilder.append(getSpacer(billDtSpace));
        strbuilder.append(formattedDate);
        strbuilder.append(ActivitySalesbill.this.eol);
        strbuilder.append(getDividerSales());
        strbuilder.append(ActivitySalesbill.this.eol);

        return String.valueOf(strbuilder);
    }

    public String FooterValueFullBill() {
        store_name = db.getAllStores();
        String Footer = store_name.get(6).toString();
        StringBuilder strbuilder = new StringBuilder();

        DecimalFormat f = new DecimalFormat("##.00");
        String Roundoffvalue = String.valueOf(f.format(Double.parseDouble(GrandTotal.getText().toString())));
        if (OverallTotalDiscount.getText().toString().equals("0.00") || OverallTotalDiscount.getText().toString().equals("")
                || OverallTotalDiscount.getText().toString().equals(".00")) {


            int spaces = 10 - String.valueOf(Totalitems.getText().toString()).length();
            int amtspaces = 8 - Roundoffvalue.length();
            strbuilder.append(TOTAL_ITEMS + Totalitems.getText().toString() + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);
            strbuilder.append(getSpacer(20) + "-------------");
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - NETPAYAMOUNT.length();
            int netvalspce = 9 - (grandtotalafterdiscount.getText().toString()).length();
            strbuilder.append(getSpacer(amtspaces) + NETPAYAMOUNT + getSpacer(netvalspce) + grandtotalafterdiscount.getText().toString() + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - RECEIVEAMOUNT.length();
            netvalspce = 9 - (f.format(Double.parseDouble(String.valueOf(reciveamt.getText().toString())))).length();
            strbuilder.append(getSpacer(amtspaces) + RECEIVEAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(reciveamt.getText().toString()))) + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - BALANCEAMOUNT.length();
            netvalspce = 9 - String.valueOf(f.format(Double.parseDouble(expectedchang.getText().toString().replace("-", "")))).length();
            strbuilder.append(getSpacer(amtspaces) + BALANCEAMOUNT + getSpacer(netvalspce) + String.valueOf(f.format(Double.parseDouble(expectedchang.getText().toString().replace("-", "")))) + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);
            strbuilder.append(getDividerSales());
            // strbuilder.append(ActivitySalesbill.this.eol);
        } else {
            int spaces = 10 - String.valueOf(Totalitems.getText().toString()).length();
            int amtspaces = 8 - Roundoffvalue.length();
            strbuilder.append(TOTAL_ITEMS + Totalitems.getText().toString() + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);

          /*  int Disamtspaces = getSpacer(26).length()-LINE_BILL_DISCOUNT.length();
            int netvalspce=6-(Totalsavings.getText().toString()).length();
            strbuilder.append(getSpacer(Disamtspaces)+LINE_BILL_DISCOUNT+getSpacer(netvalspce)+LineDiscountAmount.getText().toString()+getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);
*/
            int Disamtspaces = getSpacer(24).length() - BILL_DISC.length();
            int netvalspce = 9 - (OverallTotalDiscount.getText().toString()).length();

            // float OverAllDiscount=Float.parseFloat(Totalsavings.getText().toString())+Float.parseFloat(LineDiscountAmount.getText().toString());
            float OverAllDiscount = Float.parseFloat(OverallTotalDiscount.getText().toString());
            strbuilder.append(getSpacer(Disamtspaces) + BILL_DISC + getSpacer(netvalspce) + f.format(OverAllDiscount) + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);
            strbuilder.append(getSpacer(20) + "-------------");
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - NETPAYAMOUNT.length();
            netvalspce = 9 - (grandtotalafterdiscount.getText().toString()).length();
            strbuilder.append(getSpacer(amtspaces) + NETPAYAMOUNT + getSpacer(netvalspce) + grandtotalafterdiscount.getText().toString() + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - RECEIVEAMOUNT.length();
            netvalspce = 9 - (f.format(Math.round(Double.parseDouble(String.valueOf(reciveamt.getText().toString()))))).length();
            strbuilder.append(getSpacer(amtspaces) + RECEIVEAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(reciveamt.getText().toString()))) + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);

            amtspaces = getSpacer(24).length() - BALANCEAMOUNT.length();
            netvalspce = 9 - String.valueOf(f.format(Double.parseDouble(expectedchang.getText().toString().replace("-", "")))).length();
            strbuilder.append(getSpacer(amtspaces) + BALANCEAMOUNT + getSpacer(netvalspce) + String.valueOf(f.format(Double.parseDouble(expectedchang.getText().toString().replace("-", "")))) + getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(ActivitySalesbill.this.eol);
            strbuilder.append(getDividerSales());
            //1 strbuilder.append(ActivitySalesbill.this.eol);

        }
        return String.valueOf(strbuilder);
    }

    public void clearbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        //     alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                try {
                    if (adapter.getList().isEmpty()) {
                        return;
                    }
                    adapter.clearAllRows();
                    RemoteVideoPresentation.clearTotal();
                    invoiceno();
                    PosCust.setText("");
                    GrandTotal.setText(".0");
                    Totalitems.setText("0");
                    Totalsavings.setText(".0");
                    reciveamt.setText(".0");
                    setSummaryRow();

                } catch (Exception e) {
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


    public void clearbuttondialogforpaybycash() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        //     alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                try {
                    Intent in = new Intent(ActivitySalesbill.this, ActivitySalesbill.class);
                    startActivity(in);

                } catch (Exception e) {
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


////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////

///*************************CreditDebitcard dialog*************************/////


    public void Creditdebitcarddialog() {


        username = login.b.getString("Pos_User");
        DecimalFormat f3 = new DecimalFormat("##.00");

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.cebit_creditcard_dialog, null);
        alert = new AlertDialog.Builder(this);

        Creditclose = (Button) alertLayout.findViewById(R.id.closecreditdebitcard);
        Creditcard = (RadioButton) alertLayout.findViewById(R.id.creditcard);
        debitcard = (RadioButton) alertLayout.findViewById(R.id.debitcard);
        mastercard = (ImageButton) alertLayout.findViewById(R.id.mastercard);
        amexcard = (ImageButton) alertLayout.findViewById(R.id.amexcard);
        Creditcard.setChecked(true);


        Creditcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                debitcard.setChecked(false);


            }

        });

        Creditclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        debitcard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Creditcard.setChecked(false);

            }
        });


        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cardno.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });


        cardno = (EditText) alertLayout.findViewById(R.id.cardno_ed);
        cardno2 = (EditText) alertLayout.findViewById(R.id.cardno_ed2);
        cardno3 = (EditText) alertLayout.findViewById(R.id.cardno_ed3);
        cardno4 = (EditText) alertLayout.findViewById(R.id.cardno_ed4);

        cardholdername = (EditText) alertLayout.findViewById(R.id.cardholdername);
        cardbankname = (Spinner) alertLayout.findViewById(R.id.bankname);
        cardgrandtotal = (TextView) alertLayout.findViewById(R.id.cardamount);

        cardexpirytdate = (Spinner) alertLayout.findViewById(R.id.cardexpirydate_ed);
        cardexpirytdateyear = (Spinner) alertLayout.findViewById(R.id.cardexpirydate_year);
        cardpay = (Button) alertLayout.findViewById(R.id.pay);

        if (grandtotalafterdiscount.getText().toString().matches("") && Double.parseDouble(discountlinelevel.getText().toString()) <= .00) {
            cardgrandtotal.setText(PayResult.toString());
        } else if (Double.parseDouble(discountlinelevel.getText().toString()) > .00 && discount.getText().toString().matches("")) {
            cardgrandtotal.setText(String.valueOf(f3.format(Double.parseDouble(discountlinelevel.getText().toString()) + PayResult)));
        } else if (discount.getText().toString().matches("") && Double.parseDouble(discountamount.getText().toString()) > 0.00) {
            cardgrandtotal.setText(String.valueOf(f3.format(Double.parseDouble(discountlinelevel.getText().toString()) + PayResult - Double.parseDouble(discountamount.getText().toString()))));
        } else {
            cardgrandtotal.setText(String.valueOf(f3.format(Double.parseDouble(discountlinelevel.getText().toString()) + PayResult - Double.parseDouble(discountamount.getText().toString()))));
        }

        ArrayList<String> BankNameArraylist = db.getBankName();
        ArrayAdapter<String> BankNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BankNameArraylist);
        cardbankname.setAdapter(BankNameAdapter);


        ArrayAdapter<CharSequence> bankadapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        bankadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardexpirytdate.setAdapter(bankadapter);


        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.year_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardexpirytdateyear.setAdapter(adapter1);


        cardno.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = cardno.getText().length();

                if (textlength1 >= 4) {
                    cardno2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });

        cardno2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = cardno2.getText().length();

                if (textlength1 >= 4) {
                    cardno3.requestFocus();
                }
            }


            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });
        cardno3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = cardno3.getText().length();

                if (textlength1 >= 4) {
                    cardno4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });
        cardno4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = cardno4.getText().length();

                if (textlength1 >= 4) {
                    cardholdername.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });


        cardpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardtype = cardno.getText().toString();

                if (cardtype.startsWith("5")) {
                    cardtype = "MASTERCARD";
                } else if (cardtype.startsWith("4")) {
                    cardtype = "VISA";
                } else if (cardtype.startsWith("3")) {
                    cardtype = "AMEX";

                } else {
                    cardtype = "VISA";

                }
                try {
                    BankNameSelected = cardbankname.getSelectedItem().toString();
                    String imeino = imeibill.getText().toString();
                    tp.setColor(Color.BLUE);
                    tp.setTextSize(26);
                    tp.setTypeface(tff);

                    Transid.setText(String.valueOf(getBillnumber()));
                    // PaySelling = Double.parseDouble(reciveamt.getText().toString());
//                    PayPurchase = Double.parseDouble(grandtotalafterdiscount.getText().toString());
                  /*  if (PaySelling < PayPurchase) {
                        Toast.makeText(getApplicationContext(), "Please enter Amount Received > = Sales Value", Toast.LENGTH_SHORT).show();
                        return;

                    }*/
                    if (adapter.getList().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (cardno4.getText().toString().isEmpty() || cardholdername.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (cardexpirytdate.toString().matches("MM") || cardexpirytdateyear.toString().matches("YYYY")) {
                        Toast.makeText(getApplicationContext(), "Please fill field", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    db.savecarddetail(Transid.getText().toString(), adapter.getList(), user2.getText().toString(), cardno4.getText().toString(), BankNameSelected, cardtype);
                    db.insertdetailsifpaybaycard(Transid.getText().toString(), GrandTotal.getText().toString(), user2.getText().toString(), cardno4.getText().toString(), BankNameSelected, cardgrandtotal.getText().toString(), cardholdername.getText().toString(), OverallTotalDiscount.getText().toString());

                    db.updateStockQty(adapter.getList());
                    db.insertdataIntosendMailforSales(Transid.getText().toString(), PosCust.getText().toString(), user2.getText().toString(), emailids.getText().toString());
                    updateSaveSalesListforcard();


                    //////////////////////////////////////Printer testing ///////////////////////////////////////
                    store_name = db.getAllStores();
                    store = (store_name.get(1).toString());
                    //   String Store_ID = (store_name.get(0).toString());
                    storeAddress = store_name.get(2).toString();
                    City = store_name.get(3).toString();
                    Storenumber = store_name.get(4).toString();
                    AlternateNo = store_name.get(5).toString();
                    Footer = store_name.get(6).toString();
                    amountdiscount = discountamount.getText().toString();
                    Totalsavings.setText(amountdiscount);

                    DecimalFormat f = new DecimalFormat("##.00");
                    for (int i = 1; i <= paybycashbillcopy; i++) {
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
                            strbuilder.append(ActivitySalesbill.this.eol);
                        }
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                        strbuilder.setLength(0);
                        strbuilder.append("Customer Name:" + cardholdername.getText().toString());
                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append("Card Number:" + "XXXX-XXXX-XXXX-" + cardno4.getText().toString());
                        strbuilder.append(ActivitySalesbill.this.eol);

                        String formattedDate;
                        formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                        int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(Transid.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                        strbuilder.append("Bill No:");
                        strbuilder.append(Transid.getText().toString());
                        strbuilder.append(getSpacer(billDtSpace));
                        strbuilder.append(formattedDate);
                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append(getDividerSales());
                        strbuilder.append(ActivitySalesbill.this.eol);

                        if (MRPisShown.matches("Y")) {
                            strbuilder.append(" ProductName" + "       " + "MRP");
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");

                            //strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);
                            for (Sales prod : adapter.getList()) {
                                String ProuctName = prod.getProductName();
                                if (ProuctName.length() >= 21) {
                                    ProuctName = ProuctName.substring(0, 21);
                                }
                                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);

                                String printMrp = String.valueOf(f.format(prod.getMrp()));
                                int spaces = 10 - printMrp.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                }

                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(ActivitySalesbill.this.eol);

                                String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                spaces = 9 - printSPrice.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                String printqty = String.valueOf(prod.getQuantity());
                                spaces = 3 - printqty.length();
                                strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                spaces = 7 - printDiscount.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                if (prod.getDiscountamount() > 0.00) {
                                    String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                    spaces = 8 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);
                                } else {
                                    String printtotal = String.valueOf(f.format(prod.getTotal()));
                                    spaces = 8 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);
                                }

                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(ActivitySalesbill.this.eol);


                            }
                        } else {
                            strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);
                            for (Sales prod : adapter.getList()) {

                                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                                strbuilder.append(ActivitySalesbill.this.eol);

                                String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                int spaces = 9 - printSPrice.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                String printqty = String.valueOf(prod.getQuantity());
                                spaces = 3 - printqty.length();
                                strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                spaces = 7 - printDiscount.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                if (prod.getDiscountamount() > 0.00) {
                                    String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                    spaces = 8 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);
                                } else {
                                    String printtotal = String.valueOf(f.format(prod.getTotal()));
                                    spaces = 8 - printtotal.length();
                                    strbuilder.append(getSpacer(spaces) + printtotal);
                                }
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(ActivitySalesbill.this.eol);

                            }
                        }
                        strbuilder.append(getDividerSales());

                       /* String getFooterpart = FooterValueFullBill();
                        strbuilder.append(getFooterpart);*/

                        DecimalFormat f1 = new DecimalFormat("##.00");
                        String Roundoffvalue = String.valueOf(f1.format(Double.parseDouble(cardgrandtotal.getText().toString())));
                        int amtspaces = 8 - Roundoffvalue.length();
                        strbuilder.append(TOTAL_ITEMS + Totalitems.getText().toString() + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append(getSpacer(20) + "-------------");
                        strbuilder.append(ActivitySalesbill.this.eol);

                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                        strbuilder.setLength(0);
                        //  cardgrandtotal
                        if (FooterShown.matches("Y")) {
                            int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                            strbuilder.append(Footer);
                            strbuilder.append(ActivitySalesbill.this.eol);
                        }
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                        strbuilder.setLength(0);
                        UsPrinter.print();
                        UsPrinter.FeedLine();
                        // UPrinter.FeedLine();
                        UsPrinter.FullCut();

                    }
                    Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();
                    adapter.clearAllRows();
                    GrandTotal.setText("");
                    Totalitems.setText("");
                    reciveamt.setText("");
                    Totalsavings.setText("");
                    adapter.clearAllRows();
                    PosCust.setText("");
                    invoiceno();

                    PosCust.setText("");
                    dialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        alert.setView(alertLayout);
        dialog = alert.create();
        dialog.setTitle("PAY CARD");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //****************************************upload code for doctor dialog******************************
    public void insertDoctor() {

        final String dr_name = doctname.getText().toString().trim();
        // final String dr_address = DOCTORADDRESS.getText().toString().trim();
        final String dr_speciality = itemdoctor.toString();
        final String dr_username = user2.getText().toString();
        final String dr_id = getSystemCurrentTime();
        final String dr_active = SpinValue.toString();

        PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(ActivitySalesbill.this);

        class UpdateDoctor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(Activity_Doctor.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(Activity_Doctor.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.DOCTOR_DISCRIPTION_STORE_ID, store_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ID, dr_id);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_NAME, dr_name);
                    //  hashMap.put(Config.DOCTOR_DISCRIPTION_ADDRESS, dr_address);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_SPECIALITY, dr_speciality);
                    hashMap.put(Config.Retail_Pos_user, dr_username);
                    hashMap.put(Config.DOCTOR_DISCRIPTION_ACTIVE, dr_active);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.INSERT_DOCTOR, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        db.updatedoctorsflag(dr_id);


                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        //  return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdateDoctor updatedr = new UpdateDoctor();
        updatedr.execute();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
////********************************************local product dialog**************************************
    public void updateLocalProduct() {

        final DBhelper mydb = new DBhelper(ActivitySalesbill.this);

        //Local_prod_store_id =local_prod_store_id.getText().toString().trim();
        final String local_prod_id = productid.getText().toString().trim();
        final String local_product_name = productname.getText().toString().trim();
        final String local_product_bare_code = localbarcode.getText().toString().trim();
        final String local_product_mrp = localhmrp.getText().toString().trim();
        final String local_product_selling_price = localsprice.getText().toString().trim();
        final String local_product_purchase_price = localpprice.getText().toString().trim();
        final String local_product_active = localactive.getSelectedItem().toString().trim();
        final String local_product_margin = localmargin.getText().toString().trim();
        final String local_product_discount = localdiscount.getText().toString().trim();
        final String local_product_uom = localuom.getText().toString().trim();
        final String pos_user = user2.getText().toString();


        PersistenceManager.saveStoreId(ActivitySalesbill.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(this);
        class UpdateLocalProduct extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityLocalProduct.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(ActivityLocalProduct.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_STORE_ID, store_id);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PRODUCT_ID, local_prod_id);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PRODUCT_NAME, local_product_name);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_BARE_CODE, local_product_bare_code);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_MRP, local_product_mrp);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_SELLING_PRICE, local_product_selling_price);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PURCHASE_PRICE, local_product_purchase_price);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_ACTIVE, local_product_active);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PROFIT_MARGIN, local_product_margin);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_DISCOUNT, local_product_discount);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PROFIT_UOM, local_product_uom);

                    hashMap.put(Config.Retail_Pos_user, pos_user);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_PRODUCT, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        mydb.updateflaglocalproduct(local_prod_id);


                        ///  return s.getString(TAG_MESSAGE);
                    } else {

                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }
        UpdateLocalProduct updateLocalProduct = new UpdateLocalProduct();
        updateLocalProduct.execute();
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    //************************************ UPLOAD sales pay by cash dialog***************************************
    public void updateSaveSalesList() {
        final String grand_total = GrandTotal.getText().toString();
        final String sale_date = db.getDate();
        final String sale_time = db.getTime();
        final String saleimeino = imeibill.getText().toString();
        final String SaveCustname = PosCust.getText().toString();
        final String DOCTNAME = doctor.getText().toString();
      /*
      db.insertdetailsifpaybaycash(Transid.getText().toString(), GrandTotal.getText().toString(), username, imeino, discountamount.getText().toString(),grandtotalafterdiscount.getText().toString(),OverallTotalDiscount.getText().toString(),TotalGrandAmountPopup.getText().toString(),expectedchang.getText().toString(),reciveamt.getText().toString());

        */
        for (Sales sales : adapter.getList()) {

            final String salestransaction = Transid.getText().toString();
            final String save_sales_prod_nm = sales.getProductName();
            final String save_sales_prod_Id = sales.getProdid();
            final String save_sales_expiry_date = sales.getExpiry();
            final String save_sales_s_price = String.valueOf(sales.getSPrice());
            final String save_sales_qty = String.valueOf(sales.getQuantity());
            final String save_sales_mrp = String.valueOf(sales.getMrp());
            final String save_sales_batch_no = sales.getBatchNo();
            final String save_sales_uom = sales.getUom();
            final String save_sales_total = String.valueOf(sales.getTotal() - sales.getDiscountamountsalestotal());
            final String save_sales_LineDisc = String.valueOf(sales.getDiscountsales());
            final String save_sales_LineDiscAmount = String.valueOf(sales.getDiscountamountsalestotal());
            save_sales_con_mul_qty = String.valueOf(sales.getStockquant());
            final String mailid = emailids.getText().toString();
            final String pos_user = user2.getText().toString();


            final String totalGrandAmountPopup = TotalGrandAmountPopup.getText().toString();
            final String overallTotalDiscount = OverallTotalDiscount.getText().toString();
            final String Grand_totalafterdiscount = grandtotalafterdiscount.getText().toString();
            final String Reciveamt = reciveamt.getText().toString();
            final String Expectedchang = expectedchang.getText().toString();

            stockQuantity = sales.getStockquant();
            saleQuantity = sales.getQuantity();

            if (saleQuantity <= stockQuantity) {

                newStockQuantity = stockQuantity - saleQuantity;
                save_sales_con_mul_qty = Double.toString(newStockQuantity);

            } else if (saleQuantity > stockQuantity) {
                newStockQuantity = stockQuantity - saleQuantity;
                newstockQuant = stockQuantity - newStockQuantity;
                newstock = saleQuantity - newstockQuant;
                save_sales_con_mul_qty = Double.toString(newstock);

            }
            PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
            save_sales_store_id = PersistenceManager.getStoreId(ActivitySalesbill.this);
            store_ID = save_sales_store_id.substring(0, 10);


            class UpdateActivitySalesbill extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {

                    try {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.SAVE_SALES_LIST_TRI_ID, salestransaction);
                        hashMap.put(Config.SAVE_SALES_LIST_STORE_ID, store_ID);
                        hashMap.put(Config.SAVE_SALES_LIST_BATCH, save_sales_batch_no);
                        hashMap.put(Config.SAVE_SALES_LIST_PRODID, save_sales_prod_Id);
                        hashMap.put(Config.SAVE_SALES_LIST_PROD_NAME, save_sales_prod_nm);
                        hashMap.put(Config.SAVE_SALES_LIST_EXPIRY_DATE, save_sales_expiry_date);
                        hashMap.put(Config.SAVE_SALES_LIST_MRP, save_sales_mrp);
                        hashMap.put(Config.SAVE_SALES_LIST_UOM, save_sales_uom);
                        hashMap.put(Config.SAVE_SALES_LIST_TOTAL, save_sales_total);
                        hashMap.put(Config.SAVE_SALES_LIST_QTY, save_sales_qty);
                        hashMap.put(Config.SAVE_SALES_LIST_S_PRICE, save_sales_s_price);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_GRAND_TOTAL, grand_total);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_DATE, sale_date);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_TIME, sale_time);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_DISCOUNT, "");
                        hashMap.put(Config.Retail_Pos_user, pos_user);
                        hashMap.put(Config.RETAIL_STR_STOCK_MASTER, save_sales_con_mul_qty);
                        hashMap.put(Config.SAVE_LIST_PREFIX_NM, "Sales-");
                        hashMap.put(Config.SAVE_LIST_PO_No, salestransaction);
                        hashMap.put(Config.STR_SALES_DETAILS_IMEI, saleimeino);
                        hashMap.put(Config.STR_SALES_LINEDISC, save_sales_LineDisc);
                        hashMap.put(Config.STR_SALES_LINEDISC_AMOUNT, save_sales_LineDiscAmount);
                        hashMap.put(Config.STR_SALES_SaveTOTALBILLAMOUNT, totalGrandAmountPopup);
                        hashMap.put(Config.STR_SALES_SaveTOTALBIllDiscount, overallTotalDiscount);
                        hashMap.put(Config.STR_SALES_SaveFINALBILLAMOUNT, Grand_totalafterdiscount);
                        hashMap.put(Config.STR_SALES_SaveRECEIVEDBILLAMOUNT, Reciveamt);
                        hashMap.put(Config.STR_SALES_SaveEXPECTEDBILLAMOUNT, Expectedchang);
                        hashMap.put(Config.MAIL_ID, mailid);

                        hashMap.put("CUST_NAME", SaveCustname);
                        hashMap.put("DOCT_NAME", DOCTNAME);

                        JSONParserSync rh = new JSONParserSync();
                        // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/Pay_By_Cash.jsp", hashMap);
                        JSONObject s = rh.sendPostRequest(Config.SALES_BILL, hashMap);
                        // String hit = rh.sendGetRequest("https://99mailpos.eu-gb.mybluemix.net/Sales.jsp");
                        URL url = new URL("https://99mailpos.eu-gb.mybluemix.net/Sales.jsp");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());
                            db.updatesalesflagdetail(salestransaction, save_sales_batch_no, save_sales_prod_Id);
                            // db.updateflagsavesalesMaster(salestransaction);
                            db.updateflagSavePdfDetailForsale(salestransaction);

//                            return s.getString(TAG_MESSAGE);
                        } else {
                            // return s.getString(TAG_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //loading = ProgressDialog.show(ActivitySalesbill.this, "Updating Sales...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    // loading.dismiss();
                    // Toast.makeText(ActivitySalesbill.this, s, Toast.LENGTH_LONG).show();
                }
            }

            UpdateActivitySalesbill activitysalesbil = new UpdateActivitySalesbill();
            activitysalesbil.execute();
        }
    }


    public void updateSaveSalesListforcard() {


        final String grand_total = GrandTotal.getText().toString();
        final String sale_date = db.getDate();
        final String sale_time = db.getTime();
        final String pos_user = user2.getText().toString();
        final String saleimeino = imeibill.getText().toString();
        final String forcardbankname = BankNameSelected;
        final String forcardnumber = cardno4.getText().toString();
        final String forcardtype = cardtype.toString();
        final String save_sales_CUSTNMAE = String.valueOf(cardholdername.getText().toString());

        for (Sales sales : adapter.getList()) {
            final String salestransaction = Transid.getText().toString();
            final String save_sales_prod_nm = sales.getProductName();
            final String save_sales_prod_Id = sales.getProdid();
            final String save_sales_expiry_date = sales.getExpiry();
            final String save_sales_s_price = String.valueOf(sales.getSPrice());
            final String save_sales_qty = String.valueOf(sales.getQuantity());
            final String save_sales_mrp = String.valueOf(sales.getMrp());
            final String save_sales_batch_no = sales.getBatchNo();
            final String save_sales_uom = sales.getUom();
            final String save_sales_total = String.valueOf(sales.getTotal());

            save_sales_con_mul_qty = String.valueOf(sales.getStockquant());

            stockQuantity = sales.getStockquant();
            saleQuantity = sales.getQuantity();

            if (saleQuantity <= stockQuantity) {

                newStockQuantity = stockQuantity - saleQuantity;
                save_sales_con_mul_qty = Double.toString(newStockQuantity);

            } else if (saleQuantity > stockQuantity) {
                newStockQuantity = stockQuantity - saleQuantity;
                newstockQuant = stockQuantity - newStockQuantity;
                newstock = saleQuantity - newstockQuant;
                save_sales_con_mul_qty = Double.toString(newstock);

            }
            PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
            save_sales_store_id = PersistenceManager.getStoreId(ActivitySalesbill.this);
            store_ID = save_sales_store_id.substring(0, 10);

            class UpdateActivitySalesbill extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {

                    try {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.SAVE_SALES_LIST_TRI_ID, salestransaction);
                        hashMap.put(Config.SAVE_SALES_LIST_STORE_ID, store_ID);
                        hashMap.put(Config.SAVE_SALES_LIST_BATCH, save_sales_batch_no);
                        hashMap.put(Config.SAVE_SALES_LIST_PRODID, save_sales_prod_Id);
                        hashMap.put(Config.SAVE_SALES_LIST_PROD_NAME, save_sales_prod_nm);
                        hashMap.put(Config.SAVE_SALES_LIST_EXPIRY_DATE, save_sales_expiry_date);
                        hashMap.put(Config.SAVE_SALES_LIST_MRP, save_sales_mrp);
                        hashMap.put(Config.SAVE_SALES_LIST_UOM, save_sales_uom);
                        hashMap.put(Config.SAVE_SALES_LIST_TOTAL, save_sales_total);
                        hashMap.put(Config.SAVE_SALES_LIST_QTY, save_sales_qty);
                        hashMap.put(Config.SAVE_SALES_LIST_S_PRICE, save_sales_s_price);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_GRAND_TOTAL, grand_total);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_DATE, sale_date);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_TIME, sale_time);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_BUSINESS_DATE, "");
                        hashMap.put(Config.Retail_Pos_user, pos_user);
                        hashMap.put(Config.RETAIL_STR_STOCK_MASTER, save_sales_con_mul_qty);
                        hashMap.put(Config.SAVE_LIST_PREFIX_NM, "Sales-");
                        hashMap.put(Config.SAVE_LIST_PO_No, salestransaction);
                        hashMap.put(Config.STR_SALES_DETAILS_IMEI, saleimeino);
                        hashMap.put(Config.STR_SALES_CARDTYPE, forcardtype);
                        hashMap.put(Config.STR_SALES_BANKNAME, forcardbankname);
                        hashMap.put(Config.STR_SALES_CARDNUMBER, forcardnumber);
                        hashMap.put("CUST_NAME", save_sales_CUSTNMAE);

                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.SALES_BILLBYCARD, hashMap);

                        //     Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            db.updatesalesflagdetail(salestransaction, save_sales_batch_no, save_sales_prod_Id);
                            //  db.updateflagsavesalesMaster(salestransaction);
                            db.updateflagSavePdfDetailForsale(salestransaction);

//                            return s.getString(TAG_MESSAGE);
                        } else {

                            //                           return s.getString(TAG_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //loading = ProgressDialog.show(ActivitySalesbill.this, "Updating Sales...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    // loading.dismiss();
                    // Toast.makeText(ActivitySalesbill.this, s, Toast.LENGTH_LONG).show();
                }
            }

            UpdateActivitySalesbill activitysalesbil = new UpdateActivitySalesbill();
            activitysalesbil.execute();
        }
    }


    public String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }

    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }

    public void calculategrangtotalafterdiscount() {
        try {

            DecimalFormat f = new DecimalFormat("##.00");
            Float Getval = Float.parseFloat(linediscount.getText().toString());
            Log.e("%%%line Called", Getval.toString());
            ValueBeforeDiscount = Float.parseFloat(GrandTotal.getText().toString());
            Log.e("%%%grandtotal Called", ValueBeforeDiscount.toString());
            Float line = Float.parseFloat(LineDiscountAmount.getText().toString());

            PayResult = Float.valueOf(f.format(ValueBeforeDiscount - (Getval)));

            Finalgrandtotaldisp.setText(PayResult.toString());
            Log.e("%%%final Called", PayResult.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getBillnumber() {
        Cursor res = db.getBill();
        if (res != null && res.moveToFirst()) {

            String store = res.getString(res.getColumnIndex(DBhelper.COLUMN_BILL_NUMBER));
            Billnumber = Integer.parseInt(store);

        }

        Billnumber++;
        String _stringVal = Integer.toString(Billnumber);
        return _stringVal;
    }

    private String getDividerSales() {
        //this for ls printer
        return "---------------------------------  ";
    }

    public void writeData(Sales sales) {


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "Add");
            jsonObject.put("Product", sales.getProductName());
            jsonObject.put("Price", sales.getSPrice());
            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setCustomerName(String customerName) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Method", "CustomerAdd");
            jsonObject.put("Name", customerName);
            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteData(int sales) {

        int i = 0;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "Delete");
            jsonObject.put("PositionDelete", sales);

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int position, int quantity) {

        int i = 0;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "Quantity");
            jsonObject.put("QuantityUpdate", quantity);
            jsonObject.put("Position", position);

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
            Log.d("JSONObject", String.valueOf(jsonObject));
        } catch (JSONException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // *****################*************06 april 2017************************************ update sales price in media screen

    public void updateSalesPrice(int position, int quantity) {

        int i = 0;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "SPrice");
            jsonObject.put("SPriceUpdate", quantity);
            jsonObject.put("Position", position);

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
            Log.d("JSONObject", String.valueOf(jsonObject));
        } catch (JSONException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateDiscountByPercentage(String discountPercentage) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Method", "AddDisountPercentage");
            jsonObject.put("Discount", discountPercentage);
            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void updateGrandTotalAfterDiscount(String grandTotalAfterDscount) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("Method", "AddGrandTotalAfterDiscount");
            jsonObject.put("GrandTotalAfterDiscount", grandTotalAfterDscount);
            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // *****################*************06 april 2017************************************ update sales price in media screen


    public void fullScreen() {

        Log.d("FullScreen", "Entering full Screen mode");
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "FullScreen");

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);

        } catch (JSONException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void showBill(String storeName) {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Method", "ShowBill");
            jsonObject.put("STORENAME", storeName);

            UssPrinter.Write(String.valueOf(jsonObject));
            UssPrinter.Write(SerialPortHelper.EOT_COMMAND);
        } catch (JSONException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*  public void showBill(){

          try {

              JSONObject jsonObject = new JSONObject();
              jsonObject.put("Method","ShowBill");
              UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
              UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);


          } catch (JSONException e) {

          }

      }*/
    public void startReading() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Method", "StartReading");
        } catch (JSONException e) {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Connect() {
        try {
            UsbPrinter.connectSerialPort();
            Log.d("***Connected", "Succesfull");
            showBill(store);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class TalkThread extends Thread {
        private AtomicBoolean working;

        TalkThread() {
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

        public void stopThread() {
            working.set(false);
            Log.i("NGX", "Data write stopped");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        // unregisterReceiver(mUsbReceiver);
        fullScreen();


    }

    public void PerformClick() {
        class ConnectionSerial extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Connect();


            }

            @Override
            protected String doInBackground(Void... params) {
                UsbPrinter.initServices(getApplicationContext(), Handler);
                UsbPrinter.initSerialPortServices();


                return null;
            }
        }

        ConnectionSerial updateShell = new ConnectionSerial();
        updateShell.execute();

    }

    public void Reprintdialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertlayout = inflater.inflate(R.layout.reprint_bill_dialog, null);
        final AlertDialog.Builder reprint_dialog = new AlertDialog.Builder(ActivitySalesbill.this);
      final  AlertDialog alertDialog=reprint_dialog.create();
        reprint_dialog.setTitle("             Reprint Bill");
        reprint_dialog.setMessage("Enter Transaction id");
        reprint_bill_dialog_text = (EditText) alertlayout.findViewById(R.id.reprint_bill_text);
        Button Print=(Button)alertlayout.findViewById(R.id.Printrprt);
        Button Cancel=(Button)alertlayout.findViewById(R.id.Cancelrprt);
        alertlayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(reprint_bill_dialog_text.getWindowToken(), 0);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }

                return true;
            }

        });
        reprint_dialog.setView(alertlayout);
        reprint_dialog.show();
        Print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String bill_Transaction_id = reprint_bill_dialog_text.getText().toString();
                    Log.e("!!!!!!!!!!!!!!!!!", "" + bill_Transaction_id.toString());

                    if (bill_Transaction_id.equals("")) {

                        Toast.makeText(ActivitySalesbill.this, "Enter Transaction id", Toast.LENGTH_LONG).show();

                        return;

                    } else {

                        get_Transcation_List = db.get_Transaction_Id_Bill(bill_Transaction_id);
//////////////////////////////////////Printer testing ///////////////////////////////////////

                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial", Typeface.BOLD));
                        tp.setTypeface(tff);
                        store_name = db.getAllStores();
                        store = (store_name.get(1).toString());
                        storeAddress = store_name.get(2).toString();
                        City = store_name.get(3).toString();
                        Storenumber = store_name.get(4).toString();
                        AlternateNo = store_name.get(5).toString();
                        Footer = store_name.get(6).toString();
                        Grandtotal = GrandTotal.getText().toString();
                        if (get_Transcation_List.size() > 0)

                        {
                            Sales card_Details = db.get_Card_Transaction_Id_Bill_Master(reprint_bill_dialog_text.getText().toString());
                            String card_No = card_Details.getCardHolderNumber();
                            String cardGrandTotal = card_Details.getCardGrandTotal();
                            String customer_name_paybycard = card_Details.getCustomerName();
                            String saledate_paybycard = card_Details.getDate();
                            String saletime_paybycard = card_Details.getSalesTime();

                            Sales getAllmasterinfoforprinter = db.get_Transaction_Id_Bill_Master(reprint_bill_dialog_text.getText().toString());
                            float expectdchnge = getAllmasterinfoforprinter.getSaveEXPECTEDBILLAMOUNT();
                            float fnlillamt = getAllmasterinfoforprinter.getSaveFINALBILLAMOUNT();
                            float receidamt = getAllmasterinfoforprinter.getSaveRECEIVEDBILLAMOUNT();
                            float totalbillamt = getAllmasterinfoforprinter.getSaveTOTALBILLAMOUNT();
                            float savetotalbilldscount = getAllmasterinfoforprinter.getSaveTOTALBIllDiscount();
                            String customer_name = getAllmasterinfoforprinter.getCustomerName();
                            String saledate = getAllmasterinfoforprinter.getDate();
                            String saletime = getAllmasterinfoforprinter.getSalesTime();
                            if (totalbillamt != 0.00 && TextUtils.isEmpty(card_No)) {

                                DecimalFormat f = new DecimalFormat("##.00");
                                for (int i = 1; i <= paybycashbillcopy; i++) {
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
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    }
                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                                    strbuilder.setLength(0);
                                    if (TextUtils.isEmpty(customer_name) || customer_name.matches("")) {
                                    } else {
                                        strbuilder.append("Customer Name:" + customer_name);
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    }
                                           /* if (card_No.matches(" "))
                                            {
                                                strbuilder.append(ActivitySalesbill.this.eol);
                                            }
                                            else {
                                                strbuilder.append("Card Number:" + "XXXX-XXXX-XXXX-" + card_No);
                                                strbuilder.append(ActivitySalesbill.this.eol);
                                            }*/

                                   /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                                    Date testDate = null;
                                    try {
                                        testDate = sdf.parse(saledate);
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                    }
                                    SimpleDateFormat formatter = new SimpleDateFormat(" EEEMMMdd yy");
*/
                                    // String newFormat = formatter.format(testDate);
                                    // Date dt = new Date(saletime);
                                    SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
                                    Date time1 = null;
                                    try {
                                        time1 = timeformat.parse(saletime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat newtimeee = new SimpleDateFormat("hh:mm a");
                                    String timeeeee = newtimeee.format(time1);
                                    //SimpleDateFormat outdate = new SimpleDateFormat("yyMMMdd");
                                    String sales_date_reprint = saledate.concat(" ").concat(timeeeee);
                                    String formattedDate;
                                    formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                                    int billDtSpace = 39 - ((String.valueOf(reprint_bill_dialog_text.getText().toString()).length() + BILL_DATE_COLUMN) + sales_date_reprint.length());
                                    strbuilder.append("Bill No:");
                                    strbuilder.append(reprint_bill_dialog_text.getText().toString());
                                    strbuilder.append(getSpacer(billDtSpace));
                                    strbuilder.append(sales_date_reprint);
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(getDividerSales());
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    if (MRPisShown.matches("Y")) {
                                        strbuilder.append(" ProductName" + "       " + "MRP");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(getDividerSales());
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        for (Sales prod : get_Transcation_List) {
                                            String ProuctName = prod.getProductName();
                                            if (ProuctName.length() >= 21) {
                                                ProuctName = ProuctName.substring(0, 21);
                                            }
                                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);
                                            String printMrp = String.valueOf(f.format(prod.getMrp()));
                                            int spaces = 10 - printMrp.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                            spaces = 9 - printSPrice.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printSPrice +
                                                        getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            String printqty = String.valueOf(prod.getQuantity());
                                            spaces = 3 - printqty.length();
                                            strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                            String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                            spaces = 7 - printDiscount.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            if (prod.getDiscountamount() > 0.00) {
                                                String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            } else {
                                                String printtotal = String.valueOf(f.format(prod.getTotal()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            }
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                        }
                                    } else {
                                        strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(getDividerSales());
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        for (Sales prod : get_Transcation_List) {

                                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());

                                            strbuilder.append(ActivitySalesbill.this.eol);

                                            String printSPrice = String.valueOf(f.format(prod.getSPrice()));

                                            int spaces = 9 - printSPrice.length();

                                            if (spaces == 0) {

                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                            } else {

                                                strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                            }

                                            String printqty = String.valueOf(prod.getQuantity());

                                            spaces = 3 - printqty.length();

                                            strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                            String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));

                                            spaces = 7 - printDiscount.length();

                                            if (spaces == 0) {

                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                            } else {

                                                strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                            }

                                            if (prod.getDiscountamount() > 0.00) {

                                                String printtotal = String.valueOf(f.format(prod.getDiscountamount()));

                                                spaces = 8 - printtotal.length();

                                                strbuilder.append(getSpacer(spaces) + printtotal);

                                            } else {

                                                String printtotal = String.valueOf(f.format(prod.getTotal()));

                                                spaces = 8 - printtotal.length();

                                                strbuilder.append(getSpacer(spaces) + printtotal);

                                            }

                                            strbuilder.append(ActivitySalesbill.this.eol);

                                            strbuilder.append(ActivitySalesbill.this.eol);

                                        }

                                    }

                                    strbuilder.append(getDividerSales());

                                    DecimalFormat f1 = new DecimalFormat("##.00");
                                    int poud = 0;

                                    for (Sales prod : get_Transcation_List) {

                                        poud++;

                                    }

                                    Log.e("mastervalueforprinter", "" + expectdchnge + fnlillamt + receidamt + totalbillamt + savetotalbilldscount);

                                    String Roundoffvalue = String.valueOf(f1.format(fnlillamt));

                                    // if (Totalsavings.getText().toString().equals("")||Totalsavings.getText().toString().equals("0.00")||Totalsavings.getText().toString().equals(".00")) {

                                    int spaces = 10 - String.valueOf(poud).length();

                                    int amtspaces = 8 - Roundoffvalue.length();

                                    strbuilder.append(TOTAL_ITEMS + poud + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));

                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    int Disamtspaces = getSpacer(24).length() - BILL_DISC.length();

                                    int netvalspce = 9 - String.valueOf(savetotalbilldscount).length();

                                    strbuilder.append(getSpacer(Disamtspaces) + BILL_DISC + getSpacer(netvalspce) + f.format(savetotalbilldscount) + getSpacer(PRINT_LEFT_MARGIN));

                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    strbuilder.append(getSpacer(20) + "-------------");

                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    amtspaces = getSpacer(24).length() - NETPAYAMOUNT.length();
                                    netvalspce = 9 - String.valueOf(totalbillamt).length();
                                    strbuilder.append(getSpacer(amtspaces) + NETPAYAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(totalbillamt))) + getSpacer(PRINT_LEFT_MARGIN));

                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    amtspaces = getSpacer(24).length() - RECEIVEAMOUNT.length();

                                    netvalspce = 9 - (f.format(Double.parseDouble(String.valueOf(expectdchnge)))).length();

                                    strbuilder.append(getSpacer(amtspaces) + RECEIVEAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(expectdchnge))) + getSpacer(PRINT_LEFT_MARGIN));

                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    amtspaces = getSpacer(24).length() - BALANCEAMOUNT.length();

                                    netvalspce = 9 - String.valueOf(f.format(receidamt)).length();

                                    strbuilder.append(getSpacer(amtspaces) + BALANCEAMOUNT + getSpacer(netvalspce) + String.valueOf(f.format(receidamt)).replace("-", " ") + getSpacer(PRINT_LEFT_MARGIN));

                                    strbuilder.append(ActivitySalesbill.this.eol);


                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

                                    strbuilder.setLength(0);

                                    strbuilder.append(getDividerSales());

                                    if (FooterShown.matches("Y")) {

                                        int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;

                                        strbuilder.append(Footer);

                                        strbuilder.append(ActivitySalesbill.this.eol);

                                    }

                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                                    strbuilder.setLength(0);
                                    UsPrinter.print();
                                    UsPrinter.FeedLine();
                                    UsPrinter.FullCut();

                                    Intent intent = new Intent(getApplicationContext(), ActivitySalesbill.class);
                                    startActivity(intent);

                                }
                            } else
                            {
                                DecimalFormat f = new DecimalFormat("##.00");
                                for (int i = 1; i <= paybycashbillcopy; i++) {
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
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    }
                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                                    strbuilder.setLength(0);
                                    if (TextUtils.isEmpty(customer_name) || customer_name.matches("")) {
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    } else {
                                        strbuilder.append("Customer Name:" + customer_name);
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    }

                                        strbuilder.append("Card Number:" + "XXXX-XXXX-XXXX-" + card_No);
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
                                    Date time1 = null;
                                    try {
                                        time1 = timeformat.parse(saletime_paybycard);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat newtimeee = new SimpleDateFormat("hh:mm a");
                                    String timeeeee = newtimeee.format(time1);
                                    String sales_date_reprint_bycard = saledate.concat(" ").concat(timeeeee);
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    String formattedDate;
                                    int billDtSpace = 39 - ((String.valueOf(reprint_bill_dialog_text.getText().toString()).length() + BILL_DATE_COLUMN) + sales_date_reprint_bycard.length());
                                    strbuilder.append("Bill No:");
                                    strbuilder.append(reprint_bill_dialog_text.getText().toString());
                                    strbuilder.append(getSpacer(billDtSpace));
                                    strbuilder.append(sales_date_reprint_bycard);
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(getDividerSales());
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    if (MRPisShown.matches("Y")) {
                                        strbuilder.append(" ProductName" + " " + "MRP");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(" S.Price" + " " + "Qty" + " " + "ItemDisc" + " " + "Total");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(getDividerSales());
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        for (Sales prod : get_Transcation_List) {
                                            String ProuctName = prod.getProductName();
                                            if (ProuctName.length() >= 21) {
                                                ProuctName = ProuctName.substring(0, 21);
                                            }
                                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);
                                            String printMrp = String.valueOf(f.format(prod.getMrp()));
                                            int spaces = 10 - printMrp.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                            spaces = 9 - printSPrice.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            String printqty = String.valueOf(prod.getQuantity());
                                            spaces = 3 - printqty.length();
                                            strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                            String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                            spaces = 7 - printDiscount.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            if (prod.getDiscountamount() > 0.00) {
                                                String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            } else {
                                                String printtotal = String.valueOf(f.format(prod.getTotal()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            }
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                        }
                                    } else {
                                        strbuilder.append(" S.Price" + " " + "Qty" + " " + "ItemDisc" + " " + "Total");
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        strbuilder.append(getDividerSales());
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                        for (Sales prod : get_Transcation_List) {
                                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                            int spaces = 9 - printSPrice.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            String printqty = String.valueOf(prod.getQuantity());
                                            spaces = 3 - printqty.length();
                                            strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                            String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                            spaces = 7 - printDiscount.length();
                                            if (spaces == 0) {
                                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            } else {
                                                strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                            }
                                            if (prod.getDiscountamount() > 0.00) {
                                                String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            } else {
                                                String printtotal = String.valueOf(f.format(prod.getTotal()));
                                                spaces = 8 - printtotal.length();
                                                strbuilder.append(getSpacer(spaces) + printtotal);
                                            }
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                            strbuilder.append(ActivitySalesbill.this.eol);
                                        }
                                    }
                                    strbuilder.append(getDividerSales());
                                    DecimalFormat f1 = new DecimalFormat("##.00");
                                    float roundvalue = 0.0f;
                                    int poud = 0;
                                    float gettotal = 0.0f;
                                    float OverAllDiscount = 0.0f;
                                    for (Sales prod : get_Transcation_List) {
                                        poud++;
                                    }
                                    Log.e("mastervalueforprinter", "" + expectdchnge + fnlillamt + receidamt + totalbillamt + savetotalbilldscount);
                                    String Roundoffvalue = String.valueOf(cardGrandTotal);
                                    int spaces = 10 - String.valueOf(poud).length();
                                    int amtspaces = 8 - Roundoffvalue.length();
                                    strbuilder.append(TOTAL_ITEMS + poud + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                                    strbuilder.setLength(0);
                                    strbuilder.append(getDividerSales());
                                    if (FooterShown.matches("Y")) {
                                        int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                                        strbuilder.append(Footer);
                                        strbuilder.append(ActivitySalesbill.this.eol);
                                    }
                                    UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                                    strbuilder.setLength(0);
                                    UsPrinter.print();
                                    UsPrinter.FeedLine();
                                    UsPrinter.FullCut();
                                    Intent intent = new Intent(getApplicationContext(), ActivitySalesbill.class);
                                    startActivity(intent);
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid Transaction id", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.dismiss();
                alertDialog.dismiss();
            }
        });
    }


//////////////////////////////////////////////////////////upload Sales Master Data/////////////////////////////////////////


    public void updateSaveSalesListMaster() {


        final String grand_total = GrandTotal.getText().toString();
        final String sale_date = db.getDate();
        final String sale_time = db.getTime();
        final String saleimeino = imeibill.getText().toString();
        final String SaveCustname = PosCust.getText().toString();
        final String DOCTNAME = doctor.getText().toString();
        final String mailid = emailids.getText().toString();


        for (Sales sales : adapter.getList()) {

            final String salestransaction = Transid.getText().toString();
            final String totalGrandAmountPopup = TotalGrandAmountPopup.getText().toString();
            final String overallTotalDiscount = OverallTotalDiscount.getText().toString();
            final String Grand_totalafterdiscount = grandtotalafterdiscount.getText().toString();
            final String Reciveamt = reciveamt.getText().toString();
            final String Expectedchang = expectedchang.getText().toString();
            final String TOtalDiscount = discountamount.getText().toString();
            final String pos_user = user2.getText().toString();
            final String token = SharedPrefManager.getInstance(ActivitySalesbill.this).getDeviceToken();

            stockQuantity = sales.getStockquant();
            saleQuantity = sales.getQuantity();

            if (saleQuantity <= stockQuantity) {

                newStockQuantity = stockQuantity - saleQuantity;
                save_sales_con_mul_qty = Double.toString(newStockQuantity);

            } else if (saleQuantity > stockQuantity) {
                newStockQuantity = stockQuantity - saleQuantity;
                newstockQuant = stockQuantity - newStockQuantity;
                newstock = saleQuantity - newstockQuant;
                save_sales_con_mul_qty = Double.toString(newstock);

            }
            PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
            save_sales_store_id = PersistenceManager.getStoreId(ActivitySalesbill.this);
            store_ID = save_sales_store_id.substring(0, 10);

            class UpdateActivitySalesbill extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {

                    try {
                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.SAVE_SALES_LIST_TRI_ID, salestransaction);
                        hashMap.put(Config.SAVE_SALES_LIST_STORE_ID, store_ID);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_GRAND_TOTAL, totalGrandAmountPopup);
                        hashMap.put(Config.Retail_Pos_user, pos_user);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_BUSINESS_DATE, sale_date);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_DATE, sale_date);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_SALE_TIME, sale_time);
                        hashMap.put(Config.STR_SALES_DETAILS_IMEI, saleimeino);
                        hashMap.put(Config.RETAIL_STR_SALES_MASTER_DISCOUNT, TOtalDiscount);
                        hashMap.put(Config.STR_SALES_SaveTOTALBILLAMOUNT, totalGrandAmountPopup);
                        hashMap.put(Config.STR_SALES_SaveTOTALBIllDiscount, overallTotalDiscount);
                        hashMap.put(Config.STR_SALES_SaveFINALBILLAMOUNT, Grand_totalafterdiscount);
                        hashMap.put(Config.STR_SALES_SaveRECEIVEDBILLAMOUNT, Reciveamt);
                        hashMap.put(Config.STR_SALES_SaveEXPECTEDBILLAMOUNT, Expectedchang);
                        hashMap.put(Config.SAVE_LIST_PREFIX_NM, "Sales-");
                        hashMap.put(Config.MAIL_ID, mailid);


                        hashMap.put("CUST_NAME", SaveCustname);
                        hashMap.put("DOCT_NAME", DOCTNAME);

                        JSONParserSync rh = new JSONParserSync();
                        // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/Pay_By_Cash.jsp", hashMap);
                        JSONObject s = rh.sendPostRequest(Config.SALES_BILL_MASTER, hashMap);

                        Log.d("Login attempt Master", "Master---->" + s.toString());

                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", "Master---->" + s.toString());

                            db.updateflagsavesalesMaster(salestransaction);
                            db.updateflagSavePdfDetailForsale(salestransaction);

                        } else {

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //loading = ProgressDialog.show(ActivitySalesbill.this, "Updating Sales...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    // loading.dismiss();
                    // Toast.makeText(ActivitySalesbill.this, s, Toast.LENGTH_LONG).show();
                }
            }

            UpdateActivitySalesbill activitysalesbil = new UpdateActivitySalesbill();
            activitysalesbil.execute();
        }
    }


    public void UpdateIventoryByLocalProduct() {

//        PurchaseProductModel purchase = new PurchaseProductModel();
        final String inventoryGRNvalue = Billno.getText().toString();

        final String salestransaction = GrnIDForLocal.toString();
        final String saleimeino = imeibill.getText().toString();
        final String inventorygrandtotal = GrandTotal.getText().toString();

        for (LocalProduct purchase : localprodaddarraylist) {
            final String inventorybatchno = String.valueOf(purchase.getBatchNo());
            final String inventoryexpdate = String.valueOf(purchase.getExpDate());
            final String inventoryproductid = purchase.getProductid();
            final String inventoryproductname = purchase.getProductname();
            final String inventoryproductmrp = String.valueOf(purchase.getMRP());
            final String inventoryuom = purchase.getUom();
            final String inventoryproductprice = String.valueOf(purchase.getPurchasePrice());
            final String inventorybarcode = purchase.getBarcode();
            final String inventorysellingprice = String.valueOf(purchase.getSellingPrice());
            final String inventoryconvfact = String.valueOf(purchase.getConvFact());
            final String inventoryprofitmargin = String.valueOf(purchase.getMargin());
            final String inventoryfreegoods = String.valueOf("0");
            final String Inventoryconvmulqty = String.valueOf(purchase.getQuantity());
            final String inventoryQuantity = String.valueOf(purchase.getQuantity());
            final String pos_user = user2.getText().toString();
            final String purchasetotal = String.valueOf(Float.parseFloat(purchase.getPurchasePrice()) * Float.parseFloat(purchase.getQuantity()));
            //   final String inventoryconvmrp = String.valueOf(purchase.);

            PersistenceManager.saveStoreId(ActivitySalesbill.this, db.getStoreid().toString().replace("[", "").replace("]", ""));
            final String store_id = PersistenceManager.getStoreId(ActivitySalesbill.this);
            String universal_id = PersistenceManager.getStoreId(ActivitySalesbill.this);
            class UpdateIventoryWithoutPO extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        HashMap<String, String> hashMap = new HashMap<>();


                        hashMap.put(Config.Retail_inventory_store_id, store_id);
                        hashMap.put(Config.Retail_Inventory_grnid, salestransaction);
                        hashMap.put(Config.Retail_Inventory_mfg_batchno, inventorybatchno);
                        hashMap.put(Config.Retail_Inventory_batchno, getSystemCurrentTime());

                        hashMap.put(Config.Retail_Inventory_expdate, inventoryexpdate);
                        hashMap.put(Config.Retail_Inventory_id, inventoryproductid);
                        hashMap.put(Config.Retail_Inventory_name, inventoryproductname);
                        hashMap.put(Config.Retail_Inventory_p_price, inventoryproductprice);
                        hashMap.put(Config.Retail_Inventory_barcode, "NA");
                        hashMap.put(Config.Retail_Inventory_mrp, inventoryproductmrp);
                        hashMap.put(Config.Retail_Inventory_profit_margin, inventoryprofitmargin);
                        hashMap.put(Config.Retail_Inventory_conv_fact, inventoryconvfact);
                        hashMap.put(Config.Retail_Inventory_selling, inventorysellingprice);
                        hashMap.put(Config.Retail_Inventory_uom, inventoryuom);
                        hashMap.put(Config.Retail_Inventory_Quantity, inventoryQuantity);
                        hashMap.put(Config.Retail_Inventory_Total, purchasetotal);
                        hashMap.put(Config.Retail_Inventory_freegoods, inventoryfreegoods);
                        hashMap.put(Config.Retail_Inventory_conmulqty, Inventoryconvmulqty);
                        //need to be entered
                        //hashMap.put(Config.Retail_Inventory_GrandTotal, inventorygrandtotal);
                        hashMap.put(Config.Retail_Inventory_po_no, saleimeino);
                        hashMap.put(Config.Retail_Pos_user, pos_user);


                        JSONParserSync rh = new JSONParserSync();
                        //JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/inventory_without_po.jsp", hashMap);
                        JSONObject s = rh.sendPostRequest(Config.UPDATE_INVENTORY_WITHOUT_PO, hashMap);
                        Log.d("Login attempt", s.toString());
                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            db.updateinventoryflagstockmaster(inventoryGRNvalue);

                            db.updateflagSavePdfDetailForInventorywithpo(inventoryGRNvalue);

                            // return s.getString(TAG_MESSAGE);
                        } else {
                            // return s.getString(TAG_MESSAGE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;

                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // loading = ProgressDialog.show(activity_inventory.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //     loading.dismiss();
                    //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();

                }

            }

            UpdateIventoryWithoutPO updateinventorywithoutpo = new UpdateIventoryWithoutPO();
            updateinventorywithoutpo.execute();
        }

    }


    ///////////////////////////upload


    public void deleterecords() {

        final DBhelper mydb = new DBhelper(ActivitySalesbill.this);


        final String delete_tri_id = oldspinneritem;


        PersistenceManager.saveStoreId(ActivitySalesbill.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(this);
        class UpdateLocalProduct extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivityLocalProduct.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {

                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(ActivityLocalProduct.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.RETAIL_STORE_DELETE_STORE_ID, store_id);
                    hashMap.put(Config.RETAIL_STORE_DELETE_TRI_ID, delete_tri_id);
                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.DELETE_RECORDS, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

                return null;
            }
        }
        UpdateLocalProduct updateLocalProduct = new UpdateLocalProduct();
        updateLocalProduct.execute();
    }

    public void printTest() {
        Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
        startActivity(intent);
        if (PrintBill.matches("Y")) {

            tp.setColor(Color.BLUE);
            tp.setTextSize(26);
            tp32.setTextSize(40);
            tp36.setTextSize(34);
            tp32.setTypeface(Typeface.create("Arial", Typeface.BOLD));

            //tp32.setTextSize(38);
            tp.setTypeface(tff);
            try {
                if (UsPrinter.getState() == 0) {
                    UsPrinter.showDeviceList(ActivitySalesbill.this);
                }
            } catch (Exception e) {
            }
            // loading.dismiss();
            // Toast.makeText(ActivityLocalProduct.this, s, Toast.LENGTH_LONG).show();
            store_name = db.getAllStores();
            store = (store_name.get(1).toString());
            //   String Store_ID = (store_name.get(0).toString());
            storeAddress = store_name.get(2).toString();
            City = store_name.get(3).toString();
            Storenumber = store_name.get(4).toString();
            AlternateNo = store_name.get(5).toString();
            Footer = store_name.get(6).toString();
            amountdiscount = discountamount.getText().toString();
            Totalsavings.setText(amountdiscount);
            DecimalFormat f = new DecimalFormat("##.00");
            for (int i = 1; i <= paybycashbillcopy; i++) {
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
                    strbuilder.append(ActivitySalesbill.this.eol);
                }
                UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                strbuilder.setLength(0);
                strbuilder.append("Customer Name:" + PosCust.getText().toString());
                strbuilder.append(ActivitySalesbill.this.eol);

                String formattedDate;
                formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(Transid.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                strbuilder.append("Bill No:");
                strbuilder.append(Transid.getText().toString());
                strbuilder.append(getSpacer(billDtSpace));
                strbuilder.append(formattedDate);
                strbuilder.append(ActivitySalesbill.this.eol);
                strbuilder.append(getDividerSales());
                strbuilder.append(ActivitySalesbill.this.eol);

                if (MRPisShown.matches("Y")) {
                    strbuilder.append(" ProductName" + "       " + "MRP");
                    strbuilder.append(ActivitySalesbill.this.eol);
                    strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");

                    // strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                    strbuilder.append(ActivitySalesbill.this.eol);
                    strbuilder.append(getDividerSales());
                    strbuilder.append(ActivitySalesbill.this.eol);
                    for (Sales prod : adapter.getList()) {
                        String ProuctName = prod.getProductName();
                        if (ProuctName.length() >= 21) {
                            ProuctName = ProuctName.substring(0, 21);
                        }
                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                        String printMrp = String.valueOf(f.format(prod.getMrp()));
                        int spaces = 10 - printMrp.length();
                        if (spaces == 0) {
                            strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                        } else {
                            strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                        }

                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append(ActivitySalesbill.this.eol);

                        String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                        spaces = 9 - printSPrice.length();
                        if (spaces == 0) {
                            strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                        } else {
                            strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                        }
                        String printqty = String.valueOf(prod.getQuantity());
                        spaces = 3 - printqty.length();
                        strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                        String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                        spaces = 7 - printDiscount.length();
                        if (spaces == 0) {
                            strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                        } else {
                            strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                        }
                        if (prod.getDiscountamount() > 0.00) {
                            String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                            spaces = 8 - printtotal.length();
                            strbuilder.append(getSpacer(spaces) + printtotal);
                        } else {
                            String printtotal = String.valueOf(f.format(prod.getTotal()));
                            spaces = 8 - printtotal.length();
                            strbuilder.append(getSpacer(spaces) + printtotal);
                        }

                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append(ActivitySalesbill.this.eol);
                    }
                } else {
                    strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");
                    strbuilder.append(ActivitySalesbill.this.eol);
                    strbuilder.append(getDividerSales());
                    strbuilder.append(ActivitySalesbill.this.eol);
                    for (Sales prod : adapter.getList()) {

                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                        strbuilder.append(ActivitySalesbill.this.eol);

                        String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                        int spaces = 9 - printSPrice.length();
                        if (spaces == 0) {
                            strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                        } else {
                            strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                        }
                        String printqty = String.valueOf(prod.getQuantity());
                        spaces = 3 - printqty.length();
                        strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                        String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                        spaces = 7 - printDiscount.length();
                        if (spaces == 0) {
                            strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                        } else {
                            strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                        }
                        if (prod.getDiscountamount() > 0.00) {
                            String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                            spaces = 8 - printtotal.length();
                            strbuilder.append(getSpacer(spaces) + printtotal);
                        } else {
                            String printtotal = String.valueOf(f.format(prod.getTotal()));
                            spaces = 8 - printtotal.length();
                            strbuilder.append(getSpacer(spaces) + printtotal);
                        }
                        strbuilder.append(ActivitySalesbill.this.eol);
                        strbuilder.append(ActivitySalesbill.this.eol);
                    }
                }
                strbuilder.append(getDividerSales());

                String getFooterpart = FooterValueFullBill();
                strbuilder.append(getFooterpart);
                UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                strbuilder.setLength(0);

                if (FooterShown.matches("Y")) {
                    int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                    strbuilder.append(Footer);
                    strbuilder.append(ActivitySalesbill.this.eol);
                }
                UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                strbuilder.setLength(0);
                UsPrinter.print();
                UsPrinter.FeedLine();
                // UPrinter.FeedLine();
                UsPrinter.FullCut();


            }
            Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();

            doctor.setText("");
        } else {
            Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();
            doctor.setText("");
        }

        final String imeino = imeibill.getText().toString();
        db.savesalesListdetail(Transid.getText().toString(), adapter.getList(), user2.getText().toString(), imeino, discountamount.getText().toString());
        db.insertdetailsifpaybaycash(Transid.getText().toString(), GrandTotal.getText().toString(), user2.getText().toString(), imeino, discountamount.getText().toString(), grandtotalafterdiscount.getText().toString(), OverallTotalDiscount.getText().toString(), TotalGrandAmountPopup.getText().toString(), expectedchang.getText().toString(), reciveamt.getText().toString(), PosCust.getText().toString(), doctor.getText().toString(), PrintBill);
        db.updateStockQty(adapter.getList());
        db.insertdataIntosendMailforSales(Transid.getText().toString(), PosCust.getText().toString(), user2.getText().toString(), emailids.getText().toString());
    }



    /*    public void printTest() {
           final String imeino = imeibill.getText().toString();


                final String status=printbilladptr.getSelectedItem().toString();;
            Log.d("Status", status.toString());


            final String discountAmount  =discountamount.getText().toString();
            final String grandTotal = GrandTotal.getText().toString();
            final String afterDiscount =  grandtotalafterdiscount.getText().toString();
            final String overAllDiscount=OverallTotalDiscount.getText().toString();
           final String grandAmountPopup = TotalGrandAmountPopup.getText().toString();
           final String expectedchange =  expectedchang.getText().toString();
            final String receiveamount = reciveamt.getText().toString();
            final String poscust = PosCust.getText().toString();
           final String docto = doctor.getText().toString();
            Transid.setText(String.valueOf(getBillnumber()));
            final String transId=Transid.getText().toString();
            class UpdateLocalProduct extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);
                }
                @Override
                protected void onPostExecute(String s) {


                    super.onPostExecute(s);
                    if (PrintBill.matches("Y")) {

                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp.setTypeface(tff);
                        try {
                            if (UsPrinter.getState() == 0) {
                                UsPrinter.showDeviceList(ActivitySalesbill.this);
                            }
                        } catch (Exception e) {
                        }
                        // loading.dismiss();
                        // Toast.makeText(ActivityLocalProduct.this, s, Toast.LENGTH_LONG).show();
                        store_name = db.getAllStores();
                        store = (store_name.get(1).toString());
                        //   String Store_ID = (store_name.get(0).toString());
                        storeAddress = store_name.get(2).toString();
                        City = store_name.get(3).toString();
                        Storenumber = store_name.get(4).toString();
                        AlternateNo = store_name.get(5).toString();
                        Footer = store_name.get(6).toString();
                        amountdiscount = discountamount.getText().toString();
                        Totalsavings.setText(amountdiscount);
                        DecimalFormat f = new DecimalFormat("##.00");
                        for (int i = 1; i <= paybycashbillcopy; i++) {
                            StringBuilder strbuilder = new StringBuilder();
                            strbuilder.append(store + "\n");
                            strbuilder.append(storeAddress + "\n");
                            strbuilder.append(City + "\n");
                            if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {
                                strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");

                            } else {
                                strbuilder.append("Tel:" + Storenumber);
                                strbuilder.append(ActivitySalesbill.this.eol);
                            }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            strbuilder.append("Customer Name:" + PosCust.getText().toString());
                            strbuilder.append(ActivitySalesbill.this.eol);

                            String formattedDate;
                            formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                            int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(Transid.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                            strbuilder.append("Bill No:");
                            strbuilder.append(Transid.getText().toString());
                            strbuilder.append(getSpacer(billDtSpace));
                            strbuilder.append(formattedDate);
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);

                            if (MRPisShown.matches("Y")) {
                                strbuilder.append(" ProductName" + "       " + "MRP");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");

                                // strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : adapter.getList()) {
                                    String ProuctName = prod.getProductName();
                                    if (ProuctName.length() >= 21) {
                                        ProuctName = ProuctName.substring(0, 21);
                                    }
                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);

                                    String printMrp = String.valueOf(f.format(prod.getMrp()));
                                    int spaces = 10 - printMrp.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    }

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    spaces = 9 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                    String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                    spaces = 7 - printDiscount.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    if (prod.getDiscountamount() > 0.00) {
                                        String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    } else {
                                        String printtotal = String.valueOf(f.format(prod.getTotal()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                }
                            } else {
                                strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : adapter.getList()) {

                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    int spaces = 9 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                    spaces = 7 - printDiscount.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    if (prod.getDiscountamount() > 0.00) {
                                        String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    } else {
                                        String printtotal = String.valueOf(f.format(prod.getTotal()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                }
                            }
                            strbuilder.append(getDividerSales());

                            String getFooterpart = FooterValueFullBill();
                            strbuilder.append(getFooterpart);
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                            strbuilder.setLength(0);

                            if (FooterShown.matches("Y")) {
                                int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                                strbuilder.append(Footer);
                                strbuilder.append(ActivitySalesbill.this.eol);
                            }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            UsPrinter.print();
                            UsPrinter.FeedLine();
                            // UPrinter.FeedLine();
                            UsPrinter.FullCut();

                        }
                        Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();
                        doctor.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(), Transid.getText().toString(), Toast.LENGTH_SHORT).show();
                        doctor.setText("");
                    }

                }



                    db.savesalesListdetail(transId, adapter.getList(), username, imeino, discountAmount);
                    db.insertdetailsifpaybaycash(transId, grandAmountPopup, username, imeino, discountAmount,afterDiscount,overAllDiscount, grandAmountPopup, expectedchange, receiveamount, poscust, docto);
                    db.updateStockQty(adapter.getList());
                    db.insertdataIntosendMailforSales(transId, poscust, username);
                    updateSaveSalesList();
                    updateSaveSalesListMaster();


                    return null;
                }

            }


            UpdateLocalProduct updateLocalProduct = new UpdateLocalProduct();
            updateLocalProduct.execute();
    }*/
  /* public void Reprintdialog()
    {

        LayoutInflater inflater = getLayoutInflater();
        final View alertlayout=inflater.inflate(R.layout.reprint_bill_dialog,null);

        AlertDialog.Builder reprint_dialog=new AlertDialog.Builder(ActivitySalesbill.this);
        reprint_dialog.setTitle("Reprint Dialog");
        reprint_dialog.setMessage("Enter Transaction id");

        reprint_bill_dialog_text=(EditText)alertlayout.findViewById(R.id.reprint_bill_text);

        reprint_dialog.setNegativeButton("Print",new DialogInterface.OnClickListener()
        {public void onClick(DialogInterface dialog, int which){


            String bill_Transaction_id=reprint_bill_dialog_text.getText().toString();
            Log.e("!!!!!!!!!!!!!!!!!",""+bill_Transaction_id.toString());


            if (bill_Transaction_id.equals("")) {
                Toast.makeText(ActivitySalesbill.this, "Enter Transaction id", Toast.LENGTH_LONG).show();
                return;
            }
            else {


                //DBhelper dBhelper=new DBhelper(ActivitySalesbill.this);

                get_Transcation_List = db.get_Transaction_Id_Bill(bill_Transaction_id);

                    *//*Toast.makeText(getApplicationContext(),get_Transcation_List.toString(),Toast.LENGTH_LONG).show();
                    Log.d("%%%%%%%%%%%%%",get_Transcation_List.toString());*//*


                //////////////////////////////////////Printer testing ///////////////////////////////////////


                if(get_Transcation_List.size() > 0){
                    try {
                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp.setTypeface(tff);

                        store_name = db.getAllStores();
                        store = (store_name.get(1).toString());
                        storeAddress = store_name.get(2).toString();
                        City = store_name.get(3).toString();
                        Storenumber = store_name.get(4).toString();
                        AlternateNo = store_name.get(5).toString();
                        Footer=store_name.get(6).toString();

                        Grandtotal = GrandTotal.getText().toString();
                        //Footer = store_name.get(6).toString();
                        //amountdiscount = discountamount.getText().toString();
                        //Totalsavings.setText(amountdiscount);

                        DecimalFormat f = new DecimalFormat("##.00");
                        for(int i=1;i<=paybycashbillcopy;i++) {
                            StringBuilder strbuilder = new StringBuilder();
                            strbuilder.append(store + "\n");
                            strbuilder.append(storeAddress + "\n");
                            strbuilder.append(City + "\n");
                            if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {
                                strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");

                            } else {
                                strbuilder.append("Tel:" + Storenumber);
                                strbuilder.append(ActivitySalesbill.this.eol);
                            }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);

                            strbuilder.append("Customer Name:" + PosCust.getText().toString());
                            strbuilder.append(ActivitySalesbill.this.eol);


                            String formattedDate;
                            formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                            int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(reprint_bill_dialog_text.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                            strbuilder.append("Bill No:");
                            strbuilder.append(reprint_bill_dialog_text.getText().toString());


                            strbuilder.append(getSpacer(billDtSpace));
                            strbuilder.append(formattedDate);
                            strbuilder.append(ActivitySalesbill.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(ActivitySalesbill.this.eol);
                            if (MRPisShown.matches("Y")) {
                                strbuilder.append(" ProductName"+"       "+"MRP");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(" S.Price" + "    " + "Qty" + "  " +"ItemDisc"+"   "+ "Total");


                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : get_Transcation_List) {
                                    String ProuctName=prod.getProductName();
                                    if (ProuctName.length()>=21)
                                    {
                                        ProuctName=ProuctName.substring(0,21);
                                    }
                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) +ProuctName );

                                    String printMrp = String.valueOf(f.format(prod.getMrp()));
                                    int spaces = 10 - printMrp.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                    }

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    spaces = 9 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));
                                    String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                    spaces = 7 - printDiscount.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    if (prod.getDiscountamount()>0.00)
                                    {  String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                        spaces =8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }
                                    else {
                                        String printtotal = String.valueOf(f.format(prod.getTotal()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }

                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);


                                }
                            } else {
                                strbuilder.append(" S.Price" + "    " + "Qty" + "  " +"ItemDisc"+"   "+ "Total");
                                strbuilder.append(ActivitySalesbill.this.eol);
                                strbuilder.append(getDividerSales());
                                strbuilder.append(ActivitySalesbill.this.eol);
                                for (Sales prod : get_Transcation_List) {

                                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());
                                    strbuilder.append(ActivitySalesbill.this.eol);

                                    String printSPrice = String.valueOf(f.format(prod.getSPrice()));
                                    int  spaces = 9 - printSPrice.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    String printqty = String.valueOf(prod.getQuantity());
                                    spaces = 3 - printqty.length();
                                    strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                    String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));
                                    spaces = 7 - printDiscount.length();
                                    if (spaces == 0) {
                                        strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    } else {
                                        strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));
                                    }
                                    if (prod.getDiscountamount()>0.00)
                                    {  String printtotal = String.valueOf(f.format(prod.getDiscountamount()));
                                        spaces =8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }
                                    else{
                                        String printtotal = String.valueOf(f.format(prod.getTotal()));
                                        spaces = 8 - printtotal.length();
                                        strbuilder.append(getSpacer(spaces) + printtotal);
                                    }
                                    strbuilder.append(ActivitySalesbill.this.eol);
                                    strbuilder.append(ActivitySalesbill.this.eol);


                                }
                            }
                            strbuilder.append(getDividerSales());

                            *//*String getFooterpart = FooterValueFullBill();
                            strbuilder.append(getFooterpart);*//*

/*/

    /**
     * DecimalFormat f1 = new DecimalFormat("##.00");
     * <p>
     * float roundvalue =0.0f;
     * int poud=0;
     * float gettotal=0.0f;
     * float OverAllDiscount=0.0f;
     * for(Sales prod:get_Transcation_List)
     * {
     * <p>
     * poud++;
     * }
     * <p>
     * <p>
     * Sales  getAllmasterinfoforprinter=db.get_Transaction_Id_Bill_Master(reprint_bill_dialog_text.getText().toString());
     * <p>
     * float expectdchnge=  getAllmasterinfoforprinter.getSaveEXPECTEDBILLAMOUNT();
     * float fnlillamt= getAllmasterinfoforprinter.getSaveFINALBILLAMOUNT();
     * float receidamt=  getAllmasterinfoforprinter.getSaveRECEIVEDBILLAMOUNT();
     * float totalbillamt=  getAllmasterinfoforprinter.getSaveTOTALBILLAMOUNT();
     * float savetotalbilldscount=  getAllmasterinfoforprinter.getSaveTOTALBIllDiscount();
     * <p>
     * Log.e("mastervalueforprinter",""+expectdchnge+fnlillamt+receidamt+totalbillamt+savetotalbilldscount);
     * <p>
     * String Roundoffvalue = String.valueOf(f1.format(fnlillamt));
     * // if (Totalsavings.getText().toString().equals("")||Totalsavings.getText().toString().equals("0.00")||Totalsavings.getText().toString().equals(".00")) {
     * int spaces = 10 - String.valueOf(poud).length();
     * int amtspaces = 8 - Roundoffvalue.length();
     * strbuilder.append(TOTAL_ITEMS + poud + getSpacer(PRINT_LEFT_MARGIN) +BILL_TOTAL+ getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
     * strbuilder.append(ActivitySalesbill.this.eol);
     * <p>
     * int Disamtspaces = getSpacer(24).length()-BILL_DISC.length();
     * int netvalspce=9-String.valueOf(savetotalbilldscount).length();
     * strbuilder.append(getSpacer(Disamtspaces)+BILL_DISC+getSpacer(netvalspce)+f.format(savetotalbilldscount)+getSpacer(PRINT_LEFT_MARGIN));
     * strbuilder.append(ActivitySalesbill.this.eol);
     * strbuilder.append(getSpacer(20) + "-------------");
     * strbuilder.append(ActivitySalesbill.this.eol);
     * <p>
     * amtspaces = getSpacer(24).length()-NETPAYAMOUNT.length();
     * netvalspce=9-String.valueOf(totalbillamt).length();
     * strbuilder.append(getSpacer(amtspaces)+NETPAYAMOUNT+getSpacer(netvalspce)+f.format(Double.parseDouble(String.valueOf(totalbillamt)))+getSpacer(PRINT_LEFT_MARGIN));
     * strbuilder.append(ActivitySalesbill.this.eol);
     * <p>
     * amtspaces = getSpacer(24).length()-RECEIVEAMOUNT.length();
     * netvalspce=9-(f.format(Double.parseDouble(String.valueOf(expectdchnge)))).length();
     * strbuilder.append(getSpacer(amtspaces)+RECEIVEAMOUNT+getSpacer(netvalspce)+f.format(Double.parseDouble(String.valueOf(expectdchnge))) +getSpacer(PRINT_LEFT_MARGIN));
     * strbuilder.append(ActivitySalesbill.this.eol);
     * <p>
     * amtspaces = getSpacer(24).length()-BALANCEAMOUNT.length();
     * netvalspce=9- String.valueOf(f.format(receidamt)).length();
     * strbuilder.append(getSpacer(amtspaces)+BALANCEAMOUNT+getSpacer(netvalspce)+ String.valueOf(f.format(receidamt))+getSpacer(PRINT_LEFT_MARGIN));
     * strbuilder.append(ActivitySalesbill.this.eol);
     * <p>
     * <p>
     * UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
     * strbuilder.setLength(0);
     * strbuilder.append(getDividerSales());
     * <p>
     * if (FooterShown.matches("Y")) {
     * int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
     * strbuilder.append(Footer);
     * strbuilder.append(ActivitySalesbill.this.eol);
     * }
     * UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
     * strbuilder.setLength(0);
     * <p>
     * UsPrinter.print();
     * UsPrinter.FeedLine();
     * // UPrinter.FeedLine();
     * UsPrinter.FullCut();
     * //GrandTotal.setText(".0");
     * <p>
     * }
     * <p>
     * <p>
     * }catch (Exception e)
     * {
     * e.printStackTrace();
     * }
     * }
     * else {
     * Toast.makeText(getApplicationContext(), "Invalid Transaction id", Toast.LENGTH_SHORT).show();
     * <p>
     * }
     * }
     * <p>
     * <p>
     * }});
     * reprint_dialog.setPositiveButton("Cancel",new DialogInterface.OnClickListener()
     * {public void onClick(DialogInterface dialog, int which){
     * //
     * }});
     * <p>
     * reprint_dialog.setView(alertlayout);
     * reprint_dialog.show();
     * <p>
     * <p>
     * <p>
     * <p>
     * }
     */

}


