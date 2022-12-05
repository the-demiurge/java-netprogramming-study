package net.study.portscanner.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortScannerGUI {

    private JPanel mainPanel;
    private JLabel enterHostLabel;
    private JLabel enterStartPortLabel;
    private JLabel enterEndPortLabel;
    private JTextField hostTextField;
    private JTextField startPortField;
    private JTextField endPortField;
    private JTextArea resultTextArea;
    private JButton scanButton;
    private JScrollPane resultScrollPane;

    public PortScannerGUI() {
        resultTextArea.setEditable(false);
        scanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultTextArea.setText(null);
                String host = hostTextField.getText();
                int start_port = Integer.parseInt(startPortField.getText());
                int end_port = Integer.parseInt(endPortField.getText());

                ExecutorService executor = Executors.newFixedThreadPool(1);
                for (int i = start_port; i <= end_port; i++)
                {
                    Future<Boolean> res = null;
                    try {
                        res = executor.submit(new PortScanner(host,i));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Boolean isOpen = null;
                    try {
                        isOpen = res.get();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(isOpen){
                        resultTextArea.append("Port " + i + " is open\n");
                    }
                    else{
                        resultTextArea.append("Port " + i + " is closed\n");
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                executor.shutdown();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Port Scanner");
        frame.setContentPane(new PortScannerGUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
