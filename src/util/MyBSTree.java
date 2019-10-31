/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Product;
import exception.DuplicationException;
import exception.EmptyException;

import java.util.*;

/**
 *
 * @author TrongDuyDao
 */
public class MyBSTree {

    //a root of tree
    Node<Product> root;

    public MyBSTree() {
        root = null;
    }

    //visit a node of a tree -> output information of visited node
    public void visit(Node<Product> p) throws EmptyException {
        NodeAndParent<Product> start = new NodeAndParent<>(root , null);
        NodeAndParent<Product> found = findNodeAndParent(start , p.info.getCode());

        if (found == null){
            throw new EmptyException("there is no Product");
        }

        System.out.println(found.getTargetNode().info);

    }

    //return true if tree is empty otherwise return false
    public boolean isEmpty() {
        if (root == null){
            return true;
        }else {
            return false;
        }

    }

    //inorder a tree
    public void inOrder() throws EmptyException {

        if (isEmpty()){
            throw new EmptyException("there is no products");
        }

        inOrder(root);

    }

    private void inOrder(Node<Product> node) throws EmptyException {
        if ( node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.info);
        inOrder(node.right);
    }

    //count number of products
    public int count() {

        if(isEmpty()){
            return 0;
        }

        return count(root);

    }

    private int count(Node node){
        int count =1;
        if (node.left != null){
            count += count(node.left);
        }

        if (node.right != null){
            count += count(node.right);
        }

        return count;

    }

    //breadth-first traverse a tree
    public void BFT() throws EmptyException {

        if (isEmpty()){
            throw new EmptyException("there is no products");
        }

        Queue<Node<Product>> nodes = new LinkedList<>();

        nodes.add(root);

        while (!nodes.isEmpty()){
            Node<Product> aNode = nodes.remove();

            System.out.println(aNode.info);

            if (aNode.left != null){
                nodes.add(aNode.left);
            }

            if (aNode.right != null){
                nodes.add(aNode.right);
            }
        }
    }
    
    //insert a new Product to a tree
    public void insert(Product product) throws DuplicationException {
        Node<Product> newNode = new Node<>( product , null , null);

        if (isEmpty()){
            root = newNode;
        } else {

          Node<Product> pointer = root;

          while (pointer != null){
              if (pointer.info.getCode().equals(product.getCode())){
                  throw new DuplicationException("Product already exist");
              }

              if (product.getCode().compareTo(pointer.info.getCode())<0){
                  if (pointer.left != null){
                      pointer = pointer.left;
                  } else {
                      break;
                  }


              } else {

                  if (pointer.right != null){
                      pointer = pointer.right;
                  } else {
                      break;
                  }

              }

          }

            if (product.getCode().compareTo(pointer.info.getCode())<0){

                pointer.left = newNode;

            } else {
                pointer.right = newNode;
            }

        }

    }

    //balance a tree
    //step 1: traverse inorder tree and copy all item on tree to an arraylist
    //step 2: insert all items of list to a tree
    private void buildArray(List<Node<Product>> list, Node<Product> p) {
        /*
        if ( node == null){
            return;
        }
        inOrder(node.left);
        System.out.println(node.info);
        inOrder(node.right);

         */

        if (p == null){
            return;
        } else {
            list.add(p);
        }

        buildArray( list , p.left);
        buildArray(list , p.right);
    }

    //step 2:
    private void balance(List<Node<Product>> list, List<Node<Product>> finalList , int f, int l) {
        if (f <= l) {
            int middle = (f + l) / 2;


            finalList.add(list.get(middle));

            balance(list , finalList , f , middle -1);
            balance(list , finalList , middle + 1 , l);
        }
    }

    public void balance() throws EmptyException {

        if (isEmpty()){
            throw new EmptyException("No product");
        }
        List<Node<Product>> list = new ArrayList<>();

        buildArray(list , root);

        List<Node<Product>> finalList = new ArrayList<>();

        balance(list , finalList, 0 , list.size() - 1);

        root = null;
        for (Node<Product> node : finalList) {
            try {
                insert(node.info);
            } catch (DuplicationException ignored) {

            }

        }

    }

    //search a Node of tree by product code
    //return null if given code does not exists
    public Node<Product> search(String code) throws EmptyException {

        NodeAndParent<Product> start = new NodeAndParent<>(root , null);
        NodeAndParent<Product> found = findNodeAndParent(start , code);

        if (found == null){
            throw new EmptyException("there is no such Product");
        }

        return found.getTargetNode();
    }

    //delete a node by a given product code
    // FIX ME PLEASE
    public void delete(String code) throws EmptyException {

        NodeAndParent<Product> start = new NodeAndParent<>(root , null);
        NodeAndParent<Product> toDelete = findNodeAndParent(start , code);

        if (toDelete.targetNode.right == null && toDelete.targetNode.left == null){

            if (toDelete.parentNode == null){
                deleteRootNode();
            }

            if (toDelete.targetNode.info.getCode().compareTo(toDelete.parentNode.info.getCode())<0){
                toDelete.parentNode.left = null;
                toDelete = null;
            } else {
                toDelete.parentNode.right = null;
                toDelete = null;
            }

            return;
        }

        if (toDelete.targetNode.right != null ^ toDelete.targetNode.left != null){

            if (toDelete.parentNode == null){
                deleteRootNode(toDelete);
            }

            if (toDelete.targetNode.info.getCode().compareTo(toDelete.parentNode.info.getCode())<0){

                if (toDelete.targetNode.left != null){
                    toDelete.parentNode.left = toDelete.targetNode.left;
                    toDelete.targetNode.left = null;
                    toDelete = null;
                } else {

                    toDelete.parentNode.left = toDelete.targetNode.right;
                    toDelete.targetNode.right = null;
                    toDelete = null;
                }

            } else {

                if (toDelete.targetNode.left != null){
                    toDelete.parentNode.right = toDelete.targetNode.left;
                    toDelete.targetNode.left = null;
                    toDelete = null;
                } else {

                    toDelete.parentNode.right = toDelete.targetNode.right;
                    toDelete.targetNode.right = null;
                    toDelete = null;
                }
            }
        }

        if(toDelete.targetNode.right != null && toDelete.targetNode.left != null){

            if (toDelete.parentNode == null){

            }

        }



    }


    private NodeAndParent<Product> findNodeAndParent(NodeAndParent<Product> root , String toFindCode){

        if (isEmpty() || toFindCode.equals(root.getTargetNode().info.getCode())){
            return root;
        }

        if (root.getTargetNode().info.getCode().compareTo(toFindCode) <0){
            root.setParentNode(root.getTargetNode());
            root.setTargetNode(root.getTargetNode().right);
            return findNodeAndParent(root , toFindCode);
        } else{
            root.setParentNode(root.getTargetNode());
            root.setTargetNode(root.getTargetNode().left);
            return findNodeAndParent(root , toFindCode);
        }

    }


    private void deleteRootNode(){

        if (root.left == null && root.right == null){
            root = null;
        } else if (root.left != null){
            root = root.left;
        } else {
            root = root.right;
        }

    }

    private void deleteRootNode(NodeAndParent<Product> toDelete) {

        if (toDelete.targetNode.right.left != null){

        }

    }


}
