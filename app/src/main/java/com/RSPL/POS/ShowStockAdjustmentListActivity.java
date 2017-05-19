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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import Adapter.ShowStockAdjustmentAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.ExpiryProductStockModel;


public class ShowStockAdjustmentListActivity extends Activity implements View.OnClickListener {

    ListView listView;
    ShowStockAdjustmentAdapter expireproductAdapter;
    ArrayList<ExpiryProductStockModel> alldata;
    DBhelper helper;
    ActionBar actionBar;
    Button Sbtbtn;
    private TextView GrandTotal;
    private TextView Totalitems;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String iteam;
    TextView user2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expiredproduct_list);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        helper = new DBhelper(this);

        alldata = helper.getExpiredProductReport();

        Sbtbtn = (Button) findViewById(R.id.delete_button);
        Sbtbtn.setOnClickListener(this);

        GrandTotal = (TextView) findViewById(R.id.ReportPurchasingGrandTotal);
        Totalitems = (TextView) findViewById(R.id.ReportPurchasingTotalitem);

        listView = (ListView) findViewById(R.id.lv_StockExpiredProduct);
        Log.e("***********Lt1*******", listView.toString());

        expireproductAdapter = new ShowStockAdjustmentAdapter(this, alldata, android.R.layout.simple_expandable_list_item_1);
        listView.setAdapter(expireproductAdapter);

        setSummaryRow();

    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = expireproductAdapter.getGrnTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);

        int Getitem = expireproductAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }

    @Override
    public void onClick(View v) {

        if (v == Sbtbtn) {

            helper.DeleteAllExpiroedProduct(expireproductAdapter.getList());
            Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), ShowStockAdjustmentListActivity.class);
            startActivity(intent);
            // UpdateStockAdjustmentList();
        }
    }

    public void UpdateStockAdjustmentList() {

        for (ExpiryProductStockModel purchase : expireproductAdapter.getList()) {

            final String conmulqty = String.valueOf(purchase.getStockQty());
            final String productname = purchase.getProductName();
            final String batchno = String.valueOf(purchase.getBatchno());
            final String pprice = String.valueOf(purchase.getPprice());
            final String expdate = String.valueOf(purchase.getExpdate());


            class UpdateStockAdjustmentList extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;


                @Override
                protected String doInBackground(Void... params) {
                    try {

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_Inventory_expdate, expdate);
                        hashMap.put(Config.Retail_Inventory_name, productname);
                        hashMap.put(Config.Retail_Inventory_p_price, pprice);
                        hashMap.put(Config.Retail_Inventory_batchno, batchno);
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
            UpdateStockAdjustmentList updatereport = new UpdateStockAdjustmentList();
            updatereport.execute();
        }

    }

    public  void InventoryHelpDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle(" EXPIRED PRODUCT ");
        alertDialog.setMessage(" \n" +
                "Objective:\n" +
                "\t\n" +
                "There are products in the stock which are already expired \n" +
                "and DeleteAll button for deleting all the expired products from the stock \n" +
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
                Intent i = new Intent(ShowStockAdjustmentListActivity.this, StockAdjustment.class);
                startActivity(i);
                return true;

            case R.id.action_settinghelp:
                InventoryHelpDialog();
                return true;

            case R.id.action_settinginv:
                Intent in = new Intent(ShowStockAdjustmentListActivity.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ShowStockAdjustmentListActivity.this);
                showIncentive.show();
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(ShowStockAdjustmentListActivity.this, ActivitySalesbill.class);
                startActivity(s);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}