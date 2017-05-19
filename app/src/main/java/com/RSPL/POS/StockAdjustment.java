package com.RSPL.POS;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.Inventorystockadapter;
import Adapter.Stockproductadapter;
import Adapter.stockvendernameadapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryStockadjustmentmodel;
import Pojo.Inventorymodel;

public class StockAdjustment extends Activity {
    ActionBar actionBar;
    AutoCompleteTextView autoCompleteTextView;

    AutoCompleteTextView ProductName;

    private TextWatcher mTextWatcher;
    private TextWatcher pTextWatcher;

    String iteam;
    TextView user2;

    ArrayList<Inventorymodel> arrayList;

    Inventorystockadapter adapter;
    stockvendernameadapter vendernameadapter;
    Stockproductadapter stockproductadapterobject;

    ArrayList<InventoryStockadjustmentmodel> arrayList1, arrayList2;

    ListView listView;
    DBhelper helper;
    Button update,expiredProduct;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";



    String sq;
    TextView tvproductname,tvbatch,tvexp,tvmrp,tvpprice,tvsprice,tvqty,tvtotal;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_adjustment);

        update = (Button) findViewById(R.id.addtolist_button);
        expiredProduct = (Button) findViewById(R.id.expiredProduct_button);
        Button clrbtn = (Button) findViewById(R.id.clear);
        autoCompleteTextView = (CustomAuto) findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);


        ProductName = (CustomAuto) findViewById(R.id.InventoryautoProductIdandName);


        listView = (ListView) findViewById(R.id.listView);



        ProductName.setThreshold(1);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        helper = new DBhelper(StockAdjustment.this);
        Decimal valuetextsize = helper.getStoreprice();
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



