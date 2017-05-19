package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Adapter.PurchaseAdapter;
import Adapter.PurchaseInvoiceNoListAdapter;
import Adapter.PurchaseProductAdapter;
import Adapter.Purchaselocalvendoradapter;
import Adapter.PurchaseproductlistAdapter;
import Adapter.SalesAdapter;
import Adapter.SearchproductAdapter;
import Adapter.localproductadapter;
import Adapter.localproductadapterdialog;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.LocalProduct;
import Pojo.PurchaseInvoiceDropDownModel;
import Pojo.Sales;
import Pojo.VendorModel;
import Pojo.PurchaseProductModel;
import Pojo.localvendor;

import static org.apache.http.impl.EnglishReasonPhraseCatalog.INSTANCE;

public class PurchaseActivity extends Activity  {
    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    private TextWatcher pTextWatcher;
    // private TextWatcher xTextWatcher;

    EditText statusEditText = null;
    ActionBar actionBar;
    private TextWatcher barcodeTextWatcher;
    public static String username = null;
    AlertDialog dialog;
    //*******************************************addd local product ********************************************************
    localproductadapterdialog localadapter;
    ArrayList<LocalProduct>localproductlist;
    String ActiveType[] ;
    ArrayAdapter<String> adapterActiveType;
    String SpinValue,localProd_Id_To_Update,SpinValue2,SpinValue3,SpinValue4,localVendor_Id_To_Update;
    AutoCompleteTextView localproductname;
    EditText productname,localbarcode,localhmrp,localsprice,localpprice,localuom,localdiscount;
    TextView productid,localmargin;
    Spinner localactive;

    //*****************************************************8addd localvendor**********************************************************88
    String ActiveTypevendor[] ;
    ArrayAdapter<String> adapterActiveTypevendor;
    ArrayList<localvendor>localvendorlist;
    Purchaselocalvendoradapter purchasevendoradapter;
    AutoCompleteTextView localvendornametname;
    EditText vendorname,vendorcontactname,localadrress1,localcity,localzip,localtelephone;
    TextView vendorid,localcountrydescription,localmobile;
    Spinner localinventory,localvendoractive;
    AutoCompleteTextView ProductidorName;
    EditText BarcodeScan;
    TextView invoiceNo;
    DBhelper helper;
    String store_id,universal_id;

    ArrayList<VendorModel> arrayList;
    ArrayList<PurchaseProductModel> dropdownProductArrayList;
    ArrayList<PurchaseProductModel> purchaseBarcodearraylist;
    ArrayList<PurchaseProductModel> OldPurchaseArraylist;
    ArrayList<PurchaseProductModel>HoldPurchaseList;


    PurchaseAdapter adapter;
    PurchaseProductAdapter productDropdownAdapter;
    PurchaseInvoiceNoListAdapter invoiceNOListAdapter;
    PurchaseproductlistAdapter productListAdapter = null;

    PurchaseInvoiceDropDownModel purchasemodel;
    TelephonyManager tel;


    String PurchaseOrderNUmber;
    String HoldPurchaseorderNo;
    String x_imei,x1,roundofff;
    ImageButton addlocalprod,addlocalvendor;
    Button get_All_productAsDistri;
    //purchaseBarcodeAdapter barcodeAdapter;

    ListView listView;
    private TextView GrandTotal;
    private TextView Saving;
    private TextView Totalitems,Billno;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    String retail_purchase_op = "sh /sdcard/purchase.sh";
    String superuser ="su";


    String mrp ;
    String pprice ;
    String sprice;
    String purchaseholdno;
    String VendorName;


/*

    private PurchaseActivity(){
        this.dropdownProductArrayList=dropdownProductArrayList;
        this.clearbuttondialog();
    }*/
    //INSTANCE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Log.v("&&&&&&&&&&", "on create");


        // getBundle.putString("d4s", "Synctype=RS");
        username = login.b.getString("Pos_User");


        helper = new DBhelper(PurchaseActivity.this);
        Decimal value = helper.getStoreprice();

        roundofff = value.getRoundofff();
        purchaseholdno = String.valueOf(value.getPurchaseholdno());

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        helper = new DBhelper(PurchaseActivity.this);
        Decimal value3 = helper.getStoreprice();
        mrp = value3.getDecimalmrp();
        pprice = value3.getDecimalpprice();
        sprice = value3.getDecimalsprice();
        roundofff = value3.getRoundofff();
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Button sbtbtn = (Button) findViewById(R.id.submit);
        Button clrbtn = (Button) findViewById(R.id.clear);
        Button HoldBtn = (Button) findViewById(R.id.HoldPurchase);
        addlocalprod = (ImageButton) findViewById(R.id.addlocalproduct);
        addlocalvendor = (ImageButton) findViewById(R.id.addlocalvendor);

        // get_All_productAsDistri=(Button)findViewById(R.id.get_All_productAsDistri);

        Billno = (TextView) findViewById(R.id.sales_billno);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);

        Billno.setText("012345678912345");

        Log.v("&&&&&&&&&&", "Listview initilised");
        listView = (ListView) findViewById(R.id.lv_PurchaseProduct);
        GrandTotal = (TextView) findViewById(R.id.GrandTotal);
        Totalitems = (TextView) findViewById(R.id.Totalitem);
        // Saving=(TextView)findViewById(R.id.PurchaseSaving);

        Log.v("&&&&&&&&&&", "AutoCompletetextview for vendorname initilised");

        Log.v("&&&&&&&&&&", "AutoCompletetextview for Productname initilised");

        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);

       /* autoCompleteTextView.setDropDownHeight(200);

        mCustomKeyboard = new CustomAlphaNumericKeyboard(PurchaseActivity.this, R.id.keyboard_purchase_vend, R.xml.alphanumerickeyboard);

        mCustomKeyboard.registerEditText(R.id.autoCompleteTextView);*/
        autoCompleteTextView.setThreshold(1);


        ProductidorName = (CustomAuto) findViewById(R.id.autoProductIdandName);

       /* ProductidorName.setDropDownHeight(300);

        mCustomKeyboard = new CustomAlphaNumericKeyboard(PurchaseActivity.this, R.id.keyboard_purchase_prod, R.xml.alphanumerickeyboard);

        mCustomKeyboard.registerEditText(R.id.autoProductIdandName);*/

        ProductidorName.setThreshold(1);

        invoiceNo = (TextView) findViewById(R.id.invoiceno);
        Billno = (TextView) findViewById(R.id.sales_billno);

