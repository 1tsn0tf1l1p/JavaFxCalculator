package app.controller;

import app.model.Database;
import app.model.Result;
import app.view.MainView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
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
            case "-":
            case "*":
            case "/":
                if (checkExpression(tfResult.getText())) {
                    return;
                }
                tfResult.setText(tfResult.getText() + buttonText);
                break;
            case ".":
                if (checkExpression(tfResult.getText())) {
                    return;
                }
                tfResult.setText(tfResult.getText() + ".");
                break;
            case "C":
                String currentText = mainView.getTfResult().getText();
                if (currentText.length() == 1 || currentText.equals("0")) {
                    mainView.getTfResult().setText("0");
                } else {
                    mainView.getTfResult().setText(currentText.substring(0, currentText.length() - 1));
                }
                break;
            case "sin":
                trigonometric(tfResult, "sin");
                break;
            case "cos":
                trigonometric(tfResult, "cos");
                break;
            case "tan":
                trigonometric(tfResult, "tan");
                break;
            case "cot":
                trigonometric(tfResult, "cot");
                break;
            default:
                appendText(tfResult, buttonText);
        }
    }

    private void trigonometric(TextField tfResult, String function) {
        double value = Math.toRadians(Double.parseDouble(tfResult.getText()));
        double result = 0;
        switch (function) {
            case "sin":
                result = Math.sin(value);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sin(").append(tfResult.getText()).append(")");
                LocalDateTime localDateTime = LocalDateTime.now();
                Result r = new Result(stringBuilder.toString(), BigDecimal.valueOf(result), localDateTime);
                System.out.println(r);
                Database.getInstance().write(r);
                tfResult.setText(String.valueOf(result));
                return;
            case "cos":
                result = Math.cos(value);
                StringBuilder stringBuilder1 = new StringBuilder();
                stringBuilder1.append("cos(").append(tfResult.getText()).append(")");
                LocalDateTime localDateTime1 = LocalDateTime.now();
                Result r1 = new Result(stringBuilder1.toString(), BigDecimal.valueOf(result), localDateTime1);
                System.out.println(r1);
                Database.getInstance().write(r1);
                tfResult.setText(String.valueOf(result));
                return;
            case "tan":
                result = Math.tan(value);
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("tan(").append(tfResult.getText()).append(")");
                LocalDateTime localDateTime2 = LocalDateTime.now();
                Result r2 = new Result(stringBuilder2.toString(), BigDecimal.valueOf(result), localDateTime2);
                Database.getInstance().write(r2);
                System.out.println(r2);

                tfResult.setText(String.valueOf(result));
                return;
            case "cot":
                result = 1.0/Math.tan(value);
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("cot(").append(tfResult.getText()).append(")");
                LocalDateTime localDateTime3 = LocalDateTime.now();
                Result r3 = new Result(stringBuilder3.toString(), BigDecimal.valueOf(result), localDateTime3);
                System.out.println(r3);
                Database.getInstance().write(r3);
                tfResult.setText(String.valueOf(result));
        }

    }

    public void handleEqualsAction() {
        TextField tfResult = mainView.getTfResult();
        String expression = prefixToPostfix(tfResult.getText());
        BigDecimal result = calc(expression);
        LocalDateTime localDateTime = LocalDateTime.now();
        Result r = new Result(tfResult.getText(), result, localDateTime);
        System.out.println(r);
        Database.getInstance().write(r);
        tfResult.setText(result.stripTrailingZeros().toPlainString());
    }

    private void appendText(TextField tfResult, String buttonText) {
        if (tfResult.getText().equals("0")) {
            tfResult.setText(buttonText);
        } else {
            tfResult.setText(tfResult.getText() + buttonText);
        }
    }

    private void equals(TextField tfResult) {
        String expression = prefixToPostfix(tfResult.getText());
        BigDecimal result = calc(expression);
        LocalDateTime localDateTime = LocalDateTime.now();
        Result r = new Result(tfResult.getText(), result, localDateTime);
        System.out.println(r);
        Database.getInstance().write(r);
        tfResult.setText(result.stripTrailingZeros().toPlainString());
    }

    private void toggleSign(TextField tfResult) {
        if (tfResult.getText().startsWith("-")) {
            tfResult.setText(tfResult.getText().substring(1));
        } else {
            tfResult.setText("-" + tfResult.getText());
        }
    }

    private void percentage(TextField tfResult) {
        if (checkExpression(tfResult.getText())) {
            return;
        }
        String expression = prefixToPostfix(tfResult.getText());
        BigDecimal percent = calc(expression).divide(BigDecimal.valueOf(100));
        LocalDateTime localDateTime = LocalDateTime.now();
        Result r = new Result("(" + tfResult.getText() + ")%", percent, localDateTime);
        System.out.println(r);
        Database.getInstance().write(r);
        tfResult.setText(percent.stripTrailingZeros().toPlainString());
    }

    private String prefixToPostfix(String expression) {
        if (checkExpression(expression)) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("The expression could not end with an operator!");
            a.setTitle("Invalid expression syntax");
            a.show();
            return "";
        }

        Stack<Character> stack = new Stack<>();
        StringBuilder stringBuilder = new StringBuilder();

        char[] expressionArray = expression.toCharArray();
        String num = "";
        boolean negative = false;
        boolean expectNumber = true;

        for (char c : expressionArray) {
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                if (num.length() > 0) {
                    stringBuilder.append(num).append(",");
                    num = "";
                }
                if (c == '-' && expectNumber) {
                    negative = true;
                } else {
                    while (!stack.isEmpty() && check(c, stack.peek()) <= 0) {
                        stringBuilder.append(stack.pop()).append(",");
                    }
                    stack.push(c);
                    expectNumber = true;
                }
            } else {
                if (negative) {
                    num += '-';
                    negative = false;
                }
                num += c;
                expectNumber = false;
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

    private int check(char currentOperator, char stackOperator) {
        int current = (currentOperator == '+' || currentOperator == '-') ? 0 : 1;
        int stack = (stackOperator == '+' || stackOperator == '-') ? 0 : 1;
        return current - stack;
    }

    private boolean checkExpression(String expression) {
        return expression.endsWith("+") || expression.endsWith("-") || expression.endsWith("*") || expression.endsWith("/") || expression.endsWith(".");
    }

    private BigDecimal calc(String expression) {
        Stack<BigDecimal> stack = new Stack<>();
        String[] expressionArray = expression.split(",");

        for (String s : expressionArray) {
            try {
                stack.push(new BigDecimal(s));
            } catch (Exception e) {
                BigDecimal num2 = stack.pop();
                BigDecimal num1 = stack.pop();
                stack.push(izracunaj(num1, num2, s));
            }
        }

        return stack.pop();
    }

    private static BigDecimal izracunaj(BigDecimal num1, BigDecimal num2, String operator) {
        switch (operator) {
            case "+":
                return num1.add(num2);
            case "-":
                return num1.subtract(num2);
            case "*":
                return num1.multiply(num2);
            default:
                if(num2.compareTo(BigDecimal.ZERO) == 0) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Division by 0 is impossible!");
                    a.show();
                    return BigDecimal.ZERO;
                }else {
                    BigDecimal result = num1.divide(num2, 20, RoundingMode.HALF_UP);
                    if (result.stripTrailingZeros().scale() > 0) {
                        String resultStr = result.toPlainString();
                        if (resultStr.endsWith("5")) {
                            return result.setScale(1, RoundingMode.DOWN);
                        } else if (result.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) != 0) {
                            return result.setScale(20, RoundingMode.HALF_UP);
                        }
                    }
                    return result;
                }
        }
    }

}
