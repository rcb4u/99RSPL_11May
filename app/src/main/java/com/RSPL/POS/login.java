package com.RSPL.POS;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.serialport.api.SerialPortHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ngx.USBPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteStatement;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import Config.Config;
import Config.ConfigItems;
import JSON.JSONParserSync;
import Pojo.Decimal;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jimmy on 2/4/2016.
 */
public class login extends Activity  implements ConnectivityReceiver.ConnectivityReceiverListener, Callback<ConfigItems> {

    public static SerialPortHelper serialPortHelper;
    private static int  Statenotconnect=1;
    static ArrayList<String>getSalesMasterrecord = new ArrayList<>();
    static ArrayList<String>getSalesDetailrecord=new ArrayList<>();
    static ArrayList<String>getstockMasterrecord=new ArrayList<>();
    //for sales return
    static ArrayList<String>getSalesReturnArr = new ArrayList<>();
    static ArrayList<String>getDataSendMailArr = new ArrayList<>();
    static ArrayList<String>getSalesReturnProductDetailsArr = new ArrayList<>();
    static ArrayList<String>getSalesReturnStockUpdate = new ArrayList<>();
    static ArrayList<String>getSalesFlagReturn = new ArrayList<>();
    //Inventory By Total
    static ArrayList<String>getInventoryTotalArr = new ArrayList<>();
    static ArrayList<String>getInventoryUpdatedDataArr = new ArrayList<>();
    static ArrayList<String>getGrnMasterwithoutpoArr = new ArrayList<>();
    static ArrayList<String>getPDfDetailInventoryWithoutPoArr = new ArrayList<>();
    //Inventory By Discount
    static ArrayList<String>getInventoryTotalDiscountArr = new ArrayList<>();
    static ArrayList<String>getInventoryUpdatedDiscountDataArr = new ArrayList<>();
    public ArrayList list ;
    public ArrayList<String> field1;
    public ArrayList<String> field2;
    public ArrayList<String> field3;
    public ArrayList<String> field4;
    public ArrayList<String> field5;
    public ArrayList<String> field6;
    public ArrayList<String> field7;
    public ArrayList<String> field8;
    public ArrayList<String> field9;
    public ArrayList<String> field10;
    public ArrayList<String> field11;
    public ArrayList<String> field12;
    public ArrayList<String> field13;
    public ArrayList<String> field14;
    public ArrayList<String> field15;
    public ArrayList<String> field16;
    public ArrayList<String> field17;
    public ArrayList<String> field18;
    public ArrayList<String> field19;
    public ArrayList<String> field20;
    public ArrayList<String> field21;
    public ArrayList<String> field22;
    public ArrayList<String> field23;
    public ArrayList<String> field24;
    public ArrayList<String> field25;
    public ArrayList<String> field26;
    public ArrayList<String> field27;
    public ArrayList<String> field28;
    public ArrayList<String> field29;
    public ArrayList<String> field30;
    public ArrayList<String> field31;
    public ArrayList<String> field32;
    public ArrayList<String> field33;
    public ArrayList<String> field34;
    public ArrayList<String> field35;
    public ArrayList<String> field36;
    public ArrayList<String> field37;
    public ArrayList<String> field38;
    public ArrayList<String> field39;
    public ArrayList<String> field40;
    public ArrayList<String> field41;
    public ArrayList<String> field42;
    public ArrayList<String> field43;
    public ArrayList<String> field44;
    public ArrayList<String> field45;
    public ArrayList<String> field46;
    public ArrayList<String> field47;
    public ArrayList<String> field48;
    public ArrayList<String> field49;
    public ArrayList<String> field50;
    public ArrayList<String> field51;
    public ArrayList<String> field52;
    public ArrayList<String> field53;
    public ArrayList<String> field54;
    public ArrayList<String> field55;
    public ArrayList<String> field56;
    public ArrayList<String> field57;
    public ArrayList<String> field58;
    public ArrayList<String> field59;
    public ArrayList<String> field60;
    public ArrayList<String> field61;
    public ArrayList<String> field62;
    public ArrayList<String> field63;
    public ArrayList<String> field64;
    public ArrayList<String> field65;
    public ArrayList<String> field66;
    public ArrayList<String> field67;
    public ArrayList<String> field68;
    public ArrayList<String> field69;
    public ArrayList<String> field70;
    public ArrayList<String> field71;
    public ArrayList<String> field72;
    public ArrayList<String> field73;
    public ArrayList<String> field74;
    public ArrayList<String> field75;
    public ArrayList<String> field76;
    public ArrayList<String> field77;
    public ArrayList<String> field78;
    public ArrayList<String> field79;
    public ArrayList<String> field80;
    public ArrayList<String> field81;
    public ArrayList<String>field82;
    public ArrayList<String>field83;
    public ArrayList<String> field84;
    public ArrayList<Config> data1;
    public ArrayList<Config> data2;
    public ArrayList<Config> data3;
    public ArrayList<Config> data4;
    public ArrayList<Config> data5;
    public ArrayList<Config> data6;
    public ArrayList<Config> data7;
    public ArrayList<Config> data8;
    public ArrayList<Config> data9;
    public ArrayList<Config> data10;
    public ArrayList<Config> data11;
    public ArrayList<Config> data12;
    public ArrayList<Config> data13;
    public ArrayList<Config> data14;
    public ArrayList<Config> data15;
    public ArrayList<Config> data16;
    public ArrayList<Config> data17;
    public ArrayList<Config> data18;
    public ArrayList<Config> data19;
    public ArrayList<Config> data20;
    public ArrayList<Config> data21;
    public ArrayList<Config> data22;
    public ArrayList<Config> data23;
    public ArrayList<Config> data24;
    public ArrayList<Config> data25;
    public ArrayList<Config> data26;
    public ArrayList<Config> data27;
    public ArrayList<Config> data28;
    public ArrayList<Config> data29;
    public ArrayList<Config> data30;
    public ArrayList<Config> data31;
    public ArrayList<Config> data32;
    public ArrayList<Config> data33;
    public ArrayList<Config> data34;
    public ArrayList<Config> data35;
    public ArrayList<Config> data36;
    public ArrayList<Config> data37;
    public ArrayList<Config> data38;
    public ArrayList<Config> data39;
    public ArrayList<Config> data40;
    public ArrayList<Config> data41;
    public ArrayList<Config> data42;
    public ArrayList<Config> data43;
    public ArrayList<Config> data44;
    public ArrayList<Config> data45;
    public ArrayList<Config> data46;
    public ArrayList<Config> data47;
    public ArrayList<Config> data48;
    public ArrayList<Config> data49;
    public ArrayList<Config> data50;
    public ArrayList<Config> data51;
    public ArrayList<Config> data52;
    public ArrayList<Config> data53;
    public ArrayList<Config> data54;
    public ArrayList<Config> data55;
    public ArrayList<Config> data56;
    public ArrayList<Config> data57;
    public ArrayList<Config> data58;
    public ArrayList<Config> data59;
    public ArrayList<Config> data60;
    public ArrayList<Config> data61;
    public ArrayList<Config> data62;
    public ArrayList<Config> data63;
    public ArrayList<Config> data64;
    public ArrayList<Config> data65;
    public ArrayList<Config> data66;
    public ArrayList<Config> data67;
    public ArrayList<Config> data68;
    public ArrayList<Config> data69;
    public ArrayList<Config> data70;
    public ArrayList<Config> data71;
    public ArrayList<Config> data72;
    public ArrayList<Config> data73;
    public ArrayList<Config> data74;
    public ArrayList<Config> data75;
    public ArrayList<Config> data76;
    public ArrayList<Config> data77;
    public ArrayList<Config> data78;
    public ArrayList<Config> data79;
    public ArrayList<Config> data80;
    public ArrayList<Config> data81;
    public ArrayList<Config>data82;
    public ArrayList<Config>data83;
    public ArrayList<Config>data84;





    String tablename_1 = "ret_ticket_install_register";
    String tablename_2 = "retail_ad_blinkinglogo";
    String tablename_3 = "retail_ad_blinkinglogo_cont";
    String tablename_4 = "retail_ad_store_main";
    String tablename_5 = "retail_ad_store_main_cont";
    String tablename_6 = "retail_ad_ticker ";
    String tablename_7 = "retail_ad_ticker_cont";
    String tablename_8 = "retail_card_define";
    String tablename_9 = "retail_card_define_mfg";
    String tablename_10 = "retail_comp_btl";
    String tablename_11 = "retail_cust";
    String tablename_12 = "retail_cust_loyalty";
    String tablename_13 = "retail_inventory";
    String tablename_14 = "retail_media";
    String tablename_15 = "retail_mfg_btl";
    String tablename_16 = "retail_store";
    String tablename_17 = "retail_store_maint";
    String tablename_18 = "retail_store_prod";
    String tablename_19 = "retail_store_prod_local_cpg";
    String tablename_20 = "retail_store_reports";
    String tablename_21 = "retail_store_vend_reject";
    String tablename_22 = "retail_store_vendor";
    String tablename_23 = "retail_str_dstr";
    String tablename_24 = "retail_str_grn_detail";
    String tablename_25 = "retail_str_grn_master";
    String tablename_26 = "retail_str_po_detail";
    String tablename_27 = "retail_str_po_master";
    String tablename_28 = "retail_str_sales_detail";
    String tablename_29 = "retail_str_sales_details_return";
    String tablename_30 = "retail_str_sales_master_return";
    String tablename_31 = "retail_str_sales_master";
    String tablename_32 = "retail_str_vendor_detail_return";
    String tablename_33 = "retail_str_vendor_master_return";
    String tablename_34 = "retail_tax";
    String tablename_35 = "retail_top_product";
    String tablename_36 = "retail_vend_dstr";
    String tablename_37 = "retail_videodata";
    String tablename_38 = "retail_videodata_cont";
    String tablename_39 = "retail_videodata_cont1";
    String tablename_40 = "rule_defination";
    String tablename_41 = "tmp_retail_pay_desc";
    String tablename_42 = "ad_ticker_main";
    String tablename_43 = "bank_details";
    String tablename_44 = "retail_store_prod_com";
    String tablename_45 = "retail_store_prod_cpg";
    String tablename_46 = "retail_store_prod_local";
    String tablename_47 = "retail_str_po_detail_hold";
    String tablename_48 = "retail_str_stock_master";
    String tablename_49 = "retail_str_stock_master_hold";
    String tablename_50 = "retail_str_day_open_close";
    String tablename_51 = "retail_store_sales_desc";
    String tablename_52 = "retail_employees";
    String tablename_53 = "tmp_retail_str_sales_detail";
    String tablename_54 = "tmp_retail_str_sales_master";
    String tablename_55 = "dr_speciality";
    String tablename_56 = "dr_discription";
    String tablename_57 = "retail_send_mail_pdf";
    String tablename_58 = "retail_str_lineitem_disc";
    String tablename_59 = "ad_main";
    String tablename_60 = "retail_media_click";
    String tablename_61 = "tmp_vend_dstr_payment";
    String tablename_62 = "retail_str_bill_lvl_disc";
    String tablename_63="retail_store_cust_reject";
    String tablename_64="retail_credit_cust";
    String tablename_65="retail_store_vendor_mail";
    String tablename_66="retail_str_dstr_mail";
    String tablename_68 = "retail_store_prod_mail";
    String tablename_69 = "retail_store_prod_local_mail";
    String tablename_70 = "retail_store_prod_local_cpg_mail";
    String tablename_71 = "retail_store_prod_cpg_mail";
    String tablename_72 = "retail_str_po_detail_mail";
    String tablename_73 = "retail_str_stock_master_mail";
    String tablename_74 = "tmp_vend_dstr_payment_mail";
    String tablename_75 = "retail_str_vendor_detail_return_mail";
    String tablename_76 = "retail_str_sales_detail_mail";
    String tablename_77 = "retail_str_sales_details_return_mail";
    String tablename_78 = "retail_ad_ticker_mail";
    String tablename_79 = "retail_ad_blinkinglogo_mail";
    String tablename_80 = "retail_ad_Store_Main_mail";
    String tablename_81= "retail_store_decimal";
    String tablename_82  = "retail_bill_display";
    String tablename_83  = "retail_bill_visible";
    String tablename_84 = "retail_credit_bill_details";



    SQLiteDatabase myDataBase;
    SQLiteStatement insertStmt;
    SQLiteStatement updateStmt;

    String INSERT;
    Button submit;

    ProgressDialog loading;


