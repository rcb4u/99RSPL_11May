

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
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import Adapter.PurchaseproductlistAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.VendorModel;

public class VendorPaymentActivity extends Activity  {
    PurchaseproductlistAdapter productlistAdapter;
    public static String username = null;

    TextView VendorPayGrandtotal;
    Spinner VendorSpinner;
    Spinner PO_NoSpinner;
    DBhelper helper;
    Button PaybyCash;
    Button PaybyCheque;
    String PO_Numberselected,store_id,universal_id;
    String Vendorselected,mrp;
    ArrayAdapter<VendorModel> VendorName;
    ArrayList<String> Ponumbers;
    ArrayList<String>VendoReturnIDArrayList;
    EditText UserPayAmount;
    TextView VendorDuePayAmount,Billno;
    TextWatcher dueAMountTextWatcher;
    String iteam;
    TextView user2;

    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    String resval,BankNameSelected,ChequeNo;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";


    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;

    TextView tvdistributorname,tvgrnno,tvamountreceived,tvgrand,tvdue;
    String backroundcolour,colorchange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_vendor_payment);

        final String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        helper = new DBhelper(this);
        username =  login.b.getString("Pos_User");

        helper = new DBhelper(VendorPaymentActivity.this);
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


//////////////////////////////////////////jimmmyy////////////////////////////////////////////////
        tvdistributorname = (TextView) findViewById(R.id.SelectvendorOrDistributorName);
        tvdistributorname.setTextSize(Float.parseFloat(textsize));
        tvgrnno = (TextView) findViewById(R.id.Selectpono);
        tvgrnno.setTextSize(Float.parseFloat(textsize));
        tvamountreceived = (TextView) findViewById(R.id.Amount_Received);
        tvamountreceived.setTextSize(Float.parseFloat(textsize));
        tvgrand = (TextView) findViewById(R.id.grandAmount);
        tvgrand.setTextSize(Float.parseFloat(textsize));
        tvdue = (TextView) findViewById(R.id.Amount_Due_toPay);
        tvdue.setTextSize(Float.parseFloat(textsize));




        VendorPayGrandtotal = (TextView) findViewById(R.id.VendorPayGrandtotal);
        VendorPayGrandtotal.setTextSize(Float.parseFloat(textsize));


        UserPayAmount= (EditText) findViewById(R.id.VendorPayAmount);
        UserPayAmount.setTextSize(Float.parseFloat(textsize));

        UserPayAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(mrp))});


        VendorDuePayAmount= (TextView) findViewById(R.id.VendorDuePayAmount);
        VendorDuePayAmount.setTextSize(Float.parseFloat(textsize));

        VendorDuePayAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});


        VendorSpinner = (Spinner) findViewById(R.id.VendororDistributorName);
        PO_NoSpinner = (Spinner) findViewById(R.id.Lastinvoiceno);
