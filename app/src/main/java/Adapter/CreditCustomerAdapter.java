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
 * Created by shilpa on 13/7/16.
 */
public class CreditCustomerAdapter extends ArrayAdapter<CreditCustomer> {

    private final int layoutResourceId;
    //private final int layoutResourceId;
    private Context mcontext;
    ActivitySalesbill activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;


    public CreditCustomerAdapter(ActivitySalesbill activity, int layoutResourceId, ArrayList<CreditCustomer> mcreditcustomerlist) {
        super(activity, layoutResourceId, mcreditcustomerlist);
        this.activity = activity;
        this.layoutResourceId = layoutResourceId;
        this.creditcustomerlist = mcreditcustomerlist;


    }

    public void setcreditcustomerlist(ArrayList<CreditCustomer> creditcustomerlist) {

    }

    private ArrayList<CreditCustomer> creditcustomerlist;

    public int getCount() {
        if (creditcustomerlist.size() < 0)
            return 1;
        return creditcustomerlist.size();
    }

    public CreditCustomer getItem(int position) {
        return creditcustomerlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public static class ViewHolder {

        public TextView name;
        public TextView mobile_no;
        public TextView invoiceno;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.display_creditcust_list, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.Creditcustname);
            holder.invoiceno=(TextView)convertView.findViewById(R.id.Customerinvoice);
            holder.invoiceno=(TextView)convertView.findViewById(R.id.Customermobileno);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        holder.name.setText(creditcustomerlist.get(position).getCreditcustname());
       // holder.invoiceno.setText(creditcustomerlist.get(position).getCreditcustinvoiceno());


        return convertView;

    }
}

