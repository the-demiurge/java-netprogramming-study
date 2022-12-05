package net.study.chat.text.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChatMessage implements Serializable {

    private final Long authorId;
    private final String message;

}
