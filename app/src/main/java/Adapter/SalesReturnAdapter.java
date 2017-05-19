package Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesreturn;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.R;
import com.RSPL.POS.RemoteVideoPresentation;
//import com.mycompany.apps.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import Pojo.Decimal;
import Pojo.Salesreturndetail;

/**
 * Created by rspl-nishant on 1/4/16.
 */
public class SalesReturnAdapter extends ArrayAdapter<Salesreturndetail> {
    private final int layoutResourceId;
    private Context mcontext;
    ActivitySalesreturn activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;
    DBhelper mydb;

    public SalesReturnAdapter(ActivitySalesreturn activity, int layoutResourceId, ArrayList<Salesreturndetail> msalesreturnlist) {
        super(activity, layoutResourceId,  msalesreturnlist);
        this.activity = activity;
        this.salesreturnlist=  msalesreturnlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void setSalesReturnList(ArrayList<Salesreturndetail> salesreturnlist) {
        this.salesreturnlist = salesreturnlist;
    }

    private ArrayList<Salesreturndetail> salesreturnlist;
    public int getCount() {
        if (salesreturnlist.size() < 0)
            return 1;
        return salesreturnlist.size();
    }


    public Salesreturndetail getItem(int position) {
        return salesreturnlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public void clearAllRows() {
        salesreturnlist.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder {

        public TextView Transid;
        // public TextView Billno;
        public TextView Expdate;
        public TextView BatchNo;
        public TextView mrp;
        public TextView quantity;
        public TextView uom,discount;
        public TextView productname;
        public TextView sellingprice;
        public TextView total,stock,Discount;
        //  public TextView saledate;
        // public Spinner  reason;
        public ImageButton Delete;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            mydb = new DBhelper(activity);
            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();

            holder = new ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.display_sales_return, parent, false);
            // holder.Transid = (TextView) convertView.findViewById(R.id.transid);
            // holder.Billno = (TextView) convertView.findViewById(R.id.billno);
            holder.Expdate = (TextView) convertView.findViewById(R.id.expdate);
            holder.Expdate.setTextSize(Float.parseFloat(textsize));

            holder.discount = (TextView) convertView.findViewById(R.id.discountreturn);
            holder.discount.setTextSize(Float.parseFloat(textsize));
            holder.BatchNo = (TextView) convertView.findViewById(R.id.batchno);
            holder.BatchNo.setTextSize(Float.parseFloat(textsize));
            // holder.reason = (EditText) convertView.findViewById(R.id.reason);
            holder.quantity = (TextView) convertView.findViewById(R.id.quty);
            holder.quantity.setTextSize(Float.parseFloat(textsize));
            // holder. reason = (Spinner) convertView.findViewById(R.id.reasonreturn);
            holder.uom = (TextView) convertView.findViewById(R.id.uom);
            holder.uom.setTextSize(Float.parseFloat(textsize));
            //   holder.saledate = (TextView)convertView.findViewById(R.id.saledate);
            holder.productname = (TextView) convertView.findViewById(R.id.prodname);
            holder.productname.setTextSize(Float.parseFloat(textsize));
            holder.sellingprice = (TextView) convertView.findViewById(R.id.selling);
            holder.sellingprice.setTextSize(Float.parseFloat(textsize));
            holder.total = (TextView) convertView.findViewById(R.id.totalpt);
            holder.total.setTextSize(Float.parseFloat(textsize));
            holder.stock = (TextView) convertView.findViewById(R.id.stock);
            holder.stock.setTextSize(Float.parseFloat(textsize));
            holder.Discount= (TextView) convertView.findViewById(R.id.discountsalereturn);
            holder.Discount.setTextSize(Float.parseFloat(textsize));

            holder.Delete = (ImageButton) convertView.findViewById(R.id.deleteButton);

             holder.mrp = (TextView) convertView.findViewById(R.id.Returnmrp);


            convertView.setTag(holder);
        }
        //holder.name.setText(mcustomerlist.get(position).getCustomername());
        else {
            holder = (ViewHolder) convertView.getTag();

        }
//            holder.Transid.setText(salesreturnlist.get(position).getSaleTransid());
        // holder.Billno.setText(salesreturnlist.get(position).getSaleBillno());
        DecimalFormat f=new DecimalFormat("##.00");

        holder.productname.setText(salesreturnlist.get(position).getSaleproductname());
        holder.Expdate.setText(salesreturnlist.get(position).getSaleexpiry());
        // holder.saledate.setText(salesreturnlist.get(position).getSaleDate());
        holder.BatchNo.setText(salesreturnlist.get(position).getSalebatchno());
        holder.Discount.setText(salesreturnlist.get(position).getSalediscoumt());

         holder.mrp.setText(salesreturnlist.get(position).getSalemrp());
        // holder.reason.setText(salesreturnlist.get(position).);


        if ((holder.quantity.getTag() != null) && (holder.quantity.getTag() instanceof TextWatcher)) {
            holder.quantity.removeTextChangedListener((TextWatcher) holder.quantity.getTag());
        }
        holder.quantity.setText(String.format("%d", salesreturnlist.get(position).getSaleqty()));
        holder.stock.setText(String.format("%d", salesreturnlist.get(position).getSaleqty()));

        holder.uom.setText(salesreturnlist.get(position).getSaleuom());
        holder.discount.setText(salesreturnlist.get(position).getDiscount());
        holder.sellingprice.setText(String.format("%.2f", salesreturnlist.get(position).getSalesellingprice()));
        holder.total.setText(String.format("%.2f",salesreturnlist.get(position).getSaletotal()));
        holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) * (Double.parseDouble(holder.quantity.getText().toString())))));

        TextWatcher qty = new TextWatcher() {
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

                  if (holder.quantity.getText().toString().equals("")) {
                      Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                      salesreturnlist.get(position).setSaleqty(0);
                      holder.quantity.setError("not empty");
                      return;
                  }


                  if (Integer.parseInt(holder.quantity.getText().toString()) > (Integer.parseInt(holder.stock.getText().toString()))) {
                      holder.quantity.setText("");
                      return;
                  }
                  salesreturnlist.get(position).setSaleqty(Integer.parseInt(holder.quantity.getText().toString()));
                  holder.total.setText(String.valueOf(f.format(Double.parseDouble(holder.sellingprice.getText().toString()) * (Double.parseDouble(holder.quantity.getText().toString())))));
                  activity.grandtotal();

                  RemoteVideoPresentation.updateReturnQuantity(position, Integer.parseInt(holder.quantity.getText().toString()));
              }catch (Exception ex)
              {
                  ex.printStackTrace();
              }

                }
            };

            // holder.quantity.removeTextChangedListener(qty);
            holder.quantity.addTextChangedListener(qty);
            holder.quantity.setTag(qty);
       /*     holder.sellingprice.addTextChangedListener(qty);
            holder.sellingprice.setTag(qty);*/


        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    salesreturnlist.remove(position);
                    RemoteVideoPresentation.deleteSalesReturnProductfromListWithPo(position);
                    activity.grandtotal();
                    notifyDataSetChanged();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });



        holder.productname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.Expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        holder.BatchNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });





        holder.sellingprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

