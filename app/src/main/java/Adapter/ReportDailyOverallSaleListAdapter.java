package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;
import com.RSPL.POS.ShowDailyOverallSalesReportListActivity;
import com.RSPL.POS.ShowDailySalesReportListActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.SaleReportModel;


public class ReportDailyOverallSaleListAdapter extends BaseAdapter
{

    ShowDailyOverallSalesReportListActivity activity;
    ArrayList<SaleReportModel> list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    SaleReportModel purchaseProductModel;
    boolean isUserEditingQuantityTextView = true;


    public ReportDailyOverallSaleListAdapter(ShowDailyOverallSalesReportListActivity activity, ArrayList<SaleReportModel> list, int layoutResourceId, SaleReportModel purchaseProductModel) {
        Log.e("AAAAAAAA&&&&&&&&&", "in fullproductAdapterclass inside constructor");
        this.activity = activity;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.purchaseProductModel = purchaseProductModel;

    }

    public long getItemId(int position)
    {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<0)
            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }
    public SaleReportModel getItem(int position) {

        return list.get(position);
    }

    public static class ViewHolder
    {
        TextView TV_NAME;
        public TextView Exp;
        public TextView Mrp;
        public TextView Qty;
        TextView TVBATCH;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView==null)
        {

            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_full_dailyoverallsales_grandtotal_row,parent,false);
            holder.TV_NAME=(TextView)convertView.findViewById(R.id.rowtvProdNm);
            holder.TVBATCH=(TextView)convertView.findViewById(R.id.rowtvBatchNo);
            holder.Exp =(TextView)convertView.findViewById(R.id.rowtvExpDate);
            holder.Mrp=(TextView)convertView.findViewById(R.id.rowtvMrp);
            holder.Qty=(TextView)convertView.findViewById(R.id.rowtvQty);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.TVBATCH.setText(list.get(position).getBatch());
        holder.Exp.setText(list.get(position).getExp());
        holder.TV_NAME.setText(list.get(position).getProdNm());
        holder.Qty.setText(list.get(position).getQty());
        DecimalFormat f=new DecimalFormat("##.00");
        String MRP=list.get(position).getMrp();
        Float mrp=Float.parseFloat(MRP);
        holder.Mrp.setText(f.format(mrp));

        return convertView;
    }

    public ArrayList<SaleReportModel> getList() {
        return list;
    }
}
