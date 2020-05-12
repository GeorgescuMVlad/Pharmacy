package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import main.AlertBox;
import main.GUI;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;

public class ControllerLogin {

    @FXML
    public Button loginButton;

    @FXML
    public TextField mail;

    @FXML
    public TextField password;

    @FXML
    public TextField role;


    public static UsernamePasswordAuthenticationToken authReq;
    public void login() throws IOException {

        if (mail.getText().equals("") || password.getText().equals("") || role.getText().equals(""))
            AlertBox.display("No input", "You forgot to write your mail/password/role");
        else if (!role.getText().equals("admin") && !role.getText().equals("user")) {
            AlertBox.display("Wrong role", "Insert admin for admin role or user for user/player role");
        } else {
            if (role.getText().equals("user")) {
                authReq = new UsernamePasswordAuthenticationToken(mail.getText(), password.getText());
                Scene scene = GUI.changeScene("user.fxml");
            } else {
                Scene scene = GUI.changeScene("admin.fxml");
            }
        }
    }







}
