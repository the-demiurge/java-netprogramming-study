package net.study.chat.text.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Participant implements Serializable {

    private Long id;
    private final String nickname;

}
