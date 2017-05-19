package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Config.Config;
import Pojo.Decimal;
import Pojo.Store;
import Pojo.Visibility;

public class ActivityLoyalityCust extends Activity implements View.OnClickListener{

    ActionBar actionBar;
    DBhelper mydb;
    String backroundcolour,colorchange;
    String name,mob,store_id;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_loyality_cust);

        mydb = new DBhelper(ActivityLoyalityCust.this);

        Decimal valuetextsize = mydb.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();


        Store value = mydb.getStoreDetails();

        name =value.getStorecontactname();
        mob=value.getStoreTele();
        store_id=value.getStoreId();

        Visibility value2 = mydb.getStorevisibility();
        otp =value2.getOtp();




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


        ImageButton MasterDataBtn= (ImageButton) findViewById(R.id.MasterdataReports);
        ImageButton MediaBtn=(ImageButton)findViewById(R.id.MediaReports);
        ImageButton PurchasingBtn=(ImageButton)findViewById(R.id.PurchasingReports);
        ImageButton SalesBtn=(ImageButton)findViewById(R.id.Sales_Reports);
        ImageButton FinancialBtn=(ImageButton)findViewById(R.id.Financial_Reports);
        ImageButton OthersBtn=(ImageButton)findViewById(R.id.OthersReports);

        MasterDataBtn.setOnClickListener(this);
        MediaBtn.setOnClickListener(this);
        PurchasingBtn.setOnClickListener(this);
        SalesBtn.setOnClickListener(this);
        FinancialBtn.setOnClickListener(this);
        OthersBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.MasterdataReports:
                Intent intent = new Intent(this, ReportTabActivityMasterData.class);
                startActivity(intent);
                break;
            case R.id.MediaReports:
                clearbuttondialog();

                break;

            case R.id.PurchasingReports:
                if (otp.matches("Y")){

//                    genrateOTP();

                    popUpForOTP();

                }
                else {
                    Intent intent4 = new Intent(this, PurchasingReportActivity.class);
                    startActivity(intent4);
                }
                break;
            case R.id.Sales_Reports:
                if (otp.matches("Y")){

//                    genrateOTP();

                    Sales_Reports_popUpForOTP();

                }

                else {
                    Intent intent4 = new Intent(this, SaleReportActivity.class);
                    startActivity(intent4);
                }
                break;
            case R.id.Financial_Reports:
                if (otp.matches("Y")){

//                    genrateOTP();

                    Financial_Reports_popUpForOTP();

                }
                else {
                    Intent intent4 = new Intent(this, ReportDayOpenCash.class);
                    startActivity(intent4);
                }
                break;
            case R.id.OthersReports:

                clearbuttondialog();
                break;

        }

    }

    public  void help_reports_dialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("             REPORT PAGE   ");
        alertDialog.setMessage(

                "Objective:\n" +
                        "\t\n" +
                        "Following are the option available on this  page:\n" +
                        "\n" +
                        "\t * Master Data Reports \n" +
                        "\t * Media Reports\n" +
                        "\t * Purchasing Reports\n" +
                        "\t * Sales Reports\n" +
                        "\t * Financial Reports\n" +
                        "\t * Others \n"
        );
        alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {



                dialog.dismiss();
            }
        });


        alertDialog.show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {


            case R.id.action_settings:
                Intent i=new Intent(ActivityLoyalityCust.this,Activity_masterScreen1.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityLoyalityCust.this);
                showIncentive.show();
                return true;
            case R.id.action_settinghelp:
                help_reports_dialog();
                return true;
           /* case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityLoyalityCust.this,PurchaseActivity.class);
                startActivity(p);
                return true;
*/
            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityLoyalityCust.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityLoyalityCust.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityLoyalityCust.this,ActivitySalesbill.class);
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
        Intent intent = new Intent(ActivityLoyalityCust.this ,login.class);
        startActivity(intent);
    }



    public  void clearbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.alertimage);
        alertDialog.setTitle("                              ");
        alertDialog.setMessage("Under Progress");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


            /*    Intent intent = new Intent(getApplicationContext(), ActivityLoyality.class);
                startActivity(intent);*/
            }
        });


        alertDialog.show();
    }

    public void popUpForOTP()
    {
        LayoutInflater li = LayoutInflater.from(ActivityLoyalityCust.this);
        View promptsView = li.inflate(R.layout.popup_password_otp, null);


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ActivityLoyalityCust.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText otp = (EditText) promptsView
                .findViewById(R.id.editTextOtp_pop);



//        final EditText pincode_prompt = (EditText)promptsView.findViewById(R.id.pincode_prompts);
//        userInput.setText(address);
//        pincode_prompt.setText(pincode);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Please Enter OTP")
//                .setIcon(R.drawable.w)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                final String getOTP = otp.getText().toString().trim();
                                if(getOTP.equalsIgnoreCase("")) {
                                    popUpForOTP();

                                }
                                else {
                                    final ProgressDialog loading = ProgressDialog.show(ActivityLoyalityCust.this, "OTP Checking", "Please Wait...", false, false);
                                    StringRequest request = new StringRequest(Request.Method.POST, Config.VERIFY_OTP, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            loading.dismiss();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String message = jsonObject.getString("success");
                                                if (message.equalsIgnoreCase("1")) {
//                                                Toast.makeText(getApplicationContext(),"OTP Genrated",Toast.LENGTH_SHORT).show();


                                                    //   mydb.insertotpdata(name,mob,store_id);
                                                    Intent intent4 = new Intent(getApplicationContext(), PurchasingReportActivity.class);
                                                    startActivity(intent4);

                                                } else if (message.equalsIgnoreCase("0")) {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            loading.dismiss();
                                            String message = null;
                                            if (volleyError instanceof NetworkError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ServerError) {
                                                message = "Something went wrong. Please try again after some time!!";
                                            } else if (volleyError instanceof AuthFailureError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ParseError) {
                                                message = "Please try again after some time!!";
                                            } else if (volleyError instanceof NoConnectionError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof TimeoutError) {
                                                message = "Connection TimeOut! Please check your internet connection.";
                                            }
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                        }


                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();


                                            params.put("STORE_ID", store_id);
                                            params.put("OTP", getOTP);
//                                        params.put("TELE","8310876676");
                                            return params;
                                        }
                                    };

                                    RequestQueue queue = Volley.newRequestQueue(ActivityLoyalityCust.this);
                                    queue.add(request);
                                }


                            }
                        })
