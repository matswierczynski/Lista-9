package com.company;
import java.util.EmptyStackException;
import java.util.Stack;


 class BinaryTree {
    private Node root;
    private Stack<Node> nodeStack;
     BinaryTree(){
        root=null;
        nodeStack=new Stack<>();
    }

    /*Na podstawie przekazengo wyrażenia w postaci ONP funkcja tworzy drzewo binarne.
    * Jeśli kolejny znak jest liczbą jest tworzony węzęł o wartości tej liczby, który
    * następnie dodawany jest do tymczasowego stosu. Jeśli znak jest operatorem
    * to pobierane są dwa węzły ze stosu. Tworzony jest węzęł drzewa, zawierający
    * napotkany operator. Pierwszy pobrany węzeł staje się jego prawym dzieckiem a drugi - lewym.
    * Na tymczasowym stosie umieszczony zostaje węzeł z operatorem  */
    void createTree (String postFix){
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
    private void showInOrder(Node node, StringBuilder stringBuilder){
        if (node==null)
            return;
        stringBuilder.append("(");
        showInOrder(node.left, stringBuilder);
        stringBuilder.append(node);
        showInOrder(node.right,stringBuilder);
        stringBuilder.append(")");
        }

        /*Funkcja dostępna publicznie do wyświetlania w kolejności InOrder*/
     StringBuilder InfixFromTree(){
        StringBuilder stringBuilder = new StringBuilder();
        showInOrder(root,stringBuilder);
        return stringBuilder;
    }

    /*Funkcja pokazuje węzły w kolejności PostOrder - Postfix*/
    private void showPostOrder(Node node, StringBuilder stringBuilder){
        if (node==null)
            return;
        showPostOrder(node.left,stringBuilder);
        showPostOrder(node.right,stringBuilder);
        stringBuilder.append(node);
        stringBuilder.append(" ");
    }

    /*Funkcja dostępna publicznie do wyświetlania w kolejności PostOrder*/
     StringBuilder PostfixFromTree(){
        StringBuilder stringBuilder = new StringBuilder();
        showPostOrder(root,stringBuilder);
        return stringBuilder;
    }

    /*Funkcja dostępna publicznie do obliczenie wartości wyrażenia zapisanego w drzewie*/
     String computeFromPostFix(){
        Stack<Integer> stack = new Stack<>();
        try{
        showInPostOrder(root,stack);
        } catch (ArithmeticException e) {return "Dzielenie przez zero !!!!!!";}
         if(!stack.isEmpty())
             return stack.pop().toString();
         else throw new EmptyStackException();
    }

    /*Funkcja zbiera węzły w kolejności PostOrder i prekazuje do obliczenia funkcji proessOperatorsFromTree*/
    private void showInPostOrder(Node node,Stack<Integer> stack){
        if (node==null)
            return;
        showInPostOrder(node.left,stack);
        showInPostOrder(node.right,stack);
        if (node.getValue() instanceof Integer)
            stack.push((Integer)node.getValue());
        else
        processOperatorsFromTree((char) node.getValue(), stack);
    }

    /*Funkcja do przetwarzania operatorów podczas obliczania wartości wyrażenia w drzewie*/
    private void processOperatorsFromTree(char operator, Stack<Integer> stack) {
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

    /*Funkcja zwraca liczbę liści drzewa*/
     int leavesNr(Node node){
        if (node==null)
            return 0;
        else
            if (node.getLeft()==null && node.getRight()==null)
            return 1;
            else
                return leavesNr(node.left)+leavesNr(node.right);
    }

    /*Funkcja zwraca liczbę węzłów drzewa*/
    int nodesNr(Node node){
        if (node==null)
            return 0;
        else
            return 1+nodesNr(node.left)+nodesNr(node.right);
    }

    /*Fuckja zwraca wysokośc drzewa*/
     int treeHeight(Node node){
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


    Node getRoot() {
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

         void setLeft(Node left) {
            this.left = left;
        }

         void setRight(Node right) {
            this.right = right;
        }

         Node getLeft() {
            return left;
        }

         Node getRight() {
            return right;
        }

         T getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}
