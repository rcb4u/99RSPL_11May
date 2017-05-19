package com.RSPL.POS;

/**
 * Created by rspl-gourav on 20/8/16.
 */

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ShellExecuter {



    public ShellExecuter() {

    }

    public String Executer(String command) {



        StringBuffer output = new StringBuffer();


        try {
           Process p = Runtime.getRuntime().exec(command);



            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));


            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());
            DataOutputStream outputStream1=new DataOutputStream(p.getOutputStream());

            outputStream.writeBytes("sh /sdcard/sales_op.sh");
            outputStream.flush();
            outputStream.close();

            outputStream1.writeBytes("sh /sdcard/retail_store_op.sh");

            outputStream1.flush();
            outputStream1.close();
















            BufferedReader read = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        String response = output.toString();

        return response;




    }
}
