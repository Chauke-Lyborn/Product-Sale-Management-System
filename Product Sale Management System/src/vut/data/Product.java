/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vut.data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Examiner
 */
public abstract class Product implements Serializable {

    public enum ProductCategory {
        IBP, WBP;
    }
    protected String barcodeNumber;
    protected String productName;
    ProductCategory productCategory;
    protected int unitPrice;
    protected static final double TAX_RATE = 0.15;

    protected double totalCost;

    //For collecting data in the GUI to the PD class 
    //totalCoast is not included bcs we calculate it we don't get it from the user
    public Product(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice) {
        setBarcodeNumber(barcodeNumber);
        setProductName(productName);
        setProductCategory(productCategory);
        setUnitPrice(unitPrice);

    }

    //For storing data colled from the Database
    public Product(String barcodeNumber, String productName, ProductCategory productCategory, int unitPrice, double totalCost) {
        setBarcodeNumber(barcodeNumber);
        setProductName(productName);
        setProductCategory(productCategory);
        setUnitPrice(unitPrice);
        setTotalCost(calculateTotalCost());
    }

    public Product() {
        this(" ", " ", ProductCategory.IBP, 100);
    }

    public String getBarcodeNumber() {
        return barcodeNumber;
    }

    public void setBarcodeNumber(String barcodeNumber) {
        this.barcodeNumber = barcodeNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public abstract double calculateTotalCost();

    @Override
    public String toString() {
        return "Barcode: " + getBarcodeNumber() + "\n"
                + "Product Name : " + getProductName() + "\n"
                + "Product Catergory : " + getProductCategory() + "\n"
                + "UnitPrice : " + getUnitPrice() + "\n"
                + "Total Cost : " + String.format("%.2f", getTotalCost());
    }

    //calling ProductDA methods
    public void getProductSalesDbConnection() throws DataStorageException {
        ProductDA.getProductSalesDbConnection();
    }

    public void addProduct(Product objProduct) throws DuplicateException {
        ProductDA.addProduct(objProduct);
    }

    public ArrayList<Product> returnAll() throws NotFoundException {
        return ProductDA.returnAll();
    }

    public double calculateTotalWBP() throws NotFoundException {
        return ProductDA.calculateTotalWBP();
    }

    public double calculateTotalIBP() throws NotFoundException {
        return ProductDA.calculateTotalIBP();
    }

    public Product getProductByBarcode(String barcodeNumber) throws NotFoundException {
        return ProductDA.getProductByBarcode(barcodeNumber);
    }
    
    public void updatePrice(String barcodeNumber, int percentage) throws NotFoundException {
        ProductDA.updatePrice(barcodeNumber, percentage);
    }
    public  void deleteProduct(String barcodeNumber) throws NotFoundException {
        ProductDA.deleteProduct(barcodeNumber);
    }

}
