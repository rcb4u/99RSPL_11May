package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Adapter.CreditCustomerAdapter;
import Adapter.CreditcustNameadapter;
import Adapter.SettleCreditcustAdapter;
import Pojo.Decimal;
import Pojo.Settlecustmodel;

public class Activity_SettleCreditCustomer extends Activity {
    DBhelper db;
    AutoCompleteTextView searchcustname;
    CreditCustomerAdapter creditCustomerAdapter;
    CreditcustNameadapter creditcustNameadapter;
    SettleCreditcustAdapter settleCreditcustAdapter;
    ArrayList<Settlecustmodel> settlecustmodelArrayList;
    ArrayList<Settlecustmodel> CreditcustNamelist;
    TextView creditcustinvoice;
    ActionBar actionBar;
    ListView custlistView;
    PendingIntent mPermissionIntent;
    UsbDevice device;
    UsbController usbController;
    UsbManager mUsbManager;
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";

 /*   protected final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice devices = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (devices != null) {
                            //call method to set up device communication
                            Log.d("Connection", "Suicide");

                        }
                    } else {
                        Log.d("Tag", "permission denied for device " + devices);
                    }
                }
            }
        }

    };
*/
 String backroundcolour,colorchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity__settle_credit_customer);

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        searchcustname = (CustomAuto) findViewById(R.id.creditmyautocomplete);
        db = new DBhelper(this);
        custlistView = (ListView) findViewById(R.id.custlistView);
        creditcustinvoice=(TextView)findViewById(R.id.creditcustinvoice);
        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        Decimal valuetextsize = db.getStoreprice();
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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        searchcustname.setThreshold(1);

/*      mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = mUsbManager.getDeviceList();

        Log.d("Device", String.valueOf(deviceList));
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();

        mPermissionIntent = PendingIntent.getBroadcast(Activity_SettleCreditCustomer.this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        while (deviceIterator.hasNext()) {
            device = deviceIterator.next();
            mUsbManager.requestPermission(device, mPermissionIntent);

        }
        registerReceiver(mUsbReceiver, filter);
*/
        TextWatcher cTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if (searchcustname.isPerformingCompletion()) {
                    Log.d("Debuging", "Performing completion ");
                    return;
                }
                String userTypedString = searchcustname.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                CreditcustNamelist = db.getAllsettleCreditCustomer(userTypedString);

                if (CreditcustNamelist != null) {
                    if (creditcustNameadapter == null) {
                        creditcustNameadapter = new CreditcustNameadapter(Activity_SettleCreditCustomer.this, android.R.layout.simple_dropdown_item_1line, CreditcustNamelist);
                        creditcustNameadapter.setList(CreditcustNamelist);
                        searchcustname.setAdapter(creditcustNameadapter);
                        searchcustname.setThreshold(1);
                    } else {
                        creditcustNameadapter.setList(CreditcustNamelist);
                        creditcustNameadapter.notifyDataSetChanged();
                    }
                }
            }
        };
        searchcustname.addTextChangedListener(cTextWatcher);
        searchcustname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Settlecustmodel result = (Settlecustmodel) parent.getItemAtPosition(position);
                // String invoice=result.toString();
                if (settleCreditcustAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                    settleCreditcustAdapter = new SettleCreditcustAdapter(Activity_SettleCreditCustomer.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Settlecustmodel>(),device);
                    custlistView.setAdapter(settleCreditcustAdapter);


                }
                settleCreditcustAdapter.addProductToList(result);
                Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                settleCreditcustAdapter.notifyDataSetChanged();
                searchcustname.setText("");
                //  creditcustinvoice.setText(invoice);
            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity_settle_creditcustomer, menu);
        // inflater.inflate(R.menu.activity_main_actions, menu);
        // inflater.inflate(R.menu.menu_activity_salesbill,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        try {
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

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i = new Intent(Activity_SettleCreditCustomer.this, ActivitySalesbill.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_SettleCreditCustomer.this);
                showIncentive.show();
                return true;

            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_SettleCreditCustomer.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;
            case R.id.action_settinginv:
                Intent in = new Intent(Activity_SettleCreditCustomer.this, InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_logout:
                Logout();

                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void Logout()
    {
        Intent intent = new Intent(Activity_SettleCreditCustomer.this ,login.class);
        startActivity(intent);
    }


    public void GetCreditDetails(String Name,String outstandingblance){

        Log.e("DailySales", Name);
        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", Name);
        dataBundle.putString("outstandblance", outstandingblance);
        Intent intent = new Intent(Activity_SettleCreditCustomer.this, BillDetails.class);
        intent.putExtras(dataBundle);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }


}