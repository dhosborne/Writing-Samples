/*
 * In House Class
 * Created By: Derek H Osborne
 * Initial date: 20190621
 */
package inventoryapp.model;


public class InHouse extends Part {
    private int machineId;

    /**
     * In House Part Class
     * Instatiate an instance of In House Class
     * @param int id
     * @param String name
     * @param Double price
     * @param int stock
     * @param int min
     * @param int max
     * @param int machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Get Machine Id
     * Returns the machine id 
     * @return int 
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Set Machine Id
     * Sets the machine id
     * @param int machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
