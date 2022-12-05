package net.study.chat.text.client;

import net.study.chat.text.common.Participant;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {

        String host = "127.0.0.1";
        int port = 5558;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        System.out.print("Enter your nickname: ");
        Scanner scanner = new Scanner(System.in);
        String nickname = scanner.nextLine();
        Participant participant = new Participant(nickname);
        executor.submit(new Client(host, port, participant));

        executor.shutdown();
    }
}
