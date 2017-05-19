package Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.Activity_Top_Product;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import Pojo.Topfullproductmodel;




public class TopProductDropDownAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    Activity_Top_Product activity;
    ArrayList<Topfullproductmodel> list;
    DBhelper helper;
    public TopProductDropDownAdapter(ArrayList<Topfullproductmodel> list, Activity_Top_Product activity) {
        this.list = list;
        this.activity = activity;
    }

    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addVendorToList(Topfullproductmodel vendor) {
        Log.e("&&&&&&&&", "Adding product " + vendor.toString() + " to product list");
        list.add(vendor);
    }

    public void setList(ArrayList<Topfullproductmodel> list) {
        this.list = list;
    }

    public static class ViewHolder {

        TextView Product_Name;
        TextView Prodcutshortname;
        ImageButton DeleteBtn;

    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_dropdown_top_product, null);
            holder = new ViewHolder();

            holder.Product_Name=(TextView)convertView.findViewById(R.id.rowtvOrder_No);
            holder.Prodcutshortname=(TextView)convertView.findViewById(R.id.Shortname);
            holder.DeleteBtn=(ImageButton)convertView.findViewById(R.id.deleteButton) ;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.Product_Name.setText(list.get(position).getProductname());
        holder.Prodcutshortname.setText(list.get(position).getShortname());
        holder.Product_Name.setText(list.get(position).getProductname());
        holder.DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper= new DBhelper(activity);
                DeleteTop_Product(position);
                // helper.DeleteRecord(list.get(position).getProductname());
            }
        });


        return convertView;
    }

    public  void DeleteTop_Product( final int pos)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want Delete this?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                    try {
                        helper.DeleteRecord(list.get(pos).getProductname());
                        list.remove(pos);
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public ArrayList<Topfullproductmodel> getList() {
        return list;
    }
}
