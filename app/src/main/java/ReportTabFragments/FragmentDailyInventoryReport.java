package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.ActivityStore;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowDailySalesReportListActivity;
import com.RSPL.POS.login;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryReportModel;
import Pojo.SaleReportModel;


public class FragmentDailyInventoryReport extends android.support.v4.app.Fragment {

    ListView listview;
    FragmentListDailyInventoryAdapter inventoryListAdapter;
    ArrayList<InventoryReportModel> GetDailyInventory;
    Context context;
    Button emailbutton;
    public TextView TodayDate;
    public static String username = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    TextView tvtodaydate,tvuser,tvproductnames,tvbatch,tvexpiry,tvqty;
    String backroundcolour,colorchange;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_daily_inventory_report, container, false);

        helper = new DBhelper(getContext());

        username = login.b.getString("Pos_User");

        context = getActivity();
        listview = (ListView) view.findViewById(R.id.lv_InventoryReport);

        TodayDate = (TextView) view.findViewById(R.id.InventoryDate);

        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
        String curr_date = s.toString();

        DecimalFormat f=new DecimalFormat("##.00");

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        TodayDate.setText(result);

        final Date Todaydate = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        TodayDate.setText(dateFormat.format(Todaydate));

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


        tvtodaydate = (TextView) view.findViewById(R.id.from);
        tvtodaydate.setTextSize(Float.parseFloat(textsize));

        tvuser = (TextView) view.findViewById(R.id.tvNm);
        tvuser.setTextSize(Float.parseFloat(textsize));

        tvproductnames = (TextView) view.findViewById(R.id.tvId);
        tvproductnames.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) view.findViewById(R.id.tvDesc);
        tvbatch.setTextSize(Float.parseFloat(textsize));

        tvexpiry = (TextView) view.findViewById(R.id.tvStart);
        tvexpiry.setTextSize(Float.parseFloat(textsize));

        tvqty = (TextView) view.findViewById(R.id.tvEnd);
        tvqty.setTextSize(Float.parseFloat(textsize));


        GetDailyInventory = helper.getdataforDailyinventory(curr_date);
        Collections.reverse(GetDailyInventory);
        if (GetDailyInventory != null) {
            if (inventoryListAdapter == null) {
                inventoryListAdapter = new FragmentListDailyInventoryAdapter(GetDailyInventory, getContext(), FragmentDailyInventoryReport.this);
                listview.setAdapter(inventoryListAdapter);
                inventoryListAdapter.notifyDataSetChanged();
            } else {
                inventoryListAdapter = new FragmentListDailyInventoryAdapter(new ArrayList<InventoryReportModel>(), getContext(), FragmentDailyInventoryReport.this);
                listview.setAdapter(inventoryListAdapter);
                inventoryListAdapter.notifyDataSetChanged();
            }
        }

        return view;
    }

    public void insertEmaildata() {
        class UpdateReportForInventory extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertEmailDailyinventory(inventoryListAdapter.getList());
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(, "UPDATING...", "Wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();


                //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateReportForInventory updateReportForInventory = new UpdateReportForInventory();
        updateReportForInventory.execute();
    }

    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }

    public void Updateinventoryreport1month() {
        final HashMap<String, String> hashMap = new HashMap<>();

        for (InventoryReportModel purchase : inventoryListAdapter.getList())

        {

            final String reportbatch = purchase.getBatch();
            final String reportproductname = purchase.getProd_Nm();
            final String reportexpdate = String.valueOf(purchase.getExpiry());
            final String reportqty = String.valueOf(purchase.getQuantity());
            final String reportposuser = String.valueOf(purchase.getUser_Nm());

            hashMap.put(Config.Retail_report_ticketid, getSystemCurrentTime());
            hashMap.put(Config.Retail_report_productname, reportproductname);
            hashMap.put(Config.Retail_report_expdate, reportexpdate);
            hashMap.put(Config.Retail_report_Batch, reportbatch);
            hashMap.put(Config.Retail_report_qty, reportqty);
            hashMap.put(Config.Retail_Pos_user, reportposuser);
        }

        class Updatedistributorreport extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try {

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_str_stock_master_mail.php", hashMap);
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

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //     loading.dismiss();
                //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();
            }

        }
        Updatedistributorreport updatereport = new Updatedistributorreport();
        updatereport.execute();


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
                    Updateinventoryreport1month();

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