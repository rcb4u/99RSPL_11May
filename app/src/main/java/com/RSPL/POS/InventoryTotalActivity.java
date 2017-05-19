package com.RSPL.POS;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ngx.USBPrinter;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Adapter.InventoryDropDownProductNmAdapter;
import Adapter.InventoryProductListAdapter;
import Adapter.InventoryTotalLocalVendoradapter;
import Adapter.InventoryVendorNmAdapter;
import Adapter.Inventoryadapter;
import Adapter.InventorygrnnoAdapter;
import Adapter.localproductadapterinventorytotal;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryTotalProductModel;
import Pojo.Inventorygrnmodel;
import Pojo.Inventorymodel;
import Pojo.LocalProduct;
import Pojo.Registeremployeesmodel;
import Pojo.Visibility;
import Pojo.localvendor;

public class InventoryTotalActivity extends FragmentActivity {
    ActionBar actionBar;

    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;
    public USBPrinter UsbPrinter = USBPrinter.INSTANCE;
    TextPaint tp = new TextPaint();
    TextPaint tp32 = new TextPaint();
    TextPaint tp36 = new TextPaint();
    Typeface tff;
    DBhelper db = new DBhelper(this);
    ArrayList<String> store_name;


    String iteam;
    TextView user2;

    String store, storeAddress, City, Storenumber, Str, AlternateNo, Footer, amountdiscount;

    private String eol = "\n";

    private static final int PRINT_LEFT_MARGIN = 2;

    private static final int BILL_DATE_COLUMN = 14;
    private static int Billnumber;
    public String extraspace = "  ";

    ////////////////////////////AutoCompleteTextView/////////////////////////////////
    AutoCompleteTextView autoCompleteTextView;
    AutoCompleteTextView ProductidorName;

    ListView listView;

    RecyclerView RecycleView;
    ///////////////////////////TextWatcher//////////////////////////////////////////
    private TextWatcher mTextWatcher;
    private TextWatcher pTextWatcher;
    ////////////////////////////Public String ,Int ////////////////////////////////
    public static final int MIN_LENGTH_OF_BARCODE_STRING = 13;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    public static final String BARCODE_STRING_PREFIX = "@";
    String Store_Id, store_id, universal_id;
    String x_imei, x1, user;
    public static String username = null;
    String mrp, sprice, pprice;
    ////////////////////////EditText///////////////////////////////////////////////
    EditText quantity, VendorEditNo, VendorEditDate;

    ////////////////////////TextView///////////////////////////////////////////////
    private TextView GrandTotal, Billno;
    private TextView Totalitems;
    private TextView LineDiscountamt;
    private TextView LineDiscountTotal;
    //////////////////////////Button,,,,,,,//////////////////////////////////////////
    Button update, Holdinventorybutton, AddAllProduct;
    ImageButton addlocalprod;
    ///////////////////////////////DBhelper/////////////////////////////////////////////
    DBhelper helper;

    /////////////////////////////////////ArrayList////////////////////////////////////
    ArrayList<Inventorymodel> GetAllVendorNameList;
    ArrayList<InventoryTotalProductModel> GetAllProductNameDropDownList;
    ////////////////////////////////Adapter //////////////////////////////
    Inventoryadapter adapter;
    InventoryDropDownProductNmAdapter inventoryDropDownProductNmAdapter;
    InventoryProductListAdapter inventoryFullProductListAdapter;
    InventoryVendorNmAdapter inventoryVendorNmAdapter;

    //////////////////////////////////Global things   ////////////////////////////////

    Calendar myCalendar = Calendar.getInstance();
    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH) + 1;
    int Day = c.get(Calendar.DAY_OF_MONTH);
    ////////////////////////////////////////////////////////////////////////////
    TelephonyManager tel;
    String VendorName;
    //******************************************************addlocal product**************************************************************

    localproductadapterinventorytotal localadapter;
    ArrayList<LocalProduct> localproductlist;
    String ActiveType[];
    ArrayAdapter<String> adapterActiveType;
    String SpinValue, localProd_Id_To_Update, localVendor_Id_To_Update;
    AutoCompleteTextView localproductname;
    EditText productname, localbarcode, localhmrp, localsprice, localpprice, localuom, localdiscount;
    TextView productid, localmargin;
    Spinner localactive;
    //*******************************************add local vendor**************************************************
    String ActiveTypevendor[];
    ArrayAdapter<String> adapterActiveTypevendor;
    ArrayList<localvendor> localvendorlist;
    InventoryTotalLocalVendoradapter inventoryvendoradapter;
    AutoCompleteTextView localvendornametname;
    EditText vendorname, vendorcontactname, localadrress1, localcity, localzip, localtelephone;
    TextView vendorid, localcountrydescription, localmobile;
    Spinner localinventory, localvendoractive;
    String SpinValue3, SpinValue4, roundofff;
    ImageButton addlocalvendor;

    /////////////////////////////////////////////////////////////////////////////////////////
    //for inventory alert dialog
    AlertDialog dialog;
    TextView Ponumbers;
    String Spinnervalue, HoldInventoryorderNo, document;
    String GetDistributorNumber;
    String userTypedInvoiceno;
    String purchaseholdno;
    ArrayList<InventoryTotalProductModel> documentNumberForIntegration;
    ArrayList<InventoryTotalProductModel> arrayList;
    InventorygrnnoAdapter grnnumberlistadapter;
/////////////////////////////////////visibilty code///////////////////////////////////////
    TextView marginvisible,freeqty;
    String marginvisibilty,invbillprint,freeqtyvisible;

////////////////////////////////////////hold//////////////////////////


    Spinner holdbill;
    ArrayList<String> holdbilltransaction;
    ArrayAdapter<String> holdbilladapter;
    String holdspinneritem;
    ArrayList<InventoryTotalProductModel> HoldInventoryList;

    //////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////jimmmy(forsize)///////////////////////////////////////////////////////
    TextView tvproductname,tvbatch,tvexp,tvmrp,tvpprice,tvsprice,tvmargin,tvqty,tvdisc,tvfreeqty,tvtotalqty,tvuom,tvtotal;
    TextView tvtotalitems,tvinvvalue,tvvenderinvoiceno,tvvendorinvoicedate;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_total);

        actionBar = getActionBar();
     //   actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        tff = Typeface.createFromAsset(InventoryTotalActivity.this.getAssets(), "Fonts/DroidSansMono.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        holdbill = (Spinner) findViewById(R.id.hold_inventory_by_total);

        username = login.b.getString("Pos_User");

        freeqty = (TextView) findViewById(R.id.F_QTY);

        helper = new DBhelper(InventoryTotalActivity.this);
        Decimal value3 = helper.getStoreprice();
        mrp = value3.getDecimalmrp();
        pprice = value3.getDecimalpprice();
        sprice = value3.getDecimalsprice();
        roundofff = value3.getRoundofff();
        String textsize=         value3.getHoldpo();
        backroundcolour=  value3.getColorbackround();




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




        Visibility value = helper.getStorevisibility();
        marginvisibilty = value.getMarginvisiblty();
        invbillprint=value.getInvbillprints();
        freeqtyvisible=value.getFreequantity();


        addlocalprod = (ImageButton) findViewById(R.id.addlocalproductinv);
        addlocalprod.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        addlocalvendor = (ImageButton) findViewById(R.id.addlocalvendorinv);
        addlocalvendor.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));