//        final Long Value = System.currentTimeMillis();
//        final String resval = Long.toString(Value);
//        invoiceNo.setText(resval);

        SelectPurchaseAlertDialog();

        invoiceno();

/******************************** vendor name oor Distributor name selected from here********************************************************************************************/
        autoCompleteTextView.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                arrayList = helper.getVendorNameforPurchase(userTypedString);
                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new PurchaseAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                        adapter.setList(arrayList);

                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        adapter.setList(arrayList);
                        adapter.notifyDataSetChanged();
                    }

                }
            }
        };
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VendorName = parent.getItemAtPosition(position).toString();

                dropdownProductArrayList = helper.getAllProductForPurchase(VendorName);
                for(int i=0;i<dropdownProductArrayList.size();i++) {

                    for (int j = i + 1; j < dropdownProductArrayList.size(); j++) {

                        if (dropdownProductArrayList.get(i).getProductName().compareTo(dropdownProductArrayList.get(j).getProductName()) < 0) {

                            PurchaseProductModel temp = dropdownProductArrayList.get(j);
                            dropdownProductArrayList.set(j, dropdownProductArrayList.get(i));
                            dropdownProductArrayList.set(i,temp);

                        }
                    }
                    //Collections.reverse(dropdownProductArrayList);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    productListAdapter.setList(dropdownProductArrayList);
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    //Toast.makeText(getApplicationContext(), "U select " + VendorName, Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**************************End for select product and display into the listview ***********************************************************************************************************************/


        helper = new DBhelper(this);
        pTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (autoCompleteTextView.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please select the vendor Or Distributor ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("&&&&&&&&", "After text changed called and text value is" + s.toString());
                if (ProductidorName.isPerformingCompletion()) {
                    Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                    return;
                }
                String userTypedString = ProductidorName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                if (userTypedString.length() < 1) {
                    return;
                }


                if (userTypedString.startsWith(BARCODE_STRING_PREFIX)) {
                    if (dropdownProductArrayList != null) {
                        dropdownProductArrayList.clear();
                    }
                    //this is a barcode generated string
                    if (userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                        //ignore all strings of length < 13
                        return;
                    }
                    dropdownProductArrayList = helper.getProductdata(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if (dropdownProductArrayList.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(dropdownProductArrayList.get(0));
                        return;
                    } else if (dropdownProductArrayList.size() < 1) {
                        //no product found
                        // Toast.makeText(PurchaseActivity.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {

                    dropdownProductArrayList = helper.getProductdata(userTypedString);

                    if (dropdownProductArrayList.size() < 1) {
                        dropdownProductArrayList.clear();
                        //no product found
                        // Toast.makeText(PurchaseActivity.this, "No Product found", Toast.LENGTH_SHORT).show();
                    }
                }


                if (productDropdownAdapter == null) {
                    productDropdownAdapter = new PurchaseProductAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, dropdownProductArrayList);
                    productDropdownAdapter.setList(dropdownProductArrayList);
                    ProductidorName.setAdapter(productDropdownAdapter);
                    ProductidorName.setThreshold(1);

                } else

                {
                    productDropdownAdapter.setList(dropdownProductArrayList);
                    productDropdownAdapter.notifyDataSetChanged();
                }
            }
            //}
        };
        ProductidorName.addTextChangedListener(pTextWatcher);
        ProductidorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);

                PurchaseProductModel resval1 = (PurchaseProductModel) parent.getItemAtPosition(position);

                addProductToPurchaseList(resval1);
            }

        });
/*************************************************************************************************************************/
/**************************End for Barcode***********************************************************************************************************************/
        sbtbtn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          try {

                                              final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
                                              org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
                                              String invoicevalue = invoiceNo.getText().toString();

                                              if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                                                  Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                                                  return;
                                              }

                                              if (productListAdapter.isEmpty()) {
                                                  Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                                                  return;
                                              }
                                              int pos = 0;
                                              for (PurchaseProductModel item : productListAdapter.getList()) {
                                                  if (item.getProductQuantity() <= 0 || item.getProductPrice() <= 0) {
                                                      // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                                                      Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                                                      return;
                                                  } else if (Float.parseFloat(item.getMRP()) < item.getProductPrice()) {
                                                      Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                                                      return;
                                                  }
                                                  pos++;
                                              }
                                              helper.savePurchaseList(productListAdapter.getList(), invoiceNo.getText().toString(), autoCompleteTextView.getText().toString(), username);
                                              helper.SaveGrandDataForPurchase(invoiceNo.getText().toString(), autoCompleteTextView.getText().toString(), GrandTotal.getText().toString(), username);
                                              helper.SavePdfDetailForPurchase(invoiceNo.getText().toString(), autoCompleteTextView.getText().toString(), username);

                                              Toast.makeText(getApplicationContext(), invoicevalue, Toast.LENGTH_SHORT).show();
                                              UpdatePurchasepodetail();
                                              // UpdateShell();

                                              Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                                              startActivity(intent);
                                          } catch (Exception e) {
                                              e.printStackTrace();
                                          }
                                      }
                                  }
        );


        addlocalprod.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchlocalproductDialod();
                    }
                });

        addlocalvendor.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchlocalvendorDialod();

                    }
                });


      /*  addlocalprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Dialog_Purchase_LocalProduct.class);
                startActivity(intent);
            }
        });*/

