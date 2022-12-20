package net.study.udpEcho;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import static java.lang.Boolean.TRUE;

public class Receiver {

    //Echo: receive and send the same message via UDP
    public static boolean echoMessage(int port, int packSize){
        if (packSize<=0)
            packSize = 1024;
        String receivedMessage = null;
        DatagramSocket sndSock = null;
        byte[] buffer = null;
        try {
            sndSock = new DatagramSocket(port);
            buffer = new byte[packSize];
            DatagramPacket pack = new DatagramPacket(buffer,
                    buffer.length);
            sndSock.receive(pack);
            receivedMessage = new String(buffer).trim();
            System.out.println("Received message: '" + receivedMessage + "'");
            sndSock.send(pack);
            System.out.println("Sent message: '" + receivedMessage + "'");
            return TRUE;
        }catch (SocketTimeoutException e) {
            if (receivedMessage!=null){
                return TRUE;
            }else
                System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return TRUE;
    }
    public static void main(String[] args) {
        int port = 5559;
        if (!echoMessage(port,2048)){
            System.out.println("Haven't received message");
        }
    }
}