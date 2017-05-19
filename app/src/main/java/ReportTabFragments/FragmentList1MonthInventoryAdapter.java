package ReportTabFragments;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Decimal;
import Pojo.InventoryReportModel;


public class FragmentList1MonthInventoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<InventoryReportModel> list = new ArrayList<InventoryReportModel>();
    private  Context context;
    DBhelper mydb;


    public FragmentList1MonthInventoryAdapter(ArrayList<InventoryReportModel> list, Context context) {
        super();
        this.list = list;
        this.context=context;
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

    public void addVendorToList(InventoryReportModel id) {
        list.add(id);
    }

    public void setList(ArrayList<InventoryReportModel> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVPROD_NM;
        TextView TVBATCH;
        TextView TVEXPIRY;
        TextView TVQUANTITY;
        TextView TVDAYS1MONTH;
        TextView TVUSERNM;
        TextView TVPRICE;
        TextView Total;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            mydb = new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.report_display_inventory_month1_row, null);
            holder = new ViewHolder();
            holder.TVPROD_NM = (TextView) convertView.findViewById(R.id.reportProd_Nm);
            holder.TVPROD_NM.setTextSize(Float.parseFloat(textsize));

            holder.TVUSERNM = (TextView) convertView.findViewById(R.id.reportUser_Nm);
            holder.TVUSERNM.setTextSize(Float.parseFloat(textsize));
            holder.TVBATCH = (TextView) convertView.findViewById(R.id.reportBatch_No);
            holder.TVBATCH.setTextSize(Float.parseFloat(textsize));
            holder.TVEXPIRY = (TextView) convertView.findViewById(R.id.reportExp_Date);
            holder.TVEXPIRY.setTextSize(Float.parseFloat(textsize));
            holder.TVQUANTITY = (TextView) convertView.findViewById(R.id.report_Qty);
            holder.TVQUANTITY.setTextSize(Float.parseFloat(textsize));
            holder.TVDAYS1MONTH = (TextView) convertView.findViewById(R.id.report_1month);
            holder.TVDAYS1MONTH.setTextSize(Float.parseFloat(textsize));
            holder.TVPRICE = (TextView) convertView.findViewById(R.id.reportP_Price);
            holder.TVPRICE.setTextSize(Float.parseFloat(textsize));
            holder.Total = (TextView) convertView.findViewById(R.id.reportTotal);
            holder.Total.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String StrExpdate = list.get(position).getExpiry();
        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
        String curr_date = s.toString();

        SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");
        //Dates to compare
        Date date1;
        Date date2;
        Long DaysLeft = null;
        try {
            //Setting dates
            date1 = dates.parse(curr_date);
            date2 = dates.parse(StrExpdate);
            //Comparing dates
            long difference = date2.getTime() - date1.getTime();
            DaysLeft = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);

            if(DaysLeft >= 0  && DaysLeft <= 31)
            {
                holder.TVUSERNM.setText(list.get(position).getUser_Nm());
                holder.TVPROD_NM.setText(list.get(position).getProd_Nm());
                holder.TVBATCH.setText(list.get(position).getBatch());
                holder.TVEXPIRY.setText(list.get(position).getExpiry());
                holder.TVQUANTITY.setText(list.get(position).getQuantity());

                holder.TVDAYS1MONTH.setText(DaysLeft.toString());
                DecimalFormat f=new DecimalFormat("##.00");
                Double Value = Double.valueOf(list.get(position).getPPrice());
                holder.TVPRICE.setText(f.format(Value));
                holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.TVPRICE.getText().toString()) * Double.parseDouble(holder.TVQUANTITY.getText().toString()))));

            }

        } catch (Exception exception) {
        }
        return convertView;
    }

    public ArrayList<InventoryReportModel> getList() {
        return list;
    }


    public float getGrnTotal()
    {
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {

            try {
                finalamount += Float.parseFloat(list.get(listIndex).getPPrice())*Float.parseFloat(list.get(listIndex).getQuantity());
            } catch (Exception e) {
                //ignore exeption for parse
            }

        }

        return finalamount;
    }
}
