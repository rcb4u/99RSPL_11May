package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesbill;
import com.RSPL.POS.R;

//import com.mycompany.apps.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Sales;

/**
 * Created by shilpa on 2/4/16.
 */
public class SalesProductNmAdaptor extends ArrayAdapter<Sales> {

    ActivitySalesbill activity;
    private final int layoutResourceId;
    ArrayList<Sales> ProductNamelist;
    LayoutInflater layoutInflater;
    Long DaysLeft;

    public SalesProductNmAdaptor(ActivitySalesbill activity, int textViewResourceId, ArrayList<Sales> ProductNamelist) {
        super(activity, textViewResourceId, ProductNamelist);
        this.layoutResourceId = textViewResourceId;
        this.activity = activity;
        this.ProductNamelist = ProductNamelist;
    }


    // Sales SalesProductModel;

    public void setList(ArrayList<Sales> ProductNamelist)

    {
        this.ProductNamelist = ProductNamelist;
    }

    public int getCount() {
        if (ProductNamelist.size() < 0)
            return 1;
        return ProductNamelist.size();
    }

    public Sales getItem(int position) {
        return ProductNamelist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView ProductName;
        public TextView BatchNo;
        public TextView Expiry;
        public TextView Quantity;
        public TextView sprice;
        public TextView prodid;


    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_productname_row, parent, false);
            holder.ProductName = (TextView) convertView.findViewById(R.id.productname);
            holder.sprice = (TextView) convertView.findViewById(R.id.sprice);
            holder.BatchNo = (TextView) convertView.findViewById(R.id.batchno);
            holder.Expiry = (TextView) convertView.findViewById(R.id.productexp);
            holder.Quantity = (TextView) convertView.findViewById(R.id.quantity);
            holder.prodid = (TextView) convertView.findViewById(R.id.productid);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        if (position > 0) {
            if (ProductNamelist.get(position).getProdid().trim().equals(ProductNamelist.get(position - 1).getProdid().trim())) {
                holder.ProductName.setVisibility(View.VISIBLE);
                holder.sprice.setVisibility(View.VISIBLE);
                holder.Quantity.setVisibility(View.VISIBLE);
                holder.Expiry.setVisibility(View.VISIBLE);
            } else {
                holder.ProductName.setVisibility(View.VISIBLE);
                holder.sprice.setVisibility(View.VISIBLE);
                holder.Quantity.setVisibility(View.VISIBLE);
                holder.Expiry.setVisibility(View.VISIBLE);
            }
        }
        try{
            String StrExpdate = ProductNamelist.get(position).getExpiry();

            Date date = new Date();
            final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
            String curr_date = s.toString();

            SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
            //Dates to compare
            Date date1;
            Date date2;
            DaysLeft = null;

            date1 = dates.parse(curr_date);
            date2 = dates.parse(StrExpdate);


            long difference = (date2.getTime() - date1.getTime());
            DaysLeft = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);
            if (DaysLeft > 0 ) {

                holder.ProductName.setText(ProductNamelist.get(position).getProductName());
                holder.sprice.setText(String.format("%.2f",ProductNamelist.get(position).getSPrice()));
                holder.BatchNo.setText(ProductNamelist.get(position).getBatchNo());
                holder.prodid.setText(ProductNamelist.get(position).getProdid().replaceFirst("X-",""));

                holder.Expiry.setText(ProductNamelist.get(position).getExpiry().concat("  (").concat(String.valueOf(DaysLeft)).concat(")"));
                holder.Quantity.setText(String.format("%.2f", ProductNamelist.get(position).getStockquant()));
                holder.ProductName.setTextColor(Color.BLACK);
                holder.sprice.setTextColor(Color.BLACK);
                holder.BatchNo.setTextColor(Color.BLACK);
                holder.Expiry.setTextColor(Color.BLACK);
                holder.Quantity.setTextColor(Color.BLACK);
                holder.prodid.setTextColor(Color.BLACK);



            }

            if (DaysLeft <= 45) {

                holder.ProductName.setTextColor(Color.RED);
                holder.sprice.setTextColor(Color.RED);
                holder.BatchNo.setTextColor(Color.RED);
                holder.Expiry.setTextColor(Color.RED);
                holder.Quantity.setTextColor(Color.RED);
                holder.prodid.setTextColor(Color.RED);



            }
          /*  if (DaysLeft >=45) {


                holder.ProductName.setText(ProductNamelist.get(position).getProductName());
                holder.sprice.setText(String.format("%.2f",ProductNamelist.get(position).getSPrice()));
                holder.BatchNo.setText(ProductNamelist.get(position).getBatchNo());
                holder.Expiry.setText(ProductNamelist.get(position).getExpiry().concat("  (").concat(String.valueOf(DaysLeft)).concat(")"));
                holder.Quantity.setText(String.format("%.2f", ProductNamelist.get(position).getStockquant()));

            }*/

        }
        catch (Exception e)
        {e.printStackTrace();
        }
        return convertView;

    }

}
