package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowWithoutInvoiceReportListActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.SalesReturnReportModel;


public class FragmentWithoutInvoiceReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;
    FragmentListWithoutInvoiceAdapter totalListAdapter;
    ArrayList<SalesReturnReportModel> searchIdList;
    ArrayList<SalesReturnReportModel> arrayTotalList;

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
    private TextView GrandTotal;
    private TextView Totalitems;
    FragmentSearchWithoutInvoiceAdapter searchIdAdapter;
    Context context;
    DBhelper helper;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    TextView tvusername,tvtransactionid,tvtoalitems,tvcardno,tvbilltotal,tvgrandtotal,tvfromdate,tvtodate;
    String backroundcolour,colorchange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sales_return_withoutinvoice_report, container, false);

        helper = new DBhelper(getContext());
        Decimal valuetextsize = helper.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();




        if(backroundcolour.matches("Orange")){

            colorchange="#ff9033";
            //  String    d="#bdc3c7";//silver


            //f39c12
            //EF643C
            //EE782D
        }
        if(backroundcolour.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

            //    d=Color.BLUE;//FBB917

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
            //C3C3C3 grey//E1E1E1

            ////FBB917

        }
        LinearLayout layout=(LinearLayout)view.findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));
        tvfromdate = (TextView) view.findViewById(R.id.from);
        tvfromdate.setTextSize(Float.parseFloat(textsize));

        tvtodate = (TextView) view.findViewById(R.id.to);
        tvtodate.setTextSize(Float.parseFloat(textsize));

        tvusername = (TextView) view.findViewById(R.id.tvuser);
        tvusername.setTextSize(Float.parseFloat(textsize));

        tvtransactionid = (TextView) view.findViewById(R.id.tvid);
        tvtransactionid.setTextSize(Float.parseFloat(textsize));

        tvbilltotal = (TextView) view.findViewById(R.id.tvtotal);
        tvbilltotal.setTextSize(Float.parseFloat(textsize));

        tvtoalitems= (TextView) view.findViewById(R.id.discount_text);
        tvtoalitems.setTextSize(Float.parseFloat(textsize));

        tvgrandtotal = (TextView) view.findViewById(R.id.discount_text9);
        tvgrandtotal.setTextSize(Float.parseFloat(textsize));






        Submit = (Button)view.findViewById(R.id.Submit);

        FromMonth = (Spinner)view.findViewById(R.id.TotalFromMonth);
        ToMonth = (Spinner)view.findViewById(R.id.TotalToMonth);
        FromYear = (Spinner)view.findViewById(R.id.TotalFromYear);
        ToYear = (Spinner)view.findViewById(R.id.TotalToYear);
        FromDate = (Spinner)view.findViewById(R.id.TotalFromDate);
        ToDate = (Spinner)view.findViewById(R.id.TotalToDate);

        listview = (ListView)view.findViewById(R.id.lv_SalesReturnReport);
        Log.e("***********Lt1*******", listview.toString());

        /*idTextView = (CustomAuto)view.findViewById(R.id.idTextView);
        idTextView.setThreshold(1);*/

        monthList = (ListView)view.findViewById(R.id.lv_SalesReturnReport);
        Log.e("***********Lt1*******", monthList.toString());

        GrandTotal = (TextView) view.findViewById(R.id.ReportPurchasingGrandTotal);
        GrandTotal.setTextSize(Float.parseFloat(textsize));

        Totalitems = (TextView)view. findViewById(R.id.ReportPurchasingTotalitem);
        Totalitems.setTextSize(Float.parseFloat(textsize));

        final String[] month = {"Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec"};

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, month);


        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 2016; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }

        ArrayList<String> dates = new ArrayList<String>();
        int Date = Calendar.getInstance().get(Calendar.DATE);
        for (int i = 1; i <= 31; i++) {
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

                fromString = String.format("%s/%02d/%s " , year_from, getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ", fromString);
                toString = String.format("%s/%02d/%s ", year_to, getIntFromMonthName(month_to), date_to);
                Log.e("Value To date ", toString);

                arrayTotalList = helper.SalesReturnWithoutInvoiceData(fromString, toString);
                Collections.reverse(arrayTotalList);

                if (arrayTotalList!=null) {
                    if (totalListAdapter== null) {
                        totalListAdapter = new FragmentListWithoutInvoiceAdapter(arrayTotalList, getContext(), FragmentWithoutInvoiceReport.this);
                        monthList.setAdapter(totalListAdapter);
                    }else {
                        totalListAdapter = new FragmentListWithoutInvoiceAdapter(arrayTotalList, getContext(), FragmentWithoutInvoiceReport.this);
                        monthList.setAdapter(totalListAdapter);
                    }
                }
                setSummaryRow();
                totalListAdapter.notifyDataSetChanged();
            }
        });

        /******************************** vendor name selected from here********************************************************************************************/

       /* idTextView.setThreshold(1);
        idTextWatcher = new

                TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (idTextView.getText().toString().matches("")) {
                            //Toast.makeText(getContext(), "Please select the Transaction Id", Toast.LENGTH_SHORT).show();
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
                        searchIdList = helper.getWithoutInvoiceTransId(userTypedString);
                        if (searchIdList != null) {
                            if (searchIdAdapter == null) {
                                searchIdAdapter = new FragmentSearchWithoutInvoiceAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
                                searchIdAdapter.setList(searchIdList);

                                idTextView.setAdapter(searchIdAdapter);
                                idTextView.setThreshold(1);
                            } else {
                                searchIdAdapter.setList(searchIdList);
                                searchIdAdapter.notifyDataSetChanged();
                            }

                        }
                    }
                };

        idTextView.addTextChangedListener(idTextWatcher);
        idTextView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();

                arrayTotalList = helper.getSalesReturnWithoutInvoiceReport(Value);
                if (totalListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    totalListAdapter = new FragmentListWithoutInvoiceAdapter(new ArrayList<SalesReturnReportModel>(),getContext(),FragmentWithoutInvoiceReport.this);
                    totalListAdapter.setList(arrayTotalList);
                    listview.setAdapter(totalListAdapter);
                    idTextView.setText("");
                } else {
                    totalListAdapter.setList(arrayTotalList);
                    totalListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }

            }
        });

        setSummaryRow();
        totalListAdapter.notifyDataSetChanged();*/

        return view;
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

    public void GetWithoutInvoiceDetailPage(String BillNo){

        Log.e("DailySales", BillNo);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", BillNo);

        Intent intent = new Intent(getContext(), ShowWithoutInvoiceReportListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = totalListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        //  productListAdapter.notifyDataSetChanged();


        int Getitem = totalListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }

    public void insertEmaildata() {

        helper .insertemailsalesreturn_withoutinvoicereport(idTextView.getText().toString());



    }

    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void UpdatewithoutInvoice() {


        for (SalesReturnReportModel purchase : totalListAdapter.getList()) {


            final String posuser = purchase.getUserNm();
            final String tri_id =purchase.getTransId();
            final String total=purchase.getTotal();
            final String prod_name= purchase.getProdNm();
            final String qty=purchase.getQty();
            final String sale_date=purchase.getSaleDate();

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
                    // Toast.makeText(ActivityVendor.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.FRAGMENT_POS_USER, posuser);
                    hashMap.put(Config.FRAGMENT_TRI_ID, tri_id);
                    hashMap.put(Config.FRAGMENT_TOTAL, total);
                    hashMap.put(Config.FRAGMENT_PROD_NM, prod_name);
                    hashMap.put(Config.FRAGMENT_QTY, qty);
                    hashMap.put(Config.FRAGMENT_SALE_DATE, sale_date);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_str_sales_details_returns_mail.php", hashMap);
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
                    UpdatewithoutInvoice();
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
