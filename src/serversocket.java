package src;
import src.messages.Message;
import src.messages.MessageType;
import src.messages.Status;
import src.messages.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Handler;

public class serversocket {
    private static HashSet<ObjectOutputStream> writers =new HashSet<>();
    private static ArrayList<User> users = new ArrayList<>();
    private static final HashMap<String, User> names = new HashMap<>();



    public static void main(String []args) throws Exception{
        System.out.println("server.main is used");
        ServerSocket listener = new ServerSocket(9999);

        try{
            while (true){
                new Handler(listener.accept()).start();
                System.out.println("new one connect");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            listener.close();
        }
    }
    private static  class Handler extends Thread{
        private Socket socket;
        private String name;
        private User user;
        private OutputStream ops;
        private InputStream ips;
        private ObjectInputStream input;
        private ObjectOutputStream output;


        public Handler(Socket socket) throws IOException{
            System.out.println("server.handler is used");
            this.socket = socket;
        }
        public void run(){
            System.out.println("server.run is used");
            try{
                ips = socket.getInputStream();
                input = new ObjectInputStream(ips);
                ops = socket.getOutputStream();
                output = new ObjectOutputStream(ops);

                Message firstmessage = (Message) input.readObject();
                checkDuplicateUsername(firstmessage);
                writers.add(output);                          //在hash set中加入输出的东西
                sendNotification(firstmessage);                //TODO 在login中实现为新来的打招呼
                addToList();                            //TODO 在login中实现为新来的加入到聊天的表中
                while(socket.isConnected()){
                    Message inputmsg = (Message) input.readObject();
                    if (inputmsg != null){
                        switch (inputmsg.getType()){
                            case USER :
                                System.out.println("inputmsg.getType() = USER");
                                write(inputmsg);
                                break;
//                            case VOICE       //TODO 声音
//                                write(inputmsg);
//                                break;
                            case CONNECTED:
                                System.out.println("inputmsg.getType() = CONNECTED");
                                addToList();
                                break;
//                            case STATUS:     //TODO 改变状态
//                                changeStatus(inputmsg);
//                                break;

                        }
                    }
                }


            }catch (SocketException socketException){
                System.out.println("Socket Exception for user" + name);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                closeConnections();           //TODO
            }
        }
        private synchronized void checkDuplicateUsername(Message firstMessage) throws Duplicate {
            System.out.println("server.checkDuplicateUsername is used");
//            logger.info(firstMessage.getName() + " is trying to connect");
            if (!names.containsKey(firstMessage.getName())) {
                this.name = firstMessage.getName();
                user = new User();
                user.setName(firstMessage.getName());
                user.setStatus(Status.ONLINE);
//                user.setPicture(firstMessage.getPicture());
                users.add(user);
                names.put(name, user);
                System.out.println("names = "+ names);

//                logger.info(name + " has been added to the list");
            } else {
//                logger.error(firstMessage.getName() + " is already connected");
                try {
                    throw new Duplicate(firstMessage.getName() + " is already connected");
                } catch (Duplicate e) {
                    throw new RuntimeException(e);
                }
            }
        }
        private Message sendNotification(Message firstMessage) throws IOException {
            System.out.println("server.sendNotification is used");
            Message msg = new Message();
            msg.setMsg("has joined the chat.");
            System.out.println(names + "has joined the chat.");             //TODO 在login中实现为新来的打招呼
            msg.setType(MessageType.NOTIFICATION);
            msg.setName(firstMessage.getName());
//            msg.setPicture(firstMessage.getPicture());
            write(msg);
            return msg;
        }
        private Message addToList() throws IOException {
            System.out.println("server.addToList is used");
            Message msg = new Message();
            msg.setMsg("Welcome, You have now joined the server! Enjoy chatting!");
            System.out.println("Welcome, You have now joined the server! Enjoy chatting!");
            msg.setType(MessageType.CONNECTED);
            msg.setName("SERVER");
            write(msg);
            return msg;
        }
        private void write(Message msg) throws IOException {
            System.out.println("server.write is used");
            for (ObjectOutputStream writer : writers) {
                msg.setUserlist(names);
                msg.setUsers(users);
                msg.setOnlineCount(names.size());
                writer.writeObject(msg);
                writer.reset();
            }

        }
        private Message removeFromList() throws IOException {
            System.out.println("server.removeFromList is used");
            System.out.println("removeFromList() method Enter");
            Message msg = new Message();
            msg.setMsg("has left the chat.");
            msg.setType(MessageType.DISCONNECTED);
            msg.setName("SERVER");
            msg.setUserlist(names);
            write(msg);
//            logger.debug("removeFromList() method Exit");
            return msg;
        }


        private synchronized void closeConnections(){
            System.out.println("server.closeConnections is used");
            if(name != null){
                names.remove(name);
                System.out.println("User: " + name + " has been removed!");
            }
            if(user != null){
                users.remove(output);
                System.out.println("User object: " + user + " has been removed");
            }
            if(output != null){
                writers.remove(output);
                System.out.println("Writer object: " + user + " has been removed!");
            }

            if (ips != null){
                try {
                    ips.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ops != null){
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                removeFromList();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("HashMap names:" + names.size() + " writers:" + writers.size() + " usersList size:" + users.size());
            System.out.println("closeConnections() method Exit");
        }
    }
}
