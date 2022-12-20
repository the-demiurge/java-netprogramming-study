package net.study.udpEcho;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

//Send message and receive it echoed via UDP
public class Sender {

    public static boolean sendMessage(String message, String host,
                                      int port, int packSize) {
        if (host == null) {
            System.out.print("Invalid host");
            return FALSE;
        }
        if (packSize <= 0)
            packSize = 1024;

        InetAddress addr;
        byte[] buffer = null;
        try {
            addr = InetAddress.getByName(host);
            DatagramSocket rcvSock = new DatagramSocket();

            byte[] sentMessage = message.getBytes();
            DatagramPacket pack = new DatagramPacket(sentMessage,
                    sentMessage.length, addr, port);
            rcvSock.send(pack);
            buffer = new byte[packSize];
            pack = new DatagramPacket(buffer,
                    buffer.length);
            rcvSock.receive(pack);
            String echoedMessage = new String(buffer).trim();
            System.out.print("Echoed message: '" + echoedMessage + "'");
            rcvSock.close();
            return TRUE;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return FALSE;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message = scanner.nextLine();

        if (message != null) {
            String hostName = "localhost";
            int port = 5559;
            if (!sendMessage(message, hostName, port, 2048)) {
                System.out.println("Error sending message");
            }
        }
    }
}