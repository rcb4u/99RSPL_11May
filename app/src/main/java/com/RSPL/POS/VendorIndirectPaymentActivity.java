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
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.VendorModel;

public class VendorIndirectPaymentActivity extends Activity  {
        TextView Vendorpaymentno,Billno;
         Spinner selectVendorName;
         Spinner selecttheReason;
        ArrayList<VendorModel>vendors;
        ArrayList<String>SelectReasons;
        EditText PaymentAmount;
        Button Payycash;
        Button paybycheque;
    String Vendorselected;
    String selectedReasons;
        DBhelper helper;
    String mrp;
    String x_imei,store_id,universal_id,resval,BankNameSelected,ChequeNo;
    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    public static String username = null;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String backroundcolour,colorchange;
    String iteam;
    TextView user2;






    String RETAIL_INDIRECT_PAYMENT_CASH = "sh /sdcard/indirect_payment_cash.sh";
    String SUPERUSER ="su";



    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;


    String RETAIL_INDIRECT_PAYMENT_CHEQUE = "sh /sdcard/indirect_payment_cheque.sh";


    TextView tvdistributorname,tvgrnno,tvamountreceived,tvreason;


    @Override



    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_indirect_payment);

        username =  login.b.getString("Pos_User");

        helper = new DBhelper(VendorIndirectPaymentActivity.this);
        Decimal value = helper.getStoreprice();
        mrp=value.getDecimalmrp();
        String textsize=         value.getHoldpo();


        backroundcolour=  value.getColorbackround();



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

        Billno = (TextView)findViewById(R.id.sales_billno);
        Vendorpaymentno=(TextView)findViewById(R.id.VendorPay);
        TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tel.getDeviceId();
        Log.e("imei is :", device_id);
        Billno.setText("012345678912345");

        helper = new DBhelper(this);


        tvdistributorname = (TextView) findViewById(R.id.SelectvendorOrDistributorName);
        tvdistributorname.setTextSize(Float.parseFloat(textsize));
        tvgrnno = (TextView) findViewById(R.id.PaymentNumber);
        tvgrnno.setTextSize(Float.parseFloat(textsize));
        tvamountreceived = (TextView) findViewById(R.id.Selectpono);
        tvamountreceived.setTextSize(Float.parseFloat(textsize));
        tvreason = (TextView) findViewById(R.id.Enterthepaymentvalue);
        tvreason.setTextSize(Float.parseFloat(textsize));



        selectVendorName=(Spinner)findViewById(R.id.Vendornameforindirectpayment);
        selecttheReason=(Spinner)findViewById(R.id.selectthereason);
        PaymentAmount=(EditText)findViewById(R.id.VendorPaytotal);
        PaymentAmount.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,Integer.parseInt(mrp))});

        Payycash=(Button)findViewById(R.id.indirectpaybycash);
        paybycheque=(Button)findViewById(R.id.indirectpaybycheque);



       /* PaymentAmount.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                customFractionalKeyboard= new CustomFractionalKeyboard(VendorIndirectPaymentActivity.this, R.id.keyboard_number_indirct_payment, R.xml.fractional_keyboard);

                customFractionalKeyboard.registerEditText(R.id.VendorPaytotal);

                return false;
            }
        });

*/


