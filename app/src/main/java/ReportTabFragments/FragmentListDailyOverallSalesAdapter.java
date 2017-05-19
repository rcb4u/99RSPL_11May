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

import Pojo.OverallSaleReportModel;
import Pojo.SaleReportModel;


public class FragmentListDailyOverallSalesAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<OverallSaleReportModel> list = new ArrayList<OverallSaleReportModel>();
    public Context context;
    FragmentDailyOverallSalesReport fragmentDailySalesReport;
    OverallSaleReportModel saleReportModel = new OverallSaleReportModel();

    public FragmentListDailyOverallSalesAdapter(ArrayList<OverallSaleReportModel> list, Context context, FragmentDailyOverallSalesReport fragmentDailySalesReport) {
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



    public void addIdToList( OverallSaleReportModel id ) {
        list.add(id);
    }

    public void setList(ArrayList<OverallSaleReportModel> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TV_TRIID;
        // TextView TV_TOTAL;
        TextView TV_USER;
        TextView TV_NAME;
        public TextView Exp;
        // public TextView Mrp;
        public TextView Qty;
        TextView TVBATCH;
        TextView TVPROFIT;
        TextView TVSPRICE;
        TextView TVPPRICE;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_overallsale_row, null);
            holder = new ViewHolder();
            holder.TV_USER=(TextView)convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TV_TRIID=(TextView)convertView.findViewById(R.id.rowtvTri_Id);
            //  holder.TV_TOTAL=(TextView)convertView.findViewById(R.id.rowtvGrn_Total);
            holder.TV_NAME=(TextView)convertView.findViewById(R.id.rowtvProdNm);
            holder.TVBATCH=(TextView)convertView.findViewById(R.id.rowtvBatchNo);
            holder.Exp =(TextView)convertView.findViewById(R.id.rowtvExpDate);
            //   holder.Mrp=(TextView)convertView.findViewById(R.id.rowtvMrp);
            holder.Qty=(TextView)convertView.findViewById(R.id.rowtvQty);
            holder.TVPROFIT=(TextView)convertView.findViewById(R.id.rowtvProfit);
            holder.TVSPRICE=(TextView)convertView.findViewById(R.id.rowtvSPrice);
            holder.TVPPRICE=(TextView)convertView.findViewById(R.id.rowtvPPrice);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TV_USER.setText(list.get(position).getUserNm());
        holder.TV_TRIID.setText(list.get(position).getTransId());
       /* DecimalFormat f=new DecimalFormat("##.00");
       String grndTotl=list.get(position).getGrnTotl();
        Float GrndTtl=Float.parseFloat(grndTotl);
        holder.TV_TOTAL.setText(f.format(GrndTtl));*/
        DecimalFormat f=new DecimalFormat("##.00");
        float profittotal=list.get(position).getPROFIT();
        holder.TVPROFIT.setText(f.format(profittotal));
        holder.TVBATCH.setText(list.get(position).getBatch());
        holder.Exp.setText(list.get(position).getExp());
        holder.TV_NAME.setText(list.get(position).getProdNm());
        holder.Qty.setText(list.get(position).getQty());

        holder.TVSPRICE.setText(list.get(position).getSPrice().toString());
        holder.TVPPRICE.setText(list.get(position).getPPrice().toString());

        if (Double.parseDouble(holder.TVSPRICE.getText().toString())>0 || Double.parseDouble(holder.TVPPRICE.getText().toString())>0) {
            holder.TVPROFIT.setText(String.valueOf(f.format(Double.parseDouble(holder.TVSPRICE.getText().toString()) - Double.parseDouble(holder.TVPPRICE.getText().toString()))));
            list.get(position).setPROFIT(Float.parseFloat(String.valueOf(Double.parseDouble((holder.TVPROFIT.getText().toString())))));
            String profit = String.valueOf(list.get(position).getPROFIT());

            Log.e("Rc..AfterTextChange", "$$$$$" + holder.TVPROFIT.getText().toString());
            Log.d("$$$$",profit);
        }

        return convertView;
    }

    public ArrayList<OverallSaleReportModel> getList() {
        return list;
    }

    public int  addProductToList(OverallSaleReportModel product) {

        OverallSaleReportModel productAlreadyInList = findProductInList(product);
        if (productAlreadyInList == null) {
            list.add(0, product);
            return 0;
        } else {
            return list.indexOf(productAlreadyInList);
        }

    }

    private OverallSaleReportModel findProductInList(OverallSaleReportModel product) {
        OverallSaleReportModel returnProductVal = null;

        for (OverallSaleReportModel productInList : list) {
            if (productInList.getProdNm().trim().equals(product.getProdNm().trim())) {
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
                finalamount+= Float.parseFloat(list.get(listIndex).getTotalAfterDscnt() + list.get(listIndex).getCredit());

            } catch (Exception e) {
                //ignore exeption for parse
            }

        }

        return finalamount;
    }

    public float getProfitTotal()
    {
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {

            try {
                finalamount+= list.get(listIndex).getSPrice()- list.get(listIndex).getPPrice();

                Log.d("****", String.valueOf(list.get(listIndex).getPROFIT()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Log.d("totalllll", String.valueOf(finalamount));

        return finalamount;
    }

}
