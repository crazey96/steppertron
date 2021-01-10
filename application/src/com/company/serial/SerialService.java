package com.company.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class SerialService {

    private SerialPort serialPort;

    public void initialize() {
        serialPort = SerialPort.getCommPort("/dev/ttyUSB0");
        if(!serialPort.openPort()) {
            return;
        }
        serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
        ThreadRX threadRX = new ThreadRX();
        threadRX.start();
    }
    public void write(String message) {
        System.out.println(message);
        try {
            byte[] encoded = (message + "\n").getBytes();
            serialPort.getOutputStream().write(encoded);
            serialPort.getOutputStream().flush();
        } catch (IOException exception) {
            serialPort = null;
        }
    }
    private class ThreadRX extends Thread {

        @Override
        public void run() {
            while(serialPort != null) {
                try {
                    Reader _message = new StringReader(read());
                    BufferedReader bufferedReader = new BufferedReader(_message);
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException exception) {
                    System.err.println(exception.getMessage());
                    return;
                }
            }
        }
        private String read() {
            byte[] readBuffer = new byte[serialPort.bytesAvailable()];
            serialPort.readBytes(readBuffer, readBuffer.length);
            return new String(readBuffer);
        }
    }
}