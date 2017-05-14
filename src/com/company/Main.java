package com.company;

public class Main {

    public static void main(String[] args) {
	InfixToPostfix infixtopostfix = new InfixToPostfix();
	String expr="((10+2)*(2+3))/10";
	System.out.println("ONP"+infixtopostfix.convert(expr));
	BinaryTree binaryTree = new BinaryTree();
	binaryTree.createTree(infixtopostfix.convert(expr));
	binaryTree.showInOrder(binaryTree.getRoot());
    }
}
