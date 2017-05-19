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

import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.ReportDistributorModel;
import Pojo.ReportLocalProductPharmaModel;


public class FragmentListDistributorAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ReportDistributorModel> list = new ArrayList<ReportDistributorModel>();
    private Context context;
    DBhelper mydb;
    FragmentListDistributorAdapter acttivity;

    public FragmentListDistributorAdapter(ArrayList<ReportDistributorModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public long getItemId(int position) {
        return position;
    }

    public void setList(ArrayList<ReportDistributorModel> list) {
        this.list = list;
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

    public void addDistributorList(ReportDistributorModel id) {
        list.add(id);
    }

    public void addProductToList( ReportDistributorModel product ) {
        list.add(product);

    }
    public static class ViewHolder {
        /*2
        layout created display _report _row which text view are used
        */
        TextView TVUSER_NM;
        TextView TVDSBTR_NM;
        TextView TVACTVE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            mydb=new DBhelper(context);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //**** Inflate tabitem.xml file for each row ( Defined below ) ******
            convertView = layoutInflater.inflate(R.layout.display_distributor_row, null);
            holder = new ViewHolder();
            holder.TVUSER_NM=(TextView)convertView.findViewById(R.id.rowtvUser_Nm);
            holder.TVUSER_NM.setTextSize(Float.parseFloat(textsize));

            holder.TVDSBTR_NM=(TextView)convertView.findViewById(R.id.rowtvDstr_Nm);
            holder.TVDSBTR_NM.setTextSize(Float.parseFloat(textsize));

            holder.TVACTVE=(TextView)convertView.findViewById(R.id.rowtvDstr_Act);
            holder.TVACTVE.setTextSize(Float.parseFloat(textsize));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.TVUSER_NM.setText(list.get(position).getUser_Nm());
        holder.TVDSBTR_NM.setText(list.get(position).getDstr_Nm());
        holder.TVACTVE.setText(list.get(position).getActive());
        return convertView;
    }
    public ArrayList<ReportDistributorModel> getList() {
        return list;
    }
}


