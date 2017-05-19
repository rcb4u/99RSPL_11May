package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.DatePicker;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import Adapter.InventoryProductDropdownAdapter;
import Adapter.Inventorygrnadapter;
import Adapter.Inventorywithpoadapter;
import Adapter.PurchaseproductlistwithpoAdapter;
import Adapter.Withoutpo_localvendoradapter;
import Adapter.Withpo_localvendoradapter;
import Adapter.localproductadapterinventorydialog;
import Adapter.localproductadapterinventorypodialog;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Inventorygrnmodel;
import Pojo.LocalProduct;
import Pojo.PurchaseProductModelwithpo;
import Pojo.localvendor;


public class activity_inventorywithpo extends Activity {
    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    public static final String BARCODE_STRING_PREFIX = "@";
    public static String username = null;


    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    AlertDialog dialog;
    JSONParserSync rh = new JSONParserSync();
    Calendar myCalendar = Calendar.getInstance();
    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH)+1;
    int Day = c.get(Calendar.DAY_OF_MONTH);


    //**********************************************Adapter class*******************************************************

    PurchaseproductlistwithpoAdapter productListAdapter;
    Inventorygrnadapter grnnumberlistadapter;
    InventoryProductDropdownAdapter inventoryProductDropdownAdapter;

    Inventorywithpoadapter adapter;


    //***********************************************pojo class**********************************************************88

    ArrayList<PurchaseProductModelwithpo> dropdownProductArrayList;
    ArrayList<PurchaseProductModelwithpo> HoldInventoryList;
    ArrayList<PurchaseProductModelwithpo> documentNumberForIntegration;
    ArrayList<PurchaseProductModelwithpo> arrayList;

    //*************************textwatcher**********************************************************
    private TextWatcher mTextWatcher;
    private TextWatcher ListTextWatcher;

    AutoCompleteTextView InvProductidorName;
    AutoCompleteTextView autoCompleteTextView;

    String x_imei, x1, store_id, universal_id, Store_Id;
    TextView Ponumbers;
    String userTypedInvoiceno,roundofff;
    String Spinnervalue, HoldInventoryorderNo, document;
    String GetDistributorNumber;
    private TextView GrandTotal, Billno;
    private TextView Totalitems;
    ActionBar actionBar;
    Button update, Holdinventorybutton;
    DBhelper helper;
    ListView listView;
    TextView Ponum;
    TelephonyManager tel;
   EditText VendorEditNo,VendorEditDate;
    ImageButton addlocalproduct;

    String retail_inventory_op = "sh /sdcard/inventory.sh";
    String superuser ="su";

    //********************************************************************************************************************

    localproductadapterinventorypodialog localadapter;
    ArrayList<LocalProduct>localproductlist;
    String ActiveType[] ;
    ArrayAdapter<String> adapterActiveType;
    String SpinValue,localProd_Id_To_Update;
    AutoCompleteTextView localproductname;
    EditText productname,localbarcode,localhmrp,localsprice,localpprice,localuom,localdiscount;
    TextView productid,localmargin;
    Spinner localactive;
    //*******************************************add local vendor**************************************************
    String ActiveTypevendor[] ;
    ArrayAdapter<String> adapterActiveTypevendor;
    ArrayList<localvendor>localvendorlist;
    Withpo_localvendoradapter inventorywithpovendoradapter;
    AutoCompleteTextView localvendornametname;
    EditText vendorname,vendorcontactname,localadrress1,localcity,localzip,localtelephone;
    TextView vendorid,localcountrydescription,localmobile;
    Spinner localinventory,localvendoractive;
    String SpinValue3,SpinValue4,localVendor_Id_To_Update;
    ImageButton  addlocalvendor;



    String mrp,sprice,pprice,batchinsert;
    String purchaseholdno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        //setContentView(R.layout.activity_activity_inventory);
        //this the change done by rahul for directily visit to w
        setContentView(R.layout.activity_activity_inventorywithpo);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        addlocalproduct = (ImageButton) findViewById(R.id.addlocalproductinvpo);
        addlocalvendor = (ImageButton) findViewById(R.id.addlocalvendorinvpo);




        helper = new DBhelper(activity_inventorywithpo.this);
        Decimal value3 = helper.getStoreprice();
        mrp=value3.getDecimalmrp();
        pprice=value3.getDecimalpprice();
        sprice=value3.getDecimalsprice();
        roundofff = value3.getRoundofff();
        purchaseholdno=String.valueOf(value3.getPurchaseholdno());


        username = login.b.getString("Pos_User");
        update = (Button) findViewById(R.id.addtolist_button);
        Button clrbtn = (Button) findViewById(R.id.clear);
        Ponumbers = (TextView) findViewById(R.id.Po_numbers);
        Ponum = (TextView) findViewById(R.id.ponum);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView) findViewById(R.id.sales_billno);
        Billno.setText("012345678912345");
        Holdinventorybutton = (Button) findViewById(R.id.HoldInventorybill);
        GrandTotal = (TextView) findViewById(R.id.discount_textt10);
        Totalitems = (TextView) findViewById(R.id.discount_textt);
        VendorEditNo=(EditText)findViewById(R.id.VendorEditNo);
        VendorEditDate=(EditText)findViewById(R.id.VendorEditDate);
        VendorEditDate.setText("select Date");
        batchinsert=getSystemCurrentTime();



        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        helper = new DBhelper(this);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setThreshold(1);

        InvProductidorName = (CustomAuto) findViewById(R.id.InventoryautoProductIdandName);

        InvProductidorName.setThreshold(1);

        listView = (ListView) findViewById(R.id.listView);
        SelectPurchaseAlertDialog();
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
                arrayList = helper.getvendorsearch();
                // ArrayList<String>vendornames=helper.getvendorsearchforSpinner();
                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new Inventorywithpoadapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, arrayList);
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
                String Value = parent.getItemAtPosition(position).toString();
            }
        });

        helper = new DBhelper(this);
        ListTextWatcher = new TextWatcher() {
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
                if (InvProductidorName.isPerformingCompletion()) {
                    Log.i("&&&&&&&&", "Performing completion and hence drop down will not be shown ");
                    return;
                }
                String userTypedString = InvProductidorName.getText().toString().trim();
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
                    dropdownProductArrayList = helper.getProductdataforInventory(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if (dropdownProductArrayList.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(dropdownProductArrayList.get(0));
                        return;
                    } else if (dropdownProductArrayList.size() < 1) {
                        //no product found
                        //  Toast.makeText(activity_inventorywithpo.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    dropdownProductArrayList = helper.getProductdataforInventory(userTypedString);
                    if (dropdownProductArrayList.size() < 1) {
                        //no product found
                        dropdownProductArrayList.clear();
                        //   Toast.makeText(activity_inventorywithpo.this, "No Product found", Toast.LENGTH_SHORT).show();

                    }
                }
                if (inventoryProductDropdownAdapter == null) {
                    inventoryProductDropdownAdapter = new InventoryProductDropdownAdapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, dropdownProductArrayList);
                    inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
                    InvProductidorName.setAdapter(inventoryProductDropdownAdapter);
                    InvProductidorName.setThreshold(1);
                } else {
                    inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
                    inventoryProductDropdownAdapter.notifyDataSetChanged();
                }
            }

        };
        InvProductidorName.addTextChangedListener(ListTextWatcher);
        InvProductidorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PurchaseProductModelwithpo resval1 = (PurchaseProductModelwithpo) parent.getItemAtPosition(position);
                addProductToPurchaseList(resval1);
                productListAdapter.setBatchdata(resval1);
            }

        });

        addlocalproduct.setOnClickListener(
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



        //********************************************submit button***************************************************8
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);

                try {
                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    ArrayList<String> billno = helper.getimeino();
                    for (String str : billno) {
                        if (str.equals(invoicevalue)) {
                            ArrayList<String> imei = helper.getprefix(str);
                            Log.e("%%%%%%%%%%%%%", imei.toString());
                            x_imei = imei.toString();
                            x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                            Log.e("X1_imei is :", x1);
                        } else {
                            continue;
                        }
                    }
                    int pos = 0;
                    for (PurchaseProductModelwithpo item : productListAdapter.getList()) {
                       if (item.getProductQuantity() <= 0||item.getProductPrice()<=0 || item.getExp_Date().trim().equals("select date")  ||item.getSprice()<=0 || item.getDiscountitems()<0) {
                           //if (item.getProductQuantity() <= 0||item.getProductPrice()<=0 ) {
                         //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                           Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                           return;
                       }
                      /* else if ( item.getBatch_No()== null||item.getBatch_No().trim().equals("")||item.getBatch_No()=="batch"){

                           item.setBatch_No(getSystemCurrentTime());
                       }*/
                       else if (item.getMrp()<item.getProductPrice()) {
                           Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                       }else if (item.getMrp()<item.getSprice()) {
                           Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                       }
                        pos++;
                    }
                    helper.saveInventorywithpo(productListAdapter.getList(), autoCompleteTextView.getText().toString(), Ponumbers.getText().toString(), x1, username,VendorEditNo.getText().toString(),VendorEditDate.getText().toString());
                    helper.saveGranddataintoGrnMaster(x1, Ponumbers.getText().toString(), autoCompleteTextView.getText().toString(), GrandTotal.getText().toString(), username);
                    helper.SavePdfDetailForInventorywithpo(x1, autoCompleteTextView.getText().toString(), username);
                    //  helper.updatebatchnowithpo(productListAdapter.getList());
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();
                    UpdateIventoryWithPO();
                    //UpdateShell();
                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });
//********************************************hiddon button***************************************************8

        Holdinventorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                helper = new DBhelper(activity_inventorywithpo.this);

                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (productListAdapter.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();

                    return;
                }
                try {
                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    ArrayList<String> billno = helper.getimeino();
                    for (String str : billno) {
                        if (str.equals(invoicevalue)) {
                            ArrayList<String> imei = helper.getprefix(str);
                            Log.e("%%%%%%%%%%%%%", imei.toString());
                            x_imei = imei.toString();
                            x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                            Log.e("X1_imei is :", x1);
                        } else {
                            continue;
                        }
                    }
                    int pos = 0;

                    for (PurchaseProductModelwithpo item : productListAdapter.getList()) {
                       if (item.getProductQuantity() <= 0||item.getProductPrice()<=0 ||item.getExp_Date().trim().equals("select date")||item.getSprice()<=0 || item.getDiscountitems()<0) {
                           //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }
                      /* else if ( item.getBatch_No()== null||item.getBatch_No().trim().equals("")||item.getBatch_No()=="batch"){

                           item.setBatch_No(getSystemCurrentTime());
                       }*/
                        else if (item.getMrp()<item.getProductPrice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                        }else if (item.getMrp()<item.getSprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }

                    helper.saveInventoryholdbillwithpo(productListAdapter.getList(), autoCompleteTextView.getText().toString(), Ponumbers.getText().toString(), x1,VendorEditNo.getText().toString(),VendorEditDate.getText().toString());
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();

                    Intent syncIntent = new Intent(getApplicationContext(), com.RSPL.POS.Activitypurchase.class);
                    startActivity(syncIntent);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });

        //********************************************cencel button***************************************************8
        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                try {
                    clearbuttondialog();
/*
                    productListAdapter.clearAllRows();
                    autoCompleteTextView.setText("");
                    Ponumbers.setText("");
                    // Ponumbers.setAdapter(null);
                    setSummaryRow();*/
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
        VendorEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity_inventorywithpo.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub

                    validateDate(year, month + 1, day);
                    int Mymonth=1+month;
                    VendorEditDate.setText(year + "/" + Mymonth + "/" + day);


                }
            };
    public void validateDate(int year,int month,int day) {
        Date Todaydate=null,edate=null;

        String enddate=year+"/"+month+"/"+day;
        Log.e("########end", "----------->" + enddate);
        String todaysdate=Year+"/"+Month+"/"+Day;

        //    String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########today date", "----------->" + todaysdate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {

            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();


        }
       /* if(edate.before(Todaydate)){
            //   startDatepicker.dismiss();
            Toast.makeText(this,"Invalid date !!", Toast.LENGTH_SHORT).show();
            //  list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+month +"/"+day);
            //  startDatepicker.show(getSupportFragmentManager(), "showDate");
            //return;
            return ;


        }
        else{
            Log.e("########", "----------->" + todaysdate);
            Log.e("########", "----------->" + edate);

            //   startDatePicked=true;
            //  if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
            int Mymonth=month;
            // list.get(mTempPositionBeforeCalenderDialog).setExpdate(year+"/"+Mymonth +"/"+day);
            // notifyDataSetChanged();
            //mTempPositionBeforeCalenderDialog = -1;
            // holder.ExpDate.setText(day + "/" + month + "/" + year);

        }*/
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

                    productListAdapter.clearAllRows();
                    autoCompleteTextView.setText("");
                    Ponumbers.setText("");
                    // Ponumbers.setAdapter(null);
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


    private void addProductToPurchaseList(PurchaseProductModelwithpo resval1) {
        if (productListAdapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, resval1);
            listView.setAdapter(productListAdapter);
        }
        int pos = productListAdapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        productListAdapter.notifyDataSetChanged();
        InvProductidorName.setText("");
        setSummaryRow();
        inventoryProductDropdownAdapter.setList(dropdownProductArrayList);
        dropdownProductArrayList.clear();
        listView.smoothScrollToPosition(pos);
    }

    private void SearchlocalvendorDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localvendor_dialog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(activity_inventorywithpo.this);
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
                    if (inventorywithpovendoradapter == null) {
                        inventorywithpovendoradapter = new Withpo_localvendoradapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                        inventorywithpovendoradapter.setLocalVendorList(localvendorlist);
                        localvendornametname.setAdapter(inventorywithpovendoradapter);
                        localvendornametname.setThreshold(1);
                    } else {
                        localvendornametname.setAdapter(inventorywithpovendoradapter);

                        inventorywithpovendoradapter.setLocalVendorList(localvendorlist);
                        inventorywithpovendoradapter.notifyDataSetChanged();
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

                final DBhelper mydb = new DBhelper(activity_inventorywithpo.this);

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
                    //  UpdateShell();
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





    private void SearchlocalproductDialod() {

        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localproduct_dialog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(activity_inventorywithpo.this);
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
                        localadapter = new localproductadapterinventorypodialog(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
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

                final DBhelper mydb = new DBhelper(activity_inventorywithpo.this);

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





    //******************************************alertbox***********************************************************
    public void SelectPurchaseAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.inventory_alert_dialog, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button po = (Button) alertLayout.findViewById(R.id.po);
        final Button withoutpo = (Button) alertLayout.findViewById(R.id.withpo);
        final Button cancel = (Button) alertLayout.findViewById(R.id.cancelinventoryalert);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Spinner spinner = (Spinner) alertLayout.findViewById(R.id.VendororDistributorName);
        final Button Hold = (Button) alertLayout.findViewById(R.id.HoldButton);
        final Button SubmitForHold = (Button) alertLayout.findViewById(R.id.submitbtnForHold);
        final Spinner Holddata = (Spinner) alertLayout.findViewById(R.id.HoldPurchaseNoforinventory);
        final Spinner Last3InvoiceNo = (Spinner) alertLayout.findViewById(R.id.Last3InvoiceNo);
        final Button integrationwithdistributor = (Button) alertLayout.findViewById(R.id.integrationwithdistibutor);
        final Button submitfordistributor = (Button) alertLayout.findViewById(R.id.submitbtnforintegration);
        final Spinner distributorno = (Spinner) alertLayout.findViewById(R.id.Integrationdistributorno);
        final Spinner documentno = (Spinner) alertLayout.findViewById(R.id.Integrationdocumentno);


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        Last3InvoiceNo.setVisibility(View.GONE);
        submit.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);
        cancel.setVisibility(View.VISIBLE);
        Hold.setVisibility(View.VISIBLE);
        Holddata.setVisibility(View.GONE);
        SubmitForHold.setVisibility(View.GONE);
        po.setVisibility(View.GONE);

        distributorno.setVisibility(View.GONE);
        documentno.setVisibility(View.GONE);
        submitfordistributor.setVisibility(View.GONE);
        alert.setView(alertLayout);
        alert.setTitle("SELECT INVENTORY OPTION");
        alert.setCancelable(true);
        dialog = alert.create();


        po.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                withoutpo.setVisibility(View.GONE);
                integrationwithdistributor.setVisibility(View.GONE);
                Last3InvoiceNo.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                distributorno.setVisibility(View.GONE);
                documentno.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);
                submitfordistributor.setVisibility(View.GONE);
                Holddata.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(intent);
            }
        });

        withoutpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                Intent intent = new Intent(getApplicationContext(), activity_inventory.class);
                startActivity(intent);

            }
        });
        Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Holddata.setVisibility(View.VISIBLE);
                Last3InvoiceNo.setVisibility(View.GONE);
                po.setVisibility(View.GONE);
                withoutpo.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.VISIBLE);
                distributorno.setVisibility(View.GONE);
                documentno.setVisibility(View.GONE);
                integrationwithdistributor.setVisibility(View.GONE);
                submitfordistributor.setVisibility(View.GONE);


            }
        });

        integrationwithdistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Holddata.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);
                Last3InvoiceNo.setVisibility(View.GONE);
                po.setVisibility(View.GONE);
                withoutpo.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);
                distributorno.setVisibility(View.VISIBLE);
                documentno.setVisibility(View.VISIBLE);
                integrationwithdistributor.setVisibility(View.VISIBLE);
                submitfordistributor.setVisibility(View.VISIBLE);

            }
        });


        submitfordistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                Ponumbers.setVisibility(View.GONE);
                InvProductidorName.setVisibility(View.GONE);
                Ponum.setVisibility(View.GONE);

                if (GetDistributorNumber == null) {
                    Toast.makeText(getApplicationContext(), "No Distributor Number Item Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                documentNumberForIntegration = helper.getdataforintegration(GetDistributorNumber);
                if (documentNumberForIntegration != null && documentNumberForIntegration.size() > 0) {
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    for (PurchaseProductModelwithpo prod : documentNumberForIntegration) {
                        productListAdapter.addProductToList(prod);
                        // Ponumbers.findViewById(R.id.Po_numbers);
                    }
                    helper.Updateflag(GetDistributorNumber);

                    autoCompleteTextView.setText(documentNumberForIntegration.get(0).getVendorName());
                    // Ponumbers.setText(HoldInventoryList.get(0).getPurchaseno());
                    //   autoCompleteTextView.setText("" + Holddata.getSelectedItem().toString() + "");

                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + documentNumberForIntegration.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();

                }

            }
        });
        arrayList = helper.getdocumentname();
        ArrayAdapter<PurchaseProductModelwithpo> documentadapter =
                new ArrayAdapter<PurchaseProductModelwithpo>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        distributorno.setAdapter(documentadapter);
        distributorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String distributorrvalue = distributorno.getSelectedItem().toString();

                ArrayList<Inventorygrnmodel> LastInvoices = helper.getgrnNumberForintegration(distributorrvalue);


                ArrayAdapter<Inventorygrnmodel> documentAdapter =
                        new ArrayAdapter<Inventorygrnmodel>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
                documentno.setAdapter(documentAdapter);
                documentno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        GetDistributorNumber = documentno.getSelectedItem().toString();
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

