package net.study.chat.text.server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{

    private final String host;
    private final Integer port;

    private final ServerSessionHandler handler;
    private final int backlog;
    private Boolean active = true;
    private ServerSocket serverSocket = null;

    public Server(String host, Integer port, int backlog, ServerSessionHandler handler) {
        this.host = host;
        this.port = port;
        this.backlog = backlog;
        this.handler = handler;
    }
    public Server(Integer port, ServerSessionHandler handler) {
        this("0.0.0.0", port, 100, handler);
    }

    private void handle(){
        try{
            this.serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(host));
            try(ServerSocket serverSocket = this.serverSocket) {
                while(active){
                    Socket socket = serverSocket.accept();
                    if(socket == null){
                        break;
                    }
                    handler.handle(socket);

                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void stop(){
        active = false;
        if(serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void run() {
        if(!active){
            return;
        }
        handle();

    }
}
