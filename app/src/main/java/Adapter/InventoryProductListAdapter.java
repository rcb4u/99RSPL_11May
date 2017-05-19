package Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.RSPL.POS.DBhelper;
import com.RSPL.POS.DecimalDigitsInputFilter;
import com.RSPL.POS.InventoryTotalActivity;
import com.RSPL.POS.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Pojo.Decimal;
import Pojo.InventoryTotalProductModel;
import Pojo.Visibility;

public class InventoryProductListAdapter extends  RecyclerView.Adapter<InventoryProductListAdapter.ViewHolder> {
    InventoryTotalActivity activity;
    ArrayList<InventoryTotalProductModel> list;
    private final int layoutResourceId;
    LayoutInflater layoutInflater;
    String item;
    InventoryTotalProductModel inventoryTotalProductModel;
    static final int DATE_DIALOG_ID = 0;
    private int mTempPositionBeforeCalenderDialog = -1;
    TextWatcher quantityTextChangeWatcher;
    Calendar myCalendar = Calendar.getInstance();
    final Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH) + 1;
    int Day = c.get(Calendar.DAY_OF_MONTH);
    DBhelper mydb = new DBhelper(activity);
    String mrp;
    String pprice;
    String sprice;
    String marginvisible, freeqtyvisible;

    public InventoryProductListAdapter(InventoryTotalActivity activity, ArrayList<InventoryTotalProductModel> list, int layoutResourceId, InventoryTotalProductModel inventoryTotalProductModel) {
        this.layoutResourceId = layoutResourceId;
        this.activity = activity;
        this.list = list;
        this.inventoryTotalProductModel = inventoryTotalProductModel;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView PurchaseName;
        public EditText Purchasemrp;
        public EditText Sellingprice;
        public EditText purchasingprice;
        public TextView measure;
        public TextView Total;
        public EditText EtQty;
        public EditText expdate;
        public EditText freequantity;
        public TextView inventorydiscount;
        public EditText batchno;
        public ImageButton DeleteButton;
        public ImageButton EditButton;
        public TextView pricemargin;
        public TextView industry;
        public TextView totalqty;
        public TextView conversion;
        public TextView discountamount;
        public TextView discountTotal;
        public ViewHolder(View convertView) {
            super(convertView);

            mydb = new DBhelper(activity);
            Visibility value2 = mydb.getStorevisibility();
            marginvisible = value2.getMarginvisiblty();
            freeqtyvisible=value2.getFreequantity();
            Decimal value = mydb.getStoreprice();
            mrp = value.getDecimalmrp();
            pprice = value.getDecimalpprice();
            sprice = value.getDecimalsprice();
            String textsize=         value.getHoldpo();



            PurchaseName = (TextView) convertView.findViewById(R.id.PurchaseproductName);
            PurchaseName.setTextSize(Float.parseFloat(textsize));

            Purchasemrp = (EditText) convertView.findViewById(R.id.PurchaseproductMrp);
            Purchasemrp.setTextSize(Float.parseFloat(textsize));
            Purchasemrp.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(mrp))});

            Sellingprice = (EditText) convertView.findViewById(R.id.purchaseselling);
            Sellingprice.setTextSize(Float.parseFloat(textsize));
            Sellingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(sprice))});

            industry = (TextView) convertView.findViewById(R.id.inventoryindustery);
            industry.setTextSize(Float.parseFloat(textsize));

            purchasingprice = (EditText) convertView.findViewById(R.id.purchaseprice);
            purchasingprice.setTextSize(Float.parseFloat(textsize));
            purchasingprice.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, Integer.parseInt(pprice))});

            measure = (TextView) convertView.findViewById(R.id.purchasemeasure);
            measure.setTextSize(Float.parseFloat(textsize));

            EtQty = (EditText) convertView.findViewById(R.id.purchasequantity);
            EtQty.setTextSize(Float.parseFloat(textsize));

            expdate = (EditText) convertView.findViewById(R.id.Exp_date);
            expdate.setTextSize(Float.parseFloat(textsize));

            inventorydiscount = (TextView) convertView.findViewById(R.id.purchasediscount);
            inventorydiscount.setTextSize(Float.parseFloat(textsize));
            inventorydiscount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(3, 2)});

            batchno = (EditText) convertView.findViewById(R.id.purchasebatchno);
            batchno.setTextSize(Float.parseFloat(textsize));

            freequantity = (EditText) convertView.findViewById(R.id.discountwithoutpo);
            freequantity.setTextSize(Float.parseFloat(textsize));
            if (freeqtyvisible.matches("N")) {
                freequantity.setVisibility(View.INVISIBLE);
            } else {
                freequantity.setVisibility(View.VISIBLE);
            }
            DeleteButton = (ImageButton) convertView.findViewById(R.id.deleteButton);
            EditButton = (ImageButton) convertView.findViewById(R.id.EditButton);
            Total = (TextView) convertView.findViewById(R.id.we);
            Total.setTextSize(Float.parseFloat(textsize));

            Total.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
            pricemargin = (TextView) convertView.findViewById(R.id.inventorymargin);
            pricemargin.setTextSize(Float.parseFloat(textsize));

            pricemargin.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(7, 2)});
            if (marginvisible.matches("N")) {
                pricemargin.setVisibility(View.INVISIBLE);
            } else {
                pricemargin.setVisibility(View.VISIBLE);
            }
            totalqty = (TextView) convertView.findViewById(R.id.totalqty);
            totalqty.setTextSize(Float.parseFloat(textsize));

            conversion = (TextView) convertView.findViewById(R.id.inventoryconversion);

        }

    }
    public void add(int position,InventoryTotalProductModel sales) {
       list.add(position, sales);
        notifyItemInserted(position);
    }

    public void setsalearrayList(ArrayList<InventoryTotalProductModel> salearrayList) {
        this.list = salearrayList;
    }

    public ArrayList<InventoryTotalProductModel> getList() {
        return list;
    }

    @Override
    public InventoryProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.display_inventory_product_row_total, parent, false);
        ViewHolder vh1 = new ViewHolder(v);
        return vh1;
    }

    @Override
    public void onBindViewHolder(final  ViewHolder holder,final  int position) {
        mydb = new DBhelper(activity);
        try {
            DecimalFormat f = new DecimalFormat("##.00");
            if ((holder.Purchasemrp.getTag() != null) && (holder.Purchasemrp.getTag() instanceof TextWatcher)) {
                holder.Purchasemrp.removeTextChangedListener((TextWatcher) holder.Purchasemrp.getTag());
            }
            holder.Purchasemrp.setText(String.format("%.2f", list.get(position).getMrp()));
      /*  if ((holder.inventorydiscount.getTag() != null) && (holder.inventorydiscount.getTag() instanceof TextWatcher)) {
            holder.inventorydiscount.removeTextChangedListener((TextWatcher) holder.inventorydiscount.getTag());
        }*/
        /*if ((holder.Total.getTag() != null) && (holder.Total.getTag() instanceof TextWatcher)) {
            holder.Total.removeTextChangedListener((TextWatcher) holder.Total.getTag());
        }*/
            if ((holder.EtQty.getTag() != null) && (holder.EtQty.getTag() instanceof TextWatcher)) {
                holder.EtQty.removeTextChangedListener((TextWatcher) holder.EtQty.getTag());
            }
            holder.EtQty.setText(String.format("%d", list.get(position).getProductQuantity()));
            if ((holder.freequantity.getTag() != null) && (holder.freequantity.getTag() instanceof TextWatcher)) {
                holder.freequantity.removeTextChangedListener((TextWatcher) holder.freequantity.getTag());
            }
            holder.freequantity.setText(String.format("%d", list.get(position).getFreequantity()));
            if ((holder.Sellingprice.getTag() != null) && (holder.Sellingprice.getTag() instanceof TextWatcher)) {
                holder.Sellingprice.removeTextChangedListener((TextWatcher) holder.Sellingprice.getTag());
            }
            holder.Sellingprice.setText(String.format("%.2f", list.get(position).getSprice()));
            if ((holder.batchno.getTag() != null) && (holder.batchno.getTag() instanceof TextWatcher)) {
                holder.batchno.removeTextChangedListener((TextWatcher) holder.batchno.getTag());
            }
            holder.batchno.setText(list.get(position).getBatchno());
            holder.totalqty.setText(String.format("%.2f", list.get(position).getTotalqty()));
            holder.conversion.setText(String.format("%.2f", list.get(position).getConvfact()));
            holder.pricemargin.setText(String.format("%.2f", list.get(position).getProductmargin()));
            holder.PurchaseName.setText(list.get(position).getProductName());
            holder.measure.setText(list.get(position).getTax());
            holder.expdate.setText(list.get(position).getExpdate());
            if ((holder.purchasingprice.getTag() != null) && (holder.purchasingprice.getTag() instanceof TextWatcher)) {
                holder.purchasingprice.removeTextChangedListener((TextWatcher) holder.purchasingprice.getTag());
            }
            holder.purchasingprice.setText(String.format("%.2f", list.get(position).getPprice()));
            ////////////////////////////////////////Calculations////////////////////////////////////////////////////////////////
            holder.inventorydiscount.setText(String.format("%.2f", list.get(position).getInvdiscount()));
            Double RetVal = ((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString());
            holder.pricemargin.setText(String.valueOf(f.format(RetVal)));
            holder.inventorydiscount.setText(String.valueOf(f.format(RetVal)));
            holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.freequantity.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));
            holder.Total.setText(String.format("%.2f", list.get(position).getTotal()));
            if (Float.parseFloat(holder.inventorydiscount.getText().toString()) > 0.00) {
                String Distotl = String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString())) * Float.parseFloat(holder.inventorydiscount.getText().toString()) / 100));
                String Totl = String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))));
                holder.Total.setText(String.valueOf(f.format(Double.parseDouble(Totl) - Double.parseDouble(Distotl))));
            } else {
                holder.Total.setText((String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))))));
            }
            Log.e("Rahul before TOTAL", "" + holder.Total.getText().toString());
            quantityTextChangeWatcher = new TextWatcher() {
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
                            list.get(position).setProductQuantity(0);
                            return;
                        }
                        if (holder.purchasingprice.getText().toString().equals("")) {
                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                            list.get(position).setPprice(0.0f);
                            return;
                        }
                        if (holder.Purchasemrp.getText().toString().equals("")) {
                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                            list.get(position).setMrp(0.0f);
                            return;
                        }
                        if (holder.freequantity.getText().toString().equals("")) {
                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                            list.get(position).setFreequantity(0);
                            return;
                        }
                        if (holder.Sellingprice.getText().toString().equals("")) {
                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                            list.get(position).setSprice(0.0f);
                            return;
                        }
                        if (holder.Total.getText().toString().equals("")) {
                            Log.w("&&&&&&&&", "Quantity string was NULL hence returning ....");
                            list.get(position).setTotal(0.0f);
                            return;
                        }
                        ////////////////////////////////////Calculations///////////////////////////////////////////////////////////////////////
                        DecimalFormat f = new DecimalFormat("##.00");
                        holder.pricemargin.setText(String.valueOf(((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString())));
                        Double RetVal = ((Double.parseDouble(holder.Purchasemrp.getText().toString()) - (Double.parseDouble(holder.purchasingprice.getText().toString()))) * 100) / Double.parseDouble(holder.Purchasemrp.getText().toString());
                        holder.pricemargin.setText(String.valueOf(f.format(RetVal)));
                        holder.inventorydiscount.setText(String.valueOf(f.format(RetVal)));
                        holder.totalqty.setText(String.valueOf(((Double.parseDouble(holder.EtQty.getText().toString()) + (Double.parseDouble(holder.freequantity.getText().toString()))) * Double.parseDouble(holder.conversion.getText().toString()))));
                        list.get(position).setProductQuantity(Integer.parseInt(holder.EtQty.getText().toString()));
                        list.get(position).setMrp(Float.parseFloat(holder.Purchasemrp.getText().toString()));
                        list.get(position).setSprice(Float.parseFloat(holder.Sellingprice.getText().toString()));
                        list.get(position).setPprice(Float.parseFloat(holder.purchasingprice.getText().toString()));
                        list.get(position).setFreequantity(Integer.parseInt(holder.freequantity.getText().toString()));
                        list.get(position).setProductmargin(Float.parseFloat(holder.pricemargin.getText().toString()));
                        list.get(position).setTotalqty(Float.parseFloat(holder.totalqty.getText().toString()));
                        list.get(position).setBatchno(holder.batchno.getText().toString());
                        list.get(position).setInvdiscount(Float.parseFloat(holder.inventorydiscount.getText().toString()));
                        if (Float.parseFloat(holder.inventorydiscount.getText().toString()) > 0.00) {
                            String Distotl = String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString())) * Float.parseFloat(holder.inventorydiscount.getText().toString()) / 100));
                            String Totl = String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))));
                            holder.Total.setText(String.valueOf(f.format(Double.parseDouble(Totl) - Double.parseDouble(Distotl))));
                        } else {
                            holder.Total.setText((String.valueOf(f.format(Double.parseDouble(holder.purchasingprice.getText().toString()) * (Double.parseDouble(holder.EtQty.getText().toString()))))));
                        }
                        list.get(position).setTotal(Float.parseFloat(holder.Total.getText().toString()));
                        activity.setSummaryRow();
                    } catch (Exception e) {
                        Log.e("&&&&&&&&", "Exception " + e + " while parsing double");
                        e.printStackTrace();
                    }
                }
            };
            holder.EtQty.addTextChangedListener(quantityTextChangeWatcher);
            holder.EtQty.setTag(quantityTextChangeWatcher);
            holder.Purchasemrp.addTextChangedListener(quantityTextChangeWatcher);
            holder.Purchasemrp.setTag(quantityTextChangeWatcher);
            holder.Sellingprice.addTextChangedListener(quantityTextChangeWatcher);
            holder.Sellingprice.setTag(quantityTextChangeWatcher);
            holder.freequantity.addTextChangedListener(quantityTextChangeWatcher);
            holder.freequantity.setTag(quantityTextChangeWatcher);
            holder.batchno.addTextChangedListener(quantityTextChangeWatcher);
            holder.batchno.setTag(quantityTextChangeWatcher);
