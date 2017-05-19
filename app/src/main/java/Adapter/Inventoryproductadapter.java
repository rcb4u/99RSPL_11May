package Adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.RSPL.POS.R;
        import com.RSPL.POS.activity_inventory;

        import java.util.ArrayList;

        import Pojo.Inventoryproductmodel;

/**
 * Created by Abhigyan on 26-03-2016.
 */
public class Inventoryproductadapter  extends ArrayAdapter<Inventoryproductmodel> {
    activity_inventory activity;
    private final int layoutResourceId;
    ArrayList<Inventoryproductmodel> list;
    LayoutInflater layoutInflater;
    Inventoryproductmodel inventoryproductmodel;

    public Inventoryproductadapter(activity_inventory activity, int textViewResourceId, ArrayList<Inventoryproductmodel> objects) {
        super(activity, textViewResourceId, objects);
        this.layoutResourceId=textViewResourceId;
        this.activity=activity;
        this.list=objects;
    }


    public void setList(ArrayList<Inventoryproductmodel> list) {
        this.list = list;
    }

    public int getCount() {
        if(list.size()<0)
            return 1;
        return list.size();
    }
    public Inventoryproductmodel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder
    {
        TextView PurchaseId;
        TextView PurchaseName;

        /*  TextView purchaseBatchno;
          TextView purchaseExpdate;
        */  TextView purchaseMrp;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null)
        {
            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_inventory_product,parent,false);
            holder.PurchaseId=(TextView)convertView.findViewById(R.id.Purchaseproductid);
            holder.PurchaseName=(TextView)convertView.findViewById(R.id.PurchaseproductName);
            holder.purchaseMrp=(TextView)convertView.findViewById(R.id.PurchaseproductMRP);
          /*  holder.purchaseBatchno=(TextView)convertView.findViewById(R.id.Purchaseproductbatch);
            holder.purchaseExpdate=(TextView)convertView.findViewById(R.id.Purchaseproductexpdate);
         // holder.Purchasemrp=(TextView)convertView.findViewById(R.id.purchaseMRP);

*/          convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.PurchaseId.setText(list.get(position).getProductId().replace("X-",""));
         /*  holder.purchaseBatchno.setText(list.get(position).getBatchno());
             holder.purchaseExpdate.setText(list.get(position).getExpdate());
*/
        //  holder.purchaseMrp.setText(list.get(position).getMrp());
        holder.PurchaseName.setText(list.get(position).getProductName());
        holder.purchaseMrp.setText(String.format("%.2f", list.get(position).getMrp()));

        return convertView;
    }
}


