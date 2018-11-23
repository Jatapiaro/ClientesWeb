package com.jacobo.controllers;

import com.holidaywebservice.holidayservice_v2.ArrayOfHolidayCode;
import com.holidaywebservice.holidayservice_v2.Country;
import com.holidaywebservice.holidayservice_v2.HolidayCode;
import com.holidaywebservice.holidayservice_v2.HolidayService2;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceRef;
import security.bypass.SSLUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jacobotapia
 */
@Named
@ViewScoped
public class WsdlCustomController implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/holidaywebservice.com/HolidayService_v2/HolidayService2.asmx.wsdl")
    private HolidayService2 service;
    
    private StringBuilder defaultPath;
    
    private String country;
    
    private List<CustomHoliday> holidays;
    
    @PostConstruct
    public void init() {
        
        //Disable all SSL security
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();
        
        //Store context path for back button
        defaultPath = new StringBuilder();
        defaultPath.append(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
        
        
        this.country = "Canada";
        this.holidays = new ArrayList<CustomHoliday>();
        this.fetchData();
    }
    
    public void fetchData() {
        Country c = this.getCountryEnum();
        List<HolidayCode> hc = this.getHolidaysAvailable(c).getHolidayCode();
        this.getHolidays().clear();
        for ( HolidayCode co : hc ) {
            this.getHolidays().add(new CustomHoliday(
                co.getCode(),
                co.getDescription()
            ));
        }
        for ( CustomHoliday ch : this.getHolidays() ) {
            ch.setDate(
                this.getHolidayDate(c, ch.getHolidayCode(), 2019).toString()
            );
        }
    }
    
    private Country getCountryEnum() {
        switch(this.country) {
            case "Canada":
                return Country.CANADA;
            case "Great Britain and Wales":
                return Country.GREAT_BRITAIN;
            case "Northern Ireland":
                return Country.IRELAND_NORTHERN;
            case "Republic of Ireland":
                return Country.IRELAND_REPUBLIC_OF;
            case "Scotland":
                return Country.SCOTLAND;
            case "United States":
                return Country.UNITED_STATES;
        }
        return null;
    }
    
    /**
    * GIven a Country, return all their holidays
    * @param Country (enum)
    * @return ArrayOfHollidayCode
    */
    private ArrayOfHolidayCode getHolidaysAvailable(Country countryCode) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.holidaywebservice.holidayservice_v2.HolidayService2Soap port = service.getHolidayService2Soap12();
        return port.getHolidaysAvailable(countryCode);
    }
    
    /**
     * Given a Country, a holiday and a year, return the date of that holiday
     * @param Country country (enum)
     * @param String holidayCode
     * @param int year
     * @return XMLGregorianCalendar
    */
    private XMLGregorianCalendar getHolidayDate(Country countryCode, String holidayCode, int year) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        com.holidaywebservice.holidayservice_v2.HolidayService2Soap port = service.getHolidayService2Soap();
        return port.getHolidayDate(countryCode, holidayCode, year);
    }    

    
    /*
    * Handles back button action
    */
    public void goToIndex() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.defaultPath.toString());
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return this.country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the holidays
     */
    public List<CustomHoliday> getHolidays() {
        return holidays;
    }

    /**
     * @param holidays the holidays to set
     */
    public void setHolidays(List<CustomHoliday> holidays) {
        this.holidays = holidays;
    }
    
}
