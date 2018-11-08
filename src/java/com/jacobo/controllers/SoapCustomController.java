package com.jacobo.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.jacobo.web.services.Calculadora_Service;
import com.jacobo.web.services.Calculadora;

/**
 *
 * @author jacobotapia
 */
@Named
@ViewScoped
public class SoapCustomController implements Serializable {
    
    private double a;
    private double b;
    private char operation;
    private String result;
    
    private String aString, bString;
    
    
    @PostConstruct()
    public void init() {
        this.initParameters(false);
    }
    
    /*
    * Add a number to when a button number is clicked
    */
    public void addNumber(char n) { 
        if ( this.operation == 'e' ) {
            this.aString += n;
            this.result = this.aString;
        } else {
            this.bString += n;
            this.result = this.bString;
        }
        System.out.println(this.aString + " : " + this.bString);
    }
    
    /*
    * Specify the operation when an operation button is clicked
    */
    public void addOperation(char o) {
        if (this.operation == 'e') {
            this.operation = o;
        }
    }
    
    /*
    * Method to handle the = button
    */
    public void calculate() {
        this.a = Double.parseDouble(this.aString);
        this.b = Double.parseDouble(this.bString);
        String op = ""+this.operation;
        double res = this.callService(this.a, op, this.b);
        this.result = "ANS: " + res;
        this.a = res;
        this.initParameters(false);
        this.aString = "" + res;
    }
    
    /*
    * Call the web service
    * @param double a 
    * @param double b
    * @param String o
    * @return double result of a (operation) b
    */
    private double callService(double a, String o, double b) {
        Calculadora_Service service = new Calculadora_Service();
        Calculadora port = service.getCalculadoraPort();
        return port.calculate(a, o, b);
    }
    
    /*
    * Init or reste parameters
    */
    public void initParameters(boolean isAC) {
        this.operation = 'e';
        this.aString = this.bString = "";
        if ( isAC ) {
            this.result = "";
        }
    }
    

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(double b) {
        this.b = b;
    }

    /**
     * @return the operation
     */
    public char getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(char operation) {
        this.operation = operation;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }
    
}
