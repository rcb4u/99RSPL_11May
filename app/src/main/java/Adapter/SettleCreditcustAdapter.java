package Adapter;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.ActivitySales;
import com.RSPL.POS.ActivitySalesbill;
import com.RSPL.POS.Activity_SettleCreditCustomer;
import com.RSPL.POS.Activitypurchase;
import com.RSPL.POS.BillDetails;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.DecimalDigitsInputFilter;
import com.RSPL.POS.R;
import com.RSPL.POS.UsbController;
import com.ngx.USBPrinter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import Pojo.CreditCustomer;
import Pojo.Sales;
import Pojo.Settlecustmodel;

import static android.view.View.INVISIBLE;

/**
 * Created by shilpa on 22/9/16.
 */
public class SettleCreditcustAdapter extends ArrayAdapter<Settlecustmodel> {

    Activity_SettleCreditCustomer settlecredit;

    ArrayList<Settlecustmodel> settlecustmodelArrayList;
    private final int layoutResourceId;
    ArrayList productarrayList;
    String  Searchcreditcustomer,hdSearchcreditcustomer,mob;
    ArrayList<String> InvoiceNoForcreditCustomer;
    TextWatcher dueAMountTextWatcher;
    LayoutInflater layoutInflater;
    BillDetails billDetails;
    //TextView invoiceno;
    private int meditposition=0;
    ArrayList<CreditCustomer> creditcustomerlist,hdcreditcustomerlist;
    // SettleCreditcustomerDialogAdapter creditcustomeradapter;
    // HdSettleCreditCustAdapter hdSettleCreditCustomerAdapter;
    public static String username = null;
    Sales salesmodel;
    int mrp;
    PendingIntent mPermissionIntent;
    UsbDevice device;
    UsbController usbController;
    UsbManager mUsbManager;
    private static final String ACTION_USB_PERMISSION =
            "com.android.example.USB_PERMISSION";
    private static final int PRINT_COLUMN = 40;
    private static final int PRINT_LEFT_MARGIN = 2;
    private String eol = "\n";
    private static final int BILL_DATE_COLUMN = 14;
    private static final String TOTAL_ITEMS = "Total Items:";
    private static final String BILL_TOTAL = "Bill Total:";
    private static final String BILL_DISC = "Discount:";
    private static final String NETPAYAMOUNT = "Net Amount:";
    private static final String RECEIVEAMOUNT = "Recieved Amount:";
    private static final String BALANCEAMOUNT = "Balance to be Paid:";
    private static final String CREDIT_BILL_TOTAL = "Credit Amount:";
    private static final String TOTALSAVINGS = "Total Savings:";
    private static final String DUEAMOUNT = "Due Amount:";

    int sprice;
    String BankNameSelected,ChequeNo,PhneNo;
    AlertDialog settlecustdialog;
    //////////////////////////////////////printer code////////////////////////////////////////////////
    public static USBPrinter UPrinter = USBPrinter.INSTANCE;
    public static SharedPreferences mSp;
    TextPaint tp = new TextPaint();
    Typeface tff;
    /////////////////////////////////////////////////////////////////////

    TextView creditcustinvoice,creditcustamount ,dueamount;
    Button creditcustexit,creditcustpay,creditcustpayCheque;
    EditText recievedamount;
    DBhelper db = new DBhelper(settlecredit);
    ArrayList<String> store_name = db.getAllStores();
////////////////////////////////////////////////////////////////////////////////////

