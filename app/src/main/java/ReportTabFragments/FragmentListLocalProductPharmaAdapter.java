package ReportTabFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.ReportLocalProductPharmaModel;


public class FragmentListLocalProductPharmaAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ReportLocalProductPharmaModel> list = new ArrayList<ReportLocalProductPharmaModel>();

    private Context context;
    DBhelper helper;

    public FragmentListLocalProductPharmaAdapter(ArrayList<ReportLocalProductPharmaModel> list, Context context) {
        this.list = list;
        this.context = context;
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

    public void addProductToList(ReportLocalProductPharmaModel product ) {
        list.add(product);

    }

    public void setList(ArrayList<ReportLocalProductPharmaModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVUSER_NM;
        TextView TVLOCAL_NM;
        TextView TVBAR_CODE;
        TextView TVLOCAL_MRP;
        TextView TV_SPRICE;
        TextView TV_PPRICE;
        TextView TVLOCAL_ACTVE;
        TextView TV_MARGIN;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            helper=new DBhelper(context);
            Decimal valuetextsize = helper.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_report_localprod_pharma_row, null);
            holder = new ViewHolder();
            holder.TVUSER_NM=(TextView)convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TVUSER_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVLOCAL_NM=(TextView)convertView.findViewById(R.id.rowtvLocal_Nm);
            holder.TVLOCAL_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVBAR_CODE=(TextView)convertView.findViewById(R.id.rowtvBar_Code);
            holder.TVBAR_CODE.setTextSize(Float.parseFloat(textsize));
            holder.TVLOCAL_MRP=(TextView)convertView.findViewById(R.id.rowtvMrp);
            holder.TVLOCAL_MRP.setTextSize(Float.parseFloat(textsize));
            holder.TV_SPRICE=(TextView)convertView.findViewById(R.id.rowtvSPrice);
            holder.TV_SPRICE.setTextSize(Float.parseFloat(textsize));
            holder.TV_PPRICE=(TextView)convertView.findViewById(R.id.rowtvPPrice);
            holder.TV_PPRICE.setTextSize(Float.parseFloat(textsize));
            holder.TVLOCAL_ACTVE=(TextView)convertView.findViewById(R.id.rowtv_Active);
            holder.TVLOCAL_ACTVE.setTextSize(Float.parseFloat(textsize));
            holder.TV_MARGIN=(TextView)convertView.findViewById(R.id.rowtvP_Margin);
            holder.TV_MARGIN.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVUSER_NM.setText(list.get(position).getUserNm());
        holder.TVLOCAL_NM.setText(list.get(position).getProd_Nm());
        holder.TVBAR_CODE.setText(list.get(position).getBarCode());
        holder.TVLOCAL_MRP.setText(list.get(position).getMRP());
        holder.TV_SPRICE.setText(list.get(position).getS_Price());
        holder.TV_PPRICE.setText(list.get(position).getPPrice());
        holder.TVLOCAL_ACTVE.setText(list.get(position).getActive());
        holder.TV_MARGIN.setText(list.get(position).getMargin());
        return convertView;
    }
    public ArrayList<ReportLocalProductPharmaModel> getList() {
        return list;
    }
}


