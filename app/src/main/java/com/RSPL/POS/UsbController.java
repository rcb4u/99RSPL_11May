package com.RSPL.POS;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by rspl-rahul on 26/8/16.
 */public class UsbController {
    private final Context mApplicationContext;
    private UsbManager mUsbManager;
    protected static final String ACTION_USB_PERMISSION = "com.zj.usbconn.USB";
    private Handler mHandler;
    public static final int USB_CONNECTED = 0;
    public static final int USB_DISCONNECTED = 1;
    private UsbEndpoint ep = null;
    private UsbInterface usbIf = null;
    private UsbDeviceConnection conn = null;
    private final BroadcastReceiver mPermissionReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            UsbController.this.mApplicationContext.unregisterReceiver(this);
            if(intent.getAction().equals("com.RSPL.POS.15inchfinalcode") && intent.getBooleanExtra("permission", false)) {
                Log.d("usb调试", "已获取usb设备访问权限");
                UsbDevice dev = intent.getParcelableExtra("device");
                if(dev != null) {
                    Message msg = UsbController.this.mHandler.obtainMessage(0);
                    UsbController.this.mHandler.sendMessage(msg);
                }
            }

        }
    };

    public UsbController(Activity parentActivity, Handler handler) {
        this.mApplicationContext = parentActivity;
        this.mUsbManager = (UsbManager) this.mApplicationContext.getSystemService(Context.USB_SERVICE);
        this.mHandler = handler;
    }



    public synchronized UsbDevice getDev(int vid, int pid) {
        UsbDevice dev = null;
        this.ep = null;
        this.usbIf = null;
        this.conn = null;
        HashMap devlist = this.mUsbManager.getDeviceList();
        Iterator deviter = devlist.values().iterator();

        while(deviter.hasNext()) {
            UsbDevice d = (UsbDevice) deviter.next();
            Log.d("usb device:", d.getDeviceName() + "  " + String.format("%04X:%04X", Integer.valueOf(d.getVendorId()), Integer.valueOf(d.getProductId())));
            if(d.getVendorId() == vid && d.getProductId() == pid) {
                dev = d;
                break;
            }
        }

        return dev;
    }

    public synchronized HashMap<String,UsbDevice> getUsbList() {
        return this.mUsbManager.getDeviceList();
    }

    public synchronized boolean isHasPermissions(UsbDevice dev) {
        return this.mUsbManager.hasPermission(dev);
    }

    public synchronized void getPermission(UsbDevice dev) {
        if(dev != null) {
            if(!this.isHasPermissions(dev)) {
                PendingIntent msg = PendingIntent.getBroadcast(this.mApplicationContext, 0, new Intent("com.RSPL.POS.15inchfinalcode"), 0);
                this.mApplicationContext.registerReceiver(this.mPermissionReceiver, new IntentFilter("com.RSPL.POS.15inchfinalcode"));
                this.mUsbManager.requestPermission(dev, msg);
            } else {
                Message msg1 = this.mHandler.obtainMessage(0);
                this.mHandler.sendMessage(msg1);
            }

        }
    }

    public synchronized void sendMsgs(String msg, String charset, UsbDevice dev) {
        if(msg.length()!= 0) {
            byte[] send;
            try {
                send = msg.getBytes("GBK");

            } catch (UnsupportedEncodingException var6) {
                send = msg.getBytes();
            }

            this.sendBytes(send, dev);
            this.sendBytes(new byte[]{(byte)13, (byte)10, (byte)0}, dev);
        }
    }


    public synchronized void sendBytes(byte[] bits,UsbDevice dev) {
        if(this.ep != null && this.usbIf != null && this.conn != null) {
            this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
        } else {
            if(dev.getInterfaceCount() == 0) {
                return;
            }

            this.usbIf = dev.getInterface(0);
            if(this.usbIf.getEndpointCount() == 0) {
                return;
            }

            for(int i = 0; i < this.usbIf.getEndpointCount(); ++i) {
                if(this.usbIf.getEndpoint(i).getType() == 2 && this.usbIf.getEndpoint(i).getDirection() != 128) {
                    this.ep = this.usbIf.getEndpoint(i);
                }
            }

            this.conn = this.mUsbManager.openDevice(dev);
            if(this.conn.claimInterface(this.usbIf, true)) {
                this.conn.bulkTransfer(this.ep, bits, bits.length, 0);
            }
        }

    }

    public synchronized void close() {
        if(this.conn != null) {
            this.conn.close();
            this.ep = null;
            this.usbIf = null;
            this.conn = null;
        }

    }

    public synchronized void cutPapers(UsbDevice dev, int n) {
        byte[] bits = new byte[]{(byte)29, (byte)86, (byte)66, (byte)n};
        this.sendBytes(bits, dev);
    }
    public synchronized void defaultBuzzer(UsbDevice dev) {
        byte[] bits = new byte[]{(byte)27, (byte)66, (byte)4, (byte)1};
        this.sendBytes(bits, dev);
    }


}
