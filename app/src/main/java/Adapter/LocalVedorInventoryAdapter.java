package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.ActivityLocalProduct;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Vendor;

/**
 * Created by rspl-rahul on 28/12/16.
 */

public class LocalVedorInventoryAdapter extends ArrayAdapter<Vendor>
{
private  int layoutResourceId;
private Context mcontext;
        ActivityLocalProduct activity;
private LayoutInflater mInflater;
        TextView tv;
        TextView v;


public LocalVedorInventoryAdapter(Context context, ArrayList<Vendor> vendors, int resource, Object o) {
        super(context, resource);
        }

public LocalVedorInventoryAdapter(ActivityLocalProduct activity, int layoutResourceId, ArrayList<Vendor> mvendorlist) {
        super(activity, layoutResourceId, mvendorlist);
        System.out.println("in localvendor adapter");
        this.activity = activity;
        this.vendorlist = mvendorlist;
        this.layoutResourceId = layoutResourceId;
        }

public void setVendorList(ArrayList<Vendor> VendorList) {
        this.vendorlist = VendorList;
        System.out.println("in setvendorlist");
        }

private ArrayList<Vendor> vendorlist;

public int getCount() {
        if (vendorlist.size() < 0)
        return 1;
        return vendorlist.size();
        }


public Vendor getItem(int position) {
        return vendorlist.get(position);
        }


public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
        }

public static class ViewHolder {

    public TextView VendorId;
    public TextView VendorName;

}

    public View getView(int position, View convertView, ViewGroup parent) {

        VendorAdapter.ViewHolder holder;

        if (convertView == null) {

            holder = new VendorAdapter.ViewHolder();

            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.productlist, parent, false);
            //  holder.VendorId = (TextView) convertView.findViewById(R.id.Productlist_id);
            holder.VendorName = (TextView) convertView.findViewById(R.id.Productlist_name);

            convertView.setTag(holder);
        } else {
            holder = (VendorAdapter.ViewHolder) convertView.getTag();
        }
        //   holder.VendorId.setText(vendorlist.get(position).getVendorId());
        holder.VendorName.setText(vendorlist.get(position).getVendorname());

        return convertView;

    }

}