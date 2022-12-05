package net.study.chat.text.server;

import java.net.Socket;

@FunctionalInterface
public interface ServerSessionHandler {
    void handle(Socket socket) throws Exception;
}
