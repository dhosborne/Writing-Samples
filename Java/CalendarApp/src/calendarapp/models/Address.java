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

/**
 *
 * @author derekosborne
 */
public class Address extends BaseClass {
    private Integer addressId;
    private String address;
    private String address2;
    private Integer cityId;
    private String postalCode;
    private String phone;

    // instatiate an SQL connection object
    DBConnect connection = new DBConnect();
    String dbURI = connection.connURL;
    String dbUserName = connection.dbUserName;
    String dbPassword = connection.dbPassword;
    
    // common 
    String sql;
    PreparedStatement stmt;
    ResultSet rs;

// <editor-fold desc="Constructors">    
    public Address(){}
    
    public Address(
            int addressId, 
            String address, 
            String address2, 
            int cityId, 
            String postalCode, 
            String phone, 
            LocalDate createDate, 
            String createdBy,
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        
        super(createDate, createdBy,lastUpdate, lastUpdateBy);
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        
    }
    
    public Address(
            String address, 
            String address2, 
            int cityId,
            String postalCode,
            String phone,
            String user){
        super(LocalDate.now(), user);
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        
    }

    
// </editor-fold>
    
// <editor-fold desc="Getters & Setters">
    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
// </editor-fold>

// <editor-fold desc="SQL Functions">
    
    
    /**
     * Recall an Address from SQL Database by address id
     * @param addressId
     * @return <Address> Object of address found in SQL DB
     * @throws SQLException 
     */
    public Address recallAddress(int addressId) throws SQLException {
        Address result = null;
        sql = "SELECT * from address where addressId = ? ";
        
        try(Connection conn = DriverManager.getConnection(dbURI, dbUserName, dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(addressId));
            rs = stmt.executeQuery();
            
            while(rs.next()){
                result = new Address(
                        rs.getInt("addressId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getInt("cityId"),
                        rs.getString("postalCode"),
                        rs.getString("phone"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy")
                );
            }
            
        } catch (SQLException ex) {
            System.out.println("caught ex recalling addres by addressId in from Address: " 
                    + ex.getMessage());
        }
        
        return result;
    }
    
    
    /**
     * Recall an Address from SQL Database by address string
     * @param address
     * @return <Address> Object of Address found in SQL Db
     * @throws SQLException 
     */
    public Address recallAddress(String address) throws SQLException {
        Address result = null;
        sql = "SELECT * from address where address = ? ";
        
        try(Connection conn = DriverManager.getConnection(dbURI, dbUserName, dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, address);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                result = new Address(
                        rs.getInt("addressId"),
                        rs.getString("address"),
                        rs.getString("address2"),
                        rs.getInt("cityId"),
                        rs.getString("postalCode"),
                        rs.getString("phone"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy")
                );
            }
            
        } catch (SQLException ex) {
            System.out.println("caught ex recalling address from Address: " 
                    + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Create a new Address SQL entry
     * @param newAddress
     * @return <int> Id of new Address
     * @throws SQLException 
     */
    public int createAddress(Address newAddress) throws SQLException {
        int newId = 0;
        
        sql = "insert into address "
                + "(address, "
                + "address2, "
                + "cityId, "
                + "postalCode, "
                + "phone, "
                + "createDate, "
                + "createdBy,"
                + "lastUpdateBy)"
                + "values (?,?,?,?,?,?,?,?)";
        
        try (Connection conn = DriverManager.getConnection(
                dbURI, 
                dbUserName, 
                dbPassword)) {
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newAddress.getAddress());
            stmt.setString(2, newAddress.getAddress2());
            stmt.setString(3, String.valueOf(newAddress.getCityId()));
            stmt.setString(4, newAddress.getPostalCode());
            stmt.setString(5, newAddress.getPhone());
            stmt.setString(6, String.valueOf(newAddress.getCreateDate()));
            stmt.setString(7, newAddress.getCreatedBy());
            stmt.setString(8, newAddress.getLastUpdateBy());
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                newId = rs.getInt(1);
                System.out.println("addressId = " + String.valueOf(newId));
            }
            
        } catch (SQLException ex) {
            System.out.println("ex caught creating address from Address: " 
                    + ex.getMessage());
        }
        
        return newId;
    }
    
    /**
     * Update Address entry in SQL database
     * @param address
     * @return <int> of effected row/s or 0 if failed
     * @throws SQLException 
     */
    public int updateAddress(Address address) throws SQLException {
        int result = 0;
        
        sql = "UPDATE address SET "
                + "address = ?, "
                + "address2 = ?, "
                + "cityId = ?, "
                + "postalCode = ?, "
                + "phone = ?, "
                + "lastUpdateBy = ? "
                + "WHERE addressId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                dbURI, 
                dbUserName, 
                dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, address.getAddress());
            stmt.setString(2, address.getAddress2());
            stmt.setString(3, String.valueOf(address.getCityId()));
            stmt.setString(4, address.getPostalCode());
            stmt.setString(5, address.getPhone());
            stmt.setString(6, address.getLastUpdateBy());
            stmt.setString(7, String.valueOf(address.getAddressId()));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("ex caught updating address from Address: " 
                    + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Delete an Address entry in the SQL Database
     * @param addressId
     * @return <int> row/s effected or 0 if failed
     * @throws SQLException 
     */
    public int deleteAddress(int addressId) throws SQLException {
        int result = 0;
        sql = "delete from address where addressId = ?";
        
        try(Connection conn = DriverManager.getConnection(dbURI, dbUserName, dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(addressId));
            result = stmt.executeUpdate();
        } catch (SQLException ex){
            System.out.println("ex caught deleting address from Address: " + ex.getMessage());
        }
        
        return result;
    }
    
//    </editor-fold>
}
