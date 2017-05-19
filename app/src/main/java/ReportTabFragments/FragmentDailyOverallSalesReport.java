package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
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
import Pojo.OverallSaleReportModel;
import Pojo.SaleReportModel;


public class FragmentDailyOverallSalesReport extends android.support.v4.app.Fragment {

    ListView listview;
    FragmentListDailyOverallSalesAdapter totalListAdapter;
    ArrayList<OverallSaleReportModel> GetDailySales;
    private TextView GrandTotal,Profit;
    private TextView TotalCash;
    private TextView TotalCredit,TotalDiscount;
    Context context;
    public TextView TodayDate;
    public static String username = null;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    String backroundcolour,colorchange;
    TextView tvtodaydate,tvusername,tvtotal,tvbillno,tvcash,tvcredit,tvgrandtotal,tvproductname,tvbatch,tvmrp,tvqty,tvexpiry,tvprofit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_overall_sale_report, container, false);

        helper = new DBhelper(getContext());
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

        tvusername = (TextView) view.findViewById(R.id.tvUserNm);
        tvusername.setTextSize(Float.parseFloat(textsize));

        tvbillno = (TextView) view.findViewById(R.id.tvTriId);
        tvbillno.setTextSize(Float.parseFloat(textsize));

        tvproductname = (TextView) view.findViewById(R.id.tvName);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) view.findViewById(R.id.tvBatch);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvexpiry = (TextView) view.findViewById(R.id.tvExp);
        tvexpiry.setTextSize(Float.parseFloat(textsize));

      /*  tvmrp = (TextView) view.findViewById(R.id.tvMrp);
        tvmrp.setTextSize(Float.parseFloat(textsize));*/

        tvqty = (TextView) view.findViewById(R.id.tvQty);
        tvqty.setTextSize(Float.parseFloat(textsize));

       /* tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setTextSize(Float.parseFloat(textsize));*/

        tvcash= (TextView) view.findViewById(R.id.discount_text1);
        tvcash.setTextSize(Float.parseFloat(textsize));

        tvprofit = (TextView) view.findViewById(R.id.discount_text7);
        tvprofit.setTextSize(Float.parseFloat(textsize));


        tvcredit = (TextView) view.findViewById(R.id.discount_text2);
        tvcredit.setTextSize(Float.parseFloat(textsize));

        tvgrandtotal = (TextView) view.findViewById(R.id.discount_text3);
        tvgrandtotal.setTextSize(Float.parseFloat(textsize));





        username = login.b.getString("Pos_User");

        context = getActivity();
        listview = (ListView) view.findViewById(R.id.lv_SaleReport);

        TotalCash = (TextView) view.findViewById(R.id.ReportSalesTotalcash);
        TotalCredit = (TextView) view.findViewById(R.id.ReportSalesTotalcredit);
        GrandTotal = (TextView) view.findViewById(R.id.ReportSalesGrandTotal);
        TotalDiscount = (TextView) view.findViewById(R.id.ReportSalesTotaldiscount);
        Profit = (TextView) view.findViewById(R.id.ReportSalesTotalprofit);
        TodayDate = (TextView) view.findViewById(R.id.SaleFromDate);

        TotalCash.setTextSize(Float.parseFloat(textsize));
        TotalCredit.setTextSize(Float.parseFloat(textsize));
        GrandTotal.setTextSize(Float.parseFloat(textsize));
        Profit.setTextSize(Float.parseFloat(textsize));
        TodayDate.setTextSize(Float.parseFloat(textsize));





        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
        String curr_date = s.toString();

        DecimalFormat f=new DecimalFormat("##.00");

        float cash = helper.getDailyoverallCashReport(curr_date);
        TotalCash.setText(f.format(cash));

        float credit = helper.getDailyoverallCreditReport(curr_date);
        TotalCredit.setText(f.format(credit));

        float discount = helper.getDailyoverallDiscountReport(curr_date);
        TotalDiscount.setText(f.format(discount));

        Float sum = cash+ credit;
        GrandTotal.setText(f.format(sum));

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        TodayDate.setText(result);

        final Date Todaydate = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
        TodayDate.setText(dateFormat.format(Todaydate));

        GetDailySales = helper.getDailyoverallSalesReport(curr_date);
        Collections.reverse(GetDailySales);
        if (GetDailySales != null) {
            if (totalListAdapter == null) {
                totalListAdapter = new FragmentListDailyOverallSalesAdapter(GetDailySales, getContext(), FragmentDailyOverallSalesReport.this);
                listview.setAdapter(totalListAdapter);
                totalListAdapter.notifyDataSetChanged();
            }
            else {
                totalListAdapter = new FragmentListDailyOverallSalesAdapter(new ArrayList<OverallSaleReportModel>(), getContext(), FragmentDailyOverallSalesReport.this);
                listview.setAdapter(totalListAdapter);
                totalListAdapter.notifyDataSetChanged();

            }
        }
        //setSummaryRowTotal();
        setSummaryProfitTotal();
        return view;
    }

   /* public void setSummaryRowTotal() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = totalListAdapter.getGrnTotal();
        String GrandVal = f.format(Getval);
        TotalCash.setText(GrandVal);
    }*/

    public void setSummaryProfitTotal() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval1 = totalListAdapter.getProfitTotal();
        String GrandVal1 = f.format(Getval1);
        Profit.setText(GrandVal1);
    }

    public void insertEmaildata() {

        class UpdateReportForSales extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertemailoveralldailysalesreport(totalListAdapter.getList());
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
        UpdateReportForSales updateReportForSales = new UpdateReportForSales();
        updateReportForSales.execute();

    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updatedailyoverallsalesreport() {


        for (OverallSaleReportModel purchase : totalListAdapter.getList()) {

            final String reporttransid = String.valueOf(purchase.getTransId());
            final String reportgrandtotal = String.valueOf(purchase.getGrnTotl());
            //  final String reportuom = purchase.getUom();
            final String reportproductname = purchase.getProdNm();
            final String reportexpdate = String.valueOf(purchase.getExp());
            //  final String reportSprice = purchase.getPrice();
            final String reportqty = String.valueOf(purchase.getQty());
            final String reportposuser = String.valueOf(purchase.getUserNm());

            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try {

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_report_ticketid, getSystemCurrentTime());


                        hashMap.put(Config.Retail_report_transid, reporttransid);
                        hashMap.put(Config.Retail_report_grandtotal, reportgrandtotal);
                        // hashMap.put(Config.Retail_report_uom, reportuom);
                        hashMap.put(Config.Retail_report_productname, reportproductname);
                        hashMap.put(Config.Retail_report_expdate, reportexpdate);
                        //  hashMap.put(Config.Retail_report_sprice, reportSprice);
                        hashMap.put(Config.Retail_report_qty, reportqty);
                        hashMap.put(
                                Config.Retail_Pos_user, reportposuser);


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_str_sales_detail_mail.php", hashMap);
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
                    //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }

            }
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }

    }

    public void emailbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                try {

                    insertEmaildata();

                    Toast.makeText(getContext(), "Email Sent", Toast.LENGTH_LONG).show();
                    getActivity().finish();

                } catch (Exception e) {

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