///////////
        tvproductname = (TextView) findViewById(R.id.product1);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) findViewById(R.id.product10);
        tvbatch.setTextSize(Float.parseFloat(textsize));
        tvexp = (TextView) findViewById(R.id.product11);
        tvexp.setTextSize(Float.parseFloat(textsize));
        tvmrp = (TextView) findViewById(R.id.productmrp);
        tvmrp.setTextSize(Float.parseFloat(textsize));
        tvpprice = (TextView) findViewById(R.id.product8);
        tvpprice.setTextSize(Float.parseFloat(textsize));
        tvsprice = (TextView) findViewById(R.id.productsprice);
        tvsprice.setTextSize(Float.parseFloat(textsize));
        tvmargin = (TextView) findViewById(R.id.visibiltymargin);
        tvmargin.setTextSize(Float.parseFloat(textsize));
        tvqty = (TextView) findViewById(R.id.product12);
        tvqty.setTextSize(Float.parseFloat(textsize));
        tvdisc = (TextView) findViewById(R.id.discount);
        tvdisc.setTextSize(Float.parseFloat(textsize));
        tvfreeqty = (TextView) findViewById(R.id.F_QTY);
        tvfreeqty.setTextSize(Float.parseFloat(textsize));
        tvtotalqty = (TextView) findViewById(R.id.total_QTY);
        tvtotalqty.setTextSize(Float.parseFloat(textsize));
        tvuom = (TextView) findViewById(R.id.product9);
        tvuom.setTextSize(Float.parseFloat(textsize));
        tvtotal = (TextView) findViewById(R.id.totalvalue);
        tvtotal.setTextSize(Float.parseFloat(textsize));
        tvtotalitems = (TextView) findViewById(R.id.totalitem_tv);
        tvtotalitems.setTextSize(Float.parseFloat(textsize));
        tvinvvalue = (TextView) findViewById(R.id.discount_text9);
        tvinvvalue.setTextSize(Float.parseFloat(textsize));
        tvvenderinvoiceno = (TextView) findViewById(R.id.TV_VendorEditNo);
        tvvenderinvoiceno.setTextSize(Float.parseFloat(textsize));
        tvvendorinvoicedate = (TextView) findViewById(R.id.TV_VendorEditDate);
        tvvendorinvoicedate.setTextSize(Float.parseFloat(textsize));

