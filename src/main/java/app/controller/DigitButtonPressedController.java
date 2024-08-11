package app.controller;

import app.view.MainView;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class DigitButtonPressedController implements EventHandler<KeyEvent> {
    MainView mainView;
    ButtonController buttonController;

    public DigitButtonPressedController(MainView mainView) {
        this.mainView = mainView;
        this.buttonController = new ButtonController(mainView);
        this.mainView.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyPress);
    }

    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.isShiftDown() && keyEvent.getCode().equals(KeyCode.DIGIT8)) {
            appendOperator("*");
            keyEvent.consume();
        }
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case DIGIT0 -> appendDigit("0");
            case DIGIT1 -> appendDigit("1");
            case DIGIT2 -> appendDigit("2");
            case DIGIT3 -> appendDigit("3");
            case DIGIT4 -> appendDigit("4");
            case DIGIT5 -> appendDigit("5");
            case DIGIT6 -> appendDigit("6");
            case DIGIT7 -> appendDigit("7");
            case DIGIT8 -> appendDigit("8");
            case DIGIT9 -> appendDigit("9");
            case EQUALS -> {
                if (keyEvent.isShiftDown()) {
                    appendOperator("+");
                }
            }
            case MINUS -> appendOperator("-");
            case SLASH -> appendOperator("/");
            case ENTER -> {
                if (keyEvent.isShiftDown()) {
                    buttonController.handleEqualsAction();
                }
            }
            case BACK_SPACE -> handleBackspace();
            default -> {}
        }
    }

    private void appendDigit(String digit) {
        String currentText = mainView.getTfResult().getText();
        mainView.getTfResult().setText(
                currentText.equals("0") ? digit : currentText + digit
        );
    }

    private void appendOperator(String operator) {
        if (!check()) {
            mainView.getTfResult().setText(mainView.getTfResult().getText() + operator);
        }
    }

    private void handleBackspace() {
        String currentText = mainView.getTfResult().getText();
        if (currentText.length() == 1 || currentText.equals("0")) {
            mainView.getTfResult().setText("0");
        } else {
            mainView.getTfResult().setText(currentText.substring(0, currentText.length() - 1));
        }
    }

    private boolean check() {
        String currentText = mainView.getTfResult().getText();
        return currentText.endsWith("+") || currentText.endsWith("-") ||
                currentText.endsWith("*") || currentText.endsWith("/") ||
                currentText.endsWith(".");
    }
}
