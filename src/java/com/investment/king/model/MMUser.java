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
public class MMUser {
    
    private int user_id, role_id, status_id;
    private String name, username, password, initial_pass, user_altered_pass;

    public MMUser(int user_id, int role_id, String name, String username, int status_id, String password, String initial_pass, String user_altered_pass) {
        this.user_id = user_id;
        this.role_id = role_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.initial_pass = initial_pass;
        this.user_altered_pass = user_altered_pass;
        this.status_id = status_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInitial_pass() {
        return initial_pass;
    }

    public void setInitial_pass(String initial_pass) {
        this.initial_pass = initial_pass;
    }

    public String getUser_altered_pass() {
        return user_altered_pass;
    }

    public void setUser_altered_pass(String user_altered_pass) {
        this.user_altered_pass = user_altered_pass;
    }
}
