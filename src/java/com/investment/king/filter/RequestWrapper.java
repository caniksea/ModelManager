/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investment.king.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.owasp.html.Sanitizers;

/**
 *
 * @author @caniksea
 */
public class RequestWrapper extends HttpServletRequestWrapper {
    
    
    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }
    
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = cleanXSS(values[i]);
        }
        return encodedValues;
    }
    
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
//        if(parameter.equalsIgnoreCase("REC") || parameter.equalsIgnoreCase("param") || parameter.equalsIgnoreCase("EMAIL") 
//                || parameter.equalsIgnoreCase("pocketmoniPin") || parameter.equalsIgnoreCase("genNo") || parameter.equalsIgnoreCase("reg_email") 
//                || parameter.equalsIgnoreCase("reg_pass") || parameter.equalsIgnoreCase("lcardNumber") || parameter.equalsIgnoreCase("lcardExpiryDate")){
//            return value;
//        }
        if (value == null) {
            return null;
        }
        return cleanXSS(value);
    }
    
    private String cleanXSS(String value) {
//        LOG.info("XSS FILTER INFO - RAW VALUE: " + value);
        String sanitizedValue = Sanitizers.FORMATTING.sanitize(value);
//        LOG.info("XSS FILTER INFO - SANITIZED VALUE: " + sanitizedValue);
        return sanitizedValue;
    }
    
}
