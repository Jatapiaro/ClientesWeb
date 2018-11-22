/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacobo.controllers;
import com.jacobo.web.services.*;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author jacobotapia
 */
@Named
@ViewScoped
public class SoapTutorialController implements Serializable {
    
    private String defaultName, name, salute;
    private StringBuilder defaultPath;
    
    @PostConstruct
    public void init() {
      this.defaultName = this.callService("Fulanito");
      defaultPath = new StringBuilder();
      defaultPath.append(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
    }
    
    /**
     * Called when the user click on the Saludar button
     */
    public void makeSalutation() {
        if ( this.name.length() == 0 ) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ingresa tu nombre",  null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {
            this.salute = callService(this.name);
        }
    }
    
    /**
     * Given an string calles the desired service
     * @param String n
     */
    private String callService(String n) {
        try {
            HelloWebService_Service service = new HelloWebService_Service();
            HelloWebService port = service.getHelloWebServicePort();
            return port.hello(n);
        } catch(Exception e) {
            throw e;
        }          
    }

    /**
     * @return the defaultName
     */
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * @param defaultName the defaultName to set
     */
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
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
     * @return the salute
     */
    public String getSalute() {
        return salute;
    }

    /**
     * @param salute the salute to set
     */
    public void setSalute(String salute) {
        this.salute = salute;
    }
    
    public void goToIndex() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.defaultPath.toString());
    }
    
}
