package com.RSPL.POS;
import android.content.DialogInterface;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteStatement;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import Config.ConfigItems;
import Config.Config;
import JSON.JSONParser;
import JSON.JSONParserSync;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Installation extends Activity implements OnClickListener, Callback<ConfigItems> {
    public EditText user, pass;
    String store_id,password;
    private Button bLogin;
    // Progress Dialog
    private ProgressDialog pDialog;
    //rahul created this file
    // JSON parser class


    //rajeev diwan
    private static final String
            LOGIN_URL = "http://52.76.28.14/Android/arun.php";
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    DBhelper db;
    ActionBar actionBar;
    int size;

    //    ProgressDialog progressBar;
//    private int progressBarStatus = 0;
//    private Handler progressBarHandler = new Handler();
//    private long fileSize = 0;
    private ArrayList list ;

    private ArrayList<String> field1;
    private ArrayList<String> field2;
    private ArrayList<String> field3;
    private ArrayList<String> field4;
    private ArrayList<String> field5;
    private ArrayList<String> field6;
    private ArrayList<String> field7;
    private ArrayList<String> field8;
    private ArrayList<String> field9;

    private ArrayList<String> field10;
    private ArrayList<String> field11;
    private ArrayList<String> field12;
    private ArrayList<String> field13;
    private ArrayList<String> field14;
    private ArrayList<String> field15;
    private ArrayList<String> field16;
    private ArrayList<String> field17;
    private ArrayList<String> field18;
    private ArrayList<String> field19;
    private ArrayList<String> field20;
    private ArrayList<String> field21;
    private ArrayList<String> field22;
    private ArrayList<String> field23;
    private ArrayList<String> field24;
    private ArrayList<String> field25;
    private ArrayList<String> field26;
    private ArrayList<String> field27;
    private ArrayList<String> field28;
    private ArrayList<String> field29;
    private ArrayList<String> field30;
    private ArrayList<String> field31;
    private ArrayList<String> field32;
    private ArrayList<String> field33;
    private ArrayList<String> field34;
    private ArrayList<String> field35;
    private ArrayList<String> field36;
    private ArrayList<String> field37;
    private ArrayList<String> field38;
    private ArrayList<String> field39;
    private ArrayList<String> field40;
    private ArrayList<String> field41;
    private ArrayList<String> field42;
    private ArrayList<String> field43;
    private ArrayList<String> field44;
    private ArrayList<String> field45;
    private ArrayList<String> field46;
    private ArrayList<String> field47;
    private ArrayList<String> field48;
    private ArrayList<String> field49;
    private ArrayList<String> field50;
    private ArrayList<String> field51;
    private ArrayList<String> field52;
    private ArrayList<String> field53;
    private ArrayList<String> field54;
    private ArrayList<String> field55;
    private ArrayList<String> field56;
    private ArrayList<String> field57;
    private ArrayList<String> field58;
    private ArrayList<String> field59;
    private ArrayList<String> field60;
    private ArrayList<String> field61;
    private ArrayList<String> field62;
    private ArrayList<String> field63;
    private ArrayList<String> field64;
    private ArrayList<String> field65;
    private ArrayList<String> field66;
    private ArrayList<String> field67;
    private ArrayList<String> field68;
    private ArrayList<String> field69;
    private ArrayList<String> field70;
    private ArrayList<String> field71;
    private ArrayList<String> field72;
    private ArrayList<String> field73;
    private ArrayList<String> field74;
    private ArrayList<String> field75;
    private ArrayList<String> field76;
    private ArrayList<String> field77;
    private ArrayList<String> field78;
    private ArrayList<String> field79;
    private ArrayList<String> field80;
    private ArrayList<String> field81;
    private ArrayList<String>field82;
    private ArrayList<String>field83;
    private ArrayList<String> field84;
    private ArrayList<String> field85;



    private ArrayList<String> constraint1;
    private ArrayList<String> constraint2;
    private ArrayList<String> constraint3;
    private ArrayList<String> constraint4;
    private ArrayList<String> constraint5;
    private ArrayList<String> constraint6;
    private ArrayList<String> constraint7;
    private ArrayList<String> constraint8;
    private ArrayList<String> constraint9;
    private ArrayList<String> constraint10;
    private ArrayList<String> constraint11;
    private ArrayList<String> constraint12;
    private ArrayList<String> constraint13;
    private ArrayList<String> constraint14;
    private ArrayList<String> constraint15;
    private ArrayList<String> constraint16;
    private ArrayList<String> constraint17;
    private ArrayList<String> constraint18;
    private ArrayList<String> constraint19;
    private ArrayList<String> constraint20;
    private ArrayList<String> constraint21;
    private ArrayList<String> constraint22;
    private ArrayList<String> constraint23;
    private ArrayList<String> constraint24;
    private ArrayList<String> constraint25;
    private ArrayList<String> constraint26;
    private ArrayList<String> constraint27;
    private ArrayList<String> constraint28;
    private ArrayList<String> constraint29;
    private ArrayList<String> constraint30;
    private ArrayList<String> constraint31;
    private ArrayList<String> constraint32;
    private ArrayList<String> constraint33;
    private ArrayList<String> constraint34;
    private ArrayList<String> constraint35;
    private ArrayList<String> constraint36;
    private ArrayList<String> constraint37;
    private ArrayList<String> constraint38;
    private ArrayList<String> constraint39;
    private ArrayList<String> constraint40;
    private ArrayList<String> constraint41;
    private ArrayList<String> constraint42;
    private ArrayList<String> constraint43;
    private ArrayList<String> constraint44;
    private ArrayList<String> constraint45;
    private ArrayList<String> constraint46;
    private ArrayList<String> constraint47;
    private ArrayList<String> constraint48;
    private ArrayList<String> constraint49;
    private ArrayList<String> constraint50;
    private ArrayList<String> constraint51;
    private ArrayList<String> constraint52;
    private ArrayList<String> constraint53;
    private ArrayList<String> constraint54;
    private ArrayList<String> constraint55;
    private ArrayList<String> constraint56;
    private ArrayList<String> constraint57;
    private ArrayList<String> constraint58;
    private ArrayList<String> constraint59;
    private ArrayList<String> constraint60;
    private ArrayList<String> constraint61;
    private ArrayList<String> constraint62;
    private ArrayList<String> constraint63;
    private ArrayList<String> constraint64;
    private ArrayList<String> constraint65;
    private ArrayList<String> constraint66;
    private ArrayList<String> constraint67;
    private ArrayList<String> constraint68;
    private ArrayList<String> constraint69;
    private ArrayList<String> constraint70;
    private ArrayList<String> constraint71;
    private ArrayList<String> constraint72;
    private ArrayList<String> constraint73;
    private ArrayList<String> constraint74;
    private ArrayList<String> constraint75;
    private ArrayList<String> constraint76;
    private ArrayList<String> constraint77;
    private ArrayList<String> constraint78;
    private ArrayList<String> constraint79;
    private ArrayList<String> constraint80;
    private ArrayList<String> constraint81;
    private ArrayList<String>constraint82;
    private ArrayList<String>constraint83;
    private ArrayList<String> constraint84;
    private ArrayList<String> constraint85;




    private ArrayList<Config> data1;
    private ArrayList<Config> data2;
    private ArrayList<Config> data3;
    private ArrayList<Config> data4;
    private ArrayList<Config> data5;
    private ArrayList<Config> data6;
    private ArrayList<Config> data7;
    private ArrayList<Config> data8;
    private ArrayList<Config> data9;

    private ArrayList<Config> data10;
    private ArrayList<Config> data11;
    private ArrayList<Config> data12;
    private ArrayList<Config> data13;
    private ArrayList<Config> data14;
    private ArrayList<Config> data15;
    private ArrayList<Config> data16;
    private ArrayList<Config> data17;
    private ArrayList<Config> data18;
    private ArrayList<Config> data19;
    private ArrayList<Config> data20;
    private ArrayList<Config> data21;
    private ArrayList<Config> data22;
    private ArrayList<Config> data23;
    private ArrayList<Config> data24;
    private ArrayList<Config> data25;
    private ArrayList<Config> data26;
    private ArrayList<Config> data27;
    private ArrayList<Config> data28;
    private ArrayList<Config> data29;
    private ArrayList<Config> data30;
    private ArrayList<Config> data31;
    private ArrayList<Config> data32;
    private ArrayList<Config> data33;
    private ArrayList<Config> data34;
    private ArrayList<Config> data35;
    private ArrayList<Config> data36;
    private ArrayList<Config> data37;
    private ArrayList<Config> data38;
    private ArrayList<Config> data39;
    private ArrayList<Config> data40;
    private ArrayList<Config> data41;
    private ArrayList<Config> data42;
    private ArrayList<Config> data43;
    private ArrayList<Config> data44;
    private ArrayList<Config> data45;
    private ArrayList<Config> data46;
    private ArrayList<Config> data47;
    private ArrayList<Config> data48;
    private ArrayList<Config> data49;
    private ArrayList<Config> data50;
    private ArrayList<Config> data51;
    private ArrayList<Config> data52;
    private ArrayList<Config> data53;
    private ArrayList<Config> data54;
    private ArrayList<Config> data55;
    private ArrayList<Config> data56;
    private ArrayList<Config> data57;
    private ArrayList<Config> data58;
    private ArrayList<Config> data59;
    private ArrayList<Config> data60;
    private ArrayList<Config> data61;
    private ArrayList<Config> data62;
    private ArrayList<Config> data63;
    private ArrayList<Config> data64;

    private ArrayList<Config> data65;
    private ArrayList<Config> data66;
    private ArrayList<Config> data67;
    private ArrayList<Config> data68;
    private ArrayList<Config> data69;
    private ArrayList<Config> data70;
    private ArrayList<Config> data71;
    private ArrayList<Config> data72;
    private ArrayList<Config> data73;
    private ArrayList<Config> data74;
    private ArrayList<Config> data75;
    private ArrayList<Config> data76;
    private ArrayList<Config> data77;
    private ArrayList<Config> data78;
    private ArrayList<Config> data79;
    private ArrayList<Config> data80;
    private ArrayList<Config> data81;
    private ArrayList<Config>data82;
    private ArrayList<Config>data83;
    private ArrayList<Config>data84;




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
    String tablename_85 = "retail_str_lost_sales";




    SQLiteDatabase myDataBase;
    SQLiteStatement insertStmt;
    SQLiteStatement updateStmt;

    String INSERT;
    Button submit;

    ProgressDialog loading;

    String id, JSON_STRING;
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");



        try {


            DBhelper db = new DBhelper(Activity_Installation.this);


            Boolean fortrue = db.checkDataBase();
            if (fortrue == true) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                return;
            } else {
                setContentView(R.layout.activity_activity__installation);
                actionBar = getActionBar();
                actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setDisplayUseLogoEnabled(true);
                actionBar.setIcon(R.drawable.w);
                user = (EditText) findViewById(R.id.username);
                pass = (EditText) findViewById(R.id.password);
                bLogin = (Button) findViewById(R.id.login);




                bLogin.setOnClickListener(this);

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
                field85=new ArrayList<>();







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
                constraint1 = new ArrayList<>();
                constraint2 = new ArrayList<>();
                constraint3 = new ArrayList<>();
                constraint4 = new ArrayList<>();
                constraint5 = new ArrayList<>();
                constraint6 = new ArrayList<>();
                constraint7 = new ArrayList<>();
                constraint8 = new ArrayList<>();
                constraint9 = new ArrayList<>();
                constraint10 = new ArrayList<>();
                constraint11 = new ArrayList<>();
                constraint12 = new ArrayList<>();
                constraint13 = new ArrayList<>();
                constraint14 = new ArrayList<>();
                constraint15 = new ArrayList<>();
                constraint16 = new ArrayList<>();
                constraint17 = new ArrayList<>();
                constraint18 = new ArrayList<>();
                constraint19 = new ArrayList<>();
                constraint20 = new ArrayList<>();
                constraint21 = new ArrayList<>();
                constraint22 = new ArrayList<>();
                constraint23 = new ArrayList<>();
                constraint24 = new ArrayList<>();
                constraint25 = new ArrayList<>();
                constraint26 = new ArrayList<>();
                constraint27 = new ArrayList<>();
                constraint28 = new ArrayList<>();
                constraint29 = new ArrayList<>();
                constraint30 = new ArrayList<>();
                constraint31 = new ArrayList<>();
                constraint32 = new ArrayList<>();
                constraint33 = new ArrayList<>();
                constraint34 = new ArrayList<>();
                constraint35 = new ArrayList<>();
                constraint36 = new ArrayList<>();
                constraint37 = new ArrayList<>();
                constraint38 = new ArrayList<>();
                constraint39 = new ArrayList<>();
                constraint40 = new ArrayList<>();
                constraint41 = new ArrayList<>();
                constraint42 = new ArrayList<>();
                constraint43 = new ArrayList<>();
                constraint44 = new ArrayList<>();
                constraint45 = new ArrayList<>();
                constraint46 = new ArrayList<>();
                constraint47 = new ArrayList<>();
                constraint48 = new ArrayList<>();
                constraint49 = new ArrayList<>();
                constraint50 = new ArrayList<>();
                constraint51 = new ArrayList<>();
                constraint52 = new ArrayList<>();
                constraint53 = new ArrayList<>();
                constraint54 = new ArrayList<>();
                constraint55 = new ArrayList<>();
                constraint56 = new ArrayList<>();
                constraint57 = new ArrayList<>();
                constraint58 = new ArrayList<>();
                constraint59 = new ArrayList<>();
                constraint60 = new ArrayList<>();
                constraint61 = new ArrayList<>();
                constraint62 = new ArrayList<>();
                constraint63 = new ArrayList<>();
                constraint64 = new ArrayList<>();
                constraint65 = new ArrayList<>();
                constraint66 = new ArrayList<>();
                constraint67 = new ArrayList<>();
                constraint68 = new ArrayList<>();
                constraint69 = new ArrayList<>();
                constraint70=new ArrayList<>();
                constraint71=new ArrayList<>();
                constraint72=new ArrayList<>();
                constraint73=new ArrayList<>();
                constraint74=new ArrayList<>();
                constraint75=new ArrayList<>();
                constraint76=new ArrayList<>();
                constraint77=new ArrayList<>();
                constraint78=new ArrayList<>();
                constraint79=new ArrayList<>();
                constraint80=new ArrayList<>();
                constraint81=new ArrayList<>();
                constraint82=new ArrayList<>();
                constraint83=new ArrayList<>();
                constraint84=new ArrayList<>();
                constraint85=new ArrayList<>();








                getJSON();

                //   addListenerOnButtonClick();
//        SharedPreferences pref = getSharedPreferences("Activity_Installation", Context.MODE_PRIVATE);
//        if(pref.getBoolean("activity_executed", false)){
//            Intent intent = new Intent(this, GMainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            SharedPreferences.Editor ed = pref.edit();
//            ed.putBoolean("activity_executed", true);
//            ed.commit();
//        }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                insert(Activity_Installation.this, list, field1, tablename_1, 0);



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

                insert(Activity_Installation.this, list, field2, tablename_2, 0);
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

                insert(Activity_Installation.this, list, field3, tablename_3, 0);
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

                insert(Activity_Installation.this, list, field4, tablename_4, 0);
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

                insert(Activity_Installation.this, list, field5, tablename_5, 0);
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

                insert(Activity_Installation.this, list, field6, tablename_6, 0);
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

                insert(Activity_Installation.this, list, field7, tablename_7, 0);
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

                insert(Activity_Installation.this, list, field8, tablename_8, 0);
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

                insert(Activity_Installation.this, list, field9, tablename_9, 0);
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

                insert(Activity_Installation.this, list, field10, tablename_10, 0);
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

                insert(Activity_Installation.this, list, field11, tablename_11, 0);
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

                insert(Activity_Installation.this, list, field12, tablename_12, 0);
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

                insert(Activity_Installation.this, list, field13, tablename_13, 0);
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

                insert(Activity_Installation.this, list1, field13, tablename_13, 0);
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

                insert(Activity_Installation.this, list, field14, tablename_14, 0);
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

                insert(Activity_Installation.this, list, field15, tablename_15, 0);
            }
        } catch (Exception e) {
            Log.e("Exception in table 15", e.toString());
        }

        try {


            data16.addAll(response.body().Retail_Store);
            int rowindex = data16.size()/field16.size();




            list = new ArrayList<>();
            String data;

            for (int k = 0; k < data16.size() - 1; k+=(field16.size()*rowindex)+1) {
                for (int i = k; i < field16.size() * rowindex; i++)
                {
                    data = (data16.get(i).toString());
                    list.add(data);
                }

                insert(Activity_Installation.this, list, field16, tablename_16, 0);
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

                insert(Activity_Installation.this, list, field17, tablename_17, 0);
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

                insert(Activity_Installation.this, list, field18, tablename_18, 0);
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

                insert(Activity_Installation.this, list1, field18, tablename_18, 0);
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

                insert(Activity_Installation.this, list, field19, tablename_19, 0);
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

                insert(Activity_Installation.this, list, field20, tablename_20, 0);
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

                insert(Activity_Installation.this, list, field21, tablename_21, 0);
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

                insert(Activity_Installation.this, list, field22, tablename_22, 0);
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

                insert(Activity_Installation.this, list, field23, tablename_23, 0);
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

                insert(Activity_Installation.this, list, field24, tablename_24, 0);
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

                insert(Activity_Installation.this, list, field25, tablename_25, 0);
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

                insert(Activity_Installation.this, list, field26, tablename_26, 0);
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

                insert(Activity_Installation.this, list, field27, tablename_27, 0);
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

                insert(Activity_Installation.this, list, field28, tablename_28, 0);
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

                insert(Activity_Installation.this, list, field29, tablename_29, 0);
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

                insert(Activity_Installation.this, list, field30, tablename_30, 0);
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

                insert(Activity_Installation.this, list, field31, tablename_31, 0);
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

                insert(Activity_Installation.this, list, field32, tablename_32, 0);
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

                insert(Activity_Installation.this, list, field33, tablename_33, 0);
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

                insert(Activity_Installation.this, list, field34, tablename_34, 0);
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

                insert(Activity_Installation.this, list, field35, tablename_35, 0);
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

                insert(Activity_Installation.this, list, field36, tablename_36, 0);
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

                insert(Activity_Installation.this, list, field40, tablename_40, 0);
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

                insert(Activity_Installation.this, list, field41, tablename_41, 0);
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

                insert(Activity_Installation.this, list, field42, tablename_42, 0);
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

                insert(Activity_Installation.this, list, field43, tablename_43, 0);
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

                insert(Activity_Installation.this, list, field44, tablename_44, 0);
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

                insert(Activity_Installation.this, list1, field44, tablename_44, 0);
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

                insert(Activity_Installation.this, list, field45, tablename_45, 0);
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

                insert(Activity_Installation.this, list, field46, tablename_46, 0);
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

                insert(Activity_Installation.this, list, field47, tablename_47, 0);
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
                insert(Activity_Installation.this, list, field48, tablename_48, 0);
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
                insert(Activity_Installation.this, list1, field48, tablename_48, 0);
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

                insert(Activity_Installation.this, list, field49, tablename_49, 0);
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

                insert(Activity_Installation.this, list, field50, tablename_50, 0);
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

                insert(Activity_Installation.this, list, field51, tablename_51, 0);
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

                insert(Activity_Installation.this, list, field52, tablename_52, 0);
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

                insert(Activity_Installation.this, list, field53, tablename_53, 0);
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

                insert(Activity_Installation.this, list, field54, tablename_54, 0);
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

                insert(Activity_Installation.this, list, field55, tablename_55, 0);
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

                insert(Activity_Installation.this, list, field56, tablename_56, 0);
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

                insert(Activity_Installation.this, list1, field56, tablename_56, 0);
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

                insert(Activity_Installation.this, list, field57, tablename_57, 0);
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

                insert(Activity_Installation.this, list, field58, tablename_58, 0);
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

                insert(Activity_Installation.this, list, field59, tablename_59, 0);
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

                insert(Activity_Installation.this, list, field60, tablename_60, 0);
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

                insert(Activity_Installation.this, list, field61, tablename_61, 0);
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

                insert(Activity_Installation.this, list, field62, tablename_62, 0);
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

                insert(Activity_Installation.this, list, field63, tablename_63, 0);
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

                insert(Activity_Installation.this, list, field64, tablename_64, 0);
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

                insert(Activity_Installation.this, list, field81, tablename_81, 0);
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

                insert(Activity_Installation.this, list, field82, tablename_82, 0);
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

                insert(Activity_Installation.this, list, field83, tablename_83, 0);
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

                insert(Activity_Installation.this, list, field84, tablename_84, 0);
            }

        } catch (Exception e) {
            Log.e("Exception in table 84", e.toString());
        }

        loading.dismiss();
        Intent intent = new Intent(getApplicationContext(),login.class);
        startActivity(intent);

    }

    @Override
    public void onFailure(Call<ConfigItems> call, Throwable t) {
        // Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_Installation.class);
        startActivity(intent);

    }
    public void jsonparsed(String input) {
        String constraint;
        try {
            //storeId = storeID.getText().toString();
            JSONObject jsonaman = new JSONObject(input);
            JSONArray firsttitlearray = jsonaman.getJSONArray(Config.TAG_ARRAY_TABLE_ONE);
            for (int i = 0; i < firsttitlearray.length(); i++) {
                JSONObject obj1 = (JSONObject) firsttitlearray.get(i);
                id = obj1.getString(Config.TAG_FIELD);
                field1.add(id);
            }
            JSONArray constraint_1 = jsonaman.getJSONArray(Config.PK_TAG_ARRAY_TABLE_ONE);
            for (int i = 0; i < constraint_1.length(); i++) {
                JSONObject obj1 = (JSONObject) constraint_1.get(i);
                constraint = obj1.getString("Constraint");
                constraint1.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_1, field1,constraint1);
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
            }
            JSONArray constraint_2 = jsonObject2.getJSONArray(Config.PK_TAG_ARRAY_TABLE_TWO);
            for (int i = 0; i < constraint_2.length(); i++) {
                JSONObject obj2 = (JSONObject) constraint_2.get(i);
                constraint = obj2.getString("Constraint");
                constraint2.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_2, field2,constraint2);
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
            JSONArray constraint_3 = jsonObject3.getJSONArray(Config.PK_TAG_ARRAY_TABLE_THREE);
            for (int i = 0; i < constraint_3.length(); i++) {
                JSONObject obj3 = (JSONObject) constraint_3.get(i);
                constraint = obj3.getString("Constraint");
                constraint3.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_3, field3,constraint3);
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
            JSONArray constraint_4 = jsonObject4.getJSONArray(Config.PK_TAG_ARRAY_TABLE_FOUR);
            for (int i = 0; i < constraint_4.length(); i++) {
                JSONObject obj4 = (JSONObject) constraint_4.get(i);
                constraint = obj4.getString("Constraint");
                constraint4.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_4, field4,constraint4);
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
            JSONArray constraint_5 = jsonObject5.getJSONArray(Config.PK_TAG_ARRAY_TABLE_FIVE);
            for (int i = 0; i < constraint_5.length(); i++) {
                JSONObject obj5 = (JSONObject) constraint_5.get(i);
                constraint = obj5.getString("Constraint");
                constraint5.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_5, field5,constraint5);
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
            JSONArray constraint_6 = jsonObject6.getJSONArray(Config.PK_TAG_ARRAY_TABLE_SIX);
            for (int i = 0; i < constraint_6.length(); i++) {
                JSONObject obj6 = (JSONObject) constraint_6.get(i);
                constraint = obj6.getString("Constraint");
                constraint6.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_6, field6,constraint6);
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
            JSONArray constraint_7 = jsonObject7.getJSONArray(Config.PK_TAG_ARRAY_TABLE_SEVEN);
            for (int i = 0; i < constraint_7.length(); i++) {
                JSONObject obj7 = (JSONObject) constraint_7.get(i);
                constraint = obj7.getString("Constraint");
                constraint7.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_7, field7,constraint7);
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
            JSONArray constraint_8 = jsonObject8.getJSONArray(Config.PK_TAG_ARRAY_TABLE_EIGHT);
            for (int i = 0; i < constraint_8.length(); i++) {
                JSONObject obj8 = (JSONObject) constraint_8.get(i);
                constraint = obj8.getString("Constraint");
                constraint8.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_8, field8,constraint8);
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
            JSONArray constraint_9 = jsonObject9.getJSONArray(Config.PK_TAG_ARRAY_TABLE_NINE);
            for (int i = 0; i < constraint_9.length(); i++) {
                JSONObject obj9 = (JSONObject) constraint_9.get(i);
                constraint = obj9.getString("Constraint");
                constraint9.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_9, field9,constraint9);
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
            JSONArray constraint_10 = jsonObject10.getJSONArray(Config.PK_TAG_ARRAY_TABLE_TEN);
            for (int i = 0; i < constraint_10.length(); i++) {
                JSONObject obj10 = (JSONObject) constraint_10.get(i);
                constraint = obj10.getString("Constraint");
                constraint10.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_10, field10,constraint10);
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
            JSONArray constraint_11 = jsonObject11.getJSONArray(Config.PK_TAG_ARRAY_TABLE_ELEVEN);
            for (int i = 0; i < constraint_11.length(); i++) {
                JSONObject obj11 = (JSONObject) constraint_11.get(i);
                constraint = obj11.getString("Constraint");
                constraint11.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_11, field11,constraint11);
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
            JSONArray constraint_12 = jsonObject12.getJSONArray(Config.PK_TAG_ARRAY_TABLE_TWELVE);
            for (int i = 0; i < constraint_12.length(); i++) {
                JSONObject obj12 = (JSONObject) constraint_12.get(i);
                constraint = obj12.getString("Constraint");
                constraint12.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_12, field12,constraint12);
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
            JSONArray constraint_13 = jsonObject13.getJSONArray(Config.PK_TAG_ARRAY_TABLE_THIRTEEN);
            for (int i = 0; i < constraint_13.length(); i++) {
                JSONObject obj13 = (JSONObject) constraint_13.get(i);
                constraint = obj13.getString("Constraint");
                constraint13.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_13, field13,constraint13);
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
            JSONArray constraint_14 = jsonObject14.getJSONArray(Config.PK_TAG_ARRAY_TABLE_FOURTEEN);
            for (int i = 0; i < constraint_14.length(); i++) {
                JSONObject obj14 = (JSONObject) constraint_14.get(i);
                constraint = obj14.getString("Constraint");
                constraint14.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_14, field14,constraint14);
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
            JSONArray constraint_15 = jsonObject15.getJSONArray(Config.PK_TAG_ARRAY_TABLE_FIFTEEN);
            for (int i = 0; i < constraint_15.length(); i++) {
                JSONObject obj15 = (JSONObject) constraint_15.get(i);
                constraint = obj15.getString("Constraint");
                constraint15.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_15, field15,constraint15);
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
            JSONArray constraint_16 = jsonObject16.getJSONArray(Config.PK_TAG_ARRAY_TABLE_SIXTEEN);
            for (int i = 0; i < constraint_16.length(); i++) {
                JSONObject obj16 = (JSONObject) constraint_16.get(i);
                constraint = obj16.getString("Constraint");
                constraint16.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_16, field16,constraint16);
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
            JSONArray constraint_17 = jsonObject17.getJSONArray(Config.PK_TAG_ARRAY_TABLE_SEVENTEEN);
            for (int i = 0; i < constraint_17.length(); i++) {
                JSONObject obj17 = (JSONObject) constraint_17.get(i);
                constraint = obj17.getString("Constraint");
                constraint17.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_17, field17,constraint17);
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
            JSONArray constraint_18 = jsonObject18.getJSONArray(Config.PK_TAG_ARRAY_TABLE_EIGHTEEN);
            for (int i = 0; i < constraint_18.length(); i++) {
                JSONObject obj18 = (JSONObject) constraint_18.get(i);
                constraint = obj18.getString("Constraint");
                constraint18.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_18, field18,constraint18);
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
            JSONArray constraint_19 = jsonObject19.getJSONArray(Config.PK_TAG_ARRAY_TABLE_NINETEEN);
            for (int i = 0; i < constraint_19.length(); i++) {
                JSONObject obj19 = (JSONObject) constraint_19.get(i);
                constraint = obj19.getString("Constraint");
                constraint19.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_19, field19,constraint19);
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
            JSONArray constraint_20 = jsonObject20.getJSONArray(Config.PK_TAG_ARRAY_TABLE_TWENTY);
            for (int i = 0; i < constraint_20.length(); i++) {
                JSONObject obj20 = (JSONObject) constraint_20.get(i);
                constraint = obj20.getString("Constraint");
                constraint20.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_20, field20,constraint20);
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
            JSONArray constraint_21 = jsonObject21.getJSONArray(Config.PK_TAG_ARRAY_TABLE_21);
            for (int i = 0; i < constraint_21.length(); i++) {
                JSONObject obj21 = (JSONObject) constraint_21.get(i);
                constraint = obj21.getString("Constraint");
                constraint21.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_21, field21,constraint21);
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
            JSONArray constraint_22 = jsonObject22.getJSONArray(Config.PK_TAG_ARRAY_TABLE_22);
            for (int i = 0; i < constraint_22.length(); i++) {
                JSONObject obj22 = (JSONObject) constraint_22.get(i);
                constraint = obj22.getString("Constraint");
                constraint22.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_22, field22,constraint22);
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
            JSONArray constraint_23 = jsonObject23.getJSONArray(Config.PK_TAG_ARRAY_TABLE_23);
            for (int i = 0; i < constraint_23.length(); i++) {
                JSONObject obj23 = (JSONObject) constraint_23.get(i);
                constraint = obj23.getString("Constraint");
                constraint23.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_23, field23,constraint23);
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
            JSONArray constraint_24 = jsonObject24.getJSONArray(Config.PK_TAG_ARRAY_TABLE_24);
            for (int i = 0; i < constraint_24.length(); i++) {
                JSONObject obj24 = (JSONObject) constraint_24.get(i);
                constraint = obj24.getString("Constraint");
                constraint24.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_24, field24,constraint24);
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
            JSONArray constraint_25 = jsonObject25.getJSONArray(Config.PK_TAG_ARRAY_TABLE_25);
            for (int i = 0; i < constraint_25.length(); i++) {
                JSONObject obj25 = (JSONObject) constraint_25.get(i);
                constraint = obj25.getString("Constraint");
                constraint25.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_25, field25,constraint25);
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
            JSONArray constraint_26 = jsonObject26.getJSONArray(Config.PK_TAG_ARRAY_TABLE_26);
            for (int i = 0; i < constraint_26.length(); i++) {
                JSONObject obj26 = (JSONObject) constraint_26.get(i);
                constraint = obj26.getString("Constraint");
                constraint26.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_26, field26,constraint26);
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
            JSONArray constraint_27 = jsonObject27.getJSONArray(Config.PK_TAG_ARRAY_TABLE_27);
            for (int i = 0; i < constraint_27.length(); i++) {
                JSONObject obj27 = (JSONObject) constraint_27.get(i);
                constraint = obj27.getString("Constraint");
                constraint27.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_27, field27,constraint27);
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
            JSONArray constraint_28 = jsonObject28.getJSONArray(Config.PK_TAG_ARRAY_TABLE_28);
            for (int i = 0; i < constraint_28.length(); i++) {
                JSONObject obj28 = (JSONObject) constraint_28.get(i);
                constraint = obj28.getString("Constraint");
                constraint28.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_28, field28,constraint28);
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
            JSONArray constraint_29 = jsonObject29.getJSONArray(Config.PK_TAG_ARRAY_TABLE_29);
            for (int i = 0; i < constraint_29.length(); i++) {
                JSONObject obj29 = (JSONObject) constraint_29.get(i);
                constraint = obj29.getString("Constraint");
                constraint29.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_29, field29,constraint29);
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
            JSONArray constraint_30 = jsonObject30.getJSONArray(Config.PK_TAG_ARRAY_TABLE_30);
            for (int i = 0; i < constraint_30.length(); i++) {
                JSONObject obj30 = (JSONObject) constraint_30.get(i);
                constraint = obj30.getString("Constraint");
                constraint30.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_30, field30,constraint30);
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
            JSONArray constraint_31 = jsonObject31.getJSONArray(Config.PK_TAG_ARRAY_TABLE_31);
            for (int i = 0; i < constraint_31.length(); i++) {
                JSONObject obj31 = (JSONObject) constraint_31.get(i);
                constraint = obj31.getString("Constraint");
                constraint31.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_31, field31,constraint31);
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
            JSONArray constraint_32 = jsonObject32.getJSONArray(Config.PK_TAG_ARRAY_TABLE_32);
            for (int i = 0; i < constraint_32.length(); i++) {
                JSONObject obj32 = (JSONObject) constraint_32.get(i);
                constraint = obj32.getString("Constraint");
                constraint32.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_32, field32,constraint32);
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
            JSONArray constraint_33 = jsonObject33.getJSONArray(Config.PK_TAG_ARRAY_TABLE_33);
            for (int i = 0; i < constraint_33.length(); i++) {
                JSONObject obj33 = (JSONObject) constraint_33.get(i);
                constraint = obj33.getString("Constraint");
                constraint33.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_33, field33,constraint33);
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
            JSONArray constraint_34 = jsonObject34.getJSONArray(Config.PK_TAG_ARRAY_TABLE_34);
            for (int i = 0; i < constraint_34.length(); i++) {
                JSONObject obj34 = (JSONObject) constraint_34.get(i);
                constraint = obj34.getString("Constraint");
                constraint34.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_34, field34,constraint34);
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
            JSONArray constraint_35 = jsonObject35.getJSONArray(Config.PK_TAG_ARRAY_TABLE_35);
            for (int i = 0; i < constraint_35.length(); i++) {
                JSONObject obj35 = (JSONObject) constraint_35.get(i);
                constraint = obj35.getString("Constraint");
                constraint35.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_35, field35,constraint35);
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
            JSONArray constraint_36 = jsonObject36.getJSONArray(Config.PK_TAG_ARRAY_TABLE_36);
            for (int i = 0; i < constraint_36.length(); i++) {
                JSONObject obj36 = (JSONObject) constraint_36.get(i);
                constraint = obj36.getString("Constraint");
                constraint36.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_36, field36,constraint36);
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
            JSONArray constraint_37 = jsonObject37.getJSONArray(Config.PK_TAG_ARRAY_TABLE_37);
            for (int i = 0; i < constraint_37.length(); i++) {
                JSONObject obj37 = (JSONObject) constraint_37.get(i);
                constraint = obj37.getString("Constraint");
                constraint37.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_37, field37,constraint37);
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
            JSONArray constraint_38 = jsonObject38.getJSONArray(Config.PK_TAG_ARRAY_TABLE_38);
            for (int i = 0; i < constraint_38.length(); i++) {
                JSONObject obj38 = (JSONObject) constraint_38.get(i);
                constraint = obj38.getString("Constraint");
                constraint38.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_38, field38,constraint38);
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
            JSONArray constraint_39 = jsonObject39.getJSONArray(Config.PK_TAG_ARRAY_TABLE_39);
            for (int i = 0; i < constraint_39.length(); i++) {
                JSONObject obj39 = (JSONObject) constraint_39.get(i);
                constraint = obj39.getString("Constraint");
                constraint39.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_39, field39,constraint39);
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
            JSONArray constraint_40 = jsonObject40.getJSONArray(Config.PK_TAG_ARRAY_TABLE_40);
            for (int i = 0; i < constraint_40.length(); i++) {
                JSONObject obj40 = (JSONObject) constraint_40.get(i);
                constraint = obj40.getString("Constraint");
                constraint40.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_40, field40,constraint40);
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
            JSONArray constraint_41 = jsonObject41.getJSONArray(Config.PK_TAG_ARRAY_TABLE_41);
            for (int i = 0; i < constraint_41.length(); i++) {
                JSONObject obj41 = (JSONObject) constraint_41.get(i);
                constraint = obj41.getString("Constraint");
                constraint41.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_41, field41,constraint41);
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
            JSONArray constraint_42 = jsonObject42.getJSONArray(Config.PK_TAG_ARRAY_TABLE_42);
            for (int i = 0; i < constraint_42.length(); i++) {
                JSONObject obj42 = (JSONObject) constraint_42.get(i);
                constraint = obj42.getString("Constraint");
                constraint42.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_42, field42,constraint42);
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
            JSONArray constraint_43 = jsonObject43.getJSONArray(Config.PK_TAG_ARRAY_TABLE_43);
            for (int i = 0; i < constraint_43.length(); i++) {
                JSONObject obj43 = (JSONObject) constraint_43.get(i);
                constraint = obj43.getString("Constraint");
                constraint43.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_43, field43,constraint43);
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
            JSONArray constraint_44 = jsonObject44.getJSONArray(Config.PK_TAG_ARRAY_TABLE_44);
            for (int i = 0; i < constraint_44.length(); i++) {
                JSONObject obj44 = (JSONObject) constraint_44.get(i);
                constraint = obj44.getString("Constraint");
                constraint44.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_44, field44,constraint44);
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
            JSONArray constraint_45 = jsonObject45.getJSONArray(Config.PK_TAG_ARRAY_TABLE_45);
            for (int i = 0; i < constraint_45.length(); i++) {
                JSONObject obj45 = (JSONObject) constraint_45.get(i);
                constraint = obj45.getString("Constraint");
                constraint45.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_45, field45,constraint45);
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
            JSONArray constraint_46 = jsonObject46.getJSONArray(Config.PK_TAG_ARRAY_TABLE_46);
            for (int i = 0; i < constraint_46.length(); i++) {
                JSONObject obj46 = (JSONObject) constraint_46.get(i);
                constraint = obj46.getString("Constraint");
                constraint46.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_46, field46,constraint46);
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
            JSONArray constraint_47 = jsonObject47.getJSONArray(Config.PK_TAG_ARRAY_TABLE_47);
            for (int i = 0; i < constraint_47.length(); i++) {
                JSONObject obj47 = (JSONObject) constraint_47.get(i);
                constraint = obj47.getString("Constraint");
                constraint47.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_47, field47,constraint47);
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
            JSONArray constraint_48 = jsonObject48.getJSONArray(Config.PK_TAG_ARRAY_TABLE_48);
            for (int i = 0; i < constraint_48.length(); i++) {
                JSONObject obj48 = (JSONObject) constraint_48.get(i);
                constraint = obj48.getString("Constraint");
                constraint48.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_48, field48,constraint48);
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
            JSONArray constraint_49 = jsonObject49.getJSONArray(Config.PK_TAG_ARRAY_TABLE_49);
            for (int i = 0; i < constraint_49.length(); i++) {
                JSONObject obj49 = (JSONObject) constraint_49.get(i);
                constraint = obj49.getString("Constraint");
                constraint49.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_49, field49,constraint49);
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
            JSONArray constraint_50 = jsonObject50.getJSONArray(Config.PK_TAG_ARRAY_TABLE_50);
            for (int i = 0; i < constraint_50.length(); i++) {
                JSONObject obj50 = (JSONObject) constraint_50.get(i);
                constraint = obj50.getString("Constraint");
                constraint50.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_50, field50,constraint50);
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
            JSONArray constraint_51 = jsonObject51.getJSONArray(Config.PK_TAG_ARRAY_TABLE_51);
            for (int i = 0; i < constraint_51.length(); i++) {
                JSONObject obj51 = (JSONObject) constraint_51.get(i);
                constraint = obj51.getString("Constraint");
                constraint51.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_51, field51,constraint51);
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
            JSONArray constraint_52 = jsonObject52.getJSONArray(Config.PK_TAG_ARRAY_TABLE_52);
            for (int i = 0; i < constraint_52.length(); i++) {
                JSONObject obj52 = (JSONObject) constraint_52.get(i);
                constraint = obj52.getString("Constraint");
                constraint52.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_52, field52,constraint52);
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
            JSONArray constraint_53 = jsonObject53.getJSONArray(Config.PK_TAG_ARRAY_TABLE_53);
            for (int i = 0; i < constraint_53.length(); i++) {
                JSONObject obj53 = (JSONObject) constraint_53.get(i);
                constraint = obj53.getString("Constraint");
                constraint53.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_53, field53,constraint53);
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
            JSONArray constraint_54 = jsonObject54.getJSONArray(Config.PK_TAG_ARRAY_TABLE_54);
            for (int i = 0; i < constraint_54.length(); i++) {
                JSONObject obj54 = (JSONObject) constraint_54.get(i);
                constraint = obj54.getString("Constraint");
                constraint54.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_54, field54,constraint54);
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
            JSONArray constraint_55 = jsonObject55.getJSONArray(Config.PK_TAG_ARRAY_TABLE_55);
            for (int i = 0; i < constraint_55.length(); i++) {
                JSONObject obj55 = (JSONObject) constraint_55.get(i);
                constraint = obj55.getString("Constraint");
                constraint55.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_55, field55,constraint55);
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
            JSONArray constraint_56 = jsonObject56.getJSONArray(Config.PK_TAG_ARRAY_TABLE_56);
            for (int i = 0; i < constraint_56.length(); i++) {
                JSONObject obj56 = (JSONObject) constraint_56.get(i);
                constraint = obj56.getString("Constraint");
                constraint56.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_56, field56,constraint56);
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
            JSONArray constraint_57 = jsonObject57.getJSONArray(Config.PK_TAG_ARRAY_TABLE_57);
            for (int i = 0; i < constraint_57.length(); i++) {
                JSONObject obj57 = (JSONObject) constraint_57.get(i);
                constraint = obj57.getString("Constraint");
                constraint57.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_57, field57,constraint57);
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
            JSONArray constraint_58 = jsonObject58.getJSONArray(Config.PK_TAG_ARRAY_TABLE_58);
            for (int i = 0; i < constraint_58.length(); i++) {
                JSONObject obj58 = (JSONObject) constraint_58.get(i);
                constraint = obj58.getString("Constraint");
                constraint58.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_58, field58,constraint58);
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
            JSONArray constraint_59 = jsonObject59.getJSONArray(Config.PK_TAG_ARRAY_TABLE_59);
            for (int i = 0; i < constraint_59.length(); i++) {
                JSONObject obj59 = (JSONObject) constraint_59.get(i);
                constraint = obj59.getString("Constraint");
                constraint59.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_59, field59,constraint59);
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
            JSONArray constraint_60 = jsonObject60.getJSONArray(Config.PK_TAG_ARRAY_TABLE_60);
            for (int i = 0; i < constraint_60.length(); i++) {
                JSONObject obj60 = (JSONObject) constraint_60.get(i);
                constraint = obj60.getString("Constraint");
                constraint60.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_60, field60,constraint60);
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
            JSONArray constraint_61 = jsonObject61.getJSONArray(Config.PK_TAG_ARRAY_TABLE_61);
            for (int i = 0; i < constraint_61.length(); i++) {
                JSONObject obj61 = (JSONObject) constraint_61.get(i);
                constraint = obj61.getString("Constraint");
                constraint61.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_61, field61,constraint61);
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
            JSONArray constraint_62 = jsonObject62.getJSONArray(Config.PK_TAG_ARRAY_TABLE_62);
            for (int i = 0; i < constraint_62.length(); i++) {
                JSONObject obj62 = (JSONObject) constraint_62.get(i);
                constraint = obj62.getString("Constraint");
                constraint62.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_62, field62,constraint62);
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
            JSONArray constraint_63 = jsonObject63.getJSONArray(Config.PK_TAG_ARRAY_TABLE_63);
            for (int i = 0; i < constraint_63.length(); i++) {
                JSONObject obj63 = (JSONObject) constraint_63.get(i);
                constraint = obj63.getString("Constraint");
                constraint63.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_63, field63,constraint63);
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
            JSONArray constraint_64 = jsonObject64.getJSONArray(Config.PK_TAG_ARRAY_TABLE_64);
            for (int i = 0; i < constraint_64.length(); i++) {
                JSONObject obj64 = (JSONObject) constraint_64.get(i);
                constraint = obj64.getString("Constraint");
                constraint64.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_64, field64,constraint64);
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
            JSONArray constraint_65 = jsonObject65.getJSONArray(Config.PK_TAG_ARRAY_TABLE_65);
            for (int i = 0; i < constraint_65.length(); i++) {
                JSONObject obj65 = (JSONObject) constraint_65.get(i);
                constraint = obj65.getString("Constraint");
                constraint65.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_65, field65,constraint65);
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
            JSONArray constraint_66 = jsonObject66.getJSONArray(Config.PK_TAG_ARRAY_TABLE_66);
            for (int i = 0; i < constraint_66.length(); i++) {
                JSONObject obj66 = (JSONObject) constraint_66.get(i);
                constraint = obj66.getString("Constraint");
                constraint66.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_66, field66,constraint66);
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
            JSONArray constraint_68 = jsonObject68.getJSONArray(Config.PK_TAG_ARRAY_TABLE_68);
            for (int i = 0; i < constraint_68.length(); i++) {
                JSONObject obj68 = (JSONObject) constraint_68.get(i);
                constraint = obj68.getString("Constraint");
                constraint68.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_68, field68,constraint68);
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
            JSONArray constraint_69 = jsonObject69.getJSONArray(Config.PK_TAG_ARRAY_TABLE_69);
            for (int i = 0; i < constraint_69.length(); i++) {
                JSONObject obj69 = (JSONObject) constraint_69.get(i);
                constraint = obj69.getString("Constraint");
                constraint69.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_69, field69,constraint69);
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
            JSONArray constraint_70 = jsonObject70.getJSONArray(Config.PK_TAG_ARRAY_TABLE_70);
            for (int i = 0; i < constraint_70.length(); i++) {
                JSONObject obj70 = (JSONObject) constraint_70.get(i);
                constraint = obj70.getString("Constraint");
                constraint70.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_70, field70,constraint70);
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
            JSONArray constraint_71 = jsonObject71.getJSONArray(Config.PK_TAG_ARRAY_TABLE_71);
            for (int i = 0; i < constraint_71.length(); i++) {
                JSONObject obj71 = (JSONObject) constraint_71.get(i);
                constraint = obj71.getString("Constraint");
                constraint71.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_71, field71,constraint71);
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
            JSONArray constraint_72 = jsonObject72.getJSONArray(Config.PK_TAG_ARRAY_TABLE_72);
            for (int i = 0; i < constraint_72.length(); i++) {
                JSONObject obj72 = (JSONObject) constraint_72.get(i);
                constraint = obj72.getString("Constraint");
                constraint72.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_72, field72,constraint72);
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
            JSONArray constraint_73 = jsonObject73.getJSONArray(Config.PK_TAG_ARRAY_TABLE_73);
            for (int i = 0; i < constraint_73.length(); i++) {
                JSONObject obj73 = (JSONObject) constraint_73.get(i);
                constraint = obj73.getString("Constraint");
                constraint73.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_73, field73,constraint73);
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
            JSONArray constraint_74 = jsonObject74.getJSONArray(Config.PK_TAG_ARRAY_TABLE_74);
            for (int i = 0; i < constraint_74.length(); i++) {
                JSONObject obj74 = (JSONObject) constraint_74.get(i);
                constraint = obj74.getString("Constraint");
                constraint74.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_74, field74,constraint74);
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
            JSONArray constraint_75 = jsonObject75.getJSONArray(Config.PK_TAG_ARRAY_TABLE_75);
            for (int i = 0; i < constraint_75.length(); i++) {
                JSONObject obj75 = (JSONObject) constraint_75.get(i);
                constraint = obj75.getString("Constraint");
                constraint75.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_75, field75,constraint75);
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
            JSONArray constraint_76 = jsonObject76.getJSONArray(Config.PK_TAG_ARRAY_TABLE_76);
            for (int i = 0; i < constraint_76.length(); i++) {
                JSONObject obj76 = (JSONObject) constraint_76.get(i);
                constraint = obj76.getString("Constraint");
                constraint76.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_76, field76,constraint76);
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
            JSONArray constraint_77 = jsonObject77.getJSONArray(Config.PK_TAG_ARRAY_TABLE_77);
            for (int i = 0; i < constraint_77.length(); i++) {
                JSONObject obj77 = (JSONObject) constraint_77.get(i);
                constraint = obj77.getString("Constraint");
                constraint77.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_77, field77,constraint77);
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
            JSONArray constraint_78 = jsonObject78.getJSONArray(Config.PK_TAG_ARRAY_TABLE_78);
            for (int i = 0; i < constraint_78.length(); i++) {
                JSONObject obj78 = (JSONObject) constraint_78.get(i);
                constraint = obj78.getString("Constraint");
                constraint78.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_78, field78,constraint78);
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
            JSONArray constraint_79 = jsonObject79.getJSONArray(Config.PK_TAG_ARRAY_TABLE_79);
            for (int i = 0; i < constraint_79.length(); i++) {
                JSONObject obj79 = (JSONObject) constraint_79.get(i);
                constraint = obj79.getString("Constraint");
                constraint79.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_79, field79,constraint79);
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
            JSONArray constraint_80 = jsonObject80.getJSONArray(Config.PK_TAG_ARRAY_TABLE_80);
            for (int i = 0; i < constraint_80.length(); i++) {
                JSONObject obj80 = (JSONObject) constraint_80.get(i);
                constraint = obj80.getString("Constraint");
                constraint80.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_80, field80,constraint80);
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
            JSONArray constraint_81 = jsonObject81.getJSONArray(Config.PK_TAG_ARRAY_TABLE_81);
            for (int i = 0; i < constraint_81.length(); i++) {
                JSONObject obj81 = (JSONObject) constraint_81.get(i);
                constraint = obj81.getString("Constraint");
                constraint81.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_81, field81,constraint81);
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
            JSONArray constraint_82 = jsonObject82.getJSONArray(Config.PK_TAG_ARRAY_TABLE_82);
            for (int i = 0; i < constraint_82.length(); i++) {
                JSONObject obj82 = (JSONObject) constraint_82.get(i);
                constraint = obj82.getString("Constraint");
                constraint82.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_82, field82,constraint82);
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
            JSONArray constraint_83 = jsonObject83.getJSONArray(Config.PK_TAG_ARRAY_TABLE_83);
            for (int i = 0; i < constraint_83.length(); i++) {
                JSONObject obj83 = (JSONObject) constraint_83.get(i);
                constraint = obj83.getString("Constraint");
                constraint83.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_83, field83,constraint83);
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
            JSONArray constraint_84 = jsonObject84.getJSONArray("retail_credit_bill_details_pk");
            for (int i = 0; i < constraint_84.length(); i++) {
                JSONObject obj84 = (JSONObject) constraint_84.get(i);
                constraint = obj84.getString("Constraint");
                constraint84.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_84, field84,constraint84);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }



        try {
            JSONObject jsonObject85 = new JSONObject(input);
            JSONArray firsttitlearray85 = jsonObject85.getJSONArray("retail_str_lost_sales");
            for (int i = 0; i < firsttitlearray85.length(); i++) {
                JSONObject obj85 = (JSONObject) firsttitlearray85.get(i);
                id = obj85.getString(Config.TAG_FIELD);
                field85.add(id);
            }
            JSONArray constraint_85 = jsonObject85.getJSONArray("retail_str_lost_sales_pk");
            for (int i = 0; i < constraint_85.length(); i++) {
                JSONObject obj85 = (JSONObject) constraint_85.get(i);
                constraint = obj85.getString("Constraint");
                constraint85.add(constraint);
            }
            createDynamicDatabase(Activity_Installation.this, tablename_85, field85,constraint85);
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }




        Toast.makeText(getApplicationContext(), "Tables Created", Toast.LENGTH_LONG).show();
    }

    public void createDynamicDatabase(Context context, String tableName, ArrayList<String> title,ArrayList<String> constraint) {
        Log.i("INSIDE LoginDatabase", "****creatLoginDatabase*****");
        try {
            int i;
            String querryString;
            File db_path = new File("/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/Db");
            db_path.getParentFile().mkdirs();
            myDataBase = SQLiteDatabase.openOrCreateDatabase(db_path, null);          //Opens database in writable mode.
            myDataBase.execSQL("PRAGMA key='Anaconda'");
            //Opens database in writable mode.
            querryString = title.get(0) + " NVARCHAR(30),";
            Log.d("**createDynamicDatabase", "in oncreate");
            for (i = 1; i < title.size() - 1; i++) {
                querryString += title.get(i);
                querryString += " NVARCHAR(30) ";
                querryString += ",";
            }
            String constraintString = constraint.toString().replace("[","(").replace("]",")");
            querryString += title.get(i) + " NVARCHAR(30) ";
            querryString = "CREATE TABLE IF NOT EXISTS " + tableName + "(" + querryString + ", CONSTRAINT store_pk PRIMARY KEY " +constraintString +" );";
            System.out.println("Create Table Stmt : " + querryString);
            myDataBase.execSQL(querryString);
            //   Toast.makeText(this, "Table "+ tablename_first +" created.", Toas
            // t.LENGTH_SHORT).show();
            myDataBase.close();
        } catch (org.sqlite.database.SQLException ex) {
            Log.i("xyz CreateDB Exception ", ex.getMessage());
        }
    }

    int insertion_index = 0;

    public void insert(Context context, ArrayList<String> array_vals, ArrayList<String> title, String TABLE_NAME, int row_index) {
        Log.d("Inside Insert", "Insertion starts for table name: " + TABLE_NAME);
        String querryString;
        File db_path = new File("/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/Db");
        db_path.getParentFile().mkdirs();
        myDataBase = SQLiteDatabase.openOrCreateDatabase(db_path, null);          //Opens database in writable mode.
        myDataBase.execSQL("PRAGMA key='Anaconda'");
        //Opens database in writable mode.
        String titleString = null;
        // String markString = "";
        int i = 0, c = 0;
        titleString = title.get(0) + ",";
   /* for(int k = 0;k<array_vals.size();k++) {
        markString += String.valueOf(array_vals.get(row_index));
    }
    Log.d("888888",markString);*/
  /*  String markString = array_vals.toString().replace("[","").replace("]","");
    Log.d("Marksssss",markString);*/
        //  Log.d("@@@@@@", String.valueOf(array_vals.size()));
        String markString = array_vals.toString().replace("[","").replace("]","");
        //  Log.d("88888888", markString);
   /* Log.d("&&&&&&", String.valueOf(list.size()));
    String markString = array_vals.toString().replace("[","").replace("]","");*/
        for (i = 1; i < title.size() - 1; i++) {
            titleString += title.get(i);
            titleString += ",";
        }
 /*   for (c = row_index + 1; c < row_index + (title.size() - 1); c++) {
        Log.d("**Inserting Column", "in On create");
        Log.d("Column index", String.valueOf(c));
        markString += array_vals.get(c);
        markString += "','";
    }*/
        Log.d("**createDynamicDatabase", "in oncreate");
        titleString += title.get(i);
        //  markString += array_vals.get(c) + "'";
        INSERT = "INSERT OR REPLACE into " + TABLE_NAME  + " values " + markString ;
        String update_S_Flag = "UPDATE " + TABLE_NAME + " set S_FLAG = '1';";
        insertion_index++;
        // Log.d("Insert called", String.valueOf(insertion_index));
        // System.out.println("Insert statement: " + INSERT);
        this.insertStmt = this.myDataBase.compileStatement(INSERT);
        insertStmt.executeInsert();
        this.updateStmt = this.myDataBase.compileStatement(update_S_Flag);
        updateStmt.executeUpdateDelete();
        myDataBase.close();
        int flagger = 1;
    }
    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Activity_Installation.this, "Fetching Data", "Wait...", false, false);
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
                String s = rh.sendGetRequest("https://99sync.eu-gb.mybluemix.net/Testing_For_PrimaryKey.jsp");
                //String s = rh.sendGetRequest("http://52.76.28.14/Android/json.php");
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.login:
                store_id=user.getText().toString().trim();
                password = pass.getText().toString().trim();
                downloadata(store_id,password,getApplicationContext());
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen007, menu);
        return true;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try{
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (NullPointerException e)
        {
            e.printStackTrace();
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
            case R.id.help:
                help_installation();
                return true;
            default:
                return super.onOptionsItemSelected(item);

            //Intent i = new Intent(Activity_masterScreen1.this, .class);
            //  startActivity(i);

        }

    }
    public void downloadata(String storeId,String Otp,Context context){
        final OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(1200, TimeUnit.SECONDS).readTimeout(1200,TimeUnit.SECONDS).build();
        //context=getApplicationContext();
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

        Call<ConfigItems> call1 = stackOverflowAPI.load(storeId,Otp,"DATA");
        //asynchronous call
        // call.enqueue(this);
        call1.enqueue(this);

        loading = new ProgressDialog(Activity_Installation.this);
        loading.setMessage("Thanks for Being Patient...");
        loading.setIndeterminate(false);
        loading.setCancelable(false);
        loading.show();

    }

    public void help_installation() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                              STORE ID");
        alertDialog.setMessage("\n" +
                "1.Objective:\n" +
                "\n" +
                "Store Id is the unique identification number which is given to a particular store. It is needed only for the first time login along with the ONE TIME PASSWORD(OTP).\n" +
                "\n" +
                "2.Input Description:\n" +
                "\n" +
                "          a.Store Id: User has to enter store id.\n" +
                "          b.Password: User has to enter one time password.\n" +
                "\n" +
                "3.Field Description:\n" +
                "\n" +
                "   Login: User wil navigate to the login page.");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                /*Intent intent = new Intent(getApplicationContext(), Activity_Installation.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();


    }





}