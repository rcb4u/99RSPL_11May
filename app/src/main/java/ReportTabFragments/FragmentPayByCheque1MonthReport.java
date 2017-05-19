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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.PayByChequeVendorPaymentModel;


public class FragmentPayByCheque1MonthReport extends android.support.v4.app.Fragment{

    ListView listview;
    FragmentPayByChequeList1MonthAdapter paycashListAdapter;
    ArrayList<PayByChequeVendorPaymentModel> Get1MonthData;
    Button emailbutton;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;

    TextView tvuser,tvvendornames,tvpaid,tvreceived,tvdue,tvdate,tvreason,tvdays,tvcheque,tvbank;
    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_report_pay_by_cheque_1month, container, false);
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



        tvuser = (TextView) view.findViewById(R.id.tvUserNm);
        tvuser.setTextSize(Float.parseFloat(textsize));

        tvvendornames = (TextView) view.findViewById(R.id.tvDsbtrNm);
        tvvendornames.setTextSize(Float.parseFloat(textsize));


        tvpaid = (TextView) view.findViewById(R.id.tvDsbtrAmnt);
        tvpaid.setTextSize(Float.parseFloat(textsize));

        tvreceived = (TextView) view.findViewById(R.id.tvDsbtrAmn);
        tvreceived.setTextSize(Float.parseFloat(textsize));

        tvdue = (TextView) view.findViewById(R.id.tvDsbtrAmunt);
        tvdue.setTextSize(Float.parseFloat(textsize));

        tvdate = (TextView) view.findViewById(R.id.tvDate);
        tvdate.setTextSize(Float.parseFloat(textsize));

        tvreason = (TextView) view.findViewById(R.id.tvReason);
        tvreason.setTextSize(Float.parseFloat(textsize));

        tvdays = (TextView) view.findViewById(R.id.tvDays);
        tvdays.setTextSize(Float.parseFloat(textsize));


        tvcheque = (TextView) view.findViewById(R.id.tvCheque);
        tvcheque.setTextSize(Float.parseFloat(textsize));
        tvbank = (TextView) view.findViewById(R.id.tvBank);
        tvbank.setTextSize(Float.parseFloat(textsize));


        listview = (ListView) view.findViewById(R.id.lv_payByChequeReport);
        Log.e("***********Lt1*******", listview.toString());

        Get1MonthData = helper.getPayByCheque1MonthDataForReport();

        if(Get1MonthData!=null) {
            if(paycashListAdapter==null)
            {
                paycashListAdapter = new FragmentPayByChequeList1MonthAdapter(Get1MonthData, getContext(), FragmentPayByCheque1MonthReport.this);
                listview.setAdapter(paycashListAdapter);
            }
            else
            {
                paycashListAdapter = new FragmentPayByChequeList1MonthAdapter(Get1MonthData, getContext(), FragmentPayByCheque1MonthReport.this);
                listview.setAdapter(paycashListAdapter);
                paycashListAdapter.notifyDataSetChanged();
            }
        }

        return view;
    }

    public void insertEmaildata() {

        DBhelper dBhelper = new DBhelper(getContext());

        dBhelper.insertemail_1monthpaybycheque(paycashListAdapter.getList());
    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }



    public void Updatepaybycheque1month() {


        for (PayByChequeVendorPaymentModel purchase : paycashListAdapter.getList()) {

            final String reportamountpaid = String.valueOf(purchase.getAmountPaid());
            final String reportreceivedamount = purchase.getAmountRcvd();
            final String reportvendorname = purchase.getVendorNm();
            final String reportdueamount = String.valueOf(purchase.getAmountDue());
            final String reportreasonofpay= purchase.getReasonOfPay();
            final String reportlastmodified = String.valueOf(purchase.getLastUpdate());
            final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportbankname = String.valueOf(purchase.getBankName());
            final String reportchequeno = String.valueOf(purchase.getChequeNo());




            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.Retail_report_amountpaid, reportamountpaid);
                    hashMap.put(Config.Retail_report_receivedamoumt, reportreceivedamount);
                    hashMap.put(Config.Retail_report_vendorname, reportvendorname);
                    hashMap.put(Config.Retail_report_reportdueamount, reportdueamount);
                    hashMap.put(Config.Retail_report_reportreasonofpay,reportreasonofpay);
                    hashMap.put(Config.Retail_report_reportlastmodified,reportlastmodified);
                    hashMap.put(Config.Retail_report_bankname,reportbankname);
                    hashMap.put(Config.Retail_report_chequeno,reportchequeno);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/tmp_vend_dstr_payment_mail.php", hashMap);
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
                    Updatepaybycheque1month();
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