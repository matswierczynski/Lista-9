package com.company;

public class Main {

    public static void main(String[] args) {
	InfixToPostfix infixtopostfix = new InfixToPostfix();
	String expr="2+3*2+6";
	String convertedToONP = infixtopostfix.convert(expr);
	BinaryTree binaryTree = new BinaryTree();
	binaryTree.createTree(convertedToONP);
	System.out.println("ONP: ");
	binaryTree.showPostOrder(binaryTree.getRoot());
	System.out.print(" = "+infixtopostfix.computeONP(convertedToONP));
	System.out.println("\nWyrażenie z nawiasami: ");
	binaryTree.showInOrder(binaryTree.getRoot());

	System.out.println("\nLiczba liści: "+binaryTree.leavesNr(binaryTree.getRoot()));
	System.out.println("Liczba węzłów: "+binaryTree.nodesNr(binaryTree.getRoot()));
	System.out.println("Wysokość drzewa: "+binaryTree.treeHeight(binaryTree.getRoot()));
    }
}
