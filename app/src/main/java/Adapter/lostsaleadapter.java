package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.Activity_Lost_Sale;
import com.RSPL.POS.Activity_Salesreturn_withoutinvoiceno;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.lostsale;

/**
 * Created by rspl-gourav on 28/3/17.
 */

public class lostsaleadapter extends ArrayAdapter<lostsale> {

    Activity_Lost_Sale activity;
    private final int layoutResourceId;
    ArrayList<lostsale> ProductNamelist;
    LayoutInflater layoutInflater;
    DBhelper mydb;

    public lostsaleadapter(Activity_Lost_Sale activity, int textViewResourceId, ArrayList<lostsale> ProductNamelist) {
        super(activity, textViewResourceId, ProductNamelist);
        this.layoutResourceId = textViewResourceId;
        this.activity = activity;
        this.ProductNamelist = ProductNamelist;
    }


    // Sales SalesProductModel;

    public void setList(ArrayList<lostsale> ProductNamelist)

    {
        this.ProductNamelist = ProductNamelist;
    }


    public ArrayList<lostsale> getList() {
        return ProductNamelist;
    }

    public int getCount() {
        if (ProductNamelist.size() < 0)
            return 1;
        return ProductNamelist.size();
    }

    public lostsale getItem(int position) {
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
        public TextView Productid;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final lostsaleadapter.ViewHolder holder;
        if (convertView == null) {
            mydb = new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            holder = new lostsaleadapter.ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_salereturnproduct_row, parent, false);
            holder.ProductName = (TextView) convertView.findViewById(R.id.productname);
            holder.ProductName.setTextSize(Float.parseFloat(textsize));

            holder.Productid = (TextView) convertView.findViewById(R.id.productid);
            holder.Productid.setTextSize(Float.parseFloat(textsize));


         /*   holder.BatchNo = (TextView) convertView.findViewById(R.id.batchno);
            holder.BatchNo.setTextSize(Float.parseFloat(textsize));

            holder.Expiry = (TextView) convertView.findViewById(R.id.productexp);
            holder.Expiry.setTextSize(Float.parseFloat(textsize));
            holder.Quantity = (TextView) convertView.findViewById(R.id.quantity);
            holder.Quantity.setTextSize(Float.parseFloat(textsize));*/

            convertView.setTag(holder);

        } else {
            holder = (lostsaleadapter.ViewHolder) convertView.getTag();
        }


        if (position > 0) {
            if (ProductNamelist.get(position).getProdid().trim().equals(ProductNamelist.get(position - 1).getProdid().trim())) {
                holder.ProductName.setVisibility(View.VISIBLE);
                holder.Productid.setVisibility(View.VISIBLE);
                //holder.Quantity.setVisibility(View.INVISIBLE);
                // holder.Expiry.setVisibility(View.INVISIBLE);

            } else {
                holder.ProductName.setVisibility(View.VISIBLE);
                holder.Productid.setVisibility(View.VISIBLE);
                //holder.Quantity.setVisibility(View.VISIBLE);
                //holder.Expiry.setVisibility(View.VISIBLE);

            }
        }

        holder.ProductName.setText(ProductNamelist.get(position).getSaleproductname());
        holder.Productid.setText(ProductNamelist.get(position).getProdid());
        // holder.BatchNo.setText(ProductNamelist.get(position).getSalebatchno());
        // holder.Expiry.setText(ProductNamelist.get(position).getS());
        // holder.Quantity.setText(String.format("%d", ProductNamelist.get(position).getQuantity()));
        return convertView;

    }

}