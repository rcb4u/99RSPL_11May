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
import com.RSPL.POS.ReportVendorReturnActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.ReportVendorReturnModel;

public class ReportVendorReturnAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ReportVendorReturnModel> list=new ArrayList<ReportVendorReturnModel>();
    ReportVendorReturnActivity activity;
    DBhelper mydb;

    public ReportVendorReturnAdapter(ArrayList<ReportVendorReturnModel> list, ReportVendorReturnActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public long getItemId(int position)
    {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<0)
            return 1;
        Log.e("**get Count***",list.toString());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addIdToList( ReportVendorReturnModel id ) {
        Log.e("&&&&&&&&", "Adding Id " + id.toString() + " to list");
        list.add(id);
    }

    public void setList(ArrayList<ReportVendorReturnModel> list) {
        this.list = list;
    }

    public static class ViewHolder{

        TextView TVVEND_NM;
        TextView TVREASON_RETRN;
        TextView TVPROD_NM;
        TextView TVBATCH_NO;
        TextView TVEXP_DATE;
        TextView TVP_PRICE;
        TextView TVQTY;
        TextView TVUNIT;
        TextView TVTOTAL;
        TextView TVUSER;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView==null)
        {
            mydb = new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /***** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView =layoutInflater.inflate(R.layout.display_report_vendor_return,null);
            holder = new ViewHolder();
            holder.TVUSER=(TextView)convertView.findViewById(R.id.reportuser_name);
            holder.TVVEND_NM=(TextView)convertView.findViewById(R.id.reportvend_name);
            holder.TVREASON_RETRN=(TextView)convertView.findViewById(R.id.report_reason);
            holder.TVPROD_NM=(TextView)convertView.findViewById(R.id.reportprod_name);
            holder.TVBATCH_NO=(TextView)convertView.findViewById(R.id.report_No);
            holder.TVEXP_DATE=(TextView)convertView.findViewById(R.id.report_date);
            holder.TVP_PRICE=(TextView)convertView.findViewById(R.id.report_price);
            holder.TVQTY=(TextView)convertView.findViewById(R.id.report_qty);
            holder.TVUNIT=(TextView)convertView.findViewById(R.id.report_unit);
            holder.TVTOTAL=(TextView)convertView.findViewById(R.id.report_total);

            holder.TVUSER.setTextSize(Float.parseFloat(textsize));
            holder.TVVEND_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVREASON_RETRN.setTextSize(Float.parseFloat(textsize));
            holder.TVPROD_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVBATCH_NO.setTextSize(Float.parseFloat(textsize));
            holder.TVEXP_DATE.setTextSize(Float.parseFloat(textsize));
            holder.TVP_PRICE.setTextSize(Float.parseFloat(textsize));
            holder.TVQTY.setTextSize(Float.parseFloat(textsize));
            holder.TVUNIT.setTextSize(Float.parseFloat(textsize));
            holder.TVTOTAL.setTextSize(Float.parseFloat(textsize));





            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVUSER.setText(list.get(position).getUserNm());
        holder.TVVEND_NM.setText(list.get(position).getVendrNm());
        holder.TVREASON_RETRN.setText(list.get(position).getReason());
        holder.TVPROD_NM.setText(list.get(position).getProdctNm());
        holder.TVBATCH_NO.setText(list.get(position).getBatchNo());
        holder.TVEXP_DATE.setText(list.get(position).getExpDate());
        DecimalFormat f=new DecimalFormat("##.00");
        Double Value = Double.valueOf(list.get(position).getPPrice());
        holder.TVP_PRICE.setText(f.format(Value));
        holder.TVQTY.setText(list.get(position).getQty());
        holder.TVUNIT.setText(list.get(position).getUom());
        Double Value1 = Double.valueOf(list.get(position).getTotal());
        holder.TVTOTAL.setText(f.format(Value1));
        return convertView;
    }

    public ArrayList<ReportVendorReturnModel> getList() {
        return list;
    }
}
