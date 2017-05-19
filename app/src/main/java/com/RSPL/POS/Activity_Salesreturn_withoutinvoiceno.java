
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
        import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
import java.util.Locale;

import Adapter.SalesReturnProductAdapter;
import Adapter.SalesReturnwithoutinvoiceno;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
        import Pojo.Registeremployeesmodel;
        import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.Visibility;
import Pojo.displaypojo;

        public class Activity_Salesreturn_withoutinvoiceno extends Activity {
            public static USBPrinter UsPrinter = USBPrinter.INSTANCE;


            private TextWatcher ProductNameTextWatcher;
            AutoCompleteTextView ProductName;
            ArrayList<SalesreturndetailWithoutPo> ProductNamelist;
            SalesReturnProductAdapter productNmAdaptor;
            SalesReturnwithoutinvoiceno ReturnListAdapter;
            ArrayList<SalesreturndetailWithoutPo> ProductNameArrayList;
            DBhelper mydb;
            Spinner reason;
            ListView listView;


            String item, transactionid;
            Button submit, clear;
            public static String username = null;
            String iteam;
            TextView user2;



            String store_id;
            TextView Grandtotal, invoice, invoiceid;
            TextView Totalitems, Billno, imeibillno;
            ActionBar actionBar;
            TelephonyManager tel;
            String x_imei;
            public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
            Bundle syncDataBundle = null;
            private boolean syncInProgress = false;
            private boolean didSyncSucceed = false;
            private static final String TAG_SUCCESS = "Success";
            private static final String TAG_MESSAGE = "message";
            String retail_salesreturn_op = "sh /sdcard/sales_return.sh";
            String superuser = "su";


            DBhelper db;
            PendingIntent mPermissionIntent;
            UsbManager mUsbManager;
            UsbDevice device;
            UsbController usbController;
    /*  <<<<<<<Updated upstream
        =======*/

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

            String MRPisShown, FooterShown, Tele2Shown;
            int paybycashbillcopy, creditbillcopy, ReturnBillCopy;
            public static USBPrinter UPrinter = USBPrinter.INSTANCE;
            public static SharedPreferences mSp;
            TextPaint tp = new TextPaint();
            TextPaint tp32 = new TextPaint();
            TextPaint tp36 = new TextPaint();
            Typeface tff;

            protected static final String ACTION_USB_PERMISSION =
                    "com.android.example.USB_PERMISSION";
            TextView tvproductname,tvexp,tvbatch,tvsprice,tvqty,tvuom,tvtotal,tvreason,tvdiscount,tvgrand;
            String backroundcolour,colorchange;


//            private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
           @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                System.loadLibrary("sqliteX");
                setContentView(R.layout.activity_activity__salesreturn_withoutinvoiceno);
                TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
                tff = Typeface.createFromAsset(Activity_Salesreturn_withoutinvoiceno.this.getAssets(), "Fonts/DroidSansMono.ttf");
                listView = (ListView) findViewById(R.id.lv_PurchaseProduct);
                submit = (Button) findViewById(R.id.salesreturn_submit);
                clear = (Button) findViewById(R.id.clear);



               reason = (Spinner) findViewById(R.id.reasonreturn);
                invoice = (TextView) findViewById(R.id.invoiceno);

               invoiceid = (TextView) findViewById(R.id.invoiceid);
                // ProductName = (AutoCompleteTextView) findViewById(R.id.autoProductName);
                actionBar = getActionBar();

                username = login.b.getString("Pos_User");


              //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setIcon(R.drawable.w);
               mydb = new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);
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

               actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



               ////////////////jimmmyyy//////////////////////////
               tvproductname = (TextView) findViewById(R.id.product6);
               tvproductname.setTextSize(Float.parseFloat(textsize));

               tvexp = (TextView) findViewById(R.id.productpp);
               tvexp.setTextSize(Float.parseFloat(textsize));

               tvbatch = (TextView) findViewById(R.id.product8);
               tvbatch.setTextSize(Float.parseFloat(textsize));

               tvsprice = (TextView) findViewById(R.id.product9);
               tvsprice.setTextSize(Float.parseFloat(textsize));



               tvqty = (TextView) findViewById(R.id.product9se);
               tvqty.setTextSize(Float.parseFloat(textsize));

               tvuom = (TextView) findViewById(R.id.product10);
               tvuom.setTextSize(Float.parseFloat(textsize));

               tvtotal = (TextView) findViewById(R.id.product12);
               tvtotal.setTextSize(Float.parseFloat(textsize));

               tvreason = (TextView) findViewById(R.id.tv_reason);
               tvreason.setTextSize(Float.parseFloat(textsize));

               tvgrand = (TextView) findViewById(R.id.grandtotal);
               tvgrand.setTextSize(Float.parseFloat(textsize));


               tvdiscount = (TextView) findViewById(R.id.totalitems);
               tvdiscount.setTextSize(Float.parseFloat(textsize));


               Grandtotal = (TextView) findViewById(R.id.grandtotal_textt);
               Grandtotal.setTextSize(Float.parseFloat(textsize));
               Totalitems = (TextView) findViewById(R.id.totalitem_textt);
               Totalitems.setTextSize(Float.parseFloat(textsize));



               tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                Billno = (TextView) findViewById(R.id.sales_billno);
                imeibillno = (TextView) findViewById(R.id.sales_imeibill);
                Billno.setText("012345678912345");
                invoiceno();
                Long value = System.currentTimeMillis() + 1000;
                String result = Long.toString(value);
                RemoteVideoPresentation.showBill();
                showBill();
                invoiceid.setText(result);
                db = new DBhelper(this);

                Visibility value1 = mydb.getStorevisibility();
                MRPisShown = value1.getMrpvisibility();
                FooterShown = value1.getFootervisi();
                Tele2Shown = value1.getTele2();
                paybycashbillcopy = Integer.parseInt(value1.getBillcopy());
                creditbillcopy = Integer.parseInt(value1.getCreditcopy());
                ReturnBillCopy = Integer.parseInt(value1.getReturncopy());