//       holder.name.setText(customerlist.get(position).getCustomername());
//        holder.mobile_no.setText(customerlist.get(position).getCustomermobileno());

        return convertView;


    }
    public void addProductToList( Salesreturndetail product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");
        // salesreturnlist.add(product);
       /* PurchaseProductModel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {

        } else {
            productAlreadyInList.setProductQuantity( productAlreadyInList.getProductQuantity() + product.getProductQuantity());
        }
*/          salesreturnlist.add(product);


    }

    public ArrayList<Salesreturndetail> getList() {
        return salesreturnlist;
    }

   /* private PurchaseProductModel findProductInList(PurchaseProductModel product) {
        PurchaseProductModel returnProductVal = null;

        for( Salesreturndetail productInList : salesreturnlist) {
            if( productInList.getProductId().trim().equals(product.getProductId().trim()) ) {
                returnProductVal = productInList;
            }
        }


        return returnProductVal;
    }*/

    public float getGrandTotal() {
        // ViewHolder holder=new ViewHolder();
        float finalamount = 0.0f;
        for (int listIndex = 0; listIndex < salesreturnlist.size(); listIndex++) {
            try {
                finalamount += (salesreturnlist.get(listIndex).getSaletotal());
            } catch (Exception e) {
                //ignore exeption for parse
            }
            Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);

        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }


    public static void hideSoftKeyboard(ActivitySalesreturn activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(ActivitySalesreturn.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
