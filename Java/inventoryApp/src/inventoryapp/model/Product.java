/*
 * Product Class
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts;
    
    /**
     * Product Constructor
     * 
     * @param int id
     * @param String name
     * @param Double price
     * @param int stock
     * @param int min
     * @param int max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Get Id
     * Get the product id
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Id
     * Set id of product
     * @param int id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Name
     * Get name of product
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * Set name of product
     * @param String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Price
     * Get price of product
     * @return Double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Price
     * Set price of product
     * @return Double price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get Stock
     * Get stock levels of product
     * @return int stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set Stock
     * Set stock level of products
     * @param int stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get Min
     * Get min level of products
     * @return int min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set Min
     * Set min level of products
     * @param int min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get Max
     * Get max level of products
     * @return int max
     */
    public int getMax() {
        return max;
    }

    /**
     * Set Max
     * Set max level of products
     * @param int max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Add Associated Part
     * Add associated part to the product parts list
     * @param Part part
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }
    
    /**
     * Delete Associated Part
     * Delete assoicated part from the products parts list
     * @param Part part
     */
    public void deleteAssociatedPart(Part part) {
        if(this.associatedParts.contains(part)){
            this.associatedParts.remove(part);
        }
    }
    
    /**
     * Get All Associated Parts
     * Get all assoicated parts from the products parts list
     * @return ObservableList<Part>
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }
}
