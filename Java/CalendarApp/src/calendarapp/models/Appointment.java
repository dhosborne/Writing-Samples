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
import java.time.*;



public class Appointment extends BaseClass {
    private Integer appointmentId;
    private Integer customerId;
    private Integer userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private LocalDateTime start;
    private LocalDateTime end;
    
    HelperClass helper = new HelperClass();
    public final DBConnect connection = new DBConnect();
    private PreparedStatement stmt;
    private ResultSet rs;
    private String sql;
    
// <editor-fold desc="Constructors">    
    /**
     * Empty Appointment Class
     */
    public Appointment(){}
    
    /**
     * Create a User object without id, for making new users
     * @param appointmentId
     * @param customerId
     * @param userId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param url
     * @param start
     * @param end
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdateBy 
     */
    public Appointment(
            Integer appointmentId, 
            Integer customerId, 
            Integer userId, 
            String title, 
            String description, 
            String location, 
            String contact, 
            String type, 
            String url, 
            LocalDateTime start, 
            LocalDateTime end, 
            LocalDate createDate, 
            String createdBy, 
            LocalDateTime lastUpdate,
            String lastUpdateBy) {
        super(createDate, createdBy,lastUpdate, lastUpdateBy);
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
    }
    
    /**
     * Create a User object w/ all fields
     * @param customerId
     * @param userId
     * @param title
     * @param description
     * @param location
     * @param contact
     * @param type
     * @param url
     * @param start
     * @param end
     * @param user 
     */
    public Appointment(
            Integer customerId, 
            Integer userId, 
            String title, 
            String description, 
            String location, 
            String contact, 
            String type, 
            String url, 
            LocalDateTime start, 
            LocalDateTime end,
            String user) {
        super(LocalDate.now(), user, null, user);
        this.customerId = customerId;
        this.userId = userId;
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
    
// <editor-fold desc="Getters and setters">
    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
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
    
    
// <editor-fold desc="SQL functions">
    /**
     * Recall an appointment by appointmentId and convert it to local time
     * @param apptId
     * @return <Apointment> Object of appointment returned from SQL Db
     * @throws SQLException 
     */
    public Appointment recallAppointment(int apptId) throws SQLException {
        Appointment result = null;
        
        
        sql = "select * from appointment where appointmentId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL,
                connection.dbUserName,
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(apptId));
            rs = stmt.executeQuery();
            
            while(rs.next()){
               result = new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getInt("customerId"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        helper.utcToLDTConverter(
                                rs.getTimestamp("start")
                        ),
                        helper.utcToLDTConverter(
                                rs.getTimestamp("end")
                        ),
                        rs.getDate("createDate").toLocalDate(),
                        rs.getString("createdBy"),
                        rs.getTimestamp("lastUpdate").toLocalDateTime(),
                        rs.getString("lastUpdateBy")); 
            }
        } catch (SQLException ex) {
            System.out.println("SQL exception caught: " + ex.getMessage());
        }
        
        return result;
    }
    
    /**
     * Create a new appointment in SQL DB, times are converted to UTC here
     * @param newAppt
     * @return <int> Id of newly created entry or 0 if failed
     * @throws SQLException 
     */
    public int createAppointment(Appointment newAppt) throws SQLException {
        int newId = 0;
        
        sql = "INSERT INTO appointment ("
                + "customerId,"
                + "userId, "
                + "title,"
                + "description, "
                + "location, "
                + "contact, "
                + "type, "
                + "url, "
                + "start, "
                + "end, "
                + "createDate, "
                + "createdBy, "
                + "lastUpdateBy) "
                + "values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; // 13
        try (Connection conn = DriverManager.getConnection(
                                connection.connURL,
                                connection.dbUserName,
                                connection.dbPassword)) {
            stmt = conn.prepareStatement(sql, 
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            
            // set statement strings
            stmt.setString(1, String.valueOf(newAppt.getCustomerId()));
            stmt.setString(2, String.valueOf(newAppt.getUserId()));
            stmt.setString(3, newAppt.getTitle());
            stmt.setString(4, newAppt.getDescription());
            stmt.setString(5, newAppt.getLocation());
            stmt.setString(6, newAppt.getContact());
            stmt.setString(7, newAppt.getType());
            stmt.setString(8, newAppt.getUrl());
            stmt.setString(9, String.valueOf(
                  helper.ldtToUTCConverter(newAppt.getStart())
            ));
            stmt.setString(10, String.valueOf(
                  helper.ldtToUTCConverter(newAppt.getEnd())
            ));
            stmt.setString(11, String.valueOf(newAppt.getCreateDate()));
            stmt.setString(12, newAppt.getCreatedBy());
            stmt.setString(13, String.valueOf(newAppt.getLastUpdateBy()));
            
            stmt.executeUpdate();
            
            rs = stmt.getGeneratedKeys();
            if(rs.next()){
                newId = rs.getInt(1);
            }
        } catch (SQLException ex ) {
            System.out.println("SQL ex caught: " + ex.getMessage());
        }
        
        return newId;
    }
    
    /**
     * Update an appointment in SQL Db
     * @param updated
     * @return <int> number of effected rows or 0 if failed
     * @throws SQLException 
     */
    public int updateAppointemnt(Appointment updated) throws SQLException {
        int result = 0;
        
        sql = "update appointment set "
                + "customerId = ?, "
                + "userId = ?, "
                + "title = ?, "
                + "description = ?, "
                + "location = ?, "
                + "contact = ?, "
                + "type = ?, "
                + "url = ?, "
                + "start = ?, "
                + "end = ?, "
                + "lastUpdateBy = ? "
                + "where appointmentId = ?"; // 12
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL,
                connection.dbUserName,
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, String.valueOf(updated.getCustomerId()));
            stmt.setString(2, String.valueOf(updated.getUserId()));
            stmt.setString(3, updated.getTitle());
            stmt.setString(4, updated.getDescription());
            stmt.setString(5, updated.getLocation());
            stmt.setString(6, updated.getContact());
            stmt.setString(7, updated.getType());
            stmt.setString(8, updated.getUrl());
            stmt.setString(9, String.valueOf(
                    helper.ldtToUTCConverter(updated.getStart()))
            );
            stmt.setString(10, String.valueOf(
                    helper.ldtToUTCConverter(updated.getEnd()))
            );
            stmt.setString(11, updated.getLastUpdateBy());
            stmt.setString(12, String.valueOf(updated.getAppointmentId()));
            
            result = stmt.executeUpdate();
        } catch(SQLException ex) {
            System.out.println("SQL Ex caught while updating appointment: " 
                    + ex.getMessage());
        }
        
        return result;
    }
    
    
    /**
     * Remove a record from SQL Db matching supplied appointment id
     * @param apptId
     * @return <int> number of effected rows or 0 if failed
     * @throws SQLException 
     */
    public int deleteAppointment(int apptId) throws SQLException {
        int result = 0;
        sql = "delete from appointment where appointmentId = ?";
        
        try(Connection conn = DriverManager.getConnection(
                connection.connURL,
                connection.dbUserName,
                connection.dbPassword)){
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, String.valueOf(apptId));
            result = stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("SQL Ex caught while deleting appointment: " 
                    + ex.getMessage());
        }
        
        return result;
    }
// </editor-fold> 
}