//                .setNeutralButton("Resend",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(final DialogInterface dialog, int id) {
//
////                                final ProgressDialog loading = ProgressDialog.show(Activitypurchase.this, "OTP REsending", "Please Wait...", false, false);
//
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RESEND_OTP, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
////                                        loading.dismiss();
//                                        Log.e("OTP resend",response);
//
//                                        popUpForOTP();
//
//
//
//
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
////                                        loading.dismiss();
//                                    }
//                                })
//                                {
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        Map<String,String> params = new HashMap<String, String>();
//                                        params.put("STORE_ID",store_id);
//                                        params.put("USER_NAME",name);
//                                        params.put("TELE","8310876676");
//
//                                        return params;
//                                    }
//                                };
//
//                                RequestQueue queue = Volley.newRequestQueue(Activitypurchase.this);
//                                queue.add(stringRequest);

//                            }
//                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void Sales_Reports_popUpForOTP()
    {
        LayoutInflater li = LayoutInflater.from(ActivityLoyalityCust.this);
        View promptsView = li.inflate(R.layout.popup_password_otp, null);


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ActivityLoyalityCust.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText otp = (EditText) promptsView
                .findViewById(R.id.editTextOtp_pop);



//        final EditText pincode_prompt = (EditText)promptsView.findViewById(R.id.pincode_prompts);
//        userInput.setText(address);
//        pincode_prompt.setText(pincode);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Please Enter OTP")
//                .setIcon(R.drawable.w)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                final String getOTP = otp.getText().toString().trim();
                                if(getOTP.equalsIgnoreCase("")) {
                                    Sales_Reports_popUpForOTP();

                                }
                                else {
                                    final ProgressDialog loading = ProgressDialog.show(ActivityLoyalityCust.this, "OTP Checking", "Please Wait...", false, false);
                                    StringRequest request = new StringRequest(Request.Method.POST, Config.VERIFY_OTP, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            loading.dismiss();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String message = jsonObject.getString("success");
                                                if (message.equalsIgnoreCase("1")) {
//                                                Toast.makeText(getApplicationContext(),"OTP Genrated",Toast.LENGTH_SHORT).show();


                                                    //   mydb.insertotpdata(name,mob,store_id);
                                                    Intent intent4 = new Intent(getApplicationContext(),SaleReportActivity.class);
                                                    startActivity(intent4);

                                                } else if (message.equalsIgnoreCase("0")) {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            loading.dismiss();
                                            String message = null;
                                            if (volleyError instanceof NetworkError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ServerError) {
                                                message = "Something went wrong. Please try again after some time!!";
                                            } else if (volleyError instanceof AuthFailureError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ParseError) {
                                                message = "Please try again after some time!!";
                                            } else if (volleyError instanceof NoConnectionError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof TimeoutError) {
                                                message = "Connection TimeOut! Please check your internet connection.";
                                            }
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                        }


                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();


                                            params.put("STORE_ID", store_id);
                                            params.put("OTP", getOTP);
//                                        params.put("TELE","8310876676");
                                            return params;
                                        }
                                    };

                                    RequestQueue queue = Volley.newRequestQueue(ActivityLoyalityCust.this);
                                    queue.add(request);
                                }


                            }
                        })
