package src;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXML;
import javafx.application.Platform;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//import com.client.chatwindow.ChatController;
//import com.client.chatwindow.Listener;                       //TODO
//import com.client.util.ResizeHelper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Stage;
import java.awt.*;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.plaf.synth.SynthTextAreaUI;


public class outquiz {
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "@Yht88319375";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/chat";

    @FXML private TextField usernameinput;
    @FXML private TextField passwordsinput;
    @FXML private Label warning;
    @FXML private Label name;

    @FXML
    public void quiz(){
        Platform.exit();
    }

    private  String user;
    private  String password;
    private ResultSet resultSet;
    private PreparedStatement pstmt;
    private boolean flag = false;
    private Stage primaryStage;
    public ChatControl control;

//    public static ChatController con;          //TODO
    @FXML
    public void login() throws ClassNotFoundException, SQLException, IOException {
        user = usernameinput.getText();
        password = passwordsinput.getText();
//        System.out.println(user + "     " + password);


        Class.forName(DRIVER);

        Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

        if (conn == null) {
//            System.out.println("失败.....");
        } else {
//            System.out.println("成功");
            Statement statement = conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM admin");
            while(resultSet.next()){
                String numberuser = resultSet.getString(1);
//                String number = resultSet.getString(2);
                String numberpass = resultSet.getString(2);
                if (user.equals(numberuser) && password.equals(numberpass)){
                    System.out.println("you have signed up.....loading....");
                    flag = true;
                } else if (user.equals(numberuser) && !password.equals(numberpass)) {
                    System.out.println("wrong password!!!!");
                }
            }

            if(flag){
                System.out.println("login......");
                warning.setText("loging");                             //TODO  改字
                primaryStage = new Stage();
                        // 加载fxml文件
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("window.fxml"));

//                        name.setText("asdasd");
                        Parent root = (Parent) loader.load();

                        ChatControl ctc=loader.getController();
                        ctc.setname("asdihdfiuh");                                 //TODO
//                        controller = loader.getController();
                        Scene scene = new Scene(root);
                        primaryStage.setResizable(false);
                        primaryStage.setScene(scene);
                        primaryStage.show();
                        primaryStage.show();
//                        name.setText("asdasd");                                 //TODO 怎么会这里的name联系不是上面的@FXML呢





//                }

            }else {                                                                 //TODO
                warning.setText("sign in ing");
                String sql1 = "INSERT INTO admin (tableuser, tablepass) VALUES ('" + user + "','" + password + "')";
                statement.executeUpdate(sql1);
                System.out.println("create new people");
            }
            //FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("/src/window.fxml"));
//                Parent window = (Pane) fmxlLoader.load();
//                con = fmxlLoader.<ChatController>getController();
//                Listener listener = new Listener(hostname, port, username, picture, con);
//                Thread x = new Thread(listener);
//                x.start();
//                this.scene = new Scene(window);


//            try {
//                Thread.sleep(secondsToSleep * 1000);
//            } catch (InterruptedException ie) {
//                Thread.currentThread().interrupt();
//            }


//            String sql = "INSERT INTO people VALUES('zhangsan', 456456456)";
        }































//        try{
//
////            System.out.println(456);
//            String sql = "select * from `admin` where username = ? and password = ?";
//            List<Object> params = new ArrayList<>();
//            params.add(user);
//            params.add(password);
//            Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setObject(1, params.get(0));
//            pstmt.setObject(2, params.get(1));
//            resultSet = pstmt.executeQuery();
//            ResultSetMetaData metaData = resultSet.getMetaData();
//            int col = metaData.getColumnCount();
//            int counter=0;
//            while (resultSet.next()) {
//                String contextname = resultSet.getString(0);
//                String contextpass = resultSet.getString(2);
//                System.out.println(contextname);
//                System.out.println(contextpass);
//
//                counter++;
//                System.out.print("ID: " + resultSet.getInt("id"));
//            }
//            if (counter==0){
//                System.out.println("Either password or username is incorrect.");
//            }else{
//                System.out.println("It worked!!!!!");
//
//            }
//
//            System.out.println("Connected database successfully...");
//        } catch (SQLException e) {
////            System.out.println(789);
//            e.printStackTrace();
//        }

        //检查mysql是否正常连接
//
//
//
//

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("xxx.fxml"));
//        String myTextName = (String) loader.getNamespace().get("myText");
//        System.out.print(user);
//        // 加载 JDBC 驱动程序
//        Statement mysql;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//// 创建一个 Connection 对象，用于从数据库中检索和更新数据
//        Connection con = null;
//        try {
//            con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//// 执行查询，获取 ResultSet 对象
//        String query = "SELECT * FROM admin";
//        mysql = con.createStatement();
//        resultSet = mysql.executeQuery(query);
//        while (resultSet.next()){
//            String numberuser = resultSet.getString(0);
//            String number = resultSet.getString(1);
//            String numberpass = resultSet.getString(2);
//            System.out.println(numberuser);
//            System.out.println(number);
//            System.out.println(numberpass);
//        }

////        PreparedStatement stmt = null;
////        try {
////            stmt = con.prepareStatement(query);
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////        ResultSet rs = null;
////        try {
////            rs = stmt.executeQuery();
////        } catch (SQLException e) {
////            throw new RuntimeException(e);
////        }
////
////// 从 ResultSet 中检索结果，并在 JavaFX GUI 中显示
////        while(true) {
////            try {
////                if (!rs.next()) break;
////            } catch (SQLException e) {
////                throw new RuntimeException(e);
////            }
////            String username = null;
////            try {
////                username = rs.getString("username");
////            } catch (SQLException e) {
////                throw new RuntimeException(e);
////            }
////            String fullname = null;
////            try {
////                fullname = rs.getString("fullname");
////            } catch (SQLException e) {
////                throw new RuntimeException(e);
////            }
////            System.out.println("Username: " + username + ", Full Name: " + fullname);
////        }
//
//// 关闭数据库连接
//        try {
//            mysql.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            con.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

}
//