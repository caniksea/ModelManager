/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author @caniksea
 */
public class DBConnection {
    private static Context initCtx;

    static {
        try {
            initCtx = new InitialContext();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static Connection getMySQLConnection() {
        try {
//            DataSource ds = (DataSource) initCtx.lookup("java:esaDB"); java:jboss/datasources/consoleDB
//            DataSource ds = (DataSource) initCtx.lookup("java:jboss/datasources/consoleDB");
            DataSource ds = (DataSource) initCtx.lookup("java:jboss/datasources/MMDS");
            Connection con = ds.getConnection();
            return con;
        } catch (NamingException | SQLException ce) {
//            System.out.println("WARNING::Error occured trying to connect to etmc database " + ce.getMessage());
            ce.printStackTrace();
            return null;
        }
    }
}
