import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;

public class Index extends Application {
    private static Stage primaryStageObj;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStageObj = primaryStage;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL resource = getClass().getResource("view/LoginView.fxml");
            Parent pane = (Pane)loader.load(resource);
            Scene mainScene = new Scene(pane, 480, 555);
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Socket Chat");
//            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            //监听窗口关闭
//            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    System.out.print("监听到窗口关闭");
//                }
//            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStageObj;
    }
}
