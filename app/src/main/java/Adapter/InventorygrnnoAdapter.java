package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.InventoryTotalActivity;
import com.RSPL.POS.R;
import com.RSPL.POS.activity_inventory;

import java.util.ArrayList;

import Pojo.Inventorygrnmodel;

/**
 * Created by rspl-abhinay on 28/12/16.
 */

public class InventorygrnnoAdapter extends BaseAdapter
{
    InventoryTotalActivity activity;
    private final int layoutResourceId;
    ArrayList<Inventorygrnmodel> list;
    LayoutInflater layoutInflater;

    public InventorygrnnoAdapter(InventoryTotalActivity activity, int textViewResourceId, ArrayList<Inventorygrnmodel> objects) {
        this.activity=activity;
        this.layoutResourceId=textViewResourceId;
        this.list=objects;
    }



    public void setList(ArrayList<Inventorygrnmodel> list) {
        this.list = list;
    }


    public void getList(ArrayList<Inventorygrnmodel> list) {
        this.list = list;
    }


    public int getCount() {
        if(list.size()<0)
            return 1;
        return list.size();
    }
    public Inventorygrnmodel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView grnNumber;
        TextView TimeStamp;
        TextView flag;

    }
    public View getView(int position, View convertView, ViewGroup parent) {
        Inventorygrnadapter.ViewHolder holder;
        if(convertView==null){
            holder= new Inventorygrnadapter.ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_grn_no_dropdown,parent,false);
            holder.grnNumber=(TextView)convertView.findViewById(R.id.GRNNumber);
            holder.TimeStamp=(TextView)convertView.findViewById(R.id.timestamp);
            holder.flag=(TextView)convertView.findViewById(R.id.flag);


            convertView.setTag(holder);
        }
        else
        {
            holder = (Inventorygrnadapter.ViewHolder) convertView.getTag();
        }
        holder.grnNumber.setText(list.get(position).getInventoryOrderNo());
        try {
            String input = list.get(position).getLastUpdate();
            String output = input.substring(0, 10);
            holder.TimeStamp.setText(output.toString());
            holder.flag.setText(list.get(position).getFlag());

        }catch(NullPointerException ex)
        {
            ex.printStackTrace();
        }return convertView;
    }

}
