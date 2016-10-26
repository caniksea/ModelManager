/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.util;

import java.io.IOException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author @caniksea
 */
public class HashUtil {

    public static byte[] base64ToByte(String data) throws IOException {
        return Base64.decodeBase64(data);
    }

    public static String byteToBase64(byte[] data) {
        return new String(Base64.encodeBase64(data));
    }
}
