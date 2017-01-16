package model;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

/**
 * Created by sam on 16/01/2017.
 */
public class Arduino {
    private static SerialPort chosenPort;
    private static String data;
    private static boolean firstTime = true;
    private static Thread thread;

    public static void startDataSendThread(){
        chosenPort = SerialPort.getCommPort("COM3");
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_SEMI_BLOCKING, 0, 0);
        System.out.println(chosenPort.openPort());

        if(firstTime){
            thread = new Thread(){
                @Override public void run() {
                    // wait after connecting, so the bootloader can finish
                    try {Thread.sleep(1000); } catch(Exception e) {}

                    // enter an infinite loop that sends text to the arduino
                    PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
                    while(true) {
                        output.print(data);
                        output.flush();
                        try {Thread.sleep(100); } catch(Exception e) {}
                    }
                }
            };
            thread.start();
        }
    }


    public static void setData(String dataIn){
        data = dataIn;
    }

    public static void main(String[] args){
        setData("this works");
        startDataSendThread();
    }
}
