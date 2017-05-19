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
import Pojo.ReportProductCpgModel;


public class FragmentProductCPGReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListProductCPGAdapter productListAdapter;
    ArrayList<ReportProductCpgModel> searchProductList;
    ArrayList<ReportProductCpgModel> arrayProductList;
    FragmentSearchProductCPGAdapter searchProductAdapter;
    ArrayList<ReportProductCpgModel>GetAllCpgProduct;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    Button emailbutton;
    ArrayAdapter<String> adapterActiveType;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_cpg_report, container, false);

        helper = new DBhelper(getContext());

        emailbutton=(Button)view.findViewById(R.id.emailbtn);

        emailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailbuttondialog();

            }
        });


        listview = (ListView)view.findViewById(R.id.lv_ProductCpgReport);
        Log.e("***********Lt1*******", listview.toString());
        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.setThreshold(1);
        Active = (Spinner)view.findViewById(R.id.activeProdCpg);
        GetAllCpgProduct=helper.getProductCpgForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
        listview.setAdapter(productListAdapter);

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
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for(ReportProductCpgModel product:arrayProductList)
                    {
                        productListAdapter.addProductToList(product);}
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductCpgModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getCpgReportforAllActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductCpgModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");
                }
                else
                {   arrayProductList = new ArrayList<ReportProductCpgModel>();
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductCPGAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    productListAdapter.notifyDataSetChanged();
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
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchProductList = helper.getProductCpgName(userTypedString);
                if (searchProductList != null) {
                    if (searchProductAdapter == null) {
                        searchProductAdapter = new FragmentSearchProductCPGAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchProductList);
                        searchProductAdapter.setList(searchProductList);

                        autoCompleteTextView.setAdapter(searchProductAdapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {
                        searchProductAdapter.setList(searchProductList);
                        searchProductAdapter.notifyDataSetChanged();
                    }

                }
            }
        };

        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Value = parent.getItemAtPosition(position).toString();
                GetAllCpgProduct=new ArrayList<ReportProductCpgModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new FragmentListProductCPGAdapter( GetAllCpgProduct ,getContext());
                listview.setAdapter(productListAdapter);
                arrayProductList = helper.getProductCpgReport(Value);
                if (productListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductCPGAdapter(new ArrayList<ReportProductCpgModel>(),getContext());
                    productListAdapter.setList(arrayProductList);
                    listview.setAdapter(productListAdapter);
                }
                for(ReportProductCpgModel product:arrayProductList)
                {
                productListAdapter.addProductToList(product);}
                //Log.i("&&&&&&&&", "Adding " + arrayProductList.get(0) + " to Product List..");
                    productListAdapter.setList(arrayProductList);
                    productListAdapter.notifyDataSetChanged();
                    autoCompleteTextView.setText("");

            }
        });

        return  view;

    }

    public void newReportProductCPG() {

        DBhelper dBhelper = new DBhelper(getContext());

        dBhelper.insertEmaildataProductCPG(productListAdapter.getList());
    }



    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updateproductcpgreport() {

        for (ReportProductCpgModel purchase : productListAdapter.getList()) {

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
                    hashMap.put(Config.Retail_report_barcode, reportbarcode);
                    hashMap.put(Config.Retail_report_mrp, reportmrp);
                    hashMap.put(Config.Retail_report_sprice, reportsprice);
                    hashMap.put(Config.Retail_report_p_price, reportpprice);
                    hashMap.put(Config.Retail_report_profitmargin, reportprofitmargin);
                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_store_prod_cpg_mail.jsp", hashMap);

                        Log.d("Login attempt", s.toString());


                        // success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());


                          //  return s.getString(TAG_MESSAGE);
                        } else {

                           // return s.getString(TAG_MESSAGE);

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

                    newReportProductCPG();
                    Updateproductcpgreport();
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
