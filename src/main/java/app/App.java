package app;

import app.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        MainView root = new MainView();
        Scene mainScene = new Scene(root);
        stage.setScene(mainScene);
        stage.setTitle("Calculator");
        stage.show();
        root.requestFocus();
    }
}
