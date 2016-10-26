<%-- 
    Document   : authenticate
    Created on : Oct 22, 2016, 6:02:55 PM
    Author     : Win7
--%>

<%@page import="com.investment.king.controller.MMEngine"%>
<%@page import="com.investment.king.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    if ((username != null || !username.equals("")) && (!password.equals("") || password != null)) {
        User u = new MMEngine().authenticateUser(username, password);
        session.setAttribute("user_data", u);
        session.setAttribute("source", "authenticate");
        response.sendRedirect("home");
    }
%>
