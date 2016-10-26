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
public class MMUserResponse extends GenericResponse{
    
    private List<MMUser> users;

    public MMUserResponse(String response, String response_code, List<MMUser> users) {
        super(response, response_code);
        this.users = users;
    }

    public List<MMUser> getUsers() {
        return users;
    }

    public void setUsers(List<MMUser> users) {
        this.users = users;
    }
}
