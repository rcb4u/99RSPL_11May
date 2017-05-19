package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesbill;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.CreditCustomer;

/**
 * Created by shilpa on 28/7/16.
 */
public class HdCreditCustomerAdapter extends ArrayAdapter<CreditCustomer> {
    private final int layoutResourceId;
    //private final int layoutResourceId;
    private Context mcontext;
    ActivitySalesbill activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;


    public HdCreditCustomerAdapter(ActivitySalesbill activity, int layoutResourceId, ArrayList<CreditCustomer> mcreditcustomerlist) {
        super(activity, layoutResourceId, mcreditcustomerlist);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.hcreditcustomerlist = mcreditcustomerlist;
    }

    public void setcreditcustomerlist(ArrayList<CreditCustomer> creditcustomerlist) {
        this.hcreditcustomerlist = creditcustomerlist;


    }

    private ArrayList<CreditCustomer> hcreditcustomerlist;

    public int getCount() {
        if (hcreditcustomerlist.size() < 0)
            return 1;
        return hcreditcustomerlist.size();
    }

    public CreditCustomer getItem(int position) {
        return hcreditcustomerlist.get(position);
    }

    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public static class ViewHolder {

        public TextView name;
        public TextView mobile_no;
        public TextView invoiceno;
        public TextView creditamount;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.display_hdfullcreditcust_list, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.Creditcustname);
            holder.invoiceno=(TextView)convertView.findViewById(R.id.Customerinvoice);
            holder.mobile_no=(TextView)convertView.findViewById(R.id.Customermobileno);
            holder.creditamount=(TextView)convertView.findViewById(R.id.Customercredt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.mobile_no.setText(hcreditcustomerlist.get(position).getMobileNo());
        holder.creditamount.setText(hcreditcustomerlist.get(position).getCreditcustgrandtotal());
       // holder.invoiceno.setText(hcreditcustomerlist.get(position).getCreditcustinvoiceno());


        return convertView;

    }
}