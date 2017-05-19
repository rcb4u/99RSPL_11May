package ReportTabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.ReportVendorModel;
import Pojo.SalesReturnReportModel;


public class FragmentSearchWithInvoiceAdapter extends ArrayAdapter<SalesReturnReportModel> {

    ArrayList<SalesReturnReportModel> idList;
    private Context context;
    private int resource;
    private View view;


    public FragmentSearchWithInvoiceAdapter(Context context, int resource, ArrayList<SalesReturnReportModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.idList=objects;
    }

    public void setList(ArrayList<SalesReturnReportModel> list) {
        this.idList = list;
    }

    public int getCount() {
        if(idList.size()<0)
            return 1;
        return idList.size();
    }
    public SalesReturnReportModel getItem(int position) {
        return idList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView transactionId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_search_transid_withinvoice_row,parent,false);
            holder.transactionId=(TextView)convertView.findViewById(R.id.TranscId);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.transactionId.setText(idList.get(position).toString());
        return convertView;
    }




    public ArrayList<SalesReturnReportModel> getList() {
        return idList;
    }



}
