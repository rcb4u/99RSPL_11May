package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.Activity_Top_Product;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Topfullproductmodel;

/**
 * Created by rspl-nishant on 10/3/16.
 */
public class Top_Product_Adapter extends ArrayAdapter<Topfullproductmodel>{


    private final int layoutResourceId;
    private Context mcontext;
    Activity_Top_Product activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;

    public Top_Product_Adapter(Activity_Top_Product activity, int layoutResourceId, ArrayList<Topfullproductmodel> mlocalproductlist) {
        super(activity, layoutResourceId, mlocalproductlist);
        this.activity = activity;
        this.localproductlist = mlocalproductlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFvendorlistR_SERVICE);


    }
    public void setLocalProductList(ArrayList<Topfullproductmodel> LocalProductList) {
        this.localproductlist= LocalProductList;
    }

    private ArrayList<Topfullproductmodel> localproductlist;
    public int getCount() {
        if (localproductlist.size() < 0)
            return 1;
        return localproductlist.size();
    }


    public Topfullproductmodel getItem(int position) {
        return localproductlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public static class ViewHolder {

        public TextView LocalprodId;
        public TextView LocalprodName;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.top_productlist, parent, false);
            holder.LocalprodId = (TextView) convertView.findViewById(R.id.Top_Productlist_id);
            holder.LocalprodName = (TextView) convertView.findViewById(R.id.Top_Productlist_name);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.LocalprodName.setText(localproductlist.get(position).getProductname());
        holder.LocalprodId.setText(localproductlist.get(position).getProductId());
        return convertView;


    }




















}