////////////////////////////////////////////////////////////////////////////////////////////////
                displaypojo billDisplayModel = mydb.getStoredisplaydetail();

                BILL_TOTAL = billDisplayModel.getTotalbillvalue();
                BILL_DISC = billDisplayModel.getDiscount();
                NETPAYAMOUNT = billDisplayModel.getNetbillpayable();
                RECEIVEAMOUNT = billDisplayModel.getAmountreceived();
                BALANCEAMOUNT = billDisplayModel.getAmountpaidback();

                ProductName = (CustomAuto) findViewById(R.id.autoProductName);



                ProductName.setThreshold(1);

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
                        if (userTypedString.length() < 1) {
                            return;
                        }
                        ProductNameArrayList = db.getProductReturnData(userTypedString);
                        if (ProductNameArrayList != null) {
                            if (productNmAdaptor == null) {
                                productNmAdaptor = new SalesReturnProductAdapter(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
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
                ProductName.setOnItemClickListener(
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                                // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                                SalesreturndetailWithoutPo result = (SalesreturndetailWithoutPo) parent.getItemAtPosition(position);
                                if (ReturnListAdapter == null) {
                                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                                    ReturnListAdapter = new SalesReturnwithoutinvoiceno(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<SalesreturndetailWithoutPo>());
                                    listView.setAdapter(ReturnListAdapter);
                                }

                                int pos = ReturnListAdapter.addProductToList(result);

                                RemoteVideoPresentation.AddSalesReturnProducttoList(result);
                                writeData(result);
                                Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                                ReturnListAdapter.notifyDataSetChanged();
                                ProductName.setText("");
                                setSummaryRow();

                            }


                        });

                final DBhelper mydb = new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);
                final ArrayList<Salesreturndetail> reasonReturn = mydb.getReasonReturn();
                ArrayAdapter<Salesreturndetail> stringArrayAdapter =
                        new ArrayAdapter<Salesreturndetail>(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_spinner_dropdown_item, reasonReturn);
                reason.setAdapter(stringArrayAdapter);
                reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                        item = adapterView.getItemAtPosition(position).toString();

                        // Toast.makeText(getApplicationContext(),
                        //    "report : " + item, Toast.LENGTH_LONG).show();
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
                            for (SalesreturndetailWithoutPo item : ReturnListAdapter.getList()) {
                                if (item.getSaleqty() < 0) {
                                    // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                // pos++;


                                if (item.getExp_Date() == "select date") {
                                    // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                    return;
                                } else if (item.getSalebatchno() == null || item.getSalebatchno() == "batch") {

                                    item.setSalebatchno(getSystemCurrentTime());

                                }
                                pos++;
                            }
                            if (item.equals("")) {
                                Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            SharedPreferences in = getSharedPreferences("key_code", MODE_PRIVATE);
                            int code = in.getInt("code", 0);

                            if (code <= 0) {
                                code = 1; //--set default start value--
                            } else {
                                code++; //--or just increment it--
                            }

                            in.edit().putInt("code", code).commit(); //--save new value--

                            //--use code variable now--
                            int newKey = code;

                            invoice.setText(String.valueOf(newKey));
                            Log.e("@@@nishant", String.valueOf(newKey));
                            mydb.insertsalesreturnwithoutinvoice(invoice.getText().toString(), ReturnListAdapter.getList(), item.toString(), user2.getText().toString(), imeibillno.getText().toString());
                            mydb.insertsalereturndataforproductdetailwithoutinvoiceno(invoice.getText().toString(), ReturnListAdapter.getList(), user2.getText().toString(), imeibillno.getText().toString());
                            mydb.updateqtyforinvoiceforsalesreturnwithoutinvoiceno(ReturnListAdapter.getList());
                            Toast.makeText(getApplicationContext(), invoice.getText().toString(), Toast.LENGTH_SHORT).show();
                            UpdateSalesReturn_withoutinvoice();
                            UpdateShell();

                            //////////////////////////////////////Printer testing ///////////////////////////////////////
                            try {
                                tp.setColor(Color.BLUE);
                                tp.setTextSize(26);

                                tp32.setTextSize(40);
                                tp36.setTextSize(34);
                                tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));
                                tp.setTypeface(tff);
                                ArrayList<String> store_name = mydb.getAllStores();
                                String store = (store_name.get(1).toString());
                                String storeAddress = store_name.get(2).toString();
                                String City = store_name.get(3).toString();
                                String Storenumber = store_name.get(4).toString();
                                String AlternateNo = store_name.get(5).toString();
                                String Footer = store_name.get(6).toString();
                                String formattedDate;
                                if (UsPrinter.getState()==0) {
                                    UsPrinter.showDeviceList(Activity_Salesreturn_withoutinvoiceno.this);
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
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                     }
                                     /*UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                                     strbuilder.setLength(0);*/
                                     UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                                     strbuilder.setLength(0);
                                     formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                                     int billDtSpace = 39 - ((String.valueOf(Integer.parseInt(invoice.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                                     strbuilder.append("Bill No:");
                                     strbuilder.append(invoice.getText().toString());
                                     strbuilder.append(getSpacer(billDtSpace));
                                     strbuilder.append(formattedDate);
                                     strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                     strbuilder.append(getDividerSales());
                                     strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                     if (MRPisShown.matches("Y")) {
                                         strbuilder.append("     MRP" + "    " + " S.Price" + "   " + "Qty" + "  " + "Total");
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                         strbuilder.append(getDividerSales());
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                         for (SalesreturndetailWithoutPo prod : ReturnListAdapter.getList()) {
                                             strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getSaleproductname());
                                             strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                             String printMrp = prod.getSalemrp();
                                             String printmrp= f.format(Float.parseFloat(printMrp));
                                             int spaces = 9 - printMrp.length();
                                             if (spaces == 0) {
                                                 strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printmrp + getSpacer(PRINT_LEFT_MARGIN));
                                             } else {
                                                 strbuilder.append(getSpacer(spaces) + printmrp + getSpacer(PRINT_LEFT_MARGIN));
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

                                             strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                             strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);


                                         }
                                     } else {
                                         strbuilder.append("   S.Price" + "       " + "Qty" + "       " + "Total");
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                         strbuilder.append(getDividerSales());
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                         for (SalesreturndetailWithoutPo prod : ReturnListAdapter.getList()) {
                                             strbuilder.append(prod.getSaleproductname());
                                             strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
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

                                             strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                             // strbuilder.append(ActivitySalesbill.this.eol);
                                         }
                                     }
                                     strbuilder.append(getDividerSales());

                                    /* String getFooterpart = FooterValueFullBill();
                                     strbuilder.append(getFooterpart);*/
                                     UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                                     strbuilder.setLength(0);

                                     if (FooterShown.matches("Y")) {
                                         int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                                         strbuilder.append(Footer);
                                         strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                     }
                                     UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                                     strbuilder.setLength(0);
                                     UPrinter.print();
                                     UPrinter.FeedLine();
                                     UPrinter.FullCut();
                                 }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                            startActivity(intent);


                        } catch (NullPointerException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    }
                });

                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        clearbuttondialog();
                    }
                });
            }

            protected final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ACTION_USB_PERMISSION.equals(action)) {
                        synchronized (this) {
                            UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                            if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                                if (device != null) {
                                }
                            } else {
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
                            Toast.makeText(getApplicationContext(), "Permission Granted Successfully",
                                    Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            break;
                    }
                }
            };

            @Override
            protected void onResume() {
                super.onResume();
                RemoteVideoPresentation.showBill();
                showBill();
            }

            public final String getSystemCurrentTime() {

                Long value = System.currentTimeMillis();
                String result = Long.toString(value);
                return result;
            }


            @Override
            protected void onPause() {
                super.onPause();

                RemoteVideoPresentation.fullScreenVideo();
                fullScreen();
            }

            public String getDate() {
                SimpleDateFormat dateFormat = new SimpleDateFormat(
                        "yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                return dateFormat.format(date);
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

                            listView.setAdapter(null);
                            invoiceno();
                            Totalitems.setText("");
                            Grandtotal.setText("");
                            RemoteVideoPresentation.clearTotal();

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


            public void invoiceno() {
                Long Value = System.currentTimeMillis();
                final String result = Long.toString(Value);
                String invoicevalue = Billno.getText().toString();
                mydb = new DBhelper(this);

                ArrayList<String> billno = mydb.getimeino();
                for (String str : billno) {
                    if (str.equals(invoicevalue)) {
                        ArrayList<String> imei = mydb.getprefix(str);
                        Log.e("%%%%%%%%%%%%%", imei.toString());
                        x_imei = imei.toString();
                        String x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                        Log.e("X1_imei is :", x1);
                        imeibillno.setText(x1);
                    } else {
                        continue;
                    }
                }
            }


            /*    public void grandtotal()
            {
                float Getval = ReturnListAdapter.getGrandTotal();
                Log.d("&&&&&&&&", "" + Getval);
                String GrandVal = Float.toString(Getval);
                Grandtotal.setText(GrandVal);


            }*/
            public void setSummaryRow() {
                DecimalFormat f = new DecimalFormat("##.00");
                float Getval = ReturnListAdapter.getGrandTotal();
                Log.d("&&&&&&&&", "" + Getval);
                String GrandVal = f.format(Getval);
                Grandtotal.setText(GrandVal);
                Log.d("@@@@@@@@@@", "" + Grandtotal);


                int Getitem = ReturnListAdapter.getCount();
                int Allitem = Getitem;
                String GETITEM = Integer.toString(Allitem);
                Totalitems.setText(GETITEM);
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



                mydb=new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);
                ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

                ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                        new ArrayAdapter<Registeremployeesmodel>(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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


                        Intent i = new Intent(Activity_Salesreturn_withoutinvoiceno.this, ActivitySales.class);
                        startActivity(i);
                        return true;
                    case R.id.action_settinginfo:
                        ShowIncentive showIncentive = new ShowIncentive(Activity_Salesreturn_withoutinvoiceno.this);
                        showIncentive.show();
                        return true;

                 /*   case R.id.action_settingpurchase:
                        Intent p = new Intent(Activity_Salesreturn_withoutinvoiceno.this, PurchaseActivity.class);
                        startActivity(p);
                        return true;
*/


                    case R.id.action_Masterscn1:
                        Intent p = new Intent(Activity_Salesreturn_withoutinvoiceno.this, Activity_masterScreen1.class);
                        startActivity(p);
                        return true;
                    case R.id.action_settinginv:
                        Intent in = new Intent(Activity_Salesreturn_withoutinvoiceno.this, InventoryTotalActivity.class);
                        startActivity(in);
                        return true;

                    case R.id.action_settingsales:
                        Intent s = new Intent(Activity_Salesreturn_withoutinvoiceno.this, ActivitySalesbill.class);
                        startActivity(s);
                        return true;


                }

                return super.onOptionsItemSelected(item);
            }

            public void UpdateSalesReturn_withoutinvoice() {
                // changed  by richa 8-7-16

                //Updating the server

                final String invoiceidn = invoiceid.getText().toString();
                final String saleimeino = imeibillno.getText().toString();
                for (SalesreturndetailWithoutPo purchase : ReturnListAdapter.getList()) {

                    final DBhelper mydb = new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);


                    transactionid = invoice.getText().toString();
                    final String reasonofreturn = item.toString();
                    final String grandtotal = String.valueOf(purchase.getGrandTotal());
                    final String prodname = purchase.getSaleproductname();
                    final String batchno = purchase.getSalebatchno();
                    final String expirydate = purchase.getExp_Date();
                    final String sellingprice = String.valueOf(purchase.getSalesellingprice());
                    final String mrp = purchase.getSalemrp();
                    final String uom = purchase.getSaleuom();
                    final String qty = String.valueOf(purchase.getSaleqty());
                    final String total = String.valueOf(purchase.getSaletotal());
                    final String pos_user = user2.getText().toString();


                    PersistenceManager.saveStoreId(Activity_Salesreturn_withoutinvoiceno.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    store_id = PersistenceManager.getStoreId(Activity_Salesreturn_withoutinvoiceno.this);


                    class UpdateSalesReturn_WithoutPO extends AsyncTask<Void, Void, String> {
                        ProgressDialog loading;
                        int success;

                        @Override
                        protected String doInBackground(Void... params) {

                            try {
                                HashMap<String, String> hashMap = new HashMap<>();
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_ID, invoiceidn);
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_TRI_ID, transactionid);
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_STORE_ID, store_id);
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_GRAND_TOTAL, grandtotal);
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_REASON_OF_RETURN, reasonofreturn);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_PROD_NAME, prodname);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_BATCH_NO, batchno);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_EXP_DATE, expirydate);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_SELLING_PRICE, sellingprice);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_QUANTITY, qty);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_MRP, mrp);
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_DATE, getDate());
                                hashMap.put(Config.STR_SALES_DETAILS_RETURN_UOM, uom);
                                hashMap.put(Config.STR_SALES_MASTER_RETURN_TOTAL, total);
                                hashMap.put(Config.Retail_Pos_user, pos_user);
                                hashMap.put(Config.SAVE_LIST_PREFIX_NM, "SalesReturn-");
                                hashMap.put(Config.SAVE_LIST_PO_No, transactionid);
                                hashMap.put(Config.STR_SALES_DETAILS_IMEI, saleimeino);


                                JSONParserSync rh = new JSONParserSync();
                              //  JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/sales_return_without_invoice.jsp", hashMap);
                                JSONObject s = rh.sendPostRequest(Config.SALES_RETURN_WITHOUT_INVOICE_NO, hashMap);
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

                            return null;

                        }

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            //     loading = ProgressDialog.show(Activity_Salesreturn_withoutinvoiceno.this, "UPDATING...", "Wait...", false, false);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            //    loading.dismiss();
                            //  Toast.makeText(Activity_Salesreturn_withoutinvoiceno.this, s, Toast.LENGTH_LONG).show();
                        }

                    }
                    UpdateSalesReturn_WithoutPO salesreturn = new UpdateSalesReturn_WithoutPO();
                    salesreturn.execute();
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
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        return null;
                    }
                }

                UpdateShell updateShell = new UpdateShell();
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


            public String HeaderValueFullBill() {
                ArrayList<String> store_name = db.getAllStores();
                String store = (store_name.get(1).toString());
                //   String Store_ID = (store_name.get(0).toString());
                String storeAddress = store_name.get(2).toString();
                String City = store_name.get(3).toString();
                String Storenumber = store_name.get(4).toString();
                String AlternateNo = store_name.get(5).toString();
                String formattedDate;
                StringBuilder strbuilder = new StringBuilder();
                int spaceSide = 3 + (40 - store.length()) / PRINT_LEFT_MARGIN;
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                strbuilder.append(getSpacer(spaceSide) + store + getSpacer(spaceSide));
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                spaceSide = 3 + (40 - storeAddress.length()) / PRINT_LEFT_MARGIN;
                strbuilder.append(getSpacer(spaceSide) + storeAddress + getSpacer(spaceSide));
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                spaceSide = 3 + (40 - City.length()) / PRINT_LEFT_MARGIN;
                strbuilder.append(getSpacer(spaceSide) + City + getSpacer(spaceSide));
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                spaceSide = 3 + (40 - Storenumber.length()) / PRINT_LEFT_MARGIN;
                if (!TextUtils.isEmpty(AlternateNo)) {
                    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN * 3) + "  Ph:" + Storenumber + "," + AlternateNo + getSpacer(spaceSide));
                    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

                } else {
                    strbuilder.append(getSpacer(spaceSide) + "Ph:" + Storenumber + getSpacer(spaceSide));
                    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

                }
                formattedDate = new SimpleDateFormat("EEE,dd MMM yy hh:mma").format(Calendar.getInstance().getTime());
                int billDtSpace = 3 + 40 - ((String.valueOf(Integer.parseInt(invoice.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:  ");
                strbuilder.append(invoice.getText().toString());
                strbuilder.append(getSpacer(billDtSpace));
                strbuilder.append(formattedDate);
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                strbuilder.append(getDividerSales());
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

                return String.valueOf(strbuilder);
            }

            public String FooterValueFullBill() {
                ArrayList<String> store_name = db.getAllStores();
                String Footer = store_name.get(6).toString();

                StringBuilder strbuilder = new StringBuilder();
                DecimalFormat f = new DecimalFormat("##.00");
                String Roundoffvalue = String.valueOf(f.format(Double.parseDouble(Grandtotal.getText().toString())));
                int spaces = 9- String.valueOf(Totalitems.getText().toString()).length();
                int amtspaces = 9 - Roundoffvalue.length();
                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + TOTAL_ITEMS + Totalitems.getText().toString() + getSpacer(PRINT_LEFT_MARGIN ) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                strbuilder.append(getDividerSales());
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

                int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
                strbuilder.append(getSpacer(spaceSide) + Footer + getSpacer(spaceSide));
                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);


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


            public void writeData(SalesreturndetailWithoutPo salesreturndetailWithoutPo) {


                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Method", "AddSalesReturnWithoutInvoice");

                    jsonObject.put("Name", salesreturndetailWithoutPo.getSaleproductname());
                    jsonObject.put("Quantity", salesreturndetailWithoutPo.getSaleqty());
                    jsonObject.put("Price", salesreturndetailWithoutPo.getSalesellingprice());
                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            public void setCustomerName(String customerName) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("Method", "CustomerAdd");
                    jsonObject.put("Name", customerName);
                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void deleteData(int sales) {

                int i = 0;

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Method", "DeleteReturn");
                    jsonObject.put("PositionDelete", sales);

                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            public void updateQuantity(int position, int quantity) {

                int i = 0;

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Method", "ReturnQuantity");
                    jsonObject.put("QuantityUpdate", quantity);
                    jsonObject.put("Position", position);

                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);
                    Log.d("JSONObject", String.valueOf(jsonObject));
                } catch (JSONException e) {

                }


            }

            public void fullScreen() {

                Log.d("FullScreen", "Entering full Screen mode");
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Method", "FullScreen");

                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);

                } catch (JSONException e) {

                }

            }

            public void showBill() {

                try {

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Method", "ShowBill");
                    UsPrinter.writeDataToSerialPort(String.valueOf(jsonObject));
                    UsPrinter.writeDataToSerialPort(USBPrinter.EOT_COMMAND);


                } catch (JSONException e) {

                }

            }
        }

