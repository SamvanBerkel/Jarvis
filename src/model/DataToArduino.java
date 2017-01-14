package model;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

import static com.fazecast.jSerialComm.SerialPort.getCommPort;

public class DataToArduino {
    private static String speech;

    static SerialPort serialPort;

    public static void main(String[] args){
        startConnection();
    }

    public static void startConnection() {
        speech = "";

        serialPort = getCommPort("COM3");

        serialPort.openPort();
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);

        if (serialPort.openPort()) {
            System.out.println("port is open");
        } else {
            System.out.println("port is closed");
        }

        // create a new thread for sending data to the arduino
        Thread thread = new Thread() {
            @Override
            public void run() {
                // wait after connecting, so the bootloader can finish
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }

                // enter an infinite loop that sends text to the arduino
                PrintWriter output = new PrintWriter(serialPort.getOutputStream());
                while (true) {
                    output.print("tvon");
                    output.flush();
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                }
            }
        };
        thread.start();
    }

    public static void setSpeech(String speechIn){
        speech = speechIn;
    }
}