package com.company.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class SerialService {

    private final SerialHandler serialHandler;

    private SerialPort serialPort;

    public SerialService(SerialHandler serialHandler) {
        this.serialHandler = serialHandler;
    }
    public void initialize() {
        serialPort = SerialPort.getCommPort("/dev/ttyUSB0");
        if(!serialPort.openPort()) {
            serialHandler.onConnectionLost();
            return;
        }
        serialPort.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
    }
    public void write(String message) {
        try {
            byte[] encoded = (message + "\n").getBytes();
            serialPort.getOutputStream().write(encoded);
            serialPort.getOutputStream().flush();
        } catch (IOException exception) {
            serialPort = null;
        }
    }
}