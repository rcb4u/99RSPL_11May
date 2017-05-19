package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowDailySalesReportListActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.SaleReportModel;


public class ReportDailySaleListAdapter extends BaseAdapter
{

    ShowDailySalesReportListActivity activity;
    ArrayList<SaleReportModel> list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    SaleReportModel purchaseProductModel;
    boolean isUserEditingQuantityTextView = true;
    DBhelper mydb;


    public ReportDailySaleListAdapter(ShowDailySalesReportListActivity activity, ArrayList<SaleReportModel> list, int layoutResourceId, SaleReportModel purchaseProductModel) {
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
        public TextView Sprice;
        public TextView Qty;
        public TextView Total;
        TextView TVCARDTYPE;
        TextView TVBANKNM;
        TextView TVBATCH;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;

        if (convertView==null)
        {

            mydb=new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();


            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_full_dailysales_grandtotal_row,parent,false);
            holder.TV_NAME=(TextView)convertView.findViewById(R.id.rowtvProdNm);
            holder.TVBATCH=(TextView)convertView.findViewById(R.id.rowtvBatchNo);
            holder.Exp =(TextView)convertView.findViewById(R.id.rowtvExpDate);
            holder.Sprice=(TextView)convertView.findViewById(R.id.rowtvSprice);
            holder.Total=(TextView)convertView.findViewById(R.id.rowtvTotal);
            holder.Qty=(TextView)convertView.findViewById(R.id.rowtvQty);
            holder.TVCARDTYPE=(TextView)convertView.findViewById(R.id.rowtvCard_Type);
            holder.TVBANKNM=(TextView)convertView.findViewById(R.id.rowtvBank_Nm);


            holder.TV_NAME.setTextSize(Float.parseFloat(textsize));
            holder.TVBATCH.setTextSize(Float.parseFloat(textsize));
            holder.Exp.setTextSize(Float.parseFloat(textsize));
            holder.Sprice.setTextSize(Float.parseFloat(textsize));
            holder.Qty.setTextSize(Float.parseFloat(textsize));
            holder.TVCARDTYPE.setTextSize(Float.parseFloat(textsize));
            holder.TVBANKNM.setTextSize(Float.parseFloat(textsize));
            holder.Total.setTextSize(Float.parseFloat(textsize));

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
        holder.TVCARDTYPE.setText(list.get(position).getCardType());
        holder.TVBANKNM.setText(list.get(position).getBankNm());
        DecimalFormat f=new DecimalFormat("##.00");
        Double Value = Double.valueOf(list.get(position).getSPrice());
        holder.Sprice.setText(f.format(Value));
        holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.Sprice.getText().toString()) * Double.parseDouble(holder.Qty.getText().toString()))));

        return convertView;
    }

    public ArrayList<SaleReportModel> getList() {
        return list;
    }
}