//********************************************************po****************************************************************8
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
try{
                Spinnervalue = spinner.getSelectedItem().toString();
                userTypedInvoiceno = Last3InvoiceNo.getSelectedItem().toString().trim();
                Log.e("!!!!!!!!!!!", "" + Last3InvoiceNo);
                if (userTypedInvoiceno.equals("")) {
                    return;
                }

                ArrayList<PurchaseProductModelwithpo> alldata = helper.getPurchaseProductdata(userTypedInvoiceno);
                //   PurchaseProductModelwithpo resval1 = (PurchaseProductModelwithpo) parent.getItemAtPosition(position);
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, null);
                listView.setAdapter(productListAdapter);

                for (PurchaseProductModelwithpo prod : alldata) {
                    productListAdapter.addProductToList(prod);
                    productListAdapter.setBatchdata(prod);
                }
                productListAdapter.notifyDataSetChanged();
                //autoCompleteTextView.setText("");
                setSummaryRow();
                autoCompleteTextView.setText("" + spinner.getSelectedItem().toString() + "");
                autoCompleteTextView.removeTextChangedListener(mTextWatcher);

                Ponumbers.setText(userTypedInvoiceno);
                //  Log.i("&&&&&&&&!", "Adding " + OldPurchaseArraylist.get(0) + " to Product List..");
                productListAdapter.notifyDataSetChanged();
                setSummaryRow();
                autoCompleteTextView.setEnabled(false);
                dialog.dismiss();


            }catch (Exception e){
    e.printStackTrace();
            }

            }
        });
        arrayList = helper.getvendorsearch();
        ArrayAdapter<PurchaseProductModelwithpo> stringArrayAdapter =
                new ArrayAdapter<PurchaseProductModelwithpo>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(stringArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = spinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);
                ArrayList<String> LastInvoices = helper.getPo_numbers(Spinnervalue,Integer.parseInt(purchaseholdno));
                ArrayAdapter<String> InvoiceNoAdapter =
                        new ArrayAdapter<String>(activity_inventorywithpo.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
                Last3InvoiceNo.setAdapter(InvoiceNoAdapter);
                Last3InvoiceNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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


//****************************************************hold******************************************************************
        SubmitForHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HoldInventoryorderNo == null) {
                    Toast.makeText(getApplicationContext(), "No Hold Item Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                HoldInventoryList = helper.getholddataforinventory(HoldInventoryorderNo);
                if (HoldInventoryList != null && HoldInventoryList.size() > 0) {
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new PurchaseproductlistwithpoAdapter(activity_inventorywithpo.this, new ArrayList<PurchaseProductModelwithpo>(), android.R.layout.simple_dropdown_item_1line, null);
                        listView.setAdapter(productListAdapter);
                    }
                    for (PurchaseProductModelwithpo prod : HoldInventoryList) {
                        productListAdapter.addProductToList(prod);
                        // Ponumbers.findViewById(R.id.Po_numbers);
                    }

                    autoCompleteTextView.setText(HoldInventoryList.get(0).getVendorName());
                    // Ponumbers.setText(HoldInventoryList.get(0).getPurchaseno());
                    //   autoCompleteTextView.setText("" + Holddata.getSelectedItem().toString() + "");
                    helper.Updateflag(HoldInventoryorderNo);
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + HoldInventoryList.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();

                }

            }
        });

        ArrayList<String> LastInvoices = helper.getgrnNumberForinventory();
       // grnnumberlistadapter = new Inventorygrnadapter(activity_inventorywithpo.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
        Holddata.setAdapter(grnnumberlistadapter);
        Holddata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Inventorygrnmodel resval1 = (Inventorygrnmodel) parent.getItemAtPosition(position);
                HoldInventoryorderNo = resval1.getInventoryOrderNo();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }

    public   String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }

    //*************************************************************************************************************************8
    public void setSummaryRow() {
        if (roundofff.toString().matches("Y")) {
            DecimalFormat f2 = new DecimalFormat("##");
            float Getval = productListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f2.format(Getval).concat(".00");
            GrandTotal.setText(GrandVal);

            int Getitem = productListAdapter.getCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);
        }else{

            DecimalFormat f = new DecimalFormat("##.00");
            float Getval = productListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f.format(Getval);
            GrandTotal.setText(GrandVal);

            int Getitem = productListAdapter.getCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);

        }
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


    public  void InventoryHelpDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  INVENTORY WITH PO    ");
        alertDialog.setMessage("Inventory refers to the goods stocked at the store for selling. The following are the four options available for the entering the stocks.\n" +
                " \n" +
                "Objective:\n" +
                "\n" +
                "Option:\n" +
                "\n" +
                "Option 1: PO NO with Purchase order already raised to the vendor.\n" +
                "\n" +
                "Option 2: Without PO NO means the retailer is doing the inventory for stocks that has arrived at the store.\n" +
                "\n" +
                "\n" +
                "Option 3: Hold means continuing the stocks intake after a period of time. The “Hold” inventory stocks would not be available for sale till inventory is completed. Also all stocks cannot be “Hold” more than a day and will be deleted automatically.\n" +
                "\n" +
                "Option 4: Integration with Distributors – The system can be integrated with the distributors where inventory once send by the distributor will automatically be available. The user will only have to verify the inventory before confirming the same.\n" +
                "\n" +
                "Cancel:User will go back to the Home Page \uF0E0 Purchasing \uF0E0 Inventory.\n" +
                "\n" +
                "\n" +
                "Option 1:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The user selects the vendor and selects the purchase order number. The user can select upto the last 7 purchase orders for inventory for a vendor.\n" +
                "\n" +
                "Option:\n" +
                "Submit\n" +
                "Cancel\n" +
                "\n" +
                "\n" +
                "Option 2:\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The user selects “Without PO Number” if user doesn’t have PO number of the products.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Vendor Name: User selects the vendor name.\n" +
                "Distributors Name: User select the distributor name.\n" +
                "\n" +
                "Option:\n" +
                "Submit\n" +
                "Hold\n" +
                "Clear\n" +
                "\n" +
                "\n" +
                "\n" +
                "Option 3\n" +
                "\n" +
                "Objective: \n" +
                "\n" +
                "The use selects the inventory on hold by selecting the \n" +
                "Hold option with the inventory document number. \n" +
                "\n" +
                "Option:\n" +
                "Submit\n" +
                "Cancel\n" +
                "\n" +
                "\n" +

                "Option 4\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The store has an inventory integrated with the distributors. Hence when the goods arrive at the store seeing the “Inventory receipt number” enters the same to get the inventory proposed for intake to the store. \n" +
                "\n" +
                "\n" +
                "Inventory \n" +
                "\n" +
                "The user can add any new product by manual entering the product name or scanning the bar code.\n" +
                "The product when added by default adds up the product, quantity = 1, MRP, Purchase price and Selling price maintained in the product master.\n" +
                "The user can enter the Batch number and Expiry Date. \n" +
                "If the batch number and expiry date with respect to product id is same, the quantity is updated else a new batch number and expiry date gets updated in the stock master.\n" +
                "Note: Though the inventory is taken in packs, the inventory gets updated with the packs multiplied by conversion factor in the smallest unit possible.\n" +
                "When the user selects the “Submit” option the stocks is updated in the system.\n" +
                "When the user selects the “Hold” option, the inventory is on hold and the user can select using “Option 2” and continue to do inventory at a later time.\n" +
                "When the user selects the “Clear” option the inventory items which are entered can be cleared off to restart the process based on the confirmation.\n" +
                "When the user selects the “Exit” option the user can go back to the “Home Page”");

        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
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


                Intent i = new Intent(activity_inventorywithpo.this, Activitypurchase.class);
                startActivity(i);
                return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(activity_inventorywithpo.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                InventoryHelpDialog();
                return true;
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(activity_inventorywithpo.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/


            case R.id.action_settinginv:
                Intent in=new Intent(activity_inventorywithpo.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(activity_inventorywithpo.this,ActivitySalesbill.class);
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
//********************************************************8Sync code*******************************************************8
public void updateLocalProduct() {


    final DBhelper mydb = new DBhelper(activity_inventorywithpo.this);

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


    PersistenceManager.saveStoreId(activity_inventorywithpo.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
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
               // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/retail_store_prod_local.jsp",hashMap);
                JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_PRODUCT,hashMap);
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



//****************************************************************************************************************************8

    public void updateLocalVendor() {

        final DBhelper  mydb=new DBhelper(activity_inventorywithpo.this);

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
        PersistenceManager.saveStoreId(activity_inventorywithpo.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String  store_id= PersistenceManager.getStoreId(activity_inventorywithpo.this);

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
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_VENDOR,hashMap);

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



    //*************************************************************************************************************************
    public void UpdateIventoryWithPO() {

        final String inventoryGRNvalue = x1;
        final String inventoryvendorname = autoCompleteTextView.getText().toString();
        final String inventorygrandtotal = GrandTotal.getText().toString();
        final String inventoryvendoreditno = VendorEditNo.getText().toString();
        final String inventoryvendoreditdate = VendorEditDate.getText().toString();



        for (PurchaseProductModelwithpo Inventorywithpo : productListAdapter.getList()) {
            final String inventorybatchno = String.valueOf(Inventorywithpo.getBatch_No());
            final String inventoryexpdate = String.valueOf(Inventorywithpo.getExp_Date());
            final String inventoryproductid = Inventorywithpo.getProductId();
            final String inventoryproductname = Inventorywithpo.getProductName();
            final String inventoryproductmrp = String.valueOf(Inventorywithpo.getMrp());
            final String inventorypono = Ponumbers.getText().toString();
            final String inventoryuom = Inventorywithpo.getUom();
            final String inventoryproductprice = String.valueOf(Inventorywithpo.getProductPrice());
            final String inventorysellingprice = String.valueOf(Inventorywithpo.getSprice());
            final String inventoryconvfact = String.valueOf(Inventorywithpo.getConversion());
            final String inventoryprofitmargin = String.valueOf(Inventorywithpo.getProductmargin());
            final String inventoryfreegoods = String.valueOf(Inventorywithpo.getDiscountitems());
            final String Inventoryconvmulqty = String.valueOf(Inventorywithpo.getTotalqty());
            final String inventoryQuantity = String.valueOf(Inventorywithpo.getProductQuantity());
            final String purchasetotal = String.valueOf(Inventorywithpo.getTotal());
            final String batchtime = batchinsert.toString();
            final String date3 = getDate();





            PersistenceManager.saveStoreId(activity_inventorywithpo.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));

            store_id = PersistenceManager.getStoreId(activity_inventorywithpo.this);
            universal_id = PersistenceManager.getStoreId(activity_inventorywithpo.this);


            class UpdateIventoryWithPO extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try {

                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.Retail_inventorypo_store_id, store_id);
                        hashMap.put(Config.Retail_Inventorypo_grnid, inventoryGRNvalue);
                        hashMap.put(Config.Retail_Inventory_mfg_batchno, inventorybatchno);
                        hashMap.put(Config.Retail_Inventorypo_batchno, batchtime);
                        hashMap.put(Config.Retail_Inventory_vendoreditno, inventoryvendoreditno);
                        hashMap.put(Config.Retail_Inventory_vendoreditdate, inventoryvendoreditdate);
                        hashMap.put(Config.Retail_Inventorypo_expdate, inventoryexpdate);
                        hashMap.put(Config.Retail_Inventorypo_id, inventoryproductid);
                        hashMap.put(Config.Retail_Inventorypo_name, inventoryproductname);
                        hashMap.put(Config.Retail_Inventorypo_p_price, inventoryproductprice);
                        hashMap.put(Config.Retail_Inventorypo_po_no, inventorypono);
                        hashMap.put(Config.Retail_Inventorypo_barcode, "NA");
                        hashMap.put(Config.Retail_Inventorypo_mrp, inventoryproductmrp);
                        hashMap.put(Config.Retail_Inventorypo_profit_margin, "12.23");
                        hashMap.put(Config.Retail_Inventorypo_conv_fact, inventoryconvfact);
                        hashMap.put(Config.Retail_Inventorypo_selling, inventorysellingprice);
                        hashMap.put(Config.Retail_Inventorypo_Vendorname, inventoryvendorname);
                        hashMap.put(Config.Retail_Inventorypo_uom, inventoryuom);
                        hashMap.put(Config.Retail_Inventorypo_Quantity, inventoryQuantity);
                        hashMap.put(Config.Retail_Inventorypo_Total, purchasetotal);
                        hashMap.put(Config.Retail_Inventorypo_freegoods, inventoryfreegoods);
                        hashMap.put(Config.Retail_Inventorypo_conmulqty, Inventoryconvmulqty);
                        hashMap.put(Config.Retail_Inventorypo_GrandTotal, inventorygrandtotal);
                        hashMap.put(Config.Retail_Inventorypo_Universal_id, universal_id);
                        hashMap.put(Config.Retail_Pos_user, username);
                        hashMap.put(Config.Retail_Inventory_Inventory_Date,date3);


                       // JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/inv.jsp", hashMap);
                        JSONObject s = rh.sendPostRequest(Config.UPDATE_INVENTORY_WITH_PO, hashMap);

                        //    checking  log for json response
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            helper.updateinventoryflagstockmaster(x1);

                            helper.updateflagSavePdfDetailForInventorywithpo(x1);

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
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);

                    Toast.makeText(activity_inventorywithpo.this, s, Toast.LENGTH_LONG).show();
                }

            }
            UpdateIventoryWithPO updateinvwithpo = new UpdateIventoryWithPO();
            updateinvwithpo.execute();
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
                    String s = executerInventory(retail_inventory_op);
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
            outputStream.writeBytes("sh /sdcard/inventory.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }



}