/*
        VendorReturnID=(Spinner)findViewById(R.id.VendorReturnID);
*/

        PaybyCash = (Button) findViewById(R.id.Paybycashbtn);
        PaybyCheque = (Button) findViewById(R.id.paybychequebtn);
        //  PaybyWallet=(Button)findViewById(R.id.paybyWalletbtn);
        //   PaybyCARD=(Button)findViewById(R.id.paybycardbtn);






        final ArrayList<VendorModel> vendors = helper.getVendorsForVendorPayment();
        VendorName= new ArrayAdapter<VendorModel>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, vendors);

        VendorSpinner.setAdapter(VendorName);
        VendorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vendorselected = parent.getSelectedItem().toString();
                Ponumbers = helper.getGrnNumber(Vendorselected);
                ArrayAdapter<String> Purchasrordernumber = new ArrayAdapter<String>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, Ponumbers);
                PO_NoSpinner.setAdapter(Purchasrordernumber);
                PO_NoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        PO_Numberselected = parent.getSelectedItem().toString();
                        ArrayList<String> grandtotal = helper.getGrandTotalforVendorPayment(PO_Numberselected);
                        VendorPayGrandtotal.setText(grandtotal.get(0));
                        UserPayAmount.setText(VendorPayGrandtotal.getText().toString());
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
        // AmountDuePayment();

        if( (UserPayAmount.getTag() != null)  && (UserPayAmount.getTag() instanceof TextWatcher) ) {
            UserPayAmount.removeTextChangedListener((TextWatcher) UserPayAmount.getTag() );
        }
        dueAMountTextWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f=new DecimalFormat("##.00");

                try {
                    if (UserPayAmount.getText().toString().equals("")||VendorPayGrandtotal.getText().toString().trim().equals("") ) {
                        VendorDuePayAmount.setText("");
                        return;
                    }

                    VendorDuePayAmount.setText(String.valueOf(f.format(Double.parseDouble(VendorPayGrandtotal.getText().toString()) - Double.parseDouble(UserPayAmount.getText().toString()))));
                } catch (Exception ex) {
                    ex .printStackTrace();
                }

            }

        };
        UserPayAmount.addTextChangedListener(dueAMountTextWatcher);
        UserPayAmount.setTag(dueAMountTextWatcher);

        VendoReturnIDArrayList=helper.getVendorReturndataForVendorPayment();
        if (VendoReturnIDArrayList==null){
            return;
        }
      /*  ArrayAdapter<String>VendorReturnIDAdpater=new ArrayAdapter<String>(VendorPaymentActivity.this, android.R.layout.simple_list_item_1, VendoReturnIDArrayList);
        VendorReturnID.setAdapter(VendorReturnIDAdpater);*/
    /*    PaybyCARD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CardAlertDialog();

            }
        });*/
        PaybyCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if (VendorName.isEmpty()) {
                        return;
                    }
                    final Long Value = System.currentTimeMillis();
                    resval = Long.toString(Value);
                    if (UserPayAmount.getText().toString().equals("")) {
                        return;
                    } else if (VendorDuePayAmount.getText().toString().equals("0.00") || VendorDuePayAmount.getText().toString().equals("0.0") || VendorDuePayAmount.getText().toString().equals("0") || VendorDuePayAmount.getText().toString().equals("00.00")) {
                        return;
                    }
                    if (VendorPayGrandtotal.getText().toString().equals(UserPayAmount.getText().toString()) || Double.parseDouble(UserPayAmount.getText().toString()) > Double.parseDouble(VendorPayGrandtotal.getText().toString())) {
                        helper.savedirectPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString(), resval, UserPayAmount.getText().toString(), VendorDuePayAmount.getText().toString(),user2.getText().toString());
                        helper.updateintoGrnMasterTableForVendorPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString());
                        helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected,user2.getText().toString());
                        UpdateVendorPayment();
                    } else {
                        helper.savedirectPayment(Vendorselected, PO_Numberselected, UserPayAmount.getText().toString(), resval, UserPayAmount.getText().toString(), VendorDuePayAmount.getText().toString(),user2.getText().toString());
                        helper.updateintoGrnMasterForDueAmount(Vendorselected, PO_Numberselected, VendorDuePayAmount.getText().toString());
                        helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected,user2.getText().toString());
                        UpdateVendorPayment();
                    }

                    Toast.makeText(getApplicationContext(), PO_Numberselected, Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(getApplicationContext(), Activityvendorpayment.class);
                    startActivity(in);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

            }
        });

        PaybyCheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserPayAmount.getText().toString().trim().equals("") ||VendorPayGrandtotal.getText().toString().trim().equals("") )
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


        if (VendorName.isEmpty())
        {
            return;
        }else {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    try {
                        BankNameSelected = BankName.getSelectedItem().toString();
                        //EnterChequeno.getText().toString();
                        final Long Value = System.currentTimeMillis();
                        resval = Long.toString(Value);
                        if (UserPayAmount.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(), " Please enter amount", Toast.LENGTH_SHORT).show();

                            return;
                        } else if (BankNameSelected.trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), " Please select Bank name", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (EnterChequeno.getText().toString().trim().isEmpty()) {
                            Toast.makeText(getApplicationContext(), " Please Enter Cheque Number", Toast.LENGTH_SHORT).show();
                            return;
                        }
                   /* if ( Double.parseDouble(UserPayAmount.getText().toString())>Double.parseDouble(VendorPayGrandtotal.getText().toString()))
                    {

                        Toast.makeText(getApplicationContext(),"Amount Is Greater than Payment" ,Toast.LENGTH_SHORT).show();
                        return;

                    }*/
                        else if (VendorDuePayAmount.getText().toString().equals("0.00") || VendorDuePayAmount.getText().toString().equals("0.0") || VendorDuePayAmount.getText().toString().equals("0") || VendorDuePayAmount.getText().toString().equals("00.00")) {
                            return;
                        }
                        ChequeNo = EnterChequeno.getText().toString();

                        if (VendorPayGrandtotal.getText().toString().equals(UserPayAmount.getText().toString()) || VendorDuePayAmount.getText().toString().equals("0.00")) {
                            helper.savedirectPaymentwithpobycheque(PO_Numberselected, Vendorselected, VendorPayGrandtotal.getText().toString(), BankNameSelected, EnterChequeno.getText().toString(), resval, UserPayAmount.getText().toString(), VendorDuePayAmount.getText().toString(), user2.getText().toString());
                            helper.updateintoGrnMasterTableForVendorPayment(Vendorselected, PO_Numberselected, VendorPayGrandtotal.getText().toString());
                            helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected, user2.getText().toString());
                            UpdateVendorPaymentbycheque();
                        } else {
                            helper.savedirectPaymentwithpobycheque(PO_Numberselected, Vendorselected, UserPayAmount.getText().toString(), BankNameSelected, EnterChequeno.getText().toString(), resval, UserPayAmount.getText().toString(), VendorDuePayAmount.getText().toString(), user2.getText().toString());
                            helper.updateintoGrnMasterForDueAmount(Vendorselected, PO_Numberselected, VendorDuePayAmount.getText().toString());
                            helper.saveDataforPdfVendorPayment(PO_Numberselected, Vendorselected, user2.getText().toString());
                            UpdateVendorPaymentbycheque();
                        }

                        Intent in = new Intent(getApplicationContext(), Activityvendorpayment.class);
                        startActivity(in);

                        Toast.makeText(getApplicationContext(), PO_Numberselected, Toast.LENGTH_SHORT).show();
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
        }

        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    private void AlertForMcashpayment(){
        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.walletpopup_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final Button cancel=(Button) alertLayout.findViewById(R.id.cancelForWallet);
        final Button Submit=(Button) alertLayout.findViewById(R.id.submitForWallet);

        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setTitle("                    Enter MCASH Details ");
        final AlertDialog dialog =alert.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }



    public void CardAlertDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.cardpayment_dialog, null);
        final Button cancel=(Button)alertLayout.findViewById(R.id.search_button);
        final Button visabtn =(Button)alertLayout.findViewById(R.id.visabutton);
        final Button mastrobtn =(Button)alertLayout.findViewById(R.id.mastrobutton);
        final Button masterbtn =(Button)alertLayout.findViewById(R.id.masterbutton);
        final Button cancelpay =(Button)alertLayout.findViewById(R.id.cancelopayment_button);
        final Button pay =(Button)alertLayout.findViewById(R.id.addtopayment_button);


        final EditText amount=(EditText)alertLayout.findViewById(R.id.entramount);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(true);
        alert.setView(alertLayout);
        alert.setTitle("CARD PAYMENT");
        final AlertDialog dialog = alert.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();

        cancelpay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        visabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mastrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        masterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
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



        helper=new DBhelper(VendorPaymentActivity.this);
        ArrayList<Registeremployeesmodel> user_name= helper.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(VendorPaymentActivity.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

    public  void VendorPaymenthelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                     VENDOR  PAYMENTS       ");
        alertDialog.setMessage("Objective:\n" +
                "\n" +
                "Vendor Payment:\n" +
                "The user selects the vendor number and the invoice to be paid. The total amouyt is defaulted. The user can change the value to do “Partial payments” as well. The user can select two options:\n" +
                "\n" +
                "Pay By Cash\n" +
                "Pay By Cheque – The user selects this option to enter the cheque details. \n" +
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
                "Cancel\n" +
                "\n" +
                "Option 2:\n" +
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {

            //noinspection SimplifiableIfStatement
            case R.id.action_settings:

                Intent i=new Intent(VendorPaymentActivity.this,Activitypurchase.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(VendorPaymentActivity.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                VendorPaymenthelpdialog();
                return true;
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(VendorPaymentActivity.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/


            case R.id.action_settinginv:
                Intent in=new Intent(VendorPaymentActivity.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(VendorPaymentActivity.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_Masterscn1:
                Intent r=new Intent(VendorPaymentActivity.this,Activity_masterScreen1.class);
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
        Intent intent = new Intent(VendorPaymentActivity.this ,login.class);
        startActivity(intent);
    }


    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }



    public void UpdateVendorPayment() {

//        PurchaseProductModel purchase = new PurchaseProductModel();
        final String VendorPaymentPaymentID =resval ;
        final String vendorPaymentname = Vendorselected;
        final String VendorPaymentPurchaseOrderNumber=PO_Numberselected;
        final String VendorPaymentgrandtotal =VendorPayGrandtotal.getText().toString();
        final String VendorPaymentDueAmount=VendorDuePayAmount.getText().toString();
        final String VendorPaymentUserPayamount=UserPayAmount.getText().toString();
        final String VendorPaymentBankName=BankNameSelected;
        final String VendorPaymentChequeNumber= ChequeNo;
        final String vendorprefix="VEND_PAY-";
        final String pos_user = user2.getText().toString();

        //   final String inventoryconvmrp = String.valueOf(purchase.);

        PersistenceManager.saveStoreId(VendorPaymentActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(VendorPaymentActivity.this);
        universal_id = PersistenceManager.getStoreId(VendorPaymentActivity.this);

        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.RETAIL_STR_VENDOR_STORE_ID,store_id);
                    hashMap.put(Config.Retail_VendorPayment_PayID,VendorPaymentPaymentID);
                    hashMap.put(Config.Retail_VendorPayment_PurchaseOrderNo,VendorPaymentPurchaseOrderNumber);
                    hashMap.put(Config.Retail_VendorPayment_Vendorname,vendorPaymentname);
                    hashMap.put(Config.Retail_VendorPayment_Amount, VendorPaymentgrandtotal);
                    hashMap.put(Config.Retail_VendorPayment_Received_Amount, VendorPaymentUserPayamount);
                    hashMap.put(Config.Retail_VendorPayment_Due_Amount, VendorPaymentDueAmount);
                    //  hashMap.put(Config.Retail_VendorPayment_Bank_Name, VendorPaymentBankName);
                    // hashMap.put(Config.Retail_VendorPayment_Cheque_No, VendorPaymentChequeNumber);
                    hashMap.put(Config.Retail_VendorPayment_Universal_id,store_id);
                    hashMap.put(Config.Retail_VendorPayment_payment_date, getDate());
                    hashMap.put(Config.Retail_VendorPayment_prefix, vendorprefix);
                    hashMap.put(Config.Retail_Pos_user,pos_user);


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.DIRECT_PAYMENT_CASH, hashMap);
                    Log.d("Login attempt", s.toString());



                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        // if(VendorPayGrandtotal.getText().toString().equals(UserPayAmount.getText().toString()))
                        helper.updatevendorpaymentflagdetail(resval);
                        helper.updateflagsaveGranddataintoGrnMaster(resval);
                        helper.updateflagSavePdfDetailForvendor(resval);
                        //         return s.getString(TAG_MESSAGE);
                    } else {

                        //   return s.getString(TAG_MESSAGE);

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
                //  Toast.makeText(VendorPaymentActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }









    public void UpdateVendorPaymentbycheque() {

//        PurchaseProductModel purchase = new PurchaseProductModel();



        final String VendorPaymentgrandtotal =VendorPayGrandtotal.getText().toString();
        final String VendorPaymentDueAmount=VendorDuePayAmount.getText().toString();
        final String VendorPaymentUserPayamount=UserPayAmount.getText().toString();
        final String VendorPaymentBankName=BankNameSelected;
        final String VendorPaymentChequeNumber= ChequeNo;
        final String vendorprefix="VEND_PAY-";
        final String invoiceno="InvoiceNO";
        final String pos_user = user2.getText().toString();


        //   final String inventoryconvmrp = String.valueOf(purchase.);

        PersistenceManager.saveStoreId(VendorPaymentActivity.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(VendorPaymentActivity.this);
        universal_id = PersistenceManager.getStoreId(VendorPaymentActivity.this);

        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_VendorPayment_store_id, store_id);
                    hashMap.put(Config.Retail_VendorPayment_PayID, resval);
                    hashMap.put(Config.Retail_VendorPayment_PurchaseOrderNo,invoiceno);
                    hashMap.put(Config.Retail_VendorPayment_Vendorname, Vendorselected);
                    hashMap.put(Config.Retail_VendorPayment_Amount, VendorPaymentgrandtotal);
                    hashMap.put(Config.Retail_VendorPayment_Received_Amount, VendorPaymentUserPayamount);
                    hashMap.put(Config.Retail_VendorPayment_Due_Amount, VendorPaymentDueAmount);
                    hashMap.put(Config.Retail_VendorPayment_Bank_Name, VendorPaymentBankName);
                    hashMap.put(Config.Retail_VendorPayment_Cheque_No, VendorPaymentChequeNumber);
                    hashMap.put(Config.Retail_VendorPayment_payment_date, getDate());
                    hashMap.put(Config.Retail_VendorPayment_prefix, vendorprefix);
                    hashMap.put(Config.Retail_Pos_user,pos_user);

                    hashMap.put(Config.Retail_VendorPayment_Universal_id,store_id);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.DIRECT_PAYMENT_CHEQUE, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1 && VendorPaymentgrandtotal.equals(VendorPaymentUserPayamount) || Double.parseDouble(VendorPaymentUserPayamount) > Double.parseDouble(VendorPaymentgrandtotal)) {
                        Log.d("Successfully Login!", s.toString());
                        helper.updatevendorpaymentflagdetail(resval);
                        helper.updateflagsaveGranddataintoGrnMaster(resval);
                        helper.updateflagSavePdfDetailForvendor(resval);


                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        //   return s.getString(TAG_MESSAGE);

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
                //  Toast.makeText(VendorPaymentActivity.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }





}
