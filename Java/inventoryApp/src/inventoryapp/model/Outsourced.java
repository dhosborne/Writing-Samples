/*
 * Out Sourced Part Class
 * Created by: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.model;


public class Outsourced extends Part{
    private String companyName;

    /**
     * Outsource constructor
     * @param int id
     * @param String name
     * @param Double price
     * @param int stock
     * @param int min
     * @param int max
     * @param String companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Get Company Name
     * @return String
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Set Company Name
     * @param String companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
  
}
