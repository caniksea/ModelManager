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
public class UserRoleResponse extends GenericResponse{
    
    private List<UserRole> user_roles;

    public UserRoleResponse(String response, String response_code, List<UserRole> user_roles) {
        super(response, response_code);
        this.user_roles = user_roles;
    }

    public List<UserRole> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(List<UserRole> user_roles) {
        this.user_roles = user_roles;
    }
    
}