/////////////////////////////////////////////////////////////////////////////////////////////////////////////


        update = (Button) findViewById(R.id.addtolist_button);
        GrandTotal = (TextView) findViewById(R.id.discount_textt10);
        GrandTotal.setTextSize(Float.parseFloat(textsize));

        Totalitems = (TextView) findViewById(R.id.discount_textt);
        Totalitems.setTextSize(Float.parseFloat(textsize));

        Button clrbtn = (Button) findViewById(R.id.clearinventory);
        Holdinventorybutton = (Button) findViewById(R.id.HoldInventorybillwithoutpo);
        tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Billno = (TextView) findViewById(R.id.sales_billno);


        marginvisible = (TextView) findViewById(R.id.visibiltymargin);


        try {
            if (marginvisibilty.matches("N")) {
                marginvisible.setVisibility(View.INVISIBLE);
            } else {

                marginvisible.setVisibility(View.VISIBLE);

            }

            if (freeqtyvisible.matches("Y")) {
                freeqty.setVisibility(View.VISIBLE);
            } else {

                freeqty.setVisibility(View.INVISIBLE);

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Billno.setText("012345678912345");
        SelectPurchaseAlertDialog();

        VendorEditNo = (EditText) findViewById(R.id.VendorEditNo);
        VendorEditNo.setTextSize(Float.parseFloat(textsize));

        VendorEditDate = (EditText) findViewById(R.id.VendorEditDate);
        VendorEditDate.setTextSize(Float.parseFloat(textsize));

        VendorEditDate.setText("select Date");


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);

        helper = new DBhelper(this);

        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);

        ProductidorName = (CustomAuto) findViewById(R.id.autoProductIdandNameforWithout);
        ProductidorName.setThreshold(1);

        RecycleView = (RecyclerView) findViewById(R.id.listViewTotal);
        LinearLayoutManager llm = new LinearLayoutManager(InventoryTotalActivity.this);
        RecycleView.setLayoutManager(llm);
        RecycleView.setHasFixedSize(true);
        RecycleView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        RecycleView.setItemAnimator(new DefaultItemAnimator());
        AddAllProduct = (Button) findViewById(R.id.AddAllProduct);

        autoCompleteTextView.setThreshold(3);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called " + s);
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();



                if (userTypedString.equals("")) {
                    return;
                }
                GetAllVendorNameList = helper.getInventoryName(userTypedString);

                Log.d("Debuging", "Performing completion " + GetAllVendorNameList);
                if (GetAllVendorNameList != null) {
                    if (inventoryVendorNmAdapter == null) {
                        inventoryVendorNmAdapter = new InventoryVendorNmAdapter(InventoryTotalActivity.this, android.R.layout.simple_dropdown_item_1line, GetAllVendorNameList);
                        inventoryVendorNmAdapter.setList(GetAllVendorNameList);

                        autoCompleteTextView.setAdapter(inventoryVendorNmAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        inventoryVendorNmAdapter.setList(GetAllVendorNameList);
                        inventoryVendorNmAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VendorName = parent.getItemAtPosition(position).toString();
            }
        });
        AddAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (autoCompleteTextView.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please select the vendor Or Distributor ", Toast.LENGTH_SHORT).show();
                    return;
                }

                GetAllProductNameDropDownList = helper.getProductdataforwithoutInventoryforDistriTotal(VendorName);
                for (int i = 0; i < GetAllProductNameDropDownList.size(); i++) {

                    for (int j = i + 1; j < GetAllProductNameDropDownList.size(); j++) {

                        if (GetAllProductNameDropDownList.get(i).getProductName().compareTo(GetAllProductNameDropDownList.get(j).getProductName()) < 0) {

                            InventoryTotalProductModel temp = GetAllProductNameDropDownList.get(j);
                            GetAllProductNameDropDownList.set(j, GetAllProductNameDropDownList.get(i));
                            GetAllProductNameDropDownList.set(i, temp);

                        }
                    }
                    //Collections.reverse(dropdownProductArrayList);
                    if (inventoryFullProductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");

                        inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, new ArrayList<InventoryTotalProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        RecycleView.setAdapter(inventoryFullProductListAdapter);
                    }
                    inventoryFullProductListAdapter.setList(GetAllProductNameDropDownList);
                    inventoryFullProductListAdapter.notifyDataSetChanged();
                    setSummaryRow();

                }

                // Toast.makeText(getApplicationContext(), "selected store " + Value, Toast.LENGTH_SHORT).show();

            }
        });

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

                String  x= "X-";
                if (userTypedString.startsWith("0")||userTypedString.startsWith("1")||
                        userTypedString.startsWith("3")||userTypedString.startsWith("4")||
                        userTypedString.startsWith("5")||userTypedString.startsWith("6")||
                        userTypedString.startsWith("7")||userTypedString.startsWith("8")||
                        userTypedString.startsWith("9")) {

                    userTypedString = x.concat(ProductidorName.getText().toString().trim());
                }
                if (userTypedString.length() < 1) {
                    return;
                }

                if (userTypedString.startsWith(BARCODE_STRING_PREFIX)) {
                    if (GetAllProductNameDropDownList != null) {
                        GetAllProductNameDropDownList.clear();
                    }
                    //this is a barcode generated string
                    if (userTypedString.length() <= MIN_LENGTH_OF_BARCODE_STRING) {
                        //ignore all strings of length < 13
                        return;
                    }
                    GetAllProductNameDropDownList = helper.getProductdataforwithoutInventoryTotal(userTypedString.substring(1));
                    //dropdownProductArrayList = helper.getProductdata(userTypedString);
                    if (GetAllProductNameDropDownList.size() == 1) {
                        //we have found the product
                        addProductToPurchaseList(GetAllProductNameDropDownList.get(0));
                        return;
                    } else if (GetAllProductNameDropDownList.size() < 1) {
                        //no product found
                        // Toast.makeText(activity_inventory.this, "No Product found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    GetAllProductNameDropDownList = helper.getProductdataforwithoutInventoryTotal(userTypedString);
                    if (GetAllProductNameDropDownList.size() < 1) {
                        //no product found
                        GetAllProductNameDropDownList.clear();
                        //Toast.makeText(activity_inventory.this, "No Product found", Toast.LENGTH_SHORT).show();

                    }
                }
                if (inventoryDropDownProductNmAdapter == null) {
                    inventoryDropDownProductNmAdapter = new InventoryDropDownProductNmAdapter(InventoryTotalActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<InventoryTotalProductModel>());
                    inventoryDropDownProductNmAdapter.setList(GetAllProductNameDropDownList);
                    ProductidorName.setAdapter(inventoryDropDownProductNmAdapter);
                    ProductidorName.setThreshold(1);
                } else {
                    inventoryDropDownProductNmAdapter.setList(GetAllProductNameDropDownList);
                    inventoryDropDownProductNmAdapter.notifyDataSetChanged();
                }
            }

        };
        ProductidorName.addTextChangedListener(pTextWatcher);
        ProductidorName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InventoryTotalProductModel resval = (InventoryTotalProductModel) parent.getItemAtPosition(position);
                addProductToPurchaseList(resval);
                inventoryFullProductListAdapter.setBatchdata(resval);

            }


        });

        addlocalprod.setOnClickListener(new View.OnClickListener() {
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

        VendorEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InventoryTotalActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


//****************************************************submit Button****************************************************8
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    view.startAnimation(Buttonok);
                    int pos = 0;
                    for (InventoryTotalProductModel item : inventoryFullProductListAdapter.getlist()) {

                        if (item.getProductQuantity() <= 0 || item.getPprice() <= 0 || item.getExpdate().trim().equals("select date") || item.getMrp() <= 0 || item.getSprice() <= 0 || item.getFreequantity() < 0) {
                            //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (item.getMrp() < item.getPprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (item.getMrp() < item.getSprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }
                    discountPopUpTotalInventory();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
              /*
                    GrandTotal.getText().toString();


                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    Log.d("***IMEI",invoicevalue);
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
                    helper.saveInventorytotal(inventoryFullProductListAdapter.getlist(), autoCompleteTextView.getText().toString(), x1, username,VendorEditNo.getText().toString(),VendorEditDate.getText().toString());
                    helper.saveGranddataintoGrnMasterwithoutpo(x1, autoCompleteTextView.getText().toString(), GrandTotal.getText().toString(), username);
                    helper.SavePDfDetailForInventoryWithoutPo(x1, autoCompleteTextView.getText().toString(), username);

                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();

                    UpdateIventoryWithoutPO();
                    //UpdateShell();

                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }*/
            }

        });
/////////////////////////////////////////////////////////////////hold////////////////////////////



        holdbilltransaction = helper.getgrnNumberForinventorytotal();
        holdbilltransaction.add(0, "Hold Bills");
        holdbilladapter =
                new ArrayAdapter<String>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item, holdbilltransaction);
        holdbill.setAdapter(holdbilladapter);
        holdbill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holdspinneritem = holdbill.getSelectedItem().toString();
                if (holdspinneritem.toString().equals("Hold Bills")) {
                    if (inventoryFullProductListAdapter != null) {
                        //  adapter.clear();
                        inventoryFullProductListAdapter.notifyDataSetChanged();
                    }
                }

                holdbilltransaction = helper.getgrnNumberForinventorytotal();
                for (String str : holdbilltransaction) {
                    if (str.equals(holdspinneritem)) {
                        HoldInventoryList = helper.getholddataforinventoryTotalinventory(holdspinneritem);
                        if (HoldInventoryList != null && HoldInventoryList.size() > 0) {
                            if (inventoryFullProductListAdapter == null) {
                                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                                inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, HoldInventoryList, android.R.layout.simple_dropdown_item_1line, null);
                                RecycleView.setAdapter(inventoryFullProductListAdapter);
                            }

                            autoCompleteTextView.setText(HoldInventoryList.get(0).getVendorName());
//                           else {
                            // adapter.setsalearrayList(holdinvoicelist);


                        }

                        setSummaryRow();
                         helper.Updateflag(holdspinneritem);
                        autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                        autoCompleteTextView.setEnabled(false);
                        Log.i("&&&&&&&&!", "Adding " + HoldInventoryList.get(0) + " to Product List..");


                        inventoryFullProductListAdapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




//******************************************************hold button*************************************************

        Holdinventorybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                helper = new DBhelper(InventoryTotalActivity.this);


                if (autoCompleteTextView.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
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
                            Log.e("X1_imei is hold:", x1);
                        } else {
                            continue;
                        }
                    }
                    int pos = 0;
                    for (InventoryTotalProductModel item : inventoryFullProductListAdapter.getlist()) {
                        if ( item.getProductQuantity() <= 0 || item.getPprice() <= 0 || item.getExpdate() == null || item.getExpdate().trim().equals("select date") || item.getMrp() <= 0 || item.getSprice() <= 0 ) {
                            //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }

                       /* else if ( item.getBatchno()== null||item.getBatchno().trim().equals("")||item.getBatchno()=="batch"){
                                 item.setBatchno(getSystemCurrentTime());
                        }*/
                        else if (item.getMrp() < item.getPprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (item.getMrp() < item.getSprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }

                    helper.saveInventoryholdbillTotal(inventoryFullProductListAdapter.getlist(), autoCompleteTextView.getText().toString(), x1, VendorEditNo.getText().toString(), VendorEditDate.getText().toString());

                    Toast.makeText(getApplicationContext(), x1, Toast.LENGTH_SHORT).show();
                    Intent syncIntent = new Intent(getApplicationContext(), com.RSPL.POS.Activitypurchase.class);
                    startActivity(syncIntent);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });


        //**************************Alll clear screen ***********************************************************************************************************************/

        clrbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                try {
                    clearbuttondialog();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
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
                    int Mymonth = 1 + month;
                    VendorEditDate.setText(year + "/" + Mymonth + "/" + day);


                }
            };


    public void validateDate(int year, int month, int day) {
        Date Todaydate = null, edate = null;

        String enddate = year + "/" + month + "/" + day;
        Log.e("########end", "----------->" + enddate);
        String todaysdate = Year + "/" + Month + "/" + Day;

        //    String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########today date", "----------->" + todaysdate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {

            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();

        }
    }

    public void clearbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want delete this?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {

                    inventoryFullProductListAdapter.clearAllRows();
                    setSummaryRow();
                    autoCompleteTextView.setText("");
                    ProductidorName.setText("");
                    VendorEditDate.setText("Select Vendor Date");

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


    private void addProductToPurchaseList(InventoryTotalProductModel resval1) {
        if (inventoryFullProductListAdapter == null) {
            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
            inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, new ArrayList<InventoryTotalProductModel>(), android.R.layout.simple_dropdown_item_1line, resval1);
            RecycleView.setAdapter(inventoryFullProductListAdapter);
        }
        int pos = inventoryFullProductListAdapter.addProductToList(resval1);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        inventoryFullProductListAdapter.notifyDataSetChanged();
        ProductidorName.setText("");
        setSummaryRow();
        inventoryDropDownProductNmAdapter.setList(GetAllProductNameDropDownList);
        GetAllProductNameDropDownList.clear();
        RecycleView.smoothScrollToPosition(pos);
    }

    public void setSummaryRow() {


        if (roundofff.toString().matches("Y")) {

            DecimalFormat f2 = new DecimalFormat("##");
            float Getval = inventoryFullProductListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f2.format(Getval).concat(".00");
            GrandTotal.setText(GrandVal);

            int Getitem = inventoryFullProductListAdapter.getItemCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);

        } else {

            DecimalFormat f = new DecimalFormat("##.00");
            float Getval = inventoryFullProductListAdapter.getGrandTotal();
            Log.d("&&&&&&&&", "" + Getval);
            String GrandVal = f.format(Getval);
            GrandTotal.setText(GrandVal);

            int Getitem = inventoryFullProductListAdapter.getItemCount();
            int Allitem = Getitem;
            String GETITEM = Integer.toString(Allitem);
            Totalitems.setText(GETITEM);
        }
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    private void SearchlocalproductDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localproduct_dialog_inventory, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(InventoryTotalActivity.this);
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

        //  try {
        helper = new DBhelper(this);
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

                localproductlist = helper.getAllLocalProductforpurchase(userTypedString);

                if (localproductlist != null) {
                    if (localadapter == null) {
                        localadapter = new localproductadapterinventorytotal(InventoryTotalActivity.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
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

                final DBhelper mydb = new DBhelper(InventoryTotalActivity.this);

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
                try {

                    float selling = Float.parseFloat(localsprice.getText().toString());
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
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValue, localmargin.getText().toString(), user2.getText().toString(), localdiscount.getText().toString(), localuom.getText().toString());


                    helper.updatelocalProductCom(localProd_Id_To_Update, productname.getText().toString(), localbarcode.getText().toString(), localhmrp.getText().toString(),
                            localsprice.getText().toString(), localpprice.getText().toString(), SpinValue, user2.getText().toString(), localdiscount.getText().toString(), localuom.getText().toString());

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

//*****************************************local vendor dialog********************************************************

    private void SearchlocalvendorDialod() {


        LayoutInflater inflater = getLayoutInflater();
        final View searchalertLayout = inflater.inflate(R.layout.search_localvendor_dialog, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(InventoryTotalActivity.this);
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


                try {
                    InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(localvendornametname.getWindowToken(), 0);


                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
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
                    if (inventoryvendoradapter == null) {
                        inventoryvendoradapter = new InventoryTotalLocalVendoradapter(InventoryTotalActivity.this, android.R.layout.simple_dropdown_item_1line, localvendorlist);
                        inventoryvendoradapter.setLocalVendorList(localvendorlist);
                        localvendornametname.setAdapter(inventoryvendoradapter);
                        localvendornametname.setThreshold(1);
                    } else {

                        localvendornametname.setAdapter(inventoryvendoradapter);
                        inventoryvendoradapter.setLocalVendorList(localvendorlist);
                        inventoryvendoradapter.notifyDataSetChanged();
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

                final DBhelper mydb = new DBhelper(InventoryTotalActivity.this);

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
                SpinValue4 = value.getLocalvendorinventory();
                if (SpinValue4.equals("N")) {
                    localinventory.setSelection(0);
                }
                if (SpinValue4.equals("Y")) {
                    localinventory.setSelection(1);
                }
                SpinValue3 = value.getLocalactive();
                if (SpinValue3.equals("Y")) {
                    localvendoractive.setSelection(0);
                }
                if (SpinValue3.equals("N")) {
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
                if (vendorid.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                    return;
                }


                try {
                    if (vendorname.getText().toString().matches("")) {
                        vendorname.setError("Please select Vendor Name");
                        return;
                    }
                    if (vendorcontactname.getText().toString().matches("")) {
                        vendorcontactname.setError("Please select Vendor Contact Name");
                        return;
                    }

                    helper.updatelocalVendor(localVendor_Id_To_Update, vendorname.getText().toString(), vendorcontactname.getText().toString(),
                            localadrress1.getText().toString(), localcountrydescription.getText().toString(), localcity.getText().toString(), localmobile.getText().toString(), SpinValue3, SpinValue4, localzip.getText().toString(), localtelephone.getText().toString(), user2.getText().toString());


                    helper.updatelocalVendorinpurchase(localVendor_Id_To_Update, vendorname.getText().toString(), localadrress1.getText().toString(), localcountrydescription.getText().toString(), localcity.getText().toString(), localzip.getText().toString(), SpinValue4, SpinValue3, user2.getText().toString());

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


    public void InventoryHelpDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                      INVENTORY  ");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);


        final MenuItem item2 = menu.findItem(R.id.TextView);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        user2 = (TextView) MenuItemCompat.getActionView(item2);


        // user2.setText(username);



        helper=new DBhelper(InventoryTotalActivity.this);
        ArrayList<Registeremployeesmodel> user_name= helper.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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


            case R.id.action_settings:

                Intent i = new Intent(InventoryTotalActivity.this, Activitypurchase.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(InventoryTotalActivity.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                InventoryHelpDialog();
                return true;
          /*  case R.id.action_settingpurchase:
                Intent p=new Intent(activity_inventory.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(InventoryTotalActivity.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in = new Intent(InventoryTotalActivity.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(InventoryTotalActivity.this, ActivitySalesbill.class);
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
        Intent intent = new Intent(InventoryTotalActivity.this ,login.class);
        startActivity(intent);
    }
//******************************************************sync code****************************************************************

    public void updateLocalProduct() {


        final DBhelper mydb = new DBhelper(InventoryTotalActivity.this);

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
        final  String pos_user = user2.getText().toString();


        PersistenceManager.saveStoreId(InventoryTotalActivity.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
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

//*************************************************************************************************************************

    public void updateLocalVendor() {

        final DBhelper mydb = new DBhelper(InventoryTotalActivity.this);

        final String local_vendorname = vendorname.getText().toString().trim();
        final String local_vendoraddress = localadrress1.getText().toString().trim();
        final String local_vendortele = localtelephone.getText().toString().trim();
        final String local_vendorzip = localzip.getText().toString().trim();
        final String local_vendorcontactname = vendorcontactname.getText().toString().trim();
        final String local_vendormobile = localmobile.getText().toString().trim();
        final String local_vendorcity = localcity.getText().toString().trim();
        final String local_vendorcountry = localcountrydescription.getText().toString().trim();
        final String local_vendorinventory = localinventory.getSelectedItem().toString();
        final String pos_user = user2.getText().toString();
        final String local_vendoractive = localvendoractive.getSelectedItem().toString();
        PersistenceManager.saveStoreId(InventoryTotalActivity.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String store_id = PersistenceManager.getStoreId(InventoryTotalActivity.this);

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
                try {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.LOCAL_VENDOR_STORE_ID, store_id);
                    hashMap.put(Config.LOCAL_VENDOR_ID, localVendor_Id_To_Update);
                    hashMap.put(Config.RETAIL_VEND_DSTR_ID, localVendor_Id_To_Update);

                    hashMap.put(Config.LOCAL_VENDOR_NAME, local_vendorname);
                    hashMap.put(Config.LOCAL_VENDOR_CONTACT, local_vendorcontactname);
                    hashMap.put(Config.LOCAL_VENDOR_ADDRESS, local_vendoraddress);
                    hashMap.put(Config.LOCAL_VENDOR_CITY, local_vendorcity);
                    hashMap.put(Config.LOCAL_VENDOR_TELE, local_vendortele);
                    hashMap.put(Config.LOCAL_VENDOR_ZIP, local_vendorzip);
                    hashMap.put(Config.LOCAL_VENDOR_ACTIVE, local_vendoractive);
                    hashMap.put(Config.LOCAL_VENDOR_COUNTRY, local_vendorcountry);
                    hashMap.put(Config.LOCAL_VENDOR_MOBILE, local_vendormobile);
                    hashMap.put(Config.LOCAL_VENDOR_INVENTORY, local_vendorinventory);
                    hashMap.put(Config.Retail_Pos_user, pos_user);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_LOCAL_VENDOR, hashMap);

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

                return null;
            }
        }

        UpdateVendor updateVendor = new UpdateVendor();
        updateVendor.execute();
    }


    public void UpdateIventoryWithoutPO() {

//        PurchaseProductModel purchase = new PurchaseProductModel();
        final String inventoryGRNvalue = x1;

        final String inventoryvendorname = autoCompleteTextView.getText().toString();
        final String inventorygrandtotal = GrandTotal.getText().toString();

        final String inventoryvendoreditno = VendorEditNo.getText().toString();
        final String inventoryvendoreditdate = VendorEditDate.getText().toString();


        for (InventoryTotalProductModel purchase : inventoryFullProductListAdapter.getlist()) {
            final String inventorybatchno = String.valueOf(purchase.getBatchno());
            final String inventoryexpdate = String.valueOf(purchase.getExpdate());
            final String inventoryproductid = purchase.getProductId();
            final String inventoryproductname = purchase.getProductName();
            final String inventoryproductmrp = String.valueOf(purchase.getMrp());
            final String inventoryuom = purchase.getTax();
            final String inventoryproductprice = String.valueOf(purchase.getPprice());
            final String inventorybarcode = purchase.getBarcode();
            final String inventorysellingprice = String.valueOf(purchase.getSprice());
            final String inventoryconvfact = String.valueOf(purchase.getConvfact());
            final String inventoryprofitmargin = String.valueOf(purchase.getProductmargin());
            final String inventoryfreegoods = String.valueOf(purchase.getFreequantity());
            final String Inventoryconvmulqty = String.valueOf(purchase.getTotalqty());
            final String inventoryQuantity = String.valueOf(purchase.getProductQuantity());
            final String purchasetotal = String.valueOf(purchase.getTotal());
            final String pos_user = user2.getText().toString();


            //   final String inventoryconvmrp = String.valueOf(purchase.);

            PersistenceManager.saveStoreId(InventoryTotalActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
            store_id = PersistenceManager.getStoreId(InventoryTotalActivity.this);
            universal_id = PersistenceManager.getStoreId(InventoryTotalActivity.this);
            class UpdateIventoryWithoutPO extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        HashMap<String, String> hashMap = new HashMap<>();


                        hashMap.put(Config.Retail_inventory_store_id, store_id);
                        hashMap.put(Config.Retail_Inventory_grnid, inventoryGRNvalue);
                        hashMap.put(Config.Retail_Inventory_mfg_batchno, inventorybatchno);
                        hashMap.put(Config.Retail_Inventory_batchno, getSystemCurrentTime());
                        hashMap.put(Config.Retail_Inventory_vendoreditno, inventoryvendoreditno);
                        hashMap.put(Config.Retail_Inventory_vendoreditdate, inventoryvendoreditdate);
                        hashMap.put(Config.Retail_Inventory_expdate, inventoryexpdate);
                        hashMap.put(Config.Retail_Inventory_id, inventoryproductid);
                        hashMap.put(Config.Retail_Inventory_name, inventoryproductname);
                        hashMap.put(Config.Retail_Inventory_p_price, inventoryproductprice);
                        hashMap.put(Config.Retail_Inventory_barcode, "NA");
                        hashMap.put(Config.Retail_Inventory_mrp, inventoryproductmrp);
                        hashMap.put(Config.Retail_Inventory_profit_margin, inventoryprofitmargin);
                        hashMap.put(Config.Retail_Inventory_conv_fact, inventoryconvfact);
                        hashMap.put(Config.Retail_Inventory_selling, inventorysellingprice);
                        hashMap.put(Config.Retail_Inventory_Vendorname, inventoryvendorname);
                        hashMap.put(Config.Retail_Inventory_uom, inventoryuom);
                        hashMap.put(Config.Retail_Inventory_Quantity, inventoryQuantity);
                        hashMap.put(Config.Retail_Inventory_Total, purchasetotal);
                        hashMap.put(Config.Retail_Inventory_freegoods, inventoryfreegoods);
                        hashMap.put(Config.Retail_Inventory_conmulqty, Inventoryconvmulqty);
                        hashMap.put(Config.Retail_Inventory_GrandTotal, inventorygrandtotal);
                        hashMap.put(Config.Retail_Inventory_Universal_id, universal_id);
                        hashMap.put(Config.Retail_Inventory_po_no, inventoryGRNvalue);
                        hashMap.put(Config.Retail_Pos_user, pos_user);
                        hashMap.put(Config.Retail_Inventory_Inventory_Date, getDate());


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.UPDATE_INVENTORY_WITHOUT_PO, hashMap);
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

    public void discountPopUpTotalInventory() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View alertLayout = layoutInflater.inflate(R.layout.discountpopuptotalinventory, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final TextView TotalValue = (TextView) alertLayout.findViewById(R.id.totalforDiscount);
        final TextView TotalTobePaid = (TextView) alertLayout.findViewById(R.id.TotalValuetobePaid);
        final EditText Discount = (EditText) alertLayout.findViewById(R.id.Enterdiscount);
        final Button TotalSubmit = (Button) alertLayout.findViewById(R.id.SbtTotalbutton);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("    ENTER BILL LEVEL DISCOUNT AMOUNT");
        final AlertDialog dialog = alert.create();
        TotalValue.setText(GrandTotal.getText().toString());
        TotalTobePaid.setText(GrandTotal.getText().toString());
        dialog.show();

        TextWatcher DiscountTextChanger = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Discount.getText().toString().matches("")) {
                    Toast.makeText(InventoryTotalActivity.this, "Please Enter some Value if u want give discount", Toast.LENGTH_SHORT).show();
                    Discount.setText("0.00");
                    return;
                }
                if (Discount.getText().toString().isEmpty()) {
                    TotalTobePaid.setText(GrandTotal.getText().toString());
                }

                TotalTobePaid.setText(String.valueOf(Float.parseFloat(GrandTotal.getText().toString()) - Float.parseFloat(Discount.getText().toString())));
            }
        };
        Discount.removeTextChangedListener(DiscountTextChanger);
        Discount.addTextChangedListener(DiscountTextChanger);

        TotalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    GrandTotal.getText().toString();
                    Long Value = System.currentTimeMillis();
                    final String result = Long.toString(Value);
                    String invoicevalue = Billno.getText().toString();
                    Log.d("***IMEI", invoicevalue);
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

                    for (InventoryTotalProductModel item : inventoryFullProductListAdapter.getlist()) {

                        if (item.getProductQuantity() <= 0 || item.getPprice() <= 0 || item.getExpdate().trim().equals("select date") || item.getMrp() <= 0 || item.getSprice() <= 0 || item.getFreequantity() < 0) {
                            //  Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (item.getMrp() < item.getPprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (item.getMrp() < item.getSprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }
                    helper.saveInventorytotal(inventoryFullProductListAdapter.getlist(), autoCompleteTextView.getText().toString(), x1, user2.getText().toString(), VendorEditNo.getText().toString(), VendorEditDate.getText().toString());
                    helper.saveGranddataintoGrnMasterwithoutpo(x1, autoCompleteTextView.getText().toString(), TotalTobePaid.getText().toString(), user2.getText().toString());
                    helper.SavePDfDetailForInventoryWithoutPo(x1, autoCompleteTextView.getText().toString(), user2.getText().toString());

                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();
                    UpdateIventoryWithoutPO();
                    //UpdateShell();

                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);

                    if (invbillprint.matches("Y"))
                    {
                    String imeino = "123656";
                    tp.setColor(Color.BLUE);
                    tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));
                    tp.setTypeface(tff);

                    VendorEditNo.setText(String.valueOf(getBillnumber()));


                    //////////////////////////////////////Printer testing ///////////////////////////////////////

                    store_name = db.getAllStores();
                    store = (store_name.get(1).toString());
                    //   String Store_ID = (store_name.get(0).toString());
                    storeAddress = store_name.get(2).toString();
                    City = store_name.get(3).toString();
                    Storenumber = store_name.get(4).toString();
                    AlternateNo = store_name.get(5).toString();
                    Footer = store_name.get(6).toString();


                    DecimalFormat f = new DecimalFormat("##.00");
                    for (int i = 1; i <= 1; i++) {
                        StringBuilder strbuilder = new StringBuilder();
                        strbuilder.append(store + "\n");
                        strbuilder.append(storeAddress + "\n");
                        strbuilder.append(City + "\n");
                        /*UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                        strbuilder.setLength(0);*/
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                        strbuilder.setLength(0);
                        strbuilder.append(extraspace + "Distributor:" + autoCompleteTextView.getText().toString() + "\n");
                        String formattedDate;
                        formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());
                        int billDtSpace = 39 - ((String.valueOf(1234567).length() + BILL_DATE_COLUMN) + formattedDate.length());
                        strbuilder.append(extraspace + "GRN ID :" + x1);
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                        strbuilder.setLength(0);

                        strbuilder.append(extraspace + formattedDate);

                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                        strbuilder.setLength(0);
                        strbuilder.append(getDividerSales());
                        strbuilder.append(InventoryTotalActivity.this.eol);

                        strbuilder.append("Product Name" + "   " + "P.Price");


                        strbuilder.append(InventoryTotalActivity.this.eol);

                        strbuilder.append(" Qty" + "   " + " Free" + "   " + "Margin" + "   " + "Total");
                        strbuilder.append(InventoryTotalActivity.this.eol);
                        strbuilder.append(getDividerSales());
                        // strbuilder.append(InventoryTotalActivity.this.eol);
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                        strbuilder.setLength(0);

                        strbuilder.setLength(0);
                        for (InventoryTotalProductModel prod : inventoryFullProductListAdapter.getlist()) {
                            String ProuctName = prod.getProductName();
                            if (ProuctName.length() >= 21) {
                                ProuctName = ProuctName.substring(0, 21);
                            }
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);

                            String printPurchasePrice = String.valueOf(f.format(prod.getPprice()));
                            int spaces = 10 - printPurchasePrice.length();
                            if (spaces == 0) {
                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printPurchasePrice + getSpacer(PRINT_LEFT_MARGIN));
                            } else {
                                strbuilder.append(getSpacer(spaces) + printPurchasePrice + getSpacer(PRINT_LEFT_MARGIN));
                            }

                            strbuilder.append(InventoryTotalActivity.this.eol);

                            String printQuantity = String.valueOf(f.format(prod.getProductQuantity()));
                            spaces = 5 - printQuantity.length();
                            if (spaces == 0) {
                                strbuilder.append(" " + getSpacer(spaces + PRINT_LEFT_MARGIN) + " " + printQuantity + getSpacer(PRINT_LEFT_MARGIN));
                            } else {
                                strbuilder.append(" " + getSpacer(spaces) + printQuantity + " " + getSpacer(PRINT_LEFT_MARGIN));

                            }
                            String printqty = String.valueOf(prod.getFreequantity());
                            spaces = 5 - printqty.length();
                            strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                            float p = (((prod.getMrp() - prod.getPprice()) / prod.getMrp()) * 100);

                            String printMargin = String.valueOf(f.format((p)));
                            spaces = 4 - printMargin.length();
                            if (spaces == 0) {
                                strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + "  " + printMargin + getSpacer(PRINT_LEFT_MARGIN));
                            } else {
                                strbuilder.append(getSpacer(spaces) + printMargin + getSpacer(PRINT_LEFT_MARGIN));
                            }


                            if (p > 0.00) {
                                String total = String.valueOf(f.format(prod.getProductQuantity() * prod.getPprice() - (prod.getProductQuantity() * prod.getPprice() * prod.getInvdiscount() / 100)));
                                spaces = 9 - total.length();
                                strbuilder.append(getSpacer(spaces) + total);
                            } else {
                                String total = String.valueOf(f.format(prod.getTotal()));
                                spaces = 8 - total.length();
                                strbuilder.append(getSpacer(spaces) + total);
                            }

                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);
                            strbuilder.setLength(0);
                        }
                        strbuilder.append(getDividerSales());

                        String noofitems = Totalitems.getText().toString();
                        strbuilder.append("Total Items : " + noofitems + extraspace);
                        strbuilder.append(InventoryTotalActivity.this.eol);

                        String finaltotal = GrandTotal.getText().toString();
                        strbuilder.append("Total Amount: " + finaltotal + extraspace);
                        strbuilder.append(InventoryTotalActivity.this.eol);

                        //String discounttotal = Discount.getText().toString();
                        //int discountamount= String.valueOf((Integer.parseInt(finaltotal)-String.valueOf(Integer.parseInt(discounttotal));

                        String finalamount = TotalTobePaid.getText().toString();
                        strbuilder.append("Amount To be paid : " + finalamount + extraspace);
                        strbuilder.append(InventoryTotalActivity.this.eol);
                        strbuilder.append(getDividerSales());
                        UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_OPPOSITE, tp);
                        strbuilder.setLength(0);

                        UsPrinter.addText("Thank You", Layout.Alignment.ALIGN_CENTER, tp);
                        strbuilder.setLength(0);
                        UsPrinter.print();
                        UsPrinter.FeedLine();
                        UsPrinter.FeedLine();
                        UsPrinter.FullCut();

                        // Toast.makeText(getApplicationContext(), VendorEditNo.getText().toString(), Toast.LENGTH_SHORT).show();

                        dialog.dismiss();
                    }

                    }

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }


            }

        });
    }


    public void SelectPurchaseAlertDialog() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.inventory_alert_dialog, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button po = (Button) alertLayout.findViewById(R.id.po);
        final Button withoutpo = (Button) alertLayout.findViewById(R.id.withpo);
        final Button cancel = (Button) alertLayout.findViewById(R.id.cancelinventoryalert);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Spinner spinner = (Spinner) alertLayout.findViewById(R.id.VendororDistributorName);
      //  final Button Hold = (Button) alertLayout.findViewById(R.id.HoldButton);
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
      //  Hold.setVisibility(View.VISIBLE);
        Holddata.setVisibility(View.GONE);
        SubmitForHold.setVisibility(View.GONE);

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

              /*  withoutpo.setVisibility(View.GONE);
                integrationwithdistributor.setVisibility(View.GONE);
                Last3InvoiceNo.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                spinner.setVisibility(View.VISIBLE);
                distributorno.setVisibility(View.GONE);
                documentno.setVisibility(View.GONE);
                SubmitForHold.setVisibility(View.GONE);
                submitfordistributor.setVisibility(View.GONE);
                Holddata.setVisibility(View.GONE);
                Hold.setVisibility(View.GONE);*/
                Intent intent = new Intent(getApplicationContext(), activity_inventory.class);
                startActivity(intent);

            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        withoutpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                dialog.dismiss();

            }
        });

     /*   Hold.setOnClickListener(new View.OnClickListener() {
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

*/
        integrationwithdistributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);

                Holddata.setVisibility(View.GONE);
               // Hold.setVisibility(View.GONE);
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


                try {
                    v.startAnimation(Buttonok);
                    Ponumbers.setVisibility(View.GONE);
                    // InvProductidorName.setVisibility(View.GONE);
                    // Ponum.setVisibility(View.GONE);

                    if (GetDistributorNumber == null) {
                        Toast.makeText(getApplicationContext(), "No Distributor Number Item Found", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    documentNumberForIntegration = helper.getdataforintegrationTotalinventory(GetDistributorNumber);
                    if (documentNumberForIntegration != null && documentNumberForIntegration.size() > 0) {
                        if (inventoryFullProductListAdapter == null) {
                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, new ArrayList<InventoryTotalProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                            RecycleView.setAdapter(inventoryFullProductListAdapter);
                        }
                        for (InventoryTotalProductModel prod : documentNumberForIntegration) {
                            inventoryFullProductListAdapter.addProductToList(prod);
                            // Ponumbers.findViewById(R.id.Po_numbers);
                        }
                        helper.Updateflag(GetDistributorNumber);

                        autoCompleteTextView.setText(documentNumberForIntegration.get(0).getVendorName());
                        // Ponumbers.setText(HoldInventoryList.get(0).getPurchaseno());
                        //   autoCompleteTextView.setText("" + Holddata.getSelectedItem().toString() + "");

                        autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                        autoCompleteTextView.setEnabled(false);
                        Log.i("&&&&&&&&!", "Adding " + documentNumberForIntegration.get(0) + " to Product List..");
                        inventoryFullProductListAdapter.notifyDataSetChanged();
                        setSummaryRow();
                        dialog.dismiss();

                    }
                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        arrayList = helper.getdocumentnameTotalinventory();
        ArrayAdapter<InventoryTotalProductModel> documentadapter =
                new ArrayAdapter<InventoryTotalProductModel>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        distributorno.setAdapter(documentadapter);
        distributorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String distributorrvalue = distributorno.getSelectedItem().toString();

                ArrayList<Inventorygrnmodel> LastInvoices = helper.getgrnNumberForintegration(distributorrvalue);


                ArrayAdapter<Inventorygrnmodel> documentAdapter =
                        new ArrayAdapter<Inventorygrnmodel>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                try {
                    Spinnervalue = spinner.getSelectedItem().toString();
                    userTypedInvoiceno = Last3InvoiceNo.getSelectedItem().toString().trim();
                    Log.e("!!!!!!!!!!!", "" + Last3InvoiceNo);
                    if (userTypedInvoiceno.equals("")) {
                        return;
                    }

                    ArrayList<InventoryTotalProductModel> alldata = helper.getPurchaseProductdataTotalinventory(userTypedInvoiceno);
                    //   PurchaseProductModelwithpo resval1 = (PurchaseProductModelwithpo) parent.getItemAtPosition(position);
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, new ArrayList<InventoryTotalProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                    RecycleView.setAdapter(inventoryFullProductListAdapter);

                    for (InventoryTotalProductModel prod : alldata) {
                        inventoryFullProductListAdapter.addProductToList(prod);
                        inventoryFullProductListAdapter.setBatchdata(prod);
                    }
                    inventoryFullProductListAdapter.notifyDataSetChanged();
                    //autoCompleteTextView.setText("");
                    setSummaryRow();
                    autoCompleteTextView.setText("" + spinner.getSelectedItem().toString() + "");
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);

                    Ponumbers.setText(userTypedInvoiceno);
                    //  Log.i("&&&&&&&&!", "Adding " + OldPurchaseArraylist.get(0) + " to Product List..");
                    inventoryFullProductListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    autoCompleteTextView.setEnabled(false);
                    dialog.dismiss();


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        arrayList = helper.getvendorsearchTotalinventory();
        ArrayAdapter<InventoryTotalProductModel> stringArrayAdapter =
                new ArrayAdapter<InventoryTotalProductModel>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(stringArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = spinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);
                ArrayList<String> LastInvoices = helper.getPo_numbers(Spinnervalue, Integer.parseInt(purchaseholdno));
                ArrayAdapter<String> InvoiceNoAdapter =
                        new ArrayAdapter<String>(InventoryTotalActivity.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
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


        SubmitForHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (HoldInventoryorderNo == null) {
                    Toast.makeText(getApplicationContext(), "No Hold Item Found", Toast.LENGTH_SHORT).show();
                    return;
                }
                HoldInventoryList = helper.getholddataforinventoryTotalinventory(HoldInventoryorderNo);
                if (HoldInventoryList != null && HoldInventoryList.size() > 0) {
                    if (inventoryFullProductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        inventoryFullProductListAdapter = new InventoryProductListAdapter(InventoryTotalActivity.this, new ArrayList<InventoryTotalProductModel>(), android.R.layout.simple_dropdown_item_1line, null);
                        RecycleView.setAdapter(inventoryFullProductListAdapter);
                    }
                    for (InventoryTotalProductModel prod : HoldInventoryList) {
                        inventoryFullProductListAdapter.addProductToList(prod);
                        // Ponumbers.findViewById(R.id.Po_numbers);
                    }
                    autoCompleteTextView.setText(HoldInventoryList.get(0).getVendorName());
                    // Ponumbers.setText(HoldInventoryList.get(0).getPurchaseno());
                    //   autoCompleteTextView.setText("" + Holddata.getSelectedItem().toString() + "");
                    helper.Updateflag(HoldInventoryorderNo);
                    autoCompleteTextView.removeTextChangedListener(mTextWatcher);
                    autoCompleteTextView.setEnabled(false);
                    Log.i("&&&&&&&&!", "Adding " + HoldInventoryList.get(0) + " to Product List..");
                    inventoryFullProductListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    dialog.dismiss();
                }
            }
        });
        ArrayList<String > LastInvoices = helper.getgrnNumberForinventory();
       // grnnumberlistadapter = new InventorygrnnoAdapter(InventoryTotalActivity.this, android.R.layout.simple_dropdown_item_1line, LastInvoices);
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

    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }

    private String getDividerSales() {
        //this for ls printer
        return "-----------------------------------";
    }


}