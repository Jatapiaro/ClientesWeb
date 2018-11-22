/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacobo.controllers;

import https.footballpool_dataaccess.ArrayOftTeamCoachName;
import https.footballpool_dataaccess.ArrayOftTeamPlayerName;
import https.footballpool_dataaccess.Info;
import https.footballpool_dataaccess.TTeamCoachName;
import https.footballpool_dataaccess.TTeamPlayerName;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import security.bypass.SSLUtilities;

/**
 *
 * @author jacobotapia
 */
@Named
@ViewScoped
public class WsdlTutorialController implements Serializable {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/footballpool.dataaccess.eu/info.wso.wsdl")
    private Info service;
    
    private StringBuilder defaultPath;
    private String country = "Mexico";
    private List<String> countries;
    private List<TTeamPlayerName> players;
    
    
    @PostConstruct
    public void init() {
        
        //Disable all SSL security
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();
        
        //Store context path for back button
        defaultPath = new StringBuilder();
        defaultPath.append(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
        
        this.countries = new ArrayList<String>();
        List<TTeamCoachName> coaches = this.allTeamCoachNames().getTTeamCoachName();
        for( TTeamCoachName c : coaches ) {
            getCountries().add(c.getSTeamName());
        }
        this.onCountryChange();
        
    }

    /*
    * Return all the team coaches
    * Used to get all the countries,
    * we will just use the country
    * @return ArrayOftTeamCoachName
    */
    private ArrayOftTeamCoachName allTeamCoachNames() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allTeamCoachNames();
    }
    
    private ArrayOftTeamPlayerName allPlayersWithRole(java.lang.String sTeamName, java.lang.String sRoleCode) {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allPlayersWithRole(sTeamName, sRoleCode);
    }
   
    
    
    public void onCountryChange() { 
        this.players = this.allPlayersWithRole(this.country, "defender").getTTeamPlayerName();
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
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the countries
     */
    public List<String> getCountries() {
        return countries;
    }

    /**
     * @param countries the countries to set
     */
    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    /**
     * @return the players
     */
    public List<TTeamPlayerName> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(List<TTeamPlayerName> players) {
        this.players = players;
    }
    
}
