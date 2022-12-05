package net.study.chat.text.server;

import net.study.chat.text.common.ChatEntry;
import net.study.chat.text.common.ChatHistory;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class ServerSessionHandlerImpl implements ServerSessionHandler, ChatMessageDeliverable {

    private final ExecutorService executor;

    private final ChatHistory history = new ChatHistory();
    private final AtomicLong counter = new AtomicLong(0L);
    private final ParticipantList participantList = new ParticipantList();
    private final List<ClientSessionHandler> clientSessionHandlerList = new ArrayList<>();

    public ServerSessionHandlerImpl(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void handle(Socket socket) throws Exception {
        var sessionHandler = new ClientSessionHandler(socket, history, counter, participantList, this, clientSessionHandlerList);
        executor.submit(sessionHandler);
    }

    @Override
    public void delivery(ChatEntry chatEntry) {
        clientSessionHandlerList.stream().filter(ClientSessionHandler::isActive).forEach(c -> c.send(chatEntry));
    }
}
