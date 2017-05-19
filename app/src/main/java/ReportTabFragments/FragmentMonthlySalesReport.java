package ReportTabFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowMonthlySalesReportListActivity;
import com.ngx.USBPrinter;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.SaleReportModel;
import Pojo.Sales;


public class FragmentMonthlySalesReport extends android.support.v4.app.Fragment{

    ListView listview;
    FragmentListMonthlySalesAdapter totalListAdapter;
    ArrayList<SaleReportModel>GetMonthlySales;
    private TextView TotalCash;
    private TextView TotalCredit;
    private TextView GrandTotal;
    private TextView Totalitems;
    Button emailbutton;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    DBhelper helper;

    ArrayList<Sales> get_Transcation_List;
    ArrayList<String> store_name;
    TextPaint tp = new TextPaint();
    TextPaint tp32 = new TextPaint();
    TextPaint tp36 = new TextPaint();
    Typeface tff;
    String store, storeAddress, City, Storenumber, Str, AlternateNo, Footer, amountdiscount
            ,Grandtotal,Tele2Shown,FooterShown,MRPisShown;
    public static USBPrinter UsPrinter = USBPrinter.INSTANCE;
    private static final int BILL_DATE_COLUMN = 14;
    private static final int PRINT_LEFT_MARGIN = 2;
    String TOTAL_ITEMS = "Total Items:";
    String BILL_TOTAL = "Bill Total:";
    String BILL_DISC = "Discount:";
    String NETPAYAMOUNT = "Net Amount:";
    String RECEIVEAMOUNT = "Recieved Amount:";
    String BALANCEAMOUNT = "Balance to be Paid:";
    private String eol = "\n";
    TextView tvsaledate,tvusername,tvbillno,tvtotal,tvcardno,tvbilltotal,tvcash,tvcredit,tvgrandtotal;
    String backroundcolour,colorchange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_monthly_sale_report, container, false);
        tff = Typeface.createFromAsset(getContext().getAssets(), "Fonts/DroidSansMono.ttf");
        helper = new DBhelper(getContext());

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

        LinearLayout layout=(LinearLayout)view.findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        tvsaledate = (TextView) view.findViewById(R.id.tvDate);
        tvsaledate.setTextSize(Float.parseFloat(textsize));

        tvusername = (TextView) view.findViewById(R.id.tvUserNm);
        tvusername.setTextSize(Float.parseFloat(textsize));

        tvbillno = (TextView) view.findViewById(R.id.tvTriId);
        tvbillno.setTextSize(Float.parseFloat(textsize));

        tvtotal = (TextView) view.findViewById(R.id.tvTotal);
        tvtotal.setTextSize(Float.parseFloat(textsize));

        tvcardno = (TextView) view.findViewById(R.id.tvCardNo);
        tvcardno.setTextSize(Float.parseFloat(textsize));

        tvbilltotal = (TextView) view.findViewById(R.id.discount_text);
        tvbilltotal.setTextSize(Float.parseFloat(textsize));

        tvcash= (TextView) view.findViewById(R.id.discount_text1);
        tvcash.setTextSize(Float.parseFloat(textsize));

        tvcredit = (TextView) view.findViewById(R.id.discount_text2);
        tvcredit.setTextSize(Float.parseFloat(textsize));

        tvgrandtotal = (TextView) view.findViewById(R.id.discount_text3);
        tvgrandtotal.setTextSize(Float.parseFloat(textsize));



        listview = (ListView)view.findViewById(R.id.lv_SaleReport);

        TotalCash = (TextView) view.findViewById(R.id.ReportSalesTotalcash);
        TotalCredit = (TextView) view.findViewById(R.id.ReportSalesTotalcredit);
        Totalitems = (TextView)view. findViewById(R.id.ReportSalesTotalitem);
        GrandTotal = (TextView) view.findViewById(R.id.ReportSalesGrandTotal);
        TotalCash.setTextSize(Float.parseFloat(textsize));
        TotalCredit.setTextSize(Float.parseFloat(textsize));
        Totalitems.setTextSize(Float.parseFloat(textsize));
        GrandTotal.setTextSize(Float.parseFloat(textsize));


        DecimalFormat f=new DecimalFormat("##.00");

        float cash = helper.getMonthlyCashReport();
        TotalCash.setText(f.format(cash));

        float credit = helper.getMonthlyCreditReport();
        TotalCredit.setText(f.format(credit));

        Float sum = cash+ credit;
        GrandTotal.setText(f.format(sum));

        GetMonthlySales=helper.getMonthlySalesReport();
        Collections.reverse(GetMonthlySales);
                if(GetMonthlySales!=null) {
                    if(totalListAdapter==null)
                    {
                        totalListAdapter = new FragmentListMonthlySalesAdapter(GetMonthlySales, getContext(), FragmentMonthlySalesReport.this);
                        listview.setAdapter(totalListAdapter);
                    }
                    else
                    {
                        totalListAdapter = new FragmentListMonthlySalesAdapter(GetMonthlySales, getContext(), FragmentMonthlySalesReport.this);
                        listview.setAdapter(totalListAdapter);
                        totalListAdapter.notifyDataSetChanged();
                    }
                }
        setSummaryRowTotal();

        return view;
    }

    public void GetWeeklyDetailPage(String BillNo){

        Bundle dataBundle = new Bundle();
        dataBundle.putString("id", BillNo);

        Intent intent = new Intent(getContext(), ShowMonthlySalesReportListActivity.class);
        intent.putExtras(dataBundle);
        startActivity(intent);
    }

    public void setSummaryRowTotal() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = totalListAdapter.getGrnTotal();
        String GrandVal = f.format(Getval);
        TotalCash.setText(GrandVal);

        float sum =Float.parseFloat(TotalCash.getText().toString())+Float.parseFloat(TotalCredit.getText().toString());


        GrandTotal.setText(f.format(sum));

        int Getitem = totalListAdapter.getCount();
        int Allitem = Getitem;
        String GETITEM = Integer.toString(Allitem);
        Totalitems.setText(GETITEM);
    }


    public void insertEmaildata() {

        class UpdateReportForSales extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;


            @Override
            protected String doInBackground(Void... params) {
                helper.insertemailmonthlysalesreport(totalListAdapter.getList());
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(, "UPDATING...", "Wait...", false, false);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();

                //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();
            }

        }
        UpdateReportForSales updateReportForSales = new UpdateReportForSales();
        updateReportForSales.execute();

    }


    String getSystemCurrentTime() {

        Long value = System.currentTimeMillis();
        String result = Long.toString(value);
        return result;
    }



    public void Updatemonthlyysalesreport() {


        for (SaleReportModel purchase : totalListAdapter.getList()) {

            final String reporttransid = String.valueOf(purchase.getTransId());
            final String reportgrandtotal = String.valueOf(purchase.getGrnTotl());
            final String reportuom = purchase.getUom();
            final String reportproductname = purchase.getProdNm();
            final String reportexpdate = String.valueOf(purchase.getExp());
            final String reportSprice= purchase.getPrice();
            final String reportqty = String.valueOf(purchase.getQty());
            final String reportposuser = String.valueOf(purchase.getUserNm());
            final String reportsaledate = String.valueOf(purchase.getSaleDate());


            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;

                @Override
                protected String doInBackground(Void... params) {
                    try{

                    HashMap<String, String> hashMap = new HashMap<>();

                    hashMap.put(Config.Retail_report_ticketid,getSystemCurrentTime());
                    hashMap.put(Config.Retail_report_transid, reporttransid);
                    hashMap.put(Config.Retail_report_grandtotal, reportgrandtotal);
                    hashMap.put(Config.Retail_report_uom, reportuom);
                    hashMap.put(Config.Retail_report_productname, reportproductname);
                    hashMap.put(Config.Retail_report_expdate, reportexpdate);
                    hashMap.put(Config.Retail_report_sprice,reportSprice);
                    hashMap.put(Config.Retail_report_qty,reportqty);
                    hashMap.put(Config.Retail_Pos_user,reportposuser);
                    hashMap.put(Config.Retail_report_saledate, reportsaledate);

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/E_mail/retail_str_sales_detail_mail.php", hashMap);
                        Log.d("Login attempt", s.toString());

                        // success tag for json
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
                    // loading = ProgressDialog.show(activity_inventory.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //     loading.dismiss();
                    //  Toast.makeText(activity_inventory.this, s, Toast.LENGTH_LONG).show();
                }

            }
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }

    }

    public  void emailbuttondialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        alertDialog.setTitle("Confirm Email....");

        alertDialog.setMessage("Are you sure you want email this report?");

        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {


                try {

                    insertEmaildata();
                    Updatemonthlyysalesreport();
                    Toast.makeText(getContext(),"Email Sent",Toast.LENGTH_LONG).show();
                    getActivity().finish();


                }catch (Exception e){

                    e.printStackTrace();
                }
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void printBillDialog(String mValue)

    {

        try

        {

            String bill_Transaction_id = mValue.toString();

            Log.e("!!!!!!!!!!!!!!!!!", "" + bill_Transaction_id.toString());

            if (bill_Transaction_id.equals("")) {

                Toast.makeText(getActivity(), "Enter Transaction id", Toast.LENGTH_LONG).show();

                return;

            } else {

//DBhelper dBhelper=new DBhelper(ActivitySalesbill.this);

                get_Transcation_List = helper.get_Transaction_Id_Bill(bill_Transaction_id);
//////////////////////////////////////Printer testing ///////////////////////////////////////

                if (get_Transcation_List.size() > 0)

                {

                    Sales card_Details = helper.get_Card_Transaction_Id_Bill_Master(mValue);
                    String card_No = card_Details.getCardHolderNumber();
                    String cardGrandTotal = card_Details.getCardGrandTotal();
                    String customer_name_paybycard = card_Details.getCustomerName();
                    String saledate_paybycard = card_Details.getDate();
                    String saletime_paybycard = card_Details.getSalesTime();

                    Sales getAllmasterinfoforprinter = helper.get_Transaction_Id_Bill_Master(mValue);
                    float expectdchnge = getAllmasterinfoforprinter.getSaveEXPECTEDBILLAMOUNT();
                    float fnlillamt = getAllmasterinfoforprinter.getSaveFINALBILLAMOUNT();
                    float receidamt = getAllmasterinfoforprinter.getSaveRECEIVEDBILLAMOUNT();
                    float totalbillamt = getAllmasterinfoforprinter.getSaveTOTALBILLAMOUNT();
                    float savetotalbilldscount = getAllmasterinfoforprinter.getSaveTOTALBIllDiscount();

                    String customer_name = getAllmasterinfoforprinter.getCustomerName();
                    String saledate = getAllmasterinfoforprinter.getDate();
                    String saletime = getAllmasterinfoforprinter.getSalesTime();

                    if (totalbillamt!=0.00 && expectdchnge != 0.00 )

                    {
                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));
                        tp.setTypeface(tff);
                        store_name = helper.getAllStores();
                        store = (store_name.get(1).toString());
                        storeAddress = store_name.get(2).toString();
                        City = store_name.get(3).toString();
                        Storenumber = store_name.get(4).toString();
                        AlternateNo = store_name.get(5).toString();
                        Footer = store_name.get(6).toString();
                        Grandtotal = GrandTotal.getText().toString();
                        DecimalFormat f = new DecimalFormat("##.00");
                        int paybycashbillcopy=1;
                        for (int i = 1; i <= paybycashbillcopy; i++) {
                            StringBuilder strbuilder = new StringBuilder();
                            strbuilder.append(store + "\n");
                            strbuilder.append(storeAddress + "\n");
                            strbuilder.append(City + "\n");
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                            strbuilder.setLength(0);
                                  /*  if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {
                                        strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");

                                    } else {*/
                            strbuilder.append("Tel:" + Storenumber);
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                            strbuilder.setLength(0);
                           // strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            //  }
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            if (TextUtils.isEmpty(customer_name) || customer_name.matches(""))
                            {
                                strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            }else {
                                strbuilder.append("Customer Name:" + customer_name);
                                strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            }
                                           /* if (card_No.matches(" "))
                                            {
                                                strbuilder.append(ActivitySalesbill.this.eol);
                                            }
                                            else {
                                                strbuilder.append("Card Number:" + "XXXX-XXXX-XXXX-" + card_No);
                                                strbuilder.append(ActivitySalesbill.this.eol);
                                            }*/

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                            Date testDate = null;
                            try {
                                testDate = sdf.parse(saledate);
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat(" EEEMMMdd yy");

                            String newFormat = formatter.format(testDate);



                            // Date dt = new Date(saletime);
                            SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
                            Date time1 = null;
                            try {
                                time1 = timeformat.parse(saletime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat newtimeee = new SimpleDateFormat("hh:mm a");
                            String timeeeee = newtimeee.format(time1);

                            // SimpleDateFormat outdate = new SimpleDateFormat("yyMMMdd");
                            String sales_date_reprint = newFormat.concat(" ").concat(timeeeee);

                            String formattedDate;
                            formattedDate = new SimpleDateFormat("EEE,ddMMMyy hh:mma").format(Calendar.getInstance().getTime());

                            int billDtSpace = 39 - ((String.valueOf(mValue.toString()).length() + BILL_DATE_COLUMN) + sales_date_reprint.length());

                            strbuilder.append("Bill No:");

                            strbuilder.append(mValue.toString());

                            strbuilder.append(getSpacer(billDtSpace));

                            strbuilder.append(sales_date_reprint);

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(getDividerSales());

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(" S.Price" + "    " + "Qty" + "  " + "ItemDisc" + "   " + "Total");

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(getDividerSales());

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            for (Sales prod : get_Transcation_List) {

                                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + prod.getProductName());

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                                String printSPrice = String.valueOf(f.format(prod.getSPrice()));

                                int spaces = 9 - printSPrice.length();

                                if (spaces == 0) {

                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                } else {

                                    strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                }

                                String printqty = String.valueOf(prod.getQuantity());

                                spaces = 3 - printqty.length();

                                strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));

                                spaces = 7 - printDiscount.length();

                                if (spaces == 0) {

                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                } else {

                                    strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                }

                                if (prod.getDiscountamount() > 0.00) {

                                    String printtotal = String.valueOf(f.format(prod.getDiscountamount()));

                                    spaces = 8 - printtotal.length();

                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                } else {

                                    String printtotal = String.valueOf(f.format(prod.getTotal()));

                                    spaces = 8 - printtotal.length();

                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                }

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            }



                            strbuilder.append(getDividerSales());

                            DecimalFormat f1 = new DecimalFormat("##.00");
                            int poud = 0;

                            for (Sales prod : get_Transcation_List) {

                                poud++;

                            }

                            Log.e("mastervalueforprinter", "" + expectdchnge + fnlillamt + receidamt + totalbillamt + savetotalbilldscount);

                            String Roundoffvalue = String.valueOf(f1.format(fnlillamt));

                            // if (Totalsavings.getText().toString().equals("")||Totalsavings.getText().toString().equals("0.00")||Totalsavings.getText().toString().equals(".00")) {

                            int spaces = 10 - String.valueOf(poud).length();

                            int amtspaces = 8 - Roundoffvalue.length();

                            strbuilder.append(TOTAL_ITEMS + poud + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            int Disamtspaces = getSpacer(24).length() - BILL_DISC.length();

                            int netvalspce = 9 - String.valueOf(savetotalbilldscount).length();

                            strbuilder.append(getSpacer(Disamtspaces) + BILL_DISC + getSpacer(netvalspce) + f.format(savetotalbilldscount) + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(getSpacer(20) + "-------------");

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            amtspaces = getSpacer(24).length() - NETPAYAMOUNT.length();
                            netvalspce = 9 - String.valueOf(totalbillamt).length();
                            strbuilder.append(getSpacer(amtspaces) + NETPAYAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(totalbillamt))) + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            amtspaces = getSpacer(24).length() - RECEIVEAMOUNT.length();

                            netvalspce = 9 - (f.format(Double.parseDouble(String.valueOf(receidamt)))).length();

                            strbuilder.append(getSpacer(amtspaces) + RECEIVEAMOUNT + getSpacer(netvalspce) + f.format(Double.parseDouble(String.valueOf(receidamt))) + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            amtspaces = getSpacer(24).length() - BALANCEAMOUNT.length();

                            netvalspce = 9 - String.valueOf(f.format(expectdchnge)).length();

                            strbuilder.append(getSpacer(amtspaces) + BALANCEAMOUNT + getSpacer(netvalspce) + String.valueOf(f.format(expectdchnge


                            )).replace("-"," ") + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);


                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

                            strbuilder.setLength(0);

                            strbuilder.append(getDividerSales());


                            int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;

                            strbuilder.append(Footer);

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);



                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            UsPrinter.print();
                            UsPrinter.FeedLine();
                            UsPrinter.FullCut();

                        }

                    }
                    else

                    {

                        tp.setColor(Color.BLUE);

                        tp.setTextSize(26);
                        tp32.setTextSize(40);
                        tp36.setTextSize(34);
                        tp32.setTypeface(Typeface.create("Arial",Typeface.BOLD));

                        tp.setTypeface(tff);

                        store_name = helper.getAllStores();

                        store = (store_name.get(1).toString());

                        storeAddress = store_name.get(2).toString();

                        City = store_name.get(3).toString();

                        Storenumber = store_name.get(4).toString();

                        AlternateNo = store_name.get(5).toString();

                        Footer = store_name.get(6).toString();

                        Grandtotal = GrandTotal.getText().toString();

                        //Footer = store_name.get(6).toString();

                        //amountdiscount = discountamount.getText().toString();

                        //Totalsavings.setText(amountdiscount);

                        DecimalFormat f = new DecimalFormat("##.00");
                        int paybycashbillcopy=1;
                        for (int i = 1; i <= paybycashbillcopy; i++) {

                            StringBuilder strbuilder = new StringBuilder();

                            strbuilder.append(store + "\n");

                            strbuilder.append(storeAddress + "\n");

                            strbuilder.append(City + "\n");
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp32);
                            strbuilder.setLength(0);

//                                    if (!TextUtils.isEmpty(AlternateNo) && Tele2Shown.matches("Y")) {

                            //     strbuilder.append("Tel:" + Storenumber + "," + AlternateNo + "\n");

                            // } else {

                            strbuilder.append("Tel:" + Storenumber);
                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp36);
                            strbuilder.setLength(0);

                           // strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            //  }

                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);

                            strbuilder.setLength(0);
                            if (TextUtils.isEmpty(customer_name)||customer_name.matches(""))
                            {strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            }else {
                                strbuilder.append("Customer Name:" + customer_name);
                                strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            }
                          /*  if (card_No.matches(" "))
                            {
                                strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            }
                            else {
                                strbuilder.append("Card Number:" + "XXXX-XXXX-XXXX-" + card_No);
                                strbuilder.append(FragmentMonthlySalesReport.this.eol);
                            }*/


                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                            Date testDate = null;
                            try {
                                testDate = sdf.parse(saledate_paybycard);
                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                            SimpleDateFormat formatter = new SimpleDateFormat("EEE,MMM dd,yy");

                            String newFormat = formatter.format(testDate);

                            // Date dt = new Date(saletime);
                            SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm:ss");
                            Date time1 = null;
                            try {
                                time1 = timeformat.parse(saletime_paybycard);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            SimpleDateFormat newtimeee = new SimpleDateFormat("hh:mm a");
                            String timeeeee = newtimeee.format(time1);

                            // SimpleDateFormat outdate = new SimpleDateFormat("yyMMMdd");
                            String sales_date_reprint_bycard = newFormat.concat(" ").concat(timeeeee);

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            String formattedDate;



                            int billDtSpace = 39 - ((String.valueOf(mValue.toString()).length() + BILL_DATE_COLUMN) + sales_date_reprint_bycard.length());

                            strbuilder.append("Bill No:");

                            strbuilder.append(mValue.toString());

                            strbuilder.append(getSpacer(billDtSpace));

                            strbuilder.append(sales_date_reprint_bycard);

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(getDividerSales());

                            //  strbuilder.append(FragmentDailySalesReport.this.eol);

                            //  if (MRPisShown.matches("Y")) {

                            //      strbuilder.append(" ProductName" + "        " + "MRP");

                            //    strbuilder.append(FragmentDailySalesReport.this.eol);

                            strbuilder.append(" S.Price" + " " + "Qty" + " " + "ItemDisc" + " " + "Total");

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            strbuilder.append(getDividerSales());

                            //   strbuilder.append(FragmentDailySalesReport.this.eol);

                            for (Sales prod : get_Transcation_List) {

                                String ProuctName = prod.getProductName();

                                if (ProuctName.length() >= 21) {

                                    ProuctName = ProuctName.substring(0, 21);

                                }

                                strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + ProuctName);

                                String printMrp = String.valueOf(f.format(prod.getMrp()));

                                int spaces = 10 - printMrp.length();

                                if (spaces == 0) {

                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printMrp + getSpacer(PRINT_LEFT_MARGIN));

                                } else {

                                    strbuilder.append(getSpacer(spaces) + printMrp + getSpacer(PRINT_LEFT_MARGIN));

                                }

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                                String printSPrice = String.valueOf(f.format(prod.getSPrice()));

                                spaces = 9 - printSPrice.length();

                                if (spaces == 0) {

                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                } else {

                                    strbuilder.append(getSpacer(spaces) + printSPrice + getSpacer(PRINT_LEFT_MARGIN));

                                }

                                String printqty = String.valueOf(prod.getQuantity());

                                spaces = 3 - printqty.length();

                                strbuilder.append(getSpacer(spaces) + printqty + getSpacer(PRINT_LEFT_MARGIN));

                                String printDiscount = String.valueOf(f.format(prod.getDiscountsales()));

                                spaces = 7 - printDiscount.length();

                                if (spaces == 0) {

                                    strbuilder.append(getSpacer(spaces + PRINT_LEFT_MARGIN) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                } else {

                                    strbuilder.append(getSpacer(spaces) + printDiscount + getSpacer(PRINT_LEFT_MARGIN));

                                }

                                if (prod.getDiscountamount() > 0.00) {

                                    String printtotal = String.valueOf(f.format(prod.getDiscountamount()));

                                    spaces = 8 - printtotal.length();

                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                } else {

                                    String printtotal = String.valueOf(f.format(prod.getTotal()));

                                    spaces = 8 - printtotal.length();

                                    strbuilder.append(getSpacer(spaces) + printtotal);

                                }

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                                strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            }



                            strbuilder.append(getDividerSales());
                            DecimalFormat f1 = new DecimalFormat("##.00");

                            float roundvalue = 0.0f;

                            int poud = 0;

                            float gettotal = 0.0f;

                            float OverAllDiscount = 0.0f;

                            for (Sales prod : get_Transcation_List) {

                                poud++;

                            }

                            Log.e("mastervalueforprinter", "" + expectdchnge + fnlillamt + receidamt + totalbillamt + savetotalbilldscount);

                            String Roundoffvalue = String.valueOf(cardGrandTotal);

                            int spaces = 10 - String.valueOf(poud).length();

                            int amtspaces = 8 - Roundoffvalue.length();

                            strbuilder.append(TOTAL_ITEMS + poud + getSpacer(PRINT_LEFT_MARGIN) + BILL_TOTAL + getSpacer(amtspaces) + Roundoffvalue + getSpacer(PRINT_LEFT_MARGIN));

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);

                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL, tp);

                            strbuilder.setLength(0);

                            strbuilder.append(getDividerSales());


                            int spaceSide = 3 + (40 - Footer.length()) / PRINT_LEFT_MARGIN;

                            strbuilder.append(Footer);

                            strbuilder.append(FragmentMonthlySalesReport.this.eol);



                            UsPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER, tp);
                            strbuilder.setLength(0);
                            UsPrinter.print();
                            UsPrinter.FeedLine();
                            UsPrinter.FullCut();
                        }

                    }

                } else {

                    Toast.makeText(getActivity(), "Invalid Transaction id", Toast.LENGTH_SHORT).show();
                }

            }

        } catch (Exception e)

        {

            e.printStackTrace();

            Toast.makeText(getActivity(), "Invalid Transaction id", Toast.LENGTH_SHORT).show();

        }


    }


    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }

    private String getDividerSales() {
        //this for ls printer
        return "--------------------------------- ";
    }



}