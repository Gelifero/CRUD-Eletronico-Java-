package com.template;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));

        // Define o título da janela
        primaryStage.setTitle("Sistema de Eletrônicos");

        // >>> ESTA É A LINHA QUE MAXIMIZA A TELA AO RODAR <<<
        primaryStage.setMaximized(true);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}