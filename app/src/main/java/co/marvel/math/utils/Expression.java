package co.marvel.math.utils;

import java.util.ArrayList;
import java.util.Stack;

public class Expression {
    private String expression;

    public Expression(String expression) {
        this.expression = expression;
    }

    public Boolean isInvalid(String input) {
        return input.matches("(.*)\\(\\s*?[-+/*]\\s+\\d+(.*)") ||
                input.matches("^[-+/*]\\s*\\d+(.*)") ||
                input.matches("^[-+/*]\\s*\\((.*)");
    }

    public int calculate() {
        expression = expression.replaceAll(" ", "");
        Stack<String> stack = new Stack<>();
        char[] arrayOfLetters = expression.toCharArray();
        StringBuilder finalExpression = new StringBuilder();
        for (int i = 0; i < arrayOfLetters.length; i++) {
            if (arrayOfLetters[i] == ' ') {
                continue;
            }

            if (arrayOfLetters[i] >= '0' && arrayOfLetters[i] <= '9') {
                finalExpression.append(arrayOfLetters[i]);
                if (i == arrayOfLetters.length - 1) {
                    stack.push(finalExpression.toString());
                }
            } else {
                if (finalExpression.length() > 0) {
                    stack.push(finalExpression.toString());
                    finalExpression = new StringBuilder();
                }
                if (arrayOfLetters[i] != ')') {
                    stack.push(new String(new char[]{arrayOfLetters[i]}));
                } else {
                    ArrayList<String> letters = new ArrayList<>();
                    while (!stack.isEmpty()) {
                        String top = stack.pop();
                        if (top.equals("(")) {
                            break;
                        } else {
                            letters.add(0, top);
                        }
                    }
                    int temporal;
                    if (letters.size() == 1) {
                        temporal = Integer.valueOf(letters.get(0));
                    } else {
                        temporal = doMath(letters);
                    }
                    stack.push(String.valueOf(temporal));
                }
            }
        }
        ArrayList<String> t = new ArrayList<>();
        while (!stack.isEmpty()) {
            String elem = stack.pop();
            t.add(0, elem);
        }
        return doMath(t);

    }

    private Integer doMath(ArrayList<String> letters) {
        int temporal = 0;
        boolean flag = false;
        for (int j = letters.size() - 1; j > 0; j = j - 2) {
            switch (letters.get(j - 1)) {
                case "-":
                    flag = true;
                    temporal += 0 - Integer.valueOf(letters.get(j));
                    break;
                case "*":
                    temporal += Integer.valueOf(letters.get(j)) * Integer.valueOf(letters.get(j - 2));
                    break;
                case "/":
                    if (Integer.valueOf(letters.get(j - 2)) > 0) {
                        temporal +=  Integer.valueOf(letters.get(j - 2)) / Integer.valueOf(letters.get(j));
                    }
                    break;
                default:
                    flag = true;
                    temporal += Integer.valueOf(letters.get(j));
                    break;
            }
        }
        if (flag) {
            try {
                temporal += Integer.valueOf(letters.get(0));
            } catch (NumberFormatException error) {
                System.out.println(error.getLocalizedMessage());
            }
        }
        return temporal;
    }
}
