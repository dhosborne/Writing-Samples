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
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Caffeine
 */
public class ContactCard{
    private int     customerId;
    private String  customerName;
    private int     active;
    private String  address;
    private String  address2;
    private String  city;
    private String  postalCode;
    private String  country;
    private String  phone;

    DBConnect connection = new DBConnect();
    private String sql;
    private PreparedStatement stmt;
    private ResultSet rs;
    
// <editor-fold desc="Contstructors">
    public ContactCard(){}
    
    public ContactCard(
            String customerName,
            String address,
            String address2,
            String city,
            String postalCode,
            String country,
            String phone){
        this.customerName = customerName;
        this.active = 1;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;        
    }
    
    public ContactCard(
            int customerId,
            String customerName,
            int active,
            String address,
            String address2,
            String city,
            String postalCode,
            String country,
            String phone){
        this.customerId = customerId;
        this.customerName = customerName;
        this.active = active;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.phone = phone;
    }
// </editor-fold>

    /**
     * Creates an ObservableList of all customer contacts
     * @return ObservableList<Contact>
     * @throws SQLException 
     */
    public ObservableList<ContactCard> getContactList() throws SQLException {
        ObservableList<ContactCard> contactList = FXCollections.observableArrayList();

        Customer customer = new Customer();
        List<Customer> customerList = customer.getCustomerList();
        
        for (Customer c : customerList) {
            contactList.add(this.getContact(c.getCustomerId()));
        }
        
        return contactList;
    }


// <editor-fold desc="SQL Functions">   
    /**
     * Recall a customer record from the SQL DB matching supplied customerId
     * @param customerId
     * @return <Contact> Object matching supplied customerId
     * @throws SQLException 
     */
    public ContactCard getContact(int customerId) throws SQLException {
        ContactCard contact = null;
        
        sql = "select "
                + "cs.customerId,"
                + "cs.customerName,"
                + "ad.address,"
                + "ad.address2,"
                + "ci.city,"
                + "ad.postalCode,"
                + "co.country,"
                + "ad.phone,"
                + "cs.active "
                + "from (customer cs) "
                + "inner join address ad on cs.addressId = ad.addressId "
                + "inner join city ci on ad.cityId = ci.cityId "
                + "inner join country co on ci.countryId = co.countryId "
                + "where cs.customerId = ?";
        
        try(Connection conn = DriverManager.getConnection(connection.connURL, connection.dbUserName, connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(customerId));
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
                contact = new ContactCard(
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getInt("active"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getString("city"),
                        rs.getString("postalCode"),
                        rs.getString("country"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException ex) {
            System.out.println("SQL Ex caught: " + ex.getMessage());
        }
        
        return contact;
    }
// </editor-fold>
    
// <editor-fold desc="Getters & Setters">
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
// </editor-fold>    
}
