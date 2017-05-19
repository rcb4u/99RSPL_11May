
        package com.RSPL.POS;

        import android.app.ActionBar;
        import android.app.Activity;
        import android.app.AlertDialog;
        import android.app.ProgressDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.graphics.drawable.ColorDrawable;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.v4.view.MenuItemCompat;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.animation.AlphaAnimation;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.Spinner;
        import android.widget.TextView;
        import android.widget.Toast;

        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.InputStreamReader;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.util.HashMap;
        import java.util.Locale;

        import Config.Config;
        import JSON.JSONParserSync;
        import Pojo.Decimal;
        import Pojo.Registeremployeesmodel;


        public class ActivityMaintain extends Activity {
    ActionBar actionBar;
    public static final String GCM_PROJECT_ID = "407176891585";//only if you need GCM notifications
    Bundle syncDataBundle = null;
    private boolean syncInProgress = false;
    private boolean didSyncSucceed = false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    String store_id,Ticket_id;
    String result;
    DBhelper helper;
    public static String username = null;
            Button btnlock,btnunlock;

            String iteam;
            TextView user2;

    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_MESSAGE = "message";
    String retail_maintenance_op = "sh /sdcard/maintenance.sh";
    String superuser ="su";
    String backroundcolour,colorchange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.loadLibrary("sqliteX");
        setContentView(R.layout.activity_activity_maintain);
        actionBar=getActionBar();
      //  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ff9033")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setIcon(R.drawable.w);

        helper=new DBhelper(ActivityMaintain.this);
        Decimal valuetextsize = helper.getStoreprice();
        String textsize=         valuetextsize.getHoldpo();
        backroundcolour=  valuetextsize.getColorbackround();

        btnlock = (Button) findViewById(R.id.lock);
        btnunlock = (Button) findViewById(R.id.unlock);


        btnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideNav();

            }
        });

        btnunlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityMaintain.this,ActivitySystemValidate.class);
                startActivity(intent);

            }
        });




        if(backroundcolour.matches("Orange")){

            colorchange="#ff9033";
        }
        if(backroundcolour.matches("Orange Dark")){

            colorchange="#EE782D";

            //    d=Color.BLUE;

        }
        if(backroundcolour.matches("Orange Lite")){

            colorchange="#FFA500";

        }
        if(backroundcolour.matches("Blue")){

            colorchange= "#357EC7";

        }
        if(backroundcolour.matches("Grey")) {

            colorchange = "#E1E1E1";
        }
        LinearLayout layout=(LinearLayout)findViewById(R.id.layout_color);
        layout.setBackgroundColor(Color.parseColor(colorchange));

        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorchange)));



        username =  login.b.getString("Pos_User");

    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void SubBack(View view)
    {
        Intent intent = new Intent(this,Activity_masterScreen1.class);
        startActivity(intent);
    }

