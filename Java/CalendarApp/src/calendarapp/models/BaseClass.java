/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp.models;

import java.time.*;

/**
 *
 * @author derekosborne
 */


public class BaseClass {
    private LocalDate createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
    
    
// <editor-fold desc="Constructors">
    public BaseClass(){};
    
    public BaseClass(LocalDate createDate, String createdBy){
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdateBy = createdBy;
    }
    public BaseClass(LocalDate createDate, String createdBy, 
            LocalDateTime lastUpdate, String lastUpdateBy) {
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
// </editor-fold>

// <editor-fold desc="Getters & Setters">    
    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }
    
// </editor-fold>
}
