package ReportTabFragments;


import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Decimal;
import Pojo.InventoryReportModel;


public class FragmentListInventoryAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<InventoryReportModel> list = new ArrayList<InventoryReportModel>();
    FragmentInventoryReport fragmentInventoryReport;
    private Context context;
    DBhelper mydb;

    public FragmentListInventoryAdapter(ArrayList<InventoryReportModel> list, Context context,FragmentInventoryReport fragmentInventoryReport1) {
        this.list = list;
        this.context = context;
        this.fragmentInventoryReport = fragmentInventoryReport1;
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

    public void addVendorToList(InventoryReportModel id) {
        list.add(id);
    }

    public void setList(ArrayList<InventoryReportModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVUSER_NM;
        TextView TVPROD_NM;
        TextView TVBATCH;
        TextView TVEXPIRY;
        TextView TVQUANTITY;
        TextView TVDAYS;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            mydb = new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.report_display_inventory_row, null);
            holder = new ViewHolder();
            holder.TVUSER_NM = (TextView) convertView.findViewById(R.id.reportUser_Nm);
            holder.TVUSER_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVPROD_NM = (TextView) convertView.findViewById(R.id.reportProd_Nm);
            holder.TVPROD_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVBATCH = (TextView) convertView.findViewById(R.id.reportBatch_No);
            holder.TVBATCH.setTextSize(Float.parseFloat(textsize));
            holder.TVEXPIRY = (TextView) convertView.findViewById(R.id.reportExp_Date);
            holder.TVEXPIRY.setTextSize(Float.parseFloat(textsize));
            holder.TVQUANTITY = (TextView) convertView.findViewById(R.id.report_Qty);
            holder.TVQUANTITY.setTextSize(Float.parseFloat(textsize));
            holder.TVDAYS = (TextView) convertView.findViewById(R.id.report_month);
            holder.TVDAYS.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.TVUSER_NM.setText(list.get(position).getUser_Nm());
        holder.TVPROD_NM.setText(list.get(position).getProd_Nm());
        holder.TVBATCH.setText(list.get(position).getBatch());
        holder.TVEXPIRY.setText(list.get(position).getExpiry());
        holder.TVQUANTITY.setText(list.get(position).getQuantity());
        String StrExpdate = list.get(position).getExpiry();
        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
        String curr_date = s.toString();
        SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
        //Dates to compare
        Date date1;
        Date date2;
        Long DaysLeft = null;
        try {
            //Setting dates
            date1 = dates.parse(curr_date);
            date2 = dates.parse(StrExpdate);
            //Comparing dates
            long difference = date2.getTime() - date1.getTime();
            DaysLeft = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);

            if(DaysLeft!=0)
            {
                holder.TVDAYS.setText(DaysLeft.toString());
            }

        } catch (Exception exception) {
        }
        return convertView;
    }
    public ArrayList<InventoryReportModel> getList() {
        return list;
    }
}


