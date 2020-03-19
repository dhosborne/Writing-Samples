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
import java.time.LocalDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AppointmentCard{
    int appointmentId;
    int customerId;
    String customerName;
    int userId;
    String userName;
    String title;
    String description;
    String location;
    String contact;
    String type;
    String url;
    LocalDateTime start;
    LocalDateTime end;
    
    
    HelperClass helper = new HelperClass();
    public final DBConnect connection = new DBConnect();
    private PreparedStatement stmt;
    private ResultSet rs;
    private String sql;
    
// <editor-fold desc="Constructors">
    
    public AppointmentCard(){}
    
    public AppointmentCard(
                int appointmentId, 
                int customerId, 
                String customerName, 
                int userId, 
                String userName,
                String title,
                String description, 
                String location, 
                String contact, 
                String type, 
                String url, 
                LocalDateTime start, 
                LocalDateTime end) {
            this.appointmentId = appointmentId;
            this.customerId = customerId;
            this.customerName = customerName;
            this.userId = userId;
            this.userName = userName;
            this.title = title;
            this.description = description;
            this.location = location;
            this.contact = contact;
            this.type = type;
            this.url = url;
            this.start = start;
            this.end = end;
        }
    
    public AppointmentCard( 
                int customerId, 
                String customerName, 
                int userId, 
                String userName,
                String title,
                String description, 
                String location, 
                String contact, 
                String type, 
                String url, 
                LocalDateTime start, 
                LocalDateTime end) {
            this.customerId = customerId;
            this.customerName = customerName;
            this.userId = userId;
            this.userName = userName;
            this.title = title;
            this.description = description;
            this.location = location;
            this.contact = contact;
            this.type = type;
            this.url = url;
            this.start = start;
            this.end = end;
        }
    // </editor-fold>
    
// <editor-fold desc="Getters & Setters">
    


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
// </editor-fold>

// <editor-fold desc="SQL Functions">
    
    /**
     * Create an AppointmentCard by selecting data from SQL DB from associated
     * appointment matching supplied appointmentId
     * @param appointmentId
     * @return <AppointmentCard> Aggregated appointment information.
     * @throws SQLException 
     */
    public AppointmentCard getAppointmentCard(int appointmentId) throws SQLException {
        AppointmentCard apc = null;
        
        sql = "SELECT "
                + "ap.appointmentId, "
                + "ap.customerId, "
                + "cs.customerName, "
                + "ap.userId, "
                + "us.userName, "
                + "ap.title, "
                + "ap.description, "
                + "ap.location, "
                + "ap.contact, "
                + "ap.type, "
                + "ap.url, "
                + "ap.start, "
                + "ap.end "
                + "from (appointment ap) "
                + "inner join customer cs on cs.customerId = ap.customerId "
                + "inner join user us on us.userId = ap.userId "
                + "where ap.appointmentId = ?";
        
        
        try(Connection conn = DriverManager.getConnection(
                                connection.connURL,
                                connection.dbUserName,
                                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(appointmentId));
            
            rs = stmt.executeQuery();
            
            while (rs.next()){
                apc = new AppointmentCard(
                        rs.getInt("appointmentId"),
                        rs.getInt("customerId"),
                        rs.getString("customerName"),
                        rs.getInt("userId"),
                        rs.getString("userName"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        helper.utcToLDTConverter(rs.getTimestamp("start")),
                        helper.utcToLDTConverter(rs.getTimestamp("end"))
                );
            }
            
            
        } catch (SQLException ex) {
            System.out.println(
                    "An SQL Ex occured while trying to get appointment card" 
                    + ex.getMessage());
        }
        
        return apc;
    }
    
    /**
     * Get an ObservableList of all appointments in SQL Db as AppointmentCards
     * @return ObservableList<AppointmentCard> List of appointment cards
     * @throws SQLException 
     */
    public ObservableList<AppointmentCard> getAppointmentCardList() {
        AppointmentCard apc = new AppointmentCard();
        
        ObservableList<AppointmentCard> appointmentList = FXCollections.observableArrayList();
        sql = "SELECT "
                + "ap.appointmentId "
                + "from (appointment ap)";
        
        try(Connection conn = DriverManager.getConnection(
                                            connection.connURL, 
                                            connection.dbUserName, 
                                            connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
              appointmentList.add(
                apc.getAppointmentCard(rs.getInt("appointmentId")));
            }
        } catch (SQLException ex) {
            System.out.println("An SQL Ex occured while getting appointment card: " 
                    + ex.getMessage());
        }
        
        return appointmentList;
    }
    
    /**
     * Get an ObservableList of all appointments in SQL Db as AppointmentCards
     * for the specified userId
     * @param userId
     * @return ObservableList<AppointmentCard> 
     * List of appointments for specified userId
     * @throws SQLException 
     */
    public ObservableList<AppointmentCard> getAppointmentCardList(int userId) throws SQLException {
        AppointmentCard apc = new AppointmentCard();
        ObservableList<AppointmentCard> appointmentList = FXCollections.observableArrayList();
        sql = "SELECT "
                + "ap.appointmentId "
                + "from (appointment ap) "
                + "where ap.userId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                                            connection.connURL, 
                                            connection.dbUserName, 
                                            connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(userId));
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
              appointmentList.add(
                apc.getAppointmentCard(rs.getInt("appointmentId")));
            }
        }
                
        
        return appointmentList;
    }
    
     /**
     * Get an ObservableList of all appointments in SQL Db as AppointmentCards
     * by specified userName
     * @param userName
     * @return ObservableList<AppointmentCard> 
     * List of appointments for specified userName
     * @throws SQLException 
     */
    public ObservableList<AppointmentCard> getAppointmentCardList(String userName) {
        AppointmentCard apc = new AppointmentCard();
        ObservableList<AppointmentCard> appointmentList = FXCollections
                .observableArrayList();
        sql = "SELECT "
                + "ap.appointmentId, "
                + "us.userName "
                + "from (appointment ap) "
                + "inner join user us on us.userId = ap.userId "
                + "where us.userName = ?";
        
        try(Connection conn = DriverManager.getConnection(
                                            connection.connURL, 
                                            connection.dbUserName, 
                                            connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            
            rs = stmt.executeQuery();
            
            while(rs.next()){
              appointmentList.add(
                apc.getAppointmentCard(rs.getInt("appointmentId")));
            }
        } catch (SQLException ex) {
            System.out.println("Could not get AppointmentCard List by userName " 
                    + ex.getMessage());
        }

        return appointmentList;
    }
// </editor-fold>    
    
} 


    