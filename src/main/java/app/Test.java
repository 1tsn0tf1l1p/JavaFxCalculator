package app;

import java.util.Stack;

public class Test {
    public static void main(String[] args) {

        calc(prefixToPostfix("20"));

    }

    private static String prefixToPostfix(String expression) {

        Stack<Character> stack = new Stack<>();

        String[] expressionNumbers = expression.split("[+\\-*/]");
        char[] expressionArray = expression.toCharArray();

        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        String num = "";
        for (char c : expressionArray) {
            if(c == '+' || c == '-' || c == '*' || c == '/') {
                stringBuilder.append(num).append(",");
                num = "";
                if(!stack.isEmpty() && check(c, stack.peek()) <= 0) {
                    while(!stack.isEmpty()) {
                        stringBuilder.append(stack.pop()).append(",");
                    }
                    stack.push(c);
                }
                else if(!stack.isEmpty() && check(c, stack.peek()) > 0) {
                    stack.push(c);
                }
                else if(stack.isEmpty()) {
                    stack.push(c);
                }
            }
            else {
                num = num + c;
            }
        }

        stringBuilder.append(num).append(",");
        while(!stack.isEmpty()) {
            stringBuilder.append(stack.pop()).append(",");
        }

        return stringBuilder.toString();
    }


    private static int check(char currentOperator, char stackOperator) {
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

    private static void calc(String expression) {
        // 1.0,2.0,*,3.0,*,4.0,*,5.0,*,2.0,4.0,/,+,9.0,-,24.0,4.0,*,+,
        Stack<Double> stack = new Stack<>();
        String[] expressionArray = expression.split(",");

        for (String s : expressionArray) {
            try {
                stack.push(Double.parseDouble(s));
            }catch (Exception e) {
                double num2 = stack.pop();
                double num1 = stack.pop();
                stack.push(izracunaj(num1, num2, s));
            }
        }

        System.out.println(stack);
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
