/*
 * Part Class
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.model;

/**
 *
 * @author derekosborne
 */
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Part class constructor
     * 
     * @param int id
     * @param String name
     * @param Double price
     * @param int stock
     * @param int min
     * @param int max
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * Get Id
     * Get part Id
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Set Id
     * Set part id
     * @param int id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Name
     * Get part name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Set Name
     * Set name of part
     * @param String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Price
     * Get price of part
     * @return Double price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set Price
     * Set price of part
     * @param Double price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get Stock
     * Get stock level of part
     * @return int stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Set Stock
     * Set stock level of part
     * @param int stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Get Min
     * Get minimum levels of part stock
     * @return int min
     */
    public int getMin() {
        return min;
    }

    /**
     * Set Min
     * Set min level of part stock
     * @param int min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Get Max
     * Get max level of part stock
     * @return int max
     */
    public int getMax() {
        return max;
    }

    /**
     * Set Max
     * Set max level of part stock
     * @param int max
     */
    public void setMax(int max) {
        this.max = max;
    }
    
    

}
