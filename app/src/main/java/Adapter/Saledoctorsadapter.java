package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.RSPL.POS.ActivitySalesbill;
import com.RSPL.POS.Activity_Doctor;
import com.RSPL.POS.R;

import java.util.ArrayList;

import Pojo.DoctorPojo;

/**
 * Created by rspl-rahul on 12/1/17.
 */

public class Saledoctorsadapter extends ArrayAdapter<DoctorPojo> {
    private final int layoutResourceId;
    private Context mcontext;
    ActivitySalesbill activity;
    private LayoutInflater mInflater;
    TextView tv;
    TextView v;

    public Saledoctorsadapter(ActivitySalesbill activity, int layoutResourceId, ArrayList<DoctorPojo> mdoctorlist) {
        super(activity, layoutResourceId, mdoctorlist);
        this.activity = activity;
        this.doctorlist= mdoctorlist;
        this.layoutResourceId = layoutResourceId;
//        mInflater = (LayoutInflater) activity
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void setDoctorList(ArrayList<DoctorPojo> doctorList) {
        this.doctorlist = doctorList;
    }

    private ArrayList<DoctorPojo> doctorlist;
    public int getCount() {
        if (doctorlist.size() < 0)
            return 1;
        return doctorlist.size();
    }


    public DoctorPojo getItem(int position) {
        return doctorlist.get(position);
    }


    public long getItemId(int position) {

        //.getCustomermobileno();
        return position;
    }

    public static class ViewHolder {

        public TextView name;
        public TextView mobile_no;
        public TextView email;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        DoctorAdapter.ViewHolder holder;

        if (convertView == null) {

            holder = new DoctorAdapter.ViewHolder();


            LayoutInflater mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.doctoritems, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.doctorlist_name);
            holder.mobile_no = (TextView) convertView.findViewById(R.id.Customerlist_mobile);
            convertView.setTag(holder);
        }

        else {
            holder = (DoctorAdapter.ViewHolder) convertView.getTag();
        }
        holder.name.setText(doctorlist.get(position).getDoctorName());
        holder.mobile_no.setText(doctorlist.get(position).getDoctid());

        return convertView;


    }
}