/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.util;

import java.security.MessageDigest;

/**
 *
 * @author @caniksea
 */
public class PasswordSecurity {
    
    private static final String HASH_SALT = "d8a8e885-ecce-42bb-8332-894f20f0d8ed";
    private static final int HASH_ITERATIONS = 1000;
    
    public String hashPassword(String passwordToHash, String concat) throws Exception {
        return hashToken(passwordToHash, concat + HASH_SALT);
    }

    private String hashToken(String token, String salt) throws Exception {
        return HashUtil.byteToBase64(getHash(HASH_ITERATIONS, token, salt.getBytes()));
    }
    
    private byte[] getHash(int numberOfIterations, String password, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < numberOfIterations; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }
    
}
