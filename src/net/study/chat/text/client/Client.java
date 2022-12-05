package net.study.chat.text.client;

import net.study.chat.text.common.ChatHistory;
import net.study.chat.text.common.Participant;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {

    private final String host;
    private final Integer port;
    private Socket socket = null;

    private Participant participant;
    private ChatHistory history;

    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public Client(String host, Integer port, Participant participant) throws IOException {
        this.host = host;
        this.port = port;
        this.participant = participant;
        this.socket = new Socket(host, port);
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

    }

    private synchronized void send(Object o) throws IOException {
        out.writeObject(o);
    }
    private synchronized void receiveID(Participant participant) throws IOException, ClassNotFoundException {
        Participant receivedParticipant = (Participant) in.readObject();
        participant.setId(receivedParticipant.getId());
    }


    @Override
    public void run()  {
        try {
            send(participant);
            //Get ID
            receiveID(this.participant);
            //show history
            this.history = (ChatHistory) in.readObject();
            System.out.println("---Previous chat messages---");
            history.displayEntries();
            GetThread get = new GetThread(in);
            WriteThread write = new WriteThread(out, participant);
            ExecutorService executor = Executors.newFixedThreadPool(3);
            executor.submit(write);
            executor.submit(get);
            System.out.println("---Write your message---");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
