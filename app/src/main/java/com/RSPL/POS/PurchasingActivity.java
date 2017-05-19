package com.RSPL.POS;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;


public class PurchasingActivity extends Activity implements View.OnClickListener {

    ActionBar actionBar;
    Button submit;
    Intent intent;
    String CurrentDate,TicketId;
    SimpleDateFormat ticketId;
    public TextView Purchasing;
    private EditText editOldPurchase,editNewPurchase,editHoldPurchase,editWithPoInventory,editWithoutPoInventory,editHoldInventory,editIntegrateDsbtr,editStockAdjustment,editExpiredProduct,editDirectPayment,editIndirectPayment,editWithGRNIdVendRetrn,editWithoutGRNIdVendRetrn;
    private RadioGroup radioScreenGroup;
    private RadioButton radioButtonOldPurchase,radioButtonNewPurchase,radioButtonHoldPurchase,radioButtonWithPoInventory,radioButtonWithoutPoInventory,radioButtonHoldInventory,radioButtonIntegrateDsbtr,radioButtonStockAdjustment,radioButtonExpiredProduct,radioButtonDirectPayment,radioButtonIndirectPayment,radioButtonWithGRNIdVendRetrn,radioButtonWithoutGRNIdVendRetrn;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchasing_status);

        submit = (Button) findViewById(R.id.Submit);
        Purchasing = (TextView) findViewById(R.id.purchasing);

        actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
        CurrentDate = s.toString();
        Log.e("current date :", CurrentDate);

        submit.setOnClickListener(this);

        radioScreenGroup=(RadioGroup)findViewById(R.id.radioGroup);

        radioButtonOldPurchase=(RadioButton)findViewById(R.id.radioButton1) ;
        radioButtonNewPurchase=(RadioButton)findViewById(R.id.radioButton2) ;
        radioButtonHoldPurchase=(RadioButton)findViewById(R.id.radioButton3) ;
        radioButtonWithPoInventory=(RadioButton)findViewById(R.id.radioButton4) ;
        radioButtonWithoutPoInventory=(RadioButton)findViewById(R.id.radioButton5) ;
        radioButtonHoldInventory=(RadioButton)findViewById(R.id.radioButton6) ;
        radioButtonIntegrateDsbtr=(RadioButton)findViewById(R.id.radioButton7) ;
        radioButtonStockAdjustment=(RadioButton)findViewById(R.id.radioButton8) ;
        radioButtonExpiredProduct=(RadioButton)findViewById(R.id.radioButton9) ;
        radioButtonDirectPayment=(RadioButton)findViewById(R.id.radioButton10) ;
        radioButtonIndirectPayment=(RadioButton)findViewById(R.id.radioButton11) ;
        radioButtonWithGRNIdVendRetrn=(RadioButton)findViewById(R.id.radioButton12) ;
        radioButtonWithoutGRNIdVendRetrn=(RadioButton)findViewById(R.id.radioButton13) ;

        editOldPurchase=(EditText)findViewById(R.id.editText1);
        editNewPurchase=(EditText)findViewById(R.id.editText2);
        editHoldPurchase=(EditText)findViewById(R.id.editText3);
        editWithPoInventory=(EditText)findViewById(R.id.editText4);
        editWithoutPoInventory=(EditText)findViewById(R.id.editText5);
        editHoldInventory=(EditText)findViewById(R.id.editText6);
        editIntegrateDsbtr=(EditText)findViewById(R.id.editText7);
        editStockAdjustment=(EditText)findViewById(R.id.editText8);
        editExpiredProduct=(EditText)findViewById(R.id.editText9);
        editDirectPayment=(EditText)findViewById(R.id.editText10);
        editIndirectPayment=(EditText)findViewById(R.id.editText11);
        editWithGRNIdVendRetrn=(EditText)findViewById(R.id.editText12);
        editWithoutGRNIdVendRetrn=(EditText)findViewById(R.id.editText13);

        radioScreenGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioButton1)
                {
                    editOldPurchase.setVisibility(View.VISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton2)
                {
                    editNewPurchase.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton3)
                {
                    editHoldPurchase.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton4)
                {
                    editWithPoInventory.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton5)
                {
                    editWithoutPoInventory.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton6)
                {
                    editHoldInventory.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton7)
                {
                    editIntegrateDsbtr.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton8)
                {
                    editStockAdjustment.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton9)
                {
                    editExpiredProduct.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton10)
                {
                    editDirectPayment.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton11)
                {
                    editIndirectPayment.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton12)
                {
                    editWithGRNIdVendRetrn.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithoutGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
                else if(checkedId==R.id.radioButton13)
                {
                    editWithoutGRNIdVendRetrn.setVisibility(View.VISIBLE);
                    editOldPurchase.setVisibility(View.INVISIBLE);
                    editNewPurchase.setVisibility(View.INVISIBLE);
                    editHoldPurchase.setVisibility(View.INVISIBLE);
                    editWithPoInventory.setVisibility(View.INVISIBLE);
                    editWithoutPoInventory.setVisibility(View.INVISIBLE);
                    editHoldInventory.setVisibility(View.INVISIBLE);
                    editIntegrateDsbtr.setVisibility(View.INVISIBLE);
                    editStockAdjustment.setVisibility(View.INVISIBLE);
                    editExpiredProduct.setVisibility(View.INVISIBLE);
                    editDirectPayment.setVisibility(View.INVISIBLE);
                    editIndirectPayment.setVisibility(View.INVISIBLE);
                    editWithGRNIdVendRetrn.setVisibility(View.INVISIBLE);
                }
            }
        });


    }


    @Override
    public void onClick(View v) {

        if (radioButtonOldPurchase.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editOldPurchase.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonOldPurchase.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingOldPurchseIssues(editOldPurchase.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingOldPurchse();
                editOldPurchase.setText("");
            }
        }

        else if (radioButtonNewPurchase.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editNewPurchase.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonNewPurchase.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingNewPurchseIssues(editNewPurchase.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingNewPurchse();
                editNewPurchase.setText("");
            }
        }

        else if (radioButtonHoldPurchase.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editHoldPurchase.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonHoldPurchase.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingHoldPurchseIssues(editHoldPurchase.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingHoldPurchse();
                editHoldPurchase.setText("");
            }
        }

        else if (radioButtonWithPoInventory.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editWithPoInventory.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonWithPoInventory.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingWithPoInvntryIssues(editWithPoInventory.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingWithPo();
                editWithPoInventory.setText("");
            }
        }

        else if (radioButtonWithoutPoInventory.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editWithoutPoInventory.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonWithoutPoInventory.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingWithoutPoInvntryIssues(editWithoutPoInventory.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingWithoutPo();
                editWithoutPoInventory.setText("");
            }
        }

        else if (radioButtonHoldInventory.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editHoldInventory.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonHoldInventory.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingHoldPoInvntryIssues(editHoldInventory.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingHoldInvntry();
                editHoldInventory.setText("");
            }
        }

        else if (radioButtonIntegrateDsbtr.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editIntegrateDsbtr.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonIntegrateDsbtr.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingIntegratdDsbtrIssues(editIntegrateDsbtr.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingIntgrtdDsbtr();
                editIntegrateDsbtr.setText("");
            }
        }

        else if (radioButtonStockAdjustment.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editStockAdjustment.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonStockAdjustment.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingStockAdjstmntIssues(editStockAdjustment.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingStockAdjsmnt();
                editStockAdjustment.setText("");
            }
        }

        else if (radioButtonExpiredProduct.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editExpiredProduct.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonExpiredProduct.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingExpiredProdctIssues(editExpiredProduct.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingExpiredPrdct();
                editExpiredProduct.setText("");
            }
        }

        else if (radioButtonDirectPayment.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editDirectPayment.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonDirectPayment.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingDirectVendrIssues(editDirectPayment.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingDirectPaymnt();
                editDirectPayment.setText("");
            }
        }

        else if (radioButtonIndirectPayment.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editIndirectPayment.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonIndirectPayment.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingIndirectVendrIssues(editIndirectPayment.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingIndirectPaymnt();
                editIndirectPayment.setText("");
            }
        }

        else if (radioButtonWithGRNIdVendRetrn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editWithGRNIdVendRetrn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonWithGRNIdVendRetrn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingDirectGrnIssues(editWithGRNIdVendRetrn.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingWithGrnId();
                editWithGRNIdVendRetrn.setText("");
            }
        }

        else if (radioButtonWithoutGRNIdVendRetrn.isChecked()) {

            Log.e("@@@@@@@@@@@@@@","button pressed..!!!!!!!!!!");

            if ( ( editWithoutGRNIdVendRetrn.getText().toString().equals("")))
            {
                Toast.makeText(getApplicationContext(),"Please Enter Mendatory Fields",Toast.LENGTH_LONG).show();
                return;
            }

            else
            {
                String PurchaseScreen = Purchasing.getText().toString();
                String SubScreen = radioButtonWithoutGRNIdVendRetrn.getText().toString();
                final Calendar calendar = Calendar.getInstance();
                ticketId = new SimpleDateFormat("yyyyMMDDHHmmss");
                TicketId = ticketId.format(calendar.getTime());
                Log.e("############", TicketId);
                DBhelper helper = new DBhelper(getApplicationContext());
                helper.getWritableDatabase();
                helper.InsertPurchasingIndirectGrnIssues(editWithoutGRNIdVendRetrn.getText().toString(),CurrentDate.toString(),PurchaseScreen,SubScreen,TicketId);
                Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                UpdatePurchasingWithoutGrnId();
                editWithoutGRNIdVendRetrn.setText("");
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);
        return true;

    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.
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
        if (id == R.id.action_settings) {

            Intent i=new Intent(PurchasingActivity.this, ActivityStoreScreenFeedback.class);
            startActivity(i);
            return true;
        }
        if(id==R.id.action_settinginfo){
        ShowIncentive showIncentive = new ShowIncentive(PurchasingActivity.this);
        showIncentive.show();
        return true;
    }

        return super.onOptionsItemSelected(item);
    }

    public void UpdatePurchasingOldPurchse() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  OldPurchse =editOldPurchase.getText().toString().trim();

        class UpdatePurchasingOldPurchse extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"25");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Old Purchase");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,OldPurchse);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingOldPurchse updateStore = new UpdatePurchasingOldPurchse();
        updateStore.execute();
    }

    public void UpdatePurchasingNewPurchse() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  NewPurchse =editNewPurchase.getText().toString().trim();

        class UpdatePurchasingNewPurchse extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"26");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"New Purchase");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,NewPurchse);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingNewPurchse updateStore = new UpdatePurchasingNewPurchse();
        updateStore.execute();
    }

    public void UpdatePurchasingHoldPurchse() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  HoldPurchse =editHoldPurchase.getText().toString().trim();

        class UpdatePurchasingHoldPurchse extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"27");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Hold Purchase");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,HoldPurchse);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingHoldPurchse updateStore = new UpdatePurchasingHoldPurchse();
        updateStore.execute();
    }

    public void UpdatePurchasingWithPo() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  WithPo =editWithPoInventory.getText().toString().trim();

        class UpdatePurchasingWithPo extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"28");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"With Po Inventory");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithPo);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingWithPo updateStore = new UpdatePurchasingWithPo();
        updateStore.execute();
    }

    public void UpdatePurchasingWithoutPo() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  WithoutPo =editWithoutPoInventory.getText().toString().trim();

        class UpdatePurchasingWithoutPo extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"29");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Without Po Inventory");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithoutPo);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingWithoutPo updateStore = new UpdatePurchasingWithoutPo();
        updateStore.execute();
    }

    public void UpdatePurchasingHoldInvntry() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  HoldInvntry =editHoldInventory.getText().toString().trim();

        class UpdatePurchasingHoldInvntry extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"30");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Hold Inventory");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,HoldInvntry);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingHoldInvntry updateStore = new UpdatePurchasingHoldInvntry();
        updateStore.execute();
    }

    public void UpdatePurchasingIntgrtdDsbtr() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  IntgrtdDsbtr =editIntegrateDsbtr.getText().toString().trim();

        class UpdatePurchasingIntgrtdDsbtr extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"31");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Integrated With Distributor");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,IntgrtdDsbtr);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingIntgrtdDsbtr updateStore = new UpdatePurchasingIntgrtdDsbtr();
        updateStore.execute();
    }

    public void UpdatePurchasingStockAdjsmnt() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  StockAdjsmnt =editStockAdjustment.getText().toString().trim();

        class UpdatePurchasingStockAdjsmnt extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"32");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Stock Adjustment");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,StockAdjsmnt);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingStockAdjsmnt updateStore = new UpdatePurchasingStockAdjsmnt();
        updateStore.execute();
    }

    public void UpdatePurchasingExpiredPrdct() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  ExpiredPrdct =editExpiredProduct.getText().toString().trim();

        class UpdatePurchasingExpiredPrdct extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"33");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Expired Product");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,ExpiredPrdct);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingExpiredPrdct updateStore = new UpdatePurchasingExpiredPrdct();
        updateStore.execute();
    }

    public void UpdatePurchasingDirectPaymnt() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  DirectPaymnt =editDirectPayment.getText().toString().trim();

        class UpdatePurchasingDirectPaymnt extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"34");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Direct Vendor Payment");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,DirectPaymnt);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingDirectPaymnt updateStore = new UpdatePurchasingDirectPaymnt();
        updateStore.execute();
    }

    public void UpdatePurchasingIndirectPaymnt() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  IndirectPaymnt =editIndirectPayment.getText().toString().trim();

        class UpdatePurchasingIndirectPaymnt extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"35");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Indirect Vendor Payment");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,IndirectPaymnt);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingIndirectPaymnt updateStore = new UpdatePurchasingIndirectPaymnt();
        updateStore.execute();
    }

    public void UpdatePurchasingWithGrnId() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  WithGrnId =editWithGRNIdVendRetrn.getText().toString().trim();

        class UpdatePurchasingWithGrnId extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"36");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Vendor Return With GRN ID");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithGrnId);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingWithGrnId updateStore = new UpdatePurchasingWithGrnId();
        updateStore.execute();
    }

    public void UpdatePurchasingWithoutGrnId() {

        final DBhelper mydb = new DBhelper(PurchasingActivity.this);
        final String  WithoutGrnId =editWithoutGRNIdVendRetrn.getText().toString().trim();

        class UpdatePurchasingWithoutGrnId extends AsyncTask<Void, Void, String> {
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
                try{
                    String currentdate= CurrentDate.toString();
                    PersistenceManager.saveStoreId(PurchasingActivity.this,mydb.getStoreid().toString().replace("[", "").replace("]", ""));
                    final String  store_id= PersistenceManager.getStoreId(PurchasingActivity.this);

                    Log.e("STORE ID",store_id);
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.COLUMN_ID,"37");
                    hashMap.put(Config.COLUMN_RETAIL_STORE_ID,store_id);
                    hashMap.put(Config.COLUMN_MASTER_SCREEN,"Purchasing");
                    hashMap.put(Config.COLUMN_SUB_SCREEN,"Vendor Return Without GRN ID");
                    hashMap.put(Config.COLUMN_RETAIL_CHANGE_REQUEST,WithoutGrnId);
                    hashMap.put(Config.COLUMN_CURRENT_DATE,currentdate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://99sync.eu-gb.mybluemix.net/Store_Screen_Feedback/RETAIL_STORE_SCREEN_DESC.jsp",hashMap);
                    Log.d("Login attempt", s.toString());

                    // success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());
                        //return s.getString(TAG_MESSAGE);

                    } else {
                        //return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

        }
        UpdatePurchasingWithoutGrnId updateStore = new UpdatePurchasingWithoutGrnId();
        updateStore.execute();
    }
}