    public SettleCreditcustAdapter(Activity_SettleCreditCustomer settlecredit, int layoutResourceId, ArrayList<Settlecustmodel> settlecustmodelArrayList,UsbDevice device) {
        super(settlecredit, layoutResourceId);
        this.settlecredit = settlecredit;
        this.layoutResourceId = layoutResourceId;
        this.settlecustmodelArrayList = settlecustmodelArrayList;
        this.device=device;
    }



    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (settlecustmodelArrayList.size() < 0)
            return 1;
        Log.e("**get Count***", settlecustmodelArrayList.toString());
        return settlecustmodelArrayList.size();
    }

    public Settlecustmodel getItem(int position) {

        return settlecustmodelArrayList.get(position);
    }

    public void setSettlecustmodelArrayList(ArrayList<Settlecustmodel> settlecustmodelArrayList) {
        this.settlecustmodelArrayList = settlecustmodelArrayList;
    }



    public void clearAllRows() {
        settlecustmodelArrayList.clear();
        notifyDataSetChanged();
    }

    public void addProductToList(Settlecustmodel result) {
        Settlecustmodel productAlreadyInList = findsalesitemInList(result);
        if (productAlreadyInList == null) {
            settlecustmodelArrayList.add(result);
        } else {
            //productAlreadyInList.setQuantity((int) (productAlreadyInList.getQuantity() + salesproduct.getQuantity()));
        }

    }

    private Settlecustmodel findsalesitemInList(Settlecustmodel salesproduct) {
        Settlecustmodel returnSalesVal = null;

        for (Settlecustmodel productInList : settlecustmodelArrayList) {
            if (productInList.getName().trim().equals(salesproduct.getName().trim())) {
                //check batch number also (if batch number is different, we should not add to the
                // same row


                returnSalesVal = productInList;
            }
        }



        return returnSalesVal;
    }

    private void SettleCreditCustomer(final int pos, final String phnNo) {
        LayoutInflater inflater = layoutInflater;
        final View searchalertLayout = inflater.inflate(R.layout.credit_setlement_dialog, null);
        AlertDialog.Builder settlecustalert = new AlertDialog.Builder(settlecredit);
         creditcustinvoice = (TextView) searchalertLayout.findViewById(R.id.settleCust_invoice);
        creditcustamount = (TextView) searchalertLayout.findViewById(R.id.Cust_grandtotal);
         creditcustexit = (Button) searchalertLayout.findViewById(R.id.CreditCust_Exit_button);
         creditcustpay = (Button) searchalertLayout.findViewById(R.id.CreditCust_pay_button);
         creditcustpayCheque = (Button) searchalertLayout.findViewById(R.id.CreditCust_payCheque_button);
         recievedamount = (EditText) searchalertLayout.findViewById(R.id.Cust_reciecvamt);
          recievedamount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
         dueamount = (TextView) searchalertLayout.findViewById(R.id.Cust_dueamnt);

            PhneNo=phnNo;
         settlecustdialog = settlecustalert.create();
        settlecustdialog.setTitle("Settle Customer");
        settlecustdialog.setView(searchalertLayout);
        settlecustdialog.show();
        settlecustdialog.setCanceledOnTouchOutside(false);

        creditcustexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settlecustdialog.dismiss();
            }
        });

