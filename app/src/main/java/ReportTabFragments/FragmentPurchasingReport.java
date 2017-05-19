package ReportTabFragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowPurchaseReportListActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.VendorReportModel;


public class FragmentPurchasingReport extends android.support.v4.app.Fragment{

    ListView listview;
    CustomAuto vendorTextViewName;
    private TextWatcher vendorTextWatcher;
    FragmentListPurchasingAdapter orderNoListAdapter;
    ArrayList<VendorReportModel> searchVendorList;
    ArrayList<VendorReportModel> arrayVendorList;

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
    Button Submit;
    private ListView monthList;
    FragmentSearchPurchasingAdapter searchVendorAdapter;
    private TextView GrandTotal;
    private TextView Totalitems;
    Button emailbutton;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_vendor_reportprocurement, container, false);

         helper = new DBhelper(getContext());

        emailbutton=(Button)view.findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });


        Submit=(Button)view.findViewById(R.id.Submit);

        FromMonth = (Spinner)view.findViewById(R.id.NoFromMonth);
        ToMonth=(Spinner)view.findViewById(R.id.NoToMonth);
        FromYear=(Spinner)view.findViewById(R.id.NoFromYear);
        ToYear=(Spinner)view.findViewById(R.id.NoToYear);
        FromDate=(Spinner)view.findViewById(R.id.NoFromDate);
        ToDate=(Spinner)view.findViewById(R.id.NoToDate);

        listview = (ListView)view.findViewById(R.id.lv_VendorReport);
        Log.e("***********Lt1*******", listview.toString());
        vendorTextViewName = (CustomAuto)view.findViewById(R.id.VendorTextView);
        vendorTextViewName.setThreshold(1);

        monthList = (ListView)view.findViewById(R.id.lv_VendorReport);
        Log.e("***********Lt1*******", monthList.toString());

        GrandTotal = (TextView) view.findViewById(R.id.ReportPurchasingGrandTotal);
        Totalitems = (TextView)view. findViewById(R.id.ReportPurchasingTotalitem);

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
                imm.hideSoftInputFromWindow(vendorTextViewName.getWindowToken(), 0);
                return true;
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


                fromString = String.format("%s-%02d-%s " + "00:00:00", year_from, getIntFromMonthName(month_from), date_from);
                Log.e("Value from date ", fromString);
                toString = String.format("%s-%02d-%s " + "23:59:59", year_to, getIntFromMonthName(month_to), date_to);
                Log.e("Value To date ", toString);


                arrayVendorList = helper.PurchaseDataForMonth(fromString, toString);
                Collections.reverse(arrayVendorList);

                if (arrayVendorList!=null) {
                    if (orderNoListAdapter== null) {
                        orderNoListAdapter = new FragmentListPurchasingAdapter(arrayVendorList, getContext(), FragmentPurchasingReport.this);
                        monthList.setAdapter(orderNoListAdapter);
                    }else {
                        orderNoListAdapter = new FragmentListPurchasingAdapter(arrayVendorList, getContext(), FragmentPurchasingReport.this);
                        monthList.setAdapter(orderNoListAdapter);
                    }
                }
                setSummaryRow();
                orderNoListAdapter.notifyDataSetChanged();

            }
        });

        //******************************** vendor name selected from here********************************************************************************************/
        vendorTextViewName.setThreshold(1);
        vendorTextWatcher = new

                TextWatcher() {
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
                        searchVendorList = helper.getVendorNameprocurement(userTypedString);
                        if (searchVendorList != null) {
                            if (searchVendorAdapter == null) {
                                searchVendorAdapter = new FragmentSearchPurchasingAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchVendorList);
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

                Toast.makeText(getContext(), "U select " + Value, Toast.LENGTH_SHORT).show();
                arrayVendorList = helper.getVendorReportprocurement(Value);
                if (orderNoListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    orderNoListAdapter = new FragmentListPurchasingAdapter(new ArrayList<VendorReportModel>(),getContext(),FragmentPurchasingReport.this);
                    orderNoListAdapter.setList(arrayVendorList);
                    listview.setAdapter(orderNoListAdapter);
                    vendorTextViewName.setText("");
                } else {
                    orderNoListAdapter.setList(arrayVendorList);
                    orderNoListAdapter.notifyDataSetChanged();
                    vendorTextViewName.setText("");
                }

                setSummaryRow();
                orderNoListAdapter.notifyDataSetChanged();

            }
        });

        return  view;
    }

    public void GetDetailPage(String prodId){

        Log.e("Test", prodId);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", prodId);

        Intent intent = new Intent(getContext(), ShowPurchaseReportListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = orderNoListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        //  productListAdapter.notifyDataSetChanged();


        int Getitem = orderNoListAdapter.getCount();
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

try{
        DBhelper dBhelper = new DBhelper(getContext());

        dBhelper.insertEmailpurchase(vendorTextViewName.getText().toString());
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


    public void Updatepurchasingreport() {

        for (VendorReportModel purchase : orderNoListAdapter.getList()) {

            final String reportpono = purchase.getPo_No();
            final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportvendoename = String.valueOf(purchase.getVendor_Nm());
            final String reporttotal = String.valueOf(purchase.getTotal());

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
                    hashMap.put(Config.Retail_report_pono, reportpono);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);
                    hashMap.put(Config.Retail_report_vendornamepurchasing, reportvendoename);
                    hashMap.put(Config.Retail_report_total, reporttotal);

                       // hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_str_po_detail_mail.jsp", hashMap);
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
                    Updatepurchasingreport();
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
