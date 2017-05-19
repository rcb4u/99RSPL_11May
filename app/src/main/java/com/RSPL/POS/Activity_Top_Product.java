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
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import Adapter.TopProductDropDownAdapter;
import Adapter.Top_Product_Adapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.Registeremployeesmodel;
import Pojo.Topfullproductmodel;

//import Adapter.Topfullproductadapter;


public class Activity_Top_Product extends Activity {
    DBhelper mydb;
    ArrayList<Topfullproductmodel> arrayList;
    String ActiveType[];
    AutoCompleteTextView autoCompleteTextView;
    TextWatcher mTextWatcher;
    Top_Product_Adapter adapter;
    // Adapter
    ArrayAdapter<String> adapteractiveType;
    String SpinValue;
    public static String username = null;

    Button submit, clrbtn;
    String Store_Id,store_id;
    ActionBar actionBar;
    Spinner Products,active;
    EditText Product_Short_Name,TProduct_ID;
    TextView Product_Name;
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";

    Spinner DeleteProduct;
    //////////////////////////jimmmy/////////////////////////////////////
    TextView tvproducts,tvproductname,tvshortname,tvdelete;

    String backroundcolour,colorchange;

    String iteam;
    TextView user2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity__top__product);
        username =  login.b.getString("Pos_User");

        actionBar = getActionBar();
     //   actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);
        mydb = new DBhelper(Activity_Top_Product.this);
        Decimal valuetextsize = mydb.getStoreprice();
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


