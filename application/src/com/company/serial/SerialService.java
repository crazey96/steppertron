package com.company.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.company.Config;

public class SerialService {

    private List<SerialPort> serialPortList;

    public SerialService() {
        serialPortList = new ArrayList<>();
    }
    public void initialize() {
        for(int index = 0; index < Config.SLAVES; index++) {
            SerialPort serialPort = SerialPort.getCommPort("/dev/ttyUSB" + index);
            if(!serialPort.openPort()) {
                return;
            }
            serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
            serialPortList.add(serialPort);
            ThreadRX threadRX = new ThreadRX(index);
            threadRX.start();
        }
    }
    public void write(int slave, String message) {
        System.out.println(slave + " " + message);
        try {
            byte[] encoded = (message + "\n").getBytes();
            serialPortList.get(slave).getOutputStream().write(encoded);
            serialPortList.get(slave).getOutputStream().flush();
        } catch (IOException exception) {
            serialPortList = null;
        }
    }
    private class ThreadRX extends Thread {

        private final int slave;

        private ThreadRX(int slave) {
            this.slave = slave;
        }
        @Override
        public void run() {
            while(serialPortList.get(slave) != null) {
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
            byte[] readBuffer = new byte[serialPortList.get(slave).bytesAvailable()];
            serialPortList.get(slave).readBytes(readBuffer, readBuffer.length);
            return new String(readBuffer);
        }
    }
}