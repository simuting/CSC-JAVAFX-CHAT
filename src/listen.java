package src;
import src.messages.Message;
//import com.client.login.LoginController;
import src.messages.Message;
import src.messages.MessageType;
import src.messages.Status;
import src.outquiz;
import static src.messages.MessageType.CONNECTED;

import java.io.*;
import java.net.Socket;

public class listen implements Runnable{
//    private String hostname;
    private ChatControl controller;
//    private String hostname;
    public static String username;
//    private static String picture;
    private static ObjectOutputStream oos;
    private InputStream is;
    private ObjectInputStream input;
    private OutputStream outputStream;
    private Socket socket;

    private static final String HASCONNECTED = "has connected";

    public listen(String username, ChatControl controller) {
        System.out.println("listen.listen is used");
//        this.hostname = hostname;
//        this.port = port;
        listen.username = username;
//        listen.picture = picture;
        this.controller = controller;
//        System.out.println("controller:  "+ controller);
    }

    @Override
    public void run() {
        System.out.println("listen.run is used");
        try {
            socket = new Socket("127.0.0.1", 9999);
//            outquiz.getInstance().showScene();
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            is = socket.getInputStream();
            input = new ObjectInputStream(is);
        } catch (IOException e) {
//            LoginController.getInstance().showErrorDialog("Could not connect to server");
            e.printStackTrace();
        }
//        logger.info("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            connect();
//            logger.info("Sockets in and out ready!");
            while (socket.isConnected()) {
                Message message = null;
                message = (Message) input.readObject();

                if (message != null) {
//                    logger.debug("Message recieved:" + message.getMsg() + " MessageType:" + message.getType() + "Name:" + message.getName());
                    switch (message.getType()) {
                        case USER:
                            System.out.println("listen.run.switch in USER");
                            System.out.println(message.getMsg());
//                            System.out.println(controller);
                            controller.addToChat(message);
                            break;
                        case VOICE:
                            System.out.println("listen.run.switch in VOICE");
                            controller.addToChat(message);

                            System.out.println(controller);
                            break;
                        case NOTIFICATION:
                            System.out.println("listen.run.switch in NOTIFICATION");
                            controller.newUserNotification(message);
                            break;
                        case SERVER:
                            System.out.println("listen.run.switch in SERVER");
                            controller.addAsServer(message);              //TODO control
                            break;
                        case CONNECTED:
                            System.out.println("listen.run.switch in CONNECT");
                            controller.setUserList(message);              //在user listview里面展示用户
                            break;
                        case DISCONNECTED:
                            System.out.println("listen.run.switch in DISCONNECT");
                            controller.setUserList(message);
                            break;
                        case STATUS:
                            System.out.println("listen.run.switch in STATUS");
                            controller.setUserList(message);
                            break;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
//            controller.logoutScene();                   //TODO
        }
    }
    public static void send(String msg) throws IOException {
        System.out.println("listen.send is used");
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(MessageType.USER);
        createMessage.setStatus(Status.AWAY);
        createMessage.setMsg(msg);
//        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
        oos.flush();
    }

    /* This method is used for sending a voice Message
     * @param msg - The message which the user generates
     */
    public static void sendVoiceMessage(byte[] audio) throws IOException {
        System.out.println("listen.sendVoiceMessage is used");
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(MessageType.VOICE);
        createMessage.setStatus(Status.AWAY);
        createMessage.setVoiceMsg(audio);
//        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
        oos.flush();
    }

    /* This method is used for sending a normal Message
     * @param msg - The message which the user generates
     */
    public static void sendStatusUpdate(Status status) throws IOException {
        System.out.println("listen.sendStatusUpdate is used");
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(MessageType.STATUS);
        createMessage.setStatus(status);
//        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
        oos.flush();
    }
    public static void connect() throws IOException {
        System.out.println("listen.connect is used");
        Message createMessage = new Message();
        createMessage.setName(username);
        createMessage.setType(CONNECTED);       //TODO check
        createMessage.setMsg(HASCONNECTED);
//        createMessage.setPicture(picture);
        oos.writeObject(createMessage);
    }
}

