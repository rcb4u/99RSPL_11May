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

import Adapter.Inventoryadapter;
import Adapter.SalesReturnProductAdapter;
import Adapter.SalesReturnwithoutinvoiceno;
import Adapter.lostsaleadapter;
import Adapter.lostsaleslistadapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Inventorymodel;
import Pojo.Registeremployeesmodel;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.Visibility;
import Pojo.displaypojo;
import Pojo.lostsale;

public class Activity_Lost_Sale extends Activity {
    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;


    private TextWatcher xtWatcher;
    AutoCompleteTextView ProductName;
    lostsaleadapter productNmAdaptor;
    lostsaleslistadapter ReturnListAdapter;
    ArrayList<lostsale> ProductNameArrayList;
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


    DBhelper db;

    TextView tvproductname,tvexp,tvbatch,tvsprice,tvqty,tvuom,tvtotal,tvreason,tvdiscount,tvgrand;
    String backroundcolour,colorchange;


    //            private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity__lost__sale);
        listView = (ListView) findViewById(R.id.lv_PurchaseProduct);
        ProductName = (CustomAuto) findViewById(R.id.autoProductName);

        clear = (Button) findViewById(R.id.clear);

        submit = (Button) findViewById(R.id.salesreturn_submit);
        Grandtotal = (TextView) findViewById(R.id.grandtotal_textt);
      //  Grandtotal.setTextSize(Float.parseFloat(textsize));
        Totalitems = (TextView) findViewById(R.id.totalitem_textt);
       // Totalitems.setTextSize(Float.parseFloat(textsize));



        actionBar = getActionBar();

        username = login.b.getString("Pos_User");


        //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(Activity_Lost_Sale.this);
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
     /*   LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));
*/
        db = new DBhelper(this);


////////////////////////////////////////////////////////////////////////////////////////////////


       ProductName.setThreshold(1);

        xtWatcher = new TextWatcher() {
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
                ProductNameArrayList = db.getsalelostData(userTypedString);
                if (ProductNameArrayList != null) {
                    if (productNmAdaptor == null) {
                        productNmAdaptor = new lostsaleadapter(Activity_Lost_Sale.this, android.R.layout.simple_dropdown_item_1line, ProductNameArrayList);
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
        ProductName.addTextChangedListener(xtWatcher);
        ProductName.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //  Cursor curral=(Cursor)parent.getItemAtPosition(position);
                        // PurchaseProductModel resval1=(PurchaseProductModel) parent.getItemAtPosition(position);
                        lostsale result = (lostsale) parent.getItemAtPosition(position);
                        if (ReturnListAdapter == null) {

                            Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                            ReturnListAdapter = new lostsaleslistadapter(Activity_Lost_Sale.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<lostsale>());
                            listView.setAdapter(ReturnListAdapter);

                        }
                        int pos = ReturnListAdapter.addProductToList(result);

                        Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                        ReturnListAdapter.notifyDataSetChanged();
                        ProductName.setText("");

                        setSummaryRow();

                    }


                });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db.insertlostdata(ReturnListAdapter.getList());
                Intent intent = new Intent(getApplicationContext(), ActivitySalesbill.class);
                startActivity(intent);
                Updatelostsale();


            }
            });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clearbuttondialog();
            }
        });


    }





    public void clearbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {

                    listView.setAdapter(null);

                    Totalitems.setText("");
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

        alertDialog.show();
    }
    public void grandtotal()
    {
        float Getval = ReturnListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = Float.toString(Getval);
        Grandtotal.setText(GrandVal);
    }
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

        mydb=new DBhelper(Activity_Lost_Sale.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(Activity_Lost_Sale.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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


                Intent i = new Intent(Activity_Lost_Sale.this, ActivitySales.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_Lost_Sale.this);
                showIncentive.show();
                return true;

                 /*   case R.id.action_settingpurchase:
                        Intent p = new Intent(Activity_Salesreturn_withoutinvoiceno.this, PurchaseActivity.class);
                        startActivity(p);
                        return true;
*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_Lost_Sale.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in = new Intent(Activity_Lost_Sale.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(Activity_Lost_Sale.this, ActivitySalesbill.class);
                startActivity(s);
                return true;


        }

        return super.onOptionsItemSelected(item);
    }


    public String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void Updatelostsale() {

        for (lostsale purchase : ReturnListAdapter.getList()) {

            final DBhelper mydb = new DBhelper(Activity_Lost_Sale.this);


            final String tri_id = getSystemCurrentTime();
            final String prodname = purchase.getSaleproductname();
            final String prodid = purchase.getProdid();
            final String sellingprice = String.valueOf(purchase.getSalesellingprice());
            final String qty = String.valueOf(purchase.getqty());
            final String total = String.valueOf(purchase.gettotal());
            final String saledate = getDate();




            PersistenceManager.saveStoreId(Activity_Lost_Sale.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
            store_id = PersistenceManager.getStoreId(Activity_Lost_Sale.this);


            class UpdateSalesReturn_WithoutPO extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {

                    try {
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.Lost_tri_id, tri_id);
                        hashMap.put(Config.lost_storeid, store_id);
                        hashMap.put(Config.lost_prodname, prodname);
                        hashMap.put(Config.lost_prodid, prodid);
                        hashMap.put(Config.lost_s_price, sellingprice);
                        hashMap.put(Config.lostqty, qty);
                        hashMap.put(Config.losttotal, total);
                        hashMap.put(Config.salesdate, saledate);


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.SALES_LOST, hashMap);
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








}

