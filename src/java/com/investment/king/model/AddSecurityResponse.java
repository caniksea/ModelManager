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
public class AddSecurityResponse extends GenericResponse{
    
    private Security security;

    public AddSecurityResponse(Security security, String response, String response_code) {
        super(response, response_code);
        this.security = security;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }       
}
