package com.RSPL.POS;

/**
 * Created by rspl-rajeev on 5/5/16.
 */

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.Presentation;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import Adapter.SecondBillAdapter;
import Adapter.SecondSalesReturnAdapter;
import Adapter.SecondSalesReturnAdapterWithotInvoiceNumber;
import Adapter.TabsPagerAdapter;
import Config.Config;
import JSON.JSONParserSync;
import Pojo.Sales;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;


@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class RemoteVideoPresentation extends Presentation{
    VideoView videoView;
    TextView textView_scrolling;
    ImageView imageView;
    TextView customerId;
    String Ad_Play, Store_id, Store_Media_Id, startdate, enddate,
            starttime, endtime, Str_store_id, Str_store_media_id, Cust_id, Ad_Play_Click;
    ArrayList<Video_Data> videodata = new ArrayList<Video_Data>();

    int videoIncrement, imageIncrement = 0;
    SecondBillAdapter adapter1;
    ArrayList<Uri> main_ad_video = new ArrayList<Uri>();
    SimpleDateFormat timeFormat;
    SimpleDateFormat Addatetime, Addatetimeforclick;
    static ListView listView;
    static SecondBillAdapter adapter;

    static SecondSalesReturnAdapterWithotInvoiceNumber secondSalesReturnAdapterWithotInvoiceNumber;
    static SecondSalesReturnAdapter salesReturnAdapter;

    private TimerTask clickSchedule;
    private TimerTask scrollerSchedule;
    private TimerTask faceAnimationSchedule;

    private Timer faceTimer = null;
    int clickcount = 0;
    String touch_Count,CustomerMobNumber;
    String videoname;
    public static  HorizontalScrollView hsv1;
    public static  LinearLayout ll1;
    Button search,clear;


    //private ImageView imageViewRound,imageViewRound1;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    Button cashbt, chequebt, cardbt, creditnote;

    public static TextView GrandTotal;
    private TextView Transid;
    private TextView Billno;
    private  TextView Totalitems;
    private  Button paybycash;
    // Tab titles
    private String[] tabs = {"Fast Moving", "Discount", "Push Adds"};






    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    // private GoogleApiClient client;



    public static Display mDisplay;
    private static  Context mContext;
    private VideoView mVideoView;
    TextView store_Name;
    static TextView customer_name;
    private Uri mVideoUri;
    private Uri mVideoUri1;
    private Uri mVideoUri2;

    private TextView adNewsStream;
    private ImageView imageeee;


    private TextView customeridtextview;

    public static ListView billing_List;
    public static Context outerContext;
    Display display;


    private AudioManager mAudManager;
    Video_DataBase_Helper databasehandler = new Video_DataBase_Helper(getContext());



    Presentation presentation;

    String Value;
    static LinearLayout linearLayout,linearLayout1;
    static TextView discount, netpayable;

    DBhelper helper =new DBhelper(getContext());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.loadLibrary("sqliteX");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);
        creatFoldervideo1();
        creatFolderimage();
        mAudManager = (AudioManager)getContext().getSystemService(Context.AUDIO_SERVICE);
        getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        listView=(ListView)findViewById(R.id.listviewSec);
        mVideoView = (VideoView) findViewById(R.id.SecScrVV1);
        store_Name=(TextView)findViewById(R.id.store_name_second);
        linearLayout=(LinearLayout)findViewById(R.id.linearlayoutss);
        linearLayout1=(LinearLayout)findViewById(R.id.linearlayouts);
        customer_name=(TextView)findViewById(R.id.custName);
        GrandTotal=(TextView)findViewById(R.id.sec_total);
        netpayable =(TextView)findViewById(R.id.sec_total_net);
        discount =(TextView)findViewById(R.id.sec_discount) ;
        ArrayList<String> store_name= helper.getAllStores();



        store_Name.setText(store_name.get(1).toString());
        fullScreenVideo();




       /* SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        Value = sharedPref.getString("id", "Not Available");
        Log.e("Demoooooooo",""+Value);*/

    }
    public static final void billLevelDisount(String grandtotal,String discounts){
        netpayable.setText(grandtotal);
        discount.setText(discounts);


    }
    public static void fullScreenVideo(){
        try {
            if (mDisplay != null) {
                clearTotal();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 100.0f;
                linearLayout.setLayoutParams(layoutParams);


            } else {
                Log.e("No display", "No display Found");
            }
        }catch (Exception e){}
    }
    public static void showBill(){
        try {
            if (mDisplay != null) {
                // linearLayout.animate().scaleX(-25);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);

                layoutParams.weight = 80.0f;
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams1.weight = 20.0f;

                linearLayout.setLayoutParams(layoutParams);
                linearLayout1.setLayoutParams(layoutParams1);
            } else {
                Log.e("No display", "No display Found");
            }
        }catch (Exception e){}
    }


    public static void clearTotal(){

        GrandTotal.setText("0.0");
        discount.setText("0.0");
        netpayable.setText("0.0");
        customer_name.setText("");
        if (adapter != null) {
            adapter.clearAllRows();
        }
        else if(salesReturnAdapter!=null){
            salesReturnAdapter.clearAllRows();
        }
        else if(secondSalesReturnAdapterWithotInvoiceNumber!=null){
            secondSalesReturnAdapterWithotInvoiceNumber.clearAllRows();
        }
        else{}


    }



    public void creatFoldervideo1(){

        //  main_ad_video folder Name Specify Here
        File videodir = new File(Environment.getExternalStorageDirectory() + "/1464772267" + "/MainAd");
        Log.e("####******########", videodir.toString());
        final File[] filest = videodir.listFiles();
        if (videodir.exists() && videodir.isDirectory()) {
            final File[] files = videodir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {  // it is a folder...
                        } else {  // it is a file...
                            main_ad_video.add(Uri.fromFile(file));
                        }
                    }
                }
            }

        } else {
            videodir.mkdir();
        }


    }




    //create image folder in external storage of POS device(company logo)
    public void creatFolderimage(){

        File imagedir = new File(Environment.getExternalStorageDirectory() + "/1464772267" + "/CLogo");
        Log.e("####******########", imagedir.toString());
        imagedir.mkdir();
    }






    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public RemoteVideoPresentation(Context outerContext, Display display, Uri video, TextView newsticker,
                                   LinearLayout linearLayouter, HorizontalScrollView horizontalScrollView123, ImageView imageView1, ListView bill) {


        super(outerContext, display);
        mDisplay = display;
        mContext = outerContext;
        mVideoUri = video;
        adNewsStream = newsticker;
        ll1 = linearLayouter;
        hsv1 = horizontalScrollView123;
        imageeee = imageView1;
        billing_List=bill;
    }
    public static  void getCustomername(String pos_cust){

        try {
            customer_name.setText(pos_cust);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void playVideo() {
        if (mVideoView != null) {
           databasehandler = new Video_DataBase_Helper(getContext());

            videodata = new ArrayList<Video_Data>();
            videodata = databasehandler.getVideosDetails();
            Log.e("####", "#####size:" + videodata.size());

            ArrayList<Video_Data> mediaclick = new ArrayList<Video_Data>();
            mediaclick = databasehandler.getAllMediaClick();
            Log.e("####", "#####size:" + mediaclick.size());

            Date date = new Date();
            final CharSequence s = DateFormat.format("yyyy-MM-dd ", date.getTime());
            final Calendar c1 = Calendar.getInstance();
            timeFormat = new SimpleDateFormat("HH:mm:ss");
            Addatetime = new SimpleDateFormat("yyyyMMDDHHmmssmm");

            final List<String> storeidlist = databasehandler.getStoreid();
            Log.e("###############", storeidlist.get(0).toString().replace("[", "").replace("]", ""));
            Str_store_id = storeidlist.get(0).toString().replace("[", "").replace("]", "");


            final List<String> storemediaid = databasehandler.getMediaid();
            Log.e("###############", storemediaid.get(0).toString().replace("[", "").replace("]", ""));
            Str_store_media_id = storemediaid.get(0).toString().replace("[", "").replace("]", "");


            File videodir = new File(Environment.getExternalStorageDirectory() +  "/1464772267" + "/MainAd");
            Log.e("####******########", videodir.toString());
            final File[] filest = videodir.listFiles();
            if (videodir.exists() && videodir.isDirectory()) {
                final File[] files = videodir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file != null) {
                            if (file.isDirectory()) {  // it is a folder...
                            } else {  // it is a file...
                                main_ad_video.add(Uri.fromFile(file));
                            }
                        }
                    }
                }

            } else {
                videodir.mkdir();
            }

            //  mVideoView.setVideoURI(mVideoUri);
            mVideoView.setVideoURI(main_ad_video.get(videoIncrement));


            int result = mAudManager.requestAudioFocus(afChangeListener,
                    // Use the music stream.
                    AudioManager.STREAM_MUSIC,
                    // Request permanent focus.
                    AudioManager.AUDIOFOCUS_GAIN);
            if (result == AudioManager.AUDIOFOCUS_REQUEST_FAILED)
            {
                //Error
            }
            mAudManager.setParameters("bgm_state=true");


            mVideoView.start();
            startdate = s.toString();
            starttime = timeFormat.format(c1.getTime());
            Ad_Play = Addatetime.format(c1.getTimeInMillis());
            Store_id = Str_store_id;
            Store_Media_Id = Str_store_media_id;

            validatevideoname();

            // call setOnPreparedListener for set default mute option........
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    mp.setVolume(0, 0);
                }
            });

            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    Calendar c2 = Calendar.getInstance();
                    enddate = s.toString();
                    Ad_Play = Addatetime.format(c2.getTime());
                    endtime = timeFormat.format(c2.getTimeInMillis());
                 /*  databasehandler = new Video_DataBase_Helper(getContext());
                    databasehandler.getWritableDatabase();*/

                    //video details insert from here...
                    databasehandler.insertVideoData(Ad_Play, Store_id, Store_Media_Id, videoname, startdate, enddate, starttime, endtime);

                    uploadVideoDetails();


                    videoIncrement++;
                    if (videoIncrement < main_ad_video.size()) {
                        mVideoView = (VideoView) findViewById(R.id.SecScrVV1);
                        mVideoView.setVideoURI(main_ad_video.get(videoIncrement));
                        mVideoView.start();
                        Calendar c3 = Calendar.getInstance();
                        startdate = s.toString();
                        Ad_Play = Addatetime.format(c3.getTimeInMillis());
                        starttime = timeFormat.format(c3.getTime());
                        enddate = s.toString();
                        endtime = timeFormat.format(c3.getTime());
                        validatevideoname();


                    } else {
                        videoIncrement = 0;
                        mVideoView = (VideoView) findViewById(R.id.SecScrVV1);
                        mVideoView.setVideoURI(main_ad_video.get(videoIncrement));
                        mVideoView.start();
                        Calendar c4 = Calendar.getInstance();
                        startdate = s.toString();
                        Ad_Play = Addatetime.format(c4.getTimeInMillis());
                        starttime = timeFormat.format(c4.getTime());
                        enddate = s.toString();
                        endtime = timeFormat.format(c4.getTime());
                    }
                }
            });
        }
    }



    public void playNewStream(){
        databasehandler = new Video_DataBase_Helper(getContext());
        //all retail_ad_ticker call here and set here......................
        final ArrayList array_list = databasehandler.getAllAdTicker();
   /* Log.e("###############", array_list.toString().replace("[", "").replace("]", "").replace(",", "----"));*/

        ScrollTextView scrolltext = (ScrollTextView)findViewById(R.id.SecScr_scrolling_text_id);
        scrolltext.setRndDuration(35000);
        String scrolling= array_list.toString().replace("[", "").replace("]", "").replace(",", "  ||  ");
        scrolltext.setText(scrolling);

        scrolltext.startScroll();





    }

    public void sponcerlogo(){

       databasehandler = new Video_DataBase_Helper(getContext()); //Blinking Image code Here.....................................
        imageView = (ImageView) findViewById(R.id.SecScr_blinking_image_id);
        File imagedir = new File(Environment.getExternalStorageDirectory() + "/Retail_Street_Image");
        Log.e("####******########", imagedir.toString());
        imagedir.mkdir();
        File imageList[] = imagedir.listFiles();
        Bitmap b = BitmapFactory.decodeFile(imageList[imageIncrement].getAbsolutePath());
        imageView.setImageBitmap(b);
        imageView = (ImageView) findViewById(R.id.SecScr_blinking_image_id);


    }

    public void scrolllogos() {

        final RemoteActivity rv = new RemoteActivity();
        hsv1 = (HorizontalScrollView) findViewById(R.id.SecScr_horiztonal_scrollview_id);
        ll1 = (LinearLayout) findViewById(R.id.SecScr_horiztonal_outer_layout_id);
        hsv1.setHorizontalScrollBarEnabled(false);

        ViewTreeObserver viewTreeObserver = ll1.getViewTreeObserver();

        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll1.getViewTreeObserver().removeOnGlobalLayoutListener(this);


                rv. getScrollMaxAmount();
                rv. startAutoScrolling();
                LockScroll();

            }
        });

    }

    /*for Locking Scroll upper side company images*/
    public void LockScroll() {
        ((LockableScrollView) findViewById(R.id.SecScr_horiztonal_scrollview_id)).setScrollingEnabled(true);
    }


    public void customerId(){
       databasehandler = new Video_DataBase_Helper(getContext());


        //all retail_cust_id call here and set here......................
        final ArrayList array_list_custid = databasehandler.getAllCust_Id();
        Log.e("###############", array_list_custid.toString());
        Cust_id=array_list_custid.get(1).toString();
        customerId=(TextView)findViewById(R.id.SecScr_show_customer_id);
        customerId.setText(Cust_id.toString().replace("[", "").replace("]", "").replace(",", ""));
    }


    public void billingItemList(){


    }

    OnAudioFocusChangeListener afChangeListener = new OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                mAudManager.abandonAudioFocus(afChangeListener);
            }
        }
    };

    @Override
    protected void onStart()
    {
        super.onStart();
        playVideo();
        playNewStream();
        scrolllogos();
        //AddSalesProducttoList();


    }


    private void uploadVideoDetails() {

        class Update extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getContext(), "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //  loading.dismiss();
                // Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();



                hashMap.put(Config.VIDEODATA_ADPLAY,Ad_Play);
                hashMap.put(Config.VIDEODATA_STOREID,Store_id);
                hashMap.put(Config.VIDEODATA_MEDIAID,Store_Media_Id);
                hashMap.put(Config.VIDEODATA_VIDEONAME,videoname);
                hashMap.put(Config.VIDEODATA_STARTDATE,startdate);
                hashMap.put(Config.VIDEODATA_ENDDATE,enddate);
                hashMap.put(Config.VIDEODATA_STARTTIME,starttime);
                hashMap.put(Config.VIDEODATA_ENDTIME,endtime);


                JSONParserSync jsonParser = new JSONParserSync();
                String s = String.valueOf(jsonParser.sendPostRequest(Config.Link_Upload, hashMap));

                return s;
            }
        }

        Update update = new Update();
        update.execute();
    }

    public void validatevideoname() {

        File videodir = new File(Environment.getExternalStorageDirectory() + "/1464772267" + "/MainAd");

        // File videodir = new File(Environment.getExternalStorageDirectory() + "/99Retail");

        Log.e("####******########", videodir.toString());
        final File[] filest = videodir.listFiles();
        if (videodir.exists() && videodir.isDirectory()) {
            final File[] files = videodir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) {
                        if (file.isDirectory()) {  // it is a folder...
                        } else {  // it is a file...
                            main_ad_video.add(Uri.fromFile(file));
                        }
                    }
                }
            }

        }

        try
        {
            String uriString = String.valueOf(main_ad_video.get(videoIncrement));
            URI uri = new URI(uriString);

            URL videoUrl = uri.toURL();
            File tempFile = new File(videoUrl.getFile());
            videoname = tempFile.getName();
            Log.e("^^^",videoname);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        if(videoname.startsWith("A")){
            //  Toast.makeText(getApplicationContext(),"video's start with A",Toast.LENGTH_LONG).show();
            mVideoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    Addatetimeforclick = new SimpleDateFormat("yyyyMMDDHHmmss");

                    final Calendar cal1233 = Calendar.getInstance();

                    Ad_Play_Click = Addatetimeforclick.format(cal1233.getTime());

                    Log.e("%%%%%", Ad_Play_Click.toString());

                    Store_Media_Id = Str_store_media_id;

                   /* CustomDialogClass cdd = new CustomDialogClass(RemoteVideoPresentation.this);
                    cdd.show();*/

                    clickcount = clickcount + 1;
                    if (clickcount == 1) {

                        //  Toast.makeText(getApplicationContext(), "Click : 1", Toast.LENGTH_LONG).show();

                    } else {
                        // Toast.makeText(getApplicationContext(), "Click :" + clickcount, Toast.LENGTH_LONG).show();
                        touch_Count = String.valueOf(clickcount);
                    }


                    return false;
                }
            });
        }


        else {


            mVideoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // do nothing here......
                    return true;
                }
            });


        }



    }




    public class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        public Button customer_ok, customer_cal;
        public EditText Edit_mobile_no;

        public CustomDialogClass(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_popup);

            customer_ok = (Button) findViewById(R.id.button_cutomer_ok);
            customer_cal = (Button) findViewById(R.id.button_customer_cancel);
            Edit_mobile_no = (EditText) findViewById(R.id.edit_customermobile);


            customer_ok.setOnClickListener(this);
            customer_cal.setOnClickListener(this);




        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_cutomer_ok:
                   databasehandler = new Video_DataBase_Helper(getContext());

                    if (Edit_mobile_no.getText().toString().length() > 10 || Edit_mobile_no.getText().toString().length() != 10) {
                        Edit_mobile_no.setError("Invalid Number");
                        Edit_mobile_no.setInputType(InputType.TYPE_CLASS_PHONE);


                        KeyListener keyListener = DigitsKeyListener.getInstance("1234567890");
                        Edit_mobile_no.setKeyListener(keyListener);
                        return;
                    }

                    if (helper.CheckIsDataAlreadyInDBorNot(Edit_mobile_no.getText().toString()))

                    {
                        Toast toast1 = Toast.makeText(getContext(), "MOBILE NUMBER ALREADY REGISTERED", Toast.LENGTH_SHORT);
                        toast1.show();
                        return;
                    } else if (databasehandler.InsertdataintoRetail_click(Edit_mobile_no.getText().toString(), Str_store_media_id, Ad_Play, touch_Count))

                    {
                        CustomerMobNumber=Edit_mobile_no.getText().toString();
                        Toast toast = Toast.makeText(getContext(), "Thank You", Toast.LENGTH_SHORT);
                        toast.show();
                        uploadMediaClickDetails();
                    }

                    dismiss();

                    break;
                case R.id.button_customer_cancel:
                    dismiss();
                    break;
                default:
                    break;
            }
            dismiss();
        }
    }


    private void uploadMediaClickDetails() {


        class Update extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // loading = ProgressDialog.show(getContext(), "Click Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                // loading.dismiss();
                // Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(Config.MEDIACLICK_ADPLAYID,Ad_Play_Click);

                hashMap.put(Config.MEDIACLICK_STOREMEDIAID,Store_Media_Id);
                hashMap.put(Config.MEDIACLICK_MOBILENUMBER,CustomerMobNumber);
                hashMap.put(Config.MEDIACLICK_NUMBEROFCLICK,touch_Count);
                Log.e("mediaclick data",hashMap.toString());

                JSONParserSync jsonParser = new JSONParserSync();
                String s = String.valueOf(jsonParser.sendPostRequest(Config.Link_Upload_Media_Click, hashMap));

                return s;
            }
        }

        Update update = new Update();
        update.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mVideoView.stopPlayback();
        mAudManager.setParameters("bgm_state=false");
    }
    public static void AddSalesProducttoList(Sales result) {
        try {
            if (adapter == null) {
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                adapter = new SecondBillAdapter(mContext.getApplicationContext()
                        , android.R.layout.simple_dropdown_item_1line, new ArrayList<Sales>());
                listView.setAdapter(adapter);
                ScrollviewSales.getListViewSize(listView);
            }
            int pos = adapter.addProductToList(result);
            Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
            adapter.notifyDataSetChanged();
            setSummaryRow();
            listView.smoothScrollToPosition(pos);
        }catch(Exception e){}
    }
    public static void setSummaryRow() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = adapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        netpayable.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + GrandTotal);
    }



    public static void deleteSalesProductfromList(int result) {
        try {
            if (mDisplay != null) {

                int pos = adapter.removeProductFromList(result);
                Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                adapter.notifyDataSetChanged();
                setSummaryRow();
                listView.smoothScrollToPosition(pos);

            } else {
            }
        }catch (Exception e){}
    }
    public static void updateQuantity(int position,int quan) {
        try {
            if (mDisplay != null) {

                int pos = adapter.updateQuantityAtPosition(position, quan);

                adapter.notifyDataSetChanged();
                setSummaryRow();
                listView.smoothScrollToPosition(pos);
            } else {
            }
        }catch (Exception e){}
    }



    public static void AddSalesReturnProducttoList(SalesreturndetailWithoutPo result) {
        try {
            if (mDisplay != null) {
                if (secondSalesReturnAdapterWithotInvoiceNumber == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null  Creating.....");
                    secondSalesReturnAdapterWithotInvoiceNumber = new SecondSalesReturnAdapterWithotInvoiceNumber(mContext.getApplicationContext()
                            , android.R.layout.simple_dropdown_item_1line, new ArrayList<SalesreturndetailWithoutPo>());
                    listView.setAdapter(secondSalesReturnAdapterWithotInvoiceNumber);
                    ScrollviewSales.getListViewSize(listView);
                }
                int pos = secondSalesReturnAdapterWithotInvoiceNumber.addProductToList(result);
                Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
                secondSalesReturnAdapterWithotInvoiceNumber.notifyDataSetChanged();
                setSummaryRowForReturnwithoutpo();
                listView.smoothScrollToPosition(pos);
            } else {
            }
        }catch(Exception e){}
    }
    public static void AddSalesReturnProducttoListWithPo(ArrayList<Salesreturndetail>demo) {
        try {
            if (mDisplay != null) {
                if (salesReturnAdapter == null) {
                    Log.e("&&&&&&&&", "Product Adapter was null  Creating.....");
                    salesReturnAdapter = new SecondSalesReturnAdapter(mContext.getApplicationContext()
                            , android.R.layout.simple_dropdown_item_1line, demo);
                    listView.setAdapter(salesReturnAdapter);
                }

                salesReturnAdapter.notifyDataSetChanged();
                setSummaryRowForReturn();

            } else {
            }
        }catch(Exception e){}
    }
    public static void setSummaryRowForReturn() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = salesReturnAdapter.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + GrandTotal);
    }

    public static void setSummaryRowForReturnwithoutpo() {
        DecimalFormat f = new DecimalFormat("##.00");
        float Getval = secondSalesReturnAdapterWithotInvoiceNumber.getGrandTotal();
        Log.d("&&&&&&&&", "" + Getval);
        String GrandVal = f.format(Getval);
        GrandTotal.setText(GrandVal);
        Log.d("@@@@@@@@@@", "" + GrandTotal);
    }
    public static void deleteSalesReturnProductfromListWithPo(int result) {
        try {

            salesReturnAdapter.removeProductFromList(result);
            Log.i("&&&&&&&&", "Deleting " + result + " to Product List..");
            salesReturnAdapter.notifyDataSetChanged();
            setSummaryRowForReturn();
        }catch (Exception e){}

    }



    public static void deleteSalesReturnProductfromList(int result) {
        try {

            int pos = secondSalesReturnAdapterWithotInvoiceNumber.removeProductFromList(result);
            Log.i("&&&&&&&&", "Deleting " + result + " to Product List..");
            secondSalesReturnAdapterWithotInvoiceNumber.notifyDataSetChanged();
            setSummaryRowForReturnwithoutpo();
            listView.smoothScrollToPosition(pos);
        }catch(Exception e){}
    }
    public static void updateReturnQuantity(int position,int quan) {
        try {

            int pos = secondSalesReturnAdapterWithotInvoiceNumber.updateQuantityAtPosition(position, quan);

            secondSalesReturnAdapterWithotInvoiceNumber.notifyDataSetChanged();
            setSummaryRowForReturnwithoutpo();
            listView.smoothScrollToPosition(pos);
        }catch (Exception e){}
    }

    public  class RemoteActivity extends Activity {

        private  Timer scrollTimer = null;
        private TimerTask scrollerSchedule;
        private int scrollPos = 0;
        private int scrollMax;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.second_screen);


        }
  /*      public  void AddSalesProducttoList(Sales result) {



            if (adapter == null) {
                Log.e("&&&&&&&&", "Product Adapter was null and HENCE Creating.....");
                adapter = new SecondBillAdapter(RemoteActivity.this, android.R.layout.simple_dropdown_item_1line, new ArrayList<Sales>());
                listView.setAdapter(adapter);


            }
            int pos = adapter.addProductToList(result);
            Log.i("&&&&&&&&", "Adding " + result + " to Product List..");
            adapter.notifyDataSetChanged();

            listView.smoothScrollToPosition(pos);
        }
*/


        public void startAutoScrolling() {
            if (scrollTimer == null) {
                scrollTimer = new Timer();
                final Runnable Timer_Tick = new Runnable() {
                    public void run() {
                        moveScrollView();
                        // hsv1.smoothScrollTo(ll1.getChildAt(11).getLeft(), 0);

                    }
                };

                if (scrollerSchedule != null) {
                    scrollerSchedule.cancel();
                    scrollerSchedule = null;
                }
                scrollerSchedule = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(Timer_Tick);
                    }
                };

                scrollTimer.schedule(scrollerSchedule, 10, 10);
            }
        }


        public void moveScrollView() {
            scrollPos = (int) (hsv1.getScrollX() + 1.0);
            if (scrollPos >= scrollMax) {
                scrollPos = 0;

            }
            hsv1.scrollTo(scrollPos, 0);


        }


        public void stopAutoScrolling() {
            if (scrollTimer != null) {
                scrollTimer.cancel();
                scrollTimer = null;
            }
        }

        public void getScrollMaxAmount() {
            int actualWidth = (ll1.getMeasuredWidth() - 2048);
            scrollMax = actualWidth;
        }


    }}