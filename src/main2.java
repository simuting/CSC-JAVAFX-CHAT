package src;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.image.Image;

public class main2 extends Application {

    private static Stage primaryStageObj;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStageObj = primaryStage;

//        FXMLLoader tmp=new FXMLLoader();
////        tmp.setLocation(Main.class.getResource("shiyong.fxml"));
//        tmp.setLocation(src.Main.class.getResource("shiyong.fxml"));
////        tmp.setLocation(Main.class.getResource("yong.fxml"));//为什么是Main.class
//
//        Parent root = (Parent) tmp.load();                      //什么意思：加载FXML文件并返回根节点（Parent）的方法，返回的根节点就是FXML文件中定义的所有元素的容器。
////        primaryStage.initStyle(StageStyle.UNDECORATED);
//        primaryStage.setTitle("Socket Chat : Client version 0.3");
//        System.out.println(src.Main.class.getClassLoader().getResource("src/pic.png"));
//        primaryStage.getIcons().add(new Image(src.Main.class.getClassLoader().getResource("src/pic.png").toString()));
//        Scene mainScene = new Scene(root); //是创建一个Scene实例，其中root参数指定这个Scene实例的根节点，即FXML文件中定义的所有元素的容器。
////        mainScene.setRoot(root);           //上下两句是不是重复了？
//        primaryStage.setResizable(false);
//        primaryStage.setScene(mainScene);
//        primaryStage.show();



        FXMLLoader tmp=new FXMLLoader();
//        tmp.setLocation(Main.class.getResource("shiyong.fxml"));
//        tmp.setLocation(src.Main.class.getResource("shiyong.fxml"));
        tmp.setLocation(Main.class.getResource("yong.fxml"));//为什么是Main.class

        Parent root = (Parent) tmp.load();                      //什么意思：加载FXML文件并返回根节点（Parent）的方法，返回的根节点就是FXML文件中定义的所有元素的容器。
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Socket Chat : server version 0.3");
        System.out.println(src.Main.class.getClassLoader().getResource("src/pic.png"));
        primaryStage.getIcons().add(new Image(src.Main.class.getClassLoader().getResource("src/pic.png").toString()));
        Scene mainScene = new Scene(root); //是创建一个Scene实例，其中root参数指定这个Scene实例的根节点，即FXML文件中定义的所有元素的容器。
//        mainScene.setRoot(root);           //上下两句是不是重复了？
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }

    public static void main(String[] args){
        launch(args);
    }
}
