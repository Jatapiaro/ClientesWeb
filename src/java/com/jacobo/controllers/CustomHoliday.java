/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacobo.controllers;

/**
 *
 * @author jacobotapia
 */
public class CustomHoliday {
   
    private String holidayCode, name, date;
    
    public CustomHoliday(String holidayCode, String name) {
        this.holidayCode = holidayCode;
        this.name = name;
    }

    /**
     * @return the holidayCode
     */
    public String getHolidayCode() {
        return holidayCode;
    }

    /**
     * @param holidayCode the holidayCode to set
     */
    public void setHolidayCode(String holidayCode) {
        this.holidayCode = holidayCode;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return this.name + " : " + this.date;
    }
    
}
