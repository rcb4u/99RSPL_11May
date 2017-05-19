package Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.CustomAuto;
import com.RSPL.POS.DBhelper;
import com.RSPL.POS.DecimalDigitsInputFilter;
import com.RSPL.POS.PersistenceManager;
import com.RSPL.POS.R;
import com.RSPL.POS.StockAdjustment;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import Config.Config;
import JSON.JSONParserSync;
import Pojo.Decimal;
import Pojo.InventoryStockadjustmentmodel;
import Pojo.Inventoryproductmodel;

/**
 * Created by rspl-rahul on 6/9/16.
 */
public class Stockproductadapter extends BaseAdapter {
    StockAdjustment activity;
    InventoryStockadjustmentmodel purchaseProductModel;
    ArrayList<InventoryStockadjustmentmodel> list;
    Stockproductadapter adapteralert;
    DecimalFormat f = new DecimalFormat("##.00");

    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    private int meditposition = 0;

    String mrp;
    String pprice;
    String sprice;
    DBhelper mydb;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    Stockproductadapter stockproductadapterobject;
    private int mTempPositionBeforeCalenderDialog = -1;
    Calendar myCalendar = Calendar.getInstance();
    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH)+1;
    int Day = c.get(Calendar.DAY_OF_MONTH);



    public Stockproductadapter(StockAdjustment activity, ArrayList<InventoryStockadjustmentmodel> list, int layoutResourceId, InventoryStockadjustmentmodel purchaseProductModel) {
        this.activity = activity;
        this.list = list;
        this.layoutResourceId = layoutResourceId;
        this.purchaseProductModel = purchaseProductModel;
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

    public void setList(ArrayList<InventoryStockadjustmentmodel> list) {
        this.list = list;
    }

    public InventoryStockadjustmentmodel getItem(int position) {
        return list.get(position);
    }

    public static class ViewHolder {
        //TextView PurchaseId;
        public TextView PurchaseName;
        public EditText Purchasemrp;
        public EditText Sellingprice;
        public EditText purchasingprice;
        public EditText EditQty;
        public TextView Total;
        public EditText expdate;
        public TextView batchno;
        public ImageButton DeleteButton;
        //   public ImageButton editbutton;
        public Button stockdelete;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        DecimalFormat f = new DecimalFormat("##.00");

        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_stockadjustment_product_row, parent, false);


            mydb = new DBhelper(activity);
            Decimal value = mydb.getStoreprice();
            mrp = value.getDecimalmrp();
            pprice = value.getDecimalpprice();
            sprice = value.getDecimalsprice();
            String textsize=         value.getHoldpo();



            holder.PurchaseName = (TextView) convertView.findViewById(R.id.PurchaseproductName);
            holder.PurchaseName.setTextSize(Float.parseFloat(textsize));

            holder.Purchasemrp = (EditText) convertView.findViewById(R.id.PurchaseproductMrp);
            holder.Purchasemrp.setTextSize(Float.parseFloat(textsize));
            holder.Purchasemrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(mrp))});

            holder.Sellingprice = (EditText) convertView.findViewById(R.id.purchaseselling);
            holder.Sellingprice.setTextSize(Float.parseFloat(textsize));
            holder.Sellingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(sprice))});

            holder.purchasingprice = (EditText) convertView.findViewById(R.id.purchaseprice);
            holder.purchasingprice.setTextSize(Float.parseFloat(textsize));
            holder.purchasingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(pprice))});

            holder.Total = (TextView) convertView.findViewById(R.id.TotalStockAdj);
            holder.Total.setTextSize(Float.parseFloat(textsize));

            holder.expdate = (EditText) convertView.findViewById(R.id.Exp_date);
            holder.expdate.setTextSize(Float.parseFloat(textsize));

            holder.batchno = (TextView) convertView.findViewById(R.id.purchasebatchno);
            holder.batchno.setTextSize(Float.parseFloat(textsize));

            holder.EditQty = (EditText) convertView.findViewById(R.id.EditQty);
            holder.EditQty.setTextSize(Float.parseFloat(textsize));

            holder.DeleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);
            holder.stockdelete = (Button) convertView.findViewById(R.id.deletestock);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ((holder.Purchasemrp.getTag() != null) && (holder.Purchasemrp.getTag() instanceof TextWatcher)) {
            holder.Purchasemrp.removeTextChangedListener((TextWatcher) holder.Purchasemrp.getTag());
        }
        holder.Purchasemrp.setText(String.format("%.2f", list.get(position).getConvMrp()));

        if ((holder.purchasingprice.getTag() != null) && (holder.purchasingprice.getTag() instanceof TextWatcher)) {
            holder.purchasingprice.removeTextChangedListener((TextWatcher) holder.purchasingprice.getTag());
        }
        holder.purchasingprice.setText(String.format("%.2f", list.get(position).getPprice()));


        if ((holder.Sellingprice.getTag() != null) && (holder.Sellingprice.getTag() instanceof TextWatcher)) {
            holder.Sellingprice.removeTextChangedListener((TextWatcher) holder.Sellingprice.getTag());
        }
        holder.Sellingprice.setText(String.format("%.2f", list.get(position).getSprice()));

        holder.batchno.setText(list.get(position).getBatchno());
        Log.e("editbatchnumber", "" + list.get(position).getBatchno());
        holder.PurchaseName.setText(list.get(position).getProductName());
        holder.expdate.setText(list.get(position).getExpdate());
        if ((holder.EditQty.getTag() != null) && (holder.EditQty.getTag() instanceof TextWatcher)) {
            holder.EditQty.removeTextChangedListener((TextWatcher) holder.EditQty.getTag());
        }
        holder.EditQty.setText(String.format("%d", list.get(position).getProductQuantity()));

       /*   if ((holder.Total.getTag() != null) && (holder.Total.getTag() instanceof TextWatcher)) {
            holder.Total.removeTextChangedListener((TextWatcher) holder.Total.getTag());
        }*/
        holder.Total.setText(String.format("%.2f",list.get(position).getTotal()));
        holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString())*Double.parseDouble(holder.EditQty.getText().toString()))));
        //TextWatcher quantityTextChangeWatcher = new TextWatcher() {

   /* catch (NumberFormatException ex){
    ex.printStackTrace();*/


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
                    DecimalFormat f = new DecimalFormat("##.00");
                  /*  if (holder.EtQty.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.EtQty.setError("Enter a valid quantity");
                        list.get(position).setProductQuantity(0);
                        return;
                    }*/

                    if (holder.purchasingprice.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.purchasingprice.setError("Enter a valid purchase price");
                        list.get(position).setPprice(0.0f);
                        return;
                    }
                  /*  if (holder.batchno.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.batchno.setError("Enter a valid batch number");
                        list.get(position).setBatchno("");
                        return;
                    }
                    if (holder.expdate.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.expdate.setError("Enter a valid exp date");
                        list.get(position).setExpdate("");
                        return;
                    }*/
                    if (holder.Purchasemrp.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.Purchasemrp.setError("Enter a valid mrp");
                        list.get(position).setConvMrp(0.0f);
                        return;
                    }
                    /*if (holder.freequantity.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.freequantity.setError("Enter a  free quantity");
                        list.get(position).setFreequantity(0);
                        return;
                    }*/
                    if (holder.Sellingprice.getText().toString().equals("")) {
                        Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                        holder.Sellingprice.setError("Enter a valid selling price");
                        list.get(position).setSprice(0.0f);
                        return;
                    }
                    list.get(position).setBatchno(holder.batchno.getText().toString());
                    list.get(position).setConvMrp(Float.parseFloat(holder.Purchasemrp.getText().toString()));
                    list.get(position).setSprice(Float.parseFloat(holder.Sellingprice.getText().toString()));
                    list.get(position).setPprice(Float.parseFloat(holder.purchasingprice.getText().toString()));
                    list.get(position).setProductQuantity(Integer.parseInt(holder.EditQty.getText().toString()));
                    holder.Total.setText(String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString())*Double.parseDouble(holder.EditQty.getText().toString()))));
                    list.get(position).setTotal(Float.parseFloat(holder.Total.getText().toString()));
                } catch (Exception e) {
                    Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
                    e.printStackTrace();
                }
            }


        };
      /*  holder.editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meditposition=position;
                SearchnewproductDialod( meditposition);


            }
        });*/

        holder.Purchasemrp.addTextChangedListener(quantityTextChangeWatcher);
        holder.Purchasemrp.setTag(quantityTextChangeWatcher);

        holder.Sellingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.Sellingprice.setTag(quantityTextChangeWatcher);

        holder.purchasingprice.addTextChangedListener(quantityTextChangeWatcher);
        holder.purchasingprice.setTag(quantityTextChangeWatcher);

        holder.EditQty.addTextChangedListener(quantityTextChangeWatcher);
        holder.EditQty.setTag(quantityTextChangeWatcher);

