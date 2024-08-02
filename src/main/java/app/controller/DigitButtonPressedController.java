package app.controller;

import app.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DigitButtonPressedController implements EventHandler<KeyEvent> {
    MainView mainView;
    ButtonController buttonController;

    public DigitButtonPressedController(MainView mainView) {
        this.mainView = mainView;
        this.buttonController = new ButtonController(mainView);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.DIGIT0)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "0" : mainView.getTfResult().getText() + "0"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT1)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "1" : mainView.getTfResult().getText() + "1"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "2" : mainView.getTfResult().getText() + "2"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT3)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "3" : mainView.getTfResult().getText() + "3"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT4)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "4" : mainView.getTfResult().getText() + "4"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT5)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "5" : mainView.getTfResult().getText() + "5"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT6)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "6" : mainView.getTfResult().getText() + "6"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT7)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "7" : mainView.getTfResult().getText() + "7"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT8)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "8" : mainView.getTfResult().getText() + "8"
            );
        } else if (keyEvent.getCode().equals(KeyCode.DIGIT9)) {
            mainView.getTfResult().setText(
                    mainView.getTfResult().getText().equals("0") ? "9" : mainView.getTfResult().getText() + "9"
            );
        } else if (keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.EQUALS)) {
            if(check()) {
                return;
            }else {
                mainView.getTfResult().setText(mainView.getTfResult().getText() + "+");
            }
        } else if (keyEvent.getCode().equals(KeyCode.MINUS)) {
            if(check()) {
                return;
            }else {
                mainView.getTfResult().setText(mainView.getTfResult().getText() + "-");
            }
        } else if (keyEvent.getCode().equals(KeyCode.SLASH)) {
            if(check()) {
                return;
            }
            else {
                mainView.getTfResult().setText(mainView.getTfResult().getText() + "/");
            }
        } else if (keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.ENTER)) {
            buttonController.handleEqualsAction();
        } else if(keyEvent.isShiftDown() && keyEvent.getCode().equals((KeyCode.DIGIT8))) {
            System.out.println("Prosao");
        }
    }

    private boolean check() {
        if(mainView.getTfResult().getText().endsWith("+") ||
                mainView.getTfResult().getText().endsWith("-") ||
                mainView.getTfResult().getText().endsWith("*") ||
                mainView.getTfResult().getText().endsWith("/") ||
                mainView.getTfResult().getText().endsWith(".")) {
            return true;
        }else {
            return false;
        }
    }
}