/*
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
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import Adapter.SalesReturnProductAdapter;
import Adapter.SalesReturnwithoutinvoiceno;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Sales;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.Visibility;

public class Activity_Salesreturn_withoutinvoiceno extends Activity {



    private TextWatcher  ProductNameTextWatcher;
    AutoCompleteTextView ProductName;
    ArrayList<SalesreturndetailWithoutPo> ProductNamelist;
    SalesReturnProductAdapter productNmAdaptor ;
    SalesReturnwithoutinvoiceno ReturnListAdapter;
    ArrayList<SalesreturndetailWithoutPo>ProductNameArrayList;
    DBhelper mydb;
    Spinner reason;
    ListView listView;
    String item,transactionid;
    Button submit,clear;
    public static String username = null;

    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;
    CustomAlphaNumericKeyboard mCustomKeyboard;

    String store_id;
    TextView Grandtotal,invoice,invoiceid;
    TextView Totalitems,Billno,imeibillno;
    ActionBar actionBar;
    TelephonyManager tel;
    String x_imei;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String retail_salesreturn_op = "sh /sdcard/sales_return.sh";
    String superuser ="su";


    DBhelper db;
    PendingIntent mPermissionIntent;
    UsbManager mUsbManager;
    UsbDevice device;
    UsbController usbController;
    */
