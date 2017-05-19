
package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Pojo.Decimal;


/**
 * Created by w7 on 1/27/2016.
 */
public class
Activity_masterScreen1 extends Activity implements View.OnClickListener {
    ActionBar actionBar;



    private AlarmManager alarmManager_sh;
    private PendingIntent pendingIntent_sh;
    DBhelper mydb;
    String backroundcolour,colorchange;


    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_master_screen1);


        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar=getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(Activity_masterScreen1.this);

        Decimal valuetextsize = mydb.getStoreprice();

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
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_colors);
        layout.setBackgroundColor(Color.parseColor(colorchange));

       actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));

        ImageButton imageButton=(ImageButton)findViewById(R.id.mastermanagement_button);
        ImageButton imageButton2=(ImageButton)findViewById(R.id.sales_button);
        ImageButton imageButton3=(ImageButton)findViewById(R.id.purchase_button);
        ImageButton imageButton4=(ImageButton)findViewById(R.id.report_button);
        ImageButton imageButton6=(ImageButton)findViewById(R.id.systemsupport_button);
        // ImageButton imageButton7=(ImageButton)findViewById(R.id.Internetorderbutton);
        ImageButton imageButton8=(ImageButton)findViewById(R.id.systemsetting_button);
       // Button Button9=(Button)findViewById(R.id.screen_issue);

        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        // imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        //Button9.setOnClickListener(this);




    }

    //    @Override
//    public void onBackPressed() {
//        // nothing to do here
//        // … really
//    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mastermanagement_button:

                Intent intent = new Intent(this, Activity_masterScreen2.class);
                startActivity(intent);
                break;
            case R.id.sales_button:
                Intent intent1 = new Intent(this, ActivitySales.class);
                startActivity(intent1);
                break;
            case R.id.purchase_button:
                Intent intent2 = new Intent(this, Activitypurchase.class);
                startActivity(intent2);
                break;
            case R.id.report_button:
                Intent intent3 = new Intent(this, ActivityLoyalityCust.class);
                startActivity(intent3);
                break;
            case R.id.systemsupport_button:
                Intent intent4 = new Intent(this,ActivityMainMaintainence.class);
                startActivity(intent4);
                break;
//            case R.id.Internetorderbutton:
//                Intent intent5 = new Intent(this,BiilinternetActivity.class);
//                startActivity(intent5);
//                break;
            case R.id.systemsetting_button:
                Intent intent6 = new Intent(this,ActivityLoyality.class);
                startActivity(intent6);
                break;
          /*  case R.id.screen_issue:
                Intent intent7 = new Intent(this,ActivityStoreScreenFeedback.class);
                startActivity(intent7);
                break;*/



        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);


        MenuItem item = menu.findItem(R.id.spinner_user);
        item.setVisible(false);

        final MenuItem item2 = menu.findItem(R.id.TextView);

        item2.setVisible(false);

        return true;

    }

    public void Logout()
    {
        Intent intent = new Intent(Activity_masterScreen1.this ,login.class);
        startActivity(intent);
    }




    public void MasterscreenDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  HOME PAGE   ");
        alertDialog.setMessage("Objective:\n" +
                "\t\n" +
                "Following are the option available on the home page:\n" +
                "\n" +
                "\t*Master Data Management \n" +
                "\t*Purchasing\n" +
                "\t*Sales Order Management\n" +
                "\t*System Settings\n" +
                "\t*System Maintenance\n" +
                "\t*Reports\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();



    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
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
                return true;

            case R.id.action_settinghelp:
                MasterscreenDialog();
                return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_masterScreen1.this);
                showIncentive.show();
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_masterScreen1.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_settinginv:
                Intent in=new Intent(Activity_masterScreen1.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_masterScreen1.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }



}



