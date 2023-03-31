package src;

import org.json.simple.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {



    public static void main(String[] args){

        try {
            System.out.println("Socket服务器开始运行...");
            ServerSocket serverSocket = new ServerSocket(9995);
            while (true){
                System.out.println("listening");
                Socket socket = serverSocket.accept();
                System.out.println("Socket服务器开始运行...");
                new Thread(new Server_listen(socket)).start();
                new Thread(new Server_send(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

class Server_listen implements Runnable{
    private Socket socket;

    Server_listen(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while (true)
                System.out.println(ois.readObject());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

class Server_send implements Runnable{
    private Socket socket;

    Server_send(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.print("请输入要发送的内容：");
                String string = scanner.nextLine();
                JSONObject object = new JSONObject();
                object.put("type","chat");
                object.put("msg",string);
                oos.writeObject(object);
                oos.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

















//package src;
//
//import javafx.fxml.FXML;
//import org.json.simple.JSONObject;
//
//import java.awt.*;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.Scanner;
//
//public class Server {
//
//
//
//    public static void main(String[] args){
//
//        try {
//            System.out.println("Socket服务器开始运行...");
//            ServerSocket serverSocket = new ServerSocket(9995);
//            while (true){
//                System.out.println("listening");
//                Socket socket = serverSocket.accept();
//                System.out.println("Socket服务器开始运行...");
//                new Thread(new Server_listen(socket)).start();
//                new Thread(new Server_send(socket)).start();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
//
//    @FXML
//    public void serveraction(){
//        try{
//            ServerSocket serverSocket = new ServerSocket(9997);
//            Socket socket = serverSocket.accept();
//            new Thread(new Server_send(socket)).start();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//}
//
//class Server_listen implements Runnable{
//    private Socket socket;
//
//    Server_listen(Socket socket){
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                System.out.println(ois.readObject());
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try{
//                socket.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//}
//
//class Server_send implements Runnable{
//    private Socket socket;
//
//    Server_send(Socket socket){
//        this.socket = socket;
//    }
//
////    @FXML private TextArea servertext;
////    private String serverconcent;
////
////    @FXML
////    public void Serveraction(){
////        serverconcent = servertext.getText();
////    }
//
//
//    @Override
//    public void run() {
//        try {
//            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
//            Scanner scanner = new Scanner(System.in);
//            while (true){
////                System.out.print("请输入要发送的内容：");
//                String string = scanner.nextLine();
//                JSONObject object = new JSONObject();
////                object.put("type","chat");
//                object.put("msg",string);
//                oos.writeObject(object);
//                oos.flush();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//}