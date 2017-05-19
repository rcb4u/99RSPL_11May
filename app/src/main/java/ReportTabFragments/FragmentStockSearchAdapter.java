package ReportTabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.InventoryReportModel;
import Pojo.PayByChequeVendorPaymentModel;
import Pojo.StockInventoryReportModel;


public class FragmentStockSearchAdapter extends ArrayAdapter<StockInventoryReportModel>{

    ArrayList<StockInventoryReportModel> idList;
    private Context context;
    private int resource;
    private View view;

    public FragmentStockSearchAdapter(Context context, int resource, ArrayList<StockInventoryReportModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource=resource;
        this.idList=objects;
    }

    public void setList(ArrayList<StockInventoryReportModel> list) {
        this.idList = list;
    }



    public int getCount() {
        if(idList.size()<0)
            return 1;
        return idList.size();
    }
    public StockInventoryReportModel getItem(int position) {
        return idList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        // TextView InvoiceNo;
        TextView DstrNm;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.report_display_search_dstrnm_row,parent,false);
            //  holder.InvoiceNo=(TextView)convertView.findViewById(R.id.GrnId);
            holder.DstrNm=(TextView)convertView.findViewById(R.id.DstrNm);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.InvoiceNo.setText(idList.get(position).getInvoiceNo());
        holder.DstrNm.setText(idList.get(position).getDstrNm());
        return convertView;
    }


    public ArrayList<StockInventoryReportModel> getList() {
        return idList;
    }





}
