package ReportTabFragments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.SaleReportModel;


public class FragmentListMonthlySalesAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<SaleReportModel> list = new ArrayList<SaleReportModel>();
    private Context context;
    FragmentMonthlySalesReport fragmentMonthlySalesReport;
    DBhelper mydb;

    public FragmentListMonthlySalesAdapter(ArrayList<SaleReportModel> list, Context context, FragmentMonthlySalesReport fragmentMonthlySalesReport) {
        this.list = list;
        this.context = context;
        this.fragmentMonthlySalesReport=fragmentMonthlySalesReport;
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



    public void addIdToList( SaleReportModel id ) {
        list.add(id);
    }

    public void setList(ArrayList<SaleReportModel> list) {
        this.list = list;
    }


    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TV_TRIID;
        TextView TV_TOTAL;
        TextView TVUSER;
        TextView TVDATE;
        TextView TVCARDNO;
        Button View_Details;
        ImageButton mPrintBtn;


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
            convertView = layoutInflater.inflate(R.layout.display_monthlysales_grandtotal_row, null);
            holder = new ViewHolder();
            holder.TVUSER=(TextView)convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TV_TRIID=(TextView)convertView.findViewById(R.id.rowtvTri_Id);
            holder.TV_TOTAL=(TextView)convertView.findViewById(R.id.rowtvTotal);
            holder.TVCARDNO=(TextView)convertView.findViewById(R.id.rowtvCard_No);
            holder.TVDATE=(TextView)convertView.findViewById(R.id.rowtvSaleDate);
            holder.View_Details=(Button)convertView.findViewById(R.id.View_Details);
            holder.mPrintBtn=(ImageButton)convertView.findViewById(R.id.printbtn);
            holder.TVUSER.setTextSize(Float.parseFloat(textsize));
            holder.TV_TRIID.setTextSize(Float.parseFloat(textsize));
            holder.TV_TOTAL.setTextSize(Float.parseFloat(textsize));
            holder.TVCARDNO.setTextSize(Float.parseFloat(textsize));
            holder.TVDATE.setTextSize(Float.parseFloat(textsize));
            holder.View_Details.setTextSize(Float.parseFloat(textsize));


            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVUSER.setText(list.get(position).getUserNm());
        holder.TV_TRIID.setText(list.get(position).getTransId());
        holder.TVDATE.setText(list.get(position).getSaleDate());
        holder.TVCARDNO.setText(list.get(position).getCardNo());
        DecimalFormat f=new DecimalFormat("##.00");
        String grndTotl=list.get(position).getGrnTotl();
        Float GrndTtl=Float.parseFloat(grndTotl);
        holder.TV_TOTAL.setText(f.format(GrndTtl));

        holder.View_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMonthlySalesReport.GetWeeklyDetailPage(holder.TV_TRIID.getText().toString());

            }
        });

        holder.mPrintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentMonthlySalesReport.printBillDialog(holder.TV_TRIID.getText().toString());
            }
        });

        return convertView;
    }

    public ArrayList<SaleReportModel> getList()

    {
        return list;
    }

    public float getGrnTotal()
    {
        float finalamount=0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {

            try {
                finalamount += Float.parseFloat(list.get(listIndex).getGrnTotl());
            } catch (Exception e) {
                //ignore exeption for parse
            }

        }

        return finalamount;
    }
}


