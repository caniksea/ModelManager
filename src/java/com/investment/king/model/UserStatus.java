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
public class UserStatus {
    
    private int status_id;
    private String description;

    public UserStatus(int status_id, String description) {
        this.status_id = status_id;
        this.description = description;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
