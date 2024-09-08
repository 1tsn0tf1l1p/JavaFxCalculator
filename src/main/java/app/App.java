package app;

import app.model.Database;
import app.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Database.getInstance();
        MainView root = new MainView();
        root.setId("mainVBox");
        String css = this.getClass().getResource("/mainstyle.css").toExternalForm();
        root.getStylesheets().add(css);
        Scene mainScene = new Scene(root, 640, 500);
        stage.setScene(mainScene);
        Image icon = new Image("/calculator.png");
        stage.getIcons().add(icon);
        stage.setTitle("Calculator");
        stage.show();
        root.requestFocus();
    }
}
