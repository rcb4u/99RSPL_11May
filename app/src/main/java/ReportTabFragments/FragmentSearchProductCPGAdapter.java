package ReportTabFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.ReportProductCpgModel;


public class FragmentSearchProductCPGAdapter extends ArrayAdapter<ReportProductCpgModel> {

    ArrayList<ReportProductCpgModel>list;
    private Context context;
    private int resource;


    public FragmentSearchProductCPGAdapter(Context context, int resource, ArrayList<ReportProductCpgModel> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    public void setList(ArrayList<ReportProductCpgModel> list) {
        this.list = list;
    }

    public int getCount() {
        if(list.size()<0)
            return 1;
        return list.size();
    }
    public ReportProductCpgModel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView productName;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder= new ViewHolder();
            LayoutInflater  layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_search_productid_row,parent,false);
            holder.productName=(TextView)convertView.findViewById(R.id.productId);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.productName.setText(list.get(position).getProd_Nm());
        return convertView;
    }
}
