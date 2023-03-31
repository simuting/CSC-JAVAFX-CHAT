package src;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.awt.*;


public class controller {

    @FXML private TextArea servertext;
    @FXML private TextArea clienttext;
    @FXML
    public void serveraction(){
        String serverconcent = servertext.getText();
    }


    @FXML
    public void clientaction(){
        String clientconcent = clienttext.getText();

    }
}
