package com.company;
import java.util.EmptyStackException;
import java.util.Stack;


public class BinaryTree {
    private Node root;
    private Stack<Node> nodeStack;
    public BinaryTree(){
        root=null;
        nodeStack=new Stack<>();
    }

    public void createTree (String postFix){
        char[] charArray = postFix.toCharArray();

        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != ' ') {
                if (InfixToPostfix.isOperand(charArray[i])) {
                    if (i + 1 < charArray.length && InfixToPostfix.isOperand(charArray[i + 1]))
                        i = addDigit(i, charArray);
                    else
                        nodeStack.push(new Node<>(Character.getNumericValue(charArray[i])));
                }
                    else {
                        if(InfixToPostfix.isOperator(charArray[i]))
                        processOperator(charArray[i]);
                    }
                }
            }
        }

    private void processOperator(char operator) {
    Node<Character> node = new Node<>(operator);
    Node right,left;
    if (!nodeStack.isEmpty())
         right=nodeStack.pop();
    else throw new EmptyStackException();

    if (!nodeStack.isEmpty())
        left=nodeStack.pop();
    else throw new EmptyStackException();

    node.setRight(right);
    node.setLeft(left);
    nodeStack.push(node);
    root=node;
    }

    private int addDigit(int position, char[] array) {
        StringBuilder tmp = new StringBuilder(Character.toString(array[position]));
        position++;
        while (position<array.length && array[position] != ' ' ) {
            tmp.append(array[position]);
            position++;
        }
        nodeStack.push(new Node<>(Integer.parseInt(tmp.toString())));
        return position;
    }

    public void showInOrder(Node node){
        if (node==null)
            return;
        showInOrder(node.left);
        System.out.print(" "+node);
        showInOrder(node.right);
        }


    public Node getRoot() {
        return root;
    }

    private class Node <T>{
        private T value;
        private Node left;
        private Node right;

        Node(T val){
            value=val;
            left=null;
            right=null;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }

        public T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