//////////////////////////////////////////code for master screen having force bluetooth for pano,pierson ////////////////////////////////////
/*
package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ngx.USBPrinter;

import org.json.JSONObject;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteStatement;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.RSPL.POS.login.mChatService;


*/
/**
 * Created by w7 on 1/27/2016.
 *//*

public class Activity_masterScreen1 extends Activity implements View.OnClickListener {



    private static final String TAG = "BluetoothChat";
    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services

    public static SharedPreferences mSp;

    private String mConnectedDeviceName = "";


    public static USBPrinter UPrinter = USBPrinter.INSTANCE;

    Boolean statusBluetooth;


    ActionBar actionBar;
    private static final boolean D = true;
    AlertDialog dialog;

    private AlarmManager alarmManager_sh;
    private PendingIntent pendingIntent_sh;


    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
       // mChatService.start();
        setContentView(R.layout.activity_master_screen1);


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED && mChatService!=null) {


            BluetoothAlertDialog();
        }






        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        ImageButton imageButton = (ImageButton) findViewById(R.id.mastermanagement_button);
        ImageButton imageButton2 = (ImageButton) findViewById(R.id.sales_button);
        ImageButton imageButton3 = (ImageButton) findViewById(R.id.purchase_button);
        ImageButton imageButton4 = (ImageButton) findViewById(R.id.report_button);
        ImageButton imageButton6 = (ImageButton) findViewById(R.id.systemsupport_button);
        // ImageButton imageButton7=(ImageButton)findViewById(R.id.Internetorderbutton);
        ImageButton imageButton8 = (ImageButton) findViewById(R.id.systemsetting_button);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        // imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);


    }

    //    @Override
//    public void onBackPressed() {
//        // nothing to do here
//        // … really
//    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mastermanagement_button:

                Intent intent = new Intent(this, Activity_masterScreen2.class);
                startActivity(intent);
                break;
            case R.id.sales_button:
                Intent intent1 = new Intent(this, ActivitySales.class);
                startActivity(intent1);
                break;
            case R.id.purchase_button:
                Intent intent2 = new Intent(this, Activitypurchase.class);
                startActivity(intent2);
                break;
            case R.id.report_button:
                Intent intent3 = new Intent(this, ActivityLoyalityCust.class);
                startActivity(intent3);
                break;
            case R.id.systemsupport_button:
                Intent intent4 = new Intent(this, ActivityMainMaintainence.class);
                startActivity(intent4);
                break;
//            case R.id.Internetorderbutton:
//                Intent intent5 = new Intent(this,BiilinternetActivity.class);
//                startActivity(intent5);
//                break;
            case R.id.systemsetting_button:
                Intent intent6 = new Intent(this, ActivityLoyality.class);
                startActivity(intent6);
                break;

        }

    }
    @Override
    public void onStart() {
        super.onStart();
        if (D) Log.e(TAG, "++ ON START ++");
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!login.mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }
    public void setupChat() {
        // Initialize the BluetoothChatService to perform bluetooth connections
 mChatService = new BluetoothChatService(mHandler);        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }
    public final Handler mHandler = new Handler() {
        SQLiteDatabase myDataBase;
        SQLiteStatement insertStmt;


        @Override
        public void handleMessage(Message msg) {
            //myDataBase = getApplicationContext().openOrCreateDatabase("Db", Context.MODE_WORLD_WRITEABLE, null);         //Opens database in writable mode.
            File db_path = new File("/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/Db");
            db_path.getParentFile().mkdirs();
            myDataBase = SQLiteDatabase.openOrCreateDatabase(db_path, null);          //Opens database in writable mode.
            myDataBase.execSQL("PRAGMA key='Anaconda'");
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothChatService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //mConversationArrayAdapter.clear();
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                        case BluetoothChatService.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);

                    break;
                case MESSAGE_READ:

                    byte[] readBuf = (byte[]) msg.obj;


                    String readMessage = new String(readBuf,0,msg.arg1);

                    Log.d("ReadMessage@@@", readMessage);
                    String readmessages;
                    try {
                        JSONObject jsonObject = new JSONObject(readMessage);

                        //Log.d("Size#####", String.valueOf(readmessages.length())) ;
                        this.insertStmt = this.myDataBase.compileStatement(jsonObject.getString("Query"));

                        insertStmt.executeInsert();
                        myDataBase.close();
                    } catch (Exception e) {

                    }

                    break;

                // construct a string from the valid bytes in the buffer



                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
    }

    private final boolean setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(subTitle);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }


    public void MasterscreenDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("                  HOME PAGE   ");
        alertDialog.setMessage("Objective:\n" +
                "\t\n" +
                "Following are the option available on the home page:\n" +
                "\n" +
                "\t*Master Data Management \n" +
                "\t*Purchasing\n" +
                "\t*Sales Order Management\n" +
                "\t*System Settings\n" +
                "\t*System Maintenance\n" +
                "\t*Reports\n" +
                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();


    }






    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                    setStatus("Connected To slave");
                    PersistenceManager persistenceManager = new PersistenceManager();
                    persistenceManager.saveStatusBluetooth(getApplicationContext(),"Connected To Slave");
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    public void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }




    private void ensureDiscoverable() {
        if (D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
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
                return true;

            case R.id.action_settinghelp:
                MasterscreenDialog();
                return true;

          */
/*  case R.id.action_settingpurchase:
                Intent p = new Intent(Activity_masterScreen1.this, PurchaseActivity.class);
                startActivity(p);
                return true;*//*


            case R.id.action_settinginv:
                Intent in = new Intent(Activity_masterScreen1.this, activity_inventorywithpo.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s = new Intent(Activity_masterScreen1.this, ActivitySalesbill.class);
                startActivity(s);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }



    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D) Log.e(TAG, "+ ON RESUME +");
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.

        if(BluetoothChatService.STATE_NONE==1){}
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }



    public void BluetoothAlertDialog() {

        LayoutInflater inflater = getLayoutInflater();
        final View alertLayout = inflater.inflate(R.layout.masterscreen1_dialog, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);



        final Button submit = (Button) alertLayout.findViewById(R.id.submitbtnForHold);





        alert.setView(alertLayout);
        alert.setCancelable(true);
        alert.setTitle("You are not connected to Bluetooth");

        dialog = alert.create();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                // If the adapter is null, then Bluetooth is not supported
                if (mBluetoothAdapter == null) {
                    finish();
                    return;
                }
                Intent serverIntent = null;

                serverIntent = new Intent(Activity_masterScreen1.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                dialog.dismiss();


            }
        });
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);


    }

}*/
