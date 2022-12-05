package net.study.chat.text.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatEntry implements Serializable {

    private String text;
    private LocalDateTime timeStamp;
    private String author;

    public static ChatEntry from(Participant participant, ChatMessage message){
        return new ChatEntry(message.getMessage(),LocalDateTime.now(), participant.getNickname());
    }
}
