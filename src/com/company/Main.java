package com.company;

public class Main {

    public static void main(String[] args) {
	InfixToPostfix infixtopostfix = new InfixToPostfix();
	String expr="22+5-8/2*(40-8+1-3)";
	String convertedToONP = infixtopostfix.convert(expr);
	BinaryTree binaryTree = new BinaryTree();
	binaryTree.createTree(convertedToONP);
	System.out.println("ONP: "+binaryTree.PostfixFromTree()+
    " = "+binaryTree.computeFromPostFix());
	System.out.println("Wyrażenie z nawiasami: "+binaryTree.InfixFromTree()+
    " = "+binaryTree.computeFromPostFix());
	System.out.println("Liczba liści: "+binaryTree.leavesNr(binaryTree.getRoot()));
	System.out.println("Liczba węzłów: "+binaryTree.nodesNr(binaryTree.getRoot()));
	System.out.println("Wysokość drzewa: "+binaryTree.treeHeight(binaryTree.getRoot()));
    }
}
