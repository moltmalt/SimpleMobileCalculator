package com.example.myapplication;
import java.util.Stack;
public class StackEvaluate {

    public static Stack<Integer> numbers; //stack for numbers
    public static Stack<Character> operations; //stack for operations

    //checks level of precendence, true of secondOp is higher or same precedence as, else false
    public static boolean precedenceChecker(char firstOp, char secondOp) {
        if (secondOp == '(' || secondOp == ')')
            return false;
        return (firstOp != '*' && firstOp != '/') || (secondOp != '+' && secondOp != '-');
    }

    public static int smolEvaluate(char op, int b, int a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero is not permitted");
                return a / b;
        }
        return 0;
    }
    public static int bigEvaluate(String stringToEvaluate) {

         numbers = new Stack<>(); //stack for numbers
         operations = new Stack<>();
        
        char[] toks = stringToEvaluate.toCharArray();

        for (int i = 0; i < toks.length; i++) {
            if (toks[i] >= '0' && toks[i] <= '9') {
                StringBuilder sb = new StringBuilder();
                while (i < toks.length && toks[i] >= '0' && toks[i] <= '9') {
                    sb.append(toks[i++]);
                }
                numbers.push(Integer.parseInt(sb.toString()));
                i--;
            }else if (toks[i] == '('){
                operations.push(toks[i]); //REMEMBER push sa operations stack not sa numbers
            }else if (toks[i] == ')') {
                while (operations.peek() != '('){
                    numbers.push(smolEvaluate(operations.pop(), numbers.pop(), numbers.pop()));
                }
                operations.pop();
            }else if (toks[i] == '+' || toks[i] == '-' || toks[i] == '*' || toks[i] == '/') {
                if (i < toks.length - 1 && (toks[i + 1] >= '0' && toks[i + 1] <= '9')) {
                    // Check if the next character is a digit, indicating a complete operation
                    while (!operations.empty() && precedenceChecker(toks[i], operations.peek())) {
                        numbers.push(smolEvaluate(operations.pop(), numbers.pop(), numbers.pop()));
                    }
                    operations.push(toks[i]);
                }
            }
        }
        
        while (!operations.empty()) numbers.push(smolEvaluate(operations.pop(), numbers.pop(), numbers.pop()));
        return numbers.pop();
    }
}
