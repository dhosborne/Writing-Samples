/*
 * Inventory Class
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author derekosborne
 */
public class Inventory {
    

    private static final ObservableList<Part> partsList = FXCollections.observableArrayList();
    private static final ObservableList<Product> productsList = FXCollections.observableArrayList();
    
    /**
     * Add Part
     * Add a part to the inventory parts list
     * @param Part part
     */
    public static void addPart(Part part) {
        Inventory.partsList.add(part);
    }

    /**
     * Add Product
     * Add a product to the inventory products list
     * @param Product prod
     */
    public static void addProduct(Product prod) {
        Inventory.productsList.add(prod);
    }
    

    /**
     * Look Up Part
     * Look up a part in the inventory parts list by id
     * @param Part partId
     * @return int or null
     */
    public static Part lookUpPart(int partId) {
        for(Part part: Inventory.partsList){
            if(part.getId() == partId) {
                return part;
            }
        }
        
        return null;
    }
    
    /**
     * Look Up Product
     * Look up a product in the inventory by id
     * @param int productId
     * @return int or null
     */
    public static Product lookUpProduct(int productId) {
        for(Product prod : Inventory.productsList) {
            if(prod.getId() == productId) {
                return prod;
            }
        }
        
        return null;
    }

    /**
     * Look Up Part
     * Look up a parts by name and add it to an ObservableList<Part>
     * @param String partName
     * @return ObservableList<Part>
     */
    public static ObservableList<Part> lookUpPart(String partName) {
        ObservableList<Part>list = FXCollections.observableArrayList();
        for(Part part: Inventory.partsList){
            if(part.getName().compareToIgnoreCase(partName) >= 0 ) {
                list.add(part);
            }
        }
        
        return list;
    }
    
    /**
     * Look Up Product
     * Look up product by id
     * @param String productName
     * @return ObservableList<Product>
     */
    public static ObservableList<Product> lookUpProduct(String productName) {
        ObservableList<Product>list = FXCollections.observableArrayList();
        for(Product prod : Inventory.productsList) {
            if(prod.getName().compareToIgnoreCase(productName) >= 0){
                list.add(prod);
            }
        }
        
        return list;
    }
    
    /**
     * Update Part
     * Update a part in the inventory parts list
     * @param int index
     * @param Part part
     * @throws IndexOutOfBoundsException
     */
    public static void updatePart(int index, Part part) throws IndexOutOfBoundsException {
        Inventory.partsList.set(index, part);
    }
    

    /**
     * Update Product
     * Update a product in the inventory product list
     * @param int index
     * @param Product product
     */
    public static void updateProduct(int index, Product product) {
        Inventory.productsList.set(index, product);
    }
    
    /**
     * Delete Part
     * Delete a part from the parts list
     * @param Part part
     */
    public static void deletePart(Part part) {
        if(Inventory.partsList.contains(part)){
            Inventory.partsList.remove(part);
        }
    }
    
    /**
     * Delete Product
     * Delete a product from the inventory procut list
     * @param Product product
     */
    public static void deleteProduct(Product product) {
        if(Inventory.productsList.contains(product)) {
            Inventory.productsList.remove(product);
        }
    }
    

    /**
     * Get All Parts
     * Get all parts from the inventory parts list
     * @return ObservableList<Part>
     */
    public static ObservableList<Part> getAllParts() {
        return partsList;
    }
    
    /**
     * Get All Products
     * Return all products from the inventory products list
     * @return ObservableList<Products>
     */
    public static ObservableList<Product> getAllProducts() {
        return productsList;
    }
    
    /**
     * Get Part Index
     * Get the index of a part from the inventory parts list
     * @param Part part
     * @return int
     */
    public static int getPartIndex(Part part){
        final SimpleIntegerProperty index = new SimpleIntegerProperty();
        
        Inventory.partsList.forEach((Part i) -> {
            if(part.getId() == i.getId()){
                index.set(Inventory.partsList.indexOf(i));
            }

        });
        
        return index.get();
    }
    
    /**
     * Get Product Index
     * Get the index of a product from the inventory products list
     * @param Product product
     * @return int
     */
    public static int getProductIndex(Product product){
        final SimpleIntegerProperty index = new SimpleIntegerProperty();
        
        Inventory.productsList.forEach((Product i ) -> {
            if(product.getId() == i.getId()){
                index.set(Inventory.productsList.indexOf(i));
            }
        });
        
        return index.get();
    }
    
}