/*  <<<<<<<Updated upstream
        =======*//*

    String MRPisShown;
    private static final int PRINT_COLUMN = 40;
    private static final int PRINT_LEFT_MARGIN = 2;
    private String eol = "\n";
    private static final int BILL_DATE_COLUMN = 14;
    private static final String TOTAL_ITEMS = "Total Items:";
    private static final String BILL_TOTAL = "Bill Total:";
    private static final String BILL_DISC = "Discount:";
    private static final String NETPAYAMOUNT = "Net Amount:";
    private static final String RECEIVEAMOUNT = "Recieved Amount:";
    private static final String BALANCEAMOUNT = "Balance to be Paid:";
    private static final String CREDIT_BILL_TOTAL = "Total Credit Amount:";
    private static final String TOTALSAVINGS = "Total Savings:";
    private static final String DUEAMOUNT = "Due Amount:";



    // >>>>>>>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
    //Stashed changes
    // Tab titles

    protected static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";


    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    */
/**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *//*

    // private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__salesreturn_withoutinvoiceno);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

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
        listView = (ListView)findViewById(R.id.lv_PurchaseProduct);
        submit = (Button)findViewById(R.id.salesreturn_submit);
        clear=(Button)findViewById(R.id.clear);
        Grandtotal=(TextView)findViewById(R.id.grandtotal_textt);
        reason = (Spinner)findViewById(R.id.reasonreturn);
        invoice = (TextView)findViewById(R.id.invoiceno);
        Totalitems=(TextView)findViewById(R.id.totalitem_textt);
        invoiceid = (TextView)findViewById(R.id.invoiceid);
       // ProductName = (AutoCompleteTextView) findViewById(R.id.autoProductName);
        actionBar=getActionBar();

        username =  login.b.getString("Pos_User");


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView)findViewById(R.id.sales_billno);
        imeibillno = (TextView) findViewById(R.id.sales_imeibill);
        Billno.setText(tel.getDeviceId().toString());
        invoiceno();
        Long value = System.currentTimeMillis() + 1000;
        String result = Long.toString(value);
        RemoteVideoPresentation.showBill();
        invoiceid.setText(result);
      db = new DBhelper(this);

        Visibility value1 = db.getStorevisibility();
        MRPisShown=value1.getMrpvisibility();

        ProductName = (CustomAuto) findViewById(R.id.autoProductName);

        ProductName.setDropDownHeight(300);

        mCustomKeyboard = new CustomAlphaNumericKeyboard(Activity_Salesreturn_withoutinvoiceno.this, R.id.keyboard_salesreturn_custom, R.xml.alphanumerickeyboard);

        mCustomKeyboard.registerEditText(R.id.autoProductName);

        ProductName.setThreshold(1);

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
                if (userTypedString.length()<1)
                {
                    return;
                }


                ProductNameArrayList = db.getProductReturnData(userTypedString);
                if (ProductNameArrayList != null) {
                    if (productNmAdaptor == null) {
                        productNmAdaptor = new SalesReturnProductAdapter(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
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
        ProductName.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                        // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                        SalesreturndetailWithoutPo result = (SalesreturndetailWithoutPo) parent.getItemAtPosition(position);
                        if (ReturnListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            ReturnListAdapter = new SalesReturnwithoutinvoiceno(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<SalesreturndetailWithoutPo>());
                            listView.setAdapter(ReturnListAdapter);
                        }

                        int pos = ReturnListAdapter.addProductToList(result);

                        RemoteVideoPresentation.AddSalesReturnProducttoList(result);
                        Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                        ReturnListAdapter.notifyDataSetChanged();
                        ProductName.setText("");
                        setSummaryRow();

                    }


                });

        final DBhelper mydb=new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);
        final ArrayList<Salesreturndetail> reasonReturn= mydb.getReasonReturn();
        ArrayAdapter<Salesreturndetail > stringArrayAdapter =
                new ArrayAdapter<Salesreturndetail>(Activity_Salesreturn_withoutinvoiceno.this, android.R.layout.simple_spinner_dropdown_item,reasonReturn);
        reason.setAdapter(stringArrayAdapter);
        reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                item = adapterView.getItemAtPosition(position).toString();

                // Toast.makeText(getApplicationContext(),
                //    "report : " + item, Toast.LENGTH_LONG).show();
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
                    for (SalesreturndetailWithoutPo item : ReturnListAdapter.getList()) {
                        if (item.getSaleqty() < 0  ) {
                            // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        // pos++;


                        if ( item.getExp_Date()=="select date") {
                            // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (item.getSalebatchno() == null||item.getSalebatchno()=="batch"){

                            item.setSalebatchno(getSystemCurrentTime());

                        }
                        pos++;
                    }
                    if (item.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    SharedPreferences in = getSharedPreferences("key_code", MODE_PRIVATE);
                    int code = in.getInt("code",0);

                    if(code <= 0){
                        code = 1; //--set default start value--
                    }else{
                        code++; //--or just increment it--
                    }

                    in.edit().putInt("code",code).commit(); //--save new value--

                    //--use code variable now--
                    int newKey = code;

                    invoice.setText(String.valueOf(newKey));
                    Log.e ("@@@nishant",String.valueOf(newKey));
                    mydb.insertsalesreturnwithoutinvoice(invoice.getText().toString(), ReturnListAdapter.getList(), item.toString(),username,imeibillno.getText().toString());
                    mydb.insertsalereturndataforproductdetailwithoutinvoiceno(invoice.getText().toString(), ReturnListAdapter.getList(),username,imeibillno.getText().toString());
                    mydb.updateqtyforinvoiceforsalesreturnwithoutinvoiceno(ReturnListAdapter.getList());
                    Toast.makeText(getApplicationContext(), invoice.getText().toString(), Toast.LENGTH_SHORT).show();
                    UpdateSalesReturn_withoutinvoice();
                    UpdateShell();

                    usbController = new UsbController(Activity_Salesreturn_withoutinvoiceno.this,mHandler);

                    //////////////////////////////////////Printer testing ///////////////////////////////////////
                    ArrayList<String> store_name = mydb.getAllStores();
                    String store = (store_name.get(1).toString());
                    String Store_ID = (store_name.get(0).toString());
                    String storeAddress = store_name.get(2).toString();
                    String City = store_name.get(3).toString();

                    String date = mydb.getDate();
                    usbController = new UsbController(Activity_Salesreturn_withoutinvoiceno.this, mHandler);
                    try {
                        if ( mUsbManager.hasPermission(device)==true) {
                            byte[] cmd = new byte[3];
                            cmd[0] = 0x1b;
                            cmd[1] = 0x21;
                            cmd[2] |= 0x10;
                            cmd[2] &= 0xEF;
                            usbController.sendBytes(cmd, device);
                            StringBuilder strbuilder = new StringBuilder();

                            String HeaderVal= HeaderValueFullBill();
                            strbuilder.append(HeaderVal);

                            strbuilder.append("  MRP" + "         " + " S.Price" + "          " + "Qty" + "      " + "Total");
                            strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

                            for (SalesreturndetailWithoutPo prod : ReturnListAdapter.getList()) {
                                DecimalFormat f = new DecimalFormat("##00.00");
                                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getSaleproductname());
                                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                String printMrp = String.valueOf(f.format(prod.getSalemrp()));
                                int spaces = 10 - printMrp.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                String printSPrice = String.valueOf(f.format(prod.getSalesellingprice()));
                                spaces = 10 - printSPrice.length();
                                if (spaces == 0) {
                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                } else {
                                    strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));
                                }
                                String printqty = String.valueOf(prod.getSaleqty());
                                spaces = 10 - printqty.length();
                                strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                String printtotal = String.valueOf(f.format(prod.getSaletotal()));
                                spaces = 10 - printtotal.length();
                                strbuilder.append(getSpacer(spaces) + printtotal);

                                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                                strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
                            }

                        strbuilder.append(getDividerSales());

                        String getFooterpart= FooterValueFullBill();
                        strbuilder.append(getFooterpart);

                        usbController.sendMsgs(String.valueOf(strbuilder), "GBK", device);

                           */
