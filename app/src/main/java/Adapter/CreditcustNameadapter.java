package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.Activity_SettleCreditCustomer;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Settlecustmodel;

/**
 * Created by rspl-rahul on 22/9/16.
 */
public class CreditcustNameadapter extends ArrayAdapter<Settlecustmodel> {
    Activity_SettleCreditCustomer creditCustomeractivity;
    private final int layoutResourceId;
    ArrayList<Settlecustmodel> CreditcustNamelist;
    LayoutInflater layoutInflater;


    public CreditcustNameadapter(Activity_SettleCreditCustomer creditCustomeractivity, int textViewResourceId, ArrayList<Settlecustmodel> CreditcustNamelist) {
        super(creditCustomeractivity, textViewResourceId,CreditcustNamelist);
        this.layoutResourceId = textViewResourceId;
        this.creditCustomeractivity = creditCustomeractivity;
        this.CreditcustNamelist = CreditcustNamelist;
    }

    public void setList(ArrayList<Settlecustmodel> CreditcustNamelist)

    {
        this.CreditcustNamelist = CreditcustNamelist;
    }

    public int getCount() {
        if (CreditcustNamelist.size() < 0)
            return 1;
        return CreditcustNamelist.size();
    }

    public Settlecustmodel getItem(int position) {
        return CreditcustNamelist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public static class ViewHolder {
        public TextView CustomerName;
        public TextView Phoneno;
        public TextView Creditcustinvoiceno;


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) creditCustomeractivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.displaycreditcustname_row, parent, false);
            holder.CustomerName = (TextView) convertView.findViewById(R.id.custname);
            holder.Phoneno = (TextView) convertView.findViewById(R.id.phoneno);
            holder.Creditcustinvoiceno = (TextView) convertView.findViewById(R.id.creditcustinvoiceno);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.CustomerName.setText(CreditcustNamelist.get(position).getName());
        holder.Phoneno.setText(CreditcustNamelist.get(position).getPhoneno());
        holder.Creditcustinvoiceno.setText(CreditcustNamelist.get(position).getInvoiceno());
        return convertView;

    }


}