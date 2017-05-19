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

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.ReportProductPharmaModel;

public class FragmentListProductPharmaAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ReportProductPharmaModel> list = new ArrayList<ReportProductPharmaModel>();

    private Context context;
    DBhelper helper;

    public FragmentListProductPharmaAdapter(ArrayList<ReportProductPharmaModel> list, Context context) {
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

    public void addProductToList(ReportProductPharmaModel product ) {
        list.add(product);

    }

    public void setList(ArrayList<ReportProductPharmaModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVUSER_NM;
        TextView TVLOCAL_NM;
        TextView TVPROD_ID;
        // TextView TVBAR_CODE;
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
            convertView = layoutInflater.inflate(R.layout.display_report_product_pharma_row, null);
            holder = new ViewHolder();
           // holder.TVUSER_NM=(TextView)convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TVLOCAL_NM=(TextView)convertView.findViewById(R.id.rowtvProdct_Nm);
            holder.TVLOCAL_NM.setTextSize(Float.parseFloat(textsize));

            holder.TVPROD_ID=(TextView)convertView.findViewById(R.id.rowtv_ProdId);
            //holder.TVBAR_CODE=(TextView)convertView.findViewById(R.id.rowtv_BarCode);
            holder.TVPROD_ID.setTextSize(Float.parseFloat(textsize));
            holder.TVLOCAL_MRP=(TextView)convertView.findViewById(R.id.rowtv_Mrp);
            holder.TVLOCAL_MRP.setTextSize(Float.parseFloat(textsize));
            holder.TV_SPRICE=(TextView)convertView.findViewById(R.id.rowtv_SPrice);
            holder.TV_SPRICE.setTextSize(Float.parseFloat(textsize));
            holder.TV_PPRICE=(TextView)convertView.findViewById(R.id.rowtv_PPrice);
            holder.TV_PPRICE.setTextSize(Float.parseFloat(textsize));
            holder.TVLOCAL_ACTVE=(TextView)convertView.findViewById(R.id.rowtv_Actve);
            holder.TVLOCAL_ACTVE.setTextSize(Float.parseFloat(textsize));
            holder.TV_MARGIN=(TextView)convertView.findViewById(R.id.rowtv_Margin);
            holder.TV_MARGIN.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DecimalFormat f= new DecimalFormat("##.00");
       // holder.TVUSER_NM.setText(list.get(position).getUserNm());
        holder.TVLOCAL_NM.setText(list.get(position).getProd_Nm());
        //holder.TVBAR_CODE.setText(list.get(position).getBarCode());
        holder.TVPROD_ID.setText(list.get(position).getProd_Id());
        holder.TVLOCAL_MRP.setText(f.format(Double.parseDouble(list.get(position).getMRP())));
        holder.TV_SPRICE.setText(f.format(Double.parseDouble(list.get(position).getS_Price())));
        holder.TV_PPRICE.setText(f.format(Double.parseDouble(list.get(position).getP_Price())));
        holder.TVLOCAL_ACTVE.setText(list.get(position).getActive());
        if(list.get(position).getMargin()=="NULL")
        {
            holder.TV_MARGIN.setText("0");
        }else {
            holder.TV_MARGIN.setText(list.get(position).getMargin());
        }
        return convertView;
    }
    public ArrayList<ReportProductPharmaModel> getList() {
        return list;
    }

}

