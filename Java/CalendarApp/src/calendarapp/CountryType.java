/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calendarapp;

/**
 *
 * @author derekosborne
 */
public class CountryType {
    String countryName;
    Integer count;

    public CountryType(String countryName, Integer count) {
        this.countryName = countryName;
        this.count = count;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    
}
