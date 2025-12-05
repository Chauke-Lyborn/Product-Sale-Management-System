/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vut.data;

import java.util.Scanner;

/**
 *
 * @author chauk
 */
public class TestClass {

    public static void main(String[] args) {
        ItemBasedProduct objITM = new ItemBasedProduct("12345678", "Noodles", Product.ProductCategory.IBP, 1500, "Kellogs");

        System.out.println(objITM.getTotalCost());

        WeightBasedProduct objWTM = new WeightBasedProduct("12345678", "Noodles", Product.ProductCategory.IBP, 4300, 450);
        System.out.println(objWTM.getTotalCost());
        
        Scanner in = new Scanner(System.in);
        System.out.print("Enter value: ");
        int value = in.nextInt();
        
        if(value == 9){
            System.out.println("Work");
        }else{
            System.out.println("Doesn't work");
        }
            
    }
}
