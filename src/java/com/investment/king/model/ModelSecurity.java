/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.model;

/**
 *
 * @author @caniksea
 */
public class ModelSecurity {
    
    private Security security;
    private double percentage;

    public ModelSecurity(Security security, double percentage) {
        this.security = security;
        this.percentage = percentage;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
    
}
