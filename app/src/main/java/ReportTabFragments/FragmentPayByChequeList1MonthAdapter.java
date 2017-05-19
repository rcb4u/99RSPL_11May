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
import Pojo.PayByChequeVendorPaymentModel;

public class FragmentPayByChequeList1MonthAdapter extends BaseAdapter{

    LayoutInflater layoutInflater;
    ArrayList<PayByChequeVendorPaymentModel> list = new ArrayList<PayByChequeVendorPaymentModel>();
    private  Context context;
    FragmentPayByCheque1MonthReport fragmentPayByCheque1MonthReport;
    DBhelper mydb;

    public FragmentPayByChequeList1MonthAdapter(ArrayList<PayByChequeVendorPaymentModel> list, Context context,FragmentPayByCheque1MonthReport fragmentPayByCheque1MonthReport1) {
        super();
        this.list = list;
        this.context = context;
        this.fragmentPayByCheque1MonthReport=fragmentPayByCheque1MonthReport1;
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

    public void addIdToList( PayByChequeVendorPaymentModel id ) {
        Log.e("&&&&&&&&", "Adding Id " + id.toString() + " to list");
        list.add(id);
    }

    public void setList(ArrayList<PayByChequeVendorPaymentModel> list) {
        this.list = list;
    }

    public static class ViewHolder{

        TextView TVVENDOR_NM;
        TextView TVAMNT_PAID;
        TextView TVAMNT_RECVD;
        TextView TVAMNT_DUE;
        TextView TVBANK_NAME;
        TextView TVCHEQUE_NO;
        TextView TVREASON_PAY;
        TextView TVLAST_UPDATE;
        TextView TV_DAYS;
        TextView TV_USER;
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
            convertView =layoutInflater.inflate(R.layout.display_report_pay_by_cheque,null);
            holder = new ViewHolder();
            holder.TV_USER=(TextView)convertView.findViewById(R.id.reportUser_Nm);
            holder.TVVENDOR_NM=(TextView)convertView.findViewById(R.id.reportVend_Nm);
            holder.TVAMNT_PAID=(TextView)convertView.findViewById(R.id.reportAmnt_Paid);
            holder.TVAMNT_RECVD=(TextView)convertView.findViewById(R.id.reportAmnt_Recvd);
            holder.TVAMNT_DUE=(TextView)convertView.findViewById(R.id.reportAmnt_Due);
            holder.TVBANK_NAME=(TextView)convertView.findViewById(R.id.reportBank_Nm);
            holder.TVCHEQUE_NO=(TextView)convertView.findViewById(R.id.reportCheque_No);
            holder.TVLAST_UPDATE=(TextView)convertView.findViewById(R.id.reportLast_Update);
            holder.TVREASON_PAY=(TextView)convertView.findViewById(R.id.report_reason);
            holder.TV_DAYS=(TextView)convertView.findViewById(R.id.report_days);


            holder.TV_USER.setTextSize(Float.parseFloat(textsize));
            holder.TVVENDOR_NM.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_PAID.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_RECVD.setTextSize(Float.parseFloat(textsize));
            holder.TVAMNT_DUE.setTextSize(Float.parseFloat(textsize));
            holder.TVBANK_NAME.setTextSize(Float.parseFloat(textsize));
            holder.TVCHEQUE_NO.setTextSize(Float.parseFloat(textsize));
            holder.TVLAST_UPDATE.setTextSize(Float.parseFloat(textsize));
            holder.TVREASON_PAY.setTextSize(Float.parseFloat(textsize));
            holder.TV_DAYS.setTextSize(Float.parseFloat(textsize));



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
            if (DaysLeft>=0 && DaysLeft <= 31)
            {
                holder.TV_USER.setText(list.get(position).getUserNm());
                holder.TVVENDOR_NM.setText(list.get(position).getVendorNm());
                holder.TVLAST_UPDATE.setText(list.get(position).getPayDate());
                holder.TVREASON_PAY.setText(list.get(position).getReasonOfPay());
                holder.TVBANK_NAME.setText(list.get(position).getBankName());
                holder.TVCHEQUE_NO.setText(list.get(position).getChequeNo());
                holder.TV_DAYS.setText(DaysLeft.toString());
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

    public ArrayList<PayByChequeVendorPaymentModel> getList() {
        return list;
    }

}
