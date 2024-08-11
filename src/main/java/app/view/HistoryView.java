package app.view;

import app.model.Database;
import app.model.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HistoryView extends VBox {
    private MainView mainView;
    private Stage mainStage;
    private ListView<Result> resultListView;
    private Button btnClose;

    public HistoryView(MainView mainView, Stage mainStage) {
        this.mainView = mainView;
        this.mainStage = mainStage;
        initElements();
        addElements();
        addActions();
        setupBindings();
        connectDatabase();
    }

    private void addActions() {
        btnClose.setOnAction(e -> {
            mainStage.close();
        });
    }

    private void connectDatabase() {
        Database.getInstance().refreshDatabase();
        ObservableList<Result> resultObservableList = FXCollections.observableList(Database.getInstance().getAllResults());
        resultListView.setItems(resultObservableList);
    }

    private void addElements() {
        this.setPadding(new Insets(15));
        this.setSpacing(15);
        this.setAlignment(Pos.CENTER);

        this.getChildren().addAll(resultListView, btnClose);
        VBox.setVgrow(resultListView, Priority.ALWAYS);
    }

    private void initElements() {
        resultListView = new ListView<>();
        btnClose = new Button("Close");
    }

    private void setupBindings() {
        btnClose.prefWidthProperty().bind(this.widthProperty());
    }
}
