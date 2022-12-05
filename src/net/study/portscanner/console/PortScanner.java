package net.study.portscanner.console;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortScanner implements Runnable  {

    private final String host;
    private final int port;

    public PortScanner(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 1000);
            System.out.println("Port " + port + " is open");
            socket.close();
        }
        catch(IOException ex){
            System.out.println("Port " + port + " is closed");
        }
    }
}
