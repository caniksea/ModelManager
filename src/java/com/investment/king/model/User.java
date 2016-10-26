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
public class User {
    private String response, password, name, username;
    private int role, user_id, response_code, status_id;

    public User(String response, int response_code, String username, String password, String name, int role, int user_id, int status_id) {
        this.response = response;
        this.password = password;
        this.username = username;
        this.name = name;
        this.role = role;
        this.user_id = user_id;
        this.response_code = response_code;
        this.status_id = status_id;
    }

    public User(String response, String username, int response_code, int user_id) {
        this.response = response;
        this.username = username;
        this.response_code = response_code;
        this.user_id = user_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
}
