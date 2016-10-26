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
public class UserStatusResponse extends GenericResponse{
    
    private List<UserStatus> all_status;

    public UserStatusResponse(String response, String response_code, List<UserStatus> all_status) {
        super(response, response_code);
        this.all_status = all_status;
    }

    public List<UserStatus> getAll_status() {
        return all_status;
    }

    public void setAll_status(List<UserStatus> all_status) {
        this.all_status = all_status;
    }
}
