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

import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryReportModel;


public class FragmentInventoryReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView idTextView;
    private TextWatcher idTextWatcher;
    FragmentListInventoryAdapter inventoryListAdapter;
    ArrayList<InventoryReportModel> searchIdList;
    ArrayList<InventoryReportModel> arrayInventoryList;
    FragmentSearchInventoryAdapter searchIdAdapter;
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
    DBhelper dBhelper;
    TextView tvuser,tvproductnames,tvbatch,tvexpiry,tvqty,tvdayleft,tvid,tvfrom,tvto;

    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.report_activity_inventory_data, container, false);



        emailbutton=(Button)view.findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });
        dBhelper= new DBhelper(getContext());
        Decimal valuetextsize = dBhelper.getStoreprice();
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

        tvid = (TextView) view.findViewById(R.id.id);
        tvid.setTextSize(Float.parseFloat(textsize));

        tvfrom = (TextView) view.findViewById(R.id.from);
        tvfrom.setTextSize(Float.parseFloat(textsize));


        tvto = (TextView) view.findViewById(R.id.to);
        tvto.setTextSize(Float.parseFloat(textsize));



        Submit=(Button)view.findViewById(R.id.Submit);
        FromMonth = (Spinner)view. findViewById(R.id.DaysFromMonth);
        ToMonth=(Spinner)view.findViewById(R.id.DaysToMonth);
        FromYear=(Spinner)view.findViewById(R.id.DaysFromYear);
        ToYear=(Spinner)view.findViewById(R.id.DaysToYear);
        FromDate=(Spinner)view.findViewById(R.id.DaysFromDate);
        ToDate=(Spinner)view.findViewById(R.id.DaysToDate);
        listview = (ListView)view. findViewById(R.id.lv_InventoryReport);
        idTextView = (CustomAuto) view.findViewById(R.id.InventryReportTextView);
        idTextView.setThreshold(1);

        monthList = (ListView)view. findViewById(R.id.lv_InventoryReport);

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

                String month_to = ToMonthValue;
                String year_to = ToYearValue;
                String date_to = ToDateValue;

                fromString = String.format("%s/%02d/%s " + "00:00:00", year_from, getIntFromMonthName(month_from), date_from);
                toString = String.format("%s/%02d/%s " + "23:59:59", year_to, getIntFromMonthName(month_to), date_to);

                arrayInventoryList = dBhelper.InventoryDataForMonth(fromString, toString);
                Collections.reverse(arrayInventoryList);

                if (arrayInventoryList!=null) {
                    if (inventoryListAdapter== null) {
                        inventoryListAdapter = new FragmentListInventoryAdapter(arrayInventoryList, getContext(), FragmentInventoryReport.this);
                        monthList.setAdapter(inventoryListAdapter);
                    }else {
                        inventoryListAdapter = new FragmentListInventoryAdapter(arrayInventoryList, getContext(), FragmentInventoryReport.this);
                        monthList.setAdapter(inventoryListAdapter);
                    }
                }
                //setSummaryRow();
                inventoryListAdapter.notifyDataSetChanged();

            }
        });


        //******************************** vendor name selected from here********************************************************************************************/
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
                    //Toast.makeText(getContext(), "Please select the Product Id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idTextView.isPerformingCompletion()) {
                    return;
                }
                String userTypedString = idTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchIdList = dBhelper.getProductId(userTypedString);
                if (searchIdList != null) {
                    if (searchIdAdapter == null) {
                        searchIdAdapter = new FragmentSearchInventoryAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchIdList);
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
                arrayInventoryList = dBhelper.getInventoryReport(Value);
                if (inventoryListAdapter == null) {
                    inventoryListAdapter = new FragmentListInventoryAdapter(new ArrayList<InventoryReportModel>(), getContext(),FragmentInventoryReport.this);
                    inventoryListAdapter.setList(arrayInventoryList);
                    listview.setAdapter(inventoryListAdapter);
                    idTextView.setText("");
                } else {
                    inventoryListAdapter.setList(arrayInventoryList);
                    inventoryListAdapter.notifyDataSetChanged();
                    idTextView.setText("");
                }
            }
        });

        return  view;
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

            dBhelper.insertEmailmonthinventory(idTextView.getText().toString());
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

    public void Updateinventoryreport() {


        for (InventoryReportModel purchase : inventoryListAdapter.getList()) {

            final String reportbatch = purchase.getBatch();
            final String reportproductname = purchase.getProd_Nm();
            final String reportexpdate = String.valueOf(purchase.getExpiry());
            final String reportqty = String.valueOf(purchase.getQuantity());
            final String reportposuser = String.valueOf(purchase.getUser_Nm());

            PersistenceManager.saveStoreId(getContext(), dBhelper.getStoreid().toString().replace("[", "").replace("]", ""));
            PersistenceManager.getStoreId(getContext());
            final String  store_id= PersistenceManager.getStoreId(getContext());

            class Updatedistributorreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.Retail_report_productname, reportproductname);
                    hashMap.put(Config.Retail_report_expdate, reportexpdate);
                    hashMap.put(Config.Retail_report_Batch,reportbatch);
                    hashMap.put(Config.Retail_report_qty,reportqty);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);

                        //hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);

                    JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_str_stock_master_mail.jsp", hashMap);
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
            Updatedistributorreport updatereport = new Updatedistributorreport();
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
                    Updateinventoryreport();
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
