/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.models;

import calendarapp.DBConnect;
import java.time.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author derekosborne
 */
public class User extends BaseClass{
    private Integer userId;
    private String userName;
    private Boolean active;
    
    private final DBConnect connection = new DBConnect();
    private String sql;
    private PreparedStatement stmt;
    private ResultSet rs;
    

// <editor-fold desc="User Constructors">
    
    public User() {
        super();
    };
    
    public User(
            int userId,
            String userName, 
            int active, 
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        super(createDate, createdBy, lastUpdate, lastUpdateBy);
        this.userName = userName;
        if(active > 0){
            this.active = true;
        } else {
            this.active = false;
        }
    }
    
    public User(
            String userName, 
            int active, 
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate, 
            String lastUpdateBy) {
        super(createDate, createdBy, lastUpdate, lastUpdateBy);
        this.userName = userName;
        if(active > 0){
            this.active = true;
        } else {
            this.active = false;
        }
    }
// </editor-fold>
    
// <editor-fold desc="User Getters & Setters">
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    @Override
    public String toString(){
        return this.getUserName();
    }
// </editor-fold>
    
// <editor-fold desc="User SQL Functions">
    
    /**
     * Recall user from SQL DB where matches supplied userName, only if
     * supplied password matches record password
     * @param userName
     * @param password
     * @return <boolean> true if user found and passwords match
     * @throws SQLException 
     */
    public boolean findUserByUserName(String userName, String password) throws SQLException {
        Boolean loggedIn = false;
        sql = "SELECT * from user where userName = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword);){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
                    while(rs.next()) {
                String stored = rs.getString("password");

                if (stored.equals(password))
                {
                    this.setUserId(rs.getInt("userId"));
                    this.setUserName(rs.getString("userName"));
                    this.setActive(rs.getBoolean("active"));
                    this.setCreateDate(rs.getDate("createDate").toLocalDate());
                    this.setCreatedBy(rs.getString("createdBy"));
                    this.setLastUpdate(rs.getTimestamp("lastUpdate").toLocalDateTime());
                    this.setLastUpdateBy(rs.getString("lastUpdateBy"));
                    loggedIn = true;
                }
            }
        } catch(SQLException ex) {
            System.out.println("SQL exception logging in user " + ex.getMessage());
        }
        
        return loggedIn;
    }
    
    /**
     * Recall user from SQL DB where matches supplied userName
     * @param userName
     * @return <User> Object of record found, or null
     * @throws SQLException 
     */
    public User recallUser(String userName) throws SQLException {
        User user = null;
        sql = "Select * from user where userId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL,
                connection.dbUserName,
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            
            if(rs.next()){
                user = new User(
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getInt("active"),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy")
                );
            }
        } catch(SQLException ex) {
            System.out.println("SQL Ex occured while retreiving user: " 
                    + ex.getMessage());
        }
        
        return user;
    }
    
    /**
     * Get a list of all users from SQL DB
     * @return ObservableList<User> 
     */
    public ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        sql = "SELECT * from user";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL, 
                connection.dbUserName, 
                connection.dbPassword)){
            
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while(rs.next()){
                User user = new User(
                      rs.getInt("userId"),
                      rs.getString("userName"),
                      rs.getInt("active"),
                      rs.getDate("createDate").toLocalDate(),
                      rs.getString("createdBy"),
                      rs.getTimestamp("lastUpdate").toLocalDateTime(),
                      rs.getString("lastUpdateBy")
                );
                
                userList.add(user);
            }
        } catch(SQLException ex) {
            System.out.println("SQL exception getting users " + ex.getMessage());
        }

        return userList;
    }
// </editor-fold>
}
