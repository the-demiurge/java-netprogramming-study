package net.study.chat.text.server;

import net.study.chat.text.common.ChatEntry;
import net.study.chat.text.common.ChatHistory;
import net.study.chat.text.common.ChatMessage;
import net.study.chat.text.common.Participant;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ClientSessionHandler implements Runnable {

    private final Socket socket;
    private Participant participant;
    private final ChatHistory history;
    private final AtomicLong counter;
    private final ParticipantList participantList;
    private final ChatMessageDeliverable chatMessageDeliverable;
    private final List<ClientSessionHandler> clientSessionHandlerList;
    private final ObjectOutputStream outputStream;


    public ClientSessionHandler(Socket socket, ChatHistory history, AtomicLong counter, ParticipantList participantList, ChatMessageDeliverable chatMessageDeliverable, List<ClientSessionHandler> clientSessionHandlerList) throws IOException {
        this.socket = socket;
        this.history = history;
        this.counter = counter;
        this.participantList = participantList;
        this.chatMessageDeliverable = chatMessageDeliverable;
        this.clientSessionHandlerList = clientSessionHandlerList;
        this.clientSessionHandlerList.add(this);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public Boolean isActive(){
        return !socket.isClosed();
    }

    private void receive(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.participant = (Participant) in.readObject();
        var id = this.counter.incrementAndGet();
        this.participant.setId(id);
        participantList.add(this.participant);
        send(this.participant);
        send(this.history);
        while (isActive()){
            var message = (ChatMessage) in.readObject();
            var chatEntry = ChatEntry.from(participant, message);
            history.addEntry(chatEntry);
            chatMessageDeliverable.delivery(chatEntry);
        }
    }

    private synchronized void send(Object o) throws IOException {
        outputStream.writeObject(o);
    }
    public synchronized void send(ChatEntry chatEntry){
        if(!chatEntry.getAuthor().equalsIgnoreCase(participant.getNickname())){
            try {
                outputStream.writeObject(chatEntry);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            receive(in);
        }
        catch (Exception e){}
        finally {
            this.participantList.delete(this.participant);
            this.clientSessionHandlerList.remove(this);
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
