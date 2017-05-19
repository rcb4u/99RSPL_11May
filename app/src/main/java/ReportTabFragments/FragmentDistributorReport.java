package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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

import com.RSPL.POS.ActivityStore;
import com.RSPL.POS.CustomAlphaNumericKeyboard;
import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.ReportDistributorModel;


public class FragmentDistributorReport extends android.support.v4.app.Fragment {

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListDistributorAdapter distributorListAdapter;
    ArrayList<ReportDistributorModel> GetAllDistibutor;
    ArrayList<ReportDistributorModel> searchDistributorList;
    ArrayList<ReportDistributorModel> arrayDistributorList;
    FragmentSearchDistributorAdapter searchDistributorAdapter;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    Button emailbutton;
    TextView tvdistnamesearch,tvavtivesrech,tvuser,tvname,tvactive;
    ArrayAdapter<String> adapterActiveType;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    CustomAlphaNumericKeyboard mCustomKeyboard;
    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.activity_distributor_report, container, false);
        helper = new DBhelper(getContext());

        emailbutton = (Button) view.findViewById(R.id.emailbtn);
        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();
            }
        });


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
        tvdistnamesearch = (TextView) view.findViewById(R.id.id);
        tvdistnamesearch.setTextSize(Float.parseFloat(textsize));

        tvavtivesrech = (TextView) view.findViewById(R.id.actve);
        tvavtivesrech.setTextSize(Float.parseFloat(textsize));

        tvuser = (TextView) view.findViewById(R.id.tvUserNm);
        tvuser.setTextSize(Float.parseFloat(textsize));

        tvname = (TextView) view.findViewById(R.id.tvDsbtrNm);
        tvname.setTextSize(Float.parseFloat(textsize));

        tvactive = (TextView) view.findViewById(R.id.tvActve);
        tvactive.setTextSize(Float.parseFloat(textsize));





        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);


        listview = (ListView) view.findViewById(R.id.lv_DistributorReport);
        autoCompleteTextView = (CustomAuto) view.findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setThreshold(1);

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                return true;
            }

        });



        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                return true;
            }

        });

        Active = (Spinner) view.findViewById(R.id.activeDistributor);

        GetAllDistibutor = helper.getDistributorForReport();
        distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
        listview.setAdapter(distributorListAdapter);

        ActiveType = getResources().getStringArray(R.array.active_Status_for_Report);
        adapterActiveType = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
                if (SpinValue.equals("Y")) {
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforActive(SpinValue);
                    if (distributorListAdapter == null) {
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList, getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for (ReportDistributorModel distributor : arrayDistributorList) {
                        distributorListAdapter.addDistributorList(distributor);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                   // autoCompleteTextView.setText("");
                } else if (SpinValue.equals("N")) {
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforActive(SpinValue);
                    if (distributorListAdapter == null) {
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList, getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for (ReportDistributorModel prod : arrayDistributorList) {
                        distributorListAdapter.addDistributorList(prod);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                   // autoCompleteTextView.setText("");
                } else if (SpinValue.equals("All")) {
                    GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                    distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                    listview.setAdapter(distributorListAdapter);
                    arrayDistributorList = helper.getDistributorReportforAllActive(SpinValue);
                    if (distributorListAdapter == null) {
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList, getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    for (ReportDistributorModel prod : arrayDistributorList) {
                        distributorListAdapter.addDistributorList(prod);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                   // autoCompleteTextView.setText("");
                } else {
                    arrayDistributorList = new ArrayList<ReportDistributorModel>();
                    if (distributorListAdapter == null) {
                        distributorListAdapter = new FragmentListDistributorAdapter(arrayDistributorList, getContext());
                        listview.setAdapter(distributorListAdapter);
                    }
                    distributorListAdapter.notifyDataSetChanged();
                    //autoCompleteTextView.setText("");

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /******************************** distributor name selected from here********************************************************************************************/
        autoCompleteTextView.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (autoCompleteTextView.isPerformingCompletion()) {
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchDistributorList = helper.getDistributorName(userTypedString);
                if (searchDistributorList != null) {
                    if (searchDistributorAdapter == null) {
                        searchDistributorAdapter = new FragmentSearchDistributorAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchDistributorList);
                        searchDistributorAdapter.setList(searchDistributorList);

                        autoCompleteTextView.setAdapter(searchDistributorAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchDistributorAdapter.setList(searchDistributorList);
                        searchDistributorAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllDistibutor = new ArrayList<ReportDistributorModel>();
                distributorListAdapter = new FragmentListDistributorAdapter(GetAllDistibutor, getContext());
                listview.setAdapter(distributorListAdapter);

                arrayDistributorList = helper.getDistributorReport(Value);
                if (distributorListAdapter == null) {
                    distributorListAdapter = new FragmentListDistributorAdapter(new ArrayList<ReportDistributorModel>(), getContext());
                    distributorListAdapter.setList(arrayDistributorList);
                    listview.setAdapter(distributorListAdapter);
                    //autoCompleteTextView.setText("");
                }
                for (ReportDistributorModel distributor : arrayDistributorList) {
                    distributorListAdapter.addDistributorList(distributor);
                }
                distributorListAdapter.setList(arrayDistributorList);
                distributorListAdapter.notifyDataSetChanged();
               // autoCompleteTextView.setText("");
            }
        });

        return view;
    }

    public void newReportDistributor() {

        helper.insertEmaildatadistributor(autoCompleteTextView.getText().toString(), SpinValue.toString());

        /*class UpdateReportForInventory extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertEmaildatadistributor(distributorListAdapter.getList());
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

    }*/

       /* UpdateReportForInventory updateReportForInventory = new UpdateReportForInventory();
        updateReportForInventory.execute();*/

    }

    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updatedistributorreport() {


        for (ReportDistributorModel purchase : distributorListAdapter.getList()) {

            final String reportdstrname = autoCompleteTextView.getText().toString();
            final String reportactive = SpinValue.toString();
            //final String reportposuser = String.valueOf(purchase.getUser_Nm());

            PersistenceManager.saveStoreId(getContext(), helper.getStoreid().toString().replace("[", "").replace("]", ""));
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
                        hashMap.put(Config.Retail_report_dstrname, reportdstrname);
                        hashMap.put(Config.Retail_report_active, reportactive);
                       // hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);
                        //hashMap.put(Config.Retail_Pos_user,reportposuser);


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_str_dstr_mail.jsp", hashMap);
//                        Log.d("Login attempt", s.toString());

                        // success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                            //return s.getString(TAG_MESSAGE);
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
                    //              Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
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

                    newReportDistributor();
                    Updatedistributorreport();
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
