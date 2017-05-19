package ReportTabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.VendorReportModel;

public class FragmentSearchPurchasingAdapter extends ArrayAdapter<VendorReportModel> {



    ArrayList<VendorReportModel> vendorList;
    private Context context;
    private int resource;

    public FragmentSearchPurchasingAdapter(Context context, int resource, ArrayList<VendorReportModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.vendorList=objects;
    }




    public void setList(ArrayList<VendorReportModel> list) {
        this.vendorList = list;
    }

    public int getCount() {
        if(vendorList.size()<0)
            return 1;
        return vendorList.size();
    }
    public VendorReportModel getItem(int position) {
        return vendorList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView vendorName;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_search_vendorname_row,parent,false);
            holder.vendorName=(TextView)convertView.findViewById(R.id.vendorName);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.vendorName.setText(vendorList.get(position).getVendor_Nm());
        return convertView;
    }







}
