package src.chat.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import src.ChatControl;

public class Client {
    static String name = "";
    @FXML  TextArea messageBox;
    @FXML  TextArea allMessage;
    @FXML static Button sendButtonAction;
    @FXML Label gtn;
    private static boolean done = false;
    private static Socket socket;
    private static PrintWriter cout;
    private static String reply;
    public void startClient(String yourname) throws IOException {
        name = yourname;
        reply = "";
//        System.out.println("Enter your name (Please enter your name to join the chat): ");
//        reply = sc.nextLine();
//        name = reply;
        socket = new Socket("localhost", 5000);
        try {
            cout = new PrintWriter(socket.getOutputStream(), true);
            System.out.println(allMessage.getText());
            ThreadClient threadClient = new ThreadClient(socket, allMessage);
            new Thread(threadClient).start(); // start thread to receive message

            cout.println(name + ": has joined chat-room.");
//            do {
//
//
//                doSomething();
////                if(sendButtonAction.setOnAction(new EventHandler<sendButtonAction>())){       //TODO 怎么等待按钮按下
////                    continue;
////                }
//                reply = messageBox.getText();
//                messageBox.clear();
//                if (reply.equals("logout")) {
//                    cout.println("logout");
//                    break;
//                }
//                cout.println(message + reply);
//            } while (!reply.equals("logout"));
        } catch (Exception e) {
            System.err.println(e.getStackTrace());
        }
    }


    public void doSomething() {
        synchronized (this) {                        //TODO 两边互相弄这种static
            while (!done) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // handle exception
                }
            }
            // do something
        }
    }

//    public void buttonClicked() {
//        synchronized (this) {
//            done = true;
//            notify();
//            done = false;
//        }
//    }
    public void addANewLine(String message){
        System.out.println(message);
        allMessage.appendText(message);
        allMessage.appendText("\n");
    }
    public void buttonClicked(){
        String message = (name + " : ");
        reply = messageBox.getText();
        messageBox.clear();
        if (reply.equals("logout")) {
            cout.println("logout");
        }else{
            cout.println(message + reply);
        }

    }
    public void setname(String name){
//        System.out.println(gtn.getText());
        gtn.setText(name);
    }
}
