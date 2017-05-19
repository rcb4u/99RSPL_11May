package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Adapter.localproductadapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.LocalProduct;
import Pojo.Registeremployeesmodel;


public class ActivityLocalProduct extends Activity  {
    ActionBar actionBar;
    EditText localproductname,localbarcode,localmrp,localsellingprice,localpurchaseprice,localtaxid,uom;
    String localProd_Id_To_Update;
    TextView localproductid,localmargin;
    DBhelper mydb;
    TextWatcher mTextWatcher;
    public static String username = null;

    String pprice ,sprice,mrpdecimal;
    String backroundcolour,colorchange;


    //////////////////////////jimmmy///////////////////////////////////////
    TextView tvsearch,tvproductid,tvproductname,tvbaecode,tvmrp,tvmargin,tvsellingprice,tvpurchaseprice,tvuom,tvdiscount,tvactive;



    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String  local_prod_id;

    String iteam;
    TextView user2;


    localproductadapter adapter;
    AutoCompleteTextView autoCompleteTextView;
    EditText auto,discount;
    ArrayList<LocalProduct>localproductlist;
    String ActiveType[] ;
    String item;
    String SpinValue;
    Spinner localactive;
    Button Exit,Update;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

    // Adapter
    ArrayAdapter<String> adapterActiveType;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications


    private boolean    syncInProgress = false;
    private boolean    didSyncSucceed = false;

    String LOCAL_PROD = "sh /sdcard/local_prod.sh";
    String SUPER_USER ="su";

    //  DecimalDigitsInput dc =null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_local_product);
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        username =  login.b.getString("Pos_User");

        mydb = new DBhelper(ActivityLocalProduct.this);

        Decimal value = mydb.getStoreprice();
        mrpdecimal=value.getDecimalmrp();
        pprice=value.getDecimalpprice();
        sprice=value.getDecimalsprice();
        String textsize=         value.getHoldpo();


        actionBar=getActionBar();
       // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        backroundcolour=  value.getColorbackround();




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


        Exit = (Button) findViewById(R.id.localprodexit);
        Update = (Button) findViewById(R.id.localprod_edit_button);

        //////////////////////jimmy/////////////////////////////////


        tvsearch = (TextView)findViewById(R.id.textView1);
        tvsearch.setTextSize(Float.parseFloat(textsize));

        tvproductid = (TextView)findViewById(R.id.textView0);
        tvproductid.setTextSize(Float.parseFloat(textsize));

        tvproductname = (TextView)findViewById(R.id.textView2);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvbaecode = (TextView)findViewById(R.id.textView4);
        tvbaecode.setTextSize(Float.parseFloat(textsize));

        tvmrp = (TextView)findViewById(R.id.textView19);
        tvmrp.setTextSize(Float.parseFloat(textsize));

        tvmargin = (TextView)findViewById(R.id.margintext);
        tvmargin.setTextSize(Float.parseFloat(textsize));

        tvsellingprice = (TextView)findViewById(R.id.textView5);
        tvsellingprice.setTextSize(Float.parseFloat(textsize));

        tvpurchaseprice = (TextView)findViewById(R.id.textView6);
        tvpurchaseprice.setTextSize(Float.parseFloat(textsize));

        tvuom = (TextView)findViewById(R.id.textView55);
        tvuom.setTextSize(Float.parseFloat(textsize));

        tvdiscount = (TextView)findViewById(R.id.disc);
        tvdiscount.setTextSize(Float.parseFloat(textsize));

        tvactive = (TextView)findViewById(R.id.textViewa);
        tvactive.setTextSize(Float.parseFloat(textsize));




        localproductid = (TextView)findViewById(R.id.localprod_prodid);
        localproductid.setTextSize(Float.parseFloat(textsize));

        localproductname= (EditText)findViewById(R.id.localprod_prodname);
        localproductname.setTextSize(Float.parseFloat(textsize));

        localbarcode =(EditText)findViewById(R.id.localprod_barcode);
        localbarcode.setTextSize(Float.parseFloat(textsize));

        localmrp = (EditText)findViewById(R.id.localprod_mrp);
        localmrp.setTextSize(Float.parseFloat(textsize));

        localmargin = (TextView)findViewById(R.id.localprod_margin);
        localmargin.setTextSize(Float.parseFloat(textsize));


        localpurchaseprice =(EditText) findViewById(R.id.localprod_purchaseprice) ;
        localpurchaseprice.setTextSize(Float.parseFloat(textsize));

        localsellingprice =(EditText)findViewById(R.id.localprod_sellingprice);
        localsellingprice.setTextSize(Float.parseFloat(textsize));

        uom = (EditText)findViewById(R.id.uom);
        uom.setTextSize(Float.parseFloat(textsize));

