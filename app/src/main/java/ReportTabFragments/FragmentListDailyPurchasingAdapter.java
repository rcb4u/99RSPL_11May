package ReportTabFragments;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.VendorReportModel;


public class FragmentListDailyPurchasingAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<VendorReportModel> list = new ArrayList<VendorReportModel>();
    private Context context;
    FragmentDailyPurchasingReport fragmentDailyPurchasingReport;


    public FragmentListDailyPurchasingAdapter(ArrayList<VendorReportModel> list, Context context,FragmentDailyPurchasingReport fragmentDailyPurchasingReport1) {
        this.list = list;
        this.context = context;
        this.fragmentDailyPurchasingReport = fragmentDailyPurchasingReport1;
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addVendorToList(VendorReportModel id) {
        list.add(id);
    }

    public void setList(ArrayList<VendorReportModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVPO_NO;
        TextView TVVENDR_NM;
        TextView TVTOTAL;
        TextView TVUSER_NM;
        Button View_Details;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_purchase_order_row, null);
            holder = new ViewHolder();
            holder.TVUSER_NM = (TextView) convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TVPO_NO = (TextView) convertView.findViewById(R.id.rowtvOrder_No);
            holder.TVVENDR_NM = (TextView) convertView.findViewById(R.id.rowtvVendr_Nm);
            holder.TVTOTAL = (TextView) convertView.findViewById(R.id.rowtv_Total);
            holder.View_Details=(Button)convertView.findViewById(R.id.View_Details);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVUSER_NM.setText(list.get(position).getUserNm());
        holder.TVPO_NO.setText(list.get(position).getPo_No());
        holder.TVVENDR_NM.setText(list.get(position).getVendor_Nm());
        DecimalFormat f=new DecimalFormat("##.00");
            Double Value = Double.valueOf(list.get(position).getTotal());
        holder.TVTOTAL.setText(f.format(Value));
        holder.View_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentDailyPurchasingReport.GetDailyDetailPage(holder.TVPO_NO.getText().toString());
            }
        });
        return convertView;
    }
    public ArrayList<VendorReportModel> getList() {
        return list;
    }

    public float getGrandTotal()
    {
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            try {
                finalamount += Float.parseFloat(list.get(listIndex).getTotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
        }
        return finalamount;
    }
}


