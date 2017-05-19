package Pojo;

import android.net.Uri;
import android.provider.BaseColumns;

import com.RSPL.POS.DBhelper;

/**
 * Created by rspl-gourav on 28/10/16.
 */
public class passwordpojo  {

    public static DBhelper db;


    public static final String AUTHORITY = "com.linkwithweb.providers.MyActivity";

    // BaseColumn contains _id.
    public static final class User implements BaseColumns {

        public static final Uri CONTENT_URI = Uri
                .parse("content://com.linkwithweb.providers.MyActivity");

        // Table column
        public static final String STORE = "STORE_ID";


    }

}
