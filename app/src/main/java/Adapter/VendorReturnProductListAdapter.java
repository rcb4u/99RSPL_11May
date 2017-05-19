package Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesreturn;
import com.RSPL.POS.ActivityStore;
import com.RSPL.POS.CustomAlphabatKeyboard;
import com.RSPL.POS.CustomFractionalKeyboard;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.DecimalDigitsInputFilter;
import com.RSPL.POS.R;
import com.RSPL.POS.VendorReturnActivity;


import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.VendorReturnModel;

/**
 * Created by rspl-rahul on 3/5/16.
 */
public class VendorReturnProductListAdapter extends BaseAdapter {
    VendorReturnActivity activity;
    private final int layoutResourceId;
    ArrayList<VendorReturnModel> list;
    LayoutInflater layoutInflater;
    VendorReturnModel vendorReturnModel;
    CustomFractionalKeyboard customFractionalKeyboard;
    CustomAlphabatKeyboard customAlphabatKeyboard;
    DBhelper helper;



    public VendorReturnProductListAdapter(VendorReturnActivity activity, int textViewResourceId, ArrayList<VendorReturnModel> objects,VendorReturnModel vendorReturnModel) {
        //super(activity, textViewResourceId, objects);
        this.activity=activity;
        this.layoutResourceId=textViewResourceId;
        this.list=objects;
        this.vendorReturnModel=vendorReturnModel;
    }



    public void setList(ArrayList<VendorReturnModel> list) {
        this.list = list;
    }

