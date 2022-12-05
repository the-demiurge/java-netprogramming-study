package net.study.chat.text.common;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ChatHistory implements Serializable {

    private final List<ChatEntry> entries = new CopyOnWriteArrayList<>();

    public synchronized void addEntry(ChatEntry entry){
        entries.add(entry);
    }

    public synchronized void displayEntries(){
        if(!entries.isEmpty()){
            for(ChatEntry e : entries){
                System.out.println(e.getAuthor() + ": " + e.getText());
            }
        }
        else{
            System.out.println("Nobody wrote in this chat yet :(");
        }
    }
}
