<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="pack.*"%>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

Liste des evenements : <br>
<br>
<% 
Collection<Evenement> es = (Collection<Evenement>) request.getAttribute("listeevenements");
for(Evenement e : es) {		
	
	String s = e.getNom() ;
	
%>
<%= s %> <br>
    <blockquote><p>
<%  
    String gn = e.getGroupeE().getNom();
    %> 
    <%= s %> <br>
    </p></blockquote>
    <%
}
%>

</body>
</html>