//kaha lagana hai batao


        if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
            creditcustamount.setText(settlecustmodelArrayList.get(pos).getOutstanding());
            creditcustinvoice.setText(settlecustmodelArrayList.get(pos).getInvoiceno());
        }

        if ((recievedamount.getTag() != null) && (recievedamount.getTag() instanceof TextWatcher)) {
            recievedamount.removeTextChangedListener((TextWatcher) recievedamount.getTag());
        }
        dueAMountTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f = new DecimalFormat("##.00");

                try {
                    if (recievedamount.getText().toString().equals("") || creditcustamount.getText().toString().trim().equals("")) {
                        dueamount.setText("");
                        return;
                    }
                    dueamount.setText(String.valueOf(f.format(Double.parseDouble(creditcustamount.getText().toString()) - Double.parseDouble(recievedamount.getText().toString()))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }

        };

        recievedamount.addTextChangedListener(dueAMountTextWatcher);
        recievedamount.setTag(dueAMountTextWatcher);


        creditcustpay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DBhelper db = new DBhelper(settlecredit);
                            String dueamount1 =  dueamount.getText().toString();
                            String Valuetrans = (settlecustmodelArrayList.get(pos).getInvoiceno());
                            Searchcreditcustomer=(settlecustmodelArrayList.get(pos).getName());

                            Log.e("Bada dum"," Chota dum"+Valuetrans);
                             /*   if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
*/
                            // holder.Outstanding.setText(dueamount.getText().toString());
                                   /* settlecustmodelArrayList.get(pos).setOutstanding( dueamount.getText().toString());
                                    notifyDataSetChanged();*/
                            if (recievedamount.getText().toString().equals("") || recievedamount.getText().toString().equals("0.00") || recievedamount.getText().toString().equals("0.0") || recievedamount.getText().toString().equals("0") || recievedamount.getText().toString().equals("00.00")) {
                                return;
                            }
                            if (creditcustamount.getText().toString().equals(recievedamount.getText().toString()) || Double.parseDouble(recievedamount.getText().toString()) > Double.parseDouble(creditcustamount.getText().toString())) {
                                db.updatedetailsofcreditcustcash(Valuetrans, creditcustamount.getText().toString(), recievedamount.getText().toString(),"0.00", username,PhneNo.toString());
                                if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                    // holder.Outstanding.setText(dueamount.getText().toString());
                                    settlecustmodelArrayList.get(pos).setOutstanding("0.00");
                                    notifyDataSetChanged();
                                }
                                Intent intent = new Intent(settlecredit, ActivitySalesbill.class);
                                settlecredit.startActivity(intent);
                            }
                             else if (Double.parseDouble(dueamount.getText().toString())>0){
                                    db.updatedetailsofcreditcustcashForPartial(Valuetrans, dueamount.getText().toString(), recievedamount.getText().toString(), dueamount.getText().toString(), username);
                                    if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                        // holder.Outstanding.setText(dueamount.getText().toString());
                                        settlecustmodelArrayList.get(pos).setOutstanding(dueamount.getText().toString());

                                        notifyDataSetChanged();
                                    }
                                }
                                    else{
                                        db.updatedetailsofcreditcustcashForPartial(Valuetrans, dueamount.getText().toString(), recievedamount.getText().toString(), dueamount.getText().toString(), username);
                                        if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                            // holder.Outstanding.setText(dueamount.getText().toString());
                                            settlecustmodelArrayList.get(pos).setOutstanding(dueamount.getText().toString());
                                            notifyDataSetChanged();
                                    }
                                }

                            // }

                            //////////////////////////////////////Printer testing ///////////////////////////////////////

                            String store = (store_name.get(1).toString());
                            //   String Store_ID = (store_name.get(0).toString());
                            String storeAddress = store_name.get(2).toString();
                            String City = store_name.get(3).toString();
                            String Storenumber = store_name.get(4).toString();
                            String AlternateNo = store_name.get(5).toString();
                            String Footer=store_name.get(6).toString();
                            tff = Typeface.createFromAsset(settlecredit.getAssets(), "Fonts/DroidSansMono.ttf");
                            tp.setColor(Color.BLUE);
                            tp.setTextSize(26);
                            tp.setTypeface(tff);

                            String formattedDate;
                            StringBuilder strbuilder = new StringBuilder();
                            DecimalFormat f = new DecimalFormat("##.00");

                            strbuilder.append(store+"\n");
                            strbuilder.append(storeAddress+"\n");
                            strbuilder.append(City+"\n");
                            if (!TextUtils.isEmpty(AlternateNo)) {
                                strbuilder.append("Tel:" +Storenumber+","+AlternateNo+"\n");

                            } else {
                                strbuilder.append( "Tel:" + Storenumber);
                                strbuilder.append(eol);
                            }
                            UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER,tp);
                            strbuilder.setLength(0);

                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Credit Customer:");
                            int spaceSide = 3 + (40 - Searchcreditcustomer.length()) / PRINT_LEFT_MARGIN;
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + Searchcreditcustomer + getSpacer(spaceSide));
                            strbuilder.append(eol);

                            formattedDate = new SimpleDateFormat("EEE,dd MMMyy hh:mma").format(Calendar.getInstance().getTime());
                            int val=Valuetrans.toString().length();
                       /* int creditbill=Integer.parseInt(String.valueOf(val));*/
                            int billDtSpace =39 - (val+ BILL_DATE_COLUMN) + formattedDate.length();
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:");
                            strbuilder.append(Valuetrans.toString() );
                            strbuilder.append(getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(formattedDate);
                            strbuilder.append(eol);
                            strbuilder.append(getDividerSales());
                            strbuilder.append(eol);
                            int Disamtspaces = getSpacer(24).length()-CREDIT_BILL_TOTAL.length();
                            int netvalspce=9-(creditcustamount.getText().toString()).length();
                            strbuilder.append(getSpacer(Disamtspaces)+CREDIT_BILL_TOTAL+getSpacer(netvalspce)+creditcustamount.getText().toString()+getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(eol);

                            Disamtspaces = getSpacer(24).length()-RECEIVEAMOUNT.length();
                            netvalspce=9-(recievedamount.getText().toString()).length();
                            strbuilder.append(getSpacer(Disamtspaces)+RECEIVEAMOUNT+getSpacer(netvalspce)+recievedamount.getText().toString()+getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(eol);

                            int amtspaces = getSpacer(24).length()-DUEAMOUNT.length();
                            netvalspce=9 - String.valueOf(f.format(Double.parseDouble(dueamount.getText().toString()))).length();
                            strbuilder.append(getSpacer(amtspaces)+DUEAMOUNT+getSpacer(netvalspce)+ String.valueOf(f.format(Double.parseDouble(dueamount.getText().toString())))+getSpacer(PRINT_LEFT_MARGIN));
                            strbuilder.append(eol);

                            strbuilder.append(getDividerSales());
                            strbuilder.append(eol);
                            UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL,tp);
                            strbuilder.setLength(0);
                            strbuilder.append( Footer);
                            strbuilder.append(eol);

                            UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER,tp);
                            strbuilder.setLength(0);
                            UPrinter.print();
                            UPrinter.FeedLine();
                            UPrinter.FullCut();
                            settlecustdialog.dismiss();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }


                });

            creditcustpayCheque.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (recievedamount.getText().toString().equals("") || recievedamount.getText().toString().equals("0.00") || recievedamount.getText().toString().equals("0.0") || recievedamount.getText().toString().equals("0") || recievedamount.getText().toString().equals("00.00")) {
                        return;
                    }else {
                        settlecustdialog.dismiss();
                        AlertForChequePayment();
                    }
                }
            });

    }

    private void AlertForChequePayment() {
        LayoutInflater inflater = layoutInflater;
        final View alertLayout = inflater.inflate(R.layout.alertdialog_forvendorindirectpayment, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(settlecredit);
        DBhelper db = new DBhelper(settlecredit);
        final  Button submit=(Button)alertLayout.findViewById(R.id.submitpaymentbtn);
        final EditText EnterChequeno=(EditText)alertLayout.findViewById(R.id.EnterCheque1);
        final Spinner BankName = (Spinner)alertLayout.findViewById(R.id.selectBank);
        final Button Cancel=(Button)alertLayout.findViewById(R.id.cancel);
        alert.setView(alertLayout);
        alert.setCancelable(true);

        alert.setTitle("                    Enter Bank Details ");
        final AlertDialog dialog =alert.create();

        ArrayList<String>BankNameArraylist=db.getBankName();
        ArrayAdapter<String>BankNameAdapter=new ArrayAdapter<String>(settlecredit,android.R.layout.simple_spinner_item,BankNameArraylist);
        BankName.setAdapter(BankNameAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    BankNameSelected = BankName.getSelectedItem().toString();
                    ChequeNo = EnterChequeno.getText().toString();
                      if (BankNameSelected.trim().isEmpty()) {
                        Toast.makeText(settlecredit, " Please select Bank name", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (EnterChequeno.getText().toString().trim().isEmpty()) {
                        Toast.makeText(settlecredit, " Please Enter Cheque Number", Toast.LENGTH_SHORT).show();
                        return;
                      }

                    try {
                        DBhelper db = new DBhelper(settlecredit);
                        String dueamount1 =  dueamount.getText().toString();
                        String Valuetrans = (settlecustmodelArrayList.get(meditposition).getInvoiceno());
                        Searchcreditcustomer=(settlecustmodelArrayList.get(meditposition).getName());

                        Log.e("Bada dum"," Chota dum"+Valuetrans);
                             /*   if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
*/
                        // holder.Outstanding.setText(dueamount.getText().toString());
                                   /* settlecustmodelArrayList.get(pos).setOutstanding( dueamount.getText().toString());
                                    notifyDataSetChanged();*/
                        if (recievedamount.getText().toString().equals("") || recievedamount.getText().toString().equals("0.00") || recievedamount.getText().toString().equals("0.0") || recievedamount.getText().toString().equals("0") || recievedamount.getText().toString().equals("00.00")) {
                            return;
                        }
                        if (creditcustamount.getText().toString().equals(recievedamount.getText().toString()) || Double.parseDouble(recievedamount.getText().toString()) > Double.parseDouble(creditcustamount.getText().toString())) {
                            db.updatedetailsofcreditcustcash(Valuetrans, creditcustamount.getText().toString(), recievedamount.getText().toString(),"0.00", username,PhneNo);
                            if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                // holder.Outstanding.setText(dueamount.getText().toString());
                                settlecustmodelArrayList.get(meditposition).setOutstanding("0.00");
                                notifyDataSetChanged();
                            }
                            Intent intent = new Intent(settlecredit, ActivitySalesbill.class);
                            settlecredit.startActivity(intent);
                        }
                        else if (Double.parseDouble(dueamount.getText().toString())>0){
                            db.updatedetailsofcreditcustcashForPartial(Valuetrans, dueamount.getText().toString(), recievedamount.getText().toString(), dueamount.getText().toString(), username);
                            if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                // holder.Outstanding.setText(dueamount.getText().toString());
                                settlecustmodelArrayList.get(meditposition).setOutstanding(dueamount.getText().toString());

                                notifyDataSetChanged();
                            }
                        }
                        else{
                            db.updatedetailsofcreditcustcashForPartial(Valuetrans, dueamount.getText().toString(), recievedamount.getText().toString(), dueamount.getText().toString(), username);
                            if (meditposition != -1 && meditposition < settlecustmodelArrayList.size()) {
                                // holder.Outstanding.setText(dueamount.getText().toString());
                                settlecustmodelArrayList.get(meditposition).setOutstanding(dueamount.getText().toString());
                                notifyDataSetChanged();
                            }
                        }

                        // }

                        //////////////////////////////////////Printer testing ///////////////////////////////////////

                        String store = (store_name.get(1).toString());
                        //   String Store_ID = (store_name.get(0).toString());
                        String storeAddress = store_name.get(2).toString();
                        String City = store_name.get(3).toString();
                        String Storenumber = store_name.get(4).toString();
                        String AlternateNo = store_name.get(5).toString();
                        String Footer=store_name.get(6).toString();
                        tff = Typeface.createFromAsset(settlecredit.getAssets(), "Fonts/DroidSansMono.ttf");
                        tp.setColor(Color.BLUE);
                        tp.setTextSize(26);
                        tp.setTypeface(tff);

                        String formattedDate;
                        StringBuilder strbuilder = new StringBuilder();
                        DecimalFormat f = new DecimalFormat("##.00");

                        strbuilder.append(store+"\n");
                        strbuilder.append(storeAddress+"\n");
                        strbuilder.append(City+"\n");
                        if (!TextUtils.isEmpty(AlternateNo)) {
                            strbuilder.append("Tel:" +Storenumber+","+AlternateNo+"\n");

                        } else {
                            strbuilder.append( "Tel:" + Storenumber);
                            strbuilder.append(eol);
                        }
                        UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER,tp);
                        strbuilder.setLength(0);

                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Credit Customer:");
                        int spaceSide = 3 + (40 - Searchcreditcustomer.length()) / PRINT_LEFT_MARGIN;
                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + Searchcreditcustomer + getSpacer(spaceSide));
                        strbuilder.append(eol);

                        formattedDate = new SimpleDateFormat("EEE,dd MMMyy hh:mma").format(Calendar.getInstance().getTime());
                        int val=Valuetrans.toString().length();
                       /* int creditbill=Integer.parseInt(String.valueOf(val));*/
                        int billDtSpace =39 - (val+ BILL_DATE_COLUMN) + formattedDate.length();
                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN) + "Bill No:");
                        strbuilder.append(Valuetrans.toString() );
                        strbuilder.append(getSpacer(PRINT_LEFT_MARGIN));
                        strbuilder.append(formattedDate);
                        strbuilder.append(eol);
                        strbuilder.append(getDividerSales());
                        strbuilder.append(eol);
                        int Disamtspaces = getSpacer(24).length()-CREDIT_BILL_TOTAL.length();
                        int netvalspce=9-(creditcustamount.getText().toString()).length();
                        strbuilder.append(getSpacer(Disamtspaces)+CREDIT_BILL_TOTAL+getSpacer(netvalspce)+creditcustamount.getText().toString()+getSpacer(PRINT_LEFT_MARGIN));
                        strbuilder.append(eol);

                        Disamtspaces = getSpacer(24).length()-RECEIVEAMOUNT.length();
                        netvalspce=9-(recievedamount.getText().toString()).length();
                        strbuilder.append(getSpacer(Disamtspaces)+RECEIVEAMOUNT+getSpacer(netvalspce)+recievedamount.getText().toString()+getSpacer(PRINT_LEFT_MARGIN));
                        strbuilder.append(eol);

                        int amtspaces = getSpacer(24).length()-DUEAMOUNT.length();
                        netvalspce=9 - String.valueOf(f.format(Double.parseDouble(dueamount.getText().toString()))).length();
                        strbuilder.append(getSpacer(amtspaces)+DUEAMOUNT+getSpacer(netvalspce)+ String.valueOf(f.format(Double.parseDouble(dueamount.getText().toString())))+getSpacer(PRINT_LEFT_MARGIN));
                        strbuilder.append(eol);

                        strbuilder.append(getDividerSales());
                        strbuilder.append(eol);
                        UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_NORMAL,tp);
                        strbuilder.setLength(0);
                        strbuilder.append( Footer);
                        strbuilder.append(eol);

                        UPrinter.addText(strbuilder.toString(), Layout.Alignment.ALIGN_CENTER,tp);
                        strbuilder.setLength(0);
                        UPrinter.print();
                        UPrinter.FeedLine();
                        UPrinter.FullCut();
                        dialog.dismiss();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }



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


    public static class ViewHolder1 {
        public TextView Name;
        public TextView PhoneNo;
        public TextView Outstanding;
        public TextView settleinvoice;
        public Button settle, Detils;
    }
    @Override
    public View getView ( final int position, View convertView, ViewGroup parent){
        final ViewHolder1 holder;
        if (convertView == null) {
            holder = new ViewHolder1();
            layoutInflater = (LayoutInflater) settlecredit.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_settlecreditcust_row, parent, false);
            holder.Name = (TextView) convertView.findViewById(R.id.settlecustname);
            holder.PhoneNo = (TextView) convertView.findViewById(R.id.settlecustphoneno);
            holder.Outstanding = (TextView) convertView.findViewById(R.id.settleoutstanding);
            holder.settleinvoice = (TextView) convertView.findViewById(R.id.settlecustinvoice);

            holder.settle = (Button) convertView.findViewById(R.id.SettleButton);
            holder.Detils = (Button) convertView.findViewById(R.id.detailsButton);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();

            holder.Name.setText(settlecustmodelArrayList.get(position).getName());
            holder.PhoneNo.setText(settlecustmodelArrayList.get(position).getPhoneno());
            holder.Outstanding.setText(settlecustmodelArrayList.get(position).getOutstanding());
            holder.settleinvoice.setText(settlecustmodelArrayList.get(position).getInvoiceno());

            // holder.Detils.setText(settlecustmodelArrayList.get(position).getInvoiceno());

            holder.settle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        meditposition = position;
                        SettleCreditCustomer(meditposition,holder.PhoneNo.getText().toString());

                }
            });

            holder.Detils.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    settlecredit.GetCreditDetails(holder.Name.getText().toString(),holder.Outstanding.getText().toString());
                    // settlecredit.startActivity(new Intent(settlecredit, BillDetails.class));
                }
            });


        }
        return convertView;

    }

    private StringBuffer getSpacer(int space) {
        StringBuffer spaceString = new StringBuffer();
        for (int i = 1; i <= space; i++) {
            spaceString.append(" ");
        }
        return spaceString;
    }

    private String getDividerSales() {
        return "---------------------------------  ";
    }



}