/*

        holder.Total.addTextChangedListener(quantityTextChangeWatcher);
        holder.Total.setTag(quantityTextChangeWatcher);
*/

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    list.remove(position);
                    //  activity.setSummaryRow();
                    notifyDataSetChanged();
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });


        holder.expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempPositionBeforeCalenderDialog = position;
                new DatePickerDialog(activity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        holder.stockdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    DeleteTop_Product(position);
                    Updatestokeadjustment(position);
                    //     list.remove(deposotion);
                }catch(Exception e)
                {
                    e.printStackTrace();
                    Log.e("Stock Delete",""+e.toString());
                }
            }
        });



        holder.PurchaseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });


        holder.batchno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftKeyboard(activity);
            }

        });

        return convertView;

    }





    private DatePickerDialog.OnDateSetListener date = new
            DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int month,
                                      int day) {
                    // TODO Auto-generated method stub
                    validateDate(year, month + 1, day);
                }
            };

    public void validateDate(int year,int month,int day) {

        Date Todaydate=null,edate=null;

        String enddate=year+"/"+month+"/"+day;
        Log.e("########", "----------->" + enddate);
        String todaysdate=Year+"/"+Month+"/"+Day;

        //    String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########", "----------->" + todaysdate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        if(edate.before(Todaydate)){
            Toast.makeText(activity,"Invalid date !!", Toast.LENGTH_SHORT).show();

            return ;

        }
        else{
            Log.e("########", "----------->" + todaysdate);
            Log.e("########", "----------->" + edate);

            //   startDatePicked=true;
            if(mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
                int Mymonth=month;
                list.get(mTempPositionBeforeCalenderDialog).setExpdate(year+"/"+Mymonth +"/"+day);
                notifyDataSetChanged();
                mTempPositionBeforeCalenderDialog = -1;

            }
        }
    }





    public ArrayList<InventoryStockadjustmentmodel> getlist() {

        return list;
    }

    public int addProductToList( InventoryStockadjustmentmodel product ) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");

        InventoryStockadjustmentmodel productAlreadyInList = findProductInList(product);
        if(productAlreadyInList == null) {
            list.add(0,product);
            return 0;
        } else {
            productAlreadyInList.setProductQuantity(productAlreadyInList.getProductQuantity() + product.getProductQuantity());
            return list.indexOf(productAlreadyInList);
        }

    }
    private InventoryStockadjustmentmodel findProductInList(InventoryStockadjustmentmodel product) {
        InventoryStockadjustmentmodel returnProductVal = null;

        for( InventoryStockadjustmentmodel productInList : list) {
            if( productInList.getBatchno().trim().equals(product.getBatchno().trim()) ) {
                returnProductVal = productInList;
            }
        }

        return returnProductVal;
    }
    public void clearAllRows() {
        list.clear();
        notifyDataSetChanged();

    }


    private void SearchnewproductDialod(final int pos) {


        LayoutInflater inflater = layoutInflater;
        final View searchalertLayout = inflater.inflate(R.layout.inventoryeditalert, null);
        AlertDialog.Builder searchalert = new AlertDialog.Builder(activity);
        final AutoCompleteTextView searchproductname = (CustomAuto) searchalertLayout.findViewById(R.id.searchproductname);
        final TextView productname = (TextView) searchalertLayout.findViewById(R.id.setproductname);
        final TextView searchbatch = (TextView) searchalertLayout.findViewById(R.id.searchbatchno);
        final EditText searchmrp = (EditText) searchalertLayout.findViewById(R.id.searchmrp);
        final EditText searchsprice = (EditText) searchalertLayout.findViewById(R.id.searchsprice);
        final EditText searchqty = (EditText) searchalertLayout.findViewById(R.id.searchqty);
        final TextView searchpprice = (TextView) searchalertLayout.findViewById(R.id.searchpprice);
        final Button searchsubmit = (Button) searchalertLayout.findViewById(R.id.Searchsubmit);
        final Button searchexit = (Button) searchalertLayout.findViewById(R.id.searchexit);

        final Bundle Bundle = new Bundle();

        final AlertDialog dialog2 = searchalert.create();
        dialog2.setView(searchalertLayout);
        dialog2.show();

        dialog2.setCanceledOnTouchOutside(false);

        final DBhelper db = new DBhelper(activity);
        if (meditposition != -1 && meditposition < list.size()) {

            productname.setText(list.get(pos).getProductName());
            searchbatch.setText(list.get(pos).getBatchno());

            searchmrp.setText(String.valueOf(list.get(pos).getConvMrp()));
            searchmrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});

            searchsprice.setText((String.valueOf(list.get(pos).getSprice())));
            searchsprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});

            searchpprice.setText((String.valueOf(list.get(pos).getPprice())));
            searchpprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});

            searchqty.setText((String.valueOf(list.get(pos).getProductQuantity())));
            //      searchqty.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});


        }
        searchsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (meditposition != -1 && meditposition < list.size()) {


                   /* String b=     searchbatch.getText().toString();
                    list.get(pos).setBatchno(b);
                    Log.e("editbatch",""+searchbatch.getText().toString());*/
                        //     list.get(pos).setProductQuantity(Integer.parseInt(searchqty.getText().toString()));
                        list.get(pos).setConvMrp(Float.parseFloat(searchmrp.getText().toString()));
                        list.get(pos).setSprice(Float.parseFloat(searchsprice.getText().toString()));
                        list.get(pos).setPprice(Float.parseFloat(searchpprice.getText().toString()));
                        list.get(pos).setProductQuantity(Integer.parseInt(searchqty.getText().toString()));

                        notifyDataSetChanged();
                    }
                    // activity.setSummaryRow();
                    dialog2.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        searchexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();

            }
        });

        //  });
    }
