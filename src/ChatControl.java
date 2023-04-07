package src;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.awt.*;

public class ChatControl {
    String a="";
    @FXML private Label gtn;
    public ChatControl(){


    }

    public void setname(String st){
        gtn.setText(st);
    }
}
