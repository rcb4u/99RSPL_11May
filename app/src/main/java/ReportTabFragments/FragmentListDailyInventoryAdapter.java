package ReportTabFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.ActivityStore;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.InventoryReportModel;
import Pojo.SaleReportModel;


public class FragmentListDailyInventoryAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<InventoryReportModel> list = new ArrayList<InventoryReportModel>();
    public Context context;
    FragmentDailyInventoryReport fragmentDailyInventoryReport;
    FragmentListDailyInventoryAdapter activity;
    DBhelper mydb;

    public FragmentListDailyInventoryAdapter(ArrayList<InventoryReportModel> list, Context context, FragmentDailyInventoryReport fragmentDailyInventoryReport1) {
        this.list = list;
        this.context = context;
        this.fragmentDailyInventoryReport=fragmentDailyInventoryReport1;
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



    public void addIdToList( InventoryReportModel id ) {
        list.add(id);
    }

    public void setList(ArrayList<InventoryReportModel> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVPROD_NM;
        TextView TVBATCH;
        TextView TVEXPIRY;
        TextView TVQUANTITY;
       // TextView TVDAYS1MONTH;
        TextView TVUSERNM;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            mydb = new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.report_display_daily_inventory_row, null);
            holder = new ViewHolder();
            holder.TVPROD_NM = (TextView) convertView.findViewById(R.id.reportProd_Nm);
            holder.TVPROD_NM.setTextSize(Float.parseFloat(textsize));

            holder.TVUSERNM = (TextView) convertView.findViewById(R.id.reportUser_Nm);
            holder.TVUSERNM.setTextSize(Float.parseFloat(textsize));


            holder.TVBATCH = (TextView) convertView.findViewById(R.id.reportBatch_No);
            holder.TVBATCH.setTextSize(Float.parseFloat(textsize));

            holder.TVEXPIRY = (TextView) convertView.findViewById(R.id.reportExp_Date);
            holder.TVEXPIRY.setTextSize(Float.parseFloat(textsize));

            holder.TVQUANTITY = (TextView) convertView.findViewById(R.id.report_Qty);
            holder.TVQUANTITY.setTextSize(Float.parseFloat(textsize));

            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVPROD_NM.setText(list.get(position).getProd_Nm());
        holder.TVBATCH.setText(list.get(position).getBatch());
        holder.TVEXPIRY.setText(list.get(position).getExpiry());
        holder.TVUSERNM.setText(list.get(position).getUser_Nm());
        holder.TVQUANTITY.setText(list.get(position).getQuantity());



        return convertView;
    }

    public ArrayList<InventoryReportModel> getList() {
        return list;
    }
}