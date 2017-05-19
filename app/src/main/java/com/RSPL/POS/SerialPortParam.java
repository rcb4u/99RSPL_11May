package com.RSPL.POS;

/**
 * Created by rspl-rahul on 17/2/17.
 */

 class SerialPortParam {

    public static String Name = "";
    public static String Path = "";
    static int Baudrate = 9600;
    static int DataBits = 8;
    static int StopBits = 1;
    static int Parity = 110;
    public static int SpaceTime = 0;
    static String Device = "/dev/ttyS0";
    static int Flowcontrol = 0;

    SerialPortParam() {
    }

    public static enum ParityEnum {
        n,
        o,
        e,
        s;

        private ParityEnum() {
        }
    }
}
