package src;
import org.json.simple.JSONObject;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    public static boolean connection_state = false;

    public static void main(String[] args){
        connect();
//        while (!connection_state) {
//            connect();
//            try {
//                Thread.sleep(3000);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    private static void connect(){
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            new Thread(new Client_listen(socket,ois)).start();
            new Thread(new Client_send(socket,oos)).start();
//            new Thread(new Client_heart(socket,oos)).start();
        }catch (Exception e){
            e.printStackTrace();
//            connection_state = false;
        }
    }

//    public static void reconnect(){
//        while (!connection_state){
//            System.out.println("正在尝试重新链接.....");
//            connect();
//            try {
//                Thread.sleep(3000);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
}

class Client_listen implements Runnable{
    private Socket socket;
    private ObjectInputStream oiss;

    Client_listen(Socket socket,ObjectInputStream ois){
        this.socket = socket;
        this.oiss = ois;
    }

    @Override
    public void run() {
        try {
            while (true){
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                System.out.println(ois.readObject());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Client_send implements Runnable{
    private Socket socket;
    private ObjectOutputStream oos;

    Client_send(Socket socket, ObjectOutputStream oos){
        this.socket = socket;
        this.oos = oos;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("请输入你要发送的信息：");
                String string = scanner.nextLine();
                JSONObject object = new JSONObject();
                object.put("type","chat");
                object.put("msg",string);
                oos.writeObject(object);
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                socket.close();
                Client.connection_state = false;
//                Client.reconnect();
            }catch (Exception ee){
                ee.printStackTrace();
            }
        }
    }
}

//class Client_heart implements Runnable{
//    private Socket socket;
//    private ObjectOutputStream oos;
//
//    Client_heart(Socket socket, ObjectOutputStream oos){
//        this.socket = socket;
//        this.oos = oos;
//    }
//
//    @Override
//    public void run() {
//        try {
//            System.out.println("心跳包线程已启动...");
//            while (true){
//                Thread.sleep(5000);
//                JSONObject object = new JSONObject();
//                object.put("type","heart");
//                object.put("msg","心跳包");
//                oos.writeObject(object);
//                oos.flush();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            try {
//                socket.close();
//                Client.connection_state = false;
//                Client.reconnect();
//            }catch (Exception ee){
//                ee.printStackTrace();
//            }
//        }
//    }
//}










//package src;
//
//import javafx.fxml.FXML;
//import org.json.simple.JSONObject;
//import org.reactfx.util.Try;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Client {
//    private static Socket socket;
//    public static boolean connection_state = false;
//    @FXML
//    private static TextArea txa;
//
//    public static void main(String[] args){
//        connect();
////        while (!connection_state) {
////            connect();invoke
////            try {
////                Thread.sleep(3000);
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////        }
//    }
//
//    private static void connect(){
//        try {
//            socket = new Socket("127.0.0.1", 9995);
//            connection_state = true;
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//            new Thread(new Client_listen(socket,ois,txa)).start();
////            new Thread(new Client_send(socket,oos)).start();
////            new Thread(new Client_heart(socket,oos)).start();
//        }catch (Exception e){
//            e.printStackTrace();
//            connection_state = false;
//        }
//    }
//    @FXML
//    public void clientaction(){
//        try{
//            ServerSocket serverSocket = new ServerSocket(9995);
//            Socket socket = serverSocket.accept();
//            new Thread(new Server_send(socket)).start();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//
////    public static void reconnect(){
////        while (!connection_state){
////            System.out.println("正在尝试重新链接.....");
////            connect();
////            try {
////                Thread.sleep(3000);
////            }catch (Exception e){
////                e.printStackTrace();
////            }
////        }
////    }
//}
//
//class Client_listen implements Runnable{
//    private Socket socket;
//    private ObjectInputStream ois;
// //   @FXML button cilentbut;
//    private TextArea txa;
//
//    Client_listen(Socket socket,ObjectInputStream ois,TextArea txa){
//        this.socket = socket;
//        this.ois = ois;
//        this.txa=txa;
//    }
//
//    @Override
//    public void run() {
//        try {
////            btn.setOnAction(new EventHandler<ActionEvent>() {
////                @Override
////                public void handle(ActionEvent event) {
////                    System.out.println("Hello World!");
////                }
////            });
//
//            while(true) {                                       //TODO 用Actionlistener
////                    if updated:
////                        this.txa.setText("newtext");
////                (clientbut.setOnAction((ActionEvent a) -> System.out.println("Hello, World!"));)
////                @FXML private JTextArea
////                clientreverse.setText(ois.readObject());           //TODO 输出到gui上
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}
//
//
//
//
//
//
//class Client_send implements Runnable{
//    private Socket socket;
//    private ObjectOutputStream oos;
//
//
//
//
//    Client_send(Socket socket, ObjectOutputStream oos){
//        this.socket = socket;                               //TODO 不知道
//        this.oos = oos;
//    }
//    @FXML private TextArea clienttext;
//
//    @FXML
//    public void clientaction(){                                //TODO 奇奇怪怪，识别不到
//
//        String clientconcent = clienttext.getText();}
//
//    @Override
//    public void run() {
//        try {
//            while (true){
////                System.out.print("请输入你要发送的信息：");
//                JSONObject object = new JSONObject();
////                object.put("type","chat");
//                object.put("msg: ",clienttext);
//                oos.writeObject(object);
//                oos.flush();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            try {
//                socket.close();
//                Client.connection_state = false;
////                Client.reconnect();
//            }catch (Exception ee){
//                ee.printStackTrace();
//            }
//        }
//    }
//}
//
////class Client_heart implements Runnable{
////    private Socket socket;
////    private ObjectOutputStream oos;
////
////    Client_heart(Socket socket, ObjectOutputStream oos){
////        this.socket = socket;
////        this.oos = oos;
////    }
////
////    @Override
////    public void run() {
////        try {
////            System.out.println("心跳包线程已启动...");
////            while (true){
////                Thread.sleep(5000);
////                JSONObject object = new JSONObject();
////                object.put("type","heart");
////                object.put("msg","心跳包");
////                oos.writeObject(object);
////                oos.flush();
////            }
////        }catch (Exception e){
////            e.printStackTrace();
////            try {
////                socket.close();
////                Client.connection_state = false;
////                Client.reconnect();
////            }catch (Exception ee){
////                ee.printStackTrace();
////            }
////        }
////    }
////}
