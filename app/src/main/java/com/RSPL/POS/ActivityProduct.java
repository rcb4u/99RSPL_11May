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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import Adapter.ProductAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Product;
import Pojo.ProductAuto;
import Pojo.Registeremployeesmodel;


public class ActivityProduct extends Activity  {
    // ActionBar actionBar;




///////////jimmy//////////////////
TextView tvsearch,tvprodid,tvbarcode,tvproductname,tvmrp,tvmanufacture,tvmeasureunit,tvsellingprice,tvpurchaseprice,tvinternetprice,tvinternetrel, tvmargin,tvdiscount,tvactive;


    TextView ProductBarcode,ProductName,MRP, Taxid,Margin,Manuf,MeasureUnit,ProductId,Internet,Internetrelevant;
    EditText Sellingprice,Purchaseprice,auto,productdiscount;
    Spinner Active;
    String Prod_Id_To_Update;

    ActionBar actionBar;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    public static String username = null;
    String mrp ,pprice,sprice;
    String iteam;
    TextView user2;


    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    ArrayList<Product> Productlist;
    ArrayList<ProductAuto>ProductAutoArraylist;
    ProductAdapter adapter;

    String item ,SpinValue,prod_id;

    // Data Source
    String ActiveType[];

    // Adapter
    ArrayAdapter<String> adapterActiveType;
    DBhelper mydb;


    Bundle syncDataBundle = null;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications

    private boolean 	syncInProgress = false;
    private boolean 	didSyncSucceed = false;

    String backroundcolour,colorchange;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_product);


        mydb = new DBhelper(ActivityProduct.this);
        Decimal value = mydb.getStoreprice();

        mrp=value.getDecimalmrp();
        pprice=value.getDecimalpprice();
        sprice=value.getDecimalsprice();
        String textsize=         value.getHoldpo();
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




        actionBar = getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);



        tvsearch = (TextView) findViewById(R.id.textView1);
        tvsearch.setTextSize(Float.parseFloat(textsize));

        tvprodid = (TextView) findViewById(R.id.textView0);
        tvprodid.setTextSize(Float.parseFloat(textsize));

        tvbarcode = (TextView) findViewById(R.id.textView2);
        tvbarcode.setTextSize(Float.parseFloat(textsize));

        tvproductname = (TextView) findViewById(R.id.textView3);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvmrp = (TextView) findViewById(R.id.textView4);
        tvmrp.setTextSize(Float.parseFloat(textsize));

        tvmanufacture = (TextView) findViewById(R.id.textView19);
        tvmanufacture.setTextSize(Float.parseFloat(textsize));

        tvmeasureunit = (TextView) findViewById(R.id.textView23);
        tvmeasureunit.setTextSize(Float.parseFloat(textsize));

        tvsellingprice = (TextView) findViewById(R.id.textView5);
        tvsellingprice.setTextSize(Float.parseFloat(textsize));

        tvpurchaseprice = (TextView) findViewById(R.id.textView6);
        tvpurchaseprice.setTextSize(Float.parseFloat(textsize));

        tvinternetprice = (TextView) findViewById(R.id.textViewinternet);
        tvinternetprice.setTextSize(Float.parseFloat(textsize));

        tvinternetrel = (TextView) findViewById(R.id.textViewrelevant);
        tvinternetrel.setTextSize(Float.parseFloat(textsize));

        tvmargin = (TextView) findViewById(R.id.margintext);
        tvmargin.setTextSize(Float.parseFloat(textsize));

        tvdiscount = (TextView) findViewById(R.id.textView66);
        tvdiscount.setTextSize(Float.parseFloat(textsize));

        tvactive = (TextView) findViewById(R.id.textViewa);
        tvactive.setTextSize(Float.parseFloat(textsize));

        //////////////////////////////////////////jimmy/////////////////////////////////////////////////////

        ProductId = (TextView) findViewById(R.id.product_Prodid);
        ProductId.setTextSize(Float.parseFloat(textsize));


        ProductBarcode = (TextView) findViewById(R.id.product_Barcode);
        ProductBarcode.setTextSize(Float.parseFloat(textsize));


        ProductName = (TextView) findViewById(R.id.product_Desc);
        ProductName.setTextSize(Float.parseFloat(textsize));

        MRP = (TextView) findViewById(R.id.product_MRP);
        MRP.setTextSize(Float.parseFloat(textsize));

        Manuf = (TextView) findViewById(R.id.product_manuf);
        Manuf.setTextSize(Float.parseFloat(textsize));

        MeasureUnit = (TextView) findViewById(R.id.product_MeasureUnit);
        MeasureUnit.setTextSize(Float.parseFloat(textsize));

        Sellingprice = (EditText) findViewById(R.id.product_Selling);
        Sellingprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,Integer.parseInt(sprice))});
        Sellingprice.setTextSize(Float.parseFloat(textsize));

        Purchaseprice = (EditText) findViewById(R.id.product_Purchase);
        Purchaseprice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,Integer.parseInt(pprice))});
        Purchaseprice.setTextSize(Float.parseFloat(textsize));

        Internet = (TextView) findViewById(R.id.product_InternetPrice);
        Internet.setTextSize(Float.parseFloat(textsize));

        Internetrelevant= (TextView) findViewById(R.id.product_internetrelevant);
        Internetrelevant.setTextSize(Float.parseFloat(textsize));

        Margin =(TextView)findViewById(R.id.product_margin);
        Margin.setTextSize(Float.parseFloat(textsize));

        productdiscount = (EditText) findViewById(R.id.product_discount);
        productdiscount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});
        productdiscount.setTextSize(Float.parseFloat(textsize));

        Active = (Spinner) findViewById(R.id.product_active);