    Button button1;
    // Debugging
    private static final String TAG = "BluetoothChat";
    private static final boolean D = true;
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
    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;
    boolean isSerialPortConnected=false;
    // Name of the connected device
    // private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private static StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    public static BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    public static   BluetoothChatService mChatService = null;
    DBhelper db = new DBhelper(this);
    public static Bundle b = new Bundle();
    Button mPlayRemoteVideoButton;
    int RESULT_LOAD_VIDEO_LOCAL = 2;
    int RESULT_LOAD_VIDEO_REMOTE = 4;
    Button mSelectLocalVideoButton;
    Button mSelectRemoteVideoButton;
    Button mPlayLocalVideoButton;
    Button mRemoteStopButton;
    TextView mLocalPathTextView;
    TextView mRemotePathTextView;
    Uri mLocalVideoUri;
    Uri mRemoteVideoUri;
    TextView newstre;
    TextView textadstream;
    ImageView sponcerlogo;
    Intent mLocalData;
    Intent mRemoteData;
    Button login;
    EditText user;
    EditText password;
    String username;
    float NOofBIlls;
    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;
    private final List blockedKeys = new ArrayList(Arrays.asList(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_HOME));
    String MACHINE2 = "sh /sdcard/insert.sh";
    String SUPER_USER = "su";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;
//    private GSyncStatusHandler gSyncStatusHandler = new GSyncStatusHandler();
//    private GBrowserNotifyHandler gBrowserNotifyHandler = new GBrowserNotifyHandler();
//    private GSyncResultHandler gSyncResultNotifyHandler = new GSyncResultHandler();
//    private GSyncItemStatusHandler gSyncItemStatusHandler = new GSyncItemStatusHandler();
    public static USBPrinter UPrinter = USBPrinter.INSTANCE;
    public static SharedPreferences mSp;
    private TextView tvStatus;
    private Button Printbtn;
    private String mConnectedDeviceName = "";
    public static final String title_connecting = "connecting...";
    public static final String title_connected_to = "connected: ";
    public static final String title_not_connected = "not connected";
    @SuppressLint("HandlerLeak")
    private final Handler mHandlerforprinter = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USBPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case USBPrinter.STATE_CONNECTED:
                       /* tvStatus.setText(title_connected_to);
                        tvStatus.append(mConnectedDeviceName);*/
                            Toast.makeText(getApplicationContext(),title_connected_to+mConnectedDeviceName,Toast.LENGTH_SHORT).show();
                            break;
                        case USBPrinter.STATE_NONE:
                            // tvStatus.setText(title_not_connected);
                            Toast.makeText(getApplicationContext(),title_not_connected,Toast.LENGTH_SHORT).show();
                            break;
                        //Bellow status message should be shown in different textview.
                        case USBPrinter.ACTION_SERIALPORT_CONNECTED:
                            Log.d("Connected","Success Serial Port");
                            break;
                        case USBPrinter.ACTION_SERIALPORT_DISCONNECTED:
                            Log.d("DisConnected","Serial Port Disconnected");
                            break;
                        case USBPrinter.ACTION_SERIALPORT_NOT_SUPPORTED:
                            Log.d("Invalid","Not Supported");
                    }
                    break;
                case USBPrinter.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(
                            USBPrinter.DEVICE_NAME);
                    break;
                case USBPrinter.MESSAGE_STATUS:
                    //  tvStatus.setText(msg.getData().getString(
                    //       USBPrinter.STATUS_TEXT));
                    Toast.makeText(getApplicationContext(),msg.getData().getString(USBPrinter.STATUS_TEXT),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public static int setboolean() {
        if ( Statenotconnect == 0)
        { return 0;
        }
        else if ( Statenotconnect == 3) {
            return 1;
        }else{
            return 0;
        }
    }
    public static void bluetoothdatadetails(){
        SQLiteDatabase DataBase =null;
        if(getSalesDetailrecord.size()<=0)
        {
            return;
        }
        else {
            String getsalesdetail=String.valueOf(getSalesDetailrecord);
            String [] salesdetailarray =getsalesdetail.split(", ;,");
            String Sales_ProductDetail=null;
            for (int j = 0; j < salesdetailarray.length; j++) {
                String[] covnert1 = salesdetailarray[j].split(",");
                for (int K = 0; K < covnert1.length; K++) {
                    if (covnert1[0].contains("[")) {
                        covnert1[0] = covnert1[0].replace("[", "");
                    }
                    Sales_ProductDetail = "insert into retail_str_sales_detail( TRI_ID , STORE_ID ,BATCH_NO , PROD_NM , EXP_DATE, POS_USER , S_FLAG , M_FLAG ,S_PRICE,EX_TRI_ID,QTY,MRP,UOM,SALE_DATE,TOTAL,DISCOUNT_PERCENT,LINE_DISC,CARD_NO,BANK_NAME,CARD_TYPE,SLAVE_FLAG) values " +
                            "(" + "'" + covnert1[0].trim() + "'," + "'" + covnert1[1].trim() + "'," + "'" + covnert1[2].trim() + "'," + "'" + covnert1[3].trim() + "'," + "'" + covnert1[4].trim() + "'," + "'" + covnert1[5].trim() + "'," + "'0'," + "'I',"
                            + "'" + covnert1[8].trim() + "'," + "'" + covnert1[9].trim() + "'," + "'" + covnert1[10].trim() + "'," + "'" + covnert1[11].trim() + "'," + "'" + covnert1[12].trim() + "'," + "'" + covnert1[13].trim() + "'," + "'" + covnert1[14].trim() + "'," + "'" + covnert1[15].trim() + "'," + "'" + covnert1[16].trim() + "'," + "'" + covnert1[17].trim() + "'," + "'" + covnert1[18].trim() + "'," + "'" + covnert1[19].trim() + "'," + "'1')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", Sales_ProductDetail);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothdataMaster() {
        SQLiteDatabase DataBase=null;
        if (getSalesMasterrecord.size()<=0){
            return;
        }else{
            String rahul=String.valueOf(getSalesMasterrecord);
            String [] hello =rahul.split(", ;,");
            String Sales_ProductMaster=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    Sales_ProductMaster = "insert into retail_str_sales_master( TRI_ID , STORE_ID ,GRAND_TOTAL, POS_USER , S_FLAG , M_FLAG ,EX_TRI_ID,BUSINESS_DATE,SALE_TIME,SALE_DATE,DISCOUNT_PERCENT,SLAVE_FLAG) values (" + "'" + covnert[0].trim() + "'," + "'" + covnert[1].trim() + "'," + "'" + covnert[2].trim() + "'," + "'" + covnert[3].trim() + "'," + "'0'," + "'I'," + "'" + covnert[6].trim() + "'," + "'" + covnert[7].trim() + "'," + "'" + covnert[8].trim() + "','" + covnert[9].trim() + "'," + "'" + covnert[10].trim() + "'," + "'1')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", Sales_ProductMaster);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
        //  DataBase.execSQL("update retail_str_sales_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
    }
    public static void bluetoothdataupdate(){
        SQLiteDatabase DataBase=null;
        if (getstockMasterrecord.size()<=0){
            return;
        }else{
            String rahul=String.valueOf(getstockMasterrecord);
            String [] hello =rahul.split(", ;,");
            String Update_StockQuantity_Sales=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    Update_StockQuantity_Sales = "UPDATE retail_str_stock_master SET CON_MUL_QTY  = " + "'" + covnert[1] + "'" + ",M_FLAG = 'U' " + " WHERE BATCH_NO= " + "'" + covnert[3] + "' AND PROD_ID = '" +covnert[4] + "'";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", Update_StockQuantity_Sales);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
        // DataBase.execSQL("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
    }
    public static void bluetoothSalesReturnData()
    {
        SQLiteDatabase DataBase=null;
        if (getSalesReturnArr.size()<=0)
        {
            return;
        }
        else
        {
            String result=String.valueOf(getSalesReturnArr);
            String [] hello=result.split(", ;,");
            String salesRecord=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    salesRecord = "insert into retail_str_sales_master_return( ID ,TRI_ID ,POS_USER , STORE_ID , FLAG , REASON_OF_RETURN ,GRAND_TOTAL , M_FLAG , S_FLAG ,SLAVE_FLAG ) values (" + "'"
                            + covnert[0].trim() + "'," + "'" + covnert[1].trim() + "'," + "'" + covnert[2].trim() + "'," + "'"
                            + covnert[3].trim() + "'," + "'Y'," +"'" + covnert[5].trim() + "'," + "'"
                            + covnert[6].trim() + "'," + "'I'," + "'0'," + "'1'," + "',')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", salesRecord);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothSendDataProductDetails()
    {
        SQLiteDatabase DataBase=null;
        if (getSalesReturnProductDetailsArr.size()<=0)
        {
            return;
        }
        else
        {
            String result=String.valueOf(getSalesReturnProductDetailsArr);
            String [] hello=result.split(", ;,");
            String salesSendMailRecord=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    salesSendMailRecord = "insert into retail_str_sales_details_return( STORE_ID ,TRI_ID ,POS_USER , ID , PROD_NM , BATCH_NO , EXP_DATE ,FLAG ,S_PRICE ,SALE_DATE ,QTY ,MRP ,UOM ,TOTAL ,M_FLAG ,S_FLAG ,SLAVE_FLAG ) values (" + "'"
                            + covnert[0].trim() + "'," + "'" + covnert[1].trim() + "'," + "'" + covnert[2].trim() + "'," + "'"
                            + covnert[3].trim() + "'," +"'" + covnert[4].trim() + "'," + "'" +
                            covnert[5].trim()+ "'," + "'" +covnert[6].trim()+ "'," + "'Y',"  + "'" +covnert[8].trim()
                            + "'," + "'" +covnert[9].trim()+ "'," + "'" +covnert[10].trim()+ "'," + "'" +covnert[11].trim()
                            + "'," + "'" +covnert[12].trim() + "'," + "'" +covnert[13].trim()+ "'," +"'I'," + "'0'," + "'1',"+"')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", salesSendMailRecord);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothdataForStockUpdate(){
        SQLiteDatabase DataBase=null;
        if (getSalesReturnStockUpdate.size()<=0){
            return;
        }else{
            String result=String.valueOf(getSalesReturnStockUpdate);
            String [] hello =result.split(", ;,");
            String Update_StockQuantity_Sales=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    Update_StockQuantity_Sales = "UPDATE retail_str_stock_master SET CON_MUL_QTY  = " + "'" + covnert[0] + "'" + ",M_FLAG = 'U' " + " WHERE EXP_DATE= " + "'" + covnert[2] + "' AND BATCH_NO = '" +covnert[3] + "'" + "'AND MRP ='" + covnert[4] +"'";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", Update_StockQuantity_Sales);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothDataForGetInventoryTotal(){
        SQLiteDatabase DataBase=null;
        if (getInventoryTotalArr.size()<=0){
            return;
        }else{
            String result=String.valueOf(getInventoryTotalArr);
            String [] hello =result.split(", ;,");
            String insert_saveInventorywithoutpo=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    insert_saveInventorywithoutpo = "insert into retail_str_stock_master " +
                            "( GRN_ID , VEND_NM , BATCH_NO , MFG_BATCH_NO , EXP_DATE , POS_USER , STORE_ID ," +
                            " PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
                            "BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE , " +
                            "VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , PURCHASE_UNIT_CONV , DISCOUNT_PERCENT,S_FLAG,SLAVE_FLAG)" +
                            " values (" + "'" + covnert[0] + "' ," + "'" + covnert[1] + "' ," + "'" + covnert[2] + "'," + "'"
                            + covnert[3] + "'," + "'" + covnert[4] + "'," + "'" + covnert[5] + "'," + "'"
                            + covnert[6] + "'," + "'" + covnert[7] + "'," + "'"
                            + covnert[8] + "'," + "'" + covnert[9] + "'," + "'" + covnert[10]
                            + "'," + "'" + covnert[11] + "'," + "'" + covnert[12] + "'," + "'" +
                            covnert[13] + "'," + "'" + covnert[14] + "'," + "'"  +  covnert[15] + "'," + "'NA',"
                            + "'" +  covnert[17] + "'," + "'" + covnert[18] + "'," + "'"  + covnert[19]
                            + "'," + "'" + covnert[20] + "','"+ covnert[21] + "'," + "'"
                            + covnert[22] + "'," + "'I'," + "'"  + covnert[24] + "'," + "'"  +  covnert[25] + "'," + "'" + covnert[26] + "',"
                            + "'" + covnert[27] + "'," + "'" + covnert[28] + "'," + "'0'," + "'1'" +"')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", insert_saveInventorywithoutpo);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothDataForUpdateInventoryData(){
        SQLiteDatabase DataBase=null;
        if (getInventoryUpdatedDataArr.size()<=0){
            return;
        }else{
            String result=String.valueOf(getInventoryUpdatedDataArr);
            String [] hello =result.split(", ;,");
            String update_inventory=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    update_inventory = "UPDATE retail_str_stock_master SET GRN_ID = "
                            + "'" + covnert[0] + "'" + " ,INVENTORY_DATE = " + "'" + covnert[1] + "'" + " ,M_FLAG = 'U' "
                            + " , CON_MUL_QTY = " + "'" + covnert[3] + "'" + " WHERE EXP_DATE = "
                            + "'" + covnert[4] + "'" + " and " + "PROD_ID = " + "'" + covnert[5] + "'"
                            + " and " + "MRP = " + "'" + covnert[6] + "'";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", update_inventory);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothDataGranddataintoGrnMasterWithoutPo(){
        SQLiteDatabase DataBase=null;
        if (getGrnMasterwithoutpoArr.size()<=0){
            return;
        }else{
            String result=String.valueOf(getGrnMasterwithoutpoArr);
            String [] hello =result.split(", ;,");
            String saveGranddataintoGrnMasterwithoutpo=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    saveGranddataintoGrnMasterwithoutpo = "insert into retail_str_grn_master ( " +
                            "STORE_ID , POS_USER , GRN_ID , VEND_NM , GRAND_AMOUNT , M_FLAG , FLAG,SLAVE_FLAG) values (" + "'"
                            + covnert[0] + "' ," + "'"
                            + covnert[1] + "' ," + "'" + covnert[2] + "'," + "'" + covnert[3] + "'," + "'" + covnert[4]
                            + "'," + "'I'," + "'0'," + "'1')";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", saveGranddataintoGrnMasterwithoutpo);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public static void bluetoothDataForUpdateInventoryDiscountData(){
        SQLiteDatabase DataBase=null;
        if (getInventoryUpdatedDiscountDataArr.size()<=0){
            return;
        }else{
            String result=String.valueOf(getInventoryUpdatedDiscountDataArr);
            String [] hello =result.split(", ;,");
            String update_inventory=null;
            for (int j = 0; j < hello.length; j++) {
                String[] covnert = hello[j].split(",");
                for (int K = 0; K < covnert.length; K++) {
                    if (covnert[0].contains("[")) {
                        covnert[0] = covnert[0].replace("[", "");
                    }
                    update_inventory = "UPDATE retail_str_stock_master SET GRN_ID = "
                            + "'" + covnert[0] + "'" + " ,INVENTORY_DATE = " + "'" + covnert[1] + "'" + " ,M_FLAG = 'U' "
                            + " , CON_MUL_QTY = " + "'" + covnert[3] + "'" + " WHERE EXP_DATE = "
                            + "'" + covnert[4] + "'" + " and " + "PROD_ID = " + "'" + covnert[5] + "'"
                            + " and " + "MRP = " + "'" + covnert[6] + "'";
                }
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Query", update_inventory);
                    sendMessage(String.valueOf(jsonObject));
                } catch (Exception e) {
                }
            }
        }
    }
    public void hideNav()
    {
        try
        {
            Process p;
            p = Runtime.getRuntime().exec("su");
            // Attempt to write a file to a root-only
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            os.writeBytes("service call activity 42 s16 com.android.systemui\n");
            // Close the terminal
            os.writeBytes("exit\n");
            os.flush();
            p.waitFor();
        }
        catch (Exception e)
        {
        }
    } public  void connectPrinter(Context context){
        UPrinter.initServices(context,mHandlerforprinter);
    }
    public static  SerialPortHelper mSerialPortHelper;

    public Boolean Connects() {
        return mSerialPortHelper.OpenSerialPort();
    }



    String backroundcolour,colorchange;

    @Override



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        setContentView(R.layout.activity_login);



        db = new DBhelper(login.this);
        Decimal valuetextsize = db.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();




        if(backroundcolour.matches("Orange")){

            colorchange="#ff9033";
            //  String    d="#bdc3c7";//silver


            //f39c12
            //EF643C
            //EE782D
        }
        if(backroundcolour.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

            //    d=Color.BLUE;//FBB917

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
            //C3C3C3 grey//E1E1E1

            ////FBB917

        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
       layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar = getActionBar();

       actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);





        field1 = new ArrayList<>();
        field2 = new ArrayList<>();
        field3 = new ArrayList<>();
        field4 = new ArrayList<>();
        field5 = new ArrayList<>();
        field6 = new ArrayList<>();
        field7 = new ArrayList<>();
        field8 = new ArrayList<>();
        field9 = new ArrayList<>();
        field10 = new ArrayList<>();
        field11 = new ArrayList<>();
        field12 = new ArrayList<>();
        field13 = new ArrayList<>();
        field14 = new ArrayList<>();
        field15 = new ArrayList<>();
        field16 = new ArrayList<>();
        field17 = new ArrayList<>();
        field18 = new ArrayList<>();
        field19 = new ArrayList<>();
        field20 = new ArrayList<>();
        field21 = new ArrayList<>();
        field22 = new ArrayList<>();
        field23 = new ArrayList<>();
        field24 = new ArrayList<>();
        field25 = new ArrayList<>();
        field26 = new ArrayList<>();
        field27 = new ArrayList<>();
        field28 = new ArrayList<>();
        field29 = new ArrayList<>();
        field30 = new ArrayList<>();
        field31 = new ArrayList<>();
        field32 = new ArrayList<>();
        field33 = new ArrayList<>();
        field34 = new ArrayList<>();
        field35 = new ArrayList<>();
        field36 = new ArrayList<>();
        field37 = new ArrayList<>();
        field38 = new ArrayList<>();
        field39 = new ArrayList<>();
        field40 = new ArrayList<>();
        field41 = new ArrayList<>();
        field42 = new ArrayList<>();
        field43 = new ArrayList<>();
        field44 = new ArrayList<>();
        field45 = new ArrayList<>();
        field46 = new ArrayList<>();
        field47 = new ArrayList<>();
        field48 = new ArrayList<>();
        field49 = new ArrayList<>();
        field50 = new ArrayList<>();
        field51 = new ArrayList<>();
        field52 = new ArrayList<>();
        field53 = new ArrayList<>();
        field54 = new ArrayList<>();
        field55 = new ArrayList<>();
        field56 = new ArrayList<>();
        field57 = new ArrayList<>();
        field58 = new ArrayList<>();
        field59 = new ArrayList<>();
        field60 = new ArrayList<>();
        field61 = new ArrayList<>();
        field62 = new ArrayList<>();
        field63 = new ArrayList<>();
        field64 = new ArrayList<>();
        field65 = new ArrayList<>();
        field66 = new ArrayList<>();
        field67 = new ArrayList<>();

        field68 = new ArrayList<>();
        field69 = new ArrayList<>();
        field70=new ArrayList<>();
        field71=new ArrayList<>();
        field72=new ArrayList<>();
        field73=new ArrayList<>();
        field74=new ArrayList<>();
        field75=new ArrayList<>();
        field76=new ArrayList<>();
        field77=new ArrayList<>();
        field78=new ArrayList<>();
        field79=new ArrayList<>();
        field80=new ArrayList<>();
        field81=new ArrayList<>();
        field82=new ArrayList<>();
        field83=new ArrayList<>();
        field84=new ArrayList<>();






        data1 = new ArrayList<>();
        data2 = new ArrayList<>();
        data3 = new ArrayList<>();
        data4 = new ArrayList<>();
        data5 = new ArrayList<>();
        data6 = new ArrayList<>();
        data7 = new ArrayList<>();
        data8 = new ArrayList<>();
        data9 = new ArrayList<>();
        data10 = new ArrayList<>();
        data11 = new ArrayList<>();
        data12 = new ArrayList<>();
        data13 = new ArrayList<>();
        data14 = new ArrayList<>();
        data15 = new ArrayList<>();
        data16 = new ArrayList<>();
        data17 = new ArrayList<>();
        data18 = new ArrayList<>();
        data19 = new ArrayList<>();
        data20 = new ArrayList<>();
        data21 = new ArrayList<>();
        data22 = new ArrayList<>();
        data23 = new ArrayList<>();
        data24 = new ArrayList<>();
        data25 = new ArrayList<>();
        data26 = new ArrayList<>();
        data27 = new ArrayList<>();
        data28 = new ArrayList<>();
        data29 = new ArrayList<>();
        data30 = new ArrayList<>();
        data31 = new ArrayList<>();
        data32 = new ArrayList<>();
        data33 = new ArrayList<>();
        data34 = new ArrayList<>();
        data35 = new ArrayList<>();
        data36 = new ArrayList<>();
        data37 = new ArrayList<>();
        data38 = new ArrayList<>();
        data39 = new ArrayList<>();
        data40 = new ArrayList<>();
        data41 = new ArrayList<>();
        data42 = new ArrayList<>();
        data43 = new ArrayList<>();
        data44 = new ArrayList<>();
        data45 = new ArrayList<>();
        data46 = new ArrayList<>();
        data47 = new ArrayList<>();
        data48 = new ArrayList<>();
        data49 = new ArrayList<>();
        data50 = new ArrayList<>();
        data51 = new ArrayList<>();
        data52 = new ArrayList<>();
        data53 = new ArrayList<>();
        data54 = new ArrayList<>();
        data55 = new ArrayList<>();
        data56 = new ArrayList<>();
        data57 = new ArrayList<>();
        data58 = new ArrayList<>();
        data59 = new ArrayList<>();
        data60 = new ArrayList<>();
        data61 = new ArrayList<>();
        data62 = new ArrayList<>();
        data63 = new ArrayList<>();
        data64 = new ArrayList<>();
        data81 = new ArrayList<>();
        data82 = new ArrayList<>();
        data83 = new ArrayList<>();
        data84 = new ArrayList<>();





        getJSON();

        MyApplication.getInstance().setConnectivityListener(this);
        checkConnection();
        hideNav();
       /* if(UPrinter.getState()==0) {
            UPrinter.initServices(login.this, mHandlerforprinter);
        }
        if(UPrinter.getSerialPortState()==0){
        if(UPrinter.getSerialPortState()==0){
            UPrinter.initSerialPortServices();
        }*/
        mSerialPortHelper =new SerialPortHelper();
        if (!isSerialPortConnected) {
            if ((isSerialPortConnected = Connects())) {

                // btnClose.setEnabled(true);
                // btnConnect.setEnabled(false);
            } else {

                // btnClose.setEnabled(false);
                // btnConnect.setEnabled(true);
            }
        }


        UPrinter.initServices(this,mHandlerforprinter);
        if(UPrinter.connectPrinter()){Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();}
        else{Toast.makeText(getApplicationContext()," Not Connected",Toast.LENGTH_LONG).show();}

       //nstar mSp = PreferenceManager.getDefaultSharedPreferences(login.this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // If the adapter is null, then Bluetooth is not supported
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // startService(new Intent(this, SyncService.class));

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        login = (Button) findViewById(R.id.loginbutton);
        user = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.userpassword);

        /* No of bills for incentive */

        NOofBIlls=db.getNoBIllToSplash();

        if(NOofBIlls > 0)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(login.this);
            builder.setTitle("How Many Bills Remain To Get Incentive");

            if(NOofBIlls < 300) {
                float Noofbillless300=300-NOofBIlls;
                builder.setMessage("   No Of Bills Left To Reach 300 :-"   +Noofbillless300);
            }

            else if(NOofBIlls>300 && NOofBIlls<=1000) {
                float Noofbillless1000=1000-NOofBIlls;
                builder.setMessage("   No Of Bills Left To Reach 1000 :-"+   Noofbillless1000);
            }
            else if(NOofBIlls>1000 && NOofBIlls<=2000) {
                float Noofbillless2000=2000-NOofBIlls;
                builder.setMessage("   No Of Bills Left To Reach 2000 :-"+   Noofbillless2000);
            }
            else if(NOofBIlls>2000 && NOofBIlls<=3000) {
                float Noofbillless3000=3000-NOofBIlls;
                builder.setMessage("       No Of Bills Left To Reach 3000 :-"+   Noofbillless3000);
            }
            else{
                float Noofbillless4000=4000-NOofBIlls;
                builder.setMessage("  No Of Bills Left To Reach 4000 :-"+   Noofbillless4000);
            }
            builder.setCancelable(true);


            final AlertDialog closedialog= builder.create();

            closedialog.show();

            final Timer timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                public void run() {
                    closedialog.dismiss();
                    timer2.cancel(); //this will cancel the timer of the system
                }
            }, 15000); // the timer will count 15 seconds....

        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                String password1 = password.getText().toString();
                username = user.getText().toString();
                final Long Value = System.currentTimeMillis();
                final String resval = Long.toString(Value);
                db.getData();
                db.activestore();
                UPrinter.connectSerialPort();
//                db.SavePdfDetailForInstallation(resval);
                if (db.validateUser(username,password1))
                {      //Intent i =new Intent(getApplicationContext().this,test.class);
                    //
                    //UpdateexecuterMachine2();
                    String store_id=PersistenceManager.getStoreId(login.this);
                    String st= store_id.substring(0,10);
                    String OTPS= db.getStoreDetails().getOTP();
                    downloadData(st,OTPS,login.this);
                    Intent in = new Intent(login.this,Activity_masterScreen1.class);
                    startActivity(in);
                    Log.d("***",OTPS);
                    Log.d("***",st);
//                    loadSyncLibrary();
//                    doSync(syncDataBundle);
                    com.RSPL.POS.login.b.putString("Pos_User", username);
                    password.setText("");
                    user.setText("");
                } else {
                    Toast.makeText(getBaseContext(), "Wrong username/password", Toast.LENGTH_LONG).show();
                }
            }
        });
        getSalesMasterrecord=db.getsalesmasterrecord();
        getSalesDetailrecord=db.getSalesDetailrecord();
        getstockMasterrecord=db.getstockMasterrecord();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (D) Log.e(TAG, "++ ON START ++");
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }
    public void setupChat() {
        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BluetoothChatService(mHandler);
        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }
    private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
    }
    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(subTitle);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.secure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                if (UPrinter.getState()==0){
                    // Toast.makeText(login.this,"Please connect with printer ",Toast.LENGTH_SHORT).show();
                    UPrinter.showDeviceList(login.this);
                    return true;
                }else{
                    return  false;
                }
            case R.id.insecure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            case R.id.discoverable:
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
        }
        return false;
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
    // The Handler that gets information back from the BluetoothChatService
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
                            Statenotconnect= BluetoothChatService.STATE_CONNECTED;
                            break;
                        case BluetoothChatService.STATE_CONNECTING:
                            Statenotconnect= BluetoothChatService.STATE_CONNECTING;
                            setStatus(R.string.title_connecting);
                            break;
                        case BluetoothChatService.STATE_LISTEN:
                            Statenotconnect= BluetoothChatService.STATE_LISTEN;
                        case BluetoothChatService.STATE_NONE:
                            Statenotconnect= BluetoothChatService.STATE_NONE;
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
    @Override
    public synchronized void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
        if (D) Log.e(TAG, "+ ON RESUME +");
        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (blockedKeys.contains(event.getKeyCode())) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
    /*String msa;
    public void getMessage(String msg){
        msa=msg;
    }*/
    public static void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        Log.d("Message####", message);
        if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
            // Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);
            // Reset out string buffer to zero and clear the edit text field
            mOutStringBuffer.setLength(0);
            /*mOutEditText.setText(mOutStringBuffer);*/
        }
    }
    public void UpdateexecuterMachine2() {
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
                executerMachine2(SUPER_USER);
                String s = executerMachine2(MACHINE2);
                Log.e("@@@Script Called", s);
                return null;
            }
        }
        UpdateShell updateShell = new UpdateShell();
        updateShell.execute();
    }
    public String executerMachine2(String command) {
        StringBuffer output = new StringBuffer();
        try {
            Process p = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            outputStream.writeBytes("sh /sdcard/insert.sh");
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();
        return response;
    }
    /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen007, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            clearbuttondialog();
           *//* super.onBackPressed();
            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);*//*
            //Intent i = new Intent(Activity_masterScreen1.this, .class);
            //  startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    public void clearbuttondialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                              LOGIN");
        alertDialog.setMessage(" \n" +
                "Objective:\n" +
                "\n" +
                "User has to login in the application.\n" +
                "\n" +
                "Input Description:\n" +
                "\n" +
                "       1.User Id\n" +
                "       2.Password\n" +
                "\n" +
                "Function: \n" +
                "\n" +
                "  Login: User will navigate to the Home page.");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               /* Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);*/
            }
        });
        alertDialog.show();
    }
    /* protected void onActivityResult(int requestCode, int resultCode, Intent data)
     {
         if (resultCode == RESULT_OK)
         {
             Uri videoUri = data.getData();
             String videoPath = getPathFromUri(videoUri);
             if (requestCode == RESULT_LOAD_VIDEO_LOCAL)
             {
                 mPlayLocalVideoButton.setVisibility(View.VISIBLE);
                 mPlayLocalVideoButton.setClickable(true);
                 mLocalPathTextView.setText(videoPath);
                 mLocalVideoUri = videoUri;
                 mLocalData = data;
             }
             else if (requestCode == RESULT_LOAD_VIDEO_REMOTE)
             {
                 mPlayRemoteVideoButton.setVisibility(View.VISIBLE);
                 mPlayRemoteVideoButton.setClickable(true);
             }
         }
     }*/
    public void OnClickPlayRemoteVideo(View view) {
        Intent serviceIntent = new Intent(this, RemoteVideoService.class);
        serviceIntent.putExtra(RemoteVideoService.URI, mRemoteVideoUri);
        // serviceIntent.getParcelableExtra(RemoteVideoService.adnewstream);
        //serviceIntent.putExtra(RemoteVideoService.URI1,newstre);
        startService(serviceIntent);
    }
    public String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    public void remoteMediaPlay() {
        Button btn_media = (Button) findViewById(R.id.buttonPlayRemoteVideo);
        btn_media.performClick();
    }
    public void uploadData() {
        class uploadData extends AsyncTask<Void, Void, String> {
            ArrayList<String>getStockMaster = new ArrayList<>();
            ArrayList<String>retail_store = new ArrayList<>();
            ArrayList<String>tmp_vend_dstr_payment_mail = new ArrayList<>();
            ArrayList<String>retail_str_sales_master_return = new ArrayList<>();
            ArrayList<String>retail_str_sales_details_return = new ArrayList<>();
            ArrayList<String>retail_str_dstr = new ArrayList<>();
            ArrayList<String>retail_store_prod_local = new ArrayList<>();
            ArrayList<String>retail_store_prod = new ArrayList<>();
            ArrayList<String>retail_str_po_master = new ArrayList<>();
            ArrayList<String>retail_send_mail_pdf = new ArrayList<>();
            ArrayList<String>dr_discription = new ArrayList<>();
            ArrayList<String>ad_ticker_main = new ArrayList<>();
            ArrayList<String>retail_top_product = new ArrayList<>();
            ArrayList<String>retail_employees = new ArrayList<>();
            ArrayList<String> retail_store_vend_reject = new ArrayList<>();
            ArrayList<String>retail_str_vendor_detail_return = new ArrayList<>();
            ArrayList<String>retail_store_maint = new ArrayList<>();
            ArrayList<String>retail_ad_blinkinglogo_mail = new ArrayList<>();
            ArrayList<String>retail_ad_store_main_mail = new ArrayList<>();
            ArrayList<String>retail_ad_ticker_mail = new ArrayList<>();
            ArrayList<String>retail_store_prod_cpg_mail = new ArrayList<>();
            ArrayList<String>retail_store_prod_local_cpg_mail = new ArrayList<>();
            ArrayList<String>retail_store_prod_local_mail = new ArrayList<>();
            ArrayList<String>retail_store_prod_mail = new ArrayList<>();
            ArrayList<String>retail_store_vendor_mail = new ArrayList<>();
            ArrayList<String>retail_str_dstr_mail = new ArrayList<>();
            ArrayList<String>retail_str_po_detail_mail = new ArrayList<>();
            ArrayList<String>retail_str_sales_detail_mail = new ArrayList<>();
            ArrayList<String>tmp_vend_dstr_payment = new ArrayList<>();
            ArrayList<String>retail_str_grn_master = new ArrayList<>();
            ArrayList<String>retail_str_day_open_close = new ArrayList<>();
            ArrayList<String>retail_cust = new ArrayList<>();
            ArrayList<String>retail_str_vendor_detail_return_mail = new ArrayList<>();
            ArrayList<String>retail_str_stock_master_mail = new ArrayList<>();
            ArrayList<String>retail_str_sales_details_return_mail = new ArrayList<>();
            ArrayList<String>retail_card_define = new ArrayList<>();
            ArrayList<String>rule_defination = new ArrayList<>();
            ArrayList<String>retail_str_lineitem_disc = new ArrayList<>();
            ArrayList<String>retail_store_cust_reject = new ArrayList<>();
            ArrayList<String>retail_str_bill_lvl_disc = new ArrayList<>();
            ArrayList<String>retail_media_click = new ArrayList<>();
            ArrayList<String>retail_str_sales_detail = new ArrayList<>();
            ArrayList<String>retail_str_sales_master = new ArrayList<>();
            ArrayList<String>retail_bill_visible = new ArrayList<>();
            ArrayList<String>retail_bill_display = new ArrayList<>();
            ArrayList<String>retail_store_decimal= new ArrayList<>();
            ProgressDialog loading;
            int success;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                getStockMaster = db.stockMaster();
                retail_store = db.retailStore();
                tmp_vend_dstr_payment_mail=db.tmpVendDstrPaymentMail();
                retail_str_sales_master_return = db.retailStrSalesMasterReturn();
                retail_str_sales_details_return = db.retailStrSalesDetailsReturn();
                retail_str_dstr = db.retailStrDstr();
                retail_store_prod_local = db.retailStoreProdLocal();
                retail_store_prod = db.retailStoreProd();
                retail_str_po_master = db.retailStrPoMaster();
                retail_send_mail_pdf = db.retailSendMailPdf();
                dr_discription = db.drDiscription();
                ad_ticker_main = db.adTickerMain();
                retail_top_product = db.retailTopProduct();
                retail_employees = db.retailEmployees();
                retail_store_vend_reject = db.retailStoreVendReject();
                retail_str_vendor_detail_return = db.retailStrVendorDetailReturn();
                retail_store_maint = db.retailStoreMaint();
                retail_ad_blinkinglogo_mail = db.retail_ad_blinkinglogo_mail();
                retail_ad_store_main_mail = db.retailAdStoreMainMail();
                retail_ad_ticker_mail = db.retailAdTickerMail();
                retail_store_prod_cpg_mail = db.retailStoreProdCpgMail();
                retail_store_prod_local_cpg_mail = db.retailStoreProdLocalCpgMail();
                retail_store_prod_local_mail = db.retailStoreProdLocalMail();
                retail_store_prod_mail = db.retailStoreProdMail();
                retail_store_vendor_mail = db.retailStoreVendorMail();
                retail_str_dstr_mail = db.retailStrDstrMail();
                retail_str_po_detail_mail = db.retailStrPoDetailMail();
                retail_str_sales_detail_mail = db.retailStrSalesDetailMail();
                tmp_vend_dstr_payment = db.tmpVendDstrPayment();
                retail_str_grn_master = db.retailStrGrnMaster();
                retail_str_day_open_close = db.retailStrDayOpenClose();
                retail_cust = db.retailCust();
                retail_str_vendor_detail_return_mail = db.retail_strVendorDetailReturnMail();
                retail_str_stock_master_mail = db.retailStrStockMasterMail();
                retail_str_sales_details_return_mail = db.retailStrSalesDetailsReturnMail();
                retail_card_define = db.retailCardDefine();
                rule_defination = db.ruleDefination();
                retail_str_lineitem_disc = db.retailStrLineitemDisc();
                retail_store_cust_reject = db.retailStoreCustReject();
                retail_str_bill_lvl_disc = db.retailStrBillLvlDisc();
                retail_media_click = db.retailMediaClick();
                retail_str_sales_detail = db.retailStrSalesDetail();
                retail_str_sales_master = db.retailStrSalesMaster();
                retail_bill_display=db.retailBillDisplay();
                retail_bill_visible=db.retailBillVisible();
                retail_store_decimal = db.retailStoreDecimal();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> uploadStockMaster = new HashMap<>();
                HashMap<String,String>retail_storeh = new HashMap<>();
                HashMap<String,String>tmp_vend_dstr_payment_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_sales_master_returnh
                        = new HashMap<>();
                HashMap<String,String>retail_str_sales_details_returnh
                        = new HashMap<>();
                HashMap<String,String>retail_str_dstrh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prod_localh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prodh
                        = new HashMap<>();
                HashMap<String,String>retail_str_po_masterh
                        = new HashMap<>();
                HashMap<String,String>retail_send_mail_pdfh
                        = new HashMap<>();
                HashMap<String,String>dr_discriptionh
                        = new HashMap<>();
                HashMap<String,String>ad_ticker_mainh
                        = new HashMap<>();
                HashMap<String,String>retail_top_producth
                        = new HashMap<>();
                HashMap<String,String> retail_employeesh
                        = new HashMap<>();
                HashMap<String,String> retail_store_vend_rejecth
                        = new HashMap<>();
                HashMap<String,String>retail_str_vendor_detail_returnh
                        = new HashMap<>();
                HashMap<String,String>retail_store_mainth
                        = new HashMap<>();
                HashMap<String,String>retail_ad_blinkinglogo_mailh = new HashMap<>();
                HashMap<String,String>retail_ad_store_main_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_ad_ticker_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prod_cpg_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prod_local_cpg_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prod_local_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_store_prod_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_store_vendor_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_dstr_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_po_detail_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_sales_detail_mailh
                        = new HashMap<>();
                HashMap<String,String>tmp_vend_dstr_paymenth
                        = new HashMap<>();
                HashMap<String,String>retail_str_grn_masterh
                        = new HashMap<>();
                HashMap<String,String> retail_str_day_open_closeh
                        = new HashMap<>();
                HashMap<String,String>retail_custh
                        = new HashMap<>();
                HashMap<String,String>retail_str_vendor_detail_return_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_stock_master_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_str_sales_details_return_mailh
                        = new HashMap<>();
                HashMap<String,String>retail_card_defineh
                        = new HashMap<>();
                HashMap<String,String>rule_definationh
                        = new HashMap<>();
                HashMap<String,String>retail_str_lineitem_disch
                        = new HashMap<>();
                HashMap<String,String>retail_store_cust_rejecth
                        = new HashMap<>();
                HashMap<String,String>retail_str_bill_lvl_disch
                        = new HashMap<>();
                HashMap<String,String>retail_media_clickh = new HashMap<>();
                HashMap<String,String>retail_str_sales_detailh = new HashMap<>();
                HashMap<String,String>retail_str_sales_masterh = new HashMap<>();
                HashMap<String,String>retail_bill_visibleh = new HashMap<>();
                HashMap<String,String>retail_bill_displayh = new HashMap<>();
                HashMap<String,String>retail_store_decimalh = new HashMap<>();
                StringBuilder sb = new StringBuilder(128);
                for (String value : getStockMaster) {
                    if (sb.length() > 0) {
                        sb.append("|");
                    }
                    sb.append(value);
                }
                sb.insert(0, "|");
                StringBuilder sb2 = new StringBuilder(128);
                for (String value : retail_store) {
                    if (sb2.length() > 0) {
                        sb2.append("|");
                    }
                    sb2.append(value);
                }
                sb2.insert(0, "|");
                StringBuilder sb3 = new StringBuilder(128);
                for (String value : tmp_vend_dstr_payment_mail) {
                    if (sb3.length() > 0) {
                        sb3.append("|");
                    }
                    sb3.append(value);
                }
                sb3.insert(0, "|");
                StringBuilder sb4 = new StringBuilder(128);
                for (String value : retail_str_sales_master_return) {
                    if (sb4.length() > 0) {
                        sb4.append("|");
                    }
                    sb4.append(value);
                }
                sb4.insert(0, "|");
                StringBuilder sb5 = new StringBuilder(128);
                for (String value : retail_str_sales_details_return) {
                    if (sb5.length() > 0) {
                        sb5.append("|");
                    }
                    sb5.append(value);
                }
                sb5.insert(0, "|");
                StringBuilder sb6 = new StringBuilder(128);
                for (String value : retail_str_dstr) {
                    if (sb6.length() > 0) {
                        sb6.append("|");
                    }
                    sb6.append(value);
                }
                sb6.insert(0, "|");
                StringBuilder sb7 = new StringBuilder(128);
                for (String value : retail_store_prod_local) {
                    if (sb7.length() > 0) {
                        sb7.append("|");
                    }
                    sb7.append(value);
                }
                sb7.insert(0, "|");
                StringBuilder sb8 = new StringBuilder(128);
                for (String value :retail_store_prod) {
                    if (sb8.length() > 0) {
                        sb8.append("|");
                    }
                    sb8.append(value);
                }
                sb8.insert(0, "|");
                StringBuilder sb9 = new StringBuilder(128);
                for (String value : retail_str_po_master) {
                    if (sb9.length() > 0) {
                        sb9.append("|");
                    }
                    sb9.append(value);
                }
                sb9.insert(0, "|");
                StringBuilder sb10 = new StringBuilder(128);
                for (String value :retail_send_mail_pdf) {
                    if (sb10.length() > 0) {
                        sb10.append("|");
                    }
                    sb10.append(value);
                }
                sb10.insert(0, "|");
                StringBuilder sb11 = new StringBuilder(128);
                for (String value :  dr_discription ) {
                    if (sb11.length() > 0) {
                        sb11.append("|");
                    }
                    sb11.append(value);
                }
                sb11.insert(0, "|");
                StringBuilder sb12 = new StringBuilder(128);
                for (String value : ad_ticker_main) {
                    if (sb12.length() > 0) {
                        sb12.append("|");
                    }
                    sb12.append(value);
                }
                sb12.insert(0, "|");
                StringBuilder sb13 = new StringBuilder(128);
                for (String value : retail_top_product) {
                    if (sb13.length() > 0) {
                        sb13.append("|");
                    }
                    sb13.append(value);
                }
                sb13.insert(0, "|");
                StringBuilder sb14 = new StringBuilder(128);
                for (String value : retail_employees) {
                    if (sb14.length() > 0) {
                        sb14.append("|");
                    }
                    sb14.append(value);
                }
                sb14.insert(0, "|");
                StringBuilder sb15 = new StringBuilder(128);
                for (String value :  retail_store_vend_reject) {
                    if (sb15.length() > 0) {
                        sb15.append("|");
                    }
                    sb15.append(value);
                }
                sb15.insert(0, "|");
                StringBuilder sb16 = new StringBuilder(128);
                for (String value :  retail_str_vendor_detail_return ) {
                    if (sb16.length() > 0) {
                        sb16.append("|");
                    }
                    sb16.append(value);
                }
                sb16.insert(0, "|");
                StringBuilder sb17 = new StringBuilder(128);
                for (String value : retail_store_maint) {
                    if (sb17.length() > 0) {
                        sb17.append("|");
                    }
                    sb17.append(value);
                }
                sb17.insert(0, "|");
                StringBuilder sb18 = new StringBuilder(128);
                for (String value : retail_ad_blinkinglogo_mail ) {
                    if (sb18.length() > 0) {
                        sb18.append("|");
                    }
                    sb18.append(value);
                }
                sb18.insert(0, "|");
                StringBuilder sb19 = new StringBuilder(128);
                for (String value : retail_ad_store_main_mail) {
                    if (sb19.length() > 0) {
                        sb19.append("|");
                    }
                    sb19.append(value);
                }
                sb19.insert(0, "|");
                StringBuilder sb20 = new StringBuilder(128);
                for (String value : retail_ad_ticker_mail) {
                    if (sb20.length() > 0) {
                        sb20.append("|");
                    }
                    sb20.append(value);
                }
                sb20.insert(0, "|");
                StringBuilder sb21 = new StringBuilder(128);
                for (String value : retail_store_prod_cpg_mail) {
                    if (sb21.length() > 0) {
                        sb21.append("|");
                    }
                    sb21.append(value);
                }
                sb21.insert(0, "|");
                StringBuilder sb22 = new StringBuilder(128);
                for (String value : retail_store_prod_local_cpg_mail) {
                    if (sb22.length() > 0) {
                        sb22.append("|");
                    }
                    sb22.append(value);
                }
                sb22.insert(0, "|");
                StringBuilder sb23 = new StringBuilder(128);
                for (String value : retail_store_prod_local_mail) {
                    if (sb23.length() > 0) {
                        sb23.append("|");
                    }
                    sb23.append(value);
                }
                sb23.insert(0, "|");
                StringBuilder sb24 = new StringBuilder(128);
                for (String value : retail_store_prod_mail) {
                    if (sb24.length() > 0) {
                        sb24.append("|");
                    }
                    sb24.append(value);
                }
                sb24.insert(0, "|");
                StringBuilder sb25 = new StringBuilder(128);
                for (String value : retail_store_vendor_mail) {
                    if (sb25.length() > 0) {
                        sb25.append("|");
                    }
                    sb25.append(value);
                }
                sb25.insert(0, "|");
                StringBuilder sb26 = new StringBuilder(128);
                for (String value : retail_str_dstr_mail) {
                    if (sb26.length() > 0) {
                        sb26.append("|");
                    }
                    sb26.append(value);
                }
                sb26.insert(0, "|");
                StringBuilder sb27 = new StringBuilder(128);
                for (String value : retail_str_po_detail_mail) {
                    if (sb27.length() > 0) {
                        sb27.append("|");
                    }
                    sb27.append(value);
                }
                sb27.insert(0, "|");
                StringBuilder sb28 = new StringBuilder(128);
                for (String value : retail_str_sales_detail_mail) {
                    if (sb28.length() > 0) {
                        sb28.append("|");
                    }
                    sb28.append(value);
                }
                sb28.insert(0, "|");
                StringBuilder sb29 = new StringBuilder(128);
                for (String value :tmp_vend_dstr_payment) {
                    if (sb29.length() > 0) {
                        sb29.append("|");
                    }
                    sb29.append(value);
                }
                sb29.insert(0, "|");
                StringBuilder sb31 = new StringBuilder(128);
                for (String value : retail_str_grn_master) {
                    if (sb31.length() > 0) {
                        sb31.append("|");
                    }
                    sb31.append(value);
                }
                sb31.insert(0, "|");
                StringBuilder sb32 = new StringBuilder(128);
                for (String value : retail_str_day_open_close) {
                    if (sb32.length() > 0) {
                        sb32.append("|");
                    }
                    sb32.append(value);
                }
                sb32.insert(0, "|");
                StringBuilder sb33 = new StringBuilder(128);
                for (String value :retail_cust) {
                    if (sb33.length() > 0) {
                        sb33.append("|");
                    }
                    sb33.append(value);
                }
                sb33.insert(0, "|");
                StringBuilder sb34 = new StringBuilder(128);
                for (String value : retail_str_vendor_detail_return_mail) {
                    if (sb34.length() > 0) {
                        sb34.append("|");
                    }
                    sb34.append(value);
                }
                sb34.insert(0, "|");
                StringBuilder sb35 = new StringBuilder(128);
                for (String value : retail_str_stock_master_mail) {
                    if (sb35.length() > 0) {
                        sb35.append("|");
                    }
                    sb35.append(value);
                }
                sb35.insert(0, "|");
                StringBuilder sb36 = new StringBuilder(128);
                for (String value : retail_str_sales_details_return_mail) {
                    if (sb36.length() > 0) {
                        sb36.append("|");
                    }
                    sb36.append(value);
                }
                sb36.insert(0, "|");
                StringBuilder sb37 = new StringBuilder(128);
                for (String value : retail_card_define) {
                    if (sb37.length() > 0) {
                        sb37.append("|");
                    }
                    sb37.append(value);
                }
                sb37.insert(0, "|");
                StringBuilder sb38 = new StringBuilder(128);
                for (String value : rule_defination) {
                    if (sb38.length() > 0) {
                        sb38.append("|");
                    }
                    sb38.append(value);
                }
                sb38.insert(0, "|");
                StringBuilder sb39 = new StringBuilder(128);
                for (String value : retail_str_lineitem_disc) {
                    if (sb39.length() > 0) {
                        sb39.append("|");
                    }
                    sb39.append(value);
                }
                sb39.insert(0, "|");
                StringBuilder sb40 = new StringBuilder(128);
                for (String value : retail_store_cust_reject) {
                    if (sb40.length() > 0) {
                        sb40.append("|");
                    }
                    sb40.append(value);
                }
                sb40.insert(0, "|");
                StringBuilder sb41 = new StringBuilder(128);
                for (String value : retail_str_bill_lvl_disc) {
                    if (sb41.length() > 0) {
                        sb41.append("|");
                    }
                    sb41.append(value);
                }
                sb41.insert(0, "|");
                StringBuilder sb42 = new StringBuilder(128);
                for (String value :retail_media_click) {
                    if (sb42.length() > 0) {
                        sb42.append("|");
                    }
                    sb42.append(value);
                }
                sb42.insert(0, "|");
                StringBuilder sb43 = new StringBuilder(128);
                for (String value :retail_str_sales_detail) {
                    if (sb43.length() > 0) {
                        sb43.append("|");
                    }
                    sb43.append(value);
                }
                sb43.insert(0, "|");
                StringBuilder sb44 = new StringBuilder(128);
                for (String value :retail_str_sales_master) {
                    if (sb44.length() > 0) {
                        sb44.append("|");
                    }
                    sb44.append(value);
                }
                sb44.insert(0, "|");
                StringBuilder sb45 = new StringBuilder(128);
                for (String value :retail_bill_display) {
                    if (sb45.length() > 0) {
                        sb45.append("|");
                    }
                    sb45.append(value);
                }
                sb45.insert(0, "|");
                StringBuilder sb46 = new StringBuilder(128);
                for (String value :retail_bill_visible) {
                    if (sb46.length() > 0) {
                        sb46.append("|");
                    }
                    sb46.append(value);
                }
                sb46.insert(0, "|");
                StringBuilder sb47 = new StringBuilder(128);
                for (String value :retail_store_decimal) {
                    if (sb47.length() > 0) {
                        sb47.append("|");
                    }
                    sb47.append(value);
                }
                sb47.insert(0, "|");
                SQLiteDatabase DataBase;
                File db_path = new File("/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/Db");
                db_path.getParentFile().mkdirs();
                DataBase = SQLiteDatabase.openOrCreateDatabase(db_path, null);          //Opens database in writable mode.
                DataBase.execSQL("PRAGMA key='Anaconda'");
                uploadStockMaster.put("retail_str_stock_master",String.valueOf(sb));
                retail_storeh.put("retail_store",String.valueOf(sb2));
                tmp_vend_dstr_payment_mailh.put("tmp_vend_dstr_payment_mail",String.valueOf(sb3));
                retail_str_sales_master_returnh.put("retail_str_sales_master_return",String.valueOf(sb4));
                retail_str_sales_details_returnh.put("retail_str_sales_details_return",String.valueOf(sb5));
                retail_str_dstrh.put("retail_str_dstr",String.valueOf(sb6));
                retail_store_prod_localh.put("retail_store_prod_local",String.valueOf(sb7));
                retail_store_prodh.put("retail_store_prod",String.valueOf(sb8));
                retail_str_po_masterh.put("retail_str_po_master",String.valueOf(sb9));
                retail_send_mail_pdfh.put("retail_send_mail_pdf",String.valueOf(sb10));
                dr_discriptionh.put("dr_discription",String.valueOf(sb11));
                ad_ticker_mainh.put("ad_ticker_main",String.valueOf(sb12));
                retail_top_producth.put("retail_top_product",String.valueOf(sb13));
                retail_employeesh.put("retail_employees",String.valueOf(sb14));
                retail_store_vend_rejecth.put("retail_store_vend_reject",String.valueOf(sb15));
                retail_str_vendor_detail_returnh.put("retail_store",String.valueOf(sb16));
                retail_store_mainth.put("retail_store_maint",String.valueOf(sb17));
                retail_ad_blinkinglogo_mailh .put("retail_ad_blinkinglogo_mail",String.valueOf(sb18));
                retail_ad_store_main_mailh.put("retail_ad_store_main_mail",String.valueOf(sb19));
                retail_ad_ticker_mailh.put("retail_ad_ticker_mail",String.valueOf(sb20));
                retail_store_prod_cpg_mailh.put("retail_store_prod_cpg_mail",String.valueOf(sb21));
                retail_store_prod_local_cpg_mailh.put("retail_store_prod_local_cpg_mail",String.valueOf(sb22));
                retail_store_prod_local_mailh.put("retail_store_prod_local_mail",String.valueOf(sb23));
                retail_store_prod_mailh.put("retail_store_prod_mail",String.valueOf(sb24));
                retail_store_vendor_mailh.put("retail_store_vendor_mail",String.valueOf(sb25));
                retail_str_dstr_mailh.put("retail_str_dstr_mail",String.valueOf(sb26));
                retail_str_po_detail_mailh.put("retail_str_po_detail_mail",String.valueOf(sb27));
                retail_str_sales_detail_mailh.put("retail_str_sales_detail_mail",String.valueOf(sb28));

                tmp_vend_dstr_paymenth.put("tmp_vend_dstr_payment",String.valueOf(sb29));
                retail_str_grn_masterh.put("retail_str_grn_master",String.valueOf(sb31));
                retail_str_day_open_closeh.put("retail_str_day_open_close",String.valueOf(sb32));
                retail_custh.put("retail_cust",String.valueOf(sb33));
                retail_str_vendor_detail_return_mailh.put("retail_str_vendor_detail_return_mail",String.valueOf(sb34));
                retail_str_stock_master_mailh.put("retail_str_stock_master_mail",String.valueOf(sb35));
                retail_str_sales_details_return_mailh.put("retail_str_sales_details_return_mail",String.valueOf(sb36));
                retail_card_defineh.put("retail_card_define",String.valueOf(sb37));
                rule_definationh.put("rule_defination",String.valueOf(sb38));
                retail_str_lineitem_disch.put("retail_str_lineitem_disc",String.valueOf(sb39));
                retail_store_cust_rejecth.put("retail_store_cust_reject",String.valueOf(sb40));
                retail_str_bill_lvl_disch.put("retail_str_bill_lvl_disc",String.valueOf(sb41));
                retail_media_clickh .put("retail_media_click",String.valueOf(sb42));
                retail_str_sales_detailh.put("retail_str_sales_detail",String.valueOf(sb43));
                retail_str_sales_masterh.put("retail_str_sales_master",String.valueOf(sb44));
                retail_bill_displayh.put("retail_bill_display",String.valueOf(sb45));
                retail_bill_visibleh.put("retail_bill_visible",String.valueOf(sb46));
                retail_store_decimalh.put("retail_store_decimal",String.valueOf(sb47));
                Log.d("*****",String.valueOf(sb));
                JSONParserSync jsonParserSync = new JSONParserSync();
                JSONObject s = jsonParserSync.sendPostRequest("https://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Stock_Master.jsp",uploadStockMaster);
                try {
                    String succes = s.getString("success");
                    if(succes.equals("1")) {
                        Log.d("Resposnse", succes + " " + "retail_str_stock_master");
                        DataBase.execSQL("update retail_str_stock_master set S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb2));
                JSONParserSync jsonParserSync2 = new JSONParserSync();
                JSONObject s2 = jsonParserSync2.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store.jsp",retail_storeh);
                try {
                    String succes = s2.getString("success");
                    Log.d("Resposnse", String.valueOf(succes)+" " +"retail_Store");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_Store set S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb3));
                JSONParserSync jsonParserSync3 = new JSONParserSync();
                JSONObject s3 = jsonParserSync3.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Tmp_Vend_Dstr_Payment_Mail.jsp", tmp_vend_dstr_payment_mailh);
                try {
                    String succes = s3.getString("success");
                    Log.d("Resposnse", String.valueOf(succes)+" " +"Tmp_Vend_Dstr_Payment_Mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update Tmp_Vend_Dstr_Payment_Mail set S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb4));
                JSONParserSync jsonParserSync4 = new JSONParserSync();
                JSONObject s4 = jsonParserSync4.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Master_Return.jsp",retail_str_sales_master_returnh
                );
                try {
                    String succes = s4.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_sales_master_Return");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update Retail_str_sales_master_Return set S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb5));
                JSONParserSync jsonParserSync5 = new JSONParserSync();
                JSONObject s5 = jsonParserSync5.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Details_Return.jsp",retail_str_sales_details_returnh);
                try {
                    String succes = s5.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_sales_detail_return");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_sales_details_return set S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb6));
                JSONParserSync jsonParserSync6 = new JSONParserSync();
                JSONObject s6 = jsonParserSync6.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Dstr.jsp",retail_str_dstrh
                );
                try {
                    String succes = s6.getString("success");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_dstr set S_FLAG='1'");
                    }
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_dstr");
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb7));
                JSONParserSync jsonParserSync7 = new JSONParserSync();
                JSONObject s7 = jsonParserSync7.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod_Local.jsp",retail_store_prod_localh
                );
                try {
                    String succes = s7.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_prod_local");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_prod_local SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb8));
                JSONParserSync jsonParserSync8 = new JSONParserSync();
                JSONObject s8 = jsonParserSync8.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod.jsp", retail_store_prodh
                );
                try {
                    String succes = s8.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_prod");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_prod SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb9));
                JSONParserSync jsonParserSync9 = new JSONParserSync();
                JSONObject s9 = jsonParserSync9.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Po_Master.jsp", retail_str_po_masterh
                );
                try {
                    String succes = s9.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Po_Master");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_po_master SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb10));
                JSONParserSync jsonParserSync10 = new JSONParserSync();
                JSONObject s10 = jsonParserSync10.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Send_Mail_Pdf.jsp",retail_send_mail_pdfh
                );
                try {
                    String succes = s10.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_send_mail_pdf");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_send_mail_pdf SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb11));
                JSONParserSync jsonParserSync11 = new JSONParserSync();
                JSONObject s11 = jsonParserSync11.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Dr_Discription.jsp", dr_discriptionh
                );
                try {
                    String succes = s11.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Dr_Discription");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update dr_discription SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb12));
                JSONParserSync jsonParserSync12 = new JSONParserSync();
                JSONObject s12 = jsonParserSync12.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Ad_Ticker_Main.jsp",ad_ticker_mainh
                );
                try {
                    String succes = s12.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Ad_ticker_main");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update ad_ticker_main SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb13));
                JSONParserSync jsonParserSync13 = new JSONParserSync();
                JSONObject s13 = jsonParserSync13.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Top_Product.jsp",retail_top_producth);
                try {
                    String succes = s13.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Top_Product");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_top_product SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb14));
                JSONParserSync jsonParserSync14 = new JSONParserSync();
                JSONObject s14 = jsonParserSync14.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Employees.jsp",retail_employeesh
                );
                try {
                    String succes = s14.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Emloyess");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_employees SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb15));
                JSONParserSync jsonParserSync15 = new JSONParserSync();
                JSONObject s15 = jsonParserSync15.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Vend_Reject.jsp", retail_store_vend_rejecth);
                try {
                    String succes = s15.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Store_Vend_Reject");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_vend_reject SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb16));
                JSONParserSync jsonParserSync16 = new JSONParserSync();
                JSONObject s16 = jsonParserSync16.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Vendor_Detail_Return.jsp",retail_str_vendor_detail_returnh
                );
                try {
                    String succes = s16.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_vendor_detail_return");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_vendor_detail_return SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb17));
                JSONParserSync jsonParserSync17 = new JSONParserSync();
                JSONObject s17 = jsonParserSync17.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Maint.jsp",retail_store_mainth
                );
                try {
                    String succes = s17.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"retail-store_maint");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_maint SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb18));
                JSONParserSync jsonParserSync18 = new JSONParserSync();
                JSONObject s18 = jsonParserSync18.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Ad_Blinkinglogo_Mail.jsp",retail_ad_blinkinglogo_mailh
                );
                try {
                    String succes = s18.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retrail_Ad_Blinking_logo_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_ad_blinkinlogo SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb19));
                JSONParserSync jsonParserSync19 = new JSONParserSync();
                JSONObject s19 = jsonParserSync19.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Ad_Store_Main_Mail.jsp",retail_ad_store_main_mailh
                );
                try {
                    String succes = s19.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Ad_Store_Main_Mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_ad_store_main_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb20));
                JSONParserSync jsonParserSync20 = new JSONParserSync();
                JSONObject s20 = jsonParserSync20.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Ad_Ticker_Mail.jsp",retail_ad_ticker_mailh
                );
                try {
                    String succes = s20.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_ad_ticker_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_ad_ticker_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb21));
                JSONParserSync jsonParserSync21 = new JSONParserSync();
                JSONObject s21 = jsonParserSync21.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod_Cpg_Mail.jsp",retail_store_prod_cpg_mailh
                );
                try {
                    String succes = s21.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_prod_cpg_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_prod_cpg_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb22));
                JSONParserSync jsonParserSync22 = new JSONParserSync();
                JSONObject s22 = jsonParserSync22.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod_Local_Cpg_Mail.jsp",retail_store_prod_local_cpg_mailh
                );
                try {
                    String succes = s22.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_prod_local_cpg_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_prod_local_cpg_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb23));
                JSONParserSync jsonParserSync23 = new JSONParserSync();
                JSONObject s23 = jsonParserSync23.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod_Local_Mail.jsp",retail_store_prod_local_mailh);
                try {
                    String succes = s23.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_prod_local_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_prod_local_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb24));
                JSONParserSync jsonParserSync24 = new JSONParserSync();
                JSONObject s24 = jsonParserSync24.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Prod_Mail.jsp",retail_store_prod_mailh
                );
                try {
                    String succes = s24.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Store_Prod_Mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update Retail_Store_Prod_Mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb25));
                JSONParserSync jsonParserSync25 = new JSONParserSync();
                JSONObject s25 = jsonParserSync25.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Vendor_Mail.jsp",retail_store_vendor_mailh);
                try {
                    String succes = s25.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Store_vendor_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_Store_vendor_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb26));
                JSONParserSync jsonParserSync26 = new JSONParserSync();
                JSONObject s26 = jsonParserSync26.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Dstr_Mail.jsp",retail_str_dstr_mailh
                );
                try {
                    String succes = s26.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Dstr-mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_Str_dstr_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb27));
                JSONParserSync jsonParserSync27 = new JSONParserSync();
                JSONObject s27 = jsonParserSync27.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Po_Detail_Mail.jsp",retail_str_po_detail_mailh
                );
                try {
                    String succes = s27.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_po_detail_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_Str_po_detail_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb28));
                JSONParserSync jsonParserSync28 = new JSONParserSync();
                JSONObject s28 = jsonParserSync28.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Detail_Mail.jsp",retail_str_sales_detail_mailh
                );
                try {
                    String succes = s28.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Sales_Detail_Mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_sales_detail_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb29));
                JSONParserSync jsonParserSync29 = new JSONParserSync();
                JSONObject s29 = jsonParserSync29.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Tmp_Vend_Dstr_Payment.jsp",tmp_vend_dstr_paymenth
                );
                try {
                    String succes = s29.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Tmp_Vend_Dstr_Payment");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update tmp_vend_dstr_payment SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb31));
                JSONParserSync jsonParserSync31 = new JSONParserSync();
                JSONObject s31 = jsonParserSync31.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Grn_Master.jsp",retail_str_grn_masterh
                );
                try {
                    String succes = s31.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Grn_Master");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_grn_master SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb32));
                JSONParserSync jsonParserSync32 = new JSONParserSync();
                JSONObject s32 = jsonParserSync32.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Day_Open_Close.jsp",retail_str_day_open_closeh
                );
                try {
                    String succes = s32.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_day_open_close");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_day_open_close SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb33));
                JSONParserSync jsonParserSync33 = new JSONParserSync();
                JSONObject s33 = jsonParserSync33.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Cust.jsp",retail_custh);
                try {
                    String succes = s33.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Cust");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_cust SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb34));
                JSONParserSync jsonParserSync34 = new JSONParserSync();
                JSONObject s34 = jsonParserSync34.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Vendor_Detail_Return_Mail.jsp",retail_str_vendor_detail_return_mailh
                );
                try {
                    String succes = s34.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_vendor_detail_returnn");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_vendor_detail_return SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb35));
                JSONParserSync jsonParserSync35 = new JSONParserSync();
                JSONObject s35 = jsonParserSync35.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Stock_Master_Mail.jsp",retail_str_stock_master_mailh);
                try {
                    String succes = s35.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Stock_Master_Mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_stock_master_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb36));
                JSONParserSync jsonParserSync36 = new JSONParserSync();
                JSONObject s36 = jsonParserSync36.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Details_Return_Mail.jsp",retail_str_sales_details_return_mailh
                );
                try {
                    String succes = s36.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_str_sales_details_return_mail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_sales_details_return_mail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb37));
                JSONParserSync jsonParserSync37 = new JSONParserSync();
                JSONObject s37 = jsonParserSync37.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Card_Define.jsp",retail_card_defineh
                );
                try {
                    String succes = s37.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_card_define");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_card_define SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb38));
                JSONParserSync jsonParserSync38 = new JSONParserSync();
                JSONObject s38 = jsonParserSync38.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Rule_Defination.jsp",rule_definationh
                );
                try {
                    String succes = s38.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Rule_Definition");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update rule_defination SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb39));
                JSONParserSync jsonParserSync39 = new JSONParserSync();
                JSONObject s39 = jsonParserSync39.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Lineitem_Disc.jsp",retail_str_lineitem_disch
                );
                try {
                    String succes = s39.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_LineItem_Discount");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_lineitem_discount SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb40));
                JSONParserSync jsonParserSync40 = new JSONParserSync();
                JSONObject s40 = jsonParserSync40.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Cust_Reject.jsp",retail_store_cust_rejecth
                );
                try {
                    String succes = s40.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Store_cust_reject");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_Store_cust_reject SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb41));
                JSONParserSync jsonParserSync41 = new JSONParserSync();
                JSONObject s41 = jsonParserSync41.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Bill_Lvl_Disc.jsp",retail_str_bill_lvl_disch
                );
                try {
                    String succes = s41.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Str_Bill_Level_Disc");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_bill_lvl_disc SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb42));
                JSONParserSync jsonParserSync42 = new JSONParserSync();
                JSONObject s42 = jsonParserSync42.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Media_Click.jsp",retail_media_clickh
                );
                try {
                    String succes = s42.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Media_Click");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_media_click SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb43));
                JSONParserSync jsonParserSync43 = new JSONParserSync();
                JSONObject s43 = jsonParserSync43.sendPostRequest(" https://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Detail.jsp",retail_str_sales_detailh
                );
                try {
                    String succes = s43.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retails_Str_sales_detail");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_sales_detail SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}

                Log.d("*****",String.valueOf(sb44));
                JSONParserSync jsonParserSync44 = new JSONParserSync();
                JSONObject s44 = jsonParserSync44.sendPostRequest("https://uploadinconn.eu-gb.mybluemix.net/Retail_Str_Sales_Master.jsp",retail_str_sales_masterh
                );
                try {
                    String succes = s44.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"retail_str_sales_master");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_str_sales_master SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb45));
                JSONParserSync jsonParserSync45 = new JSONParserSync();
                JSONObject s45 = jsonParserSync45.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Bill_Display.jsp",retail_bill_displayh
                );
                try {
                    String succes = s45.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_bill_display");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_bill_display SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb46));
                JSONParserSync jsonParserSync46 = new JSONParserSync();
                JSONObject s46 = jsonParserSync46.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Bill_Visible.jsp",retail_bill_visibleh
                );
                try {
                    String succes = s46.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_Bill_Visible");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_bill_visible SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                Log.d("*****",String.valueOf(sb47));
                JSONParserSync jsonParserSync47 = new JSONParserSync();
                JSONObject s47 = jsonParserSync47.sendPostRequest("http://uploadinconn.eu-gb.mybluemix.net/Retail_Store_Decimal.jsp",retail_store_decimalh
                );
                try {
                    String succes = s47.getString("success");
                    Log.d("Resposnse*****", String.valueOf(succes)+" " +"Retail_store_decimal");
                    if(succes.equals("1")) {
                        DataBase.execSQL("update retail_store_decimal SET S_FLAG='1'");
                    }
                }catch (Exception e){e.printStackTrace();}
                return null;
            }
        }
        uploadData uploadData= new uploadData();
        uploadData.execute();
    }
    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        int color;
        if (isConnected) {
            String message = "Good! Connected to Internet";
            Log.d("Message",message);
            uploadData();
        } else {
            String  messages = "Sorry! Not connected to internet";
            Log.d("Message",messages);
            color = Color.RED;
        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) throws InterruptedException {
        showSnack(isConnected);
    }
    public void downloadData(String store_id,String otp,Context context){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(120, TimeUnit.SECONDS).readTimeout(120,TimeUnit.SECONDS).build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder().client(okHttpClient)
                .baseUrl("http://d.eu-gb.mybluemix.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        // prepare call in Retrofit 2.0
        GettingApi stackOverflowAPI = retrofit.create(GettingApi.class);
        // Call<ConfigItems> call = stackOverflowAPI.loadQuestions("Field");
        Call<ConfigItems> call1 = stackOverflowAPI.load(store_id,otp,"DATA");
        //asynchronous call
        // call.enqueue(this);
        call1.enqueue(this);
        loading = new ProgressDialog(context);
        loading.setMessage("Thanks for Being Patient...");
        loading.setIndeterminate(false);
        loading.setCancelable(false);
        loading.show();
    }
    String id,JSON_STRING;
    public void jsonparsed(String input) {
        try {
            //storeId = storeID.getText().toString();

            JSONObject jsonaman = new JSONObject(input);
            JSONArray firsttitlearray = jsonaman.getJSONArray(Config.TAG_ARRAY_TABLE_ONE);
            for (int i = 0; i < firsttitlearray.length(); i++) {
                JSONObject obj1 = (JSONObject) firsttitlearray.get(i);
                id = obj1.getString(Config.TAG_FIELD);
                field1.add(id);
                System.gc();

            }

            System.gc();
        } catch (JSONException e) {
            Log.e("exception", e.toString());
        }

        try {

            JSONObject jsonObject2 = new JSONObject(input);
            JSONArray firsttitlearray2 = jsonObject2.getJSONArray(Config.TAG_ARRAY_TABLE_TWO);
            for (int i = 0; i < firsttitlearray2.length(); i++) {
                JSONObject obj2 = (JSONObject) firsttitlearray2.get(i);
                id = obj2.getString(Config.TAG_FIELD);
                field2.add(id);
                System.gc();

            }

            System.gc();
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject3 = new JSONObject(input);
            JSONArray firsttitlearray3 = jsonObject3.getJSONArray(Config.TAG_ARRAY_TABLE_THREE);
            for (int i = 0; i < firsttitlearray3.length(); i++) {
                JSONObject obj3 = (JSONObject) firsttitlearray3.get(i);
                id = obj3.getString(Config.TAG_FIELD);
                field3.add(id);
                System.gc();
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject4 = new JSONObject(input);
            JSONArray firsttitlearray4 = jsonObject4.getJSONArray(Config.TAG_ARRAY_TABLE_FOUR);
            for (int i = 0; i < firsttitlearray4.length(); i++) {
                JSONObject obj4 = (JSONObject) firsttitlearray4.get(i);
                id = obj4.getString(Config.TAG_FIELD);
                field4.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject5 = new JSONObject(input);
            JSONArray firsttitlearray5 = jsonObject5.getJSONArray(Config.TAG_ARRAY_TABLE_FIVE);
            for (int i = 0; i < firsttitlearray5.length(); i++) {
                JSONObject obj5 = (JSONObject) firsttitlearray5.get(i);
                id = obj5.getString(Config.TAG_FIELD);
                field5.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject6 = new JSONObject(input);
            JSONArray firsttitlearray6 = jsonObject6.getJSONArray(Config.TAG_ARRAY_TABLE_SIX);
            for (int i = 0; i < firsttitlearray6.length(); i++) {
                JSONObject obj6 = (JSONObject) firsttitlearray6.get(i);
                id = obj6.getString(Config.TAG_FIELD);
                field6.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject7 = new JSONObject(input);
            JSONArray firsttitlearray7 = jsonObject7.getJSONArray(Config.TAG_ARRAY_TABLE_SEVEN);
            for (int i = 0; i < firsttitlearray7.length(); i++) {

                JSONObject obj7 = (JSONObject) firsttitlearray7.get(i);
                id = obj7.getString(Config.TAG_FIELD);
                field7.add(id);

            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject8 = new JSONObject(input);
            JSONArray firsttitlearray8 = jsonObject8.getJSONArray(Config.TAG_ARRAY_TABLE_EIGHT);
            for (int i = 0; i < firsttitlearray8.length(); i++) {
                JSONObject obj8 = (JSONObject) firsttitlearray8.get(i);
                id = obj8.getString(Config.TAG_FIELD);
                field8.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject9 = new JSONObject(input);
            JSONArray firsttitlearray9 = jsonObject9.getJSONArray(Config.TAG_ARRAY_TABLE_NINE);
            for (int i = 0; i < firsttitlearray9.length(); i++) {

                JSONObject obj9 = (JSONObject) firsttitlearray9.get(i);
                id = obj9.getString(Config.TAG_FIELD);
                field9.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject10 = new JSONObject(input);
            JSONArray firsttitlearray10 = jsonObject10.getJSONArray(Config.TAG_ARRAY_TABLE_TEN);
            for (int i = 0; i < firsttitlearray10.length(); i++) {
                JSONObject obj10 = (JSONObject) firsttitlearray10.get(i);
                id = obj10.getString(Config.TAG_FIELD);
                field10.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject11 = new JSONObject(input);
            JSONArray firsttitlearray11 = jsonObject11.getJSONArray(Config.TAG_ARRAY_TABLE_ELEVEN);
            for (int i = 0; i < firsttitlearray11.length(); i++) {
                JSONObject obj11 = (JSONObject) firsttitlearray11.get(i);
                id = obj11.getString(Config.TAG_FIELD);
                field11.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject12 = new JSONObject(input);
            JSONArray firsttitlearray12 = jsonObject12.getJSONArray(Config.TAG_ARRAY_TABLE_TWELVE);
            for (int i = 0; i < firsttitlearray12.length(); i++) {
                JSONObject obj12 = (JSONObject) firsttitlearray12.get(i);
                id = obj12.getString(Config.TAG_FIELD);
                field12.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject13 = new JSONObject(input);
            JSONArray firsttitlearray13 = jsonObject13.getJSONArray(Config.TAG_ARRAY_TABLE_THIRTEEN);
            for (int i = 0; i < firsttitlearray13.length(); i++) {
                JSONObject obj13 = (JSONObject) firsttitlearray13.get(i);
                id = obj13.getString(Config.TAG_FIELD);
                field13.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject14 = new JSONObject(input);
            JSONArray firsttitlearray14 = jsonObject14.getJSONArray(Config.TAG_ARRAY_TABLE_FOURTEEN);
            for (int i = 0; i < firsttitlearray14.length(); i++) {
                JSONObject obj14 = (JSONObject) firsttitlearray14.get(i);
                id = obj14.getString(Config.TAG_FIELD);
                field14.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject15 = new JSONObject(input);
            JSONArray firsttitlearray15 = jsonObject15.getJSONArray(Config.TAG_ARRAY_TABLE_FIFTEEN);
            for (int i = 0; i < firsttitlearray15.length(); i++) {
                JSONObject obj15 = (JSONObject) firsttitlearray15.get(i);
                id = obj15.getString(Config.TAG_FIELD);
                field15.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject16 = new JSONObject(input);
            JSONArray firsttitlearray16 = jsonObject16.getJSONArray(Config.TAG_ARRAY_TABLE_SIXTEEN);
            for (int i = 0; i < firsttitlearray16.length(); i++) {
                JSONObject obj16 = (JSONObject) firsttitlearray16.get(i);
                id = obj16.getString(Config.TAG_FIELD);
                field16.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject17 = new JSONObject(input);
            JSONArray firsttitlearray17 = jsonObject17.getJSONArray(Config.TAG_ARRAY_TABLE_SEVENTEEN);
            for (int i = 0; i < firsttitlearray17.length(); i++) {
                JSONObject obj17 = (JSONObject) firsttitlearray17.get(i);
                id = obj17.getString(Config.TAG_FIELD);
                field17.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject18 = new JSONObject(input);
            JSONArray firsttitlearray18 = jsonObject18.getJSONArray(Config.TAG_ARRAY_TABLE_EIGHTEEN);
            for (int i = 0; i < firsttitlearray18.length(); i++) {
                JSONObject obj18 = (JSONObject) firsttitlearray18.get(i);
                id = obj18.getString(Config.TAG_FIELD);
                field18.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject19 = new JSONObject(input);
            JSONArray firsttitlearray19 = jsonObject19.getJSONArray(Config.TAG_ARRAY_TABLE_NINETEEN);
            for (int i = 0; i < firsttitlearray19.length(); i++) {
                JSONObject obj19 = (JSONObject) firsttitlearray19.get(i);
                id = obj19.getString(Config.TAG_FIELD);
                field19.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject20 = new JSONObject(input);
            JSONArray firsttitlearray20 = jsonObject20.getJSONArray(Config.TAG_ARRAY_TABLE_TWENTY);
            for (int i = 0; i < firsttitlearray20.length(); i++) {
                JSONObject obj20 = (JSONObject) firsttitlearray20.get(i);
                id = obj20.getString(Config.TAG_FIELD);
                field20.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject21 = new JSONObject(input);
            JSONArray firsttitlearray21 = jsonObject21.getJSONArray(Config.TAG_ARRAY_TABLE_21);
            for (int i = 0; i < firsttitlearray21.length(); i++) {
                JSONObject obj21 = (JSONObject) firsttitlearray21.get(i);
                id = obj21.getString(Config.TAG_FIELD);
                field21.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject22 = new JSONObject(input);
            JSONArray firsttitlearray22 = jsonObject22.getJSONArray(Config.TAG_ARRAY_TABLE_22);
            for (int i = 0; i < firsttitlearray22.length(); i++) {
                JSONObject obj22 = (JSONObject) firsttitlearray22.get(i);
                id = obj22.getString(Config.TAG_FIELD);
                field22.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject23 = new JSONObject(input);
            JSONArray firsttitlearray23 = jsonObject23.getJSONArray(Config.TAG_ARRAY_TABLE_23);
            for (int i = 0; i < firsttitlearray23.length(); i++) {
                JSONObject obj23 = (JSONObject) firsttitlearray23.get(i);
                id = obj23.getString(Config.TAG_FIELD);
                field23.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject24 = new JSONObject(input);
            JSONArray firsttitlearray24 = jsonObject24.getJSONArray(Config.TAG_ARRAY_TABLE_24);
            for (int i = 0; i < firsttitlearray24.length(); i++) {
                JSONObject obj24 = (JSONObject) firsttitlearray24.get(i);
                id = obj24.getString(Config.TAG_FIELD);
                field24.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject25 = new JSONObject(input);
            JSONArray firsttitlearray25 = jsonObject25.getJSONArray(Config.TAG_ARRAY_TABLE_25);
            for (int i = 0; i < firsttitlearray25.length(); i++) {
                JSONObject obj25 = (JSONObject) firsttitlearray25.get(i);
                id = obj25.getString(Config.TAG_FIELD);
                field25.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject26 = new JSONObject(input);
            JSONArray firsttitlearray26 = jsonObject26.getJSONArray(Config.TAG_ARRAY_TABLE_26);
            for (int i = 0; i < firsttitlearray26.length(); i++) {
                JSONObject obj26 = (JSONObject) firsttitlearray26.get(i);
                id = obj26.getString(Config.TAG_FIELD);
                field26.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject27 = new JSONObject(input);
            JSONArray firsttitlearray27 = jsonObject27.getJSONArray(Config.TAG_ARRAY_TABLE_27);
            for (int i = 0; i < firsttitlearray27.length(); i++) {
                JSONObject obj27 = (JSONObject) firsttitlearray27.get(i);
                id = obj27.getString(Config.TAG_FIELD);
                field27.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject28 = new JSONObject(input);
            JSONArray firsttitlearray28 = jsonObject28.getJSONArray(Config.TAG_ARRAY_TABLE_28);
            for (int i = 0; i < firsttitlearray28.length(); i++) {
                JSONObject obj28 = (JSONObject) firsttitlearray28.get(i);
                id = obj28.getString(Config.TAG_FIELD);
                field28.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject29 = new JSONObject(input);
            JSONArray firsttitlearray29 = jsonObject29.getJSONArray(Config.TAG_ARRAY_TABLE_29);
            for (int i = 0; i < firsttitlearray29.length(); i++) {
                JSONObject obj29 = (JSONObject) firsttitlearray29.get(i);
                id = obj29.getString(Config.TAG_FIELD);
                field29.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject30 = new JSONObject(input);
            JSONArray firsttitlearray30 = jsonObject30.getJSONArray(Config.TAG_ARRAY_TABLE_30);
            for (int i = 0; i < firsttitlearray30.length(); i++) {
                JSONObject obj30 = (JSONObject) firsttitlearray30.get(i);
                id = obj30.getString(Config.TAG_FIELD);
                field30.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject31 = new JSONObject(input);
            JSONArray firsttitlearray31 = jsonObject31.getJSONArray(Config.TAG_ARRAY_TABLE_31);
            for (int i = 0; i < firsttitlearray31.length(); i++) {
                JSONObject obj31 = (JSONObject) firsttitlearray31.get(i);
                id = obj31.getString(Config.TAG_FIELD);
                field31.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject32 = new JSONObject(input);
            JSONArray firsttitlearray32 = jsonObject32.getJSONArray(Config.TAG_ARRAY_TABLE_32);
            for (int i = 0; i < firsttitlearray32.length(); i++) {
                JSONObject obj32 = (JSONObject) firsttitlearray32.get(i);
                id = obj32.getString(Config.TAG_FIELD);
                field32.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject33 = new JSONObject(input);
            JSONArray firsttitlearray33 = jsonObject33.getJSONArray(Config.TAG_ARRAY_TABLE_33);
            for (int i = 0; i < firsttitlearray33.length(); i++) {
                JSONObject obj33 = (JSONObject) firsttitlearray33.get(i);
                id = obj33.getString(Config.TAG_FIELD);
                field33.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject34 = new JSONObject(input);
            JSONArray firsttitlearray34 = jsonObject34.getJSONArray(Config.TAG_ARRAY_TABLE_34);
            for (int i = 0; i < firsttitlearray34.length(); i++) {
                JSONObject obj34 = (JSONObject) firsttitlearray34.get(i);
                id = obj34.getString(Config.TAG_FIELD);
                field34.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject35 = new JSONObject(input);
            JSONArray firsttitlearray35 = jsonObject35.getJSONArray(Config.TAG_ARRAY_TABLE_35);
            for (int i = 0; i < firsttitlearray35.length(); i++) {
                JSONObject obj35 = (JSONObject) firsttitlearray35.get(i);
                id = obj35.getString(Config.TAG_FIELD);
                field35.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject36 = new JSONObject(input);
            JSONArray firsttitlearray36 = jsonObject36.getJSONArray(Config.TAG_ARRAY_TABLE_36);
            for (int i = 0; i < firsttitlearray36.length(); i++) {
                JSONObject obj36 = (JSONObject) firsttitlearray36.get(i);
                id = obj36.getString(Config.TAG_FIELD);
                field36.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject37 = new JSONObject(input);
            JSONArray firsttitlearray37 = jsonObject37.getJSONArray(Config.TAG_ARRAY_TABLE_37);
            for (int i = 0; i < firsttitlearray37.length(); i++) {
                JSONObject obj37 = (JSONObject) firsttitlearray37.get(i);
                id = obj37.getString(Config.TAG_FIELD);
                field37.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject38 = new JSONObject(input);
            JSONArray firsttitlearray38 = jsonObject38.getJSONArray(Config.TAG_ARRAY_TABLE_38);
            for (int i = 0; i < firsttitlearray38.length(); i++) {
                JSONObject obj38 = (JSONObject) firsttitlearray38.get(i);
                id = obj38.getString(Config.TAG_FIELD);
                field38.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject39 = new JSONObject(input);
            JSONArray firsttitlearray39 = jsonObject39.getJSONArray(Config.TAG_ARRAY_TABLE_39);
            for (int i = 0; i < firsttitlearray39.length(); i++) {
                JSONObject obj39 = (JSONObject) firsttitlearray39.get(i);
                id = obj39.getString(Config.TAG_FIELD);
                field39.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject40 = new JSONObject(input);
            JSONArray firsttitlearray40 = jsonObject40.getJSONArray(Config.TAG_ARRAY_TABLE_40);
            for (int i = 0; i < firsttitlearray40.length(); i++) {
                JSONObject obj40 = (JSONObject) firsttitlearray40.get(i);
                id = obj40.getString(Config.TAG_FIELD);
                field40.add(id);
            }
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject41 = new JSONObject(input);
            JSONArray firsttitlearray41 = jsonObject41.getJSONArray(Config.TAG_ARRAY_TABLE_41);
            for (int i = 0; i < firsttitlearray41.length(); i++) {
                JSONObject obj41 = (JSONObject) firsttitlearray41.get(i);
                id = obj41.getString(Config.TAG_FIELD);
                field41.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject42 = new JSONObject(input);
            JSONArray firsttitlearray42 = jsonObject42.getJSONArray(Config.TAG_ARRAY_TABLE_42);
            for (int i = 0; i < firsttitlearray42.length(); i++) {
                JSONObject obj42 = (JSONObject) firsttitlearray42.get(i);
                id = obj42.getString(Config.TAG_FIELD);
                field42.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject43 = new JSONObject(input);
            JSONArray firsttitlearray43 = jsonObject43.getJSONArray(Config.TAG_ARRAY_TABLE_43);
            for (int i = 0; i < firsttitlearray43.length(); i++) {
                JSONObject obj43 = (JSONObject) firsttitlearray43.get(i);
                id = obj43.getString(Config.TAG_FIELD);
                field43.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject44 = new JSONObject(input);
            JSONArray firsttitlearray44 = jsonObject44.getJSONArray(Config.TAG_ARRAY_TABLE_44);
            for (int i = 0; i < firsttitlearray44.length(); i++) {
                JSONObject obj44 = (JSONObject) firsttitlearray44.get(i);
                id = obj44.getString(Config.TAG_FIELD);
                field44.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject45 = new JSONObject(input);
            JSONArray firsttitlearray45 = jsonObject45.getJSONArray(Config.TAG_ARRAY_TABLE_45);
            for (int i = 0; i < firsttitlearray45.length(); i++) {
                JSONObject obj45 = (JSONObject) firsttitlearray45.get(i);
                id = obj45.getString(Config.TAG_FIELD);
                field45.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject46 = new JSONObject(input);
            JSONArray firsttitlearray46 = jsonObject46.getJSONArray(Config.TAG_ARRAY_TABLE_46);
            for (int i = 0; i < firsttitlearray46.length(); i++) {
                JSONObject obj46 = (JSONObject) firsttitlearray46.get(i);
                id = obj46.getString(Config.TAG_FIELD);
                field46.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject47 = new JSONObject(input);
            JSONArray firsttitlearray47 = jsonObject47.getJSONArray(Config.TAG_ARRAY_TABLE_47);
            for (int i = 0; i < firsttitlearray47.length(); i++) {
                JSONObject obj47 = (JSONObject) firsttitlearray47.get(i);
                id = obj47.getString(Config.TAG_FIELD);
                field47.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject48 = new JSONObject(input);
            JSONArray firsttitlearray48 = jsonObject48.getJSONArray(Config.TAG_ARRAY_TABLE_48);
            for (int i = 0; i < firsttitlearray48.length(); i++) {
                JSONObject obj48 = (JSONObject) firsttitlearray48.get(i);
                id = obj48.getString(Config.TAG_FIELD);
                field48.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject49 = new JSONObject(input);
            JSONArray firsttitlearray49 = jsonObject49.getJSONArray(Config.TAG_ARRAY_TABLE_49);
            for (int i = 0; i < firsttitlearray49.length(); i++) {
                JSONObject obj49 = (JSONObject) firsttitlearray49.get(i);
                id = obj49.getString(Config.TAG_FIELD);
                field49.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject50 = new JSONObject(input);
            JSONArray firsttitlearray50 = jsonObject50.getJSONArray(Config.TAG_ARRAY_TABLE_50);
            for (int i = 0; i < firsttitlearray50.length(); i++) {
                JSONObject obj50 = (JSONObject) firsttitlearray50.get(i);
                id = obj50.getString(Config.TAG_FIELD);
                field50.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject51 = new JSONObject(input);
            JSONArray firsttitlearray51 = jsonObject51.getJSONArray(Config.TAG_ARRAY_TABLE_51);
            for (int i = 0; i < firsttitlearray51.length(); i++) {
                JSONObject obj51 = (JSONObject) firsttitlearray51.get(i);
                id = obj51.getString(Config.TAG_FIELD);
                field51.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject52 = new JSONObject(input);
            JSONArray firsttitlearray52 = jsonObject52.getJSONArray(Config.TAG_ARRAY_TABLE_52);
            for (int i = 0; i < firsttitlearray52.length(); i++) {
                JSONObject obj52 = (JSONObject) firsttitlearray52.get(i);
                id = obj52.getString(Config.TAG_FIELD);
                field52.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject53 = new JSONObject(input);
            JSONArray firsttitlearray53 = jsonObject53.getJSONArray(Config.TAG_ARRAY_TABLE_53);
            for (int i = 0; i < firsttitlearray53.length(); i++) {
                JSONObject obj53 = (JSONObject) firsttitlearray53.get(i);
                id = obj53.getString(Config.TAG_FIELD);
                field53.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject54 = new JSONObject(input);
            JSONArray firsttitlearray54 = jsonObject54.getJSONArray(Config.TAG_ARRAY_TABLE_54);
            for (int i = 0; i < firsttitlearray54.length(); i++) {
                JSONObject obj54 = (JSONObject) firsttitlearray54.get(i);
                id = obj54.getString(Config.TAG_FIELD);
                field54.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject55 = new JSONObject(input);
            JSONArray firsttitlearray55 = jsonObject55.getJSONArray(Config.TAG_ARRAY_TABLE_55);
            for (int i = 0; i < firsttitlearray55.length(); i++) {
                JSONObject obj55 = (JSONObject) firsttitlearray55.get(i);
                id = obj55.getString(Config.TAG_FIELD);
                field55.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject56 = new JSONObject(input);
            JSONArray firsttitlearray56 = jsonObject56.getJSONArray(Config.TAG_ARRAY_TABLE_56);
            for (int i = 0; i < firsttitlearray56.length(); i++) {
                JSONObject obj56 = (JSONObject) firsttitlearray56.get(i);
                id = obj56.getString(Config.TAG_FIELD);
                field56.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject57 = new JSONObject(input);
            JSONArray firsttitlearray57 = jsonObject57.getJSONArray(Config.TAG_ARRAY_TABLE_57);
            for (int i = 0; i < firsttitlearray57.length(); i++) {
                JSONObject obj57 = (JSONObject) firsttitlearray57.get(i);
                id = obj57.getString(Config.TAG_FIELD);
                field57.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject58 = new JSONObject(input);
            JSONArray firsttitlearray58 = jsonObject58.getJSONArray(Config.TAG_ARRAY_TABLE_58);
            for (int i = 0; i < firsttitlearray58.length(); i++) {
                JSONObject obj58 = (JSONObject) firsttitlearray58.get(i);
                id = obj58.getString(Config.TAG_FIELD);
                field58.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject59 = new JSONObject(input);
            JSONArray firsttitlearray59 = jsonObject59.getJSONArray(Config.TAG_ARRAY_TABLE_59);
            for (int i = 0; i < firsttitlearray59.length(); i++) {
                JSONObject obj59 = (JSONObject) firsttitlearray59.get(i);
                id = obj59.getString(Config.TAG_FIELD);
                field59.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject60 = new JSONObject(input);
            JSONArray firsttitlearray60 = jsonObject60.getJSONArray(Config.TAG_ARRAY_TABLE_60);
            for (int i = 0; i < firsttitlearray60.length(); i++) {
                JSONObject obj60 = (JSONObject) firsttitlearray60.get(i);
                id = obj60.getString(Config.TAG_FIELD);
                field60.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject61 = new JSONObject(input);
            JSONArray firsttitlearray61 = jsonObject61.getJSONArray(Config.TAG_ARRAY_TABLE_61);
            for (int i = 0; i < firsttitlearray61.length(); i++) {
                JSONObject obj61 = (JSONObject) firsttitlearray61.get(i);
                id = obj61.getString(Config.TAG_FIELD);
                field61.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());

        }
        try {
            JSONObject jsonObject62 = new JSONObject(input);
            JSONArray firsttitlearray62 = jsonObject62.getJSONArray(Config.TAG_ARRAY_TABLE_62);
            for (int i = 0; i < firsttitlearray62.length(); i++) {
                JSONObject obj62 = (JSONObject) firsttitlearray62.get(i);
                id = obj62.getString(Config.TAG_FIELD);
                field62.add(id);
            }

        } catch (Exception e) {
            Log.e("exception 62", e.toString());
        }


        try {
            JSONObject jsonObject63 = new JSONObject(input);
            JSONArray firsttitlearray63 = jsonObject63.getJSONArray(Config.TAG_ARRAY_TABLE_63);
            for (int i = 0; i < firsttitlearray63.length(); i++) {
                JSONObject obj63 = (JSONObject) firsttitlearray63.get(i);
                id = obj63.getString(Config.TAG_FIELD);
                field63.add(id);
            }

        } catch (Exception e) {
            Log.e("exception 63", e.toString());
        }

        try {
            JSONObject jsonObject64 = new JSONObject(input);
            JSONArray firsttitlearray64 = jsonObject64.getJSONArray(Config.TAG_ARRAY_TABLE_64);
            for (int i = 0; i < firsttitlearray64.length(); i++) {
                JSONObject obj64 = (JSONObject) firsttitlearray64.get(i);
                id = obj64.getString(Config.TAG_FIELD);
                field64.add(id);
            }

        } catch (Exception e) {
            Log.e("exception 64", e.toString());
        }

        try {
            JSONObject jsonObject65 = new JSONObject(input);
            JSONArray firsttitlearray65 = jsonObject65.getJSONArray(Config.TAG_ARRAY_TABLE_65);
            for (int i = 0; i < firsttitlearray65.length(); i++) {
                JSONObject obj65 = (JSONObject) firsttitlearray65.get(i);
                id = obj65.getString(Config.TAG_FIELD);
                field65.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject66 = new JSONObject(input);
            JSONArray firsttitlearray66 = jsonObject66.getJSONArray(Config.TAG_ARRAY_TABLE_66);
            for (int i = 0; i < firsttitlearray66.length(); i++) {
                JSONObject obj66 = (JSONObject) firsttitlearray66.get(i);
                id = obj66.getString(Config.TAG_FIELD);
                field66.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        try {
            JSONObject jsonObject68 = new JSONObject(input);
            JSONArray firsttitlearray68 = jsonObject68.getJSONArray(Config.TAG_ARRAY_TABLE_68);
            for (int i = 0; i < firsttitlearray68.length(); i++) {
                JSONObject obj68 = (JSONObject) firsttitlearray68.get(i);
                id = obj68.getString(Config.TAG_FIELD);
                field68.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject69 = new JSONObject(input);
            JSONArray firsttitlearray69 = jsonObject69.getJSONArray(Config.TAG_ARRAY_TABLE_69);
            for (int i = 0; i < firsttitlearray69.length(); i++) {
                JSONObject obj69 = (JSONObject) firsttitlearray69.get(i);
                id = obj69.getString(Config.TAG_FIELD);
                field69.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject70 = new JSONObject(input);
            JSONArray firsttitlearray70 = jsonObject70.getJSONArray(Config.TAG_ARRAY_TABLE_70);
            for (int i = 0; i < firsttitlearray70.length(); i++) {
                JSONObject obj70 = (JSONObject) firsttitlearray70.get(i);
                id = obj70.getString(Config.TAG_FIELD);
                field70.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject71 = new JSONObject(input);
            JSONArray firsttitlearray71 = jsonObject71.getJSONArray(Config.TAG_ARRAY_TABLE_71);
            for (int i = 0; i < firsttitlearray71.length(); i++) {
                JSONObject obj71 = (JSONObject) firsttitlearray71.get(i);
                id = obj71.getString(Config.TAG_FIELD);
                field71.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject72 = new JSONObject(input);
            JSONArray firsttitlearray72 = jsonObject72.getJSONArray(Config.TAG_ARRAY_TABLE_72);
            for (int i = 0; i < firsttitlearray72.length(); i++) {
                JSONObject obj72 = (JSONObject) firsttitlearray72.get(i);
                id = obj72.getString(Config.TAG_FIELD);
                field72.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject73 = new JSONObject(input);
            JSONArray firsttitlearray73 = jsonObject73.getJSONArray(Config.TAG_ARRAY_TABLE_73);
            for (int i = 0; i < firsttitlearray73.length(); i++) {
                JSONObject obj73 = (JSONObject) firsttitlearray73.get(i);
                id = obj73.getString(Config.TAG_FIELD);
                field73.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject74 = new JSONObject(input);
            JSONArray firsttitlearray74 = jsonObject74.getJSONArray(Config.TAG_ARRAY_TABLE_74);
            for (int i = 0; i < firsttitlearray74.length(); i++) {
                JSONObject obj74 = (JSONObject) firsttitlearray74.get(i);
                id = obj74.getString(Config.TAG_FIELD);
                field74.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject75 = new JSONObject(input);
            JSONArray firsttitlearray75 = jsonObject75.getJSONArray(Config.TAG_ARRAY_TABLE_75);
            for (int i = 0; i < firsttitlearray75.length(); i++) {
                JSONObject obj75 = (JSONObject) firsttitlearray75.get(i);
                id = obj75.getString(Config.TAG_FIELD);
                field75.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject76 = new JSONObject(input);
            JSONArray firsttitlearray76 = jsonObject76.getJSONArray(Config.TAG_ARRAY_TABLE_76);
            for (int i = 0; i < firsttitlearray76.length(); i++) {
                JSONObject obj76 = (JSONObject) firsttitlearray76.get(i);
                id = obj76.getString(Config.TAG_FIELD);
                field76.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject77 = new JSONObject(input);
            JSONArray firsttitlearray77 = jsonObject77.getJSONArray(Config.TAG_ARRAY_TABLE_77);
            for (int i = 0; i < firsttitlearray77.length(); i++) {
                JSONObject obj77 = (JSONObject) firsttitlearray77.get(i);
                id = obj77.getString(Config.TAG_FIELD);
                field77.add(id);
            }
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject78 = new JSONObject(input);
            JSONArray firsttitlearray78 = jsonObject78.getJSONArray(Config.TAG_ARRAY_TABLE_78);
            for (int i = 0; i < firsttitlearray78.length(); i++) {
                JSONObject obj78 = (JSONObject) firsttitlearray78.get(i);
                id = obj78.getString(Config.TAG_FIELD);
                field78.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject79 = new JSONObject(input);
            JSONArray firsttitlearray79 = jsonObject79.getJSONArray(Config.TAG_ARRAY_TABLE_79);
            for (int i = 0; i < firsttitlearray79.length(); i++) {
                JSONObject obj79 = (JSONObject) firsttitlearray79.get(i);
                id = obj79.getString(Config.TAG_FIELD);
                field79.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject80 = new JSONObject(input);
            JSONArray firsttitlearray80 = jsonObject80.getJSONArray(Config.TAG_ARRAY_TABLE_80);
            for (int i = 0; i < firsttitlearray80.length(); i++) {
                JSONObject obj80 = (JSONObject) firsttitlearray80.get(i);
                id = obj80.getString(Config.TAG_FIELD);
                field80.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        try {
            JSONObject jsonObject81 = new JSONObject(input);
            JSONArray firsttitlearray81 = jsonObject81.getJSONArray(Config.TAG_ARRAY_TABLE_81);
            for (int i = 0; i < firsttitlearray81.length(); i++) {
                JSONObject obj81 = (JSONObject) firsttitlearray81.get(i);
                id = obj81.getString(Config.TAG_FIELD);
                field81.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject82 = new JSONObject(input);
            JSONArray firsttitlearray82 = jsonObject82.getJSONArray(Config.TAG_ARRAY_TABLE_82);
            for (int i = 0; i < firsttitlearray82.length(); i++) {
                JSONObject obj82 = (JSONObject) firsttitlearray82.get(i);
                id = obj82.getString(Config.TAG_FIELD);
                field82.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
        try {
            JSONObject jsonObject83 = new JSONObject(input);
            JSONArray firsttitlearray83 = jsonObject83.getJSONArray(Config.TAG_ARRAY_TABLE_83);
            for (int i = 0; i < firsttitlearray83.length(); i++) {
                JSONObject obj83 = (JSONObject) firsttitlearray83.get(i);
                id = obj83.getString(Config.TAG_FIELD);
                field83.add(id);
            }

        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        try {
            JSONObject jsonObject84 = new JSONObject(input);
            JSONArray firsttitlearray84 = jsonObject84.getJSONArray("retail_credit_bill_details");
            for (int i = 0; i < firsttitlearray84.length(); i++) {
                JSONObject obj84 = (JSONObject) firsttitlearray84.get(i);
                id = obj84.getString(Config.TAG_FIELD);
                field84.add(id);
            }


        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

        Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();

    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(login.this, "Fetching Data", "Wait...", false, false);

            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                jsonparsed(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                JSONParserSync rh = new JSONParserSync();
                String s = rh.sendGetRequest("http://99sync.eu-gb.mybluemix.net/Testing_Str_JSON.jsp");
                String hit = rh.sendGetRequest("https://99mailpos.eu-gb.mybluemix.net/Sales.jsp");
                String ravimail = rh.sendGetRequest("https://99mailpos.eu-gb.mybluemix.net/My_Deal_Mail.jsp");


                //String s = rh.sendGetRequest("http://52.76.28.14/Android/json.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    @Override
    public void onResponse(Call<ConfigItems> call, Response<ConfigItems> response) {
        try {
            data1.addAll(response.body().ret_ticket_install_register);
            list = new ArrayList<>();
            int rowindex = data1.size()/field1.size();
            String data;
            for (int k = 0; k < data1.size()-1; k+=(field1.size()*rowindex)+1) {
                for (int i = k; i < field1.size() * rowindex; i++)
                {
                    data = (data1.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_1, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 1", e.toString());
        }
        try {
            data2.addAll(response.body().Retail_ad_blinkinglogo);
            list = new ArrayList<>();
            int rowindex = data1.size()/field1.size();
            String data;
            for (int k = 0; k < data2.size() - 1; k+=(field2.size()*rowindex)+1) {
                for (int i = k; i < field2.size() * rowindex; i++)
                {
                    data = (data2.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_2, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 2", e.toString());
        }
        try {
            list = new ArrayList<>();
            data3.addAll(response.body().Retail_ad_blinkinglogo_cont);
            String data;
            for (int k = 0; k < data3.size() - 1; k+=(field3.size()*500)+1) {
                for (int i = k; i < field3.size() * 500; i++)
                {
                    data = (data3.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_3, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 3", e.toString());
        }
        try {
            data4.addAll(response.body().Retail_ad_store_main);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data4.size() - 1; k+=(field4.size()*500)+1) {
                for (int i = k; i < field4.size() * 500; i++)
                {
                    data = (data4.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_4, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 4", e.toString());
        }
        try {
            data5.addAll(response.body().Retail_ad_store_main_cont);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data5.size() - 1; k+=(field5.size()*500)+1) {
                for (int i = k; i < field5.size() * 500; i++)
                {
                    data = (data5.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_5, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 5", e.toString());
        }
        try {
            data6.addAll(response.body().Retail_ad_ticker);
            list = new ArrayList<>();
            String data;
            int rowindex = data6.size()/field6.size();
            for (int k = 0; k < data6.size() - 1; k+=(field6.size()*rowindex)+1) {
                for (int i = k; i < field6.size() * rowindex; i++)
                {
                    data = (data6.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_6, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 6", e.toString());
        }
        try {
            data7.addAll(response.body().Retail_ad_ticker_cont);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data7.size() - 1; k+=(field7.size()*500)+1) {
                for (int i = k; i < field7.size() * 500; i++)
                {
                    data = (data7.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_7, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 7", e.toString());
        }
        try {
            data8.addAll(response.body().Retail_card_define);
            int rowindex = data8.size()/field8.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data8.size() - 1; k+=(field8.size()*rowindex)+1) {
                for (int i = k; i < field8.size() *rowindex; i++)
                {
                    data = (data8.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_8, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 8", e.toString());
        }
        try {
            data9.addAll(response.body().Retail_card_define_mfg);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data9.size() - 1; k+=(field9.size()*500)+1) {
                for (int i = k; i < field9.size() * 500; i++)
                {
                    data = (data9.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_9, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 9", e.toString());
        }
        try {
            data10.addAll(response.body().Retail_comp_btl);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data10.size() - 1; k+=(field10.size()*500)+1) {
                for (int i = k; i < field10.size() * 500; i++)
                {
                    data = (data10.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_10, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 10", e.toString());
        }
        try {
            data11.addAll(response.body().Retail_cust);
            int rowindex = data11.size()/field11.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data11.size() - 1; k+=(field11.size()*rowindex)+1) {
                for (int i = k; i < field11.size() * rowindex; i++)
                {
                    data = (data11.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_11, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 11", e.toString());
        }
        try {
            data12.addAll(response.body().Retail_cust_loyalty);
            list = new ArrayList<>();
            int rowindex = data12.size()/field12.size();
            String data;
            for (int k = 0; k < data12.size() - 1; k+=(field12.size()*rowindex)+1) {
                for (int i = k; i < field12.size() * rowindex; i++)
                {
                    data = (data12.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_12, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 12", e.toString());
        }
/* try {
     data13.addAll(response.body().Retail_inventory);
     String data;
     int k;
     int i ;
     // int z;
     int rem  = (data13.size()%500)*field13.size();
     for (k = 0; k < (data13.size()-rem) ;   k+=(field13.size()*500)) {
         list = new ArrayList<>();
         for (i = k; i <((field13.size() * 500)+k); i++)
         {
             data = (data13.get(i).toString());
             list.add(data);
         }
         insert(login.this, list, field13, tablename_13, 0);
         list.clear();
         // z=k;
     }
     int rowindex = (data13.size()/field13.size())%500;
     int l = k;
     ArrayList list1 = new ArrayList<>();
     for (int j=l; j < data13.size()-1; j+=(field13.size()*rowindex)) {
         for (int w = j; w < data13.size(); w++)
         {
             data = (data13.get(w).toString());
             list1.add(data);
         }
         insert(login.this, list1, field13, tablename_13, 0);
     }
 } catch (Exception e) {
     Log.e("Exception in table 13", e.toString());
 }*/
        try {
            data14.addAll(response.body().Retail_media);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data14.size() - 1; k+=(field14.size()*500)+1) {
                for (int i = k; i < field14.size() * 500; i++)
                {
                    data = (data14.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_14, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 14", e.toString());
        }
        try {
            data15.addAll(response.body().Retail_mfg_btl);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data15.size() - 1; k+=(field15.size()*500)+1) {
                for (int i = k; i < field15.size() * 500; i++)
                {
                    data = (data15.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_15, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 15", e.toString());
        }
        try {
            data16.addAll(response.body().Retail_Store);
            list = new ArrayList<>();
            int rowindex = data16.size()/field16.size();
            String size = String.valueOf(field16.size());
            String sizee = String.valueOf(data16.size());
            Log.d("&&&&&&&&",size + "  " + sizee);
            String data;
            for (int k = 0; k < data16.size()-1; k+=(field16.size()*rowindex)+1) {
                for (int i = k; i < field16.size() * rowindex; i++) {
                    data = (data16.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_16, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 16", e.toString());
        }
        try {
            data17.addAll(response.body().Retail_store_maint);
            int rowindex = data17.size()/field17.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data17.size() - 1; k+=(field17.size()*rowindex)+1) {
                for (int i = k; i < field17.size() * rowindex; i++)
                {
                    data = (data17.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_17, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 17", e.toString());
        }
        try {
            data18.addAll(response.body().Retail_store_prod);
            String data;
            int k;
            int i ;
            // int z;
            int rem  = data18.size()/(500*field18.size());
            for (k = 0; k < (rem*(field18.size())*500) ;   k+=(field18.size()*500)) {
                list = new ArrayList<>();
                for (i = k; i <((field18.size() * 500)+k); i++)
                {
                    data = (data18.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_18, 0);
                list.clear();
                // z=k;
            }
            int rowindex = (data18.size()/field18.size())%500;
            int l = k;
            ArrayList list1 = new ArrayList<>();
            for (int j=l; j < data18.size()-1; j+=(field18.size()*rowindex)) {
                for (int w = j; w < data18.size(); w++)
                {
                    data = (data18.get(w).toString());
                    list1.add(data);
                }
                insert(login.this, list1, tablename_18, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 18", e.toString());
        }
        try {
            data19.addAll(response.body().Retail_store_prod_local_cpg);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data19.size() - 1; k+=(field19.size()*500)+1) {
                for (int i = k; i < field19.size() * 500; i++)
                {
                    data = (data19.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_19, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 19", e.toString());
        }
        try {
            data20.addAll(response.body().retail_store_reports);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data20.size() - 1; k+=(field20.size()*500)+1) {
                for (int i = k; i < field20.size() * 500; i++)
                {
                    data = (data20.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_20, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 20", e.toString());
        }
        try {
            data21.addAll(response.body().retail_store_vend_reject);
            int rowindex = data21.size()/field21.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data21.size() - 1; k+=(field21.size()*rowindex)+1) {
                for (int i = k; i < field21.size() * rowindex; i++)
                {
                    data = (data21.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_21, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 21", e.toString());
        }
        try {
            data22.addAll(response.body().retail_store_vendor);
            int rowindex = data22.size()/field22.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data22.size() - 1; k+=(field22.size()*rowindex)+1) {
                for (int i = k; i < field22.size() * rowindex; i++)
                {
                    data = (data22.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_22, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 22", e.toString());
        }
        try {
            data23.addAll(response.body().Retail_str_dstr);
            int rowindex = data23.size()/field23.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data23.size() - 1; k+=(field23.size()*rowindex)+1) {
                for (int i = k; i < field23.size() * rowindex; i++)
                {
                    data = (data23.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_23, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 23", e.toString());
        }
        try {
            data24.addAll(response.body().retail_str_grn_detail);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data24.size() - 1; k+=(field24.size()*500)+1) {
                for (int i = k; i < field24.size() * 500; i++)
                {
                    data = (data24.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_24, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 24", e.toString());
        }
        try {
            data25.addAll(response.body().retail_str_grn_master);
            int rowindex = data25.size()/field25.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data25.size() - 1; k+=(field25.size()*rowindex)+1) {
                for (int i = k; i < field25.size() * rowindex; i++)
                {
                    data = (data25.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_25, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 25", e.toString());
        }
        try {
            data26.addAll(response.body().retail_str_po_detail);
            int rowindex = data26.size()/field26.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data26.size() - 1; k+=(field26.size()*rowindex)+1) {
                for (int i = k; i < field26.size() *rowindex; i++)
                {
                    data = (data26.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_26, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 26", e.toString());
        }
        try {
            data27.addAll(response.body().retail_str_po_master);
            int rowindex = data27.size()/field27.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data27.size() - 1; k+=(field27.size()*rowindex)+1) {
                for (int i = k; i < field27.size() * rowindex; i++)
                {
                    data = (data27.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_27, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 27", e.toString());
        }
        try {
            data28.addAll(response.body().retail_str_sales_detail);
            int rowindex = data28.size()/field28.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data28.size() - 1; k+=(field28.size()*rowindex)+1) {
                for (int i = k; i < field28.size() * rowindex; i++)
                {
                    data = (data28.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_28, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 28", e.toString());
        }
        try {
            data29.addAll(response.body().retail_str_sales_details_return);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data29.size() - 1; k+=(field29.size()*500)+1) {
                for (int i = k; i < field29.size() * 500; i++)
                {
                    data = (data29.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_29, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 29", e.toString());
        }
        try {
            data30.addAll(response.body().retail_str_sales_master_return);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data30.size() - 1; k+=(field30.size()*500)+1) {
                for (int i = k; i < field30.size() * 500; i++)
                {
                    data = (data30.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_30, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 30", e.toString());
        }
        try {
            data31.addAll(response.body().retail_str_sales_master);
            int rowindex = data31.size()/field31.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data31.size() - 1; k+=(field31.size()*rowindex)+1) {
                for (int i = k; i < field31.size() * rowindex; i++)
                {
                    data = (data31.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_31, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 31", e.toString());
        }
        try {
            data32.addAll(response.body().retail_str_vendor_detail_return);
            int rowindex = data32.size()/field32.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data32.size() - 1; k+=(field32.size()*rowindex)+1) {
                for (int i = k; i < field32.size() * rowindex; i++)
                {
                    data = (data32.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_32, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 32", e.toString());
        }
        try {
            data33.addAll(response.body().retail_str_vendor_master_return);
            int rowindex = data33.size()/field33.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data33.size() - 1; k+=(field33.size()*rowindex)+1) {
                for (int i = k; i < field33.size() * rowindex; i++)
                {
                    data = (data33.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_33, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 33", e.toString());
        }
        try {
            data34.addAll(response.body().retail_tax);
            int rowindex = data34.size()/field34.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data34.size() - 1; k+=(field34.size()*rowindex)+1) {
                for (int i = k; i < field34.size() * rowindex; i++)
                {
                    data = (data34.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_34, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 34", e.toString());
        }
        try {
            data35.addAll(response.body().Retail_top_product);
            int rowindex = data35.size()/field35.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data35.size() - 1; k+=(field35.size()*rowindex)+1) {
                for (int i = k; i < field35.size() * rowindex; i++)
                {
                    data = (data35.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_35, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 35", e.toString());
        }
        try {
            data36.addAll(response.body().Retail_vend_dstr);
            int rowindex = data36.size()/field36.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data36.size() - 1; k+=(field36.size()*rowindex)+1) {
                for (int i = k; i < field36.size() * rowindex; i++)
                {
                    data = (data36.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_36, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 36", e.toString());
        }
        try {
            data40.addAll(response.body().Rule_defination);
            int rowindex = data40.size()/field40.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data40.size() - 1; k+=(field40.size()*rowindex)+1) {
                for (int i = k; i < field40.size() * rowindex; i++)
                {
                    data = (data40.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_40, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 40", e.toString());
        }
        try {
            data41.addAll(response.body().Tmp_retail_pay_desc);
            int rowindex = data41.size()/field41.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data41.size() - 1; k+=(field41.size()*rowindex)+1) {
                for (int i = k; i < field41.size() * rowindex; i++)
                {
                    data = (data41.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_41, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 41", e.toString());
        }
        try {
            data42.addAll(response.body().ad_ticker_main);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data42.size() - 1; k+=(field42.size()*500)+1) {
                for (int i = k; i < field42.size() * 500; i++)
                {
                    data = (data42.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_42, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 42", e.toString());
        }
        try {
            data43.addAll(response.body().bank_details);
            int rowindex = data43.size()/field43.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data43.size() - 1; k+=(field43.size()*rowindex)+1) {
                for (int i = k; i < field43.size() * rowindex; i++)
                {
                    data = (data43.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_43, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 43", e.toString());
        }
        try {
            data44.addAll(response.body().Retail_store_prod_com);
            String data;
            int k;
            int i ;
            // int z;
            int rem  = data44.size()/(500*field44.size());
            for (k = 0; k < (rem*(field44.size())*500) ;   k+=(field44.size()*500)) {
                list = new ArrayList<>();
                for (i = k; i <((field44.size() * 500)+k); i++)
                {
                    data = (data44.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_44, 0);
                list.clear();
                // z=k;
            }
            int rowindex = (data44.size()/field44.size())%500;
            int l = k;
            ArrayList list1 = new ArrayList<>();
            for (int j=l; j < data44.size()-1; j+=(field44.size()*rowindex)) {
                for (int w = j; w < data44.size(); w++)
                {
                    data = (data44.get(w).toString());
                    list1.add(data);
                }
                insert(login.this, list1, tablename_44, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 44", e.toString());
        }
        try {
            data45.addAll(response.body().retail_store_prod_cpg);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data45.size() - 1; k+=(field45.size()*500)+1) {
                for (int i = k; i < field45.size() * 500; i++)
                {
                    data = (data45.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_45, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 45", e.toString());
        }
        try {
            data46.addAll(response.body().Retail_store_prod_local);
            int rowindex = data46.size()/field46.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data46.size() - 1; k+=(field46.size()*rowindex)+1) {
                for (int i = k; i < field46.size() * rowindex; i++)
                {
                    data = (data46.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_46, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 46", e.toString());
        }
        try {
            data47.addAll(response.body().Retail_str_po_detail_hold);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data47.size() - 1; k+=(field47.size()*500)+1) {
                for (int i = k; i < field47.size() * 500; i++)
                {
                    data = (data47.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_47, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 47", e.toString());
        }
        try {
            data48.addAll(response.body().retail_str_stock_master);
            String data;
            int k;
            int i ;
            // int z;
            int rem  = data48.size()/(500*field48.size());
            for (k = 0; k < (rem*(field48.size())*500) ;   k+=(field48.size()*500)) {
                list = new ArrayList<>();
                for (i = k; i <((field48.size() * 500)+k); i++)
                {
                    data = (data48.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_48, 0);
                list.clear();
                // z=k;
            }
            int rowindex = (data48.size()/field48.size())%500;
            int l = k;
            ArrayList list1 = new ArrayList<>();
            for (int j=l; j < data48.size()-1; j+=(field48.size()*rowindex)) {
                for (int w = j; w < data48.size(); w++)
                {
                    data = (data48.get(w).toString());
                    list1.add(data);
                }
                insert(login.this, list1, tablename_48, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 48", e.toString());
        }
        try {
            data49.addAll(response.body().Retail_str_stock_master_hold);
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data49.size() - 1; k+=(field49.size()*500)+1) {
                for (int i = k; i < field49.size() * 500; i++)
                {
                    data = (data49.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_49, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 49", e.toString());
        }
        try {
            data50.addAll(response.body().Retail_str_day_open_close);
            int rowindex = data50.size()/field50.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data50.size() - 1; k+=(field50.size()*rowindex)+1) {
                for (int i = k; i < field50.size() * rowindex; i++)
                {
                    data = (data50.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_50, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 50", e.toString());
        }
        try {
            data51.addAll(response.body().Retail_store_sales_desc);
            int rowindex = data51.size()/field51.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data51.size() - 1; k+=(field51.size()*rowindex)+1) {
                for (int i = k; i < field51.size() * rowindex; i++)
                {
                    data = (data51.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_51, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 51", e.toString());
        }
        try {
            data52.addAll(response.body().Retail_employees);
            int rowindex = data52.size()/field52.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data52.size() - 1; k+=(field52.size()*rowindex)+1) {
                for (int i = k; i < field52.size() * rowindex; i++)
                {
                    data = (data52.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_52, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 52", e.toString());
        }
        try {
            data53.addAll(response.body().Tmp_retail_str_sales_detail);
            int rowindex = data53.size()/field53.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data53.size() - 1; k+=(field53.size()*rowindex)+1) {
                for (int i = k; i < field53.size() * rowindex; i++)
                {
                    data = (data53.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_53, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 53", e.toString());
        }
        try {
            data54.addAll(response.body().Tmp_retail_str_sales_master);
            int rowindex = data54.size()/field54.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data54.size() - 1; k+=(field54.size()*rowindex)+1) {
                for (int i = k; i < field54.size() * rowindex; i++)
                {
                    data = (data54.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_54, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 54", e.toString());
        }
        try {
            data55.addAll(response.body().dr_speciality);
            int rowindex = data55.size()/field55.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data55.size() - 1; k+=(field55.size()*rowindex)+1) {
                for (int i = k; i < field55.size() * rowindex; i++)
                {
                    data = (data55.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_55, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 55", e.toString());
        }
        try {
            data56.addAll(response.body().Dr_discription);
            String data;
            int k;
            int i ;
            // int z;
            int rem  = (data56.size()%500)*field56.size();
            for (k = 0; k < (data56.size()-rem) ;   k+=(field56.size()*500)) {
                list = new ArrayList<>();
                for (i = k; i <((field56.size() * 500)+k); i++)
                {
                    data = (data56.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_56, 0);
                list.clear();
                // z=k;
            }
            int rowindex = (data56.size()/field56.size())%500;
            int l = k;
            ArrayList list1 = new ArrayList<>();
            for (int j=l; j < data56.size()-1; j+=(field56.size()*rowindex)) {
                for (int w = j; w < data56.size(); w++)
                {
                    data = (data56.get(w).toString());
                    list1.add(data);
                }
                insert(login.this, list1, tablename_56, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 56", e.toString());
        }
        try {
            data57.addAll(response.body().Retail_send_mail_pdf);
            int rowindex = data57.size()/field57.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data57.size() - 1; k+=(field57.size()*rowindex)+1) {
                for (int i = k; i < field57.size() * rowindex; i++)
                {
                    data = (data57.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_57, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 57", e.toString());
        }
        try {
            data58.addAll(response.body().Retail_str_lineitem_disc);
            int rowindex = data58.size()/field58.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data58.size() - 1; k+=(field58.size()*rowindex)+1) {
                for (int i = k; i < field58.size() * rowindex; i++)
                {
                    data = (data58.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_58, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 58", e.toString());
        }
        try {
            data59.addAll(response.body().Ad_main);
            int rowindex = data59.size()/field59.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data59.size() - 1; k+=(field59.size()*rowindex)+1) {
                for (int i = k; i < field59.size() * rowindex; i++)
                {
                    data = (data59.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_59, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 59", e.toString());
        }
        try {
            data60.addAll(response.body().Retail_media_click);
            int rowindex = data60.size()/field60.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data60.size() - 1; k+=(field60.size()*rowindex)+1) {
                for (int i = k; i < field55.size() * rowindex; i++)
                {
                    data = (data60.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_60, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 60", e.toString());
        }
        try {
            data61.addAll(response.body().Tmp_vend_dstr_payment);
            int rowindex = data61.size()/field61.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data61.size() - 1; k+=(field61.size()*rowindex)+1) {
                for (int i = k; i < field61.size() * rowindex; i++)
                {
                    data = (data61.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_61, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 61", e.toString());
        }
        try {
            data62.addAll(response.body().Retail_str_bill_lvl_disc);
            int rowindex = data62.size()/field62.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data62.size() - 1; k+=(field62.size()*rowindex)+1) {
                for (int i = k; i < field62.size() * rowindex; i++)
                {
                    data = (data62.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_62, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 62", e.toString());
        }
        try {
            data63.addAll(response.body().Retail_store_cust_reject);
            int rowindex = data63.size()/field63.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data63.size() - 1; k+=(field63.size()*rowindex)+1) {
                for (int i = k; i < field63.size() * rowindex; i++)
                {
                    data = (data63.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_63, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 63", e.toString());
        }
        try {
            data64.addAll(response.body().Retail_credit_cust);
            int rowindex = data64.size()/field64.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data64.size() - 1; k+=(field64.size()*rowindex)+1) {
                for (int i = k; i < field64.size() * rowindex; i++)
                {
                    data = (data64.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,tablename_64, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 64", e.toString());
        }
        try {
            data81.addAll(response.body().Retail_store_decimal);
            int rowindex = data81.size()/field81.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data81.size() - 1; k+=(field81.size()*rowindex)+1) {
                for (int i = k; i < field81.size() * rowindex; i++)
                {
                    data = (data81.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_81, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 81", e.toString());
        }
        try {
            data82.addAll(response.body().retail_bill_display);
            int rowindex = data82.size()/field82.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data82.size() - 1; k+=(field82.size()*rowindex)+1) {
                for (int i = k; i < field82.size() * rowindex; i++)
                {
                    data = (data82.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_82, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 82", e.toString());
        }
        try {
            data83.addAll(response.body().retail_bill_visible);
            int rowindex = data83.size()/field83.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data83.size() - 1; k+=(field83.size()*rowindex)+1) {
                for (int i = k; i < field83.size() * rowindex; i++)
                {
                    data = (data83.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list,  tablename_83, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 83", e.toString());
        }
        try {
            data84.addAll(response.body().retail_credit_bill_details);
            int rowindex = data84.size()/field84.size();
            list = new ArrayList<>();
            String data;
            for (int k = 0; k < data84.size() - 1; k+=(field84.size()*rowindex)+1) {
                for (int i = k; i < field84.size() * rowindex; i++)
                {
                    data = (data84.get(i).toString());
                    list.add(data);
                }
                insert(login.this, list, tablename_84, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 84", e.toString());
        }

        loading.dismiss();
       /* Intent i = new Intent(getApplicationContext(),Activity_masterScreen1.class);
        startActivity(i);*/
    }
    int insertion_index = 0;
    public void insert(Context context, ArrayList<String> array_vals, String TABLE_NAME, int row_index) {
        Log.d("Inside Insert", "Insertion starts for table name: " + TABLE_NAME);
        String querryString;
        File db_path = new File("/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/Db");
        db_path.getParentFile().mkdirs();
        myDataBase = SQLiteDatabase.openOrCreateDatabase(db_path, null);          //Opens database in writable mode.
        myDataBase.execSQL("PRAGMA key='Anaconda'");
        //Opens database in writable mode.
        String markString = array_vals.toString().replace("[","").replace("]","");
        Log.d("**createDynamicDatabase", "in oncreate");
        INSERT = "INSERT OR REPLACE INTO " + TABLE_NAME  + " values " + markString ;
        String update_S_Flag = "UPDATE " + TABLE_NAME + " set S_FLAG = '1';";
        insertion_index++;
        // Log.d("Insert called", String.valueOf(insertion_index));
        // System.out.println("Insert statement: " + INSERT);
        this.insertStmt = this.myDataBase.compileStatement(INSERT);
        insertStmt.executeInsert();
        this.updateStmt = this.myDataBase.compileStatement(update_S_Flag);
        updateStmt.executeUpdateDelete();
        myDataBase.close();
        // Log.d("Insert called", String.valueOf(insertion_index));
        // System.out.println("Insert statement: " + INSERT);
        int flagger = 1;
    }
    @Override
    public void onFailure(Call<ConfigItems> call, Throwable t) {
    }

    /**
     * Callback will be triggered when there is change in
     * network connection
     */
}

