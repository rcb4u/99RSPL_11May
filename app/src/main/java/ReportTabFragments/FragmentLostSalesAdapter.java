package ReportTabFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.FragmentSalesLostReportPOJO;
import Pojo.OverallSaleReportModel;


public class FragmentLostSalesAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<FragmentSalesLostReportPOJO> list = new ArrayList<FragmentSalesLostReportPOJO>();

    public Context context;

    FragmentLostSalesReport fragmentDailySalesReport;
    FragmentSalesLostReportPOJO saleReportModel = new FragmentSalesLostReportPOJO();

    public FragmentLostSalesAdapter(ArrayList<FragmentSalesLostReportPOJO> list, Context context, FragmentLostSalesReport FragmentLostSalesAdapter) {
        this.list = list;
        this.context = context;
        this.fragmentDailySalesReport=fragmentDailySalesReport;
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



    public void addIdToList( FragmentSalesLostReportPOJO id ) {
        list.add(id);
    }

    public void setList(ArrayList<FragmentSalesLostReportPOJO> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */

        TextView TV_NAME;
        public TextView Qty;
        TextView TVSALEDATE;
        TextView TVSPRICE;
        TextView TVTOTAL;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_lost_sale_row, null);
            holder = new ViewHolder();
            holder.TV_NAME=(TextView)convertView.findViewById(R.id.rowtvProdNm);
            holder.Qty=(TextView)convertView.findViewById(R.id.rowtvQty);
            holder.TVTOTAL=(TextView)convertView.findViewById(R.id.total);
            holder.TVSPRICE=(TextView)convertView.findViewById(R.id.rowtvSPrice);
            holder.TVSALEDATE=(TextView)convertView.findViewById(R.id.saledate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DecimalFormat f=new DecimalFormat("##.00");

        holder.TV_NAME.setText(list.get(position).getProduct_name());
        holder.Qty.setText(list.get(position).getQuntity());
        holder.TVSALEDATE.setText(list.get(position).getSaledate());

        String sprice=list.get(position).getSprice();
        Float SPrice=Float.parseFloat(sprice);
        holder.TVSPRICE.setText(f.format(SPrice));

        String grndTotl=list.get(position).getTotal();
        Float GrndTtl=Float.parseFloat(grndTotl);
        holder.TVTOTAL.setText(f.format(GrndTtl));





        return convertView;
    }

    public ArrayList<FragmentSalesLostReportPOJO> getList() {
        return list;
    }

    public int  addProductToList(FragmentSalesLostReportPOJO product) {

        FragmentSalesLostReportPOJO productAlreadyInList = findProductInList(product);
        if (productAlreadyInList == null) {
            list.add(0, product);
            return 0;
        } else {
            return list.indexOf(productAlreadyInList);
        }

    }

    private FragmentSalesLostReportPOJO findProductInList(FragmentSalesLostReportPOJO product) {
        FragmentSalesLostReportPOJO returnProductVal = null;

        for (FragmentSalesLostReportPOJO productInList : list) {
            if (productInList.getProduct_name().trim().equals(product.getProduct_name().trim())) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public float getGrnTotal()
    {
        float finalamount=0.0f;
        final ViewHolder holder;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {

            try {
                finalamount += Float.parseFloat(list.get(listIndex).getTotal());

            } catch (Exception e) {
                //ignore exeption for parse
            }

        }

        return finalamount;
    }



}
