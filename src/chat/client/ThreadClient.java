package src.chat.client;

import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

/**
 * Thread for clients
 */
public class ThreadClient implements Runnable {

    private Socket socket;
    private BufferedReader cin;

    private TextArea ta;

    public ThreadClient(Socket socket, TextArea ta) throws IOException {
        this.socket = socket;
        this.ta=ta;
        this.ta.setText("GGGGGGGGGGGG");
        this.cin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = cin.readLine();
//                ta.addANewLine(message);
                System.out.println(message);
                System.out.println("cococo");
                ta.appendText(message);
                ta.appendText("sfdsfsdf");
                ta.appendText("\n");
                System.out.println(message);
            }
        } catch (SocketException e) {
            System.out.println("You left the chat-room");
        } catch (IOException exception) {
            System.out.println(exception);
        } finally {
            try {
                cin.close();
            } catch (Exception exception) {
                System.out.println(exception);
            }
        }
    }
}
