package Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.PurchaseActivity;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.PurchaseProductModel;

/**
 * Created by rspl-rahul on 11/3/16.
 */
public class PurchaseFullProductAdapter extends BaseAdapter
{

    PurchaseActivity activity;
    ArrayList<PurchaseProductModel>list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    PurchaseProductModel purchaseProductModel;
    boolean isUserEditingQuantityTextView = true;


    public PurchaseFullProductAdapter(PurchaseActivity activity, ArrayList<PurchaseProductModel> list, int layoutResourceId, PurchaseProductModel purchaseProductModel) {
        Log.e("AAAAAAAA&&&&&&&&&","in fullproductAdapterclass inside constructor");
        this.activity = activity;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.purchaseProductModel = purchaseProductModel;

    }

    public long getItemId(int position)
    {
        Log.e("**********", "" + position);
        return position;
    }

    public int getCount() {
        if (list.size()<0)
            return 1;
        Log.e("**get Count***", list.toString());
      return list.size();
    }
    public PurchaseProductModel getItem(int position) {

        return list.get(position);
    }



    public static class ViewHolder
    {

        public   TextView PurchaseName;
        public   EditText Purchaseprice;
        public   EditText EtQty;
        public   TextView Total;
        public   TextView UOM;
        public   ImageButton DeleteButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView==null)
        {

            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_full_purchase_product_row,parent,false);
            holder.Purchaseprice=(EditText)convertView.findViewById(R.id.PurchaseproductPrice);
            holder.PurchaseName=(TextView)convertView.findViewById(R.id.PurchaseproductName);
            holder.EtQty =(EditText)convertView.findViewById(R.id.editText);
            holder.Total=(TextView)convertView.findViewById(R.id.we);
            holder.UOM=(TextView)convertView.findViewById(R.id.UOM);
            holder.DeleteButton=(ImageButton)convertView.findViewById(R.id.deleteButton);

            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }

        holder.PurchaseName.setText(list.get(position).getProductName());
        if( (holder.Purchaseprice.getTag() != null)  && (holder.Purchaseprice.getTag() instanceof TextWatcher) ) {
            holder.Purchaseprice.removeTextChangedListener((TextWatcher) holder.Purchaseprice.getTag() );
        }
        holder.Purchaseprice.setText(String.format("%.2f", list.get(position).getProductPrice()));

        if( (holder.EtQty.getTag() != null)  && (holder.EtQty.getTag() instanceof TextWatcher) ) {
            holder.EtQty.removeTextChangedListener((TextWatcher) holder.EtQty.getTag() );
        }
        holder.EtQty.setText(String.format("%d", list.get(position).getProductQuantity()));
        holder.UOM.setText(list.get(position).getUom());
        holder.Total.setText(String.format("%.2f", list.get(position).getTotal()));

        holder.Total.setText(String.valueOf(Double.parseDouble(holder.Purchaseprice.getText().toString()) * Double.parseDouble(holder.EtQty.getText().toString())));
        TextWatcher quantityTextChangeWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    if (holder.EtQty.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        return;
                    }
                    holder.Total.setText(String.valueOf(Double.parseDouble(holder.Purchaseprice.getText().toString()) * Double.parseDouble(holder.EtQty.getText().toString())));

                  //  list.get(position).setTotal((Float.parseFloat(holder.Purchaseprice.getText().toString()))*Float.parseFloat(holder.EtQty.getText().toString()));
                    list.get(position).setProductQuantity(Integer.parseInt(holder.EtQty.getText().toString()));
                    list.get(position).setProductPrice(Float.parseFloat(holder.Purchaseprice.getText().toString()));

                    Log.e("&&&total&&&&", "$$$$$" + holder.Total.getText().toString());
                    activity.setSummaryRow();
                } catch (Exception e) {
                    Log.e("&&&&&&&&","Exception " + e + " while parsing double" );
                    e.printStackTrace();
                }
            }
        };

        holder.EtQty.addTextChangedListener(quantityTextChangeWatcher);
        holder.EtQty.setTag(quantityTextChangeWatcher);

        holder.Purchaseprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.Purchaseprice.setTag(quantityTextChangeWatcher);
        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });

        return convertView;

    }

    public void addProductToList( PurchaseProductModel product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        PurchaseProductModel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            list.add(product);
        } else {
            productAlreadyInList.setProductQuantity(productAlreadyInList.getProductQuantity() + product.getProductQuantity());
        }

    }

    public ArrayList<PurchaseProductModel> getList() {
        return list;
    }

    private PurchaseProductModel findProductInList(PurchaseProductModel product) {
        PurchaseProductModel returnProductVal = null;

        for( PurchaseProductModel productInList : list) {
            if( productInList.getProductId().trim().equals(product.getProductId().trim()) ) {
                returnProductVal = productInList;
            }
        }


        return returnProductVal;
    }
public void clearAllRows()
{
    list.clear();
    notifyDataSetChanged();

}

    public float getGrandTotal()
     {
           // ViewHolder holder=new ViewHolder();
         float finalamount=0.0f;
           for (int listIndex = 0; listIndex < list.size(); listIndex++) {
             try {
                 finalamount += (list.get(listIndex).getTotal());
             } catch (Exception e) {
                 //ignore exeption for parse
             }
             Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

         }
         Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
         return finalamount;
     }

    public void remove(int list){
        getItem(list);
        notifyDataSetChanged();
    }
}