/*
    public void SubInsertData(View view)
    {
        String dbName = "/data/data/" + getClass().getPackage().getName() + "/sync.bdb.db";
        DBhelper helper = new DBhelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
*/
/*
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        Date date = new Date();
     *//*

        String result=Long.toString(value);

        Toast.makeText(getApplicationContext(), "" + value, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"before",Toast.LENGTH_LONG).show();
        db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('" + result + "','open','47','','NetworkingIssues','veryhigh','posgroup','99RS','NOCOMMENT')");
        Toast.makeText(getApplicationContext(),"After",Toast.LENGTH_LONG).show();
        db.close();
    }

*/

    public void NetworkingIssues(View view){
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);

        DBhelper helper = new DBhelper(this);
        Button network = (Button)findViewById(R.id.Networking_Issues);

        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        network.setFocusable(true);
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Networking Issues','Very high','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");

        Updatenetworkingissue();


        Toast.makeText(getApplicationContext(),"NETWORK ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);
        db.close();

    }

    public void Masterdatasupport(View view){


        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        DBhelper helper = new DBhelper(this);
        view.startAnimation(Buttonok);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Master Data Support','Very high','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"MASTER DATA SUPPORT ISSUE RAISED",Toast.LENGTH_SHORT).show();


        Updatemasterdatasupport();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();
    }

    public  void SystemMaintainancehelpdialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        // alertDialog.setTitle("Confirm Delete...");
        alertDialog.setIcon(R.drawable.finallogo_small);
        alertDialog.setTitle("     SYSTEM MAINTENANCE REPORT AN ISSUE  ");
        alertDialog.setMessage("System Maintenance: Report an Issue\n" +
                "\n" +
                "The user selects one of the options and a ticket numebr gets generated for the issue.\n" +
                "\n " +

                "\n");
        alertDialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                dialog.dismiss();
            }
        });


        alertDialog.show();
    }



    public void Printernotworking(View view){

        DBhelper helper = new DBhelper(this);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Printer Not Working','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"PRINTER ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Updateprinternotworking();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);


    }

    public void Scannernotworking(View view){

        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Scanner Not Working','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SCANNER ISSUE RAISED",Toast.LENGTH_SHORT).show();
        UpdateScannernotworking();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Simcardnotworking(View view){

        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Simcard Not Working','Veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SIM CARD ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Updatesimcardnotworking();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }


    public void Walletnotworking(View view){

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Wallet Not Working','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"WALLET ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        Updatewalletnotworking();
        ;

        startActivity(intent);

        db.close();

    }

    public void Purchaseprocess(View view){

        DBhelper helper = new DBhelper(this);
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Purchase Process','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"PURCHASE PROCESS ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Updatepurchaseprocess();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Goodsreceipt(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Goods Receipt','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"GOODS RECEIPT ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Updategoodsreceipt();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();
    }


    public void Invoiceprocess(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_SHORT).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Invoice Process','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"INVOICE PROCESS ISSUE RAISED",Toast.LENGTH_SHORT).show();
        Updateinvoiceprocess();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Salesprocess(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Sales Process','Veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SALES PROCESS ISSUE RAISED",Toast.LENGTH_LONG).show();
        Updatesaleprocess();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();


    }

    public void Salesreturn(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();
        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Sales Return','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"SALES RETURN ISSUE RAISED",Toast.LENGTH_LONG).show();
        Updatesalesreturn();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        startActivity(intent);

        db.close();

    }

    public void Reportingerror(View view)
    {
        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);

        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('" + result + "','Open','133','','Reporting Error','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"REPORTING ERROR ISSUE RAISED",Toast.LENGTH_LONG).show();

        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        UpdateReportingerror();
        startActivity(intent);

        db.close();


    }


    public void Paymentrelated(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Payment Related','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"PAYMENT RELATED ISSUE RAISED",Toast.LENGTH_LONG).show();
        ;

        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        Updatepaymentrelated();
        startActivity(intent);

        db.close();


    }

    public void Manufacturingpayment(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc," +
                "Support_Priority,Team_Group," +
                "Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Manufacturing Payment','High','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"MANUFACTURING PAYMENT ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        UpdateManufacture();
        ;

        startActivity(intent);

        db.close();


    }

    public void Anyothers(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_Store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Any Others','Medium','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"ANY OTHERS ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);
        Updateanyother();
        ;

        startActivity(intent);

        db.close();


    }


    public void Hardwarecrash(View view)
    {

        final AlphaAnimation Buttonok = new AlphaAnimation(1F, 0.1F);
        view.startAnimation(Buttonok);
        DBhelper helper = new DBhelper(this);
        org.sqlite.database.sqlite.SQLiteDatabase db = helper.getWritableDatabase();

        long value= System.currentTimeMillis();
        result=Long.toString(value);
        Toast.makeText(getApplicationContext(),""+value,Toast.LENGTH_LONG).show();
        db.execSQL("insert into retail_store_maint(Ticket_Id,Support_Ticket_Status,Store_Id,Last_Modified,Subject_Desc," +
                "Support_Priority,Team_Group,Team_Member,Comment,Date,Pos_User)VALUES('"+result+"','Open','133','','Hardware Crash'" +
                ",'Veryhigh','POSGROUP','99RS','NOCOMMENT','"+getDateTime()+"','"+user2.getText().toString()+"')");
        // db.execSQL("INSERT INTO retail_maintenance(Ticket_id,Support_Ticket_Status,Str_Id,Last_Modified,Subject_Desc,Support_Priority,Team_Group,Team_Member,Comment)VALUES('"+result+"','open','47','','NetworkingIssues','veryhigh','posgroup','OURTEAM','NOCOMMENT')");
        db.execSQL("update retail_store_maint set Store_Id =(select Store_Id from retail_store)");
        Toast.makeText(getApplicationContext(),"HARDWARE CRASH ISSUE RAISED",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), Activity_masterScreen1.class);

        Updatehardwarecrash();


        startActivity(intent);

        db.close();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_master_screen1, menu);

        MenuItem item = menu.findItem(R.id.spinner_user);


        final MenuItem item2 = menu.findItem(R.id.TextView);

        final Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        user2 = (TextView) MenuItemCompat.getActionView(item2);


        // user2.setText(username);



        helper=new DBhelper(ActivityMaintain.this);
        ArrayList<Registeremployeesmodel> user_name= helper.getusername();

        ArrayAdapter<Registeremployeesmodel > stringArrayAdapter =
                new ArrayAdapter<Registeremployeesmodel>(ActivityMaintain.this, android.R.layout.simple_spinner_dropdown_item,user_name);

        spinner.setAdapter(stringArrayAdapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   Log.d("slected item   :  ", iteam);

                if (!user2.getText().toString().trim().isEmpty()) {
                    iteam = spinner.getSelectedItem().toString();


//                    user2.setText("");
                    user2.setText(iteam );
                }

                else {

                    user2.setText(username);
                }



            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        return true;


    }



            @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (item.getItemId()) {

            case R.id.action_settings:

                Intent i=new Intent(ActivityMaintain.this,ActivityMainMaintainence.class);
                startActivity(i);
                return true;
            case R.id.action_settinginfo:
                ShowIncentive showIncentive = new ShowIncentive(ActivityMaintain.this);
                showIncentive.show();
                return true;

            case R.id.action_settinghelp:
                SystemMaintainancehelpdialog();
                return true;
            /*case R.id.action_settingpurchase:
                Intent p=new Intent(ActivityMaintain.this,PurchaseActivity.class);
                startActivity(p);
                return true;*/

            case R.id.action_Masterscn1:
                Intent p = new Intent(ActivityMaintain.this, Activity_masterScreen1.class);
                startActivity(p);
                return true;

            case R.id.action_settinginv:
                Intent in=new Intent(ActivityMaintain.this,InventoryTotalActivity.class);
                startActivity(in);
                return true;

            case R.id.action_settingsales:
                Intent s=new Intent(ActivityMaintain.this,ActivitySalesbill.class);
                startActivity(s);
                return true;

            case R.id.action_logout:
                Logout();

                return true;



        }

        return super.onOptionsItemSelected(item);
    }

            public void Logout()
            {
                Intent intent = new Intent(ActivityMaintain.this ,login.class);
                startActivity(intent);
            }





