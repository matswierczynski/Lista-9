package com.company;
import java.util.Stack;

class InfixToPostfix {
    private Stack<Character> operatorStack;
    private String postfix;

    public String convert (String infix){
        char [] charArray = infix.toCharArray();
        operatorStack = new Stack<>();
        postfix=" ";
        for(int i=0;i<charArray.length;i++) {
            if (isOperator(charArray[i])) {
                    processOperator(charArray[i]);
            }
            else{
                if(isOperand(charArray[i])){
                    postfix+=charArray[i];
                    if(i+1<charArray.length && !isOperand(charArray[i+1]))
                        postfix+=' ';
                }
                else
                    System.out.println("Niepoprawna komenda");

            }
        }
        while(!operatorStack.isEmpty())
            postfix+=" "+operatorStack.pop();
        return postfix;
    }

    private void processOperator (char op){
        if (operatorStack.isEmpty() || op=='(')
            operatorStack.push(op);
        else{
            if (op==')'){
                while (!operatorStack.isEmpty() && operatorStack.peek()!='(')
                    postfix+=operatorStack.pop()+" ";
                if (!operatorStack.isEmpty())
                    operatorStack.pop();
                else
                    throw new IllegalArgumentException();
            }
            else{
                while (!operatorStack.isEmpty() && (precedence(op)<precedence(operatorStack.peek()))){
                        postfix += operatorStack.pop()+" ";
                }
                operatorStack.push(op);
            }
        }

    }

    public int computeONP(String chain) {
        char[] charArray = chain.toCharArray();
        Stack<Integer>digitStack = new Stack<>();
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != ' ') {
                if (InfixToPostfix.isOperand(charArray[i])) {
                    if (i + 1 < charArray.length && InfixToPostfix.isOperand(charArray[i + 1])) {
                        i = addDigitONP(i, charArray, digitStack);
                    } else {
                        digitStack.push(Character.getNumericValue(charArray[i]));
                    }
                }
                else
                    processOperatorONP(charArray[i], digitStack);
            }
        }
        return digitStack.pop();
    }
    private void processOperatorONP(char operator, Stack<Integer> stack) {
        switch (operator) {
            case '+': {
                stack.push(stack.pop() + stack.pop());
                break;
            }

            case '-': {
                int right = stack.pop();
                stack.push(stack.pop() - right);
                break;
            }

            case '/': {
                int right = stack.pop();
                if (right==0) throw new ArithmeticException();
                stack.push(stack.pop() / right);
                break;
            }

            case '*': {
                int right = stack.pop();
                stack.push(stack.pop() * right);
                break;

            }

            case '%': {
                int right = stack.pop();
                stack.push(stack.pop() % right);
                break;
            }

        }
    }

    private int addDigitONP(int position, char[] array, Stack<Integer> stack) {
        StringBuilder tmp = new StringBuilder(Character.toString(array[position]));
        position++;
        while (position<array.length && array[position] != ' ' ) {
            tmp.append(array[position]);
            position++;
        }
        stack.push(Integer.parseInt(tmp.toString()));
        return position;
    }


    private int precedence(char op){
        switch (op){
            case '+': case '-':case ')': return 1;
            case '*':case '/':case '%': return 2;
            default: return 0;
        }
    }


    public static boolean isOperator(char op){
        return (op == '+' || op == '-' || op =='*'
                || op == '/' || op=='%' || op == '(' || op == ')');
    }

    public static boolean isOperand(char op){
        return Character.isDigit(op);
    }


}
