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
