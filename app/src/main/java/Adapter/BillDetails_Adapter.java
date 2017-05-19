package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.BillDetails;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Settlecustmodel;

/**
 * Created by shilpa on 22/9/16.
 */
public class BillDetails_Adapter extends ArrayAdapter<Settlecustmodel> {
    BillDetails BillDetailsactivity;
    private final int layoutResourceId;
    ArrayList<Settlecustmodel> Billdetaillist;
    LayoutInflater layoutInflater;


    public BillDetails_Adapter(BillDetails billDetailsactivity, int textViewResourceId,ArrayList<Settlecustmodel> creditcustalldata) {
        super(billDetailsactivity, textViewResourceId,creditcustalldata);
        this.layoutResourceId = textViewResourceId;
        this.BillDetailsactivity = billDetailsactivity;
        this.Billdetaillist = creditcustalldata;
    }

    public void setList(ArrayList<Settlecustmodel> Billdetaillist)

    {
        this.Billdetaillist = Billdetaillist;
    }

    public int getCount() {
        if (Billdetaillist.size() < 0)
            return 1;
        return Billdetaillist.size();
    }

    public Settlecustmodel getItem(int position) {
        return Billdetaillist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView billno;
        public TextView date;
        public TextView billamount;
        public TextView paid;
        public TextView mobileno;
        public Button viewbills;



    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) BillDetailsactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.displaybilldetails_row, parent, false);
            holder.billno = (TextView) convertView.findViewById(R.id.detailsbillno);
            holder.mobileno = (TextView) convertView.findViewById(R.id.detailsmobileno);
            holder.date = (TextView) convertView.findViewById(R.id.detailsbilldate);
            holder.billamount = (TextView) convertView.findViewById(R.id.detailsbillamount);
            holder.paid = (TextView) convertView.findViewById(R.id.detailsbiipaid);
            holder.viewbills = (Button) convertView.findViewById(R.id.viewbillButton);




            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.billno.setText(Billdetaillist.get(position).getInvoiceno());
        holder.mobileno.setText(Billdetaillist.get(position).getPhoneno());
        holder.date.setText(Billdetaillist.get(position).getCreditdate());
        holder.billamount.setText(Billdetaillist.get(position).getOutstanding());
        holder.paid.setText(Billdetaillist.get(position).getPaid());
        holder.viewbills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                                       /* Intent intent = new Intent(settlecredit, Activity_masterScreen2.class);
                                        startActivity(intent);
                                      */
                BillDetailsactivity.GetCreditviewbillDetails(holder.mobileno.getText().toString(),holder.billno.getText().toString(),holder.date.getText().toString(),holder.billamount.getText().toString());
                // settlecredit.startActivity(new Intent(settlecredit, BillDetails.class));


            }
        });




        return convertView;

    }


}