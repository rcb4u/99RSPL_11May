package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;
import com.RSPL.POS.ShowDailySalesReportListActivity;
import com.RSPL.POS.ShowStockReportListActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.InventoryReportModel;
import Pojo.SaleReportModel;


public class ReportStockListAdapter extends BaseAdapter
{

    ShowStockReportListActivity activity;
    ArrayList<InventoryReportModel> list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    InventoryReportModel purchaseProductModel;
    boolean isUserEditingQuantityTextView = true;


    public ReportStockListAdapter(ShowStockReportListActivity activity, ArrayList<InventoryReportModel> list, int layoutResourceId, InventoryReportModel purchaseProductModel) {
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
    public InventoryReportModel getItem(int position) {

        return list.get(position);
    }

    public static class ViewHolder
    {
        TextView TVPROD_NM;
        TextView TVBATCH;
        TextView TVEXPIRY;
        TextView TVQUANTITY;
        TextView TVUSERNM;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView==null)
        {

            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_full_stock_grandtotal_row,parent,false);
            holder.TVPROD_NM = (TextView) convertView.findViewById(R.id.reportProd_Nm);
            holder.TVUSERNM = (TextView) convertView.findViewById(R.id.reportUser_Nm);
            holder.TVBATCH = (TextView) convertView.findViewById(R.id.reportBatch_No);
            holder.TVEXPIRY = (TextView) convertView.findViewById(R.id.reportExp_Date);
            holder.TVQUANTITY = (TextView) convertView.findViewById(R.id.report_Qty);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
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