/*
        holder.Total.addTextChangedListener(quantityTextChangeWatcher);
        holder.Total.setTag(quantityTextChangeWatcher);*/
            holder.purchasingprice.addTextChangedListener(quantityTextChangeWatcher);
            holder.purchasingprice.setTag(quantityTextChangeWatcher);
            holder.EditButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View alertLayout = layoutInflater.inflate(R.layout.totaleditpopup, null);
                    final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                    final EditText TotalIp = (EditText) alertLayout.findViewById(R.id.TotalEdit);
                    final Button TotalSubmit = (Button) alertLayout.findViewById(R.id.buttonTotal);
                    alert.setView(alertLayout);
                    alert.setCancelable(true);
                    TextView title = new TextView(activity);
                    // You Can Customise your Title here
                    title.setText("ENTER THE TOTAL VALUE TO CHANGE");
                    //  title.setBackgroundColor(Color.DKGRAY);
                    title.setPadding(10, 10, 10, 10);
                    title.setGravity(Gravity.CENTER);
                    title.setTextColor(Color.BLACK);
                    title.setTextSize(20);
                    alert.setCustomTitle(title);
                    final AlertDialog dialog = alert.create();
                    TotalSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TotalIp.getText().toString().equals("")) {
                                TotalIp.setText("0.00");
                                return;
                            }
                            String tesstt = TotalIp.getText().toString();
                            if ((Float.parseFloat(tesstt) / Float.parseFloat(holder.EtQty.getText().toString())) > Float.parseFloat(holder.Purchasemrp.getText().toString())) {
                                Toast.makeText(activity, "Please Checked Entered Value", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            holder.Total.setText(tesstt);
                            holder.purchasingprice.setText(String.valueOf(Float.parseFloat(tesstt) / Float.parseFloat(holder.EtQty.getText().toString())));
                            Log.e("inside popup after", "" + tesstt);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
            holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        list.remove(position);
                        activity.setSummaryRow();
                        notifyDataSetChanged();
                    } catch (Exception e) {
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
            holder.PurchaseName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard(activity);
                }
            });
            holder.measure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard(activity);
                }
            });
            holder.pricemargin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard(activity);
                }
            });
            holder.totalqty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard(activity);
                }
            });
      /*  holder.purchasingprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(activity);
            }
        });
*/
    /*  holder.purchasingprice.setGravity(Gravity.CENTER);
        holder.pricemargin.setGravity(Gravity.CENTER);*/
            holder.pricemargin.setGravity(Gravity.CENTER);
            holder.inventorydiscount.setGravity(Gravity.CENTER);
            holder.totalqty.setGravity(Gravity.CENTER);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
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
    public void validateDate(int year, int month, int day) {
        Date Todaydate = null, edate = null;
        String enddate = year + "/" + month + "/" + day;
        Log.e("########", "----------->" + enddate);
        String todaysdate = Year + "/" + Month + "/" + Day;
        //    String   demo =myCalendar.get(Calendar.YEAR)+"/"+ myCalendar.get(Calendar.MONTH)+"/"+myCalendar.get(Calendar.DAY_OF_MONTH);
        Log.e("########", "----------->" + todaysdate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            edate = sdf.parse(enddate);
            Todaydate = sdf.parse(todaysdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (edate.before(Todaydate)) {
            Toast.makeText(activity, "Invalid date !!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Log.e("########", "----------->" + todaysdate);
            Log.e("########", "----------->" + edate);
            //   startDatePicked=true;
            if (mTempPositionBeforeCalenderDialog != -1 && mTempPositionBeforeCalenderDialog < list.size()) {
                int Mymonth = month;
                list.get(mTempPositionBeforeCalenderDialog).setExpdate(year + "/" + Mymonth + "/" + day);
                notifyDataSetChanged();
                mTempPositionBeforeCalenderDialog = -1;
            }
        }
    }
    public static void hideSoftKeyboard(InventoryTotalActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(InventoryTotalActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<InventoryTotalProductModel> getlist() {
        return list;
    }
    public void setList(ArrayList<InventoryTotalProductModel> list) {
        this.list = list;
    }
    public int addProductToList(InventoryTotalProductModel product) {
        Log.e("&&&&&&&&", "Adding product " + product.toString() + " to product list");
        InventoryTotalProductModel productAlreadyInList = findProductInList(product);
        if (productAlreadyInList == null) {
            list.add(0, product);
            return 0;
        } else {
            productAlreadyInList.setProductQuantity(productAlreadyInList.getProductQuantity() + product.getProductQuantity());
            return list.indexOf(productAlreadyInList);
        }
    }
    private InventoryTotalProductModel findProductInList(InventoryTotalProductModel product) {
        InventoryTotalProductModel returnProductVal = null;
        for (InventoryTotalProductModel productInList : list) {
            if (productInList.getProductName().trim().equals(product.getProductName().trim())) {
                returnProductVal = productInList;
            }
        }
        return returnProductVal;
    }
    public void clearAllRows() {
        list.clear();
        notifyDataSetChanged();
    }
    public void setBatchdata(InventoryTotalProductModel product) {
        InventoryTotalProductModel productAlreadyInList = findProductInList(product);
        if (productAlreadyInList.getIndustry().toString().equals("CPG") || productAlreadyInList.getIndustry().toString().equals("CPG local")) {
            productAlreadyInList.setBatchno(product.getProductName());
        } else {
            productAlreadyInList.setBatchno(product.getBatchno());
        }
    }
    public float getGrandTotal() {
        // ViewHolder holder=new ViewHolder();
        float finalamount = 0.0f;
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            if (list.get(listIndex).getInvdiscount() > 0.00) {
                finalamount += (list.get(listIndex).getPprice() * list.get(listIndex).getProductQuantity() - (list.get(listIndex).getPprice() * list.get(listIndex).getProductQuantity() * list.get(listIndex).getInvdiscount() / 100));
            } else {
                finalamount += (list.get(listIndex).getTotal());
            }
            Log.e("&&&&&^^Rahul^^^^^^", "" + list.get(listIndex).getInvdiscount());
        }
        Log.e("&&&&55555555&&&", "Total Price is:" + finalamount);
        return finalamount;
    }

    public float getGrandTotalineitem() {
        float finalamount = 0.0f;
        DecimalFormat f = new DecimalFormat("##.00");
        for (int listIndex = 0; listIndex < list.size(); listIndex++) {
            // finalamount += Float.parseFloat(String.valueOf((salearraylist.get(listIndex).getDiscountamount())));
            if (list.get(listIndex).getInvdiscount() > 0.00) {
                finalamount += Float.parseFloat(String.valueOf((list.get(listIndex).getInvdiscount())));
            } else {
                finalamount += (list.get(listIndex).getTotal());
            }
        }
        Log.e("Rahul_LineItemTotal", "$$$$$$$$" + finalamount);
        return finalamount;
    }
}