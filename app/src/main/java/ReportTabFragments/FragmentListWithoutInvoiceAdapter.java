package ReportTabFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.SalesReturnReportModel;


public class FragmentListWithoutInvoiceAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<SalesReturnReportModel> list = new ArrayList<SalesReturnReportModel>();
    public Context context;
    FragmentWithoutInvoiceReport fragmentWithoutInvoiceReport;
    DBhelper mydb;

    public FragmentListWithoutInvoiceAdapter(ArrayList<SalesReturnReportModel> list, Context context, FragmentWithoutInvoiceReport fragmentWithoutInvoiceReport1) {
        this.list = list;
        this.context = context;
        this.fragmentWithoutInvoiceReport = fragmentWithoutInvoiceReport1;
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        if (list.size() < 0)
            return 1;
        return list.size();
    }

    public ArrayList<SalesReturnReportModel> getList() {
        return list;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }



    public void addIdToList( SalesReturnReportModel id ) {
        list.add(id);
    }

    public void setList(ArrayList<SalesReturnReportModel> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TV_TRANSID;
        TextView TV_TOTAL;
        TextView TV_USER;
        Button View_Details;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {


            mydb = new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView =layoutInflater.inflate(R.layout.display_salesreturn_withoutinvoice_row,null);
            holder = new ViewHolder();
            holder.TV_USER=(TextView)convertView.findViewById(R.id.rowtvUser);
            holder.TV_TRANSID=(TextView)convertView.findViewById(R.id.rowtvId);
            holder.TV_TOTAL=(TextView)convertView.findViewById(R.id.rowtvTotal) ;
            holder.View_Details=(Button)convertView.findViewById(R.id.View_Details);
////////////////////FragmentListWithoutInvoiceAdapter////////////////////

            holder.TV_USER.setTextSize(Float.parseFloat(textsize));
            holder.TV_TRANSID.setTextSize(Float.parseFloat(textsize));
            holder.TV_TOTAL.setTextSize(Float.parseFloat(textsize));
            holder.View_Details.setTextSize(Float.parseFloat(textsize));


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.TV_USER.setText(list.get(position).getUserNm());
            holder.TV_TRANSID.setText(list.get(position).getTransId());
            DecimalFormat f=new DecimalFormat("##.00");
            Double Value = Double.valueOf(list.get(position).getTotal());
            holder.TV_TOTAL.setText(f.format(Value));

            holder.View_Details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentWithoutInvoiceReport.GetWithoutInvoiceDetailPage(holder.TV_TRANSID.getText().toString());
                }
            });

        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return convertView;
    }

    public float getGrandTotal()
    {
        float finalamount=0.0f;
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


