/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.models;

import calendarapp.DBConnect;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 *
 * @author derekosborne
 */
public class Customer extends BaseClass {
    private Integer customerId;
    private String  customerName;
    private Integer addressId;
    private Boolean active;
    
    private final DBConnect connection = new DBConnect();
    private PreparedStatement stmt;
    private ResultSet rs;
    private String sql;
    

// <editor-fold desc="Constructors">
    public Customer(){}
    
    public Customer(
            String customerName, 
            int addressId, 
            String user){
        super(LocalDate.now(), user, null, user);
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = true;
    }
    
    public Customer(
            Integer customerId, 
            String customerName, 
            int addressId,
            boolean active,
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        super(createDate, createdBy,lastUpdate, lastUpdateBy);
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
    }
    public Customer(
            Integer customerId, 
            String customerName, 
            int addressId,
            int active,
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        super(createDate, createdBy,lastUpdate, lastUpdateBy);
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        if(active == 1) {
            this.active = true;
        } else {
            this.active = false;
        }
    }

// </editor-fold>
    
// <editor-fold desc="Getters & Setters">
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString() {
        return customerName;
    }
 
// </editor-fold>
 
// <editor-fold desc="SQL functions">
    
    /**
     * Recall all customers from the SQL DB
     * @return ObservableList<Customer> list of all customers
     * @throws SQLException 
     */
    public ObservableList<Customer> getCustomerList() throws SQLException {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        sql = "select * from customer";
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Customer tmp = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getInt("addressId"),
                        rs.getInt("active"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy"));
                customerList.add(tmp);
            }
        } catch (SQLException ex){
            System.out.println("SQL Ex caught: " + ex.getMessage());
        }
        
        return customerList;
    }
    
    /**
     * Recall customer from SQL DB where matches supplied customerId
     * @param custId
     * @return <Customer> or null if not found
     * @throws SQLException 
     */
    public Customer recallCustomer(int custId) throws SQLException {
        Customer result = null;
        
        sql = "select * from customer where customerId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(custId));
            rs = stmt.executeQuery();
            
            while(rs.next()){
                result = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getInt("addressId"),
                        rs.getInt("active"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy"));
            }
        } catch (SQLException ex) {
            System.out.println("SQL Ex caught: " + ex.getMessage());
        }
        
        
        return result;
    }
    
    /**
     * Recall customer record from SQL DB where matches supplied customerName
     * @param customerName
     * @return <Customer> or null if not found
     * @throws SQLException 
     */
    public Customer recallCustomer(String customerName) throws SQLException {
        Customer result = null;
        
        sql = "select * from customer where customerName = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, customerName);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                result = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getInt("addressId"),
                        rs.getInt("active"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy"));
            }
        } catch (SQLException ex) {
            System.out.println("SQL Ex caught: " + ex.getMessage());
        }
        
        
        return result;
    }
    
    /**
     * Create customer entry in SQL DB
     * @param newCustomer
     * @return <int> new id of created record, or 0 if failed
     * @throws SQLException 
     */
    public int createCustomer(Customer newCustomer) throws SQLException {
        int newId = 0;
        sql = "INSERT INTO customer ("
                + "customerName, "
                + "addressId, "
                + "active, "
                + "createDate, "
                + "createdBy,"
                + "lastUpdateBy)"
                + "values (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)) {
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newCustomer.getCustomerName());
            stmt.setString(2, String.valueOf(newCustomer.getAddressId()));
            if(newCustomer.getActive()){
                stmt.setString(3, String.valueOf(1));
            }else {
                stmt.setString(3, String.valueOf(0));
            }
            stmt.setString(4, String.valueOf(newCustomer.getCreateDate()));
            stmt.setString(5, newCustomer.getCreatedBy());
            stmt.setString(6, newCustomer.getCreatedBy());
            stmt.executeUpdate();
                                
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                newId = rs.getInt(1);
            }
            
        } catch (SQLException ex) {
            System.out.println("SQL ex caught: " + ex.getMessage());
        }
        
        return newId;
    }
    
    /**
     * Update customer entry where matches supplied customer object customerId
     * @param updated
     * @return <int> row/s found or, 0 if not found
     * @throws SQLException 
     */
    public int updateCustomer(Customer updated) throws SQLException {
        int result = 0;
        
        sql = "update customer set "
                + "customerName = ?, "
                + "addressId = ?, "
                + "active = ?, "
                + "lastUpdateBy = ? "
                + "where customerId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)) {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, updated.getCustomerName());
            stmt.setString(2, String.valueOf(updated.getAddressId()));
            if(updated.getActive()){
                stmt.setString(3, String.valueOf(1));
            } else {
                stmt.setString(3, String.valueOf(0));
            }
            stmt.setString(4, updated.getLastUpdateBy());
            stmt.setString(5, String.valueOf(updated.getCustomerId()));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("SQL ex caught: " + ex.getMessage());
        }
        
        
        return result;
    }
    
    /**
     * Delete record from SQL DB where matches supplied customerId
     * @param custId
     * @return <int> row/s effected, or 0 if failed
     * @throws SQLException 
     */
    public int deleteCustomer(int custId) throws SQLException {
        int result = 0;
        sql = "delete from customer where customerId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)) {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(custId));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("SQL ex caught: " + ex.getMessage());
        }
        
        return result;
    }
    
// </editor-fold>
}