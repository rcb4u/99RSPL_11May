package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowDailySalesReportListActivity;
import com.RSPL.POS.ShowPurchaseReport1MonthListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.VendorReportModel;


public class FragmentPurchasing1MonthReport extends android.support.v4.app.Fragment{

    ListView listview;
    FragmentList1MonthPurchasingAdapter orderNoListAdapter;
    ArrayList<VendorReportModel> Get1MonthData;
    Button emailbutton;
    private TextView GrandTotal;
    private TextView Totalitems;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_vendor_1monthreportprocurement, container, false);

        helper = new DBhelper(getContext());


        listview = (ListView) view.findViewById(R.id.lv_VendorReport);
        Log.e("***********Lt1*******", listview.toString());

        GrandTotal = (TextView) view.findViewById(R.id.ReportPurchasingGrandTotal);
        Totalitems = (TextView)view. findViewById(R.id.ReportPurchasingTotalitem);

        Get1MonthData = helper.getPurchasing1MonthDataForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        orderNoListAdapter = new FragmentList1MonthPurchasingAdapter(Get1MonthData, getContext(),FragmentPurchasing1MonthReport.this);
        listview.setAdapter(orderNoListAdapter);

        setSummaryRow();

        return  view;
    }

    public void GetMonthlyDetailPage(String prodId){

        Log.e("Test", prodId);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", prodId);

        Intent intent = new Intent(getContext(), ShowPurchaseReport1MonthListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = orderNoListAdapter.getGrnTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);

        int Getitem = orderNoListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }


    public void insertEmaildata() {

        DBhelper dBhelper = new DBhelper(getContext());

        dBhelper.insertEmail_1monthpurchase(orderNoListAdapter.getList());
    }

    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updatepurchasing1monthreport() {

        for (VendorReportModel purchase : orderNoListAdapter.getList()) {

            final String reportpono = purchase.getPo_No();
            final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportvendoename = String.valueOf(purchase.getVendor_Nm());
            final String reporttotal = String.valueOf(purchase.getTotal());


            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.Retail_report_pono, reportpono);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);
                    hashMap.put(Config.Retail_report_vendornamepurchasing, reportvendoename);
                    hashMap.put(Config.Retail_report_total, reporttotal);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_str_po_detail_mail.php", hashMap);
                        Log.d("Login attempt", s.toString());

                        // success tag for json
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {

                    insertEmaildata();
                    Updatepurchasing1monthreport();
                    Toast.makeText(getContext(),"Email Sent",Toast.LENGTH_LONG).show();
                    getActivity().finish();



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



}
