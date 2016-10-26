<%-- 
    Document   : away
    Created on : Oct 22, 2016, 10:14:10 PM
    Author     : Win7
--%>

<%
    session.removeAttribute("user_data");
    session.invalidate();
    response.sendRedirect("home");
%>
