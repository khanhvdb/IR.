<%-- 
    Document   : index
    Created on : Apr 25, 2017, 1:07:26 AM
    Author     : KhanhVDb
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
         <form method ="POST" action="Search">
        <input type="text" name ="value">
        <input type="submit" name ="search"/>
        </form>
        <%
            String phim = request.getParameter("value");
            
            String phima = request.getParameter("list");

        %>
            <%=phim%>
            </br>
            <%=phima%>

    </body>
</html>
