package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;
import com.RSPL.POS.StockAdjustment;

import java.util.ArrayList;

import Pojo.InventoryStockadjustmentmodel;

/**
 * Created by rspl-rahul on 6/9/16.
 */
public class Inventorystockadapter extends ArrayAdapter<InventoryStockadjustmentmodel> {

    StockAdjustment activity;
    private final int layoutResourceId;
    ArrayList<InventoryStockadjustmentmodel> list;
    LayoutInflater layoutInflater;

    public Inventorystockadapter(StockAdjustment activity, int textViewResourceId, ArrayList<InventoryStockadjustmentmodel> objects) {
        super(activity, textViewResourceId, objects);
        this.activity = activity;
        this.layoutResourceId = textViewResourceId;
        this.list = objects;
    }

    public void setList(ArrayList<InventoryStockadjustmentmodel> list) {
        this.list = list;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        return list.size();
    }

    public InventoryStockadjustmentmodel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView productName;
        TextView batch;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_inventory_stock_row, parent, false);
            holder.productName = (TextView) convertView.findViewById(R.id.productName);
            holder.batch = (TextView) convertView.findViewById(R.id.batchno);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.productName.setText(list.get(position).getProductName());
        holder.batch.setText(list.get(position).getBatchno());


        return convertView;
    }
}
