package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import Adapter.VendorNameForVendorReturnAdapter;
import Adapter.VendorReturnProductListAdapter;
import Adapter.VendorReturnSelectProductToreturnAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.VendorModel;
import Pojo.VendorReturnModel;

public class VendorReturnActivity extends Activity {
    ActionBar actionBar;
    Spinner selectReasonsforVendorreturn;
    TextWatcher SelectProducttoreturn;
    TextWatcher selectVendorordistriName;
    ArrayList<String> selectthereason;
    ArrayList<VendorModel> getAllVenderToselect;
    ArrayList<VendorReturnModel>getAllProductstoreturn;
    ArrayList<VendorReturnModel>GetthedatausingPoNumber;
    AutoCompleteTextView VendororDistributorNameAutoComplete;
    AutoCompleteTextView ProductToreturnAutocomplete;

    VendorNameForVendorReturnAdapter adapter;
    VendorReturnSelectProductToreturnAdapter dropDownProductNameAdpater;
    VendorReturnProductListAdapter ProductListAdapter;
    ListView lv_ProductVendorreturn;
    TextView GrandTotal,Billno;
    DBhelper helper;
    Button clrbtn;
    Button Submit;
    AlertDialog dialog;
    String x_imei,x1,store_id,vendor_return_id;
    String ReasonSelected;
    public static String username = null;

