package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowSalesReportListActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.SaleReportModel;


public class FragmentSalesReport extends android.support.v4.app.Fragment{

    ListView listview;
    FragmentListSalesAdapter totalListAdapter;
    ArrayList<SaleReportModel> arraySaleList;
    private TextView TotalCash;
    private TextView TotalCredit;
    private TextView Totalitems;

    private TextView GrandTotal;
    Context context;

    public Spinner FromMonth;
    public Spinner ToMonth;
    public Spinner FromYear;
    public Spinner ToYear;
    public Spinner FromDate;
    public Spinner ToDate;
    String FromYearValue;
    String ToYearValue;
    String FromMonthValue;
    String ToMonthValue;
    String FromDateValue;
    String ToDateValue;
    String fromString;
    String toString ;
    Button Submit,emailbutton;
    private ListView monthList;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    TextView tvsaledate,tvusername,tvbillno,tvtotal,tvcardno,tvbilltotal,tvcash,tvcredit,tvgrandtotal,tvfrommonth,tvtomonth;
    String backroundcolour,colorchange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_custom_sale_report, container, false);

        helper = new DBhelper(getContext());
        context=getActivity();


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

        tvfrommonth = (TextView) view.findViewById(R.id.from);
        tvfrommonth.setTextSize(Float.parseFloat(textsize));

        tvtomonth = (TextView) view.findViewById(R.id.to);
        tvtomonth.setTextSize(Float.parseFloat(textsize));

        tvsaledate = (TextView) view.findViewById(R.id.tvDate);
        tvsaledate.setTextSize(Float.parseFloat(textsize));

        tvusername = (TextView) view.findViewById(R.id.tvUserNm);
        tvusername.setTextSize(Float.parseFloat(textsize));

        tvbillno = (TextView) view.findViewById(R.id.tvTriId);
        tvbillno.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setTextSize(Float.parseFloat(textsize));

        tvcardno = (TextView) view.findViewById(R.id.tvCardNo);
        tvcardno.setTextSize(Float.parseFloat(textsize));

        tvbilltotal = (TextView) view.findViewById(R.id.discount_text);
        tvbilltotal.setTextSize(Float.parseFloat(textsize));

        tvcash= (TextView) view.findViewById(R.id.discount_text1);
        tvcash.setTextSize(Float.parseFloat(textsize));

        tvcredit = (TextView) view.findViewById(R.id.discount_text2);
        tvcredit.setTextSize(Float.parseFloat(textsize));

        tvgrandtotal = (TextView) view.findViewById(R.id.discount_text3);
        tvgrandtotal.setTextSize(Float.parseFloat(textsize));




        Submit=(Button)view.findViewById(R.id.Submit);
        FromDate = (Spinner)view.findViewById(R.id.SaleFromDate);
        FromMonth = (Spinner)view.findViewById(R.id.SaleFromMonth);
        FromYear = (Spinner)view.findViewById(R.id.SaleFromYear);
        ToDate=(Spinner)view.findViewById(R.id.SaleToDate);
        ToMonth=(Spinner)view.findViewById(R.id.SaleToMonth);
        ToYear=(Spinner)view.findViewById(R.id.SaleToYear);

        listview = (ListView)view.findViewById(R.id.lv_SaleReport);
        Log.e("***********Lt1*******", listview.toString());

        monthList = (ListView)view.findViewById(R.id.lv_SaleReport);
        Log.e("***********Lt1*******", monthList.toString());

        TotalCash = (TextView) view.findViewById(R.id.ReportSalesTotalcash);
        TotalCredit = (TextView) view.findViewById(R.id.ReportSalesTotalcredit);
        GrandTotal = (TextView) view.findViewById(R.id.ReportSalesGrandTotal);
        Totalitems = (TextView)view. findViewById(R.id.ReportSalesTotalitem);
        TotalCash.setTextSize(Float.parseFloat(textsize));
        TotalCredit.setTextSize(Float.parseFloat(textsize));
        Totalitems.setTextSize(Float.parseFloat(textsize));
        GrandTotal.setTextSize(Float.parseFloat(textsize));



        final String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, month);

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayList<String> dates = new ArrayList<String>();
        int Date = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1;i<=31;i++){
            dates.add(Integer.toString(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, years);

        FromMonth.setAdapter(arrayAdapter);
        ToMonth.setAdapter(arrayAdapter);
        FromYear.setAdapter(adapter);
        ToYear.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dates);
        FromDate.setAdapter(adapter1);
        ToDate.setAdapter(adapter1);

        FromMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromMonthValue = FromMonth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToMonthValue = ToMonth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FromYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromYearValue = FromYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToYearValue = ToYear.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FromDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                FromDateValue = FromDate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ToDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ToDateValue = ToDate.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String month_from = FromMonthValue;
                String year_from = FromYearValue;
                String date_from = FromDateValue;

                String month_to = ToMonthValue;
                String year_to = ToYearValue;
                String date_to = ToDateValue;

                fromString = String.format("%s-%02d-%s " , year_from, getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ", fromString);
                toString = String.format("%s-%02d-%s " , year_to, getIntFromMonthName(month_to), date_to);
                Log.e("Value To date ", toString);

                arraySaleList = helper.getSalesReport(fromString, toString);
                Collections.reverse(arraySaleList);

                if (arraySaleList!=null) {
                    if (totalListAdapter== null) {
                        totalListAdapter = new FragmentListSalesAdapter(arraySaleList, getContext(), FragmentSalesReport.this);
                        monthList.setAdapter(totalListAdapter);
                    }else {
                        totalListAdapter = new FragmentListSalesAdapter(arraySaleList, getContext(), FragmentSalesReport.this);
                        monthList.setAdapter(totalListAdapter);
                    }
                }

                DecimalFormat f=new DecimalFormat("##.00");

                float cash = helper.getCashReport();
                TotalCash.setText(f.format(cash));

                float credit = helper.getCreditReport();
                TotalCredit.setText(f.format(credit));

                Float sum = cash+ credit;
                GrandTotal.setText(f.format(sum));
                setSummaryRow();
                totalListAdapter.notifyDataSetChanged();

            }
        });

        return view;
    }

    public void GetDetailPage(String BillNo){

        Log.e("DailySales", BillNo);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", BillNo);

        Intent intent = new Intent(getContext(), ShowSalesReportListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void setSummaryRow() {
        DecimalFormat f=new DecimalFormat("##.00");
        float Getval = totalListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        TotalCash.setText(GrandVal);

        float sum =Float.parseFloat(TotalCash.getText().toString())+Float.parseFloat(TotalCredit.getText().toString());


        GrandTotal.setText(f.format(sum));

        int Getitem = totalListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);

    }

    /**
     *
     * @param monthName Month name either in full ("January") or short "Jan"
     *                  case insensitive
     * @return Returns integer corresponding to month name (1 for January...)
     *          Returns -1 if month name not recognized
     */

    private int getIntFromMonthName( String monthName ) {
        String[] monthNames = new String[] { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };

        int returnVal = -1;

        for( int i=0; i < monthNames.length; i++ ) {
            if( monthNames[i].toLowerCase().contains(monthName.toLowerCase())) {
                returnVal = i+1;
                break;
            }
        }

        return returnVal;
    }

    public void insertEmaildata() {

        class UpdateReportForSales extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertemailsalesreport(totalListAdapter.getList());

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

    public void Updatesalesreport() {

        for (SaleReportModel purchase : totalListAdapter.getList()) {


            final String posuser = purchase.getUserNm();
            final String tri_id =purchase.getTransId();
            final String total=purchase.getGrnTotl();
            final String prod_name= purchase.getProdNm();
            final String qty=purchase.getQty();
            final String sale_date=purchase.getSaleDate();
            final String s_price=purchase.getPrice();
            final String exp_date=purchase.getExp();
            final String uom=purchase.getUom();

            class UpdateInvoice extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // loading = ProgressDialog.show(ActivityVendor.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //loading.dismiss();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.FRAGMENT_POS_USER, posuser);
                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.FRAGMENT_TRI_ID, tri_id);
                    hashMap.put(Config.FRAGMENT_TOTAL, total);
                    hashMap.put(Config.FRAGMENT_PROD_NM, prod_name);
                    hashMap.put(Config.FRAGMENT_QTY, qty);
                    hashMap.put(Config.FRAGMENT_SALE_DATE, sale_date);
                    hashMap.put(Config.FRAGMENT_S_PRICE, s_price);
                    hashMap.put(Config.FRAGMENT_EXP_DATE, exp_date);
                    hashMap.put(Config.FRAGMENT_UOM, uom);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(" http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_str_sales_detail_mail.jsp", hashMap);
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

                return null;                  }
            }
            UpdateInvoice updateInvoice = new UpdateInvoice();
            updateInvoice.execute();
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
                    Updatesalesreport();
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