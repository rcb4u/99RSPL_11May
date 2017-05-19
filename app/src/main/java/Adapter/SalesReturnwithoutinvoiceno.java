package Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.Activity_Salesreturn_withoutinvoiceno;
import com.RSPL.POS.CustomAlphabatKeyboard;
import com.RSPL.POS.CustomFractionalKeyboard;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.RemoteVideoPresentation;
//import com.mycompany.apps.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Pojo.Decimal;
import Pojo.SalesreturndetailWithoutPo;

/**
 * Created by rspl-nishant on 1/4/16.
 */
public class SalesReturnwithoutinvoiceno extends ArrayAdapter<SalesreturndetailWithoutPo> {
    private final int layoutResourceId;
    private Context mcontext;
    Activity_Salesreturn_withoutinvoiceno activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;
    DBhelper mydb;
    Calendar myCalendar = Calendar.getInstance();
    private int mTempPositionBeforeCalenderDialog = -1;
    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH)+1;
    int Day = c.get(Calendar.DAY_OF_MONTH);




    public SalesReturnwithoutinvoiceno (Activity_Salesreturn_withoutinvoiceno activity, int layoutResourceId, ArrayList<SalesreturndetailWithoutPo> msalesreturnlist) {
        super(activity, layoutResourceId,  msalesreturnlist);
        this.activity = activity;
        this.salesreturnlist=  msalesreturnlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void setSalesReturnList(ArrayList<SalesreturndetailWithoutPo> salesreturnlist) {
        this.salesreturnlist = salesreturnlist;
    }

    private ArrayList<SalesreturndetailWithoutPo> salesreturnlist;



    public int getCount() {
        if (salesreturnlist.size() < 0)
            return 1;
        return salesreturnlist.size();
    }


    public SalesreturndetailWithoutPo getItem(int position) {
        return salesreturnlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public void clearAllRows() {
        salesreturnlist.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        public TextView Transid;
        // public TextView Billno;
        public EditText batchno;
        public EditText ExpDate;


        // public TextView BatchNo;
        // public TextView mrp;
        public EditText quantity;
        public TextView uom;
        public TextView productname;
        public TextView sellingprice;
        public TextView total;
        public TextView convertionfactorreturn;
        // public Spinner  reason;
        public ImageButton Delete;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            mydb = new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();


            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.displaysalewithoutinvoiceno, parent, false);

            holder.batchno = (EditText) convertView.findViewById(R.id.batchno);
            holder.batchno.setTextSize(Float.parseFloat(textsize));


            holder.quantity= (EditText) convertView.findViewById(R.id.quty);
            holder.quantity.setTextSize(Float.parseFloat(textsize));
            holder.uom = (TextView) convertView.findViewById(R.id.uom);
            holder.uom.setTextSize(Float.parseFloat(textsize));

            holder.ExpDate = (EditText) convertView.findViewById(R.id.Exp_date);

            holder.ExpDate.setTextSize(Float.parseFloat(textsize));
            holder.productname = (TextView) convertView.findViewById(R.id.prodname);
            holder.productname.setTextSize(Float.parseFloat(textsize));
            holder.sellingprice = (TextView) convertView.findViewById(R.id.selling);
            holder.sellingprice.setTextSize(Float.parseFloat(textsize));
            holder.total= (TextView) convertView.findViewById(R.id.totalpt);
            holder.total.setTextSize(Float.parseFloat(textsize));
            holder.Delete = (ImageButton) convertView .findViewById(R.id.deleteButton);
            holder.convertionfactorreturn=(TextView)convertView.findViewById(R.id.convefactreturn);
            holder.convertionfactorreturn.setTextSize(Float.parseFloat(textsize));

            //  holder.mrp = (TextView) convertView.findViewById(R.id.mrp);








            convertView.setTag(holder);
        }
        //holder.name.setText(mcustomerlist.get(position).getCustomername());
        else {
            holder = (ViewHolder) convertView.getTag();

        }
        DecimalFormat f= new DecimalFormat("##.00");
//            holder.Transid.setText(salesreturnlist.get(position).getSaleTransid());
        // holder.Billno.setText(salesreturnlist.get(position).getSaleBillno());
        holder.productname.setText(salesreturnlist.get(position).getSaleproductname());
        holder.ExpDate.setText(salesreturnlist.get(position).getExp_Date());


        // holder.saledate.setText(salesreturnlist.get(position).getSaleDate());
        // holder.BatchNo.setText(salesreturnlist.get(position).getSalebatchno());

        // holder.mrp.setText(salesreturnlist.get(position).getSalemrp());
        // holder.reason.setText(salesreturnlist.get(position).);
        if ((holder.batchno.getTag() != null) && (holder.batchno.getTag() instanceof TextWatcher)) {
            holder.batchno.removeTextChangedListener((TextWatcher) holder.batchno.getTag());
        }
        holder.batchno.setText(salesreturnlist.get(position).getSalebatchno());


        if ((holder.quantity.getTag() != null) && (holder.quantity.getTag() instanceof TextWatcher)) {
            holder.quantity.removeTextChangedListener((TextWatcher) holder.quantity.getTag());
        }
        holder.quantity.setText(String.format("%d", salesreturnlist.get(position).getSaleqty()));

        holder.uom.setText(salesreturnlist.get(position).getSaleuom());

        if ((holder.sellingprice.getTag() != null) && (holder.sellingprice.getTag() instanceof TextWatcher)) {
            holder.sellingprice.removeTextChangedListener((TextWatcher) holder.sellingprice.getTag());
        }
        holder.sellingprice.setText(String.format("%.2f", salesreturnlist.get(position).getSalesellingprice()));
        if ((holder.convertionfactorreturn.getTag() != null) && (holder.convertionfactorreturn.getTag() instanceof TextWatcher)) {
            holder.convertionfactorreturn.removeTextChangedListener((TextWatcher) holder.convertionfactorreturn.getTag());
        }
        holder.convertionfactorreturn.setText(String.format("%.2f",salesreturnlist.get(position).getConversionfactorreturn()));

        holder.total.setText(String.format("%.2f", salesreturnlist.get(position).getSaletotal()));

        holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) / (Double.parseDouble(holder.convertionfactorreturn.getText().toString())) * Double.parseDouble(holder.quantity.getText().toString()))));
        TextWatcher quantityTextChangeWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {


                    if (holder.quantity.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.quantity.setError("Enter a valid quantity");
                        salesreturnlist.get(position).setSaleqty(0);
                        return;
                    }
                    if (holder.batchno.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        //  holder.batchno.setError("Enter a valid batch number");
                        salesreturnlist.get(position).setSalebatchno("");
                        return;
                    }





                    DecimalFormat f= new DecimalFormat("##.00");
                    holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) / (Double.parseDouble(holder.convertionfactorreturn.getText().toString())) * Double.parseDouble(holder.quantity.getText().toString()))));
                    salesreturnlist.get(position).setSaleqty(Integer.parseInt(holder.quantity.getText().toString()));
                    salesreturnlist.get(position).setSalesellingprice(Float.parseFloat(holder.sellingprice.getText().toString()));
                    salesreturnlist.get(position).setSalebatchno(holder.batchno.getText().toString());
                    RemoteVideoPresentation.updateReturnQuantity(position, Integer.parseInt(holder.quantity.getText().toString()));
                    activity.updateQuantity(position, Integer.parseInt(holder.quantity.getText().toString()));


                    Log.e("&&&total&&&&", "$$$$$" + holder.total.getText().toString());
                    activity.setSummaryRow();

                } catch (Exception e) {
                    Log.e("&&&&&RAhulTest&&&", "Exception " + e + " while parsing double");
                    e.printStackTrace();
                }
            }
        };
        holder.quantity.addTextChangedListener(quantityTextChangeWatcher);
        holder.quantity.setTag(quantityTextChangeWatcher);

        holder.sellingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.sellingprice.setTag(quantityTextChangeWatcher);

        holder.batchno.addTextChangedListener(quantityTextChangeWatcher);
        holder.batchno.setTag(quantityTextChangeWatcher);


        holder.convertionfactorreturn.addTextChangedListener(quantityTextChangeWatcher);
        holder.convertionfactorreturn.setTag(quantityTextChangeWatcher);

        holder.ExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempPositionBeforeCalenderDialog = position;
                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    salesreturnlist.remove(position);
                    RemoteVideoPresentation.deleteSalesReturnProductfromList(position);
                    activity.deleteData(position);
                    activity.setSummaryRow();
                    notifyDataSetChanged();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });



        holder.uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.sellingprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