    String iteam;
    TextView user2;




    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String vendor_return = "sh /sdcard/vendor_return.sh";
    String superuser ="su";
    String backroundcolour,colorchange;




    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
    TextView tvproductname,tvbatch,tvexp,tvstock,tvpprice,tvtotal,tvuom,tvqty,tvreasonamount,tvreasonofreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_return);

        Billno = (TextView) findViewById(R.id.sales_billno);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);

        username =  login.b.getString("Pos_User");

        helper = new DBhelper(VendorReturnActivity.this);
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
        actionBar = getActionBar();
        // actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));


        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));




        tvproductname = (TextView) findViewById(R.id.product6);
        tvproductname.setTextSize(Float.parseFloat(textsize));

        tvbatch = (TextView) findViewById(R.id.product11);
        tvbatch.setTextSize(Float.parseFloat(textsize));
        tvexp = (TextView) findViewById(R.id.product7);
        tvexp.setTextSize(Float.parseFloat(textsize));
        tvstock = (TextView) findViewById(R.id.product111);
        tvstock.setTextSize(Float.parseFloat(textsize));
        tvpprice = (TextView) findViewById(R.id.product9);
        tvpprice.setTextSize(Float.parseFloat(textsize));
        tvtotal = (TextView) findViewById(R.id.product12);
        tvtotal.setTextSize(Float.parseFloat(textsize));
        tvuom = (TextView) findViewById(R.id.product10);
        tvuom.setTextSize(Float.parseFloat(textsize));
        tvqty = (TextView) findViewById(R.id.product9se);
        tvqty.setTextSize(Float.parseFloat(textsize));
        tvreasonamount = (TextView) findViewById(R.id.discount_text9);
        tvreasonamount.setTextSize(Float.parseFloat(textsize));
        tvreasonofreturn = (TextView) findViewById(R.id.TvReasonofreturn);
        tvreasonofreturn.setTextSize(Float.parseFloat(textsize));



        Billno.setText("012345678912345");

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        returnpopupdialog();

        lv_ProductVendorreturn=(ListView)findViewById(R.id.lv_ProductVendorreturn);
        GrandTotal=(TextView)findViewById(R.id.GrandTotalVendorReturn);
        clrbtn = (Button) findViewById(R.id.clearvendorReturn);
        Submit=(Button)findViewById(R.id.SubmitVendorReturn);


        VendororDistributorNameAutoComplete = (CustomAuto) findViewById(R.id.VendorNameordistributorNames);

        VendororDistributorNameAutoComplete.setThreshold(1);
        ProductToreturnAutocomplete=(CustomAuto)findViewById(R.id.SelectProducttoReturn);



        ProductToreturnAutocomplete.setThreshold(1);

        selectReasonsforVendorreturn = (Spinner) findViewById(R.id.Vendorreturn);
        selectthereason = helper.getthereasonsfromVendorReturn();
        final ArrayAdapter<String> selecttheadapterforreturn = new ArrayAdapter<String>(VendorReturnActivity.this, android.R.layout.simple_spinner_item, selectthereason);
        selectReasonsforVendorreturn.setAdapter(selecttheadapterforreturn);
        selectReasonsforVendorreturn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ReasonSelected = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        selectVendorordistriName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (VendororDistributorNameAutoComplete.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String UserTypedValue = VendororDistributorNameAutoComplete.getText().toString().trim();
                if (UserTypedValue.equals("")) {
                    return;
                }
                getAllVenderToselect = helper.getVendorName(UserTypedValue);
                if (getAllVenderToselect != null) {
                    if (adapter == null) {
                        adapter = new VendorNameForVendorReturnAdapter(VendorReturnActivity.this, android.R.layout.simple_spinner_item, getAllVenderToselect);
                        adapter.setList(getAllVenderToselect);
                        VendororDistributorNameAutoComplete.setAdapter(adapter);
                    } else {
                        adapter.setList(getAllVenderToselect);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        };
        VendororDistributorNameAutoComplete.addTextChangedListener(selectVendorordistriName);

        SelectProducttoreturn =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("Debuging", "After text changed called ");
                if (VendororDistributorNameAutoComplete.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ReasonSelected.equals(""))
                {
                    //Toast.makeText(getApplicationContext(), "No Reason Selected", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ProductToreturnAutocomplete.isPerformingCompletion())
                {
                    return;
                }
                String UserTypedproduct=ProductToreturnAutocomplete.getText().toString().trim();
                if (UserTypedproduct.equals(""))
                {
                    return;
                }
                if(UserTypedproduct.length()<1)
                {
                    return;
                }
                getAllProductstoreturn=helper.GetDataToreturn(UserTypedproduct);
                if (getAllProductstoreturn.size() < 1) {
                    getAllProductstoreturn.clear();
                }
                if (getAllProductstoreturn != null) {
                    if (dropDownProductNameAdpater== null)
                    {
                        dropDownProductNameAdpater= new VendorReturnSelectProductToreturnAdapter(VendorReturnActivity.this,android.R.layout.simple_list_item_1,getAllProductstoreturn);
                        dropDownProductNameAdpater.setList(getAllProductstoreturn);
                        ProductToreturnAutocomplete.setAdapter(dropDownProductNameAdpater);
                    }else
                    {
                        dropDownProductNameAdpater.setList(getAllProductstoreturn);
                        dropDownProductNameAdpater.notifyDataSetChanged();
                    }

                }
            }
        };

        ProductToreturnAutocomplete.addTextChangedListener(SelectProducttoreturn);
        ProductToreturnAutocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VendorReturnModel resval = (VendorReturnModel) parent.getItemAtPosition(position);
                if (ProductListAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    ProductListAdapter = new VendorReturnProductListAdapter(VendorReturnActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<VendorReturnModel>(), resval);
                    lv_ProductVendorreturn.setAdapter(ProductListAdapter);
                }
                int pos = ProductListAdapter.addProductToList(resval);
                Log.i("&&&&&&&&", "Adding " + resval + " to Product List..");
                ProductListAdapter.notifyDataSetChanged();
                ProductToreturnAutocomplete.setText("");
                lv_ProductVendorreturn.smoothScrollToPosition(pos);
                setSummaryRow();
                getAllProductstoreturn.clear();
            }
        });

        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    clearbuttondialog();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }

        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Long Value = System.currentTimeMillis();
                final String result = Long.toString(Value);
                String invoicevalue = Billno.getText().toString();
                ArrayList<String> billno = helper.getimeino();
                for (String str : billno) {
                    if (str.equals(invoicevalue)) {
                        ArrayList<String> imei = helper.getprefix(str);
                        Log.e("%%%%%%%%%%%%%", imei.toString());
                        x_imei = imei.toString();
                        x1 = x_imei.replace("[", "").replace("]", "").concat(result);
                        Log.e("X1_imei is :", x1);
                    } else {
                        continue;
                    }
                }


                if (VendororDistributorNameAutoComplete.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Select Vendor Or Distributor Name", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (ProductListAdapter.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No Product Selected", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (ReasonSelected.equals("")) {
                        Toast.makeText(getApplicationContext(), "No Reason Selected", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int pos = 0;
                    for (VendorReturnModel item : ProductListAdapter.getList()) {
                        if (item.getProductQuantity() <= 0) {
                            // Toast.makeText(getApplicationContext(), "Position "  + (pos+1) + " has invalid quantity", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "Please fill mandatory field", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        pos++;
                    }
                    helper.InsertDataforVendorReturn(x1, ProductListAdapter.getList(), VendororDistributorNameAutoComplete.getText().toString(), ReasonSelected,user2.getText().toString());
                    helper.InsertMasterDataForVendorReturn(x1, VendororDistributorNameAutoComplete.getText().toString(), ReasonSelected, GrandTotal.getText().toString(),user2.getText().toString());
                    helper.UpdateStockQtyForVendorReturn(ProductListAdapter.getList());
                    helper.saveDataforPdfVendorReturn(x1, VendororDistributorNameAutoComplete.getText().toString());
                    updatevendorreturn();
                    Toast.makeText(getApplicationContext(), x1.toString(), Toast.LENGTH_SHORT).show();

UpdateShell();
                    Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(intent);

                }catch (NullPointerException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");

        float Getval = ProductListAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
    }
    public  void clearbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want delete this?");

        // Setting Icon to Dialog
        //     alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                try {

                    ProductListAdapter.clearAllRows();
                    setSummaryRow();
                    VendororDistributorNameAutoComplete.setText("");
                    ProductToreturnAutocomplete.setText("");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        alertDialog.show();
    }


    public  void VendorReturnhelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("              VENDOR RETURNS    ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "Vendor Returns can be used to return the products in the store to the distributors either due to products being expired or any other reasons. The system provides two options for returning the product.\n" +
                "\n" +
                "With Goods Receipt(see fig1)\n" +
                "Without Goods Receipt(see fig2)\n" +
                "\n" +
                "Option 1:\n" +
                "\n" +
                "Objective:\n" +
                "User selects “With GRNID” option to return products with GRNID known.\n" +
                "\n" +
                "Option:\n" +
                "\n" +
                "Submit\n" +
                "Cancel\n" +
                "\n" +
                "Objective:\n" +
                "User selects “Without GRNID” option to return products without GRNID.\n" +
                "\n" +
                "Input Description:\n" +
                "Vendor /Distributor name\n" +
                "Reason of Return\n" +
                "Option:\n" +
                "Submit\n" +
                "Cancel\n" +
                "\n" +

                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }


    public void returnpopupdialog() {

        final LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdailog_vendorreturn, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final Button WithPo = (Button) alertLayout.findViewById(R.id.WithPoButton);
        final Button WithoutPo = (Button) alertLayout.findViewById(R.id.WithoutPObutton);
        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtn);
        final Button Cancel = (Button) alertLayout.findViewById(R.id.Cancelbtn);
        final Spinner VendornameForReturnSpinner=(Spinner)alertLayout.findViewById(R.id.VendororDistributorNameforVendorReturn);
        final Spinner GrnIdForVendorreturnSpinner=(Spinner)alertLayout.findViewById(R.id.Last3InvoiceNoVendorReturn);

        //final EditText LastInvoiceno = (EditText) alertLayout.findViewById(R.id.EnterInvoicenoforVendorReturn);
        alert.setCancelable(false);
        //LastInvoiceno.setVisibility(View.INVISIBLE);
        WithPo.setVisibility(View.VISIBLE);
        WithoutPo.setVisibility(View.VISIBLE);
        VendornameForReturnSpinner.setVisibility(View.INVISIBLE);
        GrnIdForVendorreturnSpinner.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        Cancel.setVisibility(View.VISIBLE);
        alert.setView(alertLayout);
        alert.setCancelable(true);
        TextView title = new TextView(this);
// You Can Customise your Title here
        title.setText("Select The Inventory Stock Document Options");
        title.setBackgroundColor(Color.DKGRAY);
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);

        alert.setCustomTitle(title);
         dialog = alert.create();
        WithPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithoutPo.setVisibility(View.INVISIBLE);
                VendornameForReturnSpinner.setVisibility(View.VISIBLE);
                GrnIdForVendorreturnSpinner.setVisibility(View.VISIBLE);
                submit.setVisibility(View.VISIBLE);
                ProductToreturnAutocomplete.setVisibility(View.GONE);
                // LastInvoiceno.setVisibility(View.VISIBLE);
            }
        });
        WithoutPo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WithPo.setVisibility(View.INVISIBLE);
                //LastInvoiceno.setVisibility(View.INVISIBLE);
                VendornameForReturnSpinner.setVisibility(View.INVISIBLE);
                GrnIdForVendorreturnSpinner.setVisibility(View.INVISIBLE);
                submit.setVisibility(View.INVISIBLE);
                dialog.dismiss();
            }
        });



        ArrayList<VendorModel> vendors = helper.getVendorsforVendorReturn();
        ArrayAdapter<VendorModel > stringArrayAdapter =
                new ArrayAdapter<VendorModel>(VendorReturnActivity.this, android.R.layout.simple_spinner_dropdown_item,vendors);
        VendornameForReturnSpinner.setAdapter(stringArrayAdapter);
        VendornameForReturnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), "OnItemSelected  :" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
                String Spinnervalue = VendornameForReturnSpinner.getSelectedItem().toString();
                Log.e("*************", Spinnervalue);
                ArrayList<String> LastInvoices = helper.getLastInvoicesForVendorReturn(Spinnervalue);
                ArrayAdapter<String> InvoiceNoAdapter =
                        new ArrayAdapter<String>(VendorReturnActivity.this, android.R.layout.simple_spinner_dropdown_item, LastInvoices);
                GrnIdForVendorreturnSpinner.setAdapter(InvoiceNoAdapter);
                GrnIdForVendorreturnSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String UserEnteredNumber=null;
                UserEnteredNumber=GrnIdForVendorreturnSpinner.getSelectedItem().toString().trim();
                GetthedatausingPoNumber = helper.getAllVendorreturndata(UserEnteredNumber);
                ArrayList<String>GetVendorNameforAutocomplete=helper.getVendorNameForAuto(UserEnteredNumber);
                if (GetthedatausingPoNumber != null && GetthedatausingPoNumber.size() > 0) {
                    if (ProductListAdapter == null) {
                        Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                        ProductListAdapter = new VendorReturnProductListAdapter(VendorReturnActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<VendorReturnModel>(), null);
                        lv_ProductVendorreturn.setAdapter(ProductListAdapter);
                    }
                    for (VendorReturnModel prod : GetthedatausingPoNumber) {
                        ProductListAdapter.addProductToList(prod);
                    }
                    VendororDistributorNameAutoComplete.setText("" + GetVendorNameforAutocomplete.toString().replace("[", "").replace("]", "" + ""));
                    VendororDistributorNameAutoComplete.removeTextChangedListener(selectVendorordistriName);
                    VendororDistributorNameAutoComplete.setEnabled(false);
                    ProductListAdapter.notifyDataSetChanged();
                    setSummaryRow();
                    ProductToreturnAutocomplete.setEnabled(false);
                    {
                        Toast.makeText(getApplicationContext(), "Ur Using With GrnID ", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();

                }

            }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

        });
        Cancel.setOnClickListener(new View.OnClickListener()

                                  {
                                      @Override
                                      public void onClick (View v){
                                          WithPo.setVisibility(View.VISIBLE);
                                          WithoutPo.setVisibility(View.VISIBLE);
                                          submit.setVisibility(View.VISIBLE);
                                          // LastInvoiceno.setVisibility(View.INVISIBLE);
                                          Intent intent = new Intent(getApplicationContext(), Activitypurchase.class);
                                          startActivity(intent);
                                      }
                                  }

        );
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null)
            dialog.dismiss();
        dialog = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);


        final MenuItem item2 = menu.findItem(R.id.TextView);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        user2 = (TextView) MenuItemCompat.getActionView(item2);


        // user2.setText(username);



        helper=new DBhelper(VendorReturnActivity.this);
        ArrayList<Registeremployeesmodel> user_name= helper.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(VendorReturnActivity.this, android.R.layout.simple_spinner_dropdown_item,user_name);

        spinner.setAdapter(stringArrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.d("slected item   :  ", iteam);

                if (!user2.getText().toString().trim().isEmpty()) {
                    iteam = spinner.getSelectedItem().toString();


//                    user2.setText("");
                    user2.setText(iteam );
                }

                else {

                    user2.setText(username);
                }



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        return true;


    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try{

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        }catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(VendorReturnActivity.this,Activitypurchase.class);
                startActivity(i);
                return true;
            case R.id.action_settinghelp:
                VendorReturnhelpdialog();
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(VendorReturnActivity.this);
                showIncentive.show();
                return true;

         /*   case R.id.action_settingpurchase:
                Intent p=new Intent(VendorReturnActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_Masterscn1:
                Intent intent = new Intent(VendorReturnActivity.this,Activity_masterScreen1.class);
                startActivity(intent);

                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(VendorReturnActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(VendorReturnActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(VendorReturnActivity.this ,login.class);
        startActivity(intent);
    }

    public void updatevendorreturn() {

        final DBhelper mydb = new DBhelper(VendorReturnActivity.this);

        for (VendorReturnModel purchase : ProductListAdapter.getList()) {


            PersistenceManager.saveStoreId(VendorReturnActivity.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
            store_id = PersistenceManager.getStoreId(VendorReturnActivity.this);

            vendor_return_id = x1;
            final String vendor_reason = ReasonSelected;
            final String vendor_amount = GrandTotal.getText().toString();
            final String vendor_name = VendororDistributorNameAutoComplete.getText().toString();
            Log.e("Vendor",vendor_name);
            final String vendor_batchno = purchase.getBatchno();
            final String vendor_exp_date = purchase.getExpdate();
            final String vendor_pur_price = String.valueOf(purchase.getPprice());
            final String vendor_prod_name = purchase.getProductName();
            final String vendor_uom = purchase.getUOM();
            final String vendor_qty = String.valueOf(purchase.getProductQuantity());
            final String Vendor_Con_MUL_Qty=String.valueOf(purchase.getStock()-purchase.getProductQuantity());
            final String vendor_total = String.valueOf(purchase.getTotal());
            final String pos_user = user2.getText().toString();
            final String vendor_Convfact = String.valueOf(purchase.getConvfact());



            final String vendorprefix="VEND_Return-";
            final String invoiceno=x1;
            class UpdateVendor_return extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // loading = ProgressDialog.show(VendorReturnActivity.this, "Logging in...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    // loading.dismiss();
                    Toast.makeText(VendorReturnActivity.this, s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try{
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put(Config.RETAIL_STR_VENDOR_STORE_ID,store_id);
                        hashMap.put(Config.RETAIL_STR_VENDOR_RETURN_ID,vendor_return_id);
                        hashMap.put(Config.RETAIL_STR_REASON_RETURN, vendor_reason);
                        hashMap.put(Config.RETAIL_STR_RETURN_AMOUNT, vendor_amount);
                        hashMap.put(Config.RETAIL_STR_VENDOR_NAME, vendor_name);
                        hashMap.put(Config.RETAIL_STR_PROD_NAME, vendor_prod_name);
                        hashMap.put(Config.RETAIL_STR_EXP_DATE, vendor_exp_date);
                        hashMap.put(Config.RETAIL_STR_PURCHASE_PRICE, vendor_pur_price);
                        hashMap.put(Config.RETAIL_STR_BATCH_NO, vendor_batchno);
                        hashMap.put(Config.RETAIL_STR_UOM, vendor_uom);
                        hashMap.put(Config.RETAIL_STR_QTY, vendor_qty);
                        hashMap.put(Config.RETAIL_STR_TOTAL, vendor_total);
                        hashMap.put(Config.Retail_Pos_user,pos_user);
                        hashMap.put(Config.RETAIL_CON_MUL_QTY,Vendor_Con_MUL_Qty);
                        hashMap.put(Config.Retail_VendorPayment_prefix, vendorprefix);
                        hashMap.put(Config.Retail_VendorPayment_PurchaseOrderNo,invoiceno);
                        hashMap.put(Config.Retail_VendorPayment_Universal_id,store_id);


                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.UPDATE_VENDOR_RETURN, hashMap);
                        Log.d("Login attempt", s.toString());

                        // Success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successfully Login!", s.toString());

                            mydb.updatevendorreturndetailreturn(vendor_return_id);
                            mydb.updatevendorreturnMasterreturn(vendor_return_id);
                            mydb.updateflagSavePdfDetailForvendorreturn(vendor_return_id);





                            //  return s.getString(TAG_MESSAGE);
                        } else {

                            //  return s.getString(TAG_MESSAGE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            }
            UpdateVendor_return updateVendorReturn = new UpdateVendor_return();
            updateVendorReturn.execute();
        }
    }


    public void UpdateShell() {


        class UpdateShell extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }
            @Override
            protected String doInBackground(Void... params) {
                try {
                    executerInventory(superuser);
                    String s = executerInventory(vendor_return);
                    Log.e("@@@Script Called", s);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }
        }

        UpdateShell updateShell= new UpdateShell();
        updateShell.execute();

    }


    public String executerInventory(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/vendor_return.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

}