package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.GUI;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Application{

    private Stage window;

    public static final String ip = "127.0.0.1";
    public static Socket s;


    public static DataInputStream dis;

    public static DataOutputStream dos;

    public static ObjectInputStream ois;

    public static ObjectOutputStream oos;


    public static Socket getClientSocket()
    {
        return s;
    }

    public static void main(String[] args) throws IOException
    {
        s = new Socket(ip, 5056);
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        oos = new ObjectOutputStream(dos);
        ois = new ObjectInputStream(dis);

        System.setProperty("java.awt.headless", "false");
        Application.launch(args);
        try
        {
            Scanner scn = new Scanner(System.in);

            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                String tosend = scn.nextLine();
                if(tosend.equals("Exit"))
                {
                    break;
                }
                String received = dis.readUTF();
            }

            dis.close();
            dos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static DataInputStream getDis(){
        return dis;
    }

    public static DataOutputStream getDos(){
        return dos;
    }

    public static ObjectInputStream getOis(){
        return ois;
    }

    public static ObjectOutputStream getOos(){
        return oos;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClassLoader classLoader = GUI.class.getClassLoader();
        Parent root = FXMLLoader.load(classLoader.getResource("login.fxml"));
        window=primaryStage;

        Scene mainScene = new Scene(root);
        mainScene.getStylesheets().add("Style.css");
        window.setScene(mainScene);
        window.setTitle("Pharmacy App");
        window.show();
    }

    public static Scene changeScene(String fxml) throws IOException{
        ClassLoader classLoader = GUI.class.getClassLoader();
        Parent pane = FXMLLoader.load(classLoader.getResource(fxml));

        Stage stage = new Stage();
        Scene newScene = new Scene(pane);
        newScene.getStylesheets().add("Style.css");
        stage.setScene(newScene);
        stage.show();

        return newScene;
    }
}