        discount = (EditText)findViewById(R.id.discount);
        discount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3,2)});
        discount.setTextSize(Float.parseFloat(textsize));

        auto = (EditText)findViewById(R.id.product_auto_complete);
        auto.requestFocus();

        localmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(mrpdecimal))});
        localsellingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(sprice))});
        localpurchaseprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(pprice))});

        localactive = (Spinner)findViewById(R.id.localprod_active);



        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
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



        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


        try
        {
            mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);


            autoCompleteTextView.setThreshold(1);
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
                    if( autoCompleteTextView.isPerformingCompletion()) {
                        Log.d( "Debuging", "Performing completion " );
                        return;
                    }
                    String userTypedString = autoCompleteTextView.getText().toString().trim();
                    if (userTypedString.equals("")) {
                        return;
                    }
                    localproductlist = mydb.getAllLocalProduct(userTypedString);

                    if (localproductlist != null) {
                        if (adapter == null) {
                            adapter = new localproductadapter(ActivityLocalProduct.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
                            adapter.setLocalProductList(localproductlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(1);
                        } else {

                            adapter.setLocalProductList(localproductlist);
                            adapter.notifyDataSetChanged();
//
                        }
                    }
                }
            };
            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d("Debuging", "On click called ");

                    final DBhelper mydb = new DBhelper(ActivityLocalProduct.this);

                    LocalProduct value = (LocalProduct) parent.getItemAtPosition(pos);
                    localproductid.setText(value.getProductid());
                    localproductname.setText(value.getProductname());
                    localproductname.requestFocus();
                    localsellingprice.setText(value.getSellingPrice());
                    localpurchaseprice.setText(value.getPurchasePrice());
                    localbarcode.setText(value.getBarcode());
                    localmrp.setText(value.getMRP());
                    discount.setText(value.getDiscount());
                    uom.setText(value.getUom());


                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        localactive.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        localactive.setSelection(1);
                    }
                    localmargin.setText(value.getMargin());

                    autoCompleteTextView.setText("");



                }
            });


            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  view.startAnimation(Buttonok);
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);
                }
            });


            Button Clear = (Button)findViewById(R.id.localprod_clear_button);
            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        v.startAnimation(Buttonok);
                        auto.requestFocus();
                        localproductid.setText("");
                        localbarcode.setText("");
                        localmrp.setText("");
                        localproductname.setText("");
                        localpurchaseprice.setText("");
                        localsellingprice.setText("");
                        discount.setText("");
                        autoCompleteTextView.setText("");
                        auto.setText("");
                        localactive.setAdapter(null);
                        uom.setText("");

                        ActiveType = getResources().getStringArray(R.array.active_Status);
                        adapterActiveType = new ArrayAdapter<String>(ActivityLocalProduct.this, android.R.layout.simple_spinner_item, ActiveType);
                        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        localactive.setAdapter(adapterActiveType);
                        localmargin.setText("");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            auto.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String barcodeFromScanner = auto.getText().toString().trim();
                    if (barcodeFromScanner.equals("")) {
                        return;
                    }
                    localproductlist = mydb.getLocalProductfromBarcode(barcodeFromScanner);
                    if (localproductlist != null && localproductlist.size() > 0) {

                        LocalProduct value = localproductlist.get(0);
                        localproductid.setText(value.getProductid());
                        localproductname.setText(value.getProductname());
                        // localproductname.requestFocus();
                        localsellingprice.setText(value.getSellingPrice());
                        localpurchaseprice.setText(value.getPurchasePrice());
                        localbarcode.setText(value.getBarcode());
                        localmrp.setText(value.getMRP());
                        SpinValue=value.getActive();
                        if (SpinValue.equals("Y"))
                        {
                            localactive.setSelection(1);
                        }
                        if (SpinValue.equals("N"))
                        {
                            localactive.setSelection(2);
                        }
                        localmargin.setText(value.getMargin());


                    }
                }
            });




            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(Buttonok);

                    String value = localproductid.getText().toString();
                    localProd_Id_To_Update = value;
                    if (localproductid.getText().toString().matches(""))
                    {
                        Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (localsellingprice.getText().toString().matches(""))
                    {
                        localsellingprice.setError("Please fill Selling Price");
                        return;
                    }
                    if (localpurchaseprice.getText().toString().matches(""))
                    {
                        localpurchaseprice.setError("Please fill Purchase Price");
                        return;
                    }
                    if (localproductname.getText().toString().matches(""))
                    {
                        localproductname.setError("Please fill Product name ");
                        return;
                    }
                    if (localmrp.getText().toString().matches(""))
                    {
                        localmrp.setError("Please fill Mrp ");
                        return;
                    }
                    try {
                        float selling =Float.parseFloat(localsellingprice.getText().toString());
                        float purchase = Float.parseFloat(localpurchaseprice.getText().toString());
                        float mrp = Float.parseFloat(localmrp.getText().toString());
                        if (selling > mrp) {
                            localsellingprice.setError("Invalid Selling Price");
                            return;

                        }
                        if (purchase > selling) {
                            localpurchaseprice.setError("Invalid PurchasePrice");
                            return;
                        }
                        if (purchase > mrp) {
                            localpurchaseprice.setError("Invalid purchase Price");
                            return;
                        }

                        if (SpinValue .matches("N"))
                        {
                            mydb.updatesalestock(localProd_Id_To_Update) ;

                        }
                        if (SpinValue .matches("Y"))
                        {
                            mydb.updatesalestockActive(localProd_Id_To_Update) ;

                        }

                        mydb.updatelocalProduct(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue, localmargin.getText().toString(),user2.getText().toString(),discount.getText().toString(),uom.getText().toString());


                        mydb.updatelocalProductCom(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue,user2.getText().toString(),discount.getText().toString(),uom.getText().toString());

                        Toast.makeText(getApplicationContext(), "Local Product Updated", Toast.LENGTH_SHORT).show();
                        updateLocalProduct();
                        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                        startActivity(intent);


                        //call here local product update script
                        //LocalproductUpdateScript();


                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                }
            });

        }

        catch(NullPointerException e)
        {
            e.printStackTrace();
        }

        catch(Exception e)
        {
            e.printStackTrace();
        }


    }



    public  void LocalProducthelp()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  LOCAL PRODUCT (PHARMA)\n");
        alertDialog.setMessage("\n" +
                "The user is provided with the products created from “Product 1” to “Product 500” so that user can update any data to run products which are not available in the system, in case the user finds the number of 500 products to be less, the user can request for additional 500 products using the System Maintenence option.\n" +
                "\n" +
                "Objective:\n" +
                "\n" +
                "The user selects the Local Product (Pharma) option to update a local product which is not available in the Product(Pharma)\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "Search - The user enters the product name or user can scan the bar code of the product for the data entry. \n" +
                "Fields Description:\n" +
                "\n" +
                "Product Id:The Product id of the product.\n" +
                "Product Name: Name of the product.\n" +
                "Barcode : The barcode for the product.\n" +
                "MRP - Maximum Retail Price.\n" +
                "Selling Price - Selling price of the product.\n" +
                "Purchase price - Purchase price of the product.\n" +
                "Active - Product is active 'Y' or in-Active 'N' for the store to sell.\n" +
                "Margin - Margin of the product which is calculated based on (MRP – Purchase Price)/MRP*100\n" +
                "\n" +
                "Rules:\n" +
                "MRP should be greater than or equal to purchase price and selling price.\n" +
                "Selling price cannot be lower than the purchase price.\n" +
                "The purchase price and selling price can have maximum of 7 digits with values updated to 2 decimal places.\n" +
                "Functions:\n" +
                " There are 3 options below:\n" +
                "A. UPDATE - To update the record.\n" +
                "B. CLEAR-    Clear the record from the window and make it available for re-entry. \n" +
                "C. EXIT - Go back to the menu option.");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



                dialog.dismiss();
            }
        });


        alertDialog.show();
    }



    public String DecimalDigitsInput(float mrp, int x){
        //  mrp =  Activity_decimal.b.getString("MRP_Decimal");
        final NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(Integer.parseInt((String.valueOf(mrp))));
        final String resultS = numFormat.format(x);
        return resultS;
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



        mydb=new DBhelper(ActivityLocalProduct.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityLocalProduct.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        }catch (NullPointerException ex) {
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

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i=new Intent(ActivityLocalProduct.this,Activity_masterScreen2.class);

                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityLocalProduct.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                LocalProducthelp();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityLocalProduct.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityLocalProduct.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityLocalProduct.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;
            case R.id.action_settingsales:
                Intent s=new Intent(ActivityLocalProduct.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            //noinspection SimplifiableIfStatement

            case R.id.action_logout:
                Logout();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(ActivityLocalProduct.this ,login.class);
        startActivity(intent);

    }






    public void updateLocalProduct() {


        final DBhelper mydb = new DBhelper(ActivityLocalProduct.this);

        //Local_prod_store_id =local_prod_store_id.getText().toString().trim();
        local_prod_id= localproductid.getText().toString().trim();
        final String   local_product_name= localproductname.getText().toString().trim();
        final String   local_product_bare_code=localbarcode.getText().toString().trim();
        final String   local_product_mrp=localmrp.getText().toString().trim();
        final String   local_product_selling_price=localsellingprice.getText().toString().trim();
        final String  local_product_purchase_price=localpurchaseprice.getText().toString().trim();
        final String  local_product_active=localactive.getSelectedItem().toString().trim();
        final String  local_product_margin= localmargin.getText().toString().trim();

        final String  local_product_discount= discount.getText().toString().trim();
        final String  local_product_uom= uom.getText().toString().trim();
        final String pos_user = user2.getText().toString();

        PersistenceManager.saveStoreId(ActivityLocalProduct.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
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

                    hashMap.put(Config.Retail_Pos_user,pos_user);


                    JSONParserSync rh = new JSONParserSync();
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


}

