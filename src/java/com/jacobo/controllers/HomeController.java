/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jacobo.controllers;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import javax.faces.context.FacesContext;

/**
 *
 * @author jacobotapia
 */
@Named
@ViewScoped
public class HomeController implements Serializable {
    
    private StringBuilder defaultPath;
    
    @PostConstruct
    public void init() {
        defaultPath= new StringBuilder();
        defaultPath.append(FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath());
        defaultPath.append("/faces/views/");
    }
   
    public void goToTareaUnoPractica() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.defaultPath.toString() + "tarea1/practica.xhtml");
    }
    
    public void goToTareaUnoCalculadora() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.defaultPath.toString() + "tarea1/calculadora.xhtml");
    }
    
}
