/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.model;

import java.util.List;

/**
 *
 * @author @caniksea
 */
public class SecurityResponse extends GenericResponse{
    
    private List<Security> securities;

    public SecurityResponse(String response, String response_code, List<Security> securities) {
        super(response, response_code);
        this.securities = securities;
    }

    public List<Security> getSecurities() {
        return securities;
    }

    public void setSecurities(List<Security> securities) {
        this.securities = securities;
    }
    
}
