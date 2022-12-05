package net.study.chat.text.server;

import net.study.chat.text.common.ChatEntry;

public interface ChatMessageDeliverable {

    void delivery(ChatEntry chatEntry);
}
