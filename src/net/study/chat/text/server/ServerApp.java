package net.study.chat.text.server;



import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApp {
    public static void main(String[] args) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        var server = new Server(5558, new ServerSessionHandlerImpl(executor));
        executor.submit(server);
        System.out.println("Server is running...");
        System.out.println("To stop application enter \"q\"");
        try(Scanner in = new Scanner(System.in))
        {
            do {
                String line = in.nextLine();
                if (line.equalsIgnoreCase("Q"))
                {
                    break;
                }
            }while(true);
        }

        server.stop();
        executor.shutdown();
        System.out.println("Stopped application");
    }
}
