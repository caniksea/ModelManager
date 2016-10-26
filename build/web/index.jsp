<%-- 
    Document   : index
    Created on : Oct 22, 2016, 1:45:34 PM
    Author     : @caniksea
--%>

<%@page import="com.investment.king.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String source = (String) session.getAttribute("source");
    if(source == null || source.equals("")){
        source = "new";
    }
    String title = "", welcomeMsg = "Hello Guest, Please log in.";
    int userID = -99;
    String loginComment = "";
    boolean loggedIn = false, showInfo = false, admin = false, wealthManager = false, client = false;
    if (source.equals("new") || source.equals("authenticate")) {
        title = "WELCOME";
    }
    User u = (User) session.getAttribute("user_data");
    if (u != null) {
        loginComment = u.getResponse();
        if (loginComment != null && !loginComment.equals("")) {
            showInfo = true;
        }
        if (u.getResponse_code() == 0) {
            loggedIn = true;
            int role = u.getRole();
            userID = u.getUser_id();
            welcomeMsg = "Hello " + u.getName();
            if (role == 1) {
                admin = true;
            } else if (role == 2) {
                wealthManager = true;
            } else if (role == 3) {
                client = true;
            }
        }
    }
%>
<!DOCTYPE html>
<html>
    <%@include file="WEB-INF/jspf/head.jspf" %>
    <body>
        <div class="wrapper">
            <%@include file="WEB-INF/jspf/sidebar.jspf" %>
            <div class="main-panel">
                <%@include file="WEB-INF/jspf/nav.jspf" %>
                <%@include file="WEB-INF/jspf/home_content.jspf" %>
                <%@include file="WEB-INF/jspf/security_content.jspf" %>
                <%@include file="WEB-INF/jspf/addsecurity.jspf" %>
                <%@include file="WEB-INF/jspf/addUser.jspf" %>
                <%@include file="WEB-INF/jspf/editUser.jspf" %>
                <%@include file="WEB-INF/jspf/addModel.jspf" %>
                <%@include file="WEB-INF/jspf/editModel.jspf" %>
                <%@include file="WEB-INF/jspf/security2Model.jspf" %>
                <%@include file="WEB-INF/jspf/footer.jspf" %>
            </div>
        </div>
    </body>
    <%@include file="WEB-INF/jspf/scripts.jspf" %>
    <% if (showInfo && (source.equals("new") || source.equals("authenticate"))) {%>
    <script type="text/javascript">
        $(document).ready(function () {

            demo.initChartist();

            $.notify({
                icon: 'pe-7s-gift',
                message: '<%= loginComment%>'

            }, {
                type: 'info',
                timer: 3000
            });

        });
    </script>
    <% }%>
</html>
