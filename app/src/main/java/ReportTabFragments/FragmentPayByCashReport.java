package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.CustomAlphaNumericKeyboard;
import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.PayByCashVendorPaymentModel;


public class FragmentPayByCashReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;
    FragmentPayByCashListAdapter paycashListAdapter;
    ArrayList<PayByCashVendorPaymentModel> searchIdList;
    ArrayList<PayByCashVendorPaymentModel> arrayPayCashList;
    FragmentPayByCashSearchAdapter searchIdAdapter;
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
    TextView tvuser,tvvendornames,tvpaid,tvreceived,tvdue,tvdate,tvreason,tvdays,tvto,tvfrom,tvgrnsearch;
    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_report_pay_by_cash, container, false);

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

        tvto = (TextView) view.findViewById(R.id.to);
        tvto.setTextSize(Float.parseFloat(textsize));
        tvfrom = (TextView) view.findViewById(R.id.from);
        tvfrom.setTextSize(Float.parseFloat(textsize));
        tvgrnsearch = (TextView) view.findViewById(R.id.id);
        tvgrnsearch.setTextSize(Float.parseFloat(textsize));




        emailbutton=(Button)view.findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });


        listview = (ListView) view.findViewById(R.id.lv_payByCashReport);
        Log.e("***********Lt1*******", listview.toString());
        idTextView = (CustomAuto) view.findViewById(R.id.DirectCashIdTextView);


        idTextView.setDropDownHeight(300);
        idTextView.setThreshold(1);

        Submit=(Button)view.findViewById(R.id.Submit);
        FromMonth = (Spinner)view. findViewById(R.id.DaysFromMonth);
        ToMonth=(Spinner)view.findViewById(R.id.DaysToMonth);
        FromYear=(Spinner)view.findViewById(R.id.DaysFromYear);
        ToYear=(Spinner)view.findViewById(R.id.DaysToYear);
        FromDate=(Spinner)view.findViewById(R.id.DaysFromDate);
        ToDate=(Spinner)view.findViewById(R.id.DaysToDate);

        monthList = (ListView)view. findViewById(R.id.lv_payByCashReport);
        Log.e("***********Lt1*******", monthList.toString());

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


        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(idTextView.getWindowToken(), 0);
                return true;
            }

        });

        Submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String month_from = FromMonthValue;
                String year_from = FromYearValue;
                String date_from = FromDateValue;
                String  month_to = ToMonthValue;
                String  year_to = ToYearValue;
                String  date_to = ToDateValue;

                fromString = String.format("%s-%02d-%s",year_from ,getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ",fromString);
                toString = String.format("%s-%02d-%s", year_to,getIntFromMonthName(month_to),date_to );
                Log.e("Value To date ",toString);
                arrayPayCashList = helper.PayByCashDataForMonth(fromString, toString);
                paycashListAdapter = new FragmentPayByCashListAdapter(arrayPayCashList,getContext());
                monthList.setAdapter(paycashListAdapter);
                paycashListAdapter.notifyDataSetChanged();
            }
        });

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
                    Toast.makeText(getContext(), "Please select the Grn Id", Toast.LENGTH_SHORT).show();
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
                searchIdList = helper.getGrnPayByCashId(userTypedString);
                if (searchIdList != null) {
                    if (searchIdAdapter == null) {
                        searchIdAdapter = new FragmentPayByCashSearchAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
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
        idTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                arrayPayCashList = helper.getPayByCashDataForReport(Value);
                if (paycashListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    paycashListAdapter = new FragmentPayByCashListAdapter(new ArrayList<PayByCashVendorPaymentModel>(), getContext());
                    paycashListAdapter.setList(arrayPayCashList);
                    listview.setAdapter(paycashListAdapter);
                    idTextView.setText("");
                } else {
                    paycashListAdapter.setList(arrayPayCashList);
                    paycashListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }
            }
        });


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

    public void insertEmaildata() {

        try{
            DBhelper dBhelper = new DBhelper(getContext());

            dBhelper.insertemailmonthpaybycash(idTextView.getText().toString());
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



    public void Updatepaybycash1month() {


        for (PayByCashVendorPaymentModel purchase : paycashListAdapter.getList()) {

           /* final String reportamountpaid = String.valueOf(purchase.getAmountPaid());
            final String reportreceivedamount = purchase.getAmountRcvd();*/
            final String reportvendorname = String.valueOf(searchIdAdapter.getList());
            final String reportvendorid = String.valueOf(searchIdAdapter.getList());
            /*final String reportdueamount = String.valueOf(purchase.getAmountDue());
            final String reportreasonofpay= purchase.getReasonOfPay();
            final String reportlastmodified = String.valueOf(purchase.getLastUpdate());
            final String reportposuser = String.valueOf(purchase.getUserName());*/

            PersistenceManager.saveStoreId(getContext(),helper .getStoreid().toString().replace("[", "").replace("]", ""));
            PersistenceManager.getStoreId(getContext());
            final String  store_id= PersistenceManager.getStoreId(getContext());

            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                        /*hashMap.put(Config.Retail_report_amountpaid, reportamountpaid);
                        hashMap.put(Config.Retail_report_receivedamoumt, reportreceivedamount);*/
                        hashMap.put(Config.Retail_report_vendorname, reportvendorname);
                        hashMap.put(Config.Retail_report_vendorid, reportvendorid);

                      //  hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);

                       /* hashMap.put(Config.Retail_report_reportdueamount, reportdueamount);
                        hashMap.put(Config.Retail_report_reportreasonofpay,reportreasonofpay);
                        hashMap.put(Config.Retail_report_reportlastmodified,reportlastmodified);
                        hashMap.put(Config.Retail_Pos_user,reportposuser);*/

                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/tmp_vend_dstr_payment_mail.jsp", hashMap);
                        Log.d("Login attempt", s.toString());

                        // success tag for json
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {

                    insertEmaildata();
                    Updatepaybycash1month();
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
