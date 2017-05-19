package Adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.Activity_Lost_Sale;
import com.RSPL.POS.Activity_Salesreturn_withoutinvoiceno;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.RemoteVideoPresentation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Pojo.Decimal;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.lostsale;

/**
 * Created by rspl-gourav on 28/3/17.
 */

public class lostsaleslistadapter extends ArrayAdapter<lostsale> {
    private final int layoutResourceId;
    private Context mcontext;
    Activity_Lost_Sale activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;
    DBhelper mydb;





    public lostsaleslistadapter (Activity_Lost_Sale activity, int layoutResourceId, ArrayList<lostsale> msalesreturnlist) {
        super(activity, layoutResourceId,  msalesreturnlist);
        this.activity = activity;
        this.salesreturnlist=  msalesreturnlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    private ArrayList<lostsale> salesreturnlist;



    public int getCount() {
        if (salesreturnlist.size() < 0)
            return 1;
        return salesreturnlist.size();
    }


    public lostsale getItem(int position) {
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


        public EditText quantity;
        public TextView productname;
        public TextView sellingprice;
        public TextView total;
        public ImageButton Delete;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final lostsaleslistadapter.ViewHolder holder;

        if (convertView == null) {
            mydb = new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();


            holder = new lostsaleslistadapter.ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.display_lost_list, parent, false);


            holder.quantity= (EditText) convertView.findViewById(R.id.quty);
          //  holder.quantity.setTextSize(Float.parseFloat(textsize));

            holder.productname = (TextView) convertView.findViewById(R.id.prodname);
          //  holder.productname.setTextSize(Float.parseFloat(textsize));

            holder.sellingprice = (TextView) convertView.findViewById(R.id.selling);
         //   holder.sellingprice.setTextSize(Float.parseFloat(textsize));

            holder.total= (TextView) convertView.findViewById(R.id.totalpt);
          //  holder.total.setTextSize(Float.parseFloat(textsize));

            holder.Delete = (ImageButton) convertView .findViewById(R.id.deleteButton);
            //  holder.mrp = (TextView) convertView.findViewById(R.id.mrp);

            convertView.setTag(holder);
        }
        //holder.name.setText(mcustomerlist.get(position).getCustomername());
        else {
            holder = (lostsaleslistadapter.ViewHolder) convertView.getTag();

        }
        DecimalFormat f= new DecimalFormat("##.00");
//            holder.Transid.setText(salesreturnlist.get(position).getSaleTransid());
        // holder.Billno.setText(salesreturnlist.get(position).getSaleBillno());
        holder.productname.setText(salesreturnlist.get(position).getSaleproductname());
      //  holder.quantity.setText(salesreturnlist.get(position).getqty());

        if ((holder.sellingprice.getTag() != null) && (holder.sellingprice.getTag() instanceof TextWatcher)) {
            holder.sellingprice.removeTextChangedListener((TextWatcher) holder.sellingprice.getTag());
        }
        holder.sellingprice.setText(String.format("%.2f", salesreturnlist.get(position).getSalesellingprice()));

        holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString())  * Double.parseDouble(holder.quantity.getText().toString()))));

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

                    DecimalFormat f= new DecimalFormat("##.00");
                    holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) * Double.parseDouble(holder.quantity.getText().toString()))));

                    salesreturnlist.get(position).setSalesellingprice(Float.parseFloat(holder.sellingprice.getText().toString()));
                    salesreturnlist.get(position).setqty(Integer.parseInt(holder.quantity.getText().toString()));
                    salesreturnlist.get(position).settotal(Integer.parseInt(holder.total.getText().toString()));


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



        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    salesreturnlist.remove(position);
                    RemoteVideoPresentation.deleteSalesReturnProductfromList(position);
                    activity.setSummaryRow();
                    notifyDataSetChanged();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        
        return convertView;


    }

    public int  addProductToList( lostsale product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        lostsale productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            salesreturnlist.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setqty(productAlreadyInList.getqty() + product.getqty());
            return  salesreturnlist.indexOf(productAlreadyInList);
        }

    }

    public  ArrayList<lostsale> getList() {
        return salesreturnlist;
    }

    private lostsale findProductInList(lostsale product) {
        lostsale returnProductVal = null;

        for( lostsale productInList : salesreturnlist) {
            if( productInList.getProdid().trim().equals(product.getProdid().trim()) ) {
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
                finalamount += (salesreturnlist.get(listIndex).gettotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }

}