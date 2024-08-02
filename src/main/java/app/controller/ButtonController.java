package app.controller;

import app.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.Stack;

public class ButtonController implements EventHandler<ActionEvent> {
    private MainView mainView;

    public ButtonController(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        Button sourceButton = (Button) actionEvent.getSource();
        String buttonText = sourceButton.getText();
        TextField tfResult = mainView.getTfResult();

        switch (buttonText) {
            case "AC":
                tfResult.setText("0");
                break;
            case "+/-":
                toggleSign(mainView.getTfResult());
                break;
            case "%":
                percentage(mainView.getTfResult());
                break;
            case "=":
                equals(mainView.getTfResult());
                break;
            case "+":
                if(tfResult.getText().endsWith("+") ||
                        tfResult.getText().endsWith("-") ||
                        tfResult.getText().endsWith("*") ||
                        tfResult.getText().endsWith("/")) {
                    return;
                }
                tfResult.setText(tfResult.getText() + "+");
                break;
            case "-":
                if(tfResult.getText().endsWith("+") ||
                        tfResult.getText().endsWith("-") ||
                        tfResult.getText().endsWith("*") ||
                        tfResult.getText().endsWith("/")) {
                    return;
                }
                tfResult.setText(tfResult.getText() + "-");
                break;
            case "*":
                if(tfResult.getText().endsWith("+") ||
                        tfResult.getText().endsWith("-") ||
                        tfResult.getText().endsWith("*") ||
                        tfResult.getText().endsWith("/")) {
                    return;
                }
                tfResult.setText(tfResult.getText() + "*");
                break;
            case "/":
                if(tfResult.getText().endsWith("+") ||
                        tfResult.getText().endsWith("-") ||
                        tfResult.getText().endsWith("*") ||
                        tfResult.getText().endsWith("/")) {
                    return;
                }
                tfResult.setText(tfResult.getText() + "/");
                break;
            case ".":
                if(tfResult.getText().endsWith("+") ||
                        tfResult.getText().endsWith("-") ||
                        tfResult.getText().endsWith("*") ||
                        tfResult.getText().endsWith("/")) {
                    return;
                }
                tfResult.setText(tfResult.getText() + ".");
            default:
                appendText(tfResult, buttonText);
        }
    }

    public void handleEqualsAction() {
        TextField tfResult = mainView.getTfResult();
        String expression = prefixToPostfix(tfResult.getText());
        double result = calc(expression);
        if(result == (long) result) {
            mainView.getTfResult().setText("" + (long) result);
        }else {
            mainView.getTfResult().setText(""+result);
        }
    }

    private void appendText(TextField tfResult, String buttonText) {
        if(tfResult.getText().equals("0")) {
            tfResult.setText(buttonText);
        }else {
            tfResult.setText(tfResult.getText() + buttonText);
        }
    }

    private void equals(TextField tfResult) {
        String expression = prefixToPostfix(tfResult.getText());
        double result = calc(expression);
        if(result == (long) result) {
            mainView.getTfResult().setText("" + (long) result);
        }else {
            mainView.getTfResult().setText(""+result);
        }
    }

    private void toggleSign(TextField tfResult) {
        if(tfResult.getText().startsWith("-")) {
            tfResult.setText(tfResult.getText().substring(1));
        }else {
            tfResult.setText("-" + tfResult.getText());
        }
    }

    private void percentage(TextField tfResult) {
        if(tfResult.getText().endsWith("+") ||
                tfResult.getText().endsWith("-") ||
                tfResult.getText().endsWith("*") ||
                tfResult.getText().endsWith("/")) {
            return;
        }
        String expression = prefixToPostfix(tfResult.getText());
        double percent = calc(expression)/100;
        if(percent == (long) percent) {
            tfResult.setText("" + (long) percent);
        }else {
            tfResult.setText(""+percent);
        }
    }

    private String prefixToPostfix(String expression) {
        if(checkExpression(expression)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("The expression could not end with an operator!");
            a.setTitle("Invalid expression syntax");
            a.show();
        }else {
            Stack<Character> stack = new Stack<>();
            StringBuilder stringBuilder = new StringBuilder();

            char[] expressionArray = expression.toCharArray();
            String num = "";
            boolean negative = false;

            for (char c : expressionArray) {
                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    if (num.length() > 0) {
                        stringBuilder.append(num).append(",");
                        num = "";
                    }
                    if (c == '-' && (stringBuilder.length() == 0 || expressionArray[stringBuilder.length() - 1] == ',')) {
                        negative = true;
                    } else {
                        while (!stack.isEmpty() && check(c, stack.peek()) <= 0) {
                            stringBuilder.append(stack.pop()).append(",");
                        }
                        stack.push(c);
                    }
                } else {
                    if (negative) {
                        num += '-';
                        negative = false;
                    }
                    num += c;
                }
            }

            if (num.length() > 0) {
                stringBuilder.append(num).append(",");
            }
            while (!stack.isEmpty()) {
                stringBuilder.append(stack.pop()).append(",");
            }

            return stringBuilder.toString();
        }
        return "";
    }


    private int check(char currentOperator, char stackOperator) {
        int current;
        int stack;
        if(currentOperator == '+' || currentOperator == '-') {
            current = 0;
        }else {
            current = 1;
        }
        if(stackOperator == '+' || stackOperator == '-') {
            stack = 0;
        }else {
            stack = 1;
        }
        return current-stack;
    }

    private boolean checkExpression(String expression) {
        if(expression.endsWith("+") ||
                expression.endsWith("-") ||
                expression.endsWith("*") ||
                expression.endsWith("/") ||
                expression.endsWith(".")) {
            return true;
        }else {
            return false;
        }
    }

    private double calc(String expression) {
            Stack<Double> stack = new Stack<>();
            String[] expressionArray = expression.split(",");

            for (String s : expressionArray) {
                try {
                    stack.push(Double.parseDouble(s));
                } catch (Exception e) {
                    double num2 = stack.pop();
                    double num1 = stack.pop();
                    stack.push(izracunaj(num1, num2, s));
                }
            }

            return stack.pop();
    }

        private static double izracunaj(double num1, double num2, String operator) {
            switch (operator) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                default:
                    return num1 / num2;
            }
        }
}
