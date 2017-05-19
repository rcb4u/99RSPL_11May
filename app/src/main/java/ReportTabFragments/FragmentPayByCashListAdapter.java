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
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Decimal;
import Pojo.PayByCashVendorPaymentModel;

public class FragmentPayByCashListAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    ArrayList<PayByCashVendorPaymentModel> list = new ArrayList<PayByCashVendorPaymentModel>();
    private  Context context;
    DBhelper mydb;

    public FragmentPayByCashListAdapter(ArrayList<PayByCashVendorPaymentModel> list, Context context) {
        super();
        this.list = list;
        this.context = context;
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

    public void addIdToList( PayByCashVendorPaymentModel id ) {
        Log.e("&&&&&&&&", "Adding Id " + id.toString() + " to list");
        list.add(id);
    }

    public void setList(ArrayList<PayByCashVendorPaymentModel> list) {
        this.list = list;
    }

    public static class ViewHolder{

        TextView TVVENDOR_NM;
        TextView TVAMNT_PAID;
        TextView TVAMNT_RECVD;
        TextView TVAMNT_DUE;
        TextView TVLAST_UPDATE;
        TextView TVREASON_PAY;
        TextView TVDAYS;
        TextView TVUSERNM;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {
            mydb = new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();


            layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /***** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView =layoutInflater.inflate(R.layout.display_report_pay_by_cash,null);
            holder = new ViewHolder();
            holder.TVUSERNM=(TextView)convertView.findViewById(R.id.reportUser_Nm);
            holder.TVUSERNM.setTextSize(Float.parseFloat(textsize));
            holder.TVVENDOR_NM=(TextView)convertView.findViewById(R.id.reportVend_Nm);
            holder.TVVENDOR_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_PAID=(TextView)convertView.findViewById(R.id.reportAmnt_Paid);
            holder.TVAMNT_PAID.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_RECVD=(TextView)convertView.findViewById(R.id.reportAmnt_Recvd);
            holder.TVAMNT_RECVD.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_DUE=(TextView)convertView.findViewById(R.id.reportAmnt_Due);
            holder.TVAMNT_DUE.setTextSize(Float.parseFloat(textsize));
            holder.TVLAST_UPDATE=(TextView)convertView.findViewById(R.id.reportLast_Update);
            holder.TVLAST_UPDATE.setTextSize(Float.parseFloat(textsize));
            holder.TVREASON_PAY=(TextView)convertView.findViewById(R.id.report_reason);
            holder.TVREASON_PAY.setTextSize(Float.parseFloat(textsize));
            holder.TVDAYS=(TextView)convertView.findViewById(R.id.report_days);
            holder.TVDAYS.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        String StrLastUpdate = list.get(position).getPayDate();
        Date date = new Date();
        final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
        String curr_date = s.toString();
        Log.e("current date :", curr_date);
        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        //Dates to compare
        Date date1;
        Date date2;
        Long DaysLeft = null;
        try {
            //Setting dates
            date1 = dates.parse(curr_date);
            date2 = dates.parse(StrLastUpdate);
            //Comparing dates
            long difference = date1.getTime() - date2.getTime();
            DaysLeft = TimeUnit.DAYS.convert(difference,TimeUnit.MILLISECONDS);
            if(DaysLeft >=0||DaysLeft<=0)
            {
                holder.TVDAYS.setText(DaysLeft.toString());
                holder.TVUSERNM.setText(list.get(position).getUserName());
                holder.TVVENDOR_NM.setText(list.get(position).getVendorNm());

                try {
                    String input = list.get(position).getPayDate();
                    String output = input.substring(0, 10);
                    holder.TVLAST_UPDATE.setText(output.toString());
                }catch(NullPointerException ex)
                {
                    ex.printStackTrace();
                }
                holder.TVREASON_PAY.setText(list.get(position).getReasonOfPay());
                DecimalFormat f=new DecimalFormat("##.00");
                Double Value = Double.valueOf(list.get(position).getAmountPaid());
                holder.TVAMNT_PAID.setText(f.format(Value));

                Double Value1 = Double.valueOf(list.get(position).getAmountRcvd());
                holder.TVAMNT_RECVD.setText(f.format(Value1));

                Double Value2 = Double.valueOf(list.get(position).getAmountDue());
                holder.TVAMNT_DUE.setText(f.format(Value2));

            }

            Log.e("Days left :", DaysLeft.toString());
        } catch (Exception exception) {
            Log.e("DIDN'T WORK", "exception " + exception);
        }
        return convertView;
    }

    public ArrayList<PayByCashVendorPaymentModel> getList() {
        return list;
    }
}