//        final Long Value = System.currentTimeMillis();
//        final String resval = Long.toString(Value);
//       // Vendorpaymentno.setText(resval);
        invoiceno() ;
        vendors=helper.getvendorsforIndirectPayment();
        ArrayAdapter<VendorModel>VendorName=new ArrayAdapter<VendorModel>(this,android.R.layout.simple_spinner_item,vendors);
        selectVendorName.setAdapter(VendorName);
        selectVendorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vendorselected = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SelectReasons =helper.selecttheReason();
        ArrayAdapter<String>selectPaymentReasons=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SelectReasons);
        selecttheReason.setAdapter(selectPaymentReasons);
        selecttheReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedReasons = parent.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Payycash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              try {
                  if (Vendorselected.trim().isEmpty()) {
                      Toast.makeText(getApplicationContext(), " Please select vendorname or select distributor name", Toast.LENGTH_SHORT).show();
                      return;
                  } else if (selectedReasons.trim().isEmpty()) {
                      Toast.makeText(getApplicationContext(), " Please select reason of payment", Toast.LENGTH_SHORT).show();
                      return;
                  } else if (PaymentAmount.getText().toString().trim().isEmpty()) {
                      Toast.makeText(getApplicationContext(), " Please enter amount", Toast.LENGTH_SHORT).show();
                      return;
                  } else if (PaymentAmount.getText().toString().trim().equals("0") || PaymentAmount.getText().toString().equals("0.00")) {
                      Toast.makeText(getApplicationContext(), " Please enter a valid amount", Toast.LENGTH_SHORT).show();

                      return;
                  }
                  final Long Value = System.currentTimeMillis();
                  resval = Long.toString(Value);
                  helper.saveIndirectPayment(Vendorpaymentno.getText().toString(), Vendorselected, selectedReasons, PaymentAmount.getText().toString(), resval, user2.getText().toString());
                  helper.saveDataforPdfVendorIndirectpayment(Vendorpaymentno.getText().toString(), Vendorselected, user2.getText().toString());
                  UpdateVendorPayment();
                  UpdateShellindirectpaybycash();
                  Toast.makeText(getApplicationContext(), Vendorpaymentno.getText().toString(), Toast.LENGTH_SHORT).show();
                  Intent in = new Intent(getApplicationContext(), Activitypurchase.class);
                  startActivity(in);
              }catch (NullPointerException e){

                  e.printStackTrace();
              }
            }
        });
        paybycheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PaymentAmount.getText().toString().trim().equals(""))
                {
                    return;
                }
                AlertForChequePayment();
            }
        });

    }

    private void AlertForChequePayment() {
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.alertdialog_forvendorindirectpayment, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);

        final  Button submit=(Button)alertLayout.findViewById(R.id.submitpaymentbtn);
        final EditText EnterChequeno=(EditText)alertLayout.findViewById(R.id.EnterCheque1);
        final Spinner  BankName = (Spinner)alertLayout.findViewById(R.id.selectBank);
        final Button Cancel=(Button)alertLayout.findViewById(R.id.cancel);


        alertLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Context
                        .INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(EnterChequeno.getWindowToken(), 0);
                return true;
            }

        });


        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setTitle("                    Enter Bank Details ");
        final AlertDialog dialog =alert.create();

        ArrayList<String>BankNameArraylist=helper.getBankName();
        ArrayAdapter<String>BankNameAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,BankNameArraylist);
        BankName.setAdapter(BankNameAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    BankNameSelected = BankName.getSelectedItem().toString();
                    ChequeNo = EnterChequeno.getText().toString();

                    if (Vendorselected.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please Select VendorName or Select Distributor Name", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (selectedReasons.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please select Reason of Payment", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (PaymentAmount.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please Enter Amount", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        return;
                    } else if (BankNameSelected.trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please select Bank name", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (EnterChequeno.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), " Please Enter Cheque Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final Long Value = System.currentTimeMillis();

                    resval = Long.toString(Value);

                    helper.saveIndirectPaymentByCheque(Vendorpaymentno.getText().toString(), Vendorselected, selectedReasons, PaymentAmount.getText().toString(), BankNameSelected, EnterChequeno.getText().toString(), user2.getText().toString(), resval);
                    helper.saveDataforPdfVendorIndirectpayment(Vendorpaymentno.getText().toString(), Vendorselected, user2.getText().toString());
                    dialog.dismiss();
                    UpdateShellindirectpaybycheque();

                    Intent in = new Intent(getApplicationContext(), Activitypurchase.class);
                    startActivity(in);
                    UpdateVendorPaymentbycheque();
                    Toast.makeText(getApplicationContext(), Vendorpaymentno.getText().toString(), Toast.LENGTH_SHORT).show();

                }catch (NullPointerException e){

                    e.printStackTrace();
                }
            }

        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
    public  void invoiceno() {
        Long Value = System.currentTimeMillis();
        final String result = Long.toString(Value);
        String invoicevalue = Billno.getText().toString();
        helper = new DBhelper(this);

        ArrayList<String> billno = helper.getimeino();
        for (String str : billno) {
            if (str.equals(invoicevalue))
            {
                ArrayList<String> imei = helper.getprefix(str);
                Log.e("%%%%%%%%%%%%%", imei.toString());
                x_imei=imei.toString();
                String x1=  x_imei.replace("[", "").replace("]","").concat(result);
                Log.e("X1_imei is :",x1);
                Vendorpaymentno.setText(x1);
            } else {
                continue;
            }
        }
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



        helper=new DBhelper(VendorIndirectPaymentActivity.this);
        ArrayList<Registeremployeesmodel> user_name= helper.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(VendorIndirectPaymentActivity.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()){
            case R.id.action_settings:

            Intent i=new Intent(VendorIndirectPaymentActivity.this,Activitypurchase.class);
            startActivity(i);
            return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(VendorIndirectPaymentActivity.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                VendorPaymenthelpdialog();
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(VendorIndirectPaymentActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(VendorIndirectPaymentActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent r=new Intent(VendorIndirectPaymentActivity.this,Activity_masterScreen1.class);
                startActivity(r);
                return true;

            case R.id.action_logout:
                Logout();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Logout()
    {
        Intent intent = new Intent(VendorIndirectPaymentActivity.this ,login.class);
        startActivity(intent);
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


    public  void VendorPaymenthelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("           VENDOR  INDIRECT PAYMENTS       ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "The user selects the vendor name and the reason for making payment. These reasons are pre-configured but can be added more using the System Settings. The user can then enter the amount and select the option.\n" +
                "\n" +
                "Pay By Cash\n" +
                "\n" +
                "Pay By Cheque – The user selects this option to enter the cheque details. \n" +
                "\n" +
                "\n" +
                "Option:Pay by Cheque:\n" +
                "\n" +
                "Objective:\n" +
                "User selects pay by cheque option by entering bank detail form.\n" +
                "\n" +
                "Input Description:\n" +
                "Bank Name: Enter name of Bank.\n" +
                "Cheque no.: Enter Cheque Number.\n" +
                "\n" +
                "Option:\n" +
                "Submit\n" +
                "Cancel\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



                dialog.dismiss();
            }
        });


        alertDialog.show();
    }


    public void UpdateVendorPayment() {

//        PurchaseProductModel purchase = new PurchaseProductModel();
        final String VendorPaymentPaymentID =resval ;
        final String VendorPaymentreason =selectedReasons ;
        final String vendorPaymentname = Vendorselected;
        final String VendorPaymentPurchaseOrderNumber=Vendorpaymentno.getText().toString();
        final String VendorPaymentUserPayamount=PaymentAmount.getText().toString();
        final String vendorprefix="VEND_INDIRECT-";
        final String VendorPaymentusername=user2.getText().toString();

        PersistenceManager.saveStoreId(VendorIndirectPaymentActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(VendorIndirectPaymentActivity.this);
        universal_id = PersistenceManager.getStoreId(VendorIndirectPaymentActivity.this);

        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_VendorPayment_store_id, store_id);
                    hashMap.put(Config.Retail_VendorPayment_PayID, VendorPaymentPaymentID);
                    hashMap.put(Config.Retail_VendorPayment_PurchaseOrderNo,VendorPaymentPurchaseOrderNumber);
                    hashMap.put(Config.Retail_VendorPayment_Vendorname, vendorPaymentname);
                    hashMap.put(Config.Retail_VendorPayment_Reason, VendorPaymentreason);
                    hashMap.put(Config.Retail_VendorPayment_Amount, VendorPaymentUserPayamount);
                    hashMap.put(Config.Retail_Pos_user, VendorPaymentusername);
                    hashMap.put(Config.Retail_VendorPayment_payment_date, getDate());
                    hashMap.put(Config.Retail_VendorPayment_prefix, vendorprefix);
                    //  hashMap.put(Config.Retail_VendorPayment_Bank_Name, VendorPaymentBankName);
                    // hashMap.put(Config.Retail_VendorPayment_Cheque_No, VendorPaymentChequeNumber);
                    //  hashMap.put(Config.Retail_VendorPayment_Universal_id, universal_id);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.INDIRECT_PAYMENT_CASH, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        //       return s.getString(TAG_MESSAGE);
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
                // loading = ProgressDialog.show(VendorPaymentActivity.this, "UPDATING...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    loading.dismiss();
                Toast.makeText(VendorIndirectPaymentActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    public void UpdateVendorPaymentbycheque() {


        //  final String vendorPaymentname = Vendorselected;
        final String VendorPaymentPurchaseOrderNumber=Vendorpaymentno.getText().toString();
        final String VendorPaymentBankName=BankNameSelected;
        final String VendorPaymentChequeNumber= ChequeNo;
        final String pos_user = user2.getText().toString();
        final String vendorprefix="VEND_INDIRECT-";
        final String VendorPaymentUserPayamount=PaymentAmount.getText().toString();


        //   final String inventoryconvmrp = String.valueOf(purchase.);

        PersistenceManager.saveStoreId(VendorIndirectPaymentActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(VendorIndirectPaymentActivity.this);
        universal_id = PersistenceManager.getStoreId(VendorIndirectPaymentActivity.this);

        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_VendorPayment_store_id, store_id);
                    hashMap.put(Config.Retail_VendorPayment_PayID, resval.toString());
                    hashMap.put(Config.Retail_VendorPayment_PurchaseOrderNo,VendorPaymentPurchaseOrderNumber);
                    hashMap.put(Config.Retail_VendorPayment_Vendorname, Vendorselected);
                    hashMap.put(Config.Retail_VendorPayment_Reason, selectedReasons);
                    hashMap.put(Config.Retail_VendorPayment_Bank_Name, VendorPaymentBankName);
                    hashMap.put(Config.Retail_VendorPayment_Cheque_No, VendorPaymentChequeNumber);
                    //   hashMap.put(Config.Retail_VendorPayment_Universal_id, universal_id);
                    hashMap.put(Config.Retail_Pos_user, pos_user);
                    hashMap.put(Config.Retail_VendorPayment_prefix, vendorprefix);
                    hashMap.put(Config.Retail_VendorPayment_payment_date, getDate());
                    hashMap.put(Config.Retail_VendorPayment_Amount, VendorPaymentUserPayamount);
                    hashMap.put(Config.Retail_VendorPayment_Received_Amount, VendorPaymentUserPayamount);





                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.INDIRECT_PAYMENT_CHEQUE, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());

                        helper.updatevendorpaymentflagdetail(resval);
                        //  helper.updateflagsaveGranddataintoGrnMaster(resval);
                        helper.updateflagSavePdfDetailForvendor(resval);



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
                // loading = ProgressDialog.show(VendorPaymentActivity.this, "UPDATING...", "Wait...", false, false);
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //    loading.dismiss();
                // Toast.makeText(VendorIndirectPaymentActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }




    @Override
    public String toString() {
        return resval;
    }






    public void UpdateShellindirectpaybycash() {


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
                executerpaybycash(SUPERUSER);
                String s= executerpaybycash(RETAIL_INDIRECT_PAYMENT_CASH);
                Log.e("@@@Script Called",s);

                return null;
            }
        }

        UpdateShell updateShell= new UpdateShell();
        updateShell.execute();

    }


    public String executerpaybycash(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/indirect_payment_cash.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }

    public void UpdateShellindirectpaybycheque() {


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
                executerpaybycheque(SUPERUSER);
                String s= executerpaybycash(RETAIL_INDIRECT_PAYMENT_CHEQUE);
                Log.e("@@@Script Called",s);

                return null;
            }
        }

        UpdateShell updateShell= new UpdateShell();
        updateShell.execute();

    }


    public String executerpaybycheque(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/indirect_payment_cash.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;
    }


}
