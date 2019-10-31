/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import entity.Product;
import exception.DuplicationException;
import exception.EmptyException;
import util.MyBSTree;

/**
 *
 * @author TrongDuyDao
 */
public class Main {
    
     
    
    public static void main(String[] args) throws EmptyException {

        Product p1 = new Product("A" , "A1" , 100 , 0 , 1000);
        Product p01 = new Product("A02" , "A2" , 100 , 0 ,100);
        Product p2 = new Product("B" , "B1" , 100 , 0 , 1000);
        Product p3 = new Product("C" , "C1" , 100 , 0 , 1000);
        Product p4 = new Product("D" , "D1" , 100 , 0 , 1000);
        Product p5 = new Product("E" , "E1" , 100 , 0 , 1000);
        Product p6 = new Product("F" , "F1" , 100 , 0 , 1000);
        Product p7 = new Product("G" , "G1" , 100 , 0 , 1000);

        MyBSTree tree = new MyBSTree();

        try {
            tree.insert(p1);
            tree.insert(p2);
            tree.insert(p3);
            tree.insert(p4);
            tree.insert(p5);
            tree.insert(p6);
            tree.insert(p7);
            tree.insert(p01);
        } catch (DuplicationException e) {
            e.printStackTrace();
        }

        tree.balance();


        System.out.println("done");
    }
}
