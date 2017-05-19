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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.ReportVendorReturnAdapter;
import Adapter.ReportSearchReturnIdAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.ReportVendorReturnModel;

public class ReportVendorReturnActivity extends Activity {

    ListView listview;
    AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;
    ReportVendorReturnAdapter PaymentListAdapter;
    ArrayList<ReportVendorReturnModel> searchidList;
    ArrayList<ReportVendorReturnModel> arrayPaymentList;
    ReportSearchReturnIdAdapter searchIdAdapter;
    ActionBar actionBar;
    Button emailbutton;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    TextView tvuser,tvvendornames,tvreturnid,tvbatch,tvexp,tvprice,tvreason,tvqty,tvproduct,tvuom,tvtotal;
    String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_vendor_return);
        helper = new DBhelper(ReportVendorReturnActivity.this);
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

        tvreturnid = (TextView)findViewById(R.id.id1);
        tvreturnid.setTextSize(Float.parseFloat(textsize));


        tvuser = (TextView) findViewById(R.id.tvUserNm);
        tvuser.setTextSize(Float.parseFloat(textsize));

        tvvendornames = (TextView) findViewById(R.id.tvDsbtrNm);
        tvvendornames.setTextSize(Float.parseFloat(textsize));


        tvreason = (TextView)findViewById(R.id.tvNm);
        tvreason.setTextSize(Float.parseFloat(textsize));

        tvproduct = (TextView) findViewById(R.id.tvName);
        tvproduct.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) findViewById(R.id.tvDsbtrNo);
        tvbatch.setTextSize(Float.parseFloat(textsize));

        tvexp = (TextView) findViewById(R.id.tvDsbtrDate);
        tvexp.setTextSize(Float.parseFloat(textsize));

        tvprice = (TextView) findViewById(R.id.tvDsbtrPrice);
        tvprice.setTextSize(Float.parseFloat(textsize));

        tvqty = (TextView) findViewById(R.id.tvDsbtr);
        tvqty.setTextSize(Float.parseFloat(textsize));

        tvuom = (TextView) findViewById(R.id.tvDsbtrUnit);
        tvuom.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) findViewById(R.id.tvDsbtrTotal);
        tvtotal.setTextSize(Float.parseFloat(textsize));



        emailbutton=(Button)findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });

        actionBar=getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        listview = (ListView) findViewById(R.id.lv_vendorReturnReport);
        Log.e("***********Lt1*******", listview.toString());
        idTextView = (CustomAuto) findViewById(R.id.reprtRetrnIdTextView);

        idTextView.setThreshold(1);

        //******************************** grn id selected from here********************************************************************************************/

        idTextView.setThreshold(1);
        idTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (idTextView.getText().toString().matches("")) {
                    // Toast.makeText(getApplicationContext(), "Please select the Grn Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("Debuging", "After text changed called ");
                if (idTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = idTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchidList = helper.getVendorReturnId(userTypedString);
                if (searchidList != null) {
                    if (searchIdAdapter == null) {
                        searchIdAdapter = new ReportSearchReturnIdAdapter(ReportVendorReturnActivity.this, android.R.layout.simple_dropdown_item_1line, searchidList);
                        searchIdAdapter.setList(searchidList);
                        idTextView.setAdapter(searchIdAdapter);
                        idTextView.setThreshold(1);
                    } else {
                        searchIdAdapter.setList(searchidList);
                        searchIdAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        idTextView.addTextChangedListener(idTextWatcher);
        idTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                arrayPaymentList = helper.getVendorReturnReport(Value);
                if (PaymentListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    PaymentListAdapter = new ReportVendorReturnAdapter(new ArrayList<ReportVendorReturnModel>(), ReportVendorReturnActivity.this);
                    PaymentListAdapter.setList(arrayPaymentList);
                    listview.setAdapter(PaymentListAdapter);
                    idTextView.setText("");
                } else {
                    PaymentListAdapter.setList(arrayPaymentList);
                    PaymentListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
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
        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i = new Intent(ReportVendorReturnActivity.this, PurchasingReportActivity.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                help_vendorreturnreports_dialog();
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ReportVendorReturnActivity.this);
                showIncentive.show();
                return true;
/*
            case R.id.action_settingpurchase:
                Intent p=new Intent(ReportVendorReturnActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(ReportVendorReturnActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ReportVendorReturnActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent intent=new Intent(ReportVendorReturnActivity.this,Activity_masterScreen1.class);
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
        Intent intent = new Intent(ReportVendorReturnActivity.this ,login.class);
        startActivity(intent);
    }


    public void insertEmaildata() {

        try{
            DBhelper dBhelper = new DBhelper(getApplicationContext());

            dBhelper.insertemailvendorreturn(idTextView.getText().toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }





    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }



    public void Updatevendorreturnreport() {


        for (ReportVendorReturnModel purchase : PaymentListAdapter.getList()) {


            //final String posuser = purchase.getUserNm();
            final String vendor_nm=purchase.getVendrNm();
            final String reportprodname = String.valueOf(searchIdAdapter.getList());
            final String reportprodid = String.valueOf(searchIdAdapter.getList());
            /*final String reportbatchno = purchase.getBatchNo();
            final String reportexpdate = purchase.getExpDate();
            final String reportreason = purchase.getReason();
            final String reportqty= purchase.getQty();
            final String reportp_price = purchase.getPPrice();
            final String reporttotal = purchase.getTotal();
            final String reportuom = purchase.getUom();*/

            PersistenceManager.saveStoreId(getApplicationContext(),helper .getStoreid().toString().replace("[", "").replace("]", ""));
            PersistenceManager.getStoreId(getApplicationContext());
            final String  store_id= PersistenceManager.getStoreId(getApplicationContext());



            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {

                    try{
                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                        hashMap.put(Config.Retail_report_vendornamepurchasing, vendor_nm);
                        hashMap.put(Config.RETAIL_STR_PROD_NAME, reportprodname);
                        hashMap.put(Config.RETAIL_STR_VENDOR_RETURN_ID, reportprodid);

                      //  hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);

                        /*hashMap.put(Config.Retail_report_Batch, reportbatchno);
                        hashMap.put(Config.Retail_report_expdate, reportexpdate);
                        hashMap.put(Config.RETAIL_STR_REASON_RETURN, reportreason);
                        hashMap.put(Config.Retail_report_qty,reportqty);
                        hashMap.put(Config.FRAGMENT_P_PRICE,reportp_price);
                        hashMap.put(Config.Retail_Pos_user,posuser);
                        hashMap.put(Config.FRAGMENT_TOTAL,reporttotal);
                        hashMap.put(Config.FRAGMENT_UOM,reportuom);*/



                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.RETAIL_STR_VENDOR_DETAIL_RETURN_MAIL, hashMap);
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            // return s.getString(TAG_MESSAGE);
                        } else {

                            //return s.getString(TAG_MESSAGE);

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
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }

    }




    public  void emailbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {

                    insertEmaildata();
                    Updatevendorreturnreport();
                    Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();
                    finish();


                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public  void help_vendorreturnreports_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("       VENDOR RETURN REPORT   ");
        alertDialog.setMessage("\t\n" +
                "Goods returned to the vendor based on stock provided by the vendor. The return reason is captured as well to define the course of action.\n" +
                "\n"

        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
            startActivity(intent);*/
            }
        });


        alertDialog.show();
    }

}