//////////////////////////////////////jimmyy///////////////////////////////////
        tvproductname = (TextView) findViewById(R.id.product11);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) findViewById(R.id.Batchno);
        tvbatch.setTextSize(Float.parseFloat(textsize));
        tvexp = (TextView) findViewById(R.id.Exp_dt);
        tvexp.setTextSize(Float.parseFloat(textsize));
        tvmrp = (TextView) findViewById(R.id.productmrp);
        tvmrp.setTextSize(Float.parseFloat(textsize));
        tvpprice = (TextView) findViewById(R.id.product9);
        tvpprice.setTextSize(Float.parseFloat(textsize));
        tvsprice = (TextView) findViewById(R.id.productsprice);
        tvsprice.setTextSize(Float.parseFloat(textsize));

        tvqty = (TextView) findViewById(R.id.productQty);
        tvqty.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) findViewById(R.id.producttotalqty);
        tvtotal.setTextSize(Float.parseFloat(textsize));





        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);


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
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                arrayList = helper.getInventoryName(userTypedString);
                if (arrayList != null) {
                    if (vendernameadapter == null) {
                        vendernameadapter = new stockvendernameadapter(StockAdjustment.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                        vendernameadapter.setList(arrayList);

                        autoCompleteTextView.setAdapter(vendernameadapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        vendernameadapter.setList(arrayList);
                        vendernameadapter.notifyDataSetChanged();
                    }
                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                String VendorName = parent.getItemAtPosition(position).toString();

                arrayList2 = helper.getProductStockAdjustmentForDistri(VendorName);
              /*  for(int i=0;i<arrayList2.size();i++) {

                    for (int j = i + 1; j < arrayList2.size(); j++) {

                        if (arrayList2.get(i).getProductName().compareTo(arrayList2.get(j).getProductName()) < 0) {

                            InventoryStockadjustmentmodel temp = arrayList1.get(j);
                            arrayList1.set(j, arrayList1.get(i));
                            arrayList1.set(i,temp);

                        }
                    }
*/
                    stockproductadapterobject = new Stockproductadapter(StockAdjustment.this, arrayList2, android.R.layout.simple_dropdown_item_1line, null);
                    listView.setAdapter(stockproductadapterobject);
                    stockproductadapterobject.setList(arrayList2);
                    //  Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
                    ProductName.setText("");

                    stockproductadapterobject.notifyDataSetChanged();

               // }
                //  Toast.makeText(getApplicationContext(), "selected store " + Value, Toast.LENGTH_SHORT).show();
            }
        });


        helper = new DBhelper(this);
        ProductName.setThreshold(3);

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

                Log.d("Debuging", "After text changed called ");
                if (ProductName.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = ProductName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                arrayList1 = helper.getstockProductName(userTypedString);
                if (arrayList1 != null) {
                    if (adapter == null) {
                        adapter = new Inventorystockadapter(StockAdjustment.this, android.R.layout.simple_dropdown_item_1line, arrayList1);
                        adapter.setList(arrayList1);

                        ProductName.setAdapter(adapter);
                        ProductName.setThreshold(1);
                    } else {
                        adapter.setList(arrayList1);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        };

        ProductName.addTextChangedListener(pTextWatcher);
        ProductName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
              /*  String userTypedString = ProductName.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }*/
                InventoryStockadjustmentmodel resval = (InventoryStockadjustmentmodel) parent.getItemAtPosition(position);
                addProductToPurchaseList(resval);

                //  Toast.makeText(getApplicationContext(), "selected store " + Value, Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);
                try {

                    int pos = 0;
                    for (InventoryStockadjustmentmodel item : stockproductadapterobject.getlist()) {
                        String s=String.valueOf(item.getProductQuantity());
                        Log.d("ddd",s.toString());

                        if (item.getPprice() <= 0 || item.getSprice() <= 0) {
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }
                      /* else if ( item.getBatch_No()== null||item.getBatch_No().trim().equals("")||item.getBatch_No()=="batch"){

                           item.setBatch_No(getSystemCurrentTime());
                       }*/
                        if (item.getConvMrp() < item.getPprice()) {

                            Toast.makeText(getApplicationContext(), "Mrp never less then purchase price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (item.getConvMrp() < item.getSprice()) {
                            Toast.makeText(getApplicationContext(), "Mrp never less then selling price", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }
                    helper.updateStockAdjustment(stockproductadapterobject.getlist(), autoCompleteTextView.getText().toString());
                    Updatestokeadjustment();
                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);

                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }
            }
        });


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

        expiredProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockAdjustment.this, ShowStockAdjustmentListActivity.class);
                startActivity(intent);
            }
        });


    }


  /*  private void addProductToPurchaseList(InventoryStockadjustmentmodel resval1) {
        arrayList2 = helper.getProductStockAdjustment(resval1);
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        stockproductadapterobject = new Stockproductadapter(StockAdjustment.this, arrayList2, android.R.layout.simple_dropdown_item_1line, resval1);
        listView.setAdapter(stockproductadapterobject);
        stockproductadapterobject.setList(arrayList2);
        Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
        ProductName.setText("");
        arrayList1.clear();
    }
*/
  private void addProductToPurchaseList(InventoryStockadjustmentmodel resval1) {
      if (stockproductadapterobject == null) {
          Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
          stockproductadapterobject = new Stockproductadapter(StockAdjustment.this, new ArrayList<InventoryStockadjustmentmodel>(), android.R.layout.simple_dropdown_item_1line, resval1);
          listView.setAdapter(stockproductadapterobject);
      }
      int pos = stockproductadapterobject.addProductToList(resval1);
      Log.i("&&&&&&&&", "Adding " + resval1 + " to Product List..");
      stockproductadapterobject.notifyDataSetChanged();
      ProductName.setText("");
      //setSummaryRow();
      adapter.setList(arrayList1);
      arrayList1.clear();
      listView.smoothScrollToPosition(pos);
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

                    stockproductadapterobject.clearAllRows();

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


    public void Updatestokeadjustment() {


        for (InventoryStockadjustmentmodel purchase : stockproductadapterobject.getlist()) {

            final String mrp = String.valueOf(purchase.getMrp());
            final String sprice = String.valueOf(purchase.getSprice());
            final String conmulqty = String.valueOf(purchase.getProductQuantity());
            final String productname = purchase.getProductName();
            final String batchno = String.valueOf(purchase.getBatchno());
            final String pprice = String.valueOf(purchase.getPprice());
            final String vendorname = autoCompleteTextView.getText().toString();

            sq=String.valueOf(purchase.getProductQuantity());

            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;


                @Override
                protected String doInBackground(Void... params) {
                    try {

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_Inventory_mrp, mrp);
                        hashMap.put(Config.Retail_Inventory_selling, sprice);
                        hashMap.put(Config.Retail_Inventory_name, productname);
                        hashMap.put(Config.Retail_Inventory_p_price, pprice);
                        hashMap.put(Config.Retail_Inventory_batchno, batchno);
                        hashMap.put(Config.Retail_Inventory_Vendorname, vendorname);
                        hashMap.put(Config.Retail_Inventory_conmulqty, conmulqty);


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.STOCK_ADJUSTMENT, hashMap);
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully StockAdj!", s.toString());

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
                    //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }

            }
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_master_screen1, menu);



        MenuItem item = menu.findItem(R.id.spinner_user);
        item.setVisible(false);
        final MenuItem item2 = menu.findItem(R.id.TextView);
        item2.setVisible(false);


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

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {


            case R.id.action_settings:

                Intent i = new Intent(StockAdjustment.this, Activitypurchase.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(StockAdjustment.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                // help_stockadjustment();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(StockAdjustment.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/

            case R.id.action_settinginv:
                Intent in=new Intent(StockAdjustment.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(StockAdjustment.this,ActivitySalesbill.class);
                startActivity(s);
                return true;
            case R.id.action_settinghel:
                Intent login = new Intent(StockAdjustment.this,login.class);
                startActivity(login);

                return true;

            case R.id.action_Masterscn1:
                Intent intent = new Intent(StockAdjustment.this,Activity_masterScreen1.class);
                startActivity(intent);

                return true;

            case R.id.action_logout:
                Logout();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public void Logout()
    {
        Intent intent = new Intent(StockAdjustment.this ,login.class);
        startActivity(intent);
    }


    public void help_stockadjustment() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("          STOCK ADJUSTMENT      ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "\n"
        );
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }
}