/* String bill =       "------------------------------------------------------------------------------------" +
                                    "MRP" + "           " + " S.Price" + "      " + "Qty" + "      " + "Total" + "\n" +
                                    "------------------------------------------------------------------------------------";
                            usbController.sendMsgs(bill, "GBK", device);
                            // cmd[2] &= 0xEF;
                            usbController.sendBytes(cmd, device);
                            for (SalesreturndetailWithoutPo prod : ReturnListAdapter.getList()) {
                                DecimalFormat f = new DecimalFormat("##00.00");
                                String printMrp = String.valueOf(f.format(Double.parseDouble(prod.getSalemrp())));
                                if (printMrp.length() > 8) {
                                    printMrp = printMrp.substring(0, 8) + "  ";
                                } else {
                                    for (int j = printMrp.length() - 8; j <= printMrp.length(); j++)
                                        ;
                                    {
                                        printMrp = printMrp + " ";
                                    }
                                }
                                String printSPrice = String.valueOf(f.format(prod.getSalesellingprice()));
                                if (printSPrice.length() > 8) {
                                    printSPrice = printSPrice.substring(0, 8) + "  ";
                                } else {
                                    for (int j = printSPrice.length() - 8; j <= printSPrice.length(); j++)
                                        ;
                                    {
                                        printSPrice = printSPrice + " ";
                                    }
                                }
                                String printqty = String.valueOf(prod.getSaleqty());
                                if (printqty.length() > 8) {
                                    printqty = printqty.substring(0, 8) + "  ";
                                } else {
                                    for (int j = printqty.length() - 3; j <= printqty.length(); j++)
                                        ;
                                    {
                                        printqty = printqty + " ";
                                    }
                                }
                                String printtotal = String.valueOf(f.format(prod.getSaletotal()));
                                if (printtotal.length() > 15) {
                                    printtotal = printtotal.substring(0, 15) + "  ";
                                } else {
                                    for (int j = printtotal.length() - 15; j <= printtotal.length(); j++)
                                        ;
                                    {
                                        printtotal = printtotal + " ";
                                    }
                                }
                                usbController.sendMsgs(prod.getSaleproductname() + "\n\n" + printMrp + " \t " + printSPrice + "   " + printqty + "   " + printtotal + "\n", "GBK", device);
                            }
                            usbController.sendMsgs("------------------------------------------------------------------------------------", "GBK", device);
                            DecimalFormat f = new DecimalFormat("##00.00");
                            String Roundoffvalue = String.valueOf(f.format(Math.round(Double.parseDouble(Grandtotal.getText().toString()))));
// long RoundOff = Math.round(Double.parseDouble(GrandTotal.getText().toString())*100)/100;
                            String Totals = "Total Items:-     Bill Total:- " + Roundoffvalue + " LKR\n"+
                                    //      + "Total Savings:-" + Totalsavings.getText().toString() + "      \n" +
                                    //  "Recieved Amount:-" + selling + "\n" +
                                    //   "Expected Exchange:-" + expectedchang.getText().toString().replace("-", "") + "\n" +
                                    "------------------------------------------------------------------------------------" + "\n" +
                                    "  ******  Wish you a healthy life  ******";
                            usbController.sendMsgs(Totals, "GBK", device);*//*

                            usbController.cutPapers(device, 90);
                            usbController.defaultBuzzer(device);
                        }
                        else
                        {
                            usbController.getPermission(device);

                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), ActivitySales.class);
                    startActivity(intent);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
//
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listView.setAdapter(null);
//                invoiceno();
//                Totalitems.setText("");
//                Grandtotal.setText("");

                clearbuttondialog();


            }
        });
    }
    protected final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

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
    protected void onResume() {
        super.onResume();
        RemoteVideoPresentation.showBill();
    }
    public final String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    @Override
    protected void onPause() {
        super.onPause();

        RemoteVideoPresentation.fullScreenVideo();
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
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

                    listView.setAdapter(null);
                    invoiceno();
                    Totalitems.setText("");
                    Grandtotal.setText("");
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


    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        mydb = new DBhelper(this);

        ArrayList<String> billno = mydb.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = mydb.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                String x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                imeibillno.setText(x1);
            } else {
                continue;
            }
        }
    }


    */