//    final String stockbatch = String.valueOf(purchase.getBatchno());


    public void DeleteTop_Product(int delposition) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        final int deletePosition = delposition;

        // Setting Dialog Title

        alertDialog.setTitle("Confirm Delete...");
        alertDialog.setMessage("Are you sure you want Delete this?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //  for (int listIndex = 0; listIndex < list.size(); listIndex++) {

                if(deletePosition != -1 && deletePosition < list.size()) {

                    try {
                        mydb.Deletestock(list.get(deletePosition).getBatchno());

                        list.remove(deletePosition);
                        notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }




    public void Updatestokeadjustment(int updposotion) {


        final int updatePosition = updposotion;

        if (updatePosition != -1 && updatePosition < list.size()) {

            //     try {

            final String batchno = list.get(updatePosition).getBatchno();

            PersistenceManager.saveStoreId(activity, mydb.getStoreid().toString().replace("[", "").replace("]", ""));
            final String store_id = PersistenceManager.getStoreId(activity);


            class Updatedailyreport extends AsyncTask<Void, Void, String> {
                ProgressDialog loading;
                int success;


                @Override
                protected String doInBackground(Void... params) {
                    try {

                        HashMap<String, String> hashMap = new HashMap<>();

                        hashMap.put(Config.Retail_inventory_store_id, store_id);
                        hashMap.put(Config.Retail_Inventory_batchno, batchno);

                        JSONParserSync rh = new JSONParserSync();
                        JSONObject s = rh.sendPostRequest(Config.STOCK_ADJUSTMENT_DELETE, hashMap);
                        Log.d("Login attempt", s.toString());

                        // success tag for json
                        success = s.getInt(TAG_SUCCESS);
                        if (success == 1) {
                            Log.d("Successful StockDelete!", s.toString());


                            //                       return s.getString(TAG_MESSAGE);
                        } else {

                            //                      return s.getString(TAG_MESSAGE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    // loading = ProgressDialog.show(activity_inventory.this, "UPDATING...", "Wait...", false, false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //     loading.dismiss();
                    //  Toast.makeText(getActivity().getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }

            }
            Updatedailyreport updatereport = new Updatedailyreport();
            updatereport.execute();
        }


    }
    public static void hideSoftKeyboard(StockAdjustment activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(StockAdjustment.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}

