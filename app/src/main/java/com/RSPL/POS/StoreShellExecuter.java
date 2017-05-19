package com.RSPL.POS;

/**
 * Created by rspl-rajeev on 24/8/16.
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class StoreShellExecuter {



    public StoreShellExecuter() {

    }

    public String Executer(String command) {



        StringBuffer output = new StringBuffer();


        try {
            Process p = Runtime.getRuntime().exec(command);



            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));


            DataOutputStream outputStream = new DataOutputStream(p.getOutputStream());


            outputStream.writeBytes("sh /sdcard/retail_store_op.sh");

            outputStream.flush();
            outputStream.close();


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