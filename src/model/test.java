package model;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

public class test {

    static SerialPort chosenPort;

    public static void main(String[] args) {
        // attempt to connect to the serial port
        chosenPort = SerialPort.getCommPort("COM3");
        chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
        if (chosenPort.openPort()) {
            System.out.println("port is open");
            // enter an infinite loop that sends text to the arduino
            PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
            while (true) {
                output.print("tvon");
                output.flush();
            }
        }
    }
}