//       holder.name.setText(customerlist.get(position).getCustomername());
//        holder.mobile_no.setText(customerlist.get(position).getCustomermobileno());

        return convertView;


    }

    private DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub

                    validateDate(year, month + 1, day);
                  /* if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
                        int Mymonth=1+monthOfYear;
                        list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+Mymonth +"/"+dayOfMonth);
                        notifyDataSetChanged();
                        mTempPositionBeforeCalenderDialog = -1;
                    }*/
                    //  demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
                }
            };


    public void validateDate(int year,int month,int day) {
        ViewHolder holder=new ViewHolder();
        Date Todaydate=null,edate=null;

        String enddate=year+"/"+month+"/"+day;
        Log.e("########", "----------->" + enddate);
        String todaysdate=Year+"/"+Month+"/"+Day;


        //   String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########", "----------->" + todaysdate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(edate.before(Todaydate)){
            //   startDatepicker.dismiss();
            Toast.makeText(activity,"Invalid date !!", Toast.LENGTH_SHORT).show();
            //  list.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+month +"/"+day);
            //  startDatepicker.show(getSupportFragmentManager(), "showDate");
            //return;
            return ;


        }
        else{
            Log.e("########", "----------->" + todaysdate);
            Log.e("########", "----------->" + edate);

            //   startDatePicked=true;
            if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < salesreturnlist.size()) {
                int Mymonth=month;
                salesreturnlist.get(mTempPositionBeforeCalenderDialog).setExp_Date(year+"/"+Mymonth +"/"+day);
                notifyDataSetChanged();
                mTempPositionBeforeCalenderDialog = -1;
                // holder.ExpDate.setText(day + "/" + month + "/" + year);

            }
        }
    }








    public int  addProductToList( SalesreturndetailWithoutPo product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        SalesreturndetailWithoutPo productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            salesreturnlist.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setSaleqty(productAlreadyInList.getSaleqty() + product.getSaleqty());
           return  salesreturnlist.indexOf(productAlreadyInList);
        }

    }

    public ArrayList<SalesreturndetailWithoutPo> getList() {
        return salesreturnlist;
    }

    private SalesreturndetailWithoutPo findProductInList(SalesreturndetailWithoutPo product) {
        SalesreturndetailWithoutPo returnProductVal = null;

        for( SalesreturndetailWithoutPo productInList : salesreturnlist) {
            if( productInList.getSaleProdid().trim().equals(product.getSaleProdid().trim()) ) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public float getGrandTotal() {
        // ViewHolder holder=new ViewHolder();
        float finalamount = 0.0f;
        for (int listIndex = 0; listIndex < salesreturnlist.size(); listIndex++) {
            try {
                finalamount += (salesreturnlist.get(listIndex).getSaletotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }

    public static void hideSoftKeyboard(Activity_Salesreturn_withoutinvoiceno activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity_Salesreturn_withoutinvoiceno.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}