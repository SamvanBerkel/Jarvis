package model;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

/**
 * Created by sam on 16/01/2017.
 */
public class Arduino {
    private static SerialPort chosenPort;
    private static String data;

    public static void startDataSendThread(){
        Thread thread = new Thread(){
            @Override public void run() {
                // wait after connecting, so the bootloader can finish
                try {Thread.sleep(1000); } catch(Exception e) {}

                // enter an infinite loop that sends text to the arduino
                PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                while(true) {
                    //output.print(new SimpleDateFormat("hh:mm:ss a     MMMMMMM dd, yyyy").format(new Date()));
                    output.print(data);
                    output.flush();
                    try {Thread.sleep(100); } catch(Exception e) {}
                }
            }
        };
        thread.start();
    }

    public static void setData(String dataIn){
        data = dataIn;
    }
}
