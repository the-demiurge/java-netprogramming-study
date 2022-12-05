package net.study.portscanner.gui;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

public class PortScanner implements Callable<Boolean> {

    private final String host;
    private final int port;

    public PortScanner(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    @Override
    public Boolean call() {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(host, port), 1000);
            socket.close();
            System.out.println("Port " + port + " is open");
            return true;
        }
        catch(IOException ex){
            System.out.println("Port " + port + " is closed");
            return false;
        }
    }
}
