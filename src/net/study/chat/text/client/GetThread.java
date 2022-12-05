package net.study.chat.text.client;

import net.study.chat.text.common.ChatEntry;

import java.io.IOException;
import java.io.ObjectInputStream;

public class GetThread implements Runnable{

    private final ObjectInputStream in;

    public GetThread(ObjectInputStream in) {
        this.in = in;;
    }
    @Override
    public void run() {
        while(true){
            ChatEntry otherMessage = null;
            try {
                otherMessage = (ChatEntry) in.readObject();
                System.out.println(otherMessage.getAuthor() + ": " + otherMessage.getText());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
