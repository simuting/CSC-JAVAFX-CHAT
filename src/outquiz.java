package src;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    private static final String URL = "jdbc:mysql://localhost:3306/newsSystem?allowPublicKeyRetrieval=true&useSSL=false&characterEncoding=utf-8";

    @FXML private TextField usernameinput;
    @FXML private TextField passwordsinput;

    @FXML
    public void quiz(){
        Platform.exit();
//        user = usernameinput.getText();
//        password = passwordsinput.getText();
//        System.err.print(user + password);
    }

    private  String user;
    private  String password;
    private ResultSet resultSet;
    private PreparedStatement pstmt;

    @FXML
    public void login(){
        user = usernameinput.getText();
        password = passwordsinput.getText();
//        System.out.println(user + password);

        try{

//            System.out.println(456);
            String sql = "select * from `admin` where username = ? and password = ?";
            List<Object> params = new ArrayList<>();
            params.add(user);
            params.add(password);
            Connection conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, params.get(0));
            pstmt.setObject(2, params.get(1));
            resultSet = pstmt.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int col = metaData.getColumnCount();
            int counter=0;
            while (resultSet.next()) {
                counter++;
                System.out.print("ID: " + resultSet.getInt("id"));
            }
            if (counter==0){
                System.out.println("Either password or username is incorrect.");
            }else{
                System.out.println("It worked!!!!!");

            }

            System.out.println("Connected database successfully...");
        } catch (SQLException e) {
//            System.out.println(789);
            e.printStackTrace();
        }
//        System.out.println(0000);





////        FXMLLoader loader = new FXMLLoader(getClass().getResource("xxx.fxml"));
////        String myTextName = loader.getNamespace().get("myText");
////        System.out.print(user);
//        // 加载 JDBC 驱动程序
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//// 创建一个 Connection 对象，用于从数据库中检索和更新数据
//        Connection con = null;
//        try {
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "password");
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//// 执行查询，获取 ResultSet 对象
//        String query = "SELECT * FROM users";
//        PreparedStatement stmt = null;
//        try {
//            stmt = con.prepareStatement(query);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        ResultSet rs = null;
//        try {
//            rs = stmt.executeQuery();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//// 从 ResultSet 中检索结果，并在 JavaFX GUI 中显示
//        while(true) {
//            try {
//                if (!rs.next()) break;
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            String username = null;
//            try {
//                username = rs.getString("username");
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            String fullname = null;
//            try {
//                fullname = rs.getString("fullname");
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Username: " + username + ", Full Name: " + fullname);
//        }
//
//// 关闭数据库连接
//        try {
//            stmt.close();
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

