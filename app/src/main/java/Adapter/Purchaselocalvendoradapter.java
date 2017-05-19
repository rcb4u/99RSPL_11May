package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.ActivitylocalVendor;
import com.RSPL.POS.PurchaseActivity;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.localvendor;

/**
 * Created by rspl-gourav on 19/9/16.
 */
public class Purchaselocalvendoradapter extends ArrayAdapter<localvendor> {




    private final int layoutResourceId;
    private Context mcontext;
    PurchaseActivity activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;

    public Purchaselocalvendoradapter(PurchaseActivity activity, int layoutResourceId, ArrayList<localvendor> mlocalvendorlist) {
        super(activity, layoutResourceId, mlocalvendorlist);
        this.activity = activity;
        this.localvendorlist = mlocalvendorlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFvendorlistR_SERVICE);


    }
    public void setLocalVendorList(ArrayList<localvendor> LocalVendorList) {
        this.localvendorlist = LocalVendorList;
    }

    private ArrayList<localvendor> localvendorlist;
    public int getCount() {
        if (localvendorlist.size() < 0)
            return 1;
        return localvendorlist.size();
    }


    public localvendor getItem(int position) {
        return localvendorlist.get(position);
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

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.productlist, parent, false);
            holder.VendorId = (TextView) convertView.findViewById(R.id.Productlist_id);
            holder.VendorName = (TextView) convertView.findViewById(R.id.Productlist_name);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.VendorId.setText(localvendorlist.get(position).getLocalvendorid());
        holder.VendorName.setText(localvendorlist.get(position).getLocalvendorname());

        return convertView;


    }

















}