    public int getCount() {
        if(list.size()<0)

            return 1;
        Log.e("**get Count***", list.toString());
        return list.size();
    }
    public VendorReturnModel getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        Log.e("**********", "" + position);
        return position;
    }

    public static class ViewHolder
    {
        TextView vendorName;
        TextView ProductName;
        TextView ExpDate;
        EditText productQuantity;
        TextView MRP;
        TextView Stock;
        TextView PurchasePrice;
        TextView BatchNumber;
        TextView Uom;
        TextView Total;
        TextView FreeQty;
        public ImageButton DeleteButton;

    }
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        DecimalFormat f = new DecimalFormat("##.00");
        helper = new DBhelper(activity);
        Decimal valuetextsize = helper.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();


        if(convertView==null){
            holder= new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.display_vendor_return,parent,false);
            holder.ProductName=(TextView)convertView.findViewById(R.id.prodnameVendorreturn);
            holder.ProductName.setTextSize(Float.parseFloat(textsize));

            holder.ExpDate=(TextView)convertView.findViewById(R.id.expdateVendorReturn);
            holder.ExpDate.setTextSize(Float.parseFloat(textsize));
            holder.productQuantity=(EditText)convertView.findViewById(R.id.qtyVendorReturn);
            holder.productQuantity.setTextSize(Float.parseFloat(textsize));
            holder.BatchNumber=(TextView)convertView.findViewById(R.id.BatchNumberVendorReturn);
            holder.BatchNumber.setTextSize(Float.parseFloat(textsize));
            holder.PurchasePrice=(TextView)convertView.findViewById(R.id.PPriceVendorReturn);
            holder.PurchasePrice.setTextSize(Float.parseFloat(textsize));
            holder.Stock=(TextView)convertView.findViewById(R.id.StockVendorReturn);
            holder.Stock.setTextSize(Float.parseFloat(textsize));
          holder.Uom=(TextView)convertView.findViewById(R.id.uomVendorReturn);
            holder.Uom.setTextSize(Float.parseFloat(textsize));
            holder.Total=(TextView)convertView.findViewById(R.id.totalVendorReturn);
            holder.Total.setTextSize(Float.parseFloat(textsize));
            holder.Total.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});


            holder.DeleteButton=(ImageButton)convertView.findViewById(R.id.deleteButtonVendorReturn);



           /* holder.productQuantity.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    customAlphabatKeyboard= new CustomAlphabatKeyboard(activity, R.id.keyboard_vendorreturn_qty, R.xml.alphanumerickeyboard);

                    customAlphabatKeyboard.registerEditText(R.id.qtyVendorReturn);

                    return false;
                }
            });*/
           // holder.FreeQty=(TextView)convertView.findViewById(R.id.FreeVendorReturn);
            convertView.setTag(holder);


        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        DecimalFormat f2 = new DecimalFormat("##");
        //   holder.vendorName.setText(list.get(position).getVendorName());
        holder.ProductName.setText(list.get(position).getProductName());
        holder.ExpDate.setText(list.get(position).getExpdate());
        holder.BatchNumber.setText(list.get(position).getBatchno());
      //  holder.FreeQty.setText(list.get(position).getFreeQty());
        // holder.SellingPrice.setText(String.format("%.2f",list.get(position).getSprice()));
        if( (holder.PurchasePrice.getTag() != null)  && (holder.PurchasePrice.getTag() instanceof TextWatcher) ) {
            holder.PurchasePrice.removeTextChangedListener((TextWatcher) holder.PurchasePrice.getTag() );
        }

        holder.PurchasePrice.setText(String.format("%.2f", list.get(position).getPprice()));
        holder.Uom.setText(list.get(position).getUOM());
        holder.Stock.setText(f2.format(list.get(position).getStock()));


        if( (holder.productQuantity.getTag() != null)  && (holder.productQuantity.getTag() instanceof TextWatcher) ) {
            holder.productQuantity.removeTextChangedListener((TextWatcher) holder.productQuantity.getTag() );
        }

        holder.productQuantity.setText(f2.format(list.get(position).getProductQuantity()));
        holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.PurchasePrice.getText().toString()) * Double.parseDouble(holder.productQuantity.getText().toString()))));
        TextWatcher quantityTextChangeWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                DecimalFormat f = new DecimalFormat("##.00");

                try {
                    if (holder.productQuantity.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.productQuantity.setError("Enter a valid quantity");
                        list.get(position).setProductQuantity(0);
                        return;
                    }

                    if (holder.productQuantity.getText().toString().trim().equals(".0") || holder.productQuantity.getText().toString().trim().equals("0.00")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                       return;
                    }else if (Double.parseDouble(holder.productQuantity.getText().toString().trim()) > Double.parseDouble(holder.Stock.getText().toString()))
                    {
                        holder.productQuantity.setText("");
                        return;
                    }

                    holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.PurchasePrice.getText().toString()) * Double.parseDouble(holder.productQuantity.getText().toString()))));
                    list.get(position).setProductQuantity(Integer.parseInt(holder.productQuantity.getText().toString()));
                    list.get(position).setPprice(Float.parseFloat(holder.PurchasePrice.getText().toString()));

                    Log.e("&&&total&&&&", "$$$$$" + holder.Total.getText().toString());
                    activity.setSummaryRow();
                } catch (Exception e) {
                    Log.e("&&&&&&&&","Exception " + e + " while parsing double" );
                    e.printStackTrace();
                }
            }
        };

        holder.productQuantity.addTextChangedListener(quantityTextChangeWatcher);
        holder.productQuantity.setTag(quantityTextChangeWatcher);

        holder.PurchasePrice.addTextChangedListener(quantityTextChangeWatcher);
        holder.PurchasePrice.setTag(quantityTextChangeWatcher);

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                activity.setSummaryRow();
                notifyDataSetChanged();
            }
        });


        holder.ProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.ExpDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.BatchNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.PurchasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.Uom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });


        holder.Total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });


        return convertView;
    }



    public int  addProductToList( VendorReturnModel product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        VendorReturnModel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            list.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setProductQuantity((int) (productAlreadyInList.getProductQuantity() + product.getProductQuantity()));
            return list.indexOf(productAlreadyInList);
        }

    }

    public ArrayList<VendorReturnModel> getList() {
        return list;
    }

    private VendorReturnModel findProductInList(VendorReturnModel product) {
        VendorReturnModel returnProductVal = null;

        for( VendorReturnModel productInList : list) {
            if( productInList.getProductId().trim().equals(product.getProductId().trim()) && productInList.getBatchno().trim().equals(product.getBatchno().trim())) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }

    public void clearAllRows() {
        list.clear();
        notifyDataSetChanged();
    }

    public float getGrandTotal()
    {
        //  ViewHolder holder=new ViewHolder();
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


    public static void hideSoftKeyboard(VendorReturnActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(VendorReturnActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
