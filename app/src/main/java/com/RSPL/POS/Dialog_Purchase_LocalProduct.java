package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import Adapter.Purchase_LocalProduct_Adapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.LocalProduct;


public class Dialog_Purchase_LocalProduct extends Activity  {
    ActionBar actionBar;
    EditText localproductname,localbarcode,localmrp,localsellingprice,localpurchaseprice,localtaxid,uom;
    String localProd_Id_To_Update;
    TextView localproductid,localmargin;
    DBhelper mydb;
    TextWatcher mTextWatcher;
    public static String username = null;

    String pprice ;
    String sprice;
    String mrpdecimal;




    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String  local_prod_id;




    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;
    CustomAlphaNumericKeyboard mCustomKeyboard;
    DialogKeyboard dialogKeyboard;
    mDialogKeybaord mDialogKeybaord;


    Purchase_LocalProduct_Adapter adapter;
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
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog__purchase__loca_lproduct);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        username =  login.b.getString("Pos_User");

        mydb = new DBhelper(Dialog_Purchase_LocalProduct.this);

        Decimal value = mydb.getStoreprice();
        mrpdecimal=value.getDecimalmrp();
        pprice=value.getDecimalpprice();
        sprice=value.getDecimalsprice();

        //   mrp =  Activity_decimal.b.getInt("MRP_Decimal");
        //  pprice =  Activity_decimal.b.getInt("P_Price_Decimal");
        // sprice =  Activity_decimal.b.getInt("S_Price_Decimal");



        try {
            actionBar = getActionBar();
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setIcon(R.drawable.w);
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }

        Exit = (Button) findViewById(R.id.localprodexit);
        Update = (Button) findViewById(R.id.localprod_edit_button);
        localproductid = (TextView)findViewById(R.id.localprod_prodid);
        localproductname= (EditText)findViewById(R.id.localprod_prodname);
        localbarcode =(EditText)findViewById(R.id.localprod_barcode);
        localpurchaseprice =(EditText) findViewById(R.id.localprod_purchaseprice) ;
        localsellingprice =(EditText)findViewById(R.id.localprod_sellingprice);
        discount = (EditText)findViewById(R.id.discount);

        discount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3,2)});
        localmrp = (EditText)findViewById(R.id.localprod_mrp);

        auto = (EditText)findViewById(R.id.product_auto_complete);
        auto.requestFocus();
        uom = (EditText)findViewById(R.id.uom);


                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_product2, R.xml.fractional_keyboard );
                customFractionalKeyboard.registerEditText(R.id.localprod_purchaseprice);


                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_product3, R.xml.fractional_keyboard );
                customFractionalKeyboard.registerEditText(R.id.localprod_sellingprice);






                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_product4, R.xml.fractional_keyboard );

                customFractionalKeyboard.registerEditText(R.id.localprod_mrp);



                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_product, R.xml.fractional_keyboard );

                customFractionalKeyboard.registerEditText(R.id.localprod_barcode);





                customFractionalKeyboard= new CustomFractionalKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_discount, R.xml.fractional_keyboard );
                customFractionalKeyboard.registerEditText(R.id.discount);




        dialogKeyboard = new DialogKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_prodname, R.xml.alphanumerickeyboard);

        dialogKeyboard.registerEditText(R.id.localprod_prodname);





        dialogKeyboard = new DialogKeyboard(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_prodname, R.xml.alphanumerickeyboard);

        dialogKeyboard.registerEditText(R.id.uom);



        localmrp = (EditText)findViewById(R.id.localprod_mrp);
      /*  if (mrp < 1  )
        {
            mrp = 2;
        }*/
        localmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(mrpdecimal))});


        localsellingprice =(EditText)findViewById(R.id.localprod_sellingprice);
       /* if (sprice < 1  )
        {
            sprice = 2;
        }*/
        localsellingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(sprice))});

        localpurchaseprice =(EditText)findViewById(R.id.localprod_purchaseprice);
       /* if (pprice < 1  )
        {
            pprice = 2;
        }*/
        localpurchaseprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7,Integer.parseInt(pprice))});

        localactive = (Spinner)findViewById(R.id.localprod_active);
        localmargin = (TextView)findViewById(R.id.localprod_margin);
        // localtaxid =(EditText)findViewById(R.id.localprod_Taxid);

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
//
//        localactive.setAdapter(adapterActiveType);
//        localactive.setThreshold(1);
//        // Business Type Item Selected Listener
//        localactive.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//  adapterActiveType = new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, ActiveType);
//            @Override
//            public void onItemClick(AdapterView<?> adapter, View v,
//                                    int position, long id) {
//                // On selecting a spinner item
//                item = adapter.getItemAtPosition(position).toString();
//
////
//            }
//
//        });

        try
        {
            mydb = new DBhelper(this);
            autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);

            autoCompleteTextView.setDropDownHeight(300);


            mDialogKeybaord = new mDialogKeybaord(Dialog_Purchase_LocalProduct.this, R.id.keyboard_local_prodname, R.xml.alphanumerickeyboard);

            mDialogKeybaord.registerEditText(R.id.myautocomplete);



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
                            adapter = new Purchase_LocalProduct_Adapter(Dialog_Purchase_LocalProduct.this, android.R.layout.simple_dropdown_item_1line, localproductlist);
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

                    final DBhelper mydb = new DBhelper(Dialog_Purchase_LocalProduct.this);

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
                    Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
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
                        adapterActiveType = new ArrayAdapter<String>(Dialog_Purchase_LocalProduct.this, android.R.layout.simple_spinner_item, ActiveType);
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

                        mydb.updatelocalProduct(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue, localmargin.getText().toString(),username,discount.getText().toString(),uom.getText().toString());


                        mydb.updatelocalProductCom(localProd_Id_To_Update, localproductname.getText().toString(), localbarcode.getText().toString(), localmrp.getText().toString(),
                                localsellingprice.getText().toString(), localpurchaseprice.getText().toString(), SpinValue,username,discount.getText().toString(),uom.getText().toString());

                        Toast.makeText(getApplicationContext(), "Local Product Updated", Toast.LENGTH_SHORT).show();
                        updateLocalProduct();
                        Intent intent = new Intent(getApplicationContext(), PurchaseActivity.class);
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
                Intent i=new Intent(Dialog_Purchase_LocalProduct.this,Activity_masterScreen2.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                LocalProducthelp();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Dialog_Purchase_LocalProduct.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(Dialog_Purchase_LocalProduct.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;
            case R.id.action_settingsales:
                Intent s=new Intent(Dialog_Purchase_LocalProduct.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            //noinspection SimplifiableIfStatement
        }
        return super.onOptionsItemSelected(item);
    }






    public void updateLocalProduct() {


        final DBhelper mydb = new DBhelper(Dialog_Purchase_LocalProduct.this);

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


        PersistenceManager.saveStoreId(Dialog_Purchase_LocalProduct.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
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



    public void LocalproductUpdateScript() {


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

                    executerStore(SUPER_USER);
                    String s= executerStore(LOCAL_PROD);
                    Log.e("@@@Script Called",s);}
                catch (Exception e){

                    e.printStackTrace();
                }

                return null;
            }
        }

        UpdateShell updateShell= new UpdateShell();
        updateShell.execute();

    }


    public String executerStore(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/local_prod.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

   /* public String DecimalDigitsInput(String mrp, float x){
        //  mrp =  Activity_decimal.b.getString("MRP_Decimal");
        final NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setMaximumFractionDigits(Integer.parseInt(mrp));
        final String resultS = numFormat.format(x);
        return resultS;
    }*/

}


