//package Adapter;
//
//import android.content.Context;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import com.mycompany.apps.ActivitySalesbill;
//import com.mycompany.apps.DBhelper;
//import com.mycompany.apps.R;
//
//import java.util.ArrayList;
//
//import Pojo.Sales;
//
///**
// * Created by rspl-nishant on 30/5/16.
// */
//public class HoldBillAdapter extends ArrayAdapter<Sales>{
//
//    ActivitySalesbill activity;
//        ArrayList<Sales> salearraylist;
//        private final int layoutResourceId;
//        LayoutInflater layoutInflater;
//        Sales salesmodel;
//
//        public HoldBillAdapter(ActivitySalesbill activity, int layoutResourceId, ArrayList<Sales> salearraylist) {
//            super(activity,layoutResourceId,salearraylist);
//            // super(activity, layoutResourceId, salearraylist);
//            this.salearraylist = salearraylist;
//            this.activity = activity;
//            this.layoutResourceId = layoutResourceId;
//            // this.salesmodel=salesmodel;
//
//        }
//
//
//
//
//        public long getItemId(int position) {
//            Log.e("**********", "" + position);
//            return position;
//        }
//
//        public int getCount() {
//            if (salearraylist.size() < 0)
//                return 1;
//            Log.e("**get Count***", salearraylist.toString());
//            return salearraylist.size();
//        }
//
//        public Sales getItem(int position) {
//
//            return salearraylist.get(position);
//        }
//
//        public void setsalearrayList(ArrayList<Sales> salearrayList) {
//            this.salearraylist = salearrayList;
//        }
//
//        public void clearAllRows() {
//            salearraylist.clear();
//
//        }
//
//
//        public static class ViewHolder {
//            public TextView ProductName;
//            public TextView BatchNo;
//            public TextView Expiry;
//            public TextView PPrice;
//            public TextView SPrice;
//            public EditText Quantity;
//            public TextView StockQuant;
//            public TextView Mrp;
//            public TextView Amount;
//            public TextView Uom;
//            public TextView Total;
//            public ImageButton Delete;
//
//
//        }
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//
//            final ViewHolder holder;
//
//            if (convertView == null) {
//
//                holder = new ViewHolder();
//                layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = layoutInflater.inflate(R.layout.display_sales, parent, false);
//                holder.ProductName=(TextView)convertView.findViewById(R.id.productname);
//                holder.BatchNo = (TextView) convertView.findViewById(R.id.batchno);
//                holder.Expiry = (TextView) convertView.findViewById(R.id.productexp);
//                holder.Quantity = (EditText) convertView.findViewById(R.id.quantity);
//                holder.StockQuant  = (TextView) convertView.findViewById(R.id.quantitystock);
//                holder.Mrp = (TextView) convertView.findViewById(R.id.mrp);
//                // holder.Amount = (TextView) convertView.findViewById(R.id.amount);
//                holder.Uom = (TextView) convertView.findViewById(R.id.uom);
//                //holder.PPrice=(TextView)convertView.findViewById(R.id.pprice);
//                holder.SPrice = (TextView) convertView.findViewById(R.id.sprice);
//                holder.Total = (TextView) convertView.findViewById(R.id.total);
//                holder.Delete = (ImageButton) convertView .findViewById(R.id.deleteButton);
//                convertView.setTag(holder);
//            } else {
//                holder = (ViewHolder) convertView.getTag();
//            }
//
//       /* if (position > 0 ) {
//            if( salearraylist.get(position).getProdid().trim().equals( salearraylist.get(position-1).getProdid().trim())) {
//                holder.ProductName.setVisibility(View.INVISIBLE);
//                holder.Mrp.setVisibility(View.INVISIBLE);
//
//                holder.Uom.setVisibility(View.INVISIBLE);
//                holder.SPrice.setVisibility(View.INVISIBLE);
//                holder.Expiry.setVisibility(View.INVISIBLE);
//
//            } else {
//                holder.ProductName.setVisibility(View.VISIBLE);
//                holder.Mrp.setVisibility(View.VISIBLE);
//                holder.Uom.setVisibility(View.VISIBLE);
//                holder.SPrice.setVisibility(View.VISIBLE);
//                holder.Expiry.setVisibility(View.VISIBLE);
//            }
//        }*/
//
//            holder.ProductName.setText(salearraylist.get(position).getProductName());
//            holder.StockQuant.setText(String.format("%.2f",salearraylist.get(position).getStockquant()));
//
//            holder.BatchNo.setText(salearraylist.get(position).getBatchNo());
//            holder.Expiry.setText(salearraylist.get(position).getExpiry());
//            if ((holder.Quantity.getTag() != null) && (holder.Quantity.getTag() instanceof TextWatcher)) {
//                holder.Quantity.removeTextChangedListener((TextWatcher) holder.Quantity.getTag());
//            }
//            holder.Quantity.setText(String.format("%d", salearraylist.get(position).getQuantity()));
////            if ((holder.StockQuant.getTag()!= null) && (holder.StockQuant.getTag() instanceof TextWatcher)){
////                holder.StockQuant.removeTextChangedListener((TextWatcher) holder.StockQuant.getTag());
////            }
////            holder.StockQuant .setText(String.format("%.2f", salearraylist.get(position).getStockquant()));
//            holder.Mrp.setText(String.format("%.2f", salearraylist.get(position).getMrp()));
//            // holder.Amount.setText(salearraylist.get(position).getAmount());
//            holder.Uom.setText(salearraylist.get(position).getUom());
//
//
//            //  holder.PPrice.setText(salearraylist.get(position).getPPrice());
//            if ((holder.SPrice.getTag() != null) && (holder.SPrice.getTag() instanceof TextWatcher)) {
//                holder.SPrice.removeTextChangedListener((TextWatcher) holder.SPrice.getTag());
//            }
//            holder.SPrice.setText(String.format("%.2f", salearraylist.get(position).getSPrice()));
//            final int selling = Integer.parseInt(holder.Quantity.getText().toString());
//
//
///*       String ProductName = activity.GetProductName();
//        Log.e("!!!!!!!!!", "Barcode" + ProductName);*/
//            DBhelper helper=new DBhelper(getContext());
//            ArrayList<Sales>InsertBarcode=helper.getProductNamedata(salearraylist.get(0).getBatchNo());
//            //ArrayList<String>InsertBarcode=helper.getBatchNo(ProductName);
//
//            //create an array of strings of batch no. only
//            //pass it to the adatper creation below
//
//            //// ArrayAdapter<Sales>BatchNo=new ArrayAdapter<Sales>(getContext(),android.R.layout.simple_spinner_item,InsertBarcode);
//            //  holder.BatchNo.setAdapter(BatchNo);
//
//            holder.Total.setText(String.format("%.2f", salearraylist.get(position).getTotal()));
//            holder.Total.setText(String.valueOf(Double.parseDouble(holder.SPrice.getText().toString()) * Double.parseDouble(holder.Quantity.getText().toString())));
//            TextWatcher quantityTextChangeWatcher = new TextWatcher() {
//
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    try {
//
//
//                        if (holder.Quantity.getText().toString().equals("")) {
//                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
//                            return;
//                        }
//                        if ( selling < 0 )
//                        {
//                            holder.Quantity.setError("invalid");
//                            return;
//                        }
//                        holder.Total.setText(String.valueOf(Double.parseDouble(holder.SPrice.getText().toString()) * Double.parseDouble(holder.Quantity.getText().toString())));
//
//                        salearraylist.get(position).setQuantity(Integer.parseInt(holder.Quantity.getText().toString()));
//                        salearraylist.get(position).setSPrice(Float.parseFloat(holder.SPrice.getText().toString()));
//                        Log.e("&&&total&&&&", "$$$$$" + holder.Total.getText().toString());
//                        activity.setSummaryRow();
//                    } catch (Exception e) {
//                        Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
//                        e.printStackTrace();
//                    }
//                }
//            };
//            holder.Quantity.addTextChangedListener(quantityTextChangeWatcher);
//            holder.Quantity.setTag(quantityTextChangeWatcher);
//            holder.SPrice.addTextChangedListener(quantityTextChangeWatcher);
//            holder.SPrice.setTag(quantityTextChangeWatcher);
//
//
////            holder.StockQuant.setText(String.format("%.2f", salearraylist.get(position).getStockquant()));
////            holder.StockQuant.setText(String.valueOf(Double.parseDouble(holder.StockQuant.getText().toString()) - Double.parseDouble(holder.Quantity.getText().toString())));
////            TextWatcher stockquantityTextChangeWatcher = new TextWatcher() {
////                @Override
////                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////                }
////
////                @Override
////                public void onTextChanged(CharSequence s, int start, int before, int count) {
////
////                }
////                @Override
////                public void afterTextChanged(Editable s) {
////                    try {
////                        if (holder.StockQuant.getText().toString().equals("")) {
////                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
////                            return;
////                        }
////                        int selling = Integer.parseInt(holder.StockQuant.getText().toString());
////                        int purchase = Integer.parseInt(holder.Quantity.getText().toString());
////                        //   int mrp = Integer.parseInt(localmrp.getText().toString());
////
////                        Log.e("Nishant",selling+"  "+purchase);
////                        if (purchase>selling)
////                        {
////                            holder.Quantity.setError("invalid");
////                            return;
////                        }
////                        holder.StockQuant.setText(String.valueOf(Double.parseDouble(holder.StockQuant.getText().toString()) - Double.parseDouble(holder.Quantity.getText().toString())));
////
////                        salearraylist.get(position).setQuantity(Integer.parseInt(holder.Quantity.getText().toString()));
////                        salearraylist.get(position).setStockquant(Float.parseFloat(holder.StockQuant.getText().toString()));
////
////                        Log.e("&&&total&&&&", "$$$$$" + holder.StockQuant.getText().toString());
////                        //activity.setSummaryRow();
////                    } catch (Exception e) {
////                        Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
////                        e.printStackTrace();
////                    }
////                }
////            };
//            // holder.StockQuant.addTextChangedListener(stockquantityTextChangeWatcher);
//            // holder.StockQuant.setTag(stockquantityTextChangeWatcher);
//
////        holder.Quantity.addTextChangedListener(stockquantityTextChangeWatcher);
////        holder.Quantity.setTag(stockquantityTextChangeWatcher);
//            holder.Delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    salearraylist.remove(position);
//                    activity.setSummaryRow();
//
//                    notifyDataSetChanged();
//                }
//            });
//
//            return convertView;
//        }
//
//        public void updatequty(){
//            DBhelper db= new DBhelper(getContext());
//            salearraylist.get(0).getBatchNo();
//            salearraylist.get(0).getStockquant();
//
//            //     db.updateStockQty(sm.getBatchNo().toString(),sm.getStockquant().toString());
//
//
//        }
//
//
//
//        public void addProductToList(Sales salesproduct) {
//            Log.e("&&&&&&&&", "Adding product " + salesproduct.toString() + " to product list");
//
//            Sales productAlreadyInList = findsalesitemInList(salesproduct);
//            if (productAlreadyInList == null) {
//                salearraylist.add(salesproduct);
//            } else {
//                productAlreadyInList.setQuantity((int) (productAlreadyInList.getQuantity() + salesproduct.getQuantity()));
//            }
//
//        }
//
//        public ArrayList<Sales> getList() {
//            return salearraylist;
//        }
//
//
//
//        private Sales findsalesitemInList(Sales salesproduct) {
//            Sales returnSalesVal = null;
//
//            for (Sales productInList : salearraylist) {
//                if (productInList.getProductName().trim().equals(salesproduct.getProductName().trim())) {
//                    //check batch number also (if batch number is different, we should not add to the
//                    // same row
//
//                    if(productInList.getBatchNo().trim().equals(salesproduct.getBatchNo().trim())) {
//                        returnSalesVal = productInList;
//                    }
//                }
//            }
//
//
//            return returnSalesVal;
//        }
//
//
//        public float getGrandTotal() {
//            // ViewHolder holder=new ViewHolder();
//            float finalamount = 0.0f;
//            for (int listIndex = 0; listIndex < salearraylist.size(); listIndex++) {
//                try {
//                    finalamount += (salearraylist.get(listIndex).getTotal());
//                } catch (Exception e) {
//                    //ignore exeption for parse
//                }
//                Log.e("&&&&&^^^^^^^^", "$$$$$$$$" + finalamount);
//
//            }
//            Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
//            return finalamount;
//        }
//
//        public void remove(int list) {
//            getItem(list);
//            notifyDataSetChanged();
//        }
//
//        public float gettotalsavings(){
//
//            float finaltotalsaving = 0.0f;
//            for(int lisIndex= 0;lisIndex < salearraylist.size();lisIndex++){
//                try
//                {
//                    finaltotalsaving +=((salearraylist.get(lisIndex).getMrp() - salearraylist.get(lisIndex).getSPrice())  * salearraylist.get(lisIndex).getQuantity());
//                }catch (Exception ex){
//
//                }
//
//            }
//            Log.e("&&&&&&&", "Totalsavings :" + finaltotalsaving);
//            return finaltotalsaving;
//        }
//
//
//    }
//
//
//
