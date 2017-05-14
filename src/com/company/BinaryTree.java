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

    /*Na podstawie przekazengo wyrażenia w postaci ONP funkcja tworzy drzewo binarne.
    * Jeśli kolejny znak jest liczbą jest tworzony węzęł o wartości tej liczby, który
    * następnie dodawany jest do tymczasowego stosu. Jeśli znak jest operatorem
    * to pobierane są dwa węzły ze stosu. Tworzony jest węzęł drzewa, zawierający
    * napotkany operator. Pierwszy pobrany węzeł staje się jego prawym dzieckiem a drugi - lewym.
    * Na tymczasowym stosie umieszczony zostaje węzeł z operatorem  */
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

    /*Funkcja tworzy węzeł z operatorem oraz przyporządkowuje mu dwójkę dzieci*/
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


/*Funkcja dodaje do stosu liczby o wartości co najmniej dwucyfrowej*/
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


/*Funkcja pokazuje węzły w kolejności InOrder - Infix z nawiasami*/
    public void showInOrder(Node node){
        if (node==null)
            return;
        System.out.print("(");
        showInOrder(node.left);
        System.out.print(node);
        showInOrder(node.right);
        System.out.print(")");
        }

    /*Funkcja pokazuje węzły w kolejności PostOrder - Postfix*/
    public void showPostOrder(Node node){
        if (node==null)
            return;
        showPostOrder(node.left);
        showPostOrder(node.right);
        System.out.print(node+" ");
    }

    /*Funkcja zwraca liczbę liści drzewa*/
    public int leavesNr(Node node){
        if (node==null)
            return 0;
        else
            if (node.getLeft()==null && node.getRight()==null)
            return 1;
            else
                return leavesNr(node.left)+leavesNr(node.right);
    }

    /*Funkcja zwraca liczbę węzłów drzewa*/
    public int nodesNr(Node node){
        if (node==null)
            return 0;
        else
            return 1+nodesNr(node.left)+nodesNr(node.right);
    }

    /*Fuckja zwraca wysokośc drzewa*/
    public int treeHeight(Node node){
        if (node == null) {
            return -1;
        }

        int left = treeHeight(node.getLeft());
        int right = treeHeight(node.getRight());

        if (left > right) {
            return left + 1;
        } else {
            return right + 1;
        }
    }


    public Node getRoot() {
        return root;
    }

    /*PRywatna klasa Węzeł o atrybutach: wartość, prawe dziecko, lewe dzeicko*/
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
