package net.study.chat.text.client;

import net.study.chat.text.common.ChatMessage;
import net.study.chat.text.common.Participant;


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class WriteThread implements Runnable{

    private final ObjectOutputStream out;
    private final Participant participant;

    public WriteThread(ObjectOutputStream out, Participant participant) {
        this.out = out;
        this.participant = participant;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String text = scanner.nextLine();
            System.out.println(participant.getNickname() + ": " + text);
            ChatMessage message = new ChatMessage(participant.getId(), text);
            try {
                out.writeObject(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
