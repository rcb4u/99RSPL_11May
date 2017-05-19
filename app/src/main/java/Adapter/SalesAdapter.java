
package Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesbill;
import com.RSPL.POS.Activity_decimal;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.DecimalDigitsInputFilter;
import com.RSPL.POS.R;
import com.RSPL.POS.RemoteVideoPresentation;

//import com.mycompany.apps.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Pojo.Decimal;
import Pojo.Sales;
import Pojo.Visibility;

/**
 * Created by rahul on 18/3/16.
 */
public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {
    ActivitySalesbill activity;
    ArrayList<Sales> salearraylist;
    //  private final int layoutResourceId;
    ArrayList productarrayList;
    LayoutInflater layoutInflater;
    Sales salesmodel;
    String mrp;
    String sprice,MRPisShown;
    DBhelper mydb;

    public SalesAdapter(ActivitySalesbill activity, int layoutResourceId, ArrayList<Sales> salesarraylist) {
        super();
        this.salearraylist = salesarraylist;
        this.activity = activity;
        //  this.layoutResourceId = layoutResourceId;

        this.productarrayList = productarrayList;
        this.layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // this.salesmodel=salesmodel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ProductName;
        public TextView BatchNo;
        public TextView Expiry;
        public TextView PPrice;
        public EditText SPrice;
        public EditText Quantity;
        public TextView StockQuant;
        public EditText Mrp;
        public TextView Amount;
        public TextView Uom;
        public TextView Total;
        public TextView conversionfactor, Discountamt, Discamt;
        public ImageButton Delete;
        public EditText Discount;
        public TextView searchprm;

        public ViewHolder (View convertView) {
            super(convertView);
               mydb =new DBhelper(activity);
            Visibility value = mydb.getStorevisibility();
            MRPisShown = value.getMrpvisibility();

            Decimal valuetextsize = mydb.getStoreprice();
            String textsize=         valuetextsize.getHoldpo();


            ProductName = (TextView) convertView.findViewById(R.id.productname);
            ProductName.setTextSize(Float.parseFloat(textsize));
            Discountamt = (TextView) convertView.findViewById(R.id.discountamount);
            Discountamt.setTextSize(Float.parseFloat(textsize));
            Discamt = (TextView) convertView.findViewById(R.id.discounttotal);
            Discamt.setTextSize(Float.parseFloat(textsize));
            BatchNo = (TextView) convertView.findViewById(R.id.batchno);
            Expiry = (TextView) convertView.findViewById(R.id.productexp);
            Expiry.setTextSize(Float.parseFloat(textsize));
            Quantity = (EditText) convertView.findViewById(R.id.quantity);
            Quantity.setTextSize(Float.parseFloat(textsize));
            StockQuant = (TextView) convertView.findViewById(R.id.quantitystock);
            StockQuant.setTextSize(Float.parseFloat(textsize));
            conversionfactor = (TextView) convertView.findViewById(R.id.conersionfact);
            Mrp = (EditText) convertView.findViewById(R.id.mrp);
            Mrp.setTextSize(Float.parseFloat(textsize));

            if(MRPisShown.matches("N")){
                Mrp.setVisibility(View.GONE);
            }
            else{

                Mrp.setVisibility(View.VISIBLE);
            }
//       Mrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(mrp))});
            Discount = (EditText) convertView.findViewById(R.id.discountsale);
            Discount.setTextSize(Float.parseFloat(textsize));

            // holder.Amount = (TextView) convertView.findViewById(R.id.amount);
            Uom = (TextView) convertView.findViewById(R.id.uom);
            Uom.setTextSize(Float.parseFloat(textsize));
            //holder.PPrice=(TextView)convertView.findViewById(R.id.pprice);

            SPrice = (EditText) convertView.findViewById(R.id.sprice);
            SPrice.setTextSize(Float.parseFloat(textsize));

//        SPrice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(sprice))});

            Total = (TextView) convertView.findViewById(R.id.total);
            Total.setTextSize(Float.parseFloat(textsize));

            Delete = (ImageButton) convertView.findViewById(R.id.deleteButton);
            searchprm = (TextView) convertView.findViewById(R.id.searchmrp);

        }
    }



    public void add(int position,Sales sales) {
        salearraylist.add(position, sales);
        notifyItemInserted(position);
    }

    public void setsalearrayList(ArrayList<Sales> salearrayList) {
        this.salearraylist = salearrayList;
    }


    public void setProductarrayList(ArrayList productarrayList){
        this.productarrayList=productarrayList;
    }
    public ArrayList<Sales> getList() {
        return salearraylist;
    }



//    public SalesAdapter (ArrayList<Sales> salearraylist) {
//        arr_userid = myDataset;
//    }

    @Override
    public SalesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_sales, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mydb = new DBhelper(activity);
        try {
                /*    Decimal value = mydb.getStoreprice();
                  mrp=value.getDecimalmrp();

                 sprice=value.getDecimalsprice();
                mrp =  Activity_decimal.b.getInt("MRP_Decimal");
            sprice =  Activity_decimal.b.getInt("S_Price_Decimal");*/

                holder.ProductName.setText(salearraylist.get(position).getProductName());
                holder.StockQuant.setText(String.format("%.2f", salearraylist.get(position).getHoldstock()));
                holder.Uom.setText(salearraylist.get(position).getUom());
                holder.Expiry.setText(salearraylist.get(position).getExpiry());

                //holder.BatchNo.setText(salearraylist.get(position).getBatchNo());

                if ((holder.Quantity.getTag() != null) && (holder.Quantity.getTag() instanceof TextWatcher)) {
                    holder.Quantity.removeTextChangedListener((TextWatcher) holder.Quantity.getTag());
                }
                holder.Quantity.setText(String.format("%d", salearraylist.get(position).getQuantity()));


                if ((holder.StockQuant.getTag() != null) && (holder.StockQuant.getTag() instanceof TextWatcher)) {
                    holder.StockQuant.removeTextChangedListener((TextWatcher) holder.StockQuant.getTag());
                }
                holder.StockQuant.setText(String.format("%.2f", salearraylist.get(position).getStockquant()));

                if ((holder.conversionfactor.getTag() != null) && (holder.conversionfactor.getTag() instanceof TextWatcher)) {
                    holder.conversionfactor.removeTextChangedListener((TextWatcher) holder.conversionfactor.getTag());
                }
                DecimalFormat f = new DecimalFormat("##.00");
                holder.conversionfactor.setText(String.format("%.2f", salearraylist.get(position).getConversionfacter()));

                if ((holder.Mrp.getTag() != null) && (holder.Mrp.getTag() instanceof TextWatcher)) {
                    holder.Mrp.removeTextChangedListener((TextWatcher) holder.Mrp.getTag());
                }
                holder.Mrp.setText(String.format("%.2f", salearraylist.get(position).getMrp()));

                if ((holder.Discount.getTag() != null) && (holder.Discount.getTag() instanceof TextWatcher)) {
                    holder.Discount.removeTextChangedListener((TextWatcher) holder.Discount.getTag());
                }
                holder.Discount.setText(String.format("%.2f", salearraylist.get(position).getDiscountsales()));

                if ((holder.SPrice.getTag() != null) && (holder.SPrice.getTag() instanceof TextWatcher)) {
                    holder.SPrice.removeTextChangedListener((TextWatcher) holder.SPrice.getTag());
                }
                holder.SPrice.setText(String.format("%.2f", salearraylist.get(position).getSPrice()));

                final int selling = Integer.parseInt(holder.Quantity.getText().toString());


                holder.Total.setText(String.format("%.2f", salearraylist.get(position).getTotal()));
                holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.SPrice.getText().toString()) * (Double.parseDouble(holder.Quantity.getText().toString())) - Double.parseDouble(holder.SPrice.getText().toString()) * (Double.parseDouble(holder.Quantity.getText().toString()) * (Double.parseDouble(holder.Discount.getText().toString()) / 100)))));


                if ((holder.Discountamt.getTag() != null) && (holder.Discountamt.getTag() instanceof TextWatcher)) {
                    holder.Discountamt.removeTextChangedListener((TextWatcher) holder.Discountamt.getTag());
                }
                holder.Discountamt.setText(String.format("%.2f", salearraylist.get(position).getDiscountamount()));
                holder.Discountamt.setText(String.valueOf(Double.parseDouble(holder.Total.getText().toString()) - (Double.parseDouble(holder.Total.getText().toString())) * (Double.parseDouble(holder.Discount.getText().toString()) / 100)));

                holder.Discamt.setText(String.format("%.2f", salearraylist.get(position).getDiscountamountsalestotal()));
                holder.Discamt.setText(String.valueOf((Double.parseDouble(holder.Total.getText().toString())) * (Double.parseDouble(holder.Discount.getText().toString()) / 100)));

            holder.StockQuant.setText(String.format("%.2f", salearraylist.get(position).getStockquant()));
            holder.StockQuant.setText(String.valueOf(Double.parseDouble(holder.StockQuant.getText().toString()) - Double.parseDouble(holder.Quantity.getText().toString())));

            //  holder.StockQuant.setText(String.valueOf(Float.parseFloat(holder.StockQuant.getText().toString()) * (Float.parseFloat(holder.conversionfactor.getText().toString()))));
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
                            if (holder.Quantity.getText().toString().equals("")) {

                                holder.Quantity.setError("Enter a valid quantity");
                                salearraylist.get(position).setQuantity(0);
                                return;
                            } else if (selling < 0) {
                                holder.Quantity.setError("invalid");
                                return;
                            } else if (holder.Discountamt.getText().toString().equals("")) {
                                salearraylist.get(position).setDiscountamount(0);
                                return;
                            }
                            DecimalFormat f = new DecimalFormat("##.00");
                            // holder.StockQuant.setText(String.valueOf(Float.parseFloat(holder.StockQuant.getText().toString()) * (Float.parseFloat(holder.conversionfactor.getText().toString()))));
                            salearraylist.get(position).setMrp(Float.parseFloat(holder.Mrp.getText().toString()));

                            salearraylist.get(position).setQuantity(Integer.parseInt(holder.Quantity.getText().toString()));
                            // RemoteVideoPresentation.updateQuantity(position, Integer.parseInt(holder.Quantity.getText().toString()));
                            activity.updateQuantity(position, Integer.parseInt(holder.Quantity.getText().toString()));



                            salearraylist.get(position).setSPrice(Float.parseFloat(holder.SPrice.getText().toString()));
                           // activity.updateSalesPrice(position, Integer.parseInt((holder.SPrice.getText().toString())));
                            salearraylist.get(position).setDiscountsales(Float.parseFloat(holder.Discount.getText().toString()));
                            holder.Discamt.setText(String.valueOf((Double.parseDouble(holder.Total.getText().toString())) * (Double.parseDouble(holder.Discount.getText().toString()) / 100)));
                            salearraylist.get(position).setDiscountamountsalestotal(Float.parseFloat(holder.Discamt.getText().toString()));
                            //holder.Discountamt.setText(String.valueOf(Double.parseDouble(holder.Total.getText().toString())-(Double.parseDouble(holder.Total.getText().toString())) * (Double.parseDouble(holder.Discount.getText().toString()) / 100)));
                            //holder.StockQuant.setText(salearraylist.get(position));

                            holder.StockQuant.setText(String.format("%.2f", salearraylist.get(position).getStockquant()));
                            holder.StockQuant.setText(String.valueOf(Double.parseDouble(holder.StockQuant.getText().toString()) - Double.parseDouble(holder.Quantity.getText().toString())));


                          //  salearraylist.get(position).setStockquant(Float.parseFloat(holder.StockQuant.getText().toString()));
                            if (Double.parseDouble(holder.Discountamt.getText().toString()) > 0) {
                                holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.SPrice.getText().toString()) * (Double.parseDouble(holder.Quantity.getText().toString())) - Double.parseDouble(holder.SPrice.getText().toString()) * (Double.parseDouble(holder.Quantity.getText().toString()) * (Double.parseDouble(holder.Discount.getText().toString())/100)))));
                                salearraylist.get(position).setTotal((float) Double.parseDouble((holder.Total.getText().toString())));
                                salearraylist.get(position).setDiscountamount(Float.parseFloat(holder.Discountamt.getText().toString()));

                            } else {
                                holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.SPrice.getText().toString()) * (Double.parseDouble(holder.Quantity.getText().toString())))));
                                salearraylist.get(position).setTotal((float) Double.parseDouble((holder.Total.getText().toString())));
                                salearraylist.get(position).setDiscountamount(Float.parseFloat(holder.Discountamt.getText().toString()));
                            }

                            activity.setSummaryRow();
                            //
                        } catch (Exception e) {
                            Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
                            e.printStackTrace();
                        }
                    }
                };
                holder.Quantity.addTextChangedListener(quantityTextChangeWatcher);
                holder.Quantity.setTag(quantityTextChangeWatcher);


                holder.SPrice.addTextChangedListener(quantityTextChangeWatcher);
                holder.SPrice.setTag(quantityTextChangeWatcher);

                holder.conversionfactor.addTextChangedListener(quantityTextChangeWatcher);
                holder.conversionfactor.setTag(quantityTextChangeWatcher);

                holder.Mrp.addTextChangedListener(quantityTextChangeWatcher);
                holder.Mrp.setTag(quantityTextChangeWatcher);

                holder.Discount.addTextChangedListener(quantityTextChangeWatcher);
                holder.Discount.setTag(quantityTextChangeWatcher);

                holder.Discountamt.addTextChangedListener(quantityTextChangeWatcher);
                holder.Discountamt.setTag(quantityTextChangeWatcher);


                 // holder.StockQuant.setText(String.valueOf(Float.parseFloat(holder.StockQuant.getText().toString()) * (Float.parseFloat(holder.conversionfactor.getText().toString()))));

                holder.Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            salearraylist.remove(position);
                            //  RemoteVideoPresentation.deleteSalesProductfromList(position);
                            activity.deleteData(position);
                            activity.setSummaryRow();
                            notifyDataSetChanged();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            } catch (NumberFormatException nm) {
            nm.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Sales data) {
        salearraylist.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    @Override
    public int getItemCount() {
        return salearraylist.size();
    }

    public void clearAllRows() {
        salearraylist.clear();
        notifyDataSetChanged();
    }

    public int  addProductToList(Sales salesproduct) {
        Log.e("&&&&&&&&", "Adding product " + salesproduct.toString() + " to product list");

        Sales productAlreadyInList = findsalesitemInList(salesproduct);
        if (productAlreadyInList == null) {
            salearraylist.add(0,salesproduct);
            return 0;
        } else {
            productAlreadyInList.setQuantity(productAlreadyInList.getQuantity() + salesproduct.getQuantity());

            return salearraylist.indexOf(productAlreadyInList);
        }

    }

    private Sales findsalesitemInList(Sales salesproduct) {
        Sales returnSalesVal = null;

        for (Sales productInList : salearraylist) {
            if (productInList.getProductName().trim().equals(salesproduct.getProductName().trim())) {
                //check batch number also (if batch number is different, we should not add to the
                // same row

                if(productInList.getBatchNo().trim().equals(salesproduct.getBatchNo().trim())) {
                    returnSalesVal = productInList;
                }
            }
        }


        return returnSalesVal;
    }
//    public void updatequty(){
//        DBhelper db= new DBhelper(getContext());
//        salearraylist.get(0).getBatchNo();
//        salearraylist.get(0).getStockquant();
//
//        //     db.updateStockQty(sm.getBatchNo().toString(),sm.getStockquant().toString());
//    }

    public float getGrandTotal() {
        float finalamount = 0.0f;
        DecimalFormat f=new DecimalFormat("##.0");
        for (int listIndex = 0; listIndex < salearraylist.size(); listIndex++) {
            try {

                if (salearraylist.get(listIndex).getDiscountamount()>0.00)
                {
                    finalamount += Float.parseFloat(String.valueOf((salearraylist.get(listIndex).getDiscountamount())));
                }
                else {
                    finalamount += Float.parseFloat(String.valueOf((salearraylist.get(listIndex).getTotal())));
                }// holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.SPrice.getText().toString())  * (Double.parseDouble(holder.Quantity.getText().toString())))));

            } catch (Exception e) {
                //ignore exeption for parse
            }
        }
        Log.e("Rahul=Total", "Total Price is:" + finalamount);
        return finalamount ;
    }
    public float getGrandTotalineitem() {
        // int position = 0;
        float finalamount = 0.0f;
        //  Disvaluesales = String.valueOf(salearraylist.get(position).getDiscountsales());
        DecimalFormat f=new DecimalFormat("##.00");
        for (int listIndex = 0; listIndex < salearraylist.size(); listIndex++) {

            // finalamount += Float.parseFloat(String.valueOf((salearraylist.get(listIndex).getDiscountamount())));
            if (salearraylist.get(listIndex).getDiscountamount()>0.00)
            {
                finalamount += Float.parseFloat(String.valueOf((salearraylist.get(listIndex).getDiscountamount())));
            }
            else {
                finalamount +=(salearraylist.get(listIndex).getDiscountamountsalestotal());
            }
        }
        Log.e("Rahul_LineItemTotal", "$$$$$$$$" + finalamount);
        return finalamount ;
    }
    public float gettotalline()
    {
        DecimalFormat f=new DecimalFormat("##.00");

        float finaltotalsaving = 0.0f;
        for(int lisIndex= 0;lisIndex < salearraylist.size();lisIndex++){
            try
            {
                finaltotalsaving +=(salearraylist.get(lisIndex).getDiscountamountsalestotal());
            }catch (Exception ex){
            }
        }
        Log.e("Rahul_TotalLine", "Totalsavings :" + finaltotalsaving);
        return finaltotalsaving;
    }


    public float gettotalsavings(){
        DecimalFormat f=new DecimalFormat("##.00");

        float finaltotalsaving = 0.0f;
        for(int lisIndex= 0;lisIndex < salearraylist.size();lisIndex++){
            try
            {
                finaltotalsaving +=((salearraylist.get(lisIndex).getMrp() - salearraylist.get(lisIndex).getSPrice()) *salearraylist.get(lisIndex).getQuantity());
            }catch (Exception ex){

            }

        }
        Log.e("&&&&&&&", "Totalsavings :" + finaltotalsaving);
        return finaltotalsaving;
    }


}

