package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.InventoryTotalActivity;
import com.RSPL.POS.R;
import com.RSPL.POS.activity_inventory;

import java.util.ArrayList;

import Pojo.Inventorymodel;

/**
 * Created by rspl-rahul on 19/12/16.
 */

public class InventoryVendorNmAdapter extends ArrayAdapter<Inventorymodel> {
    InventoryTotalActivity activity;
    private final int layoutResourceId;
    ArrayList<Inventorymodel> list;
    LayoutInflater layoutInflater;

    public InventoryVendorNmAdapter(InventoryTotalActivity activity, int textViewResourceId, ArrayList<Inventorymodel> objects) {
        super(activity, textViewResourceId, objects);
        this.activity=activity;
        this.layoutResourceId=textViewResourceId;
        this.list=objects;
    }

    public void setList(ArrayList<Inventorymodel> list) {
        this.list = list;
    }

    public int getCount() {
        if(list.size()<0)
            return 1;
        return list.size();
    }
    public Inventorymodel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView vendorName;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_inventory_vendornstore_row,parent,false);
            holder.vendorName=(TextView)convertView.findViewById(R.id.vendorName);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.vendorName.setText(list.get(position).getVendorName());
        return convertView;
    }
}
