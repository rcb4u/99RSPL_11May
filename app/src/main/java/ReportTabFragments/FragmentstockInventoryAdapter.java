package ReportTabFragments;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Decimal;
import Pojo.InventoryReportModel;
import Pojo.StockInventoryReportModel;

public class FragmentstockInventoryAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;
    ArrayList<StockInventoryReportModel> list = new ArrayList<StockInventoryReportModel>();
    private Context context;
    FragmentStockReport fragmentStockReport;
    DBhelper mydb;

    public FragmentstockInventoryAdapter  (ArrayList<StockInventoryReportModel> list, Context context,FragmentStockReport fragmentStockReport1) {
        super();
        this.list = list;
        this.context = context;
        this.fragmentStockReport=fragmentStockReport1;
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

    public void addVendorToList(StockInventoryReportModel id) {
        Log.e("&&&&&&&&", "Adding product " + id.toString() + " to product list");
        list.add(id);
    }

    public void setList(ArrayList<StockInventoryReportModel> list) {
        this.list = list;
    }

    public static class ViewHolder {
        TextView TVINVOICE;
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
            convertView = layoutInflater.inflate(R.layout.report_display_inventory_stock_row, null);
            holder = new ViewHolder();
            holder.TVINVOICE = (TextView) convertView.findViewById(R.id.report_Invoice);
            holder.TVINVOICE.setTextSize(Float.parseFloat(textsize));
            holder.View_Details=(Button)convertView.findViewById(R.id.View_Details);
            holder.View_Details.setTextSize(Float.parseFloat(textsize));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVINVOICE.setText(list.get(position).getInvoiceNo());

        holder.View_Details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentStockReport.GetDailyDetailPage(holder.TVINVOICE.getText().toString());
            }
        });

        return convertView;
    }

    public ArrayList<StockInventoryReportModel> getList() {
        return list;
    }
}