/////////////////////////jimmy/////////////////////////////


        tvproducts=(TextView)findViewById(R.id.textViewsno);
        tvproducts.setTextSize(Float.parseFloat(textsize));

        tvproductname=(TextView)findViewById(R.id.textviewT1);
        tvproductname.setTextSize(Float.parseFloat(textsize));
        tvshortname=(TextView)findViewById(R.id.textviewcardtype);
        tvshortname.setTextSize(Float.parseFloat(textsize));
        tvdelete=(TextView)findViewById(R.id.textviewca1234);
        tvdelete.setTextSize(Float.parseFloat(textsize));




        Product_Name=(TextView)findViewById(R.id.Topproductname);
        Product_Name.setTextSize(Float.parseFloat(textsize));

        Product_Short_Name=(EditText)findViewById(R.id.Topproduct_shortname);
        Product_Short_Name.setTextSize(Float.parseFloat(textsize));

        TProduct_ID=(EditText)findViewById(R.id.Topproduct_id);
        TProduct_ID.setTextSize(Float.parseFloat(textsize));



        DeleteProduct =(Spinner) findViewById(R.id.top_products_delete);
        submit = (Button) findViewById(R.id.buttonupdate);
        clrbtn = (Button) findViewById(R.id.buttonclear);



        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);



        mydb = new DBhelper(this);
        autoCompleteTextView = (CustomAuto)findViewById(R.id.myautocomplete);
        autoCompleteTextView.setThreshold(1);
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("Debuging", "After text changed called ");
                if( autoCompleteTextView.isPerformingCompletion()) {
                    Log.d( "Debuging", "Performing completion " );
                    return;
                }
                String userTypedString = autoCompleteTextView.getText().toString().trim();
                if (userTypedString.equals("")) {
                    return;
                }
                arrayList = mydb.getAllTopProducts(userTypedString);

                if (arrayList != null) {
                    if (adapter == null) {
                        adapter = new Top_Product_Adapter(Activity_Top_Product.this, android.R.layout.simple_dropdown_item_1line, arrayList);
                        adapter.setLocalProductList(arrayList);
                        autoCompleteTextView.setAdapter(adapter);
                        autoCompleteTextView.setThreshold(1);
                    } else {

                        adapter.setLocalProductList(arrayList);
                        adapter.notifyDataSetChanged();
//
                    }
                }
            }
        };
        autoCompleteTextView.addTextChangedListener(mTextWatcher);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {

                Log.d("Debuging", "On click called ");

                final DBhelper mydb = new DBhelper(Activity_Top_Product.this);

                Topfullproductmodel value = (Topfullproductmodel) parent.getItemAtPosition(pos);


                Product_Name.setText(value.getProductname());
                Product_Short_Name.setText(value.getShortname());
                Product_Short_Name.requestFocus();
                TProduct_ID.setText(value.getProductId());

            }

        });



        final DBhelper helper = new DBhelper(Activity_Top_Product.this);
        ArrayList<Topfullproductmodel> deteteproducts = helper.getAllDeleteTopProducts();
        TopProductDropDownAdapter topProductDropDownAdapter=new TopProductDropDownAdapter(deteteproducts,this);
        DeleteProduct.setAdapter(topProductDropDownAdapter);
        DeleteProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String   item = DeleteProduct.getSelectedItem().toString();
                if (item.matches("")) {
                }

            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(Buttonok);


                try{


                    if (Product_Name.getText().toString().matches(""))
                    {
                        Product_Name.setError("Please enter name");
                        return;
                    }
                    if (Product_Short_Name.getText().toString().matches(""))
                    {
                        Product_Short_Name.setError("Please enter short name");
                        return;
                    }


                    mydb.saveTop15Product(TProduct_ID.getText().toString(),Product_Name.getText().toString(),Product_Short_Name.getText().toString(),user2.getText().toString());
                    // Toast.makeText(getApplicationContext(), "Data Inserted", Toast.LENGTH_SHORT).show();

                    Updatetopproduct();

                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);

                    startActivity(intent);



                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });


        clrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(Buttonok);
                try {
                    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                    startActivity(intent);
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
            }
        });
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



        mydb=new DBhelper(Activity_Top_Product.this);
        ArrayList<Registeremployeesmodel> user_name= mydb.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(Activity_Top_Product.this, android.R.layout.simple_spinner_dropdown_item,user_name);

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

                Intent i=new Intent(Activity_Top_Product.this,ActivityLoyality.class);
                startActivity(i);

                return true;

            case R.id.action_settinghelp:
                help_top15_products();
                return true;

            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(Activity_Top_Product.this);
                showIncentive.show();
                return true;

           /* case R.id.action_settingpurchase:
                Intent p=new Intent(Activity_Top_Product.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_Masterscn1:
                Intent p = new Intent(Activity_Top_Product.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(Activity_Top_Product.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(Activity_Top_Product.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(Activity_Top_Product.this ,login.class);
        startActivity(intent);
    }


    public void Updatetopproduct() {



       // final String topproducts =Products.toString();
        final String topproductname =  Product_Name.getText().toString();
        final String toptransactionid =TProduct_ID.getText().toString();
        final String topshortname =  Product_Short_Name.getText().toString();
        final String pos_user = user2.getText().toString();


        PersistenceManager.saveStoreId(Activity_Top_Product.this, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(Activity_Top_Product.this);



        class UpdateTopProduct extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
      try{
                HashMap<String, String> hashMap = new HashMap<>();


                hashMap.put(Config.Top_store_id,store_id);
               // hashMap.put(Config.Top_Product,topproducts);
                hashMap.put(Config.Top_Product_Nmae,topproductname);
                hashMap.put(Config.Top_Transaction_ID,toptransactionid);

                hashMap.put(Config.Top_Shortname,topshortname);
                hashMap.put(Config.Retail_Pos_user,pos_user);


                JSONParserSync rh = new JSONParserSync();
          //JSONObject s = rh.sendPostRequest("https://uldev.eu-gb.mybluemix.net/top_products.jsp", hashMap);
          JSONObject s = rh.sendPostRequest(Config.UPDATE_TOP_PRODUCT, hashMap);
                Log.d("Login attempt", s.toString());

                // Success tag for json
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
                //  loading = ProgressDialog.show(ActivityTender.this, "UPDATING...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //     loading.dismiss();
              //  Toast.makeText(Activity_Top_Product.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateTopProduct updatepointrange = new UpdateTopProduct();
        updatepointrange.execute();
    }



    public void help_top15_products() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                   TOP 15 PRODUCTS");
        alertDialog.setMessage(" \n" +
                        "Objective:\n" +
                        "\t\n" +
                        "The top 15 products the retailer would like to have in sales scree for fast billing.\n" +

                        "\n"
        );
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                /*Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }
}