/////////////////////////////////////////////hide item/////////////////////////////////
        Taxid = (TextView) findViewById(R.id.product_Tax);
        auto = (EditText)findViewById(R.id.product_complete);

        username =  login.b.getString("Pos_User");

        ActiveType = getResources().getStringArray(R.array.active_Status);
        adapterActiveType = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ActiveType);
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


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto) findViewById(R.id.myautocomplete);

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
                    Log.d("NishantSingh", "After text changed called ");
                    if (autoCompleteTextView.isPerformingCompletion()) {
                        Log.d("Nishu", "Performing completion ");
                        return;
                    }
                    String  x= "X-";


                    String userTypedString = (autoCompleteTextView.getText().toString().trim());

                    if (userTypedString.startsWith("0")||userTypedString.startsWith("1")||userTypedString.startsWith("3")
                            ||userTypedString.startsWith("4")
                            ||userTypedString.startsWith("5")||userTypedString.startsWith("6")||userTypedString.startsWith("7")
                            ||userTypedString.startsWith("8")||userTypedString.startsWith("9")) {

                        userTypedString = x.concat(autoCompleteTextView.getText().toString().trim());
                    }
                    if (userTypedString.equals("")) {
                        return;
                    }
                    Productlist = mydb.getAllProducts(userTypedString);


                    if (Productlist != null) {
                        if (adapter == null) {
                            adapter = new ProductAdapter(ActivityProduct.this, android.R.layout.simple_dropdown_item_1line, Productlist);
                            adapter.setProductList(Productlist);
                            autoCompleteTextView.setAdapter(adapter);
                            autoCompleteTextView.setThreshold(1);
                        } else {
                            adapter.setProductList(Productlist);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }
            };

            autoCompleteTextView.addTextChangedListener(mTextWatcher);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                    Log.d("Nishu99", "On click called ");

                    final DBhelper mydb = new DBhelper(ActivityProduct.this);
                    DecimalFormat format=new DecimalFormat("##.00");
                    Product value = (Product) parent.getItemAtPosition(pos);
                    ProductName.setText(value.getProductName());
                    ProductBarcode.setText(value.getProductBarcode());
                    ProductId.setText(value.getProductId());

                //   String dd=  ProductId.setText(value.getProductId());


                    Internet.setText( String.valueOf(format.format(Double.parseDouble(value.getInternet()))));
