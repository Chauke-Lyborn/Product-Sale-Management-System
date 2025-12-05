/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vut.data;

import java.sql.*;
import java.util.ArrayList;
import vut.data.Product.ProductCategory;

/**
 *
 * @author chauk
 */
public abstract class ProductDA {

    //class level declarations
    private static Connection dbCon;
    private static PreparedStatement ps;
    private static Statement st;
    private static ResultSet rs;

    public static void getProductSalesDbConnection() throws DataStorageException {
        //Do not include the code for loading the driver, it will be loaded automatically.
        try {
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost/ProductSaleDB", "root", "chauke");
        } catch (SQLException ex) {
            throw new DataStorageException(ex.getMessage());
        }
    }

    public static void addProduct(Product objProduct) throws DuplicateException {

        try {
//Do not include the column names in the SQL query
            ps = dbCon.prepareStatement("INSERT INTO tblproduct  VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, objProduct.getBarcodeNumber());
            ps.setString(2, objProduct.getProductName());
            ps.setString(3, objProduct.getProductCategory().name());
            if (objProduct instanceof WeightBasedProduct) {
                //assign the value for column 4
                ps.setNull(4, java.sql.Types.VARCHAR);
                ps.setInt(5, ((WeightBasedProduct) objProduct).getWeight());
            } else {
                ps.setString(4, ((ItemBasedProduct) objProduct).getManufacturer());
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setInt(6, objProduct.getUnitPrice());
            ps.setDouble(7, objProduct.getTotalCost());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new DuplicateException(e.getMessage());
        }
    }

    public static ArrayList<Product> returnAll() throws NotFoundException {

        ArrayList<Product> arListProduct = new ArrayList<>();
        try {
            ps = dbCon.prepareStatement("SELECT * FROM tblProduct");
            rs = ps.executeQuery();
            while (rs.next()) {
//use column numbers to access data from the resultset
                if (rs.getString(3).equals("WBP")) {
                    arListProduct.add((new WeightBasedProduct(rs.getString(1), rs.getString(2), ProductCategory.WBP, rs.getInt(6), rs.getInt(5))));
                } else {
                    arListProduct.add(new ItemBasedProduct(rs.getString(1), rs.getString(2), ProductCategory.IBP, rs.getInt(6), rs.getDouble(7), rs.getString(4)));
                }
            }
        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }
        return arListProduct;
    }

    public static double calculateTotalWBP() throws NotFoundException {
        double totalAmount = 0;
        try {
            ps = dbCon.prepareStatement("Select * From tblproduct WHERE Product_Category = 'WBP'");
            rs = ps.executeQuery();

            while (rs.next()) {

                totalAmount += rs.getDouble("Amount_Paid");
            }
            ps.close();
        } catch (SQLException ex) {
            throw new NotFoundException(ex.getMessage());
        }
        return totalAmount;
    }

    public static double calculateTotalIBP() throws NotFoundException {
        String qry = "SELECT SUM(Amount_Paid) AS Total FROM tblProduct WHERE Product_Category = 'IBP'";
        double totalAmt = 0;
        try {
            st = dbCon.createStatement();
            rs = st.executeQuery(qry);
            if (rs.next()) {
                totalAmt = rs.getDouble("Total");
            }
            // Close resources
            rs.close();
            st.close();
        } catch (SQLException e) {
            throw new NotFoundException(e.getMessage());
        }
        return totalAmt;
    }

    public static void updatePrice(String barcodeNumber, int percentage) throws NotFoundException {
        int normPrice, newPrice;
        double floatPerc = (double) percentage / 100;

        try {
            ps = dbCon.prepareStatement("SELECT Unit_Price FROM tblproduct WHERE Barcode_No = ?");
            ps.setString(1, barcodeNumber);
            rs = ps.executeQuery();

            if (rs.next()) {
                normPrice = rs.getInt("Unit_Price");
                newPrice = (int) (normPrice + (normPrice * floatPerc));

                ps = dbCon.prepareStatement("UPDATE tblproduct SET Unit_Price = ? WHERE Barcode_No = ? ");

                ps.setInt(1, newPrice);
                ps.setString(2, barcodeNumber);
                ps.executeUpdate();
            } else {
                throw new NotFoundException("Product with barcode " + barcodeNumber + " not found.");
            }

            ps.close();
        } catch (SQLException ex) {
            throw new NotFoundException(ex.getMessage());
        }

    }

    public static ArrayList<String> returnBarcodes() throws NotFoundException {
        ArrayList<String> barcodeList = new ArrayList<>();
        try {
            ps = dbCon.prepareStatement("Select * From tblproduct");
            rs = ps.executeQuery();

            while (rs.next()) {

                barcodeList.add(rs.getString("Barcode_No"));
            }
            ps.close();
        } catch (SQLException ex) {
            throw new NotFoundException(ex.getMessage());
        }
        return barcodeList;
    }

    public static void deleteProduct(String barcodeNumber) throws NotFoundException {
        try {

            // Check if product exists using COUNT
            String checkBarcode = "SELECT COUNT(*) FROM tblproduct WHERE Barcode_No = ?";
            ps = dbCon.prepareStatement(checkBarcode);
            ps.setString(1, barcodeNumber);
            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                //free resultset and prepared statement
                rs.close();
                ps.close();
                throw new NotFoundException("Product with barcode " + barcodeNumber + " not found in the database");
            }
            // Delete the product if the barcode exists
            String deleteSql = "DELETE FROM tblproduct WHERE Barcode_No = ?";
            ps = dbCon.prepareStatement(deleteSql);
            ps.setString(1, barcodeNumber);
            ps.executeUpdate();

            // Close resources
            ps.close();
        } catch (SQLException ex) {
            throw new NotFoundException("Error deleting product from database: " + ex.getMessage());
        }
    }

    public static Product getProductByBarcode(String barcodeNumber) throws NotFoundException {
        Product product = null;
        try {
            // Prepare statement
            String sql = "SELECT * FROM tblproduct WHERE Barcode_No = ?";
            ps = dbCon.prepareStatement(sql);
            ps.setString(1, barcodeNumber);
            // Execute query
            rs = ps.executeQuery();
            // Check if product found
            if (rs.next()) {
                // use column numbers to extract product details and create appropriate product object
                if (rs.getString(3).equals("IBP")) {
                    product = new ItemBasedProduct(rs.getString(1), rs.getString(2), ProductCategory.valueOf(rs.getString(3)), rs.getInt(6), rs.getDouble(7), rs.getString(4));
                } else {
                    product = new WeightBasedProduct(rs.getString(1), rs.getString(2), ProductCategory.valueOf(rs.getString(3)), rs.getInt(6), rs.getInt(5));
                }
            } else {
                // Product not found
                rs.close();
                ps.close();

                throw new NotFoundException("Product with barcode " + barcodeNumber + " not found");
            }
        } catch (SQLException e) {
            throw new NotFoundException("Product with barcode " + barcodeNumber + " not found");
        }

        return product;
    }

}
