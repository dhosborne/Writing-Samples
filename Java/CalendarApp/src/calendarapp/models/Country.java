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
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 *
 * @author derekosborne
 */
public class Country extends BaseClass {
    private Integer countryId;
    private String country;
    
    DBConnect connection = new DBConnect();
    private String sql;
    private PreparedStatement stmt;
    private ResultSet rs;
    
    
// <editor-fold desc="Constructors">
    public Country(){}
    public Country(String country, String createdBy){
        super(LocalDate.now(), createdBy);
        this.country = country;
        
    }
    public Country(int countryId, String country, LocalDate createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
        super(createDate, createdBy, lastUpdate, lastUpdateBy);
        this.countryId = countryId;
        this.country = country;
    }
// </editor-fold>
 
// <editor-fold desc="Getters & Setters">
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
// </editor-fold>    
    
// <editor-fold desc="SQL Functions">
    
    /**
     * Recall country record from SQL Db matching supplied countryId
     * @param id
     * @return <Country> Object matching supplied countryId
     * @throws SQLException 
     */
    public Country recallCountry(int id) throws SQLException{
        Country country = null;
        
        sql = "select * from country where countryId = ?";
        
        System.out.println("Were in recall country!");
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(id));
            rs = stmt.executeQuery();
            
            if(rs.next()){
                int countryId = rs.getInt("countryId");
                String countryName = rs.getString("country");
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("lastUpdateBy");
                
                country = new Country(
                    countryId,
                    countryName,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdateBy);
            }
            
        }catch (SQLException ex) {
            System.out.print("exception caught: " + ex.getMessage());
        }
        
        return country;
    }
    
    /**
     * Recall country from SQL DB matching the supplied countryName
     * @param countryName
     * @return <Country> Object matching supplied countryName
     * @throws SQLException 
     */
    public Country recallCountry(String countryName) throws SQLException{
        Country country = null;
        
        sql = "select * from country where country = ?";
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, countryName);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                int countryId = rs.getInt("countryId");
                String name = rs.getString("country");
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("lastUpdateBy");
                
                country = new Country(
                    countryId,
                    name,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdateBy);
            }
            
        }catch (SQLException ex) {
            System.out.print("exception caught: " + ex.getMessage());
        }
        
        return country;
    }
    
    /**
     * Create country SQL DB entry
     * @param country
     * @return <int> id of new record or 0 if failed
     * @throws SQLException 
     */
    public int createCountry(Country country) throws SQLException {
        sql = "insert into country ("
                + "country, "
                + "createDate, "
                + "createdBy,"
                + "lastUpdateBy) "
                + "values (?, ?, ?, ?)";
        int newId = 0;
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, country.getCountry());
            stmt.setString(2, String.valueOf(country.getCreateDate()));
            stmt.setString(3, country.getCreatedBy());
            stmt.setString(4, country.getCreatedBy());
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                newId = rs.getInt(1);
                
            }
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
        
        return newId;
    }
    
    /**
     * Update SQL DB entry where countryId matches that of supplied object
     * @param country
     * @return <int> row/s effected or 0 if failed
     * @throws SQLException 
     */
    public int updateCountry (Country country) throws SQLException {
        int result = 0;
    sql = "UPDATE country set "
            + "country = ?, "
            + "lastUpdateBy = ? "
            + "where countryId = ?";
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, country.getCountry());
            stmt.setString(2, country.getLastUpdateBy());
            stmt.setString(3, String.valueOf(country.getCountryId()));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Delete record from SQL DB where matches supplied countryId
     * @param countryId
     * @throws SQLException 
     */
    public void deleteCountry(int countryId) throws SQLException {
        sql = "delete from country where countryId = ?";

        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(countryId));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
    }
// </editor-fold>
}