//                .setNeutralButton("Resend",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(final DialogInterface dialog, int id) {
//
////                                final ProgressDialog loading = ProgressDialog.show(Activitypurchase.this, "OTP REsending", "Please Wait...", false, false);
//
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RESEND_OTP, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
////                                        loading.dismiss();
//                                        Log.e("OTP resend",response);
//
//                                        popUpForOTP();
//
//
//
//
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
////                                        loading.dismiss();
//                                    }
//                                })
//                                {
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        Map<String,String> params = new HashMap<String, String>();
//                                        params.put("STORE_ID",store_id);
//                                        params.put("USER_NAME",name);
//                                        params.put("TELE","8310876676");
//
//                                        return params;
//                                    }
//                                };
//
//                                RequestQueue queue = Volley.newRequestQueue(Activitypurchase.this);
//                                queue.add(stringRequest);

//                            }
//                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    public void Financial_Reports_popUpForOTP()
    {
        LayoutInflater li = LayoutInflater.from(ActivityLoyalityCust.this);
        View promptsView = li.inflate(R.layout.popup_password_otp, null);


        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                ActivityLoyalityCust.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);
        final EditText otp = (EditText) promptsView
                .findViewById(R.id.editTextOtp_pop);



//        final EditText pincode_prompt = (EditText)promptsView.findViewById(R.id.pincode_prompts);
//        userInput.setText(address);
//        pincode_prompt.setText(pincode);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Please Enter OTP")
//                .setIcon(R.drawable.w)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id){
                                final String getOTP = otp.getText().toString().trim();
                                if(getOTP.equalsIgnoreCase("")) {
                                    Financial_Reports_popUpForOTP();

                                }
                                else {
                                    final ProgressDialog loading = ProgressDialog.show(ActivityLoyalityCust.this, "OTP Checking", "Please Wait...", false, false);
                                    StringRequest request = new StringRequest(Request.Method.POST, Config.VERIFY_OTP, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            loading.dismiss();
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String message = jsonObject.getString("success");
                                                if (message.equalsIgnoreCase("1")) {
//                                                Toast.makeText(getApplicationContext(),"OTP Genrated",Toast.LENGTH_SHORT).show();


                                                    //   mydb.insertotpdata(name,mob,store_id);
                                                    Intent intent4 = new Intent(getApplicationContext(),ReportDayOpenCash.class);
                                                    startActivity(intent4);

                                                } else if (message.equalsIgnoreCase("0")) {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getApplicationContext(), "OTP NOT MATCH", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            loading.dismiss();
                                            String message = null;
                                            if (volleyError instanceof NetworkError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ServerError) {
                                                message = "Something went wrong. Please try again after some time!!";
                                            } else if (volleyError instanceof AuthFailureError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof ParseError) {
                                                message = "Please try again after some time!!";
                                            } else if (volleyError instanceof NoConnectionError) {
                                                message = "Please check your connection!";
                                            } else if (volleyError instanceof TimeoutError) {
                                                message = "Connection TimeOut! Please check your internet connection.";
                                            }
                                            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                                        }


                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<String, String>();


                                            params.put("STORE_ID", store_id);
                                            params.put("OTP", getOTP);
//                                        params.put("TELE","8310876676");
                                            return params;
                                        }
                                    };

                                    RequestQueue queue = Volley.newRequestQueue(ActivityLoyalityCust.this);
                                    queue.add(request);
                                }


                            }
                        })
//                .setNeutralButton("Resend",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(final DialogInterface dialog, int id) {
//
////                                final ProgressDialog loading = ProgressDialog.show(Activitypurchase.this, "OTP REsending", "Please Wait...", false, false);
//
//                                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.RESEND_OTP, new Response.Listener<String>() {
//                                    @Override
//                                    public void onResponse(String response) {
////                                        loading.dismiss();
//                                        Log.e("OTP resend",response);
//
//                                        popUpForOTP();
//
//
//
//
//
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//
////                                        loading.dismiss();
//                                    }
//                                })
//                                {
//                                    @Override
//                                    protected Map<String, String> getParams() throws AuthFailureError {
//                                        Map<String,String> params = new HashMap<String, String>();
//                                        params.put("STORE_ID",store_id);
//                                        params.put("USER_NAME",name);
//                                        params.put("TELE","8310876676");
//
//                                        return params;
//                                    }
//                                };
//
//                                RequestQueue queue = Volley.newRequestQueue(Activitypurchase.this);
//                                queue.add(stringRequest);

//                            }
//                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

}
