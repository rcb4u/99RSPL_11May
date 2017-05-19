package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryReportModel;


public class FragmentInventory1MonthReport extends android.support.v4.app.Fragment {

    ListView listview;
    FragmentList1MonthInventoryAdapter inventoryListAdapter;
    ArrayList<InventoryReportModel> Get1MonthData;
    Button emailbutton;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    private TextView GrandTotal;
    private TextView Totalitems;
    TextView tvuser,tvproductnames,tvbatch,tvexpiry,tvqty,tvdayleft,tvpprice,tvtotal,tvtoalitms,tvgrand;

String backroundcolour,colorchange;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.report_activity_inventory_month1_data, container, false);

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

        tvdayleft = (TextView) view.findViewById(R.id.tvSlb1);
        tvdayleft.setTextSize(Float.parseFloat(textsize));

        tvpprice = (TextView) view.findViewById(R.id.tvPrice);
        tvpprice.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setTextSize(Float.parseFloat(textsize));


        tvtoalitms = (TextView) view.findViewById(R.id.discount_text);
        tvtoalitms.setTextSize(Float.parseFloat(textsize));


        tvgrand = (TextView) view.findViewById(R.id.discount_text9);
        tvgrand.setTextSize(Float.parseFloat(textsize));




        listview = (ListView) view. findViewById(R.id.lv_InventoryReport);

        GrandTotal = (TextView) view.findViewById(R.id.ReportPurchasingGrandTotal);
        GrandTotal.setTextSize(Float.parseFloat(textsize));



        Totalitems = (TextView)view. findViewById(R.id.ReportPurchasingTotalitem);
        Totalitems.setTextSize(Float.parseFloat(textsize));


        Get1MonthData = helper.getdataforinventory();
        inventoryListAdapter = new FragmentList1MonthInventoryAdapter(Get1MonthData, getContext());
        listview.setAdapter(inventoryListAdapter);
        inventoryListAdapter.notifyDataSetChanged();

        setSummaryRow();

        return view;

    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = inventoryListAdapter.getGrnTotal();
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);

        int Getitem = inventoryListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }

    public void insertEmaildata() {

        class UpdateReportForInventory extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertEmail_1monthinventory(inventoryListAdapter.getList());
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