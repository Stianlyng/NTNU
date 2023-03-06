package com.example.restservice;

import java.io.IOException;
import java.util.Stack;

public class Calculator {
    public double evaluate(String expression) throws IOException {
        // Remove any whitespace from the expression.
        expression = expression.replaceAll("\\s+","");

        // Create a stack to hold the operands.
        Stack<Double> operands = new Stack<>();

        // Create a stack to hold the operators.
        Stack<Character> operators = new Stack<>();

        // Iterate through the expression one character at a time.
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the character is a digit, push it onto the operand stack.
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                sb.append(c);

                // Keep consuming digits until we reach the end of the number.
                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    sb.append(expression.charAt(i + 1));
                    i++;
                }

                double operand = Double.parseDouble(sb.toString());
                operands.push(operand);
            }
            // If the character is an operator, push it onto the operator stack.
            else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.empty() && hasPrecedence(c, operators.peek())) {
                    applyOperation(operands, operators.pop());
                }
                operators.push(c);
            }
            // If the character is a left parenthesis, push it onto the operator stack.
            else if (c == '(') {
                operators.push(c);
            }
            // If the character is a right parenthesis, apply all operators up to the matching left parenthesis.
            else if (c == ')') {
                while (!operators.empty() && operators.peek() != '(') {
                    applyOperation(operands, operators.pop());
                }

                if (operators.empty() || operators.peek() != '(') {
                    throw new IOException("Mismatched parentheses.");
                }

                operators.pop();
            }
            else {
                throw new IOException("Invalid character: " + c);
            }
        }

        // Apply any remaining operators to the operands.
        while (!operators.empty()) {
            applyOperation(operands, operators.pop());
        }

        // The final result is the last operand on the stack.
        if (operands.size() != 1) {
            throw new IOException("Invalid expression.");
        }

        return operands.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private void applyOperation(Stack<Double> operands, char operator) throws IOException {
        if (operands.size() < 2) {
            throw new IOException("Invalid expression.");
        }

        double operand2 = operands.pop();
        double operand1 = operands.pop();

        switch (operator) {
            case '+':
                operands.push(operand1 + operand2);
                break;
            case '-':
                operands.push(operand1 - operand2);
                break;
            case '*':
                operands.push(operand1 * operand2);
                break;
            case '/':
                if (operand2 == 0) {
                    throw new IOException("Division by zero.");
                }
                operands.push(operand1 / operand2);
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        System.out.println(calculator.evaluate("1 + 2 * 3"));
        System.out.println(calculator.evaluate("1 + 2 * 3 + 4"));
    }
}