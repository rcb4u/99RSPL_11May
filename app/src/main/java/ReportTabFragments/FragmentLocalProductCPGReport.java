package ReportTabFragments;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.ReportLocalProductCpgModel;


public class FragmentLocalProductCPGReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListLocalProductCPGAdapter localproductListAdapter;
    ArrayList<ReportLocalProductCpgModel> searchlocalProductList;
    ArrayList<ReportLocalProductCpgModel> arraylocalProductList;
    FragmentSearchLocalProductCPGAdapter searchlocalProductAdapter;
    ArrayList<ReportLocalProductCpgModel>GetAllCpgLocalProduct;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    Button emailbutton;
    ArrayAdapter<String> adapterActiveType;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_localprod_cpg_report, container, false);

        final DBhelper helper = new DBhelper(getContext());

        emailbutton=(Button)view.findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();


            }
        });


        listview = (ListView)view.findViewById(R.id.lv_LocalProdCpgReport);
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active=(Spinner)view.findViewById(R.id.activeLocalProdCpg);
        GetAllCpgLocalProduct=helper.getLocalProductCpgForReport();
        localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
        listview.setAdapter(localproductListAdapter);

        ActiveType = getResources().getStringArray(R.array.active_Status_for_Report);
        adapterActiveType = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,ActiveType);
        adapterActiveType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Active.setAdapter(adapterActiveType);
        Active.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinValue = Active.getSelectedItem().toString();
                if (SpinValue.equals("Y"))
                {
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);

                    arraylocalProductList = helper.getLocalCpgReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for(ReportLocalProductCpgModel product:arraylocalProductList)
                    {
                        localproductListAdapter.addProductToList(product);}
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalCpgReportforActive(SpinValue);
                    if (localproductListAdapter == null) {
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductCpgModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                    listview.setAdapter(localproductListAdapter);


                    arraylocalProductList = helper.getLocalCpgReportforAllActive(SpinValue);
                    if (localproductListAdapter == null) {
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    for (ReportLocalProductCpgModel prod:arraylocalProductList){
                        localproductListAdapter.addProductToList(prod);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arraylocalProductList = new ArrayList<ReportLocalProductCpgModel>();
                    if (localproductListAdapter == null) {
                        localproductListAdapter = new FragmentListLocalProductCPGAdapter( arraylocalProductList ,getContext());
                        listview.setAdapter(localproductListAdapter);
                    }
                    localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");

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
                searchlocalProductList = helper.getlocalProductCpgName(userTypedString);
                if (searchlocalProductList != null) {
                    if (searchlocalProductAdapter == null) {
                        searchlocalProductAdapter = new FragmentSearchLocalProductCPGAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchlocalProductList);
                        searchlocalProductAdapter.setList(searchlocalProductList);

                        autoCompleteTextView.setAdapter(searchlocalProductAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchlocalProductAdapter.setList(searchlocalProductList);
                        searchlocalProductAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllCpgLocalProduct=new ArrayList<ReportLocalProductCpgModel>();
                localproductListAdapter = new FragmentListLocalProductCPGAdapter( GetAllCpgLocalProduct ,getContext());
                listview.setAdapter(localproductListAdapter);
                arraylocalProductList = helper.getLocalProdCpgReport(Value);
                if (localproductListAdapter == null) {
                    localproductListAdapter = new FragmentListLocalProductCPGAdapter(new ArrayList<ReportLocalProductCpgModel>(),getContext());
                    localproductListAdapter.setList(arraylocalProductList);
                    listview.setAdapter(localproductListAdapter);
                }
                for(ReportLocalProductCpgModel product:arraylocalProductList)
                {
                    localproductListAdapter.addProductToList(product);}
                localproductListAdapter.setList(arraylocalProductList);
                localproductListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");

            }
        });

        return  view;

    }


    public void newReportLocalProductCPG() {

        DBhelper dBhelper = new DBhelper(getContext());

        dBhelper.insertEmaildataLocalProductCPG(localproductListAdapter.getList());
    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updatelocalproductreport() {

        for (ReportLocalProductCpgModel purchase : localproductListAdapter.getList()) {

            final String reportproductname = purchase.getProd_Nm();
            final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportbarcode = String.valueOf(purchase.getBarCode());
            final String reportmrp = String.valueOf(purchase.getMRP());
            final String reportsprice = String.valueOf(purchase.getS_Price());
            final String reportpprice = String.valueOf(purchase.getP_Price());
            final String reportprofitmargin = String.valueOf(purchase.getProfitMargin());
            final String reportactive = String.valueOf(purchase.getActive());


            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());

                    hashMap.put(Config.Retail_report_productname, reportproductname);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);
                    hashMap.put(Config.Retail_report_active, reportactive);
                    hashMap.put(Config.Retail_report_sprice, reportsprice);
                    hashMap.put(Config.Retail_report_barcode, reportbarcode);
                    hashMap.put(Config.Retail_report_mrp, reportmrp);
                    hashMap.put(Config.Retail_report_p_price, reportpprice);
                    hashMap.put(Config.Retail_report_profitmargin, reportprofitmargin);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_store_prod_local_cpg_mail.php", hashMap);
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

                    newReportLocalProductCPG();
                    Updatelocalproductreport();
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
