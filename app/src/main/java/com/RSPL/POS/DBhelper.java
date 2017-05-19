package com.RSPL.POS;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import org.sqlite.database.sqlite.SQLiteException;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteOpenHelper;

import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.sqlite.database.sqlite.SQLiteStatement;

import Pojo.AdTickerReportModel;
import Pojo.BillLevelModel;
import Pojo.BlinkingLogoReportModel;
import Pojo.BlinkingModel;
import Pojo.Companylistmodel;
import Pojo.CreditCustomer;
import Pojo.CreditNote;
import Pojo.Customer;
import Pojo.CustomerRejectModel;
import Pojo.Decimal;
import Pojo.DoctorPojo;
import Pojo.ExpiryProductStockModel;
import Pojo.FragmentSalesLostReportPOJO;
import Pojo.InventoryReportModel;
import Pojo.InventoryStockadjustmentmodel;
import Pojo.InventoryTotalProductModel;
import Pojo.Inventorygrnmodel;
import Pojo.Inventorymodel;
import Pojo.Inventoryproductmodel;
import Pojo.ListModel;
import Pojo.LocalProduct;
import Pojo.Loginpo;
import Pojo.LoyalityModel;
import Pojo.Mfglistmodel;
import Pojo.OverallSaleReportModel;
import Pojo.PayByCashVendorPaymentModel;
import Pojo.PayByChequeVendorPaymentModel;
import Pojo.Product;
import Pojo.ProductAuto;
import Pojo.PurchaseInvoiceDropDownModel;
import Pojo.PurchaseProductModelwithpo;
import Pojo.PurchaseProductReportModel;
import Pojo.Registeremployeesmodel;
import Pojo.ReportDistributorModel;
import Pojo.ReportLocalProductCpgModel;
import Pojo.ReportLocalProductPharmaModel;
import Pojo.ReportProductCpgModel;
import Pojo.ReportProductPharmaModel;
import Pojo.ReportVendorModel;
import Pojo.ReportVendorReturnModel;
import Pojo.SaleReportModel;
import Pojo.SalesReturnReportModel;
import Pojo.Salesreturndetail;
import Pojo.SalesreturndetailWithoutPo;
import Pojo.Settlecustmodel;
import Pojo.ShowModel;
import Pojo.StockInventoryReportModel;
import Pojo.StoreMainModel;
import Pojo.Topfullproductmodel;
import Pojo.VendorModel;
import Pojo.PurchaseProductModel;
import Pojo.RetailcardDefineModel;
import Pojo.RuleDefinationModel;
import Pojo.Sales;
import Pojo.Store;
import Pojo.Tax;
import Pojo.Vendor;
import Pojo.VendorRejectModel;
import Pojo.VendorReportModel;
import Pojo.VendorReturnModel;
import Pojo.Visibility;
import Pojo.displaypojo;
import Pojo.line_item_Product_Model;
import Pojo.localvendor;
import Pojo.lostsale;
import Pojo.passwordpojo;

import static com.RSPL.POS.login.MESSAGE_STATE_CHANGE;
import static com.RSPL.POS.login.setboolean;

public class DBhelper  extends SQLiteOpenHelper {

	private static String DB_PATH = "/data/data/" + Activity_Installation.class.getPackage().getName() + "/databases/";
	private static final String DATABASE_NAME = "Db";
	private static final int DATABASE_VERSION= 17;
	private SQLiteDatabase myDataBase;
	private final Context mycontext;

	private static final boolean D = true;
	Boolean notconnected=true;
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	float newstock;

	public static final String TABLE_NAME = "retail_cust";//customer table name
	public static final String TABLE_NAME1 = "retail_vendor";//vendor table name
	public static final String TABLE_NAME2 = "retail_employees";//employee table name
	public static final String TABLE_NAME3 = "retail_store_prod_local";//local product table name
	public static final String TABLE_NAME4 = "retail_store_prod";//local product table name
	public static final String TABLE_NAME5 = "retail_store";//store table name
	public static final String TABLE_NAME6 = "retail_store_maint";//maintainence table name
	public static final String TABLE_NAME7 = "retail_ad_ticker";//tickek table name
	public static final String TABLE_NAME8 = "retail_tax";//tax table name
	private static final String TABLE_NAME10 = "retail_str_dstr";
	private static final String TABLE_NAME11 = "retail_str_po_detail";
	private static final String TABLE_NAME13 = "retail_store_prod_com";
	public static final String TABLE_NAME_STOCK_DELETE ="retail_str_stock_master";
	public static final String TABLE_NAME_STOCK_DELETE_GRN_MASTER ="retail_str_grn_master";
	public static final String DELETE_TRI_ID ="TRI_ID";



	public static final String DELETE_STOCK_BATCH = "BATCH_NO";
	public static final String STORE_ID_INVENTORY = "STORE_ID";//Store table name
	public static final String COLUMN_MASTER_VENDOR_DSTR_NAME="VEND_DSTR_NM";
	public static final String DISCOUNT_PERCENT="DISCOUNT_PERCENT";
	public static final String COLUMN_TICKET_ID = "TICKET_ID";//Column of retail_store_maint
	public static final String COLUMN_SUPPORT_TICKET_STATUS = "SUPPORT_TICKET_STATUS";//Column of retail_store_maint
	public static final String COLUMN_SUBJECT_DESC = "SUBJECT_DESC";//Column of retail_store_maint
	public static final String COLUMN_TEAM_MEMBER = "TEAM_MEMBER";//Column of retail_store_maint
	public static final String COLUMN_DATE = "DATE";//Column of retail_store_maint
	public static final String MRP_DECIMAL = "MRP_DECIMAL";//Store table name
	public static final String SPRICE_DECIMAL = "S_PRICE_DECIMAL";//Store table name
	public static final String PPRICE_DECIMAL = "P_PRICE_DECIMAL";
	public static final String HOLD_PO = "HOLD_PO";
	public static final String HOLD_INV = "HOLD_INV";
	public static final String HOLD_SALES = "HOLD_SALES";
	public static final String ROUND_OFF = "ROUND_OFF";
	public static final String PURCHASE_HOLD_NO = "PURCHASE_HOLD_NO";
	public static final String INVENTORY_HOLD_NO = "INV_HOLD_NO";
	public static final String STORE_ID = "STORE_ID";//Store table name
	public static final String STORE_NAME = "STR_NM";//Store table name
	public static final String COLUMN_BILL_NUMBER = "Tri_Id";

	public static final String STORE_EMAIL = "E_MAIL";//Store table name
	public static final String STORE_MOBILE = "TELE";//Store table name
	public static final String STORE_ZIP = "ZIP";//Store table name
	public static final String STORE_CONTACTNAME = "STR_CNTCT_NM";//Store table name
	public static final String STORE_CITY = "CTY";//Store table name
	public static final String STORE_COUNTRY = "STR_CTR";//Store table name
	public static final String STORE_ADDRESS1 = "ADD_1";//Store table name
	public static final String STORE_FOOTER = "FOOTER";//Store table name
	public static final String STORE_ALTER_MOBILE = "TELE_1";//Store table name

	public static final String PRODUCTID = "PROD_ID";//Column of product
	public static final String PRODUCTNAME = "PROD_NM";//Column of product
	public static final String USERNAME = "POS_USER";//Column of product
	public static final String PRODUCTSTOREID = "STORE_ID";//Column of product
	public static final String PRODUCTBARCODE = "BARCODE";//Column of product
	public static final String PRODUCTMRP = "MRP";//Column of product
	public static final String PRODUCTSELLING = "S_PRICE";//Column of product
	public static final String PRODUCTPURCHASE = "P_PRICE";//Column of product
	public static final String PRODUCTACTIVE = "ACTIVE";//Column of product
	public static final String PRODUCTTAXDESC = "TAX_DESC";//Column of product
	public static final String PRODUCTMANUF = "MFG";//Column of product
	public static final String PRODUCTINTERNET = "INTERNET_PRICE";//Column of product
	public static final String PRODUCTRELEVANT = "IS_PROD_REL_INT";//Column of product
	public static final String PRODUCTMARGIN = "PROFIT_MARGIN";//Column of product
	//********************************************************************************8
	public static final String PRODUCTMEASUREUNITINV = "SELLING_ORDER_UNIT";//Column of product
	public static final String PRODUCTBATCHINV = "BATCH_NO";//Column of product
	public static final String PRODUCTEXPDATEINV = "EXP_DATE";//Column of product
	public static final String PRODUCTSTOCK = "CON_MUL_QTY";//Column of product
	public static final String PRODUCTCONVERSION = "CONV_FACT";//Column of product
	public static final String PRODUCTINDUSTERY = "IND_NM";//Column of product
	public static final String PRODUCTFREEGOODS = "FREE_GOODS";//Column of product
	public static final String PRODUCTPRODUCTID = "PROD_ID";//Column of product
	public static final String PRODUCTPACKINGUNIT = "PURCH_ORDER_UNIT";//Column of product
	public static final String CUSTOMERNAME = "NAME";// Columnn of customers
	public static final String CUSTOMERMOBILENO = "MOBILE_NO";//Columnn of customers
	public static final String CUSTOMEREMAIL = "E_MAIL";//Columnn of customers
	public static final String CUSTOMERCREDIT = "CREDIT_CUST";
	public static final String CUSTOMERADDRESS="CUST_ADD";
	public static final String VENDORID = "DSTR_ID";//Columnn of vendors
	public static final String VENDORNAME = "DSTR_NM";//Columnn of vendors
	public static final String VENDORCONTACTNAME = "DSTR_CNTCT_NM";//Columnn of vendors
	public static final String VENDORADDRESS = "ADD_1";//Columnn of vendors
	public static final String VENDORCITY = "CITY";//Columnn of vendors
	public static final String VENDORCOUNTRY = "CTR_NM";//Columnn of vendors
	public static final String VENDORZIP = "ZIP";//Columnn of vendors
	public static final String VENDORTTELEPHONE = "TELE";//Columnn of vendors
	public static final String VENDORMOBILE = "MOBILE";//Columnn of vendors
	public static final String VENDORINVENTORY = "DSTR_INV";//Columnn of vendors
	public static final String VENDORACTIVE = "ACTIVE";//Columnn of vendors
	public static final String VENDOREmail = "EMAIL";//Columnn of vendors
	public static final String PRODUCTLOCALSTOREID = "STORE_ID";//Columnn of localproduct
	public static final String PRODUCTLOCALPRODUCTID = "PROD_ID";//Columnn of localproduct
	public static final String PRODUCTLOCALPRODUCTNAME = "PROD_NM";//Columnn of localproduct
	public static final String PRODUCTLOCALUSERNAME = "POS_USER";//Columnn of localproduct
	public static final String PRODUCTLOCALBARCODE = "BARCODE";//Columnn of localproduct
	public static final String PRODUCTLOCALMRP = "MRP";//Columnn of localproduct
	public static final String PRODUCTLOCALSELLING = "S_PRICE";//Columnn of localproduct
	public static final String PRODUCTLOCALPURCHASE = "P_PRICE";//Columnn of localproduct
	public static final String PRODUCTLOCALTAXID = "TAX_ID";//Columnn of localproduct
	public static final String PRODUCTLOCALACTIVE = "ACTIVE";//Columnn of localproduct
	public static final String PRODUCTLOCALMARGIN = "PROFIT_MARGIN";//Columnn of localproduct
	public static final String TAX_DESCRIPTION = "TAX_DESC";// Columnn of Tax
	public static final String TAX_RATE = "TAX_RATE";// Columnn of Tax
	public static final String LOCALVENDORID = "VEND_ID";// Columnn of localvendor
	public static final String LOCALVENDORNAME = "VEND_NM";// Columnn of localvendor
	public static final String LOCALUSERNAME = "POS_USER";// Columnn of localvendor
	public static final String LOCALVENDORCONTACT = "VEND_CNTCT_NM";// Columnn of localvendor
	public static final String LOCALVENDORADDRESS = "ADD_1";// Columnn of localvendor
	public static final String LOCALVENDORCITY = "CITY";// Columnn of localvendor
	public static final String LOCALVENDORCOUNTRY = "CTR_DESC";// Columnn of localvendor
	public static final String LOCALVENDORZIP = "ZIP";// Columnn of localvendor
	public static final String LOCALVENDORTELE = "TELE";// Columnn of localvendor
	public static final String LOCALVENDORMOBILE = "MOBILE";// Columnn of localvendor
	public static final String LOCALVENDORACTIVE = "ACTIVE";// Columnn of localvendor
	public static final String LOCALVENDORINVENTORY = "VEND_INV";// Columnn of localvendor
	public static final String STOREID = "STORE_ID";

	public static final String COLUMN_UOM = "UOM";
	public static final String COLUMN_PURCHASEDATE = "PURCHASEDATE";


	/**************************************************
	 * retail_str_stock_master
	 **************************************/
	public static final String COLUMN_PROD_ID = "PROD_ID";
	public static final String COLUMN_PROD_NAME = "PROD_NM";
	public static final String COLUMN_BATCH_NO = "BATCH_NO";
	public static final String COLUMN_EXP_DATE = "EXP_DATE";
	public static final String COLUMN_QUANTITY = "QTY";
	public static final String COLUMN_USER_NM = "POS_USER";
	public static final String COLUMN_LAST_MODFD = "LAST_MODIFIED";
	public static final String COLUMN_CONMUL_QUANTITY = "CON_MUL_QTY";
	public static final String COLUMN_REPRT_STOCK_NM = "VEND_NM";
	public static final String COLUMN_REPRT_STOCK_INVOICE = "GRN_ID";
	public static final String COLUMN_PURCHASE_PRICE = "P_PRICE";
	public static final String COLUMN_VENDOR_INVOICE_NO="VENDOR_INVOICE_NO";
	/****************************
	 * Get the values from retail_ad_ticker
	 *****************************************/
	public static final String COLUMN_AD_MAIN_ID = "AD_MAIN_ID";
	public static final String COLUMN_POS_USER = "POS_USER";
	public static final String COLUMN_AD_TEXT = "AD_TEXT";
	public static final String COLUMN_AD_START_DATE = "AD_STRT_DT";
	public static final String COLUMN_AD_END_DATE = "AD_END_DT";
	public static final String COLUMN_AD_CST_SLB1 = "AD_CST_SLB1";
	public static final String COLUMN_AD_CST_SLB2 = "AD_CST_SLB2";
	public static final String COLUMN_AD_CST_SLB3 = "AD_CST_SLB3";
	public static final String COLUMN_AD_STATUS = "STATUS";
	public static final String COLUMN_AD_ACTIVE = "ACTIVE";
	/****************************
	 * Get the values from retail_ad_blinkinglogo
	 *****************************************/
	public static final String COLUMN_Blinkinglogo_AD_MAIN_ID = "AD_MAIN_ID";
	public static final String COLUMN_Blinkinglogo_USER_NM = "POS_USER";
	public static final String COLUMN_Blinkinglogo_AD_DESC = "AD_DESC";
	public static final String COLUMN_Blinkinglogo_AD_START_DATE = "AD_STRT_DT";
	public static final String COLUMN_Blinkinglogo_AD_END_DATE = "AD_END_DT";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB1 = "AD_CST_SLB1";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB2 = "AD_CST_SLB2";
	public static final String COLUMN_Blinkinglogo_AD_CST_SLB3 = "AD_CST_SLB3";
	/*******************************
	 * Get the Values from retail_ad_Store_Main
	 **************************************************/
	public static final String COLUMN_StoreMain_AD_MAIN_ID = "AD_MAIN_ID";
	public static final String COLUMN_StoreMain_AD_DESC = "AD_DESC";
	public static final String COLUMN_StoreMain_USER_NM = "POS_USER";
	public static final String COLUMN_StoreMain_AD_START_DATE = "AD_STRT_DT";
	public static final String COLUMN_StoreMain_AD_END_DATE = "AD_END_DT";
	public static final String COLUMN_StoreMain_AD_CST_SLB1 = "AD_CST_SLB1";
	public static final String COLUMN_StoreMain_AD_CST_SLB2 = "AD_CST_SLB2";
	public static final String COLUMN_StoreMain_AD_CST_SLB3 = "AD_CST_SLB3";
	public static final String COLUMN_StoreMain_AD_STATUS = "STATUS";
	public static final String COLUMN_StoreMain_AD_ACTIVE = "ACTIVE";

	/***************************************************
	 * retail_str_vendor_detail_return
	 ******************************************/

	public static final String COLUMN_REPRTVENDR_NM = "VENDOR_NM";
	public static final String COLUMN_REPRTVENDR_USER = "POS_USER";
	public static final String COLUMN_REPRTPROD_NM = "PROD_NM";
	public static final String COLUMN_REPRTP_Price = "P_PRICE";
	public static final String COLUMN_REPRT_QTY = "QTY";
	public static final String COLUMN_REPRTVENDR_ID = "VENDOR_RETURN_ID";
	public static final String COLUMN_REPRT_UOM = "UOM";
	public static final String COLUMN_REPRTBATCH_NO = "BATCH_NO";
	public static final String COLUMN_REPRTEXP_DATE = "EXP_DATE";
	public static final String COLUMN_REPRT_TOTAL = "TOTAL";
	public static final String COLUMN_REPRTREASON_RETRN = "REASON_OF_RETURN";

	/*******************************
	 * Get the Values from retail_str_bill_details_internet
	 **************************************************/

	public static final String COLUMN_ORDERID = "ORDERID";
	public static final String COLUMN_STORE_ID = "STORE_ID";
	public static final String COLUMN_MOBILENO = "MOBILE_NO";
	public static final String COLUMN_PRODUCTID = "PRODUCT_ID";
	public static final String COLUMN_PRODNM = "PROD_NM";
	public static final String COLUMN_MRP = "MRP";
	public static final String COLUMN_STATUS = "STATUS";
	/****************************
	 * Get the values from retail_str_day_open_close
	 *****************************************/
	public static final String POS_DATE = "BUSINESS_DATE";
	public static final String START_DATE = "START_DATE";
	public static final String START_CASH = "START_CASH";
	public static final String CBCASH_ONHAND = "CBCASH_ONHAND";
	public static final String TRANSACTIONID = "TRI_ID";
	public static final String FLAG = "FLAG";


	public static final String Userid = "USR_NM";//Columnn of Login Activity
	public static final String Pass = "PASSWORD";//Columnn of Login Activity

	//***************************************retail_Card_define************************************************
	private static final String TABLE_NAME_CARD = "retail_Card_define";
	public static final String STORE_ID_CARD = "STORE_ID";
	public static final String POINTS_RANGE = "POINTS_RANGE";
	public static final String ID = "ID";
	public static final String CARD_TYPE = "CARD_TYPE";

	//**************************************rule_defination***********************************************************
	private static final String TABLE_NAME_RULE = "rule_defination";
	public static final String STORE_ID_RULE = "STORE_ID";
	public static final String BASE_VALUE = "BASE_VALUE";
	public static final String SNO = "SNO";
	public static final String CARD_TYPE_RULE = "CARD_TYPE";
	public static final String PER_TON_POINTS = "PER_TONPURCHASEDATE_POINTS";
	//**************************************retail_store_reports***********************************************************
	private static final String TABLE_NAME_REPORT = "retail_store_reports";
	public static final String STORE_ID_REPORT = "STORE_ID";
	public static final String REPORT_NAME = "REPORT_NAME";
	public static final String SNO_REPORT = "SNO";
	public static final String REPORT_CRITERIA = "REPORT_CRITERIA";
	public static final String COMMENTS = "COMMENTS";
	/******************************************
	 * Get the values from retail_cust_loyality
	 *************************************************************************************/
	public static final String COLUMN_CUSTID = "CUST_ID";
	public static final String COLUMN_MOBILENO1 = "MOBILE_NO";
	public static final String COLUMN_NAME = "NAME";
	public static final String COLUMN_POINTSEARNED = "POINTS_EARNED";
	public static final String COLUMN_POINTSREDEEMED = "POINTS_REDEEMED";
	public static final String COLUMN_POINTSAVALILABLE = "POINTS_AVAILABLE";

	/**********************************************
	 * Get the values from retail_card_define
	 **********************************************************************************/
	public static final String COLUMN_STOREID_RETAIL_CARD_DEFINE = "STORE_ID";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_CARD_TYPE = "CARD_TYPE";
	public static final String COLUMN_POINTS_RANGE = "POINTS_RANGE";
	/****************************************************
	 * Get the values from rule defination
	 ***************************************************************************************/
	public static final String COLUMN_Store_ID_RULE_DEFINAATION = "STORE_ID";
	public static final String COLUMN_SNO = "SNO";
	public static final String COLUMN_CARDTYPE = "CARD_TYPE";
	public static final String COLUMN_BASE_VALUE = "BASE_VALUE";
	public static final String COLUMN_PER_TON_POINTS = "PER_TON_POINTS";


	/*******************************************************
	 * retail_str_dstr
	 ******************************************************************************/
	public static final String COLUMN_DSTR_NAME = "DSTR_NM";
	public static final String COLUMN_USER_NAME = "POS_USER";
	public static final String COLUMN_ACTIVE = "ACTIVE";
	public static final String COLUMN_DSTR_INV = "DSTR_INV";
	/****************************************
	 * get data and insert into retail-str_po_detail
	 ********************************************************************************************/
	public static final String COLUMN_PO_NO = "PO_NO";
	public static final String COLUMN_PRODUCT_ID = "PROD_ID";
	public static final String COLUMN_PRODUCT_NAME = "PROD_NM";
	public static final String COLUMN_P_PRICE = "P_PRICE";
	public static final String COLUMN_QTY = "QTY";
	public static final String COLUMN_TOTAL = "TOTAL";
	public static final String COLUMN_STOREID = "STORE_ID";
	public static final String COLUMN_VEND_DSTR_ID = "VEND_DSTR_ID";
	public static final String COLUMN_STORE_ID_DSTR = "STORE_ID";
	public static final String COLUMN_VEND_DSTR_NAME = "VEND_DSTR_NM";
	public static final String COLUMN_VEND_DSTR_INV = "VEND_DSTR_INV";
	//***********************************Sales bill************************************
	public static final String PRODUCT_NAME = "PROD_NM";
	public static final String BATCH_NO = "BATCH_NO";
	public static final String EXPIRY = "EXP_DATE";
	public static final String P_PRICE = "P_PRICE";
	public static final String S_PRICE = "S_PRICE";
	public static final String QUANTITY = "QTY";
	public static final String TOTALQTY = "CON_MUL_QTY";
	public static final String MRP = "MRP";
	public static final String CONVMRP = "CONV_MRP";
	public static final String CONVSPRICE = "CONV_SPRICE";






	public static final String AMOUNT = "AMOUNT";
	public static final String BARCODE = "BARCODE";
	public static final String UOM = "UOM";
	public static final String TOTAL = "TOTAL";
	//*****************************Sales Screen Co************************************
	public static final String TRANS_ID = "TRI_ID";
	public static final String STORESALES_ID = "STORE_ID";
	public static final String GRAND_TOTAL = "GRAND_TOTAL";
	public static final String BILLNO = "INVOICE_NO";
	//**************************************Sales Rturn Column****************************
	public static final String RETURNTRANSIDs = "TRI_ID";
	public static final String RETURNTORESALES_IDs = "STORE_ID";
	public static final String RETURNEXPIRY = "EXP_DATE";
	public static final String RETURNPRODUCTNAME = "PROD_NM";
	public static final String RETURNSELLINGPRICE = "S_PRICE";
	public static final String RETURNQUANTITY = "QTY";
	public static final String RETURNUNITOFMEASURE = "UOM";
	public static final String RETURNGRANDTOTAL = "TOTAL";
	public static final String RETURNBATCHNO = "BATCH_NO";
	public static final String RETURNMRP = "MRP";
	public static final String RETURNREASON = "REASON_RETURN";
	public static final String RETURSALEDATE = "SALE_DATE";
	public static final String RETURNBUSINESS = "BUSINESS_DATE";
	public static final String RETURNSALETIME = "SALE_TIME";
	/**********************************************************************************************/
	public static final String COLUMN_FOR_VEND_RETURN_REJECTION = "REASON_FOR_REJECTION";
	/****************************************************
	 * Store data into Retail_str_po_detail
	 ********************************************************************************************/
	public static final String Purchase_COLUMN_PRODUCT_ID = "PROD_ID";
	public static final String Purchase_COLUMN_PRODUCT_NAME = "PROD_NM";
	public static final String Purchase_COLUMN_P_PRICE = "P_PRICE";
	public static final String Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME = "VENDOR_NM";
	public static final String Purchase_COLUMN_UOM = "UOM";
	public static final String Purchase_COLUMN_QTY = "QTY";
	public static final String Purchase_COLUMN_TOTAL = "TOTAL";
	public static final String Purchase_COLUMN_MRP = "MRP";
	public static final String Purchase_COLUMN_Conv_Fact = "CONV_FACT";
	public static final String Purchase_COLUMN_Flag = "FLAG";
	/****************************************************
	 * Store data into Retail_str_po_master
	 ********************************************************************************************/
	public static final String COLUMN_MASTER_PO_NO = "PO_NO";
	public static final String COLUMN_MASTER_USER_NM = "POS_USER";
	public static final String COLUMN_MASTER_STORE_ID = "STORE_ID";
	public static final String COLUMN_MASTER_GRAND_TOTAL = "GRAND_TOTAL";
	public static final String COLUMN_MASTER_VENDOR_NAME = "VENDOR_NM";
	public static final String COLUMN_MASTER_LASTUPDATE = "LAST_MODIFIED";
	/****************************************************
	 * tmp_vend_pay_desc
	 ********************************************************************************************/
	public static final String COLUMN_VENDOR_REASONS_DESCRIPTION = "DESCRIPTION";
	/****************************************************
	 * tmp_vend_dstr_payment
	 ********************************************************************************************/
	public static final String COLUMN_PAYMENTID = "PAY_ID";
	public static final String COLUMN_PAYMENT_VENDORNAME = "VEND_NM";
	public static final String COLUMN_PAYMENT_REASONS_OF_PAY = "REASON_OF_PAY";
	public static final String COLUMN_PAYMENT_AMOUNT = "AMOUNT";
	public static final String COLUMN_PAYMENT_BANKNAME = "BANK_NAME";
	public static final String COLUMN_PAYMENT_CHEQUENUMBER = "CHEQUE_NO";
	/***************************************************
	 * bankdetails
	 ********************************************************************************************/
	public static final String COLUMN_BANKNAME = "BANK_NAME";

	/****************************************************
	 * Store data into Retail_str_po_detail
	 ********************************************************************************************/
	public static final String COLUMN_DETAIL_PO_NO = "PO_NO";
	public static final String COLUMN_DETAIL_PROD_ID = "PROD_ID";
	public static final String COLUMN_DETAIL_PROD_NAME = "PROD_NM";
	public static final String COLUMN_DETAIL_PPRICE = "P_PRICE";
	public static final String COLUMN_DETAIL_QTY = "QTY";
	public static final String COLUMN_DETAIL_TOTAL = "TOTAL";
	public static final String COLUMN_DETAIL_AMOUNT = "AMOUNT";


	public static final String COLUMN_DETAIL_UOM = "UOM";
	public static final String COLUMN_DETAIL_MRP = "MRP";
	public static final String COLUMN_DETAIL_SPRICE = "S_PRICE";

	//***********************************Employees Table****************************************************************
	private static final String TABLE_NAME_EMPLOYEES = "retail_employees";
	public static final String STORE_ID_EMP = "STORE_ID";
	public static final String USER_NAME = "USR_NM";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final String PASSWORD = "PASSWORD";
	public static final String CONFIRMPASSWORD = "CONFIRM_PASSWORD";
	public static final String ACTIVE = "ACTIVE";
	//****************************retail_store**************************************
	private static final String TABLE_NAME_STORE = "RETAIL_STORE";
	public static final String STORE_ID_STORE = "STORE_ID";
	//public static final String COLUMN_MASTER_GRNID ="Grn_Id";
	//**************************************retail_comp_btl***********************************************************
	private static final String COLOUM_STORE_ID_COMP = "STORE_ID";
	private static final String TABLE_NAME_COMP = "RETAIL_COMP_BTL";
	public static final String COLOUM_COMP_NM = "COMP_NM";
	public static final String COLOUM_COMP_TARGET_AMOUNT = "TARGET_AMOUNT";
	public static final String COLOUM_COMP_BTL_DESC = "BTL_DESC";
	public static final String COLOUM_COMP_TARGET_VALUE = "TARGET_VALUE";
	public static final String COLOUM_COMP_START_DATE = "START_DATE";
	public static final String COLOUM_COMP_END_DATE = "END_DATE";
	//**************************************retail_mfg_btl***********************************************************
	private static final String TABLE_NAME_MFG = "retail_mfg_btl";
	public static final String COLOUM_MFG_NM = "MFG_NM";
	public static final String COLOUM_MFG_TARGET_AMOUNT = "TARGET_AMOUNT";
	public static final String COLOUM_MFG_BTL_DESC = "BTL_DESC";
	public static final String COLOUM_MFG_TARGET_VALUE = "TARGET_VALUE";
	public static final String COLOUM_MFG_START_DATE = "START_DATE";
	public static final String COLOUM_MFG_END_DATE = "END_DATE";
	/*********************************************************
	 * Retail_str_grn_master
	 ********************************************************************************/
	public static final String COLUMN_MASTER_GRNID = "GRN_ID";
	public static final String COLUMN_MASTER_GRN_VENDORNAME = "VEND_NM";
	public static final String COLUMN_MASTER_GRN_GRAND_AMOUNT = "GRAND_AMOUNT";

	/**************************
	 * code from rahul for stock maaster only***********************Get the data from retail_str_stock_master
	 ***********************************************************************************************/
	//public static final String COLUMN_STOCK_GRNID="GRN_ID";
	public static final String COLUMN_STOCK_PROD_ID = "PROD_ID";
	public static final String COLUMN_STOCK_PROD_NAME = "PROD_NM";
	public static final String COLUMN_STOCK_BATCHNO = "BATCH_NO";
	public static final String COLUMN_STOCK_EXP_DATE = "EXP_DATE";
	public static final String COLUMN_STOCK_QTY = "QTY";
	public static final String COLUMN_STOCK_MRP = "MRP";
	public static final String COLUMN_STOCK_AMOUNT = "AMOUNT";
	public static final String COLUMN_STOCK_UOM = "UOM";
	public static final String COLUMN_STOCK_PPRICE = "P_PRICE";
	public static final String COLUMN_STOCK_SPRICE = "S_PRICE";
	public static final String IMEI = "IMEI_NUMBER";
	public static final String STOCK = "STOCK";
	public static final String COLUMN_STOCK_BARCODE = "BARCODE";
	public static final String PREFIX = "PREFIX";
	public static final String COLUMN_TOP_PRODUCT_NAME = "PROD_NM";
	public static final String COLUMN_TOP_PRODUCT_ID = "PROD_ID";
	public static final String COLUMN_FREE_GOODS = "FREE_GOODS";

	public static final String COLUMN_TOTAL_AMOUNT = "TOTAL_AMOUNT";
	public static final String COLUMN_TOTAL_DISCOUNT = "TOTAL_DISCOUNT";
	public static final String COLUMN_FINAL_AMOUNT = "FINAL_AMOUNT";
	public static final String COLUMN_RECEIVED_AMOUNT = "RECEIVED_AMOUNT";
	public static final String COLUMN_EXPECTED_CHANGE = "EXPECTED_CHANGE";



	// ********************************************************************************/
	public static final String DOCTORNAME = "DR_NAME";
	public static final String DOCTORSPECIALITY = "SPECIALITY";
	public static final String DOCTORQUAL = "DR_QUALIFICATION";
	public static final String DOCTORID = "DR_ID";

//////////////////////


	//31-Aug 2016 Done*****************************************


	/***************************************************************
	 * retail_top_product
	 **************************************************************/
	public static final String TOP_PRODUCT_NAME = "PROD_NM";
	public static final String TOP_PRODUCT_ID = "PROD_ID";
	public static final String TOP_PRODUCT_SORT_NAME = "PROD_SHORT_NM";

	//*****************************************************************************************************************

	public static final String TABLE_NAME_TOP ="retail_top_product";
	///////////////////////////////////////retail_store_prod_cpg///////////////////////////////////////////////////
	public static final String COLUMN_REPRT_CPG_ID = "PROD_ID";
	public static final String COLUMN_REPRT_CPG_NM = "PROD_NM";
	public static final String COLUMN_REPRT_CPG_USER_NM = "POS_USER";
	public static final String COLUMN_REPRT_CPG_CODE = "BARCODE";
	public static final String COLUMN_REPRT_CPG_MRP = "MRP";
	public static final String COLUMN_REPRT_CPG_S_PRICE = "S_PRICE";
	public static final String COLUMN_REPRT_CPG_P_PRICE = "P_PRICE";
	public static final String COLUMN_REPRT_CPG_ACTIVE = "ACTIVE";
	public static final String COLUMN_REPRT_CPG_MARGIN = "PROFIT_MARGIN";

	////////////////////////////////////////retail_store_prod_local_cpg///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_LOCAL_CPG_ID = "PROD_ID";
	public static final String COLUMN_REPRT_LOCAL_CPG_NM = "PROD_NM";
	public static final String COLUMN_REPRT_LOCAL_CPG_USER_NM = "POS_USER";
	public static final String COLUMN_REPRT_LOCAL_CPG_CODE = "BARCODE";
	public static final String COLUMN_REPRT_LOCAL_CPG_MRP = "MRP";
	public static final String COLUMN_REPRT_LOCAL_CPG_S_PRICE = "S_PRICE";
	public static final String COLUMN_REPRT_LOCAL_CPG_P_PRICE = "P_PRICE";
	public static final String COLUMN_REPRT_LOCAL_CPG_ACTIVE = "ACTIVE";
	public static final String COLUMN_REPRT_LOCAL_CPG_MARGIN = "PROFIT_MARGIN";

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	public static final String COLUMN_REPRT_SALE_ID = "TRI_ID";
	public static final String COLUMN_REPRT_SALE_USER = "POS_USER";
	public static final String COLUMN_REPRT_SALE_NO = "BATCH_NO";
	public static final String COLUMN_REPRT_SALE_NM = "PROD_NM";
	public static final String COLUMN_REPRT_SALE_DATE = "EXP_DATE";
	public static final String COLUMN_REPRT_SALE_SPRICE = "S_PRICE";
	public static final String COLUMN_REPRT_SALE_PPRICE = "TAX_VALUE3";
	public static final String COLUMN_REPRT_SALE_QTY = "QTY";
	public static final String COLUMN_REPRT_SALE_MRP = "MRP";
	public static final String COLUMN_REPRT_SALE_TOTAL = "TOTAL";
	public static final String COLUMN_REPRT_SALE_UOM = "UOM";
	public static final String COLUMN_REPRT_SUM_SALE_QTY = "Sum(QTY)";
	public static final String CREDITCUSTREPORT = "GRAND_TOTAL";

	public static final String COLUMN_REPRT_SALE_SUMTOTAL = "sum(TOTAL)";


	////////////////////////////////////////retail_str_sales_master///////////////////////////////////////////////////
	public static final String COLUMN_REPRT_SALE_CARDNO = "CARD_NO";
	public static final String COLUMN_REPRT_SALE_CARDTYPE = "CARD_TYPE";
	public static final String COLUMN_REPRT_SALE_BANKNM = "BANK_NAME";
	public static final String COLUMN_REPRT_GRAND_TOTAL = "GRAND_TOTAL";






	public static final String COLUMN_REPRT_SALE_RETURN_ID = "TRI_ID";
	public static final String COLUMN_REPRT_SALE_RETURN_NO = "BATCH_NO";
	public static final String COLUMN_REPRT_SALE_RETURN_NM = "PROD_NM";
	public static final String COLUMN_REPRT_SALE_RETURN_DATE = "EXP_DATE";
	public static final String COLUMN_REPRT_SALE_RETURN_PRICE = "S_PRICE";
	public static final String COLUMN_REPRT_SALE_RETURN_QTY = "QTY";
	public static final String COLUMN_REPRT_SALE_RETURN_MRP = "MRP";
	public static final String COLUMN_REPRT_SALE_RETURN_GRANDTOTAL = "GRAND_TOTAL";
	public static final String COLUMN_REPRT_SALE_RETURN_TOTAL = "TOTAL";
	public static final String COLUMN_REPRT_SALE_RETURN_SUM_TOTAL = "SUM(TOTAL)";
	public static final String COLUMN_REPRT_SALE_RETURN_UOM = "UOM";
	public static final String COLUMN_REPRT_SALE_RETURN_USER = "POS_USER";
	public static final String COLUMN_REPRT_SALE_RETURN_REASON = "REASON_OF_RETURN";
	public static final String COLUMN_REPRT_SALE_RETURN_LAST = "SALE_DATE";




	////////////////////////////////////////retail_str_sales_master///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_SALE_SDATE = "SALE_DATE";

	public static final String COLUMN_REPRT_SALEMASTERSUM_TOTAL = "SUM(GRAND_TOTAL)";

	public static final String COLUMN_SAVETOTALBILLDISCOUNT="SAVETOTALBILLDISCOUNT";
	public static final String COLUMN_SAVETOTALBILLAMOUNT="SAVETOTALBILLAMOUNT";
	public static final String COLUMN_SAVEFINALBILLAMOUNT="SAVEFINALBILLAMOUNT";
	public static final String COLUMN_SAVERECEIVEDBILLAMOUNT="SAVERECEIVEDBILLAMOUNT";
	public static final String COLUMN_SAVEEXPECTEDBILLAMOUNT="SAVEEXPECTEDBILLAMOUNT";

	public static final String COLUMN_REPRT_SALE_TIME="SALE_TIME";
	public static final String COLUMN_REPRT_CUSTOMER_NAME="CUST_NAME";

	////////////////////////////////////////retail_str_day_open_close///////////////////////////////////////////////////

	public static final String COLUMN_REPRT_CASH_SDATE = "START_DATE";
	public static final String COLUMN_REPRT_DAY_CASH = "START_CASH";




	/**************************************************
	 * tmp_vend_str_stock_master_hold
	 **************************************/


	public static final String COLUMN_DETAIL_BATCHNO = "BATCH_NO";
	public static final String COLUMN_DETAIL_MFGBATCHNO = "MFG_BATCH_NO";

	public static final String COLUMN_DETAIL_EXPDATE = "EXP_DATE";


	public static final String COLUMN_MASTER_FLAG = "FLAG";

	public static final String COLUMN_MASTER_GRN_NO = "GRN_ID";

	public static final String COLUMN_VENDOR_RETURNID = "VENDOR_RETURN_ID";


	public static final String COLUMN_REPRT_VENDDSTR_NM = "VEND_DSTR_NM";
	public static final String COLUMN_REPRT_USER_NM = "POS_USER";
	public static final String COLUMN_REPRT_PAY_ID = "PAY_ID";
	public static final String COLUMN_REPRT_RCVD_AMNT = "RECEIVED_AMOUNT";
	public static final String COLUMN_REPRT_DUE_AMNT = "DUE_AMOUNT";
	public static final String COLUMN_REPRT_PAID_AMNT = "AMOUNT";
	public static final String COLUMN_REPRT_BANK_NM = "BANK_NAME";
	public static final String COLUMN_REPRT_CHEQUE_NO = "CHEQUE_NO";
	public static final String COLUMN_REPRT_REASN_OF_PAY = "REASON_OF_PAY";
	public static final String COLUMN_REPRT_LAST_MODIFIED = "PAYMENT_DATE";//
	public static final String COLUMN_DOCTOR_SPECIAL = "SPECIALITY";


	/******************************** ******************************************************************/
	/********************************
	 * Customer Rejection
	 ************************************************/
	private static final String TABLE_NAME_CUSTOMER_REJECTION = "retail_store_sales_desc";
	public static final String REJECTIONREASON = "REASON_RETURN";
	public static final String REJECTIONID = "ID";
	public static final String REJECTIONLASTUP = "LAST_MODIFIED";


	// Customer rejection*********************************************

	private static final String TABLE_NAME_CUSTOMER_REJECTION2 = "retail_store_cust_reject";
	public static final String CUSTREJECTREASON = "REASON_FOR_REJECTION";
	public static final String CUSTREJECTID = "ID";
	public static final String CUSTREJECTACTIVE ="ACTIVE";

	/********************************Vendor Rejection************************************************/

	private static final String TABLE_NAME_VENDOR_REJECTION = "retail_store_vend_reject";
	public static final String REJECTREASON = "REASON_FOR_REJECTION";
	public static final String REJECTID ="ID";
	public static final String REJECTACTIVE ="ACTIVE";


	/*************************************retail_str_bill_lvl_disc*************************************************/
	public static final String BILLLEVELNAME = "BILL_LVL_NAME";
	public static final String DISCTYPEE = "DISC_TYPE";
	public static final String DISACTIVE ="ACTIVE";




	public static final String TABLE_NAME_TOP_PRODUCT ="retail_top_product";
	public static final String TOP_PRODUCT_NAME_TOP = "PROD_NM";




	public static final String LINEITEMNAME = "LINE_ITEM_NM";
	public static final String LINEITEMDISC ="LINE_ITEM_DISC";
	public static final String LINEITEMID ="lINEITEM_ID";
	public static final String LINEACTIVE ="ACTIVE";
	public static final String LINESALEDISC ="LINE_DISC";

	public static final String LINEITEMTABLENAME = "retail_str_lineitem_disc";

	//**********************credit customer**********************************//
	public static final String CREDITCUSTINVOICE="INVOICE_NO";
	public static final String CREDITCUSTNAME="NAME";
	public static final String CREDITCUSTGRANDTOTAL="GRAND_TOTAL";
	public static final String CREDITDUEAMOUNT="DUE_AMOUNT";
	public static final String CREDITRECIEVEDAMOUNT="RECEIVE_AMOUNT";


	public static final String CREDITTOTAL="TOTAL";
	public static final String CREDITMOBILENO="MOBILE_NO";
	public static final String CREDITCUSTGRANDSUMTOTAL = "sum(GRAND_TOTAL)";

	public static final String PURCHASEUNITCONV = "PURCHASE_UNIT_CONV";//Column of product

	//**********************************88shilpa ***********************************************************8
	public static final String CREDITDATE="CREDIT_DATE";
	public static final String CREDITCUSTBILLNO="BILL_NO";

	public static final String CREDITCUSTBILLDATE="BILL_DATE";
	public static final String CREDITCUSTPAYMENT_ID="PAYMENT_ID";
	public static final String CREDITCUSTITEM="ITEM";

	//********************************************************************************************************
	public static final String MRP_VISIBLE = "MRP";//Store table name
	public static final String TELE2_VISIBLE = "TELE_2";//Store table name
	public static final String FOOTER_VISIBLE = "FOOTER";

	public static final String BILL_COPY = "NORMAL_SALES";//Store table name
	public static final String MAIN_BODY = "MAIN_BODY";//Store table name

	public static final String CREDIT_COPY = "CREDIT_CUSTOMER";//Store table name
	public static final String RETURN_COPY = "RETURNS";
	public static final String MARGIN_VISIBILTY = "M_FLAG";//margin visibilty in system setting

	public static final String FREE_GOODSVISIBILTY = "FREE_GOODS";//margin visibilty in system setting
	public static final String INV_BILL_PRINTS = "INV_PRINT";//margin visibilty in system setting
	public static final String OTP = "OTP_CHECK";//margin visibilty in system setting



	///*****************************************************************************************8
	public static final String DISPLAYTOTALBILLVALUE = "TOTAL_BILL_VALUE";//Store table name
	public static final String DISPLAYDISCOUNT = "DISCOUNT";//Store table name
	public static final String DISPLAYNETPAYABLE = "NET_BILL_PAYABLE";
	public static final String DISPLAYAMOUNTRECEIVED = "AMOUNT_RECEIVED";//Store table name
	public static final String DISPLAYAMOUNTPAIDBACK = "AMOUNT_PAID_BACK";//Store table name
	public static final String DISPLAYFOOTER = "FOOTER";

	//*****************************************************************************************************************


	private static final String TAG = "MyActivity";

	@SuppressLint("NewApi")
	public DBhelper(Context context) {

		super(context, DB_PATH + DATABASE_NAME, null, DATABASE_VERSION);
		this.mycontext = context;
	}


	public final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case MESSAGE_STATE_CHANGE:
					if (D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
					switch (msg.arg1) {
						case BluetoothChatService.STATE_CONNECTED:
							break;
						case BluetoothChatService.STATE_CONNECTING:
							break;
						case BluetoothChatService.STATE_LISTEN:
						case BluetoothChatService.STATE_NONE:
							notconnected=false;
							break;
					}
					break;
			}
		}
	};
	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();



		if (dbExist) {
			//do nothing - database already exist
		} else {

			//By calling this method and empty database will be created into the default system path
			//of your application so we are gonna be able to overwrite that database with our database.
			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	/**
	 * Check if the database already exist to avoid re-copying the file each time you open the application.
	 *
	 * @return true if it exists, false if it doesn't
	 */
	public boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DATABASE_NAME;
			java.io.File f = new java.io.File(myPath);
			f.getParentFile().mkdirs();
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);




		} catch (SQLiteException e) {

			//database does't exist yet.

		}

		if (checkDB != null) {

			checkDB.close();

		}

		return checkDB != null ? true : false;
	}

	/**
	 * Copies your database from your local assets-folder to the just created empty database in the
	 * system folder, from where it can be accessed and handled.
	 * This is done by transfering bytestream.
	 */
	private void copyDataBase() throws IOException {

		//Open your local db as the input stream
		InputStream myInput = mycontext.getAssets().open(DATABASE_NAME);

		// Path to the just created empty db
		String outFileName = DB_PATH + DATABASE_NAME;

		//Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);

		//transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}

		//Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}

	public void openDataBase() throws SQLException {

		//Open the database
		String myPath = DB_PATH + DATABASE_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		myDataBase.execSQL("PRAGMA key='Anaconda'");

	}

	@Override
	public synchronized void close() {

		if (myDataBase != null)
			myDataBase.close();

		super.close();

	}
	@Override
	public void onConfigure(SQLiteDatabase myDataBase) {
		myDataBase.execSQL("PRAGMA key='Anaconda'");
	}


	@Override
	public void onCreate(SQLiteDatabase db) {

	}



	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
		if (newversion == oldversion + 1) {
			/*db.execSQL("ALTER TABLE retail_str_sales_master ADD COLUMN SAVETOTALBILLDISCOUNT VARCHAR (20)");
			db.execSQL("ALTER TABLE retail_str_sales_master ADD COLUMN SAVETOTALBILLAMOUNT VARCHAR (20)");
			db.execSQL("ALTER TABLE retail_str_sales_master ADD COLUMN SAVEFINALBILLAMOUNT VARCHAR (20)" );policeonline
			db.execSQL(  "ALTER TABLE retail_str_sales_master ADD COLUMN SAVERECEIVEDBILLAMOUNT VARCHAR (20)" );
			db.execSQL("ALTER TABLE retail_str_sales_master ADD COLUMN SAVEEXPECTEDBILLAMOUNT VARCHAR (20) ");*/

		//	db.execSQL("ALTER TABLE retail_str_stock_master ADD COLUMN ACTIVE VARCHAR (45) ");
		}
		onCreate(db);


	}

// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!********** CODE TO FETCH ALL DATA *************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

// !!!!!!!!*********Code to get all Customers*******!!!!!!!!!!

	public ArrayList<Customer> getAllCustomers(String nameOrPhone) {
		ArrayList<Customer> customerlist = new ArrayList<Customer>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select MOBILE_NO,NAME,E_MAIL,CREDIT_CUST,CUST_ADD from retail_cust where"
							+ " MOBILE_NO  like ? or NAME like ? "
					, params);
			if (null != res && res.moveToFirst() && res.getCount() > 0) {
				do {
					Customer customer = new Customer();
					customer.setCustomermobileno(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
					customer.setCustomername(res.getString(res.getColumnIndex(CUSTOMERNAME)));
					customer.setCustomeremail(res.getString(res.getColumnIndex(CUSTOMEREMAIL)));
					customer.setCustomercredit(res.getString(res.getColumnIndex(CUSTOMERCREDIT)));
					customer.setCustomeradress(res.getString(res.getColumnIndex(CUSTOMERADDRESS)));

					customerlist.add(customer);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return customerlist;
	}



	// !!!!!!!!!**********Code to get all Products******!!!!!!!!
	public ArrayList<Product> getAllProducts(String nameOrPhoneOrNo) {
		ArrayList<Product> Productlist = new ArrayList<Product>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = nameOrPhoneOrNo + "%";
			params[1] = nameOrPhoneOrNo + "%";
			params[2] = nameOrPhoneOrNo + "%";
			Cursor res = db.rawQuery("select distinct PROD_NM,STORE_ID,PROD_ID,BARCODE,MRP,S_PRICE,P_PRICE,TAX_DESC,PURCH_ORDER_UNIT,MFG,SELLING_ORDER_UNIT,INTERNET_PRICE,IS_PROD_REL_INT,ACTIVE ,PROFIT_MARGIN, DISCOUNT_PERCENT from retail_store_prod where"
					+ " PROD_NM  like ? or PROD_ID like ? or BARCODE like ?", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {

					Product product = new Product();
					product.setStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setProductName(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setMRP(res.getString(res.getColumnIndex(PRODUCTMRP)));
					// product.setMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setInternet(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					// product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					product.setPackingUnit1(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					product.setManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setProductBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					// product.setStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					product.setTaxid(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setInternetrelevant(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					product.setMargin(res.getString(res.getColumnIndex(PRODUCTMARGIN)));
					product.setProductdiscount(res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));

					Productlist.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return Productlist;
	}

	// !!!!!!!!!**********Code to get all Products******!!!!!!!!
	public ArrayList<Product> getAllProductsCPG(String nameOrPhoneOrNo) {
		ArrayList<Product> Productlist = new ArrayList<Product>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = nameOrPhoneOrNo + "%";
			params[1] = nameOrPhoneOrNo + "%";
			params[2] = nameOrPhoneOrNo + "%";
			Cursor res = db.rawQuery("select  distinct STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,SELLING_ORDER_UNIT,INTERNET_PRICE,IS_PROD_REL_INT,ACTIVE,PROFIT_MARGIN  from retail_store_prod_cpg where"
					+ " PROD_NM  like ? or PROD_ID like ? or BARCODE like ?", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					Product product = new Product();
					product.setStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setProductName(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setMRP(res.getString(res.getColumnIndex(PRODUCTMRP)));
					// product.setMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setInternet(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					// product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setMeasureUnit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					//product.setPackingUnit1(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					//product.setManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setProductBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					// product.setStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					//product.setTaxid(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setInternetrelevant(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					Productlist.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return Productlist;
	}
	//!!!!!!!!!!**********Code to get all Stores********!!!!!!!!!!!!!!
	public ArrayList<String> getAllStores() {
		ArrayList<String> storelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				storelist.add(res.getString(res.getColumnIndex(STORE_ID)));
				storelist.add(res.getString(res.getColumnIndex(STORE_NAME)));
				storelist.add(res.getString(res.getColumnIndex(STORE_ADDRESS1)));
				storelist.add(res.getString(res.getColumnIndex(STORE_CITY)));
				storelist.add(res.getString(res.getColumnIndex(STORE_MOBILE)));
				storelist.add(res.getString(res.getColumnIndex(STORE_ALTER_MOBILE)));
				storelist.add(res.getString(res.getColumnIndex(STORE_FOOTER)));

			} while (res.moveToNext());
		}

		return storelist;
	}
	//!!!!!!!!!!!!!!!!!!!!************Code to get maintainence********!!!!!!!!!!!!!!!!
	public ArrayList<ShowModel> getAlldata() {
		ArrayList<ShowModel> array_list = new ArrayList<ShowModel>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_maint", null);
		if (cur.moveToFirst()) {
			do {
				ShowModel sm = new ShowModel();
				sm.setTICKET_ID(cur.getString(cur.getColumnIndex(COLUMN_TICKET_ID)));
				sm.setSUBJECT_DESC(cur.getString(cur.getColumnIndex(COLUMN_SUBJECT_DESC)));
				sm.setSUPPORT_TICKET_STATUS(cur.getString(cur.getColumnIndex(COLUMN_SUPPORT_TICKET_STATUS)));
				sm.setTEAM_MEMBER(cur.getString(cur.getColumnIndex(COLUMN_TEAM_MEMBER)));
				sm.setTimeStamp(cur.getString(cur.getColumnIndex(COLUMN_DATE)));
				array_list.add(sm);
			} while (cur.moveToNext());
		}
		return array_list;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Vendor********!!!!!!!!!!!!!!!!
	public ArrayList<Vendor> getAllVendor(String nameOrPhone) {

		ArrayList<Vendor> vendorlist = new ArrayList<Vendor>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select DSTR_ID,DSTR_NM,DSTR_CNTCT_NM,ADD_1,CITY,CTR_NM,ZIP,TELE,MOBILE ,DSTR_INV, ACTIVE,EMAIL from retail_str_dstr where "
					+ " DSTR_NM like ? or DSTR_ID like ?", params);
			if (res.moveToFirst()) {
				do {
					Vendor vendor = new Vendor();
					vendor.setVendorId(res.getString(res.getColumnIndex(VENDORID)));
					vendor.setVendorname(res.getString(res.getColumnIndex(VENDORNAME)));
					vendor.setVendorContact(res.getString(res.getColumnIndex(VENDORCONTACTNAME)));
					vendor.setAddress(res.getString(res.getColumnIndex(VENDORADDRESS)));
					vendor.setCity(res.getString(res.getColumnIndex(VENDORCITY)));
					vendor.setCountry(res.getString(res.getColumnIndex(VENDORCOUNTRY)));
					vendor.setMobile(res.getString(res.getColumnIndex(VENDORMOBILE)));
					vendor.setTelephone(res.getString(res.getColumnIndex(VENDORTTELEPHONE)));
					vendor.setZip(res.getString(res.getColumnIndex(VENDORZIP)));
					vendor.setVendorInventory(res.getString(res.getColumnIndex(VENDORINVENTORY)));
					vendor.setActive(res.getString(res.getColumnIndex(VENDORACTIVE)));
					vendor.setEmail(res.getString(res.getColumnIndex(VENDOREmail)));
					vendorlist.add(vendor);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return vendorlist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get localVendor********!!!!!!!!!!!!!!!!
	public ArrayList<localvendor> getAlllocalVendor(String nameOrPhone) {
		ArrayList<localvendor> localvendorlist = new ArrayList<localvendor>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = nameOrPhone + "%";
			params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  DISTINCT STORE_ID, VEND_ID, VEND_NM,VEND_CNTCT_NM,ADD_1,CITY,CTR_DESC,ZIP,TELE,MOBILE,ACTIVE,VEND_INV from retail_store_vendor where "
					+ " VEND_NM like ? or  VEND_ID like ?", params);
			if (res.moveToFirst()) {
				do {
					localvendor localvendor = new localvendor();

					localvendor.setLocalvendorid(res.getString(res.getColumnIndex(LOCALVENDORID)));
					localvendor.setLocalvendorname(res.getString(res.getColumnIndex(LOCALVENDORNAME)));
					localvendor.setLocalvendoraddress(res.getString(res.getColumnIndex(LOCALVENDORADDRESS)));
					localvendor.setLocalvendorcity(res.getString(res.getColumnIndex(LOCALVENDORCITY)));
					localvendor.setLocalvendorcontactname(res.getString(res.getColumnIndex(LOCALVENDORCONTACT)));
					localvendor.setLocalvendormobile(res.getString(res.getColumnIndex(LOCALVENDORMOBILE)));
					localvendor.setLocalvendortele(res.getString(res.getColumnIndex(LOCALVENDORTELE)));
					localvendor.setLocalvendorcountry(res.getString(res.getColumnIndex(LOCALVENDORCOUNTRY)));
					localvendor.setLocalvendorzip(res.getString(res.getColumnIndex(LOCALVENDORZIP)));
					localvendor.setLocalactive(res.getString(res.getColumnIndex(LOCALVENDORACTIVE)));
					localvendor.setLocalvendorinventory(res.getString(res.getColumnIndex(LOCALVENDORINVENTORY)));
					localvendorlist.add(localvendor);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localvendorlist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Local product********!!!!!!!!!!!!!!!!
	public ArrayList<LocalProduct> getAllLocalProduct(String nameOrPhone) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_ID,ACTIVE,PROFIT_MARGIN, DISCOUNT_PERCENT,SELLING_ORDER_UNIT from retail_store_prod_local where "
					+ " PROD_NM  like ? or PROD_ID like ?", params);

			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));
					localProduct.setMargin(res.getString(res.getColumnIndex(PRODUCTLOCALMARGIN)));
					localProduct.setDiscount(res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));
					localProduct.setUom(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));


					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

			e.printStackTrace();
		}
		return localproductlist;
	}

	public ArrayList<LocalProduct> getAllLocalProductforpurchase(String nameOrPhone) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_ID,ACTIVE,PROFIT_MARGIN, DISCOUNT_PERCENT,SELLING_ORDER_UNIT,CONV_FACT from retail_store_prod_local where "
					+ " PROD_NM  like ? or PROD_ID like ?", params);

			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));
					localProduct.setMargin(res.getString(res.getColumnIndex(PRODUCTLOCALMARGIN)));
					localProduct.setDiscount(res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));
					localProduct.setUom(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					localProduct.setConvFact(res.getString(res.getColumnIndex(Purchase_COLUMN_Conv_Fact)));
					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

			e.printStackTrace();
		}
		return localproductlist;
	}




	///##########################################local product cpg#################################
	public ArrayList<LocalProduct> getAllLocalProductCpg(String nameOrPhone) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select  STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_ID,ACTIVE from retail_store_prod_local_cpg where "
					+ " PROD_NM  like ? or PROD_ID like ?", params);
			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));

					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localproductlist;
	}


	//!!!!!!!!!!!!!!!!!!!!************Code to get Tax********!!!!!!!!!!!!!!!!
	public ArrayList<Tax> getAllTax() {
		ArrayList<Tax> taxlist = new ArrayList<Tax>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_tax", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				//namelist.add(res.getString(res.getColumnIndex(CUSTOMER_NAME)));
				Tax tax = new Tax();
				tax.setTAX_DESCRIPTION(res.getString(res.getColumnIndex(TAX_DESCRIPTION)));
				tax.setTAX_RATE(res.getString(res.getColumnIndex(TAX_RATE)));
				taxlist.add(tax);

			} while (res.moveToNext());
		}

		return taxlist;
	}

	/************
	 * get data from mobile internet
	 ***********/
	public ArrayList<String> getMobileinternet() {

		ArrayList<String> array_list = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from retail_str_bill_details_internet", null);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {

			array_list.add(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILENO)));
			cursor.moveToNext();
		}
		return array_list;
	}

	/************
	 * get data from retail_ad_Ticker
	 ***********/
	public ArrayList<ListModel> getReportData() {
		ArrayList<ListModel> arrayList = new ArrayList<ListModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select AD_MAIN_ID,AD_TEXT,AD_CST_SLB1,AD_CST_SLB2,AD_CST_SLB3 ,STATUS,ACTIVE from retail_ad_ticker where STATUS Like 'A%'and ACTIVE Like 'Y%' ", null);
		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arrayList.toString());
				ListModel lm = new ListModel();
				lm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_AD_MAIN_ID)));
				lm.setAD_TEXT(cur.getString(cur.getColumnIndex(COLUMN_AD_TEXT)));
         /*lm.setSTR_ID(cur.getString(cur.getColumnIndex(COLUMN_STR_ID)));*/
         /*lm.setAD_START_DATE(cur.getString(cur.getColumnIndex(COLUMN_AD_START_DATE)));
         lm.setAD_END_DATE(cur.getString(cur.getColumnIndex(COLUMN_AD_END_DATE)));*/
				lm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB1)));
				lm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB2)));
				lm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_AD_CST_SLB3)));

				arrayList.add(lm);
			}
			while (cur.moveToNext());
		}
		return arrayList;
	}

	/************
	 * Get data from BLINkING
	 ***********/
	public ArrayList<BlinkingModel> getBlinkinglogoData() {
		ArrayList<BlinkingModel> arraylist = new ArrayList<BlinkingModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select AD_MAIN_ID,AD_DESC,AD_CST_SLB1,AD_CST_SLB2,AD_CST_SLB3 ,STATUS,ACTIVE from retail_ad_blinkinglogo where STATUS Like 'A%'and ACTIVE Like 'Y%' ", null);
		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arraylist.toString());
				BlinkingModel bm = new BlinkingModel();
				bm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_MAIN_ID)));
				bm.setAD_DESC(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_DESC)));
				bm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB1)));
				bm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB2)));
				bm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB3)));
				arraylist.add(bm);
			}
			while (cur.moveToNext());
		}
		return arraylist;

	}

	/************
	 * Get data from STORE NEWS
	 ***********/
	public ArrayList<StoreMainModel> getStoreMainReportData() {
		ArrayList<StoreMainModel> arraylist = new ArrayList<StoreMainModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select POS_USER,AD_MAIN_ID,AD_DESC,AD_CST_SLB1,AD_CST_SLB2,AD_CST_SLB3 ,STATUS,ACTIVE from retail_ad_Store_Main where STATUS Like 'A%'and ACTIVE Like 'Y%' ", null);
		if (cur.moveToFirst()) {
			do {
				Log.e("***Db*arrayList*** ", arraylist.toString());
				StoreMainModel sm = new StoreMainModel();
				sm.setUserNm(cur.getString(cur.getColumnIndex(COLUMN_Blinkinglogo_USER_NM)));
				sm.setAD_MAIN_ID(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_MAIN_ID)));
				sm.setAD_DESC(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_DESC)));
				sm.setAD_CST_SLB1(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB1)));
				sm.setAD_CST_SLB2(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB2)));
				sm.setAD_CST_SLB3(cur.getString(cur.getColumnIndex(COLUMN_StoreMain_AD_CST_SLB3)));
				arraylist.add(sm);
			}
			while (cur.moveToNext());
		}
		return arraylist;
	}

	//!!!!!!!!!!!!!!!!!!!!************Code to get Local product from Barcode********!!!!!!!!!!!!!!!!
	public ArrayList<LocalProduct> getLocalProductfromBarcode(String BarcodefromScanner) {

		ArrayList<LocalProduct> localproductlist = new ArrayList<LocalProduct>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = BarcodefromScanner + "%";
			Cursor res = db.rawQuery("select  STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_ID,ACTIVE,PROFIT_MARGIN ,DISCOUNT_PERCENT from retail_store_prod_local where "
					+ " BARCODE  like ? ", params);
			if (res.moveToFirst()) {
				do {
					LocalProduct localProduct = new LocalProduct();
					localProduct.setStoreid(res.getString(res.getColumnIndex(PRODUCTLOCALSTOREID)));
					localProduct.setProductid(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTID)));
					localProduct.setProductname(res.getString(res.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setMRP(res.getString(res.getColumnIndex(PRODUCTLOCALMRP)));
					localProduct.setPurchasePrice(res.getString(res.getColumnIndex(PRODUCTLOCALPURCHASE)));
					localProduct.setSellingPrice(res.getString(res.getColumnIndex(PRODUCTLOCALSELLING)));
					localProduct.setBarcode(res.getString(res.getColumnIndex(PRODUCTLOCALBARCODE)));
					localProduct.setTaxid(res.getString(res.getColumnIndex(PRODUCTLOCALTAXID)));
					localProduct.setActive(res.getString(res.getColumnIndex(PRODUCTLOCALACTIVE)));
					localProduct.setMargin(res.getString(res.getColumnIndex(PRODUCTLOCALMARGIN)));
					localProduct.setDiscount(res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));
					localproductlist.add(localProduct);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return localproductlist;
	}


	//!!!!!!!!!!!!!!!!!!!!************Code to get Single Tax********!!!!!!!!!!!!!!!!

	public Cursor getTax(String tax_id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_tax where TAX_ID=" + tax_id + "", null);
		return res;
	}
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!************* CODE TO FETCH SINGLE DATA ****************!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


	// !!!!!!!!!!**********Code to single Store******!!!!!!!!!!!!!!!
	public Store getStoreDetails() {

		Store store = null;

		SQLiteDatabase db = this.getReadableDatabase();
		db.getVersion();
		Log.e("version ", "Version number is " + db.getVersion());
		Cursor res = db.rawQuery("select * from retail_store", null);
		if (res.moveToFirst()) {
			//do {
			store = new Store();
			store.setStoreId(res.getString(res.getColumnIndex(STORE_ID)));
			store.setMediaid(res.getString(res.getColumnIndex("STORE_MEDIA_ID")));

			store.setOTP(res.getString(res.getColumnIndex("OTP")));
			store.setStoreName(res.getString(res.getColumnIndex(STORE_NAME)));
			store.setStoreemail(res.getString(res.getColumnIndex(STORE_EMAIL)));
			store.setStoreTele(res.getString(res.getColumnIndex(STORE_MOBILE)));
			store.setStorezip(res.getString(res.getColumnIndex(STORE_ZIP)));
			store.setStorecity(res.getString(res.getColumnIndex(STORE_CITY)));
			store.setStorecontactname(res.getString(res.getColumnIndex(STORE_CONTACTNAME)));
			store.setStorecountry(res.getString(res.getColumnIndex(STORE_COUNTRY)));
			store.setStoreAddress(res.getString(res.getColumnIndex(STORE_ADDRESS1)));
			store.setFooter(res.getString(res.getColumnIndex(STORE_FOOTER)));
			store.setAlterMobileNo(res.getString(res.getColumnIndex(STORE_ALTER_MOBILE)));


			//} while (res.moveToNext());


		}
		return store;
	}



	public displaypojo getStoredisplaydetail() {

		displaypojo display = null;

		SQLiteDatabase db = this.getReadableDatabase();
		db.getVersion();
		Log.e("version ", "Version number is " + db.getVersion());
		Cursor res = db.rawQuery("select * from retail_bill_display", null);
		if (res.moveToFirst()) {
			//do {
			display = new displaypojo();
			display.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
			display.setTotalbillvalue(res.getString(res.getColumnIndex(DISPLAYTOTALBILLVALUE)));
			display.setDiscount(res.getString(res.getColumnIndex(DISPLAYDISCOUNT)));
			display.setNetbillpayable(res.getString(res.getColumnIndex(DISPLAYNETPAYABLE)));
			display.setAmountreceived(res.getString(res.getColumnIndex(DISPLAYAMOUNTRECEIVED)));
			display.setAmountpaidback(res.getString(res.getColumnIndex(DISPLAYAMOUNTPAIDBACK)));
			display.setFooter(res.getString(res.getColumnIndex(DISPLAYFOOTER)));


			//} while (res.moveToNext());


		}
		return display;
	}





// public  Cursor getStore(String Storeid){
//    SQLiteDatabase db = this.getReadableDatabase();
//    Cursor res =  db.rawQuery( "select Store_Id from retail_store where Store_Id=" + Storeid + "",null) ;
//    return res;
// }


	// !!!!!!!!!!**********Code to Store for Day open******!!!!!!!!!!!!!!!
	public Cursor getStoreDay() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select STORE_ID from retail_store where STORE_ID = STORE_ID", null);
		return res;
	}
	//!!!!!!!!!!!**********Code to get single maintainence******!!!!!!!

	public Cursor getData(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store_maint where TICKET_ID=" + id + "", null);
		return res;
	}

	//!!!!!!!!!!!**********Code to get single internet detail******!!!!!!!
	public Cursor getBillReport(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor ress = db.rawQuery("select * from retail_str_bill_details_internet where MOBILE_NO=" + id + "", null);
		return ress;
	}

	public void updateRecord(String id) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("update retail_str_bill_details_internet set STATUS = 'Delivered' where ORDERID=" + id + "");
		db.close();
	}
	//!!!!!!!!!!!**********Code to get single localproductlist******!!!!!!!

	public Cursor getlocalproduct(String nameOrPhone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = nameOrPhone + "%";
		Cursor res = db.rawQuery("select  STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_ID from retail_store_prod_local where "
				+ " PROD_NM  like ?", params);
		return res;
	}

	//.......!!!!!! LOGIN GET DATA METHOD TO VALIDATE !!!!!!.........//

	public ArrayList<String> getData() {
		ArrayList<String> namelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		try {
			Cursor res = db.rawQuery("select * from retail_employees", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					namelist.add(res.getString(res.getColumnIndex(Userid)));
				} while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return namelist;
	}

	// !!!!!!!!!!!!!*********VALIDATE*****!!!!!!!!!!!!!!!
	public boolean validateUser(String username, String password) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_NAME2 + " WHERE "
						+ Loginpo.User.USER_NAME + "='" + username + "'AND " + Loginpo.User.PASS + "='" + password + "'", null);
		return c.getCount() > 0;
	}





	public boolean validatepassword( String password) {
		Cursor c = getReadableDatabase().rawQuery(
				"SELECT * FROM " + TABLE_NAME5 + " WHERE "
						+ passwordpojo.User.STORE + "='" + password + "'", null);
		return c.getCount() > 0;
	}

	// !!!!!!!!!!!!!*********INSERT CUSTOMER*****!!!!!!!!!!!!!!!
	public boolean insertCustomer(Customer customer,String username,String id,String store_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("STORE_ID",store_id);
		contentValues.put("CUST_ID", id);
		contentValues.put("POS_USER",username);
		contentValues.put("MOBILE_NO", customer.getCustomermobileno());
		contentValues.put("NAME", customer.getCustomername());
		contentValues.put("E_MAIL", customer.getCustomeremail());
		contentValues.put("PASSWORD", customer.getCustomermobileno());
		contentValues.put("CREDIT_CUST",customer.getCustomercredit());
		contentValues.put("CUST_ADD",customer.getCustomeradress());
		contentValues.put("S_FLAG","0");
		contentValues.put("M_FLAG","I");

		db.insert("retail_cust", null, contentValues);

		String insert_customer = "insert into retail_cust ( CUST_ID , POS_USER , MOBILE_NO , NAME , E_MAIL , PASSWORD , CREDIT_CUST , CUST_ADD , M_FLAG,S_FLAG ) " +
				"values (" + "'" + id +  "'," + "'" + username +  "'," + "'" + customer.getCustomermobileno() +  "'," +  "'" + customer.getCustomername() +  "'," +  "'" + customer.getCustomeremail() +  "'," + "'" + customer.getCustomermobileno() +  "'," + "'" + customer.getCustomercredit() +  "'," + "'" + customer.getCustomeradress() +  "'," + "'I'," +"'0')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", insert_customer);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}

	// !!!!!!!!!!!!!*********INSERT DOCTOR****!!!!!!!!!!!!!!!
	public boolean insertDoctor(DoctorPojo doctorPojo,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("DR_ID", doctorPojo.getDoctid());
		contentValues.put("DR_NAME", doctorPojo.getDoctorName());
		contentValues.put("SPECIALITY", doctorPojo.getDoctorQualification());
		contentValues.put("DR_ADDRESS", doctorPojo.getDoctorSpeciality());
		contentValues.put("ACTIVE",doctorPojo.getActive());
		contentValues.put("POS_USER",username);
		contentValues.put("S_FLAG", "0");
		contentValues.put("M_FLAG","I");

		db.insert("dr_discription", null, contentValues);
		String insert_doctor = "insert into dr_discription ( DR_ID , DR_NAME , SPECIALITY , DR_ADDRESS , ACTIVE , POS_USER , S_FLAG , M_FLAG ) values (" + "'" + doctorPojo.getDoctid() +  "'," + "'" + doctorPojo.getDoctorName() +  "'," + "'" + doctorPojo.getDoctorQualification() +  "'," +  "'" + doctorPojo.getDoctorSpeciality() +  "'," +  "'" + doctorPojo.getActive() +  "'," + "'" + username +  "'," + "'0'," + "'I')";

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", insert_doctor);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}


	//!!!!!!!!!!!!!!!!!!!!!!!!!!!! Inset Credit customer !!!!!!!!!!!!!!!!!!//

	public boolean insercredticustomer(String InvoiceNo,String Name,String Mobileno,String Grandtotal,String username)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		Long Value = System.currentTimeMillis();
		String Demo = String.valueOf(Value);
		boolean checkcredit = false;
		boolean returnval;

		checkcredit = CheckCreditCustomerAlredyInDb(Mobileno);
		if (checkcredit == false) {
			contentValues.put("INVOICE_NO", InvoiceNo);
			contentValues.put("NAME", Name);
			contentValues.put("MOBILE_NO", Mobileno);
			// contentValues.put("POS_USER",username);
			contentValues.put("GRAND_TOTAL", Grandtotal);
			contentValues.put("PAYMENT_ID", Demo);
			contentValues.put("FLAG", "1");
			contentValues.put("CREDIT_DATE", getDate());
			contentValues.put("M_FLAG", "I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_credit_cust", null, contentValues);
		} else {

			String grandtotal = getparticularCreditCustMobileNo(Mobileno);
			contentValues.put("MOBILE_NO", Mobileno);
			contentValues.put("M_FLAG","U");
			float total = Float.parseFloat(Grandtotal);
			float newgrandtotal = Float.parseFloat(grandtotal) + total;
			contentValues.put("GRAND_TOTAL", Double.toString(newgrandtotal));

			contentValues.put("INVOICE_NO", InvoiceNo);
			contentValues.put("PAYMENT_ID", Demo);
			contentValues.put("FLAG", "1");
			contentValues.put("CREDIT_DATE", getDate());
			int sqlUpdateRetval = db.update("retail_credit_cust", contentValues, " MOBILE_NO = ? "
					, new String[]{String.valueOf(Mobileno)});

			// Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

			if (sqlUpdateRetval < 1) {

				Log.e("Update fail", "returned: " + sqlUpdateRetval);
				returnval = false;
			}
			//return;
		}
		//}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;

	}

	/// Credit Customere
	public boolean CheckCreditCustomerAlredyInDb(String MobileNo) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = batchno + "%";
//    //  params[1] =productid + "%";

		String Query = ("select MOBILE_NO from retail_credit_cust where "
				+ "MOBILE_NO  ='"+MobileNo+"'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularCreditCustMobileNo(String MobileN0) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
 /* String[] params = new String[1];
  params[0] = batchno + "%";
  params[1] = Prod_Id + "%";*/
		String Query = ("select GRAND_TOTAL from retail_credit_cust where " + " MOBILE_NO=  '" + MobileN0+ "'");

		Log.e("Query::", "select GRAND_TOTAL from retail_credit_cust where " + " MOBILE_NO=  '" + MobileN0 + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(CREDITCUSTGRANDTOTAL));
		}
		return getQty;

	}





//*******************!!!!!! SYSTEM CURRENTTIME IN MILISECOND CODE USED IN TABLES!!!!**************

	String getSystemCurrentTime() {

		Long value = System.currentTimeMillis();
		String result = Long.toString(value);
		return result;
	}

//*******************!!!!!! SYSTEM CURRENTTIME IN MILISECOND CODE USED IN SALESRE!!!!**************

	String getSystemCurrentTimeinsalesreturnwithin() {

		Long value = System.currentTimeMillis() + 1000;
		String result = Long.toString(value);
		return result;
	}

	//!!!!!!!!!!!************UPDATE STORE************!!!!!!!!!!!!!!!!!!!!!!!!!

	///                mydb.updateStore(Store_Id_To_Update, Mobile.getText().toString(),username,footer.getText().toString(),Alter_Mobile.getText().toString(),mStoreName,mEmail,mZip,mStoreContactName,mCity,mAddress1,mCountry);

	public boolean updateStore(String storeid, String Mobile, String username, String Footer,
							   String alter_mobile, String storeNm, String email, String zip,
							   String mStoreContactName, String city, String address1, String mcountry) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("TELE", Mobile);
		contentValues.put("POS_USER",username);
		contentValues.put("M_FLAG","U");
		contentValues.put("FOOTER",Footer);
		contentValues.put("TELE_1",alter_mobile);

		contentValues.put("STR_NM",storeNm);
		contentValues.put("E_MAIL",email);
		contentValues.put("ZIP",zip);
		contentValues.put("STR_CNTCT_NM",mStoreContactName);
		contentValues.put("CTY",city);
		contentValues.put("ADD_1",address1);
		contentValues.put("STR_CTR",mcountry);
		contentValues.put("S_FLAG","0");

		db.update("retail_store", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(storeid)});

		String update_store = "UPDATE retail_store SET TELE = "+ "'" + Mobile + "'"
				+ " ,POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " ,FOOTER = " + "'" + Footer + "'"
				+ " ,TELE_1 = " + "'" + alter_mobile  + "'" + " ,STR_NM = " + "'" + storeNm
				+ "'" + " ,E_MAIL = " + "'" + email + "'" + " ,ZIP = " + "'" + zip + "'" + " ,STR_CNTCT_NM = " + "'" + mStoreContactName
				+ "'" + " ,CTY = " + "'" + city  + "'" + " ,ADD_1 = " + "'" + address1 + "'" + " ,STR_CTR = " + "'" + mcountry + "' WHERE STORE_ID= " + "'" + String.valueOf(storeid) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_store);
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		return true;
	}


	//!!!!!!!!!!!************UPDATE PRODUCT************!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean updateProduct(String PRODUCTPRODUCTID, String PRODUCTSELLING, String PRODUCTPURCHASE, String PRODUCTINTERNET, String PRODUCTRELEVANT, String ACTIVE, String MARGIN,String username,String DISCOUNT) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("S_PRICE", PRODUCTSELLING);
		contentValues.put("POS_USER",username);
		contentValues.put("P_PRICE", PRODUCTPURCHASE);
		contentValues.put("INTERNET_PRICE", PRODUCTINTERNET);
		contentValues.put("IS_PROD_REL_INT", PRODUCTRELEVANT);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("PROFIT_MARGIN", MARGIN);
		contentValues.put("M_FLAG","U");
		contentValues.put("DISCOUNT_PERCENT",DISCOUNT);
		contentValues.put("S_FLAG","0");

		db.update("retail_store_prod", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});
		String update_product = "UPDATE retail_store_prod SET S_PRICE = "+ "'" + PRODUCTSELLING + "'" + " ,POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " ,P_PRICE = " + "'" + PRODUCTPURCHASE + "'"+ " ,INTERNET_PRICE = " + "'" + PRODUCTINTERNET + "'" +  " ,IS_PROD_REL_INT = " + "'" + PRODUCTRELEVANT + "'" +   " ,ACTIVE = " + "'" + ACTIVE + "'" +   " ,PROFIT_MARGIN = " + "'" + MARGIN + "'" +    " ,DISCOUNT_PERCENT = " + "'" + DISCOUNT + "'" + " WHERE PROD_ID= " + "'" + String.valueOf(PRODUCTPRODUCTID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_product);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}

	//!!!!!!!!!!!************UPDATE LOCALPRODUCT************!!!!!!!!!!!!!!!!!!!!!!!!!
	public boolean updatelocalProduct(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String ACTIVE, String MARGIN,String username,String DISCOUNT,String uom) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("PROD_ID", PRODUCTLOCALPRODUCTID);
		contentValues.put("PROD_NM", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BARCODE", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_PRICE", PRODUCTLOCALSELLING);
		contentValues.put("P_PRICE", PRODUCTLOCALPURCHASE);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("PROFIT_MARGIN", MARGIN);
		contentValues.put("POS_USER",username);
		contentValues.put("M_FLAG","U");
		contentValues.put("DISCOUNT_PERCENT",DISCOUNT);
		contentValues.put("SELLING_ORDER_UNIT",uom);
		contentValues.put("CONV_FACT","1");
		contentValues.put("S_FLAG","0");
		db.update("retail_store_prod_local", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});

		String update_local_product = "UPDATE retail_store_prod_local SET PROD_ID = "+ "'" + PRODUCTLOCALPRODUCTID + "'" + " ,PROD_NM = " + "'" + PRODUCTLOCALPRODUCTNAME + "'" + ",M_FLAG = 'U' " + " ,BARCODE = " + "'" + PRODUCTLOCALBARCODE + "'"+ " ,MRP = " + "'" + PRODUCTLOCALMRP + "'" +  " ,S_PRICE = " + "'" + PRODUCTLOCALSELLING + "'" +   " ,ACTIVE = " + "'" + ACTIVE + "'" +   " ,PROFIT_MARGIN = " + "'" + MARGIN + "'" +    " ,DISCOUNT_PERCENT = " + "'" + DISCOUNT + "'" +  " ,P_PRICE = " + "'" + PRODUCTLOCALPURCHASE + "'" +   " ,SELLING_ORDER_UNIT = " + "'" + uom +  "'" + " ,POS_USER = " + "'" + username +  "'" + " WHERE PROD_ID= " + "'" + String.valueOf(PRODUCTLOCALPRODUCTID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_local_product);
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;


	}

	public boolean   updateVendor(String VENDORID, String VENDORACTIVE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("ACTIVE", VENDORACTIVE);
		contentValues.put("POS_USER", username);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");


		db.update("retail_str_dstr", contentValues, "DSTR_ID = ? ", new String[]{String.valueOf(VENDORID)});

		String update_vendor =  "UPDATE retail_str_dstr SET ACTIVE = " + "'" + VENDORACTIVE + "'" + " ,POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " WHERE DSTR_ID = " + "'" + String.valueOf(VENDORID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_vendor);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}

	//************************!!!!!UPDATE LOCAL VENDOR!!!!!!!*****************************************
	public boolean updatelocalVendor(String LOCALVENDORID, String LOCALVENDORNAME, String LOCALVENDORCONTACT, String LOCALVENDORADDRESS,String LOCALVENDORCOUNTRY,String LOCALVENDORCITY, String LOCALVENDORMOBILE,String Spinvalue, String invspinValue, String LOCALVENDORZIP, String LOCALVENDORTELE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("VEND_ID", LOCALVENDORID);
		contentValues.put("POS_USER",username);

		contentValues.put("VEND_NM", LOCALVENDORNAME);
		contentValues.put("VEND_CNTCT_NM", LOCALVENDORCONTACT);
		contentValues.put("ADD_1", LOCALVENDORADDRESS);
		contentValues.put("CITY", LOCALVENDORCITY);
		contentValues.put("CTR_DESC", LOCALVENDORCOUNTRY);
		contentValues.put("MOBILE", LOCALVENDORMOBILE);
		contentValues.put("VEND_INV",invspinValue );
		contentValues.put("ZIP", LOCALVENDORZIP);
		contentValues.put("ACTIVE", Spinvalue);
		contentValues.put("TELE", LOCALVENDORTELE);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");


		int SqlValue = db.update("retail_store_vendor", contentValues, "VEND_ID = ? ", new String[]{String.valueOf(LOCALVENDORID)});
		String update_local_vendor = "UPDATE retail_store_vendor SET VEND_NM = " + "'" + LOCALVENDORNAME + "'" + ",M_FLAG = 'U' " + " ,VEND_CNTCT_NM = " + "'" + LOCALVENDORCONTACT + "'"+ " ,ADD_1 = " + "'" + LOCALVENDORADDRESS + "'" +  " ,CITY = " + "'" + LOCALVENDORCITY + "'" +   " ,ACTIVE = " + "'" + Spinvalue + "'" + " ,CTR_DESC = " + "'" + LOCALVENDORCOUNTRY + "'" +  " ,MOBILE = " + "'" + LOCALVENDORMOBILE + "'" +   " ,VEND_INV = " + "'" + invspinValue +  "'" +  " ,TELE = " + "'" + LOCALVENDORTELE +  "'" + " WHERE VEND_ID= " + "'" + String.valueOf(LOCALVENDORID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_local_vendor);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		if (SqlValue < 1) {
			Log.e("UpdateFailLocalVendor:", String.valueOf(SqlValue));
			return false;
		}
		return true;
	}

	// !!!!!!!!!!!!!*********INSERT DAYOPEN*****!!!!!!!!!!!!!!!
	public boolean insertDayopen(String transid,String STOREID, String STARTCASH,String username ) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", transid);
		contentValues.put("POS_USER",username);
		contentValues.put("STORE_ID", STOREID);
		contentValues.put("BUSINESS_DATE", getDate());
		contentValues.put("START_DATE", getDate());
		contentValues.put("START_CASH", STARTCASH);
		contentValues.put("FLAG", "1");
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_day_open_close", null, contentValues);
		return true;
	}


	// *************************!!!!!!!! CURRENT DATE  !!!!!!!!!!!!!!********************************
	public String getDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	// *************************!!!!!!!! CURRENT TIME !!!!!!!!!!!!!!********************************
	public String getTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST RECORD!!!!!!!!*****************************
	public boolean CheckIsDataAlreadyInDBorNot(String Phone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Phone + "%";
		String Query = ("select MOBILE_NO from retail_cust where " + " MOBILE_NO like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST RECORD!!!!!!!!*****************************
	public boolean CheckBatchAlreadyInInventory(String BATCH_NO) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = BATCH_NO + "%";
		String Query = ("select BATCH_NO from retail_str_stock_master where " + " BATCH_NO like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Date!!!!!!!!*****************************
	public boolean CheckDateAlreadyInDBorNot(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = Phone + "%";
		//String Query =("select START_DATE from retail_str_day_open_close where START_DATE = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT START_CASH from retail_str_day_open_close where FLAG  like  '1%' AND START_DATE = date('now')", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	public boolean CheckTenderDateAlreadyInDBorNot(String Tri_Id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("SELECT START_CASH from retail_str_day_open_close where " + " TRI_ID =  '" + Tri_Id + "'  And FLAG  like  '0%'  AND START_DATE= date('now') AND TRI_ID = +'Tri_ID'", null);
		Log.e("Query::", "select TRI_ID from  retail_str_day_open_close where  FLAG  like  '0%'  AND START_DATE= date('now')");
		if (cursor.getCount() <= 0)

		{

			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	// ******************************CODE TO GET TENDER****************************
	public Cursor gettender() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select START_CASH ,TRI_ID  from retail_str_day_open_close where START_DATE= date('now')", null);
		return cursor;
	}

	//******************************CODE TO UPDATE TENDER******************************
	public boolean updatedayclose(String TRANSACTIONID, String CBCASH_ONHAND,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("CBCASH_ONHAND", CBCASH_ONHAND);
		contentValues.put("POS_USER",username);
		contentValues.put("FLAG", "0");
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("retail_str_day_open_close", contentValues, "TRI_ID = ? ", new String[]{String.valueOf(TRANSACTIONID)});
		return true;

	}

	//***************************for retail care define*********************************
	public Cursor getdata(String Store_Id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_Card_define where STORE_ID = " + Store_Id + "", null);
		return res;
	}

	//******************************for rule defination*******************************************
	public Cursor getdatarule(String Store_Id_rule) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from rule_defination where STORE_ID = " + Store_Id_rule + "", null);
		return res;
	}

	//*************************************************************************************************************************
//****************************retrive data for retail_card_define**********************************************
	public ArrayList<String> getdata1() {
		ArrayList<String> updatelist1 = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from retail_card_define ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					updatelist1.add(res.getString(res.getColumnIndex(STORE_ID_CARD)));

				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return updatelist1;
	}

	//****************************************retrive data for rule_defination************************
	public ArrayList<String> getdata2() {
		ArrayList<String> updatelist2 = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from rule_defination ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					updatelist2.add(res.getString(res.getColumnIndex(STORE_ID_RULE)));

				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return updatelist2;
	}

	//*************************update fr retail card define****************************************
	public boolean updateContact(String STORE_ID, String POINTS_RANGE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("POINTS_RANGE", POINTS_RANGE);
		contentValues.put("POS_USER",username);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("retail_Card_define", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(STORE_ID)});
		return true;
	}

	//**********************************update for rule defination*****************************************************
	public boolean updateContact2(String STORE_ID, String BASE_VALUE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("BASE_VALUE", BASE_VALUE);
		contentValues.put("POS_USER",username);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("rule_defination", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(STORE_ID)});
		String update_Contact2 = "UPDATE rule_defination SET BASE_VALUE = " + "'" + BASE_VALUE + "'" + ",POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' "  +  " WHERE STORE_ID= " + "'" + String.valueOf(STORE_ID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",update_Contact2);
			login logi = new login();
			login.sendMessage(update_Contact2);
		}catch (Exception e){}
		return true;
	}

//*******************************************insert data for reports****************************************

	public boolean insertdata3(String STORE_ID, String SNO, String REPORT_NAME, String REPORT_CRITERIA, String COMMENTS,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("STORE_ID", STORE_ID);
		contentValues.put("POS_USER",username);
		contentValues.put("SNO", SNO);
		contentValues.put("REPORT_NAME", REPORT_NAME);
		contentValues.put("REPORT_CRITERIA", REPORT_CRITERIA);
		contentValues.put("COMMENTS", COMMENTS);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_store_reports", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;
	}

//******************************retrive data for reports*********************************************************

	public ArrayList<String> getdatareports() {
		ArrayList<String> updatelist3 = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				updatelist3.add(res.getString(res.getColumnIndex(STORE_ID_REPORT)));
				Log.e(TAG, "inside do function");
			} while (res.moveToNext());
		}

		return updatelist3;
	}

	public ArrayList<LoyalityModel> getLoyalityReport() {
		ArrayList<LoyalityModel> arrayList1 = new ArrayList();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_cust_loyalty", null);

		if (cur.moveToFirst()) {
			do {
				LoyalityModel lm = new LoyalityModel();
				lm.setCust_id(cur.getString(cur.getColumnIndex(COLUMN_CUSTID)));
				lm.setMobile_No(cur.getString(cur.getColumnIndex(COLUMN_MOBILENO1)));
				lm.setName(cur.getString(cur.getColumnIndex(COLUMN_NAME)));
				lm.setPoints_Earned(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSEARNED)));
				lm.setPoints_Redeemed(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSREDEEMED)));
				lm.setPoints_Available(cur.getFloat(cur.getColumnIndex(COLUMN_POINTSAVALILABLE)));
				arrayList1.add(lm);

			} while (cur.moveToNext());

		}
		return arrayList1;
	}

	public ArrayList<RetailcardDefineModel> getRetail_Card_Define() {
		ArrayList<RetailcardDefineModel> arrayList = new ArrayList<RetailcardDefineModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select STORE_ID,ID,CARD_TYPE,POINTS_RANGE from retail_card_define", null);

		if (cursor.moveToFirst()) {
			do {
				RetailcardDefineModel rdm = new RetailcardDefineModel();
				rdm.setSTORID(cursor.getString(cursor.getColumnIndex(COLUMN_STOREID_RETAIL_CARD_DEFINE)));
				rdm.setCARDTYPE(cursor.getString(cursor.getColumnIndex(COLUMN_CARD_TYPE)));
				rdm.setID(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
				rdm.setPOINTSRANGE(cursor.getString(cursor.getColumnIndex(COLUMN_POINTS_RANGE)));
				arrayList.add(rdm);
			} while (cursor.moveToNext());
		}
		return arrayList;
	}

	public ArrayList<RuleDefinationModel> getRuleDefination() {
		ArrayList<RuleDefinationModel> arraylist = new ArrayList<RuleDefinationModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from rule_defination", null);
		if (cur.moveToFirst()) {
			do {
				RuleDefinationModel rm = new RuleDefinationModel();
				rm.setStoreID(cur.getString(cur.getColumnIndex(COLUMN_Store_ID_RULE_DEFINAATION)));
				rm.setSno(cur.getString(cur.getColumnIndex(COLUMN_SNO)));
				rm.setCARD_TYPE(cur.getString(cur.getColumnIndex(COLUMN_CARDTYPE)));
				rm.setBASEVALUE(cur.getFloat(cur.getColumnIndex(COLUMN_BASE_VALUE)));
				rm.setPerTONPOINTS(cur.getFloat(cur.getColumnIndex(COLUMN_PER_TON_POINTS)));
				arraylist.add(rm);
			} while (cur.moveToNext());
		}

		return arraylist;
	}

	public ArrayList<ProductAuto> getProductDetailsFromBarcode(String barcodeString) {

		ArrayList<ProductAuto> productAutoArrayList = new ArrayList<ProductAuto>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = barcodeString + "%";
			Cursor res = db.rawQuery("select distinct STORE_ID,PROD_ID,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,TAX_DESC,MFG,PURCH_ORDER_UNIT,SELLING_ORDER_UNIT,INTERNET_PRICE,IS_PROD_REL_INT,ACTIVE ,PROFIT_MARGIN,DISCOUNT_PERCENT from retail_store_prod where"
					+ " BARCODE like ? ", params);

			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {
					ProductAuto product = new ProductAuto();product.setAutoStoreId(res.getString(res.getColumnIndex(PRODUCTSTOREID)));
					product.setAutoProductId(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					product.setAutoProductname(res.getString(res.getColumnIndex(PRODUCTNAME)));
					product.setAutoMrp(res.getString(res.getColumnIndex(PRODUCTMRP)));
					//product.setAutoMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setAutoInternetPrice(res.getString(res.getColumnIndex(PRODUCTINTERNET)));
					//product.setAutoMeasure(res.getString(res.getColumnIndex(PRODUCTMEASURE)));
					product.setAutoMeasureunit(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					product.setAutoPackingUnit(res.getString(res.getColumnIndex(PRODUCTPACKINGUNIT)));
					product.setAutoPurchaseprice(res.getString(res.getColumnIndex(PRODUCTPURCHASE)));
					product.setAutoSellingprice(res.getString(res.getColumnIndex(PRODUCTSELLING)));
					product.setAutoManuf(res.getString(res.getColumnIndex(PRODUCTMANUF)));
					product.setAutoBarcode(res.getString(res.getColumnIndex(PRODUCTBARCODE)));
					// product.setAutoStrength(res.getString(res.getColumnIndex(PRODUCTSTRENGTH)));
					product.setAutoTax(res.getString(res.getColumnIndex(PRODUCTTAXDESC)));
					product.setAutoInternetrel(res.getString(res.getColumnIndex(PRODUCTRELEVANT)));
					product.setAutoActive(res.getString(res.getColumnIndex(PRODUCTACTIVE)));
					product.setMargin(res.getString(res.getColumnIndex(PRODUCTMARGIN)));
					product.setAutodiscount(res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));

					productAutoArrayList.add(product);


				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productAutoArrayList;
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Cash!!!!!!!!*****************************
	public boolean CheckCashInHandAlreadyInDBorNot(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = Phone + "%";
		//String Query =("select START_DATE from retail_str_day_open_close where START_DATE = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT FLAG from retail_str_day_open_close where FLAG  like '0%'", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public boolean insertdetailofpurchase(String Po_No, String Prod_Id, String Prod_Nm, String ProductPrice, String Qty, String Total) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", Po_No);
		contentValues.put("PROD_NM", Prod_Nm);
		contentValues.put("PROD_ID", Prod_Id);
		contentValues.put("P_PRICE", ProductPrice);
		contentValues.put("QTY",Qty);
		contentValues.put("TOTAL", Total);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");

		db.insert("retail_str_po_detail", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return true;
	}

	public ArrayList<PurchaseProductModel> getProductdata(String idorName) {
		ArrayList<PurchaseProductModel> productlist = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
         /*params[2] = idorName + "%";*/
			// params[3] = idorName + "%";
         /*Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,SELLING_ORDER_UNIT,MRP,PROFIT_MARGIN,S_PRICE,BARCODE,ACTIVE,CONV_FACT,IND_NM from retail_store_prod_com where "
               + "   PROD_NM  like ?  and ACTIVE = 'Y%'  or BARCODE like ? "
               , params);*/
			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,SELLING_ORDER_UNIT,MRP,PROFIT_MARGIN,S_PRICE,BARCODE,ACTIVE,CONV_FACT,IND_NM,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ "  PROD_NM  like ? and ACTIVE like'Y%' or BARCODE like ?  "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					// pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setPurchasediscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));

					//pm.setGetConMulQty(productcursor.getFloat(productcursor.getColumnIndex(TOTALQTY)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	ArrayList<VendorModel> getVendorNameforPurchase(String name) {
		ArrayList<VendorModel> vendorlist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select VEND_DSTR_NM from retail_vend_dstr  where"
							+ " VEND_DSTR_NM like ?  And ACTIVE like 'Y%' "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel(name);
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_VEND_DSTR_NAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;

	}

	ArrayList<VendorModel> getVendorName(String name) {
		ArrayList<VendorModel> vendorlist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_str_grn_master  where"
							+ " VEND_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel(name);
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	public ArrayList<String> getStoreid() {
		ArrayList<String> getstoreid = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select * from retail_store ", null);

			Log.e("Message", "############## data fetched  and result is " + res);
			if (res.moveToFirst())
				Log.e(TAG, "Get STORE_ID from retail_store table ");
			{
				do {
					getstoreid.add(res.getString(res.getColumnIndex(STOREID)));

				} while (res.moveToNext());
			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return getstoreid;
	}

	//POS USER ADD
	public void InsertAdTicker(String AdMainId, String AdDesc, String AdText, String AdStartDate, String AdEndDate, String Status,String username) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			Log.e("########", "Ad_Main_Id in database:" + AdMainId);
			Log.e("########", "Ad_Desc:" + AdDesc);
			Log.e("#######", "Ad_Text in database:" + AdText);
			Log.e("#######", "Ad_Strt_Dt in database:" + AdStartDate);
			Log.e("#######", "Ad_End_Dt in database:" + AdEndDate);
			Log.e("#######", "Status in database:" + Status);
			cv.put("AD_MAIN_ID", AdMainId);
			cv.put("AD_DESC", AdDesc);
			//POS USER ADD
			cv.put("POS_USER",username);
			cv.put("AD_TEXT", AdText);
			cv.put("AD_STRT_DT", AdStartDate);
			cv.put("AD_END_DT", AdEndDate);
			cv.put("STATUS", Status);
			cv.put("M_FLAG","I");
			cv.put("S_FLAG","0");



			long result = db.insert("ad_ticker_main", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
			//db.close();
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
		//db.close();
	}


	ArrayList<Inventorymodel> getInventoryName(String name) {
		ArrayList<Inventorymodel> vendorlist = new ArrayList<Inventorymodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select VEND_DSTR_NM from retail_vend_dstr "+
							" where"+ " VEND_DSTR_NM like ?  AND ACTIVE like 'Y%'"
					, params);

			if (cursor.moveToFirst()) {
				do {
					Inventorymodel pm = new Inventorymodel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_VEND_DSTR_NAME)));
					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	// Adding new records to customer table
	public void insertinventory(String STORE_ID_MASTER, String PRODUCT_ID, String BATCH_NO, String EXP_DATE, String PPRICE, String S_PRICE, String QUANTITY, String MRP, String AMOUNT, String UOM) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("STORE_ID", STORE_ID_MASTER);
		values.put("PROD_ID", PRODUCT_ID);
		values.put("BATCH_NO", BATCH_NO);
		values.put("EXP_DATE", EXP_DATE);
		values.put("P_PRICE", PPRICE);
		values.put("S_PRICE", S_PRICE);
		values.put("QTY", QUANTITY);
		values.put("MRP", MRP);
		values.put("AMOUNT", AMOUNT);
		values.put("UOM", UOM);
		values.put("M_FLAG","I");
		values.put("S_FLAG","0");
		// Inserting Row
		db.insert("retail_str_stock_master", null, values);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
	}

	public void savePurchaseList(ArrayList<PurchaseProductModel> list, String invoiceNumber, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();



		if (list == null || invoiceNumber == null) {
			return;
		}

		for (PurchaseProductModel prod : list) {
			ContentValues contentValues = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);

			contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			contentValues.put("POS_USER",username);
			contentValues.put("PO_NO", invoiceNumber);
			contentValues.put("VENDOR_NM", VendorName);
			contentValues.put("PROD_NM", prod.getProductName());
			contentValues.put("PROD_ID", prod.getProductId());
			contentValues.put("MRP", prod.getMRP());
			contentValues.put("UOM", prod.getUom());
			contentValues.put("S_PRICE", prod.getSellingPrice());
			contentValues.put("P_PRICE", prod.getProductPrice());
			contentValues.put("BARCODE", prod.getBarcode());
			contentValues.put("PROFIT_MARGIN", prod.getProfit_Margin());
			contentValues.put("CONV_FACT", prod.getConversionfactor());
			contentValues.put("IND_NM", prod.getIndusteryname());
			contentValues.put ("PURCHASEDATE",getDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("DISCOUNT_PERCENT",prod.getPurchasediscount());
			contentValues.put("PURCHASE_UNIT_CONV",prod.getPurchaseunitconv());
			contentValues.put("S_FLAG","0");


			if (prod.getProductQuantity() != 0) {
				contentValues.put("QTY", prod.getProductQuantity());

			} else {
				Log.e("Database Operation", "row  not inserted...");
				continue;
			}
			contentValues.put("TOTAL", prod.getTotal());


			db.insert("retail_str_po_detail", null, contentValues);
			String savePurchaseList = "insert into retail_str_po_detail ( STORE_ID , POS_USER , PO_NO , VENDOR_NM , PROD_NM , PROD_ID , MRP , UOM, S_PRICE , P_PRICE, BARCODE, PROFIT_MARGIN, CONV_FACT, IND_NM ,PURCHASEDATE , M_FLAG , DISCOUNT_PERCENT , PURCHASE_UNIT_CONV , S_FLAG , QTY, TOTAL) values (" + "'" + PersistenceManager.getStoreId(mycontext) + "' ," + "'" + username + "' ," + "'" + invoiceNumber + "'," + "'" + VendorName + "'," + "'" + prod.getProductName() + "'," + "'" + prod.getProductId() + "'," + "'" + prod.getMRP() + "'," + "'" + prod.getUom() + "'," + "'" + prod.getSellingPrice() + "'," + "'" + prod.getProductPrice() + "'," + "'" + prod.getBarcode() + "'," + "'" + prod.getProfit_Margin() + "'," + "'" + prod.getConversionfactor() + "'," + "'" +
					prod.getIndusteryname() + "'," + "'" + getDate() + "'," + "'"  + "I'," + "'" + prod.getPurchasediscount() + "'," + "'" + prod.getPurchaseunitconv() + "'," + "'" + "0'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getTotal() + "')";

			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query", savePurchaseList);

				login logi = new login();
				login.sendMessage(jsonObject.toString());
				Log.e("Slavemsgg@@@@@",jsonObject.toString());
				savePurchaseList = null;
			}catch (Exception e){}




		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}



/*
      String aman(ArrayList<PurchaseProductModel> list, String invoiceNumber, String VendorName,String username) {
      SQLiteDatabase db = this.getWritableDatabase();



      for (PurchaseProductModel prod : list) {
         ContentValues contentValues = new ContentValues();
         PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
         PersistenceManager.getStoreId(mycontext);

         contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
         contentValues.put("POS_USER",username);
         contentValues.put("PO_NO", invoiceNumber);
         contentValues.put("VENDOR_NM", VendorName);
         contentValues.put("PROD_NM", prod.getProductName());
         contentValues.put("PROD_ID", prod.getProductId());
         contentValues.put("MRP", prod.getMRP());
         contentValues.put("UOM", prod.getUom());
         contentValues.put("S_PRICE", prod.getSellingPrice());
         contentValues.put("P_PRICE", prod.getProductPrice());
         contentValues.put("BARCODE", prod.getBarcode());
         contentValues.put("PROFIT_MARGIN", prod.getProfit_Margin());
         contentValues.put("CONV_FACT", prod.getConversionfactor());
         contentValues.put("IND_NM", prod.getIndusteryname());
         contentValues.put ("PURCHASEDATE",getDate());
         contentValues.put("M_FLAG","I");
         contentValues.put("DISCOUNT_PERCENT",prod.getPurchasediscount());
         contentValues.put("PURCHASE_UNIT_CONV",prod.getPurchaseunitconv());
         contentValues.put("S_FLAG","0");


         if (prod.getProductQuantity() != 0) {
            contentValues.put("QTY", prod.getProductQuantity());

         } else {
            Log.e("Database Operation", "row  not inserted...");
            continue;
         }
         contentValues.put("TOTAL", prod.getTotal());


         db.insert("retail_str_po_detail", null, contentValues);
         login logi = new login();
         logi.sendMessage(aman());


      }


      db.close(); // Closing database connection
      Log.e("Database Operation", "row inserted...");
      return;
   }*/

	public void saveHoldPurchaseList(ArrayList<PurchaseProductModel> list, String invoiceNumber, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null || invoiceNumber == null) {
			return;
		}

		for (PurchaseProductModel prod : list) {
			ContentValues contentValues = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			contentValues.put("PO_NO", invoiceNumber);
			contentValues.put("VENDOR_NM", VendorName);
			contentValues.put("PROD_NM", prod.getProductName());
			contentValues.put("PROD_ID", prod.getProductId());
			contentValues.put("MRP", prod.getMRP());
			contentValues.put("UOM", prod.getUom());
			contentValues.put("S_PRICE", prod.getSellingPrice());
			contentValues.put("P_PRICE", prod.getProductPrice());
			contentValues.put("BARCODE", prod.getBarcode());
			contentValues.put("PROFIT_MARGIN", prod.getProfit_Margin());
			contentValues.put("CONV_FACT", prod.getConversionfactor());
			contentValues.put("IND_NM", prod.getIndusteryname());
			contentValues.put("M_FLAG","I");
			if (prod.getProductQuantity() != 0) {
				contentValues.put("QTY", prod.getProductQuantity());
			} else {
				Log.e("Database Operation", "row  not inserted...");
				continue;
			}
			contentValues.put("TOTAL", prod.getTotal());
			contentValues.put("FLAG", "H");


			db.insert("retail_str_po_detail_hold", null, contentValues);
			String save_Hold_PurchaseList = "insert into retail_str_po_detail_hold ( STORE_ID , PO_NO , VENDOR_NM , PROD_NM , PROD_ID , MRP , UOM, S_PRICE , P_PRICE, BARCODE, PROFIT_MARGIN, CONV_FACT, IND_NM , M_FLAG , QTY, TOTAL , FLAG) values (" + "'" + PersistenceManager.getStoreId(mycontext) + "' ," + "'"  + invoiceNumber + "'," + "'" + VendorName + "'," + "'" + prod.getProductName() + "'," + "'" + prod.getProductId() + "'," + "'" + prod.getMRP() + "'," + "'" + prod.getUom() + "'," + "'" + prod.getSellingPrice() + "'," + "'" + prod.getProductPrice() + "'," + "'" + prod.getBarcode() + "'," + "'" + prod.getProfit_Margin() + "'," + "'" + prod.getConversionfactor() + "'," + "'" +
					prod.getIndusteryname() + "'," + "'" + "I'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getTotal() + "'," + "'" + " H')";

			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query", save_Hold_PurchaseList);
				login logi = new login();
				login.sendMessage(String.valueOf(jsonObject));
			}catch (Exception e){}

		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public ArrayList<String> getinventorystore() {
		ArrayList<String> storelist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store_prod_com ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				storelist.add(res.getString(res.getColumnIndex(STORE_ID_INVENTORY)));
			} while (res.moveToNext());
		}

		return storelist;
	}

	// ***********************!!!!!!VALIDATION TO CHECK EXIST Cash!!!!!!!!*****************************
	public boolean CheckDayOpenforPurchase(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT START_DATE from retail_str_day_open_close  where START_DATE = ' ' ", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;
	}

	public boolean CheckValidationForPurchase(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT FLAG from retail_str_day_open_close where START_DATE = date('now')", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;


	}

	public void saveInventoryList(String STORE_ID_MASTER, ArrayList<Inventoryproductmodel> list, String BATCH_NO, String EXP_DATE) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}

		for (Inventoryproductmodel prod : list) {
			ContentValues values = new ContentValues();
			values.put("STORE_ID", STORE_ID_MASTER);
			values.put("PROD_ID", prod.getProductId());
			values.put("PROD_NM", prod.getProductName());
			values.put("BATCH_NO", BATCH_NO);
			values.put("EXP_DATE", EXP_DATE);
			values.put("P_PRICE", prod.getPprice());
			values.put("S_PRICE", prod.getSprice());
			values.put("QTY", prod.getProductQuantity());
			values.put("MRP", prod.getMrp());
			values.put("AMOUNT", prod.getTotal());
			values.put("UOM", prod.getTax());
			values.put("M_FLAG","I");
			values.put("S_FLAG","0");
			// Inserting Row
			db.insert("retail_str_stock_master", null, values);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}
	public void savesalesListdetail(String TRANS_ID, ArrayList<Sales> list,String username,String imeino,String Discount) {
		SQLiteDatabase db = this.getWritableDatabase();
		int i = login.setboolean();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		String st= PersistenceManager.getStoreId(mycontext);;
		if (list == null) {
			return;
		}
		for (Sales sales : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("TRI_ID", TRANS_ID);
			contentValues.put("STORE_ID", st.substring(0,10));
			// contentValues.put("INVOICE_NO", INVOICE);
			contentValues.put("BATCH_NO", sales.getBatchNo());
			contentValues.put("POS_USER",username);
			contentValues.put("PROD_NM", sales.getProductName());
			contentValues.put("EXP_DATE", sales.getExpiry());
			contentValues.put("PROD_ID", sales.getProdid());


			contentValues.put("S_PRICE", sales.getSPrice());
			contentValues.put("TAX_VALUE3",sales.getPPrice());//here the value insert of pprice
			contentValues.put("M_FLAG","I");
			contentValues.put("EX_TRI_ID",imeino);
			contentValues.put("QTY", sales.getQuantity());
			contentValues.put("MRP", sales.getMrp());
			contentValues.put("UOM", sales.getUom());
			contentValues.put ("SALE_DATE",getDate());
			contentValues.put("TOTAL", sales.getTotal()-sales.getDiscountamountsalestotal());
			contentValues.put("DISCOUNT_PERCENT",Discount);
			contentValues.put("LINE_DISC",sales.getDiscountsales());
			contentValues.put("LINE_ITEM_DISC",sales.getDiscountamountsalestotal());
			contentValues.put("S_FLAG","0");
			contentValues.put("FLAG","NULL");
			if (i==0)
			{
				contentValues.put("SLAVE_FLAG","0");
			}else {
				contentValues.put("SLAVE_FLAG", "1");
				String Sales_ProductDetail = "insert into retail_str_sales_detail( TRI_ID , STORE_ID ,BATCH_NO , PROD_NM , EXP_DATE, POS_USER , S_FLAG , M_FLAG ,S_PRICE,EX_TRI_ID,QTY,MRP,UOM,SALE_DATE,TOTAL,DISCOUNT_PERCENT,LINE_DISC,SLAVE_FLAG) values (" + "'" + TRANS_ID + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + sales.getBatchNo() + "'," + "'" + sales.getProductName() + "'," + "'" + sales.getExpiry() + "'," + "'" + username + "'," + "'0'," + "'I'," + "'" + sales.getSPrice() + "'," + "'" + imeino + "'," + "'" + sales.getQuantity() + "'," + "'" + sales.getMrp() + "'," + "'" + sales.getUom() + "'," + "'" + getDate() + "'," + "'" + sales.getTotal() + "'," + "'" + Discount + "'," + "'" + sales.getDiscountsales() + "'," + "'1')";
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", Sales_ProductDetail);
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}
			}
			db.insert("retail_str_sales_detail", null, contentValues);
		}
		if(i==3)
		{
			login.bluetoothdatadetails();
			updateSlaveFlagsalesdetail();
			//db.execSQL("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		}
		db.close(); // Closing database connection
		Log.e("Database Operation for", "row inserted...");
		return;
	}
	public boolean insertdetailsifpaybaycash(String TRANS_ID, String GrandTotal,String username,String imeino,String Discount,String totalamount,String totaldiscount,String FinalAmount, String ExpectedChange,String RecieveAmount,String CustNm,String DoctNm,String billprint) {

		SQLiteDatabase db = this.getWritableDatabase();
		int i = login.setboolean();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		String st = PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", TRANS_ID);
		contentValues.put("STORE_ID", st.substring(0,10));
		//contentValues.put("INVOICE_NO", INVOICENO);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("POS_USER",username);
		contentValues.put("BUSINESS_DATE", getDate());
		contentValues.put("SALE_DATE", getDate());
		contentValues.put("SALE_TIME", getTime());
		contentValues.put("M_FLAG","I");
		contentValues.put("EX_TRI_ID",imeino);
		contentValues.put("DISCOUNT_PERCENT",Discount);
		contentValues.put("S_Flag","0");
		contentValues.put("CUST_NAME",CustNm);
		contentValues.put("DOCT_NAME",DoctNm);
		contentValues.put("SAVETOTALBILLAMOUNT",totalamount);
		contentValues.put("SAVETOTALBIllDISCOUNT",totaldiscount);
		contentValues.put("SAVEFINALBILLAMOUNT",FinalAmount);
		contentValues.put("SAVERECEIVEDBILLAMOUNT",RecieveAmount);
		contentValues.put("SAVEEXPECTEDBILLAMOUNT",ExpectedChange);
		contentValues.put("FLAG",billprint);


		if (i==0)
		{
			contentValues.put("SLAVE_FLAG","0");

		}else{
			contentValues.put("SLAVE_FLAG","1");
		//	login.bluetoothdataMaster();
		//	updateSlaveFlagsalesMaster();
			String Sales_ProductMaster = "insert into retail_str_sales_master( TRI_ID , STORE_ID ,GRAND_TOTAL, POS_USER , S_FLAG , M_FLAG ,EX_TRI_ID,BUSINESS_DATE,SALE_TIME,SALE_DATE,DISCOUNT_PERCENT,SLAVE_FLAG) values (" + "'" + TRANS_ID + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + GrandTotal + "'," + "'" + username + "'," + "'0'," + "'I'," + "'" + imeino + "'," + "'" + getDate() + "'," + "'" + getTime()+ "','" + getDate() + "'," + "'" + Discount + "'," + "'1')";
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query", Sales_ProductMaster);
				login.sendMessage(String.valueOf(jsonObject));
			}catch (Exception e){}
		}
		db.insert("retail_str_sales_master", null, contentValues);


		if(i==3)
		{
			login.bluetoothdataMaster();
			updateSlaveFlagsalesMaster();

		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted Master...");
		return true;
	}


	public boolean updatedetailsofcreditcustcash(String TRANS_ID,String GrandTotal,String username) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("INVOICE_NO", TRANS_ID);
		contentValues.put("POS_USER",username);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("FLAG", "0");
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		int sqlUpdateRetval = db.update("retail_credit_cust", contentValues, "Invoice_No = ? "
				, new String[]{TRANS_ID.toString()});

		// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentValues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}
	public ArrayList<Sales> getSalesDetailsFromBarcode(String nameOrPhone) {

		ArrayList<Sales> salesproductlist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = nameOrPhone + "%";
			//params[1] = nameOrPhone + "%";
			Cursor res = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,QTY,MRP,AMOUNT,UOM,S_PRICE from retail_str_stock_master where "
					+ " BARCODE like ? ", params);
			if (res.moveToFirst()) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(res.getString(res.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(res.getString(res.getColumnIndex(EXPIRY)));
					saleslist.setQuantity(res.getInt(res.getColumnIndex(QUANTITY)));
					saleslist.setMrp(res.getFloat(res.getColumnIndex(MRP)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(res.getString(res.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(res.getFloat(res.getColumnIndex(S_PRICE)));
					salesproductlist.add(saleslist);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesproductlist;
	}

	public ArrayList<Salesreturndetail> getSalesReturn(String invoiceno) {

		ArrayList<Salesreturndetail> salesreturnlist = new ArrayList<Salesreturndetail>();
		try {

			/*String[] params = new String[1];
			params[0] = invoiceno + "%";*/

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,MRP,UOM,TOTAL,DISCOUNT_PERCENT,LINE_DISC from retail_str_sales_detail where"
					+ "  TRI_ID ='"+invoiceno+"' and FLAG='NULL' " , null);
			if (res.moveToFirst()) {
				do {
					Salesreturndetail salesreturndetail = new Salesreturndetail();
//             salesreturndetail.setSaleTran sid(res.getString(res.getColumnIndex(RETURNTRANSIDs)));
					//salesreturndetail.setSaleBillno(res.getString(res.getColumnIndex(BILLNO)));
					salesreturndetail.setSaleproductname(res.getString(res.getColumnIndex(RETURNPRODUCTNAME)));
					salesreturndetail.setSaleexpiry(res.getString(res.getColumnIndex(RETURNEXPIRY)));
					salesreturndetail.setSalebatchno(res.getString(res.getColumnIndex(RETURNBATCHNO)));
					salesreturndetail.setSalesellingprice(res.getFloat(res.getColumnIndex(RETURNSELLINGPRICE)));
					salesreturndetail.setSaleqty(res.getInt(res.getColumnIndex(RETURNQUANTITY)));
					salesreturndetail.setSalemrp(res.getString(res.getColumnIndex(RETURNMRP)));
					salesreturndetail.setSaleuom(res.getString(res.getColumnIndex(RETURNUNITOFMEASURE)));
					salesreturndetail.setSaletotal(res.getFloat(res.getColumnIndex(RETURNGRANDTOTAL)));
					salesreturndetail.setDiscount (res.getString(res.getColumnIndex(DISCOUNT_PERCENT)));
					salesreturndetail.setSalediscoumt(res.getString(res.getColumnIndex(LINESALEDISC)));
					salesreturnlist.add(salesreturndetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}

	public ArrayList<PurchaseProductModel> getAllOLDPurchaseList(String OldInvoiceno) {
		ArrayList<PurchaseProductModel> OldInvoiceData = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = OldInvoiceno + "%";

			Cursor productcursor = db.rawQuery("select PO_NO,PROD_ID,PROD_NM,P_PRICE,MRP,UOM,QTY,TOTAL,VENDOR_NM,CONV_FACT,FLAG,S_PRICE,CONV_FACT,IND_NM ,PROFIT_MARGIN from retail_str_po_detail where "
							+ " PO_NO  like ?  "
					, params);
			if (OldInvoiceno == null) {
				return null;
			}
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));

					////find from here i want to do wwork from here
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_P_PRICE)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_UOM)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(Purchase_COLUMN_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_TOTAL)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_MRP)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_Conv_Fact)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN )));
					//pm.setGetConMulQty(productcursor.getFloat(productcursor.getColumnIndex("Status")));
					OldInvoiceData.add(pm);
				} while (productcursor.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return OldInvoiceData;
	}

	public ArrayList<VendorModel> getVendors() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_master", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}

	public ArrayList<PurchaseInvoiceDropDownModel> getLastInvoices(String VendorName,int i) {
		ArrayList<PurchaseInvoiceDropDownModel> LastInvoicelist = new ArrayList<PurchaseInvoiceDropDownModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = VendorName + "%";
			Cursor cursor = db.rawQuery("select distinct PO_NO,VENDOR_NM,LAST_MODIFIED from retail_str_po_master where "
							+ "VENDOR_NM  like ? ORDER BY PO_NO DESC limit '"+i+"' "
					, params);

			if (cursor.moveToFirst()) {
				do {
					PurchaseInvoiceDropDownModel purchaseInvoiceDropDownModel = new PurchaseInvoiceDropDownModel();
					purchaseInvoiceDropDownModel.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					purchaseInvoiceDropDownModel.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_LASTUPDATE)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void SaveGrandDataForPurchase(String InvoiceNo, String VendorName, String GrandTotal,String username) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("VENDOR_NM", VendorName);
		contentValues.put("POS_USER",username);
		contentValues.put("PO_NO", InvoiceNo);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("PO_DATE", getDate());
		contentValues.put("PURCHASEDATE",getDate());
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_po_master", null, contentValues);
		String save_grand_data_for_purchase = "insert into retail_str_po_master (STORE_ID , VENDOR_NM , POS_USER ,PO_NO ," +
				" GRAND_TOTAL , PO_DATE , PURCHASEDATE , M_FLAG , S_FLAG ) values ('" + PersistenceManager.getStoreId(mycontext)
				+ "','" + VendorName + "','" + username + "','" + InvoiceNo + "','" + GrandTotal + "','" + getDate() + "','" +
				getDate() + "','" + "I" + "','0')";



		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", save_grand_data_for_purchase);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data into MAster inserted...");
		return;
	}


	public ArrayList<ReportDistributorModel> getDistributorReport(String DistributorName) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = DistributorName + "%";
			Cursor cursor = db.rawQuery("select DSTR_NM, ACTIVE, POS_USER from retail_str_dstr  where"
							+ " DSTR_NM like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportDistributorModel> getDistributorName(String name) {
		ArrayList<ReportDistributorModel> distributornamelist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select DSTR_NM from retail_str_dstr  where"
							+ " DSTR_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					distributornamelist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return distributornamelist;
	}

	public ArrayList<Salesreturndetail> getReasonReturn() {

		ArrayList<Salesreturndetail> returnlist = new ArrayList<Salesreturndetail>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select REASON_RETURN from retail_store_sales_desc", null);
			if (res.moveToFirst()) {
				do {
					Salesreturndetail salesreturndetail = new Salesreturndetail();
					salesreturndetail.setReasons(res.getString(res.getColumnIndex(RETURNREASON)));
            /*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/
					returnlist.add(salesreturndetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<VendorModel> getvendorsforIndirectPayment() {
		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_store_vendor where "
							+ "ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_PAYMENT_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> selecttheReason() {
		ArrayList<String> Paymentreasons = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select  DESCRIPTION from tmp_retail_pay_desc", null);
			if (cursor.moveToFirst()) {
				do {
					Paymentreasons.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_REASONS_DESCRIPTION)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return Paymentreasons;
	}

	public void saveIndirectPayment(String PurchaseOrderNo, String vendorselected, String selectedReasons, String amount, String Resval,String username) {
		SQLiteDatabase db = this.getWritableDatabase();

   /* if(GrandTotal == null || InvoiceNo == null || VendorName == null) {
         return;
      }
*/PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);


            /*if(VendorModel prod: InvoiceNo){*/
		if (amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("PAY_ID",PurchaseOrderNo );
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

		contentValues.put("VEND_DSTR_NM", vendorselected);
		contentValues.put("POS_USER",username);
		contentValues.put("REASON_OF_PAY", selectedReasons);
		contentValues.put("AMOUNT", amount);
		contentValues.put("PAYMENT_ID",Resval );
		contentValues.put("PAYMENT_DATE",getDate());
		contentValues.put("RECEIVED_AMOUNT",amount);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("tmp_vend_dstr_payment", null, contentValues);
		String insert_saveIndirectPayment = "insert into tmp_vend_dstr_payment ( PAY_ID, STORE_ID ,VEND_DSTR_NM , POS_USER , REASON_OF_PAY, AMOUNT ,PAYMENT_ID  ,PAYMENT_DATE , RECEIVED_AMOUNT , M_FLAG ) values ('" + PurchaseOrderNo + "' , '" + PersistenceManager.getStoreId(mycontext) + "' , '" + vendorselected + "', '"  + username + "', '" +  selectedReasons + "' , '"  + amount + "' ,'" + Resval +  "' , '" + getDate() + "','" + amount + "', 'I')";
		try
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",insert_saveIndirectPayment);
			login logi = new login();
			login.sendMessage(insert_saveIndirectPayment);
		}catch (Exception e){}
		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment table inserted...");
		return;
	}

	public ArrayList<String> getBankName() {
		ArrayList<String> BankName = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct BANK_NAME from bank_details", null);
			if (cursor.moveToFirst()) {
				do {
					BankName.add(cursor.getString(cursor.getColumnIndex(COLUMN_BANKNAME)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return BankName;
	}

	public void saveIndirectPaymentByCheque(String PurchaseOrderNo, String vendorselected, String selectedReasons, String amount, String bankNameSelected, String ChequeNo,String username,String resval) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (amount == null) {
			return;
		}

		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		ContentValues contentValues = new ContentValues();
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

		contentValues.put("PAY_ID",PurchaseOrderNo );
		contentValues.put("POS_USER",username);
		contentValues.put("PAYMENT_ID",resval);
		contentValues.put("VEND_DSTR_NM", vendorselected);
		contentValues.put("REASON_OF_PAY", selectedReasons);
		contentValues.put("AMOUNT", amount);
		contentValues.put("RECEIVED_AMOUNT",amount);
		contentValues.put("PAYMENT_DATE",getDate());
		contentValues.put("BANK_NAME", bankNameSelected);
		contentValues.put("CHEQUE_NO", ChequeNo);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");


		db.insert("tmp_vend_dstr_payment", null, contentValues);
		String insert_saveIndirectPaymentByCheque = "insert into tmp_vend_dstr_payment (STORE_ID,PAY_ID, POS_USER, PAYMENT_ID, VEND_DSTR_NM ,REASON_OF_PAY , AMOUNT ,RECEIVED_AMOUNT,PAYMENT_DATE ,BANK_NAME,CHEQUE_NO, M_FLAG ) values ('" + PersistenceManager.getStoreId(mycontext) + "' ,'" + PurchaseOrderNo + "' , '" + username  + "', '" +  resval  + "' ,'" +vendorselected + "','" + selectedReasons + "', '" +  amount + "', '"  +  amount + "','"  + getDate() + "','" +bankNameSelected + "','"+ ChequeNo +"','I')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", insert_saveIndirectPaymentByCheque);
			login logi = new login();
			login.sendMessage(String.valueOf(insert_saveIndirectPaymentByCheque));
		}catch (Exception e){}
		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment byCheque table inserted...");
		return;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProdPharmaReport(String localProductName) {

		ArrayList<ReportLocalProductPharmaModel> localproductreportlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = localProductName + "%";*/
			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_local  where"
							+ " PROD_NM = '" + localProductName + "'  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel lm = new ReportLocalProductPharmaModel();
					lm.setUserNm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALUSERNAME)));
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					lm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALBARCODE)));
					lm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMRP)));
					lm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALSELLING)));
					lm.setPPrice(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPURCHASE)));
					lm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALACTIVE)));
					lm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMARGIN)));
					localproductreportlist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductreportlist;
	}


	public ArrayList<ReportLocalProductCpgModel> getlocalProductCpgName(String name) {
		ArrayList<ReportLocalProductCpgModel> localproductnamelist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct PROD_NM from retail_store_prod_local_cpg  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel lm = new ReportLocalProductCpgModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					localproductnamelist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductnamelist;

	}

	public ArrayList<ReportLocalProductPharmaModel> getlocalProductPharmaName(String name) {
		ArrayList<ReportLocalProductPharmaModel> localproductnamelist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct PROD_NM from retail_store_prod_local  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel lm = new ReportLocalProductPharmaModel();
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					localproductnamelist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductnamelist;

	}


	public ArrayList<ReportProductPharmaModel> getProductPharmaReport(String ProductName) {

		ArrayList<ReportProductPharmaModel> productlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = ProductName + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,PROD_ID,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod where"
							+ " PROD_NM like ?  ORDER BY PROD_NM ASC "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(USERNAME)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					//pm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProd_Id(cursor.getString(cursor.getColumnIndex(PRODUCTID)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTMRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTSELLING)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					pm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTMARGIN)));
					productlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}

	public ArrayList<ReportProductCpgModel> getProductCpgReport(String ProductName) {

		ArrayList<ReportProductCpgModel> productlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = ProductName + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_cpg  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_USER_NM)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_CODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_S_PRICE)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_P_PRICE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					pm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MARGIN)));
					productlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}


	public ArrayList<ReportLocalProductCpgModel> getLocalProdCpgReport(String localProductName) {

		ArrayList<ReportLocalProductCpgModel> localproductreportlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = localProductName + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_local_cpg  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel lm = new ReportLocalProductCpgModel();
					lm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_USER_NM)));
					lm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					lm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_CODE)));
					lm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MRP)));
					lm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_S_PRICE)));
					lm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_P_PRICE)));
					lm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					lm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MARGIN)));
					localproductreportlist.add(lm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return localproductreportlist;
	}

	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////

	public ArrayList<ReportProductCpgModel> getProductCpgName(String name) {
		ArrayList<ReportProductCpgModel> productnamelist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct PROD_NM from retail_store_prod_cpg  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					productnamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productnamelist;
	}


	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////
	public ArrayList<ReportProductPharmaModel> getProductPharmaName(String name) {
		ArrayList<ReportProductPharmaModel> productnamelist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct PROD_NM from retail_store_prod  where"
							+ " PROD_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					productnamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productnamelist;
	}

	public ArrayList<ReportVendorModel> getVendorReport(String VendorName) {

		ArrayList<ReportVendorModel> vendorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = VendorName + "%";
			Cursor cursor = db.rawQuery("select distinct VEND_NM, ACTIVE, POS_USER from retail_store_vendor  where"
							+ " VEND_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel vm = new ReportVendorModel();
					vm.setUserNm(cursor.getString(cursor.getColumnIndex(LOCALUSERNAME)));
					vm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					vm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					vendorlist.add(vm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	/////////////////////////////////////*************/////////////////////////////////////////////////////////////////
	public ArrayList<ReportVendorModel> getVendorReportName(String name) {
		ArrayList<ReportVendorModel> vendornamelist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_store_vendor  where"
							+ " VEND_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel vm = new ReportVendorModel();
					vm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					vendornamelist.add(vm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}


	public ArrayList<Sales> getProductNamedata(String idorName) {
		ArrayList<Sales> productNamelist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";


			//SimpleDateFormat dates = new SimpleDateFormat("yyyy/MM/dd");

			Cursor productcursor = db.rawQuery("select distinct PROD_NM,PROD_ID,BATCH_NO,EXP_DATE,MRP,AMOUNT,UOM,S_PRICE,QTY,BARCODE,CONV_FACT,CON_MUL_QTY, CONV_MRP,CONV_SPRICE,DISCOUNT_PERCENT,P_PRICE from retail_str_stock_master where "
							+ "PROD_NM LIKE ? or BARCODE like ?  or PROD_ID LIKE ? "
					, params);
			//and ACTIVE='Y%'


			if (productcursor.moveToFirst()) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
					saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(TOTALQTY)));
					saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(CONVMRP)));
					saleslist.setProdid(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(CONVSPRICE)));
					saleslist.setDiscountsales(productcursor.getFloat(productcursor.getColumnIndex(DISCOUNT_PERCENT)));

					saleslist.setConversionfacter(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					saleslist.setPPrice(productcursor.getString(productcursor.getColumnIndex("P_PRICE")));
					productNamelist.add(saleslist);
				} while (productcursor.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}

	public ArrayList<PurchaseProductModelwithpo> getvendorsearch() {

		ArrayList<PurchaseProductModelwithpo> vendorNamelist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}

	public ArrayList<String> getPo_numbers(String name ,int i) {

		ArrayList<String> vendorNamelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct PO_NO from retail_str_po_detail where "
							+ " VENDOR_NM like  ? ORDER BY PO_NO DESC limit '"+i+"' "
					, params);
			if (cursor.moveToFirst()) {
				do {
					vendorNamelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));


				} while (cursor.moveToNext());

			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}


	public ArrayList<PurchaseProductModelwithpo> getPurchaseProductdata(String PO) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PO_NO, PROD_ID, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, TOTAL,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM ,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_str_po_detail where "
							+ " PO_NO  like ? "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_TOTAL)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public void saveInventorywithpo(ArrayList<PurchaseProductModelwithpo> list, String VendorName, String Ponumbers, String grnId,String username,String VendorNo,String VendorDate) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;

			}
			for (PurchaseProductModelwithpo prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));


				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNot(prod.getExp_Date(),prod.getProductId(),String.valueOf(prod.getMrp()));

				float totalinventoryqty=prod.productQuantity+prod.getDiscountitems();
				float mrp = prod.getMrp();
				float sprice=prod.getSprice();
				float conversin=prod.getConversion();
				float newmrp = mrp / conversin;
				if (newmrp < 0) {
					newmrp = 0;
				}
				float newsprice = sprice / conversin;
				if (newsprice < 0) {
					newsprice = 0;
				}
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("POS_USER",username);
					values.put("VEND_NM", VendorName);
					values.put("PO_NO", Ponumbers);
					values.put("BATCH_NO", getSystemCurrentTime());
					values.put("MFG_BATCH_NO", prod.getBatch_No());

					values.put("EXP_DATE", prod.getExp_Date());
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getProductPrice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("AMOUNT", prod.getTotal());
					values.put("UOM", prod.getUom());
					values.put("BARCODE", "NA");
					values.put("CONV_FACT", prod.getConversion());
					values.put("FREE_GOODS", prod.getDiscountitems());
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("CONV_MRP",newmrp);
					values.put("CONV_SPRICE",newsprice);
					values.put("PREV_QTY",totalinventoryqty);
					values.put("M_FLAG","I");
					values.put("INVENTORY_DATE",getDate());
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("DISCOUNT_PERCENT",prod.getInvpodiscount());
					values.put("PURCHASE_UNIT_CONV",prod.getPurchaseunitconv());
					values.put("S_FLAG","0");



					// Inserting Row
					db.insert("retail_str_stock_master", null, values);
					String insert_saveInventorywithpo = "insert into retail_str_stock_master ( GRN_ID , POS_USER , VEND_NM , PO_NO , BATCH_NO , MFG_BATCH_NO , " +
							"EXP_DATE  , STORE_ID , PROD_ID, PROD_NM , " +
							"P_PRICE, PROFIT_MARGIN , QTY, MRP ,S_PRICE , AMOUNT , UOM , BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , CONV_MRP , " +
							"CONV_SPRICE , PREV_QTY , M_FLAG , INVENTORY_DATE , VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , DISCOUNT_PERCENT , PURCHASE_UNIT_CONV , S_FLAG) values " +

							"(" + "'" + grnId + "' ," + "'" + username + "' ," + "'" + VendorName + "'," + "'" + Ponumbers +

							"'," + "'" + getSystemCurrentTime() + "'," + "'" + prod.getBatch_No() + "'," + "'" + prod.getExp_Date() + "'," +

							"'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'" + prod.getProductName() +

							"'," + "'" + prod.getProductPrice() + "'," + "'" + prod.getProductmargin() + "'," + "'" + prod.getProductQuantity() + "'," + "'" +

							prod.getMrp() + "'," + "'" + prod.getSprice() + "'," + "'"  +  prod.getTotal() + "'," + "'" + prod.getUom() + "',"

							+ "'"  + "NA'," + "'" + prod.getConversion() + "'," + "'" + prod.getDiscountitems() + "',"

							+ "'"  + prod.getTotalqty() + "'," + "'" + newmrp + "'," + "'" + newsprice + "'," + "'"

							+  totalinventoryqty + "',"  +  "'I '," + "'"  +  getDate() + "'," + "'" + VendorNo + "'," + "'" + VendorDate + "'," + "'" + prod.getInvpodiscount()

							+ "'," + "'" + prod.getPurchaseunitconv() + "'," + "'" + "0')";
					try {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("Query", insert_saveInventorywithpo);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonObject));
					}catch (Exception e){}

				} else {
					String batchqty = getparticularbatchqty(prod.getExp_Date(), prod.getProductId(),String.valueOf(prod.getMrp()));
					values.put("GRN_ID", grnId);
					values.put("M_FLAG","U");
					values.put("INVENTORY_DATE",getDate());

					float prodQuantity = prod.getTotalqty();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("CON_MUL_QTY", Double.toString(newStockQuantity));

					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "EXP_DATE = ?  and " +
							"PROD_ID " +
							" = ? and " + "MRP " +" = ?  ", new String[]{prod.getExp_Date(), prod.getProductId(),String.valueOf(prod.getMrp())});

					String update_inventorywithpo = "UPDATE retail_str_stock_master SET GRN_ID = "+ "'" + grnId + "'" + " ,M_FLAG = 'U' " + ",INVENTORY_DATE = " + "'" + getDate() + "'" + " , CON_MUL_QTY = " + "'" + Double.toString(newStockQuantity)  + "'" + " WHERE EXP_DATE = " + "'" + prod.getExp_Date() + "'" +" and " + "PROD_ID = " + "'" + prod.getProductId() + "'" + " and " + "MRP = " + "'" +String.valueOf(prod.getMrp()) + "'";
					try {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("Query", update_inventorywithpo);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonObject));
					}catch (Exception e){}


					Log.d("Sudhee", "Update done for batch:" + prod.getBatch_No() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean CheckbatchnoAlreadyInDBorNot(String expdate,String prodid,String mrp) {
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("select EXP_DATE, PROD_ID, MRP ,CON_MUL_QTY from retail_str_stock_master where "
				+ " EXP_DATE ='"+expdate+"' and PROD_ID='"+prodid+"' and MRP ='"+mrp+"'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}
	public String getparticularbatchqty(String expdate, String Prod_Id,String mrp) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_ID = '" + Prod_Id + "' and MRP = '" + mrp + "'");
		Log.e("Query::", "select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_ID = '" + Prod_Id + "'and MRP = '" + mrp + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;
	}

	public String getparticularbatchqtyforsalesreturn(String batchno, String Prod_Nm) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
   /* String[] params = new String[1];
    params[0] = batchno + "%";
    params[1] = Prod_Id + "%";*/
		String Query = ("select Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		Log.e("Query::", "select Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Nm = '" + Prod_Nm + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}

	public ArrayList<Salesreturndetail> getSaleReturndetail() {
		ArrayList<Salesreturndetail> salereturntimedatelist = new ArrayList<Salesreturndetail>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor Returncursor = db.rawQuery("select Business_Date,Sale_Date,Sale_Time from retail_str_sales_master", null);

			if (Returncursor.moveToFirst()) {
				do {
					Salesreturndetail sr = new Salesreturndetail();

//
					sr.setSaleDate(Returncursor.getString(Returncursor.getColumnIndex(RETURSALEDATE)));

					salereturntimedatelist.add(sr);
				} while (Returncursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return salereturntimedatelist;
	}

	public void insertsalesreturn(String INVOICE, ArrayList<Salesreturndetail> list, String Item,String grandtotal,String username) {

		int i = login.setboolean();
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		if (list == null) {
			return;
		}

		for (Salesreturndetail salesreturndetail : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

			ContentValues contentValues = new ContentValues();
			contentValues.put("ID", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("TRI_ID", INVOICE);
			contentValues.put("POS_USER",username);
			// contentValues.put("INVOICE_NO", INVOICE);
			contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			contentValues.put("FLAG","Y");

			////contentValues.put("PROD_NM", salesreturndetail.getSaleproductname());
			//contentValues.put("BATCH_NO", salesreturndetail.getSalebatchno());
			// contentValues.put("EXP_DATE", salesreturndetail.getSaleexpiry());
			//contentValues.put("S_PRICE", salesreturndetail.getSalesellingprice());
			// contentValues.put("QTY", salesreturndetail.getSaleqty());
			//contentValues.put("MRP", salesreturndetail.getSalemrp());
			// contentValues.put("UOM", salesreturndetail.getSaleUOM());
			contentValues.put("REASON_OF_RETURN", Item);
			contentValues.put("GRAND_TOTAL", grandtotal);
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			// Inserting Row
			//db.insert("retail_str_sales_master_return", null, contentValues);

			if (i==0)
			{
				contentValues.put("SLAVE_FLAG","0");

			}else {
				contentValues.put("SLAVE_FLAG", "1");


				String salesReturnRecord = "insert into retail_str_sales_master_return( ID ,TRI_ID , POS_USER , STORE_ID , FLAG , REASON_OF_RETURN , GRAND_TOTAL , M_FLAG , S_FLAG ,SLAVE_FLAG) values (" + "'" + getSystemCurrentTimeinsalesreturnwithin() + "'," + "'" + INVOICE + "'," + "'" + username + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'Y'," + "'" + Item + "'" + grandtotal + "'," + "'I'," + "'0'," + "'1')";
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", salesReturnRecord);
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}
			}
			db.insert("retail_str_sales_master_return", null, contentValues);

		}
		if(i==3)
		{
			login.bluetoothSalesReturnData();
			updateSlaveFlagSalesReturn();
			//db.execSQL("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		}


		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}

	public void insertsalesreturnwithoutinvoice(String INVOICE, ArrayList<SalesreturndetailWithoutPo> list, String Item,String username,String imeino) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		if (list == null) {
			return;
		}

		for (SalesreturndetailWithoutPo salesreturndetail : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

			ContentValues contentValues = new ContentValues();
			contentValues.put("TRI_ID", INVOICE);
			contentValues.put("POS_USER",username);
			contentValues.put("ID", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("FLAG","Y");
			// contentValues.put("INVOICE_NO", INVOICE);
			contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			contentValues.put("REASON_OF_RETURN", Item);
			contentValues.put("GRAND_TOTAL", salesreturndetail.getSaletotal());
			contentValues.put("M_FLAG","I");
			contentValues.put("EX_TRI_ID",imeino);
			contentValues.put("S_FLAG","0");
			// Inserting Row
			db.insert("retail_str_sales_master_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}



	public void insertlostdata( ArrayList<lostsale> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		if (list == null) {
			return;
		}

		for (lostsale salesreturndetail : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

			ContentValues contentValues = new ContentValues();
			contentValues.put("TRI_ID", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			contentValues.put("PROD_ID ", salesreturndetail.getProdid());
			contentValues.put("PROD_NM", salesreturndetail.getSaleproductname());
			contentValues.put("QTY", salesreturndetail.getqty());
			contentValues.put("S_PRICE", salesreturndetail.getSalesellingprice());

			contentValues.put("TOTAL", salesreturndetail.getqty()*salesreturndetail.getSalesellingprice());
			contentValues.put("SALE_DATE", getDate());




			// Inserting Row
			db.insert("retail_str_lost_sales", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}











	public ArrayList<String> getBillno() {
		ArrayList<String> productlist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


			Cursor productcursor = db.rawQuery("select (ret_ticket_install_register.PREFIX||retail_str_sales_master.TRI_ID) AS fullBillNo from ret_ticket_install_register,retail_str_sales_master", null);
			Log.d("Sudhee", "Retrieved " + productcursor.getCount() + " Rows");
			if (productcursor.moveToFirst()) {
				do {

					productlist.add(productcursor.getString(productcursor.getColumnIndex("fullBillNo")));
					//productlist.add(productcursor.getString(productcursor.getColumnIndex(PREFIX)));

				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}
	// we are calling this method in our activity
	// and setting the data in particular textbox

	public boolean updateProductCom(String PRODUCTPRODUCTID, String PRODUCTSELLING, String PRODUCTPURCHASE, String PRODUCTINTERNET, String PRODUCTRELEVANT, String ACTIVE,String username,String DISCOUNT) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("S_PRICE", PRODUCTSELLING);
		contentValues.put("POS_USER",username);

		contentValues.put("P_PRICE", PRODUCTPURCHASE);
		contentValues.put("INTERNET_PRICE", PRODUCTINTERNET);
		contentValues.put("IS_PROD_REL_INT", PRODUCTRELEVANT);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("M_FLAG","U");
		contentValues.put("DISCOUNT_PERCENT",DISCOUNT);
		contentValues.put("S_FLAG","0");
		db.update("retail_store_prod_com", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});
		String update_prod_com = "UPDATE retail_store_prod_com SET S_PRICE = "+ "'" + PRODUCTSELLING + "'" + " ,POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " ,P_PRICE = " + "'" + PRODUCTPURCHASE + "'"+ " ,INTERNET_PRICE = " + "'" + PRODUCTINTERNET + "'" +  " ,IS_PROD_REL_INT = " + "'" + PRODUCTRELEVANT + "'" +   " ,ACTIVE = " + "'" + ACTIVE + "'"  +    " ,DISCOUNT_PERCENT = " + "'" + DISCOUNT + "'" + " WHERE PROD_ID= " + "'" + String.valueOf(PRODUCTPRODUCTID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_prod_com);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}

	public boolean updatelocalProductCpg(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String Active) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("PROD_ID", PRODUCTLOCALPRODUCTID);
		contentValues.put("PROD_NM", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BARCODE", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_PRICE", PRODUCTLOCALSELLING);
		contentValues.put("P_PRICE", PRODUCTLOCALPURCHASE);
		contentValues.put("ACTIVE", Active);
		contentValues.put("M_FLAG","U");
		// contentValues.put("ACTIVE", ACTIVE);
		// contentValues.put("Active", ACTIVE);
		db.update("retail_store_prod_com", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});
		return true;
	}

	public boolean updatelocalProductCom(String PRODUCTLOCALPRODUCTID, String PRODUCTLOCALPRODUCTNAME, String PRODUCTLOCALBARCODE, String PRODUCTLOCALMRP, String PRODUCTLOCALSELLING, String PRODUCTLOCALPURCHASE, String Active,String username,String DISCOUNT,String uom) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("PROD_ID", PRODUCTLOCALPRODUCTID);
		contentValues.put("POS_USER",username);
		contentValues.put("PROD_NM", PRODUCTLOCALPRODUCTNAME);
		contentValues.put("BARCODE", PRODUCTLOCALBARCODE);
		contentValues.put("MRP", PRODUCTLOCALMRP);
		contentValues.put("S_PRICE", PRODUCTLOCALSELLING);
		contentValues.put("P_PRICE", PRODUCTLOCALPURCHASE);
		contentValues.put("ACTIVE", Active);
		contentValues.put("M_FLAG","U");
		contentValues.put("DISCOUNT_PERCENT",DISCOUNT);
		contentValues.put("SELLING_ORDER_UNIT",uom);
		contentValues.put("CONV_FACT","1");
		contentValues.put("S_FLAG","0");

		// contentValues.put("ACTIVE", ACTIVE);
		db.update("retail_store_prod_com", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTLOCALPRODUCTID)});

		String update_prod_com = "UPDATE retail_store_prod_com SET S_PRICE = "
				+ "'" + PRODUCTLOCALSELLING + "'" + " ,POS_USER = " + "'" + username + "'"
				+ " ,PROD_NM = " + "'" + PRODUCTLOCALPRODUCTNAME + "'"
				+ " ,BARCODE = " + "'" + PRODUCTLOCALBARCODE + "'"
				+ " ,MRP = " + "'" + PRODUCTLOCALMRP + "'"
				+ " ,SELLING_ORDER_UNIT = " + "'" + uom + "'"
				+ ",M_FLAG = 'U' " + " ,P_PRICE = " + "'"
				+ PRODUCTLOCALPURCHASE + "'"+  " ,ACTIVE = " + "'"
				+ Active + "'"  +    " ,DISCOUNT_PERCENT = " + "'" + DISCOUNT + "'"
				+ " WHERE PROD_ID= " + "'" + String.valueOf(PRODUCTLOCALPRODUCTID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_prod_com);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}
	//!!!!!!!!!!!************UPDATE sales Quantity************!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * Update the Stock Quantity based on purchased items according to the salesBillItems
	 *
	 * @param salesBillItems Arraylist of Sales Items in current sale
	 * @return True if all updates successful, else False
	 */public boolean updateStockQty(ArrayList<Sales> salesBillItems) {
		SQLiteDatabase db = this.getWritableDatabase();
		//For all items in sales bill, update the stock quantity
		int i = login.setboolean();
		boolean returnval = true;
		if (salesBillItems == null) {
			return true;
		}
		for (Sales sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();
			float newStockQuantity;
			float newstockQuant;
			float stockQuantity = sales.getStockquant();
			int saleQuantity = sales.getQuantity();
			if (saleQuantity <= stockQuantity) {
				newstock = stockQuantity - saleQuantity;
				contentValues.put("CON_MUL_QTY", Double.toString(newstock));
			}
			else if (saleQuantity > stockQuantity) {
				newStockQuantity = stockQuantity - saleQuantity;
				newstockQuant = stockQuantity - newStockQuantity;
				newstock = saleQuantity - newstockQuant;
				contentValues.put("CON_MUL_QTY", Double.toString(newstock));
			}
			contentValues.put("M_FLAG", "U");
			contentValues.put("S_FLAG","0");
			contentValues.put("CONV_MRP",sales.getMrp());
			contentValues.put("CONV_SPRICE",sales.getSPrice());
			if (i==0)
			{
				contentValues.put("SLAVE_FLAG","0");
			}else {
				contentValues.put("SLAVE_FLAG", "1");
				login.bluetoothdataupdate();

				String Update_StockQuantity_Sales = "UPDATE retail_str_stock_master SET CON_MUL_QTY" +
						"  = " + "'" + newstock + "'" + ",M_FLAG = 'U' " + ",CONV_MRP= '"+String.valueOf(sales.getMrp())+"'," +
						"CONV_SPRICE='"+String.valueOf(sales.getSPrice())+"'WHERE BATCH_NO= " + "'" + String.valueOf(sales.getBatchNo()) + "'" +
						" AND PROD_ID = '" + String.valueOf(sales.getProdid()) +"'";
				//db.execSQL("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
				updateSlaveFlagstockMaster();
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", Update_StockQuantity_Sales);
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}
			}
				int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "BATCH_NO = ?  and PROD_ID = ? ", new String[]{sales.getBatchNo(), String.valueOf(sales.getProdid())});
				if (sqlUpdateRetval < 1) {
					returnval = false;
				}
			}

		db.close();
		return returnval;
	}
	public boolean updateStockQtyforhold(ArrayList<Sales> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Sales sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();

			float stockQuantity = sales.getStockquant();
			int saleQuantity = sales.getQuantity();

			if (saleQuantity <= stockQuantity) {

				float newStockQuantity = stockQuantity + saleQuantity;

				contentValues.put("CON_MUL_QTY", Double.toString(newStockQuantity));


			} else if (saleQuantity > stockQuantity) {
				float newStockQuantity = stockQuantity + saleQuantity;
				float newstockQuant = stockQuantity + newStockQuantity;
				float newstock = saleQuantity + newstockQuant;

				contentValues.put("CON_MUL_QTY", Double.toString(newstock));


			}

			contentValues.put("M_FLAG","U");
			contentValues.put("S_FLAG","0");
			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "BATCH_NO = ?  and " +
					"PROD_ID " +
					" = ? ", new String[]{sales.getBatchNo(), sales.getProdid()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}



	public ArrayList<SalesreturndetailWithoutPo> getProductReturnData(String idorName) {
		ArrayList<SalesreturndetailWithoutPo> productNamelist = new ArrayList<SalesreturndetailWithoutPo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";

			Cursor res = db.rawQuery("select distinct PROD_ID, PROD_NM, MRP ,SELLING_ORDER_UNIT ,S_PRICE,CONV_FACT from retail_store_prod_com where "
					+ "  PROD_NM LIKE ? OR PROD_ID LIKE ? AND ACTIVE ='Y' ", params);

			if (res.moveToFirst()) {
				do {
					SalesreturndetailWithoutPo salesreturndetail = new SalesreturndetailWithoutPo();
					//salesreturndetail.setSaleTransid(res.getString(res.getColumnIndex(RETURNTRANSIDs)));
					//salesreturndetail.setSaleBillno(res.getString(res.getColumnIndex(BILLNO)));
					salesreturndetail.setSaleProdid(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					salesreturndetail.setSaleproductname(res.getString(res.getColumnIndex(RETURNPRODUCTNAME)));
					salesreturndetail.setSalesellingprice(res.getFloat(res.getColumnIndex(RETURNSELLINGPRICE)));
					//salesreturndetail.setSaleqty(res.getFloat(res.getColumnIndex(RETURNQUANTITY)));
					salesreturndetail.setSalemrp(res.getString(res.getColumnIndex(RETURNMRP)));
					salesreturndetail.setSaleuom(res.getString(res.getColumnIndex(PRODUCTMEASUREUNITINV)));
					//salesreturndetail.setSaletotal(res.getFloat(res.getColumnIndex(RETURNGRANDTOTAL)));
					salesreturndetail.setConversionfactorreturn(res.getFloat(
							res.getColumnIndex(PRODUCTCONVERSION)));
					productNamelist.add(salesreturndetail);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}





	public ArrayList<lostsale> getsalelostData(String idorName) {
		ArrayList<lostsale> productNamelist = new ArrayList<lostsale>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = idorName + "%";
			params[1] = idorName + "%";

			Cursor res = db.rawQuery("select distinct PROD_ID, PROD_NM,MRP,S_PRICE from retail_store_prod_com where "
					+ "  PROD_NM LIKE ? OR PROD_ID LIKE ?  ", params);

			if (res.moveToFirst()) {
				do {
					lostsale salesreturndetail = new lostsale();

					salesreturndetail.setProdid(res.getString(res.getColumnIndex(PRODUCTPRODUCTID)));
					salesreturndetail.setSaleproductname(res.getString(res.getColumnIndex(RETURNPRODUCTNAME)));
					salesreturndetail.setSalesellingprice(res.getFloat(res.getColumnIndex(RETURNSELLINGPRICE)));
					salesreturndetail.setSalemrp(res.getString(res.getColumnIndex(RETURNMRP)));

					productNamelist.add(salesreturndetail);
				} while (res.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}


   /*public boolean updatequantity(ArrayList<Inventoryproductmodel> salesBillItems) {

      boolean returnval = true;


      if (salesBillItems == null) {
         return true;
      }

      SQLiteDatabase db = this.getWritableDatabase();

      //For all items in sales bill, update the stock quantity
      for (Inventoryproductmodel sales : salesBillItems) {
         ContentValues contentValues = new ContentValues();
         try {
            float total = Float.parseFloat(sales.getMrp());
            Float conversionfactor = sales.getConvfact();
            Float stockQuantity = sales.getStock();
            int prodQuantity = sales.productQuantity;

            float newStockQuantity = stockQuantity + prodQuantity;
            float multilplefactor = conversionfactor * prodQuantity;
            float totalamount = total * prodQuantity;
            if (newStockQuantity < 0) {
               newStockQuantity = 0;
            }

            if (multilplefactor < 0) {
               multilplefactor = 0;
            }

            if (totalamount < 0) {
               totalamount = 0;
            }

            contentValues.put("Qty", Double.toString(newStockQuantity));
            contentValues.put("Con_Mul_Qty", Double.toString(multilplefactor));


            int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
                  "Prod_Id " +
                  " = ? ", new String[]{sales.getBatchno(), sales.getProductId()});
            if (sqlUpdateRetval < 1) {
               returnval = false;
            }

         } catch (NumberFormatException ex) {
            ex.printStackTrace();
         }
      }

      db.close();
      return returnval;
   }

*/

	public boolean updatebatchnowithpo(ArrayList<PurchaseProductModelwithpo> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (PurchaseProductModelwithpo invbatchno : salesBillItems) {
			ContentValues contentValues = new ContentValues();

         /*float stockQuantity = sales.getStockquant();
         int saleQuantity = sales.getQuantity();

         float newStockQuantity = stockQuantity - saleQuantity;
         if (newStockQuantity < 0) {
            newStockQuantity = 0;
         }*/

			String batchno = invbatchno.getExp_Date();
			contentValues.put("EXP_DATE", batchno);

			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "BATCH_NO = ?  and " +
							"PROD_ID " +
							"= ? ",
					new String[]{invbatchno.getBatch_No(), invbatchno.getProductId()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	//*******************************retrive data for retail_store*********************************************

	public ArrayList<String> getdataemp() {
		ArrayList<String> updatelist3 = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store ", null);
		if (res.moveToFirst())
			Log.e(TAG, "getTableAsString called");
		{
			do {
				updatelist3.add(res.getString(res.getColumnIndex(STORE_ID_STORE)));
				Log.e(TAG, "inside do function");
			} while (res.moveToNext());
		}

		return updatelist3;
	}


	//**************************retail_store***********************************************************
	public Cursor getdatastore(String Store_Id_store) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select STORE_ID from retail_store where STORE_ID = " + Store_Id_store + "", null);
		return res;
	}


	public ArrayList<Registeremployeesmodel> getusername() {

		ArrayList<Registeremployeesmodel> ponamelist = new ArrayList<Registeremployeesmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
           /* String []params=new String[1];
            params[0]=name+"%";
          */
			Cursor cursor = db.rawQuery("select distinct USR_NM  from retail_employees "
					, null);
			if (cursor.moveToFirst()) {
				do {
					Registeremployeesmodel pm = new Registeremployeesmodel();
					pm.setUsername(cursor.getString(cursor.getColumnIndex(USER_NAME)));
					ponamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return ponamelist;
	}


//***********************************update for employees table**********************************************

	public boolean updateemployees(String STORE_ID, String USER_NAME, String FIRSTNAME, String LASTNAME, String PASSWORD, String CONFIRMPASSWORD, String ACTIVE,String username,String usr) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("FIRST_NAME", FIRSTNAME);
		contentValues.put("LAST_NAME", LASTNAME);
		contentValues.put("POS_USER",username);

		contentValues.put("PASSWORD", PASSWORD);
		contentValues.put("CONFIRM_PASSWORD", CONFIRMPASSWORD);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		contentValues.put("USR_NM",usr);

		db.update("retail_employees", contentValues, "STORE_ID = ? and " + "USR_NM  " + "= ?", new String[]{String.valueOf(STORE_ID), String.valueOf(USER_NAME)});
		String update_employees = "UPDATE retail_employees SET FIRST_NAME = "+ "'" + FIRSTNAME + "'" + " ,LAST_NAME = " + "'" + LASTNAME + "'" + " ,POS_USER = " + "'" + username + "'" + " , PASSWORD = " + "'" + PASSWORD + "'" + ", CONFIRM_PASSWORD = " + "'" + CONFIRMPASSWORD + "'" + ",ACTIVE = " + "'" + ACTIVE + "'" + ",M_FLAG = 'U' " + "" + " WHERE STORE_ID = " + "'" +String.valueOf(STORE_ID) + "'" +" and " + "USR_NM = " + "'" + USER_NAME + "'";

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_employees);
			login logi = new login();
			login.sendMessage(update_employees);
		}catch (Exception e){}
		return true;

	}



	public ArrayList<Registeremployeesmodel> getdatafoeregister(String name) {
		ArrayList<Registeremployeesmodel> updatelist3 = new ArrayList<Registeremployeesmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor res = db.rawQuery("select USR_NM, FIRST_NAME, LAST_NAME, PASSWORD, CONFIRM_PASSWORD, ACTIVE  from retail_employees where "
							+ " USR_NM like ? "
					, params);
			if (res.moveToFirst())

				Log.e(TAG, "getTableAsString called");
			{
				do {
					Registeremployeesmodel rm = new Registeremployeesmodel();
					rm.setFirstname(res.getString(res.getColumnIndex(FIRST_NAME)));
					rm.setLastname(res.getString(res.getColumnIndex(LAST_NAME)));
					rm.setPassword(res.getString(res.getColumnIndex(PASSWORD)));
					rm.setConfirmpassword(res.getString(res.getColumnIndex(CONFIRMPASSWORD)));
					rm.setActive(res.getString(res.getColumnIndex(ACTIVE)));
					updatelist3.add(rm);
					Log.e(TAG, "inside do function");
				} while (res.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}

		return updatelist3;
	}

	public void saveGranddataintoGrnMaster(String result, String ponumberstoselectProducts, String VendorName, String GrandTotal,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues values = new ContentValues();
		values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		values.put("GRN_ID", result);
		values.put("POS_USER",username);
		values.put("VEND_NM", VendorName);
		values.put("PO_NO", ponumberstoselectProducts);
		values.put("GRAND_AMOUNT", GrandTotal);
		values.put("FLAG", "0");
		values.put("S_FLAG", "0");
		values.put("M_FLAG","I");
		db.insert("retail_str_grn_master", null, values);
		String inert_saveGranddataintoGrnMaster = "insert into retail_str_grn_master ( STORE_ID , GRN_ID , POS_USER ,  VEND_NM , PO_NO , GRAND_AMOUNT , FLAG , S_FLAG , M_FLAG ) values (" + "'" + PersistenceManager.getStoreId(mycontext) + "' ," + "'" + result + "' ," + "'" + username + "'," + "'" + VendorName + "'," + "'" + ponumberstoselectProducts + "'," + "'" + GrandTotal + "'," + "'" + "0','"  + "0'," +  "'I')";

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",inert_saveGranddataintoGrnMaster);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted.into retail_str_grn_master..");


		return;
	}

	public ArrayList<VendorModel> getVendorsForVendorPayment() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String []params=new String[1];
      params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_str_grn_master where FLAG like '0%' ", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> getGrnNumber(String VendorName) {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct GRN_ID,VEND_NM,FLAG from retail_str_grn_master where "
							+ "  FLAG like '0%' and VEND_NM  like '" + VendorName + "' ORDER BY GRN_ID ASC  limit 3"
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void savedirectPayment(String vendorselected, String po_numberselected, String Amount, String Payment_ID, String Userpayamount, String DueAmount,String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		if (Amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("VEND_DSTR_NM", vendorselected);
		contentValues.put("PAY_ID", po_numberselected);
		contentValues.put("AMOUNT", Amount);
		contentValues.put("POS_USER",username);
		contentValues.put("PAYMENT_ID", Payment_ID);
		contentValues.put("RECEIVED_AMOUNT", Userpayamount);
		contentValues.put("DUE_AMOUNT", DueAmount);
		contentValues.put("PAYMENT_DATE",getDate());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("tmp_vend_dstr_payment", null, contentValues);

		String inert_savedirectPayment = "insert into tmp_vend_dstr_payment ( VEND_DSTR_NM , PAY_ID , AMOUNT , POS_USER , PAYMENT_ID , RECEIVED_AMOUNT ,DUE_AMOUNT ,PAYMENT_DATE , M_FLAG ) values (" + "'" + vendorselected + "' ," + "'" + po_numberselected + "' ," + "'" + Amount + "'," + "'" + username + "'," + "'" + Payment_ID + "'," + "'" + Userpayamount + "'," + "'"  + DueAmount + "'," + "'" + getDate() + "'," + "'I'"+ "'," + "'0')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",inert_savedirectPayment);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment bycash table inserted...");
		return;
	}


	public void savedirectPaymentwithpobycheque(String PurchaseOrderNo, String vendorselected, String amount, String bankNameSelected, String ChequeNo, String Payment_Id, String UserPayment, String AmountDue,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		if (amount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("PAY_ID", PurchaseOrderNo);
		contentValues.put("VEND_DSTR_NM", vendorselected);
		contentValues.put("PAYMENT_ID", Payment_Id);
		contentValues.put("AMOUNT", amount);
		contentValues.put("POS_USER",username);

		contentValues.put("BANK_NAME", bankNameSelected);
		contentValues.put("CHEQUE_NO", ChequeNo);
		contentValues.put("RECEIVED_AMOUNT", UserPayment);
		contentValues.put("DUE_AMOUNT", AmountDue);
		contentValues.put("PAYMENT_DATE",getDate());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("tmp_vend_dstr_payment", null, contentValues);


		String inert_savedirectPaymentwithpobycheque = "insert into tmp_vend_dstr_payment (PAY_ID , VEND_DSTR_NM ,PAYMENT_ID , " +
				"AMOUNT , POS_USER ,BANK_NAME, CHEQUE_NO, RECEIVED_AMOUNT ,DUE_AMOUNT ,PAYMENT_DATE , M_FLAG ) " +
				"values ('" +  PurchaseOrderNo + "' ,'" + vendorselected + "' , '" +  Payment_Id  + "', '" +  amount  +
				"' ,'" + username + "','" + bankNameSelected + "', '" + ChequeNo + "', '"  + UserPayment + "','"  +
				AmountDue + "','" + getDate() + "','I'" +"','0')";
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",inert_savedirectPaymentwithpobycheque);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}




		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "Data  Payment byCheque table inserted...");
		return;
	}


	public ArrayList<Companylistmodel> getcompanylist() {
		ArrayList<Companylistmodel> productlist = new ArrayList<Companylistmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


          /* Cursor productcursor = db.rawQuery("select Prod_Id,Prod_Nm , MRP, S_Price, P_Price, Unit_Of_Measure,Pack_Unit_1 from retail_store_prod where"
                    + " Prod_Id  like ? or  Prod_Nm  like ? "
                    , params);
*/
			Cursor productcursor = db.rawQuery("select * from retail_comp_btl "
					, null);


			if (productcursor.moveToFirst()) {
				do {
					Companylistmodel pm = new Companylistmodel();
					pm.setCompname(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_NM)));
					pm.setTargetamount(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_TARGET_AMOUNT)));
					pm.setBTLdesc(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_BTL_DESC)));
					pm.setTargetvalue(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_TARGET_VALUE)));
					pm.setStartdate(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_START_DATE)));
					pm.setEnddate(productcursor.getString(productcursor.getColumnIndex(COLOUM_COMP_END_DATE)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public ArrayList<Mfglistmodel> getmfglist() {
		ArrayList<Mfglistmodel> productlist = new ArrayList<Mfglistmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();


          /* Cursor productcursor = db.rawQuery("select Prod_Id,Prod_Nm , MRP, S_Price, P_Price, Unit_Of_Measure,Pack_Unit_1 from retail_store_prod where"
                    + " Prod_Id  like ? or  Prod_Nm  like ? "
                    , params);
*/
			Cursor productcursor = db.rawQuery("select * from retail_mfg_btl "
					, null);


			if (productcursor.moveToFirst()) {
				do {
					Mfglistmodel pm = new Mfglistmodel();
					pm.setMfgname(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_NM)));
					pm.setTargetamount(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_TARGET_AMOUNT)));
					pm.setBTLdesc(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_BTL_DESC)));
					pm.setTargetvalue(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_TARGET_VALUE)));
					pm.setStartdate(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_START_DATE)));
					pm.setEnddate(productcursor.getString(productcursor.getColumnIndex(COLOUM_MFG_END_DATE)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public ArrayList<String> getthereasonsfromVendorReturn() {

		ArrayList<String> reasonslist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct REASON_FOR_REJECTION from retail_store_vend_reject ", null);
			if (cursor.moveToFirst()) {
				do {
					reasonslist.add(cursor.getString(cursor.getColumnIndex(COLUMN_FOR_VEND_RETURN_REJECTION)));

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return reasonslist;
	}


	public ArrayList<VendorReturnModel> GetDataToreturn(String userEnteredNumber) {
		ArrayList<VendorReturnModel> GetDataUsingGrnID = new ArrayList<VendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userEnteredNumber + "%";
			Cursor NumberCursor = db.rawQuery(" select PROD_ID,PROD_NM,EXP_DATE,BATCH_NO,MRP,QTY,P_PRICE,S_PRICE,AMOUNT,UOM,GRN_ID,CONV_FACT from retail_str_stock_master where "
							+ " PROD_NM  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					VendorReturnModel vendorReturnModel = new VendorReturnModel();
					vendorReturnModel.setProductId(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_ID)));
					vendorReturnModel.setProductName(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_NAME)));
					vendorReturnModel.setBatchno(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_BATCHNO)));
					vendorReturnModel.setExpdate(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_EXP_DATE)));
					vendorReturnModel.setMrp(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_MRP)));
					vendorReturnModel.setPprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_PPRICE)));
					vendorReturnModel.setSprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_SPRICE)));
					vendorReturnModel.setUOM(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_UOM)));
					vendorReturnModel.setStock(NumberCursor.getInt(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setTotal(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_AMOUNT)));
					vendorReturnModel.setConvfact(NumberCursor.getFloat(NumberCursor.getColumnIndex(PRODUCTCONVERSION)));

					GetDataUsingGrnID.add(vendorReturnModel);

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetDataUsingGrnID;
	}

	public ArrayList<VendorReturnModel> getAllVendorreturndata(String userTypedproduct) {
		ArrayList<VendorReturnModel> GetDataUsingGrnID = new ArrayList<VendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userTypedproduct + "%";
			Cursor NumberCursor = db.rawQuery(" select PROD_ID,PROD_NM,EXP_DATE,BATCH_NO,MRP,QTY,P_PRICE,S_PRICE,AMOUNT,UOM,GRN_ID,FREE_GOODS,CONV_FACT from retail_str_stock_master where "
							+ " GRN_ID  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					VendorReturnModel vendorReturnModel = new VendorReturnModel();
					vendorReturnModel.setProductId(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_ID)));
					vendorReturnModel.setProductName(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_PROD_NAME)));
					vendorReturnModel.setBatchno(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_BATCHNO)));
					vendorReturnModel.setExpdate(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_EXP_DATE)));
					vendorReturnModel.setMrp(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_MRP)));
					vendorReturnModel.setPprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_PPRICE)));
					vendorReturnModel.setSprice(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_SPRICE)));
					vendorReturnModel.setUOM(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_STOCK_UOM)));
					vendorReturnModel.setStock(NumberCursor.getInt(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setProductQuantity(NumberCursor.getInt(NumberCursor.getColumnIndex(COLUMN_STOCK_QTY)));
					vendorReturnModel.setTotal(NumberCursor.getFloat(NumberCursor.getColumnIndex(COLUMN_STOCK_AMOUNT)));
					vendorReturnModel.setFreeQty(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_FREE_GOODS)));
					vendorReturnModel.setConvfact(NumberCursor.getFloat(NumberCursor.getColumnIndex(PRODUCTCONVERSION)));

					GetDataUsingGrnID.add(vendorReturnModel);

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetDataUsingGrnID;

	}
	public void InsertDataforVendorReturn(String vendor_return_id, ArrayList<VendorReturnModel> list, String vendororDistributorNameAutoComplete, String reasonSelected,String username) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null || vendor_return_id == null || vendororDistributorNameAutoComplete == null || reasonSelected == null) {
			return;
		}

		for (VendorReturnModel prod : list) {

			ContentValues contentValues = new ContentValues();
			contentValues.put("VENDOR_RETURN_ID", vendor_return_id);
			contentValues.put("POS_USER",username);
			contentValues.put("VENDOR_NM", vendororDistributorNameAutoComplete);
			contentValues.put("REASON_OF_RETURN", reasonSelected);
			contentValues.put("PROD_NM", prod.getProductName());
			contentValues.put("EXP_DATE", prod.getExpdate());
			contentValues.put("P_PRICE", prod.getPprice());
			contentValues.put("BATCH_NO", prod.getBatchno());
			contentValues.put("UOM", prod.getUOM());
			if (prod.getProductQuantity() != 0.0f) {
				contentValues.put("QTY", prod.getProductQuantity());
			} else {
				Log.e("Database Operation", "row Not  inserted...");
				continue;
			}
			contentValues.put("TOTAL", prod.getTotal());
			contentValues.put("RETURN_DATE", getDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_vendor_detail_return", null, contentValues);
			String insert_InsertDataforVendorReturn = "insert into retail_str_vendor_detail_return (VENDOR_RETURN_ID ,POS_USER, " +
					"VENDOR_NM, REASON_OF_RETURN, PROD_NM ,EXP_DATE , P_PRICE , BATCH_NO ,UOM ,QTY,TOTAL,RETURN_DATE, " +
					"M_FLAG ) values ('" + vendor_return_id + "' ,'" + username + "' , '" + vendororDistributorNameAutoComplete
					+ "', '" +  reasonSelected  + "' ,'" + prod.getProductName() + "','" + prod.getExpdate() + "', '" + prod.getPprice() + "', '"
					+   prod.getBatchno() +  "','" + prod.getUOM() + "','"+ prod.getProductQuantity() +"','"+ prod.getTotal() + "','"
					+ getDate() + "','I'"+ "','0')";
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query",insert_InsertDataforVendorReturn);
				login logi = new login();
				login.sendMessage(String.valueOf(jsonObject));

			}catch (Exception e){

			}


		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}

	public void InsertMasterDataForVendorReturn(String vendor_return_id, String VendorName, String reasonSelected, String grandAmount,String username) {
		SQLiteDatabase db = this.getReadableDatabase();

		if (vendor_return_id == null || reasonSelected == null || grandAmount == null) {
			return;
		}
		ContentValues contentValues = new ContentValues();
		contentValues.put("VENDOR_RETURN_ID", vendor_return_id);
		contentValues.put("VENDOR_NM", VendorName);
		contentValues.put("POS_USER", username);

		contentValues.put("REASON_OF_RETURN", reasonSelected);
		contentValues.put("Return_AMOUNT", grandAmount);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_vendor_Master_return", null, contentValues);
		String insert_InsertMasterDataForVendorReturn = "insert into retail_str_vendor_Master_return ( VENDOR_RETURN_ID, VENDOR_NM ,POS_USER , REASON_OF_RETURN , Return_AMOUNT,M_FLAG ) values ('" + vendor_return_id + "' , '" + VendorName + "' , '" + username + "', '"  + reasonSelected + "','" + grandAmount + "', 'I'" +"','0')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", insert_InsertMasterDataForVendorReturn);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row  in Master Table inserted...");
		return;
	}

	public ArrayList<String> getVendorNameForAuto(String userEnteredNumber) {
		ArrayList<String> GetVendorNameForAutocom = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userEnteredNumber + "%";
			Cursor NumberCursor = db.rawQuery(" select VEND_NM from retail_str_grn_master where "
							+ " GRN_ID  like ?  "
					, params);
			if (NumberCursor.moveToFirst()) {
				do {
					GetVendorNameForAutocom.add(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));

				} while (NumberCursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetVendorNameForAutocom;
	}



	public ArrayList<String> getGrandTotalforVendorPayment(String po_numberselected) {
		ArrayList<String> GetGrandTotal = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = po_numberselected + "%";
			Cursor NumberCursor = db.rawQuery(" select GRAND_AMOUNT from retail_str_grn_master where "
							+ " GRN_ID  like ?  "
					, params);

			if (NumberCursor.moveToFirst()) {
				do {
					GetGrandTotal.add(NumberCursor.getString(NumberCursor.getColumnIndex(COLUMN_MASTER_GRN_GRAND_AMOUNT)));
				} while (NumberCursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetGrandTotal;

	}


	public boolean UpdateStockQtyForVendorReturn(ArrayList<VendorReturnModel> salesBillItems) {

		boolean returnval = true;


		if (salesBillItems == null) {
			return true;

		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (VendorReturnModel sales : salesBillItems) {
			ContentValues contentValues = new ContentValues();

			int stockQuantity = sales.getStock();
			int prodQuantity = sales.productQuantity;
			float conv=sales.getConvfact();

			float newStockQuantity = stockQuantity - prodQuantity;

			float newconmulQuantity = (stockQuantity - prodQuantity)*conv;
			if (newStockQuantity < 0) {
				newStockQuantity = 0;
			}

			if (newconmulQuantity < 0) {
				newconmulQuantity = 0;

			}
			contentValues.put("QTY", Double.toString(newStockQuantity));
			contentValues.put("CON_MUL_QTY", Double.toString(newconmulQuantity));
			contentValues.put("M_FLAG","U");
			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "BATCH_NO = ?  and " +
					"PROD_ID " +
					" = ? ", new String[]{sales.getBatchno(), sales.getProductId()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	public ArrayList<String> getvendorsearchforSpinner() {

		ArrayList<String> vendorNamelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*
         String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					vendorNamelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return vendorNamelist;


	}

	public ArrayList<String> getimeino() {
		ArrayList<String> imeilist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
         /*
         String []params=new String[1];
         params[0]=name+"%";*/
		Cursor cursor = db.rawQuery("select IMEI_NUMBER from ret_ticket_install_register", null);
		if (cursor.moveToFirst()) {
			do {
				imeilist.add(cursor.getString(cursor.getColumnIndex(IMEI)));

			} while (cursor.moveToNext());
		}
		return imeilist;

	}


	public ArrayList<String> getprefix(String str) {
		ArrayList<String> prefixlist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[1];
		params[0] = str + "%";
		Cursor cursor = db.rawQuery("select PREFIX from ret_ticket_install_register where "
				+ " IMEI_NUMBER LIKE ?", params);
		if (cursor.moveToFirst()) {
			do {
				///    prefixlist.add(cursor.getString(cursor.getColumnIndex(IMEI)));
				prefixlist.add(cursor.getString(cursor.getColumnIndex(PREFIX)));


			} while (cursor.moveToNext());
		}
		return prefixlist;


	}

	public boolean updateVendorinpurchase(String VENDORID, String VENDORACTIVE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("ACTIVE", VENDORACTIVE);
		contentValues.put("POS_USER", username);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("retail_vend_dstr", contentValues, "VEND_DSTR_ID = ? ", new String[]{String.valueOf(VENDORID)});
		String update_vendor_in_purchase =  "UPDATE retail_vend_dstr SET ACTIVE = " + "'" + VENDORACTIVE + "'" + " ,POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " WHERE Vend_DSTR_ID= " + "'" + String.valueOf(VENDORID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_vendor_in_purchase);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		return true;
	}


	public boolean updatelocalVendorinpurchase(String LOCALVENDORID,String LOCALVENDORNAME,String LOCALVENDORADDRESS,String LOCALVENDORCITY,String LOCALVENDORCOUNTRY,String LOCALVENDORZIP,String Invspinvalue, String LOCALVENDORACTIVE ,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("POS_USER",username);
		contentValues.put("VEND_DSTR_NM", LOCALVENDORNAME);
		contentValues.put("VEND_DSTR_ADD",LOCALVENDORADDRESS);
		contentValues.put("VEND_DSTR_CITY",LOCALVENDORCITY);
		contentValues.put("VEND_DSTR_CTR",LOCALVENDORCOUNTRY);
		contentValues.put("VEND_DSTR_ZIP",LOCALVENDORZIP);
		contentValues.put("VEND_DSTR_INV",Invspinvalue);
		contentValues.put("ACTIVE", LOCALVENDORACTIVE);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		int SqlValue = db.update("retail_vend_dstr", contentValues, "VEND_DSTR_ID = ? ", new String[]{String.valueOf(LOCALVENDORID)});
		Log.e("Update for LocalVendor", LOCALVENDORACTIVE);
		if (SqlValue < 1) {
			Log.e("UpdateFailLocalVendor:", String.valueOf(SqlValue));
			return false;
		}
		return true;

	}

	public ArrayList<CreditNote> getcreditno(String userTypedInvoiceno) {
		ArrayList<CreditNote> creditNoteslist = new ArrayList<CreditNote>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userTypedInvoiceno + "%";
			Cursor creditnotecursor = db.rawQuery("select TRI_ID,GRAND_TOTAL from retail_str_sales_master_return where "
							+ " TRI_ID  like ?  AND FLAG like 'Y%'"
					, params);
			if (null != creditnotecursor && creditnotecursor.moveToFirst() && creditnotecursor.getCount() > 0) {
				do {
					CreditNote cn = new CreditNote();
					cn.setReturnInvoiceno(creditnotecursor.getString(creditnotecursor.getColumnIndex(TRANS_ID)));
					cn.setCreditnotevalue(creditnotecursor.getString(creditnotecursor.getColumnIndex(GRAND_TOTAL)));
					creditNoteslist.add(cn);

					//cn.getReturnCreatedon(creditnotecursor.getString(creditnotecursor.getColumnIndex(BILLNO)));
				} while (creditnotecursor.moveToNext());

			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return creditNoteslist;
	}

	public Boolean updateintoGrnMasterTableForVendorPayment(String vendorselected, String po_numberselected, String Amount) {
		boolean returnval = true;
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues value = new ContentValues();
		value.put("FLAG", "1");
		value.put("M_FLAG","U");
		int sqlUpdateRetval = db.update("retail_str_grn_master", value,
				" VEND_NM = ? and  " + "GRN_ID " + " = ? "
				, new String[]{String.format(vendorselected), String.format(po_numberselected)});


		String updateintoGrnMasterTableForVendorPayment = "UPDATE retail_str_grn_master SET FLAG = '1'" + ",M_FLAG = 'U " + "'"  +
				" "  +  " WHERE VEND_NM= " + "'" + String.format(vendorselected) + "'" + " AND GRN_ID= " + "'" + String.format(po_numberselected) + "'";

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", updateintoGrnMasterTableForVendorPayment);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		if (sqlUpdateRetval < 1) {
			returnval = false;
		}
		db.close();
		return returnval;
	}

	public Boolean updateintoGrnMasterForDueAmount(String vendorselected, String po_numberselected, String Amount) {
		boolean returnval = true;
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues value = new ContentValues();

		value.put("GRAND_AMOUNT", Amount);
		value.put("M_FLAG","U");
		value.put("S_FLAG","0");
		int sqlUpdateRetval = db.update("retail_str_grn_master", value,
				" VEND_NM = ? and  " + "GRN_ID " + " = ? "
				, new String[]{String.format(vendorselected), String.format(po_numberselected)});
		String updateintoGrnMasterForDueAmount = "UPDATE retail_str_grn_master SET GRAND_AMOUNT = " + "'" + Amount + "'" + ",M_FLAG = 'U' " + "'"  + " ," +
				" "  +  " WHERE VEND_NM= " + "'" + String.format(vendorselected) + "'" + " AND GRN_ID= " + "'" + String.format(po_numberselected) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", updateintoGrnMasterForDueAmount);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		if (sqlUpdateRetval < 1) {
			returnval = false;
		}
		db.close();
		return returnval;
	}


	public ArrayList<VendorModel> getVendorsforVendorReturn() {

		ArrayList<VendorModel> vendorNamelist = new ArrayList<VendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String []params=new String[1];
      params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_str_grn_master  where  "
					+ " FLAG like '1'", null);
			if (cursor.moveToFirst()) {
				do {
					VendorModel pm = new VendorModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_VENDORNAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorNamelist;
	}

	public ArrayList<String> getLastInvoicesForVendorReturn(String VendorName) {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct GRN_ID,VEND_NM,FLAG from retail_str_grn_master where "
							+ "FLAG like '1' and VEND_NM  = '" + VendorName + "' ORDER BY PO_NO DESC limit 20"
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	//************************Line Item Discount Update 7-july 2016 time-3:34 am********************


	public ArrayList<line_item_Product_Model> getAllLineItems() {

		ArrayList<line_item_Product_Model> returnlist = new ArrayList<line_item_Product_Model>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct LINE_ITEM_NM,LINE_ITEM_DISC,LINEITEM_ID,ACTIVE from retail_str_lineitem_disc", null);
			if (res.moveToFirst()) {
				do {
					line_item_Product_Model topfullproductmodel = new line_item_Product_Model();

					topfullproductmodel.setProductLinename(res.getString(res.getColumnIndex(LINEITEMNAME)));
					topfullproductmodel.setDiscountLineitem(res.getString(res.getColumnIndex(LINEITEMDISC)));
					topfullproductmodel.setProductLineId(res.getString(res.getColumnIndex(LINEITEMID)));
					topfullproductmodel.setLineActive(res.getString(res.getColumnIndex(LINEACTIVE)));
					returnlist.add(topfullproductmodel);
				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}
	public boolean linediscountitem(String LINE_ITEMID,String LINE_ITEMNAME, String LINE_ITEMDISC, String LACTIVE,String username) {

		SQLiteDatabase db = this.getWritableDatabase();


		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		ContentValues values = new ContentValues();
		values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		values.put("LINEITEM_ID", LINE_ITEMID);
		values.put("POS_USER",username);
		values.put("LINE_ITEM_NM", LINE_ITEMNAME);
		values.put("LINE_ITEM_DISC", LINE_ITEMDISC + "%");
		values.put("ACTIVE", LACTIVE);
		values.put("M_FLAG","U");
		values.put("S_FLAG","0");
		db.update("retail_str_lineitem_disc", values, "LINE_ITEM_NM = ? ", new String[]{String.valueOf(LINE_ITEMNAME)});

		return true;
	}





	///***********************  8 july top product update *****


	public boolean saveTop15Product(String TPID,String TPRODUCTNAME, String TPRODUCTSHORTNAME,String username) {
		SQLiteDatabase db = this.getWritableDatabase();


		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		ContentValues values = new ContentValues();
		values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		values.put("POS_USER",username);
		values.put("PROD_NM", TPRODUCTNAME);
		values.put("PROD_SHORT_NM", TPRODUCTSHORTNAME);
		values.put("PROD_ID", TPID);
		values.put("M_FLAG","U");
		values.put("S_FLAG","0");
		db.insert("retail_top_product",  null, values);

		String inert_top15products = "insert into retail_top_product ( STORE_ID , POS_USER , PROD_NM , PROD_SHORT_NM , PROD_ID , M_FLAG,S_FLAG ) values (" + "'" + PersistenceManager.getStoreId(mycontext) + "' ," + "'" + username + "' ," + "'" + TPRODUCTNAME + "'," + "'" + TPRODUCTSHORTNAME + "'," + "'" + TPID + "'," + "'" + "I'"+ "'," + "'0)";
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",inert_top15products);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));

		}catch (Exception e){}
		return true;

	}

	public ArrayList<Topfullproductmodel> getAllTopProducts(String userTypedString) {

		ArrayList<Topfullproductmodel> returnlist = new ArrayList<Topfullproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = userTypedString + "%";

			Cursor res = db.rawQuery("select distinct PROD_NM,PROD_ID from retail_store_prod_com where "
					+ " PROD_NM  like ? or PROD_ID like ?", params);
			if (res.moveToFirst())
				do {
					Topfullproductmodel topfullproductmodel = new Topfullproductmodel();

					topfullproductmodel.setProductname(res.getString(res.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
					//topfullproductmodel.setShortname(res.getString(res.getColumnIndex(TOP_PRODUCT_SORT_NAME)));
					topfullproductmodel.setProductId(res.getString(res.getColumnIndex(TOP_PRODUCT_ID)));
					returnlist.add(topfullproductmodel);
				}
				while (res.moveToNext());


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}







	ArrayList<Topfullproductmodel> topproductnameorid(String NameorId) {
		ArrayList<Topfullproductmodel> vendorlist = new ArrayList<Topfullproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = NameorId + "%";
			params[1] = NameorId + "%";

			Cursor cursor = db.rawQuery("select PROD_NM, PROD_ID from retail_store_prod_com where"
							+ " PROD_NM like ?  or  PROD_ID  like ? "
					, params);

           /* Cursor cursor = db.rawQuery("select Store_Id from retail_store_prod_com  where"
                    + " Store_Id like ? "
                    , params);
*/
			if (cursor.moveToFirst()) {
				do {
					Topfullproductmodel pm = new Topfullproductmodel();

					pm.setProductId(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_ID)));
					pm.setProductname(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
                   /* pm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_MRP)));
                    pm.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_PPRICE)));
                    pm.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SPRICE)));
                    pm.setSou(cursor.getString(cursor.getColumnIndex(COLUMN_TOP_PRODUCT_SOU)));
*/


					vendorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	public void tempsavesalesListdetail(String TRANS_ID, ArrayList<Sales> list,String Username) {
		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}

		for (Sales sales : list) {
			ContentValues contentValues = new ContentValues();
			int Quants = sales.getQuantity();
			Float stockquant = sales.getStockquant();


			contentValues.put("TRI_ID", TRANS_ID);
			contentValues.put("PROD_NM", sales.getProductName());
			contentValues.put("BATCH_NO", sales.getBatchNo());
			contentValues.put("EXP_DATE", sales.getExpiry());
			contentValues.put("S_PRICE", sales.getSPrice());
			contentValues.put("QTY", sales.getQuantity());
			contentValues.put("RES_STOCK", sales.getQuantity());
			contentValues.put("MRP", sales.getMrp());
			contentValues.put("UOM", sales.getUom());
			contentValues.put("FLAG","H");
			contentValues.put("STOCK", sales.getStockquant());
			contentValues.put("TOTAL", sales.getTotal());
			//contentValues.put("M_FLAG","I");
			contentValues.put("POS_USER",Username);
			contentValues.put("S_FLAG","0");
			db.insert("tmp_retail_str_sales_detail", null, contentValues);

		}

		db.close(); // Closing database connection
		Log.e("Database Operation for", "row inserted...");
		return;
	}


	public ArrayList<Sales> getallholdinvoicedata(String data) {
		ArrayList<Sales> holdbilllist = new ArrayList<Sales>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = data + "%";

		//Cursor productcursor = db.rawQuery("select a.TRI_ID,a.PROD_NM,a.BATCH_NO,a.EXP_DATE,a.S_PRICE,a.QTY,a.MRP,a.UOM,a.STOCK,a.TOTAL from tmp_retail_str_sales_detail a,tmp_retail_str_sales_master b where a.TRI_ID = b.TRI_ID and a.TRI_ID Like ? ",params);       if (productcursor.moveToFirst()) {
		Cursor productcursor = db.rawQuery("select TRI_ID,PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,MRP,UOM,STOCK,TOTAL from tmp_retail_str_sales_detail where"
				+ " TRI_ID Like ? ",params);
		if (productcursor.moveToFirst()) {
			do {
				Sales saleslist = new Sales();
				saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
				saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
				saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
				saleslist.setQuantity(productcursor.getInt(productcursor.getColumnIndex(QUANTITY)));
				saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(MRP)));
				saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(STOCK)));
				saleslist.setTrans_id(productcursor.getString(productcursor.getColumnIndex(TRANS_ID)));
				saleslist.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_TOTAL)));
				saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
				saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(S_PRICE)));

				holdbilllist.add(saleslist);

			} while (productcursor.moveToNext());
		}
		return holdbilllist;
	}


	public ArrayList<String> getTransidofholdbill() {
		ArrayList<String> imeilist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select distinct TRI_ID from tmp_retail_str_sales_detail where FLAG like 'H%'", null);
		if (cursor.moveToFirst()) {
			do {
				imeilist.add(cursor.getString(cursor.getColumnIndex(TRANS_ID)));

			} while (cursor.moveToNext());
		}
		return imeilist;

	}

	public ArrayList<PurchaseProductModelwithpo> getProductdataforInventory(String idorName) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";
			//params[3] = idorName + "%";
			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,S_PRICE,SELLING_ORDER_UNIT,PROFIT_MARGIN,MRP,S_PRICE,BARCODE,CONV_FACT,IND_NM ,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " PROD_ID  like ? or  PROD_NM  like ? And ACTIVE like 'Y%'  or BARCODE like ? "
					, params);

			if (productcursor.moveToFirst()) {
				do {

					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public boolean activestore() {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues contentValues = new ContentValues();

		contentValues.put("FLAG", "Y");
		contentValues.put("M_FLAG","U");

		db.update("retail_store", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(PersistenceManager.getStoreId(mycontext))});
		Log.e("Store Active ####", "row inserted...");
		return true;
	}

	public void SavePdfDetailForPurchase(String InvoiceNO, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID",InvoiceNO);
		contentValues.put("PREFIX_NM", "PO-");
		contentValues.put("POS_USER",username);
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");

		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		db.insert("retail_send_mail_pdf", null, contentValues);

		String save_pdf_detail_for_purchase = "insert into retail_send_mail_pdf (TXN_ID , PREFIX_NM , POS_USER ,VENDOR_NAME ," +
				" PO_NO , M_FLAG ) values ('" + ""
				+ "','" + "PO-" + "','" + username + "','" + VendorName + "','" + InvoiceNO + "','" + "I'" +"','" + "0')";


		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", save_pdf_detail_for_purchase);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}


		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void SavePdfDetailForInventorywithpo(String InvoiceNO, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", InvoiceNO);
		contentValues.put("PREFIX_NM", "INV_PO-");
		contentValues.put("POS_USER",username);
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("S_FLAG", "0");
		contentValues.put("M_FLAG","I");

		db.insert("retail_send_mail_pdf", null, contentValues);

		String inert_SavePDfDetailForInventorywithPo = "insert into retail_send_mail_pdf ( TXN_ID , PREFIX_NM , POS_USER , VENDOR_NAME , PO_NO , UNIVERSAL_ID , S_FLAG , M_FLAG) values (" + "'" + "" + "' ," + "'" + "INV_PO-" + "' ," + "'" + username + "'," + "'" + VendorName + "'," + "'" + InvoiceNO + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + "0'," + "'I')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", inert_SavePDfDetailForInventorywithPo);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void SavePDfDetailForInventoryWithoutPo(String InvoiceNO, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", InvoiceNO);
		contentValues.put("POS_USER",username);
		contentValues.put("PREFIX_NM", "INV_PO-");
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);
		String inert_SavePDfDetailForInventoryWithoutPo = "insert into retail_send_mail_pdf ( TXN_ID , POS_USER , PREFIX_NM , VENDOR_NAME , PO_NO , UNIVERSAL_ID , M_FLAG) values (" + "'" + "" + "' ," + "'" + username + "' ," + "'" + "INV_PO-" + "'," + "'" + VendorName + "'," + "'" + InvoiceNO + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + "I')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",inert_SavePDfDetailForInventoryWithoutPo);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void SavePdfDetailForInstallation(String InvoiceNO) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("Txn_Id", InvoiceNO);
		contentValues.put("Prefix_Nm", "INS-");
		contentValues.put("Vendor_Name", "");
		contentValues.put("Po_No", InvoiceNO);
		contentValues.put("Universal_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_Flag","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public ArrayList<DoctorPojo> getAllDoctors(String idorName) {
		ArrayList<DoctorPojo> DoctorList = new ArrayList<DoctorPojo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = idorName + "%";
			Cursor productcursor = db.rawQuery("select DR_ID,DR_NAME,SPECIALITY,ACTIVE from dr_discription where "
							+ " DR_NAME like ?"
					, params);
			if (productcursor.moveToFirst()) {
				do {
					DoctorPojo pm = new DoctorPojo();
					pm.setDoctorName(productcursor.getString(productcursor.getColumnIndex(DOCTORNAME)));
					pm.setDoctorSpeciality(productcursor.getString(productcursor.getColumnIndex(DOCTORSPECIALITY)));
					pm.setActive(productcursor.getString(productcursor.getColumnIndex(ACTIVE)));
					pm.setDoctid(productcursor.getString(productcursor.getColumnIndex(DOCTORID)));


					DoctorList.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return DoctorList;
	}

	public void saveDataforPdfVendorPayment(String InvoiceNO, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", InvoiceNO);
		contentValues.put("POS_USER",username);

		contentValues.put("PREFIX_NM", "VEND_PAY-");
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);

		String saveDataforPdfVendorPayment = "insert into retail_send_mail_pdf ( TXN_ID , POS_USER , PREFIX_NM , VENDOR_NAME , PO_NO , UNIVERSAL_ID,  M_FLAG ) values (''"+",'"  + username + "' ," + "'" + "VEND_PAY-" + "'," + "'" + VendorName + "'," + "'" + InvoiceNO + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'I'"+ "'," + "'0')";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",saveDataforPdfVendorPayment);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void saveDataforPdfVendorIndirectpayment(String InvoiceNO, String VendorName,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID",InvoiceNO);
		contentValues.put("STORE_ID",PersistenceManager.getStoreId(mycontext));
		contentValues.put("POS_USER",username);
		contentValues.put("PREFIX_NM", "VEND_INDIRECT-");
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);
		String insert_saveDataforPdfVendorIndirectpayment = "insert into retail_send_mail_pdf ( TXN_ID, STORE_ID , POS_USER , PREFIX_NM, VENDOR_NAME ,PO_NO  ,UNIVERSAL_ID  , M_FLAG ) values ('" + "','" + PersistenceManager.getStoreId(mycontext) + "' , '" + username + "' , '" + "VEND_INDIRECT-" + "', '"  + VendorName + "', '" + InvoiceNO + "' , '"  + PersistenceManager.getStoreId(mycontext) + "' , 'I'" + "','0')";
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",insert_saveDataforPdfVendorIndirectpayment);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void saveDataforPdfVendorReturn(String InvoiceNO, String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", InvoiceNO);
		contentValues.put("PREFIX_NM", "VEND_Return-");
		contentValues.put("VENDOR_NAME", VendorName);
		contentValues.put("PO_NO", InvoiceNO);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}
//************************ TOP 15 PRODUCT DEtAILS***************************//

	public ArrayList<Sales> gettopproducts() {

		ArrayList<Sales> topiemlist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select PROD_SHORT_NM,PROD_ID  from retail_top_product ", null);
			if (res.moveToFirst())
				Log.e(TAG, "getTableAsString called");
			{
				do {

					Sales topitemmodel = new Sales();
					topitemmodel.setProductshortName(res.getString(res.getColumnIndex(TOP_PRODUCT_SORT_NAME)));
					topiemlist.add(topitemmodel);
				} while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();


		}
		return topiemlist;

	}

	///********************TOP 15 PRODUCT RECIEVED  DATA DETAILS *********************///
	public ArrayList<Sales> getAllTopProductDetails(String idorName) {
		ArrayList<Sales> productNamelist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = idorName + "%";

			Cursor productcursor = db.rawQuery("select a.PROD_ID,a.PROD_NM,a.BATCH_NO,a.EXP_DATE,a.QTY,a.MRP,a.S_PRICE,a.UOM,a.CONV_FACT from retail_str_stock_master a,retail_top_product b where a.PROD_ID = b.PROD_ID and b.PROD_SHORT_NM like ? ORDER BY BATCH_NO DESC LIMIT 1"
					, params);
			//Cursor productcursor= db.rawQuery(MY_QUERY, new String[]{String.valueOf(STORE_ID)});

			if (null != productcursor && productcursor.moveToFirst() && productcursor.getCount() > 0) {
				do {
					Sales saleslist = new Sales();
					saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
					saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
					saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
					saleslist.setStockquant(productcursor.getFloat(productcursor.getColumnIndex(QUANTITY)));
					saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(MRP)));
					saleslist.setProdid(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					// saleslist.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
					// saleslist.setPPrice(res.getString(res.getColumnIndex(P_PRICE)));
					saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(S_PRICE)));
					saleslist.setConversionfacter(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));


					productNamelist.add(saleslist);
				} while (productcursor.moveToNext());

			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return productNamelist;
	}

	public ArrayList<String> getAllDoctorsforsale() {
		ArrayList<String> doctorlist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {


			Cursor res = db.rawQuery("select DR_NAME,ACTIVE from dr_discription where  ACTIVE='Y' ORDER BY DR_NAME ASC"
					, null);
			if (res.moveToFirst()) {
				do {
					doctorlist.add(res.getString(res.getColumnIndex(DOCTORNAME)));
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return doctorlist;
	}

	public void insertdataIntosendMailforSales(String InvoiceNo, String Name,String username,String mailid) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", InvoiceNo);
		contentValues.put("PREFIX_NM", "Sales-");
		contentValues.put("POS_USER",username);
		contentValues.put("VENDOR_NAME", Name);
		contentValues.put("PO_NO", InvoiceNo);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		contentValues.put("EMAIL_TO",mailid);



		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public void insertdataIntosendMailforSalesReturn(String InvoiceNo,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		ContentValues contentValues = new ContentValues();
		contentValues.put("TXN_ID", getSystemCurrentTime());
		contentValues.put("PREFIX_NM", "SalesReturn-");
		contentValues.put("POS_USER",username);
		//contentValues.put("VENDOR_NAME",Name);
		contentValues.put("PO_NO", InvoiceNo);
		contentValues.put("UNIVERSAL_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_send_mail_pdf", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void insertsalereturndataforproductdetail(String INVOICE, ArrayList<Salesreturndetail> list,String username) {


		SQLiteDatabase db = this.getWritableDatabase();
		int i = login.setboolean();
		if (list == null) {
			return;
		}
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);
		for (Salesreturndetail salesreturndetail : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("STORE_ID",PersistenceManager.getStoreId(mycontext));
			contentValues.put("TRI_ID", INVOICE);
			contentValues.put("POS_USER",username);
			contentValues.put("ID", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("PROD_NM", salesreturndetail.getSaleproductname());
			contentValues.put("BATCH_NO", salesreturndetail.getSalebatchno());
			contentValues.put("EXP_DATE", salesreturndetail.getSaleexpiry());
			contentValues.put("FLAG","Y");
			contentValues.put("S_PRICE", salesreturndetail.getSalesellingprice());
			contentValues.put("SALE_DATE",getDate());
			contentValues.put("QTY", salesreturndetail.getSaleqty());
			contentValues.put("MRP", salesreturndetail.getSalemrp());
			contentValues.put("UOM", salesreturndetail.getSaleuom());
			contentValues.put("TOTAL",salesreturndetail.getSaletotal());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");

			// Inserting Row
		//	db.insert("retail_str_sales_details_return", null, contentValues);

			if (i == 0) {
				contentValues.put("SLAVE_FLAG", "0");

			} else {
				contentValues.put("SLAVE_FLAG", "1");


				String SalesReturnProductDetail = "insert into retail_str_sales_details_return(STORE_ID ,TRI_ID ,POS_USER , ID , PROD_NM , BATCH_NO , EXP_DATE ,FLAG ,S_PRICE ,SALE_DATE ,QTY ,MRP ,UOM ,TOTAL ,M_FLAG ,S_FLAG ,SLAVE_FLAG )" +
						" values (" + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'"
						+ INVOICE + "'," + "'" + username + "'," + "'" + getSystemCurrentTimeinsalesreturnwithin()
						+ "'," + "'" + salesreturndetail.getSaleproductname() + "'," + "'" + salesreturndetail.getSalebatchno() + "'," + "'" + salesreturndetail.getSaleexpiry()
						+ "'," + "'Y'," + "'" + salesreturndetail.getSalesellingprice() + "'," + "'" + getDate() + "'," + "'" + salesreturndetail.getSaleqty() + "'," + "'"
						+ salesreturndetail.getSalemrp() + "'," + "'" + salesreturndetail.getSaleuom() + "'," + "'"
						+ salesreturndetail.getSaletotal() + "'," + "'I'," + "'0'," + "'1')";
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", SalesReturnProductDetail);
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}
			}
			db.insert("retail_str_sales_details_return", null, contentValues);

		}
		if(i==3)
		{
			login.bluetoothSendDataProductDetails();
			updateSlaveFlagForProductDetails();
			//db.execSQL("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		}

		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}


	public void insertsalereturndataforproductdetailwithoutinvoiceno(String INVOICE, ArrayList<SalesreturndetailWithoutPo> list,String username,String imeino) {

		SQLiteDatabase db = this.getWritableDatabase();

		if (list == null) {
			return;
		}
		for (SalesreturndetailWithoutPo salesreturndetail : list) {
			ContentValues contentValues = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			contentValues.put("STORE_ID",PersistenceManager.getStoreId(mycontext));
			contentValues.put("TRI_ID", INVOICE);
			contentValues.put("POS_USER",username);
			contentValues.put("ID", getSystemCurrentTimeinsalesreturnwithin());
			contentValues.put("PROD_NM", salesreturndetail.getSaleproductname());
			contentValues.put("BATCH_NO", salesreturndetail.getSalebatchno());
			contentValues.put("EXP_DATE", salesreturndetail.getSaleexpiry());
			contentValues.put("FLAG","Y");
			contentValues.put("SALE_DATE",getDate());
			contentValues.put("S_PRICE", salesreturndetail.getSalesellingprice());
			contentValues.put("QTY", salesreturndetail.getSaleqty());
			contentValues.put("MRP", salesreturndetail.getSalemrp());
			contentValues.put("UOM", salesreturndetail.getSaleuom());
			contentValues.put("TOTAL", salesreturndetail.getSaletotal());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			contentValues.put("EX_TRI_ID",imeino);

			// Inserting Row
			db.insert("retail_str_sales_details_return", null, contentValues);
		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;
	}

	public boolean updateStockQtyforsalereturn(ArrayList<Salesreturndetail> list) {

		boolean returnval = true;


		if (list == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Salesreturndetail salesreturn : list) {
			ContentValues contentValues = new ContentValues();


			float stockQuantity = salesreturn.getSalestockqty();

			float saleQuantity = salesreturn.getSaleqty();


			float newStockQuantity = stockQuantity + saleQuantity;
			if (newStockQuantity < 0) {
				newStockQuantity = 0;
			}
			contentValues.put("Qty", Double.toString(newStockQuantity));


			int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "Batch_No = ?  and " +
					"Prod_Id " +
					" = ? ", new String[]{salesreturn.getSalebatchno(), salesreturn.getSaleProdid()});
			if (sqlUpdateRetval < 1) {
				returnval = false;
			}
		}

		db.close();
		return returnval;
	}


	// ***********************!!!!!!VALIDATION TO CHECK EXIST Date!!!!!!!!*****************************
	public boolean CheckInstalltionkit(String s) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = Phone + "%";
		//String Query =("select Start_Date from retail_str_day_open_close where Start_Date = date('now')",null);
		Cursor cursor = db.rawQuery("SELECT FLAG from retail_store where FLAG  like  'N%' ", null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;

	}

	public boolean CheckbatchnoAlreadyInDBorNotforinvoice(String Batchno,String prodid,String Prodnm) {
		SQLiteDatabase db = this.getReadableDatabase();
   /* String[] params = new String[1];
      params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select * from retail_str_stock_master where "
				+  " PROD_NM ='"+Prodnm+"'and BATCH_NO='"+Batchno+"'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}

		return true;

	}



	public boolean CheckbatchnoAlreadyInDBorNotforwithoutinvoice(String expdate,String prodid,String prodNm) {
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = ("select EXP_DATE, PROD_ID, MRP ,CON_MUL_QTY from retail_str_stock_master where "
				+ " EXP_DATE ='"+expdate+"'  and PROD_NM ='"+prodNm+"'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularbatchqtyforinvoice(String expdate, String Prod_nm,String mrp) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[2];
//    params[0] = batchno + "%";
//    params[1] = Prod_Id + "%";
		String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_NM = '" + Prod_nm + "' and MRP = '" + mrp + "'");
		Log.e("Query::", "select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_NM = '" + Prod_nm + "'and MRP = '" + mrp + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty=cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return  getQty;

	}
	////////////////////////////////////////////get vendorReturnId from retail_str_vendor_detail_return//////////////////////////////

	ArrayList<ReportVendorReturnModel> getVendorReturnId(String id) {
		ArrayList<ReportVendorReturnModel> ReturnId = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct VENDOR_RETURN_ID,VENDOR_NM from retail_str_vendor_detail_return  where"
							+ " VENDOR_RETURN_ID like ? or VENDOR_NM like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel vm = new ReportVendorReturnModel();
					vm.setVendrId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_ID)));
					vm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					ReturnId.add(vm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return ReturnId;
	}
	//////////////////////////////////////////////get VendorReturn data from retail_str_vendor_detail_return///////////////////////////

	public ArrayList<ReportVendorReturnModel> getVendorReturnReport(String GrnId) {
		ArrayList<ReportVendorReturnModel> vendorReturnList = new ArrayList<ReportVendorReturnModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = GrnId + "%";
			params[1] = GrnId + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,VENDOR_NM,PROD_NM,BATCH_NO,EXP_DATE,REASON_OF_RETURN,QTY,P_PRICE,TOTAL,UOM from retail_str_vendor_detail_return where"
							+ " VENDOR_RETURN_ID like ? or VENDOR_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorReturnModel pm = new ReportVendorReturnModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_USER)));
					pm.setVendrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTVENDR_NM)));
					pm.setProdctNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTPROD_NM)));
					pm.setBatchNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTBATCH_NO)));
					pm.setExpDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTEXP_DATE)));
					pm.setReason(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTREASON_RETRN)));
					pm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_QTY)));
					pm.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRTP_Price)));
					pm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_UOM)));
					vendorReturnList.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return vendorReturnList;
	}


	public void updateqtyforinvoice(ArrayList<Salesreturndetail> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		int i = login.setboolean();
		try {
			boolean returnval = true;
			boolean fortrue = true;
			if (list == null) {
				return;
			}
			for (Salesreturndetail prod : list) {

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				fortrue = CheckbatchnoAlreadyInDBorNotforinvoice(prod.getSalebatchno(),prod.getSaleProdid(),prod.getSaleproductname());

				// for(int Batch=0;Batch<list.size();Batch++) {
				if (fortrue == true) {
					String batchqty = getparticularbatchqtyforinvoicereturn(prod.getSaleexpiry(), prod.getSaleproductname(),prod.getSalebatchno());
					float prodQuantity = prod.Saleqty;


					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {

						newStockQuantity = 0;
					}
					values.put("CON_MUL_QTY", Double.toString(newStockQuantity));
					values.put("M_FLAG","U");
					values.put("S_FLAG","0");
					if (i==0)
					{
						values.put("SLAVE_FLAG","0");
					}else {
						values.put("SLAVE_FLAG", "1");
						login.bluetoothdataForStockUpdate();

						String Update_StockQuantity_Sales = "UPDATE retail_str_stock_master SET CON_MUL_QTY  = "
								+ "'" + Double.toString(newStockQuantity) + "'" + ",M_FLAG = 'U' "
								+ " WHERE EXP_DATE = " + "'" + prod.getSaleexpiry() + "' AND BATCH_NO = '" + prod.getSalebatchno() + "'" +"'AND MRP = '" + String.valueOf(prod.getSalemrp()) + "'";

						updateSlaveFlagForUpdateQty();
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("Query", Update_StockQuantity_Sales);

							login.sendMessage(String.valueOf(jsonObject));
						} catch (Exception e) {
						}
					}

					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "EXP_DATE = ?  and " + "BATCH_NO " +
							" = ? and " + "PROD_NM " +" = ? ", new String[]{prod.getSaleexpiry(), prod.getSalebatchno(),prod.getSaleproductname()});

					Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", PROD_NM:" + prod.getSaleproductname()+"CON mul qty"+Double.toString(newStockQuantity));

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean CheckbatchnoAlreadyInDBorNotforinvoice(String batchno,String mrp) {
		SQLiteDatabase db = this.getReadableDatabase();
  /* String[] params = new String[1];
     params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select BATCH_NO, MRP,PROD_NM ,CON_MUL_QTY from retail_str_stock_master where "
				+ " BATCH_NO ='"+batchno+"'  and MRP ='"+mrp+"' and PROD_NM='"+mrp+"'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularbatchqtyforinvoicereturn(String expdate, String Prod_Nm,String BatchNo) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_NM = '" + Prod_Nm + "' and BATCH_NO = '" + BatchNo + "'");
		Log.e("Query::", "select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_NM = '" + Prod_Nm + "'and BATCH_NO = '" + BatchNo + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty=cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return  getQty;

	}


	public boolean CheckIsInvoiceReturn(String Phone) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = Phone + "%";
		String Query = ("select TRI_ID from retail_str_sales_master_return where " + " TRI_ID like ?");
		Cursor cursor = db.rawQuery(Query, params);
		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		cursor.close();
		return true;



	}

	public ArrayList<Inventoryproductmodel> getProductdataforwithoutInventory(String idorName) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";

			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,S_PRICE,SELLING_ORDER_UNIT,PROFIT_MARGIN, MRP ,S_PRICE, BARCODE,CONV_FACT,IND_NM,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " PROD_ID  like ? or  PROD_NM  like ?  AND ACTIVE like 'Y%' or BARCODE like  ?  "
					, params);

			if (productcursor.moveToFirst()) {
				do {

					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


   /*public void saveInventorywithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId,String username,String VendorNo,String VendorDate) {
      SQLiteDatabase db = this.getWritableDatabase();
      try {
         boolean returnval = true;
         boolean fortrue = false;
         if (list == null) {
            return;
         }
         for (Inventoryproductmodel prod : list) {
            ContentValues values = new ContentValues();

            PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
            PersistenceManager.getStoreId(mycontext);

            Log.e("Prodlength", String.valueOf(list.size()));
            float totalinventoryqty=prod.productQuantity+prod.getFreequantity();

            float mrp = prod.getMrp();
            float sprice=prod.getSprice();
            float conversin=prod.getConvfact();
            float newmrp = mrp / conversin;
            if (newmrp < 0) {
               newmrp = 0;
            }
            float newsprice = sprice / conversin;
            if (newsprice < 0) {
               newsprice = 0;
            }

            // for(int Batch=0;Batch<list.size();Batch++) {
            fortrue = CheckbatchnoAlreadyInDBorNotwithoutpo(prod.getExpdate(),prod.getProductId(),String.valueOf(prod.getMrp()));
            if (fortrue == false) {
               values.put("GRN_ID", grnId);
               values.put("VEND_NM", VendorName);
               //values.put("PO_NO", Ponumbers);
               //String demo = prod.getIndustry().toString();
               values.put("BATCH_NO", getSystemCurrentTime());

               values.put("MFG_BATCH_NO", prod.getBatchno());
               values.put("EXP_DATE", prod.getExpdate());
               values.put("POS_USER",username);
               values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
               values.put("PROD_ID", prod.getProductId());
               values.put("PROD_NM", prod.getProductName());
               values.put("P_PRICE", prod.getPprice());
               values.put("PROFIT_MARGIN", prod.getProductmargin());
               values.put("QTY", prod.getProductQuantity());
               values.put("MRP", prod.getMrp());
               values.put("S_PRICE", prod.getSprice());
               values.put("AMOUNT", prod.getTotal());
               values.put("UOM", prod.getTax());
               values.put("BARCODE", "NA");
               values.put("CONV_FACT", prod.getConvfact());
               values.put("FREE_GOODS", prod.getFreequantity());
               values.put("CON_MUL_QTY",prod.getTotalqty());
               values.put("PREV_QTY",totalinventoryqty);
               values.put("CONV_MRP",newmrp);
               values.put("CONV_SPRICE",newsprice);
               values.put("M_FLAG","I");
               values.put("INVENTORY_DATE",getDate());
               values.put("VENDOR_INVOICE_NO",VendorNo);
               values.put("VENDOR_INVOICE_DATE",VendorDate);
               values.put("PURCHASE_UNIT_CONV",prod.getPurchaseunitconv());
               values.put("DISCOUNT_PERCENT",prod.getInvdiscount());

               // Inserting Row
               db.insert("retail_str_stock_master", null, values);
               String insert_saveInventorywithoutpo = "insert into retail_str_stock_master " +
                     "( GRN_ID , VEND_NM , BATCH_NO , MFG_BATCH_NO , EXP_DATE , POS_USER , STORE_ID ," +
                     " PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
                     "BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE , " +
                     "VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , PURCHASE_UNIT_CONV , DISCOUNT_PERCENT)" +
                     " values (" + "'" + grnId + "' ," + "'" + VendorName + "' ," + "'" + getSystemCurrentTime() + "'," + "'"
                     + prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + username + "'," + "'"
                     + PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'"
                     + prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin()
                     + "'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getMrp() + "'," + "'" +
                     prod.getSprice() + "'," + "'" + prod.getTotal() + "'," + "'"  +  prod.getTax() + "'," + "'NA',"
                     + "'" +  prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'"  + prod.getTotalqty()
                     + "'," + "'" + totalinventoryqty + "','"+ newmrp + "'," + "'"
                     + newsprice + "'," + "'I'," + "'"  + getDate() + "'," + "'"  +  VendorNo + "'," + "'" + VendorDate + "',"
                     + "'" + prod.getPurchaseunitconv() + "'," + "'" + prod.getInvdiscount() + "')";
               try {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("Query",insert_saveInventorywithoutpo);
                  login logi = new login();
                  login.sendMessage(String.valueOf(jsonObject));
               }catch (Exception e){}

            } else {


               String batchqty = getparticularbatchqtywithoutpo(prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp()));
               values.put("GRN_ID", grnId);
               values.put("INVENTORY_DATE",getDate());
               values.put("M_FLAG","U");
               float prodQuantity = prod.getTotalqty();
               float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
               if (newStockQuantity < 0) {
                  newStockQuantity = 0;
               }
               values.put("CON_MUL_QTY", Double.toString(newStockQuantity));

               int sqlUpdateRetval = db.update("retail_str_stock_master", values, "EXP_DATE = ?  and " + "PROD_ID " +
                     " = ? and " + "MRP " +" = ? ", new String[]{prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp())});

               Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

               String update_inventory = "UPDATE retail_str_stock_master SET GRN_ID = "+ "'" + grnId + "'" + " ,INVENTORY_DATE = " + "'" + getDate() + "'" + " ,M_FLAG = 'U' "  + " , CON_MUL_QTY = " + "'" + Double.toString(newStockQuantity)  + "'" + " WHERE EXP_DATE = " + "'" +prod.getExpdate() + "'" +" and " + "PROD_ID = " + "'" +prod.getProductId() + "'" + " and " + "MRP = " + "'" +String.valueOf(prod.getMrp()) + "'";
               try {
                  JSONObject jsonObject = new JSONObject();
                  jsonObject.put("Query", update_inventory);
                  login logi = new login();
                  login.sendMessage(String.valueOf(jsonObject));
               }catch (Exception e){}


               if (sqlUpdateRetval < 1) {

                  Log.e("Update fail", "returned: " + sqlUpdateRetval);
                  returnval = false;
               }
               //return;
            }
            //}
         }
         db.close(); // Closing database connection
         Log.e("Database Operation", "row inserted...");
         return;

      } catch (NumberFormatException ex) {
         ex.printStackTrace();
      }
   }
*/



	public void saveInventorywithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId
			,String username,String VendorNo,String VendorDate) {
		// android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
		SQLiteDatabase db = this.getWritableDatabase();
		int i=login.setboolean();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Inventoryproductmodel prod : list) {
				ContentValues values = new ContentValues();
				Long value = System.currentTimeMillis();
				String Batchcurrent = Long.toString(value);



				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
				float totalinventoryqty=prod.productQuantity+prod.getFreequantity();

				float mrp = prod.getMrp();
				float sprice=prod.getSprice();
				float conversin=prod.getConvfact();
				float newmrp = mrp / conversin;
				if (newmrp < 0) {
					newmrp = 0;
				}
				float newsprice = sprice / conversin;
				if (newsprice < 0) {
					newsprice = 0;
				}

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNotwithoutpo(prod.getExpdate(),prod.getProductId(),String.valueOf(prod.getMrp()));
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("VEND_NM", VendorName);
					//values.put("PO_NO", Ponumbers);
					//String demo = prod.getIndustry().toString();
					String s = prod.getBatchno();
					if(s.matches(" ")){
						values.put("BATCH_NO", Batchcurrent);
					}
					else {
						values.put("BATCH_NO", s);
					}
					values.put("MFG_BATCH_NO", prod.getBatchno());
					values.put("EXP_DATE", prod.getExpdate());
					values.put("POS_USER",username);
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getPprice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("AMOUNT", prod.getTotal());
					values.put("UOM", prod.getTax());
					values.put("BARCODE", "NA");
					values.put("CONV_FACT", prod.getConvfact());
					values.put("FREE_GOODS", prod.getFreequantity());
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("PREV_QTY",totalinventoryqty);
					values.put("CONV_MRP",newmrp);
					values.put("CONV_SPRICE",newsprice);
					values.put("M_FLAG","I");
					values.put("INVENTORY_DATE",getDate());
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("PURCHASE_UNIT_CONV",prod.getPurchaseunitconv());
					values.put("DISCOUNT_PERCENT",prod.getInvdiscount());
					values.put("S_FLAG","0");
					// Inserting Row
					/*if(notconnected==false){
						values.put("SLAVE_FLAG","0");
					}else{
						values.put("SLAVE_FLAG","1");
					}*/
				//	db.insert("retail_str_stock_master", null, values);

					if (i==0)
					{
						values.put("SLAVE_FLAG","0");

					}else {
						values.put("SLAVE_FLAG", "1");

						if(s.matches(" ")) {

							String insert_saveInventorywithoutpo = "insert into retail_str_stock_master " +
									"( GRN_ID , VEND_NM , BATCH_NO , MFG_BATCH_NO , EXP_DATE , POS_USER , STORE_ID ," +
									" PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
									"BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE , " +
									"VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , PURCHASE_UNIT_CONV , DISCOUNT_PERCENT,S_FLAG)" +
									" values (" + "'" + grnId + "' ," + "'" + VendorName + "' ," + "'" + Batchcurrent + "'," + "'"
									+ prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + username + "'," + "'"
									+ PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'"
									+ prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin()
									+ "'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getMrp() + "'," + "'" +
									prod.getSprice() + "'," + "'" + prod.getTotal() + "'," + "'" + prod.getTax() + "'," + "'NA',"
									+ "'" + prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'" + prod.getTotalqty()
									+ "'," + "'" + totalinventoryqty + "','" + newmrp + "'," + "'"
									+ newsprice + "'," + "'I'," + "'" + getDate() + "'," + "'" + VendorNo + "'," + "'" + VendorDate + "',"
									+ "'" + prod.getPurchaseunitconv() + "'," + "'" + prod.getInvdiscount() + "'," + "'0')";

							try {
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("Query", insert_saveInventorywithoutpo);
								login logi = new login();
								login.sendMessage(String.valueOf(jsonObject));
							} catch (Exception e) {
							}
						}
					else{
							String insert_saveInventorywithoutpo = "insert into retail_str_stock_master " +
									"( GRN_ID , VEND_NM , BATCH_NO , MFG_BATCH_NO , EXP_DATE , POS_USER , STORE_ID ," +
									" PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
									"BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE , " +
									"VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , PURCHASE_UNIT_CONV , DISCOUNT_PERCENT,S_FLAG)" +
									" values (" + "'" + grnId + "' ," + "'" + VendorName + "' ," + "'" + s + "'," + "'"
									+ prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + username + "'," + "'"
									+ PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'"
									+ prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin()
									+ "'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getMrp() + "'," + "'" +
									prod.getSprice() + "'," + "'" + prod.getTotal() + "'," + "'" + prod.getTax() + "'," + "'NA',"
									+ "'" + prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'" + prod.getTotalqty()
									+ "'," + "'" + totalinventoryqty + "','" + newmrp + "'," + "'"
									+ newsprice + "'," + "'I'," + "'" + getDate() + "'," + "'" + VendorNo + "'," + "'" + VendorDate + "',"
									+ "'" + prod.getPurchaseunitconv() + "'," + "'" + prod.getInvdiscount() + "'," + "'0')";

							try {
								JSONObject jsonObject = new JSONObject();
								jsonObject.put("Query", insert_saveInventorywithoutpo);
								login logi = new login();
								login.sendMessage(String.valueOf(jsonObject));
							} catch (Exception e) {
							}



						}


					}



					db.insert("retail_str_stock_master", null, values);
					if(i==3)
					{
						login.bluetoothDataForGetInventoryTotal();
						updateSlaveFlagForInventoryByTotal();
					}
				} else {

					String batchqty = getparticularbatchqtywithoutpo(prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp()));
					values.put("GRN_ID", grnId);
					values.put("INVENTORY_DATE",getDate());
					values.put("M_FLAG","U");
					float prodQuantity = prod.getTotalqty();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("CON_MUL_QTY", Double.toString(newStockQuantity));

					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "EXP_DATE = ?  and " + "PROD_ID " +
							" = ? and " + "MRP " +" = ? ", new String[]{prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp())});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

					if (i==0)
					{
						values.put("SLAVE_FLAG","0");

					}else {
						values.put("SLAVE_FLAG", "1");


						String update_inventory = "UPDATE retail_str_stock_master SET GRN_ID = " + "'" + grnId + "'" + " ,INVENTORY_DATE = " + "'" + getDate() + "'" + " ,M_FLAG = 'U' " + " , CON_MUL_QTY = " + "'" + Double.toString(newStockQuantity) + "'" + " WHERE EXP_DATE = " + "'" + prod.getExpdate() + "'" + " and " + "PROD_ID = " + "'" + prod.getProductId() + "'" + " and " + "MRP = " + "'" + String.valueOf(prod.getMrp()) + "'";
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("Query", update_inventory);
							login logi = new login();
							login.sendMessage(String.valueOf(jsonObject));
						} catch (Exception e) {

						}
					}
					if(i==3)
					{
						login.bluetoothDataForUpdateInventoryData();
						updateSlaveFlagForUpdatedDataInventoryByTotal();

					}
					if (sqlUpdateRetval < 1) {

						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
				}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();

		}
	}
	/*public static   BluetoothChatService mChatService = null;
	public void setupChat() {
		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(mycontext,mHandler);
		// Initialize the buffer for outgoing messages
	}*/

	public boolean CheckbatchnoAlreadyInDBorNotwithoutpo(String expdate ,String prodid, String mrp) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = batchno + "%";
//    //  params[1] =productid + "%";

		String Query = ("select EXP_DATE, PROD_ID, MRP ,CON_MUL_QTY from retail_str_stock_master where "
				+ " EXP_DATE ='"+expdate+"' and PROD_ID='"+prodid+"' and MRP ='"+mrp+"'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}






	public ArrayList<InventoryStockadjustmentmodel> getstockProductName(String name) {


		ArrayList<InventoryStockadjustmentmodel> productlist = new ArrayList<InventoryStockadjustmentmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select * from retail_str_stock_master  where"
							+ " PROD_NM like ? "
					, params);

			if (cursor.moveToFirst()) {
				do {
					InventoryStockadjustmentmodel pm = new InventoryStockadjustmentmodel();

					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
					pm.setExpdate(cursor.getString(cursor.getColumnIndex(EXPIRY)));
					pm.setProductId(cursor.getString(cursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(cursor.getFloat(cursor.getColumnIndex(PRODUCTPURCHASE))/(cursor.getInt(cursor.getColumnIndex(PURCHASEUNITCONV))));
					pm.setSprice(cursor.getFloat(cursor.getColumnIndex(CONVSPRICE)));
					pm.setProductQuantity(cursor.getInt(cursor.getColumnIndex(TOTALQTY)));
									pm.setBatchno(cursor.getString(cursor.getColumnIndex(BATCH_NO)));
					pm.setBarcode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvMrp(cursor.getFloat(cursor.getColumnIndex(CONVMRP)));
					productlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}



	public String getparticularbatchqtywithoutpo(String expdate, String Prod_Id,String mrp) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
  /* String[] params = new String[1];
   params[0] = batchno + "%";
   params[1] = Prod_Id + "%";*/
		String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_ID = '" + Prod_Id + "' and MRP = '" + mrp + "'");
		Log.e("Query::", "select CON_MUL_QTY from retail_str_stock_master where " + " EXP_DATE =  '" + expdate + "' and PROD_ID = '" + Prod_Id + "'and MRP = '" + mrp + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;

	}

	public void saveGranddataintoGrnMasterwithoutpo(String result, String VendorName, String GrandTotal,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		int i=login.setboolean();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		ContentValues values = new ContentValues();
		values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		values.put("POS_USER",username);
		values.put("GRN_ID", result);
		values.put("VEND_NM", VendorName);
		values.put("GRAND_AMOUNT", GrandTotal);
		values.put("FLAG", "0");
		values.put("M_FLAG","I");
		if (i==0)
		{
			values.put("SLAVE_FLAG","0");

		}else {
			values.put("SLAVE_FLAG", "1");


			String inert_saveGranddataintoGrnMasterwithoutpo = "insert into retail_str_grn_master ( STORE_ID , POS_USER , GRN_ID , VEND_NM , GRAND_AMOUNT , M_FLAG , FLAG) values (" + "'" + PersistenceManager.getStoreId(mycontext) + "' ," + "'" + username + "' ," + "'" + result + "'," + "'" + VendorName + "'," + "'" + GrandTotal + "'," + "'I', '0')";
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query",inert_saveGranddataintoGrnMasterwithoutpo);
				login logi = new login();
				login.sendMessage(String.valueOf(jsonObject));
			}catch (Exception e){}

			db.insert("retail_str_grn_master", null, values);
		}
	if(i==3)
	{
		login.bluetoothDataGranddataintoGrnMasterWithoutPo();
		updateSlaveFlagForGrnMasterwithoutpo();
	}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted.into retail_str_grn_master..");


		return;
	}

	public ArrayList<String> getdoctorspecialication() {

		ArrayList<String> doctspeclist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
          /* String []params=new String[1];
           params[0]=name+"%";
         */
			Cursor cursor = db.rawQuery("select distinct SPECIALITY  from dr_speciality "
					, null);
			if (cursor.moveToFirst()) {
				do {

					doctspeclist.add(cursor.getString(cursor.getColumnIndex(COLUMN_DOCTOR_SPECIAL)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return doctspeclist;
	}


	public ArrayList<ReportDistributorModel> getDistributorForReport() {
		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select DSTR_NM, ACTIVE, POS_USER from retail_str_dstr  where"
							+ " ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportVendorModel> getVendorForReport() {
		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select VEND_NM, ACTIVE, POS_USER from retail_store_vendor  where"
							+ " ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(LOCALUSERNAME)));
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<ReportProductCpgModel> getProductCpgForReport() {
		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_cpg  where"
							+ " ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_USER_NM)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_CODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_S_PRICE)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_P_PRICE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					pm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaForReport() {
		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,PROD_ID,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN FROM retail_store_prod WHERE"
							+ " ACTIVE like 'Y%' ORDER BY PROD_NM ASC "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(USERNAME)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					//pm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProd_Id(cursor.getString(cursor.getColumnIndex(PRODUCTID)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTMRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTSELLING)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					pm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTMARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	//////////////////////////////////////////////// get DATA From year and Month For MediaAdTicker REPORT//////////////////////////////////////////////////////////


	public ArrayList<AdTickerReportModel> MediaAdTickerReport(String Value1, String Value2) {
		ArrayList<AdTickerReportModel> OldInvoiceData = new ArrayList<AdTickerReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct POS_USER,AD_MAIN_ID,AD_TEXT,AD_CST_SLB1 from retail_ad_ticker where "
					+ " LAST_MODIFIED between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					AdTickerReportModel vm = new AdTickerReportModel();
					vm.setUser_Nm(productcursor.getString(productcursor.getColumnIndex(COLUMN_POS_USER)));
					vm.setAD_MAIN_ID(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_MAIN_ID)));
					vm.setAD_TEXT(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_TEXT)));
					vm.setAD_CST_SLB1(productcursor.getString(productcursor.getColumnIndex(COLUMN_AD_CST_SLB1)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}

	public ArrayList<BlinkingLogoReportModel> BlinkingLogoReport(String Value1, String Value2) {
		ArrayList<BlinkingLogoReportModel> OldInvoiceData = new ArrayList<BlinkingLogoReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct POS_USER,AD_MAIN_ID,AD_DESC,AD_STRT_DT,AD_END_DT,AD_CST_SLB1,AD_CST_SLB2,AD_CST_SLB3 from retail_ad_blinkinglogo where "
					+ " LAST_MODIFIED between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					BlinkingLogoReportModel vm = new BlinkingLogoReportModel();
					vm.setUser_Nm(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_USER_NM)));
					vm.setAD_MAIN_ID(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_MAIN_ID)));
					vm.setAD_DESC(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_DESC)));
					vm.setAD_START_DATE(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_START_DATE)));
					vm.setAD_END_DATE(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_END_DATE)));
					vm.setAD_CST_SLB1(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB1)));
					vm.setAD_CST_SLB2(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB2)));
					vm.setAD_CST_SLB3(productcursor.getString(productcursor.getColumnIndex(COLUMN_Blinkinglogo_AD_CST_SLB3)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}


	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaForReport() {
		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN, POS_USER from retail_store_prod_local  where"
							+ " ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALUSERNAME)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALBARCODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALSELLING)));
					dm.setPPrice(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPURCHASE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALACTIVE)));
					dm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalProductCpgForReport() {
		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select PROD_NM, ACTIVE,POS_USER,BARCODE,MRP,S_PRICE,P_PRICE,PROFIT_MARGIN from retail_store_prod_local_cpg  where"
							+ " ACTIVE like 'Y%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_USER_NM)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_CODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_S_PRICE)));
					dm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_P_PRICE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					dm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportDistributorModel> getDistributorReportforActive(String Name) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = Name + "%";*/
			Cursor cursor = db.rawQuery("select DSTR_NM, ACTIVE, POS_USER from retail_str_dstr  where"
							+ " ACTIVE like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaReportforActive(String Name) {

		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select PROD_NM, ACTIVE, POS_USER,BARCODE,MRP,S_PRICE,P_PRICE,PROFIT_MARGIN from retail_store_prod_local  where"
							+ " ACTIVE like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALUSERNAME)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALBARCODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALSELLING)));
					dm.setPPrice(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPURCHASE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALACTIVE)));
					dm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaReportforActive(String Name) {

		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String orderBy = PRODUCTNAME + " ASC";
			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,PROD_ID,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod where"
							+ " ACTIVE like '" + Name + "'  ORDER BY PROD_NM ASC "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(USERNAME)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					//pm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProd_Id(cursor.getString(cursor.getColumnIndex(PRODUCTID)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTMRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTSELLING)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					pm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTMARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}
	public ArrayList<ReportVendorModel> getVendorReportforActive(String Name) {

		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select VEND_NM, ACTIVE, POS_USER from retail_store_vendor  where"
							+ " ACTIVE like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(LOCALUSERNAME)));
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalCpgReportforActive(String Name) {

		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select PROD_NM, ACTIVE, POS_USER,BARCODE,MRP,S_PRICE,P_PRICE,PROFIT_MARGIN from retail_store_prod_local_cpg  where"
							+ " ACTIVE like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_USER_NM)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_CODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_S_PRICE)));
					dm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_P_PRICE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					dm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductCpgModel> getCpgReportforActive(String Name) {

		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_cpg  where"
							+ " ACTIVE like '" + Name + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_USER_NM)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_CODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_S_PRICE)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_P_PRICE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					pm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


   /*////////////////////////////////////////////////DATA FROM  retail_str_sales_detail TABLE//////////////////////////////////////

   public ArrayList<SalesReturnReportModel> getSalesReturnReport(String TransId) {

      ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         String[] params = new String[1];
         params[0] = TransId + "%";
         Cursor cursor = db.rawQuery("select distinct POS_USER,TOTAL,TRI_ID,PROD_NM,QTY,SALE_DATE from retail_str_sales_details_return where"
                     + " TRI_ID like ?  "
               , params);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel sm = new SalesReturnReportModel();
               sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
               sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_TOTAL)));
               sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
               sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
               sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
               idlist.add(sm);

            } while (cursor.moveToNext());
         }

      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      }

      return idlist;
   }

///////////////////////////////////////////////////////////////

   public ArrayList<SalesReturnReportModel> getInvoiceTransId(String id) {
      ArrayList<SalesReturnReportModel> transidlist = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         String[] params = new String[1];
         params[0] = id + "%";
         Cursor cursor = db.rawQuery("select distinct TRI_ID from retail_str_sales_details_return  where"
                     + " TRI_ID like ?  "
               , params);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel sm = new SalesReturnReportModel();
               sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               transidlist.add(sm);
            } while (cursor.moveToNext());
         }

      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      }

      return transidlist;
   }

//////////////////////////////////////////////// get DATA From year and Month For Sale REPORT//////////////////////////////////////////////////////////

   public ArrayList<SalesReturnReportModel> SalesReturnData(String Value3, String Value4) {
      ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("select distinct POS_USER,TOTAL,TRI_ID,PROD_NM,QTY,SALE_DATE from retail_str_sales_details_return where "
               + " SALE_DATE between '" + Value3 + "' AND '" + Value4 + "'", null);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel sm = new SalesReturnReportModel();
               sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
               sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_TOTAL)));
               sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
               sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
               sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
               OldInvoiceData.add(sm);
            } while (cursor.moveToNext());
         }
      } catch (IndexOutOfBoundsException cur) {
         cur.printStackTrace();
      }
      return OldInvoiceData;
   }

///////////////////////////////////////////////////////////////

   public ArrayList<SalesReturnReportModel> getWithoutInvoiceTransId(String id) {
      ArrayList<SalesReturnReportModel> transidlist = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         String[] params = new String[1];
         params[0] = id + "%";
         Cursor cursor = db.rawQuery("select distinct TRI_ID from retail_str_sales_details_return  where"
                     + " TRI_ID like ?  "
               , params);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel sm = new SalesReturnReportModel();
               sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               transidlist.add(sm);
            } while (cursor.moveToNext());
         }

      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      }

      return transidlist;
   }

//////////////////////////////////////////////// get DATA From year and Month For Sale REPORT//////////////////////////////////////////////////////////

   public ArrayList<SalesReturnReportModel> SalesReturn2Data(String Value3, String Value4) {
      ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("select distinct POS_USER,TOTAL,TRI_ID,PROD_NM,QTY,SALE_DATE from retail_str_sales_details_return where "
               + " SALE_DATE between '" + Value3 + "' AND '" + Value4 + "'", null);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel vm = new SalesReturnReportModel();
               vm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
               vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_TOTAL)));
               vm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               vm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
               vm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
               vm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
               OldInvoiceData.add(vm);
            } while (cursor.moveToNext());
         }
      } catch (IndexOutOfBoundsException cur) {
         cur.printStackTrace();
      }
      return OldInvoiceData;
   }

   public ArrayList<SalesReturnReportModel> getSalesReturn2Report(String TransId) {

      ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         String[] params = new String[1];
         params[0] = TransId + "%";
         Cursor cursor = db.rawQuery("select distinct POS_USER,TOTAL,TRI_ID,PROD_NM,QTY,SALE_DATE from retail_str_sales_details_return where"
                     + " TRI_ID like ?  "
               , params);
         if (cursor.moveToFirst()) {
            do {
               SalesReturnReportModel sm = new SalesReturnReportModel();
               sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
               sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_TOTAL)));
               sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
               sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
               sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
               sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
               idlist.add(sm);

            } while (cursor.moveToNext());
         }

      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      }

      return idlist;
   }*/

	public void updateqtyforinvoiceforsalesreturnwithoutinvoiceno(ArrayList<SalesreturndetailWithoutPo> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (SalesreturndetailWithoutPo salesreturndetail : list) {

				ContentValues contentValues = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				fortrue = CheckbatchnoAlreadyInDBorNotforwithoutinvoice(salesreturndetail.getSaleexpiry(),salesreturndetail.getSaleProdid(),salesreturndetail.getSaleproductname());

				// for(int Batch=0;Batch<list.size();Batch++) {
				if (fortrue == false) {
					PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
					PersistenceManager.getStoreId(mycontext);
					contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					contentValues.put("PROD_NM", salesreturndetail.getSaleproductname());
					contentValues.put("BATCH_NO", salesreturndetail.getSalebatchno());
					contentValues.put("EXP_DATE", salesreturndetail.getSaleexpiry());
					contentValues.put("S_PRICE", salesreturndetail.getSalesellingprice());
					contentValues.put("CON_MUL_QTY", salesreturndetail.getSaleqty());
					contentValues.put("MRP", salesreturndetail.getSalemrp());
					contentValues.put("UOM", salesreturndetail.getSaleuom());
					contentValues.put("PROD_ID", salesreturndetail.getSaleProdid());
					contentValues.put("M_FLAG","I");
					contentValues.put("S_FLAG","0");

					// Inserting Row
					db.insert("retail_str_stock_master", null, contentValues);
				} else {
					String batchqty = getparticularbatchqtyforinvoice(salesreturndetail.getSaleexpiry(), salesreturndetail.getSaleproductname(),salesreturndetail.getSalemrp());
					float prodQuantity = salesreturndetail.Saleqty;


					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					contentValues.put("CON_MUL_QTY", Double.toString(newStockQuantity));
					contentValues.put("M_FLAG","U");
					int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues,  "EXP_DATE = ?  and " + "PROD_ID " +
							" = ? and " + "MRP " +" = ? ", new String[]{salesreturndetail.getSaleexpiry(), salesreturndetail.getSaleProdid(),salesreturndetail.getSalemrp()});

					Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleproductname());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<PurchaseProductModel> getAllHoldPurchaseData(String spinnervalue) {
		ArrayList<PurchaseProductModel> OldInvoiceData = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = spinnervalue + "%";
			//distinct Prod_Id,Prod_Nm,P_Price,Selling_Order_Unit,MRP,Profit_Margin,S_Price,BarCode,Active,Conv_Fact,Ind_Nm
			Cursor productcursor = db.rawQuery("select PO_NO,PROD_ID,PROD_NM,P_PRICE,MRP,UOM,QTY,TOTAL,VENDOR_NM,CONV_FACT,FLAG,S_PRICE,CONV_FACT,IND_NM from retail_str_po_detail_hold where "
							+ " PO_NO  like ?  "
					, params);
			if (spinnervalue == null) {
				return null;
			}
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_PRODUCT_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_P_PRICE)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_VENDOR_DISTRIBUTOR_NAME)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_UOM)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(Purchase_COLUMN_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(Purchase_COLUMN_TOTAL)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_MRP)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(Purchase_COLUMN_Conv_Fact)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					//pm.setGetConMulQty(productcursor.getFloat(productcursor.getColumnIndex("S")));
					//pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
               /*pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
               pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
               pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
               pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
               pm.setMRP(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
               pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
               pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN)));

            pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));*/

					OldInvoiceData.add(pm);
				} while (productcursor.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return OldInvoiceData;
	}



	public ArrayList<PurchaseInvoiceDropDownModel> getInvoiceNumberForPurchase() {
		ArrayList<PurchaseInvoiceDropDownModel> LastInvoicelist = new ArrayList<PurchaseInvoiceDropDownModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct PO_NO,FLAG,LAST_MODIFIED from retail_str_po_detail_hold where FLAG like 'H%'", null);

			if (cursor.moveToFirst()) {
				do {
					PurchaseInvoiceDropDownModel purchaseInvoiceDropDownModel = new PurchaseInvoiceDropDownModel();
					purchaseInvoiceDropDownModel.setPurchaseOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					purchaseInvoiceDropDownModel.setFlag(cursor.getString(cursor.getColumnIndex(Purchase_COLUMN_Flag)));
					//purchaseInvoiceDropDownModel.setLastUpdate(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_LASTUPDATE)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


	public void replaceflag(String prod) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("FLAG", "N");
		values.put("M_FLAG","U");

		int sqlUpdateRetval = db.update("retail_str_po_detail_hold", values, "PO_NO " + " = ? ",
				new String[]{prod.toString()});
		String replace_flag  = "UPDATE retail_str_po_detail_hold set FLAG = 'N' , M_FLAG = 'U' where PO_NO = '"+prod.toString()+"'";

		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",replace_flag);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		// Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}
	}


   /*public void saveInventoryholdbillwithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId) {
      SQLiteDatabase db = this.getWritableDatabase();
      try {

         if (list == null) {
            return;
         }
         for (Inventoryproductmodel prod : list) {
            PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
            PersistenceManager.getStoreId(mycontext);

            ContentValues values = new ContentValues();
            Log.e("Prodlength", String.valueOf(list.size()));
            values.put("Grn_Id", grnId);
            values.put("Vend_Nm", VendorName);
            values.put("Batch_No", prod.getBatchno());
            values.put("Exp_Date", prod.getExpdate());
            values.put("Store_Id", PersistenceManager.getStoreId(mycontext));
            values.put("Prod_Id", prod.getProductId());
            values.put("Prod_Nm", prod.getProductName());
            values.put("P_Price", prod.getPprice());
            values.put("Profit_margin", prod.getProductmargin());
            values.put("Qty", prod.getProductQuantity());
            values.put("MRP", prod.getMrp());
            values.put("S_Price", prod.getSprice());
            values.put("Amount", prod.getTotal());
            values.put("Uom", prod.getTax());
            values.put("BarCode", "NA");
            values.put("Conv_Fact",prod.getConvfact());
            values.put("Flag","H");
            values.put("Ind_Nm",prod.getIndustry());

            // Inserting Row
            db.insert("retail_str_stock_master_hold", null, values);
         }

         db.close(); // Closing database connection
         Log.e("Database Operation", "row inserted...");
         return;

      } catch (NumberFormatException ex) {
         ex.printStackTrace();
      }
   }

*/


	public ArrayList<PurchaseProductModelwithpo> getholddataforinventory(String PO) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PO_NO,GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,FREE_GOODS,DISCOUNT_PERCENT  from retail_str_stock_master_hold where "
							+ " GRN_ID  like ? "
					, params);



			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatch_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExp_Date(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}
///////////////////////////////////////////////hold/////////////////////////////////////////////////
	public ArrayList<String> getgrnNumberForinventory() {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct GRN_ID, FLAG,LAST_MODIFIED from retail_str_stock_master_hold where FLAG = 'H' ", null);
			if (cursor.moveToFirst()) {
				do {

					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_NO)));
					//LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_FLAG)));


				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}

	public void Updateflag(String prod) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("FLAG", "N");
		values.put("M_FLAG","U");

		int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "GRN_ID " + " = ? ",
				new String[]{prod.toString()});
		// Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}
	}


	public ArrayList<Inventoryproductmodel> getholddataforinventoryDiscountinventory(String PO) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
        /*String[] params = new String[1];
        params[0] = PO + "%";*/

			Cursor productcursor = db.rawQuery(" select GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT , PROFIT_MARGIN , UOM,CONV_FACT,IND_NM,DISCOUNT_PERCENT,M_FLAG  from retail_str_stock_master_hold where "
							+ " GRN_ID ='"+PO+"' "
					, null);



			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();


					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setBatchno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExpdate(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					//pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setHoldtotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					//pm.setMFlag(productcursor.getString(productcursor.getColumnIndex("M_FLAG")));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////hold(total)	/////

	public ArrayList<String> getgrnNumberForinventorytotal() {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct GRN_ID, FLAG,LAST_MODIFIED from retail_str_stock_master_hold where FLAG = 'HT' ", null);
			if (cursor.moveToFirst()) {
				do {

					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_NO)));
					//LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_FLAG)));


				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}



	public ArrayList<InventoryTotalProductModel> getholddataforinventoryTotalinventory(String PO) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
        /*String[] params = new String[1];
        params[0] = PO + "%";*/

			Cursor productcursor = db.rawQuery(" select GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,DISCOUNT_PERCENT,M_FLAG  from retail_str_stock_master_hold where "
							+ " GRN_ID ='"+PO+"' "
					, null);



			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();


					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setBatchno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExpdate(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setUOM(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					//pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
				//	pm.setMFlag(productcursor.getString(productcursor.getColumnIndex("M_FLAG")));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}



	public void saveInventoryholdbillwithpo(ArrayList<PurchaseProductModelwithpo> list, String VendorName, String Ponumbers, String grnId,String VendorNo,String VendorDate) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (PurchaseProductModelwithpo prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));
				//float holdqty=prod.getProductQuantity()+prod.getDiscountitems();

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNotForHoldwithpo(prod.getBatch_No());
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("VEND_NM", VendorName);
					values.put("PO_NO", Ponumbers);
					values.put("BATCH_NO", getSystemCurrentTime());

					values.put("MFG_BATCH_NO", prod.getBatch_No());
					values.put("EXP_DATE", prod.getExp_Date());
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getProductPrice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("AMOUNT", prod.getTotal());
					values.put("UOM", prod.getUom());
					values.put("BARCODE", "NA");
					values.put("CONV_FACT", prod.getConversion());
					values.put("FREE_GOODS", prod.getDiscountitems());
					values.put("FLAG", "H");
					values.put("IND_NM", prod.getIndustery());
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("M_FLAG","U");
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("DISCOUNT_PERCENT",prod.getInvpodiscount());
					values.put("S_FLAG","0");
					// Inserting Row
					db.insert("retail_str_stock_master_hold", null, values);

					String insert_saveInventoryholdbillwithpo = "insert into retail_str_stock_master_hold" +
							" ( GRN_ID , VEND_NM , PO_NO , BATCH_NO , MFG_BATCH_NO , EXP_DATE  , STORE_ID , PROD_ID, PROD_NM , P_PRICE, " +
							"PROFIT_MARGIN , QTY, MRP ,S_PRICE , AMOUNT , UOM , BARCODE , CONV_FACT , FREE_GOODS, FLAG , IND_NM , " +
							"CON_MUL_QTY ,M_FLAG , VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , DISCOUNT_PERCENT ) values " +
							"(" + "'" + grnId + "' ," + "'" + VendorName + "'," + "'" + Ponumbers + "'," + "'" + getSystemCurrentTime()
							+ "'," + "'" + prod.getBatch_No() + "'," + "'" + prod.getExp_Date() + "'," + "'"
							+ PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'" + prod.getProductName()
							+ "'," + "'" + prod.getProductPrice() + "'," + "'" + prod.getProductmargin() + "'," + "'" + prod.getProductQuantity() + "'," + "'" +
							prod.getMrp() + "'," + "'" + prod.getSprice() + "'," + "'"  +  prod.getTotal() + "'," + "'" + prod.getUom() + "',"
							+ "'"  + "NA'," + prod.getConversion() + "'," + "'" + prod.getDiscountitems() + "'," + "H',"  + prod.getIndustery()
							+ "'," + "'" + prod.getTotalqty() + "'," + "'" + "U',"   + VendorNo + "'," + "'" + VendorDate + "'," + "'"
							+ prod.getInvpodiscount() + ")";


					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",insert_saveInventoryholdbillwithpo);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}

				} else {
					String batchqty = getparticularbatchqtyForHoldwithpo(prod.getBatch_No(), prod.getProductId());
					values.put("GRN_ID", grnId);
					int prodQuantity = prod.productQuantity;
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("FLAG", "H");
					values.put("M_FLAG","U");
					values.put("QTY", prod.productQuantity);
					int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "BATCH_NO = ?  and " +
							"PROD_ID " +
							" = ? ", new String[]{prod.getBatch_No(), prod.getProductId()});



					String update_sqlUpdateRetva = "UPDATE retail_str_stock_master_hold SET GRN_ID = "+ "'" + grnId + "'" + " ,FLAG = 'H' " + ", M_FLAG = 'U' " +  " , QTY = " + "'" + prod.productQuantity + "'" +  " WHERE BATCH_NO = " + "'" + prod.getBatch_No() + "'" +" and " + "PROD_ID = " + "'" + prod.getProductId() + "'";


					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",update_sqlUpdateRetva);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}


					Log.d("Sudhee", "Update done for batch:" + prod.getBatch_No() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<InventoryStockadjustmentmodel>getProductStockAdjustment(InventoryStockadjustmentmodel name){
		ArrayList<InventoryStockadjustmentmodel> productlist = new ArrayList<InventoryStockadjustmentmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[2];
      params[0] = name + "%";
      params[1] = name + "%";*/
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,CONV_MRP,CONV_SPRICE,P_PRICE,CON_MUL_QTY,PURCHASE_UNIT_CONV from retail_str_stock_master where "
					+ " PROD_NM ='"+name+"' ", null);

			if (cursor.moveToFirst()) {
				do {
					InventoryStockadjustmentmodel pm = new InventoryStockadjustmentmodel();

					pm.setExpdate(cursor.getString(cursor.getColumnIndex(EXPIRY)));
					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(cursor.getFloat(cursor.getColumnIndex(PRODUCTPURCHASE))/(cursor.getInt(cursor.getColumnIndex(PURCHASEUNITCONV))));
					pm.setSprice(cursor.getFloat(cursor.getColumnIndex(CONVSPRICE)));
					pm.setConvMrp(cursor.getFloat(cursor.getColumnIndex(CONVMRP)));
					pm.setBatchno(cursor.getString(cursor.getColumnIndex(BATCH_NO)));
					pm.setProductQuantity(cursor.getInt(cursor.getColumnIndex(TOTALQTY)));
					pm.setPurchseunitconv(cursor.getInt(cursor.getColumnIndex(PURCHASEUNITCONV)));


					productlist.add(pm);

				} while (cursor.moveToNext());

			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}


	public boolean CheckbatchnoAlreadyInDBorNotForHoldwithpo(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[1];
      params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select BATCH_NO,PROD_ID,QTY from retail_str_stock_master_hold where "
				+ " BATCH_NO like ?");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}
	public void updateStockAdjustment(ArrayList<InventoryStockadjustmentmodel> list,String VendorName) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval=false;
		if (list == null) {
			return;
		}
		for (InventoryStockadjustmentmodel prod : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);

			ContentValues values = new ContentValues();
			Log.e("PRODLENGTH", String.valueOf(list.size()));

			// for(int Batch=0;Batch<list.size();Batch++) {
			values.put("BATCH_NO",prod.getBatchno());
			values.put("P_PRICE", prod.getPprice());
			values.put("CONV_MRP", prod.getConvMrp());
			values.put("CONV_SPRICE", prod.getSprice());

			values.put("EXP_DATE",prod.getExpdate());
			values.put("CON_MUL_QTY",prod.getProductQuantity());
			values.put("VEND_NM",VendorName);
			values.put("S_FLAG","0");
			int sqlUpdateRetval = db.update("retail_str_stock_master", values, "PROD_NM = ?  and " +
					"BATCH_NO " +
					" = ? ", new String[]{prod.getProductName(), prod.getBatchno()});

			Log.d("Rahulllll...", "Update done for PRodNAme:" + prod.getProductName() + ", BAtch:" + prod.getBatchno());

			String updateStockAdjustment = "UPDATE retail_str_stock_master SET BATCH_NO = "
					+ "'" + prod.getBatchno() + "'" + ",P_PRICE = " + "'" + prod.getPprice() + "'"
					+ ",CONV_MRP = " + "'" + prod.getConvMrp()
					+ "'"  + ",CONV_SPRICE = " + "'" + prod.getSprice() + "'" +",CON_MUL_QTY = "
					+ "'" + prod.getProductQuantity() + "'" +",VEND_NM = " + "'" + VendorName
					+ "'" + " ,EXP_DATE= " + "'" + prod.getExpdate() + "'"+ "  WHERE PROD_NM= " + "'" + prod.getProductName()
					+ "'" + " AND BATCH_NO= " + "'" + prod.getBatchno() + "'";
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query", updateStockAdjustment);
				login logi = new login();
				login.sendMessage(String.valueOf(jsonObject));
			}catch (Exception e){}

			if (sqlUpdateRetval < 1) {
				Log.e("Update fail", "returned: " + sqlUpdateRetval);
				returnval = false;
			}

		}
		//}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;


	}



	public String getparticularbatchqtyForHoldwithpo(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
   /* String[] params = new String[1];
    params[0] = batchno + "%";
    params[1] = Prod_Id + "%";*/
		String Query = ("select QTY from retail_str_stock_master_hold where " + " BATCH_NO =  '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
		Log.e("Query::", "select QTY from retail_str_stock_master_hold where " + " BATCH_NO =  '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}


	public void saveInventoryholdbillwithoutpo(ArrayList<Inventoryproductmodel> list, String VendorName, String grnId,String VendorNo,String VendorDate) {

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Inventoryproductmodel prod : list) {


				ContentValues values = new ContentValues();

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
				// float holdqty=prod.getProductQuantity()-prod.getFreequantity();
				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckholdbatchnoAlreadyInDBorNotwithoutpo(prod.getBatchno());
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("VEND_NM", VendorName);
					//values.put("PO_NO", Ponumbers);
					//String demo = prod.getIndustry().toString();
					values.put("BATCH_NO", getSystemCurrentTime());
					values.put("MFG_BATCH_NO", prod.getBatchno());

					values.put("EXP_DATE", prod.getExpdate());
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getPprice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("AMOUNT", prod.getTotal());
					values.put("UOM", prod.getTax());
					values.put("BARCODE", "NA");
					values.put("FLAG", "H");
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("CONV_FACT", prod.getConvfact());
					values.put("FREE_GOODS", prod.getFreequantity());
					values.put("M_FLAG","I");
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("DISCOUNT_PERCENT",prod.getInvdiscount());
					values.put("S_FLAG","0");
					// Inserting Row


					db.insert("retail_str_stock_master_hold", null, values);


					String insert_saveInventoryholdbillwithoutpo = "insert into retail_str_stock_master_hold ( GRN_ID ,  VEND_NM ,  BATCH_NO , MFG_BATCH_NO , EXP_DATE  , STORE_ID , PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN , QTY, MRP ,S_PRICE , AMOUNT , UOM , BARCODE , FLAG , CON_MUL_QTY , CONV_FACT , FREE_GOODS , M_FLAG  ,  VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , DISCOUNT_PERCENT ) values (" + "'" + grnId + "' ," + "'" + VendorName + "'," + "'" + getSystemCurrentTime() + "'," + "'" + prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" +prod.getProductId() + "'," + "'" + prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin() + "'," + "'" + prod.getProductQuantity() + "'," + "'" +
							prod.getMrp() + "'," + "'" + prod.getSprice() + "'," + "'"  +  prod.getTotal() + "'," + "'" +  prod.getTax() + "'," + "'"  + "NA',"  + "H ',"  + prod.getTotalqty() + "'," + "'" + prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'" + "I ',"  + VendorNo + "'," + "'" + VendorDate + "'," + "'" + prod.getInvdiscount()  +  "'," + "'" + "I ')";

					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",insert_saveInventoryholdbillwithoutpo);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}

				} else {

					String batchqty = getparticularholdbatchqtywithoutpo(prod.getBatchno(), prod.getProductId());
					values.put("GRN_ID", grnId);
					int prodQuantity = prod.productQuantity;
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("FLAG", "H");
					values.put("M_FLAG","U");
					values.put("QTY", prod.productQuantity);

					int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "BATCH_NO = ?  and " + "PROD_ID " +
							" = ? ", new String[]{prod.getBatchno(), prod.getProductId()});

					String update_sqlUpdateRetval = "UPDATE retail_str_stock_master_hold SET GRN_ID = "+ "'" + grnId + "'" + " ,FLAG = 'H' " + ", M_FLAG = 'U' " +  " , QTY = " + "'" + prod.productQuantity + "'" +  " WHERE BATCH_NO = " + "'" + prod.getBatchno() + "'" +" and " + "PROD_ID = " + "'" + prod.getProductId() + "'";

					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",update_sqlUpdateRetval);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}


	public boolean CheckholdbatchnoAlreadyInDBorNotwithoutpo(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
//    String[] params = new String[1];
//    params[0] = batchno + "%";
		//  params[1] =productid + "%";

		String Query = ("select MFG_BATCH_NO,PROD_ID,QTY from retail_str_stock_master_hold where "
				+ " BATCH_NO like ?");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}


	public String getparticularholdbatchqtywithoutpo(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
  /* String[] params = new String[1];
   params[0] = batchno + "%";
   params[1] = Prod_Id + "%";*/
		String Query = ("select QTY from retail_str_stock_master_hold where " + " MFG_BATCH_NO =  '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
		Log.e("Query::", "select Qty from retail_str_stock_master_hold where " + " MFG_BATCH_NO =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(QUANTITY));
		}
		return getQty;

	}


	/*public ArrayList<String> getVendorReturndataForVendorPayment() {
      SQLiteDatabase db = this.getReadableDatabase();
      ArrayList<String> LastInvoicelist = new ArrayList<String>();
      Cursor cursor = db.rawQuery("select vendor_Return_Id from retail_str_vendor_master_return ", null);
      if (cursor.moveToFirst()) {
            do {
               LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_RETURNID)));
            } while (cursor.moveToNext());
         }
      return LastInvoicelist;

   }*/
	public ArrayList<String> getVendorReturndataForVendorPayment() {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
   /* String []params=new String[2];
      params[0]=VendorName+"%";
      params[1]="0";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_RETURN_ID from retail_str_vendor_master_return "
					, null);

			if (cursor.moveToFirst()) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_RETURNID)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}


/*************************************************************************************************************************/
	/***************************************
	 * CUSTOMER REJECTION
	 *******************************************************************/

	public ArrayList<String> getReasonRejection() {

		ArrayList<String> returnlist = new ArrayList<String>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct REASON_RETURN from retail_store_sales_desc", null);
			if (res.moveToFirst()) {
				do {
					//returnlist.add(res.getString(res.getColumnIndex(REJECTID)));
					returnlist.add(res.getString(res.getColumnIndex(REJECTIONREASON)));
         /*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<CustomerRejectModel> getCustomerRejection(String invoiceno) {

		ArrayList<CustomerRejectModel> salesreturnlist = new ArrayList<CustomerRejectModel>();
		try {

			String[] params = new String[1];
			params[0] = invoiceno + "%";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select ID,REASON_RETURN,LAST_MODIFIED from retail_store_sales_desc where"
					+ " REASON_RETURN like ? ", params);
			if (res.moveToFirst()) {
				do {
					CustomerRejectModel customerrejectiondetail = new CustomerRejectModel();

					customerrejectiondetail.setmVId(res.getString(res.getColumnIndex(REJECTIONID)));
					customerrejectiondetail.setmVReason(res.getString(res.getColumnIndex(REJECTIONREASON)));
					customerrejectiondetail.setLastupdate(res.getString(res.getColumnIndex(REJECTIONLASTUP)));
					salesreturnlist.add(customerrejectiondetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}


	public void updateCReason(String STORE_ID, String VREASON) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		// contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("ID", getSystemCurrentTime());
		contentValues.put("REASON_RETURN", VREASON);
		contentValues.put("M_FLAG","I");

		db.insert("retail_store_sales_desc", null, contentValues);

		return;
	}

//***********************************************************************************************************************/
	//**************************************Vendor Rejection *****************************************************//

	public ArrayList<String> getReasonofReject() {

		ArrayList<String> returnlist = new ArrayList<String>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct REASON_FOR_REJECTION from retail_store_vend_reject", null);
			if (res.moveToFirst()) {
				do {
					///returnlist.add(res.getString(res.getColumnIndex(REJECTID)));
					returnlist.add(res.getString(res.getColumnIndex(REJECTREASON)));
         /*returnlist.add(res.getString(res.getColumnIndex(RETURNREASON)));*/

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


	public ArrayList<VendorRejectModel> getVendorRejection(String invoiceno) {

		ArrayList<VendorRejectModel> salesreturnlist = new ArrayList<VendorRejectModel>();
		try {

			String[] params = new String[1];
			params[0] = invoiceno + "%";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select distinct STORE_ID,ID,REASON_FOR_REJECTION from retail_store_vend_reject where"
					+ " REASON_FOR_REJECTION like ? ", params);
			if (res.moveToFirst()) {
				do {
					VendorRejectModel vendorrejectiondetail = new VendorRejectModel();
//
					vendorrejectiondetail.setmVId(res.getString(res.getColumnIndex(REJECTID)));
					vendorrejectiondetail.setmVReason(res.getString(res.getColumnIndex(REJECTREASON)));

					salesreturnlist.add(vendorrejectiondetail);

				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return salesreturnlist;
	}


	public void updateReason(String STORE_ID, String VREASON) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("Id", getSystemCurrentTime());
		contentValues.put("Reason_for_Rejection", VREASON);
		contentValues.put("S_FLAG","0");
		db.insert("retail_store_vend_reject", null, contentValues);

		return;
	}
	/******************************************************Activity Bill Level********************************************/


	public ArrayList<String> getBillLevel() {
		ArrayList<String> billlevellist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select distinct bill_lvl_name from retail_str_bill_lvl_disc",null);
			if (res.moveToFirst()) {
				do {
					billlevellist.add(res.getString(res.getColumnIndex(BILLLEVELNAME)));
					//billlevellist.add(billlevelmodel);
				}
				while (res.moveToNext());
			}
		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return billlevellist;
	}


	public void updateBillLevel(String STORE_ID, String BILLLEVELNAME, String BILLLEVELTYPE) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		// contentValues.put("Store_Id", PersistenceManager.getStoreId(mycontext));
		contentValues.put("id", getSystemCurrentTime());
		contentValues.put("bill_lvl_name", BILLLEVELNAME);
		contentValues.put("disc_type", BILLLEVELTYPE);
		contentValues.put("M_Flag","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_bill_lvl_disc", null, contentValues);

		return;
	}




	public ArrayList<ReportDistributorModel> getDistributorReportforAllActive(String Name) {

		ArrayList<ReportDistributorModel> distributorlist = new ArrayList<ReportDistributorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select DSTR_NM, ACTIVE, POS_USER from retail_str_dstr  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportDistributorModel dm = new ReportDistributorModel();
					dm.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
					dm.setDstr_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_DSTR_NAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_ACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<ReportVendorModel> getVendorReportforAllActive(String Name) {
		ArrayList<ReportVendorModel> distributorlist = new ArrayList<ReportVendorModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select VEND_NM, ACTIVE, POS_USER from retail_store_vendor  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportVendorModel dm = new ReportVendorModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(LOCALUSERNAME)));
					dm.setVend_Nm(cursor.getString(cursor.getColumnIndex(LOCALVENDORNAME)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(LOCALVENDORACTIVE)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportProductPharmaModel> getProductPharmaReportforAllActive(String name) {
		ArrayList<ReportProductPharmaModel> distributorlist = new ArrayList<ReportProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,PROD_ID,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%'  ORDER BY PROD_NM ASC "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductPharmaModel pm = new ReportProductPharmaModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(USERNAME)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					//pm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProd_Id(cursor.getString(cursor.getColumnIndex(PRODUCTID)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTMRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTSELLING)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTACTIVE)));
					pm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTMARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}


	public ArrayList<ReportProductCpgModel> getCpgReportforAllActive(String name) {
		ArrayList<ReportProductCpgModel> distributorlist = new ArrayList<ReportProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN from retail_store_prod_cpg  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportProductCpgModel pm = new ReportProductCpgModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_USER_NM)));
					pm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_NM)));
					pm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_CODE)));
					pm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MRP)));
					pm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_S_PRICE)));
					pm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_P_PRICE)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_ACTIVE)));
					pm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CPG_MARGIN)));
					distributorlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductCpgModel> getLocalCpgReportforAllActive(String name) {
		ArrayList<ReportLocalProductCpgModel> distributorlist = new ArrayList<ReportLocalProductCpgModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select PROD_NM, ACTIVE, POS_USER,BARCODE,MRP,S_PRICE,P_PRICE,PROFIT_MARGIN from retail_store_prod_local_cpg  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductCpgModel dm = new ReportLocalProductCpgModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_USER_NM)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_NM)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_CODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_S_PRICE)));
					dm.setP_Price(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_P_PRICE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_ACTIVE)));
					dm.setProfitMargin(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LOCAL_CPG_MARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}

	public ArrayList<ReportLocalProductPharmaModel> getLocalProductPharmaReportforAllActive(String name) {
		ArrayList<ReportLocalProductPharmaModel> distributorlist = new ArrayList<ReportLocalProductPharmaModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select PROD_NM,BARCODE,MRP,S_PRICE,P_PRICE,ACTIVE,PROFIT_MARGIN, POS_USER from retail_store_prod_local  where"
							+ " ACTIVE like 'Y%' or ACTIVE like 'N%' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ReportLocalProductPharmaModel dm = new ReportLocalProductPharmaModel();
					dm.setUserNm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALUSERNAME)));
					dm.setProd_Nm(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPRODUCTNAME)));
					dm.setBarCode(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALBARCODE)));
					dm.setMRP(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMRP)));
					dm.setS_Price(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALSELLING)));
					dm.setPPrice(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALPURCHASE)));
					dm.setActive(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALACTIVE)));
					dm.setMargin(cursor.getString(cursor.getColumnIndex(PRODUCTLOCALMARGIN)));
					distributorlist.add(dm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return distributorlist;
	}



	public void updateholdflag(String s) {

		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("FLAG", "N");
		//values.put("M_FLAG","U");

		int sqlUpdateRetval = db.update("tmp_retail_str_sales_detail ", values, "TRI_ID " + " = ? ",
				new String[]{s.toString()});

		// Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}

	}

	public void updatecreditflags(String credit) {

		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval = true;
		ContentValues values = new ContentValues();
		values.put("FLAG", "N");
		values.put("M_FLAG","U");


		int sqlUpdateRetval = db.update("retail_str_sales_master_return ", values, "TRI_ID " + " = ? ",
				new String[]{credit.toString()});

		// Log.d("Sudhee", "Update done for batch:" + salesreturndetail.getSalebatchno() + ", prodid:" + salesreturndetail.getSaleProdid());

		if (sqlUpdateRetval < 1) {
			Log.e("Update fail", "returned: " + sqlUpdateRetval);
			returnval = false;
		}
	}

	public ArrayList<Sales> getProductdataforsearch(String idorName) {
		ArrayList<Sales> productlist = new ArrayList<Sales>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";

			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,S_PRICE,SELLING_ORDER_UNIT, MRP ,S_PRICE,CONV_FACT from retail_store_prod_com where "
							+ " PROD_ID  like ? or  PROD_NM  like ?  or BARCODE like  ? AND ACTIVE = 'Y%'  "
					, params);


			if (productcursor.moveToFirst()) {
				do {
					Sales pm = new Sales();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSearchsprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setSearchmrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setConversionfacter(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	// ****************** 6 July 2016 Bill_Level_ update ***************************************************



	public ArrayList<BillLevelModel> getBillLevelItem() {

		ArrayList<BillLevelModel> reasonlist = new ArrayList<BillLevelModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			//String []params=new String[0];
			//params[0]=name+"%";
			Cursor cursor = db.rawQuery("select distinct BILL_LVL_NAME,DISC_TYPE,ACTIVE from retail_str_bill_lvl_disc "
					, null);
			if (cursor.moveToFirst()) {
				do {
					BillLevelModel pm = new BillLevelModel();
					pm.setmBillName(cursor.getString(cursor.getColumnIndex(BILLLEVELNAME)));
					pm.setmBillType(cursor.getString(cursor.getColumnIndex(DISCTYPEE)));
					pm.setBActive(cursor.getString(cursor.getColumnIndex(DISACTIVE)));
					reasonlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return reasonlist;
	}





	public boolean updateBillLevelItem(String BILLLEVELNAMEE, String DISCTYPE, String ACTIVE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		//contentValues.put("ID", mvid);
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("BILL_LVL_NAME", BILLLEVELNAMEE);
		contentValues.put("DISC_TYPE", DISCTYPE + "%");
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("POS_USER",username);
		contentValues.put("M_FLAG","U");
		db.update("retail_str_bill_lvl_disc", contentValues, "BILL_LVL_NAME = ? ", new String[]{String.valueOf(BILLLEVELNAMEE)});

		return true;
	}



	//****************************** 6 july 2016 Customer Rejection***************************************

	public ArrayList<CustomerRejectModel> getCRejectReason() {

		ArrayList<CustomerRejectModel> reasonlist = new ArrayList<CustomerRejectModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
           /* String []params=new String[1];
            params[0]=name+"%";
          */
			Cursor cursor = db.rawQuery("select distinct ID, REASON_FOR_REJECTION, ACTIVE  from retail_store_cust_reject "
					, null);
			if (cursor.moveToFirst()) {
				do {
					CustomerRejectModel pm = new CustomerRejectModel();
					pm.setmVReason(cursor.getString(cursor.getColumnIndex(CUSTREJECTREASON)));
					pm.setmVId(cursor.getString(cursor.getColumnIndex(CUSTREJECTID)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(CUSTREJECTACTIVE)));
					reasonlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return reasonlist;
	}




	public boolean updatemCustomerRejectReason( String mvid ,String VREJECTREASON, String ACTIVE,String  username) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		contentValues.put("ID", mvid);
		contentValues.put("POS_USER",username);
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("REASON_FOR_REJECTION", VREJECTREASON);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("retail_store_cust_reject", contentValues, "ID = ? ", new String[]{String.valueOf(mvid)});


		String update_Customerrejection = "UPDATE retail_store_cust_reject SET ID = "+ "'" + mvid + "'" + " ,POS_USER = " + "'" + username + "'" + ", STORE_ID = " + "'" + PersistenceManager.getStoreId(mycontext) + "'" + ",REASON_FOR_REJECTION = " + "'" + VREJECTREASON + "'" + ",ACTIVE = " + "'" + ACTIVE + "'" + ",M_FLAG = 'U' " +  " WHERE ID= " + "'" + String.valueOf(mvid) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_Customerrejection);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		return true;

	}

	// ****************** 6 July 2016 Vendor rejection update ***************************************************

	public boolean updatemVendorRejectReason(String mvid,String VREJECTREASON, String ACTIVE,String username) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		PersistenceManager.getStoreId(mycontext);

		contentValues.put("ID", mvid);
		contentValues.put("POS_USER",username);
		contentValues.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
		contentValues.put("REASON_FOR_REJECTION", VREJECTREASON);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("M_FLAG","U");
		contentValues.put("S_FLAG","0");
		db.update("retail_store_vend_reject", contentValues, "ID = ? ", new String[]{String.valueOf(mvid)});
		String update_Vendorrejection = "UPDATE retail_store_vend_reject SET POS_USER = " + "'" + username + "'" + ", STORE_ID = " + "'" + PersistenceManager.getStoreId(mycontext) + "'" + ",REASON_FOR_REJECTION = " + "'" + VREJECTREASON + "'" + ",ACTIVE = " + "'" + ACTIVE + "'" + ",M_FLAG = 'U' " +  " WHERE ID= " + "'" + String.valueOf(mvid) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_Vendorrejection);
			login logi = new login();
			login.sendMessage(update_Vendorrejection);

		}catch (Exception e){}

		return true;
	}


	public ArrayList<VendorRejectModel> getVRejectReason() {

		ArrayList<VendorRejectModel> reasonlist = new ArrayList<VendorRejectModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
           /* String []params=new String[1];
            params[0]=name+"%";
          */
			Cursor cursor = db.rawQuery("select distinct ID, REASON_FOR_REJECTION, ACTIVE  from retail_store_vend_reject "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorRejectModel pm = new VendorRejectModel();
					pm.setmVReason(cursor.getString(cursor.getColumnIndex(REJECTREASON)));
					pm.setmVId(cursor.getString(cursor.getColumnIndex(REJECTID)));
					pm.setActive(cursor.getString(cursor.getColumnIndex(REJECTACTIVE)));
					reasonlist.add(pm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return reasonlist;
	}


	////////////////////////////////////////////////////////get all data from retail str sales master//////////////




	public ArrayList<String> getInvoiceNumberforceditcustomer(String creditcustname) {
		ArrayList<String> LastInvoicelist = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			Cursor cursor = db.rawQuery("select distinct INVOICE_NO,NAME,GRAND_TOTAL,FLAG from retail_credit_cust where "
							+"   NAME = '"+creditcustname+"' AND FLAG like '1%'  ORDER BY INVOICE_NO DESC limit 3"
					, null);
/*
         Cursor cursor=db.rawQuery("select Grand_Total,(Select  sum(Grand_Total)from retail_credit_cust where Mobile_No='7085246326')Total from retail_credit_cust",null);
*/
			if (null != cursor && cursor.moveToFirst() && cursor.getCount() > 0) {
				do {
					LastInvoicelist.add(cursor.getString(cursor.getColumnIndex(CREDITCUSTINVOICE)));
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}







	public ArrayList<String> getGrandTotalforCreditCustomer(String iNvoiceselected) {

		ArrayList<String> GetGrandTotal = new ArrayList<String>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
            /*String[] params = new String[1];
            params[0] = iNvoiceselected + "%";*/
			Cursor NumberCursor = db.rawQuery(" select GRAND_TOTAL from retail_credit_cust where "
							+ " INVOICE_NO='"+iNvoiceselected+"'  "
					, null);

			if (NumberCursor.moveToFirst()) {
				do {
					GetGrandTotal.add(NumberCursor.getString(NumberCursor.getColumnIndex(CREDITCUSTGRANDTOTAL)));
				} while (NumberCursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return GetGrandTotal;

	}

	////************************Getallcreditcustomer********************************///
	public ArrayList<CreditCustomer> getAllCreditCustomer(String name) {
		ArrayList<CreditCustomer> creditcustomerlist = new ArrayList<CreditCustomer>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[1];
			params[0] = name + "%";
			// params[1] = name + "%";
			Cursor res = db.rawQuery("select distinct INVOICE_NO,NAME,GRAND_TOTAL,DUE_AMOUNT,FLAG,TOTAL,MOBILE_NO from retail_credit_cust where"
							+ "   NAME like ? AND FLAG like '1%'  "
					, params);
			if (null != res && res.moveToFirst() && res.getCount() > 0) {
				do {
					CreditCustomer creditcustomer = new CreditCustomer();
					creditcustomer.setCreditcustinvoiceno(res.getString(res.getColumnIndex(CREDITCUSTINVOICE)));
					creditcustomer.setCreditcustname(res.getString(res.getColumnIndex(CREDITCUSTNAME)));
					creditcustomer.setCreditcustgrandtotal(res.getString(res.getColumnIndex(CREDITCUSTGRANDTOTAL)));
					creditcustomer.setCreditdueamount(res.getString(res.getColumnIndex(CREDITDUEAMOUNT)));
					creditcustomerlist.add(creditcustomer);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return creditcustomerlist;
	}





	public boolean updatedetailsofcreditcustcash(String TRANS_ID, String GrandTotal, String Recievd_Amount,String DueAmount,String username,String PhneNo) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("INVOICE_NO", TRANS_ID);
		contentValues.put("POS_USER",username);
		contentValues.put("GRAND_TOTAL", "0.00");
		contentValues.put("RECEIVE_AMOUNT",Recievd_Amount);
		contentValues.put("DUE_AMOUNT",DueAmount);
		contentValues.put("FLAG", "0");
		contentValues.put("M_FLAG","0");

		int sqlUpdateRetval = db.update("retail_credit_cust", contentValues, "INVOICE_NO = ?  and MOBILE_NO =?", new String[]{TRANS_ID.toString(),PhneNo.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentValues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}



	public boolean updatedetailsofcreditcustcashForPartial(String TRANS_ID, String GrandTotal, String Recievd_Amount,String DueAmount,String username) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("INVOICE_NO", TRANS_ID);
		contentValues.put("POS_USER",username);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("RECEIVE_AMOUNT",Recievd_Amount);
		contentValues.put("DUE_AMOUNT",DueAmount);
		contentValues.put("FLAG", "1");
		contentValues.put("M_FLAG","U");
		int sqlUpdateRetval = db.update("retail_credit_cust", contentValues, "INVOICE_NO = ? "
				, new String[]{TRANS_ID.toString()});

		// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentValues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	////////////////////////////////////////////get grnId from tmp_vend_dstr_payment//////////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> getGrnPayByCashId(String id) {
		ArrayList<PayByCashVendorPaymentModel> grnIdlist = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct PAY_ID,VEND_DSTR_NM from tmp_vend_dstr_payment  where"
							+ " PAY_ID like ? or VEND_DSTR_NM like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel im = new PayByCashVendorPaymentModel();
					im.setGrnId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAY_ID)));
					im.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					grnIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return grnIdlist;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> getPayByCashDataForReport(String PayId) {
		ArrayList<PayByCashVendorPaymentModel> paybyCashlist = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = PayId + "%";
			params[1] = PayId + "%";
			Cursor cursor = db.rawQuery("select distinct VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where"
							+ " PAY_ID like ? or VEND_DSTR_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel pm = new PayByCashVendorPaymentModel();
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));
					paybyCashlist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyCashlist;
	}



	/////////////////////////////////////get DATA From year and Month For InvePayByCash REPORT///////////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> PayByCashDataForMonth(String Value1, String Value2) {
		ArrayList<PayByCashVendorPaymentModel> purchaseData = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where "
					+ " PAYMENT_DATE between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel pm = new PayByCashVendorPaymentModel();
					pm.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> getPayByCash1MonthDataForReport() {
		ArrayList<PayByCashVendorPaymentModel> paybyCashlist = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel pm = new PayByCashVendorPaymentModel();
					pm.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> getPayByCash3MonthDataForReport() {
		ArrayList<PayByCashVendorPaymentModel> paybyCashlist = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-3);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel pm = new PayByCashVendorPaymentModel();
					pm.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByCashVendorPaymentModel> getPayByCash6MonthDataForReport() {
		ArrayList<PayByCashVendorPaymentModel> paybyCashlist = new ArrayList<PayByCashVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-6);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByCashVendorPaymentModel pm = new PayByCashVendorPaymentModel();
					pm.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}




	public ArrayList<PayByChequeVendorPaymentModel> getGrnPayByChequeId(String id) {
		ArrayList<PayByChequeVendorPaymentModel> grnIdlist = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct PAY_ID,VEND_DSTR_NM from tmp_vend_dstr_payment  where"
							+ " PAY_ID like ? or VEND_DSTR_NM like ? and BANK_NAME !='null' or CHEQUE_NO !='null'   "
					, params);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel im = new PayByChequeVendorPaymentModel();
					im.setGrnId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAY_ID)));
					im.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					grnIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return grnIdlist;
	}



	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByChequeVendorPaymentModel> getPayByChequeDataForReport(String PayId) {
		ArrayList<PayByChequeVendorPaymentModel> paybyChequelist = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,BANK_NAME,CHEQUE_NO,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where"
							+ " PAY_ID  ='"+PayId+"'  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel pm = new PayByChequeVendorPaymentModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));
					paybyChequelist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return paybyChequelist;
	}

	/////////////////////////////////////get DATA From year and Month For PayByCheque REPORT///////////////////////////////

	public ArrayList<PayByChequeVendorPaymentModel> PayByChequeDataForMonth(String Value1, String Value2) {
		ArrayList<PayByChequeVendorPaymentModel> purchaseData = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,BANK_NAME,CHEQUE_NO,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where "
					+ " PAYMENT_DATE between '" + Value1 + "' AND '" + Value2 + "' and BANK_NAME !='null' or CHEQUE_NO !='null'", null);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel pm = new PayByChequeVendorPaymentModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));
					purchaseData.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return purchaseData;
	}


	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByChequeVendorPaymentModel> getPayByCheque1MonthDataForReport() {
		ArrayList<PayByChequeVendorPaymentModel> paybyCashlist = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,BANK_NAME,CHEQUE_NO,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where BANK_NAME !='null' or CHEQUE_NO !='null'"
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel pm = new PayByChequeVendorPaymentModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByChequeVendorPaymentModel> getPayByCheque3MonthDataForReport() {
		ArrayList<PayByChequeVendorPaymentModel> paybyCashlist = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-3);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,BANK_NAME,CHEQUE_NO,DUE_AMOUNT,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where BANK_NAME !='null' or CHEQUE_NO !='null'"
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel pm = new PayByChequeVendorPaymentModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}

	/////////////////////////////////////////////get indirectpayByCash data from tmp_vend_dstr_payment///////////////////////////

	public ArrayList<PayByChequeVendorPaymentModel> getPayByCheque6MonthDataForReport() {
		ArrayList<PayByChequeVendorPaymentModel> paybyCashlist = new ArrayList<PayByChequeVendorPaymentModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,-6);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,VEND_DSTR_NM,AMOUNT,RECEIVED_AMOUNT,DUE_AMOUNT,BANK_NAME,CHEQUE_NO,REASON_OF_PAY,PAYMENT_DATE from tmp_vend_dstr_payment where BANK_NAME !='null' or CHEQUE_NO !='null'"
					, null);
			if (cursor.moveToFirst()) {
				do {
					PayByChequeVendorPaymentModel pm = new PayByChequeVendorPaymentModel();
					pm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_USER_NM)));
					pm.setVendorNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_VENDDSTR_NM)));
					pm.setAmountPaid(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_PAID_AMNT)));
					pm.setAmountRcvd(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_RCVD_AMNT)));
					pm.setAmountDue(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_DUE_AMNT)));
					pm.setBankName(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_BANK_NM)));
					pm.setChequeNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_CHEQUE_NO)));
					pm.setReasonOfPay(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_REASN_OF_PAY)));
					pm.setPayDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_LAST_MODIFIED)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date datecurrent = dateFormat1.parse(end);
					Date lastdate= dateFormat1.parse(start);
					String mydate = pm.getPayDate();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(lastdate) || urdate.equals(datecurrent) ) {
						paybyCashlist.add(pm);
					}


				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return paybyCashlist;
	}

	public ArrayList<PurchaseProductModelwithpo> getdocumentname() {

		ArrayList<PurchaseProductModelwithpo> vendorNamelist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
       /*  String []params=new String[1];
         params[0]=name+"%";
      */ Cursor cursor = db.rawQuery("select distinct VEND_DSTR_NM from retail_vend_dstr  where"
					+ "  ACTIVE like 'Y%'", null);
			if (cursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_DSTR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}



	public ArrayList<PurchaseProductModelwithpo> getdataforintegration(String PO) {
		ArrayList<PurchaseProductModelwithpo> productlist = new ArrayList<PurchaseProductModelwithpo>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PO_NO,GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,FREE_GOODS ,DISCOUNT_PERCENT from retail_str_stock_master_hold where "
							+ " GRN_ID like ? "
					, params);



			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModelwithpo pm = new PurchaseProductModelwithpo();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatch_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExp_Date(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public ArrayList<Inventorygrnmodel> getgrnNumberForintegration(String name) {
		ArrayList<Inventorygrnmodel> LastInvoicelist = new ArrayList<Inventorygrnmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct GRN_ID, FLAG,LAST_MODIFIED from retail_str_stock_master_hold where "
					+"VEND_NM like ? AND FLAG like 'X%'", params);

			if (cursor.moveToFirst()) {
				do {
					Inventorygrnmodel purchaseInvoiceDropDownModel = new Inventorygrnmodel();
					purchaseInvoiceDropDownModel.setInventoryOrderNo(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRN_NO)));
					purchaseInvoiceDropDownModel.setFlag(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_FLAG)));
					LastInvoicelist.add(purchaseInvoiceDropDownModel);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}

	public void DirectlyAddProductInSales(ArrayList<Sales> list, String grnId) {
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (Sales prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));

				fortrue = ForDirecltyAddProductCheckbatchnoAlreadyInDBorNot(prod.getBatchNo());
				if (fortrue == false) {
					float totalinventoryqty = prod.getQuantity();
					float mrp = prod.getMrp();
					float sprice = prod.getSearchsprice();
					float conversin = prod.getConversionfacter();
					float newmrp = mrp / conversin;
					if (newmrp < 0) {
						newmrp = 0;
					}
					float newsprice = sprice / conversin;
					if (newsprice < 0) {
						newsprice = 0;
					}
					if (fortrue == false) {
						values.put("GRN_ID", grnId);
      /* values.put("POS_USER",username);
            values.put("VEND_NM", VendorName);
            values.put("PO_NO", Ponumbers);*/
						values.put("BATCH_NO", prod.getBatchNo());
						values.put("EXP_DATE", prod.getExpDate());
						values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
						values.put("PROD_ID", prod.getProductId());
						values.put("PROD_NM", prod.getProductName());
						values.put("P_PRICE", prod.getPPrice());
						values.put("PROFIT_MARGIN", prod.getProductmargin());
						values.put("QTY", prod.getQuantity());
						values.put("MRP", prod.getMrp());
						values.put("S_PRICE", prod.getSearchsprice());
						values.put("AMOUNT", prod.getTotal());
						values.put("UOM", prod.getUom());
						values.put("BARCODE", "NA");
						values.put("CONV_FACT", prod.getConversionfacter());
						//values.put("FREE_GOODS", prod.getDiscountitems());
						values.put("CON_MUL_QTY", prod.getConversionfacter() * prod.getQuantity());
						values.put("CONV_MRP", newmrp);
						values.put("CONV_SPRICE", newsprice);
						values.put("PREV_QTY", totalinventoryqty);
						values.put("M_FLAG","I");
						values.put("S_FLAG","0");

						// Inserting Row
						db.insert("retail_str_stock_master", null, values);
					}
				}else {
					String batchqty = ForDirecltyAddProductgetparticularbatchqty(prod.getBatchNo(), prod.getProductId());
					values.put("GRN_ID", grnId);
					float prodQuantity = prod.getConversionfacter() * prod.getQuantity();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
             /* values.put("MRP", prod.getMrp());
               values.put("S_PRICE", prod.getSearchsprice());*/
					values.put("CON_MUL_QTY", Double.toString(newStockQuantity));
					values.put("M_FLAG","U");
					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "BATCH_NO = ?  and " +
							"PROD_ID " +
							" = ? ", new String[]{prod.getBatchNo(), prod.getProductId()});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchNo() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean ForDirecltyAddProductCheckbatchnoAlreadyInDBorNot(String batchno) {
		SQLiteDatabase db = this.getReadableDatabase();
   /*String[] params = new String[1];
   params[0] = batchno + "%";*/
		//  params[1] =productid + "%";

		String Query = ("select BATCH_NO,PROD_ID,CON_MUL_QTY from retail_str_stock_master where "
				+ " BATCH_NO ='"+batchno+"'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			Log.e("DDDDDDDDDDDDDDDDDDDD","Rhu "+cursor.getCount());
			cursor.close();
			return false;
		}else {
			return true;
		}
	}
	public String ForDirecltyAddProductgetparticularbatchqty(String batchno, String Prod_Id) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " BATCH_NO = '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
		Log.e("Query::", "select Con_Mul_Qty from retail_str_stock_master where " + " Batch_No =  '" + batchno + "' and Prod_Id = '" + Prod_Id + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;
	}


	//=====================MasterDataReport_Email=============================//

   /*public boolean insertEmaildatadistributor(String dstrbtr,String actve) {

      SQLiteDatabase db = this.getWritableDatabase();
//    for (ReportDistributorModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER",user);
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("DSTR_NM", dstrbtr);
      contentValues.put("ACTIVE", actve);
      contentValues.put("M_FLAG","I");

      db.insert("retail_str_dstr_mail", null, contentValues);
      //}
      return true;
   }


   public boolean insertEmaildataVendor(String vendr,String actve) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (ReportVendorModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("DSTR_NM", vendr);
      contentValues.put("ACTIVE", actve);
      contentValues.put("M_FLAG","I");

      db.insert("retail_store_vendor_mail", null, contentValues);
      //}
      return true;
   }


   public boolean insertEmaildataProductPharma(String prodnm,String actve) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (ReportProductPharmaModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("PROD_NM", prodnm);
      contentValues.put("ACTIVE", actve);
      contentValues.put("M_FLAG","I");

      db.insert("retail_store_prod_mail", null, contentValues);
      //}
      return true;
   }

   public boolean insertEmaildataLocalProductPharma(String prodnm,String actve) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (ReportLocalProductPharmaModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("PROD_NM", prodnm);
      contentValues.put("ACTIVE", actve);
      contentValues.put("M_FLAG","I");
      db.insert("retail_store_prod_local_mail", null, contentValues);
      //}
      return true;
   }
   public boolean insertEmaildataLocalProductCPG(ArrayList<ReportLocalProductCpgModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (ReportLocalProductCpgModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PROD_NM", report.getProd_Nm());
         contentValues.put("BARCODE", report.getBarCode());
         contentValues.put("MRP", report.getMRP());
         contentValues.put("S_PRICE", report.getS_Price());
         contentValues.put("P_PRICE", report.getP_Price());
         contentValues.put("ACTIVE", report.getActive());
         contentValues.put("PROFIT_MARGIN", report.getProfitMargin());
         contentValues.put("M_FLAG","I");
         db.insert("retail_store_prod_local_cpg_mail", null, contentValues);
      }
      return true;
   }


   public boolean insertEmaildataProductCPG(ArrayList<ReportProductCpgModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (ReportProductCpgModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PROD_NM", report.getProd_Nm());
         contentValues.put("BARCODE", report.getBarCode());
         contentValues.put("MRP", report.getMRP());
         contentValues.put("S_PRICE", report.getS_Price());
         contentValues.put("P_PRICE", report.getP_Price());
         contentValues.put("ACTIVE", report.getActive());
         contentValues.put("PROFIT_MARGIN", report.getProfitMargin());
         contentValues.put("M_FLAG","I");
         db.insert("retail_store_prod_cpg_mail", null, contentValues);
      }
      return true;
   }

   //==========PurchasingReport_Email===========================//

   public boolean insertEmail_1monthpurchase(ArrayList<VendorReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (VendorReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PO_NO", report.getPo_No());
         contentValues.put("VENDOR_NM", report.getVendor_Nm());
         contentValues.put("TOTAL", report.getTotal());
         contentValues.put("M_FLAG","I");
         db.insert(" retail_str_po_detail_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertEmail_3monthpurchase(ArrayList<VendorReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (VendorReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PO_NO", report.getPo_No());
         contentValues.put("VENDOR_NM", report.getVendor_Nm());
         contentValues.put("TOTAL", report.getTotal());
         contentValues.put("M_FLAG","I");
         db.insert(" retail_str_po_detail_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertEmailpurchase(String vendrnm) {

      SQLiteDatabase db = this.getWritableDatabase();
   // for (VendorReportModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("PO_NO", "445");
      contentValues.put("VENDOR_NM", vendrnm);




      //contentValues.put("TOTAL", report.getTotal());
      contentValues.put("M_FLAG","I");
      db.insert(" retail_str_po_detail_mail ", null, contentValues);
      //}
      return true;
   }

   //=============InventoryReport_Email========================================//

   public boolean insertEmail_1monthinventory(ArrayList<InventoryReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (InventoryReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUser_Nm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PROD_NM", report.getProd_Nm());
         contentValues.put("BATCH_NO", report.getBatch());
         contentValues.put("EXP_DATE", report.getExpiry());
         contentValues.put("QTY", report.getQuantity());
         contentValues.put("M_FLAG","I");
         db.insert(" retail_str_stock_master_mail ", null, contentValues);
      }
      return true;
   }


   public boolean insertEmail_3monthinventory(ArrayList<InventoryReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (InventoryReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUser_Nm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PROD_NM", report.getProd_Nm());
         contentValues.put("BATCH_NO", report.getBatch());
         contentValues.put("EXP_DATE", report.getExpiry());
         contentValues.put("QTY", report.getQuantity());
         contentValues.put("M_FLAG","I");
         db.insert(" retail_str_stock_master_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertEmail_6monthinventory(ArrayList<InventoryReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (InventoryReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUser_Nm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("PROD_NM", report.getProd_Nm());
         contentValues.put("BATCH_NO", report.getBatch());
         contentValues.put("EXP_DATE", report.getExpiry());
         contentValues.put("QTY", report.getQuantity());
         contentValues.put("M_FLAG","I");
         db.insert(" retail_str_stock_master_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertEmailmonthinventory(ArrayList<InventoryReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (InventoryReportModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUser_Nm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("PROD_NM", report.getProd_Nm());
      contentValues.put("PROD_ID", report.getProd_Id());
      //contentValues.put("BATCH_NO", report.getBatch());
      //contentValues.put("EXP_DATE", report.getExpiry());
      //contentValues.put("QTY", report.getQuantity());
      contentValues.put("M_FLAG","I");
      db.insert(" retail_str_stock_master_mail ", null, contentValues);
      }
      return true;
   }


   //====================VendorPaybyCashReport_Email=========================//

   public boolean insertemail_1monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByCashVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserName());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertemail_3monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByCashVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserName());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertemail_6monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByCashVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserName());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertemailmonthpaybycash(String payid,String vendnm) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (PayByCashVendorPaymentModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserName());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("VEND_DSTR_NM", vendnm);
      //contentValues.put("AMOUNT", report.getAmountPaid());
      //contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
      //contentValues.put("DUE_AMOUNT", report.getAmountDue());
      //contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
      //contentValues.put("LAST_MODIFIED", report.getLastUpdate());
      contentValues.put("PAY_ID", payid);
      contentValues.put("M_FLAG","I");
      db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      //}
      return true;
   }

   //===========VendorPaybycheque_Email================================//

   public boolean insertemail_1monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByChequeVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("BANK_NAME", report.getBankName());
         contentValues.put("CHEQUE_NO", report.getChequeNo());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertemail_3monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByChequeVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("BANK_NAME", report.getBankName());
         contentValues.put("CHEQUE_NO", report.getChequeNo());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }

   public boolean insertemail_6monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (PayByChequeVendorPaymentModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("VEND_DSTR_NM", report.getVendorNm());
         contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("BANK_NAME", report.getBankName());
         contentValues.put("CHEQUE_NO", report.getChequeNo());
         contentValues.put("M_FLAG","I");
         db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      }
      return true;
   }


   public boolean insertemailmonthpaybycheque(String payid,String vendnm) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (PayByChequeVendorPaymentModel report : list) {
      ContentValues contentValues = new ContentValues();

      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("VEND_DSTR_NM", vendnm);
         *//*contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("BANK_NAME", report.getBankName());
         contentValues.put("CHEQUE_NO", report.getChequeNo());*//*
      contentValues.put("PAY_ID", payid);
      contentValues.put("M_FLAG","I");
      db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
      //}
      return true;
   }

   public boolean insertemailvendorreturn(String payid,String vendnm) {

      SQLiteDatabase db = this.getWritableDatabase();
      //for (ReportVendorReturnModel report : list) {
      ContentValues contentValues = new ContentValues();
      //contentValues.put("POS_USER", report.getUserNm());
      contentValues.put("TICKET_ID", getSystemCurrentTime());
      contentValues.put("VENDOR_NM", vendnm);
      contentValues.put("VENDOR_RETURN_ID", payid);

         *//*contentValues.put("PROD_NM", report.getProdctNm());
         contentValues.put("BATCH_NO", report.getBatchNo());
         contentValues.put("EXP_DATE", report.getExpDate());
         contentValues.put("REASON_OF_RETURN", report.getReason());
         contentValues.put("QTY", report.getQty());
         contentValues.put("P_PRICE", report.getPPrice());
         contentValues.put("TOTAL", report.getTotal());
         contentValues.put("UOM", report.getUom());*//*

      contentValues.put("M_FLAG","I");
      db.insert("retail_str_vendor_detail_return_mail ", null, contentValues);
      //}
      return true;
   }

   //===========SalesReport_Email=======================//


   public boolean insertemaildailysalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }


   public boolean insertemailweeklysalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();


         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }




   public boolean insertemailmonthlysalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }

   public boolean insertemailquarterlysalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }

   public boolean insertemailyearlysalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();

         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }

   public boolean insertemailsalesreport(ArrayList<SaleReportModel>list) {

      SQLiteDatabase db = this.getWritableDatabase();
      for (SaleReportModel report : list) {
         ContentValues contentValues = new ContentValues();
         contentValues.put("POS_USER", report.getUserNm());
         contentValues.put("TICKET_ID", getSystemCurrentTime());
         contentValues.put("TRI_ID", report.getTransId());
         contentValues.put("TOTAL", report.getGrnTotl());
         contentValues.put("UOM", report.getUom());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("EXP_DATE", report.getExp());
         contentValues.put("S_PRICE", report.getPrice());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());
         contentValues.put("M_FLAG","I");
         db.insert("retail_str_sales_detail_mail", null, contentValues);
      }
      return true;
   }

   //========================SalesReturnReport_Email=======================//*/


//=================MediaReports_email=======================================//

	public boolean insertemailmainadvertisement(ArrayList<StoreMainModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (StoreMainModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("AD_MAIN_ID", report.getAD_MAIN_ID());
			contentValues.put("AD_DESC", report.getAD_DESC());
			contentValues.put("AD_CST_SLB1", report.getAD_CST_SLB1());
			contentValues.put("AD_CST_SLB2", report.getAD_CST_SLB2());
			contentValues.put("AD_CST_SLB3", report.getAD_CST_SLB3());
			//contentValues.put("STATUS", report.get);
			//contentValues.put("ACTIVE", report.get);
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_ad_store_main_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailBlinkinglogo(ArrayList<BlinkingLogoReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (BlinkingLogoReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("AD_MAIN_ID", report.getAD_MAIN_ID());
			contentValues.put("AD_DESC", report.getAD_DESC());
			contentValues.put("AD_CST_SLB1", report.getAD_CST_SLB1());
			contentValues.put("AD_CST_SLB2", report.getAD_CST_SLB2());
			contentValues.put("AD_CST_SLB3", report.getAD_CST_SLB3());
			contentValues.put("Ad_strt_Dt", report.getAD_START_DATE());
			contentValues.put("AD_END_DT", report.getAD_END_DATE());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_ad_blinkinglogo_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailAdticker(ArrayList<AdTickerReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (AdTickerReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("AD_MAIN_ID", report.getAD_MAIN_ID());
			contentValues.put("AD_TEXT", report.getAD_TEXT());
			contentValues.put("AD_CST_SLB1", report.getAD_CST_SLB1());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_ad_ticker_mail", null, contentValues);
		}
		return true;
	}

	public ArrayList<CreditCustomer> getInvoiceNumberforceditcustomerforfullsettlement(String name) {
		ArrayList<CreditCustomer> LastInvoicelist = new ArrayList<CreditCustomer>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();

			String []params=new String[1];
			params[0]=name+"%";

      /*Cursor cursor = db.rawQuery("select Invoice_No,Mobile_No,Name,Flag,(select sum(Grand_Total) from retail_credit_cust)Total from retail_credit_cust where "
            +" Mobile_No Like ? "
            , params);*/

			Cursor cursor = db.rawQuery("select INVOICE_NO,MOBILE_NO,NAME,FLAG,sum(GRAND_TOTAL)from retail_credit_cust where "
							+" MOBILE_NO Like ? AND FLAG Like '1%'"
					, params);
			if ( null != cursor && cursor.moveToFirst() && cursor.getCount() > 0 ) {
				do {
					CreditCustomer cc= new CreditCustomer();
					cc.setCreditcustinvoiceno(cursor.getString(cursor.getColumnIndex(CREDITCUSTINVOICE)));
					cc.setMobileNo(cursor.getString(cursor.getColumnIndex(CREDITMOBILENO)));
					cc.setCreditcustgrandtotal(cursor.getString(cursor.getColumnIndex(CREDITCUSTGRANDSUMTOTAL)));
					cc.setCreditcustname(cursor.getString(cursor.getColumnIndex(CREDITCUSTNAME)));
					LastInvoicelist.add(cc);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return LastInvoicelist;
	}

	////********************* fullcreditpaymentupdate**********************************************///

	public boolean updatefulldetailsofcreditcustcash(String MobileNo, String GrandTotal,String username) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("POS_USER",username);
		contentValues.put("MOBILE_NO", MobileNo);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("FLAG", "0");
		contentValues.put("M_FLAG","U");


		int sqlUpdateRetval = db.update("retail_credit_cust", contentValues, "MOBILE_NO = ? "
				, new String[]{MobileNo.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public Cursor getDayCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor DayCash = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_DAY_CASH + ") AS DayCashTotal FROM retail_str_day_open_close where START_DATE ='" + getDate() + "' ", null);
		return DayCash;
	}

	public Cursor getDailyTotalSalesReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_SALE_TOTAL + ") AS SalesTotal FROM retail_str_sales_detail where SALE_DATE ='" + getDate() + "' ", null);
		return GrandTotal;
	}


	public Cursor getDailySalesReturnTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_SALE_TOTAL + ") AS SalesReturnTotal FROM retail_str_sales_details_return where SALE_DATE ='"+getDate()+"'", null);
		return GrandTotal;
	}


	public Cursor getDailyVendorpaymentTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal = db.rawQuery("SELECT Sum("+COLUMN_REPRT_RCVD_AMNT+") AS PaymentTotal FROM tmp_vend_dstr_payment where PAYMENT_DATE ='"+getDate()+"'", null);
		return GrandTotal;
	}


	public Cursor getDailyPurchaseTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_SALE_TOTAL + ") AS PurchasingTotal FROM retail_str_po_detail where PURCHASEDATE ='"+getDate()+"'", null);
		return GrandTotal;
	}



	public Cursor getDailyCreditTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_SALE_RETURN_GRANDTOTAL + ") AS CreditTotal FROM retail_credit_cust where CREDIT_DATE ='"+getDate()+"' AND FLAG like '1%'", null);
		return GrandTotal;
	}


	public Cursor getDailyTotalVendorReturnReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor GrandTotal3 = db.rawQuery("SELECT Sum(" + COLUMN_REPRT_TOTAL + ") AS VendorReturnTotal FROM retail_str_vendor_detail_return where RETURN_DATE ='"+getDate()+"' ", null);
		return GrandTotal3;
	}




	public boolean updateinventoryflagstockmaster(String x1) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("GRN_ID", x1);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_stock_master", contentValues, "GRN_ID= ? "
				, new String[]{x1.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;


	}

	public boolean updateflagsaveGranddataintoGrnMaster(String x1) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("GRN_ID", x1);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_grn_master", contentValues, "GRN_ID= ? "
				, new String[]{x1.toString()});


// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updateflagSavePdfDetailForInventorywithpo(String x1) {




		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", x1);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{x1.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatepurchaseflagdetail(String invoiceNo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", invoiceNo);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_po_detail", contentValues, "PO_NO= ? "
				, new String[]{invoiceNo.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;



	}

	public boolean updateflagsaveGranddataintopoMaster(String invoiceNo) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", invoiceNo);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_po_master", contentValues, "PO_NO= ? "
				, new String[]{invoiceNo.toString()});

// Log.d("Sudhee", "Update done for batch:" + prod.getSalebatchno() + ", prodid:" + prod.getSaleproductname());

		//int sql= db.update("retail_credit_cust", contentVlues, "Invoice_No= ? AND  Name ='"+name+"'", new String[]{String.valueOf(TRANS_ID)});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updateflagSavePdfDetailForpurchase(String invoiceNo) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", invoiceNo);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{invoiceNo.toString()});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}









	public boolean updateflagSavePdfDetailForvendor(String invoiceNo) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", invoiceNo);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{invoiceNo.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}


	public boolean updatevendorpaymentflagdetail(String invoiceNo) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PAYMENT_ID", invoiceNo);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("tmp_vend_dstr_payment", contentValues, "PAYMENT_ID= ? "
				, new String[]{invoiceNo.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}


	public boolean updatemobileno(String store_id) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("STORE_ID", store_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_store", contentValues, "STORE_ID= ? "
				, new String[]{store_id.toString()});


		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updateproductflag(String prod_id) {




		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PROD_ID", prod_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_store_prod", contentValues, "PROD_ID= ? "
				, new String[]{prod_id.toString()});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatedistributorflag(String dstrId) {




		SQLiteDatabase db = this.getWritableDatabase();


		ContentValues contentValues = new ContentValues();
		contentValues.put("DSTR_ID", dstrId);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_dstr", contentValues, "DSTR_ID= ? "
				, new String[]{dstrId.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatedoctorsflag(String dr_id) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("DR_ID", dr_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("dr_discription", contentValues, "DR_ID= ? "
				, new String[]{dr_id.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updateflaglocalproduct(String local_prod_id) {





		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PROD_ID", local_prod_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_store_prod_local", contentValues, "PROD_ID= ? "
				, new String[]{local_prod_id.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updateflagcustomer(String cust_mobile) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("MOBILE_NO", cust_mobile);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_cust", contentValues, "MOBILE_NO= ? "
				, new String[]{cust_mobile.toString()});


		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatesalesflagdetail(String salestransaction,String batchno,String prodId) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", salestransaction);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_sales_detail", contentValues, "TRI_ID= ? and PROD_ID =? and BATCH_NO= ?", new String[]{salestransaction.toString(),prodId.toString(),batchno.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row Updated...");
		db.close(); // Closing database connection
		return true;

	}

	public boolean updateflagsavesalesMaster(String salestransaction) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", salestransaction);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_sales_master", contentValues, "TRI_ID= ? "
				, new String[]{salestransaction.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;


	}

	public boolean updateflagSavePdfDetailForsale(String salestransaction) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", salestransaction);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{salestransaction.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;

	}

	public boolean updateflagSavePdfDetailForsalereturn(String transactionid) {


		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", transactionid);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{transactionid.toString()});
		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;

	}

	public boolean updateflagsavesalesMasterreturn(String transactionid) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", transactionid);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_sales_master_return", contentValues, "TRI_ID= ? "
				, new String[]{transactionid.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatesalesflagdetailreturn(String transactionid) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", transactionid);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_sales_details_return", contentValues, "TRI_ID= ? "
				, new String[]{transactionid.toString()});
		if (sqlUpdateRetval < 1) {
			return false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	public boolean updatevendorreturndetailreturn(String vendor_return_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("VENDOR_RETURN_ID", vendor_return_id);
		contentValues.put("S_FLAG", "1");


		int sqlUpdateRetval = db.update("retail_str_vendor_detail_return", contentValues, "VENDOR_RETURN_ID= ? "
				, new String[]{vendor_return_id.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}



	public boolean updatevendorreturnMasterreturn(String vendor_return_id) {

		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("VENDOR_RETURN_ID", vendor_return_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_vendor_Master_return", contentValues, "VENDOR_RETURN_ID= ? "
				, new String[]{vendor_return_id.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;

	}


	public boolean updateflagSavePdfDetailForvendorreturn(String vendor_return_id) {



		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("PO_NO", vendor_return_id);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_send_mail_pdf", contentValues, "PO_NO= ? "
				, new String[]{vendor_return_id.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;

	}

	public boolean updateflaglocalvendor(String localVendor_id_to_update) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("VEND_ID", localVendor_id_to_update);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_store_vendor", contentValues, "VEND_ID= ? "
				, new String[]{localVendor_id_to_update.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;

	}


	public boolean updatesalesflagdayopenclose(String daycloseposdate) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", daycloseposdate);
		contentValues.put("S_FLAG", "1");

		int sqlUpdateRetval = db.update("retail_str_day_open_close", contentValues, "TRI_ID= ? "
				, new String[]{daycloseposdate.toString()});

		if (sqlUpdateRetval < 1)
		{
			return  false;
		}
		Log.e("Database Operation", "row inserted...");
		db.close(); // Closing database connection
		return true;
	}

	/////////!!!!!!!!!!!!!!!!!!***********Update Customer in case of Y to N and Y to N!!!!!!!!!!!!!!!********/////

	public boolean updateCustomer(String Name,String EMAIL,String Mobile,String creditcust,String username,String store_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("STORE_ID",store_id);
		contentValues.put("POS_USER",username);
		contentValues.put("NAME",Name);
		contentValues.put("E_MAIL", EMAIL);
		contentValues.put("MOBILE_NO",Mobile );
		contentValues.put("CREDIT_CUST", creditcust);
		contentValues.put("M_FLAG","U");


		int SqlValue=db.update("retail_cust", contentValues, "MOBILE_NO = ? ", new String[]{String.valueOf(Mobile)});
		String update_vendor_in_purchase =  "UPDATE retail_cust SET NAME = " + "'" + Name + "'," + " POS_USER = " + "'" + username + "'" + ",M_FLAG = 'U' " + " ,CREDIT_CUST = " + "'" + creditcust + "'" +" ,E_MAIL = " + "'" + EMAIL + "' WHERE MOBILE_NO = " + "'" + String.valueOf(Mobile) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_vendor_in_purchase);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		if (SqlValue < 1) {
			Log.e("UpdateFailLocalVendor:", String.valueOf(SqlValue));
			return false;
		}

		return true;
	}

	/////////!!!!!!!!!!!!!!!!!!***********Update  Doctorin case of Y to N and Y to N!!!!!!!!!!!!!!!********/////

	public boolean updateDoctordata( String ID,String NAME,String DOCTORSPECIALITY, String ACTIVE, String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("POS_USER",username);
		contentValues.put("DR_NAME",NAME);
		contentValues.put("SPECIALITY",DOCTORSPECIALITY);
		contentValues.put("ACTIVE", ACTIVE);
		contentValues.put("M_FLAG","U");

		int SqlValue=db.update("dr_discription", contentValues, "DR_ID = ? ", new String[]{String.valueOf(ID)});

		String update_doctor = "UPDATE dr_discription SET POS_USER = "+ "'" + username + "'" + " ,DR_NAME = " + "'" + NAME + "'" + ",M_FLAG = 'U' " + " ,SPECIALITY = " + "'" + DOCTORSPECIALITY + "'"+ " ,ACTIVE = " + "'" + ACTIVE + "'" + " WHERE DR_ID= " + "'" + String.valueOf(ID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query", update_doctor);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}
		if (SqlValue < 1) {
			Log.e("UpdateFailDoctor:", String.valueOf(SqlValue));
			return false;
		}

		return true;
	}

	public ArrayList<StockInventoryReportModel> getDstrNm(String name) {
		ArrayList<StockInventoryReportModel> vendornamelist = new ArrayList<StockInventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct VEND_NM from retail_str_stock_master  where"
							+ " VEND_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					StockInventoryReportModel vm = new StockInventoryReportModel();
					vm.setDstrNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_STOCK_NM)));
					vendornamelist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}


	public ArrayList<StockInventoryReportModel> getVendorInvoiceNo(String VendorInvoiceNo) {
		ArrayList<StockInventoryReportModel> vendornamelist = new ArrayList<StockInventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = VendorInvoiceNo + "%";
			Cursor cursor = db.rawQuery("select distinct VENDOR_INVOICE_NO from retail_str_stock_master  where"
							+ " VENDOR_INVOICE_NO like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					StockInventoryReportModel vm = new StockInventoryReportModel();
					vm.setVendInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_VENDOR_INVOICE_NO)));
					vendornamelist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}
	public ArrayList<StockInventoryReportModel> getInvoiceNo(String PoNo) {

		ArrayList<StockInventoryReportModel> vendorlist = new ArrayList<StockInventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PoNo + "%";
			Cursor cursor = db.rawQuery("select distinct GRN_ID from retail_str_stock_master  where"
							+ " VEND_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					StockInventoryReportModel vm = new StockInventoryReportModel();
					vm.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_STOCK_INVOICE)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}


	public ArrayList<StockInventoryReportModel> getGrnIDforLIst(String PoNo) {

		ArrayList<StockInventoryReportModel> vendorlist = new ArrayList<StockInventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PoNo + "%";
			Cursor cursor = db.rawQuery("select distinct GRN_ID from retail_str_stock_master where"
							+ " VENDOR_INVOICE_NO like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					StockInventoryReportModel vm = new StockInventoryReportModel();
					vm.setInvoiceNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_STOCK_INVOICE)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}

	public ArrayList<InventoryReportModel> getstockdataforinventory(String TriId) {
		ArrayList<InventoryReportModel> idlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,BATCH_NO,EXP_DATE,CON_MUL_QTY from retail_str_stock_master "
							+ "  where " + " GRN_ID ='" + TriId + "'and CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(TOTALQTY)));
					idlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}





	public Decimal getStoreprice() {

		Decimal decimal = null;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_store_decimal", null);
		if (res.moveToFirst()) {
			//do {
			decimal = new Decimal();
			decimal.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
			decimal.setDecimalmrp(res.getString(res.getColumnIndex(MRP_DECIMAL)));
			decimal.setDecimalpprice(res.getString(res.getColumnIndex(PPRICE_DECIMAL)));
			decimal.setDecimalsprice(res.getString(res.getColumnIndex(SPRICE_DECIMAL)));
			decimal.setHoldpo(res.getString(res.getColumnIndex(HOLD_PO)));
			decimal.setHoldinv(res.getString(res.getColumnIndex(HOLD_INV)));
			decimal.setHoldsales(res.getString(res.getColumnIndex(HOLD_SALES)));
			decimal.setRoundofff(res.getString(res.getColumnIndex(ROUND_OFF)));
			//decimal.setPurchaseholdno(res.getString(res.getColumnIndex(PURCHASE_HOLD_NO)));
			decimal.setInventoryholdno(res.getString(res.getColumnIndex(INVENTORY_HOLD_NO)));
			decimal.setColorbackround(res.getString(res.getColumnIndex("PURCHASE_HOLD_NO")));
		}
		db.close();
		return decimal;

	}



	public Visibility getStorevisibility() {

		Visibility visi = null;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from retail_bill_visible", null);
		if (res.moveToFirst()) {
			visi = new Visibility();
			visi.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
			visi.setMrpvisibility(res.getString(res.getColumnIndex(MRP_VISIBLE)));
			visi.setTele2(res.getString(res.getColumnIndex(TELE2_VISIBLE)));
			visi.setFootervisi(res.getString(res.getColumnIndex(FOOTER_VISIBLE)));
			visi.setBillcopy(res.getString(res.getColumnIndex(BILL_COPY)));
			visi.setItemvisibilty(res.getString(res.getColumnIndex(MAIN_BODY)));//use for bill print (sales bill)
			visi.setCreditcopy(res.getString(res.getColumnIndex(CREDIT_COPY)));
			visi.setReturncopy(res.getString(res.getColumnIndex(RETURN_COPY)));
			visi.setMarginvisiblty(res.getString(res.getColumnIndex(MARGIN_VISIBILTY)));
			 visi.setFreequantity(res.getString(res.getColumnIndex(FREE_GOODSVISIBILTY)));
			visi.setInvbillprints(res.getString(res.getColumnIndex(INV_BILL_PRINTS)));
			visi.setOtp(res.getString(res.getColumnIndex(OTP)));


		}
		db.close();
		return visi;
	}
	public boolean updatedecimal(String STORE_ID, String Mrp,String pprice,String sprice,String holdpo,String holdinv,String holdsales,String roundof,String purchaseholdno,String inventoryholdno)  {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("MRP_Decimal", Mrp);
		contentValues.put("P_PRICE_DECIMAL", pprice);
		contentValues.put("S_PRICE_DECIMAL",sprice);
		contentValues.put("HOLD_PO", holdpo);
		contentValues.put("HOLD_INV", holdinv);
		contentValues.put("HOLD_SALES",holdsales);
		contentValues.put("ROUND_OFF",roundof);

		contentValues.put("PURCHASE_HOLD_NO",purchaseholdno);
		contentValues.put("INV_HOLD_NO",inventoryholdno);
		contentValues.put("S_FLAG","0");


		db.update("retail_store_decimal", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(STORE_ID)});
		String update_decimal = "UPDATE retail_store_decimal SET MRP_Decimal = " + "'" + Mrp + "'" + ",P_PRICE_DECIMAL = "
				+ "'" + pprice + "'" + ",S_PRICE_DECIMAL =  " + "'" + sprice + "'" + ",HOLD_PO = " + "'" + holdpo + "'" + " ,HOLD_INV = "
				+ "'" + holdinv + "'" + " ,HOLD_SALES = " + "'" + holdsales + "'" + " ,ROUND_OFF = " + "'" + roundof + "'" +  " ,PURCHASE_HOLD_NO = " + "'" + purchaseholdno + "'" + " ,INV_HOLD_NO = " + "'" + inventoryholdno + "'" +" WHERE STORE_ID = "
				+ "'" + String.valueOf(STORE_ID) + "'";
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",update_decimal);

			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}



		return true;
	}



	public boolean updatedisplay(String STORE_ID, String totalbill,String discount,String netbillpayable,String amountreceived,String amountpayback,String footer) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("TOTAL_BILL_VALUE", totalbill);
		contentValues.put("DISCOUNT", discount);
		contentValues.put("NET_BILL_PAYABLE",netbillpayable);
		contentValues.put("AMOUNT_RECEIVED", amountreceived);
		contentValues.put("AMOUNT_PAID_BACK", amountpayback);
		contentValues.put("FOOTER",footer);
		contentValues.put("S_FLAG","0");
		db.update("retail_bill_display", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(STORE_ID)});
		String update_display = "UPDATE retail_bill_display SET TOTAL_BILL_VALUE = " + "'" + totalbill + "'" + ",DISCOUNT = " + "'" + discount + "'" + ",NET_BILL_PAYABLE =  " + "'" + netbillpayable + "'" + ",AMOUNT_RECEIVED = " + "'" + amountreceived + "'" + " ,AMOUNT_PAID_BACK = " + "'" + amountpayback + "'" + " ,FOOTER = " + "'" + footer + "'" +  " WHERE STORE_ID= " + "'" + String.valueOf(STORE_ID) + "'";
		try{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",update_display);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){}

		//login.sendMessage(update_display);
		return true;
	}



	public boolean updatevisibility(String STORE_ID, String Mrp,String tele,String footer,String billcopy,String creditcopy,String returncopy,String mainbody,String margin,String freeqty,String invbillprint,String otp) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("MRP", Mrp);
		contentValues.put("TELE_2", tele);
		contentValues.put("FOOTER",footer);
		contentValues.put("NORMAL_SALES", billcopy);
		contentValues.put("CREDIT_CUSTOMER", creditcopy);
		contentValues.put("M_FLAG",margin);

		contentValues.put("RETURNS",returncopy);

		contentValues.put("FREE_GOODS",freeqty);
		contentValues.put("INV_PRINT",invbillprint);

		contentValues.put("MAIN_BODY",mainbody);

		contentValues.put("OTP_CHECK",otp);

		contentValues.put("S_FLAG","0");

		db.update("retail_bill_visible", contentValues, "STORE_ID = ? ", new String[]{String.valueOf(STORE_ID)});
		String update_visibility = "UPDATE retail_bill_visible SET MRP = " + "'" + Mrp + "'" + ",TELE_2 = " + "'" + tele + "'" + ",FOOTER =  " + "'" + footer + "'" + ",NORMAL_SALES = " + "'" + billcopy + "'" + " ,CREDIT_CUSTOMER = " + "'" + creditcopy + "'" + " " +
				",M_FLAG =  " + "'" + margin + "'" + ",RETURNS = " + "'" + returncopy + "'"+",FREE_GOODS="+"'"+freeqty+"'" + ",INV_PRINT = " + "'" + invbillprint + "' WHERE STORE_ID= " + "'" + String.valueOf(STORE_ID) + "'";
		try
		{
			JSONObject json = new JSONObject();
			json.put("Query",update_visibility);
			login logi = new login();
			login.sendMessage(String.valueOf(json));
		}catch (Exception e){}
		//login.sendMessage(update_visibility);


		return true;
	}



	public ArrayList<Topfullproductmodel> getAllDeleteTopProducts() {

		ArrayList<Topfullproductmodel> returnlist = new ArrayList<Topfullproductmodel>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("select distinct PROD_NM ,PROD_SHORT_NM  from retail_top_product", null);
			if (res.moveToFirst()) {
				do {
					Topfullproductmodel topfullproductmodel = new Topfullproductmodel();

					topfullproductmodel.setProductname(res.getString(res.getColumnIndex(COLUMN_TOP_PRODUCT_NAME)));
					topfullproductmodel.setShortname(res.getString(res.getColumnIndex(TOP_PRODUCT_SORT_NAME)));
					returnlist.add(topfullproductmodel);
				}
				while (res.moveToNext());
			}

		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return returnlist;
	}



//  Delete top product***********************************




	public void DeleteRecord(String remove) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME_TOP + " where " + TOP_PRODUCT_NAME + " = '" + remove+ "'");
		db.close();

	}





	public Cursor getBill() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("SELECT Count(*)as Tri_Id from retail_str_sales_master",null);
		return res;
	}



	////******************************************shilpa*****************************************************
///************************Getallsettlecreditcustomer********************************///
	public ArrayList<Settlecustmodel> getAllsettleCreditCustomerDetalis(String name) {
		ArrayList<Settlecustmodel> settlecreditcustomerlist = new ArrayList <Settlecustmodel>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[1];
			//params[0] = name + "%";
			//params[1] = name + "%";

			//String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " BATCH_NO =  '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
			Cursor res = db.rawQuery("select INVOICE_NO,NAME,GRAND_TOTAL,DUE_AMOUNT,RECEIVE_AMOUNT,FLAG,TOTAL,MOBILE_NO,CREDIT_DATE from retail_credit_cust where"
							+ " NAME ='" + name + "'"
					,null);

			Log.e("Query::", "select INVOICE_NO,NAME,GRAND_TOTAL,DUE_AMOUNT,RECEIVE_AMOUNT,FLAG,TOTAL,MOBILE_NO,CREDIT_DATE from retail_credit_cust where"
					+ "  " + " NAME ='" + name + "'");
			if ( res.moveToFirst()) {
				do {
					Settlecustmodel settlecustmodel= new Settlecustmodel();
					settlecustmodel.setName(res.getString(res.getColumnIndex(CREDITCUSTNAME)));
					settlecustmodel.setPhoneno(res.getString(res.getColumnIndex(CREDITMOBILENO)));
					settlecustmodel.setOutstanding(res.getString(res.getColumnIndex(CREDITCUSTGRANDTOTAL)));
					settlecustmodel.setInvoiceno(res.getString(res.getColumnIndex(CREDITCUSTINVOICE)));
					settlecustmodel.setCreditdate(res.getString(res.getColumnIndex(CREDITDATE)));
					settlecustmodel.setPaid(res.getString(res.getColumnIndex(CREDITRECIEVEDAMOUNT)));
					settlecreditcustomerlist.add(settlecustmodel);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return settlecreditcustomerlist;
	}

	////************************Getallsettlecreditcustomer********************************///
	public ArrayList<Sales> getAllsettleCreditCustomerbillitmDetalis(String mobileno) {
		ArrayList<Sales> settlecreditcustomerlistitem = new ArrayList <Sales>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[1];
			//params[0] = name + "%";
			//params[1] = name + "%";

			//String Query = ("select CON_MUL_QTY from retail_str_stock_master where " + " BATCH_NO =  '" + batchno + "' and PROD_ID = '" + Prod_Id + "'");
			Cursor res = db.rawQuery("select BILL_NO,BILL_DATE,MOBILE_NO,PAYMENT_ID,ITEM,MRP,S_PRICE,QTY,AMOUNT,TOTAL,FLAG from retail_credit_bill_details where"
							+ " MOBILE_NO ='" + mobileno + "'"
					,null);

			Log.e("Query::", "select BILL_NO,BILL_DATE,MOBILE_NO,PAYMENT_ID,ITEM,MRP,S_PRICE,QTY,AMOUNT,TOTAL,FLAG from retail_credit_bill_details where"
					+ "  " + " MOBILE_NO ='" + mobileno + "'");
			if ( res.moveToFirst()) {
				do {
					Sales sales= new Sales();
					sales.setBill_No(res.getString(res.getColumnIndex(CREDITCUSTBILLNO)));
					sales.setDate(res.getString(res.getColumnIndex(CREDITCUSTBILLDATE)));
					sales.setPayment_Id(res.getString(res.getColumnIndex(CREDITCUSTPAYMENT_ID)));
					sales.setProductName(res.getString(res.getColumnIndex(CREDITCUSTITEM)));
					sales.setMrp(res.getFloat(res.getColumnIndex(MRP)));
					sales.setSPrice(res.getFloat(res.getColumnIndex(S_PRICE)));
					sales.setQuantity(res.getInt(res.getColumnIndex(QUANTITY)));
					sales.setAmount(res.getString(res.getColumnIndex(AMOUNT)));
					sales.setTotal(res.getFloat(res.getColumnIndex(TOTAL)));
					sales.setMobile_No(res.getString(res.getColumnIndex(CREDITMOBILENO)));
					settlecreditcustomerlistitem.add(sales);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return settlecreditcustomerlistitem;
	}




	//!!!!!!!!!!!!!!!!!!!!!!!!!!!! Insert Credit customer !!!!!!!!!!!!!!!!!!//

	public boolean insercredticustomeritemlist(ArrayList<Sales> creditcustBillItems,String Trans_Id,String GRAND_TOTAL,String MobileNo) {

		boolean returnval = true;


		if (creditcustBillItems == null) {
			return true;
		}

		SQLiteDatabase db = this.getWritableDatabase();

		//For all items in sales bill, update the stock quantity
		for (Sales sales : creditcustBillItems) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("BILL_NO",Trans_Id);
			contentValues.put("BILL_DATE",getDate());
			contentValues.put("PAYMENT_ID",getSystemCurrentTime());
			contentValues.put("ITEM",sales.getProductName());
			contentValues.put("MRP",sales.getMrp());
			contentValues.put("S_PRICE",sales.getSPrice());
			contentValues.put("QTY",sales.getQuantity());
			contentValues.put("AMOUNT",sales.getTotal());
			contentValues.put("TOTAL",GRAND_TOTAL);
			contentValues.put("FLAG","1");
			contentValues.put("MOBILE_NO",MobileNo);
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");


			db.insert("retail_credit_bill_details", null, contentValues);
		}
		return  true;
	}


	////************************Getallsettlecreditcustomer********************************///
	public ArrayList<Settlecustmodel> getAllsettleCreditCustomer(String name) {
		ArrayList<Settlecustmodel> settlecreditcustomerlist = new ArrayList<Settlecustmodel>();
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			String[] params = new String[1];
			params[0] = name + "%";
			// params[1] = name + "%";
			Cursor res = db.rawQuery("select distinct INVOICE_NO,NAME,GRAND_TOTAL,DUE_AMOUNT,FLAG,TOTAL,MOBILE_NO,CREDIT_DATE from retail_credit_cust where"
							+ "   NAME like ? AND FLAG like '1%' "
					//AND DUE_AMOUNT > '0.00'
					, params);
			if (null != res && res.moveToFirst() && res.getCount() > 0) {
				do {
					Settlecustmodel settlecustmodel= new Settlecustmodel();
					settlecustmodel.setName(res.getString(res.getColumnIndex(CREDITCUSTNAME)));
					settlecustmodel.setPhoneno(res.getString(res.getColumnIndex(CREDITMOBILENO)));
					settlecustmodel.setOutstanding(res.getString(res.getColumnIndex(CREDITCUSTGRANDTOTAL)));
					settlecustmodel.setInvoiceno(res.getString(res.getColumnIndex(CREDITCUSTINVOICE)));

					settlecreditcustomerlist.add(settlecustmodel);
					//customerlist.add(res.getString(res.getColumnIndex(CUSTOMERMOBILENO)));
				} while (res.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
		}

		db.close();
		return settlecreditcustomerlist;
	}



	public boolean insertemailsalesreturn_withinvoicereport(ArrayList<SalesReturnReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SalesReturnReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			// contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			// contentValues.put("TOTAL", report.getTotal());
			// contentValues.put("PROD_NM", report.getProdNm());
			// contentValues.put("QTY", report.getQty());
			// contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_details_return_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailsalesreturn_withoutinvoicereport(String prodid) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (SalesReturnReportModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("TRI_ID", prodid);
         /*contentValues.put("TOTAL", report.getTotal());
         contentValues.put("PROD_NM", report.getProdNm());
         contentValues.put("QTY", report.getQty());
         contentValues.put("SALE_DATE", report.getSaleDate());*/
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_sales_details_return_mail", null, contentValues);
		//}
		return true;
	}



//=====================MasterDataReport_Email=============================//

	public boolean insertEmaildatadistributor(String dstrbtr,String actve) {

		SQLiteDatabase db = this.getWritableDatabase();
//    for (ReportDistributorModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER",user);
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("DSTR_NM", dstrbtr);
		contentValues.put("ACTIVE", actve);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_dstr_mail", null, contentValues);
		//}
		return true;
	}


	public boolean insertEmaildataVendor(String vendr,String actve) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (ReportVendorModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("VEND_NM", vendr);
		contentValues.put("ACTIVE", actve);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_store_vendor_mail", null, contentValues);
		//}
		return true;
	}


	public boolean insertEmaildataProductPharma(String prodnm,String actve) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (ReportProductPharmaModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("PROD_NM", prodnm);
		contentValues.put("ACTIVE", actve);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_store_prod_mail", null, contentValues);
		//}
		return true;
	}

	public boolean insertEmaildataLocalProductPharma(String prodnm,String actve) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (ReportLocalProductPharmaModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("PROD_NM", prodnm);
		contentValues.put("ACTIVE", actve);
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_store_prod_local_mail", null, contentValues);
		//}
		return true;
	}
	public boolean insertEmaildataLocalProductCPG(ArrayList<ReportLocalProductCpgModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (ReportLocalProductCpgModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BARCODE", report.getBarCode());
			contentValues.put("MRP", report.getMRP());
			contentValues.put("S_PRICE", report.getS_Price());
			contentValues.put("P_PRICE", report.getP_Price());
			contentValues.put("ACTIVE", report.getActive());
			contentValues.put("PROFIT_MARGIN", report.getProfitMargin());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_store_prod_local_cpg_mail", null, contentValues);
		}
		return true;
	}


	public boolean insertEmaildataProductCPG(ArrayList<ReportProductCpgModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (ReportProductCpgModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BARCODE", report.getBarCode());
			contentValues.put("MRP", report.getMRP());
			contentValues.put("S_PRICE", report.getS_Price());
			contentValues.put("P_PRICE", report.getP_Price());
			contentValues.put("ACTIVE", report.getActive());
			contentValues.put("PROFIT_MARGIN", report.getProfitMargin());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_store_prod_cpg_mail", null, contentValues);
		}
		return true;
	}

	//==========PurchasingReport_Email===========================//

	public boolean insertEmail_1monthpurchase(ArrayList<VendorReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (VendorReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PO_NO", report.getPo_No());
			contentValues.put("VENDOR_NM", report.getVendor_Nm());
			contentValues.put("TOTAL", report.getTotal());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_po_detail_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertEmail_3monthpurchase(ArrayList<VendorReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (VendorReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PO_NO", report.getPo_No());
			contentValues.put("VENDOR_NM", report.getVendor_Nm());
			contentValues.put("TOTAL", report.getTotal());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_po_detail_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertEmailpurchase(String VendNm) {



		SQLiteDatabase db = this.getWritableDatabase();
		//for (VendorReportModel report : list) {
		//for (VendorReportModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		//contentValues.put("PO_NO", report.getPo_No());
		contentValues.put("VENDOR_NM", VendNm);
		//contentValues.put("TOTAL", report.getTotal());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert(" retail_str_po_detail_mail ", null, contentValues);
		//}
		return true;
	}

	//=============InventoryReport_Email========================================//

	public boolean insertEmailDailyinventory(ArrayList<InventoryReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (InventoryReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BATCH_NO", report.getBatch());
			contentValues.put("EXP_DATE", report.getExpiry());
			contentValues.put("QTY", report.getQuantity());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_stock_master_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertEmail_1monthinventory(ArrayList<InventoryReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (InventoryReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BATCH_NO", report.getBatch());
			contentValues.put("EXP_DATE", report.getExpiry());
			contentValues.put("QTY", report.getQuantity());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_stock_master_mail ", null, contentValues);
		}
		return true;
	}


	public boolean insertEmail_3monthinventory(ArrayList<InventoryReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (InventoryReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BATCH_NO", report.getBatch());
			contentValues.put("EXP_DATE", report.getExpiry());
			contentValues.put("QTY", report.getQuantity());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_stock_master_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertEmail_6monthinventory(ArrayList<InventoryReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (InventoryReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUser_Nm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("PROD_NM", report.getProd_Nm());
			contentValues.put("BATCH_NO", report.getBatch());
			contentValues.put("EXP_DATE", report.getExpiry());
			contentValues.put("QTY", report.getQuantity());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" retail_str_stock_master_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertEmailmonthinventory(String ProdNm) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (InventoryReportModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUser_Nm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("PROD_NM", ProdNm);
		//contentValues.put("PROD_ID", report.getProd_Id());
		//contentValues.put("BATCH_NO", report.getBatch());
		//contentValues.put("EXP_DATE", report.getExpiry());
		//contentValues.put("QTY", report.getQuantity());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert(" retail_str_stock_master_mail ", null, contentValues);
		//}
		return true;
	}


	//====================VendorPaybyCashReport_Email=========================//

	public boolean insertemail_1monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByCashVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserName());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertemail_3monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByCashVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserName());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertemail_6monthpaybycash(ArrayList<PayByCashVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByCashVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserName());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertemailmonthpaybycash(String VendNm) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (PayByCashVendorPaymentModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserName());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("VEND_DSTR_NM", VendNm);
		//contentValues.put("AMOUNT", report.getAmountPaid());
		//contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
		//contentValues.put("DUE_AMOUNT", report.getAmountDue());
		//contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
		//contentValues.put("LAST_MODIFIED", report.getLastUpdate());
		//contentValues.put("PAY_ID", report.getGrnId());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		//}
		return true;
	}

	//===========VendorPaybycheque_Email================================//

	public boolean insertemail_1monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByChequeVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("BANK_NAME", report.getBankName());
			contentValues.put("CHEQUE_NO", report.getChequeNo());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertemail_3monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByChequeVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("BANK_NAME", report.getBankName());
			contentValues.put("CHEQUE_NO", report.getChequeNo());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}

	public boolean insertemail_6monthpaybycheque(ArrayList<PayByChequeVendorPaymentModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (PayByChequeVendorPaymentModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("VEND_DSTR_NM", report.getVendorNm());
			contentValues.put("AMOUNT", report.getAmountPaid());
			contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
			contentValues.put("DUE_AMOUNT", report.getAmountDue());
			contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
			contentValues.put("LAST_MODIFIED", report.getLastUpdate());
			contentValues.put("BANK_NAME", report.getBankName());
			contentValues.put("CHEQUE_NO", report.getChequeNo());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		}
		return true;
	}


	public boolean insertemailmonthpaybycheque(String VendNm) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (PayByChequeVendorPaymentModel report : list) {
		ContentValues contentValues = new ContentValues();

		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("VEND_DSTR_NM", VendNm);
         /*contentValues.put("AMOUNT", report.getAmountPaid());
         contentValues.put("RECEIVED_AMOUNT", report.getAmountRcvd());
         contentValues.put("DUE_AMOUNT", report.getAmountDue());
         contentValues.put("REASON_OF_PAY", report.getReasonOfPay());
         contentValues.put("LAST_MODIFIED", report.getLastUpdate());
         contentValues.put("BANK_NAME", report.getBankName());
         contentValues.put("CHEQUE_NO", report.getChequeNo());*/
		//contentValues.put("PAY_ID", report.getGrnId());
		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert(" temp_vend_dstr_payment_mail ", null, contentValues);
		//}
		return true;
	}

	public boolean insertemailvendorreturn(String VendNm ) {

		SQLiteDatabase db = this.getWritableDatabase();
		//for (ReportVendorReturnModel report : list) {
		ContentValues contentValues = new ContentValues();
		//contentValues.put("POS_USER", report.getUserNm());
		contentValues.put("TICKET_ID", getSystemCurrentTime());
		contentValues.put("VENDOR_NM", VendNm);
		//contentValues.put("VENDOR_RETURN_ID", report.getVendrId());

         /*contentValues.put("PROD_NM", report.getProdctNm());
         contentValues.put("BATCH_NO", report.getBatchNo());
         contentValues.put("EXP_DATE", report.getExpDate());
         contentValues.put("REASON_OF_RETURN", report.getReason());
         contentValues.put("QTY", report.getQty());
         contentValues.put("P_PRICE", report.getPPrice());
         contentValues.put("TOTAL", report.getTotal());
         contentValues.put("UOM", report.getUom());*/

		contentValues.put("M_FLAG","I");
		contentValues.put("S_FLAG","0");
		db.insert("retail_str_vendor_detail_return_mail ", null, contentValues);
		//}
		return true;
	}

	//===========SalesReport_Email=======================//


	public boolean insertemaildailysalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}


	public boolean insertemailweeklysalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();


			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}




	public boolean insertemailmonthlysalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailquarterlysalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailyearlysalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}

	public boolean insertemailsalesreport(ArrayList<SaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (SaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("SALE_DATE", report.getSaleDate());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}

	public void Deletestock(String remove) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME_STOCK_DELETE + " where " + DELETE_STOCK_BATCH + " = '" + remove+ "'");

		String Delet_Stock = "delete  from retail_str_stock_master " + "" + "WHERE BATCH_NO=" + "'" + remove + "'";
		Log.e("@@QQQQ",Delet_Stock);

		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("Query",Delet_Stock);
			login logi = new login();
			login.sendMessage(String.valueOf(jsonObject));
		}catch (Exception e){

		}

		db.close();

	}



	public ArrayList<VendorReportModel> getVendorNameprocurement(String name) {
		ArrayList<VendorReportModel> vendornamelist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = name + "%";
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_detail  where"
							+ " VENDOR_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendornamelist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendornamelist;
	}

	//////////////////////////////////////////////get DATA FROM  retail_str_po_detail TABLE//////////////////////////////////////////////////

	public ArrayList<VendorReportModel> getVendorReportprocurement(String PoNo) {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PoNo + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,PO_NO,VENDOR_NM,GRAND_TOTAL from retail_str_po_master  where"
							+ " VENDOR_NM like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_USER_NM)));
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRAND_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return vendorlist;
	}


	public ArrayList<VendorReportModel> getDailyVendorReportprocurement(String PoNo) {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,PO_NO,VENDOR_NM,GRAND_TOTAL from retail_str_po_master where PURCHASEDATE='"+PoNo+"'  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_USER_NM)));
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRAND_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return vendorlist;
	}

	public ArrayList<VendorReportModel> getPurchasing1MonthDataForReport() {
		ArrayList<VendorReportModel> idlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();

			theStart.add(Calendar.DAY_OF_MONTH, -30);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,PO_NO,VENDOR_NM,GRAND_TOTAL from retail_str_po_master"
							+ " where PURCHASEDATE between '" + start + "' AND '" + end + "' group by PO_NO "
					, null);
			//TRI_ID = (" + TRANS_ID + ")  and
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_USER_NM)));
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRAND_TOTAL)));
					idlist.add(vm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	public ArrayList<VendorReportModel> getPurchasing3MonthDataForReport(String PoNo) {

		ArrayList<VendorReportModel> vendorlist = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -92);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct POS_USER,PO_NO,VENDOR_NM,GRAND_TOTAL from retail_str_po_master where PURCHASEDATE between '" + start + "' AND '" + end + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_USER_NM)));
					vm.setPo_No(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_GRAND_TOTAL)));
					vendorlist.add(vm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return vendorlist;
	}

/////////////////////////////////////////////// get DATA From year and Month For Purchase REPORT//////////////////////////////////////////////////////////

	public ArrayList<VendorReportModel> PurchaseDataForMonth(String Value1, String Value2) {
		ArrayList<VendorReportModel> OldInvoiceData = new ArrayList<VendorReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct POS_USER,PO_NO,VENDOR_NM,GRAND_TOTAL from retail_str_po_master where "
					+ " PURCHASEDATE between '" + Value1 + "' AND '" + Value2 + "'", null);
			if (productcursor.moveToFirst()) {
				do {
					VendorReportModel vm = new VendorReportModel();
					vm.setUserNm(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_USER_NM)));
					vm.setPo_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_PO_NO)));
					vm.setVendor_Nm(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vm.setTotal(productcursor.getString(productcursor.getColumnIndex(COLUMN_MASTER_GRAND_TOTAL)));
					OldInvoiceData.add(vm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}

/////////////////////////////////////////////////////get all data from retail_str_po_detail ////////////////////////////////////////////////////////////////////////////

	public ArrayList<PurchaseProductReportModel> getDailyPurchasedata(String TriId) {
		ArrayList<PurchaseProductReportModel> idlist = new ArrayList<PurchaseProductReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			String curr_date = s.toString();
			Log.e("current date :", curr_date);
			SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");

			Cursor cursor = db.rawQuery("select PROD_NM,P_PRICE,QTY,UOM,PURCHASEDATE from retail_str_po_detail "
							+ "  where " + " PO_NO ='" + TriId + "' and PURCHASEDATE = '" + curr_date + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PurchaseProductReportModel pm = new PurchaseProductReportModel();
					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
					pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
					//pm.setTotal(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
					pm.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASEDATE)));
					pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
					idlist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

   /*public ArrayList<PurchaseProductReportModel> getall1MonthPurchasedata(String Value) {
      ArrayList<PurchaseProductReportModel> getpurchaselist = new ArrayList<PurchaseProductReportModel>();
      SQLiteDatabase db = this.getReadableDatabase();
      Calendar theEnd = Calendar.getInstance();
      Calendar theStart = (Calendar) theEnd.clone();
      theStart.add(Calendar.DAY_OF_MONTH, -30);
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      String start = dateFormat.format(theStart.getTime());
      String end = dateFormat.format(theEnd.getTime());
      Cursor cursor = db.rawQuery("select PROD_NM,P_PRICE,QTY,UOM,PURCHASEDATE from retail_str_po_detail "
                  + "  where " + " PO_NO ='" + Value + "' and PURCHASEDATE between '" + start + "' AND '" + end + "' "
            , null);

      if (cursor.moveToFirst()) {
         do {
            PurchaseProductReportModel pm = new PurchaseProductReportModel();
            pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
            pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
            //pm.setTotal(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL)));
            pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
            pm.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASEDATE)));
            pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
            getpurchaselist.add(pm);

         } while (cursor.moveToNext());
      }
      return getpurchaselist;
   }*/

	public ArrayList<PurchaseProductReportModel> getall1MonthPurchasedata(String TriId) {
		ArrayList<PurchaseProductReportModel> idlist = new ArrayList<PurchaseProductReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -30);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select PROD_NM,P_PRICE,QTY,UOM,PURCHASEDATE from retail_str_po_detail "
							+ "  where " + " PO_NO ='" + TriId + "' and PURCHASEDATE between '" + start + "' AND '" + end + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					PurchaseProductReportModel pm = new PurchaseProductReportModel();
					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
					pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
					pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
					pm.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASEDATE)));
					pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
					idlist.add(pm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<PurchaseProductReportModel> getall3MonthPurchasedata(String Value) {
		ArrayList<PurchaseProductReportModel> getpurchaselist = new ArrayList<PurchaseProductReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -92);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor cursor = db.rawQuery("select PROD_NM,P_PRICE,QTY,UOM,PURCHASEDATE from retail_str_po_detail "
						+ "  where " + " PO_NO ='" + Value + "' and PURCHASEDATE between '" + start + "' AND '" + end + "' "
				, null);

		if (cursor.moveToFirst()) {
			do {
				PurchaseProductReportModel pm = new PurchaseProductReportModel();
				pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
				pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
				//pm.setTotal(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL)));
				pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
				pm.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASEDATE)));
				pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}

	public ArrayList<PurchaseProductReportModel> getallPurchasedata(String Value) {
		ArrayList<PurchaseProductReportModel> getpurchaselist = new ArrayList<PurchaseProductReportModel>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select PROD_NM,P_PRICE,QTY,UOM,PURCHASEDATE from retail_str_po_detail "
						+ "  where " + " PO_NO ='" + Value + "' "
				, null);

		if (cursor.moveToFirst()) {
			do {
				PurchaseProductReportModel pm = new PurchaseProductReportModel();
				pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
				pm.setProductPrice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
				//pm.setTotal(cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL)));
				pm.setUom(cursor.getString(cursor.getColumnIndex(COLUMN_UOM)));
				pm.setPurchaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASEDATE)));
				pm.setProductQuantity(cursor.getFloat(cursor.getColumnIndex(COLUMN_QTY)));
				getpurchaselist.add(pm);

			} while (cursor.moveToNext());
		}
		return getpurchaselist;
	}







   /*public ArrayList<SaleReportModel> getDailyOverallSalesBillDetailReport(String TriId) {
      ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,MRP,Sum(QTY) from retail_str_sales_detail "
                     + "  where " + " TRI_ID ='" + TriId + "' group by PROD_NM, SALE_DATE"
               , null);
         if (cursor.moveToFirst()) {
            do {
               SaleReportModel sm = new SaleReportModel();
               sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
               sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
               sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
               sm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_MRP)));
               sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SUM_SALE_QTY)));
               idlist.add(sm);
            } while (cursor.moveToNext());
         }
      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      } catch (NullPointerException ex) {
         ex.printStackTrace();
      }
      return idlist;
   }*/

	public ArrayList<InventoryReportModel> getdataforDailyinventory(String TriId) {
		ArrayList<InventoryReportModel> idlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct Pos_User,Prod_Nm,Batch_No,Exp_Date,CON_MUL_QTY from retail_str_stock_master where INVENTORY_DATE='" + TriId + "' " +
					"and CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0'  "
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
					idlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<InventoryReportModel> getdataforinventory() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theEnd.add(Calendar.MONTH,+1);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());

			Cursor cursor = db.rawQuery("select distinct Pos_User,Prod_Nm,Batch_No,Exp_Date,CON_MUL_QTY,P_PRICE from retail_str_stock_master " +
							" where CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0'"
					, null);
   /* Cursor cursor = db.rawQuery("select distinct Pos_User,Prod_Nm,Batch_No,Exp_Date,CON_MUL_QTY,P_PRICE from retail_str_stock_master " +
                  " where EXP_DATE between '" + start + "' AND '" + end + "'and CON_MUL_QTY != '0' ", null);
      */if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
					im.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
					Date datecurrent = dateFormat1.parse(start);
					Date lastdate= dateFormat1.parse(end);
					String mydate = im.getExpiry();
					Date urdate = dateFormat1.parse(mydate);

					if (urdate.after(datecurrent) && urdate.before(lastdate)) {

						distributorlist.add(im);
					}
            /*else
            {
               cursor.moveToNext();
            }*/

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return distributorlist;
	}


	public ArrayList<InventoryReportModel> getInventory3MonthDataForReport() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.MONTH,+3);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String start = dateFormat.format(theEnd.getTime());
			String end = dateFormat.format(theStart.getTime());

			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BATCH_NO,EXP_DATE,CON_MUL_QTY,P_PRICE from retail_str_stock_master " +
							"where CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0'"
					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();

					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
					Date datecurrent = dateFormat1.parse(start);
					Date lastdate= dateFormat1.parse(end);
					String mydate = im.getExpiry();
					Date urdate = dateFormat1.parse(mydate);
					if (urdate.after(datecurrent) && urdate.before(lastdate))
					{
						im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
						im.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
						distributorlist.add(im);
					}
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return distributorlist;
	}

	public ArrayList<InventoryReportModel> getInventory6MonthDataForReport() {
		ArrayList<InventoryReportModel> distributorlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theEnd.add(Calendar.MONTH, +6);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());

      /*Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BATCH_NO,EXP_DATE,CON_MUL_QTY,P_PRICE from retail_str_stock_master " +
                  "where EXP_DATE between '" + start + "' AND '" + end + "' and CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0'"
            , null);*/
			Cursor cursor = db.rawQuery("select distinct POS_USER,PROD_NM,BATCH_NO,EXP_DATE,CON_MUL_QTY,P_PRICE from retail_str_stock_master " +
							"where CON_MUL_QTY != '0' and CON_MUL_QTY != '0.0'"
					, null);

			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
					Date datecurrent = dateFormat1.parse(start);
					Date lastdate= dateFormat1.parse(end);
					String mydate = im.getExpiry();
					Date urdate = dateFormat1.parse(mydate);
					if (urdate.after(datecurrent) && urdate.before(lastdate))
					{
						im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
						im.setPPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PURCHASE_PRICE)));
						distributorlist.add(im);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}catch (ParseException e) {
			e.printStackTrace();
		}
		return distributorlist;
	}



/////////////////////////////////////get DATA From year and Month For Inventory REPORT///////////////////////////////

	public ArrayList<InventoryReportModel> InventoryDataForMonth(String Value1, String Value2) {
		ArrayList<InventoryReportModel> OldInvoiceData = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select POS_USER,PROD_NM,BATCH_NO,EXP_DATE,CON_MUL_QTY from retail_str_stock_master " +
							"where EXP_DATE between '" + Value1 + "' AND '" + Value2 + "' "

					, null);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
					OldInvoiceData.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return OldInvoiceData;
	}

	///////////////////////////////////////////////get product id from retail_str_stock_master/////////////////////////////////////////////////////////////////

	public ArrayList<InventoryReportModel> getProductId(String id) {
		ArrayList<InventoryReportModel> productIdlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[2];
			params[0] = id + "%";
			params[1] = id + "%";
			Cursor cursor = db.rawQuery("select distinct PROD_ID,PROD_NM from retail_str_stock_master  where"
							+ " PROD_ID like ? or PROD_NM like ?"
					, params);
			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setProd_Id(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_ID)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					productIdlist.add(im);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return productIdlist;
	}
	//////////////////////////////////////////////get DATA FROM  retail_str_stock_master TABLE//////////////////////////////////////////////////

	public ArrayList<InventoryReportModel> getInventoryReport(String ProdId) {
		ArrayList<InventoryReportModel> Idlist = new ArrayList<InventoryReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = ProdId + "%";
			//params[1] = ProdId + "%";
			Cursor cursor = db.rawQuery("select * from retail_str_stock_master  where"
							+ " PROD_NM like ? or PROD_ID like ? "
					, params);


			if (cursor.moveToFirst()) {
				do {
					InventoryReportModel im = new InventoryReportModel();
					im.setUser_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NM)));
					im.setProd_Nm(cursor.getString(cursor.getColumnIndex(COLUMN_PROD_NAME)));
					im.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_BATCH_NO)));
					im.setExpiry(cursor.getString(cursor.getColumnIndex(COLUMN_EXP_DATE)));
					im.setQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_CONMUL_QUANTITY)));
					Idlist.add(im);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}
		return Idlist;
	}




	public ArrayList<String> stockMaster(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_stock_master where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("FREE_GOODS")));
				arraylist.add(cur.getString(cur.getColumnIndex("MF_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("IND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_SPRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREV_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MFG_BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT_AMOUNT")));


				arraylist.add(";");




			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailStore(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_MEDIA_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("STR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ADD_1")));
				arraylist.add(cur.getString(cur.getColumnIndex("CTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("STR_CTR")));
				arraylist.add(cur.getString(cur.getColumnIndex("ZIP")));
				arraylist.add(cur.getString(cur.getColumnIndex("STR_CNTCT_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TELE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TELE_1")));
				arraylist.add(cur.getString(cur.getColumnIndex("E_MAIL")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAN_NUMBER")));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_NUMBER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FOOTER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("STR_IND_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("RET_CLS_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TEAM_MEMB")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));



				arraylist.add(cur.getString(cur.getColumnIndex("OTP")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S3_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("LKr");

				//arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrDayOpenClose(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_day_open_close where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("BUSINESS_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("START_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("START_CASH")));
				arraylist.add(cur.getString(cur.getColumnIndex("CBCASH_ONHAND")));
				arraylist.add(cur.getString(cur.getColumnIndex("START_TIME")));
				arraylist.add(cur.getString(cur.getColumnIndex("START_USER_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("OPCASH_ONHAND")));
				arraylist.add(cur.getString(cur.getColumnIndex("OP_CUR")));
				arraylist.add(cur.getString(cur.getColumnIndex("END_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("END_TIME")));
				arraylist.add(cur.getString(cur.getColumnIndex("END_USER_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("CB_CUR")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));
				arraylist.add(cur.getString(cur.getColumnIndex("REMARKS")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrGrnMaster(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_grn_master where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_AMOUNT")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}

	public ArrayList<String> tmpVendDstrPayment(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from tmp_vend_dstr_payment where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("PAYMENT_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_DSTR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_OF_PAY")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("RECEIVED_AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DUE_AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("BANK_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CHEQUE_NO")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAYMENT_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrSalesDetailMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_detail_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				//arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add("null");


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrPoDetailMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_po_detail_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrDstrMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_dstr_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreVendorMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_vendor_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProdMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProdLocalMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod_local_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProdLocalCpgMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod_local_cpg_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProdCpgMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod_cpg_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailAdTickerMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_ad_ticker_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");


			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailAdStoreMainMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_ad_Store_Main_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_MAIN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB1")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB2")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB3")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				// arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retail_ad_blinkinglogo_mail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_ad_blinkinglogo_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_MAIN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_STRT_DT")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_END_DT")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB1")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB2")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_CST_SLB3")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreMaint(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_maint where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("SUBJECT_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("SUPPORT_PRIORITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("SUPPORT_TICKET_STATUS")));
				arraylist.add(cur.getString(cur.getColumnIndex("TEAM_GROUP")));
				arraylist.add(cur.getString(cur.getColumnIndex("TEAM_MEMBER")));
				arraylist.add(cur.getString(cur.getColumnIndex("COMMENT")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrVendorDetailReturn(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_vendor_detail_return where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_RETURN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_OF_RETURN")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("RETURN_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreVendReject(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_vend_reject where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_FOR_REJECTION")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailEmployees(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_employees where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("USR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("FIRST_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("LAST_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("PASSWORD")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONFIRM_PASSWORD")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailTopProduct(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_top_product where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_SHORT_NM")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> adTickerMain(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from ad_ticker_main where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("AD_MAIN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_TEXT")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_STRT_DT")));
				arraylist.add(cur.getString(cur.getColumnIndex("AD_END_DT")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> drDiscription() {
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from dr_discription where S_FLAG='0'", null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("DR_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("DR_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("SPECIALITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("DR_ADDRESS")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailSendMailPdf() {
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_send_mail_pdf where S_FLAG='0'", null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TXN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("UNIVERSAL_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREFIX_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("FM")));
				arraylist.add(cur.getString(cur.getColumnIndex("EMAIL_TO")));
				arraylist.add(cur.getString(cur.getColumnIndex("CC")));
				arraylist.add(cur.getString(cur.getColumnIndex("SUBJECT")));
				arraylist.add(cur.getString(cur.getColumnIndex("BODY")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NAME")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrPoMaster() {
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_po_master where S_FLAG='0'", null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO1")));
            /*arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));*/
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_NAME2")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_ADDRESS")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_ADDRESS2")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_CITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_TO_CONTACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("YOUR_REFERENCE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_CODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_NAME2")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_ADDRESS")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_ADDRESS2")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_CITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("SHIP_TO_CONTACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POSTING_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXPECTED_RECEIPT_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("VAT_REGISTRATION_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASEDATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProd() {
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod where S_FLAG='0'", null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_DESC")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_HIER_DESC_3")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCH_ORDER_UNIT")));
				arraylist.add(cur.getString(cur.getColumnIndex("TH_CLASS_1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TH_CLASS_2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TH_CLASS_3")));
				arraylist.add(cur.getString(cur.getColumnIndex("TH_CLASS_4")));
				arraylist.add(cur.getString(cur.getColumnIndex("MFG")));
				arraylist.add(cur.getString(cur.getColumnIndex("SELLING_ORDER_UNIT")));
				arraylist.add(cur.getString(cur.getColumnIndex("IS_PRESE_REL")));
				arraylist.add(cur.getString(cur.getColumnIndex("IS_PHARMA_REL")));
				arraylist.add(cur.getString(cur.getColumnIndex("IS_BATCH")));
				arraylist.add(cur.getString(cur.getColumnIndex("INTERNET_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("IS_PROD_REL_INT")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("C_IMG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("IND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT_AMOUNT")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStoreProdLocal() {
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_prod_local where S_FLAG='0'", null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("INTERNET_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("IS_PROD_REL_INT")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("IND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("SELLING_ORDER_UNIT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT_AMOUNT")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrDstr(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_dstr where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_CNTCT_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ADD_1")));
				arraylist.add(cur.getString(cur.getColumnIndex("CITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CTR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("ZIP")));
				arraylist.add(cur.getString(cur.getColumnIndex("TELE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MOBILE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DSTR_INV")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("EMAIL")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrSalesDetailsReturn(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_details_return where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("STATUS")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("ITEM_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_PERC")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_VAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER3")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String>  retailStrSalesMasterReturn(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_master_return where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_OF_RETURN")));
				arraylist.add(cur.getString(cur.getColumnIndex("BUSINESS_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_TIME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("SCHEME_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> tmpVendDstrPaymentMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from tmp_vend_dstr_payment_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAY_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_DSTR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("RECEIVED_AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DUE_AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_OF_PAY")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("BANK_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CHEQUE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("PAYMENTDATE")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailCust(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_cust where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("E_MAIL")));
				arraylist.add(cur.getString(cur.getColumnIndex("MOBILE_NO")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("PASSWORD")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREDIT_CUST")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retail_strVendorDetailReturnMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_vendor_detail_return_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_RETURN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_OF_RETURN")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrStockMasterMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_stock_master_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				//arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrSalesDetailsReturnMail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_details_return_mail where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("TICKET_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));
				arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailCardDefine(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_card_define where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POINTS_RANGE")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> ruleDefination(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from rule_defination where S_FLAG='0' limit 10",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("SNO")));
				arraylist.add(cur.getString(cur.getColumnIndex("USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BASE_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PER_TON_POINTS")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				//arraylist.add("null");
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}
	public ArrayList<String> retailStrLineitemDisc(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_lineitem_disc where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINEITEM_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;
	}









	public ArrayList<String> retailStoreCustReject(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_cust_reject where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("REASON_FOR_REJECTION")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));


				arraylist.add(";");




			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailStrBillLvlDisc(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_bill_lvl_disc where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("BILL_LVL_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ACTIVE")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");




			} while (cur.moveToNext());
		}

		return arraylist;

	}

	public ArrayList<String> retailMediaClick(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_media_click where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("AD_PLAY")));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_MEDIA_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("MOBILE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("CLICK")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				//arraylist.add("null");

				arraylist.add(";");


			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailStrSalesDetail(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_detail where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {



				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("ITEM_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_PERC")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_VAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE1")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREFIX_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER3")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE3")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));



				arraylist.add(";");


			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailStrSalesMaster(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_master where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("BUSINESS_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_TIME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("SCHEME_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_CNCT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DOCT_NAME")));
				//arraylist.add(cur.getString(cur.getColumnIndex("null")));
				arraylist.add("0");

				arraylist.add(";");


			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailBillDisplay(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_bill_display where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {


				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL_BILL_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("NET_BILL_PAYABLE")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT_RECEIVED")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT_PAID_BACK")));
				arraylist.add(cur.getString(cur.getColumnIndex("FOOTER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");


			} while (cur.moveToNext());
		}

		return arraylist;

	}
	public ArrayList<String> retailBillVisible(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_bill_visible where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_ADDRESS")));
				arraylist.add(cur.getString(cur.getColumnIndex("CITY")));
				arraylist.add(cur.getString(cur.getColumnIndex("STORE_COUNTRY")));
				arraylist.add(cur.getString(cur.getColumnIndex("ZIP")));
				arraylist.add(cur.getString(cur.getColumnIndex("TELE_1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TELE_2")));
				arraylist.add(cur.getString(cur.getColumnIndex("MAIN_BODY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL_BILL_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("NET_BILL_PAYABLE")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT_RECEIVED")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT_PAID_BACK")));
				arraylist.add(cur.getString(cur.getColumnIndex("FOOTER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("NORMAL_SALES")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREDIT_CUSTOMER")));
				arraylist.add(cur.getString(cur.getColumnIndex("RETURNS")));





				arraylist.add(";");

			} while (cur.moveToNext());
		}

		return arraylist;

	}

	public ArrayList<String> retailStoreDecimal(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_store_decimal where S_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP_DECIMAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE_DECIMAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE_DECIMAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("HOLD_PO")));
				arraylist.add(cur.getString(cur.getColumnIndex("HOLD_INV")));
				arraylist.add(cur.getString(cur.getColumnIndex("HOLD_SALES")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("ROUND_OFF")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALES_HOLD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_HOLD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("INV_HOLD_NO")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}

		return arraylist;

	}





	public ArrayList<SaleReportModel> getDailySalesReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from retail_str_sales_master where SALE_DATE='" + TriId + "' group by TRI_ID "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setTotalAfterDscnt(cursor.getString(cursor.getColumnIndex(COLUMN_SAVETOTALBILLAMOUNT)));
					sm.setDiscount(cursor.getString(cursor.getColumnIndex(COLUMN_SAVETOTALBILLDISCOUNT)));

					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public float getDailyCashReport(String TriId) {
		String[] params = new String[1];
		params[0] = TriId + "%";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail where SALE_DATE='" + TriId + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getDailyCreditReport(String TriId) {
		String[] params = new String[1];
		params[0] = TriId + "%";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE='" + TriId + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getDailySalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			String curr_date = s.toString();
			Log.e("current date :", curr_date);
			SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");

			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID ='" + TriId + "' and SALE_DATE = '" + curr_date + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}


	public ArrayList<SaleReportModel> getWeeklySalesReport() {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -7);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct TRI_ID,POS_USER,SUM(GRAND_TOTAL),SALE_DATE,CARD_NO from retail_str_sales_master"
							+ " where SALE_DATE between '" + start + "' AND '" + end + "' group by TRI_ID "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALEMASTERSUM_TOTAL)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public float getWeeklyCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -7);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail where SALE_DATE between '" + start + "' AND '" + end + "'", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getWeeklyCreditReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -7);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE between '" + start + "' AND '" + end + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getWeeklySalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TriId + "%";
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -7);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Log.e("TransId",TriId);
			String TriId1=TriId.replace("-"," ");
			Log.e("TransId1",TriId1);
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID like ? and SALE_DATE between '" + start + "' AND '" + end + "' "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<SaleReportModel> getMonthlySalesReport() {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -30);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct TRI_ID,POS_USER,SUM(GRAND_TOTAL),SALE_DATE,CARD_NO from retail_str_sales_master"
							+ " where SALE_DATE between '" + start + "' AND '" + end + "' group by TRI_ID "
					, null);
			//TRI_ID = (" + TRANS_ID + ")  and
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALEMASTERSUM_TOTAL)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	public float getMonthlyCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -30);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail where SALE_DATE between '" + start + "' AND '" + end + "'", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getMonthlyCreditReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -30);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE between '" + start + "' AND '" + end + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getMonthlySalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -30);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID ='" + TriId + "' and SALE_DATE between '" + start + "' AND '" + end + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<SaleReportModel> getQuarterlySalesReport() {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();

			theStart.add(Calendar.DAY_OF_MONTH, -90);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct TRI_ID,POS_USER,SUM(GRAND_TOTAL),SALE_DATE,CARD_NO from retail_str_sales_master"
							+ " where SALE_DATE between '" + start + "' AND '" + end + "' group by TRI_ID "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALEMASTERSUM_TOTAL)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public float getQuarterlyCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -90);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail where SALE_DATE between '" + start + "' AND '" + end + "'  ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getQuarterlyCreditReport() {
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();

		theStart.add(Calendar.DAY_OF_MONTH, -90);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE between '" + start + "' AND '" + end + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getQuarterlySalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -90);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID ='" + TriId + "' and SALE_DATE between '" + start + "' AND '" + end + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}


	public ArrayList<SaleReportModel> getYearlySalesReport() {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -365);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select distinct TRI_ID,POS_USER,SUM(GRAND_TOTAL),SALE_DATE,CARD_NO from retail_str_sales_master"
							+ " where SALE_DATE between '" + start + "' AND '" + end + "' group by TRI_ID "
					, null);
			if (cursor.moveToFirst()) {

				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALEMASTERSUM_TOTAL)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	public float getYearlyCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -365);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail where SALE_DATE between '" + start + "' AND '" + end + "'", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getYearlyCreditReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
		Calendar theStart = (Calendar) theEnd.clone();
		theStart.add(Calendar.DAY_OF_MONTH, -365);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(theStart.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE between '" + start + "' AND '" + end + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getYearlySalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Calendar theEnd = Calendar.getInstance();
			Calendar theStart = (Calendar) theEnd.clone();
			theStart.add(Calendar.DAY_OF_MONTH, -365);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String start = dateFormat.format(theStart.getTime());
			String end = dateFormat.format(theEnd.getTime());
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID ='" + TriId + "' and SALE_DATE between '" + start + "' AND '" + end + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<SaleReportModel> getSalesReport(String Value1, String Value2) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct TRI_ID,POS_USER,SUM(GRAND_TOTAL),SALE_DATE,CARD_NO from retail_str_sales_master"
							+ " where SALE_DATE between '" + Value1 + "' AND '" + Value2 + "' group by TRI_ID "
					, null);
			if (cursor.moveToFirst()) {

				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					sm.setCardNo(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));
					sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALEMASTERSUM_TOTAL)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return idlist;
	}

	public float getCashReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_REPRT_SALE_TOTAL + ") FROM retail_str_sales_detail ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getCreditReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public ArrayList<SaleReportModel> getSalesBillDetailReport(String TriId) {
		ArrayList<SaleReportModel> idlist = new ArrayList<SaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,CARD_TYPE,BANK_NAME from retail_str_sales_detail "
							+ "  where " + " TRI_ID ='" + TriId + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SaleReportModel sm = new SaleReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setCardType(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_CARDTYPE)));
					sm.setBankNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_BANKNM)));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

	public ArrayList<OverallSaleReportModel> getDailyoverallSalesReport(String TriId) {
		ArrayList<OverallSaleReportModel> idlist = new ArrayList<OverallSaleReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select TRI_ID,POS_USER,PROD_NM,BATCH_NO,EXP_DATE,Sum(QTY),S_PRICE,TAX_VALUE3 from retail_str_sales_detail where SALE_DATE='"+TriId+"' group by TRI_ID, PROD_NM, SALE_DATE "
					, null);
			if (cursor.moveToFirst()) {
				do {
					OverallSaleReportModel sm = new OverallSaleReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_USER)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_ID)));
					//sm.setGrnTotl(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_TOTAL)));
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NO)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_DATE)));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SUM_SALE_QTY)));
					sm.setSPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE))));
					sm.setPPrice(Float.parseFloat(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_PPRICE))));
					idlist.add(sm);

				} while (cursor.moveToNext());

			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}




	public ArrayList<FragmentSalesLostReportPOJO> getLostSalesReport() {
		ArrayList<FragmentSalesLostReportPOJO> idlist = new ArrayList<FragmentSalesLostReportPOJO>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select * from retail_str_lost_sales"
					, null);
			if (cursor.moveToFirst()) {
				do {
					FragmentSalesLostReportPOJO sm = new FragmentSalesLostReportPOJO();
					sm.setProduct_name(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_NM)));
					sm.setSprice(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_SPRICE)));
					sm.setQuntity(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_QTY)));
					sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_TOTAL)));
					sm.setSaledate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));



//             sm.(cursor.getString(cursor.getColumnIndex(COLUMN_SAVETOTALBILLAMOUNT)));
//             sm.setDiscount(cursor.getString(cursor.getColumnIndex(COLUMN_SAVETOTALBILLDISCOUNT)));

					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}


	public float getDailyoverallCashReport(String TriId) {
		String[] params = new String[1];
		params[0] = TriId + "%";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_SAVETOTALBILLAMOUNT + ") FROM retail_str_sales_master where SALE_DATE='" + TriId + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}

	public float getDailyoverallCreditReport(String TriId) {
		String[] params = new String[1];
		params[0] = TriId + "%";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + CREDITCUSTREPORT + ") FROM retail_credit_cust where CREDIT_DATE='" + TriId + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;
	}


	public float getDailyoverallDiscountReport(String TriId) {
		String[] params = new String[1];
		params[0] = TriId + "%";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT SUM(" + COLUMN_SAVETOTALBILLDISCOUNT + ") FROM retail_str_sales_master where SALE_DATE='" + TriId + "' ", null);
		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		return i;

	}


	public boolean insertemailoveralldailysalesreport(ArrayList<OverallSaleReportModel>list) {

		SQLiteDatabase db = this.getWritableDatabase();
		for (OverallSaleReportModel report : list) {
			ContentValues contentValues = new ContentValues();

			contentValues.put("POS_USER", report.getUserNm());
			contentValues.put("TICKET_ID", getSystemCurrentTime());
			contentValues.put("TRI_ID", report.getTransId());
			contentValues.put("TOTAL", report.getGrnTotl());
			contentValues.put("UOM", report.getUom());
			contentValues.put("PROD_NM", report.getProdNm());
			contentValues.put("EXP_DATE", report.getExp());
			contentValues.put("S_PRICE", report.getPrice());
			contentValues.put("QTY", report.getQty());
			contentValues.put("M_FLAG","I");
			contentValues.put("S_FLAG","0");
			db.insert("retail_str_sales_detail_mail", null, contentValues);
		}
		return true;
	}




	public ArrayList<SalesReturnReportModel> getWithInvoiceTransId(String id) {
		ArrayList<SalesReturnReportModel> transidlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = id + "%";
			Cursor cursor = db.rawQuery("select distinct TRI_ID from retail_str_sales_details_return  where"
							+ " TRI_ID like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					transidlist.add(sm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return transidlist;
	}

	public ArrayList<SalesReturnReportModel> getSalesReturnWithInvoiceReport(String TransId) {

		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TransId + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,SUM(TOTAL),TRI_ID from retail_str_sales_details_return where TRI_ID like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
					sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_SUM_TOTAL)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	public ArrayList<SalesReturnReportModel> SalesReturnWithInvoiceData(String Value3, String Value4) {
		ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,SUM(TOTAL),TRI_ID from retail_str_sales_details_return where "
					+ " EXP_DATE between '" + Value3 + "' AND '" + Value4 + "' group by TRI_ID ", null);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
					sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_SUM_TOTAL)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					OldInvoiceData.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}

	public ArrayList<SalesReturnReportModel> getSalesReturnWithInvoiceDetailReport(String TriId) {
		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,MRP,QTY,SALE_DATE from retail_str_sales_details_return "
							+ "  where " + " TRI_ID ='" + TriId + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NO)));
					sm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_MRP)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_DATE)));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}

///////////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getWithoutInvoiceTransId(String id) {
		ArrayList<SalesReturnReportModel> transidlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = id + "%";
			Cursor cursor = db.rawQuery("select distinct TRI_ID from retail_str_sales_details_return  where"
							+ " TRI_ID like ?  "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					transidlist.add(sm);
				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return transidlist;
	}

//////////////////////////////////////////////// get DATA From year and Month For Sale REPORT//////////////////////////////////////////////////////////

	public ArrayList<SalesReturnReportModel> getSalesReturnWithoutInvoiceReport(String TransId) {

		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = TransId + "%";
			Cursor cursor = db.rawQuery("select distinct POS_USER,SUM(TOTAL),TRI_ID from retail_str_sales_details_return where TRI_ID like ? "
					, params);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
					sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_SUM_TOTAL)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					idlist.add(sm);

				} while (cursor.moveToNext());
			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return idlist;
	}

	public ArrayList<SalesReturnReportModel> SalesReturnWithoutInvoiceData(String Value3, String Value4) {
		ArrayList<SalesReturnReportModel> OldInvoiceData = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery("select distinct POS_USER,SUM(TOTAL),TRI_ID from retail_str_sales_details_return where "
					+ " EXP_DATE between '" + Value3 + "' AND '" + Value4 + "' group by TRI_ID ", null);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setUserNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_USER)));
					sm.setTotal(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_SUM_TOTAL)));
					sm.setTransId(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_ID)));
					OldInvoiceData.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return OldInvoiceData;
	}

	public ArrayList<SalesReturnReportModel> getSalesReturnWithoutInvoiceDetailReport(String TriId) {
		ArrayList<SalesReturnReportModel> idlist = new ArrayList<SalesReturnReportModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			String curr_date = s.toString();
			Log.e("current date :", curr_date);
			SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");

			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,MRP,QTY,SALE_DATE from retail_str_sales_details_return "
							+ "  where " + " TRI_ID ='" + TriId + "' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					SalesReturnReportModel sm = new SalesReturnReportModel();
					sm.setProdNm(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NM)));
					sm.setBatch(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_NO)));
					sm.setMrp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_MRP)));
					sm.setExp(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_DATE)));
					sm.setQty(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_QTY)));
					sm.setSaleDate(cursor.getString(cursor.getColumnIndex(COLUMN_REPRT_SALE_RETURN_LAST)));
					idlist.add(sm);
				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		return idlist;
	}


	public void savecarddetail(String TRANS_ID, ArrayList<Sales> list, String username, String cardnumber, String cardbankname,String cardtype) {

		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));

		String st = PersistenceManager.getStoreId(mycontext);
			int i=login.setboolean();
		if (list == null) {
			return;
		}

		for (Sales sales : list) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("TRI_ID", TRANS_ID);
			contentValues.put("STORE_ID", st.substring(0,10));
			// contentValues.put("INVOICE_NO", INVOICE);
			contentValues.put("BATCH_NO", sales.getBatchNo());
			contentValues.put("POS_USER", username);
			contentValues.put("PROD_ID", sales.getProdid());

			contentValues.put("PROD_NM", sales.getProductName());
			contentValues.put("EXP_DATE", sales.getExpiry());
			contentValues.put("S_PRICE", sales.getSPrice());
			contentValues.put("M_FLAG", "I");
			//contentValues.put("EX_TRI_ID", imeino);
			contentValues.put("QTY", sales.getQuantity());
			contentValues.put("MRP", sales.getMrp());
			contentValues.put("UOM", sales.getUom());
			contentValues.put("SALE_DATE", getDate());
			contentValues.put("TOTAL", sales.getTotal());
			// contentValues.put("DISCOUNT_PERCENT", Discount);
			contentValues.put("LINE_DISC", sales.getDiscountsales());
			contentValues.put("CARD_NO", cardnumber);
			contentValues.put("BANK_NAME", cardbankname);
			contentValues.put("CARD_TYPE", cardtype);
			contentValues.put("S_FLAG", "0");
			if (i==0)
			{
				contentValues.put("SLAVE_FLAG","0");

			}else {
				contentValues.put("SLAVE_FLAG", "1");


				String Sales_ProductDetail = "insert into retail_str_sales_detail( TRI_ID , STORE_ID ,BATCH_NO , PROD_NM , EXP_DATE, POS_USER , S_FLAG , M_FLAG ,S_PRICE,QTY,MRP,UOM,SALE_DATE,TOTAL,LINE_DISC,CARD_NO,BANK_NAME,CARD_TYPE,SLAVE_FLAG) values (" + "'" + TRANS_ID + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + sales.getBatchNo() + "'," + "'" + sales.getProductName() + "'," + "'" + sales.getExpDate() + "'," + "'" + username + "'," + "'0'," + "'I'," + "'" + sales.getSPrice() + "'," + "'" + sales.getQuantity() + "'," + "'" + sales.getMrp() + "'," + "'" + sales.getUom() + "'," + "'" + getDate() + "'," + "'" + sales.getTotal() + "'," + "'" + sales.getDiscountsales() + "'," + "'" + cardnumber + "'," + "'" + cardbankname + "'," + "'" + cardtype +"'," + "'1')";
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", Sales_ProductDetail);
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}
			}
			db.insert("retail_str_sales_detail", null, contentValues);

		}
		if(i==3)
		{
			login.bluetoothdatadetails();
			updateSlaveFlagsalesdetailcard();
		}
		db.close(); // Closing database connection
		Log.e("Database Operation for", "row inserted...");
		return;
	}


	public boolean insertdetailsifpaybaycard(String TRANS_ID, String GrandTotal,String username,String cardnumber,String bankname,String cardGrandTotal,String CUSTNm,String cardDiscount) {

		SQLiteDatabase db = this.getWritableDatabase();
		PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
		String st = PersistenceManager.getStoreId(mycontext);
		int i=login.setboolean();
		ContentValues contentValues = new ContentValues();
		contentValues.put("TRI_ID", TRANS_ID);
		contentValues.put("STORE_ID", st.substring(0,10));
		//contentValues.put("INVOICE_NO", INVOICENO);
		contentValues.put("GRAND_TOTAL", GrandTotal);
		contentValues.put("POS_USER",username);
		contentValues.put("BUSINESS_DATE", getDate());
		contentValues.put("SALE_DATE", getDate());
		contentValues.put("SALE_TIME", getTime());
		contentValues.put("M_FLAG","I");
		contentValues.put("CARD_NO",cardnumber);
		contentValues.put("BANK_NAME",bankname);
		contentValues.put("S_Flag","0");
		contentValues.put("CUST_NAME",CUSTNm);
		contentValues.put("SAVETOTALBILLAMOUNT",cardGrandTotal);
		contentValues.put("SAVETOTALBILLDISCOUNT",cardDiscount);

		if (i==0)
		{
			contentValues.put("SLAVE_FLAG","0");

		}else{
			contentValues.put("SLAVE_FLAG","1");
			login.bluetoothdataMaster();
			updateSlaveFlagsalesMastercard();
			String Sales_ProductMaster = "insert into retail_str_sales_master( TRI_ID , STORE_ID ,GRAND_TOTAL, POS_USER , S_FLAG , M_FLAG , BUSINESS_DATE , SALE_TIME , SALE_DATE,SAVEFINALBILLAMOUNT,SLAVE_FLAG) values (" + "'" + TRANS_ID + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" + GrandTotal + "'," + "'" + username + "'," + "'0'," + "'I'," + "'" + getDate() + "'," + "'" + getTime()+ "','" + getDate() + "','" + cardGrandTotal + "','1')";
			try {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Query", Sales_ProductMaster);
				login.sendMessage(String.valueOf(jsonObject));
			}catch (Exception e){}
		}
		db.insert("retail_str_sales_master", null, contentValues);
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted Master...");
		return true;
	}

	public ArrayList<Sales> get_Transaction_Id_Bill(String i)
	{
		ArrayList<Sales> bill_reprint=new ArrayList<Sales>();

		try
		{
/*
         String[] params = new String[1];
         params[0] = reprint_Transaction_Id + "%";*/
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor res = db.rawQuery("Select * from retail_str_sales_detail where"
					+ " TRI_ID = '"+i+"' ", null);


			if(res.moveToFirst())
			{
				do {
					Sales reprint_Bill_Sales=new Sales();
					reprint_Bill_Sales.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
					reprint_Bill_Sales.setTrans_id(res.getString(res.getColumnIndex(TRANS_ID)));
					reprint_Bill_Sales.setBatchNo(res.getString(res.getColumnIndex(BATCH_NO)));
					reprint_Bill_Sales.setProductName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
					reprint_Bill_Sales.setExpiry(res.getString(res.getColumnIndex(EXPIRY)));
					reprint_Bill_Sales.setSPrice(res.getFloat(res.getColumnIndex(S_PRICE)));
					reprint_Bill_Sales.setQuantity(res.getInt(res.getColumnIndex(QUANTITY)));
					reprint_Bill_Sales.setMrp(res.getFloat(res.getColumnIndex(MRP)));
					///testting for rahul done changees make bill level discount to Discountamountsalestotal both having same value
					//reprint_Bill_Sales.setBilllevelDiscount(res.getFloat(res.getColumnIndex(DISCOUNT_PERCENT)));
					reprint_Bill_Sales.setDiscountsales(res.getFloat(res.getColumnIndex(LINESALEDISC)));
					if (reprint_Bill_Sales.getDiscountsales()== 0.0)
					{     reprint_Bill_Sales.setTotal(res.getFloat(res.getColumnIndex(TOTAL)));
					}
					else {
						reprint_Bill_Sales.setTotal(res.getFloat(res.getColumnIndex(TOTAL)-(res.getColumnIndex(TOTAL)*res.getColumnIndex(LINESALEDISC)/100)));
					}
					bill_reprint.add(reprint_Bill_Sales);

				}while (res.moveToNext());
			}

		}
		catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return bill_reprint;
	}
	public Sales get_Transaction_Id_Bill_Master(String i)

	{

		Sales reprint_Bill_Sales=null;

		try

		{

			SQLiteDatabase db = this.getReadableDatabase();

			Cursor res = db.rawQuery("Select * from retail_str_sales_master where" + " TRI_ID = '"+i+"' ", null);
			if(res.moveToFirst())
			{
				do {
					reprint_Bill_Sales=new Sales();
					reprint_Bill_Sales.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
					reprint_Bill_Sales.setTrans_id(res.getString(res.getColumnIndex(TRANS_ID)));
					reprint_Bill_Sales.setSaveTOTALBILLAMOUNT(res.getFloat(res.getColumnIndex(COLUMN_SAVETOTALBILLAMOUNT)));
					reprint_Bill_Sales.setSaveTOTALBIllDiscount(res.getFloat(res.getColumnIndex(COLUMN_SAVETOTALBILLDISCOUNT)));
					reprint_Bill_Sales.setSaveFINALBILLAMOUNT(res.getFloat(res.getColumnIndex(COLUMN_SAVEFINALBILLAMOUNT)));
					reprint_Bill_Sales.setSaveRECEIVEDBILLAMOUNT(res.getFloat(res.getColumnIndex(COLUMN_SAVERECEIVEDBILLAMOUNT)));
					reprint_Bill_Sales.setSaveEXPECTEDBILLAMOUNT(res.getFloat(res.getColumnIndex(COLUMN_SAVEEXPECTEDBILLAMOUNT)));

					reprint_Bill_Sales.setDate(res.getString(res.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					reprint_Bill_Sales.setCustomerName(res.getString(res.getColumnIndex(COLUMN_REPRT_CUSTOMER_NAME)));
					reprint_Bill_Sales.setSalesTime(res.getString(res.getColumnIndex(COLUMN_REPRT_SALE_TIME)));


//bill_reprint.add(reprint_Bill_Sales);
				}while (res.moveToNext());
			}
		}
		catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		return reprint_Bill_Sales;

	}

	public Sales get_Card_Transaction_Id_Bill_Master(String i)

	{

		Sales reprint_Bill_Sales=null;

		try {

			SQLiteDatabase db = this.getReadableDatabase();

			Cursor res = db.rawQuery("Select * from retail_str_sales_master where" + " TRI_ID = '"+i+"' ", null);

			if(res.moveToFirst())

			{

				do {

					reprint_Bill_Sales=new Sales();

					reprint_Bill_Sales.setCardHolderNumber(res.getString(res.getColumnIndex(COLUMN_REPRT_SALE_CARDNO)));

					reprint_Bill_Sales.setCardGrandTotal(res.getString(res.getColumnIndex(COLUMN_REPRT_GRAND_TOTAL)));

					reprint_Bill_Sales.setDate(res.getString(res.getColumnIndex(COLUMN_REPRT_SALE_SDATE)));
					reprint_Bill_Sales.setCustomerName(res.getString(res.getColumnIndex(COLUMN_REPRT_CUSTOMER_NAME)));
					reprint_Bill_Sales.setSalesTime(res.getString(res.getColumnIndex(COLUMN_REPRT_SALE_TIME)));
//bill_reprint.add(reprint_Bill_Sales);

				}while (res.moveToNext());

			}

		}

		catch (CursorIndexOutOfBoundsException e) {

			e.printStackTrace();

		} catch (IndexOutOfBoundsException e) {

			e.printStackTrace();

		} catch (NullPointerException e) {

			e.printStackTrace();

		}

		return reprint_Bill_Sales;

	}

   /*public ArrayList<Sales> get_Transaction_Id_Bill_Master(String i)
   {
      ArrayList<Sales> bill_reprint=new ArrayList<Sales>();

      try
      {
*//*
         String[] params = new String[1];
         params[0] = reprint_Transaction_Id + "%";*//*
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor res = db.rawQuery("Select * from retail_str_sales_master where"
               + " TRI_ID = '"+i+"' ", null);


         if(res.moveToFirst())
         {
            do {
               Sales reprint_Bill_Sales=new Sales();
               reprint_Bill_Sales.setSaveEXPECTEDBILLAMOUNT();
               reprint_Bill_Sales.setSaveTOTALBIllDiscount();
               reprint_Bill_Sales.setSaveFINALBILLAMOUNT();
               reprint_Bill_Sales.setSaveRECEIVEDBILLAMOUNT();
               reprint_Bill_Sales.setSaveTOTALBILLAMOUNT();
               bill_reprint.add(reprint_Bill_Sales);

            }while (res.moveToNext());
         }

      }
      catch (CursorIndexOutOfBoundsException e) {
         e.printStackTrace();
      } catch (IndexOutOfBoundsException e) {
         e.printStackTrace();
      } catch (NullPointerException e) {
         e.printStackTrace();
      }

      return bill_reprint;
   }*/

	/*reprint_Bill_Sales.setStoreid(res.getString(res.getColumnIndex(STORE_ID)));
   reprint_Bill_Sales.setTrans_id(res.getString(res.getColumnIndex(TRANS_ID)));
   reprint_Bill_Sales.setBatchNo(res.getString(res.getColumnIndex(BATCH_NO)));
   reprint_Bill_Sales.setProductName(res.getString(res.getColumnIndex(PRODUCT_NAME)));
   reprint_Bill_Sales.setExpiry(res.getString(res.getColumnIndex(EXPIRY)));
   reprint_Bill_Sales.setSPrice(res.getFloat(res.getColumnIndex(S_PRICE)));
   reprint_Bill_Sales.setQuantity(res.getInt(res.getColumnIndex(QUANTITY)));
   reprint_Bill_Sales.setMrp(res.getFloat(res.getColumnIndex(MRP)));
   ///testting for rahul done changees make bill level discount to Discountamountsalestotal both having same value
   reprint_Bill_Sales.setBilllevelDiscount(res.getFloat(res.getColumnIndex(DISCOUNT_PERCENT)));
   reprint_Bill_Sales.setDiscountsales(res.getFloat(res.getColumnIndex(LINESALEDISC)));
   if (reprint_Bill_Sales.getDiscountsales()== 0.0)
   {     reprint_Bill_Sales.setTotal(res.getFloat(res.getColumnIndex(TOTAL)));
   }
   else {
      reprint_Bill_Sales.setTotal(res.getFloat(res.getColumnIndex(TOTAL)-(res.getColumnIndex(TOTAL)*res.getColumnIndex(LINESALEDISC)/100)));
   }*/
	public ArrayList<PurchaseProductModel> getAllProductForPurchase(String idorName) {
		ArrayList<PurchaseProductModel> productlist = new ArrayList<PurchaseProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[2];
      params[0] = idorName + "%";
      params[1] = idorName + "%";*/

			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,SELLING_ORDER_UNIT,MRP,PROFIT_MARGIN,S_PRICE,BARCODE,ACTIVE,CONV_FACT,IND_NM,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " DSTR_NM  ='"+idorName+"'  "
					, null);
			if (productcursor.moveToFirst()) {
				do {
					PurchaseProductModel pm = new PurchaseProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					// pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSellingPrice(productcursor.getString(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setMRP(productcursor.getString(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setProfit_Margin(productcursor.getString(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversionfactor(productcursor.getString(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndusteryname(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setPurchasediscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));

					//pm.setGetConMulQty(productcursor.getFloat(productcursor.getColumnIndex(TOTALQTY)));

					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public ArrayList<Inventoryproductmodel> getProductdataforwithoutInventoryforDistri(String idorName) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[3];
      params[0] = idorName + "%";
      params[1] = idorName + "%";
      params[2] = idorName + "%";*/

			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,P_PRICE,S_PRICE,SELLING_ORDER_UNIT,PROFIT_MARGIN, MRP ,S_PRICE, BARCODE,CONV_FACT,IND_NM,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " DSTR_NM='"+idorName+"'  AND ACTIVE like 'Y%' or BARCODE like  ?  "
					, null);

			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

	public void updatesalestock(String PRODUCTPRODUCTID) {

		SQLiteDatabase db = this.getWritableDatabase();
		boolean checkid = false;
		ContentValues contentValues = new ContentValues();
		checkid = checkprodid(PRODUCTPRODUCTID);

		if (checkid == false) {
			return;
		}
		else {
			String qty = getparticulaarquantityforsale(PRODUCTPRODUCTID);

			if (qty.equals("0.0") || qty.equals("0"))

			{
				contentValues.put("ACTIVE", "N");
				db.update("retail_str_stock_master", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});

			}
		}

	}


	public String getparticulaarquantityforsale(String prodid) {
		String getQty = null;
		SQLiteDatabase db = this.getReadableDatabase();

		String Query = ("select PROD_ID, CON_MUL_QTY from retail_str_stock_master where " + "PROD_ID = '" + prodid + "'");
		Cursor cursor = db.rawQuery(Query, null);
		if (cursor.moveToFirst()) {
			getQty = cursor.getString(cursor.getColumnIndex(TOTALQTY));
		}
		return getQty;
	}



	public boolean checkprodid(String prodid) {


		SQLiteDatabase db = this.getReadableDatabase();

		String Query = ("select PROD_ID from retail_str_stock_master where " + "PROD_ID = '" + prodid + "'");
		Cursor cursor = db.rawQuery(Query, null);

		if (cursor.getCount() <= 0) {
			cursor.close();
			return false;
		}
		return true;
	}
	public void updatesalestockActive(String PRODUCTPRODUCTID) {
		boolean checkid = false;
		checkid = checkprodid(PRODUCTPRODUCTID);

		if (checkid == false) {
			return;
		} else {

			SQLiteDatabase db = this.getWritableDatabase();
			ContentValues contentValues = new ContentValues();

			contentValues.put("ACTIVE", "Y");
			db.update("retail_str_stock_master", contentValues, "PROD_ID = ? ", new String[]{String.valueOf(PRODUCTPRODUCTID)});

		}
	}

	public ArrayList<InventoryStockadjustmentmodel>getProductStockAdjustmentForDistri(String  name){
		ArrayList<InventoryStockadjustmentmodel> productlist = new ArrayList<InventoryStockadjustmentmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
      /*String[] params = new String[2];
      params[0] = name + "%";
      params[1] = name + "%";*/
			Cursor cursor = db.rawQuery("select PROD_NM,BATCH_NO,EXP_DATE,CONV_MRP,CONV_SPRICE,P_PRICE,CON_MUL_QTY,PURCHASE_UNIT_CONV from retail_str_stock_master where "
					+ " VEND_NM ='"+name+"' ", null);

			if (cursor.moveToFirst()) {
				do {
					InventoryStockadjustmentmodel pm = new InventoryStockadjustmentmodel();
					pm.setExpdate(cursor.getString(cursor.getColumnIndex(EXPIRY)));
					pm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(cursor.getFloat(cursor.getColumnIndex(PRODUCTPURCHASE))/(cursor.getInt(cursor.getColumnIndex(PURCHASEUNITCONV))));
					pm.setSprice(cursor.getFloat(cursor.getColumnIndex(CONVSPRICE)));
					pm.setConvMrp(cursor.getFloat(cursor.getColumnIndex(CONVMRP)));
					pm.setBatchno(cursor.getString(cursor.getColumnIndex(BATCH_NO)));
					pm.setProductQuantity(cursor.getInt(cursor.getColumnIndex(TOTALQTY)));
					pm.setPurchseunitconv(cursor.getInt(cursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (cursor.moveToNext());

			}

		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		}

		return productlist;
	}




	public ArrayList<InventoryTotalProductModel> getProductdataforwithoutInventoryforDistriTotal(String idorName) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		try {

			SQLiteDatabase db = this.getReadableDatabase();
			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,S_PRICE,SELLING_ORDER_UNIT,PROFIT_MARGIN,P_PRICE,MRP ,S_PRICE, BARCODE,CONV_FACT,IND_NM,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " DSTR_NM='"+idorName+"'  AND ACTIVE like 'Y%' or BARCODE like  ?  "
					, null);
			//P_PRICE,
			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));

				//	pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public ArrayList<InventoryTotalProductModel> getProductdataforwithoutInventoryTotal(String idorName) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		//try {

			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[3];
			params[0] = idorName + "%";
			params[1] = idorName + "%";
			params[2] = idorName + "%";

			Cursor productcursor = db.rawQuery("select distinct PROD_ID,PROD_NM,S_PRICE,SELLING_ORDER_UNIT,PROFIT_MARGIN, MRP,P_PRICE ,S_PRICE, BARCODE,CONV_FACT,IND_NM,PURCHASE_UNIT_CONV from retail_store_prod_com where "
							+ " PROD_ID  like ? or  PROD_NM  like ? AND ACTIVE like 'Y%'  or BARCODE like  ?  "
					, params);

			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(PRODUCTPRODUCTID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCTNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTPURCHASE))/productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					pm.setTax(productcursor.getString(productcursor.getColumnIndex(PRODUCTMEASUREUNITINV)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTSELLING)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMRP)));
					pm.setBarcode(productcursor.getString(productcursor.getColumnIndex(PRODUCTBARCODE)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					//pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));
					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		/*} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}*/
		return productlist;
	}


	public void saveInventoryholdbillTotal(ArrayList<InventoryTotalProductModel> list, String VendorName, String grnId,String VendorNo,String VendorDate) {

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (InventoryTotalProductModel prod : list) {


				ContentValues values = new ContentValues();

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
				// float holdqty=prod.getProductQuantity()-prod.getFreequantity();
				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckholdbatchnoAlreadyInDBorNotwithoutpo(prod.getBatchno());
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("VEND_NM", VendorName);
					values.put("BATCH_NO", getSystemCurrentTime());
					values.put("MFG_BATCH_NO", prod.getBatchno());

					values.put("EXP_DATE", prod.getExpdate());
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getPprice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("AMOUNT", prod.getTotal());
					values.put("UOM", prod.getTax());
					values.put("BARCODE", "NA");
					values.put("FLAG", "HT");
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("CONV_FACT", prod.getConvfact());
					values.put("FREE_GOODS", prod.getFreequantity());
					values.put("M_FLAG","I");
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("DISCOUNT_PERCENT",prod.getInvdiscount());
					values.put("S_FLAG","0");
					// Inserting Row


					db.insert("retail_str_stock_master_hold", null, values);


					String insert_saveInventoryholdbillwithoutpo = "insert into retail_str_stock_master_hold ( GRN_ID ,  VEND_NM ,  BATCH_NO , MFG_BATCH_NO , EXP_DATE  , STORE_ID , PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN , QTY, MRP ,S_PRICE , AMOUNT , UOM , BARCODE , FLAG , CON_MUL_QTY , CONV_FACT , FREE_GOODS , M_FLAG  ,  VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , DISCOUNT_PERCENT,S_FLAG ) values (" + "'" + grnId + "' ," + "'" + VendorName + "'," + "'" + getSystemCurrentTime() + "'," + "'" + prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + PersistenceManager.getStoreId(mycontext) + "'," + "'" +prod.getProductId() + "'," + "'" + prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin() + "'," + "'" + prod.getProductQuantity() + "'," + "'" +
							prod.getMrp() + "'," + "'" + prod.getSprice() + "'," + "'"  +  prod.getTotal() + "'," + "'" +  prod.getTax() + "'," + "'"  + "NA',"  + "H ',"  + prod.getTotalqty() + "'," + "'" + prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'" + "I ',"  + VendorNo + "'," + "'" + VendorDate + "'," + "'" + prod.getInvdiscount()  +  "'," + "'" + "I'" + "'," + "'" + "0')";

					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",insert_saveInventoryholdbillwithoutpo);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}

				} else {

					String batchqty = getparticularholdbatchqtywithoutpo(prod.getBatchno(), prod.getProductId());
					values.put("GRN_ID", grnId);
					int prodQuantity = prod.productQuantity;
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("FLAG", "H");
					values.put("M_FLAG","U");
					values.put("QTY", prod.productQuantity);

					int sqlUpdateRetval = db.update("retail_str_stock_master_hold", values, "BATCH_NO = ?  and " + "PROD_ID " +
							" = ? ", new String[]{prod.getBatchno(), prod.getProductId()});

					String update_sqlUpdateRetval = "UPDATE retail_str_stock_master_hold SET GRN_ID = "+ "'" + grnId + "'" + " ,FLAG = 'H' " + ", M_FLAG = 'U' " +  " , QTY = " + "'" + prod.productQuantity + "'" +  " WHERE BATCH_NO = " + "'" + prod.getBatchno() + "'" +" and " + "PROD_ID = " + "'" + prod.getProductId() + "'";

					try {
						JSONObject jsonobject = new JSONObject();
						jsonobject.put("Query",update_sqlUpdateRetval);
						login logi = new login();
						login.sendMessage(String.valueOf(jsonobject));
					}catch ( Exception e){}

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());

					if (sqlUpdateRetval < 1) {
						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}
	public void saveInventorytotal(ArrayList<InventoryTotalProductModel> list, String VendorName, String grnId
			,String username,String VendorNo,String VendorDate) {
		// android.database.sqlite.SQLiteDatabase db = this.getWritableDatabase();
		int i = login.setboolean();
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			boolean returnval = true;
			boolean fortrue = false;
			if (list == null) {
				return;
			}
			for (InventoryTotalProductModel prod : list) {
				ContentValues values = new ContentValues();
				Long value = System.currentTimeMillis();
				String Batchcurrent = Long.toString(value);

				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				Log.e("Prodlength", String.valueOf(list.size()));
				float totalinventoryqty=prod.productQuantity+prod.getFreequantity();

				float mrp = prod.getMrp();
				float sprice=prod.getSprice();
				float conversin=prod.getConvfact();
				float newmrp = mrp / conversin;
				if (newmrp < 0) {
					newmrp = 0;
				}
				float newsprice = sprice / conversin;
				if (newsprice < 0) {
					newsprice = 0;
				}

				// for(int Batch=0;Batch<list.size();Batch++) {
				fortrue = CheckbatchnoAlreadyInDBorNotwithoutpo(prod.getExpdate(),prod.getProductId(),String.valueOf(prod.getMrp()));
				if (fortrue == false) {
					values.put("GRN_ID", grnId);
					values.put("VEND_NM", VendorName);

					String s = prod.getBatchno();
					if(s.matches(" ")){
						values.put("BATCH_NO", Batchcurrent);
					}
					else {
						values.put("BATCH_NO", s);
					}

					values.put("MFG_BATCH_NO", prod.getBatchno());
					values.put("EXP_DATE", prod.getExpdate());
					values.put("POS_USER",username);
					values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
					values.put("PROD_ID", prod.getProductId());
					values.put("PROD_NM", prod.getProductName());
					values.put("P_PRICE", prod.getPprice());
					values.put("PROFIT_MARGIN", prod.getProductmargin());
					values.put("QTY", prod.getProductQuantity());
					values.put("MRP", prod.getMrp());
					values.put("S_PRICE", prod.getSprice());
					values.put("UOM", prod.getTax());
					values.put("BARCODE", "NA");
					values.put("CONV_FACT", prod.getConvfact());
					values.put("FREE_GOODS", prod.getFreequantity());
					values.put("CON_MUL_QTY",prod.getTotalqty());
					values.put("PREV_QTY",totalinventoryqty);
					values.put("CONV_MRP",newmrp);
					values.put("CONV_SPRICE",newsprice);
					values.put("M_FLAG","I");
					values.put("INVENTORY_DATE",getDate());
					values.put("VENDOR_INVOICE_NO",VendorNo);
					values.put("VENDOR_INVOICE_DATE",VendorDate);
					values.put("PURCHASE_UNIT_CONV","1");
					values.put("DISCOUNT_PERCENT",prod.getProductmargin());
					values.put("S_FLAG","0");
					if (prod.getProductmargin()>=0.0 ||prod.getProductmargin()>=0.00) {
						values.put("AMOUNT", (prod.getPprice()*prod.getProductQuantity()-(prod.getPprice()*prod.getProductQuantity()*prod.getProductmargin())/100));
					}else{
						values.put("AMOUNT",prod.getTotal());
					}
					// Inserting Row

					db.insert("retail_str_stock_master", null, values);
					if (i==0)
					{
						values.put("SLAVE_FLAG","0");

					}else {
						values.put("SLAVE_FLAG", "1");


						String insert_saveInventorywithoutpo = "insert into retail_str_stock_master " +
								"( GRN_ID , VEND_NM , BATCH_NO , MFG_BATCH_NO , EXP_DATE , POS_USER , STORE_ID ," +
								" PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
								"BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE , " +
								"VENDOR_INVOICE_NO , VENDOR_INVOICE_DATE , PURCHASE_UNIT_CONV , DISCOUNT_PERCENT)" +
								" values (" + "'" + grnId + "' ," + "'" + VendorName + "' ," + "'" + Batchcurrent + "'," + "'"
								+ prod.getBatchno() + "'," + "'" + prod.getExpdate() + "'," + "'" + username + "'," + "'"
								+ PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductId() + "'," + "'"
								+ prod.getProductName() + "'," + "'" + prod.getPprice() + "'," + "'" + prod.getProductmargin()
								+ "'," + "'" + prod.getProductQuantity() + "'," + "'" + prod.getMrp() + "'," + "'" +
								prod.getSprice() + "'," + "'" + prod.getTotal() + "'," + "'" + prod.getTax() + "'," + "'NA',"
								+ "'" + prod.getConvfact() + "'," + "'" + prod.getFreequantity() + "'," + "'" + prod.getTotalqty()
								+ "'," + "'" + totalinventoryqty + "','" + newmrp + "'," + "'"
								+ newsprice + "'," + "'I'," + "'" + getDate() + "'," + "'" + VendorNo + "'," + "'" + VendorDate + "',"
								+ "'" + prod.getPurchaseunitconv() + "'," + "'" + prod.getInvdiscount() + "','" + 0 + "')";
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("Query", insert_saveInventorywithoutpo);
							login logi = new login();
							login.sendMessage(String.valueOf(jsonObject));
						} catch (Exception e) {
						}
					}
				} else {

					String batchqty = getparticularbatchqtywithoutpo(prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp()));
					values.put("GRN_ID", grnId);
					values.put("INVENTORY_DATE",getDate());
					values.put("M_FLAG","U");
					float prodQuantity = prod.getTotalqty();
					float newStockQuantity = Float.parseFloat(batchqty) + prodQuantity;
					if (newStockQuantity < 0) {
						newStockQuantity = 0;
					}
					values.put("CON_MUL_QTY", Double.toString(newStockQuantity));

					int sqlUpdateRetval = db.update("retail_str_stock_master", values, "EXP_DATE = ?  and " + "PROD_ID " +
							" = ? and " + "MRP " +" = ? ", new String[]{prod.getExpdate(), prod.getProductId(),String.valueOf(prod.getMrp())});

					Log.d("Sudhee", "Update done for batch:" + prod.getBatchno() + ", prodid:" + prod.getProductId());


					if (i==0)
					{
						values.put("SLAVE_FLAG","0");

					}else {
						values.put("SLAVE_FLAG", "1");

						String update_inventory = "UPDATE retail_str_stock_master SET GRN_ID = " + "'" + grnId + "'" + " ,INVENTORY_DATE = " + "'" + getDate() + "'" + " ,M_FLAG = 'U' " + " , CON_MUL_QTY = " + "'" + Double.toString(newStockQuantity) + "'" + " WHERE EXP_DATE = " + "'" + prod.getExpdate() + "'" + " and " + "PROD_ID = " + "'" + prod.getProductId() + "'" + " and " + "MRP = " + "'" + String.valueOf(prod.getMrp()) + "'";
						try {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("Query", update_inventory);
							login logi = new login();
							login.sendMessage(String.valueOf(jsonObject));
						} catch (Exception e) {
						}

					}
					if(i==3)
					{
						login.bluetoothDataForUpdateInventoryDiscountData();
						updateSlaveFlagForInventoryByTotalUpdateDiscount();
					}
					if (sqlUpdateRetval < 1) {

						Log.e("Update fail", "returned: " + sqlUpdateRetval);
						returnval = false;
					}
					//return;
				}
				//}
			}
			db.close(); // Closing database connection
			Log.e("Database Operation", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();

		}
	}











   /*ArrayList<Inventorymodel> getInventoryName(String name) {
      ArrayList<Inventorymodel> vendorlist = new ArrayList<Inventorymodel>();
      try {
         SQLiteDatabase db = this.getReadableDatabase();
         String[] params = new String[1];
         params[0] = name + "%";
         Cursor cursor = db.rawQuery("select VEND_DSTR_NM from retail_vend_dstr  where"
                     + " VEND_DSTR_NM like ?  AND ACTIVE like 'Y%'"
               , params);

         if (cursor.moveToFirst()) {
            do {
               Inventorymodel pm = new Inventorymodel(name);
               //pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_STOREID)));

               pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_VEND_DSTR_NAME)));
               vendorlist.add(pm);

            } while (cursor.moveToNext());
         }

      } catch (IndexOutOfBoundsException ex) {
         ex.printStackTrace();
      }

      return vendorlist;
   }*/

	//for hold inventory


	public ArrayList<Inventoryproductmodel> getholddataforinventoryinventory(String PO) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			/*String[] params = new String[1];
			params[0] = PO + "%";*/

			Cursor productcursor = db.rawQuery(" select PO_NO,GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,FREE_GOODS,DISCOUNT_PERCENT  from retail_str_stock_master_hold where "
							+ " GRN_ID ='"+PO+"' "
					, null);



			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();



					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatch_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExp_Date(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


//vendor search inventory


	public ArrayList<Inventoryproductmodel> getvendorsearchinventory() {

		ArrayList<Inventoryproductmodel> vendorNamelist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}

//getdatapurchaseinventory


	public ArrayList<Inventoryproductmodel> getPurchaseProductdatainventory(String PO) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PO_NO, PROD_ID, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, TOTAL,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM ,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_str_po_detail where "
							+ " PO_NO  like ? "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_TOTAL)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

//In Dbhelper

	public ArrayList<Inventoryproductmodel> getdataforintegrationinventory(String PO) {
		ArrayList<Inventoryproductmodel> productlist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PO_NO,GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,FREE_GOODS ,DISCOUNT_PERCENT from retail_str_stock_master_hold where "
							+ " GRN_ID like ? "
					, params);



			if (productcursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductPrice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatch_No(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExp_Date(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUom(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConversion(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustery(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvpodiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}



//nameinventory

	public ArrayList<Inventoryproductmodel> getdocumentnameinventory() {

		ArrayList<Inventoryproductmodel> vendorNamelist = new ArrayList<Inventoryproductmodel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
       /*  String []params=new String[1];
         params[0]=name+"%";
		*/	Cursor cursor = db.rawQuery("select distinct VEND_DSTR_NM from retail_vend_dstr  where"
					+ "  ACTIVE like 'Y%'", null);
			if (cursor.moveToFirst()) {
				do {
					Inventoryproductmodel pm = new Inventoryproductmodel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_DSTR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}
	////////////////////////////////////////////////////////////////////////////////////

/*

	public ArrayList<InventoryTotalProductModel> getholddataforinventoryTotalinventory(String PO) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         */
/*String[] params = new String[1];
         params[0] = PO + "%";*//*


			Cursor productcursor = db.rawQuery(" select GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,DISCOUNT_PERCENT  from retail_str_stock_master_hold where "
							+ " GRN_ID ='"+PO+"' "
					, null);



			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();


					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setBatchno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExpdate(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setUOM(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					//pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));


					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}
*/




	public ArrayList<InventoryTotalProductModel> getvendorsearchTotalinventory() {

		ArrayList<InventoryTotalProductModel> vendorNamelist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
         /*String []params=new String[1];
         params[0]=name+"%";*/
			Cursor cursor = db.rawQuery("select distinct VENDOR_NM from retail_str_po_detail", null);
			if (cursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}


	//getdatapurchaseinventory


	public ArrayList<InventoryTotalProductModel> getPurchaseProductdataTotalinventory(String PO) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select PROD_ID, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, TOTAL,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM ,DISCOUNT_PERCENT,PURCHASE_UNIT_CONV from retail_str_po_detail where "
							+ " PO_NO  like ? "
					, params);
			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));

					//pm.setPurchaseno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PO_NO)));
					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_TOTAL)));
					pm.setUOM(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));
					pm.setPurchaseunitconv(productcursor.getFloat(productcursor.getColumnIndex(PURCHASEUNITCONV)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}

//nameinventory

	public ArrayList<InventoryTotalProductModel> getdocumentnameTotalinventory() {

		ArrayList<InventoryTotalProductModel> vendorNamelist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
       /*  String []params=new String[1];
         params[0]=name+"%";
      */ Cursor cursor = db.rawQuery("select distinct VEND_DSTR_NM from retail_vend_dstr  where"
					+ "  ACTIVE like 'Y%'", null);
			if (cursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					pm.setVendorName(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_VENDOR_DSTR_NAME)));
					vendorNamelist.add(pm);

				} while (cursor.moveToNext());
			}


		} catch (CursorIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return vendorNamelist;
	}



//In Dbhelper

	public ArrayList<InventoryTotalProductModel> getdataforintegrationTotalinventory(String PO) {
		ArrayList<InventoryTotalProductModel> productlist = new ArrayList<InventoryTotalProductModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			String[] params = new String[1];
			params[0] = PO + "%";

			Cursor productcursor = db.rawQuery(" select GRN_ID,VEND_NM, PROD_ID,MFG_BATCH_NO,EXP_DATE, MRP,S_PRICE ,PROD_NM ,P_PRICE, QTY, AMOUNT,PROFIT_MARGIN, UOM,CONV_FACT,IND_NM,DISCOUNT_PERCENT from retail_str_stock_master_hold where "
							+ " GRN_ID like ? "
					, params);



			if (productcursor.moveToFirst()) {
				do {
					InventoryTotalProductModel pm = new InventoryTotalProductModel();
					//  pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_VENDOR)));


					pm.setProductId(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_ID)));
					pm.setVendorName(productcursor.getString(productcursor.getColumnIndex(LOCALVENDORNAME)));
					pm.setProductName(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_PROD_NAME)));
					pm.setPprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_PPRICE)));
					pm.setProductQuantity(productcursor.getInt(productcursor.getColumnIndex(COLUMN_DETAIL_QTY)));
					pm.setBatchno(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_MFGBATCHNO)));
					pm.setExpdate(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_EXPDATE)));
					pm.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_AMOUNT)));
					pm.setUOM(productcursor.getString(productcursor.getColumnIndex(COLUMN_DETAIL_UOM)));
					pm.setMrp(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_MRP)));
					pm.setSprice(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_DETAIL_SPRICE)));
					pm.setProductmargin(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTMARGIN)));
					pm.setConvfact(productcursor.getFloat(productcursor.getColumnIndex(PRODUCTCONVERSION)));
					pm.setIndustry(productcursor.getString(productcursor.getColumnIndex(PRODUCTINDUSTERY)));
					// pm.setDiscountitems(productcursor.getInt(productcursor.getColumnIndex(PRODUCTFREEGOODS)));
					pm.setInvdiscount(productcursor.getInt(productcursor.getColumnIndex(DISCOUNT_PERCENT)));



					productlist.add(pm);
				} while (productcursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException cur) {
			cur.printStackTrace();
		}
		return productlist;
	}


	public Cursor getFinancialLossReport() {
		SQLiteDatabase db = this.getReadableDatabase();
		Date date = new Date();
		final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
		String curr_date = s.toString();
		Log.e("current date :", curr_date);
		Cursor cursor = db.rawQuery("SELECT SUM( " + P_PRICE + " * " + PRODUCTSTOCK + " ) AS TotalFinancialLoss FROM retail_str_stock_master where EXP_DATE < '"+curr_date+"' and QTY!=0 and  ACTIVE IS NULL OR  ACTIVE = 'Y' ", null);
		/*if (cursor.moveToFirst()) {
			Log.e("TotalFinancialLoss:", cursor.getDouble(0) + "");
		}*/
		return cursor ;
	}

	public ArrayList<ExpiryProductStockModel> getExpiredProductReport() {
		ArrayList<ExpiryProductStockModel> idlist = new ArrayList<ExpiryProductStockModel>();
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy/MM/dd", date.getTime());
			String curr_date = s.toString();
			Cursor cursor = db.rawQuery("select * from retail_str_stock_master where CON_MUL_QTY!=0 and CON_MUL_QTY!=0.0 and EXP_DATE < '"+curr_date+"' and ACTIVE IS NULL OR ACTIVE = 'Y' "
					, null);
			if (cursor.moveToFirst()) {
				do {
					ExpiryProductStockModel sm = new ExpiryProductStockModel();
					sm.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCTNAME)));
					sm.setBatchno(cursor.getString(cursor.getColumnIndex(PRODUCTBATCHINV)));
					sm.setExpdate(cursor.getString(cursor.getColumnIndex(PRODUCTEXPDATEINV)));
					sm.setPprice(cursor.getFloat(cursor.getColumnIndex(P_PRICE)));
					sm.setStockQty(cursor.getDouble(cursor.getColumnIndex(PRODUCTSTOCK)));

					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
					Date date1 = new Date();
					final CharSequence s1 = DateFormat.format("yyyy/MM/dd", date1.getTime());

					String curr_date1 = s1.toString();
					Date currDate = dateFormat1.parse(curr_date1);

					String mydate = sm.getExpdate();
					Date expDate = dateFormat1.parse(mydate);

					Double QTY = sm.getStockQty();
					//double QTY = Double.parseDouble(qty);

					if (expDate.before(currDate) && QTY!=0 && QTY!=0.0) {
						idlist.add(sm);
					}

				} while (cursor.moveToNext());
			}
		} catch (IndexOutOfBoundsException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return idlist;
	}

	public void DeleteAllExpiroedProduct(ArrayList<ExpiryProductStockModel> list) {
		SQLiteDatabase db = this.getWritableDatabase();
		boolean returnval=false;
		if (list == null) {
			return;
		}
		for (ExpiryProductStockModel prod : list) {
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);

			ContentValues values = new ContentValues();
			Log.e("PRODLENGTH", String.valueOf(list.size()));

			for(int Batch=0;Batch<list.size();Batch++) {
				values.put("ACTIVE", "N");
				int sqlUpdateRetval = db.update("retail_str_stock_master", values, "BATCH_NO = ?  and " + "PROD_NM " +
						" = ? and " + "EXP_DATE " + " = ? ", new String[]{prod.getBatchno(), prod.getProductName(), prod.getExpdate()});

				Log.d("DELETE.", "Update done for ExpiredProduct:" + prod.getProductName());

				if (sqlUpdateRetval < 1) {
					Log.e("Update fail", "returned: " + sqlUpdateRetval);
					returnval = false;
				}

				String updateStockAdjustment = "UPDATE retail_store_prod_com SET BATCH_NO = "
						+ "'" + prod.getBatchno() + "'" + ",P_PRICE = " + "'" + prod.getPprice() + "'"
						+ ",CON_MUL_QTY = " + "'" + prod.getStockQty()
						+ "'" + ",EXP_DATE = " + "'" + prod.getExpdate() + "'" + " WHERE PROD_NM= " + "'" + prod.getProductName();
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query", updateStockAdjustment);
					login logi = new login();
					login.sendMessage(String.valueOf(jsonObject));
				} catch (Exception e) {
				}

			}

		}
		db.close(); // Closing database connection
		Log.e("Database Operation", "row inserted...");
		return;

	}


	public void DirectlyAddProductInSalesForLocalprod(ArrayList<LocalProduct> list, String grnId,String username) {
		SQLiteDatabase db = this.getWritableDatabase();
		int i=setboolean();
		try {

			for (LocalProduct prod : list) {
				PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
				PersistenceManager.getStoreId(mycontext);

				ContentValues values = new ContentValues();
				Log.e("Prodlength", String.valueOf(list.size()));

					float totalinventoryqty = Float.parseFloat(prod.getQuantity());
					float mrp = Float.parseFloat(prod.getMRP());
					float sprice =Float.parseFloat( prod.getSellingPrice());
					float conversin =Float.parseFloat( prod.getConvFact());
					float newmrp = mrp / conversin;
					if (newmrp < 0) {
						newmrp = 0;
					}
					float newsprice = sprice / conversin;
					if (newsprice < 0) {
						newsprice = 0;
					}
						values.put("GRN_ID", grnId);
						values.put("BATCH_NO", prod.getBatchNo());
						values.put("EXP_DATE", prod.getExpDate());
						values.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
						values.put("PROD_ID", prod.getProductid());
						values.put("PROD_NM", prod.getProductname());
						values.put("P_PRICE", prod.getPurchasePrice());
						values.put("PROFIT_MARGIN", prod.getMargin());
						values.put("QTY", prod.getQuantity());
						values.put("MRP", prod.getMRP());
						values.put("S_PRICE", prod.getSellingPrice());
						values.put("AMOUNT", Float.parseFloat(prod.getPurchasePrice())*Float.parseFloat(prod.getQuantity()));
						values.put("UOM", prod.getUom());
						values.put("BARCODE", "NA");
						values.put("CONV_FACT", prod.getConvFact());
						values.put("INVENTORY_DATE", getDate());
						values.put("CON_MUL_QTY", Float.parseFloat(prod.getConvFact()) * Float.parseFloat(prod.getQuantity()));
						values.put("CONV_MRP", newmrp);
						values.put("CONV_SPRICE", newsprice);
						values.put("PREV_QTY", totalinventoryqty);
				        values.put("DISCOUNT_PERCENT",prod.getDiscount());
						values.put("POS_USER",username);
						values.put("M_FLAG","I");
						values.put("S_FLAG","0");
						values.put("ACTIVE","Y");

						// Inserting Row
				if (i==0)
				{
					values.put("SLAVE_FLAG","0");
				}
					else{
					values.put("SLAVE_FLAG","1");
				String insert_LocalProductDirectlytostock = "insert into retail_str_stock_master " +
						"( GRN_ID , VEND_NM , BATCH_NO  , EXP_DATE , POS_USER , STORE_ID ," +
						" PROD_ID, PROD_NM , P_PRICE, PROFIT_MARGIN,  QTY, MRP ,S_PRICE , AMOUNT , UOM , " +
						"BARCODE , CONV_FACT , FREE_GOODS, CON_MUL_QTY , PREV_QTY , CONV_MRP , CONV_SPRICE , M_FLAG , INVENTORY_DATE ,"+
							"PURCHASE_UNIT_CONV , DISCOUNT_PERCENT,S_FLAG,ACTIVE,SLAVE_FLAG)" +
						" values (" + "'" + grnId + "' ," + "''," + "'"+ prod.getBatchNo() + "'," + "'" + prod.getExpDate() + "'," + "'"+username+"'," + "'"
						+ PersistenceManager.getStoreId(mycontext) + "'," + "'" + prod.getProductid() + "'," + "'"
						+ prod.getProductname() + "'," + "'" + prod.getPurchasePrice() + "'," + "'" + prod.getMargin()
						+ "'," + "'" + prod.getQuantity() + "'," + "'" + prod.getMRP() + "'," + "'" +
						prod.getSellingPrice() + "'," + "'" + Float.parseFloat(prod.getPurchasePrice())*Float.parseFloat(prod.getQuantity())
						+ "'," + "'"  +  prod.getUom() + "'," + "'NA',"
						+ "'" +   prod.getConvFact() + "'," + "'0.0'," + "'"  + Float.parseFloat(prod.getConvFact()) * Float.parseFloat(prod.getQuantity())
						+ "'," + "'" + totalinventoryqty + "','"+ newmrp + "'," + "'"
						+ newsprice + "'," + "'I'," + "'"  + getDate() + "',"
						+ "'1'," + "'" + prod.getDiscount() + "'," + "'0'," + "'Y'," + "'1')";
				try {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("Query",insert_LocalProductDirectlytostock);
					login.sendMessage(String.valueOf(jsonObject));
				}catch (Exception e){}
				}
				db.insert("retail_str_stock_master", null, values);
			}
			db.close(); // Closing database connection
			Log.e("DB stockmaster", "row inserted...");
			return;

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		}
	}

	public boolean updateflagforreturn(String TRANSID) {


		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();

		contentValues.put("TRI_ID",TRANSID);
		contentValues.put("FLAG","1");
		db.update("retail_str_sales_detail", contentValues, "TRI_ID = ? ", new String[]{String.valueOf(TRANSID)});
	//	String update_employees = "UPDATE retail_employees SET FIRST_NAME = "+ "'" + FIRSTNAME + "'" + " ,LAST_NAME = " + "'" + LASTNAME + "'" + " ,POS_USER = " + "'" + username + "'" + " , PASSWORD = " + "'" + PASSWORD + "'" + ", CONFIRM_PASSWORD = " + "'" + CONFIRMPASSWORD + "'" + ",ACTIVE = " + "'" + ACTIVE + "'" + ",M_FLAG = 'U' " + "" + " WHERE STORE_ID = " + "'" +String.valueOf(STORE_ID) + "'" +" and " + "USR_NM = " + "'" + username + "'";
		return true;

	}


	///////////////////////////////////jimmy(old bill in sale screen)///////////////

	public ArrayList<String> getoldnumberbill() {
		ArrayList<String> imeilist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery("select DISTINCT TRI_ID from retail_str_sales_detail ", null);
		if (cursor.moveToFirst()) {
			do {

				imeilist.add(cursor.getString(cursor.getColumnIndex(TRANS_ID)));


			} while (cursor.moveToNext());



		}

		return imeilist;

	}




	public ArrayList<Sales> getalloldinvoicedata(String data) {
		ArrayList<Sales> holdbilllist = new ArrayList<Sales>();
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[1];
		params[0] = data + "%";

		//Cursor productcursor = db.rawQuery("select a.TRI_ID,a.PROD_NM,a.BATCH_NO,a.EXP_DATE,a.S_PRICE,a.QTY,a.MRP,a.UOM,a.STOCK,a.TOTAL from tmp_retail_str_sales_detail a,tmp_retail_str_sales_master b where a.TRI_ID = b.TRI_ID and a.TRI_ID Like ? ",params);       if (productcursor.moveToFirst()) {
		Cursor productcursor = db.rawQuery("select TRI_ID,PROD_NM,BATCH_NO,EXP_DATE,S_PRICE,QTY,MRP,UOM,TOTAL from retail_str_sales_detail where"
				+ " TRI_ID Like ? ",params);
		if (productcursor.moveToFirst()) {
			do {
				Sales saleslist = new Sales();
				saleslist.setProductName(productcursor.getString(productcursor.getColumnIndex(PRODUCT_NAME)));
				saleslist.setBatchNo(productcursor.getString(productcursor.getColumnIndex(BATCH_NO)));
				saleslist.setExpiry(productcursor.getString(productcursor.getColumnIndex(EXPIRY)));
				saleslist.setQuantity(productcursor.getInt(productcursor.getColumnIndex(QUANTITY)));
				saleslist.setMrp(productcursor.getFloat(productcursor.getColumnIndex(MRP)));
				saleslist.setTrans_id(productcursor.getString(productcursor.getColumnIndex(TRANS_ID)));
				saleslist.setTotal(productcursor.getFloat(productcursor.getColumnIndex(COLUMN_TOTAL)));
				saleslist.setUom(productcursor.getString(productcursor.getColumnIndex(UOM)));
				saleslist.setSPrice(productcursor.getFloat(productcursor.getColumnIndex(S_PRICE)));

				holdbilllist.add(saleslist);

			} while (productcursor.moveToNext());
		}
		return holdbilllist;
	}
	public void Deleteolddatefromsalesdetail(String remove) {
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("delete from " + TABLE_NAME_STOCK_DELETE + " where " + DELETE_TRI_ID + " = '" + remove+ "'");
		db.execSQL("delete from " + TABLE_NAME_STOCK_DELETE_GRN_MASTER + " where " + DELETE_TRI_ID + " = '" + remove+ "'");


   /*String Delet_Stock = "delete  from retail_str_stock_master " + "" + "WHERE BATCH_NO=" + "'" + remove + "'";
   Log.e("@@QQQQ",Delet_Stock);

   try {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("Query",Delet_Stock);
      login logi = new login();
      login.sendMessage(String.valueOf(jsonObject));
   }catch (Exception e){

   }*/

		db.close();

	}


	public void InsertMasterDataStoreMgmntIssues(String StoreMngmnt,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + StoreMngmnt);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID :-" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", StoreMngmnt);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataPrdctPhrmaIssues(String ProdctPhrma,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + ProdctPhrma);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", ProdctPhrma);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataLocalPrdctPhrmaIssues(String LocalProdctPhrma,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + LocalProdctPhrma);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", LocalProdctPhrma);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataCaptrngDctrIssues(String CaptrngDctr,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + CaptrngDctr);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", CaptrngDctr);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataDistributorIssues(String Distributor,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + Distributor);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", Distributor);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataSchemeMgmntIssues(String SchemeMgmnt,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + SchemeMgmnt);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", SchemeMgmnt);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataCustomerMgmntIssues(String CustomerMgmnt,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + CustomerMgmnt);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", CustomerMgmnt);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertMasterDataLocalVendrIssues(String LocalVendr,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + LocalVendr);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", LocalVendr);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingUserMgmntIssues(String UserMngmnt,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + UserMngmnt);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", UserMngmnt);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingBillPara1Issues(String BillPara1,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + BillPara1);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", BillPara1);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingBillPara2Issues(String BillPara2,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + BillPara2);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", BillPara2);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingTopProdctIssues(String TopProdct,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + TopProdct);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", TopProdct);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingVendrReasnRejctnIssues(String VendReasn,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + VendReasn);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", VendReasn);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingCustmrReasnRejctnIssues(String CustmrReasn,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + CustmrReasn);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", CustmrReasn);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSystemSettingStoreParaIssues(String StorePara,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + StorePara);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", StorePara);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingOldPurchseIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingNewPurchseIssues(String NewPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + NewPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", NewPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingHoldPurchseIssues(String HoldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + HoldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", HoldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingWithPoInvntryIssues(String WithPoInvntry,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + WithPoInvntry);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", WithPoInvntry);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingWithoutPoInvntryIssues(String WithoutPoInvntry,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + WithoutPoInvntry);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", WithoutPoInvntry);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingHoldPoInvntryIssues(String HoldPoInvntry,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + HoldPoInvntry);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", HoldPoInvntry);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingIntegratdDsbtrIssues(String IntegratdDsbtr,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + IntegratdDsbtr);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", IntegratdDsbtr);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingStockAdjstmntIssues(String StockAdjstmnt,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + StockAdjstmnt);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", StockAdjstmnt);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingExpiredProdctIssues(String ExpiredProdct,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + ExpiredProdct);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", ExpiredProdct);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingDirectVendrIssues(String DirectVendr,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + DirectVendr);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", DirectVendr);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingIndirectVendrIssues(String IndirectVendr,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + IndirectVendr);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", IndirectVendr);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingDirectGrnIssues(String DirectGrn,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + DirectGrn);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", DirectGrn);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertPurchasingIndirectGrnIssues(String IndirectGrn,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + IndirectGrn);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", IndirectGrn);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSalesDayOpenIssues(String DayOpen,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + DayOpen);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", DayOpen);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSalesDayCloseIssues(String DayClose,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + DayClose);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", DayClose);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSalesReturnWithInvoiceIssues(String WithInvoice,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + WithInvoice);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", WithInvoice);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSalesReturnWithoutInvoiceIssues(String WithoutInvoice,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + WithoutInvoice);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", WithoutInvoice);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertSalesIssues(String Sales,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + Sales);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", Sales);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportMasterDataIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportMediaIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportPurchasingIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportInventoryIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportVendorPaymentIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportVendorReturnIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportSalesIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportSalesReturnIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportFinancialIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public void InsertReportOtherIssues(String OldPurchse,String CurrentDate,String MasterScreen,String SubScreen,String id) {
		try {
			Log.e("#########", "We Are Inside DataBase Class");
			SQLiteDatabase db = this.getWritableDatabase();
			Date date = new Date();
			final CharSequence s = DateFormat.format("yyyy-MM-dd", date.getTime());
			CurrentDate = s.toString();
			Log.e("current date :", CurrentDate);
			ContentValues cv = new ContentValues();
			PersistenceManager.saveStoreId(mycontext, getStoreid().toString().replace("[", "").replace("]", ""));
			PersistenceManager.getStoreId(mycontext);
			//cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));

			Log.e("########", "CHANGE REQUEST in database -:" + OldPurchse);
			Log.e("########", "CURRENT DATE in database -:" + CurrentDate);
			Log.e("####","Master Screen -:" +MasterScreen);
			Log.e("####","Sub Screen -:" +SubScreen);
			Log.e("####","ID -:" +id);
			//Log.e("####","ID" +SubScreen);

			cv.put("CHANGE_REQUEST ", OldPurchse);
			cv.put("CURRENT_DATE", CurrentDate);
			cv.put("ID",id);
			cv.put("STORE_ID", PersistenceManager.getStoreId(mycontext));
			cv.put("MASTER_SCREEN", MasterScreen);
			cv.put("SUB_SCREEN", SubScreen);

			long result = db.insert("RETAIL_STORE_SCREEN_DESC", null, cv);
			Log.e("Message", "############## data inserted and result is " + result);
		} catch (Exception e) {
			Log.e("Message", "##############:" + e);
		}
	}

	public ArrayList<String> getsalesmasterrecord(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("BUSINESS_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_TIME")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));

				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("SCHEME_POINTS")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CUST_CNCT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DOCT_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("DOCT_SPEC")));

				arraylist.add(";");


			} while (cur.moveToNext());
		}
		db.close();
		return arraylist;

	}


	public ArrayList<String> getSalesDetailrecord(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_detail where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("EX_TRI_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_DISC")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("BANK_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("CARD_TYPE")));

				arraylist.add(cur.getString(cur.getColumnIndex("ITEM_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_PERC")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISC_VAL")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER1")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE1")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREFIX_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE2")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_PER3")));
				arraylist.add(cur.getString(cur.getColumnIndex("TAX_VALUE3")));
				//arraylist.add(cur.getString(cur.getColumnIndex("LAST_MODIFIED")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("ORDER_TYPE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PICK_UP_LOCATION")));
				arraylist.add(cur.getString(cur.getColumnIndex("LINE_ITEM_DISC")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		db.close();
		return arraylist;
	}

	public ArrayList<String> getstockMasterrecord(){
		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));

				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("FREE_GOODS")));
				arraylist.add(cur.getString(cur.getColumnIndex("MF_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_BY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CREATED_ON")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("IND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_SPRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREV_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MFG_BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT_AMOUNT")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}
		public void updateSlaveFlagsalesdetail(){
			SQLiteDatabase db = this.getReadableDatabase();
			String Query = ("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'and CARD_NO =''");
			db.execSQL(Query);
			Log.e("Update Slave Flag","update retail_str_sales_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		}
	public void updateSlaveFlagsalesMaster(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'and CARD_NO =''");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}

	public void updateSlaveFlagsalesdetailcard(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0' and CARD_NO!=''");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_detail with card SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}
	public void updateSlaveFlagsalesMastercard(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0' and CARD_NO!='' ");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_master with card SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}
	public void updateSlaveFlagstockMaster(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}





	//////////////////code from akshay///////////////////////////////////////////////
	public ArrayList<String> getSalesReturnRecord() {
		ArrayList<String> arrayList = new ArrayList<>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur= db.rawQuery("select * from retail_str_sales_master_return",null);
		if (cur.moveToFirst())
		{
			do {

				arrayList.add(cur.getString(cur.getColumnIndex("ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arrayList.add(cur.getString(cur.getColumnIndex("STORE_ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("FLAG")));
				arrayList.add(cur.getString(cur.getColumnIndex("REASON_OF_RETURN")));
				arrayList.add(cur.getString(cur.getColumnIndex("GRAND_TOTAL")));
				arrayList.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arrayList.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arrayList.add(";");


			}while (cur.moveToNext());
		}

		return arrayList;
	}


	public void updateSlaveFlagSalesReturn(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_master_return SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_master_return SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}



	public ArrayList<String> getDataSendMailRecord() {

		ArrayList<String> arrayList= new ArrayList<>();
		SQLiteDatabase db=this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_send_mail_pdf",null);
		if (cur.moveToFirst())
		{
			do {

				arrayList.add(cur.getString(cur.getColumnIndex("TXN_ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("PREFIX_NM")));
				arrayList.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arrayList.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arrayList.add(cur.getString(cur.getColumnIndex("UNIVERSAL_ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arrayList.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arrayList.add(";");


			}while (cur.moveToNext());
		}

		return arrayList;
	}



	public void updateSlaveFlagSendMailPdf(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_send_mail_pdf SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_send_mail_pdf SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}



	public ArrayList<String> getSaleReturnDataRecordProductDetail() {

		ArrayList<String> arrayList = new ArrayList<>();
		SQLiteDatabase db= this.getReadableDatabase();
		Cursor cur =db.rawQuery("select * from retail_str_sales_details_return",null);
		if (cur.moveToNext())
		{
			do
			{

				arrayList.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arrayList.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arrayList.add(cur.getString(cur.getColumnIndex("ID")));
				arrayList.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arrayList.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arrayList.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arrayList.add(cur.getString(cur.getColumnIndex("FLAG")));
				arrayList.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arrayList.add(cur.getString(cur.getColumnIndex("SALE_DATE")));
				arrayList.add(cur.getString(cur.getColumnIndex("QTY")));
				arrayList.add(cur.getString(cur.getColumnIndex("MRP")));
				arrayList.add(cur.getString(cur.getColumnIndex("UOM")));
				arrayList.add(cur.getString(cur.getColumnIndex("TOTAL")));
				arrayList.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arrayList.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arrayList.add(";");



			}while(cur.moveToNext());
		}
		return arrayList;
	}

	public void updateSlaveFlagForProductDetails(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_details_return SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_details_return SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}

	public ArrayList<String> getSalesReturnStockUpdateForInvoice() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));

				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));


				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}




	public void updateSlaveFlagForUpdateQty(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}



	/*public ArrayList<String> getSalesFlagReturn() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("select * from retail_str_sales_detail where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("TRI_ID")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;


	}
	public void updateSlaveFlagForReturn(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_sales_detail SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}*/


	public ArrayList<String> getInventoryByTotal() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("MFG_BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));

				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("FREE_GOODS")));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREV_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_SPRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("MFG_BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(";");


			} while (cur.moveToNext());
		}
		return arraylist;


	}
	public void updateSlaveFlagForInventoryByTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}

	public ArrayList<String> getInventoryUpdatedData() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}

	public void updateSlaveFlagForUpdatedDataInventoryByTotal(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}



	public ArrayList<String> getGrnMasterwithoutpo() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_str_grn_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("GRAND_AMOUNT")));
				arraylist.add(cur.getString(cur.getColumnIndex("FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}

	public void updateSlaveFlagForGrnMasterwithoutpo(){
		SQLiteDatabase db = this.getReadableDatabase();
			String Query = ("update retail_str_grn_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
			db.execSQL(Query);
			Log.e("Update Slave Flag","retail_str_grn_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}


	/*public ArrayList<String> getPDfDetailInventoryWithoutPo() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_send_mail_pdf where SLAVE_FLAG='0' ",null);
		if (cur.moveToFirst()) {
			do {

				arraylist.add(cur.getString(cur.getColumnIndex("TXN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREFIX_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_NAME")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("UNIVERSAL_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}

	public void updateSlaveFlagForgetPDfDetailInventoryWithoutPo(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_send_mail_pdf SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","retail_send_mail_pdf SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}*/


	public ArrayList<String> getInventoryTotalDiscount() {

		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {


				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("VEND_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("PO_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("MFG_BATCH_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("EXP_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("POS_USER")));
				arraylist.add(cur.getString(cur.getColumnIndex(STORE_ID)));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_NM")));
				arraylist.add(cur.getString(cur.getColumnIndex("P_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROFIT_MARGIN")));
				arraylist.add(cur.getString(cur.getColumnIndex("QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_PRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("UOM")));
				arraylist.add(cur.getString(cur.getColumnIndex("BARCODE")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_FACT")));
				arraylist.add(cur.getString(cur.getColumnIndex("FREE_GOODS")));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("PREV_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_MRP")));
				arraylist.add(cur.getString(cur.getColumnIndex("CONV_SPRICE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_NO")));
				arraylist.add(cur.getString(cur.getColumnIndex("VENDOR_INVOICE_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("PURCHASE_UNIT_CONV")));
				arraylist.add(cur.getString(cur.getColumnIndex("DISCOUNT_PERCENT")));
				arraylist.add(cur.getString(cur.getColumnIndex("S_FLAG")));
				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}



	public void updateSlaveFlagForInventoryByTotalDiscount(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}
	public ArrayList<String> getInventoryUpdatedDiscountData() {


		ArrayList<String> arraylist = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cur = db.rawQuery("select * from retail_str_stock_master where SLAVE_FLAG='0'",null);
		if (cur.moveToFirst()) {
			do {
				arraylist.add(cur.getString(cur.getColumnIndex("GRN_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("INVENTORY_DATE")));
				arraylist.add(cur.getString(cur.getColumnIndex("M_FLAG")));
				arraylist.add(cur.getString(cur.getColumnIndex("CON_MUL_QTY")));
				arraylist.add(cur.getString(cur.getColumnIndex("PROD_ID")));
				arraylist.add(cur.getString(cur.getColumnIndex("MRP")));

				arraylist.add(";");
			} while (cur.moveToNext());
		}
		return arraylist;

	}

	public void updateSlaveFlagForInventoryByTotalUpdateDiscount(){
		SQLiteDatabase db = this.getReadableDatabase();
		String Query = ("update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
		db.execSQL(Query);
		Log.e("Update Slave Flag","update retail_str_stock_master SET SLAVE_FLAG='1' where SLAVE_FLAG='0'");
	}


	public float getNoBIllToSplash()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Calendar theEnd = Calendar.getInstance();
   /*Calendar theStart = Calendar.getInstance();
   theStart.add(Calendar.DAY_OF_MONTH,1 )*/;
		Calendar cs = Calendar.getInstance();   // this takes current date
		cs.set(Calendar.DAY_OF_MONTH, 1);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String start = dateFormat.format(cs.getTime());
		String end = dateFormat.format(theEnd.getTime());
		Log.d("****",start + " " +  end);
		String Query = ("select count(*) from retail_str_sales_master " + " where SALE_DATE between '" + start + "' AND '" + end + "' ");
		Cursor c=db.rawQuery(Query,null);

		c.moveToFirst();
		float i = c.getFloat(0);
		c.close();
		Log.e("NO of bill done",String.valueOf(i));
		return i;
	}



	///////////////////////////////////////////////////////////////////////////////
}




