/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package alemanpluming;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class AlemanPluming extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("principal.fxml"));
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        Duration duracion = Duration.seconds(5);
        KeyFrame keyFrame = new KeyFrame(duracion, event -> {
            // Crear la segunda escena desde el archivo FXML
            try {
                Parent segundaEscena = FXMLLoader.load(getClass().getResource("segunda.fxml"));
                Screen screen = Screen.getPrimary();
                double width = screen.getVisualBounds().getWidth();
                double height = screen.getVisualBounds().getHeight();

                // Establecer el tamaño de la ventana al tamaño de la pantalla
                stage.setWidth(width);
                stage.setHeight(height);
                Scene sc = new Scene(segundaEscena);
                stage.setScene(sc);
                stage.centerOnScreen();

            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

        Timeline timeline = new Timeline(keyFrame);
        timeline.play();

    }

}
