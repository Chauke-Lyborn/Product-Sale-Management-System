/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vut.data;

/**
 *
 * @author Examiner
 */
public class WeightBasedProduct extends Product {

    private int weight;

    public WeightBasedProduct() {
    }

    //For storing data collect from the Database
    public WeightBasedProduct(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice, double totalCost, int weight) {
        super(barcodeNumber, productName, productCategory, unitPrice, totalCost);
        setWeight(weight);
    }

    //For collecting data in the GUI to the PD class 
    //totalCoast is not included bcs we calculate it we don't get it from the user
    public WeightBasedProduct(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice, int weight) {
        super(barcodeNumber, productName, productCategory, unitPrice);
        setWeight(weight);
        setTotalCost(calculateTotalCost());
        
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (weight >= 50) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Invalid input! item weight must be at least 50g");

        }
    }

    @Override
    public double calculateTotalCost() {

        double cost = getUnitPrice() * getWeight() / 1000;
        double totalCost = (cost + (cost * TAX_RATE)) / 100;
        return totalCost;

    }

    public String toString() {
        return "Barcode: " + getBarcodeNumber() + "\n"
                + "Product Name : " + getProductName() + "\n"
                + "Product Catergory : " + getProductCategory() + "\n"
                + "Price : " + getUnitPrice() + "c/Kg\n"
                + "Weight : " + getWeight() + "grams\n"
                + "Total Cost : " + String.format("%.2f", getTotalCost())+"\n\n";
    }
}