//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     1.networking issues    *****************************************************

    public void Updatenetworkingissue() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;
            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put(Config.Retail_Maintain_store_id, "1465804506");
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Networking Issues");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest(Config.UPDATE_NETWORKING_ISSUE, hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {

                        Log.d("Successfully Login!", s.toString());

                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }




    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     2.Master data support issues    *****************************************************

    public void Updatemasterdatasupport() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;
            @Override
            protected String doInBackground(Void... params) {
                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Master Data Support");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     3.printer not working issues    *****************************************************

    public void Updateprinternotworking() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Printer Not Working");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime());
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     4.Scanner not working issues    *****************************************************

    public void UpdateScannernotworking() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Scanner Not Working");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //    Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     5.Simcardnotworking issues    *****************************************************

    public void Updatesimcardnotworking() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Simcard Not Working");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     6.Walletnotworking issues    *****************************************************

    public void Updatewalletnotworking() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Wallet Not Working");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Medium");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //    Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     7.purchase process issues    *****************************************************

    public void Updatepurchaseprocess() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Purchase Process");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //   Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }





    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     8.Goods receipt issues    *****************************************************

    public void Updategoodsreceipt() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Goods Receipt");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //   Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     9.Invoice process issues    *****************************************************

    public void Updateinvoiceprocess() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Invoice Process");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //    Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     10.Sales process issues    *****************************************************

    public void Updatesaleprocess() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Sales Process");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     11.Salesreturn issues    *****************************************************

    public void Updatesalesreturn() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Sales Return");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );

                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     12.reporting error issues    *****************************************************

    public void UpdateReportingerror() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Reporting Error");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Medium");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //    Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }


    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     13.paymentrelater issues    *****************************************************

    public void Updatepaymentrelated() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Payment Related");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://sysmainissue.eu-gb.mybluemix.net/NetworkingIssue.jsp", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