//                    Strength.setText(value.getStrength());

                    Manuf.setText(value.getManuf());
                    MeasureUnit.setText(value.getMeasureUnit());
                    //    Measure.setText(value.getMeasure());
                    Taxid.setText(value.getTaxid());
                    Sellingprice.setText(String.valueOf(format.format(Double.parseDouble(value.getSellingprice()))));
                    Sellingprice.requestFocus();
                    Purchaseprice.setText(String.valueOf(format.format(Double.parseDouble(value.getPurchaseprice()))));
                    MRP.setText(String.valueOf(format.format(Double.parseDouble(value.getMRP()))));
                    autoCompleteTextView.setText("");
                    Internetrelevant.setText(value.getInternetrelevant());
                    Margin.setText(value.getMargin());
                    productdiscount.setText(value.getProductdiscount());


                    SpinValue=value.getActive();
                    if (SpinValue.equals("Y"))
                    {
                        Active.setSelection(0);
                    }
                    if (SpinValue.equals("N"))
                    {
                        Active.setSelection(1);
                    }

                }
            });
            mydb = new DBhelper(this);
            auto = (EditText) findViewById(R.id.product_complete);
           // auto.requestFocus();
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
                    ProductAutoArraylist = mydb.getProductDetailsFromBarcode(barcodeFromScanner);

                    if (ProductAutoArraylist != null && ProductAutoArraylist.size() > 0) {

                        ProductAuto value = ProductAutoArraylist.get(0);
                        ProductName.setText(value.getAutoProductname());
                        ProductBarcode.setText(value.getAutoBarcode());
                        ProductId.setText(value.getAutoProductId());
                        Internetrelevant.setText(value.getAutoInternetrel());
                        productdiscount.setText(value.getAutodiscount());

                        SpinValue=value.getAutoActive();
                        if (SpinValue.equals("Y"))
                        {
                            Active.setSelection(0);
                        }
                        if (SpinValue.equals("N"))
                        {
                            Active.setSelection(1);
                        }
                        Internet.setText(value.getAutoInternetPrice());
//                        Strength.setText(value.getAutoStrength());
                        Manuf.setText(value.getAutoManuf());
                        MeasureUnit.setText(value.getAutoMeasureunit());
                        //        Measure.setText(value.getAutoMeasure());
                        Taxid.setText(value.getAutoTax());
                        Sellingprice.setText(value.getAutoSellingprice());
                        Purchaseprice.setText(value.getAutoPurchaseprice());
                        MRP.setText(value.getAutoMrp());
                        Margin.setText(value.getMargin());
                        ProductAutoArraylist.clear();
                    }
                }
            });
        Button Exit = (Button) findViewById(R.id.product_exit_button);
            Exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    view.startAnimation(Buttonok);
                    Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                    startActivity(intent);
                }
            });

            Button Update = (Button) findViewById(R.id.product_update_button);
            Update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  //  try {
                        view.startAnimation(Buttonok);

                        String value = ProductId.getText().toString();
                        Prod_Id_To_Update = value;
                    if (ProductId.getText().toString().matches(""))
                    {

                        Toast.makeText(getApplicationContext(), "Please select the product ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(Sellingprice.getText().toString().matches(""))
                    {
                        Sellingprice.setError("Please enter the Selling Price");
                        return;
                    }
                    if (Purchaseprice.getText().toString().matches(""))
                    {
                        Purchaseprice.setError("Please enter the Purchase Price");
                        return;
                    }
                  /*  if (Internet.getText().toString().matches(""))
                    {
                        Internet.setError("Please enter the Internet Price");
                        return;
                    }

*/               /*  if(productdiscount.getText().toString()==null)

                    {

                    }*/
                    try {
                        float selling = Float.parseFloat(Sellingprice.getText().toString());
                        float purchase = Float.parseFloat(Purchaseprice.getText().toString());
                        float mrp = Float.parseFloat(MRP.getText().toString());

                        if (selling > mrp) {
                            Toast.makeText(getApplicationContext(), "Please check entered value ", Toast.LENGTH_SHORT).show();


                            return;

                        }
                        if (purchase > selling) {
                            Toast.makeText(getApplicationContext(), "Please check entered value ", Toast.LENGTH_SHORT).show();

                            return;
                        }
                        if (purchase > mrp)
                        {
                            Toast.makeText(getApplicationContext(), "Please check entered value ", Toast.LENGTH_SHORT).show();

                            return;
                        }

                        if (SpinValue .matches("N"))
                        {
                            mydb.updatesalestock(Prod_Id_To_Update) ;

                        }
                        if (SpinValue .matches("Y"))
                        {
                            mydb.updatesalestockActive(Prod_Id_To_Update) ;

                        }

                        mydb.updateProduct(Prod_Id_To_Update, Sellingprice.getText().toString(),
                                Purchaseprice.getText().toString(), Internet.getText().toString(),
                                Internetrelevant.getText().toString(), SpinValue,Margin.getText().toString(),
                                user2.getText().toString(),productdiscount.getText().toString()) ;
                        mydb.updateProductCom(Prod_Id_To_Update, Sellingprice.getText().toString(),
                                Purchaseprice.getText().toString(), Internet.getText().toString(),
                                Internetrelevant.getText().toString(), SpinValue,user2.getText().toString(),productdiscount.getText().toString());

                        {

                            Toast.makeText(getApplicationContext(), "Product Updated", Toast.LENGTH_SHORT).show();
                            UpdateProduct();
                            Intent intent = new Intent(getApplicationContext(), Activity_masterScreen2.class);
                            startActivity(intent);




                        }
                    } catch (Exception ex) {

                    }


                }
            });

            Button Clear = (Button) findViewById(R.id.product_clear_button);
            Clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.startAnimation(Buttonok);
                    auto.requestFocus();
                    ProductId.setText("");
                    ProductBarcode.setText("");
                    Purchaseprice.setText("");
                    Sellingprice.setText("");
                    MRP.setText("");
                    Internetrelevant.setText("");
