package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.Activity_CreditView_bills;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Sales;
import Pojo.Settlecustmodel;

/**
 * Created by shilpa on 23/9/16.
 */
public class CreditViewBill_Adapter extends ArrayAdapter<Sales> {
    Activity_CreditView_bills activity_creditView_bills;
    private final int layoutResourceId;
    ArrayList<Sales> Billviewdetaillist;
    LayoutInflater layoutInflater;


    public CreditViewBill_Adapter(Activity_CreditView_bills activity_creditView_bills, int textViewResourceId,ArrayList<Sales> creditcustviewbilldataa) {
        super(activity_creditView_bills, textViewResourceId,creditcustviewbilldataa);
        this.layoutResourceId = textViewResourceId;
        this.activity_creditView_bills = activity_creditView_bills;
        this.Billviewdetaillist = creditcustviewbilldataa;
    }

    public void setList(ArrayList<Sales> Billviewdetaillist)

    {
        this.Billviewdetaillist = Billviewdetaillist;
    }

    public int getCount() {
        if (Billviewdetaillist.size() < 0)
            return 1;
        return Billviewdetaillist.size();
    }

    public Sales getItem(int position) {
        return Billviewdetaillist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public float getGrandTotal() {
        float finalamount = 0.0f;
        DecimalFormat f=new DecimalFormat("##.0");
        for (int listIndex = 0; listIndex < Billviewdetaillist.size(); listIndex++) {
            try {
                finalamount += Float.parseFloat(String.valueOf((Billviewdetaillist.get(listIndex).getAmount())));
                // holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.SPrice.getText().toString())  * (Double.parseDouble(holder.Quantity.getText().toString())))));

            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount ;
    }


    public static class ViewHolder {
        public TextView item;
        public TextView mrp;
        public TextView sp;
        public TextView qty;
        public TextView amount;




    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        DecimalFormat f = new DecimalFormat("##.00");
        if (convertView == null) {


            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity_creditView_bills.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_viewbilldetails_row, parent, false);
            holder.item = (TextView) convertView.findViewById(R.id.items);
            holder.mrp = (TextView) convertView.findViewById(R.id.viewbillmrp);
            holder.sp = (TextView) convertView.findViewById(R.id.viewbillsp);
            holder.qty = (TextView) convertView.findViewById(R.id.viewbillqty);
            holder.amount = (TextView) convertView.findViewById(R.id.viewbillamount);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DecimalFormat f2 = new DecimalFormat("##.00");

        holder.item.setText(Billviewdetaillist.get(position).getProductName());
        holder.mrp.setText(String.valueOf(Billviewdetaillist.get(position).getMrp()));
        holder.sp.setText (String.valueOf(Billviewdetaillist.get(position).getSPrice()));
        holder.qty.setText(String.valueOf(Billviewdetaillist.get(position).getQuantity()));
        String creditbillamount = String.valueOf(f2.format(Float.parseFloat(Billviewdetaillist.get(position).getAmount())));
        holder.amount.setText(creditbillamount);



        return convertView;

    }

}