//                        return s.getString(TAG_MESSAGE);
                    } else {

                        //                       return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }

    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     14.Manufacture issues    *****************************************************

    public void UpdateManufacture() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Manufacturing Payment");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "High");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://sysmainissue.eu-gb.mybluemix.net/NetworkingIssue.jsp", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        //  return s.getString(TAG_MESSAGE);
                    } else {

                        //  return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //    Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }





    //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     15.Any other issues    *****************************************************

    public void Updateanyother() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;

            @Override
            protected String doInBackground(Void... params) {
                try{

                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Any Others");
                    hashMap.put(Config.Retail_Maintain_Support_priority, "Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://52.76.28.14/MaintenenceIssue/NetworkingIssue.php", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


                        return s.getString(TAG_MESSAGE);
                    } else {

                        return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                //  Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }





//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%     16.Hardware Crash issues    *****************************************************

    public void Updatehardwarecrash() {

        final String MaintainticketidID =result ;


        helper=new DBhelper(this);
        PersistenceManager.saveStoreId(ActivityMaintain.this, helper.getStoreid().toString().replace("[", "").replace("]", ""));
        store_id = PersistenceManager.getStoreId(ActivityMaintain.this);
        Ticket_id = PersistenceManager.getStoreId(ActivityMaintain.this);


        class UpdateVendorPayment extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;
            int success;
            DBhelper mydb;

            @Override
            protected String doInBackground(Void... params) {

                try{
                    HashMap<String, String> hashMap = new HashMap<>();


                    hashMap.put(Config.Retail_Maintain_store_id, store_id);
                    hashMap.put(Config.Retail_Maintain_ticketid, MaintainticketidID);
                    hashMap.put(Config.Retail_Maintain_ticket_support, "Open");
                    hashMap.put(Config.Retail_Maintain_Subject_desc, "Hardware Crash");
                    hashMap.put(Config.Retail_Maintain_Support_priority,"Veryhigh");
                    hashMap.put(Config.Retail_Maintain_Teammember, "99RS");
                    hashMap.put(Config.Retail_Maintain_Teamgroup, "POSGROUP");
                    hashMap.put(Config.Retail_Maintain_Comment, "NOCOMMENT");
                    hashMap.put(Config.Retail_Maintain_Date,getDateTime() );
                    hashMap.put(Config.Retail_Pos_user,username );


                    JSONParserSync rh = new JSONParserSync();
                    JSONObject s = rh.sendPostRequest("http://sysmainissue.eu-gb.mybluemix.net/NetworkingIssue.jsp", hashMap);
                    Log.d("Login attempt", s.toString());

                    // Success tag for json
                    success = s.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        Log.d("Successfully Login!", s.toString());


//                        return s.getString(TAG_MESSAGE);
                    } else {

                        //   return s.getString(TAG_MESSAGE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override

            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override

            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // Toast.makeText(ActivityMaintain.this, s, Toast.LENGTH_LONG).show();
            }

        }

        UpdateVendorPayment updateVendorPayment = new UpdateVendorPayment();
        updateVendorPayment.execute();
    }



            public void hideNav()
            {
                try
                {
                    Process p;
                    p = Runtime.getRuntime().exec("su");
                    // Attempt to write a file to a root-only
                    DataOutputStream os = new DataOutputStream(p.getOutputStream());
                    os.writeBytes("service call activity 42 s16 com.android.systemui\n");
                    // Close the terminal
                    os.writeBytes("exit\n");
                    os.flush();
                    p.waitFor();
                }
                catch (Exception e)
                {
                }
            }






}