/*    public void grandtotal()
    {
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = Float.toString(Getval);
        Grandtotal.setText(GrandVal);


    }*//*

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        Grandtotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + Grandtotal);


        int Getitem=ReturnListAdapter.getCount();
        int Allitem=Getitem;
        String GETITEM=Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }
    @Override
    public String toString() {
        return item;
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
        switch (item.getItemId()) {
            case R.id.action_settings:


            Intent i=new Intent(Activity_Salesreturn_withoutinvoiceno.this,ActivitySales.class);
            startActivity(i);
            return true;

            case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_Salesreturn_withoutinvoiceno.this,PurchaseActivity.class);
                startActivity(p);
                return true;


            case R.id.action_settinginv:
                Intent in=new Intent(Activity_Salesreturn_withoutinvoiceno.this,activity_inventorywithpo.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_Salesreturn_withoutinvoiceno.this,ActivitySalesbill.class);
                startActivity(s);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
    public void UpdateSalesReturn_withoutinvoice() {
        // changed  by richa 8-7-16

        //Updating the server

        final String invoiceidn = invoiceid.getText().toString();
        final String saleimeino = imeibillno.getText().toString();
        for (SalesreturndetailWithoutPo purchase : ReturnListAdapter.getList()) {

            final DBhelper mydb=new DBhelper(Activity_Salesreturn_withoutinvoiceno.this);


            transactionid=invoice.getText().toString();
            final String reasonofreturn = item.toString();
            final String grandtotal = String.valueOf(purchase.getGrandTotal());
            final String prodname = purchase.getSaleproductname();
            final String batchno = purchase.getSalebatchno();
            final String expirydate = purchase.getExp_Date();
            final String sellingprice= String.valueOf(purchase.getSalesellingprice());
            final String mrp = purchase.getSalemrp();
            final String uom = purchase.getSaleuom();
            final String qty =String.valueOf(purchase.getSaleqty());
            final String total =String.valueOf(purchase.getSaletotal());




            PersistenceManager.saveStoreId(Activity_Salesreturn_withoutinvoiceno.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
            store_id = PersistenceManager.getStoreId(Activity_Salesreturn_withoutinvoiceno.this);


            class UpdateSalesReturn_WithoutPO extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int Success;

                @Override
                protected String doInBackground(Void... params) {

                    try{   HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_ID, invoiceidn);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_TRI_ID, transactionid);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_STORE_ID, store_id);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_GRAND_TOTAL,grandtotal);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_REASON_OF_RETURN, reasonofreturn);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_PROD_NAME, prodname);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_BATCH_NO, batchno);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_EXP_DATE, expirydate);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_SELLING_PRICE,sellingprice);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_QUANTITY,qty);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_MRP,mrp);
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_DATE,getDate());
                        hashMap.put(Config.STR_SALES_DETAILS_RETURN_UOM,uom);
                        hashMap.put(Config.STR_SALES_MASTER_RETURN_TOTAL,total);
                        hashMap.put(Config.Retail_Pos_user,username);
                        hashMap.put(Config.SAVE_LIST_PREFIX_NM,"SalesReturn-");
                        hashMap.put(Config.SAVE_LIST_PO_No,transactionid);
                        hashMap.put(Config.STR_SALES_DETAILS_IMEI,saleimeino);





                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.SALES_RETURN_WITHOUT_INVOICE_NO, hashMap);
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        Success = s.getInt(TAG_SUCCESS);
                        if (Success == 1) {
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

                    return null;

                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    //     loading = ProgressDialog.show(Activity_Salesreturn_withoutinvoiceno.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //    loading.dismiss();
                    //  Toast.makeText(Activity_Salesreturn_withoutinvoiceno.this, s, Toast.LENGTH_LONG).show();
                }

            }
            UpdateSalesReturn_WithoutPO salesreturn = new UpdateSalesReturn_WithoutPO();
            salesreturn.execute();
        }

    }


    public void UpdateShell() {


        class UpdateShell extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int Success;
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
    ArrayList<String> store_name = db.getAllStores();
    String store = (store_name.get(1).toString());
    //   String Store_ID = (store_name.get(0).toString());
    String storeAddress = store_name.get(2).toString();
    String City = store_name.get(3).toString();
    String Storenumber = store_name.get(4).toString();
    String AlternateNo = store_name.get(5).toString();
    String formattedDate;
    StringBuilder strbuilder = new StringBuilder();
    int spaceSide = 3 + (40 - store.length()) / PRINT_LEFT_MARGIN;
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
    strbuilder.append(getSpacer(spaceSide) + store + getSpacer(spaceSide));
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
    spaceSide = 3 + (40 - storeAddress.length()) / PRINT_LEFT_MARGIN;
    strbuilder.append(getSpacer(spaceSide) + storeAddress + getSpacer(spaceSide));
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
    spaceSide = 3 + (40 - City.length()) / PRINT_LEFT_MARGIN;
    strbuilder.append(getSpacer(spaceSide) + City + getSpacer(spaceSide));
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
    spaceSide = 3 + (40 - Storenumber.length()) / PRINT_LEFT_MARGIN;
    if (!TextUtils.isEmpty(AlternateNo)) {
        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN * 3) + "  Ph:" + Storenumber + "," + AlternateNo + getSpacer(spaceSide));
        strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

    } else {
        strbuilder.append(getSpacer(spaceSide) + "Ph:" + Storenumber + getSpacer(spaceSide));
        strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

    }
    formattedDate = new SimpleDateFormat("EEE,dd MMM yy hh:mma").format(Calendar.getInstance().getTime());
    int billDtSpace = 3 + 40 - ((String.valueOf(Integer.parseInt(invoice.getText().toString())).length() + BILL_DATE_COLUMN) + formattedDate.length());
    strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:  ");
    strbuilder.append(invoice.getText().toString());
    strbuilder.append(getSpacer(billDtSpace));
    strbuilder.append(formattedDate);
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
    strbuilder.append(getDividerSales());
    strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

    return String.valueOf(strbuilder);
}

    public String FooterValueFullBill()
    {     ArrayList<String> store_name = db.getAllStores();
        String Footer=store_name.get(6).toString();

        StringBuilder strbuilder = new StringBuilder();
        DecimalFormat f = new DecimalFormat("##.00");
        String Roundoffvalue = String.valueOf(f.format(Double.parseDouble(Grandtotal.getText().toString())));
            int spaces = 10 - String.valueOf(Totalitems.getText().toString()).length();
            int amtspaces = 10 - Roundoffvalue.length();
            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + TOTAL_ITEMS + Totalitems.getText().toString() +getSpacer(PRINT_LEFT_MARGIN*5) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue+ getSpacer(PRINT_LEFT_MARGIN));
            strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);
            strbuilder.append(getDividerSales());
            strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);

        int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;
            strbuilder.append(getSpacer(spaceSide) + Footer + getSpacer(spaceSide));
            strbuilder.append(Activity_Salesreturn_withoutinvoiceno.this.eol);


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
        return "  --------------------------------------------  ";
    }


}*/
