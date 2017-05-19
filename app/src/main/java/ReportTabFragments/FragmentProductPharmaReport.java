package ReportTabFragments;

import android.app.ActionBar;
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
import Pojo.ReportProductPharmaModel;


public class FragmentProductPharmaReport extends android.support.v4.app.Fragment{

    ListView listview;
    AutoCompleteTextView autoCompleteTextView;
    private TextWatcher mTextWatcher;
    FragmentListProductPharmaAdapter productListAdapter;
    ArrayList<ReportProductPharmaModel> searchProductList;
    ArrayList<ReportProductPharmaModel> arrayProductList;
    FragmentSearchProductPharmaAdapter searchProductAdapter;
    ArrayList<ReportProductPharmaModel>GetAllPharmaProduct;
    String ActiveType[];
    Spinner Active;
    String SpinValue;
    Button emailbutton;
    ArrayAdapter<String> adapterActiveType;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;
    TextView tvprodnamesearch,tvavtivesrech,tvid,tvname,tvmrp,tvsprice,tvpprice,tvactive,tvmargin;
    String backroundcolour,colorchange;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_product_pharma_report, container, false);

        helper = new DBhelper(getContext());

        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        emailbutton=(Button)view.findViewById(R.id.emailbtn);

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
        tvprodnamesearch = (TextView) view.findViewById(R.id.id);
        tvprodnamesearch.setTextSize(Float.parseFloat(textsize));

        tvavtivesrech = (TextView) view.findViewById(R.id.actve);
        tvavtivesrech.setTextSize(Float.parseFloat(textsize));

        tvid = (TextView) view.findViewById(R.id.tvProdId);
        tvid.setTextSize(Float.parseFloat(textsize));

        tvname = (TextView) view.findViewById(R.id.tvProdctNm);
        tvname.setTextSize(Float.parseFloat(textsize));

        tvmrp = (TextView) view.findViewById(R.id.tvMrp);
        tvmrp.setTextSize(Float.parseFloat(textsize));

        tvsprice = (TextView) view.findViewById(R.id.tvSPrice);
        tvsprice.setTextSize(Float.parseFloat(textsize));

        tvpprice = (TextView) view.findViewById(R.id.tvPPrice);
        tvpprice.setTextSize(Float.parseFloat(textsize));

        tvactive = (TextView) view.findViewById(R.id.tvActve);
        tvactive.setTextSize(Float.parseFloat(textsize));

        tvmargin = (TextView) view.findViewById(R.id.tvMargin);
        tvmargin.setTextSize(Float.parseFloat(textsize));




        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoCompleteTextView.getWindowToken(), 0);
                return true;
            }

        });

        listview = (ListView)view.findViewById(R.id.lv_ProductReport);
        Log.e("***********Lt1*******", listview.toString());

        autoCompleteTextView = (CustomAuto)view.findViewById(R.id.autoCompleteTextView);

        autoCompleteTextView.setThreshold(1);



        Active = (Spinner)view.findViewById(R.id.activeProdPharma);

        GetAllPharmaProduct=helper.getProductPharmaForReport();
        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
        productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
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
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for(ReportProductPharmaModel product:arrayProductList)
                    {
                        productListAdapter.addProductToList(product);}
                    productListAdapter.notifyDataSetChanged();
                    //autoCompleteTextView.setText("");
                }else if(SpinValue.equals("N"))
                {
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductPharmaModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                   // autoCompleteTextView.setText("");
                }
                else if(SpinValue.equals("All"))
                {
                    GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                    listview.setAdapter(productListAdapter);


                    arrayProductList = helper.getProductPharmaReportforAllActive(SpinValue);
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }
                    for (ReportProductPharmaModel prod:arrayProductList){
                        productListAdapter.addProductToList(prod);
                    }
                    productListAdapter.notifyDataSetChanged();
                   // autoCompleteTextView.setText("");
                }
                else
                {   arrayProductList = new ArrayList<ReportProductPharmaModel>();
                    if (productListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        productListAdapter = new FragmentListProductPharmaAdapter( arrayProductList ,getContext());
                        listview.setAdapter(productListAdapter);
                    }

                    // distributorListAdapter.addProductToList(arrayDistributorList.get(0));
                    // Log.i("&&&&&&&&", "Adding " + arrayDistributorList.get(0) + " to Product List..");
                    productListAdapter.notifyDataSetChanged();
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
                Log.d("Debuging", "After text changed called ");
                if (autoCompleteTextView.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                searchProductList = helper.getProductPharmaName(userTypedString);
                if (searchProductList != null) {
                    if (searchProductAdapter == null) {
                        searchProductAdapter = new FragmentSearchProductPharmaAdapter(getContext(), android.R.layout.simple_dropdown_item_1line, searchProductList);
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
                GetAllPharmaProduct=new ArrayList<ReportProductPharmaModel>();
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                productListAdapter = new FragmentListProductPharmaAdapter( GetAllPharmaProduct ,getContext());
                listview.setAdapter(productListAdapter);
                arrayProductList = helper.getProductPharmaReport(Value);
                if (productListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    productListAdapter = new FragmentListProductPharmaAdapter(new ArrayList<ReportProductPharmaModel>(),getContext());
                    productListAdapter.setList(arrayProductList);
                    listview.setAdapter(productListAdapter);
                }
                for(ReportProductPharmaModel product:arrayProductList)
                {
                    productListAdapter.addProductToList(product);}
                //Log.i("&&&&&&&&", "Adding " + arrayProductList.get(0) + " to Product List..");
                productListAdapter.setList(arrayProductList);
                productListAdapter.notifyDataSetChanged();
               // autoCompleteTextView.setText("");

            }
        });

        return  view;

    }

    public void newReportProductPharma() {

        helper.insertEmaildataProductPharma(autoCompleteTextView.getText().toString(), SpinValue.toString());


       /* class UpdateReportForInventory extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertEmaildataProductPharma(productListAdapter.getList());
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
        UpdateReportForInventory updateReportForInventory = new UpdateReportForInventory();
        updateReportForInventory.execute();*/

    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }


    public void Updateproductpharmareport() {

        for (ReportProductPharmaModel purchase : productListAdapter.getList()) {

            final String reportproductname = autoCompleteTextView.getText().toString();
            //final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportactive = SpinValue.toString();

            PersistenceManager.saveStoreId(getContext(), helper.getStoreid().toString().replace("[", "").replace("]", ""));
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
                        hashMap.put(Config.Retail_report_productname, reportproductname);
                        //hashMap.put(Config.Retail_Pos_user,reportposuser);
                        hashMap.put(Config.Retail_report_active, reportactive);
                      //  hashMap.put(Config.RETAIL_REPORT_STORE_ID,store_id);

                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Upload_Report/retail_store_prod_mail.jsp", hashMap);
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

                    newReportProductPharma();
                    Updateproductpharmareport();
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
