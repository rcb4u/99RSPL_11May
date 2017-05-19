package Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.AdTickerReportActivity;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;
import com.RSPL.POS.ShowStockAdjustmentListActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.AdTickerReportModel;
import Pojo.ExpiryProductStockModel;
import Pojo.InventoryStockadjustmentmodel;


public class ShowStockAdjustmentAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ExpiryProductStockModel> list = new ArrayList<ExpiryProductStockModel>();
    ShowStockAdjustmentListActivity activity;

    public ShowStockAdjustmentAdapter(ShowStockAdjustmentListActivity showStockAdjustmentListActivity, ArrayList<ExpiryProductStockModel> list, int simple_expandable_list_item_1) {
        this.list = list;
        this.activity = showStockAdjustmentListActivity;
    }

    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    public void addVendorToList(ExpiryProductStockModel vendor) {
        Log.e("&&&&&&&&", "Adding product " + vendor.toString() + " to product list");
        list.add(vendor);
    }

    public void setList(ArrayList<ExpiryProductStockModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView ProductNm;
        TextView Qty;
        TextView PPrice;
        TextView ExpDate;
        TextView BatchNo;
        TextView DaysLeft;
        TextView Total;
        public ImageButton DeleteButton;
        public Button stockdelete;
    }

    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_stockadjustment_report_row, null);
            holder = new ViewHolder();
            holder.ProductNm = (TextView) convertView.findViewById(R.id.rowtvProd_Nm);
            holder.Qty = (TextView) convertView.findViewById(R.id.rowtvQty);
            holder.PPrice = (TextView) convertView.findViewById(R.id.rowtvMrp);
            holder.ExpDate = (TextView) convertView.findViewById(R.id.rowtvExp_Date);
            holder.BatchNo = (TextView) convertView.findViewById(R.id.rowtvBatch_No);
            holder.DaysLeft = (TextView) convertView.findViewById(R.id.rowtvDays_Left);
            holder.Total = (TextView) convertView.findViewById(R.id.reportTotal);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String StrExpdate = list.get(position).getExpdate();
        Log.e("exp date :", StrExpdate);
        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
        String curr_date = s.toString();
        Log.e("current date :", curr_date);
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
            long difference = (date2.getTime() - date1.getTime());
            DaysLeft = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);

            if (DaysLeft<0)
            {
                holder.DaysLeft.setText(DaysLeft.toString());
                holder.ProductNm.setText(list.get(position).getProductName());

                DecimalFormat f=new DecimalFormat("##.00");
                Double Value = Double.valueOf(list.get(position).getStockQty());
                holder.Qty.setText(String.valueOf(Value));

                Float Value1 = Float.valueOf(list.get(position).getPprice());
                holder.PPrice.setText(f.format(Value1));
                holder.ExpDate.setText(list.get(position).getExpdate());
                holder.BatchNo.setText(list.get(position).getBatchno());
                holder.Total.setText(String.valueOf(f.format(Float.parseFloat(holder.PPrice.getText().toString()) * Double.parseDouble(holder.Qty.getText().toString()))));

            }

        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
        return convertView;
    }

    public ArrayList<ExpiryProductStockModel> getList() {
        return list;
    }

    public float getGrnTotal()
    {
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {

            try {
                finalamount += (list.get(listIndex).getPprice())*(list.get(listIndex).getStockQty());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);

        return finalamount;
    }
}