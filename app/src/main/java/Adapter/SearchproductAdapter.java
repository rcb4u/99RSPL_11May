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

import Pojo.Sales;


/**
 * Created by rspl-gourav on 30/6/16.
 */
public class SearchproductAdapter extends ArrayAdapter<Sales> {
    ActivitySalesbill activity;

    private final int layoutResourceId;
    ArrayList<Sales> list;
    LayoutInflater layoutInflater;



    public SearchproductAdapter(ActivitySalesbill activity, int textViewResourceId, ArrayList<Sales> list) {
        super(activity, textViewResourceId, list);
        this.layoutResourceId = textViewResourceId;
        this.activity = activity;
        this.list = list;
    }



    public void setList(ArrayList<Sales> list) {
        this.list = list;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        return list.size();
    }

    public Sales getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {

        TextView PurchaseId;
        TextView PurchaseName;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.isplay_search_product, parent, false);
            // holder.PurchaseId=(TextView)convertView.findViewById(R.id.PurchaseproductId);
            holder.PurchaseName = (TextView) convertView.findViewById(R.id.searchproductname);
            holder.PurchaseId = (TextView) convertView.findViewById(R.id.searchproductid);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.PurchaseName.setText(list.get(position).getProductName());
            holder.PurchaseId.setText(list.get(position).getProductId());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
