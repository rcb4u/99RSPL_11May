package ReportTabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.PayByCashVendorPaymentModel;
import Pojo.ReportVendorModel;


public class FragmentPayByCashSearchAdapter extends ArrayAdapter<PayByCashVendorPaymentModel>{

    ArrayList<PayByCashVendorPaymentModel> idList;
    private Context context;
    private int resource;
    private View view;

    public FragmentPayByCashSearchAdapter(Context context, int resource, ArrayList<PayByCashVendorPaymentModel> objects) {
        super(context, resource, objects);
       this.context = context;
        this.resource=resource;
        this.idList=objects;
    }

    public void setList(ArrayList<PayByCashVendorPaymentModel> list) {
        this.idList = list;
    }

    public int getCount() {
        if(idList.size()<0)
            return 1;
        return idList.size();
    }
    public PayByCashVendorPaymentModel getItem(int position) {
        return idList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView grnId;
        TextView vendNm;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
          LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.report_display_search_grnid_row,parent,false);
            holder.grnId=(TextView)convertView.findViewById(R.id.GrnId);
            holder.vendNm=(TextView)convertView.findViewById(R.id.VendNm);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.grnId.setText(idList.get(position).getGrnId());
        holder.vendNm.setText(idList.get(position).getVendorNm());
        return convertView;
    }







    public ArrayList<PayByCashVendorPaymentModel> getList() {
        return idList;
    }




}
