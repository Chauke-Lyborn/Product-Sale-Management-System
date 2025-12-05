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
public class ItemBasedProduct extends Product {

    private String manufacturer;

    public ItemBasedProduct() {
    }

    //For storing data colled from the Database
    public ItemBasedProduct(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice, double totalCost, String manufacturer) {
        super(barcodeNumber, productName, productCategory, unitPrice,totalCost);
        setManufacturer(manufacturer);
    }

    //For collecting data in the GUI to the PD class 
    //totalCoast is not included bcs we calculate it we don't get it from the user
    public ItemBasedProduct(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice, String manufacturer) {
        super(barcodeNumber, productName, productCategory, unitPrice);
        setManufacturer(manufacturer);
        setTotalCost(calculateTotalCost());
    }
    
    
    

    public void setManufacturer(String manufacturer) {
        if (manufacturer.length() >= 4) {
            this.manufacturer = manufacturer;
        } else {
            throw new IllegalArgumentException("Invalid input! manufacturer name too shot, must have at least 4 characters");
        }
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public double calculateTotalCost() {

        return (getUnitPrice() + (getUnitPrice() * TAX_RATE)) / 100;

    }
    
       public String toString() {
        return "Barcode: "+ getBarcodeNumber() +"\n"+
                "Product Name : "+getProductName() +"\n"+
                "Product Catergory : "+ getProductCategory() +"\n"+
                 "Manufacturer : "+ getManufacturer() +"\n"+
                 "UnitPrice : "+ getUnitPrice() +"\n"+
                 "Total Cost : "+ String.format("%.2f", getTotalCost())+"\n\n";
    }

}
