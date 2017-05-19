package ReportTabFragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowPurchaseReportListActivity;
import com.RSPL.POS.ShowStockReportListActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import Adapter.FragmentStockVendorInvoiceAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryReportModel;
import Pojo.StockInventoryReportModel;
import Pojo.VendorReportModel;


public class FragmentStockReport extends android.support.v4.app.Fragment{

    ListView listview;
    CustomAuto vendorTextViewName,VendorInvoiceNo;
    private TextWatcher vendorTextWatcher,VendorInvoiceTextWatcher;
    FragmentstockInventoryAdapter orderNoListAdapter;
    ArrayList<StockInventoryReportModel> searchVendorList,VendorInvoiceNoList,VendorGrnIDList;
    ArrayList<StockInventoryReportModel> arrayVendorList;

    FragmentStockSearchAdapter searchVendorAdapter;
    FragmentStockVendorInvoiceAdapter fragmentStockVendorInvoiceAdapter;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    TextView tvdist,tvvendor,tvinvoiceno;

    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.report_activity_inventory_stock_data, container, false);

        helper= new DBhelper(getContext());
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

        LinearLayout layout=(LinearLayout)view.findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        tvdist = (TextView) view.findViewById(R.id.id);
        tvdist.setTextSize(Float.parseFloat(textsize));

        tvvendor = (TextView) view.findViewById(R.id.no);
        tvvendor.setTextSize(Float.parseFloat(textsize));

        tvinvoiceno = (TextView) view.findViewById(R.id.tvNm);
        tvinvoiceno.setTextSize(Float.parseFloat(textsize));

        listview = (ListView)view.findViewById(R.id.lv_InventoryReport);
        Log.e("***********Lt1*******", listview.toString());
        vendorTextViewName = (CustomAuto)view.findViewById(R.id.DstrNmTextView);
        vendorTextViewName.setThreshold(1);

        VendorInvoiceNo=(CustomAuto)view.findViewById(R.id.vendorInvoiceNoAuto);
        VendorInvoiceNo.setThreshold(1);

        //******************************** vendor name selected from here********************************************************************************************/
        vendorTextWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (vendorTextViewName.getText().toString().matches("")) {
                            // Toast.makeText(getContext(), "Please select the Vendor Name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("Debuging", "After text changed called ");
                        if (vendorTextViewName.isPerformingCompletion()) {
                            Log.d("Debuging", "Performing completion ");
                            return;
                        }
                        String userTypedString = vendorTextViewName.getText().toString().trim();
                        if (userTypedString.equals("")) {
                            return;
                        }
                        searchVendorList = helper.getDstrNm(userTypedString);
                        if (searchVendorList != null) {
                            if (searchVendorAdapter == null) {
                                searchVendorAdapter = new FragmentStockSearchAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchVendorList);
                                searchVendorAdapter.setList(searchVendorList);

                                vendorTextViewName.setAdapter(searchVendorAdapter);
                                vendorTextViewName.setThreshold(1);
                            } else {
                                searchVendorAdapter.setList(searchVendorList);
                                searchVendorAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                };

        vendorTextViewName.addTextChangedListener(vendorTextWatcher);
        vendorTextViewName.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();

                //Toast.makeText(getContext(), "U select " + Value, Toast.LENGTH_SHORT).show();
                arrayVendorList = helper.getInvoiceNo(Value);
                if (orderNoListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    orderNoListAdapter = new FragmentstockInventoryAdapter(new ArrayList<StockInventoryReportModel>(),getContext(),FragmentStockReport.this);
                    orderNoListAdapter.setList(arrayVendorList);
                    listview.setAdapter(orderNoListAdapter);
                    //vendorTextViewName.setText("");
                } else {
                    orderNoListAdapter.setList(arrayVendorList);
                    orderNoListAdapter.notifyDataSetChanged();
                    //vendorTextViewName.setText("");
                }
                orderNoListAdapter.notifyDataSetChanged();

            }
        });


        VendorInvoiceTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(VendorInvoiceNo.isPerformingCompletion()){
                    Log.d("Debuging VendorInvoice", "Performing completion ");
                    return;
                }
                String userTypedString = VendorInvoiceNo.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                VendorInvoiceNoList = helper.getVendorInvoiceNo(userTypedString);
                if (VendorInvoiceNoList != null) {
                    fragmentStockVendorInvoiceAdapter = new FragmentStockVendorInvoiceAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, VendorInvoiceNoList);
                    VendorInvoiceNo.setAdapter(fragmentStockVendorInvoiceAdapter);
                }
            }
        };
        VendorInvoiceNo.addTextChangedListener(VendorInvoiceTextWatcher);
        VendorInvoiceNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String Value = parent.getItemAtPosition(position).toString();

                //Toast.makeText(getContext(), "U select " + Value, Toast.LENGTH_SHORT).show();
                VendorGrnIDList = helper.getGrnIDforLIst(Value);
                if (orderNoListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    orderNoListAdapter = new FragmentstockInventoryAdapter(new ArrayList<StockInventoryReportModel>(),getContext(),FragmentStockReport.this);
                    orderNoListAdapter.setList(VendorGrnIDList);
                    listview.setAdapter(orderNoListAdapter);
                    //vendorTextViewName.setText("");
                } else {
                    orderNoListAdapter.setList(VendorGrnIDList);
                    orderNoListAdapter.notifyDataSetChanged();
                    //vendorTextViewName.setText("");
                }
                orderNoListAdapter.notifyDataSetChanged();
            }

        });


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                try {

                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                            .INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(vendorTextViewName.getWindowToken(), 0);

                }catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                return true;
            }

        });


        return  view;
    }

    public void GetDailyDetailPage(String prodId){

        Log.e("Test", prodId);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", prodId);

        Intent intent = new Intent(getContext(), ShowStockReportListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }





}


