

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/giaodien/giaodien.fxml"));
            BorderPane root = loader.load();

            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("Quản lý XML với JavaFX");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(" Lỗi tải giao diện FXML!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
