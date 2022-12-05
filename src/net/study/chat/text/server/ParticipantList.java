package net.study.chat.text.server;

import lombok.Getter;
import net.study.chat.text.common.ChatEntry;
import net.study.chat.text.common.Participant;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class ParticipantList {

    private final CopyOnWriteArrayList<Participant> participants = new CopyOnWriteArrayList<>();

    public synchronized void add(Participant participant){
        participants.add(participant);
    }

    public synchronized void delete(Participant participant){
        participants.remove(participant);
    }

}
