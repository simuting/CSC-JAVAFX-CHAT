package src;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Paint;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import src.messages.Message;
import src.messages.MessageType;
import src.messages.User;
import src.messages.bubble.BubbleSpec;
import src.messages.bubble.BubbledLabel;
import src.traynotifications.animations.AnimationType;
import src.traynotifications.notification.TrayNotification;
import java.io.IOException;

public class ChatControl {
    public ChatControl(){
    }
    @FXML public Label gtn;
    @FXML ListView chatPane;

    @FXML private Label usernameLabel;
    @FXML private ImageView userImageView;
    @FXML private Label onlineCountLabel;
    @FXML private ListView userList;

    public void setname(String st){
        gtn.setText(st);
    }
    @FXML TextArea messageBox;
    public String getAreaText(){                 //TODO 还有这种用法？

        String areatext = "";
        areatext = messageBox.getText();
        return areatext;
    }

    
    public void sendButtonAction() throws IOException{
        System.out.println("ChatControl.sentButtonAction is used");
        String meg = messageBox.getText();
        if(!messageBox.getText().isEmpty()){
            listen.send(meg);
            messageBox.clear();
        }
    }
    public synchronized void addToChat(Message msg) {
        System.out.println("ChatControl.addToChat is used");
        Task<HBox> othersMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                javafx.scene.image.Image image = new javafx.scene.image.Image(getClass().getClassLoader().getResource("images/" + msg.getPicture().toLowerCase() + ".png").toString());
                ImageView profileImage = new ImageView(image);
                profileImage.setFitHeight(32);
                profileImage.setFitWidth(32);
                BubbledLabel bl6 = new BubbledLabel();
//                if (msg.getType() == MessageType.VOICE){
//                    ImageView imageview = new ImageView(new javafx.scene.image.Image(getClass().getClassLoader().getResource("images/sound.png").toString()));
//                    bl6.setGraphic(imageview);                        //TODO  声音
//                    bl6.setText("Sent a voice message!");
//                    VoicePlayback.playAudio(msg.getVoiceMsg());
//                }else {
                    bl6.setText(msg.getName() + ": " + msg.getMsg());
//                }


                bl6.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.WHITE,null, null)));
                HBox x = new HBox();
                bl6.setBubbleSpec(BubbleSpec.FACE_LEFT_CENTER);
                x.getChildren().addAll(profileImage, bl6);
//                logger.debug("ONLINE USERS: " + Integer.toString(msg.getUserlist().size()));
                setOnlineLabel(Integer.toString(msg.getOnlineCount()));
                return x;
            }
        };

        othersMessages.setOnSucceeded(event -> {
            chatPane.getItems().add(othersMessages.getValue());
        });

        Task<HBox> yourMessages = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                javafx.scene.image.Image image = userImageView.getImage();
                ImageView profileImage = new ImageView(image);
                profileImage.setFitHeight(32);
                profileImage.setFitWidth(32);

                BubbledLabel bl6 = new BubbledLabel();
                if (msg.getType() == MessageType.VOICE){
                    bl6.setGraphic(new ImageView(new Image(getClass().getClassLoader().getResource("images/sound.png").toString())));
                    bl6.setText("Sent a voice message!");
                    VoicePlayback.playAudio(msg.getVoiceMsg());
                }else {
                    bl6.setText(msg.getMsg());
                }
                bl6.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
                HBox x = new HBox();
                x.setMaxWidth(chatPane.getWidth() - 20);
                x.setAlignment(Pos.TOP_RIGHT);
                bl6.setBubbleSpec(BubbleSpec.FACE_RIGHT_CENTER);
                x.getChildren().addAll(bl6, profileImage);

                setOnlineLabel(Integer.toString(msg.getOnlineCount()));
                return x;
            }
        };
        yourMessages.setOnSucceeded(event -> chatPane.getItems().add(yourMessages.getValue()));

        if (msg.getName().equals(usernameLabel.getText())) {
            Thread t2 = new Thread(yourMessages);
            t2.setDaemon(true);
            t2.start();
        } else {
            Thread t = new Thread(othersMessages);
            t.setDaemon(true);
            t.start();
        }
    }
    public void newUserNotification(Message msg) {
        System.out.println("ChatControl.newUserNotification is used");
        Platform.runLater(() -> {
//            Image profileImg = new Image(getClass().getClassLoader().getResource("images/" + msg.getPicture().toLowerCase() +".png").toString(),50,50,false,false);
            TrayNotification tray = new TrayNotification();
            tray.setTitle("A new user has joined!");
            tray.setMessage(msg.getName() + " has joined the JavaFX Chatroom!");
            tray.setRectangleFill(Paint.valueOf("#2C3E50"));
            tray.setAnimationType(AnimationType.POPUP);                   //TODO see
//            tray.setImage(profileImg);

//            tray.showAndDismiss(Duration.seconds(5));
//            try {
//                Media hit = new Media(getClass().getClassLoader().getResource("sounds/notification.wav").toString());   //TODO 有新人来的声音
//                MediaPlayer mediaPlayer = new MediaPlayer(hit);
//                mediaPlayer.play();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        });
    }
    public void setUserList(Message msg) {                                 //将边上的online user list 弄出来
        System.out.println("ChatControl.setUserList is used");
//        logger.info("setUserList() method Enter");
        Platform.runLater(() -> {
            ObservableList<User> users = FXCollections.observableList(msg.getUsers());
            System.out.println();
            System.out.println(users);
            System.out.println();
            userList.setItems(users);
            userList.setCellFactory(new CellRenderer());                   //决定userlist的渲染样式
            setOnlineLabel(String.valueOf(msg.getUserlist().size()));
        });
//        logger.info("setUserList() method Exit");
    }
    public synchronized void addAsServer(Message msg) {
        System.out.println("ChatControl.addAsServer is used");
        Task<HBox> task = new Task<HBox>() {
            @Override
            public HBox call() throws Exception {
                BubbledLabel bl6 = new BubbledLabel();
                bl6.setText(msg.getMsg());
                bl6.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE,
                        null, null)));
                HBox x = new HBox();
                bl6.setBubbleSpec(BubbleSpec.FACE_BOTTOM);
                x.setAlignment(Pos.CENTER);
                x.getChildren().addAll(bl6);
                return x;
            }
        };
        task.setOnSucceeded(event -> {
            chatPane.getItems().add(task.getValue());
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
    public void setOnlineLabel(String usercount) {
        System.out.println("ChatControl.setOnlineLable is used");
        Platform.runLater(() -> onlineCountLabel.setText(usercount));
    }





}
