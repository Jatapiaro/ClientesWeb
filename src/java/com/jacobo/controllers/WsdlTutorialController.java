/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacobo.controllers;

import https.footballpool_dataaccess.ArrayOftTeamCoachName;
import https.footballpool_dataaccess.Info;
import https.footballpool_dataaccess.TTeamCoachName;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;

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
    private String test = "sdadsd";
    
    public String getTest() {
        return this.test;
    }
    
    @PostConstruct
    public void init() {
        List<TTeamCoachName> coaches = this.allTeamCoachNames().getTTeamCoachName();
        for( TTeamCoachName c : coaches ) {
            System.out.println(c.getSTeamName());
        }
        defaultPath = new StringBuilder();
        defaultPath.append(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
    }

    private ArrayOftTeamCoachName allTeamCoachNames() {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        https.footballpool_dataaccess.InfoSoapType port = service.getInfoSoap();
        return port.allTeamCoachNames();
    }
    
    public void goToIndex() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.defaultPath.toString());
    }
    
}
