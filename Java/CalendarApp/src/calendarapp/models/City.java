/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.models;

import calendarapp.DBConnect;
import calendarapp.HelperClass;
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
public class City extends BaseClass {
    private Integer cityId;
    private String city;
    private Integer countryId;

    PreparedStatement stmt;
    ResultSet rs;
    String sql;
    HelperClass helper;
    
    DBConnect connection = new DBConnect();
    String dbURI = connection.connURL;
    String dbUserName = connection.dbUserName;
    String dbPassword = connection.dbPassword;
    
    
// <editor-fold desc="Constructors">
    public City(){}
    public City(String cityName, int countryId, String user){
        super(LocalDate.now(), user, null, null);
        this.city = cityName;
        this.countryId = countryId;
    }
    
    public City(
            int cityId, 
            String city, 
            int countryId, 
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        super(createDate, createdBy, lastUpdate, lastUpdateBy);
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
    }
// </editor-fold>
    
// <editor-fold desc="Getters & Setters">
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
// </editor-fold>
// <editor-fold desc="SQL Functions">
    
    /**
     * Recall a city from th SQL DB with the supplied cityId
     * @param id
     * @return <City> Object matching id
     * @throws SQLException 
     */
    public City recallCity(int id) throws SQLException{
        City result = null;
        
        sql = "select * from city where cityId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                this.dbURI,
                this.dbUserName,
                this.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(id));
            rs = stmt.executeQuery();
            
            if(rs.next()){
                int cityId = rs.getInt("cityId");
                String cityName = rs.getString("city");
                int countryId = rs.getInt("countryId");
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("lastUpdateBy");
                
                result = new City(
                    countryId,
                    cityName,
                    countryId,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdateBy);
            }
            
        }catch (SQLException ex) {
            System.out.print("exception caught: " + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Recall city from SQL DB city name
     * @param city
     * @return <City> Object matching supplied city name
     * @throws SQLException 
     */
    public City recallCity(String city) throws SQLException{
        City result = null;
        
        sql = "select * from city where city = ?";
        
        try(Connection conn = DriverManager.getConnection(
                this.dbURI,
                this.dbUserName,
                this.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, city);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                int cityId = rs.getInt("cityId");
                String cityName = rs.getString("city");
                int countryId = rs.getInt("countryId");
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                String createdBy = rs.getString("createdBy");
                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
                String lastUpdateBy = rs.getString("lastUpdateBy");
                
                result = new City(
                    cityId,
                    cityName,
                    countryId,
                    createDate,
                    createdBy,
                    lastUpdate,
                    lastUpdateBy);
            }
            
        }catch (SQLException ex) {
            System.out.print("exception caught: " + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Create a city entry in the SQL DB 
     * @param newCity
     * @return <int> id of the new city object or 0 if failed
     * @throws SQLException 
     */
    public int createCity(City newCity) throws SQLException {
        sql = "insert into city ("
                + "city, "
                + "countryId, "
                + "createDate, "
                + "createdBy,"
                + "lastUpdateBy) "
                + "values (?, ?, ?, ?, ?)";
        int newId = 0;
        
        try(Connection conn = DriverManager.getConnection(
                this.dbURI,
                this.dbUserName,
                this.dbPassword)){
            
            stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, newCity.getCity());
            stmt.setString(2, String.valueOf(newCity.getCountryId()));
            stmt.setString(3, String.valueOf(newCity.getCreateDate()));
            stmt.setString(4, newCity.getCreatedBy());
            stmt.setString(5, newCity.getCreatedBy());
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            
            if(rs.next()){
                newId = rs.getInt(1);
                System.out.println("city id = " + String.valueOf(newId));
            }
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
        
        return newId;
    }
    
    /**
     * Update a city record in the SQL DB
     * @param city
     * @return <int> count of row/s effected or 0 if failed
     * @throws SQLException 
     */
    public int updateCity(City city) throws SQLException {
        int result = 0;
        
        sql = "UPDATE city SET "
                + "city = ?, "
                + "countryId = ?, "
                + "lastUpdateBy = ? "
                + "where cityId = ?";
        try(Connection conn = DriverManager.getConnection(
                this.dbURI,
                this.dbUserName,
                this.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, city.getCity());
            stmt.setString(2, String.valueOf(city.getCountryId()));
            stmt.setString(3, city.getLastUpdateBy());
            stmt.setString(4, String.valueOf(city.getCityId()));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Remove city record from SQL DB matching supplied cityId
     * @param cityId
     * @return <int> row/s effected or 0 if failed
     * @throws SQLException 
     */
    public int deleteCity(int cityId) throws SQLException {
        sql = "delete from city where cityId = ?";
        int result = 0;
        
        try(Connection conn = DriverManager.getConnection(
                this.dbURI,
                this.dbUserName,
                this.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(cityId));
            result = stmt.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("ex caught: " + ex.getMessage());
        }
        
        return result;
    }    
// </editor-fold>    
}