/*
        addlocalvendor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), Dialog_Purchase_LocalVendor.class);
                startActivity(intent);
            }
        });*/


        HoldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
                org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
                String invoicevalue = invoiceNo.getText().toString();

                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productListAdapter.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                    return;
                }
                int pos = 0;
                for (PurchaseProductModel item : productListAdapter.getList()) {
                    if (item.getProductQuantity() <= 0 || item.getProductPrice() <= 0) {
                        // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();

                        return;
                    } else if (Float.parseFloat(item.getMRP()) < item.getProductPrice()) {
                        Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    pos++;
                }
                helper.saveHoldPurchaseList(productListAdapter.getList(), invoiceNo.getText().toString(), autoCompleteTextView.getText().toString());

                Toast.makeText(getApplicationContext(), invoiceNo.getText().toString(), Toast.LENGTH_SHORT).show();


                Intent syncIntent = new Intent(getApplicationContext(), com.RSPL.POS.Activitypurchase.class);
                startActivity(syncIntent);
            }
        });

        /**************************Alll clear screen ***********************************************************************************************************************/
        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearbuttondialog();
                   /* productListAdapter.clearAllRows();
                    setSummaryRow();
                    invoiceNo.setText("");
                    autoCompleteTextView.setText("");
                    invoiceno();
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(true);*/
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } finally {
                    autoCompleteTextView.setText("");
                }
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


       /* get_All_productAsDistri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownProductArrayList = helper.getAllProductForPurchase(VendorName);
                if (productListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                    listView.setAdapter(productListAdapter);
                }
                productListAdapter.setList(dropdownProductArrayList);
                productListAdapter.notifyDataSetChanged();
                setSummaryRow();
              //  productDropdownAdapter.setList(dropdownProductArrayList);
            //    dropdownProductArrayList.clear();
              //  listView.smoothScrollToPosition(pos);

            }
        });
   */
    }

    private void SearchlocalvendorDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localvendor_dialog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(PurchaseActivity.this);
        localvendornametname = (CustomAuto) searchalertLayout.findViewById(R.id.searchlocalvendorname);
        vendorname = (EditText) searchalertLayout.findViewById(R.id.localvendorname);
        vendorid = (TextView) searchalertLayout.findViewById(R.id.localvendorid);
        vendorcontactname = (EditText) searchalertLayout.findViewById(R.id.localcontactname);
        localadrress1 = (EditText) searchalertLayout.findViewById(R.id.localadress1);
        localcountrydescription = (TextView) searchalertLayout.findViewById(R.id.localcountrydiscription);
        localcity = (EditText) searchalertLayout.findViewById(R.id.localcity);
        localinventory = (Spinner) searchalertLayout.findViewById(R.id.localinventory);
        localvendoractive = (Spinner) searchalertLayout.findViewById(R.id.localactive);
        localmobile = (EditText) searchalertLayout.findViewById(R.id.localmobileno);
        localzip = (EditText) searchalertLayout.findViewById(R.id.localzip);
        localtelephone = (EditText) searchalertLayout.findViewById(R.id.localtelephone);


        final Button searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
        final Button searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);
        final Bundle Bundle = new Bundle();


        final AlertDialog dialog3 = searchalert.create();
        dialog3.setView(searchalertLayout);
        dialog3.setTitle("Add To Distributor");
        dialog3.show();
        dialog3.setCanceledOnTouchOutside(false);

        ActiveTypevendor = getResources().getStringArray(R.array.active_Status);
        adapterActiveTypevendor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveTypevendor);
        adapterActiveTypevendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localvendoractive.setAdapter(adapterActiveTypevendor);
        localvendoractive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue3 = localvendoractive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchalertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(localvendornametname.getWindowToken(), 0);
                return true;
            }

        });

        ActiveTypevendor = getResources().getStringArray(R.array.active_Status);
        adapterActiveTypevendor = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveTypevendor);
        adapterActiveTypevendor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localinventory.setAdapter(adapterActiveTypevendor);
        localinventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue4 = localinventory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //  try {
        helper = new DBhelper(this);
        localvendornametname.setThreshold(1);
        TextWatcher  xTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (localvendornametname.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = localvendornametname.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }

                localvendorlist = helper.getAlllocalVendor(userTypedString);

                if (localvendorlist != null) {
                    if (purchasevendoradapter == null) {
                        purchasevendoradapter = new Purchaselocalvendoradapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                        purchasevendoradapter.setLocalVendorList(localvendorlist);
                        localvendornametname.setAdapter(purchasevendoradapter);
                        localvendornametname.setThreshold(1);
                    } else {
                        localvendornametname.setAdapter(purchasevendoradapter);

                        purchasevendoradapter.setLocalVendorList(localvendorlist);
                        purchasevendoradapter.notifyDataSetChanged();
//
                    }
                }
            }
        };
        localvendornametname.addTextChangedListener(xTextWatcher);
        localvendornametname.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(PurchaseActivity.this);

                localvendor value = (localvendor) parent.getItemAtPosition(pos);
                vendorid.setText(value.getLocalvendorid());
                vendorname.setText(value.getLocalvendorname());
                vendorname.requestFocus();
                localadrress1.setText(value.getLocalvendoraddress());
                localtelephone.setText(value.getLocalvendortele());
                localzip.setText(value.getLocalvendorzip());
                vendorcontactname.setText(value.getLocalvendorcontactname());
                localmobile.setText(value.getLocalvendormobile());
                localcity.setText(value.getLocalvendorcity());
                localcountrydescription.setText(value.getLocalvendorcountry());
                //localvendorinventory.setText();
                SpinValue4=value.getLocalvendorinventory();
                if (  SpinValue4.equals("N"))
                {
                    localinventory.setSelection(0);
                }
                if (  SpinValue4.equals("Y"))
                {
                    localinventory.setSelection(1);
                }
                SpinValue3=value.getLocalactive();
                if (SpinValue3.equals("Y"))
                {
                    localvendoractive.setSelection(0);
                }
                if (SpinValue3.equals("N"))
                {

                    localvendoractive.setSelection(1);
                }
                autoCompleteTextView.setText("");

            }
        });
        searchsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = vendorid.getText().toString();
                localVendor_Id_To_Update = value;
                if (vendorid.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    if (vendorname.getText().toString().matches(""))
                    {
                        vendorname.setError("Please select Vendor Name");
                        return;
                    }
                    if (vendorcontactname.getText().toString().matches(""))
                    {
                        vendorcontactname.setError("Please select Vendor Contact Name");
                        return;
                    }

                    helper.updatelocalVendor(localVendor_Id_To_Update, vendorname.getText().toString(), vendorcontactname.getText().toString(),
                            localadrress1.getText().toString(), localcountrydescription.getText().toString(), localcity.getText().toString(), localmobile.getText().toString(), SpinValue3, SpinValue4, localzip.getText().toString(), localtelephone.getText().toString(),username);


                    helper.updatelocalVendorinpurchase(localVendor_Id_To_Update, vendorname.getText().toString(), localadrress1.getText().toString(), localcountrydescription.getText().toString(), localcity.getText().toString(), localzip.getText().toString(), SpinValue4, SpinValue3,username);

                    Toast.makeText(getApplicationContext(), "Local Vendor Updated", Toast.LENGTH_SHORT).show();
                    updateLocalVendor();
                    dialog3.dismiss();


                       /* Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                        startActivity(intent);*/



                } catch (Exception e) {

                    e.printStackTrace();
                }

            }




        });

        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog3.dismiss();

            }
        });


       /* }catch (Exception e){
            e.printStackTrace();
        }*/
    }



    /*  private void SearchlocalproductDialod() {


          LayoutInflater inflater = getLayoutInflater();

          final View searchalertLayout = inflater.inflate(R.layout.search_localproduct_dialog, null);
          AlertDialog.Builder searchalert = new AlertDialog.Builder(PurchaseActivity.this);
          localproductname = (CustomAuto) searchalertLayout.findViewById(R.id.searchlocalproductname);
           productname = (EditText) searchalertLayout.findViewById(R.id.localproductname);
           productid = (TextView) searchalertLayout.findViewById(R.id.localProductid);
          localbarcode = (EditText) searchalertLayout.findViewById(R.id.localbarcode);
          localhmrp = (EditText) searchalertLayout.findViewById(R.id.localmrp);
          localhmrp.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(mrp))});

          localsprice = (EditText) searchalertLayout.findViewById(R.id.localsprice);
          localsprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(sprice))});

          localpprice = (EditText) searchalertLayout.findViewById(R.id.localpprice);
          localpprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(pprice))});


          localactive = (Spinner) searchalertLayout.findViewById(R.id.localactive);
          localmargin = (TextView) searchalertLayout.findViewById(R.id.localmargin);
       localuom = (EditText) searchalertLayout.findViewById(R.id.localuom);
           localdiscount = (EditText) searchalertLayout.findViewById(R.id.localdiscount);
          localdiscount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,2)});



          final Button searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
          final Button searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);
          final Bundle Bundle = new Bundle();



          final AlertDialog dialog2 = searchalert.create();
          dialog2.setView(searchalertLayout);
          dialog2.setTitle("Add To Product");
          dialog2.show();
          dialog2.setCanceledOnTouchOutside(false);

          ActiveType = getResources().getStringArray(R.array.active_Status);
          adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
          adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

          localactive.setAdapter(adapterActiveType);
          localactive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  SpinValue = localactive.getSelectedItem().toString();
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });

          //  try {
          helper = new DBhelper(this);
          localproductname.setThreshold(1);
          TextWatcher  xTextWatcher = new TextWatcher() {
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

                  localproductlist = helper.getAllLocalProductforpurchase(userTypedString);

                  if (localproductlist != null) {
                      if (localadapter == null) {
                          localadapter = new localproductadapterdialog(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
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
              public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                  Log.d("Debuging", "On click called ");

                  final DBhelper mydb = new DBhelper(PurchaseActivity.this);

                  LocalProduct value = (LocalProduct) parent.getItemAtPosition(pos);
                  productid.setText(value.getProductid());
                  productname.setText(value.getProductname());
                  productname.requestFocus();
                  localsprice.setText(value.getSellingPrice());
                  localpprice.setText(value.getPurchasePrice());
                  localbarcode.setText(value.getBarcode());
                  localhmrp.setText(value.getMRP());
                  localdiscount.setText(value.getDiscount());
                  localuom.setText(value.getUom());
                  SpinValue = value.getActive();
                  if (SpinValue.equals("Y")) {
                      localactive.setSelection(0);
                  }
                  if (SpinValue.equals("N")) {
                      localactive.setSelection(1);
                  }
                  localmargin.setText(value.getMargin());

                  localproductname.setText("");


              }
          });
          searchsubmit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  String value = productid.getText().toString();
                  localProd_Id_To_Update = value;
                  if (productid.getText().toString().matches(""))
                  {
                      Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                      return;
                  }
                  if (localsprice.getText().toString().matches(""))
                  {
                      localsprice.setError("Please fill Selling Price");
                      return;
                  }
                  if (localpprice.getText().toString().matches(""))
                  {

                      localpprice.setError("Please fill Purchase Price");
                      return;
                  }
                  if (productname.getText().toString().matches(""))
                  {
                      productname.setError("Please fill Product name ");
                      return;
                  }
                  if (localhmrp.getText().toString().matches(""))
                  {
                      localhmrp.setError("Please fill Mrp ");
                      return;
                  }


                  try {
                      float selling =Float.parseFloat(localsprice.getText().toString());
                      float purchase = Float.parseFloat(localpprice.getText().toString());
                      float mrp = Float.parseFloat(localhmrp.getText().toString());
                      if (selling > mrp) {
                          localsprice.setError("Invalid Selling Price");
                          return;

                      }
                      if (purchase > selling) {
                          localpprice.setError("Invalid PurchasePrice");
                          return;
                      }
                      if (purchase > mrp) {
                          localpprice.setError("Invalid purchase Price");
                          return;
                      }

                      helper.updatelocalProduct(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                              localsprice.getText().toString(), localpprice.getText().toString(), SpinValue, localmargin.getText().toString(),username,localdiscount.getText().toString(),localuom.getText().toString());


                      helper.updatelocalProductCom(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                              localsprice.getText().toString(), localpprice.getText().toString(), SpinValue,username,localdiscount.getText().toString(),localuom.getText().toString());

                      Toast.makeText(getApplicationContext(), "Local Product Updated", Toast.LENGTH_SHORT).show();
                       updateLocalProduct();
                      dialog2.dismiss();
                         *//* Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
                        startActivity(intent);*//*
                    // LocalproductUpdateScript();


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


       *//* }catch (Exception e){
            e.printStackTrace();
        }*//*
    }

*/
    private void SearchlocalproductDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localproduct_dialog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(PurchaseActivity.this);
        localproductname = (CustomAuto) searchalertLayout.findViewById(R.id.searchlocalproductname);
        productname = (EditText) searchalertLayout.findViewById(R.id.localproductname);
        productid = (TextView) searchalertLayout.findViewById(R.id.localProductid);
        localbarcode = (EditText) searchalertLayout.findViewById(R.id.localbarcode);
        localhmrp = (EditText) searchalertLayout.findViewById(R.id.localmrp);
        localhmrp.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(mrp))});

        localsprice = (EditText) searchalertLayout.findViewById(R.id.localsprice);
        localsprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(sprice))});

        localpprice = (EditText) searchalertLayout.findViewById(R.id.localpprice);
        localpprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,Integer.parseInt(pprice))});

        localactive = (Spinner) searchalertLayout.findViewById(R.id.localactive);
        localmargin = (TextView) searchalertLayout.findViewById(R.id.localmargin);
        localuom = (EditText) searchalertLayout.findViewById(R.id.localuom);
        localdiscount = (EditText) searchalertLayout.findViewById(R.id.localdiscount);
        localdiscount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,2)});



        final Button searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
        final Button searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);
        final Bundle Bundle = new Bundle();


        final AlertDialog dialog2 = searchalert.create();
        dialog2.setView(searchalertLayout);
        dialog2.setTitle("Add To Product");
        dialog2.show();
        dialog2.setCanceledOnTouchOutside(false);

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        localactive.setAdapter(adapterActiveType);
        localactive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = localactive.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchalertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(localproductname.getWindowToken(), 0);
                return true;
            }

        });

        //  try {
        helper = new DBhelper(this);
        localproductname.setThreshold(1);
        TextWatcher  xTextWatcher = new TextWatcher() {
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

                localproductlist = helper.getAllLocalProductforpurchase(userTypedString);

                if (localproductlist != null) {
                    if (localadapter == null) {
                        localadapter = new localproductadapterdialog(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
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
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(PurchaseActivity.this);

                LocalProduct value = (LocalProduct) parent.getItemAtPosition(pos);
                productid.setText(value.getProductid());
                productname.setText(value.getProductname());
                productname.requestFocus();
                localsprice.setText(value.getSellingPrice());
                localpprice.setText(value.getPurchasePrice());
                localbarcode.setText(value.getBarcode());
                localhmrp.setText(value.getMRP());
                localdiscount.setText(value.getDiscount());
                localuom.setText(value.getUom());
                SpinValue = value.getActive();
                if (SpinValue.equals("Y")) {
                    localactive.setSelection(0);
                }
                if (SpinValue.equals("N")) {
                    localactive.setSelection(1);
                }
                localmargin.setText(value.getMargin());

                localproductname.setText("");


            }
        });
        searchsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value = productid.getText().toString();
                localProd_Id_To_Update = value;
                if (productid.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (localsprice.getText().toString().matches(""))
                {
                    localsprice.setError("Please fill Selling Price");
                    return;
                }
                if (localpprice.getText().toString().matches(""))
                {
                    localpprice.setError("Please fill Purchase Price");
                    return;
                }
                if (productname.getText().toString().matches(""))
                {
                    productname.setError("Please fill Product name ");
                    return;
                }
                if (localhmrp.getText().toString().matches(""))
                {
                    localhmrp.setError("Please fill Mrp ");
                    return;
                }
                try {

                    float selling =Float.parseFloat(localsprice.getText().toString());
                    float purchase = Float.parseFloat(localpprice.getText().toString());
                    float mrp = Float.parseFloat(localhmrp.getText().toString());
                    if (selling > mrp) {
                        localsprice.setError("Invalid Selling Price");
                        return;

                    }
                    if (purchase > selling) {
                        localpprice.setError("Invalid PurchasePrice");
                        return;
                    }
                    if (purchase > mrp) {
                        localpprice.setError("Invalid purchase Price");
                        return;
                    }

                    helper.updatelocalProduct(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValue, localmargin.getText().toString(),username,localdiscount.getText().toString(),localuom.getText().toString());


                    helper.updatelocalProductCom(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValue,username,localdiscount.getText().toString(),localuom.getText().toString());

                    Toast.makeText(getApplicationContext(), "Local Product Updated", Toast.LENGTH_SHORT).show();
                    updateLocalProduct();
                    dialog2.dismiss();
                       /* Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
                        startActivity(intent);*/


                    //call here local product update script
                    // LocalproductUpdateScript();


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


       /* }catch (Exception e){
            e.printStackTrace();
        }*/
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

                    productListAdapter.clearAllRows();
                    setSummaryRow();
                    invoiceNo.setText("");
                    autoCompleteTextView.setText("");
                    invoiceno();
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(true);

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
    final Handler requestFocusHandler = new Handler();



    private void addProductToPurchaseList(PurchaseProductModel resval1) {
        if (productListAdapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, resval1);
            listView.setAdapter(productListAdapter);
        }
        int pos= productListAdapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        productListAdapter.notifyDataSetChanged();
        ProductidorName.setText("");
        setSummaryRow();
        productDropdownAdapter.setList(dropdownProductArrayList);
        dropdownProductArrayList.clear();
        listView.smoothScrollToPosition(pos);
    }

    public void SelectPurchaseAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdailog_purchase, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);


        final Button NewPurchase = (Button) alertLayout.findViewById(R.id.NewPurchase);
        final Button OldPurchase = (Button) alertLayout.findViewById(R.id.Oldpurchasebutton);
        final Button Hold=(Button)alertLayout.findViewById(R.id.HoldButton);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Button Cancel = (Button) alertLayout.findViewById(R.id.Cancelbtn);
        final Button SubmitForHold=(Button)alertLayout.findViewById(R.id.submitbtnForHold);

               /*final EditText LastInvoiceno=(EditText)alertLayout.findViewById(R.id.EnterInvoiceno);*/
        final Spinner spinner = (Spinner) alertLayout.findViewById(R.id.VendororDistributorName);
        final Spinner Last3InvoiceNo = (Spinner) alertLayout.findViewById(R.id.Last3InvoiceNo);
        final Spinner Holddata=(Spinner)alertLayout.findViewById(R.id.HoldPurchaseNo);
        // final Spinner spinner;
        Last3InvoiceNo.setVisibility(View.GONE);
        submit.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.GONE);
        Cancel.setVisibility(View.VISIBLE);
        Holddata.setVisibility(View.GONE);
        SubmitForHold.setVisibility(View.GONE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("SELECT PURCHASE OPTION");
       /* TextView title = new TextView(this);
        title.setText("Select Purchase Option");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        alert.setCustomTitle(title);*/
        dialog = alert.create();

/*
        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(localproductname.getWindowToken(), 0);
                return true;
            }

        });*/

        OldPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getApplicationContext(), "Old Purchase", Toast.LENGTH_SHORT).show();
                Last3InvoiceNo.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);

              /*  LastInvoiceno.getText().toString();*/

            }
        });

        NewPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "New Purchase", Toast.LENGTH_SHORT).show();
                OldPurchase.setVisibility(View.GONE);
                NewPurchase.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Holddata.setVisibility(View.VISIBLE);
                OldPurchase.setVisibility(View.GONE);
                NewPurchase.setVisibility(View.GONE);
                Last3InvoiceNo.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.VISIBLE);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String Spinnervalue = spinner.getSelectedItem().toString();
                    String userTypedInvoiceno = Last3InvoiceNo.getSelectedItem().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + Last3InvoiceNo);
                    ProductidorName.requestFocus();
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }
                    OldPurchaseArraylist = helper.getAllOLDPurchaseList(PurchaseOrderNUmber);
                    Log.d("!@#!@#!@#!@#", "Product arraylist size is " + OldPurchaseArraylist.size());

                    if (OldPurchaseArraylist != null && OldPurchaseArraylist.size() > 0) {
                        if (productListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                            listView.setAdapter(productListAdapter);
                        }
                        for (PurchaseProductModel prod : OldPurchaseArraylist) {
                            productListAdapter.addProductToList(prod);
                        }
                        autoCompleteTextView.setText("" + spinner.getSelectedItem().toString() + "");
                        autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                        autoCompleteTextView.setEnabled(false);
                        Log.i("&&&&&&&&!", "Adding " + OldPurchaseArraylist.get(0) + " to Product List..");
                        productListAdapter.notifyDataSetChanged();
                        setSummaryRow();
                        dialog.dismiss();

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        SubmitForHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    String Spinnervalue = Holddata.getSelectedItem().toString();
                if (HoldPurchaseorderNo == null)
                {
                    Toast.makeText(getApplicationContext(), "No Hold Item Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                HoldPurchaseList = helper.getAllHoldPurchaseData(HoldPurchaseorderNo);
                if (HoldPurchaseList != null && HoldPurchaseList.size() > 0) {
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistAdapter(PurchaseActivity.this, new ArrayList<PurchaseProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    for (PurchaseProductModel prod : HoldPurchaseList) {
                        productListAdapter.addProductToList(prod);

                    }
                    autoCompleteTextView.setText(HoldPurchaseList.get(0).getVendorName());
                    helper.replaceflag(HoldPurchaseorderNo);
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + HoldPurchaseList.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();
                }
            }
        });

        ArrayList<VendorModel> vendors = helper.getVendors();
        ArrayAdapter<VendorModel> stringArrayAdapter =
                new ArrayAdapter<VendorModel>(PurchaseActivity.this, android.R.layout.simple_spinner_dropdown_item, vendors);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  Toast.makeText(getApplicationContext(), "OnItemSelectedListener  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = spinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);


                ArrayList<PurchaseInvoiceDropDownModel> LastInvoices = helper.getLastInvoices(Spinnervalue,Integer.parseInt(purchaseholdno));
                invoiceNOListAdapter = new PurchaseInvoiceNoListAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
                Last3InvoiceNo.setAdapter(invoiceNOListAdapter);
                Last3InvoiceNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Toast.makeText(getApplicationContext(), "OnItemSelectedListener  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                        PurchaseInvoiceDropDownModel resval1 = (PurchaseInvoiceDropDownModel) parent.getItemAtPosition(position);
                        PurchaseOrderNUmber = resval1.getPurchaseOrderNo();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayList<PurchaseInvoiceDropDownModel> LastInvoices = helper.getInvoiceNumberForPurchase();
        invoiceNOListAdapter = new PurchaseInvoiceNoListAdapter(PurchaseActivity.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
        Holddata.setAdapter(invoiceNOListAdapter);
        Holddata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PurchaseInvoiceDropDownModel resval1 = (PurchaseInvoiceDropDownModel) parent.getItemAtPosition(position);
                HoldPurchaseorderNo = resval1.getPurchaseOrderNo();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OldPurchase.setVisibility(View.VISIBLE);
                NewPurchase.setVisibility(View.VISIBLE);
                submit.setVisibility(View.INVISIBLE);
                spinner.setVisibility(View.INVISIBLE);
                Last3InvoiceNo.setVisibility(View.INVISIBLE);
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(intent);

            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }



    /**************************
     * Alll Calculation done here
     ***********************************************************************************************************************/

    public void setSummaryRow() {


        if (roundofff.toString().matches("Y")) {

            DecimalFormat f2 = new DecimalFormat("##");

            float Getval = productListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);

            String GrandVal = f2.format(Getval).concat(".00");
            GrandTotal.setText(GrandVal);
            //  productListAdapter.notifyDataSetChanged();


            int Getitem = productListAdapter.getCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);


        } else {


            DecimalFormat f=new DecimalFormat("##.00");


            float Getval = productListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);

            String GrandVal = f.format(Getval);
            GrandTotal.setText(GrandVal);
            //  productListAdapter.notifyDataSetChanged();


            int Getitem = productListAdapter.getCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);

       /* float GetValue=productListAdapter.TotalSaving();
        String Getsave=Float.toString(GetValue);
        Saving.setText(Getsave);
*/
        }
    }

    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        helper = new DBhelper(this);

        ArrayList<String> billno = helper.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = helper.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                invoiceNo.setText(x1);
            } else {
                continue;
            }
        }
    }

    public  void PurchaseActivityhelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("          PURCHASE     ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "The user selects from the Home Pasge \uF0E0 Purchasing \uF0E0 Purchaing. The purchasing is used to procure a product from a vendor. This is used when the retailer issues a purchase order to the vendor. In case retailer does not issue a purchase order to the vendor, the retailer can ignore the option and directly do inventory without a purchase order.\n" +
                "\n" +
                "Functions:\n" +
                "\n" +
                "The user can create a purchase order using the following options:\n" +
                "\n" +
                "Option 1: Old Purchase: User wants to procure products purchased in the past from the existing vendor.(see fig 1.)\n" +
                "Option 2: New Purchase: User is procuring the items from the vendor first time or is procuring tems once (see fig 2.)\n" +
                "Option 3: Hold: User is creating a purchase order but he decides to do some other work. He can hold a purchase order and resume the purchase order from where he has left. (see fig 3).\n" +
                "Cancel: User will go back to Home Page -\uF0E0 Purchasing.\n" +
                "\n" +
                "\n" +
                "\n" +
                "Option 1:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The user selets the option “Old Purchase”. The user can select the vendor and last 7 purchases he has done from the vendor. Here predominently the vendor is a distributor.\n" +
                "\n" +
                "Option:\n" +
                "\n" +
                "Submit: The user selects the option “Submit” to copy the selected Purchase order.\n" +
                "Cancel: User will go back to the menu page.\n" +
                "\n" +
                "\n" +

                "Option 2:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The user selects the “Hold” option to select from the list purchase orders which are under “Hold’.\n" +
                "\n" +
                "All hold purchase orders are deleted if not released within 3 days of rasing the request.\n" +
                "\n" +
                "Option:\n" +
                "\n" +
                "Submit: When the user selects the “Submit” option with the Purchase order selected, the purchase order all items which are under hold are retrieved and shown.\n" +
                "Cancel: User goes back to the Home Page \uF0E0 Purchasing \uF0E0 Purchasing\n" +
                "\n" +
                "Option 3:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "User either by copying a purchase order or holding a new purchase order or creating a new puchase order.\n" +
                "\n" +
                "Input:\n" +
                "\n" +
                "Vendor/Distributor Name\n" +
                "Product Name\n" +
                "MRP\n" +
                "Purchase Price\n" +
                "Quantity\n" +
                "\n" +
                "\n" +
                "Option:\n" +
                "\n" +
                "Submit: User will submit the purchase order to the vendor and will e-Mail the purchase order to the distributor/local vendor if the e-mail address of distributor//vendor is available.\n" +
                "Hold: User can hold the purchase order and return to add products later.\n" +
                "Clear: It will clear the Item.\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.dismiss();
            }
        });


        alertDialog.show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
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

                Intent i = new Intent(PurchaseActivity.this, Activitypurchase.class);
                startActivity(i);
                break;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(PurchaseActivity.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                PurchaseActivityhelpdialog();

                return true;
          /*  case R.id.action_settingpurchase:
                Intent p=new Intent(PurchaseActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/

            case R.id.action_settinginv:
                Intent in=new Intent(PurchaseActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(PurchaseActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;



        }

        return super.onOptionsItemSelected(item);
    }
    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void UpdatePurchasepodetail() {

//        PurchaseProductModel purchase = new PurchaseProductModel();
        final String purchaseinvoicevalue = invoiceNo.getText().toString();
        final String purchasevendorname = autoCompleteTextView.getText().toString();
        final String purchasegrandtotal = GrandTotal.getText().toString();

        for (PurchaseProductModel purchase : productListAdapter.getList()) {
            final String purchaseid = purchase.getProductId();
            final String purchasename = purchase.getProductName();
            final String purchaseprice = String.valueOf(purchase.getProductPrice());
            //  final String purchasebarcode = purchase.getBarcode();
            final String purchaseunitmeasure= purchase.getUom();
            final String purchasesellingprice = purchase.getSellingPrice();
            final String purchaseconvfact = purchase.getConversionfactor();
            final String purchaseprofitmargin = purchase.getProfit_Margin();
            final String purchaseindustryname = purchase.getIndusteryname();
            final String purchaseQuantity = String.valueOf(purchase.getProductQuantity());
            final String purchasemrp = purchase.getMRP();
            final String purchasetotal = String.valueOf(purchase.getTotal());
            final String purchasediscount = String.valueOf(purchase.getPurchasediscount());



            PersistenceManager.saveStoreId(PurchaseActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
            store_id = PersistenceManager.getStoreId(PurchaseActivity.this);
            universal_id =  PersistenceManager.getStoreId(PurchaseActivity.this);
            class UpdatePurchase extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.Retail_store_id, store_id);
                        hashMap.put(Config.Retail_purchase_po_no,purchaseinvoicevalue);
                        hashMap.put(Config.Retail_purchase_id, purchaseid);
                        hashMap.put(Config.Retail_purchase_name, purchasename);
                        hashMap.put(Config.Retail_purchase_puchase, purchaseprice);
                        hashMap.put(Config.Retail_purchase_barcode,"n/a");
                        hashMap.put(Config.Retail_purchase_mrp,purchasemrp);
                        hashMap.put(Config.Retail_purchase_profit_margin,purchaseprofitmargin);
                        hashMap.put(Config.Retail_purchase_conv_fact,purchaseconvfact);
                        hashMap.put(Config.Retail_purchase_selling,purchasesellingprice);
                        hashMap.put(Config.Retail_purchase_mrp,purchasemrp);
                        hashMap.put(Config.Retail_purchase_Vendorname,purchasevendorname);
                        hashMap.put(Config.Retail_purchase_uom,purchaseunitmeasure);
                        hashMap.put(Config.Retail_purchase_Industryname,purchaseindustryname);
                        hashMap.put(Config. Retail_purchase_Quantity,purchaseQuantity);
                        hashMap.put(Config.Retail_purchase_Total,purchasetotal);
                        hashMap.put(Config.Retail_purchase_GrandTotal,purchasegrandtotal);
                        hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_DISCOUNT,purchasediscount);

                        hashMap.put(Config.Retail_purchase_Universal_id,universal_id);

                        hashMap.put(Config.Retail_Pos_user,username);
                        hashMap.put(Config.Retail_purchase_date,getDate());





                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://uploaddev.eu-gb.mybluemix.net/retail_store_po_detail.jsp", hashMap);
                        Log.d("Login attempt", s.toString());
                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            helper.updatepurchaseflagdetail(x1);
                            helper.updateflagsaveGranddataintopoMaster(x1);
                            helper.updateflagSavePdfDetailForpurchase(x1);



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
                    // loading = ProgressDialog.show(PurchaseActivity.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
//                    loading.dismiss();
                    //  Toast.makeText(PurchaseActivity.this, s, Toast.LENGTH_LONG).show();
                }

            }
            UpdatePurchase updatevendor = new UpdatePurchase();
            updatevendor.execute();
        }

    }



    public void updateLocalVendor() {

        final DBhelper  mydb=new DBhelper(PurchaseActivity.this);

        final String  local_vendorname =vendorname.getText().toString().trim();
        final String   local_vendoraddress= localadrress1.getText().toString().trim();
        final String   local_vendortele= localtelephone.getText().toString().trim();
        final String   local_vendorzip=localzip.getText().toString().trim();
        final String  local_vendorcontactname=vendorcontactname.getText().toString().trim();
        final String  local_vendormobile=localmobile.getText().toString().trim();
        final String  local_vendorcity=localcity.getText().toString().trim();
        final String  local_vendorcountry=localcountrydescription.getText().toString().trim();
        final String  local_vendorinventory= localinventory.getSelectedItem().toString();
        final String  local_vendoractive= localvendoractive.getSelectedItem().toString();
        PersistenceManager.saveStoreId(PurchaseActivity.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String  store_id= PersistenceManager.getStoreId(PurchaseActivity.this);

        class UpdateVendor extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(ActivitylocalVendor.this, "Logging in...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(ActivitylocalVendor.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.LOCAL_VENDOR_STORE_ID,store_id);
                    hashMap.put(Config.LOCAL_VENDOR_ID,localVendor_Id_To_Update);
                    hashMap.put(Config.RETAIL_VEND_DSTR_ID,localVendor_Id_To_Update);

                    hashMap.put(Config.LOCAL_VENDOR_NAME,local_vendorname);
                    hashMap.put(Config.LOCAL_VENDOR_CONTACT,local_vendorcontactname);
                    hashMap.put(Config.LOCAL_VENDOR_ADDRESS,local_vendoraddress);
                    hashMap.put(Config.LOCAL_VENDOR_CITY,local_vendorcity);
                    hashMap.put(Config.LOCAL_VENDOR_TELE,local_vendortele);
                    hashMap.put(Config.LOCAL_VENDOR_ZIP,local_vendorzip);
                    hashMap.put(Config.LOCAL_VENDOR_ACTIVE,local_vendoractive);
                    hashMap.put(Config.LOCAL_VENDOR_COUNTRY,local_vendorcountry);
                    hashMap.put(Config.LOCAL_VENDOR_MOBILE, local_vendormobile);
                    hashMap.put(Config.LOCAL_VENDOR_INVENTORY, local_vendorinventory);
                    hashMap.put(Config.Retail_Pos_user,username);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/try.jsp",hashMap);

                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        mydb.updateflaglocalvendor(localVendor_Id_To_Update);


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;            }
        }

        UpdateVendor updateVendor = new UpdateVendor();
        updateVendor.execute();
    }

    public void updateLocalProduct() {


        final DBhelper mydb = new DBhelper(PurchaseActivity.this);

        //Local_prod_store_id =local_prod_store_id.getText().toString().trim();
        final  String  local_prod_id= productid.getText().toString().trim();
        final String   local_product_name= productname.getText().toString().trim();
        final String   local_product_bare_code=localbarcode.getText().toString().trim();
        final String   local_product_mrp=localhmrp.getText().toString().trim();
        final String   local_product_selling_price=localsprice.getText().toString().trim();
        final String  local_product_purchase_price=localpprice.getText().toString().trim();
        final String  local_product_active=localactive.getSelectedItem().toString().trim();
        final String  local_product_margin= localmargin.getText().toString().trim();
        final String  local_product_discount= localdiscount.getText().toString().trim();
        final String  local_product_uom= localuom.getText().toString().trim();


        PersistenceManager.saveStoreId(PurchaseActivity.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String  store_id= PersistenceManager.getStoreId(this);
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
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_STORE_ID,store_id);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PRODUCT_ID,local_prod_id);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PRODUCT_NAME,local_product_name);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_BARE_CODE,local_product_bare_code);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_MRP,local_product_mrp);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_SELLING_PRICE,local_product_selling_price);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PURCHASE_PRICE,local_product_purchase_price);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_ACTIVE,local_product_active);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PROFIT_MARGIN,local_product_margin);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_DISCOUNT,local_product_discount);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_PROFIT_UOM,local_product_uom);

                    hashMap.put(Config.Retail_Pos_user,username);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/retail_store_prod_local.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        mydb.updateflaglocalproduct(local_prod_id);


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





   /* public void UpdateShell() {


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
                    executerPurchase(superuser);
                    String s = executerPurchase(retail_purchase_op);
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


    public String executerPurchase(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/purchase.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }
*/

}