//                    ActiveType = getResources().getStringArray(R.array.active_Status);
//                    adapterInventoryType = new ArrayAdapter<String>(ActivityProduct.this,android.R.layout.simple_spinner_item,ActiveType);
//                    adapterInventoryType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    Internetrelevant.setAdapter(adapterInventoryType);
                    Internet.setText("");
//                Strength.setText("");
                    Taxid.setText("");
                    //        Measure.setText("");
                    MeasureUnit.setText("");
                    ProductName.setText("");
                    Manuf.setText("");
                    Active.setAdapter(null);
                    ActiveType = getResources().getStringArray(R.array.active_Status);
                    adapterActiveType = new ArrayAdapter<String>(ActivityProduct.this,android.R.layout.simple_spinner_item,ActiveType);
                    adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    Active.setAdapter(adapterActiveType);
                    autoCompleteTextView.setText("");
                    auto.setText("");


                }
            });




        }


    public void ProductPharmaDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                 PRODUCT PHARMA");
        alertDialog.setMessage("Objective\n" +
                "\n" +
                "The user selects the Product Pharma option to do the following activities.\n" +
                "\n" +
                "A. Activate the Product if the retailer is selling the same. This is a one time activity but it will reduce the master data and make the usage much more easier.\n" +
                "\n" +
                "B. Change the purchase price, selling price at a shop level. \n" +
                "\n" +
                "C. The user can change this information any time.\n" +
                "\n" +
                "Input Description\n" +
                "\n" +
                "Search - The user enters the product name or user can scan the bar code of the product for the data entry. \n" +
                "Fields Description:\n" +
                "\n" +
                "Product Id: The Product id of the product.\n" +
                "Barcode : The barcode for the product\n" +
                "Product Name: Name of the product\n" +
                "MRP - Maximum Retail Price\n" +
                "Manufacturer - The name of the manufacturer responsible for product existence.\n" +
                "Measure Unit: The unit under which product is measured.\n" +
                "Selling Price - Selling price of the product\n" +
                "Purchase price - Purchase price of the product\n" +
                "Internet price - Internet price of the product\n" +
                "Internet Rel - Internet relevance of the product\n" +
                "Active - Product is active 'Y' or in-Active 'N' for the store to sell.\n" +
                "Margin - Margin of the product which is calculated based on (MRP – Purchase Price)/MRP*100\n" +

                "\n" +
                "\n" +
                "Rules :\n" +
                "\n" +
                "MRP should be greater than or equal to purchase price and selling price.\n" +
                "Selling price cannot be lower than the purchase price.\n" +
                "The purchase price and selling price can have maximum of 7 digits with values updated to 2 decimal places.\n" +
                "\n" +
                "Functions :\n" +
                "There are 3 options below:\n" +
                "\tA. UPDATE- To update the record.\n" +
                "  B. CLEAR- Clear the record from the window and make it available for re-entry.\n" +
                "  C. EXIT - Go back to the menu option.\n" +
                "\n");
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



        mydb=new DBhelper(ActivityProduct.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityProduct.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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
                Intent i = new Intent(ActivityProduct.this, Activity_masterScreen2.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityProduct.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                ProductPharmaDialog();
                return true;

            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityProduct.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityProduct.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;


            case R.id.action_settinginv:
                Intent in=new Intent(ActivityProduct.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityProduct.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityProduct.this ,login.class);
        startActivity(intent);
    }


    @Override
    public String toString() {
        return item;
    }




    public void UpdateProduct() {

        final DBhelper mydb = new DBhelper(ActivityProduct.this);

      final String  active =Active.getSelectedItem().toString();
        final String   purchase_price =Purchaseprice.getText().toString().trim();
        final String   selling_price =Sellingprice.getText().toString().trim();
        final String   product_discount =productdiscount.getText().toString().trim();
        final String pos_user = user2.getText().toString();

        prod_id =ProductId.getText().toString().trim();

        PersistenceManager.saveStoreId(ActivityProduct.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        final String   store_id= PersistenceManager.getStoreId(ActivityProduct.this);

        class UpdateProduct extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
               // loading = ProgressDialog.show(ActivityProduct.this, "UPDATING...", "Wait...", false, false);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
              //  loading.dismiss();
               // Toast.makeText(ActivityProduct.this, s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(Void... params) {
                try{
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Config.RETAIL_STORE_PROD_STORE_ID,store_id);
                hashMap.put(Config.RETAIL_STORE_PROD_ACTIVE,active);
                hashMap.put(Config.RETAIL_STORE_PROD_ID,prod_id);
                hashMap.put(Config.RETAIL_STORE_PROD_PURCHASE_PRICE,purchase_price);
                hashMap.put(Config.RETAIL_STORE_PROD_SELLING_PRICE,selling_price);
                    hashMap.put(Config.RETAIL_STORE_PROD_LOCAL_DISCOUNT,product_discount);

                    hashMap.put(Config.Retail_Pos_user,pos_user);



                JSONParserSync rh = new JSONParserSync();
                    //JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/retail_store_prod.jsp",hashMap);
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_PRODUCT,hashMap);
                    Log.d("Login attempt", s.toString());

                // Success tag for json
                success = s.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("Successfully Login!", s.toString());


                    mydb.updateproductflag(prod_id);


                    return s.getString(TAG_MESSAGE);
                } else {

                    return s.getString(TAG_MESSAGE);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;            }
        }
        UpdateProduct updateproduct = new UpdateProduct();
        updateproduct.execute();
    }


}
