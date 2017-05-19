package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.RSPL.POS.MainAdvertisementReportActivity;
import com.RSPL.POS.R;
import Pojo.StoreMainModel;

import java.util.ArrayList;


/**
 * Created by Rahul on 2/21/2016.
 */
public class StoreMainAdapter extends BaseAdapter {

    private static LayoutInflater layoutInflater;
   StoreMainModel storeMainModel;
    ArrayList<StoreMainModel> list =new ArrayList<StoreMainModel>();
    MainAdvertisementReportActivity activity;
    Context context;


    public StoreMainAdapter(MainAdvertisementReportActivity activity, ArrayList <StoreMainModel>arrayList) {
        //super(activity, arrayList);
         this.activity=activity;
        this.list=arrayList;
    }
    public long getItemId(int position)
    {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<=0)
            return 1;
        Log.e("**get Count***",list.toString());
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public ArrayList<StoreMainModel> getList() {
        return list;
    }

    public static class ViewHolder{
        /*
        layout created display _report _row which text view are used
        */
        TextView TVStoreMainAd_Main_Id;
        TextView TVStoreMainUser;
        TextView TVStoreMainAd_Cst_Slb1;
        TextView TVStoreMainAd_Cst_Slb2;
        TextView TVStoreMainAd_Cst_Slb3;
        TextView TVStoreMainAd_Desc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {

            layoutInflater=(LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /***** Inflate tabitem.xml file for each row ( Defined below ) *******/
            convertView =layoutInflater.inflate(R.layout.display_store_main_row,null);
            holder = new ViewHolder();
            holder.TVStoreMainAd_Main_Id=(TextView)convertView.findViewById(R.id.rowStoreMaintvAd_Main_Id);
            holder.TVStoreMainUser=(TextView)convertView.findViewById(R.id.rowStoreMainuser_Nm);
            holder.TVStoreMainAd_Cst_Slb1=(TextView)convertView.findViewById(R.id.rowStoreMaintvAd_Cst_Slb1);
            holder.TVStoreMainAd_Cst_Slb2=(TextView)convertView.findViewById(R.id.rowStoreMaintvAd_Cst_Slb2);
            holder.TVStoreMainAd_Cst_Slb3=(TextView)convertView.findViewById(R.id.rowStoreMaintvAd_Cst_Slb3);
            holder.TVStoreMainAd_Desc=(TextView)convertView.findViewById(R.id.rowStoreMainAd_Desc);

            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            holder.TVStoreMainUser.setText(list.get(position).getUserNm());
        holder.TVStoreMainAd_Main_Id.setText(list.get(position).getAD_MAIN_ID());
        holder.TVStoreMainAd_Desc.setText(list.get(position).getAD_DESC());
        holder.TVStoreMainAd_Cst_Slb1.setText(list.get(position).getAD_CST_SLB1());
        holder.TVStoreMainAd_Cst_Slb2.setText(list.get(position).getAD_CST_SLB2());
        holder.TVStoreMainAd_Cst_Slb3.setText(list.get(position).getAD_CST_SLB3());
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
        return convertView;
    }
}