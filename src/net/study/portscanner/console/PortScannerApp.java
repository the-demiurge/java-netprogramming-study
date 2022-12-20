package net.study.portscanner.console;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PortScannerApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter IP: ");
        String host = sc.next();
        System.out.print("Enter start port: ");
        int start_port = sc.nextInt();
        System.out.print("Enter end port: ");
        int end_port = sc.nextInt();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (int i = start_port; i <= end_port; i++)
        {
            executor.submit(new PortScanner(host, i));
        }
        Thread.sleep(100);
        executor.shutdown();
    }
}